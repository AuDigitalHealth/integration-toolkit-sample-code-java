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
package au.gov.nehta.vendorlibrary.mdm.enums;

/**
 * {@link CountryCode}
 * Enumeration of country codes (ISO 3166).
 */
public enum CountryCode {

  /**
   * Australia.
   */
  AUSTRALIA("AUS"),

  /**
   * Country code is unknown/unspecified - ie. blank string.
   */
  UNKNOWN("");

  /**
   * Country code.
   */
  private final String code;

  /**
   * Private constructor.
   *
   * @param code Country code.
   */
  private CountryCode(String code) {
    this.code = code;
  }

  /**
   * Retrieve {@link CountryCode} code value.
   *
   * @return Country code.
   */
  public String getCode() {
    return code;
  }

  /**
   * Retrieves a {@link CountryCode} based on a supplied code value.
   *
   * @param code Code to search for.
   * @return Corresponding {@link CountryCode} or UNKNOWN if country
   *         code cannot be retrieved.
   */
  public static CountryCode findByCode(String code) {
    for (CountryCode v : values()) {
      if (v.getCode().equals(code)) {
        return v;
      }
    }
    return UNKNOWN;
  }
}
