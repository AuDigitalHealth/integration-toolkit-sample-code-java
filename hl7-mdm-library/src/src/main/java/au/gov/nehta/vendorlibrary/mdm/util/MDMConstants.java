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
package au.gov.nehta.vendorlibrary.mdm.util;

/**
 * {@link MDMConstants}
 * Helper class to encapsulate library constants.
 */
public final class MDMConstants {

  /**
   * MSH.1 - Field separator.
   */
  public static final String FIELD_SEPARATOR = "|";

  /**
   * Value separator in specific sub-segments.
   */
  public static final String VALUE_SEPARATOR = "^";

  /**
   * ID separator in PID ID list.
   */
  public static final String ID_SEPARATOR = "~";

  /**
   * Carriage return required at the end of each segment string to denote end of segment
   * (and message in the case of the final segment).
   */
  public static final String SEGMENT_TERMINATOR = "\r";

  /**
   * File IO buffer size.
   */
  public static final int BUFFER = 2048;

  /**
   * Private constructor.
   */
  private MDMConstants() {
    // Prevent instantiation.
  }
}
