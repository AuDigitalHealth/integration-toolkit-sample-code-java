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
package au.gov.nehta.vendorlibrary.mdm.datatypes;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.vendorlibrary.mdm.enums.CodingSystem;
import au.gov.nehta.vendorlibrary.mdm.util.MDMConstants;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidation;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link CE}
 * Encapsulates an HL7 V2 coded element (CE).
 */
public final class CE {

  /**
   * CE string component count.
   */
  private static final int CE_COMPONENT_COUNT = 3;

  /**
   * Identifies element referenced in text component.
   */
  private final String identifier;

  /**
   * Name/description of the element.
   */
  private final String text;

  /**
   * Coding scheme in use by the identifier component.
   */
  private final CodingSystem codingSystem;

  /**
   * Private constructor to set {@link CE} instance variables.
   *
   * @param builder {@link Builder} to construct {@link CE} from.
   */
  private CE(Builder builder) {
    identifier = builder.identifier;
    text = builder.text;
    codingSystem = builder.codingSystem;
  }

  /**
   * Retrieve identifier.
   *
   * @return Identifier.
   */
  public String getIdentifier() {
    return identifier;
  }

  /**
   * Retrieve text description.
   *
   * @return Text.
   */
  public String getText() {
    return text;
  }

  /**
   * Retrieve identifier's coding system.
   *
   * @return Coding system.
   */
  public CodingSystem getCodingSystem() {
    return codingSystem;
  }

  /**
   * Parse {@link CE} from string.
   *
   * @param parseValue String to be parsed (not null).
   * @return {@link CE}
   */
  public static CE parse(String parseValue) {
    ArgumentUtils.checkNotNull(parseValue, "parseValue");

    // Split string in to components.
    List<String> values = new ArrayList<String>(Arrays.asList(parseValue.split("\\" + MDMConstants.VALUE_SEPARATOR)));

    // Pad empty values.
    while (values.size() < CE_COMPONENT_COUNT) {
      values.add("");
    }

    if (values.size() == CE_COMPONENT_COUNT) {
      try {
        return new Builder()
          .identifier(values.get(0))
          .text(values.get(1))
          .codingSystem(CodingSystem.valueOf(values.get(2)))
          .build();
      } catch (MDMValidationException e) {
        throw new IllegalArgumentException("Unable to parse CE", e);
      }
    } else {
      throw new IllegalArgumentException("Unable to parse CE - Invalid number of value components:\n\t"
        + "Actual: "
        + values.size()
        + "\n\tExpected: "
        + CE_COMPONENT_COUNT
      );
    }
  }

  /**
   * Outputs an {@link CE} in the following format:
   * <p/>
   * identifier^text^codingSystem
   * <p/>
   * Variables that are not set are denoted by an empty string.
   * <p/>
   * Trailing '^' symbols are trimmed. For example:
   * <p/>
   * 'identifier^^' becomes 'identifier'
   *
   * @return Formatted string.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(identifier);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(text);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(codingSystem);
    return sb.toString().replaceAll(String.format("\\%s*$", MDMConstants.VALUE_SEPARATOR), "");
  }

  /**
   * {@link Builder}
   * Build class used to construct and validate an {@link CE} object.
   */
  public static final class Builder {

    private String identifier;
    private String text;
    private CodingSystem codingSystem;

    /**
     * Default constructor.
     */
    public Builder() {
      this.identifier = "";
      this.text = "";
    }

    /**
     * Set identifier.
     *
     * @param value Identifier (not null).
     * @return {@link Builder}
     */
    public Builder identifier(String value) {
      this.identifier = value;
      return this;
    }

    /**
     * Set text.
     *
     * @param value Text (not null).
     * @return {@link Builder}
     */
    public Builder text(String value) {
      this.text = value;
      return this;
    }

    /**
     * Set coding system.
     *
     * @param value Coding system (not null).
     * @return {@link Builder}
     */
    public Builder codingSystem(CodingSystem value) {
      this.codingSystem = value;
      return this;
    }

    /**
     * Build {@link CE} and validate necessary fields.
     *
     * @return {@link CE}
     * @throws MDMValidationException Thrown when validation fails.
     */
    public CE build() throws MDMValidationException {

      Map<String, List<String>> failedVariables = new HashMap<String, List<String>>();

      failedVariables.putAll(MDMValidation.confirmNotNull("identifier", identifier, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("text", text, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("codingSystem", identifier, true));

      // Confirm whether any variables have failed.
      if (!failedVariables.isEmpty()) {
        throw new MDMValidationException("Failed to build CE", failedVariables);
      }

      return new CE(this);
    }
  }
}
