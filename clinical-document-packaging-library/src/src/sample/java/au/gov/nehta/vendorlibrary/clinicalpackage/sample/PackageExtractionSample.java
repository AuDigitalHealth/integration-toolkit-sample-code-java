/*
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
package au.gov.nehta.vendorlibrary.clinicalpackage.sample;

import au.gov.nehta.vendorlibrary.clinicalpackage.core.Member;
import au.gov.nehta.vendorlibrary.clinicalpackage.core.SubmissionSet;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.PackagingUtility;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.PackageExtractionException;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.SignatureGenerationException;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.VerificationException;
import au.gov.nehta.xsp.CertificateVerificationException;
import au.gov.nehta.xsp.SignatureValidationException;
import au.gov.nehta.xsp.XspException;
import org.xml.sax.SAXException;

import javax.activation.MimeTypeParseException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * Sample class showing CDA package extraction from a package ZIP file.
 */
public final class PackageExtractionSample {

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
   * Private constructor.
   */
  private PackageExtractionSample() {
  }

  /**
   * Main method.
   *
   * @param args Any supplied arguments.
   */
  public static void main(String[] args) throws IOException, SAXException, SignatureValidationException, XPathExpressionException, MimeTypeParseException, ParserConfigurationException, CertificateVerificationException, XspException, SignatureGenerationException, PackageExtractionException, VerificationException {

    // Specify the path of the file you want to import here.

    List<String> zips = new ArrayList<String>();
    zips.add("C:/projects/tmp/ClinicalPackage/create_sample.zip");

    for (String zip : zips) {
      System.out.println("---------------------------------------------------\nZIP Path: " + zip + "\n---------------------------------------------------");

      // Extract the package contents and return a CDAPackage.
      SubmissionSet subset = PackagingUtility.extractPackage(zip);

      if (subset != null) {
        System.out.println("Signature Present: " + (subset.getSignature() != null));
        printFile("\tRoot Document", subset.getRootDocument());

        List<Member> attachments = subset.getAttachments();

        if (attachments != null) {
          for (int i = 0; i < attachments.size(); i++) {
            printFile("\tAttachment " + (i + 1), attachments.get(i));
          }
        }

        if (subset.getSignature() != null) {
          printFile("\tSignature", subset.getSignature());

          PackagingUtility.verifyPackage(subset, null);
        }
      }
    }
  }

  /**
   * Helper utility method to output a file member.
   *
   * @param name name of the the file.
   * @param file file (type {@link Member} object.
   */
  public static void printFile(String name, Member file) {
    // Output member information.
    System.out.println("\n" + name + ": \n");
    System.out.println("\t\tURI: " + file.getUri());
    System.out.println("\t\tFile Content: " + (file.getFileContent() != null ? "present" : "absent"));
  }

  private static X509Certificate getCertificate() throws FileNotFoundException {
    KeyStore keyStore = null;
    try {
      keyStore = KeyStore.getInstance(PRIVATE_KEY_STORE_TYPE);
    } catch (KeyStoreException e) {
      e.printStackTrace();
    }
    try {
      if (keyStore != null) {
        keyStore.load(new FileInputStream(PRIVATE_KEY_STORE_PATH), PRIVATE_KEY_STORE_PASSWORD.toCharArray());
        return (X509Certificate) keyStore.getCertificate(CERTIFICATE_ALIAS);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (CertificateException e) {
      e.printStackTrace();
    } catch (KeyStoreException e) {
      e.printStackTrace();
    }

    return null;
  }
}
