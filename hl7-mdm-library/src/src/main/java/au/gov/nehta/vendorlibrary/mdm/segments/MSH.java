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
import au.gov.nehta.vendorlibrary.mdm.datatypes.HD;
import au.gov.nehta.vendorlibrary.mdm.enums.AcknowledgementType;
import au.gov.nehta.vendorlibrary.mdm.enums.CountryCode;
import au.gov.nehta.vendorlibrary.mdm.enums.ProcessingId;
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
 * {@link MSH}
 * Encapsulates MDM message header.
 */
public final class MSH {

  /**
   * MSH segment name.
   */
  private static final String MSH_SEGMENT_NAME = "MSH";

  /**
   * MSH segment field count.
   */
  private static final int MSH_FIELD_COUNT = 17;

  /**
   * MSH date string pattern.
   */
  private static final String MSH_DATE_TIME_PATTERN = "yyyyMMddHHmmssZ";
  
  /**
   * MSH lenient date string pattern.
   */
  private static final String MSH_LENIENT_DATE_TIME_PATTERN = "yyyyMMddHHmmss";

  /**
   * MSH.2 - Encoding characters.
   */
  private static final String ENCODING_CHARACTERS = "^~\\&";

  /**
   * MSH.3 - Sending application.
   * <p/>
   * e.g. HPI-O name.
   */
  private final HD sendingApplication;

  /**
   * MSH.4 - Sending facility.
   * <p/>
   * e.g. HPI-O.
   */
  private final HD sendingFacility;

  /**
   * MSH.5 - Receiving application.
   * <p/>
   * e.g. HPI-O name.
   */
  private final HD receivingApplication;

  /**
   * MSH.6 - Receiving facility.
   * <p/>
   * e.g. HPI-O.
   */
  private final HD receivingFacility;

  /**
   * MSH.7 - Message date/time.
   */
  private final Date messageDateTime;

  /**
   * MSH.9 - Message type.
   */
  private static final String MESSAGE_TYPE = "MDM^T02^MDM_T02";

  /**
   * MSH.10 - Message control ID.
   */
  private final String messageControlId;

  /**
   * MSH.11 - Processing ID.
   */
  private static final ProcessingId PROCESSING_ID = ProcessingId.P;

  /**
   * MSH.12 - Version ID.
   */
  private static final String VERSION_ID = "2.3.1";

  /**
   * MSH.15 - Accept acknowledgement type.
   */
  private static final AcknowledgementType ACCEPT_ACKNOWLEDGEMENT_TYPE = AcknowledgementType.NE;

  /**
   * MSH.16 - Application acknowledgement type.
   */
  private static final AcknowledgementType APPLICATION_ACKNOWLEDGEMENT_TYPE = AcknowledgementType.AL;

  /**
   * MSH.17 - Country code.
   * <p/>
   * Constrained to "AUS" - Australia.
   */
  private static final CountryCode COUNTRY_CODE = CountryCode.AUSTRALIA;

  /**
   * Private constructor to set {@link MSH} instance variables.
   *
   * @param builder {@link Builder} to construct {@link MSH} from.
   */
  private MSH(Builder builder) {
    sendingApplication = builder.sendingApplication;
    sendingFacility = builder.sendingFacility;
    receivingApplication = builder.receivingApplication;
    receivingFacility = builder.receivingFacility;
    messageDateTime = builder.messageDateTime;
    messageControlId = builder.messageControlId;
  }

  /**
   * Retrieve encoding characters.
   *
   * @return Encoding characters string.
   */
  public String getEncodingCharacters() {
    return ENCODING_CHARACTERS;
  }

  /**
   * Retrieve sending application.
   *
   * @return Sending application {@link HD}.
   */
  public HD getSendingApplication() {
    return sendingApplication;
  }

  /**
   * Retrieve sending facility.
   *
   * @return Sending facility.
   */
  public HD getSendingFacility() {
    return sendingFacility;
  }

