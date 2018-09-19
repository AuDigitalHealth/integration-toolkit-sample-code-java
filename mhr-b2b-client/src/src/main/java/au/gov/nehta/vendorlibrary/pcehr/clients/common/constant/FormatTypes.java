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
package au.gov.nehta.vendorlibrary.pcehr.clients.common.constant;

/**
 * {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.FormatTypes}
 * Enum of format strings used with String.Format().
 */
public enum FormatTypes {

  /**
   * Structure of error.
   */
  ERROR_HEADER("%s: %s\n\n");

  /**
   * Contains format string structure.
   */
  private final String structure;

  /**
   * Constructor used to create a FormatTypes enum.
   *
   * @param structure String format structure.
   */
  private FormatTypes(final String structure) {
    this.structure = structure;
  }

  /**
   * Retrieve the relevant string format.
   *
   * @return String.
   */
  public String getStructure() {
    return structure;
  }
}
