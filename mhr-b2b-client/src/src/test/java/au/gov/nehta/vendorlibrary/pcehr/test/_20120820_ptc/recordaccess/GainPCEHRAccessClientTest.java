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
package au.gov.nehta.vendorlibrary.pcehr.test._20120820_ptc.recordaccess;

import au.gov.nehta.vendorlibrary.pcehr.clients.recordaccess.GainPCEHRAccessClient;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.test._20120820_ptc.AllTests;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.pcehrprofile._1.GainPCEHRAccess;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.pcehrprofile._1.GainPCEHRAccessResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GainPCEHRAccessClientTest {

  private GainPCEHRAccessClient client;

  @Before
  public final void setUp() throws Exception {
    AllTests.setUp();
    client = new GainPCEHRAccessClient(
      AllTests.getSslSocketFactory(),
      AllTests.getCertificate(),
      AllTests.getPrivateKey(),
      Endpoints.PTC_GAIN_PCEHR_ACCESS,
      Logging.GAIN_PCEHR_ACCESS
    );
  }

  @After
  public final void tearDown() throws Exception {
    AllTests.tearDown();
    client = null;
  }

  @Test
  public void test_020() throws Exception {
    GainPCEHRAccess.PCEHRRecord record = MessageComponents.createGainPCEHRRecord(null);
    GainPCEHRAccessResponse response = client.gainPCEHRAccess(record, AllTests.getDefaultRequest());
    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
    Assert.assertEquals("SUCCESS", response.getResponseStatus().getDescription());
    Assert.assertEquals("8003601008083417", response.getIndividual().getIhiNumber());
  }
}
