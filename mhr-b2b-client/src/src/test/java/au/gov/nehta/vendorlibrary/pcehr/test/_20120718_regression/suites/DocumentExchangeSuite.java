package au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.suites;


import au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.tests.documentexchange.GetDocumentTest;
import au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.tests.documentexchange.RemoveDocumentTest;
import au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.tests.documentexchange.UploadDocumentClientTest;
import au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.tests.documentexchange.UploadDocumentMetadataClientTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  UploadDocumentClientTest.class,
  UploadDocumentMetadataClientTest.class,
  RemoveDocumentTest.class,
  GetDocumentTest.class
})

public class DocumentExchangeSuite {
}
