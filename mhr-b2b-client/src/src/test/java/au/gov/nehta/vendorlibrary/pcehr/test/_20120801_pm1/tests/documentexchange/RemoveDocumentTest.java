package au.gov.nehta.vendorlibrary.pcehr.test._20120801_pm1.tests.documentexchange;

import au.gov.nehta.vendorlibrary.pcehr.clients.documentexchange.RemoveDocumentClient;
import au.gov.nehta.vendorlibrary.pcehr.test._20120801_pm1.AllTests;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.removedocument._1.RemoveDocument;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.removedocument._1.RemoveDocumentResponse;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RemoveDocumentTest {

  private RemoveDocumentClient client;

  @Before
  public final void setUp() throws Exception {
    AllTests.setUp();
    client = new RemoveDocumentClient(
      AllTests.getSslSocketFactory(),
      AllTests.getCertificate(),
      AllTests.getPrivateKey(),
      Endpoints.PM1_REMOVE_DOCUMENT,
      Logging.REMOVE_DOCUMENT
    );
  }

  @After
  public final void tearDown() throws Exception {
    AllTests.tearDown();
    client = null;
  }

  @Test
  public void test_007() throws Exception {
    UploadDocumentClientTest uploadDocumentClientTest = new UploadDocumentClientTest();
    UploadDocumentClientTest.initialSetup();
    uploadDocumentClientTest.setUp();
    uploadDocumentClientTest.test_040();
    String currentId = uploadDocumentClientTest.getCurrentId().value;
    uploadDocumentClientTest.tearDown();
    RemoveDocumentResponse response = client.removeDocument(AllTests.getDefaultRequest(), currentId, RemoveDocument.DocumentRemovalReason.WITHDRAWN);
    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
  }
}
