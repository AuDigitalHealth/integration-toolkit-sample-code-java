package au.gov.nehta.vendorlibrary.mdm.test;

import au.gov.nehta.vendorlibrary.mdm.enums.DataType;
import au.gov.nehta.vendorlibrary.mdm.enums.Result;
import au.gov.nehta.vendorlibrary.mdm.enums.ValueType;
import au.gov.nehta.vendorlibrary.mdm.segments.OBX;
import au.gov.nehta.vendorlibrary.mdm.util.MDMConstants;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidationException;
import org.junit.Assert;
import org.junit.Test;

public class OBXTest {
  @Test
  public void testGetSetId() throws Exception {
    String sample1 = "OBX|1|ED|18842-5^Discharge Summarization Note^LN||^application^zip^base64^SSBhbSBiYXNlNjQgQ29udGVudA==||||||F";
    OBX obx = OBX.parse(sample1);
    Assert.assertEquals(1, obx.getSetId());
  }

  @Test
  public void testGetValueType() throws Exception {
    String sample1 = "OBX|1|ED|18842-5^Discharge Summarization Note^LN||^application^zip^base64^SSBhbSBiYXNlNjQgQ29udGVudA==||||||F";
    OBX obx = OBX.parse(sample1);
    Assert.assertEquals(ValueType.ED, obx.getValueType());
  }

  @Test
  public void testGetObservationIdentifier() throws Exception {
    String sample1 = "OBX|1|ED|18842-5^Discharge Summarization Note^LN||^application^zip^base64^SSBhbSBiYXNlNjQgQ29udGVudA==||||||F";
    OBX obx = OBX.parse(sample1);
    Assert.assertEquals("18842-5^Discharge Summarization Note^LN", obx.getObservationIdentifier().toString());
  }

  @Test
  public void testGetSourceApplication() throws Exception {
    String sample1 = "OBX|1|ED|18842-5^Discharge Summarization Note^LN||^application^zip^base64^SSBhbSBiYXNlNjQgQ29udGVudA==||||||F";
    OBX obx = OBX.parse(sample1);
    Assert.assertEquals("", obx.getSourceApplication().toString());
  }

  @Test
  public void testGetDataType() throws Exception {
    String sample1 = "OBX|1|ED|18842-5^Discharge Summarization Note^LN||^application^zip^base64^SSBhbSBiYXNlNjQgQ29udGVudA==||||||F";
    OBX obx = OBX.parse(sample1);
    Assert.assertEquals(DataType.ZIP, obx.getDataType());
  }

  @Test
  public void testGetDataEncoding() throws Exception {
    String sample1 = "OBX|1|ED|18842-5^Discharge Summarization Note^LN||^application^zip^base64^SSBhbSBiYXNlNjQgQ29udGVudA==||||||F";
    OBX obx = OBX.parse(sample1);
    Assert.assertEquals("base64", obx.getDataEncoding());
  }

  @Test
  public void testGetEncodedData() throws Exception {
    String sample1 = "OBX|1|ED|18842-5^Discharge Summarization Note^LN||^application^zip^base64^SSBhbSBiYXNlNjQgQ29udGVudA==||||||F";
    OBX obx = OBX.parse(sample1);
    Assert.assertEquals("SSBhbSBiYXNlNjQgQ29udGVudA==", obx.getEncodedData());
  }

  @Test
  public void testGetObservationResult() throws Exception {
    String sample1 = "OBX|1|ED|18842-5^Discharge Summarization Note^LN||^application^zip^base64^SSBhbSBiYXNlNjQgQ29udGVudA==||||||F";
    OBX obx = OBX.parse(sample1);
    Assert.assertEquals(Result.F, obx.getObservationResult());
  }

  @Test
  public void testToString() throws Exception {
    String sample1 = "OBX|1|ED|18842-5^Discharge Summarization Note^LN||^application^zip^base64^SSBhbSBiYXNlNjQgQ29udGVudA==||||||F";
    OBX obx = OBX.parse(sample1);
    Assert.assertEquals(sample1 + MDMConstants.SEGMENT_TERMINATOR, obx.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseInvalidSegmentName() throws Exception {
    String sample1 = "SomethingElse|1|ED|18842-5^Discharge Summarization Note^LN||^application^zip^base64^SSBhbSBiYXNlNjQgQ29udGVudA==||||||F";
    OBX.parse(sample1);
  }

  // TODO: This restriction has been relaxed and as such, no exception is expected.
  @Test //(expected = IllegalArgumentException.class)
  public void testParseInvalidFieldCount() throws Exception {
    String sample1 = "OBX|1|ED|18842-5^Discharge Summarization Note^LN||^application^zip^base64^SSBhbSBiYXNlNjQgQ29udGVudA==||||||F|SomethingElse";
    OBX.parse(sample1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMissingFields() throws Exception {
    String sample1 = "OBX|1|ED|||^application^zip^^SSBhbSBiYXNlNjQgQ29udGVudA==||||||F";
    OBX.parse(sample1);
  }

  @Test(expected = MDMValidationException.class)
  public void testNullFields() throws Exception {
    OBX.Builder builder = new OBX.Builder();
    builder.encodedData(null);
    builder.build();
  }

}
