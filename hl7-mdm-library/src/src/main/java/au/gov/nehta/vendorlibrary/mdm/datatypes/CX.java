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
import au.gov.nehta.vendorlibrary.mdm.util.MDMConstants;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidation;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link CX}
 * Encapsulates a HL7 V2 CX identifier.
 */
public final class CX {

  /**
   * CX string component count.
   */
  private static final int CX_COMPONENT_COUNT = 5;

  /**
   * Identifier.
   */
  private final String identifier;

  /**
   * Assigning authority.
   */
  private final HD assigningAuthority;

  /**
   * Identifier type code.
   */
  private final String identifierTypeCode;

  /**
   * Private constructor to set {@link CX} instance variables.
   *
   * @param builder {@link Builder} to construct {@link CX} from.
   */
  private CX(Builder builder) {
    this.identifier = builder.identifier;
    this.assigningAuthority = builder.assigningAuthority;
    this.identifierTypeCode = builder.identifierTypeCode;
  }

  /**
   * Retrieve unique identifier.
   *
   * @return Unique identifier.
   */
  public String getIdentifier() {
    return identifier;
  }

  /**
   * Retrieve assigning authority.
   *
   * @return Assigning authority designator.
   */
  public HD getAssigningAuthority() {
    return assigningAuthority;
  }

  /**
   * Retrieve identifier type code.
   *
   * @return Identifier type code.
   */
  public String getIdentifierTypeCode() {
    return identifierTypeCode;
  }


  /**
   * Parse multiple {@link CX} objects from string.
   *
   * @param value     String to be parsed (not null nor blank).
   * @param separator {@link CX} list delimiter (not null or blank).
   * @return List of {@link CX}.
   */
  public static List<CX> parseMultiple(String value, String separator) {
    ArgumentUtils.checkNotNullNorBlank(value, "value");
    ArgumentUtils.checkNotNullNorBlank(separator, "separator");

    List<CX> cxs = new ArrayList<CX>();
    List<String> values = Arrays.asList(value.split(separator));

    for (String rawCx : values) {
      cxs.add(parse(rawCx));
    }
    return cxs;
  }

  /**
   * Outputs a {@link CX} in the following format:
   * <p/>
   * identifier^^^assigningAuthority^identifierTypeCode
   * <p/>
   * Variables that are not set are denoted by an empty string.
   * <p/>
   * Trailing '^' symbols are trimmed. For example:
   * <p/>
   * 'identifier^^^^' becomes 'identifier'
   *
   * @return Formatted string.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(identifier);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(assigningAuthority.toString());
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(identifierTypeCode);
    return sb.toString().replaceAll(String.format("\\%s*$", MDMConstants.VALUE_SEPARATOR), "");
  }

  /**
   * Parse {@link CX} from string.
   *
   * @param parseValue String to be parsed (not null).
   * @return {@link CX}
   */
  public static CX parse(String parseValue) {
    ArgumentUtils.checkNotNull(parseValue, "parseValue");

    // Split string in to components.
    List<String> values = new ArrayList<String>(Arrays.asList(parseValue.split("\\" + MDMConstants.VALUE_SEPARATOR)));

    // Pad empty values.
    while (values.size() < CX_COMPONENT_COUNT) {
      values.add("");
    }

    if (values.size() == CX_COMPONENT_COUNT) {
      try {
        return new Builder()
          .identifier(values.get(0))
          .assigningAuthority(HD.parse(values.get(3)))
          .identifierTypeCode(values.get(4))
          .build();
      } catch (MDMValidationException e) {
        throw new IllegalArgumentException("Unable to parse CX", e);
      }
    } else {
      throw new IllegalArgumentException("Unable to parse CX - Invalid number of value components:\n\t"
        + "Actual: "
        + values.size()
        + "\n\tExpected: "
        + CX_COMPONENT_COUNT
      );
    }
  }

  /**
   * {@link Builder}
   * Build class used to construct and validate a {@link CX} object.
   */
  public static final class Builder {

    private String identifier;
    private HD assigningAuthority;
    private String identifierTypeCode;

    /**
     * Default constructor.
     */
    public Builder() {
      this.identifier = "";
      this.identifierTypeCode = "";
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
     * Set assigning authority.
     *
     * @param value {@link HD} (not null).
     * @return {@link Builder}
     */
    public Builder assigningAuthority(HD value) {
      this.assigningAuthority = value;
      return this;
    }

    /**
     * Set identifier type code.
     *
     * @param value Identifier type code (not null).
     * @return {@link Builder}
     */
    public Builder identifierTypeCode(String value) {
      this.identifierTypeCode = value;
      return this;
    }

    /**
     * Build {@link CX} and validate necessary fields.
     *
     * @return {@link CX}
     * @throws MDMValidationException Thrown when validation fails.
     */
    public CX build() throws MDMValidationException {
      Map<String, List<String>> failedVariables = new HashMap<String, List<String>>();

      failedVariables.putAll(MDMValidation.confirmNotNull("identifier", identifier, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("assigningAuthority", assigningAuthority, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("identifierTypeCode", identifierTypeCode, true));

      // Confirm whether any variables have failed.
      if (!failedVariables.isEmpty()) {
        throw new MDMValidationException("Invalid CX", failedVariables);
      }
      return new CX(this);
    }
  }
}
