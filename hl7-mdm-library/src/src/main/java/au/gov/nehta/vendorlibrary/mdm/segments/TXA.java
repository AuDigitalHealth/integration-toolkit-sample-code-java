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
import au.gov.nehta.vendorlibrary.mdm.enums.DocumentCompletionStatus;
import au.gov.nehta.vendorlibrary.mdm.enums.DocumentContentPresentation;
import au.gov.nehta.vendorlibrary.mdm.enums.DocumentType;
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
import java.util.UUID;

/**
 * {@link TXA}
 * Encapsulates MDM documentation notification segment.
 */
public final class TXA {

  /**
   * TXA segment name.
   */
  private static final String TXA_SEGMENT_NAME = "TXA";

  /**
   * Expected segment field count.
   */
  private static final int TXA_FIELD_COUNT = 18;

  /**
   * TXA activity date string pattern.
   */
  private static final String TXA_DATE_TIME_PATTERN = "yyyyMMddHHmmss";

  /**
   * TXA.1 - Set ID.
   */
  private static final int SET_ID = 1;

  /**
   * TXA.2 - Document type.
   */
  private static final DocumentType DOCUMENT_TYPE = DocumentType.NEHTA;

  /**
   * TXA.3 - Document content presentation.
   */
  private static final DocumentContentPresentation DOCUMENT_CONTENT_PRESENTATION = DocumentContentPresentation.AP;

  /**
   * TXA.4 - Activity date/time.
   */
  private final Date activityDateTime;

  /**
   * TXA.12 - Unique document number (UUID).
   * i.e. root CDA document ID.
   */
  private final UUID uniqueDocumentNumber;

  /**
   * TXA.16 - Unique document file name.
   * i.e. CDA package ZIP file name.
   */
  private final String uniqueDocumentFileName;

  /**
   * TXA.17 - Document completion status.
   */
  private static final DocumentCompletionStatus DOCUMENT_COMPLETION_STATUS = DocumentCompletionStatus.LA;

