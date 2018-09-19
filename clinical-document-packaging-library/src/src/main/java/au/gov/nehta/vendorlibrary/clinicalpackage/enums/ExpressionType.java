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
package au.gov.nehta.vendorlibrary.clinicalpackage.enums;

/**
 * {@link ExpressionType}
 * XPath expression types.
 */
public enum ExpressionType {

  /**
   * Slot XPath expression.
   */
  INTEGRITY_CHECK("//hl7:value[@integrityCheck!='' and descendant::hl7:reference[@value!='']]");

  private final String expression;

  private ExpressionType(String structure) {
    this.expression = structure;
  }

  /**
   * Retrieves an XPath expression.
   *
   * @return XPath expression string.
   */
  public String getExpression() {
    return expression;
  }
}
