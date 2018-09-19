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
package au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.view;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XDSConstants;
import au.gov.nehta.vendorlibrary.pcehr.clients.view.GetChangeHistoryViewClient;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.SecurityUtil;
import au.net.electronichealth.ns.pcehr.svc.getchangehistoryview._1.StandardErrorMsg;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.getchangehistoryview._1.GetChangeHistoryViewResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

/**
 * Test cases for the {@link GetChangeHistoryViewClient} class.
 */
public class GetChangeHistoryViewClientTest_NOC {

  private GetChangeHistoryViewClient client;
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
    client = new GetChangeHistoryViewClient(
      sslSocketFactory,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      Endpoints.ACCENTURE_GET_CHANGE_HISTORY_VIEW,
      Logging.GET_CHANGE_HISTORY_VIEW
    );
  }

  @After
  public final void tearDown() throws Exception {
    client = null;
  }

  @Test
  public void test_005() throws Exception {

    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003604570901321",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );

    GetChangeHistoryViewResponse response = client.getChangeHistoryView(request, "urn:uuid:00da425d-ddfb-4ef0-876e-855cd882fa78");
    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getAdhocQueryResponse().getStatus());
//    Assert.assertTrue(response.getAdhocQueryResponse().getRegistryObjectList().getExtrinsicObjects().size() > 0);
  }

  @Test
  public void test_006() throws Exception {

    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003604570901321",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );

    GetChangeHistoryViewResponse response = client.getChangeHistoryView(request, "urn:uuid:b494924e-9bd6-4adc-950c-12f0224f6805");

    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_FAILURE, response.getAdhocQueryResponse().getStatus());
    Assert.assertEquals("XDSRegistryError", response.getAdhocQueryResponse().getRegistryErrorList().getRegistryErrors().get(0).getErrorCode());
    Assert.assertEquals("METADATA_ERROR - The requested registry object is deprecated.", response.getAdhocQueryResponse().getRegistryErrorList().getRegistryErrors().get(0).getCodeContext());
  }

  @Test(expected = StandardErrorMsg.class)
  public void test_007() throws Exception {

    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003606792133161",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8000627500003640", "Medicare305", null)
      );

    try {
      GetChangeHistoryViewResponse response = client.getChangeHistoryView(request, "urn:uuid:00da425d-ddfb-4ef0-876e-855cd8");
    } catch (StandardErrorMsg e) {
      Assert.assertEquals("badParam", e.getFaultInfo().getErrorCode().value());
      Assert.assertEquals("PCEHR_ERROR_0505 - Invalid HPI-O", e.getFaultInfo().getMessage());
      throw e;
    }
  }
}
