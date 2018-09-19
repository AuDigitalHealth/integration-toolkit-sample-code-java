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

package au.gov.nehta.vendorlibrary.pcehr.test._20120926_dev.documentexchange;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.Holder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import au.gov.nehta.vendorlibrary.clinicalpackage.core.SubmissionSet;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.PackagingUtility;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.SignatureGenerationException;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XDSConstants;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XPathExpressions;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CodedValue;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HealthcareFacilityTypeCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.PracticeSettingTypeCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.FileUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.MinimalCertificateVerifier;
import au.gov.nehta.vendorlibrary.pcehr.clients.documentexchange.UploadDocumentClient;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.test._20120926_dev.AllTests;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.net.electronichealth.ns.cdapackage.xsd.esignature._2012.PersonNameType;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.removedocument._1.RemoveDocumentResponse;

public class UploadDocumentClientTest {

  private UploadDocumentClient client;

  public Holder<String> getCurrentId() {
    return currentId;
  }

  private Holder<String> currentId;
  private static PersonNameType personNameType;

  @BeforeClass
  public static void initialSetup() throws Exception {

    // Approver.
    personNameType = new PersonNameType();
    personNameType.setFamilyName("John");
    personNameType.getGivenName().add("Ross");
  }

  @Before
  public final void setUp() throws Exception {
	 // LDAPCertStore a;
    AllTests.setUp();
    currentId = new Holder<String>();
    client = new UploadDocumentClient(
            AllTests.getSslSocketFactory(),
            AllTests.getCertificate(),
            new MinimalCertificateVerifier(),
            AllTests.getPrivateKey(),
           // Endpoints.DEV_UPLOAD_DOCUMENT,
            Endpoints.SVT_UPLOAD_DOCUMENT,
            Logging.UPLOAD_DOCUMENT
    );
  }

  @After
  public final void tearDown() throws Exception {
    AllTests.tearDown();
    client = null;
    currentId = null;
  }

  @Test
  public void test_uploadDocument() throws Exception {

    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/MTOM/CDA_ROOT_dev.xml")), currentId);

    SubmissionSet subset = createPackage(rootDocument, AllTests.getCertificate(), AllTests.getPrivateKey(), "8003614166668846", personNameType);

    // Build ZIP from package.
    byte[] packageContent = PackagingUtility.createZip(subset);

    // Write out to file for debug purposes.
    PackagingUtility.writeZip(subset, "./src/test/resources/TestFiles/Generated/out_" + new Date().getTime() + ".zip");

    RegistryResponseType
            response = client.uploadDocument(
            AllTests.getDefaultRequest(),
            packageContent,
            HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
            PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
            new CodedValue("PCEHR_FormatCodes", "1.2.36.1.2001.1006.1.20000.3", "Discharge Summary - Conformance Level 3A")
    );

    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
  }
  
  @Test
  public void test_pathologyMaxDocUpload() throws Exception {
	  PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003618334357646", null, "Ross John", false),
        "8003601243017717",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );
	  

    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/pathologyDI/PathologyResultReport_3A_Max.xml")), currentId);
    SubmissionSet subset = createPackage(rootDocument, AllTests.getCertificate(), AllTests.getPrivateKey(), "8003628233352432", personNameType);

    // Build ZIP from package.
    byte[] packageContent = PackagingUtility.createZip(subset);

    // Write out to file for debug purposes.
    //  PackagingUtility.writeZip(subset, "./src/test/resources/TestFiles/Generated/UploadDocument/out_path_" + new Date().getTime() + ".zip");

    RegistryResponseType
      response = client.uploadDocument(
      request,
      packageContent,
      HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
      PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
      new CodedValue("PCEHR_FormatCodes", "1.2.36.1.2001.1006.1.220.1", "Pathology Result Report - Conformance Level 3A")
    );
    System.out.println(response.getStatus());
    System.out.println(response.getRegistryErrorList().getRegistryErrors().get( 0 ).getCodeContext());
    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
  }
  
  @Test
  public void test_pathologyMinDocUpload() throws Exception {
	  PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003618334357646", null, "Ross John", false),
        "8003601243017717",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );
	  

    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/pathologyDI/PathologyResultReport_3A_Min.xml")), currentId);
    SubmissionSet subset = createPackage(rootDocument, AllTests.getCertificate(), AllTests.getPrivateKey(), "8003628233352432", personNameType);

    // Build ZIP from package.
    byte[] packageContent = PackagingUtility.createZip(subset);

    // Write out to file for debug purposes.
    //  PackagingUtility.writeZip(subset, "./src/test/resources/TestFiles/Generated/UploadDocument/out_path_" + new Date().getTime() + ".zip");

    RegistryResponseType
      response = client.uploadDocument(
      request,
      packageContent,
      HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
      PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
      new CodedValue("PCEHR_FormatCodes", "1.2.36.1.2001.1006.1.220.1", "Pathology Result Report - Conformance Level 3A")
    );
    System.out.println(response.getStatus());
    System.out.println(response.getRegistryErrorList().getRegistryErrors().get( 0 ).getCodeContext());
    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());

  }
  

  private byte[] modifyDocId(byte[] templateContent, Holder<String> currentId) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException {

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(new ByteArrayInputStream(templateContent));
    XPathFactory xPathFactory = XPathFactory.newInstance();
    XPath xPath = xPathFactory.newXPath();
    NodeList nodes = (NodeList) xPath
            .compile(XPathExpressions.DOCUMENT_ID)
            .evaluate(doc, XPathConstants.NODESET);

    

    String newId =  UUID.randomUUID().toString();
    System.out.println("Doc id:"+newId);
    currentId.value = newId;
    nodes.item(0).setNodeValue(newId);
    Source source = new DOMSource(doc);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Result result = new StreamResult(out);
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.transform(source, result);

    return out.toByteArray();
  }

  private static SubmissionSet createPackage(final byte[] rootDocument, X509Certificate certificate, PrivateKey privateKey, String approverId, PersonNameType approverName) throws GeneralSecurityException, SignatureGenerationException {
    // Begin building package.
    SubmissionSet.Builder subsetBuilder = new SubmissionSet.Builder();
    subsetBuilder.rootDocument(rootDocument);

    // Add signature to package.
    subsetBuilder.signature(PackagingUtility.generateSignature(
            rootDocument,
            certificate,
            privateKey,
            approverId,
            approverName
    ));

    // Build package.
    return subsetBuilder.build();
  }


}
