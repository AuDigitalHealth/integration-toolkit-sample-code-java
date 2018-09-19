package au.gov.nehta.vendorlibrary.mdm.test;

import au.gov.nehta.vendorlibrary.mdm.segments.EVN;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidationException;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;

public class EVNTest {
  @Test
  public void testGetEventTypeCode() throws Exception {
    String sample1 = "EVN|T02|20121207151904\r";
    EVN evn = EVN.parse(sample1);
    Assert.assertEquals("T02", evn.getEventTypeCode());
  }

  @Test
  public void testGetRecordedDateTime() throws Exception {
    String sample1 = "EVN|T02|20121207151904\r";
    EVN evn = EVN.parse(sample1);
    Assert.assertEquals(new SimpleDateFormat("yyyyMMddHHmmss").parse("20121207151904"), evn.getRecordedDateTime());
  }

  // TODO: This restriction has been relaxed and as such, no exception is expected.
  @Test//(expected = IllegalArgumentException.class)
  public void testParseInvalidFieldNumberMore() throws Exception {
    String sample1 = "EVN|T02|20121207151904|SomethingElse\r";
    EVN.parse(sample1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseInvalidFieldNumberLess() throws Exception {
    String sample1 = "EVN|T02\r";
    EVN.parse(sample1);
  }

  @Test
  public void testToString() throws Exception {
    String sample1 = "EVN|T02|20121207151904\r";
    EVN evn = EVN.parse(sample1);
    Assert.assertEquals(sample1, evn.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseNull() throws Exception {
    String sample1 = null;
    EVN.parse(sample1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseInvalidDate() throws Exception {
    String sample1 = "EVN|T02|2012";
    EVN.parse(sample1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseInvalidSegmentName() throws Exception {
    String sample1 = "SomethingElse|T02|20121207151904";
    EVN.parse(sample1);
  }

  @Test(expected = MDMValidationException.class)
  public void testNullFields() throws Exception {
    EVN.Builder builder = new EVN.Builder();
    builder.recordedDateTime(null);
    builder.build();
  }


}
