package au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.suites;

import au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.template.GetTemplateClientTest_NOC;
import au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.template.SearchTemplateClientTest_NOC;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  GetTemplateClientTest_NOC.class,
  SearchTemplateClientTest_NOC.class
})

public class TemplateSuite_NOC {
}

