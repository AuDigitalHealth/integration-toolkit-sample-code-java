package au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.tests.view;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XDSConstants;
import au.gov.nehta.vendorlibrary.pcehr.clients.view.GetChangeHistoryViewClient;
import au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.AllTests;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.getchangehistoryview._1.GetChangeHistoryViewResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetChangeHistoryViewClientTest {

  private GetChangeHistoryViewClient client;

  @Before
  public final void setUp() throws Exception {
    AllTests.setUp();
    client = new GetChangeHistoryViewClient(
      AllTests.getSslSocketFactory(),
      AllTests.getCertificate(),
      AllTests.getPrivateKey(),
      Endpoints.REGRESSION_GET_CHANGE_HISTORY_VIEW,
      Logging.GET_CHANGE_HISTORY_VIEW
    );
  }

  @After
  public final void tearDown() throws Exception {
    AllTests.tearDown();
    client = null;
  }

  @Test
  public void test_100() throws Exception {
    GetChangeHistoryViewResponse response = client.getChangeHistoryView(AllTests.getDefaultRequest(), "2.25.269016203286022974372968221446807081525.1343175045853");
    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getAdhocQueryResponse().getStatus());
  }
}
