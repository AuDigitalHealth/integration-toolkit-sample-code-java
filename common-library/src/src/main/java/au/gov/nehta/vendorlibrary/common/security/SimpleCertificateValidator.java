package au.gov.nehta.vendorlibrary.common.security;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;

import au.gov.nehta.xsp.CertificateValidationException;
import au.gov.nehta.xsp.CertificateValidator;
import au.gov.nehta.xsp.XspException;

/**
 * A baseline non-null implementation of the security validator, intended to be extended
 * by implementors to add additional functionality.
 * 
 *  The base check will cover certificate validiity and trust
 *
 */
public class SimpleCertificateValidator implements CertificateValidator{

	private final KeyStore truststore;
	
	/**
	 * Initialise the validator with a keystore containing certificates considered to be trusted when
	 * validating.
	 *
	 * A Null truststore will indicate all certificates are trusted.
	 */
	public SimpleCertificateValidator(KeyStore truststore) {
		this.truststore=truststore;
	}
	
	@Override
	public void validate(X509Certificate cert) throws CertificateValidationException, XspException {
		try {
			cert.checkValidity();
			isTrustedCertificate(cert);
		} catch (Exception ex) {
			throw new XspException(ex.getMessage(), ex);
		}
	}
 
	/**
	 * Returns true if the trust store has not been supplied or the trust
	 * store exists and contains the certificate's alias
	 * 
	 */
	private boolean isTrustedCertificate(X509Certificate cert) throws KeyStoreException {
		
		if (truststore == null) {
			return true;
		}

		boolean trusted = false;

		if (cert != null) {

			// Only returns null if cert is NOT in keystore.
			String alias = truststore.getCertificateAlias(cert);

			if (alias != null) {
				trusted = true;
			}
		}

		return trusted;
	}
	
	

}
