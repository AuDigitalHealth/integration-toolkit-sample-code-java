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
package au.gov.nehta.vendorlibrary.mdm.segments;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.vendorlibrary.mdm.datatypes.CX;
import au.gov.nehta.vendorlibrary.mdm.datatypes.XAD;
import au.gov.nehta.vendorlibrary.mdm.datatypes.XPN;
import au.gov.nehta.vendorlibrary.mdm.enums.Sex;
import au.gov.nehta.vendorlibrary.mdm.util.MDMConstants;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidation;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link PID}
 * Encapsulates MDM patient identification type.
 */
public final class PID {

  /**
   * PID segment name.
   */
  private static final String PID_SEGMENT_NAME = "PID";

  /**
   * Expected segment field count.
   */
  private static final int PID_FIELD_COUNT = 12;

  /**
   * PID date of birth pattern.
   */
  private static final String PID_DATE_OF_BIRTH_PATTERN = "yyyyMMdd";

  /**
   * PID.1 - Set ID.
   */
  private static final int SET_ID = 1;

  /**
   * PID.3 - List of patient identifiers.<br/>
   * e.g. Medicare + IHI number.
   */
  private final List<CX> patientIdentifiers;

  /**
   * PID.5 - Patient name.
   */
  private final XPN patientName;

  /**
   * PID.7 - Patient date/time of birth.
   */
  private final Date dateTimeOfBirth;

  /**
   * PID.8 - Patient sex.
   */
  private final Sex sex;

  /**
   * PID.11 - Patient address.
   */
  private final XAD patientAddress;

  /**
   * Private constructor to set {@link PID} instance variables.
   *
   * @param builder {@link Builder} to construct {@link PID} from.
   */
  private PID(Builder builder) {
    patientIdentifiers = builder.patientIdentifiers;
    patientName = builder.patientName;
    dateTimeOfBirth = builder.dateTimeOfBirth;
    sex = builder.sex;
    patientAddress = builder.patientAddress;
  }

  /**
   * Retrieve set ID.
   *
   * @return Set ID integer.
   */
  public int getSetId() {
    return SET_ID;
  }

  /**
   * Retrieve patient identifiers.
   *
   * @return List of patient identifiers.
   */
  public List<CX> getPatientIdentifiers() {
    return patientIdentifiers;
  }

  /**
   * Retrieve patient name.
   *
   * @return Patient name string.
   */
  public XPN getPatientName() {
    return patientName;
  }

  /**
   * Retrieve patient date/time of birth.
   *
   * @return Date/time of birth.
   */
  public Date getDateTimeOfBirth() {
    return dateTimeOfBirth;
  }

  /**
   * Retrieve patient sex.
   *
   * @return Sex.
   */
  public Sex getSex() {
    return sex;
  }

  /**
   * Retrieve patient address.
   *
   * @return Patient address string.
   */
  public XAD getPatientAddress() {
    return patientAddress;
  }

  /**
   * Parse {@link PID} segment from string.
   *
   * @param parseValue String to be parsed (not null).
   * @return {@link PID} segment.
   */
  public static PID parse(String parseValue) {
    ArgumentUtils.checkNotNull(parseValue, "parseValue");

    // Extract individual fields.
    List<String> fields = Arrays.asList(parseValue.split("\\" + MDMConstants.FIELD_SEPARATOR));

    if (fields.size() >= PID_FIELD_COUNT) {
      if (fields.get(0).compareTo(PID_SEGMENT_NAME) != 0) {
        throw new IllegalArgumentException(String.format(
          "Unable to parse PID - Segment name is incorrect: \n\tActual Value: %s\n\tExpected Value: %s",
          fields.get(0),
          PID_SEGMENT_NAME
        ));
      }
      try {
        return new Builder()
          .patientIdentifiers(CX.parseMultiple(fields.get(3), MDMConstants.ID_SEPARATOR))
          .patientName(XPN.parse(fields.get(5)))
          .dateTimeOfBirth(new SimpleDateFormat(PID_DATE_OF_BIRTH_PATTERN).parse(fields.get(7)))
          .sex(Sex.valueOf(fields.get(8)))
          .patientAddress(XAD.parse(fields.get(11)))
          .build();
      } catch (MDMValidationException e) {
        throw new IllegalArgumentException("Unable to parse PID", e);
      } catch (ParseException e) {
        throw new IllegalArgumentException(String.format(
          "Unable to parse PID - Invalid date/time pattern supplied:\n\tActual Value: %s\n\tExpected Format: %s",
          fields.get(7),
          PID_DATE_OF_BIRTH_PATTERN
        ));
      }
    } else {
      throw new IllegalArgumentException("Unable to parse PID - Invalid number of fields:\n\t"
        + "Actual: "
        + fields.size()
        + "\n\tExpected: "
        + PID_FIELD_COUNT
      );
    }
  }

