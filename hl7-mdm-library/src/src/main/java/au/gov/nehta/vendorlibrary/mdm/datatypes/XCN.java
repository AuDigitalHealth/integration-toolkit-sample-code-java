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
 * {@link XCN}
 * Encapsulates an HL7 V2 Extended Person Name XCN identifier.
 */
public final class XCN {

  /**
   * XCN string component count.
   */
  private static final int XCN_COMPONENT_COUNT = 13;

  /**
   * Identifier.
   */
  private final String identifier;

  /**
   * Family name.
   */
  private final String familyName;

  /**
   * Given name.
   */
  private final String givenName;

  /**
   * Middle initial or name.
   */
  private final String middleInitialOrName;

  /**
   * Suffix.
   */
  private final String suffix;

  /**
   * Prefix.
   */
  private final String prefix;

  /**
   * Assigning authority.
   */
  private final HD assigningAuthority;

  /**
   * Identifier type code.
   */
  private final String identifierTypeCode;

  /**
   * Private constructor to set {@link XCN} instance variables.
   *
   * @param builder Builder to construct {@link XCN} from.
   */
  private XCN(Builder builder) {
    identifier = builder.identifier;
    familyName = builder.familyName;
    givenName = builder.givenName;
    middleInitialOrName = builder.middleInitialOrName;
    suffix = builder.suffix;
    prefix = builder.prefix;
    assigningAuthority = builder.assigningAuthority;
    identifierTypeCode = builder.identifierTypeCode;
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
   * Retrieve family name.
   *
   * @return Family name.
   */
  public String getFamilyName() {
    return familyName;
  }

  /**
   * Retrieve given name.
   *
   * @return Given name.
   */
  public String getGivenName() {
    return givenName;
  }

  /**
   * Retrieve middle initial or name.
   *
   * @return Middle initial or name.
   */
  public String getMiddleInitialOrName() {
    return middleInitialOrName;
  }

  /**
   * Retrieve suffix.
   *
   * @return Suffix.
   */
  public String getSuffix() {
    return suffix;
  }

  /**
   * Retrieve prefix.
   *
   * @return Prefix.
   */
  public String getPrefix() {
    return prefix;
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
   * Parse {@link XCN} from string.
   *
   * @param parseValue String to be parsed (not null).
   * @return {@link XCN}
   */
  public static XCN parse(String parseValue) {
    ArgumentUtils.checkNotNull(parseValue, "parseValue");

    // Split string in to components.
    List<String> values = new ArrayList<String>(Arrays.asList(parseValue.split("\\" + MDMConstants.VALUE_SEPARATOR)));

    // Pad empty values.
    while (values.size() < XCN_COMPONENT_COUNT) {
      values.add("");
    }

    if (values.size() == XCN_COMPONENT_COUNT) {
      try {
        return new Builder()
          .identifier(values.get(0))
          .familyName(values.get(1))
          .givenName(values.get(2))
          .middleInitialOrName(values.get(3))
          .suffix(values.get(4))
          .prefix(values.get(5))
          .assigningAuthority(HD.parse(values.get(8)))
          .identifierTypeCode(values.get(12))
          .build();
      } catch (MDMValidationException e) {
        throw new IllegalArgumentException("Unable to parse XCN", e);
      }
    } else {
      throw new IllegalArgumentException("Unable to parse XCN - Invalid number of value components:\n\t"
        + "Actual: "
        + values.size()
        + "\n\tExpected: "
        + XCN_COMPONENT_COUNT
      );
    }
  }

  /**
   * Outputs an {@link XCN} in the following format:
   * <p/>
   * identifier^familyName^givenName^middleInitialOrName^^suffix^prefix^^^assigningAuthority
   * <p/>
   * Variables that are not set are denoted by an empty string.
   * <p/>
   * Trailing '^' symbols are trimmed. For example:
   * <p/>
   * 'identifier^familyName^^^^^^^' becomes 'identifier^familyName'
   *
   * @return Formatted string.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(identifier);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(familyName);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(givenName);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(middleInitialOrName);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(suffix);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(prefix);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(assigningAuthority.toString());
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(identifierTypeCode);
    return sb.toString().replaceAll(String.format("\\%s*$", MDMConstants.VALUE_SEPARATOR), "");
  }

  /**
   * {@link Builder}
   * Build class used to construct and validate an {@link XCN} object.
   */
  public static final class Builder {

    private String identifier;
    private String familyName;
    private String givenName;
    private String middleInitialOrName;
    private String suffix;
    private String prefix;
    private HD assigningAuthority;
    private String identifierTypeCode;

    /**
     * Default constructor.
     */
    public Builder() {
      this.identifier = "";
      this.familyName = "";
      this.givenName = "";
      this.middleInitialOrName = "";
      this.suffix = "";
      this.prefix = "";
      this.identifierTypeCode = "";
    }

    /**
     * Set the identifier.
     *
     * @param value Identifier (not null).
     * @return {@link Builder}
     */
    public Builder identifier(String value) {
      this.identifier = value;
      return this;
    }

    /**
     * Set the family name (not null).
     *
     * @param value Family name.
     * @return {@link Builder}
     */
    public Builder familyName(String value) {
      this.familyName = value;
      return this;
    }

    /**
     * Set the given name.
     *
     * @param value Given name (not null).
     * @return {@link Builder}
     */
    public Builder givenName(String value) {
      this.givenName = value;
      return this;
    }

    /**
     * Set the middle initial/name.
     *
     * @param value Middle initial/name (not null).
     * @return {@link Builder}
     */
    public Builder middleInitialOrName(String value) {
      this.middleInitialOrName = value;
      return this;
    }

    /**
     * Set the suffix.
     *
     * @param value Suffix (not null).
     * @return {@link Builder}
     */
    public Builder suffix(String value) {
      this.suffix = value;
      return this;
    }

    /**
     * Set the prefix.
     *
     * @param value Prefix (not null).
     * @return {@link Builder}
     */
    public Builder prefix(String value) {
      this.prefix = value;
      return this;
    }

    /**
     * Set the assigning authority.
     *
     * @param value Assigning authority (not null).
     * @return {@link Builder}
     */
    public Builder assigningAuthority(HD value) {
      this.assigningAuthority = value;
      return this;
    }

    /**
     * Set the identifier type code.
     *
     * @param value Identifier type code (not null).
     * @return {@link Builder}
     */
    public Builder identifierTypeCode(String value) {
      this.identifierTypeCode = value;
      return this;
    }

    /**
     * Build {@link XCN} and validate necessary fields.
     *
     * @return {@link XCN}
     * @throws MDMValidationException Thrown when validation fails.
     */
    public XCN build() throws MDMValidationException {

      Map<String, List<String>> failedVariables = new HashMap<String, List<String>>();

      failedVariables.putAll(MDMValidation.confirmNotNull("identifier", identifier, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("familyName", familyName, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("givenName", givenName, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("middleInitialOrName", middleInitialOrName, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("suffix", suffix, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("prefix", prefix, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("assigningAuthority", assigningAuthority, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("identifierTypeCode", identifierTypeCode, true));

      // Confirm whether any variables have failed.
      if (!failedVariables.isEmpty()) {
        throw new MDMValidationException("Invalid XCN", failedVariables);
      }

      return new XCN(this);
    }
  }
}