  /**
   * Private constructor to set {@link TXA} instance variables.
   *
   * @param builder {@link Builder} to construct {@link TXA} from.
   */
  private TXA(Builder builder) {
    activityDateTime = builder.activityDateTime;
    uniqueDocumentNumber = builder.uniqueDocumentNumber;
    uniqueDocumentFileName = builder.uniqueDocumentFileName;
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
   * Retrieve document type.
   *
   * @return {@link DocumentType}.
   */
  public DocumentType getDocumentType() {
    return DOCUMENT_TYPE;
  }

  /**
   * Retrieve document content presentation.
   *
   * @return {@link DocumentContentPresentation}.
   */
  public DocumentContentPresentation getDocumentContentPresentation() {
    return DOCUMENT_CONTENT_PRESENTATION;
  }

  /**
   * Retrieve activity date time.
   *
   * @return Activity date/time.
   */
  public Date getActivityDateTime() {
    return activityDateTime;
  }

  /**
   * Retrieve unique document number.
   *
   * @return {@link UUID} object.
   */
  public UUID getUniqueDocumentNumber() {
    return uniqueDocumentNumber;
  }

  /**
   * Retrieve unique document file name.
   *
   * @return Document file name string.
   */
  public String getUniqueDocumentFileName() {
    return uniqueDocumentFileName;
  }

  /**
   * Retrieve document completion status.
   *
   * @return {@link DocumentCompletionStatus} object.
   */
  public DocumentCompletionStatus getDocumentCompletionStatus() {
    return DOCUMENT_COMPLETION_STATUS;
  }

  /**
   * Parse {@link TXA} segment from string.
   *
   * @param parseValue String to be parsed (not null).
   * @return {@link TXA} segment.
   */
  public static TXA parse(String parseValue) {
    ArgumentUtils.checkNotNull(parseValue, "parseValue");

    // Extract individual fields.
    List<String> fields = Arrays.asList(parseValue.split("\\" + MDMConstants.FIELD_SEPARATOR));

    if (fields.size() >= TXA_FIELD_COUNT) {
      if (fields.get(0).compareTo(TXA_SEGMENT_NAME) != 0) {
        throw new IllegalArgumentException(String.format(
          "Unable to parse TXA - Segment name is incorrect: \n\tActual Value: %s\n\tExpected Value: %s",
          fields.get(0),
          TXA_SEGMENT_NAME
        ));
      }
      try {
        return new Builder()
          .activityDateTime(new SimpleDateFormat(TXA_DATE_TIME_PATTERN).parse(fields.get(4)))
          .uniqueDocumentNumber(UUID.fromString(fields.get(12)))
          .uniqueDocumentFileName(fields.get(16))
          .build();
      } catch (MDMValidationException e) {
        throw new IllegalArgumentException("Unable to parse TXA", e);
      } catch (ParseException e) {
        throw new IllegalArgumentException(String.format(
          "Unable to parse TXA - Invalid date/time pattern supplied:\n\tActual Value: %s\n\tExpected Format: %s",
          fields.get(4),
          TXA_DATE_TIME_PATTERN
        ));
      }
    } else {
      throw new IllegalArgumentException("Unable to parse TXA - Invalid number of fields:\n\t"
        + "Actual: "
        + fields.size()
        + "\n\tExpected: "
        + TXA_FIELD_COUNT
      );
    }
  }

  /**
   * Outputs a formatted {@link TXA} segment.
   *
   * @return Formatted string.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(TXA_SEGMENT_NAME);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(SET_ID);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(DOCUMENT_TYPE);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(DOCUMENT_CONTENT_PRESENTATION);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(new SimpleDateFormat(TXA_DATE_TIME_PATTERN).format(activityDateTime));
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(uniqueDocumentNumber);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(uniqueDocumentFileName);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(DOCUMENT_COMPLETION_STATUS);
    sb.append(MDMConstants.SEGMENT_TERMINATOR);
    return sb.toString();
  }

  /**
   * {@link Builder}
   * Build class used to construct and validate an {@link TXA} object.
   */
  public static final class Builder {

    private UUID uniqueDocumentNumber;
    private Date activityDateTime;
    private String uniqueDocumentFileName;

    /**
     * Default constructor.
     */
    public Builder() {
      this.uniqueDocumentFileName = "";
    }

    /**
     * Set unique document number.
     *
     * @param value Unique document number (not null).
     * @return {@link Builder}
     */
    public Builder uniqueDocumentNumber(UUID value) {
      this.uniqueDocumentNumber = value;
      return this;
    }

    /**
     * Set activity date/time.
     *
     * @param value Activity date/time (not null).
     * @return {@link Builder}
     */
    public Builder activityDateTime(Date value) {
      this.activityDateTime = value;
      return this;
    }

    /**
     * Set unique document file name.
     *
     * @param value Unique document file name (not null).
     * @return {@link Builder}
     */
    public Builder uniqueDocumentFileName(String value) {
      this.uniqueDocumentFileName = value;
      return this;
    }

    /**
     * Build {@link TXA} and validate necessary fields.
     *
     * @return {@link TXA}
     * @throws MDMValidationException Thrown when validation fails.
     */
    public TXA build() throws MDMValidationException {

      Map<String, List<String>> failedVariables = new HashMap<String, List<String>>();

      failedVariables.putAll(MDMValidation.confirmNotNull("activityDateTime", activityDateTime, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("uniqueDocumentNumber", uniqueDocumentNumber, true));
      failedVariables.putAll(MDMValidation.confirmNotNullNorBlank("uniqueDocumentFileName", uniqueDocumentFileName, true));

      // Confirm whether any variables have failed.
      if (!failedVariables.isEmpty()) {
        throw new MDMValidationException("Invalid TXA segment", failedVariables);
      }

      return new TXA(this);
    }
  }
}
