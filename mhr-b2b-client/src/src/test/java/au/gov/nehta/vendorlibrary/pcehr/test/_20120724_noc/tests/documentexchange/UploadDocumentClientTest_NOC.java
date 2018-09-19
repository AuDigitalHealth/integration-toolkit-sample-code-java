package au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.documentexchange;

import au.gov.nehta.vendorlibrary.clinicalpackage.core.SubmissionSet;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.PackagingUtility;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.SignatureGenerationException;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XDSConstants;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XPathExpressions;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CodedValue;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentQueryParams;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentStatus;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.FormatCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HealthcareFacilityTypeCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.PracticeSettingTypeCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.FileUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.documentexchange.GetDocumentClient;
import au.gov.nehta.vendorlibrary.pcehr.clients.documentexchange.RemoveDocumentClient;
import au.gov.nehta.vendorlibrary.pcehr.clients.documentexchange.UploadDocumentClient;
import au.gov.nehta.vendorlibrary.pcehr.clients.view.GetDocumentListClient;
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
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExternalIdentifierType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExtrinsicObjectType;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryError;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
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

public class UploadDocumentClientTest_NOC {

  private UploadDocumentClient client;
  private static SSLSocketFactory sslSocketFactory;

  public Holder<String> getCurrentId() {
    return currentId;
  }

  private Holder<String> currentId;
  private byte[] rootDocument;
  private static PersonNameType approverName;

  @BeforeClass
  public static void initialSetup() throws Exception {
    HttpsURLConnection.setDefaultHostnameVerifier(
      new HostnameVerifier() {
        public boolean verify(String s, SSLSession sslSession) {
          return true;
        }
      }
    );

    sslSocketFactory = SecurityUtil.getSslSocketFactory();

    // Sets the newly created sslsocketfactory as the default for all instances OF the HttpsURLConnection class.
    HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);

