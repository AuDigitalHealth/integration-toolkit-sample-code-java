package au.gov.nehta.vendorlibrary.pcehr.test._20120801_pm1.tests.documentexchange;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XDSConstants;
import au.gov.nehta.vendorlibrary.pcehr.clients.documentexchange.GetDocumentClient;
import au.gov.nehta.vendorlibrary.pcehr.test._20120801_pm1.AllTests;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetDocumentTest {

  private GetDocumentClient client;

  @Before
  public final void setUp() throws Exception {
    AllTests.setUp();
    client = new GetDocumentClient(
      AllTests.getSslSocketFactory(),
      AllTests.getCertificate(),
      AllTests.getPrivateKey(),
      Endpoints.PM1_GET_DOCUMENT,
      Logging.GET_DOCUMENT
    );
  }

  @After
  public final void tearDown() throws Exception {
    AllTests.tearDown();
    client = null;
  }

  @Test
  public void test_060() throws Exception {
    UploadDocumentClientTest uploadDocumentClientTest = new UploadDocumentClientTest();
    UploadDocumentClientTest.initialSetup();
    uploadDocumentClientTest.setUp();
    uploadDocumentClientTest.test_040();
    String currentId = uploadDocumentClientTest.getCurrentId().value;
    RetrieveDocumentSetRequest.DocumentRequest docRequest = new RetrieveDocumentSetRequest.DocumentRequest();
    docRequest.setDocumentUniqueId(currentId);
    docRequest.setRepositoryUniqueId("1.2.36.1.2001.1007.10");
    RetrieveDocumentSetResponse response = client.retrieveDocument(AllTests.getDefaultRequest(), docRequest);
    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getRegistryResponse().getStatus());
    Assert.assertEquals("application/zip", response.getDocumentResponses().get(0).getMimeType());
    Assert.assertEquals(currentId, response.getDocumentResponses().get(0).getDocumentUniqueId());
    Assert.assertEquals("1.2.36.1.2001.1007.10", response.getDocumentResponses().get(0).getRepositoryUniqueId());
    Assert.assertNotNull(response.getDocumentResponses().get(0).getDocument());
  }
}
