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
import java.util.Date;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.Holder;

import org.apache.commons.lang.Validate;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.Client;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.CommonHeaderValidator;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.DateUtils;
import au.gov.nehta.xsp.CertificateValidator;
import au.net.electronichealth.ns.pcehr.svc.getauditview._1.GetAuditViewPortType;
import au.net.electronichealth.ns.pcehr.svc.getauditview._1.GetAuditViewService;
import au.net.electronichealth.ns.pcehr.svc.getauditview._1.StandardErrorMsg;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.Signature;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.getauditview._1.GetAuditView;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.getauditview._1.GetAuditViewResponse;

/**
 * A JAX-WS client to the PCEHR 'Get Audit View' web service.
 */
public final class GetAuditViewClient extends Client<GetAuditViewPortType> {

  /**
   * Constructor - no certificate verification performed.
   *
   * @param sslSocketFactory  the {@link SSLSocketFactory} to be used when connecting to the web service provider (mandatory).
   * @param x509Certificate   the certificate key to be used for signing (mandatory)
   * @param privateKey        the private key to be used for signing (mandatory)
   * @param endpointAddress   the endpoint address of the web service (mandatory).
   * @param setLoggingEnabled set to <code>true</code> to enable logging (mandatory).
   */
  public GetAuditViewClient(
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {
    super(
      GetAuditViewService.class,
      GetAuditViewPortType.class,
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
  public GetAuditViewClient(
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final CertificateValidator certificateValidator,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {
    super(
      GetAuditViewService.class,
      GetAuditViewPortType.class,
      sslSocketFactory,
      x509Certificate,
      certificateValidator,
      privateKey,
      endpointAddress,
      setLoggingEnabled
    );
    
    
  }

  /**
   * Get the consolidated view of a PCEHR.
   *
   * @param commonHeader PCEHR common header
   * @param dateFrom     Lower date range.
   * @param dateTo       Upper date range.
   * @return The consolidated view of a PCEHR.
   * @throws StandardErrorMsg thrown in the event of an operation invocation error.
   */
  public GetAuditViewResponse getAuditView(PCEHRHeader commonHeader, Date dateFrom, Date dateTo) throws StandardErrorMsg {
    Validate.notNull(commonHeader, "'commonHeader' must be specified.");
    Validate.notNull(dateFrom, "'dateFrom' must be specified.");
    Validate.notNull(dateTo, "'dateTo' must be specified.");
    CommonHeaderValidator.validate(commonHeader, true); // IHINumber is required.

    Holder<GetAuditViewResponse> auditView = new Holder<GetAuditViewResponse>();
    Holder<Signature> signatureHolder = null;

    GetAuditView params = new GetAuditView();

    params.setDateTo(DateUtils.getXMLGregorianCalendar(dateTo));
    params.setDateFrom(DateUtils.getXMLGregorianCalendar(dateFrom));

    getPort().getAuditView(params,
      auditView,
      DateUtils.generateTimestamp(),
      signatureHolder,
      commonHeader
    );

    return auditView.value;
  }
}
