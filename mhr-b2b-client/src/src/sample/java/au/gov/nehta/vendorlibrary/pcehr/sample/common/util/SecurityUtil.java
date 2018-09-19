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
package au.gov.nehta.vendorlibrary.pcehr.sample.common.util;

import au.gov.nehta.vendorlibrary.common.security.KeystoreUtil;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.constants.SampleSecurityConstants;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 * Utility class to create an SSLSocketFactory.
 */
public final class SecurityUtil {

  /**
   * Private constructor to prevent instantiation.
   */
  private SecurityUtil() {
  }

  /**
   * Get an SSLSocketFactory instance using default sample values.
   *
   * @return SSLSocketFactory instance.
   * @throws IOException              thrown in the event the keystores cannot be accessed.
   * @throws GeneralSecurityException thrown in the event the SSLSocketFactory cannot be built.
   */
  public static SSLSocketFactory getSslSocketFactory() throws IOException, GeneralSecurityException {
    return getSslSocketFactory(
      SampleSecurityConstants.PRIVATE_KEY_STORE_TYPE,
      SampleSecurityConstants.PRIVATE_KEY_STORE_PATH,
      SampleSecurityConstants.PRIVATE_KEY_STORE_PASSWORD,
      SampleSecurityConstants.PRIVATE_KEY_PASSWORD,
      SampleSecurityConstants.PRIVATE_KEY_ALIAS,
      SampleSecurityConstants.TRUST_STORE_TYPE,
      SampleSecurityConstants.TRUST_STORE_PATH,
      SampleSecurityConstants.TRUST_STORE_PASSWORD
    );
  }

  public static X509Certificate getCertificate() throws GeneralSecurityException {
    return KeystoreUtil.getSigningCertificate(
      SampleSecurityConstants.PRIVATE_KEY_STORE_TYPE,
      SampleSecurityConstants.PRIVATE_KEY_PASSWORD,
      SampleSecurityConstants.PRIVATE_KEY_STORE_PATH,
      SampleSecurityConstants.PRIVATE_KEY_ALIAS
    );
  }

  public static PrivateKey getPrivateKey() throws GeneralSecurityException {
    return KeystoreUtil.getSigningPrivateKey(
      SampleSecurityConstants.PRIVATE_KEY_STORE_TYPE,
      SampleSecurityConstants.PRIVATE_KEY_PASSWORD,
      SampleSecurityConstants.PRIVATE_KEY_STORE_PATH,
      SampleSecurityConstants.PRIVATE_KEY_ALIAS
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
   * @throws IOException              thrown in the event the keystores cannot be accessed.
   * @throws GeneralSecurityException thrown in the event the SSLSocketFactory cannot be built.
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
