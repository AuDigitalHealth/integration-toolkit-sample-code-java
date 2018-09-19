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

package au.gov.nehta.vendorlibrary.pcehr.test.unittests;

import au.gov.nehta.vendorlibrary.clinicalpackage.core.SubmissionSet;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.PackagingUtility;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.SignatureGenerationException;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XDSConstants;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.ConfidentialityCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentMetadata;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.FormatCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HealthcareFacilityTypeCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.PracticeSettingTypeCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.SubmissionMetadata;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.FileUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.MetadataUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.XDSMapper;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.AllTests;
import au.net.electronichealth.ns.cdapackage.xsd.esignature._2012.PersonNameType;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.Signature;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.Timestamp;
import ihe.iti.xds_b._2007.DocumentRepositoryPortType;
import ihe.iti.xds_b._2007.DocumentRepositoryService;
import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequest;
import oasis.names.tc.ebxml_regrep.xsd.lcm._3.SubmitObjectsRequest;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandlerStuff {
  public static void main(String[] args) throws IOException, SAXException, XPathExpressionException, ParserConfigurationException, SignatureGenerationException, GeneralSecurityException {
    WebServiceClient annotation = DocumentRepositoryService.class.getAnnotation(WebServiceClient.class);
    Service service = getService(annotation, annotation.wsdlLocation());

    service.setHandlerResolver(new HandlerResolver() {
      @Override
      public List<Handler> getHandlerChain(PortInfo portInfo) {
        List<Handler> handlerChain = new ArrayList<Handler>();
//        handlerChain.add(new au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler(true));
        return handlerChain;
      }
    });

    DocumentRepositoryPortType port = service.getPort(DocumentRepositoryPortType.class);
    BindingProvider bp = (BindingProvider) port;
    SOAPBinding binding = (SOAPBinding) bp.getBinding();
    binding.setMTOMEnabled(true);

    Map<String, Object> requestContext = new HashMap<String, Object>();
    requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8888".trim());

    bp.getRequestContext().putAll(requestContext);

    PersonNameType approverName_tucker;
    approverName_tucker = new PersonNameType();
    approverName_tucker.setFamilyName("TUCKER");
    approverName_tucker.getGivenName().add("Anthony");

    byte[] rootDocument = FileUtils.loadFile(new File("./src/test/resources/TestFiles/20120718/040/CDA_ROOT.xml"));
    SubmissionSet subset = createPackage(rootDocument, AllTests.getCertificate(), AllTests.getPrivateKey(), "8003619166668209", approverName_tucker);
    byte[] packageContent = PackagingUtility.createZip(subset);

    PCEHRHeader commonHeader = MessageComponents.createRequest(
      MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166668209", null, "Anthony TUCKER", false),
      "8003608500011184",
      MessageComponents.createProductType("NEHTA", "testHarness", "1.0", "Windows 7 - Java"),
      PCEHRHeader.ClientSystemType.CIS,
      MessageComponents.createAccessingOrganisation("8003622500001608", "2", null)
    );

    DocumentMetadata documentMetadata = MetadataUtils.toDocumentMetadata(commonHeader, rootDocument);
    documentMetadata.setHealthcareFacilityType(HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue());
    documentMetadata.setPracticeSetting(PracticeSettingTypeCodes.LOCAL_GOVERNMENT_HEALTHCARE_ADMINISTRATION.getCodedValue());
    documentMetadata.setConfidentialityCode(ConfidentialityCodes.NA);
    documentMetadata.setEntryUuid(XDSConstants.DOCUMENT_ENTRY_ID);
    documentMetadata.setFormatCode(FormatCodes.SHARED_HEALTH_SUMMARY_3A.getCodedValue());

    SubmissionMetadata submissionMetadata = new SubmissionMetadata();
    submissionMetadata.setSubmissionTime(new Date());
    submissionMetadata.setEntryUuid(XDSConstants.SUBSET_ENTRY_ID);

    SubmitObjectsRequest request = XDSMapper.toSubmitObjectsRequest(commonHeader, submissionMetadata, documentMetadata, null);

    ProvideAndRegisterDocumentSetRequest.Document iheDocument = new ProvideAndRegisterDocumentSetRequest.Document();
    iheDocument.setId(documentMetadata.getEntryUuid());
    iheDocument.setValue(packageContent);

    ProvideAndRegisterDocumentSetRequest body = new ProvideAndRegisterDocumentSetRequest();
    body.setSubmitObjectsRequest(request);
    body.getDocuments().add(iheDocument);

    port.documentRepositoryProvideAndRegisterDocumentSetB(new Timestamp(), new Holder<Signature>(), commonHeader, body);
  }

  /**
   * Creates a web service Service.
   *
   * @param annotation  the associated WebServiceClient the location of the Service's WSDL file (Mandatory)
   * @param wsdlFileLoc the location of the Service's WSDL file (Mandatory)
   * @return the created web service interface
   */
  private static Service getService(final WebServiceClient annotation, final String wsdlFileLoc) {

    final URL wsdlURL = retrieveWsdlUrl(wsdlFileLoc);
    final QName serviceQName = new QName(annotation.targetNamespace(), annotation.name());
    return Service.create(wsdlURL, serviceQName);
  }

  /**
   * Obtain a URL to a WSDL file in the classpath
   *
   * @param filePath the path to WSDL file (Mandatory)
   * @return result the URL to the WSDL file
   */
  private static URL retrieveWsdlUrl(String filePath) {
    URL result = null;
    ClassLoader classLoader;
    if (filePath != null) {
      classLoader = Thread.currentThread().getContextClassLoader();
      result = classLoader.getResource(filePath);
    }

    return result;
  }

  private static SubmissionSet createPackage(final byte[] rootDocument, X509Certificate certificate, PrivateKey privateKey, String approverId, PersonNameType approverName) throws GeneralSecurityException, SignatureGenerationException {
    // Begin building package.
    SubmissionSet.Builder subsetBuilder = new SubmissionSet.Builder();
    subsetBuilder.rootDocument(rootDocument);

    // Build package.
    return subsetBuilder.build();
  }
}
