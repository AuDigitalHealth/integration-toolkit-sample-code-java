package au.gov.nehta.vendorlibrary.mdm.test;

import au.gov.nehta.vendorlibrary.mdm.enums.AcknowledgementType;
import au.gov.nehta.vendorlibrary.mdm.enums.CountryCode;
import au.gov.nehta.vendorlibrary.mdm.enums.ProcessingId;
import au.gov.nehta.vendorlibrary.mdm.segments.MSH;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidationException;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;

public class MSHTest {
  @Test
  public void testGetEncodingCharacters() throws Exception {
    String sample1 = "MSH|^~\\&|Burrill Lake Medical Centre|8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO|Foot Rehab Department|800362000022222^1.2.36.1.2001.1003.0.800362000022222^ISO|20120930131036+1000||MDM^T02^MDM_T02|NEHTA_0000000001|P|2.3.1|||NE|AL|AUS";
    MSH msh = MSH.parse(sample1);
    Assert.assertEquals("^~\\&", msh.getEncodingCharacters());
  }

  @Test
  public void testGetSendingApplication() throws Exception {
    String sample1 = "MSH|^~\\&|Burrill Lake Medical Centre|8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO|Foot Rehab Department|800362000022222^1.2.36.1.2001.1003.0.800362000022222^ISO|20120930131036+1000||MDM^T02^MDM_T02|NEHTA_0000000001|P|2.3.1|||NE|AL|AUS";
    MSH msh = MSH.parse(sample1);
    Assert.assertEquals("Burrill Lake Medical Centre", msh.getSendingApplication().toString());
  }

  @Test
  public void testGetSendingFacility() throws Exception {
    String sample1 = "MSH|^~\\&|Burrill Lake Medical Centre|8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO|Foot Rehab Department|800362000022222^1.2.36.1.2001.1003.0.800362000022222^ISO|20120930131036+1000||MDM^T02^MDM_T02|NEHTA_0000000001|P|2.3.1|||NE|AL|AUS";
    MSH msh = MSH.parse(sample1);
    Assert.assertEquals("8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO", msh.getSendingFacility().toString());
  }

  @Test
  public void testGetReceivingApplication() throws Exception {
    String sample1 = "MSH|^~\\&|Burrill Lake Medical Centre|8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO|Foot Rehab Department|800362000022222^1.2.36.1.2001.1003.0.800362000022222^ISO|20120930131036+1000||MDM^T02^MDM_T02|NEHTA_0000000001|P|2.3.1|||NE|AL|AUS";
    MSH msh = MSH.parse(sample1);
    Assert.assertEquals("Foot Rehab Department", msh.getReceivingApplication().toString());
  }

  @Test
  public void testGetReceivingFacility() throws Exception {
    String sample1 = "MSH|^~\\&|Burrill Lake Medical Centre|8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO|Foot Rehab Department|800362000022222^1.2.36.1.2001.1003.0.800362000022222^ISO|20120930131036+1000||MDM^T02^MDM_T02|NEHTA_0000000001|P|2.3.1|||NE|AL|AUS";
    MSH msh = MSH.parse(sample1);
    Assert.assertEquals("800362000022222^1.2.36.1.2001.1003.0.800362000022222^ISO", msh.getReceivingFacility().toString());
  }

  @Test
  public void testGetMessageDateTime() throws Exception {
    String sample1 = "MSH|^~\\&|Burrill Lake Medical Centre|8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO|Foot Rehab Department|800362000022222^1.2.36.1.2001.1003.0.800362000022222^ISO|20120930131036+1000||MDM^T02^MDM_T02|NEHTA_0000000001|P|2.3.1|||NE|AL|AUS";
    MSH msh = MSH.parse(sample1);
    Assert.assertEquals("20120930131036+1000", new SimpleDateFormat("yyyyMMddHHmmssZ").format(msh.getMessageDateTime()));
  }

  @Test
  public void testGetMessageType() throws Exception {
    String sample1 = "MSH|^~\\&|Burrill Lake Medical Centre|8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO|Foot Rehab Department|800362000022222^1.2.36.1.2001.1003.0.800362000022222^ISO|20120930131036+1000||MDM^T02^MDM_T02|NEHTA_0000000001|P|2.3.1|||NE|AL|AUS";
    MSH msh = MSH.parse(sample1);
    Assert.assertEquals("MDM^T02^MDM_T02", msh.getMessageType());
  }

  @Test
  public void testGetMessageControlId() throws Exception {
    String sample1 = "MSH|^~\\&|Burrill Lake Medical Centre|8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO|Foot Rehab Department|800362000022222^1.2.36.1.2001.1003.0.800362000022222^ISO|20120930131036+1000||MDM^T02^MDM_T02|NEHTA_0000000001|P|2.3.1|||NE|AL|AUS";
    MSH msh = MSH.parse(sample1);
    Assert.assertEquals("NEHTA_0000000001", msh.getMessageControlId());
  }

  @Test
  public void testGetProcessingId() throws Exception {
    String sample1 = "MSH|^~\\&|Burrill Lake Medical Centre|8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO|Foot Rehab Department|800362000022222^1.2.36.1.2001.1003.0.800362000022222^ISO|20120930131036+1000||MDM^T02^MDM_T02|NEHTA_0000000001|P|2.3.1|||NE|AL|AUS";
    MSH msh = MSH.parse(sample1);
    Assert.assertEquals(ProcessingId.P, msh.getProcessingId());
  }

