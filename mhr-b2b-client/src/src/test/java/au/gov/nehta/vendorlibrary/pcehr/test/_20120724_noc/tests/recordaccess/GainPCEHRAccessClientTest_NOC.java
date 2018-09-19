/*
 * Copyright 2011 NEHTA
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
package au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.recordaccess;

import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;

import java.net.URL;
import java.net.URLClassLoader;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExternalIdentifierType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExtrinsicObjectType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.Slot;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryError;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import au.gov.nehta.vendorlibrary.common.security.KeystoreUtil;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XDSConstants;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentQueryParams;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentStatus;
import au.gov.nehta.vendorlibrary.pcehr.clients.documentexchange.GetDocumentClient;
import au.gov.nehta.vendorlibrary.pcehr.clients.recordaccess.GainPCEHRAccessClient;
import au.gov.nehta.vendorlibrary.pcehr.clients.view.GetDocumentListClient;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.SecurityConstants;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.SecurityUtil;
import au.net.electronichealth.ns.pcehr.b2b.svc.pcehrprofile._1.StandardErrorMsg;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.pcehrprofile._1.GainPCEHRAccess;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.pcehrprofile._1.GainPCEHRAccessResponse;

public class GainPCEHRAccessClientTest_NOC {
	private GetDocumentListClient listclient;
  private GainPCEHRAccessClient client;
  private GetDocumentClient docclient;
  private static SSLSocketFactory sslSocketFactory;

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
    client = new GainPCEHRAccessClient(
      sslSocketFactory,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      Endpoints.ACCENTURE_GAIN_PCEHR_ACCESS,
      Logging.GAIN_PCEHR_ACCESS
    );
    
    docclient = new GetDocumentClient(
    	      sslSocketFactory,
    	      SecurityUtil.getCertificate(),
    	      SecurityUtil.getPrivateKey(),
    	      Endpoints.ACCENTURE_GET_DOCUMENT,
    	      true
    	    );
    
   listclient = new GetDocumentListClient(
    	      sslSocketFactory,
    	      SecurityUtil.getCertificate(),
    	      SecurityUtil.getPrivateKey(),
    	      Endpoints.ACCENTURE_GET_DOCUMENT_LIST,
    	      Logging.GET_DOCUMENT_LIST
    	    );
  }

  @After
  public final void tearDown() throws Exception {
    client = null;
  }

  @Test
  public void test_for_HealthMetrics() throws Exception {
	  
	  String alias  = SecurityConstants.ALIAS_8003628233352432;// SecurityConstants.REVOKED_ALIAS_8003628233351004;
	  
	  SSLSocketFactory sslSocketFactory = SecurityUtil.getSslSocketFactory(
		      SecurityConstants.PRIVATE_KEY_STORE_TYPE,
		      SecurityConstants.PRIVATE_KEY_STORE_PATH,
		      SecurityConstants.PRIVATE_KEY_STORE_PASSWORD,
		      SecurityConstants.PRIVATE_KEY_PASSWORD,
		      alias,
		      SecurityConstants.TRUST_STORE_TYPE,
		      SecurityConstants.TRUST_STORE_PATH,
		      SecurityConstants.TRUST_STORE_PASSWORD
		    );
		 
	  X509Certificate signingCertificate = KeystoreUtil.getSigningCertificate(
		      SecurityConstants.PRIVATE_KEY_STORE_TYPE,
		      SecurityConstants.PRIVATE_KEY_PASSWORD,
		      SecurityConstants.PRIVATE_KEY_STORE_PATH,
		      alias
		    );
		 
	  PrivateKey signingPrivateKey = KeystoreUtil.getSigningPrivateKey(
		      SecurityConstants.PRIVATE_KEY_STORE_TYPE,
		      SecurityConstants.PRIVATE_KEY_PASSWORD,
		      SecurityConstants.PRIVATE_KEY_STORE_PATH,
		      alias
		    );
	  
	  GainPCEHRAccessClient  client = new GainPCEHRAccessClient(
		      sslSocketFactory,
		      signingCertificate,
		      signingPrivateKey,
		      Endpoints.ACCENTURE_GAIN_PCEHR_ACCESS,
		      Logging.GAIN_PCEHR_ACCESS
		    );
	  
	  PCEHRHeader request = MessageComponents.createRequest
		      (
		        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
		        "8003606792133146",
		        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
		        PCEHRHeader.ClientSystemType.CIS,
		        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
		      );

		    GainPCEHRAccess.PCEHRRecord record = MessageComponents.createGainPCEHRRecord
		      (
		        MessageComponents.createAuthorisationDetails(GainPCEHRAccess.PCEHRRecord.AuthorisationDetails.AccessType.EMERGENCY_ACCESS, null)
		      );

		    HttpsURLConnection.setDefaultHostnameVerifier(
		              new HostnameVerifier() {
		                public boolean verify(String s, SSLSession sslSession) {
		                  return true;
		                }
		              }
		            );
		    
	
		    
		    
		    GainPCEHRAccessResponse response=null;
		    
		    response = client.gainPCEHRAccess(record, request);
		    

		    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
		    Assert.assertEquals("SUCCESS", response.getResponseStatus().getDescription());
		    Assert.assertEquals("8003606792133146", response.getIndividual().getIhiNumber());

		     ClassLoader cl = ClassLoader.getSystemClassLoader();
		     
		        URL[] urls = ((URLClassLoader)cl).getURLs();
		 
		        for(URL url: urls){
		        	System.out.println(url.getFile());
		        }
		 
  }
  
  
  @Test
  public void test_ForSterlingSystems() throws Exception {
    PCEHRHeader request = MessageComponents.createRequest(
      MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
      "8003608166697110",//"8003604570901313",
      MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
      PCEHRHeader.ClientSystemType.CIS,
      MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
    );
    
    DocumentQueryParams queryParams = new DocumentQueryParams();
    queryParams.getStatuses().add(DocumentStatus.APPROVED);
    


    AdhocQueryResponse list = listclient.getDocumentList(request, queryParams);
   
    ExtrinsicObjectType firstDocument = list.getRegistryObjectList().getExtrinsicObjects().get(0);
	String repoId =getRepoId( firstDocument.getSlots());
	
    String xdsUniqueId=getXDSUniqueID(firstDocument.getExternalIdentifiers());
    
    GainPCEHRAccess.PCEHRRecord record = MessageComponents.createGainPCEHRRecord
    (
    	MessageComponents.createAuthorisationDetails(GainPCEHRAccess.PCEHRRecord.AuthorisationDetails.AccessType.EMERGENCY_ACCESS, null)
    );
    
    GainPCEHRAccessResponse gainAccess = client.gainPCEHRAccess(record, request);
    System.out.println(gainAccess.getResponseStatus().getDescription());

    RetrieveDocumentSetRequest.DocumentRequest docRequest = new RetrieveDocumentSetRequest.DocumentRequest();
    docRequest.setDocumentUniqueId(xdsUniqueId); 
    docRequest.setRepositoryUniqueId(repoId) ;
    
    
    RetrieveDocumentSetResponse response = docclient.retrieveDocument(request, docRequest);
    System.out.println(response.getRegistryResponse().getStatus());
    RegistryError registryError = response.getRegistryResponse().getRegistryErrorList().getRegistryErrors().get(0);
	System.out.println(registryError.getCodeContext());
	System.out.println(registryError.getValue());
	System.out.println(registryError.getSeverity());
	
    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getRegistryResponse().getStatus());
    Assert.assertEquals("XDSRepositoryError", registryError.getErrorCode());
  }
  
  public String getRepoId(List<Slot> slots){
	  for(Slot slot:slots){
		  if("repositoryUniqueId".equals(slot.getName()) ){
			  return slot.getValueList().getValues().get(0);
		  }
	  }
	  
	  //failed to find
	  return "";
  }
  
  public String getXDSUniqueID(List<ExternalIdentifierType> list){
	  for(ExternalIdentifierType id:list){
		  if( XDSConstants.XDS_DOCUMENT_ENTRY_UNIQUE_ID_UUID.equals(id.getIdentificationScheme()) ){
			  return id.getValue();
		  }
	  }
	  
	  //failed to find
	  return "";
  }
  
  @Test
  public void test_018() throws Exception {

    PCEHRHeader request = MessageComponents.createRequest(
      MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
      "8003601243017691",
      MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
      PCEHRHeader.ClientSystemType.CIS,
      MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
    );

    GainPCEHRAccess.PCEHRRecord record = MessageComponents.createGainPCEHRRecord(null);

    GainPCEHRAccessResponse response = client.gainPCEHRAccess(record, request);

    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
    Assert.assertEquals("SUCCESS", response.getResponseStatus().getDescription());
    Assert.assertEquals("8003601243017691", response.getIndividual().getIhiNumber());
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_019() throws Exception {
    PCEHRHeader request = MessageComponents.createRequest(
      MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
      "0003602348687602",
      MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
      PCEHRHeader.ClientSystemType.CIS,
      MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
    );

    GainPCEHRAccess.PCEHRRecord record = MessageComponents.createGainPCEHRRecord(null);

    GainPCEHRAccessResponse response = client.gainPCEHRAccess(record, request);
  }

  @Test
  public void test_020() throws Exception {
    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003606792133146",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );

    GainPCEHRAccess.PCEHRRecord record = MessageComponents.createGainPCEHRRecord
      (
        MessageComponents.createAuthorisationDetails(GainPCEHRAccess.PCEHRRecord.AuthorisationDetails.AccessType.EMERGENCY_ACCESS, null)
      );

    GainPCEHRAccessResponse response = client.gainPCEHRAccess(record, request);

    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
    Assert.assertEquals("SUCCESS", response.getResponseStatus().getDescription());
    Assert.assertEquals("8003606792133146", response.getIndividual().getIhiNumber());
  }

  @Test
  public void test_021() throws Exception {
    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003603459803459",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );

    GainPCEHRAccess.PCEHRRecord record = MessageComponents.createGainPCEHRRecord(
      MessageComponents.createAuthorisationDetails(
        GainPCEHRAccess.PCEHRRecord.AuthorisationDetails.AccessType.ACCESS_CODE,
        "12345678"
      )
    );

    GainPCEHRAccessResponse response = client.gainPCEHRAccess(record, request);

    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
    Assert.assertEquals("SUCCESS", response.getResponseStatus().getDescription());
    Assert.assertEquals("8003603459803459", response.getIndividual().getIhiNumber());
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_022() throws Exception {
    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003601243017709",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );

    GainPCEHRAccess.PCEHRRecord record = MessageComponents.createGainPCEHRRecord(
      MessageComponents.createAuthorisationDetails(
        GainPCEHRAccess.PCEHRRecord.AuthorisationDetails.AccessType.ACCESS_CODE,
        null
      )
    );

    GainPCEHRAccessResponse response = client.gainPCEHRAccess(record, request);
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_023() throws Exception {

    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );

    GainPCEHRAccess.PCEHRRecord record = MessageComponents.createGainPCEHRRecord(null);

    GainPCEHRAccessResponse response = client.gainPCEHRAccess(record, request);
  }

  @Test
  public void test_024() throws Exception {

    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003608833338197",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );

    GainPCEHRAccess.PCEHRRecord record = MessageComponents.createGainPCEHRRecord(null);

    GainPCEHRAccessResponse response = client.gainPCEHRAccess(record, request);
  }

  @Test
  public void test_025() throws Exception {

    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003605681935025",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );

    GainPCEHRAccess.PCEHRRecord record = new GainPCEHRAccess.PCEHRRecord();
    record.setIndividual(null);
    record.setAuthorisationDetails(null);

    GainPCEHRAccessResponse response = client.gainPCEHRAccess(record, request);

    Assert.assertEquals("PCEHR_ERROR_5102", response.getResponseStatus().getCode());
  }

  @Test(expected = StandardErrorMsg.class)
  public void test_026() throws Exception {

    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003602348687602",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8000627500003640", "Medicare305", null)
      );

    GainPCEHRAccess.PCEHRRecord record = new GainPCEHRAccess.PCEHRRecord();
    record.setAuthorisationDetails(null);
    record.setIndividual(null);

    try {
      GainPCEHRAccessResponse response = client.gainPCEHRAccess(record, request);
    } catch (StandardErrorMsg e) {
      Assert.assertEquals("badParam", e.getFaultInfo().getErrorCode().value());
      Assert.assertEquals("PCEHR_ERROR_0505 - Invalid HPI-O", e.getFaultInfo().getMessage());
      throw e;
    }
  }
}
