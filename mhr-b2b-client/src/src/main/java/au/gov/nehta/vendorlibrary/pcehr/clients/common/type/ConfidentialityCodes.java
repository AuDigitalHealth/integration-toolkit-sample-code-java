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
 * Confidentiality codes employed in document metadata.
 */
public enum ConfidentialityCodes {

  /**
   * Not applicable.
   */
  NA("PCEHR_DocAccessLevels", "GENERAL", "NA");

  private String codingSystem;
  private String conceptCode;
  private String conceptName;

  private ConfidentialityCodes(String codingSystem, String conceptCode, String conceptName) {
    this.codingSystem = codingSystem;
    this.conceptCode = conceptCode;
    this.conceptName = conceptName;
  }

  /**
   * Retrieve concept code.
   *
   * @return Concept code string.
   */
  private String getConceptCode() {
    return conceptCode;
  }

  public CodedValue getCodedValue() {
    return new CodedValue(codingSystem, conceptCode, conceptName);
  }

  /**
   * Retrieves a {@link ConfidentialityCodes} based on a supplied concept code.
   *
   * @param conceptCode Concept to search for.
   * @return Corresponding {@link ConfidentialityCodes} or null.
   */
  public static ConfidentialityCodes findByConceptCode(String conceptCode) {
    for (ConfidentialityCodes v : values()) {
      if (v.getConceptCode().equals(conceptCode)) {
        return v;
      }
    }
    return null;
  }
}
