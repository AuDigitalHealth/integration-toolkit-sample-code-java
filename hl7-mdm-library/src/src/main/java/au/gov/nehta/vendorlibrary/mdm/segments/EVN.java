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
 * {@link EVN}
 * Encapsulates MDM event type.
 */
public final class EVN {

  /**
   * EVN segment name.
   */
  private static final String EVN_SEGMENT_NAME = "EVN";

  /**
   * Expected segment field count
   */
  private static final int EVN_FIELD_COUNT = 3;

  /**
   * EVN date string pattern.
   */
  private static final String EVN_DATE_TIME_PATTERN = "yyyyMMddHHmmss";

  /**
   * EVN.1 - Event type code.
   */
  private static final String EVENT_TYPE_CODE = "T02";

  /**
   * EVN.2 - Recorded date/time.
   */
  private final Date recordedDateTime;

  /**
   * Private constructor to set {@link EVN} instance variables.
   *
   * @param builder {@link Builder} to construct {@link EVN} from.
   */
  private EVN(Builder builder) {
    recordedDateTime = builder.recordedDateTime;
  }

  /**
   * Retrieve event type code.
   *
   * @return Event type code string.
   */
  public String getEventTypeCode() {
    return EVENT_TYPE_CODE;
  }

  /**
   * Retrieve recorded date/time.
   *
   * @return Recorded date/time.
   */
  public Date getRecordedDateTime() {
    return recordedDateTime;
  }

  /**
   * Parse {@link EVN} segment from string.
   *
   * @param parseValue String to be parsed (not null).
   * @return {@link EVN} segment.
   */
  public static EVN parse(String parseValue) {
    ArgumentUtils.checkNotNull(parseValue, "parseValue");

    // Extract individual fields.
    List<String> fields = Arrays.asList(parseValue.split("\\" + MDMConstants.FIELD_SEPARATOR));

    if (fields.size() >= EVN_FIELD_COUNT) {
      if (fields.get(0).compareTo(EVN_SEGMENT_NAME) != 0) {
        throw new IllegalArgumentException(String.format(
          "Unable to parse EVN - Segment name is incorrect: \n\tActual Value: %s\n\tExpected Value: %s",
          fields.get(0),
          EVN_SEGMENT_NAME
        ));
      }
      try {
        return new Builder()
          .recordedDateTime(new SimpleDateFormat(EVN_DATE_TIME_PATTERN).parse(fields.get(2)))
          .build();
      } catch (ParseException e) {
        throw new IllegalArgumentException(String.format(
          "Unable to parse EVN - Invalid date/time pattern supplied:\n\tActual Value: %s\n\tExpected Format: %s",
          fields.get(2),
          EVN_DATE_TIME_PATTERN
        ));
      } catch (MDMValidationException e) {
        throw new IllegalArgumentException("Unable to parse EVN", e);
      }
    } else {
      throw new IllegalArgumentException(String.format(
        "Unable to parse EVN - Invalid number of fields:\n\tActual: %d\n\tExpected: %d",
        fields.size(),
        EVN_FIELD_COUNT
      ));
    }
  }

  /**
   * Outputs a {@link EVN} segment in the following format:
   * <p/>
   * EVN|eventTypeCode|recordedDateTime
   *
   * @return Formatted string.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(EVN_SEGMENT_NAME);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(EVENT_TYPE_CODE);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(new SimpleDateFormat(EVN_DATE_TIME_PATTERN).format(recordedDateTime));
    sb.append(MDMConstants.SEGMENT_TERMINATOR);
    return sb.toString();
  }

  /**
   * {@link Builder}
   * Build class used to construct and validate an {@link EVN} object.
   */
  public static final class Builder {

    private Date recordedDateTime;

    /**
     * Set the recorded date/time.
     *
     * @param value Recorded date/time (not null).
     * @return {@link Builder}
     */
    public Builder recordedDateTime(Date value) {
      this.recordedDateTime = value;
      return this;
    }

    /**
     * Build {@link EVN} and validate necessary fields.
     *
     * @return {@link EVN}
     * @throws MDMValidationException Thrown when validation fails.
     */
    public EVN build() throws MDMValidationException {

      Map<String, List<String>> failedVariables = new HashMap<String, List<String>>();

      failedVariables.putAll(MDMValidation.confirmNotNull("recordedDateTime", recordedDateTime, true));

      // Confirm whether any variables have failed.
      if (!failedVariables.isEmpty()) {
        throw new MDMValidationException("Invalid EVN segment", failedVariables);
      }

      return new EVN(this);
    }
  }
}
