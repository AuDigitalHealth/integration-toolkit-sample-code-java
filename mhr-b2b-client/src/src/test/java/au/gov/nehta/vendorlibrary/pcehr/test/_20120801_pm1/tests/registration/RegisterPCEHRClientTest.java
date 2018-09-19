package au.gov.nehta.vendorlibrary.pcehr.test._20120801_pm1.tests.registration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

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
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XPathExpressions;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.EvidenceOfIdentityCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.IVCCorrespondeceChannelCode;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.IndigenousStatusCode;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.FileUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.registration.RegisterPCEHRClient;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.AllTests;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.SecurityUtil;
import au.net.electronichealth.ns.cdapackage.xsd.esignature._2012.PersonNameType;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.ContactDetailsType;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.NameTypeSupp;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.Sex;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHR;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHR.Assertions.Identity;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHR.Assertions.Identity.EvidenceOfIdentity;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHR.Assertions.IvcCorrespondence;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHR.Individual;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHR.Individual.Demographics;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHRResponse;

public class RegisterPCEHRClientTest {

  private RegisterPCEHRClient client;
  private static SSLSocketFactory sslSocketFactory;
  private Holder<String> currentId;
  
  private static PersonNameType approverName_tucker;
  
  static{
      approverName_tucker = new PersonNameType();
      approverName_tucker.setFamilyName("TUCKER");
      approverName_tucker.getGivenName().add("Anthony");
  }
  
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
      AllTests.setUp();
    client = new RegisterPCEHRClient(
      sslSocketFactory,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      Endpoints.ACCENTURE_REGISTER_PCEHR,
      Logging.REGISTER_PCEHR
    );
    
