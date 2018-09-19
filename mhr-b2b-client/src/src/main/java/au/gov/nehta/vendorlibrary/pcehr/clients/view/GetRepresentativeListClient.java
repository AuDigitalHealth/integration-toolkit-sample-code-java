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
package au.gov.nehta.vendorlibrary.pcehr.clients.view;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.Holder;

import org.apache.commons.lang.Validate;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.Client;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.CommonHeaderValidator;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.DateUtils;
import au.gov.nehta.xsp.CertificateValidator;
import au.net.electronichealth.ns.pcehr.svc.getrepresentativelist._1.GetRepresentativeListPortType;
import au.net.electronichealth.ns.pcehr.svc.getrepresentativelist._1.GetRepresentativeListService;
import au.net.electronichealth.ns.pcehr.svc.getrepresentativelist._1.StandardErrorMsg;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.Signature;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.getrepresentativelist._1.GetRepresentativeListResponse;

/**
 * A JAX-WS client to the PCEHR 'Get Representative List' web service.
 */
public class GetRepresentativeListClient extends Client<GetRepresentativeListPortType> {

  /**
   * Constructor - no certificate verification performed.
   *
   * @param sslSocketFactory  the {@link SSLSocketFactory} to be used when connecting to the web service provider (mandatory).
   * @param x509Certificate   the certificate key to be used for signing (mandatory)
   * @param privateKey        the private key to be used for signing (mandatory)
   * @param endpointAddress   the endpoint address of the web service (mandatory).
   * @param setLoggingEnabled set to <code>true</code> to enable logging (mandatory).
   */
  public GetRepresentativeListClient(
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {
    super(
      GetRepresentativeListService.class,
      GetRepresentativeListPortType.class,
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
  public GetRepresentativeListClient(
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final CertificateValidator certificateValidator,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {

    super(
      GetRepresentativeListService.class,
      GetRepresentativeListPortType.class,
      sslSocketFactory,
      x509Certificate,
      certificateValidator,
      privateKey,
      endpointAddress,
      setLoggingEnabled
    );
  }

  /**
   * Invokes the web service operation that returns a list of representatives associated with a particular individual’s PCEHR.
   *
   * @param commonHeader populated {@link PCEHRHeader} request object (Mandatory).
   * @return response (type {@link GetRepresentativeListResponse}) containing representative list.
   * @throws StandardErrorMsg Thrown in the event that the operation fails.
   */
  public final GetRepresentativeListResponse getRepresentativeList(final PCEHRHeader commonHeader) throws StandardErrorMsg {

    Validate.notNull(commonHeader, "'commonHeader' must be specified.");
    CommonHeaderValidator.validate(commonHeader, true); // IHINumber is required.

    // Response holder variables.

    Holder<GetRepresentativeListResponse> responseHolder = new Holder<GetRepresentativeListResponse>();
    Holder<Signature> signatureHolder = null;

    // Attempt to call the doesPCEHRExist operation, storing response message values in the supplied holders.
    getPort().getRepresentativeList(
      "",
      responseHolder,
      DateUtils.generateTimestamp(),
      signatureHolder,
      commonHeader
    );

    return responseHolder.value;
  }
}
