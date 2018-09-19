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
import au.gov.nehta.vendorlibrary.mdm.enums.CountryCode;
import au.gov.nehta.vendorlibrary.mdm.util.MDMConstants;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidation;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link XAD}
 * Encapsulates an extended address.
 */
public class XAD {

  /**
   * XAD string component count.
   */
  private static final int XAD_COMPONENT_COUNT = 6;

  /**
   * Street address - first line.
   */
  private final String firstLine;

  /**
   * Street address - second line.
   */
  private final String secondLine;

  /**
   * Street Address - city.
   */
  private final String city;

  /**
   * Street address - state.
   */
  private final String state;

  /**
   * Street address - post code.
   */
  private final int postCode;

  /**
   * Street address - country code.
   */
  private final String country;

  /**
   * Private constructor to set {@link XAD} instance variables.
   *
   * @param builder {@link Builder} to construct {@link XAD} from.
   */
  private XAD(Builder builder) {
    firstLine = builder.firstLine;
    secondLine = builder.secondLine;
    city = builder.city;
    state = builder.state;
    postCode = builder.postCode;
    country = builder.country;
  }

  /**
   * Retrieve street address first line.
   *
   * @return street address first line.
   */
  public String getFirstLine() {
    return firstLine;
  }

  /**
   * Retrieve street address second line.
   *
   * @return street address second line.
   */
  public String getSecondLine() {
    return secondLine;
  }

  /**
   * Retrieve street address city.
   *
   * @return city.
   */
  public String getCity() {
    return city;
  }

  /**
   * Retrieve street address state.
   *
   * @return state.
   */
  public String getState() {
    return state;
  }

  /**
   * Retrieve street address post code.
   *
   * @return post code.
   */
  public int getPostCode() {
    return postCode;
  }

  /**
   * Retrieve street address country.
   *
   * @return country.
   */
  public String getCountry() {
    return country;
  }

  /**
   * Parse {@link XAD} from string.
   *
   * @param parseValue String to be parsed (not null).
   * @return {@link XAD}
   */
  public static XAD parse(String parseValue) {
    ArgumentUtils.checkNotNull(parseValue, "parseValue");

    // Split string in to components.
    List<String> values = new ArrayList<String>(Arrays.asList(parseValue.split("\\" + MDMConstants.VALUE_SEPARATOR)));

    // Pad empty values.
    while (values.size() < XAD_COMPONENT_COUNT) {
      values.add("");
    }

    if (values.size() == XAD_COMPONENT_COUNT) {
      try {
        return new Builder()
          .firstLine(values.get(0))
          .secondLine(values.get(1))
          .city(values.get(2))
          .state(values.get(3))
          .postCode(Integer.parseInt(values.get(4)))
          .country(values.get(5))
          .build();
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Unable to parse XAD - post code value is not a valid integer", e);
      } catch (MDMValidationException e) {
        throw new IllegalArgumentException("Unable to parse XAD", e);
      }
    } else {
      throw new IllegalArgumentException("Unable to parse XAD - Invalid number of value components:\n\t"
        + "Actual: "
        + values.size()
        + "\n\tExpected: "
        + XAD_COMPONENT_COUNT
      );
    }
  }

  /**
   * Outputs an {@link XAD} in the following format:
   * <p/>
   * firstLine^secondLine^city^state^postCode^country
   * <p/>
   * Variables that are not set are denoted by an empty string.
   * <p/>
   * Trailing '^' symbols are trimmed. For example:
   * <p/>
   * 'firstLine^^city^state^postCode^' becomes 'firstLine^^city^state^postCode'
   *
   * @return Formatted string.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(firstLine);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(secondLine);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(city);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(state);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(postCode);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(country);
    return sb.toString().replaceAll(String.format("\\%s*$", MDMConstants.VALUE_SEPARATOR), "");
  }

  /**
   * {@link Builder}
   * Build class used to construct and validate an {@link XAD} object.
   */
  public static final class Builder {

    private String firstLine;
    private String secondLine;
    private String city;
    private String state;
    private int postCode;
    private String country;

    /**
     * Default constructor.
     */
    public Builder() {
      this.firstLine = "";
      this.secondLine = "";
      this.city = "";
      this.state = "";
      this.country = "";
    }

    /**
     * Set the first line of the street address.
     *
     * @param value First line (not null).
     * @return {@link Builder}
     */
    public Builder firstLine(String value) {
      this.firstLine = value;
      return this;
    }

    /**
     * Set the second line of the street address.
     *
     * @param value Second line (not null).
     * @return {@link Builder}
     */
    public Builder secondLine(String value) {
      this.secondLine = value;
      return this;
    }

    /**
     * Set the street address city.
     *
     * @param value City (not null).
     * @return {@link Builder}
     */
    public Builder city(String value) {
      this.city = value;
      return this;
    }

    /**
     * Set the street address state.
     *
     * @param value State (not null).
     * @return {@link Builder}
     */
    public Builder state(String value) {
      this.state = value;
      return this;
    }

    /**
     * Set the street address post code.
     *
     * @param value Post code (not null).
     * @return {@link Builder}
     */
    public Builder postCode(int value) {
      this.postCode = value;
      return this;
    }

    /**
     * Set the street address country.
     *
     * @param value Country (not null).
     * @return {@link Builder}
     */
    public Builder country(String value) {
      this.country = value;
      return this;
    }

    /**
     * Build {@link XAD} and validate necessary fields.
     *
     * @return {@link XAD}
     * @throws MDMValidationException Thrown when validation fails.
     */
    public XAD build() throws MDMValidationException {

      Map<String, List<String>> failedVariables = new HashMap<String, List<String>>();

      failedVariables.putAll(MDMValidation.confirmNotNull("firstLine", firstLine, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("secondLine", secondLine, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("city", city, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("state", state, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("postCode", postCode, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("country", country, true));

      // Confirm whether any variables have failed.
      if (!failedVariables.isEmpty()) {
        throw new MDMValidationException("Invalid XAD", failedVariables);
      }

      return new XAD(this);
    }
  }
}
