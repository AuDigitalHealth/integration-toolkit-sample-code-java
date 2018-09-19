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
package au.gov.nehta.vendorlibrary.pcehr.clients.registration;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.Holder;

import org.apache.commons.lang.Validate;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.Client;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XMLNamespaces;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.handler.ConfigurableMTOMHandler;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.CommonHeaderValidator;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.DateUtils;
import au.gov.nehta.xsp.CertificateValidator;
import au.net.electronichealth.ns.pcehr.svc.registerpcehr._2.RegisterPCEHRPortType;
import au.net.electronichealth.ns.pcehr.svc.registerpcehr._2.RegisterPCEHRService;
import au.net.electronichealth.ns.pcehr.svc.registerpcehr._2.StandardErrorMsg;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.Signature;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHR;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHRResponse;

/**
 * A JAX-WS client to the PCEHR 'Register PCEHR' web service.
 */
public class RegisterPCEHRClient extends Client<RegisterPCEHRPortType> {
  /**
   * Constructor - no certificate verification performed.
   *
   * @param sslSocketFactory  the {@link SSLSocketFactory} to be used when connecting to the web service provider (mandatory).
   * @param x509Certificate   the certificate key to be used for signing (mandatory)
   * @param privateKey        the private key to be used for signing (mandatory)
   * @param endpointAddress   the endpoint address of the web service (mandatory).
   * @param setLoggingEnabled set to <code>true</code> to enable logging (mandatory).
   */
  public RegisterPCEHRClient(
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {
    super(
      RegisterPCEHRService.class,
      RegisterPCEHRPortType.class,
      sslSocketFactory,
      x509Certificate,
      privateKey,
      endpointAddress,
      setLoggingEnabled,
      new ConfigurableMTOMHandler("registerPCEHR",XMLNamespaces.REGISTER.getNamespace(),"signedConsentForm")
    );
    
  }

  /**
   * Constructor - accepts an optional certificate verifier.
   *
   * @param sslSocketFactory    the {@link SSLSocketFactory} to be used when connecting to the web service provider (mandatory).
   * @param x509Certificate     the certificate key to be used for signing (mandatory)
   * @param certificateVerifier CertificateVerifier implementation (optional).
   * @param privateKey          the private key to be used for signing (mandatory)
   * @param endpointAddress     the endpoint address of the web service (mandatory).
   * @param setLoggingEnabled   set to <code>true</code> to enable logging (mandatory).
   */
  public RegisterPCEHRClient(
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final CertificateValidator certificateVerifier,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {
    super(RegisterPCEHRService.class,
      RegisterPCEHRPortType.class,
      sslSocketFactory,
      x509Certificate,
      certificateVerifier,
      privateKey,
      endpointAddress,
      setLoggingEnabled,
      new ConfigurableMTOMHandler("registerPCEHR",XMLNamespaces.REGISTER.getNamespace(),"signedConsentForm")
    );
    
  }

  /**
   * Invokes the web service operation for registering a PCEHR for an individual or dependent child.
   *
   * @param commonHeader populated {@link PCEHRHeader} request object (Mandatory).
   * @return response (type {@link}
   * @throws StandardErrorMsg
   */
  public final RegisterPCEHRResponse registerPCEHR(final PCEHRHeader commonHeader, final RegisterPCEHR registrationDetails) throws StandardErrorMsg {
    Validate.notNull(commonHeader, "'commonHeader' must be specified.");
    Validate.notNull(registrationDetails, "'registrationDetails' must be specified.");
    CommonHeaderValidator.validate(commonHeader, false); // IHINumber is required.

    // Response holder variables.
    Holder<RegisterPCEHRResponse> responseHolder = new Holder<RegisterPCEHRResponse>();
    Holder<Signature> signatureHolder = null;

    // Attempt to call the registerPCEHR operation storing response message values in the supplied holders.
    getPort().registerPCEHR(
      registrationDetails,
      responseHolder,
      DateUtils.generateTimestamp(),
      signatureHolder,
      commonHeader
    );

    return responseHolder.value;
  }
}
