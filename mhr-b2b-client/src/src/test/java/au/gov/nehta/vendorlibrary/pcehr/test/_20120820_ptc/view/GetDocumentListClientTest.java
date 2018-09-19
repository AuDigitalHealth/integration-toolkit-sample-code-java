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
package au.gov.nehta.vendorlibrary.pcehr.test._20120820_ptc.view;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XDSConstants;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentQueryParams;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentStatus;
import au.gov.nehta.vendorlibrary.pcehr.clients.view.GetDocumentListClient;
import au.gov.nehta.vendorlibrary.pcehr.test._20120820_ptc.AllTests;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExtrinsicObjectType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Test cases for the {@link au.gov.nehta.vendorlibrary.pcehr.clients.view.GetDocumentListClient} class.
 */
public class GetDocumentListClientTest {

  private GetDocumentListClient client;

  @Before
  public final void setUp() throws Exception {
    AllTests.setUp();
    client = new GetDocumentListClient(
      AllTests.getSslSocketFactory(),
      AllTests.getCertificate(),
      AllTests.getPrivateKey(),
      Endpoints.PTC_GET_DOCUMENT_LIST,
      Logging.GET_DOCUMENT_LIST
    );
  }

  @After
  public final void tearDown() throws Exception {
    AllTests.tearDown();
    client = null;
  }

  @Test
  public void test_080() throws Exception {

    DocumentQueryParams queryParams = new DocumentQueryParams();
    queryParams.getStatuses().add(DocumentStatus.APPROVED);
    AdhocQueryResponse response = client.getDocumentList(AllTests.getDefaultRequest(), queryParams);
    
    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
    Assert.assertTrue(response.getRegistryObjectList().getExtrinsicObjects().size() > 0);
    List<ExtrinsicObjectType> docs = response.getRegistryObjectList().getExtrinsicObjects();
    for (ExtrinsicObjectType doc : docs) {
      Assert.assertEquals(XDSConstants.EOT_STATUS_APPROVED, doc.getStatus());
    }
  }
}

