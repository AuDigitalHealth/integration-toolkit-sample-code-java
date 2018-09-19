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
import au.gov.nehta.xsp.XspException;
import org.w3c.dom.Document;

import javax.crypto.SecretKey;
import javax.security.auth.x500.X500PrivateCredential;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public final class SignedEncryptedContainerProfileUtil {

  /**
   * Default private constructor for Utility class.
   */
  private SignedEncryptedContainerProfileUtil() {
  }

  public static Document getSignedEncryptedPayload(final String sensitivePayload,
                                                   List<X500PrivateCredential> certificateKeyPairs,
                                                   X509Certificate encryptingCert) throws XspException {

    ArgumentUtils.checkNotNullNorBlank(sensitivePayload, "sensitivePayload");
    ArgumentUtils.checkNotNull(certificateKeyPairs, "certificateKeyPairs");
    ArgumentUtils.checkNotNull(encryptingCert, "encryptingCert");

    Document signedData = SignedContainerProfileUtil.getSignedPayload(sensitivePayload, certificateKeyPairs);
    Document signedEncryptedPayload = EncryptedContainerProfileUtil.getEncryptedPayload(signedData, encryptingCert);

    return signedEncryptedPayload;
  }


  public static Document getSignedEncryptedPayload(final String sensitivePayload,
                                                   SecretKey sessionKey,
                                                   List<X500PrivateCredential> certificateKeyPairs,
                                                   List<X509Certificate> encryptingCertificates) throws XspException {
    ArgumentUtils.checkNotNullNorBlank(sensitivePayload, "sensitivePayload");
    ArgumentUtils.checkNotNull(sessionKey, "sessionKey");
    ArgumentUtils.checkNotNull(certificateKeyPairs, "certificateKeyPairs");
    ArgumentUtils.checkNotNull(encryptingCertificates, "encryptingCertificates");

    Document signedEncryptedPayload = null;
    Document signedData = SignedContainerProfileUtil.getSignedPayload(sensitivePayload, certificateKeyPairs);
    signedEncryptedPayload = EncryptedContainerProfileUtil.getEncryptedPayload(signedData, sessionKey, encryptingCertificates);

    return signedEncryptedPayload;
  }

  public static Document getSignedEncryptedPayload(final String sensitivePayload,
                                                   SecretKey sessionKey,
                                                   List<X500PrivateCredential> certificateKeyPairs,
                                                   X509Certificate encryptingCert) throws XspException {

    ArgumentUtils.checkNotNull(encryptingCert, "encryptingCert");

    List<X509Certificate> encryptingCertificates = new ArrayList<X509Certificate>();
    encryptingCertificates.add(encryptingCert);
    return getSignedEncryptedPayload(sensitivePayload, sessionKey, certificateKeyPairs, encryptingCertificates);
  }

  public static Document getDataFromSignedEncryptedPayload(final Document signedEncryptedPayload,
                                                           X500PrivateCredential privateCredential) throws XspException {
    ArgumentUtils.checkNotNull(signedEncryptedPayload, "signedEncryptedPayload");
    ArgumentUtils.checkNotNull(privateCredential, "privateCredential");

    Document decryptedDocument = EncryptedContainerProfileUtil.getDecryptedPayload(signedEncryptedPayload, privateCredential);
    Document extractedDocument = SignedContainerProfileUtil.getDataFromSignedPayload(decryptedDocument);

    return extractedDocument;

  }
}
