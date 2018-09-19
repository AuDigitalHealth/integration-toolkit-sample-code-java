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
package au.gov.nehta.vendorlibrary.common.security;

import au.gov.nehta.common.utils.ArgumentUtils;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Logger;

/**
 * {@link KeystoreUtil} class to load keystore from a file, and obtain X509 certificates and private keys from
 * key stores. 
 */

public final class KeystoreUtil {
  /**
   * Logger instance for message logging
   */
  private static final Logger LOG = Logger.getLogger(KeystoreUtil.class
    .getName());

  /**
   * Default private constructor prevents instantiation from other classes
   */
  private KeystoreUtil() { // Don't need to instantiate this class.
  }

  /**
   * Returns the truststore.url and truststore.password
   * properties must be set in client-security-env.properties or
   * server-security-env.properties
   */

  /**
   * Returns the Keystore object for the provided keystorePathname, keystorePassword, keystoreType values.
   *
   * @param keystoreType     the type of keystore  <br>
   *                         <b>See Java Cryptography Architecture API Specification for information about standard keystore types</b>.
   * @param keystorePassword the password used to check the integrity of the keystore,
   *                         the password used to unlock the keystore
   * @param keystorePathname a pathname string
   * @return a keystore object of the specified type.
   * @throws GeneralSecurityException if either of the keystorePassword, keystorePathname or keystoreType is invalid.
   */
  public static KeyStore loadKeyStore(final String keystoreType, final String keystorePassword,
                                      final String keystorePathname) throws GeneralSecurityException {
    KeyStore keystore = null;
    InputStream in = null;
    ArgumentUtils.checkNotNullNorBlank(keystoreType, "keystoreType");
    ArgumentUtils.checkNotNullNorBlank(keystorePassword, "keystorePassword");
    ArgumentUtils.checkNotNullNorBlank(keystorePathname, "keystorePathname");
    try {
      keystore = KeyStore.getInstance(keystoreType);

      File store = new File(keystorePathname);
      if (store.exists()) {
        in = new FileInputStream(keystorePathname);
      } else {
        in = null;
      }

      keystore.load(in, keystorePassword.toCharArray()); // create new one if
      // null
      if (in != null) {
        in.close();
        in = null;
      } else {
        throw new FileNotFoundException("File not found " + keystorePathname);
      }
      LOG.info("Keystore " + keystorePathname + " loaded");
    } catch (KeyStoreException e) {
      throw new GeneralSecurityException("getInstance(" + keystoreType + ") error", e);
    } catch (FileNotFoundException ex) {
      throw new GeneralSecurityException("Couldn't find truststore " + keystorePathname, ex);
    } catch (IOException ex) {
      throw new GeneralSecurityException(ex.getMessage(), ex);
    } catch (NoSuchAlgorithmException ex) {
      throw new GeneralSecurityException(ex.getMessage(), ex);
    } catch (CertificateException ex) {
      throw new GeneralSecurityException("Certificate error", ex);
    } finally {
      try {
        if (in != null) {
          in.close();
        }
      } catch (IOException ex) {
        throw new GeneralSecurityException(ex.getMessage(), ex);
      }
    }
    return keystore;
  }

  /**
   * Returns the {@link java.security.cert.X509Certificate} object for the provided keystorePathname, keystorePassword
   * keystoreType and certificateAlias values. <br>
  * <b>Note:</b><br>  TODO address this issue with AliasForcingKeyManager
   * The provided keystore must only contain one private key entry
   *
   * @param keystoreType     the type of keystore  <br>
   *                         <b>See Java Cryptography Architecture API Specification for information about standard keystore types</b>.
   * @param keystorePassword the password used to check the integrity of the keystore,
   *                         the password used to unlock the keystore
   * @param keystorePathname the abstract pathname string
   * @param certificateAlias the alias name of the certificate
   * @return Null or the {@link java.security.cert.X509Certificate} from the provided private Keystore.
   * @throws {@link java.security.GeneralSecurityException}
   */
  public static X509Certificate getSigningCertificate(final String keystoreType, final String keystorePassword,
                                                      final String keystorePathname, final String certificateAlias)
    throws GeneralSecurityException {
    //Argument checks performed at loadKeyStore and getSigningCertifiicate methods.
    KeyStore localKeystore = KeystoreUtil.loadKeyStore(keystoreType,
      keystorePassword, keystorePathname);
    return getSigningCertificate(localKeystore, certificateAlias);
  }

