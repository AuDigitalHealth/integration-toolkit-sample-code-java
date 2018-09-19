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
package au.gov.nehta.vendorlibrary.pcehr.clients.recordaccess;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.Holder;

import org.apache.commons.lang.Validate;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.Client;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.CommonHeaderValidator;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.DateUtils;
import au.gov.nehta.xsp.CertificateValidator;
import au.net.electronichealth.ns.pcehr.b2b.svc.pcehrprofile._1.PCEHRProfilePortType;
import au.net.electronichealth.ns.pcehr.b2b.svc.pcehrprofile._1.PCEHRProfileService;
import au.net.electronichealth.ns.pcehr.b2b.svc.pcehrprofile._1.StandardErrorMsg;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.ResponseStatusType;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.Signature;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.pcehrprofile._1.GainPCEHRAccess;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.pcehrprofile._1.GainPCEHRAccessResponse;

/**
 * A JAX-WS client to the PCEHR 'Gain PCEHR Access' web service.
 */
public class GainPCEHRAccessClient extends Client<PCEHRProfilePortType> {

  /**
   * Constructor - no certificate verification performed.
   *
   * @param sslSocketFactory  the {@link SSLSocketFactory} to be used when connecting to the web service provider (mandatory).
   * @param x509Certificate   the certificate key to be used for signing (mandatory)
   * @param privateKey        the private key to be used for signing (mandatory)
   * @param endpointAddress   the endpoint address of the web service (mandatory).
   * @param setLoggingEnabled set to <code>true</code> to enable logging (mandatory).
   */
  public GainPCEHRAccessClient(
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {
    super(
      PCEHRProfileService.class,
      PCEHRProfilePortType.class,
      sslSocketFactory,
      x509Certificate,
      privateKey,
      endpointAddress,
      setLoggingEnabled
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
  public GainPCEHRAccessClient(
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final CertificateValidator certificateVerifier,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {
    super(
      PCEHRProfileService.class,
      PCEHRProfilePortType.class,
      sslSocketFactory,
      x509Certificate,
      certificateVerifier,
      privateKey,
      endpointAddress,
      setLoggingEnabled
    );
  }

  /**
   * Invokes the web service operation which confirms whether or not an individual has a shared, accessible electronic health record.
   *
   * @param pcehrRecord  populated {@link GainPCEHRAccess.PCEHRRecord} object encapsulating individual and authorisation detail (Mandatory).
   * @param commonHeader populated {@link PCEHRHeader} request object (Mandatory).
   * @return response (type {@link GainPCEHRAccessResponse}) containing check results.
   * @throws StandardErrorMsg thrown in the event of an operation invocation error.
   */
  public final GainPCEHRAccessResponse gainPCEHRAccess(
    final GainPCEHRAccess.PCEHRRecord pcehrRecord,
    final PCEHRHeader commonHeader
  ) throws StandardErrorMsg {

    Validate.notNull(commonHeader, "'commonHeader' must be specified.");
    Validate.notNull(pcehrRecord);
    CommonHeaderValidator.validate(commonHeader, true); // IHINumber is required.

    if (pcehrRecord.getAuthorisationDetails() != null) {
      if (pcehrRecord.getAuthorisationDetails().getAccessType() == GainPCEHRAccess.PCEHRRecord.AuthorisationDetails.AccessType.ACCESS_CODE) {
        Validate.notEmpty(pcehrRecord.getAuthorisationDetails().getAccessCode(), "'pcehrRecord.authorisationDetails.accessCode' must be specified.");
      }
    }

    // Response object to be populated.
    GainPCEHRAccessResponse response = new GainPCEHRAccessResponse();

    // Holder variables.
    Holder<ResponseStatusType> responseHolder = new Holder<ResponseStatusType>();
    Holder<GainPCEHRAccessResponse.Individual> individualHolder = new Holder<GainPCEHRAccessResponse.Individual>();
    Holder<Signature> signatureHolder = null;

    // Attempt to call the gainPCEHRAccess operation, storing response message values in the supplied holders.
    getPort().gainPCEHRAccess(
      pcehrRecord,
      responseHolder,
      individualHolder,
      DateUtils.generateTimestamp(),
      signatureHolder,
      commonHeader
    );

    // Populate response with holder values.
    response.setIndividual(individualHolder.value);
    response.setResponseStatus(responseHolder.value);

    return response;
  }
}
