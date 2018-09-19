/*
 * Copyright 2010 NEHTA
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

import org.junit.Test;

import javax.xml.datatype.XMLGregorianCalendar;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Test cases for TimeUtility class
 */
public class TimeUtilityTest {

  @Test
  public void testNowXMLGregorianCalendar() throws Exception {
    XMLGregorianCalendar calendar = TimeUtility.nowXMLGregorianCalendar();
    assertNotNull(calendar);
    assertTrue(calendar.isValid());
  }


  @Test
  public void testGetXMLGregorianDate() throws Exception {
    String date = "19601231";
    XMLGregorianCalendar calendar = TimeUtility.getXMLGregorianDate(date);
    assertNotNull(calendar);
    assertEquals(1960, calendar.getYear());
    assertEquals(12, calendar.getMonth());
    assertEquals(31, calendar.getDay());


  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGetXMLGregorianDate() throws Exception {
    String date = "2011-03-16";
    TimeUtility.getXMLGregorianDate(date);
  }


  @Test
  public void testGetXMLGregorianDateTime() throws Exception {
    String date = "20070804 18:01:01";
    XMLGregorianCalendar calendar = TimeUtility.getXMLGregorianDateTime(date);
    assertNotNull(calendar);
    assertTrue(calendar.isValid());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGetXMLGregorianDateTime() throws Exception {
    String date = "2007-08-0418:01:01";
    TimeUtility.getXMLGregorianDateTime(date);

    date = "2007080418:01:01";
    TimeUtility.getXMLGregorianDateTime(date);

    date = "20070804 12:01:01 PM";
    TimeUtility.getXMLGregorianDateTime(date);
  }

  @Test
  public void testDateRegularExpressionGregorianDate() throws Exception {
    String date = TimeUtility.now();
    assertTrue(date.matches(TimeUtility.DATE_FORMAT_NOW_REGX));
  }



  @Test
  public void testGetDateAsYYYYMMDD() throws Exception {
    Date date = new Date();
    SimpleDateFormat dateformatYYYYMMDD = new SimpleDateFormat(TimeUtility.DATE_FORMAT);
    String expectedDate = new String( dateformatYYYYMMDD.format( date ) );
    String actualDate = TimeUtility.getDateAsYYYYMMDD(date);
    assertNotNull(actualDate);
    assertEquals(expectedDate, actualDate);

  }

}
