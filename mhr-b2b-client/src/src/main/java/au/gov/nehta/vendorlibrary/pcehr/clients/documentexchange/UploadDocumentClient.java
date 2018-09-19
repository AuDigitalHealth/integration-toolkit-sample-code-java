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
import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequest;

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
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XDSConstants;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XMLNamespaces;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.handler.ConfigurableMTOMHandler;
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
 * A JAX-WS client to the PCEHR 'Upload Document' web service.
 */
public final class UploadDocumentClient extends Client<DocumentRepositoryPortType> {

  /**
   * Constructor - no certificate verification performed.
   *
   * @param sslSocketFactory  the {@link SSLSocketFactory} to be used when connecting to the web service provider (mandatory).
   * @param x509Certificate   the certificate key to be used for signing (mandatory)
   * @param privateKey        the private key to be used for signing (mandatory)
   * @param endpointAddress   the endpoint address of the web service (mandatory).
   * @param setLoggingEnabled set to <code>true</code> to enable logging (mandatory).
   */
  public UploadDocumentClient(
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
      setLoggingEnabled,
      new ConfigurableMTOMHandler("ProvideAndRegisterDocumentSetRequest",XMLNamespaces.IHE.getNamespace(),"Document")
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
  public UploadDocumentClient(
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
      setLoggingEnabled,
      new ConfigurableMTOMHandler("ProvideAndRegisterDocumentSetRequest",XMLNamespaces.IHE.getNamespace(),"Document")
    );

  }

  /**
   * Submit a document to the document repository.
   *
   * @param commonHeader            PCEHR common header
   * @param packageContent          CDA package byte content
   * @param healthcareFacilityType  Healthcare facility type
   * @param practiceSettingTypeCode Practice setting type
   * @param formatCode              document format code
   * @return RegistryResponseType
   * @throws IOException
   * @throws SAXException
   * @throws XPathExpressionException
   * @throws ParserConfigurationException
   */
  public RegistryResponseType uploadDocument(
    final PCEHRHeader commonHeader,
    final byte[] packageContent,
    final CodedValue healthcareFacilityType,
    final CodedValue practiceSettingTypeCode,
    final CodedValue formatCode
  ) throws IOException, SAXException, XPathExpressionException, ParserConfigurationException {
    return uploadDocument(
      commonHeader,
      packageContent,
      null,
      healthcareFacilityType,
      practiceSettingTypeCode,
      formatCode
    );
  }
  
  /**
   * Submit a document to the document repository. Using this Size and Hash if Repository ID is not null
   *
   * @param commonHeader            PCEHR common header
   * @param packageContent          CDA package byte content
   * @param replacementId           repository $XDSDocumentEntry.EntryUUID for document to be replaced. Can be left null if upload is a new document.
   * @param healthcareFacilityType  Healthcare facility type
   * @param practiceSettingTypeCode Practice setting type
   * @param formatCode              document format code
   * @return RegistryResponseType
   * @throws IOException
   * @throws SAXException
   * @throws XPathExpressionException
   * @throws ParserConfigurationException
   */
  public RegistryResponseType uploadDocument(
          final PCEHRHeader commonHeader,
          final byte[] packageContent,
          final String replacementId,
          final CodedValue healthcareFacilityType,
          final CodedValue practiceSettingTypeCode,
          final CodedValue formatCode
        ) throws XPathExpressionException, IOException, SAXException, ParserConfigurationException  {
      return uploadDocument(
              commonHeader,
              packageContent,
              replacementId,
              null,
              healthcareFacilityType,
              practiceSettingTypeCode,
              formatCode
            );
  }
  
  

  /**
   * Submit a document to the document repository. Add a Size and Hash if repository ID is not null
   *
   * @param commonHeader            PCEHR common header
   * @param packageContent          CDA package byte content
   * @param replacementId           repository $XDSDocumentEntry.EntryUUID for document to be replaced. Can be left null if upload is a new document.
   * @param healthcareFacilityType  Healthcare facility type
   * @param practiceSettingTypeCode Practice setting type
   * @param formatCode              document format code
   * @return RegistryResponseType
   * @throws IOException
   * @throws SAXException
   * @throws XPathExpressionException
   * @throws ParserConfigurationException
   */
  public RegistryResponseType uploadDocument(
    final PCEHRHeader commonHeader,
    final byte[] packageContent,
    final String replacementId,
    final String repositoryUniqueId,
    final CodedValue healthcareFacilityType,
    final CodedValue practiceSettingTypeCode,
    final CodedValue formatCode
  ) throws IOException, SAXException, XPathExpressionException, ParserConfigurationException {

    Validate.notNull(packageContent, "'packageContent' must be specified");
    Validate.notNull(healthcareFacilityType, "'healthcareFacilityType' must be specified");
    Validate.notNull(practiceSettingTypeCode, "'practiceSettingTypeCode' must be specified");
    Validate.notNull(formatCode, "'formatCode' must be specified");

    byte[] document = FileUtils.getCdaDocument(packageContent);

    DocumentMetadata documentMetadata = MetadataUtils.toDocumentMetadata(commonHeader, document);
    documentMetadata.setHealthcareFacilityType(healthcareFacilityType);
    documentMetadata.setPracticeSetting(practiceSettingTypeCode);
    documentMetadata.setConfidentialityCode(ConfidentialityCodes.NA);
    documentMetadata.setEntryUuid(XDSConstants.DOCUMENT_ENTRY_ID);
    documentMetadata.setFormatCode(formatCode);
    
    if(repositoryUniqueId != null){
        documentMetadata.setRepositoryUniqueId(repositoryUniqueId);
        documentMetadata.setDocumentHash(MetadataUtils.calculateSha1Hash(packageContent));
        documentMetadata.setDocumentSize(new BigInteger(String.valueOf(packageContent.length)));
    }
    
    SubmissionMetadata submissionMetadata = new SubmissionMetadata();
    submissionMetadata.setSubmissionTime(new Date());
    submissionMetadata.setEntryUuid(XDSConstants.SUBSET_ENTRY_ID);

    SubmitObjectsRequest request = XDSMapper.toSubmitObjectsRequest(
      commonHeader, submissionMetadata, documentMetadata, replacementId);

    ProvideAndRegisterDocumentSetRequest.Document iheDocument = new ProvideAndRegisterDocumentSetRequest.Document();
    iheDocument.setId(documentMetadata.getEntryUuid());
    iheDocument.setValue(packageContent);

    ProvideAndRegisterDocumentSetRequest body = new ProvideAndRegisterDocumentSetRequest();
    body.setSubmitObjectsRequest(request);
    body.getDocuments().add(iheDocument);

    Holder<Signature> signatureHolder = null;

    return getPort().documentRepositoryProvideAndRegisterDocumentSetB(
      DateUtils.generateTimestamp(),
      signatureHolder,
      commonHeader,
      body
    );
  }
}
