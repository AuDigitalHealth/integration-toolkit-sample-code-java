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

import ihe.iti.xds_b._2007.DocumentRegistryPortType;
import ihe.iti.xds_b._2007.DocumentRegistryService;

import java.io.IOException;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.Holder;
import javax.xml.xpath.XPathExpressionException;

import oasis.names.tc.ebxml_regrep.xsd.lcm._3.SubmitObjectsRequest;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;

import org.apache.commons.lang.Validate;
import org.xml.sax.SAXException;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.Client;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CodedValue;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.ConfidentialityCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentMetadata;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.SubmissionMetadata;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.DateUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.FileUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.MetadataUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.XDSMapper;
import au.gov.nehta.xsp.CertificateValidator;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.Signature;

/**
 * A JAX-WS client to the PCEHR 'Upload Document Metadata' web service.
 */
public final class UploadDocumentMetadataClient extends Client<DocumentRegistryPortType> {

  /**
   * Constructor - no certificate verification performed.
   *
   * @param sslSocketFactory  the {@link SSLSocketFactory} to be used when connecting to the web service provider (mandatory).
   * @param x509Certificate   the certificate key to be used for signing (mandatory)
   * @param privateKey        the private key to be used for signing (mandatory)
   * @param endpointAddress   the endpoint address of the web service (mandatory).
   * @param setLoggingEnabled set to <code>true</code> to enable logging (mandatory).
   */
  public UploadDocumentMetadataClient(
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
  public UploadDocumentMetadataClient(
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final CertificateValidator certificateVerifier,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {
    super(
      DocumentRegistryService.class,
      DocumentRegistryPortType.class,
      sslSocketFactory,
      x509Certificate,
      certificateVerifier,
      privateKey,
      endpointAddress,
      setLoggingEnabled
    );
  }

  /**
   * Register a document in the document registry.
   *
   * @param commonHeader       PCEHR common header
   * @param submissionMetadata Submission metadata
   * @param documentMetadata   Document metadata describing the document.
   * @throws IllegalArgumentException If argument validation failed prior to the registration attempt.
   */

  /**
   * Register a document in the document registry.
   *
   * @param commonHeader               PCEHR common header
   * @param packageContent             CDA package file content.
   * @param repositoryUniqueId         Repository unique ID.
   * @param healthcareFacilityTypeCode Healthcare facility type code.
   * @param practiceSettingTypeCode    Practice setting type code.
   * @param formatCode                 CDA root document template format code.
   * @return {@link RegistryResponseType}.
   * @throws IOException                  Thrown in the event that package content cannot be read.
   * @throws SAXException                 Thrown in the event of a parsing failure.
   * @throws XPathExpressionException     Thrown in the event of an XPath evaluation failure.
   * @throws ParserConfigurationException Thrown in the event of a parser configuration exception.
   */

  public RegistryResponseType uploadDocumentMetadata(
    final PCEHRHeader commonHeader,
    final byte[] packageContent,
    final String repositoryUniqueId,
    final CodedValue healthcareFacilityTypeCode,
    final CodedValue practiceSettingTypeCode,
    final CodedValue formatCode
  ) throws IOException, SAXException, XPathExpressionException, ParserConfigurationException {

    Validate.notNull(packageContent);
    Validate.notNull(healthcareFacilityTypeCode);
    Validate.notNull(practiceSettingTypeCode);

    byte[] document = FileUtils.getCdaDocument(packageContent);

    DocumentMetadata documentMetadata = MetadataUtils.toDocumentMetadata(commonHeader, document);
    documentMetadata.setHealthcareFacilityType(healthcareFacilityTypeCode);
    documentMetadata.setPracticeSetting(practiceSettingTypeCode);
    documentMetadata.setConfidentialityCode(ConfidentialityCodes.NA);
    documentMetadata.setEntryUuid("DOCUMENT_SYMBOLICID_01");
    documentMetadata.setRepositoryUniqueId(repositoryUniqueId);
    documentMetadata.setFormatCode(formatCode);
    documentMetadata.setDocumentHash(MetadataUtils.calculateSha1Hash(packageContent));
    documentMetadata.setDocumentSize(new BigInteger(String.valueOf(packageContent.length)));

    SubmissionMetadata submissionMetadata = new SubmissionMetadata();
    submissionMetadata.setSubmissionTime(new Date());
    submissionMetadata.setEntryUuid("SUBSET_SYMBOLICID_01");

    SubmitObjectsRequest request = XDSMapper.toSubmitObjectsRequest(
      commonHeader,
      submissionMetadata,
      documentMetadata,
      null
    );

    Holder<Signature> signatureHolder = null;

    return getPort().documentRegistryRegisterDocumentSetB(
      DateUtils.generateTimestamp(),
      signatureHolder,
      commonHeader,
      request
    );
  }
}
