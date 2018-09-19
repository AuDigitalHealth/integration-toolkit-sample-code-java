package au.gov.nehta.vendorlibrary.pcehr.test.unittests;
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

import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.DateUtils;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class DateUtilsTest {

  private Map<String, String> validDateTimes;

  @BeforeClass
  public static void initialSetup() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
    validDateTimes = new HashMap<String, String>();
    validDateTimes.put(
      "20120825090000.1234+02",
      "20120825070000"
    );
    validDateTimes.put(
      "20120825090000+0130",
      "20120825073000"
    );
    validDateTimes.put(
      "201208250900-02",
      "201208251100"
    );
    validDateTimes.put(
      "2012082509-0200",
      "2012082511"
    );
    validDateTimes.put(
      "2012082509-0230",
      "201208251130"
    );
    validDateTimes.put(
      "20120825+0200",
      "20120825"
    );
    validDateTimes.put(
      "20120825090000.1234",
      "20120825090000"
    );
    validDateTimes.put(
      "20120825230000.1234",
      "20120825230000"
    );
  }

  @After
  public void tearDown() throws Exception {
    validDateTimes.clear();
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_BadTimeZoneTooLong() throws Exception {
    String badTooLongTimeZone = "20120825090000.1234+020035";
    DateUtils.toUtcDate(badTooLongTimeZone);
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_BadTooShortTimeZone() throws Exception {
    String badTooShortTimeZone = "20120825090000.1234+6";
    DateUtils.toUtcDate(badTooShortTimeZone);
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_BadWrongTimeZone() throws Exception {
    String badWrongTimeZone = "20120825090000.1234+103";
    DateUtils.toUtcDate(badWrongTimeZone);
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_BadInvalidMillisecond() throws Exception {
    String badInvalidMillisecond = "2012082509.1234+1030";
    String result = DateUtils.toUtcDate(badInvalidMillisecond);
  }

  @Test
  public void testToUtcDate_ValidDateTime() throws Exception {
    for (Map.Entry<String, String> entry : validDateTimes.entrySet()) {
      System.out.println(entry.getKey());
      String result = DateUtils.toUtcDate(entry.getKey());
      Assert.assertEquals(entry.getValue(), result);
    }
  }
}
