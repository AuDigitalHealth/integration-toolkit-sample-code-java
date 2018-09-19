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
package au.gov.nehta.vendorlibrary.pcehr.test.utils;

import au.gov.nehta.vendorlibrary.common.security.KeystoreUtil;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 * Utility class to create an SSLSocketFactory.
 */
public class SecurityUtil {

  /**
   * Get an SSLSocketFactory instance using default sample values.
   *
   * @return SSLSocketFactory instance.
   * @throws java.io.IOException thrown in the event the keystores cannot be accessed.
   * @throws java.security.GeneralSecurityException
   *                             thrown in the event the SSLSocketFactory cannot be built.
   */
  public static SSLSocketFactory getSslSocketFactory() throws IOException, GeneralSecurityException {
    return getSslSocketFactory(
      SecurityConstants.PRIVATE_KEY_STORE_TYPE,
      SecurityConstants.PRIVATE_KEY_STORE_PATH,
      SecurityConstants.PRIVATE_KEY_STORE_PASSWORD,
      SecurityConstants.PRIVATE_KEY_PASSWORD,
      SecurityConstants.ALIAS_8003628233352432,
      SecurityConstants.TRUST_STORE_TYPE,
      SecurityConstants.TRUST_STORE_PATH,
      SecurityConstants.TRUST_STORE_PASSWORD
    );
  }

  public static SSLSocketFactory getSslSocketFactory(String keyAlias) throws IOException, GeneralSecurityException {
    return getSslSocketFactory(
      SecurityConstants.PRIVATE_KEY_STORE_TYPE,
      SecurityConstants.PRIVATE_KEY_STORE_PATH,
      SecurityConstants.PRIVATE_KEY_STORE_PASSWORD,
      SecurityConstants.PRIVATE_KEY_PASSWORD,
      keyAlias,
      SecurityConstants.TRUST_STORE_TYPE,
      SecurityConstants.TRUST_STORE_PATH,
      SecurityConstants.TRUST_STORE_PASSWORD
    );
  }

  public static X509Certificate getCertificate() throws GeneralSecurityException {
    return KeystoreUtil.getSigningCertificate(
      SecurityConstants.PRIVATE_KEY_STORE_TYPE,
      SecurityConstants.PRIVATE_KEY_PASSWORD,
      SecurityConstants.PRIVATE_KEY_STORE_PATH,
      SecurityConstants.ALIAS_8003628233352432
    );
  }
  
  public static String getHPIOMatchingCertificate(){
	  return "8003628233352432";
  }

  public static X509Certificate getCertificate(String keyAlias) throws GeneralSecurityException {
    return KeystoreUtil.getSigningCertificate(
      SecurityConstants.PRIVATE_KEY_STORE_TYPE,
      SecurityConstants.PRIVATE_KEY_PASSWORD,
      SecurityConstants.PRIVATE_KEY_STORE_PATH,
      keyAlias
    );
  }

  public static X509Certificate getCertificate(String keyStoreType, String keyPassword, String keyStorePath, String keyAlias) throws GeneralSecurityException {
    return KeystoreUtil.getSigningCertificate(
      keyStoreType,
      keyPassword,
      keyStorePath,
      keyAlias
    );
  }

  public static PrivateKey getPrivateKey() throws GeneralSecurityException {
    return KeystoreUtil.getSigningPrivateKey(
      SecurityConstants.PRIVATE_KEY_STORE_TYPE,
      SecurityConstants.PRIVATE_KEY_PASSWORD,
      SecurityConstants.PRIVATE_KEY_STORE_PATH,
      SecurityConstants.ALIAS_8003628233352432
    );
  }

  public static PrivateKey getPrivateKey(String keyAlias) throws GeneralSecurityException {
    return KeystoreUtil.getSigningPrivateKey(
      SecurityConstants.PRIVATE_KEY_STORE_TYPE,
      SecurityConstants.PRIVATE_KEY_PASSWORD,
      SecurityConstants.PRIVATE_KEY_STORE_PATH,
      keyAlias
    );
  }

  public static PrivateKey getPrivateKey(String keyStoreType, String keyStorePassword, String keyStorePath, String keyAlias) throws GeneralSecurityException {
    return KeystoreUtil.getSigningPrivateKey(
      keyStoreType,
      keyStorePassword,
      keyStorePath,
      keyAlias
    );
  }

  /**
   * Get an SSLSocketFactory instance using custom values.
   *
   * @param privateKeyStoreType        keystore type.
   * @param privateKeyStorePath        keystore path.
   * @param privateKeyStorePassword    keystore password.
   * @param privateKeyPassword         private key password.
   * @param privateKeyCertificateAlias private key certificate alias.
   * @param trustStoreType             truststore type.
   * @param trustStorePath             truststore path.
   * @param trustStorePassword         truststore password/
   * @return SSLSocketFactory instance.
   * @throws java.io.IOException thrown in the event the keystores cannot be accessed.
   * @throws java.security.GeneralSecurityException
   *                             thrown in the event the SSLSocketFactory cannot be built.
   */
  public static SSLSocketFactory getSslSocketFactory(
    String privateKeyStoreType,
    String privateKeyStorePath,
    String privateKeyStorePassword,
    String privateKeyPassword,
    String privateKeyCertificateAlias,
    String trustStoreType,
    String trustStorePath,
    String trustStorePassword
  ) throws IOException, GeneralSecurityException {

    // Call keystore utility method with the provided details.
    return KeystoreUtil.getSslSocketFactory(
      privateKeyStoreType,
      privateKeyStorePath,
      privateKeyStorePassword,
      privateKeyPassword,
      privateKeyCertificateAlias,
      trustStoreType,
      trustStorePath,
      trustStorePassword
    );
  }
}
