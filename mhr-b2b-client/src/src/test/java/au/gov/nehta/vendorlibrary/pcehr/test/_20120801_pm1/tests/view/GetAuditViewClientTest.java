package au.gov.nehta.vendorlibrary.pcehr.test._20120801_pm1.tests.view;

import au.gov.nehta.vendorlibrary.pcehr.clients.view.GetAuditViewClient;
import au.gov.nehta.vendorlibrary.pcehr.test._20120801_pm1.AllTests;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.getauditview._1.GetAuditViewResponse;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetAuditViewClientTest {

  private GetAuditViewClient client;

  @Before
  public final void setUp() throws Exception {
    AllTests.setUp();
    client = new GetAuditViewClient(
      AllTests.getSslSocketFactory(),
      AllTests.getCertificate(),
      AllTests.getPrivateKey(),
      Endpoints.PM1_GET_AUDIT_VIEW,
      Logging.GET_AUDIT_VIEW
    );

  }

  @After
  public final void tearDown() throws Exception {
    AllTests.tearDown();
    client = null;
  }

  @Test
  public void test_090() throws Exception {
    Date from = convertFromString("20120604130000");
    Date to = convertFromString("20120604140000");
    GetAuditViewResponse response = client.getAuditView(AllTests.getDefaultRequest(), from, to);
    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
  }

  private static Date convertFromString(String dateString) throws ParseException {
    return new SimpleDateFormat("yyyyMMddHHmmss").parse(dateString);
  }
}
