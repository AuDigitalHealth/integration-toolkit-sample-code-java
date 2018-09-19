import au.gov.nehta.vendorlibrary.clinicalpackage.core.SubmissionSet;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.PackageExtractionException;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.PackagingUtility;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.SignatureGenerationException;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.VerificationException;
import au.net.electronichealth.ns.cdapackage.xsd.esignature._2012.PersonNameType;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class PackageUtilityTest {

  private static final int BUFFER_SIZE = 16384;

  /**
   * Sample values.
   */
  private static final String PRIVATE_KEY_STORE_PASSWORD = "kspassword";
  private static final String PRIVATE_KEY_PASSWORD = "kppassword";
  private static final String RESOURCES_DIR = "src/test/resources/";
  private static final String PRIVATE_KEY_STORE_TYPE = "JKS";
  private static final String PRIVATE_KEY_STORE_FILE = "keystore.jks";
  private static final String PRIVATE_KEY_STORE_PATH = String.format("%s%s", RESOURCES_DIR, PRIVATE_KEY_STORE_FILE);
  private static final String PRIVATE_KEY_ALIAS = "8003620000020052";
  private static final String CERTIFICATE_ALIAS = "8003620000020052";

  @Test
  public void testCreateZip() throws Exception {
    PackagingUtility.createZip(new SubmissionSet.Builder().rootDocument("rootdocument_filecontent".getBytes()).build());
  }

  @Test
  public void testPlaceholder() throws Exception {

    File rootDocument = new File("src/test/resources/CrossLanguageTests/MissingAttachments.XML");

    // Approver name.
    PersonNameType name = createApproverName(
      "Smith",
      createStringList("Andrew", "James"),
      createStringList("III"),
      createStringList("Dr")
    );

    PackagingUtility.writeZip(new SubmissionSet.Builder()
      .rootDocument(loadFile(rootDocument))
      .signature(PackagingUtility.generateSignature(
        loadFile(rootDocument),
        getCertificate(),
        getPrivateKey(),
        "approverId",
        name
      )).build(),
      "src/test/resources/CrossLanguageTests/MissingAttachments.zip"
    );
  }

  @Test
  public void testWriteZip() throws Exception {
    SubmissionSet submissionSet = PackagingUtility.extractPackage("src/test/resources/BasePackage.zip");
    //PackagingUtility.writeZip(submissionSet, "src/test/resources/NewPackage.zip");
    SubmissionSet newPackage = PackagingUtility.extractPackage("src/test/resources/NewPackage.zip");
    Assert.assertTrue(submissionSet.equals(newPackage));
  }

   @Test
  public void testVerifySignature() throws Exception {
    SubmissionSet pkg = PackagingUtility.extractPackage("src/test/resources/BasePackage.zip");
    PackagingUtility.verifyPackage(pkg, null);
  }

  @Test(expected = VerificationException.class)
  public void testVerifySignatureModifiedSignature() throws Exception {
    SubmissionSet pkg = PackagingUtility.extractPackage("src/test/resources/BasePackageBadSig.zip");

    try {
      PackagingUtility.verifyPackage(pkg, null);
    } catch (SignatureGenerationException e) {
      Assert.assertTrue(e.getMessage().contains("Unable to verify signature"));
      throw e;
    }
  }

  @Test(expected = PackageExtractionException.class)
  public void testVerifySignatureModifiedRoot() throws Exception {
    SubmissionSet pkg = PackagingUtility.extractPackage("src/test/resources/BasePackageBadRoot.zip");
    try {
      PackagingUtility.verifyPackage(pkg, null);
    } catch (SignatureGenerationException e) {
      Assert.assertTrue(e.getMessage().contains("Root hash in the eSignature does not match the package's root document:"));
      throw e;
    }
  }
  
  @Test 
  public void testExtractingALargePackage() throws Exception {
    SubmissionSet pkg = PackagingUtility.extractPackage("src/test/resources/BigPackage.zip");
    try {
      PackagingUtility.verifyPackage(pkg, null);
    } catch (SignatureGenerationException e) {
      Assert.assertTrue(e.getMessage().contains("Root hash in the eSignature does not match the package's root document:"));
      throw e;
    }
  }
 
  @Test 
  public void testExtractingVicHealthPackage() throws Exception {
	  
	/*
	 * This package was generating IO Exceptions for vic health  
	 */
	  
    SubmissionSet pkg = PackagingUtility.extractPackage("src/test/resources/1.3.16.1.38818.231374586-vic-health.zip");
    
    PackagingUtility.verifyPackage(pkg, null);
   
  }

  @Test
  public void testNonStandardNames() throws Exception {
    SubmissionSet pkg = PackagingUtility.extractPackage("src/test/resources/CDA.zip");
    PackagingUtility.verifyPackage(pkg, null);
  }

  @Test
  public void testAttachmentsWithFolders() throws Exception {
    SubmissionSet pkg = PackagingUtility.extractPackage("src/test/resources/CDA.zip");
    PackagingUtility.verifyPackage(pkg, null);
  }


  /**
   * Load the contents of a file.
   *
   * @param file {@link java.io.File} object to load.
   * @return The contents of the file.
   * @throws java.io.IOException thrown in the event the file cannot be read in to memory.
   */
  public static byte[] loadFile(File file) throws IOException {
    if (!file.exists()) {
      throw new IllegalArgumentException("The file [" + file + "] does not exist.");
    }

    FileInputStream is = new FileInputStream(file);

    try {

      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      int nRead;
      byte[] data = new byte[BUFFER_SIZE];

      while ((nRead = is.read(data, 0, data.length)) != -1) {
        buffer.write(data, 0, nRead);
      }

      buffer.flush();
      return buffer.toByteArray();
    } finally {
      is.close();
    }
  }

  /**
   * Retrieve cert from supplied keystore.
   *
   * @return {@link X509Certificate}
   */
  private static X509Certificate getCertificate() {
    FileInputStream fis = null;
    try {

      KeyStore keyStore = KeyStore.getInstance(PRIVATE_KEY_STORE_TYPE);
      fis = null;
      try {
        fis = new FileInputStream(PRIVATE_KEY_STORE_PATH);
        keyStore.load(fis, PRIVATE_KEY_STORE_PASSWORD.toCharArray());
      } catch (java.security.cert.CertificateException e) {
        e.printStackTrace();
      }
      return (X509Certificate) keyStore.getCertificate(CERTIFICATE_ALIAS);
    } catch (KeyStoreException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (fis != null) {
        try {
          fis.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return null;
  }

  /**
   * Retrieve private key from supplied keystore.
   *
   * @return {@link PrivateKey}
   */
  private static PrivateKey getPrivateKey() {

    KeyStore keyStore = null;
    try {
      keyStore = KeyStore.getInstance(PRIVATE_KEY_STORE_TYPE);
    } catch (KeyStoreException e) {
      e.printStackTrace();
    }
    try {
      if (keyStore != null) {
        keyStore.load(new FileInputStream(PRIVATE_KEY_STORE_PATH), PRIVATE_KEY_STORE_PASSWORD.toCharArray());
        return (PrivateKey) keyStore.getKey(PRIVATE_KEY_ALIAS, PRIVATE_KEY_PASSWORD.toCharArray());
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (CertificateException e) {
      e.printStackTrace();
    } catch (UnrecoverableKeyException e) {
      e.printStackTrace();
    } catch (KeyStoreException e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * Helper method for creating an approver's name.
   *
   * @param familyName Approver's family name.
   * @param givenNames Approver's given name(s).
   * @param suffixes   Approver's suffix(es).
   * @param titles     Approver's title(s).
   * @return
   */
  private static PersonNameType createApproverName(String familyName, List<String> givenNames, List<String> suffixes, List<String> titles) {

    PersonNameType personName = new PersonNameType();
    personName.setFamilyName(familyName);
    personName.getGivenName().addAll(givenNames);
    personName.getNameSuffix().addAll(suffixes);
    personName.getNameTitle().addAll(titles);

    return personName;
  }

  /**
   * Helper method for generating a variable number of strings.
   *
   * @param strings String(s) to add to a list.
   * @return List of Strings.
   */
  private static List<String> createStringList(String... strings) {
    List<String> output = new ArrayList<String>();
    for (String string : strings) {
      output.add(string);
    }
    return output;
  }
}
