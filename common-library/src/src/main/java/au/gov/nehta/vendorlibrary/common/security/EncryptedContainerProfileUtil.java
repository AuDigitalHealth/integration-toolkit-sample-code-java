/*
 * Copyright 2011 NEHTA
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
package au.gov.nehta.vendorlibrary.common.security;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.xsp.EncryptedContainerProfileService;
import au.gov.nehta.xsp.KeyMismatchException;
import au.gov.nehta.xsp.XspException;
import au.gov.nehta.xsp.XspFactory;
import au.gov.nehta.xsp.XspVersion;
import org.w3c.dom.Document;

import javax.crypto.SecretKey;
import javax.security.auth.x500.X500PrivateCredential;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public final class EncryptedContainerProfileUtil {

  /**
   * Default private constructor for Utility class.
   */
  private EncryptedContainerProfileUtil() {
  }

  public static Document getEncryptedPayload(Document signedDocument,
                                             X509Certificate encryptingCert) {
    ArgumentUtils.checkNotNull(signedDocument, "signedDocument");
    ArgumentUtils.checkNotNull(encryptingCert, "encryptingCert");

    Document encryptedDocument = null;
    try {

      List<X509Certificate> certificate = new ArrayList<X509Certificate>();
      certificate.add(encryptingCert);
      encryptedDocument = getEncryptedContainerProfileService().create(signedDocument,
        certificate);
    } catch (XspException e) {
      e.printStackTrace();
    }

    return encryptedDocument;
  }

  public static Document getEncryptedPayload(Document signedDocument,
                                             SecretKey sessionKey,
                                             List<X509Certificate> encryptingCertificates) {
    ArgumentUtils.checkNotNull(signedDocument, "signedDocument");
    ArgumentUtils.checkNotNull(sessionKey, "sessionKey");
    ArgumentUtils.checkNotNull(encryptingCertificates, "encryptingCertificates");

    Document encryptedDocument = null;
    try {

      encryptedDocument = getEncryptedContainerProfileService().create(signedDocument, sessionKey,
        encryptingCertificates);
    } catch (XspException e) {
      e.printStackTrace();
    }

    return encryptedDocument;

  }

  public static Document getEncryptedPayload(Document signedDocument,
                                             SecretKey sessionKey,
                                             X509Certificate encryptingCert) {
    ArgumentUtils.checkNotNull(encryptingCert, "encryptingCert");
    List<X509Certificate> encryptingCertificates = new ArrayList<X509Certificate>();
    encryptingCertificates.add(encryptingCert);
    return getEncryptedPayload(signedDocument, sessionKey, encryptingCertificates);
  }

  public static Document getDecryptedPayload(
    Document encryptedPayload, X500PrivateCredential decryptingPrivateCredential) throws XspException {
    ArgumentUtils.checkNotNull(encryptedPayload, "encryptedPayload");
    ArgumentUtils.checkNotNull(decryptingPrivateCredential, "decryptingPrivateCredential");
    Document decryptedPayload = null;
    try {
      decryptedPayload = getEncryptedContainerProfileService().getData(encryptedPayload,
        decryptingPrivateCredential);
    } catch (KeyMismatchException e) {
      throw new XspException(e);
    }
    return decryptedPayload;
  }

  public static Document getDecryptedPayload(
    Document encryptedPayload, SecretKey sessionKey) throws XspException {
    ArgumentUtils.checkNotNull(encryptedPayload, "encryptedPayload");
    ArgumentUtils.checkNotNull(sessionKey, "sessionKey");
    Document decryptedPayload = null;
    try {
      decryptedPayload = getEncryptedContainerProfileService().getData(encryptedPayload,
        sessionKey);
    } catch (KeyMismatchException e) {
      throw new XspException(e);
    }
    return decryptedPayload;
  }

  private static EncryptedContainerProfileService getEncryptedContainerProfileService() throws XspException {
    return XspFactory.getInstance()
      .getEncryptedContainerProfileService(XspVersion.V_2010);
  }


}
