package au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.view;

import org.junit.Test;

public class AllViewTest_NOC {
  @Test
  public void test_014() throws Exception {

    GetChangeHistoryViewClientTest_NOC getChangeHistoryView = new GetChangeHistoryViewClientTest_NOC();
    GetChangeHistoryViewClientTest_NOC.initialSetup();
    getChangeHistoryView.setUp();
    getChangeHistoryView.test_005();
    getChangeHistoryView.tearDown();

    GetAuditViewClientTest_NOC getAuditView = new GetAuditViewClientTest_NOC();
    GetAuditViewClientTest_NOC.initialSetup();
    getAuditView.setUp();
    getAuditView.test_008();
    getAuditView.tearDown();
  }
}
