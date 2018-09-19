package au.gov.nehta.vendorlibrary.clinicalpackage.util;/*
 * Copyright 2012 NEHTA
 *
 * Licensed under the NEHTA Open Source (Apache) License; you may not use this
 * file except in compliance with the License. A copy of the License is in the
 * 'license.txt' file, which should be provided with this work.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

import au.gov.nehta.vendorlibrary.clinicalpackage.core.SubmissionSet;
import au.gov.nehta.xsp.XspException;
import au.net.electronichealth.ns.cdapackage.xsd.esignature._2012.PersonNameType;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link PackageCreationSample}
 * Sample CDA package creation example.
 */
public final class PackageCreationSample {

  /**
   * Private constructor.
   */
  private PackageCreationSample() {
  }

  /**
   * Sample values.
   */
  private static final String PRIVATE_KEY_STORE_PASSWORD = "kspassword";
  private static final String PRIVATE_KEY_PASSWORD = "kppassword";
  private static final String RESOURCES_DIR = "./src/sample/resources/";
  private static final String PRIVATE_KEY_STORE_TYPE = "JKS";
  private static final String PRIVATE_KEY_STORE_FILE = "keystore.jks";
  private static final String PRIVATE_KEY_STORE_PATH = RESOURCES_DIR + PRIVATE_KEY_STORE_FILE;
  private static final String PRIVATE_KEY_ALIAS = "8003620000020052";
  private static final String CERTIFICATE_ALIAS = "8003620000020052";

  /**
   * Retrieve cert from supplied keystore.
   *
   * @return {@link java.security.cert.X509Certificate}
   */
  private static X509Certificate getCertificate() {
    FileInputStream fis = null;
    try {

      KeyStore keyStore = KeyStore.getInstance(PRIVATE_KEY_STORE_TYPE);
      fis = null;
      try {
        fis = new FileInputStream(PRIVATE_KEY_STORE_PATH);
        keyStore.load(fis, PRIVATE_KEY_STORE_PASSWORD.toCharArray());
      } catch (CertificateException e) {
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
   * @return {@link java.security.PrivateKey}
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
   * Main method.
   */
  public static void main(String[] args) throws IOException, TransformerException, XspException, ParserConfigurationException, JAXBException, URISyntaxException, SignatureGenerationException {

    // Approver name.
    PersonNameType name = createApproverName(
      "Smith",
      createStringList("Andrew", "James"),
      createStringList("III"),
      createStringList("Dr")
    );

    // Approver ID.
    String id = URI.create("http://ns.electronichealth.net.au/id/hi/hpii/1.0/8003610000000000").toASCIIString();

    // Submission set to be built
    SubmissionSet subset;

    // Submission Set builder object responsible for encapsulating all members
    SubmissionSet.Builder subsetBuilder = new SubmissionSet.Builder();

    // Add root document to submission set.
    byte[] rootContent = readFile(RESOURCES_DIR + "SampleSHS.xml");
    subsetBuilder.rootDocument(rootContent);

    // Add attachments to submission set.
    subsetBuilder
      .attachment("Image1.jpg", readFile(RESOURCES_DIR + "Image1.jpg"))
      .attachment("Image2.png", readFile(RESOURCES_DIR + "Image2.png"));

    // Add signature to package
    subsetBuilder.signature(PackagingUtility.generateSignature(
      rootContent,
      getCertificate(),
      getPrivateKey(),
      id,
      name
    ));


    subset = subsetBuilder.build();

    PackagingUtility.writeZip(subset, "C:\\projects\\tmp\\ClinicalPackage\\create_sample.zip");

  }

  /**
   * Helper method to read file.
   *
   * @param s File to read.
   * @return byte array of file contents.
   * @throws java.io.IOException Thrown if file cannot be read.
   */
  private static byte[] readFile(String s) throws IOException {

    File file = new File(s);
    byte[] result = new byte[(int) file.length()];
    InputStream input = null;
    try {
      int totalBytesRead = 0;
      input = new BufferedInputStream(new FileInputStream(file));
      while (totalBytesRead < result.length) {
        int bytesRemaining = result.length - totalBytesRead;
        int bytesRead = input.read(result, totalBytesRead, bytesRemaining);
        if (bytesRead > 0) {
          totalBytesRead = totalBytesRead + bytesRead;
        }
      }
    } finally {
      assert input != null;
      input.close();
    }
    return result;
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
