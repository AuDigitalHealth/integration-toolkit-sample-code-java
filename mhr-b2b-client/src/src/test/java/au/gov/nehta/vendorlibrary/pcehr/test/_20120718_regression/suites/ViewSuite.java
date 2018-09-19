package au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.tests.view.GetAuditViewClientTest;
import au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.tests.view.GetChangeHistoryViewClientTest;
import au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.tests.view.GetDocumentListClientTest;
import au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.tests.view.GetRepresentativeListClientTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  GetDocumentListClientTest.class,
  GetAuditViewClientTest.class,
  GetChangeHistoryViewClientTest.class,
  GetRepresentativeListClientTest.class
})

public class ViewSuite {
}
