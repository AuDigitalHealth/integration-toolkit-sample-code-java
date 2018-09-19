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
package au.gov.nehta.vendorlibrary.clinicalpackage.enums;

/**
 * Enumeration of the relevant String formats used in package generation and extraction.
 */
public enum FormatTypes {

  /**
   * Element with a prefixed namespace identifier.
   */
  PREFIXED_ELEMENT("%1$s:%2$s"),

  /**
   * Structure of error.
   */
  ERROR_HEADER("%s: %s\n\n");

  private final String structure;

  private FormatTypes(final String structure) {
    this.structure = structure;
  }

  /**
   * Returns the structure of the format type.
   *
   * @return structure (type String) of the format type.
   */
  public String getStructure() {
    return structure;
  }
}
