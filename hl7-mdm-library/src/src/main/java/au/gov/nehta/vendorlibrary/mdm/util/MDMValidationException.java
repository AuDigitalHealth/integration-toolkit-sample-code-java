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

import au.gov.nehta.vendorlibrary.mdm.enums.FormatTypes;

import java.util.List;
import java.util.Map;

/**
 * {@link MDMValidationException}
 * Custom exception used when validating and verifying various aspects of a
 * package.
 */
public final class MDMValidationException extends Exception {

  private final Map<String, List<String>> failMessages;

  /**
   * Constructs a {@link MDMValidationException}.
   *
   * @param message      Summary exception message.
   * @param failMessages {@link Map} of failed variable(s) and the reason(s) that caused their failures.
   */
  public MDMValidationException(
    String message,
    Map<String, List<String>> failMessages
  ) {
    super(message);
    this.failMessages = failMessages;
  }

  /**
   * Returns the size of the failed message list.
   *
   * @return Integer.
   */
  public int size() {
    return failMessages.size();
  }

  /**
   * String representation of the exception's contents.
   *
   * @return String.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append(String.format(
      FormatTypes.ERROR_HEADER.getStructure(),
      MDMValidation.class.getName(),
      super.getMessage()));

    for (Map.Entry<String, List<String>> entry : failMessages.entrySet()) {
      sb.append(entry.getKey());
      sb.append(":\n");

      // Next we need to retrieve the list of reasons why that particular
      // variable failed validation
      List<String> messages = entry.getValue();

      if (!messages.isEmpty()) {

        for (String message : messages) {
          sb.append("\t");
          sb.append(message);
          sb.append("\n");
        }
      }
    }
    return sb.toString();
  }
}
