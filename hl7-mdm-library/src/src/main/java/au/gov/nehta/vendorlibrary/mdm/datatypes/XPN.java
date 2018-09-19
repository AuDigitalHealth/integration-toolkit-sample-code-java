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
 * {@link XPN}
 * Encapsulates an MDM extended person name XPN.
 */
public final class XPN {

  /**
   * HD string component count.
   */
  private static final int XPN_COMPONENT_COUNT = 5;

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
   * Private constructor to set {@link XPN} instance variables.
   *
   * @param builder {@link Builder} to construct {@link XPN} from.
   */
  private XPN(Builder builder) {
    familyName = builder.familyName;
    givenName = builder.givenName;
    middleInitialOrName = builder.middleInitialOrName;
    suffix = builder.suffix;
    prefix = builder.prefix;
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
   * Outputs an {@link XPN} in the following format:
   * <p/>
   * familyName^givenName^middleInitialOrName^suffix^prefix
   * <p/>
   * Variables that are not set are denoted by an empty string.
   * <p/>
   * Trailing '^' symbols are trimmed. For example:
   * <p/>
   * 'familyName^^^^' becomes 'familyName'
   *
   * @return Formatted string.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(familyName);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(givenName);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(middleInitialOrName);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(suffix);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(prefix);
    return sb.toString().replaceAll(String.format("\\%s*$", MDMConstants.VALUE_SEPARATOR), "");
  }

  /**
   * Parse {@link XPN} from string.
   *
   * @param value String to be parsed (not null).
   * @return {@link XPN}
   */
  public static XPN parse(String value) {
    ArgumentUtils.checkNotNull(value, "value");

    // Split string in to components.
    List<String> values = new ArrayList<String>(Arrays.asList(value.split("\\" + MDMConstants.VALUE_SEPARATOR)));

    // Pad empty values.
    while (values.size() < XPN_COMPONENT_COUNT) {
      values.add("");
    }

    if (values.size() == XPN_COMPONENT_COUNT) {
      try {
        return new Builder()
          .familyName(values.get(0))
          .givenName(values.get(1))
          .middleInitialOrName(values.get(2))
          .suffix(values.get(3))
          .prefix(values.get(4))
          .build();
      } catch (MDMValidationException e) {
        throw new IllegalArgumentException("Unable to parse XPN", e);
      }
    } else {
      throw new IllegalArgumentException("Unable to parse XPN - Invalid number of value components:\n\t"
        + "Actual: "
        + values.size()
        + "\n\tExpected: "
        + XPN_COMPONENT_COUNT
      );
    }
  }

  /**
   * {@link Builder}
   * Build class used to construct and validate an {@link XPN} object.
   */
  public static final class Builder {

    private String familyName;
    private String givenName;
    private String middleInitialOrName;
    private String suffix;
    private String prefix;

    /**
     * Default constructor.
     */
    public Builder() {
      this.familyName = "";
      this.givenName = "";
      this.middleInitialOrName = "";
      this.suffix = "";
      this.prefix = "";
    }

    /**
     * Set the family name.
     *
     * @param value Family name (not null).
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
     * Set middle initial/name.
     *
     * @param value Middle initial/name (not null).
     * @return {@link Builder}
     */
    public Builder middleInitialOrName(String value) {
      this.middleInitialOrName = value;
      return this;
    }

    /**
     * Set name suffix.
     *
     * @param value Suffix (not null).
     * @return {@link Builder}
     */
    public Builder suffix(String value) {
      this.suffix = value;
      return this;
    }

    /**
     * Set name prefix.
     *
     * @param value Prefix (not null).
     * @return {@link Builder}
     */
    public Builder prefix(String value) {
      this.prefix = value;
      return this;
    }

    /**
     * Build {@link XPN} and validate necessary fields.
     *
     * @return {@link XPN}
     * @throws MDMValidationException Thrown when validation fails.
     */
    public XPN build() throws MDMValidationException {

      Map<String, List<String>> failedVariables = new HashMap<String, List<String>>();

      failedVariables.putAll(MDMValidation.confirmNotNull("familyName", familyName, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("givenName", givenName, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("middleInitialOrName", middleInitialOrName, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("suffix", suffix, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("prefix", prefix, true));

      // Confirm whether any variables have failed.
      if (!failedVariables.isEmpty()) {
        throw new MDMValidationException("Invalid XPN", failedVariables);
      }

      return new XPN(this);
    }
  }
}