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
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XPathExpressions;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.FormatCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HealthcareFacilityTypeCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.PracticeSettingTypeCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.FileUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.documentexchange.GetDocumentClient;
import au.gov.nehta.vendorlibrary.pcehr.clients.documentexchange.RemoveDocumentClient;
import au.gov.nehta.vendorlibrary.pcehr.clients.documentexchange.UploadDocumentClient;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.SecurityUtil;
import au.net.electronichealth.ns.cdapackage.xsd.esignature._2012.PersonNameType;
import au.net.electronichealth.ns.pcehr.svc.removedocument._1.StandardErrorMsg;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.removedocument._1.RemoveDocument;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.removedocument._1.RemoveDocumentResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import junit.framework.Assert;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryError;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class VendorEnd2End {

  private UploadDocumentClient uploadDocumentClient;
  private GetDocumentClient getDocumentClient;
  private static SSLSocketFactory sslSocketFactory;
  private Holder<String> currentId;
  private byte[] rootDocument;
  private PersonNameType approverName; 
  private static PCEHRHeader request;

  @BeforeClass
  public static void initialSetup() throws Exception {
    HttpsURLConnection.setDefaultHostnameVerifier(
      new HostnameVerifier() {
        public boolean verify(String s, SSLSession sslSession) {
          return true;
        }
      }
    );

    sslSocketFactory = SecurityUtil.getSslSocketFactory(
      "JKS",
      "./src/test/resources/security/keystore.jks",
      "Password",
      "Password",
      "bay-hill-hospital.nehta.net.au",
      "JKS",
      "./src/test/resources/security/truststore.jks",
      "Password"
    );

    // Sets the newly created sslSocketFactory as the default for all instances OF the HttpsURLConnection class.
    HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);

    // For testing purposes.
    System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");

    request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Dr. Todd Bagshaw", false),
        "8003602348687602",
        MessageComponents.createProductType("NeHTA", "testHarness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003620000020052", "Bay Hill Hospital", null)
      );

  }

  @Before
  public final void setUp() throws Exception {
    // Approver.
    approverName = new PersonNameType();
    approverName.setFamilyName("John");
    approverName.getNameTitle().add("Dr");
    approverName.getGivenName().add("Ross");

    currentId = new Holder<String>();

    uploadDocumentClient = new UploadDocumentClient(
      sslSocketFactory,
      SecurityUtil.getCertificate("JKS", "Password", "./src/test/resources/security/keystore.jks", "bay-hill-hospital.nehta.net.au"),
      SecurityUtil.getPrivateKey("JKS", "Password", "./src/test/resources/security/keystore.jks", "bay-hill-hospital.nehta.net.au"),
      Endpoints.IRP_VE2E_UPLOAD_DOCUMENT,
      Logging.UPLOAD_DOCUMENT
    );

    getDocumentClient = new GetDocumentClient(
      sslSocketFactory,
      SecurityUtil.getCertificate("JKS", "Password", "./src/test/resources/security/keystore.jks", "bay-hill-hospital.nehta.net.au"),
      SecurityUtil.getPrivateKey("JKS", "Password", "./src/test/resources/security/keystore.jks", "bay-hill-hospital.nehta.net.au"),
      Endpoints.DRP_VE2E_GET_DOCUMENT,
      Logging.GET_DOCUMENT
    );
  }

  @After
  public final void tearDown() throws Exception {
    uploadDocumentClient = null;
    getDocumentClient = null;
    approverName = null;
    currentId = null;
  }

  @Test
  public void test_uploadDocument() throws Exception {

    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/20120530/009/CDA_ROOT.xml")), currentId);

    SubmissionSet subset = createPackage(rootDocument);

    // Build ZIP from package.
    byte[] packageContent = PackagingUtility.createZip(subset);

    // Write out to file for debug purposes.
    PackagingUtility.writeZip(subset, "./src/test/resources/TestFiles/Generated/UploadDocument/out_" + new Date().getTime() + ".zip");

    RegistryResponseType response = null;

    response = uploadDocumentClient.uploadDocument(
      request,
      packageContent,
      HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
      PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
      FormatCodes.SHARED_HEALTH_SUMMARY_3A.getCodedValue()
    );

    Assert.assertNotNull(response);
    List<RegistryError> errors = response.getRegistryErrorList().getRegistryErrors();
    for (RegistryError error : errors) {
      System.out.println(error.getErrorCode() + " " + error.getCodeContext());
    }
  }

  @Test
  public void test_getDocument() throws Exception {
    RetrieveDocumentSetRequest.DocumentRequest docRequest = new RetrieveDocumentSetRequest.DocumentRequest();
    docRequest.setDocumentUniqueId("2.25.185129349660311728472975437732097334");
    docRequest.setRepositoryUniqueId("1.2.36.1.2001.1007.10");
    RetrieveDocumentSetResponse response = getDocumentClient.retrieveDocument(request, docRequest);
    System.out.println();
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

    // Check if ID is OID or UUID.
    String presentId = nodes.item(0).getNodeValue();

    UUID presentIdUuid = null;

    try {
      presentIdUuid = UUID.fromString(presentId);
    } catch (IllegalArgumentException e) {
      // Do nothing as this just means it's another format.
      System.out.println();
    }

    String newId = null;

    if (presentIdUuid != null) {
      // Generate a new doc ID.
      newId = UUID.randomUUID().toString();
    } else {
      newId = nodes.item(0).getNodeValue() + "." + new Date().getTime();
    }

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

  private SubmissionSet createPackage(final byte[] rootDocument) throws GeneralSecurityException, SignatureGenerationException {
    // Begin building package.
    SubmissionSet.Builder subsetBuilder = new SubmissionSet.Builder();
    subsetBuilder.rootDocument(rootDocument);

    // Add signature to package.
    subsetBuilder.signature(PackagingUtility.generateSignature(
      rootDocument,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      "hpii:8003620833337558",
      approverName
    ));

    // Build package.
    return subsetBuilder.build();
  }

  private SubmissionSet createPackageWithAttachments(final byte[] rootDocument) throws GeneralSecurityException, SignatureGenerationException {
    // Begin building package.
    SubmissionSet.Builder subsetBuilder = new SubmissionSet.Builder();
    subsetBuilder.rootDocument(rootDocument);

    // Add signature to package.
    subsetBuilder.signature(PackagingUtility.generateSignature(
      rootDocument,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      "8003620833337558",
      approverName
    ));

    // Add attachments to submission set.
    subsetBuilder
      .attachment("Image1.jpg", "Image1.jpg".getBytes())
      .attachment("Image2.png", "Image2.png".getBytes());

    // Build package.
    return subsetBuilder.build();
  }

  private static RemoveDocumentResponse removeDoc(PCEHRHeader commonHeader, String docId) throws GeneralSecurityException, StandardErrorMsg {
    RemoveDocumentClient removeClient = new RemoveDocumentClient(
      sslSocketFactory,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      Endpoints.ACCENTURE_REMOVE_DOCUMENT,
      false
    );

    return removeClient.removeDocument(commonHeader, docId, RemoveDocument.DocumentRemovalReason.WITHDRAWN);
  }
}