    currentId = new Holder<String>();
  }
  
  @Test
  public void test_010() throws Exception {
	 
	  
    RegisterPCEHR registrationDetails = new RegisterPCEHR();
    
    RegisterPCEHR.Assertions assertions = new RegisterPCEHR.Assertions();
   
    assertions.setAcceptedTermsAndConditions( true );

//    DocumentConsent dc = new DocumentConsent();
//    Document document = new Document();
//    document.setType( DocumentConsentCodes.Type.PBS.toString()); 
//    document.setStatus( DocumentConsentCodes.Status.ConsentGiven.toString() );
//	dc.getDocuments().add( document  );
//	assertions.setDocumentConsent( dc  );
    
	Identity identity = new Identity();
	identity.setIndigenousStatus( IndigenousStatusCode.indigenousStatus1.getCode() );
	//identity.setSignedConsentForm( FileUtils.loadFile(new File("someSignedConsentForm.pdf") ));
	EvidenceOfIdentity evidence = new EvidenceOfIdentity();
	evidence.setType( EvidenceOfIdentityCodes.IdentityVerificationMethod1.getCode() );
	identity.setEvidenceOfIdentity( evidence );
	assertions.setIdentity( identity  );
	IvcCorrespondence ivcCorrespondence = new IvcCorrespondence();
	ivcCorrespondence.setChannel( IVCCorrespondeceChannelCode.email.getCode() );
	ContactDetailsType contactDetails = new ContactDetailsType();
	contactDetails.setEmailAddress( "example@test.com" );
	contactDetails.setMobilePhoneNumber( "041234567" );
	ivcCorrespondence.setContactDetails( contactDetails  );
	assertions.setIvcCorrespondence( ivcCorrespondence  );
	assertions.setRepresentativeDeclaration( true );
	
	registrationDetails.setAssertions(assertions);
    
	//AllTests.setUp();
//	PCEHRHeader header = AllTests.getDefaultRequest();
//	header.setIhiNumber("");
	//8003608166698043 	CAREY	BERNARD		17/11/1957	M	4 IGOR CCT	BREAKFAST POINT	NSW	2137	2950476561	Adult 5 - not Registered
	PCEHRHeader header =getHeader("8003606792133161");
	//header.setIhiNumber("");
	
 //   RegisterPCEHRResponse response = client.registerPCEHR(header, registrationDetails);
  //  Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
  
  }
  
  @Test
  public void test_080() throws Exception {
	  
	    RegisterPCEHR registrationDetails = new RegisterPCEHR();
	    
	    RegisterPCEHR.Assertions assertions = getExampleAssertions();
	    byte[] rootDocument = modifyDocId(FileUtils.loadFile(new File("./src/test/resources/TestFiles/20120718/040/CDA_ROOT.xml")), currentId);

	    SubmissionSet subset = createPackage(rootDocument, AllTests.getCertificate(), AllTests.getPrivateKey(), "8003619166668209", approverName_tucker);

	    // Build ZIP from package.
	    byte[] packageContent = PackagingUtility.createZip(subset);

		byte[] document =packageContent;
        assertions.getIdentity().setSignedConsentForm( document );
		registrationDetails.setAssertions(assertions);
		
		System.setProperty( "com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true" );
		System.setProperty( "com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true" );
		
		//RegisterPCEHR.Representative rep = new RegisterPCEHR.Representative();

		//rep.setIhiNumber("8003608166687665");
//	
		//8003608166698043 	CAREY	BERNARD		17/11/1957	M	4 IGOR CCT	BREAKFAST POINT	NSW	2137	2950476561	Adult 5 - not Registered
		PCEHRHeader header =getHeader("8003608166687665");
		//header.setIhiNumber("");
		
	    RegisterPCEHRResponse response = client.registerPCEHR(header, registrationDetails);
	    if(response.getResponseStatus().getDescription() != null){
	    	System.out.println(response.getResponseStatus().getDescription());
	    }
	    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
  }
  
  
	@Test
	public void test_081() throws Exception {

		RegisterPCEHR registrationDetails = new RegisterPCEHR();
		RegisterPCEHR.Assertions assertions = getExampleAssertions();
		registrationDetails.setAssertions(assertions);

		Demographics demographics = new Demographics();
		Calendar cal = Calendar.getInstance();
		cal.set(1981, 8, 28);// 28/09/1980
		demographics.setDateOfBirth(cal);
		NameTypeSupp name = new NameTypeSupp();
		name.setFamilyName("JACOB");
		name.getGivenNames().add("ERICAL");
		demographics.setName(name);
		demographics.setSex(Sex.F);
		Individual ind = new Individual();
		ind.setDemographics(demographics);
		registrationDetails.setIndividual(ind);
		PCEHRHeader header = getHeader("8003604444567894");

		RegisterPCEHRResponse response = client.registerPCEHR(header,	registrationDetails);
		Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
	}

	private RegisterPCEHR.Assertions getExampleAssertions() {
		RegisterPCEHR.Assertions assertions = new RegisterPCEHR.Assertions();

		assertions.setAcceptedTermsAndConditions(true);
//
//		DocumentConsent dc = new DocumentConsent();
//		Document document = new Document();
//		document.setType(DocumentConsentCodes.Type.PBS.toString());
//		document.setStatus(DocumentConsentCodes.Status.ConsentGiven.toString());
//		dc.getDocuments().add(document);
//		assertions.setDocumentConsent(dc);

		Identity identity = new Identity();

		identity.setIndigenousStatus(IndigenousStatusCode.indigenousStatus1.getCode());
	    //identity.setSignedConsentForm( FileUtils.loadFile(new File("someSignedConsentForm.pdf") ));
		EvidenceOfIdentity evidence = new EvidenceOfIdentity();
		evidence.setType(EvidenceOfIdentityCodes.IdentityVerificationMethod1.getCode());
		identity.setEvidenceOfIdentity(evidence);
		assertions.setIdentity(identity);
		
		IvcCorrespondence ivcCorrespondence = new IvcCorrespondence();
		ivcCorrespondence.setChannel(IVCCorrespondeceChannelCode.email.getCode());
		ContactDetailsType contactDetails = new ContactDetailsType();
		contactDetails.setEmailAddress("test2@test.com");
		//contactDetails.setMobilePhoneNumber("0434085109");
		ivcCorrespondence.setContactDetails(contactDetails);
		
		assertions.setIvcCorrespondence(ivcCorrespondence);
		//assertions.setRepresentativeDeclaration(true);
		return assertions;
	}

  public PCEHRHeader getHeader(String ihi){

		 return MessageComponents.createRequest
			      (
			        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "JOHN ROSS", false),
			        ihi,
			        MessageComponents.createProductType("NEHTA", "Test Harness", "1.0", "Windows 7"),
			        PCEHRHeader.ClientSystemType.CIS,
			        MessageComponents.createAccessingOrganisation("8003624166667177", "Medicare305", null)
			      );
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
