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
package au.gov.nehta.vendorlibrary.clinicalpackage.util;

import au.gov.nehta.vendorlibrary.clinicalpackage.enums.FormatTypes;

import java.util.List;
import java.util.Map;

/**
 * Thrown to indicate that a {@link au.gov.nehta.vendorlibrary.clinicalpackage.core.Member} has been passed an illegal or inappropriate argument.
 */
public class MemberException extends RuntimeException {

  /**
   * Exception message.
   */
  private static final String MESSAGE = "Failed to build member.";

  /**
   * Variables and their errors.
   */
  private final Map<String, List<String>> errorMap;

  /**
   * Constructs a <code>MemberException</code> with the supplied errors.
   *
   * @param errorMap the errors (type {@link Map}) causing the exception.
   */
  public MemberException(Map<String, List<String>> errorMap) {
    super(MESSAGE);
    this.errorMap = errorMap;
  }

  /**
   * String representation of the exception's contents.
   *
   * @return formatted member exception (type String).
   */
  @Override
  public final String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append(String.format(FormatTypes.ERROR_HEADER.getStructure(), MemberException.class.getSimpleName(), super.getMessage()));

    for (Map.Entry<String, List<String>> entry : errorMap.entrySet()) {

      sb.append(String.format("%s:\n", entry.getKey()));

      // Next, we need to retrieve the list of reasons why that particular variable failed validation.
      List<String> variableErrors = entry.getValue();

      for (String variableError : variableErrors) {
        sb.append(String.format("\t%s\n", variableError));
      }
    }

    return sb.toString();
  }
}
