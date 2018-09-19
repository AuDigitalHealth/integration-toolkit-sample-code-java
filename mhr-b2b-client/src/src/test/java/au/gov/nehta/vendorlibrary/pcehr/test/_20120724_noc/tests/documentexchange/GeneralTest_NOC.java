package au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.documentexchange;

import au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.view.GetDocumentListClientTest_NOC;
import org.junit.Assert;
import org.junit.Test;

public class GeneralTest_NOC {

  @Test
  public void test_041() throws Exception {

    // Run positive getDocumentList
    GetDocumentListClientTest_NOC getDocumentListTests = new GetDocumentListClientTest_NOC();
    GetDocumentListClientTest_NOC.initialSetup();
    getDocumentListTests.setUp();
    getDocumentListTests.test_012();
    getDocumentListTests.tearDown();

    // Run positive uploadDocument
    UploadDocumentClientTest_NOC uploadDocumentClientTests = new UploadDocumentClientTest_NOC();
    UploadDocumentClientTest_NOC.initialSetup();
    uploadDocumentClientTests.setUp();
    uploadDocumentClientTests.test_028();
    uploadDocumentClientTests.tearDown();
    uploadDocumentClientTests.setUp();
    uploadDocumentClientTests.test_029();
    uploadDocumentClientTests.tearDown();

    // Run positive getDocument
    GetDocumentTest_NOC noc = new GetDocumentTest_NOC();
    GetDocumentTest_NOC.initialSetup();
    noc.setUp();
    noc.test_033();
    noc.tearDown();
  }


  @Test
  public void test_042
    () throws Exception {
    Assert.fail("Unable to replicate without breaking client functionality.");
  }
}
