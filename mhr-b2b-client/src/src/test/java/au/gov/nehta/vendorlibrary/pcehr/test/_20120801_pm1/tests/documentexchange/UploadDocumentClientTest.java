package au.gov.nehta.vendorlibrary.pcehr.test._20120801_pm1.tests.documentexchange;

import au.gov.nehta.vendorlibrary.clinicalpackage.core.SubmissionSet;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.PackagingUtility;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.SignatureGenerationException;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XDSConstants;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XPathExpressions;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CodedValue;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HealthcareFacilityTypeCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.PracticeSettingTypeCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.FileUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.documentexchange.UploadDocumentClient;
import au.gov.nehta.vendorlibrary.pcehr.test._20120801_pm1.AllTests;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.net.electronichealth.ns.cdapackage.xsd.esignature._2012.PersonNameType;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.UUID;

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
    AllTests.setUp();
    currentId = new Holder<String>();
    client = new UploadDocumentClient(
      AllTests.getSslSocketFactory(),
      AllTests.getCertificate(),
      AllTests.getPrivateKey(),
      Endpoints.PM1_UPLOAD_DOCUMENT,
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
  public void test_040() throws Exception {

    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/20120801/040/CDA_ROOT.xml")), currentId);

    SubmissionSet subset = createPackage(rootDocument, AllTests.getCertificate(), AllTests.getPrivateKey(), "8003619166674595", personNameType);

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