  /**
   * Returns the {@link X509Certificate} object for the provided keystore and certificateAlias values.
   *
   * @param keystore         the type of keystore  <br>
   *                         <b>See Java Cryptography Architecture API Specification for information about standard keystore types</b>.
   * @param certificateAlias the alias name of the certificate
   * @return the signing certificate as {@link java.security.cert.X509Certificate}
   * @throws GeneralSecurityException
   */
  public static X509Certificate getSigningCertificate(final KeyStore keystore, final String certificateAlias)
    throws GeneralSecurityException {
    ArgumentUtils.checkNotNull(keystore, "keystore");
    ArgumentUtils.checkNotNullNorBlank(certificateAlias, "certificateAlias");
    // Obtain the public certificate key as X509Certificate
    try {
      Certificate certificate = keystore.getCertificate(certificateAlias);
      return (certificate != null) ? (X509Certificate) certificate : null;
    } catch (KeyStoreException e) {
      throw new GeneralSecurityException(e);
    }
  }

  /**
   * Returns the {@link java.security.PrivateKey} object for the provided keystorepathname, keystorePassword and
   * privateKeyAlias values.
   *
   * @param keystoreType     the type of keystore  <br>
   *                         <b>See Java Cryptography Architecture API Specification for information about standard keystore types</b>.
   * @param keystorePassword the password used to check the integrity of the keystore,
   *                         the password used to unlock the keystore
   * @param keystorePathname the abstract pathname string
   * @param privateKeyAlias  the alias name of the privateKey.
   * @return the signing key as {@link java.security.PrivateKey}
   * @throws GeneralSecurityException if keystoreType, KeystorePathname, privateKeyAlias doesnot exists or invalid.
   */
  public static PrivateKey getSigningPrivateKey(final String keystoreType, final String keystorePassword,
                                                final String keystorePathname, final String privateKeyAlias)
    throws GeneralSecurityException {
    KeyStore localKeystore = KeystoreUtil.loadKeyStore(keystoreType,
      keystorePassword, keystorePathname);
    // Obtain the signing certificate as PrivateKey entry
    return getSigningPrivateKey(localKeystore, keystorePassword, privateKeyAlias);
  }

  /**
   * Returns the {@link PrivateKey} object for the provided keystore, privateKeyAlias, keystorePassword values.
   *
   * @param keystore         keystore object of type {@link java.security.KeyStore}
   * @param keystorePassword the password for recovering the key from the keystore
   * @param privateKeyAlias  the alias name of the privateKey.
   * @return the requested key, or null if the given alias does not exist
   *         or does not identify a key-related entry.
   * @throws GeneralSecurityException if keystore, privateKeyAlias or keystorePassword deosnot exists or invalid.
   */
  public static PrivateKey getSigningPrivateKey(final KeyStore keystore, final String keystorePassword,
                                                final String privateKeyAlias)
    throws GeneralSecurityException {
    ArgumentUtils.checkNotNull(keystore, "keystore");
    ArgumentUtils.checkNotNullNorBlank(privateKeyAlias, "privateKeyAlias");
    ArgumentUtils.checkNotNullNorBlank(keystorePassword, "keystorePassword");
    ArgumentUtils.checkNotNull(keystore.containsAlias(privateKeyAlias), "keystore.containsAlias(privateKeyAlias)");
    // Obtains the private Key as PrivateKey
    try {
      Key key = keystore.getKey(privateKeyAlias, keystorePassword
        .toCharArray());
      return (key != null) ? ((PrivateKey) key) : null;
    } catch (KeyStoreException e) {
      throw new GeneralSecurityException(e);
    } catch (NoSuchAlgorithmException e) {
      throw new GeneralSecurityException(e);
    } catch (UnrecoverableKeyException e) {
      throw new GeneralSecurityException(e);
    }
  }

