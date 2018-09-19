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
package au.gov.nehta.vendorlibrary.pcehr.sample.common.constants;

/**
 * Sample security constants used in sample code. These values should be replaced with valid details.
 */
public final class SampleSecurityConstants {

  /**
   * General Keystore - directory.
   */
  public static final String STORE_DIR = "/store_dir/";

  /**
   * Private Keystore - password.
   */
  public static final String PRIVATE_KEY_STORE_PASSWORD = "changeit";

  /**
   * Private Keystore - type.
   */
  public static final String PRIVATE_KEY_STORE_TYPE = "JKS";

  /**
   * Private Keystore - file name.
   */
  public static final String PRIVATE_KEY_STORE_FILE = "keystore.jks";

  /**
   * Private Keystore - file path.
   */
  public static final String PRIVATE_KEY_STORE_PATH = String.format("%s%s", STORE_DIR, PRIVATE_KEY_STORE_FILE);

  /**
   * Private Key - password.
   */
  public static final String PRIVATE_KEY_PASSWORD = "changeit";

  /**
   * Private Key - alias.
   */
  public static final String PRIVATE_KEY_ALIAS = "changeit";

  /**
   * Truststore - type.
   */
  public static final String TRUST_STORE_TYPE = "JKS";

  /**
   * Truststore - file name.
   */
  public static final String TRUST_STORE_FILE = "truststore.jks";

  /**
   * Truststore - password.
   */
  public static final String TRUST_STORE_PASSWORD = "changeit";

  /**
   * Truststore - file path.
   */
  public static final String TRUST_STORE_PATH = String.format("%s%s", STORE_DIR, TRUST_STORE_FILE);

  /**
   * Private constructor to prevent instantiation.
   */
  private SampleSecurityConstants() {
  }
}