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
import au.gov.nehta.vendorlibrary.mdm.datatypes.CE;
import au.gov.nehta.vendorlibrary.mdm.enums.DataType;
import au.gov.nehta.vendorlibrary.mdm.enums.Result;
import au.gov.nehta.vendorlibrary.mdm.enums.ValueType;
import au.gov.nehta.vendorlibrary.mdm.util.MDMConstants;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidation;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidationException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link OBX}
 * Encapsulates MDM observation segment.
 */
public final class OBX {

  /**
   * Expected segment field count
   */
  private static final int OBX_FIELD_COUNT = 12;

  /**
   * OBX segment name.
   */
  private static final String OBX_SEGMENT_NAME = "OBX";

  /**
   * OBX.1 - Set ID.
   */
  private static final int SET_ID = 1;

  /**
   * OBX.2 - Value type.
   * <p/>
   * Encapsulated data.
   */
  private static final ValueType VALUE_TYPE = ValueType.ED;

  /**
   * OBX.3 - Observation source package type.
   * <p/>
   * OBX.3.1 - Code.
   * <p/>
   * e.g. Code - root CDA document <b>code</b> value.
   * <p/>
   * OBX.3.2 - Description.
   * <p/>
   * e.g. LOINC description.
   * <p/>
   * OBX.3.3 - Descriptor.
   * <p/>
   * e.g. LN - LOINC descriptor.
   */
  private final CE observationIdentifier;

  //
  //  OBX.5 - Observation value encapsulated data components.
  //

  /**
   * OBX.5.1 - Source application.
   * <p/>
   * <i>Not used.</i>
   */
  private static final String SOURCE_APPLICATION = "";

  /**
   * OBX.5.2 - Data type.
   * <p/>
   * i.e. application.
   * <p/>
   * OBX.5.3 - Data subtype.
   * <p/>
   * i.e. zip.
   */
  private final DataType DATA_TYPE = DataType.ZIP;

  /**
   * OBX.5.4 - Data encoding.<br/>
   * i.e. base64.
   */
  private static final String DATA_ENCODING = "base64";

  /**
   * OBX.5.5 - Encoded data.
   * i.e. BASE64 encoded CDA ZIP package file.
   */
  private final String encodedData;

  /**
   * OBX.11 - Observation result.
   */
  private static final Result OBSERVATION_RESULT = Result.F;

