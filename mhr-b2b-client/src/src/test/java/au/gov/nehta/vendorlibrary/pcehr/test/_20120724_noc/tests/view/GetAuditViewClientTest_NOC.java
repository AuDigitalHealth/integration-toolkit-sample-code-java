package au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.view;

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

import au.gov.nehta.vendorlibrary.pcehr.clients.view.GetAuditViewClient;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.SecurityUtil;
import au.net.electronichealth.ns.pcehr.svc.getauditview._1.StandardErrorMsg;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.getauditview._1.GetAuditViewResponse;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Test cases for the {@link au.gov.nehta.vendorlibrary.pcehr.clients.view.GetAuditViewClient} class.
 */
public class GetAuditViewClientTest_NOC {

  private GetAuditViewClient client;
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
    client = new GetAuditViewClient(
      sslSocketFactory,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      Endpoints.ACCENTURE_GET_AUDIT_VIEW,
      Logging.GET_AUDIT_VIEW
    );
  }

  @After
  public final void tearDown() throws Exception {
    client = null;
  }

  @Test
  public void test_008() throws Exception {

    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003602348687628",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );

    Date from = convertFromString("20120314004945");
    Date to = convertFromString("20120314094945");

    GetAuditViewResponse response = client.getAuditView(request, from, to);
    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
    // FIXME: this essentially tests nothing ... why is it here. it is covered by test 009 essentially.
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_009() throws Exception {
    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003602348687628",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003624166667177", "Medicare305", null)
      );

   client.getAuditView(request, null, null);
  }

  @Test
  public void test_010() throws Exception {
    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003604570901339",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );

    Date from = convertFromString("20120314004945");
    Date to = convertFromString("20260314004945");

    GetAuditViewResponse response = client.getAuditView(request, from, to);
    Assert.assertEquals("PCEHR_ERROR_1600", response.getResponseStatus().getCode());
    Assert.assertEquals("Too many rows found", response.getResponseStatus().getDescription());

  }

  @Test (expected = StandardErrorMsg.class)
  public void test_011() throws Exception {

    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003602348687628",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8000627500003640", "Medicare305", null)
      );

    Date from = convertFromString("20120314004945");
    Date to = convertFromString("20120314094945");

    try {
      GetAuditViewResponse response = client.getAuditView(request, from, to);
    } catch (StandardErrorMsg e) {
      junit.framework.Assert.assertEquals("badParam", e.getFaultInfo().getErrorCode().value());
      junit.framework.Assert.assertEquals("PCEHR_ERROR_0505 - Invalid HPI-O", e.getFaultInfo().getMessage());
      throw e;
    }
  }

  private static Date convertFromString(String dateString) throws ParseException {
    return new SimpleDateFormat("yyyyMMddHHmmss").parse(dateString);
  }
}
