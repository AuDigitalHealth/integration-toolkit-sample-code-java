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

import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.security.auth.x500.X500PrivateCredential;

import org.w3c.dom.Document;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.vendorlibrary.common.DOMUtil;
import au.gov.nehta.xsp.CertificateValidationException;
import au.gov.nehta.xsp.CertificateValidator;
import au.gov.nehta.xsp.CertificateVerificationException;
import au.gov.nehta.xsp.CertificateVerifier;
import au.gov.nehta.xsp.SignatureValidationException;
import au.gov.nehta.xsp.SignedContainerProfileService;
import au.gov.nehta.xsp.XspException;
import au.gov.nehta.xsp.XspFactory;
import au.gov.nehta.xsp.XspVersion;

public final class SignedContainerProfileUtil {

  /**
   * CertificateVerifier instance to sign/verify signed data. Validation ignored with an assumption that the certificates
   * under test are valid. <b>MUST INCLUDE VALIDATION CODE FOR PRODUCTION PURPOSE</b>
   */
  public static final CertificateVerifier NULL_CERTIFICATE_VERIFIER = new CertificateVerifier() {
    public void verify(X509Certificate certificate)
      throws CertificateVerificationException, XspException {
      // Do nothing. Implement this method for production code.
    }
	
  };
  
  /**
   * CertificateVerifier instance to sign/verify signed data. Validation ignored with an assumption that the certificates
   * under test are valid. <b>MUST INCLUDE VALIDATION CODE FOR PRODUCTION PURPOSE</b>
   */
  public static final CertificateValidator NULL_CERTIFICATE_VALIDATOR = new CertificateValidator() {
	
	@Override
	public void validate(X509Certificate arg0) throws CertificateValidationException, XspException {
		// Do nothing. Implement this method for production code.
		}
	};

    

  private static final Logger LOG = Logger.getLogger(SignedContainerProfileUtil.class.getName());

  /**
   * Default private constructor for Utility class.
   */
  private SignedContainerProfileUtil() {
  }

  public static Document getSignedPayload(final String sensitivePayload, final X509Certificate siginingCert,
                                          final PrivateKey siginingPrivateKey) throws XspException {
    ArgumentUtils.checkNotNullNorBlank(sensitivePayload, "sensitivePayload");
    ArgumentUtils.checkNotNull(siginingCert, "siginingCert");
    ArgumentUtils.checkNotNull(siginingPrivateKey, "siginingPrivateKey");

    // Get the signing private credentials
    List<X500PrivateCredential> certificateKeyPairs = new ArrayList<X500PrivateCredential>();
    certificateKeyPairs.add(new X500PrivateCredential(siginingCert,
      siginingPrivateKey));

    return getSignedPayload(sensitivePayload, certificateKeyPairs);

  }


  public static Document getSignedPayload(final String sensitivePayload,
                                          final List<X500PrivateCredential> certificateKeyPairs) throws XspException {
    try {
      ArgumentUtils.checkNotNullNorBlank(sensitivePayload, "sensitivePayload");
      ArgumentUtils.checkNotNull(certificateKeyPairs, "certificateKeyPairs");
      if (certificateKeyPairs.size() > 0) {
        Document signedDocument = null;

        Document docToSign = DOMUtil.getDocumentFromXML(sensitivePayload);
        signedDocument = getSignedContainerProfileService().create(docToSign,
          certificateKeyPairs);
        return signedDocument;
      }
    } catch (XspException e) {
      throw new XspException("Failed to sign the document.");
    }
    throw new XspException("Failed to sign the document.");
  }

  /**
   * Get the 'DigestValue' of each signature in a <code>SignedPayload</code> XML document.
   *
   * @param containerDoc A DOM {@link Document} structured according to the
   *                     <code>signedPayload</code> element declared in the
   *                     <em>XML Secured Payload Schema</em>. Cannot be null.
   * @return The 'DigestValue' of each signature in a <code>SignedPayload</code> XML document.
   *         (in the order that the signature appears in the container document)
   * @throws XspException If there are any other errors extracting the digest values.
   */
  public static List<byte[]> getDigestValue(Document containerDoc) throws XspException {
    return getSignedContainerProfileService().getDigestValues(containerDoc);
  }

