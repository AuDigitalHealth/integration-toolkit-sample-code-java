package au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.view.AllViewTest_NOC;
import au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.view.GetAuditViewClientTest_NOC;
import au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.view.GetChangeHistoryViewClientTest_NOC;
import au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.view.GetDocumentListClientTest_NOC;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  GetChangeHistoryViewClientTest_NOC.class,
  GetAuditViewClientTest_NOC.class,
  GetDocumentListClientTest_NOC.class,
  AllViewTest_NOC.class
})

public class ViewSuite_NOC {
}
