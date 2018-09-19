package au.gov.nehta.vendorlibrary.pcehr.test.unittests.views;

import au.gov.nehta.vendorlibrary.pcehr.clients.view.GetRepresentativeListClient;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.SecurityUtil;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.getrepresentativelist._1.GetRepresentativeListResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

public class GetRepresentativeListClientTest {

  private GetRepresentativeListClient client;
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
    client = new GetRepresentativeListClient(
      sslSocketFactory,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      Endpoints.ACCENTURE_GET_REPRESENTATIVE_LIST,
      Logging.GET_REPRESENTATIVE_LIST
    );
  }

  @After
  public final void tearDown() throws Exception {
    client = null;
  }

  @Test
  public void testGetRepresentativeListResponse() throws Exception {

    GetRepresentativeListResponse response = client.getRepresentativeList(
//      MessageComponents.createRequest
//        (
//          MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Anthony TUCKER", false),
//         "8003602348687602",
//          MessageComponents.createProductType("NEHTA", "testHarness", "1.0", "Windows 7 - Java"),
//          PCEHRHeader.ClientSystemType.CIS,
//          MessageComponents.createAccessingOrganisation("8003628233352432", "2", null)
//        )
    		
    		MessageComponents.createRequest(
    	              MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166668209", null, "Anthony TUCKER", false),
    	              "8003608500011184",
    	              MessageComponents.createProductType("NEHTA", "testHarness", "1.0", "Windows 7 - Java"),
    	              PCEHRHeader.ClientSystemType.CIS,
    	              MessageComponents.createAccessingOrganisation("8003628233352432", "2", null)
    	            )
    		
    );

    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
  }
}
