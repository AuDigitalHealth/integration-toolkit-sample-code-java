package au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.suites;

import au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.tests.recordaccess.DoesPCHERExistClientTest;
import au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.tests.recordaccess.GainPCEHRAccessClientTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  GainPCEHRAccessClientTest.class,
  DoesPCHERExistClientTest.class
})

public class RecordAccessSuite {
}
