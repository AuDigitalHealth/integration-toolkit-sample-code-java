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
package au.gov.nehta.vendorlibrary.pcehr.clients.common.type;

/**
 * CDA document conformance levels.
 */
public enum ConformanceLevels {
  /**
   * Conformance Level - 1A.
   */
  ONE_A("1A"),
  /**
   * Conformance Level - 1B.
   */
  ONE_B("1B"),
  /**
   * Conformance Level - 2.
   */
  TWO("2"),
  /**
   * Conformance Level - 3A.
   */
  THREE_A("3A"),
  /**
   * Conformance Level - 3B.
   */
  THREE_B("3B"),
  /**
   * Conformance Level - Undefined.
   */
  UNDEFINED("Undefined");

  /**
   * Level descriptor.
   */
  private String level;

  /**
   * Constructor.
   *
   * @param level CDA conformance level.
   */
  private ConformanceLevels(final String level) {
    this.level = level;
  }

  /**
   * Retrieve the conformance level.
   *
   * @return conformance level.
   */
  public String getLevel() {
    return level;
  }
}
