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
package au.gov.nehta.vendorlibrary.pcehr.clients.template;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.Holder;

import org.apache.commons.lang.Validate;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.Client;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.TemplateServiceRequestorOption;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.CommonHeaderValidator;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.DateUtils;
import au.gov.nehta.xsp.CertificateValidator;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.Signature;
import au.net.electronichealth.ns.tplt.svc.gettemplate._1.GetTemplatePortType;
import au.net.electronichealth.ns.tplt.svc.gettemplate._1.GetTemplateService;
import au.net.electronichealth.ns.tplt.svc.gettemplate._1.StandardErrorMsg;
import au.net.electronichealth.ns.tplt.xsd.interfaces.gettemplate._1.GetTemplate;
import au.net.electronichealth.ns.tplt.xsd.interfaces.gettemplate._1.GetTemplateResponse;

/**
 * A JAX-WS client to the PCEHR 'Get Template' web service.
 */
public final class GetTemplateClient extends Client<GetTemplatePortType> {

  /**
   * Constructor - no certificate verification performed.
   *
   * @param sslSocketFactory  the {@link SSLSocketFactory} to be used when connecting to the web service provider (mandatory).
   * @param x509Certificate   the certificate key to be used for signing (mandatory)
   * @param privateKey        the private key to be used for signing (mandatory)
   * @param endpointAddress   the endpoint address of the web service (mandatory).
   * @param setLoggingEnabled set to <code>true</code> to enable logging (mandatory).
   */
  public GetTemplateClient(
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {
    super(
      GetTemplateService.class,
      GetTemplatePortType.class,
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
  public GetTemplateClient(
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final CertificateValidator certificateValidator,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {
    super(
      GetTemplateService.class,
      GetTemplatePortType.class,
      sslSocketFactory,
      x509Certificate,
      certificateValidator,
      privateKey,
      endpointAddress,
      setLoggingEnabled
    );
  }

  /**
   * Retrieve a template package from the template service.
   *
   * @param commonHeader    PCEHR common header
   * @param templateId      Unique template identifier.
   * @param requestorOption Controls elements to be returned within the template package.
   * @return The matching template package.
   * @throws StandardErrorMsg On operation failure.
   */
  public GetTemplateResponse getTemplate(
    PCEHRHeader commonHeader,
    String templateId,
    TemplateServiceRequestorOption requestorOption
  ) throws StandardErrorMsg {

    Validate.notNull(commonHeader, "'commonHeader' must be specified.");
    Validate.notEmpty(templateId, "'templateId' must be specified.");
    Validate.notNull(requestorOption, "'requestorOption' must be specified.");
    CommonHeaderValidator.validate(commonHeader, false); // IHINumber is NOT required.

    return getTemplate(commonHeader, templateId, requestorOption.name());
  }

  public GetTemplateResponse getTemplate(
    PCEHRHeader commonHeader,
    String templateId,
    String requestorOption
  ) throws StandardErrorMsg {

    Validate.notNull(commonHeader, "'commonHeader' must be specified.");
    Validate.notEmpty(templateId, "'templateId' must be specified.");
    Validate.notNull(requestorOption, "'requestorOption' must be specified.");
    CommonHeaderValidator.validate(commonHeader, false); // IHINumber is NOT required.

    // Response holder variables.
    Holder<GetTemplateResponse> responseHolder = new Holder<GetTemplateResponse>();
    Holder<Signature> signatureHolder = null;

    GetTemplate getTemplate = new GetTemplate();
    getTemplate.setTemplateID(templateId);
    getTemplate.setServiceRequestorOption(requestorOption);

    // Attempt to call the getTemplate operation, storing response message values in the supplied holders.
    getPort().getTemplate(
      getTemplate,
      responseHolder,
      DateUtils.generateTimestamp(),
      signatureHolder,
      commonHeader
    );

    return responseHolder.value;
  }
}
