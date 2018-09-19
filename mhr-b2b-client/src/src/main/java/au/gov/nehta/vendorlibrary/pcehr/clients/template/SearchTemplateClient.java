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
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.CommonHeaderValidator;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.DateUtils;
import au.gov.nehta.xsp.CertificateValidator;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.Signature;
import au.net.electronichealth.ns.tplt.svc.searchtemplate._1.SearchTemplatePortType;
import au.net.electronichealth.ns.tplt.svc.searchtemplate._1.SearchTemplateService;
import au.net.electronichealth.ns.tplt.svc.searchtemplate._1.StandardErrorMsg;
import au.net.electronichealth.ns.tplt.xsd.common.templatescoreelements._1.TemplateMetadataType;
import au.net.electronichealth.ns.tplt.xsd.interfaces.searchtemplate._1.SearchTemplate;
import au.net.electronichealth.ns.tplt.xsd.interfaces.searchtemplate._1.SearchTemplateResponse;

/**
 * A JAX-WS client to the PCEHR 'Search Template' web service.
 */
public final class SearchTemplateClient extends Client<SearchTemplatePortType> {

  /**
   * Default constructor.
   *
   * @param sslSocketFactory  the {@link SSLSocketFactory} to be used when connecting to the web service provider (mandatory).
   * @param x509Certificate   the certificate key to be used for signing (mandatory)
   * @param privateKey        the private key to be used for signing (mandatory)
   * @param endpointAddress   the endpoint address of the web service (mandatory).
   * @param setLoggingEnabled set to <code>true</code> to enable logging (mandatory).
   */
  public SearchTemplateClient(
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {
    super(
      SearchTemplateService.class,
      SearchTemplatePortType.class,
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
  public SearchTemplateClient(
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final CertificateValidator certificateValidator,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {

    super(SearchTemplateService.class,
      SearchTemplatePortType.class,
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
   * @param commonHeader     PCEHR common header
   * @param templateId       Optional unique template identifier. Allows retrieval of template metadata without needing to retrieve entire package.
   * @param templateMetadata Optional metadata input for searching.
   * @return A list of templates that match the search criteria.
   * @throws StandardErrorMsg thrown in the event of an operation invocation error.
   */
  public SearchTemplateResponse searchTemplate(
    PCEHRHeader commonHeader,
    String templateId,
    TemplateMetadataType templateMetadata
  ) throws StandardErrorMsg {

    Validate.notNull(commonHeader, "'commonHeader' must be specified.");
    CommonHeaderValidator.validate(commonHeader, false); // IHINumber is NOT required.

    Holder<SearchTemplateResponse> responseHolder = new Holder<SearchTemplateResponse>();
    Holder<Signature> signatureHolder = null;

    SearchTemplate params = new SearchTemplate();
    params.setTemplateID(templateId);
    params.setTemplateMetadata(templateMetadata);

    getPort().searchTemplate(
      params,
      responseHolder,
      DateUtils.generateTimestamp(),
      signatureHolder,
      commonHeader
    );

    return responseHolder.value;
  }
}
