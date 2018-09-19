package au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.documentexchange;

import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryError;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import au.gov.nehta.vendorlibrary.clinicalpackage.core.SubmissionSet;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.PackagingUtility;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XDSConstants;
import au.gov.nehta.vendorlibrary.pcehr.clients.documentexchange.GetDocumentClient;
import au.gov.nehta.vendorlibrary.pcehr.clients.view.GetDocumentListClient;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.SecurityUtil;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.TestUtils;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.wsp.xsd.standarderror._2010.StandardError;
import au.net.electronichealth.ns.wsp.xsd.standarderror._2010.StandardErrorCodeType;

public class GetDocumentTest_NOC {
	
  private GetDocumentClient client;
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
    client = new GetDocumentClient(
      sslSocketFactory,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      Endpoints.ACCENTURE_GET_DOCUMENT,
      Logging.GET_DOCUMENT
    );
  }

  @After
  public final void tearDown() throws Exception {
    client = null;
  }


  @Test
  public void test_033() throws Exception {

    StandardError fault = new StandardError();
    fault.setErrorCode(StandardErrorCodeType.BADLY_FORMED_MSG);
    fault.setMessage("something");
   // StandardErrorMsg msg = new StandardErrorMsg("more detail?", fault);


    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003604570901313",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );

    RetrieveDocumentSetRequest.DocumentRequest docRequest = new RetrieveDocumentSetRequest.DocumentRequest();
    docRequest.setDocumentUniqueId("1.2.13.1.3998.1985336745999149");
    docRequest.setRepositoryUniqueId("1.2.36.1.2001.1007.10");

    RetrieveDocumentSetResponse response = client.retrieveDocument(request, docRequest);
    System.out.println(response.getRegistryResponse().getStatus());
    
    for(RegistryError e :response.getRegistryResponse().getRegistryErrorList().getRegistryErrors()){
    	System.out.println(e.getErrorCode());
    	System.out.println(e.getCodeContext());
    }
    
    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getRegistryResponse().getStatus());
    Assert.assertEquals("application/zip", response.getDocumentResponses().get(0).getMimeType());
    Assert.assertEquals("1.2.13.1.3998.1985336745999149", response.getDocumentResponses().get(0).getDocumentUniqueId());
    Assert.assertEquals("1.2.36.1.2001.1007.10", response.getDocumentResponses().get(0).getRepositoryUniqueId());
    Assert.assertNotNull(response.getDocumentResponses().get(0).getDocument());

    TestUtils.writeDoc(response.getDocumentResponses().get(0).getDocument(), new Object() {
    }.getClass().getEnclosingMethod().getName(), "test");

    SubmissionSet submissionSet = PackagingUtility.extractPackage(String.format("./src/test/resources/TestFiles/Generated/GetDocument/%s", String.format("%stest.zip", new Object() {
    }.getClass().getEnclosingMethod().getName())));
  }

  @Test
  public void test_034() throws Exception {
    PCEHRHeader request = MessageComponents.createRequest(
      MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
      "8003604570901313",
      MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
      PCEHRHeader.ClientSystemType.CIS,
      MessageComponents.createAccessingOrganisation("8003624166667177", "Medicare305", null)
    );

    RetrieveDocumentSetRequest.DocumentRequest docRequest = new RetrieveDocumentSetRequest.DocumentRequest();
    docRequest.setDocumentUniqueId("1.3.16.1.38818.298717791234576");
    docRequest.setRepositoryUniqueId("1.2.36.1.2001.1006.0.1.3.1");

    RetrieveDocumentSetResponse response = client.retrieveDocument(request, docRequest);

    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_FAILURE, response.getRegistryResponse().getStatus());
    Assert.assertEquals("XDSRepositoryError", response.getRegistryResponse().getRegistryErrorList().getRegistryErrors().get(0).getErrorCode());
    Assert.assertEquals("PCEHR_ERROR_3503 - Removed document not retrievable from PCEHR", response.getRegistryResponse().getRegistryErrorList().getRegistryErrors().get(0).getCodeContext());
  }
}
