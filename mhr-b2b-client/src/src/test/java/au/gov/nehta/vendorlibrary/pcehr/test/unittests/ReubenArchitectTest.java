/*
 * Copyright 2011 NEHTA
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
package au.gov.nehta.vendorlibrary.pcehr.test.unittests;

import au.gov.nehta.vendorlibrary.pcehr.clients.recordaccess.DoesPCEHRExistClient;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.pcehrprofile._1.DoesPCEHRExistResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReubenArchitectTest {

  private DoesPCEHRExistClient client;

  @Before
  public final void setUp() throws Exception {
    AllTests_Reuben.setUp();
    client = new DoesPCEHRExistClient(
      AllTests_Reuben.getSslSocketFactory(),
      AllTests_Reuben.getCertificate(),
      AllTests_Reuben.getPrivateKey(),
      Endpoints.ACCENTURE_DOES_PCEHR_EXIST,
      Logging.DOES_PCEHR_EXIST
    );
  }

  @After
  public final void tearDown() throws Exception {
    AllTests_Reuben.tearDown();
    client = null;
  }

  @Test
  public void test_030() throws Exception {
    DoesPCEHRExistResponse response = client.doesPCEHRExist(AllTests_Reuben.getDefaultRequest());
    Assert.assertEquals(true, response.isPCEHRExists());
    Assert.assertEquals(DoesPCEHRExistResponse.AccessCodeRequired.ACCESS_GRANTED, response.getAccessCodeRequired());
  }
}