  @Test
  public void testGetVersionId() throws Exception {
    String sample1 = "MSH|^~\\&|Burrill Lake Medical Centre|8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO|Foot Rehab Department|800362000022222^1.2.36.1.2001.1003.0.800362000022222^ISO|20120930131036+1000||MDM^T02^MDM_T02|NEHTA_0000000001|P|2.3.1|||NE|AL|AUS";
    MSH msh = MSH.parse(sample1);
    Assert.assertEquals("2.3.1", msh.getVersionId());
  }

  @Test
  public void testGetAcceptAcknowledgementType() throws Exception {
    String sample1 = "MSH|^~\\&|Burrill Lake Medical Centre|8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO|Foot Rehab Department|800362000022222^1.2.36.1.2001.1003.0.800362000022222^ISO|20120930131036+1000||MDM^T02^MDM_T02|NEHTA_0000000001|P|2.3.1|||NE|AL|AUS";
    MSH msh = MSH.parse(sample1);
    Assert.assertEquals(AcknowledgementType.NE, msh.getAcceptAcknowledgementType());
  }

  @Test
  public void testGetApplicationAcknowledgementType() throws Exception {
    String sample1 = "MSH|^~\\&|Burrill Lake Medical Centre|8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO|Foot Rehab Department|800362000022222^1.2.36.1.2001.1003.0.800362000022222^ISO|20120930131036+1000||MDM^T02^MDM_T02|NEHTA_0000000001|P|2.3.1|||NE|AL|AUS";
    MSH msh = MSH.parse(sample1);
    Assert.assertEquals(AcknowledgementType.AL, msh.getApplicationAcknowledgementType());
  }

  @Test
  public void testGetCountryCode() throws Exception {
    String sample1 = "MSH|^~\\&|Burrill Lake Medical Centre|8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO|Foot Rehab Department|800362000022222^1.2.36.1.2001.1003.0.800362000022222^ISO|20120930131036+1000||MDM^T02^MDM_T02|NEHTA_0000000001|P|2.3.1|||NE|AL|AUS";
    MSH msh = MSH.parse(sample1);
    Assert.assertEquals(CountryCode.AUSTRALIA, msh.getCountryCode());
  }

  @Test
  public void testParseValid() throws Exception {
    String sample1 = "MSH|^~\\&|Burrill Lake Medical Centre|8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO|Foot Rehab Department|800362000022222^1.2.36.1.2001.1003.0.800362000022222^ISO|20120930131036+1000||MDM^T02^MDM_T02|NEHTA_0000000001|P|2.3.1|||NE|AL|AUS\r";
    MSH msh = MSH.parse(sample1);
    Assert.assertEquals(sample1, msh.toString());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testParseInvalidSegmentName() throws Exception {
    String sample1 = "SomethingElse|^~\\&|Burrill Lake Medical Centre|8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO|Foot Rehab Department|800362000022222^1.2.36.1.2001.1003.0.800362000022222^ISO|20120930131036+1000||MDM^T02^MDM_T02|NEHTA_0000000001|P|2.3.1|||NE|AL|AUS\r";
    MSH msh = MSH.parse(sample1);
    Assert.assertEquals(sample1, msh.toString());
  }

  @Test
  public void testParseUnspecifiedCountryCode() throws Exception {
    String sample1 = "MSH|^~\\&|Burrill Lake Medical Centre|8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO|Foot Rehab Department|800362000022222^1.2.36.1.2001.1003.0.800362000022222^ISO|20120930131036+1000||MDM^T02^MDM_T02|NEHTA_0000000001|P|2.3.1|||NE|AL|SS";
    MSH msh = MSH.parse(sample1);
    Assert.assertEquals(CountryCode.AUSTRALIA, msh.getCountryCode());
  }

  // TODO: This restriction has been relaxed and as such, no exception is expected.
  @Test//(expected = IllegalArgumentException.class)
  public void testParseInvalidFieldCount() throws Exception {
    String sample1 = "MSH|^~\\&|Burrill Lake Medical Centre|8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO|Foot Rehab Department|800362000022222^1.2.36.1.2001.1003.0.800362000022222^ISO|20120930131036+1000||MDM^T02^MDM_T02|NEHTA_0000000001|P|2.3.1|||NE|AL|AUS|SomethingElse";
    MSH.parse(sample1);
  }

  @Test(expected = MDMValidationException.class)
  public void testNullFields() throws Exception {
    MSH.Builder builder = new MSH.Builder();
    builder.sendingFacility(null);
    builder.receivingFacility(null);
    builder.build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseInvalidDate() throws Exception {
    String sample1 = "MSH|^~\\&|Burrill Lake Medical Centre|8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO|Foot Rehab Department|800362000022222^1.2.36.1.2001.1003.0.800362000022222^ISO|201||MDM^T02^MDM_T02|NEHTA_0000000001|P|2.3.1|||NE|AL|AUS";
    MSH.parse(sample1);
  }
}