  public static SSLSocketFactory getSslSocketFactory(String privateKeyStoreType,
                                                     String privateKeyStoreFile,
                                                     String privateKeyStorePassword,
                                                     String privateKeyPassword,
                                                     String privateKeyAlias,
                                                     String trustStoreType,
                                                     String trustStoreFile,
                                                     String trustStorePassword)
    throws GeneralSecurityException, IOException {

    KeyStore privateKeyStore = loadKeyStore(privateKeyStoreType, privateKeyStorePassword, privateKeyStoreFile);
    KeyStore trustStore = loadKeyStore(trustStoreType, trustStorePassword, trustStoreFile);

    // Check private key can be retrieved

    checkPrivateKey(privateKeyStore, privateKeyPassword.toCharArray(), privateKeyAlias);

    // Build the key managers.
    final KeyManagerFactory kmFactory = KeyManagerFactory.getInstance(
      KeyManagerFactory.getDefaultAlgorithm());
    kmFactory.init(privateKeyStore, privateKeyPassword.toCharArray());
    final KeyManager[] keyManagers = kmFactory.getKeyManagers();
    for (int i = 0; i < keyManagers.length; i++) {
      if (keyManagers[i] instanceof X509KeyManager) {
        keyManagers[i] = new AliasForcingX509KeyManager((X509KeyManager) keyManagers[i], privateKeyAlias);
      }
    }

    // Build the trust managers.
    final TrustManagerFactory tmFactory = TrustManagerFactory.getInstance(
      TrustManagerFactory.getDefaultAlgorithm());
    tmFactory.init(trustStore);
    final TrustManager[] trustManagers = tmFactory.getTrustManagers();

    // Build the SSLSocketFactory.
    final SSLContext context = SSLContext.getInstance("TLS");

    context.init(keyManagers, trustManagers, null);

    return context.getSocketFactory();
  }

  /*
  * Check that there is a private key in the key store with the given alias that can be retrieved using the given
  * key password.
  */
  private static void checkPrivateKey(final KeyStore keyStore, final char[] keyPassword, final String keyAlias)
    throws GeneralSecurityException {
    // Check key store has alias
    if (!keyStore.containsAlias(keyAlias)) {
      final String errorMsg = "The SSLSocketFactory keystore doesn't have key alias '" + keyAlias + "'.";
      throw new GeneralSecurityException(errorMsg);
    }

    // Check key store has private key entry
    if (!keyStore.isKeyEntry(keyAlias)) {
      final String errorMsg = "The SSLSocketFactory keystore doesn't have a private key for alias '" + keyAlias + "'.";
      throw new GeneralSecurityException(errorMsg);
    }

    // Check password works
    try {
      keyStore.getKey(keyAlias, keyPassword);
    } catch (final UnrecoverableKeyException e) {
      final String errorMsg = "Couldn't recover the private key in the SSLSocketFactory keystore."
        + " The most likely reason is that the key password is wrong.";
      throw new GeneralSecurityException(errorMsg, e);
    }
  }

  private static class AliasForcingX509KeyManager implements X509KeyManager {

    private final X509KeyManager baseKM;
    private final String keyAlias;

    public AliasForcingX509KeyManager(final X509KeyManager keyManager, final String keyAlias) {
      this.baseKM = keyManager;
      this.keyAlias = keyAlias;
    }

    public String chooseClientAlias(final String[] keyType,
                                    final Principal[] issuers,
                                    final Socket socket) {
      return this.keyAlias;
    }

    public String chooseServerAlias(final String keyType,
                                    final Principal[] issuers,
                                    final Socket socket) {
      return this.baseKM.chooseServerAlias(keyType, issuers, socket);
    }

    public X509Certificate[] getCertificateChain(final String alias) {
      return this.baseKM.getCertificateChain(alias);
    }

    public String[] getClientAliases(final String keyType, final Principal[] issuers) {
      return this.baseKM.getClientAliases(keyType, issuers);
    }

    public PrivateKey getPrivateKey(final String alias) {
      return this.baseKM.getPrivateKey(alias);
    }

    public String[] getServerAliases(final String keyType, final Principal[] issuers) {
      return this.baseKM.getServerAliases(keyType, issuers);
    }
  }
}