  /**
   * Outputs a formatted {@link PID} segment.
   *
   * @return Formatted string.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(PID_SEGMENT_NAME);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(SET_ID);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);

    // Loop through multiple CXs.
    for (CX identifier : patientIdentifiers) {
      sb.append(identifier.toString());
      sb.append(MDMConstants.ID_SEPARATOR);
    }

    // Remove trailing ID separator.
    sb.deleteCharAt(sb.length() - 1);

    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(patientName.toString());
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(new SimpleDateFormat(PID_DATE_OF_BIRTH_PATTERN).format(dateTimeOfBirth));
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(sex);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(patientAddress.toString());
    sb.append(MDMConstants.SEGMENT_TERMINATOR);
    return sb.toString();
  }

  /**
   * {@link Builder}
   * Build class used to construct and validate a {@link PID} object.
   */
  public static final class Builder {

    private List<CX> patientIdentifiers;
    private XPN patientName;
    private Date dateTimeOfBirth;
    private Sex sex;
    private XAD patientAddress;

    /**
     * Set patient identifiers.
     *
     * @param values List of patient identifiers (not null).
     * @return {@link Builder}
     */
    public Builder patientIdentifiers(List<CX> values) {
      this.patientIdentifiers = values;
      return this;
    }

    /**
     * Set patient name.
     *
     * @param value Patient name (not null).
     * @return {@link Builder}
     */
    public Builder patientName(XPN value) {
      this.patientName = value;
      return this;
    }

    /**
     * Set patient date/time of birth.
     *
     * @param value Patient date/time of birth (not null).
     * @return {@link Builder}
     */
    public Builder dateTimeOfBirth(Date value) {
      this.dateTimeOfBirth = value;
      return this;
    }

    /**
     * Set patient address.
     *
     * @param value Patient address (not null).
     * @return {@link Builder}
     */
    public Builder patientAddress(XAD value) {
      this.patientAddress = value;
      return this;
    }

    /**
     * Set patient sex.
     *
     * @param value Patient sex (not null).
     * @return {@link Builder}
     */
    public Builder sex(Sex value) {
      this.sex = value;
      return this;
    }

    /**
     * Build PID and validate necessary fields.
     *
     * @return {@link PID}
     * @throws MDMValidationException Thrown when validation of the PID contents fails.
     */
    public PID build() throws MDMValidationException {

      Map<String, List<String>> failedVariables = new HashMap<String, List<String>>();

      if (!MDMValidation.isNullOrEmpty(patientIdentifiers)) {
        for (CX patientIdentifier : patientIdentifiers) {
          MDMValidation.confirmNotNull("patientIdentifiers - patientIdentifier", patientIdentifier, true);
        }
      } else {
        failedVariables.putAll(MDMValidation.confirmNotNullNorEmpty("patientIdentifiers", patientIdentifiers, true));
      }

      failedVariables.putAll(MDMValidation.confirmNotNull("patientName", patientName, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("dateTimeOfBirth", dateTimeOfBirth, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("sex", sex, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("patientAddress", patientAddress, true));

      // Confirm whether any variables have failed.
      if (!failedVariables.isEmpty()) {
        throw new MDMValidationException("Invalid PID segment", failedVariables);
      }

      return new PID(this);
    }
  }
}