  /**
   * Retrieve receiving application.
   *
   * @return Receiving application {@link HD}.
   */
  public HD getReceivingApplication() {
    return receivingApplication;
  }

  /**
   * Retrieve receiving facility.
   *
   * @return Receiving facility.
   */
  public HD getReceivingFacility() {
    return receivingFacility;
  }

  /**
   * Retrieve message date/time.
   *
   * @return Message date/time.
   */
  public Date getMessageDateTime() {
    return messageDateTime;
  }

  /**
   * Retrieve message type.
   *
   * @return Message type string.
   */
  public String getMessageType() {
    return MESSAGE_TYPE;
  }

  /**
   * Retrieve message control ID.
   *
   * @return Message control ID string.
   */
  public String getMessageControlId() {
    return messageControlId;
  }

  /**
   * Retrieve processing ID.
   *
   * @return Processing ID enumerator.
   */
  public ProcessingId getProcessingId() {
    return PROCESSING_ID;
  }

  /**
   * Retrieve version ID.
   *
   * @return Version ID string.
   */
  public String getVersionId() {
    return VERSION_ID;
  }

  /**
   * Retrieve accept acknowledgement type.
   *
   * @return {@link AcknowledgementType}.
   */
  public AcknowledgementType getAcceptAcknowledgementType() {
    return ACCEPT_ACKNOWLEDGEMENT_TYPE;
  }

  /**
   * Retrieve application acknowledgement type.
   *
   * @return {@link AcknowledgementType}.
   */
  public AcknowledgementType getApplicationAcknowledgementType() {
    return APPLICATION_ACKNOWLEDGEMENT_TYPE;
  }

  /**
   * Retrieve country code.
   *
   * @return Country code.
   */
  public CountryCode getCountryCode() {
    return COUNTRY_CODE;
  }

  /**
   * Parse {@link MSH} segment from string.
   *
   * @param parseValue String to be parsed (not null).
   * @return {@link MSH} segment.
   */
  public static MSH parse(String parseValue) {
    ArgumentUtils.checkNotNull(parseValue, "parseValue");

    // Extract individual fields.
    List<String> fields = Arrays.asList(parseValue.split("\\" + MDMConstants.FIELD_SEPARATOR));

    if (fields.size() >= MSH_FIELD_COUNT) {
      if (fields.get(0).compareTo(MSH_SEGMENT_NAME) != 0) {
        throw new IllegalArgumentException(String.format(
          "Unable to parse MSH - Segment name is incorrect: \n\tActual Value: %s\n\tExpected Value: %s",
          fields.get(0),
          MSH_SEGMENT_NAME
        ));
      }
      try {
        return new Builder()
          .sendingApplication(new HD.Builder().namespace(fields.get(2)).build())
          .sendingFacility(HD.parse(fields.get(3)))
          .receivingApplication(new HD.Builder().namespace(fields.get(4)).build())
          .receivingFacility(HD.parse(fields.get(5)))
          .messageDateTime(parseDate( fields.get(6)))
          .messageControlId(fields.get(9))
          .build();
      } catch (MDMValidationException e) {
        throw new IllegalArgumentException("Unable to parse MSH", e);
      } catch (ParseException e) {
        throw new IllegalArgumentException(String.format(
          "Unable to parse MSH - Invalid date/time pattern supplied:\n\tActual Value: %s\n\tExpected Format: %s",
          fields.get(6),
          MSH_DATE_TIME_PATTERN
        ));
      }
    } else {
      throw new IllegalArgumentException("Unable to parse MSH - Invalid number of fields:\n\t"
        + "Actual: "
        + fields.size()
        + "\n\tExpected: "
        + MSH_FIELD_COUNT
      );
    }
  }