  /**
   * Private constructor to set {@link OBX} instance variables.
   *
   * @param builder {@link Builder} to construct {@link OBX} from.
   */
  private OBX(Builder builder) {
    ArgumentUtils.checkNotNull(builder, "builder");
    observationIdentifier = builder.observationIdentifier;
    encodedData = builder.encodedData;
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
   * Retrieve value type.
   *
   * @return Value type enumerator.
   */
  public ValueType getValueType() {
    return VALUE_TYPE;
  }

  /**
   * Retrieve observation identifier.
   *
   * @return Observation identifier.
   */
  public CE getObservationIdentifier() {
    return observationIdentifier;
  }

  public String getSourceApplication() {
    return SOURCE_APPLICATION;
  }

  /**
   * Retrieve data type/subtype.
   *
   * @return Data type/subtype.
   */
  public DataType getDataType() {
    return DATA_TYPE;
  }

  /**
   * Retrieve data encoding type.
   *
   * @return Data encoding type.
   */
  public String getDataEncoding() {
    return DATA_ENCODING;
  }

  /**
   * Retrieve encoded data.
   *
   * @return Encoded data.
   */
  public String getEncodedData() {
    return encodedData;
  }

  /**
   * Retrieve observation result.
   *
   * @return Observation result enumerator.
   */
  public Result getObservationResult() {
    return OBSERVATION_RESULT;
  }



  /**
   * Outputs an {@link OBX} segment in the following format:
   * <p/>
   * OBX|setId|valueType|observationIdentifier||observationValue||||||observationResult
   *
   * @return Formatted string.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(OBX_SEGMENT_NAME);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(SET_ID);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(VALUE_TYPE);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(observationIdentifier.toString());
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(SOURCE_APPLICATION);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(DATA_TYPE.getDataType());
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(DATA_TYPE.getDataSubType());
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(DATA_ENCODING);
    sb.append(MDMConstants.VALUE_SEPARATOR);
    sb.append(encodedData);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(OBSERVATION_RESULT);
    sb.append(MDMConstants.SEGMENT_TERMINATOR);
    return sb.toString();
  }

  /**
   * Parse {@link OBX} segment from string.
   *
   * @param parseValue String to be parsed (not null).
   * @return Populated {@link OBX} segment.
   */
  public static OBX parse(String parseValue) {
    ArgumentUtils.checkNotNull(parseValue, "parseValue");

    // Extract individual fields.
    List<String> fields = Arrays.asList(parseValue.split("\\" + MDMConstants.FIELD_SEPARATOR));

    if (fields.size() >= OBX_FIELD_COUNT) {
      if (fields.get(0).compareTo(OBX_SEGMENT_NAME) != 0) {
        throw new IllegalArgumentException(String.format(
          "Unable to parse OBX - Segment name is incorrect: \n\tActual Value: %s\n\tExpected Value: %s",
          fields.get(0),
          OBX_SEGMENT_NAME
        ));
      }
      OBX.Builder builder = new Builder()
        .observationIdentifier(CE.parse(fields.get(3)));

      List<String> observationValue = Arrays.asList(fields.get(5).split("\\" + MDMConstants.VALUE_SEPARATOR));

      builder.encodedData(observationValue.get(4));
      try {
        return builder.build();
      } catch (MDMValidationException e) {
        throw new IllegalArgumentException("Unable to parse OBX", e);
      }
    } else {
      throw new IllegalArgumentException(String.format(
        "Unable to parse OBX - Invalid number of fields:\n\tActual: %d\n\tExpected: %d",
        fields.size(),
        OBX_FIELD_COUNT
      ));
    }
  }

  /**
   * {@link Builder}
   * Build class used to construct and validate an {@link OBX} object.
   */
  public static final class Builder {

    private CE observationIdentifier;
    private String encodedData;

    /**
     * Default constructor.
     */
    public Builder() {
      this.encodedData = "";
    }

    /**
     * Set observation identifier.
     *
     * @param value Observation identifier (not null).
     * @return {@link Builder}
     */
    public Builder observationIdentifier(CE value) {
      this.observationIdentifier = value;
      return this;
    }

    /**
     * Set encoded data.
     *
     * @param value Encoded data (not null).
     * @return {@link Builder}
     */
    public Builder encodedData(String value) {
      this.encodedData = value;
      return this;
    }

    /**
     * Build OBX and validate necessary fields.
     *
     * @return {@link OBX}
     * @throws MDMValidationException Thrown when validation of the OBX content fails.
     */
    public OBX build() throws MDMValidationException {

      Map<String, List<String>> failedVariables = new HashMap<String, List<String>>();

      if (MDMValidation.isNotNull(observationIdentifier)) {
        failedVariables.putAll(MDMValidation.confirmNotNull("observationIdentifier - identifier", observationIdentifier.getIdentifier(), true));
        failedVariables.putAll(MDMValidation.confirmNotNull("observationIdentifier - text", observationIdentifier.getText(), true));
        failedVariables.putAll(MDMValidation.confirmNotNull("observationIdentifier - codingSystem", observationIdentifier.getCodingSystem(), true));
      } else {
        failedVariables.putAll(MDMValidation.confirmNotNull("observationIdentifier", observationIdentifier, true));
      }

      failedVariables.putAll(MDMValidation.confirmNotNullNorBlank("Observation Value - encodedData", encodedData, true));

      // Confirm whether any variables have failed.
      if (!failedVariables.isEmpty()) {
        throw new MDMValidationException("Invalid OBX segment", failedVariables);
      }

      return new OBX(this);
    }
  }
}
