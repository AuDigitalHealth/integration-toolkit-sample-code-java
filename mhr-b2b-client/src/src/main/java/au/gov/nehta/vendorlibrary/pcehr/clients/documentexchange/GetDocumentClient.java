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
package au.gov.nehta.vendorlibrary.pcehr.clients.documentexchange;

import ihe.iti.xds_b._2007.DocumentRepositoryPortType;
import ihe.iti.xds_b._2007.DocumentRepositoryService;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;

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

/**
 * A JAX-WS client to the PCEHR 'Get Document' web service.
 */
public final class GetDocumentClient extends Client<DocumentRepositoryPortType> {

  /**
   * Constructor - no certificate verification performed.
   *
   * @param sslSocketFactory  the {@link SSLSocketFactory} to be used when connecting to the web service provider (mandatory).
   * @param x509Certificate   the certificate key to be used for signing (mandatory)
   * @param privateKey        the private key to be used for signing (mandatory)
   * @param endpointAddress   the endpoint address of the web service (mandatory).
   * @param setLoggingEnabled set to <code>true</code> to enable logging (mandatory).
   */
  public GetDocumentClient(
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {
    super(
      DocumentRepositoryService.class,
      DocumentRepositoryPortType.class,
      sslSocketFactory,
      x509Certificate,
      privateKey,
      endpointAddress,
      setLoggingEnabled
    );

    setMTOMEnabled();
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
  public GetDocumentClient(
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final CertificateValidator certificateVerifier,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {
    super(
      DocumentRepositoryService.class,
      DocumentRepositoryPortType.class,
      sslSocketFactory,
      x509Certificate,
      certificateVerifier,
      privateKey,
      endpointAddress,
      setLoggingEnabled
    );

    setMTOMEnabled();
  }

  /**
   * Invokes the web service operation that retrieves a document from the document repository.
   *
   * @param commonHeader PCEHR common header
   * @param docRequest   A request containing the unique document identifier.
   * @return The requested document.
   */
  public RetrieveDocumentSetResponse retrieveDocument(PCEHRHeader commonHeader, DocumentRequest docRequest) {

    Validate.notNull(commonHeader, "'commonHeader' must be specified.");
    Validate.notNull(docRequest, "'docRequest' must be specified.");
    Validate.notEmpty(docRequest.getDocumentUniqueId(), "'docRequest.documentUniqueId' must be specified.");
    Validate.notEmpty(docRequest.getRepositoryUniqueId(), "'docRequest.getRepositoryUniqueId' must be specified.");

    CommonHeaderValidator.validate(commonHeader, true); // IHINumber is required.

    RetrieveDocumentSetRequest request = new RetrieveDocumentSetRequest();
    request.getDocumentRequests().add(docRequest);
    Holder<Signature> signatureHolder = null;

    return getPort().documentRepositoryRetrieveDocumentSet(
      DateUtils.generateTimestamp(),
      signatureHolder,
      commonHeader,
      request
    );
  }
}