    // For testing purposes.
    System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
  }

  @Before
  public final void setUp() throws Exception {
    // Approver.
    approverName = new PersonNameType();
    approverName.setFamilyName("John");
    approverName.getNameTitle().add("Dr");
    approverName.getGivenName().add("Ross");

    currentId = new Holder<String>();

    client = new UploadDocumentClient(
      sslSocketFactory,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      Endpoints.ACCENTURE_UPLOAD_DOCUMENT,
      //"https://b2b.ehealthvendortest.health.gov.au/uploadDocument",
      Logging.UPLOAD_DOCUMENT
    );
  }

  @After
  public final void tearDown() throws Exception {
    client = null;
    approverName = null;
    currentId = null;
  }

  @Test
  public void test_028() throws Exception {

//    PCEHRHeader request = MessageComponents.createRequest
//      (
//        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
//        "8003603459803467",
//        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
//        PCEHRHeader.ClientSystemType.CIS,
//        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
//      );
	  PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003618334357646", null, "Ross John", false),
        "8003601243017717",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );
	  

 //   byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test//resources/TestFiles/20120509/SHS1 - scen 28/CDA_ROOT.xml")), currentId);
    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/DS_IDTEST/CDA_ROOT-HPII.XML")), currentId);
    
    SubmissionSet subset = createPackage(rootDocument);

    // Build ZIP from package.
    byte[] packageContent = PackagingUtility.createZip(subset);

    // Write out to file for debug purposes.
    PackagingUtility.writeZip(subset, "./src/test/resources/TestFiles/Generated/UploadDocument/out_" + new Date().getTime() + ".zip");

    RegistryResponseType
      response = client.uploadDocument(
      request,
      packageContent,
      HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
      PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
      new CodedValue("PCEHR_FormatCodes", "1.2.36.1.2001.1006.1.20000.11", "Manually Supplied Discharge Summary - Conformance Level 3A")
    );
    System.out.println(response.getStatus());
    System.out.println(response.getRegistryErrorList().getRegistryErrors().get( 0 ).getCodeContext());
    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());

    RemoveDocumentResponse removeResponse = removeDoc(request, currentId.value);
    Assert.assertEquals("PCEHR_SUCCESS", removeResponse.getResponseStatus().getCode());
  }

  
  
  
  @Test
  public void test_029() throws Exception {
    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003603459803467",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );

    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/20120509/EvS1 - scen 29/CDA_ROOT.xml")), currentId);

    SubmissionSet subset = createPackage(rootDocument);

    // Build ZIP from package.
    byte[] packageContent = PackagingUtility.createZip(subset);

    // Write out to file for debug purposes.
    PackagingUtility.writeZip(subset, "./src/test/resources/TestFiles/Generated/UploadDocument/out_" + new Date().getTime() + ".zip");

    RegistryResponseType
      response = client.uploadDocument(
      request,
      packageContent,
      HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
      PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
      FormatCodes.EVENT_SUMMARY_3A.getCodedValue()
    );

    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());

    RemoveDocumentResponse removeResponse = removeDoc(request, currentId.value);
    Assert.assertEquals("PCEHR_SUCCESS", removeResponse.getResponseStatus().getCode());
  }

  @Test
  public void test_030() throws Exception {
    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003603459803467",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );

    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/20120509/eDS1 - scen 30/CDA_ROOT.xml")), currentId);

    SubmissionSet subset = createPackage(rootDocument);

    // Build ZIP from package.
    byte[] packageContent = PackagingUtility.createZip(subset);

    // Write out to file for debug purposes.
    PackagingUtility.writeZip(subset, "./src/test/resources/TestFiles/Generated/UploadDocument/out_" + new Date().getTime() + ".zip");

    RegistryResponseType
      response = client.uploadDocument(
      request,
      packageContent,
      HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
      PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
      FormatCodes.DISCHARGE_SUMMARY_3A.getCodedValue()
    );

    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());

    RemoveDocumentResponse removeResponse = removeDoc(request, currentId.value);
    Assert.assertEquals("PCEHR_SUCCESS", removeResponse.getResponseStatus().getCode());
  }
  
  /**
   * Test that the Document Hash and Size are created when using a UploadDocument request that contains a repository id
   */
  @Test
  public void test_SizeAndHashWhenUsingRepositoryID() throws Exception {
    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003603459803467",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation(/*"8003620833337558"*/ "8003628233352432", "Medicare305", null)
      );

    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/20120509/eDS1 - scen 30/CDA_ROOT.xml")), currentId);

    SubmissionSet subset = createPackage(rootDocument);

    // Build ZIP from package.
    byte[] packageContent = PackagingUtility.createZip(subset);

    // Write out to file for debug purposes.
    PackagingUtility.writeZip(subset, "./src/test/resources/TestFiles/Generated/UploadDocument/out_" + new Date().getTime() + ".zip");

    RegistryResponseType
      response = client.uploadDocument(
      request,
      packageContent,
      null,//replacement ID
      "MyRepositoryID",//repo id
      HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
      PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
      FormatCodes.DISCHARGE_SUMMARY_3A.getCodedValue()
    );

    //if error, spit out the details before failing the assertion
    if(!XDSConstants.RESPONSE_STATUS_SUCCESS.equals( response.getStatus())){
        for(RegistryError e: response.getRegistryErrorList().getRegistryErrors()){
            StringBuffer b = new StringBuffer( e.getCodeContext());
            b.append( "     Location: " ).append( e.getLocation() ).append( "       Value: " ).append( e.getValue() );
            b.append( "     code: " ).append( e.getErrorCode() );
            System.out.println(b.toString());
        }
    }
    
    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());

    RemoveDocumentResponse removeResponse = removeDoc(request, currentId.value);
    Assert.assertEquals("PCEHR_SUCCESS", removeResponse.getResponseStatus().getCode());
  }


  @Test
  public void test_031() throws Exception {
    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003603459803467",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );


    // Build ZIP from package.
    byte[] packageContent = FileUtils.loadFile(new File("./src/test/resources/TestFiles/20120509/SHS1 - scen 31 - Invalid/CDA.zip"));

    RegistryResponseType
      response = client.uploadDocument(
      request,
      packageContent,
      HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
      PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
      FormatCodes.SHARED_HEALTH_SUMMARY_3A.getCodedValue()
    );

    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_FAILURE, response.getStatus());
    Assert.assertEquals("XDSRepositoryError", response.getRegistryErrorList().getRegistryErrors().get(0).getErrorCode());
    Assert.assertEquals("PCEHR_ERROR_3001 - Invalid document folder structure", response.getRegistryErrorList().getRegistryErrors().get(0).getCodeContext());
  }

  @Test
  public void test_032() throws Exception {
    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003604570901305",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );

    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test//resources/TestFiles/20120509/SHS1 - scen 28/CDA_ROOT.xml")), currentId);

    SubmissionSet subset = createPackage(rootDocument);

    // Build ZIP from package.
    byte[] packageContent = PackagingUtility.createZip(subset);

    // Write out to file for debug purposes.
    PackagingUtility.writeZip(subset, "./src/test/resources/TestFiles/Generated/UploadDocument/out_" + new Date().getTime() + ".zip");

    RegistryResponseType
      response = client.uploadDocument(
      request,
      packageContent,
      HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
      PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
      FormatCodes.SHARED_HEALTH_SUMMARY_3A.getCodedValue()
    );

    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_FAILURE, response.getStatus());
    Assert.assertEquals("XDSRepositoryError", response.getRegistryErrorList().getRegistryErrors().get(0).getErrorCode());
    Assert.assertEquals("PCEHR_ERROR_3002 - Document metadata failed validation", response.getRegistryErrorList().getRegistryErrors().get(0).getCodeContext());
  }

  @Test
  public void testReplaceDocument() throws Exception {
    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003603459803467",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );

    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test//resources/TestFiles/20120509/SHS1 - scen 28/CDA_ROOT.xml")), currentId);

    SubmissionSet subset = createPackage(rootDocument);

    // Build ZIP from package.
    byte[] packageContent = PackagingUtility.createZip(subset);

    // Write out to file for debug purposes.
    PackagingUtility.writeZip(subset, "./src/test/resources/TestFiles/Generated/UploadDocument/out_" + new Date().getTime() + ".zip");

    RegistryResponseType
      response = client.uploadDocument(
      request,
      packageContent,
      HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
      PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
      FormatCodes.SHARED_HEALTH_SUMMARY_3A.getCodedValue()
    );

    // Check that the first document has been successfully uploaded.
    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());

    GetDocumentListClient getclient = new GetDocumentListClient(
      sslSocketFactory,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      Endpoints.ACCENTURE_GET_DOCUMENT_LIST,
      Logging.GET_DOCUMENT_LIST
    );

    // Get the doc ID assigned by the repository
    DocumentQueryParams queryParams = new DocumentQueryParams();
    queryParams.getStatuses().add(DocumentStatus.APPROVED);

    AdhocQueryResponse adhocQueryResponse = getclient.getDocumentList(request, queryParams);

    String replaceId = null;

    for (ExtrinsicObjectType eot : adhocQueryResponse.getRegistryObjectList().getExtrinsicObjects()) {
      System.out.println(eot.getObjectType());

      if (eot.getObjectType().compareTo("urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExternalIdentifier") == 0) {
        List<ExternalIdentifierType> identifiers = eot.getExternalIdentifiers();
        System.out.println();
      }
    }

    for (ExtrinsicObjectType eot : adhocQueryResponse.getRegistryObjectList().getExtrinsicObjects()) {
      for (ExternalIdentifierType identifier : eot.getExternalIdentifiers()) {
        System.out.println(identifier.getObjectType());
        if (identifier.getObjectType().compareTo(XDSConstants.XDS_DOCUMENT_ENTRY_UUID) == 0) {
          System.out.println(identifier.getId());
          replaceId = identifier.getId();
        }
      }
    }

    Holder<String> currentId2 = new Holder<String>();

    // Replacement ID retrieved.
    System.out.println("Current Repository Document ID: " + replaceId);

    // Get the doc ID assigned by the repository
    byte[] replacementDocument = modifyDocId(FileUtils.loadFile(new File("./src/test//resources/TestFiles/20120509/SHS1 - scen 28/CDA_ROOT.xml")), currentId2);

    System.out.println("New Document ID: " + currentId2.value);

    SubmissionSet subset2 = createPackage(replacementDocument);

    // Build ZIP from package.
    byte[] packageContent2 = PackagingUtility.createZip(subset2);

    RegistryResponseType response2 = client.uploadDocument(
      request,
      packageContent2,
      replaceId,
      HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
      PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
      FormatCodes.SHARED_HEALTH_SUMMARY_3A.getCodedValue()
    );

    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response2.getStatus());

    GetDocumentClient getdoc = new GetDocumentClient(
      sslSocketFactory,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      Endpoints.ACCENTURE_GET_DOCUMENT,
      Logging.GET_DOCUMENT
    );

    RetrieveDocumentSetRequest.DocumentRequest docRequest = new RetrieveDocumentSetRequest.DocumentRequest();
    docRequest.setDocumentUniqueId(currentId2.value);
    docRequest.setRepositoryUniqueId("1.2.36.1.2001.1006.0.1.3.1");

    RetrieveDocumentSetResponse retrieveDocumentSetResponse = getdoc.retrieveDocument(request, docRequest);
    System.out.println(retrieveDocumentSetResponse.getRegistryResponse().

      getStatus()

    );

    RemoveDocumentResponse removeResponse = removeDoc(request, currentId.value);
    Assert.assertEquals("PCEHR_SUCCESS", removeResponse.getResponseStatus().

      getCode()

    );
  }

  @Test
  public void testAttachmentsInFolders() throws Exception {

    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003603459803467",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );

    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test//resources/TestFiles/20120509/SHS1 - scen 28/CDA_ROOT.xml")), currentId);

    SubmissionSet subset = createPackageWithAttachments(rootDocument);

    // Build ZIP from package.
    byte[] packageContent = PackagingUtility.createZip(subset);

    // Write out to file for debug purposes.
    PackagingUtility.writeZip(subset, "./src/test/resources/TestFiles/Generated/UploadDocument/out_" + new Date().getTime() + ".zip");

    RegistryResponseType
      response = client.uploadDocument(
      request,
      packageContent,
      HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
      PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
      FormatCodes.SHARED_HEALTH_SUMMARY_3A.getCodedValue()
    );

    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());

    RemoveDocumentResponse removeResponse = removeDoc(request, currentId.value);
    Assert.assertEquals("PCEHR_SUCCESS", removeResponse.getResponseStatus().getCode());
  }

  @Ignore
  @Test
  public void testUploadTwice() throws Exception {
    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003603459803467",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );

    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test//resources/TestFiles/20120509/SHS1 - scen 28/CDA_ROOT.xml")), currentId);

    SubmissionSet subset = createPackage(rootDocument);

    // Build ZIP from package.
    byte[] packageContent = PackagingUtility.createZip(subset);

    RegistryResponseType
      response = client.uploadDocument(
      request,
      packageContent,
      HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
      PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
      FormatCodes.SHARED_HEALTH_SUMMARY_3A.getCodedValue()
    );

    System.out.println(response.getStatus());

    response = client.uploadDocument(
      request,
      packageContent,
      HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
      PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
      FormatCodes.SHARED_HEALTH_SUMMARY_3A.getCodedValue()
    );

    System.out.println(response.getStatus());
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

    //if (presentIdUuid != null) {
      // Generate a new doc ID.
      newId = UUID.randomUUID().toString();
    /*} else {
      newId = nodes.item(0).getNodeValue();// + "." + new Date().getTime();
    }*/

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

  private static SubmissionSet createPackage(final byte[] rootDocument) throws GeneralSecurityException, SignatureGenerationException {
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

  private static SubmissionSet createPackageWithAttachments(final byte[] rootDocument) throws GeneralSecurityException, SignatureGenerationException {
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
      .attachment("/new/Image1.jpg", "Image1.jpg".getBytes())
      .attachment("/new/Image2.png", "Image2.png".getBytes());

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