  /**
   * Get the 'DigestValue' of each signature in a <code>SignedPayload</code> XML document.
   *
   * @param containerDoc A DOM {@link Document} structured according to the
   *                     <code>signedPayload</code> element declared in the
   *                     <em>XML Secured Payload Schema</em>. Cannot be null.
   * @return The 'DigestValue' of each signature in a <code>SignedPayload</code> XML document.
   *         (in the order that the signature appears in the container document)
   * @throws XspException If there are any other errors extracting the digest values.
   */
  public static List<X509Certificate> getSigningCertificates(Document containerDoc) throws XspException {
    return getSignedContainerProfileService().getSigningCertificates(containerDoc);
  }


  /**
   * Returns the extracted data/payload form the provided signedPayload
   * Note: <br>
   * This uses the default private key from the keystore.
   *
   * @param signedDocument
   * @return the payload as {@link Document}
   */
  public static Document getDataFromSignedPayload(
    final Document signedDocument) throws XspException {
    ArgumentUtils.checkNotNull(signedDocument, "signedDocument");
    Document sensitivePayload = null;
    try {
      sensitivePayload = getSignedContainerProfileService().getData(signedDocument);
      ArgumentUtils.checkNotNull(sensitivePayload, "extractedDocument");
      return sensitivePayload;
    } catch (IllegalArgumentException ex) {
      throw new XspException(ex);
    } catch (XspException ex) {
      // TODO: If this exception is caught, it will never be thrown.
    }
    return sensitivePayload;
  }

  /**
   * Verifies the signature of a signed document.
   * <p/>
   * Note: <br>
   * This uses the default private key from the keystore.
   *
   * @param signedDocument      the signed document
   * @param certificateVerifier the certificate verifier implementation to be used to verify the certificate used
   *                            to verify the signature.
   * @return the payload as {@link Document}
   * @deprecated use the verifySignature() that takes a CertificateValidator
   */
  public static boolean verifySignature(
    final Document signedDocument,
    final CertificateVerifier certificateVerifier) throws SignatureValidationException {
    boolean signatureVerified = false;
    ArgumentUtils.checkNotNull(signedDocument, "signedDocument");
    Document sensitivePayload = null;
    try {
      getSignedContainerProfileService().check(signedDocument, certificateVerifier);
      signatureVerified = true;
    } catch (XspException e) {
      throw new SignatureValidationException("Failed to verify the signature", e);
    } catch (CertificateVerificationException e) {
      throw new SignatureValidationException("Failed to verify the signature", e);
    } catch (CertificateValidationException e) {
    	 throw new SignatureValidationException("Failed to verify the signature", e);
	}
    return signatureVerified;
  }
  
  /**
   * Verifies the signature of a signed document.
   * <p/>
   * Note: <br>
   * This uses the default private key from the keystore.
   *
   * @param signedDocument      the signed document
   * @param certificateVerifier the certificate verifier implementation to be used to verify the certificate used
   *                            to verify the signature.
   * @return the payload as {@link Document}
   */
  public static boolean verifySignature(
    final Document signedDocument,
    final CertificateValidator certificateValidator) throws SignatureValidationException {
    boolean signatureVerified = false;
    ArgumentUtils.checkNotNull(signedDocument, "signedDocument");
    Document sensitivePayload = null;
    try {
      getSignedContainerProfileService().check(signedDocument, certificateValidator);
      signatureVerified = true;
    } catch (XspException e) {
      throw new SignatureValidationException("Failed to verify the signature", e);
    }catch (CertificateValidationException e) {
    	 throw new SignatureValidationException("Failed to verify the signature", e);
	}
    return signatureVerified;
  }

  /**
   * Verifies the signature of a signed document.
   * Note: <br>
   * This uses the default private key from the keystore.
   * This uses certificate verifier which does NOT perform any verification.
   *
   * @param signedDocument the signed document
   * @return the payload as {@link Document}
   */
  public static boolean verifySignature(
    final Document signedDocument) throws SignatureValidationException {
    return verifySignature(signedDocument, NULL_CERTIFICATE_VALIDATOR);
  }

  private static SignedContainerProfileService getSignedContainerProfileService() throws XspException {
    return XspFactory.getInstance()
      .getSignedContainerProfileService(XspVersion.V_2010);
  }

  

}



