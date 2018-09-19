package au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  ViewSuite_NOC.class,
  RecordAccessSuite_NOC.class,
  DocumentExchangeSuite_NOC.class,
  TemplateSuite_NOC.class
})

public class NOCTestSuite {
}
