package au.gov.nehta.vendorlibrary.pcehr.test._20120801_pm1.suites;

import au.gov.nehta.vendorlibrary.pcehr.test._20120801_pm1.tests.recordaccess.GainPCEHRAccessClientTest;
import au.gov.nehta.vendorlibrary.pcehr.test.unittests.ReubenArchitectTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  GainPCEHRAccessClientTest.class,
  ReubenArchitectTest.class
})

public class RecordAccessSuite {
}
