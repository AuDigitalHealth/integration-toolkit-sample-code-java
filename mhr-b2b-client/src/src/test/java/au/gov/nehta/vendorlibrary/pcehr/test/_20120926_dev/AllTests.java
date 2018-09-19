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

package au.gov.nehta.vendorlibrary.pcehr.test._20120926_dev;

import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.SecurityConstants;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.SecurityUtil;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class AllTests {

  private static SSLSocketFactory sslSocketFactory;
  private static X509Certificate certificate;
  private static PrivateKey privateKey;
  private static PCEHRHeader defaultRequest;

  @BeforeClass
  public static void setUp() throws Exception {

    HttpsURLConnection.setDefaultHostnameVerifier(
      new HostnameVerifier() {
        public boolean verify(String s, SSLSession sslSession) {
          return true;
        }
      }
    );

    defaultRequest = MessageComponents.createRequest(
      MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003618334357646", null, "Anthony TUCKER", false),
      "8003601243017717",
      MessageComponents.createProductType("NEHTA", "testHarness", "1.0", "Windows 7 - Java"),
      PCEHRHeader.ClientSystemType.CIS,
      MessageComponents.createAccessingOrganisation("8003628233352432", "2", null)
    );

    sslSocketFactory = SecurityUtil.getSslSocketFactory(SecurityConstants.ALIAS_8003628233352432);

    certificate = SecurityUtil.getCertificate(SecurityConstants.ALIAS_8003628233352432);

    privateKey = SecurityUtil.getPrivateKey(
      SecurityConstants.PRIVATE_KEY_STORE_TYPE,
      SecurityConstants.PRIVATE_KEY_PASSWORD,
      SecurityConstants.PRIVATE_KEY_STORE_PATH,
      SecurityConstants.ALIAS_8003628233352432
    );

    // For testing purposes.
    System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");

    // Sets the newly created sslsocketfactory as the default for all instances OF the HttpsURLConnection class.
    HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);
  }

  @AfterClass
  public static void tearDown() {
    defaultRequest = null;
    sslSocketFactory = null;
    certificate = null;
    privateKey = null;
  }

  public static PCEHRHeader getDefaultRequest() {
    return defaultRequest;
  }

  public static SSLSocketFactory getSslSocketFactory() {
    return sslSocketFactory;
  }

  public static X509Certificate getCertificate() {
    return certificate;
  }

  public static PrivateKey getPrivateKey() {
    return privateKey;
  }
}
