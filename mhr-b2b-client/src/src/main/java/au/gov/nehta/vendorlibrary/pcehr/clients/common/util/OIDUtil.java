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
package au.gov.nehta.vendorlibrary.pcehr.clients.common.util;

import org.apache.commons.lang.Validate;

import java.math.BigInteger;
import java.util.UUID;

/**
 * Provides static functions to manipulate ASN.1 OIDs and UUIDs/GUIDs.
 */
public final class OIDUtil {

  /**
   * Hexadecimal radix.
   */
  static final int HEX_RADIX = 16;

  /**
   * Default OID prefix.
   */
  static final String OID_PREFIX = "2.25";

  /**
   * UUID prefix.
   */
  private static final String UUID_PREFIX = "urn:uuid:";

  /**
   * Replacement string value.
   */
  private static final String REPLACEMENT = "";

  /**
   * UUID separator.
   */
  private static final String SEPERATOR = "-";

  /**
   * Private constructor because class is a utility class.
   */
  private OIDUtil() {
  }

  /**
   * Convert a UUID to OID.
   *
   * @param uuid UUID string to convert.
   * @return OID string.
   */
  public static String convertUUIDToOIDIntegerPair(final String uuid) {
    return convertUUIDToOIDIntegerPair(OID_PREFIX, uuid);
  }

  /**
   * Converts a Universally Unique Identifier (UUID) to an integer and then
   * prepends an ASN.1 OID.
   *
   * @param oid  The OID to prepend to the converted UUID.
   * @param uuid A Universally unique identifier (UUID) to convert, of the format:
   *             <ul>
   *             <li>urn:uuid:a7b7c3b7-4639-43a9-8bb1-7cb8c91216c1</li>
   *             <li>a7b7c3b7-4639-43a9-8bb1-7cb8c91216c1</li>
   *             </ul>
   * @return An OID prepended to the converted UUID. It will be in the format
   *         <p/>
   *         <pre>{OID}.{IntegerUUID}</pre>
   */
  public static String convertUUIDToOIDIntegerPair(final String oid, final String uuid) {

    Validate.notEmpty(oid);
    Validate.notEmpty(uuid);

    // Strip prefix and ensure UUID can be parsed.
    validateUUID(uuid.replace(UUID_PREFIX, REPLACEMENT));

    // Strip out all non-alphanumeric characters and "urn:uuid" prefix.
    String cleanUuid = cleanUuid(uuid);

    // Split the UUID into 4 parts of 8 characters and then convert from
    // hexadecimal to integer using Horner's Method.
    BigInteger result = new BigInteger(String.valueOf(0));

    for (int i = 0; i < cleanUuid.length(); i++) {
      result = result.multiply(new BigInteger(String.valueOf(HEX_RADIX))).add(
        new BigInteger(String.valueOf(Integer.parseInt(String.valueOf(cleanUuid.charAt(i)), HEX_RADIX)))
      );
    }

    // Prepend the OID with a period and then to the integer UUID.
    return oid + '.' + result;
  }

  /**
   * Helper method to do basic validation on a String to confirm it can be
   * converted to a valid UUID.
   *
   * @param uuid UUID String.
   */
  private static void validateUUID(final String uuid) {
    UUID.fromString(uuid);
  }

  /**
   * Helper method to clean expected but unnecessary characters from the UUID string.
   *
   * @param uuid UUID String.
   * @return Cleaned UUID string.
   */
  private static String cleanUuid(final String uuid) {
    return uuid.replace(UUID_PREFIX, REPLACEMENT).replace(SEPERATOR, REPLACEMENT);
  }
}
