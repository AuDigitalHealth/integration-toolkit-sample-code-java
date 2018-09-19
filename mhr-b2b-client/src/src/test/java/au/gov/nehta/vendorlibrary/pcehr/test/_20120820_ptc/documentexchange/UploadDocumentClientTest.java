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

package au.gov.nehta.vendorlibrary.pcehr.test._20120820_ptc.documentexchange;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.UUID;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
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

import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryError;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import au.gov.nehta.vendorlibrary.clinicalpackage.core.Member;
import au.gov.nehta.vendorlibrary.clinicalpackage.core.SubmissionSet;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.PackagingUtility;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.SignatureGenerationException;
import au.gov.nehta.vendorlibrary.common.security.KeystoreUtil;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XDSConstants;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XPathExpressions;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CodedValue;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HealthcareFacilityTypeCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.PracticeSettingTypeCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.FileUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.documentexchange.UploadDocumentClient;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.test._20120820_ptc.AllTests;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.SecurityConstants;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.SecurityUtil;
import au.net.electronichealth.ns.cdapackage.xsd.esignature._2012.PersonNameType;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;

import com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe;

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
    personNameType.setFamilyName("Button");
    personNameType.getGivenName().add("Henry");
  }

  @Before
  public final void setUp() throws Exception {
    AllTests.setUp();
    currentId = new Holder<String>();
    client = new UploadDocumentClient(
      AllTests.getSslSocketFactory(),
      AllTests.getCertificate(),
      AllTests.getPrivateKey(),
//      Endpoints.SVT_UPLOAD_DOCUMENT,
        "https://nehta-drp-iis.nehta.net.au/PcehrSimulator/Services/uploadDocument",
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
  public void test_UploadDocument_139() throws Exception {

//    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/MTOM/CDA_ROOT.xml")), currentId);
	   byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/DS_IDTEST/DischargeSummary_3A_With_HPII.xml")), currentId);

    SubmissionSet subset = createPackage(rootDocument, AllTests.getCertificate(), AllTests.getPrivateKey(), "8003628233352432", personNameType);
    //subset.getAttachments().add(new Member.Builder().fileContent(FileUtils.loadFile(new File("./src/test/resources/TestFiles/MTOM/logo51.png"))).uri("logo51.png").build());



    // Build ZIP from package.
    byte[] packageContent = PackagingUtility.createZip(subset);

    PackagingUtility.writeZip(subset, "./src/test/resources/TestFiles/Generated/out_" + new Date().getTime() + ".zip");

    RegistryResponseType
      response = client.uploadDocument(
      AllTests.getDefaultRequest(),
      packageContent,
      HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
      PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
      new CodedValue("PCEHR_FormatCodes", "1.2.36.1.2001.1006.1.20000.11", "Discharge Summary - Conformance Level 3A")
      
    );
    
    System.out.println("======================================");
    System.out.println(response.getStatus());
    for(RegistryError e: response.getRegistryErrorList().getRegistryErrors()){
    	System.out.println(e.getCodeContext());
    	System.out.println(e.getErrorCode());
    	System.out.println(e.getSeverity());
    	System.out.println(e.getLocation());
    }

    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
  }

  @Test
  public void test_UploadDocument_208() throws Exception {
//    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/MTOM/CDA_ROOT.xml")), currentId);
    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/DS_IDTEST/DischargeSummary_3A_With_HPII.xml")), currentId);

    SubmissionSet subset = createPackage(rootDocument, AllTests.getCertificate(), AllTests.getPrivateKey(), "8003628233352432", personNameType);
//    subset.getAttachments().add(new Member.Builder().fileContent(FileUtils.loadFile(new File("./src/test/resources/TestFiles/MTOM/logo52.png"))).uri("logo52.png").build());



    // Build ZIP from package.
    byte[] packageContent = PackagingUtility.createZip(subset);

    PackagingUtility.writeZip(subset, "./src/test/resources/TestFiles/Generated/out_" + new Date().getTime() + ".zip");

    RegistryResponseType
      response = client.uploadDocument(
      AllTests.getDefaultRequest(),
      packageContent,
      HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
      PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
      new CodedValue("PCEHR_FormatCodes", "1.2.36.1.2001.1006.1.20000.11", "Discharge Summary - Conformance Level 3A")
    );

    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());

  }

  @Test
  public void test_UploadDocument_682() throws Exception {

//    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/MTOM/CDA_ROOT.xml")), currentId);
    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/MTOM/CDA_ROOT_mine.xml")), currentId);

    SubmissionSet subset = createPackage(rootDocument, AllTests.getCertificate(), AllTests.getPrivateKey(), "8003618334357646", personNameType);
    subset.getAttachments().add(new Member.Builder().fileContent(FileUtils.loadFile(new File("./src/test/resources/TestFiles/MTOM/logo53.png"))).uri("logo53.png").build());

    // Build ZIP from package.
    byte[] packageContent = PackagingUtility.createZip(subset);

    PackagingUtility.writeZip(subset, "./src/test/resources/TestFiles/Generated/out_" + new Date().getTime() + ".zip");

    PCEHRHeader defaultRequest = MessageComponents.createRequest(
    	      MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003618334357646", null, "Henry Button", false),
    	      "8003608666701594",
    	      MessageComponents.createProductType("NEHTA", "Test Harness", "1.0", "Windows 7 - Java"),
    	      PCEHRHeader.ClientSystemType.CIS,
    	      MessageComponents.createAccessingOrganisation("8003628233352432", "Good Hospital", null)
    	    );
    
    
    RegistryResponseType
      response = null;
    try {
      response = client.uploadDocument(
    		  defaultRequest,
        packageContent,
        HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
        PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
        new CodedValue("PCEHR_FormatCodes", "1.2.36.1.2001.1006.1.20000.11", "Discharge Summary - Conformance Level 3A")
      );
    } catch (Exception e) {
      System.out.println(e.getMessage());
      HttpTransportPipe.dump = true;
    }
    
    System.out.println("======================================");
    System.out.println(response.getStatus());
    for(RegistryError e: response.getRegistryErrorList().getRegistryErrors()){
    	System.out.println(e.getCodeContext());
    	System.out.println(e.getErrorCode());
    	System.out.println(e.getSeverity());
    	System.out.println(e.getLocation());
    }
    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());

  }
  
  
  
  
  @Test
  public void test_UploadDocument_682_H() throws Exception {

    //byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/MTOM/CDA_ROOT_mine_HPIO2.xml")), currentId);
	  byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/DS_IDTEST/CDA_ROOT-HPII.XML")), currentId);
	  

    SubmissionSet subset = createPackage(rootDocument, AllTests.getCertificate(), AllTests.getPrivateKey(), "8003618334357646", personNameType);
   // subset.getAttachments().add(new Member.Builder().fileContent(FileUtils.loadFile(new File("./src/test/resources/TestFiles/MTOM/logo53.png"))).uri("logo53.png").build());

    // Build ZIP from package.
    byte[] packageContent = PackagingUtility.createZip(subset);

    PackagingUtility.writeZip(subset, "./src/test/resources/TestFiles/Generated/out_" + new Date().getTime() + ".zip");

    RegistryResponseType
      response = null;
    try {
      response = client.uploadDocument(
        AllTests.getDefaultRequest(),
        packageContent,
        HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
        PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
        new CodedValue("PCEHR_FormatCodes", "1.2.36.1.2001.1006.1.20000.11", "Discharge Summary - Conformance Level 3A")
      );
    } catch (Exception e) {
      System.out.println(e.getMessage());
      HttpTransportPipe.dump = true;
    }
    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());

  }
  
  @Test
  public void test_UploadDocument_682_LocalID() throws Exception {

//    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/MTOM/CDA_ROOT.xml")), currentId);
    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/MTOM/CDA_ROOT_LocalID.xml")), currentId);

    SubmissionSet subset = createPackage(rootDocument, AllTests.getCertificate(), AllTests.getPrivateKey(), "buttonj001", personNameType);
    subset.getAttachments().add(new Member.Builder().fileContent(FileUtils.loadFile(new File("./src/test/resources/TestFiles/MTOM/logo53.png"))).uri("logo53.png").build());

    // Build ZIP from package.
    byte[] packageContent = PackagingUtility.createZip(subset);

    PackagingUtility.writeZip(subset, "./src/test/resources/TestFiles/Generated/out_" + new Date().getTime() + ".zip");


    PCEHRHeader request = MessageComponents.createRequest(
    	      MessageComponents.createUser(PCEHRHeader.User.IDType.LOCAL_SYSTEM_IDENTIFIER, "buttonj001", null, "Henry Button", false),
    	      "8003608666701594",
    	     // null,
    	      MessageComponents.createProductType("NEHTA", "Test Harness", "1.0", "Windows 7 - Java"),
    	      PCEHRHeader.ClientSystemType.CIS,
    	      MessageComponents.createAccessingOrganisation("8003628233352432", "Good Hospital", null)
    	    );
    
    RegistryResponseType
      response = null;
    try {
      response = client.uploadDocument(
        request,
        packageContent,
        HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
        PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
        new CodedValue("PCEHR_FormatCodes", "1.2.36.1.2001.1006.1.20000.11", "Discharge Summary - Conformance Level 3A")
      );
    } catch (Exception e) {
      System.out.println(e.getMessage());
      HttpTransportPipe.dump = true;
    }
    
    System.out.println("======================================");
    System.out.println(response.getStatus());
    for(RegistryError e: response.getRegistryErrorList().getRegistryErrors()){
    	System.out.println(e.getCodeContext());
    	System.out.println(e.getErrorCode());
    	System.out.println(e.getSeverity());
    	System.out.println(e.getLocation());
    }
    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());

  }
  
  @Test
  public void test_UploadDocument_682_PAI_D() throws Exception {

//    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/MTOM/CDA_ROOT.xml")), currentId);
    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/MTOM/CDA_ROOT_PAI-D.xml")), currentId);

    SubmissionSet subset = createPackage(rootDocument, AllTests.getCertificate(), AllTests.getPrivateKey(), "8003628233352432", personNameType);
    subset.getAttachments().add(new Member.Builder().fileContent(FileUtils.loadFile(new File("./src/test/resources/TestFiles/MTOM/logo53.png"))).uri("logo53.png").build());

    // Build ZIP from package.
    byte[] packageContent = PackagingUtility.createZip(subset);

    PackagingUtility.writeZip(subset, "./src/test/resources/TestFiles/Generated/out_" + new Date().getTime() + ".zip");

    PCEHRHeader request = MessageComponents.createRequest(
    	      MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003618334357646", null, "Henry Button", false),
    	      "8003608666701594",
    	      MessageComponents.createProductType("NEHTA", "dummyCISusr1", "dummyCISusrV1", "Windows 7 - Java"),
    	      PCEHRHeader.ClientSystemType.CIS,
    	      MessageComponents.createAccessingOrganisation("8003628233352432", "Good Hospital", null)
    	    );
    
    
    RegistryResponseType response = null;
    try {
      response = client.uploadDocument(
		  request,
        packageContent,
        HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
        PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
        new CodedValue("PCEHR_FormatCodes", "1.2.36.1.2001.1006.1.20000.11", "Discharge Summary - Conformance Level 3A")
      );
    } catch (Exception e) {
      System.out.println(e.getMessage());
      HttpTransportPipe.dump = true;
    }
    
    
    
    System.out.println("======================================");
    if(response != null){
        System.out.println(response.getStatus());
        for(RegistryError e: response.getRegistryErrorList().getRegistryErrors()){
        	System.out.println(e.getCodeContext());
        	System.out.println(e.getErrorCode());
        	System.out.println(e.getSeverity());
        	System.out.println(e.getLocation());
        }
    }
    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());

  }
  
  
  
  /**
   * Test uploading a discharge summary with a HPII identifier
   */
  @Test
  public void test_UploadDocument_SVT_HPII() throws Exception {

   byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/MTOM/CDA_ROOT_mine.XML")), currentId);
   String alias = SecurityConstants.ALIAS_8003628233352432;
  
   PrivateKey privateKey = SecurityUtil.getPrivateKey(
		     SecurityConstants.PRIVATE_KEY_STORE_TYPE,
		     SecurityConstants.PRIVATE_KEY_PASSWORD,
		    // "./src/test/resources/security/svt-keystore.jks",
		     SecurityConstants.PRIVATE_KEY_STORE_PATH,
		     alias
		   );
   
   SSLSocketFactory   sslSocketFactory = SecurityUtil.getSslSocketFactory(alias);

   X509Certificate certificate = SecurityUtil.getCertificate(alias);

   privateKey = SecurityUtil.getPrivateKey(
     SecurityConstants.PRIVATE_KEY_STORE_TYPE,
     SecurityConstants.PRIVATE_KEY_PASSWORD,
     SecurityConstants.PRIVATE_KEY_STORE_PATH,
    alias
   );
  

   UploadDocumentClient SVT_client = new UploadDocumentClient(
		   	  sslSocketFactory,
		      certificate,
		      privateKey,
		      Endpoints.SVT_UPLOAD_DOCUMENT,
		      Logging.UPLOAD_DOCUMENT
		    );

   SubmissionSet subset = createPackage(
    		rootDocument,
    		certificate,
    		privateKey,
    	//	"general.8003620833337558.id.electronichealth.net.au",
    		"http://ns.electronichealth.net.au/id/hi/hpii/1.0/8003619166674595",
    		personNameType);
   
    //subset.getAttachments().add(new Member.Builder().fileContent(FileUtils.loadFile(new File("./src/test/resources/TestFiles/MTOM/logo51.png"))).uri("logo51.png").build());

    // Build ZIP from package.
    byte[] packageContent = PackagingUtility.createZip(subset);

    PackagingUtility.writeZip(subset, "./src/test/resources/TestFiles/Generated/out_" + new Date().getTime() + ".zip");

    //8003618334357646, 8003608666701594,  dummyCISusr1, dummyCISusrV1 dummyCISusrV1
    PCEHRHeader request = MessageComponents.createRequest(
    	      MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003618334357646", null, "Anthony TUCKER", false),
    	      "8003608666701594", //. 8003608666701594 
    	      MessageComponents.createProductType("NEHTA", "Test Harness", "1.0", "Windows 7"),
//    	      PCEHRHeader.ClientSystemType.CIS,
    	      PCEHRHeader.ClientSystemType.CIS,
    	      MessageComponents.createAccessingOrganisation("8003628233352432", "Good Hospital", null)
    	      //8003620833335909			Medicare305				
    	    );
    
    RegistryResponseType
      response = SVT_client.uploadDocument(
    		//  AllTests.getDefaultRequest(),
    		  request,
      packageContent,
      HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
      PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
      new CodedValue("PCEHR_FormatCodes", "1.2.36.1.2001.1006.1.20000.11", "Discharge Summary - Conformance Level 3A")
     // new CodedValue(" ", " ", " ")
    );

    
    System.out.println("======================================");
    if(response != null){
        System.out.println(response.getStatus());
        for(RegistryError e: response.getRegistryErrorList().getRegistryErrors()){
        	System.out.println(e.getCodeContext());
        	System.out.println(e.getErrorCode());
        	System.out.println(e.getSeverity());
        	System.out.println(e.getLocation());
        }
    }
    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
  }
  


  
  
  /**
   * This is a terrible thing that should never be done to a production server.
   * 
   * Basically we'll tell our JVM to trust *ANYTHING* which is ok in this
   * instance because it's just for testing.
 * @return 
   */
  private TrustManager[] getDodgyTrustManager(){
	// Create a trust manager that does not validate certificate chains
	  TrustManager[] trustAllCerts = new TrustManager[] { 
	      new X509TrustManager() {     
	          public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
	              return null;
	          } 
	          public void checkClientTrusted(X509Certificate[] certs, String authType) { 
	        	  System.out.println("trusting cert");
	          } 
	          public void checkServerTrusted(X509Certificate[] certs, String authType) {
	        	  System.out.println("trusting cert");
	          }
	      } 
	  }; 
	  
	  return trustAllCerts;
  }
  
  public SSLSocketFactory getDodgySocketFactory(String alias){
	  SSLContext context = null;
	  try{
	   KeyStore privateKeyStore = 
			   KeystoreUtil.loadKeyStore(SecurityConstants.PRIVATE_KEY_STORE_TYPE, 
					   SecurityConstants.PRIVATE_KEY_STORE_PASSWORD,
					   //"./src/test/resources/security/svt-keystore.jks"
					   SecurityConstants.PRIVATE_KEY_STORE_PATH
					   );
//	    KeyStore trustStore = loadKeyStore(trustStoreType, trustStorePassword, trustStoreFile);

//	    // Build the key managers.
	   KeyManagerFactory kmFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
	    kmFactory.init(privateKeyStore, SecurityConstants.PRIVATE_KEY_STORE_PASSWORD.toCharArray());
	    final KeyManager[] keyManagers = kmFactory.getKeyManagers();
	    for (int i = 0; i < keyManagers.length; i++) {
	      if (keyManagers[i] instanceof X509KeyManager) {
	        keyManagers[i] = new AliasForcingX509KeyManager((X509KeyManager) keyManagers[i], alias);
	      }
	    }
	    
	    context = SSLContext.getInstance("TLS");
	    context.init(kmFactory.getKeyManagers(), getDodgyTrustManager(), null);
	    return context.getSocketFactory();
	  	}catch (Exception e ){
	    	e.printStackTrace();
	    }
	   return null;
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
  
  
  
  private static class AliasForcingX509KeyManager implements X509KeyManager {

	    private final X509KeyManager baseKM;
	    private final String keyAlias;

	    public AliasForcingX509KeyManager(final X509KeyManager keyManager, final String keyAlias) {
	      this.baseKM = keyManager;
	      this.keyAlias = keyAlias;
	    }

	    public String chooseClientAlias(final String[] keyType,
	                                    final Principal[] issuers,
	                                    final Socket socket) {
	      return this.keyAlias;
	    }

	    public String chooseServerAlias(final String keyType,
	                                    final Principal[] issuers,
	                                    final Socket socket) {
	      return this.baseKM.chooseServerAlias(keyType, issuers, socket);
	    }

	    public X509Certificate[] getCertificateChain(final String alias) {
	      return this.baseKM.getCertificateChain(alias);
	    }

	    public String[] getClientAliases(final String keyType, final Principal[] issuers) {
	      return this.baseKM.getClientAliases(keyType, issuers);
	    }

	    public PrivateKey getPrivateKey(final String alias) {
	      return this.baseKM.getPrivateKey(alias);
	    }

	    public String[] getServerAliases(final String keyType, final Principal[] issuers) {
	      return this.baseKM.getServerAliases(keyType, issuers);
	    }
	  }
}

