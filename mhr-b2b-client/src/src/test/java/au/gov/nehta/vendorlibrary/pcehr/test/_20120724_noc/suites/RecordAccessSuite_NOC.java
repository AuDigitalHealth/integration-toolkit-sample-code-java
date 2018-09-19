package au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.suites;

import au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.recordaccess.AllRecordAccessTest_NOC;
import au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.recordaccess.DoesPCHERExistClientTest_NOC;
import au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.recordaccess.GainPCEHRAccessClientTest_NOC;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  DoesPCHERExistClientTest_NOC.class,
  GainPCEHRAccessClientTest_NOC.class,
  AllRecordAccessTest_NOC.class
})

public class RecordAccessSuite_NOC {

}
