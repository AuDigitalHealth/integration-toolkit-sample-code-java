/*
 * Copyright 2012 NEHTA
 *
 * Licensed under the NEHTA Open Source (Apache) License; you may not use this
 * file except in compliance with the License. A copy of the License is in the
 * 'license.txt' file, which should be provided with this work.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package au.gov.nehta.vendorlibrary.pcehr.test._20120820_ptc.documentexchange;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XDSConstants;
import au.gov.nehta.vendorlibrary.pcehr.clients.documentexchange.GetDocumentClient;
import au.gov.nehta.vendorlibrary.pcehr.test._20120820_ptc.AllTests;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestGetDocument {

  private GetDocumentClient client;

  @Before
  public final void setUp() throws Exception {
    AllTests.setUp();
    client = new GetDocumentClient(
      AllTests.getSslSocketFactory(),
      AllTests.getCertificate(),
      AllTests.getPrivateKey(),
//      Endpoints.PTC_GET_DOCUMENT,
      "https://144.140.140.147:443/getDocument",
      Logging.GET_DOCUMENT
    );
  }

  @After
  public final void tearDown() throws Exception {
    AllTests.tearDown();
    client = null;
  }

  @Test
  public void test_GetDocumentHappyCase() throws Exception {
    UploadDocumentClientTest uploadDocumentClientTest = new UploadDocumentClientTest();
    UploadDocumentClientTest.initialSetup();
    uploadDocumentClientTest.setUp();
    uploadDocumentClientTest.test_UploadDocument_139();
    String currentId = uploadDocumentClientTest.getCurrentId().value;
    RetrieveDocumentSetRequest.DocumentRequest docRequest = new RetrieveDocumentSetRequest.DocumentRequest();
    docRequest.setDocumentUniqueId(currentId);
    docRequest.setRepositoryUniqueId("1.2.36.1.2001.1007.10.8003640002000050");
    RetrieveDocumentSetResponse response = client.retrieveDocument(AllTests.getDefaultRequest(), docRequest);
    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getRegistryResponse().getStatus());
    Assert.assertEquals("application/zip", response.getDocumentResponses().get(0).getMimeType());
    Assert.assertEquals(currentId, response.getDocumentResponses().get(0).getDocumentUniqueId());
    Assert.assertEquals("1.2.36.1.2001.1007.10.8003640002000050", response.getDocumentResponses().get(0).getRepositoryUniqueId());
    Assert.assertNotNull(response.getDocumentResponses().get(0).getDocument());
  }
}
