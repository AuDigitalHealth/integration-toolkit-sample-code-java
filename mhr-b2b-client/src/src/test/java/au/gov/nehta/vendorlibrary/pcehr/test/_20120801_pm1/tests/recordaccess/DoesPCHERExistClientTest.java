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
package au.gov.nehta.vendorlibrary.pcehr.test._20120801_pm1.tests.recordaccess;

import au.gov.nehta.vendorlibrary.pcehr.clients.recordaccess.DoesPCEHRExistClient;
import au.gov.nehta.vendorlibrary.pcehr.test._20120801_pm1.AllTests;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.pcehrprofile._1.DoesPCEHRExistResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DoesPCHERExistClientTest {

  private DoesPCEHRExistClient client;

  @Before
  public final void setUp() throws Exception {
	
    AllTests.setUp();
    client = new DoesPCEHRExistClient(
      AllTests.getSslSocketFactory(),
      AllTests.getCertificate(),
      AllTests.getPrivateKey(),
      Endpoints.PM1_DOES_PCEHR_EXIST,
      Logging.DOES_PCEHR_EXIST
    );
  }

  @After
  public final void tearDown() throws Exception {
    AllTests.tearDown();
    client = null;
  }

  @Test
  public void test_030() throws Exception {
	  try{
    DoesPCEHRExistResponse response = client.doesPCEHRExist(AllTests.getDefaultRequest());
	  }catch(Throwable t){
		  t.printStackTrace();
			    for(StackTraceElement e: t.getStackTrace())
			        System.out.println(e);
	  }
	  System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
	  System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
	 
	  System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
	  					           
	  System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
    DoesPCEHRExistResponse response = null;
	Assert.assertEquals(true, response.isPCEHRExists());
    Assert.assertEquals(DoesPCEHRExistResponse.AccessCodeRequired.ACCESS_GRANTED, response.getAccessCodeRequired());
  }
}