  private static Date parseDate( String string ) throws ParseException {
	  //might be with or without a timezone 
	  Date date;
	  try{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MSH_DATE_TIME_PATTERN);
		simpleDateFormat.setLenient( true );
		date = simpleDateFormat.parse(string);
	  }catch(ParseException e){
		  //try again this time throw the exeption if it doesn't work
		  /**
		   * MSH date string pattern.
		   */
		  date = new SimpleDateFormat(MSH_LENIENT_DATE_TIME_PATTERN).parse(string);
	  }
	  
	  return date;
}

/**
   * Outputs a formatted {@link MSH} segment.
   *
   * @return Formatted string.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(MSH_SEGMENT_NAME);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(ENCODING_CHARACTERS);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(sendingApplication.toString());
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(sendingFacility.toString());
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(receivingApplication.toString());
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(receivingFacility.toString());
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(new SimpleDateFormat(MSH_DATE_TIME_PATTERN).format(messageDateTime));
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MESSAGE_TYPE);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(messageControlId);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(PROCESSING_ID);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(VERSION_ID);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(ACCEPT_ACKNOWLEDGEMENT_TYPE);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(APPLICATION_ACKNOWLEDGEMENT_TYPE);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(COUNTRY_CODE.getCode());
    sb.append(MDMConstants.SEGMENT_TERMINATOR);
    return sb.toString();
  }

  /**
   * {@link Builder}
   * Build class used to construct and validate an (@link MSH} object.
   */
  public static final class Builder {

    private Date messageDateTime;
    private String messageControlId;
    private HD sendingApplication;
    private HD sendingFacility;
    private HD receivingApplication;
    private HD receivingFacility;

    /**
     * Default constructor.
     */
    public Builder() {
      this.messageControlId = "";
    }

    /**
     * Set the date/time message.
     *
     * @param value Date/time message (not null).
     * @return {@link Builder}.
     */
    public Builder messageDateTime(Date value) {
      this.messageDateTime = value;
      return this;
    }

    /**
     * Set the message control ID.
     *
     * @param value Message control ID (not null nor blank).
     * @return {@link Builder}.
     */
    public Builder messageControlId(String value) {
      this.messageControlId = value;
      return this;
    }

    /**
     * Set the sending application.
     *
     * @param value Sending application (not null).
     * @return {@link Builder}.
     */
    public Builder sendingApplication(HD value) {
      this.sendingApplication = value;
      return this;
    }

    /**
     * Set the sending facility.
     *
     * @param value Sending facility (not null).
     * @return {@link Builder}.
     */
    public Builder sendingFacility(HD value) {
      this.sendingFacility = value;
      return this;
    }

    /**
     * Set the receiving application.
     *
     * @param value Receiving application (not null).
     * @return {@link Builder}.
     */
    public Builder receivingApplication(HD value) {
      this.receivingApplication = value;
      return this;
    }

    /**
     * Set the receiving facility.
     *
     * @param value Receiving facility (not null).
     * @return {@link Builder}.
     */
    public Builder receivingFacility(HD value) {
      this.receivingFacility = value;
      return this;
    }

    /**
     * Build {@link MSH} and validate necessary fields.
     *
     * @return {@link MSH}
     * @throws MDMValidationException Thrown when validation of the MSH contents fails.
     */
    public MSH build() throws MDMValidationException {

      Map<String, List<String>> failedVariables = new HashMap<String, List<String>>();

      failedVariables.putAll(MDMValidation.confirmNotNull("sendingApplication", sendingApplication, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("sendingFacility", sendingFacility, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("receivingApplication", receivingApplication, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("receivingFacility", receivingFacility, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("messageDateTime", messageDateTime, true));
      failedVariables.putAll(MDMValidation.confirmNotNullNorBlank("messageControlId", messageControlId, true));

      // Confirm whether any variables have failed.
      if (!failedVariables.isEmpty()) {
        throw new MDMValidationException("Invalid MSH segment", failedVariables);
      }

      return new MSH(this);
    }
  }
}
