/*
 * Copyright 2011 NEHTA
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
package au.gov.nehta.vendorlibrary.ws;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DateTime utility class to facilitate the usage of {@link java.util.Date} and
 * {@link javax.xml.datatype.XMLGregorianCalendar} for webservice calls.
 */
public final class TimeUtility {

  /**
   * DateTime format as string. [yyyyMMdd HH:mm:ss]
   */
  public static final String DATE_FORMAT_NOW = "yyyyMMdd HH:mm:ss";

  /**
   * Regular expression for DateTime format as string. [[yyyyMMdd HH:mm:ss]
   */
  public static final String DATE_FORMAT_NOW_REGX = "^([0-9]{4})([0-1][0-9])"
    + "([0-3][0-9])\\s([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])$";

  /**
   * Compact Date format as string [yyyymmdd].
   */
  public static final String DATE_FORMAT = "yyyyMMdd";

  /**
   * Regular expression for compact Date format as string [yyyymmdd].
   */
  public static final String DATE_FORMAT_REGX = "^(19|20)\\d\\d(0[1-9]|1[012])"
    + "(0[1-9]|[12][0-9]|3[01])$";

  /**
   * Compact DateTime format as string. [yyyyMMddHHmmss]
   */
  public static final String COMPACT_DATE_TIME_FORMAT = "yyyyMMddHHmmss";

  /**
   * Length of Date time string as YYYYMMDDHHMM.
   */
  public static final int DATE_TIME_LENGTH = COMPACT_DATE_TIME_FORMAT.length();


  /**
   * Logger instance for event/error logging.
   */
  private static final Logger LOGGER = Logger.getLogger(TimeUtility.class
    .getName());

  /**
   * Default private constructor for Utility class.
   */
  private TimeUtility() {

  }

  /**
   * This method provided the XMLGregorian Date for the provided date in
   * yyyyMMdd HH:mm:ss.
   *
   * @param date as string in yyyyMMdd HH:mm:ss format
   * @return date as XMLGregorianCalendar instance. Returns null in an event
   *         of error. Invoking method must check for null.
   */
  public static XMLGregorianCalendar getXMLGregorianDateTime(
    final String date) {
    try {
      if (date.matches(DATE_FORMAT_NOW_REGX)) {
        XMLGregorianCalendar cal = DatatypeFactory.newInstance()
          .newXMLGregorianCalendar(new GregorianCalendar());
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_NOW);
        String pattern = format.toPattern();
        Date simpleDate = format.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(simpleDate);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        cal.setDay(day);
        cal.setYear(year);
        cal.setMonth(month);
        cal.setHour(hour);
        cal.setMinute(min);
        cal.setSecond(second);
        return cal;
      }
    } catch (DatatypeConfigurationException ex) {
      throw new IllegalArgumentException("Incorrect date format"
        + date + ". Must be set in " + DATE_FORMAT + " format", ex);
    } catch (ParseException ex) {
      throw new IllegalArgumentException("Incorrect date format"
        + date + ". Must be set in "
        + DATE_FORMAT + " format", ex);
    }
    throw new IllegalArgumentException("Incorrect date format"
      + date + ". Must be set in "
      + DATE_FORMAT + " format");
  }

  /**
   * This method provides the XMLGregorianCalendar Date for the provided
   * date String in yyyymmdd format .
   *
   * @param date in yyyymmdd format
   * @return XMLGregorianCalendar date. Return null in an event of error.
   *         Invoking method must check for null.
   */
  public static XMLGregorianCalendar getXMLGregorianDate(final String date) {
    try {
      XMLGregorianCalendar cal = DatatypeFactory.newInstance()
        .newXMLGregorianCalendar(new GregorianCalendar());
      if (date.matches(DATE_FORMAT_REGX)) {

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();

        Date simpleDate = (Date) format.parse(date);

        calendar.setTime(simpleDate);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        cal.setDay(day);
        cal.setYear(year);
        //Month is zero based in Date and one based in Calendar
        cal.setMonth(month + 1);

        // Unset the timezone.
        cal.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
        return cal;
      }
    } catch (ParseException ex) {
      throw new IllegalArgumentException("Incorrect date format"
        + date + ". Must be set in " + DATE_FORMAT + " format", ex);
    } catch (DatatypeConfigurationException ex) {
      throw new IllegalArgumentException("Incorrect date format"
        + date + ". Must be set in " + DATE_FORMAT + " format", ex);
    }
    throw new IllegalArgumentException("Incorrect date format "
      + date + ". Must be set in " + DATE_FORMAT + " format");
  }

  /**
   * Returns current time as string [yyyyMMdd HH:mm:ss].
   *
   * @return the current system time.
   */
  public static String now() {
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
    return sdf.format(cal.getTime());

  }

  /**
   * Get current time as {@link java.util.Date}.
   *
   * @return current dateTime as {@link java.util.Date}
   */
  public static Date nowDate() {
    Calendar cal = Calendar.getInstance();
    return cal.getTime();
  }

  /**
   * Get the Date in DATE_FORMAT pattern (Mandatory) .
   *
   * @param date the date to be parsed.
   * @return the date in YYYYMMDD format.
   */
  public static String getDateAsYYYYMMDD(final Date date) {
    String dateTime = null;
    if (date != null) {
      SimpleDateFormat dateformatYYYYMMDD = new SimpleDateFormat(
        TimeUtility.DATE_FORMAT);
      dateTime = new String(dateformatYYYYMMDD.format(date));
    }
    return dateTime;
  }

  /**
   * Returns current time as XMLGregorianCalendar instance.
   *
   * @return returns null in an event of DatatypeconfigurationException.
   */
  public static XMLGregorianCalendar nowXMLGregorianCalendar() {
    try {
      XMLGregorianCalendar cal = DatatypeFactory.newInstance()
        .newXMLGregorianCalendar(new GregorianCalendar());
      return cal;
    } catch (DatatypeConfigurationException ex) {
      LOGGER.log(Level.SEVERE, null, ex);
    }
    return null;
  }

}
