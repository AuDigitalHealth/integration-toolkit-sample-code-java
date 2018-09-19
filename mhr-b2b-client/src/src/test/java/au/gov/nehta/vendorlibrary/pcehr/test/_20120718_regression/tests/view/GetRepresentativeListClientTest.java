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

package au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.tests.view;

import au.gov.nehta.vendorlibrary.pcehr.clients.view.GetRepresentativeListClient;
import au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.AllTests;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.getrepresentativelist._1.GetRepresentativeListResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetRepresentativeListClientTest {

  private GetRepresentativeListClient client;

  @Before
  public final void setUp() throws Exception {
    AllTests.setUp();
    System.setProperty( "javax.net.debug", "all" );
    client = new GetRepresentativeListClient(
      AllTests.getSslSocketFactory(),
      AllTests.getCertificate(),
      AllTests.getPrivateKey(),
      Endpoints.REGRESSION_GET_REPRESENTATIVE_LIST,
      Logging.GET_REPRESENTATIVE_LIST
    );
  }

  @After
  public final void tearDown() throws Exception {
    AllTests.tearDown();
    client = null;
  }

  @Test
  public void test_010() throws Exception {
    GetRepresentativeListResponse response = client.getRepresentativeList(AllTests.getDefaultRequest());
    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
  }
}
