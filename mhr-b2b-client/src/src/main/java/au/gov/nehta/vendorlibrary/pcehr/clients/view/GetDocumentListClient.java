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

import ihe.iti.xds_b._2007.DocumentRegistryPortType;
import ihe.iti.xds_b._2007.DocumentRegistryService;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.Holder;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.apache.commons.lang.Validate;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.Client;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XDSConstants;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentQueryParams;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentStatus;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.CommonHeaderValidator;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.DateUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.XDSMapper;
import au.gov.nehta.xsp.CertificateValidator;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.Signature;

/**
 * A JAX-WS client to the PCEHR 'Get Document List' web service.
 */
public final class GetDocumentListClient extends Client<DocumentRegistryPortType> {

  /**
   * Constructor - no certificate verification performed.
   *
   * @param sslSocketFactory  the {@link SSLSocketFactory} to be used when connecting to the web service provider (mandatory).
   * @param x509Certificate   the certificate key to be used for signing (mandatory)
   * @param privateKey        the private key to be used for signing (mandatory)
   * @param endpointAddress   the endpoint address of the web service (mandatory).
   * @param setLoggingEnabled set to <code>true</code> to enable logging (mandatory).
   */
  public GetDocumentListClient(
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {
    super(
      DocumentRegistryService.class,
      DocumentRegistryPortType.class,
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
  public GetDocumentListClient(
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final CertificateValidator certificateValidator,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {
    super(
      DocumentRegistryService.class,
      DocumentRegistryPortType.class,
      sslSocketFactory,
      x509Certificate,
      certificateValidator,
      privateKey,
      endpointAddress,
      setLoggingEnabled
    );
  }

  /**
   * Get the list of documents related to the PCEHR identified by {@code commonHeader.ihiNumber}.
   *
   * @param commonHeader PCEHR common header.
   * @return A list of the metadata of matching documents.
   */
  public AdhocQueryResponse getDocumentList(PCEHRHeader commonHeader) {

    Validate.notNull(commonHeader, "'commonHeader' must be specified.");
    CommonHeaderValidator.validate(commonHeader, true); // IHINumber is required.

    DocumentQueryParams queryParams = new DocumentQueryParams();
    queryParams.getStatuses().add(DocumentStatus.APPROVED);


    // Map the DocumentQueryParams to the XDS equivalent.
    AdhocQueryRequest request = XDSMapper.toAdhocQueryRequest(
      commonHeader,
      XDSConstants.ADHOC_QUERY_ID_FIND_DOCUMENTS,
      queryParams
    );

    Holder<Signature> signatureHolder = null;

    // Perform the query.
    return getPort().documentRegistryRegistryStoredQuery(
      DateUtils.generateTimestamp(),
      signatureHolder,
      commonHeader,
      request
    );
  }

  /**
   * Get the list of documents related to the PCEHR identified by {@code commonHeader.ihiNumber}, matching the supplied set of query parameters.
   *
   * @param commonHeader PCEHR common header.
   * @param queryParams  populated set of query parameters.
   * @return A list of the metadata of matching documents.
   */
  public AdhocQueryResponse getDocumentList(PCEHRHeader commonHeader, DocumentQueryParams queryParams) {

    Validate.notNull(commonHeader, "'commonHeader' must be specified.");
    Validate.notNull(queryParams, "'queryParams' must be specified");
    CommonHeaderValidator.validate(commonHeader, true); // IHINumber is required.

    // Map the DocumentQueryParams to the XDS equivalent.
    AdhocQueryRequest request = XDSMapper.toAdhocQueryRequest(
      commonHeader,
      XDSConstants.ADHOC_QUERY_ID_FIND_DOCUMENTS,
      queryParams
    );

    Holder<Signature> signatureHolder = null;

    // Perform the query.
    return getPort().documentRegistryRegistryStoredQuery(
      DateUtils.generateTimestamp(),
      signatureHolder,
      commonHeader,
      request
    );
  }
}
