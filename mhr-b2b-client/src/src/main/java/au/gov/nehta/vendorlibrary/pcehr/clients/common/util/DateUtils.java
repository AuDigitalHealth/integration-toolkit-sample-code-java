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
package au.gov.nehta.vendorlibrary.pcehr.clients.common.util;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.DateParsePatterns;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.Timestamp;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Date handling utilities.
 */
public final class DateUtils {

  private static final int MIN_TIMEZONE_APPLICATION = 8;
  private static final int MIN_HALF_HOUR_APPLICATION = 10;
  private static final int MAX_PRECISION = 14;
  private static final int SMALL_TIMEZONE_PRECISION = 2;
  private static final int LARGE_TIMEZONE_PRECISION = 4;
  private static final int HALF_HOUR = 30;
  private static final int YEAR_PRECISION = 4;
  private static final int MONTH_PRECISION = 2;
  private static final int DAY_PRECISION = 2;
  private static final int HOUR_PRECISION = 2;
  private static final int MINUTE_PRECISION = 2;
  private static final int SECOND_PRECISION = 2;

  // Private constructor.
  private DateUtils() {
  }

  /**
   * Generates a timestamp for SOAP signatures.
   *
   * @return {@link Timestamp}.
   */
  public static Timestamp generateTimestamp() {
    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    gregorianCalendar.setTime(new Date());

    Timestamp timestamp = new Timestamp();

    try {
      timestamp.setCreated(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
    } catch (DatatypeConfigurationException e) {
      throw new IllegalStateException("Unexpected exception has occurred.", e);
    }

    return timestamp;
  }

  /**
   * Convert {@link Date} to {@link XMLGregorianCalendar}.
   *
   * @param date Date to be converted.
   * @return Converted {@link XMLGregorianCalendar}.
   */
  public static XMLGregorianCalendar getXMLGregorianCalendar(final Date date) {
    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    gregorianCalendar.setTime(date);

    try {
      return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    } catch (DatatypeConfigurationException e) {
      throw new IllegalStateException("Unexpected exception has occurred.", e);
    }
  }

  /**
   * Convert local CDA date time to UTC {@link String}.
   *
   * @param dateTime DateTime to convert.
   * @return Converted {@link String}.
   */
  public static String toUtcDate(final String dateTime) {

    //Validate.notNull(dateTime, "'dateTime' must be specified.");

    // Split the date string by timezone (if present).
    String[] dateTimeComponents = dateTime.split("[+|-]");


    if (dateTimeComponents != null && dateTimeComponents.length > 0) {


      // Retrieve the parse pattern.
      DateParsePatterns dateParsePattern = DateParsePatterns.findByMatchPatternLength(dateTimeComponents[0]);
      DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateParsePattern.getPatternValue());
      LocalDateTime localTime = dateTimeFormatter.parseDateTime(dateTimeComponents[0]).toLocalDateTime();

      // Process string.

      // Time zone not present.
      if (dateTimeComponents.length == 1) {
        return outputPrecision(localTime.toDateTime(), dateParsePattern.getMatchPatternLength(), false);
      } else if (dateTimeComponents.length == 2) {
        // Time zone present.
        if (dateTimeComponents[0].length() <= MIN_TIMEZONE_APPLICATION) {
          return outputPrecision(localTime.toDateTime(), dateParsePattern.getMatchPatternLength(), false);
        }

        // Set the timezone operator.
        String operator = null;

        if (dateTime.contains("-")) {
          operator = "-";
        } else {
          operator = "";
        }

        Holder<Boolean> halfHour = new Holder<Boolean>();

        DateTimeZone timeZone = findTimeZonePatternByLength(operator, dateTimeComponents[1], halfHour);
        DateTime srcDateTime = localTime.toDateTime(timeZone);
        DateTime dstDateTime = srcDateTime.withZone(DateTimeZone.UTC);

        return outputPrecision(dstDateTime, dateParsePattern.getMatchPatternLength(), halfHour.value);

      } else {
        throw new IllegalStateException("Unexpected exception has occurred.");
      }
    } else {
      throw new IllegalArgumentException("Unable to extract date/time components.");
    }
  }

  /**
   * Get timezone by pattern.
   *
   * @param operator +/- offset.
   * @param timeZone Timezone string.
   * @return {@link DateTimeZone}.
   */
  private static DateTimeZone findTimeZonePatternByLength(final String operator, final String timeZone, final Holder<Boolean> halfHour) {

    Validate.notEmpty("'operator' must be specified.");
    Validate.notEmpty("'timeZone' must be specified.");

    halfHour.value = false;

    // Time zone of the format 'ZZ'.
    if (timeZone.length() == SMALL_TIMEZONE_PRECISION) {
      return DateTimeZone.forOffsetHours(Integer.parseInt(String.format("%s%s", operator, timeZone)));
    } else if (timeZone.length() == LARGE_TIMEZONE_PRECISION) {
      // Time zone of the format 'ZZZZ'.
      int mid = timeZone.length() / 2;

      int hoursOffset = Integer.parseInt(String.format("%s%s", operator, timeZone.substring(0, mid)));
      int minsOffset = Integer.parseInt(timeZone.substring(mid, timeZone.length()));

      if (minsOffset / HALF_HOUR == 1) {
        halfHour.value = true;
      }
      return DateTimeZone.forOffsetHoursMinutes(hoursOffset, minsOffset);

    } else {
      throw new IllegalArgumentException("'dateTime' does not match an expected pattern length.");
    }
  }

  /**
   * Output a string with a particular precision.
   *
   * @param dateTime  Datetime string to format.
   * @param precision Level of precision.
   * @param halfHour  Whether or not the timezone uses a half hour level of precision.
   * @return Formatted string.
   */
  private static String outputPrecision(final DateTime dateTime, final int precision, final boolean halfHour) {

    Validate.notNull(dateTime, "'dateTime' must be specified.");
    Validate.notNull(precision, "''precision' must be specified.");
    Validate.notNull(precision, "''halfHour' must be specified.");


    StringBuilder sb = new StringBuilder();
    sb.append(StringUtils.leftPad(String.valueOf(dateTime.getYear()), YEAR_PRECISION, "0"));
    sb.append(StringUtils.leftPad(String.valueOf(dateTime.getMonthOfYear()), MONTH_PRECISION, "0"));
    sb.append(StringUtils.leftPad(String.valueOf(dateTime.getDayOfMonth()), DAY_PRECISION, "0"));
    sb.append(StringUtils.leftPad(String.valueOf(dateTime.getHourOfDay()), HOUR_PRECISION, "0"));
    sb.append(StringUtils.leftPad(String.valueOf(dateTime.getMinuteOfHour()), MINUTE_PRECISION, "0"));
    sb.append(StringUtils.leftPad(String.valueOf(dateTime.getSecondOfMinute()), SECOND_PRECISION, "0"));

    if (precision > MAX_PRECISION) {
      return sb.toString().substring(0, MAX_PRECISION);
    }

    if (precision == MIN_HALF_HOUR_APPLICATION && halfHour) {
      return sb.toString().substring(0, precision + 2);
    }

    return sb.toString().substring(0, precision);
  }
}