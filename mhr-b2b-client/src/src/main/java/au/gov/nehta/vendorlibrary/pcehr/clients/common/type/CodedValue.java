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

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Represents a coded value.
 */
public final class CodedValue implements Comparable<CodedValue> {

  private String codeSystem;
  private String codeSystemOid; 
  private String value;
  private String displayName;


  /**
   * Default constructor.
   *
   * @param codeSystem    code system.
   * @param codeSystemOid code system OID.
   * @param value         code value.
   * @param displayName   code display name.
   */
  public CodedValue(String codeSystem, String codeSystemOid, String value, String displayName) {
    this.codeSystem = codeSystem;
    this.codeSystemOid = codeSystemOid;
    this.value = value;
    this.displayName = displayName;
  }

  /**
   * Constructor.
   *
   * @param codeSystem  coded value's code system.
   * @param value       coded value's String value.
   * @param displayName coded value's display name.
   */
  public CodedValue(String codeSystem, String value, String displayName) {
    this(codeSystem, null, value, displayName);
  }

  /**
   * Constructor.
   *
   * @param codeSystem coded value's code system.
   * @param value      coded value's String value.
   */
  public CodedValue(String codeSystem, String value) {
    this(codeSystem, null, value, null);
  }

  /**
   * Returns the value of 'codeSystem'.
   *
   * @return value of codeSystem
   */
  public String getCodeSystem() {
    return this.codeSystem;
  }
  
  /**
   * @return the codeSystemOid
   */
  public String getCodeSystemOid() {
      return codeSystemOid;
  }

  /**
   * Returns the value of 'value'.
   *
   * @return value of value
   */
  public String getValue() {
    return this.value;
  }

  /**
   * Returns the value of 'displayName'.
   *
   * @return value of displayName
   */
  public String getDisplayName() {
    return this.displayName;
  }

  /**
   * Confirm two objects are equal.
   *
   * @param obj Object to compare.
   * @return true/false.
   * @see java.lang.Comparable#equals(Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof CodedValue) {
      CodedValue o = (CodedValue) obj;
      return new EqualsBuilder()
        .append(this.codeSystem, o.codeSystem)
        .append(this.value, o.value)
//        .append(this.displayName, o.displayName)
        .isEquals();
    }
    return false;
  }

  /**
   * Compare two objects' content is equal.
   *
   * @param o CodedValue object to compare to.
   * @return int representing comparison result.
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(CodedValue o) {
    return new CompareToBuilder()
      .append(this.codeSystem, o.codeSystem)
      .append(this.value, o.value)
//      .append(this.displayName, o.displayName)
      .toComparison();
  }

  /**
   * Hash code override.
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return new HashCodeBuilder()
      .append(this.codeSystem)
      .append(this.value)
//      .append(this.displayName)
      .toHashCode();
  }
}
