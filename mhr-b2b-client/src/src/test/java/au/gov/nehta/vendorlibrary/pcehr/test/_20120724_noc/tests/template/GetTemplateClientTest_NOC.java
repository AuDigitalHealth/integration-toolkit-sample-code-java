package au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.template;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.TemplateServiceRequestorOption;
import au.gov.nehta.vendorlibrary.pcehr.clients.template.GetTemplateClient;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.SecurityUtil;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.tplt.svc.gettemplate._1.StandardErrorMsg;
import au.net.electronichealth.ns.tplt.xsd.interfaces.gettemplate._1.GetTemplateResponse;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

public class GetTemplateClientTest_NOC {


  private GetTemplateClient client;
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
    client = new GetTemplateClient(
      sslSocketFactory,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      Endpoints.ACCENTURE_GET_TEMPLATE,
      Logging.GET_TEMPLATE
    );
  }

  @After
  public final void tearDown() throws Exception {
    client = null;
  }

  @Test
  public void test_043() throws Exception {

		System.setProperty("com.sun.xml.ws.util.pipe.StandaloneTubeAssembler.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.local.LocalTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		 System.setProperty( "com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true" );
	  
    PCEHRHeader request = MessageComponents.createRequest(
      MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
      null,
      MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
      PCEHRHeader.ClientSystemType.CIS,
      MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
    );
    GetTemplateResponse response =null;
    try{
     response = client.getTemplate(request, "1.2.36.1.2001.1006.1.16565.3", TemplateServiceRequestorOption.MachineUsable);
    }catch(StandardErrorMsg t){
    	System.out.println(t.getFaultInfo().getMessage());
    }
    Assert.assertNotNull(response);
    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
    Assert.assertEquals("SUCCESS", response.getResponseStatus().getDescription());
    Assert.assertNotNull(response.getTemplate().getTemplateMetadata());
    Assert.assertNotNull(response.getTemplate().getPackage());
  }

  @Test
  public void test_044() throws Exception {
    Assert.fail("No valid test data.");
//    FIXME: add proper test data here.
//    PCEHRHeader request = MessageComponents.createRequest(
//      MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
//      null,
//      MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
//      PCEHRHeader.ClientSystemType.CIS,
//      MessageComponents.createAccessingOrganisation("8003624166667177", "Medicare305", null)
//    );
//
//    GetTemplateResponse response = client.getTemplate(request, "913", "");
//    Assert.assertNotNull(response);
//    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
//    Assert.assertEquals("SUCCESS", response.getResponseStatus().getDescription());
//    Assert.assertNotNull(response.getTemplate().getTemplateMetadata());
//    Assert.assertNotNull(response.getTemplate().getPackage());
  }

  @Test
  public void test_045() throws Exception {
    PCEHRHeader request = MessageComponents.createRequest(
      MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
      null,
      MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
      PCEHRHeader.ClientSystemType.CIS,
      MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
    );

    GetTemplateResponse response = client.getTemplate(request, "333", TemplateServiceRequestorOption.FullPackage);
    Assert.assertNotNull(response);
    Assert.assertEquals("PCEHR_ERROR_1801", response.getResponseStatus().getCode());
    Assert.assertEquals("Invalid template ID", response.getResponseStatus().getDescription());
  }
}
