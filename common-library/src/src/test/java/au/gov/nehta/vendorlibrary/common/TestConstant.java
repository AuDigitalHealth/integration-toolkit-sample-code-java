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
package au.gov.nehta.vendorlibrary.common;

import au.gov.nehta.vendorlibrary.common.security.KeystoreUtil;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class TestConstant {

  public static final String RESOURCES_DIR = "./src/test/resources/";
  public static final String PRIVATE_KEY_STORE_TYPE = "JKS";
  public static final String PRIVATE_KEY_STORE_FILE = RESOURCES_DIR + "keystore.jks";
  public static final String PRIVATE_KEY_STORE_PASSWORD = "password";
  public static final String DRP_PRIVATE_KEY_ALIAS = "8003630000000004";
    public static final String MEDICARE_CURRENT_PRIVATE_KEY_ALIAS ="8003600000000000";
  public static final String MEDICARE_NEW_PRIVATE_KEY_ALIAS ="8003600000000001";
  public static final String PRIVATE_KEY_PASSWORD = "password";


  public static final String TRUST_STORE_TYPE = "JKS";
  public static final String TRUST_STORE_FILE = RESOURCES_DIR + "truststore.jks";
  public static final String TRUST_STORE_PASSWORD = "password";


  public static final String SENSITIVE_PAYLOAD = "<sample>This is a sensitive data</sample>";

  public static PrivateKey getSigningPrivateKey(String privateKeyAlias) throws GeneralSecurityException {
    PrivateKey privateKey = KeystoreUtil.getSigningPrivateKey(PRIVATE_KEY_STORE_TYPE,
        PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE, privateKeyAlias);
    return privateKey;
  }

  public static X509Certificate getSigningCertificate(String certificateAlias) throws GeneralSecurityException {
    X509Certificate cert = KeystoreUtil.getSigningCertificate(PRIVATE_KEY_STORE_TYPE,
        PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE, certificateAlias);
    return cert;
  }

  public static PrivateKey getBlankSigningPrivateKey(String privateKeyAlias) throws GeneralSecurityException {
    PrivateKey privateKey = KeystoreUtil.getSigningPrivateKey(PRIVATE_KEY_STORE_TYPE,
        PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE, privateKeyAlias);
    return privateKey;
  }


  public static X509Certificate getBlankSigningCertificate(String certificateAlias) throws GeneralSecurityException {
    X509Certificate cert = KeystoreUtil.getSigningCertificate(PRIVATE_KEY_STORE_TYPE,
        PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE, certificateAlias);
    return cert;
  }


}
