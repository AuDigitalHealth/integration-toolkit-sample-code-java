package au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.suites;

import au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.documentexchange.GeneralTest_NOC;
import au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.documentexchange.GetDocumentTest_NOC;
import au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.documentexchange.RemoveDocumentTest_NOC;
import au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.documentexchange.UploadDocumentMetadataClient_NOC;
import au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.documentexchange.UploadDocumentClientTest_NOC;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  UploadDocumentClientTest_NOC.class,
  GetDocumentTest_NOC.class,
  RemoveDocumentTest_NOC.class,
  UploadDocumentMetadataClient_NOC.class,
  GeneralTest_NOC.class
})

public class DocumentExchangeSuite_NOC {
}
