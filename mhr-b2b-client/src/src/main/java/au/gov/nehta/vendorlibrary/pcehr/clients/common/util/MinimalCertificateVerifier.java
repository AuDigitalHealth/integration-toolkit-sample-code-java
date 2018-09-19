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
package au.gov.nehta.vendorlibrary.pcehr.clients.common.util;

import au.gov.nehta.xsp.CertificateVerificationException;
import au.gov.nehta.xsp.CertificateVerifier;
import au.gov.nehta.xsp.XspException;

import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;


/**
 * Certificate verifier implementation that performs some basic verification of the certificate.
 * The sample is provided for illustrative purposes only and should be implemented in production.
 */
public class MinimalCertificateVerifier extends CertificateVerifier  {

	@Override
	public void verify(X509Certificate certificate) throws CertificateVerificationException, XspException {
		//null impl.
	}/* implements CertificateVerifier {

  /**
   * Example certificate verification.
   *
   * @param certificate Certificate to verify.
   * @throws CertificateVerificationException
   *                      Thrown in the event of a verification failure.
   * @throws XspException Thrown in the event of a verification failure.
   *//*
  @Override
  public final void verify(X509Certificate certificate) throws CertificateVerificationException, XspException {
    try {
      // Verification of certificate chain, status on CRLs etc. can be considered. For this example, the certificate is verified in terms of
      // expiration and that the certificate is currently valid.
      certificate.checkValidity();
    } catch (CertificateExpiredException e) {
      throw new CertificateVerificationException("Certificate verification failed", e);
    } catch (CertificateNotYetValidException e) {
      throw new CertificateVerificationException("Certificate verification failed", e);
    }
  }*/
}
