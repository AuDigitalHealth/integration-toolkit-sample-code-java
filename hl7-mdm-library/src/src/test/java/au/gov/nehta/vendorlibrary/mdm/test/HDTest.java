package au.gov.nehta.vendorlibrary.mdm.test;

import au.gov.nehta.vendorlibrary.mdm.datatypes.HD;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidationException;
import org.junit.Assert;
import org.junit.Test;

public class HDTest {

  @Test
  public void testParseNsIdIdT() throws Exception {
    String sample1 = "8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO";
    Assert.assertEquals(sample1, HD.parse(sample1).toString());
  }

  @Test
  public void testParseNs() throws Exception {
    String sample1 = "8003615833334118";
    String sample2 = "8003615833334118^";
    String sample3 = "8003615833334118^^";
    Assert.assertEquals(sample1, HD.parse(sample1).toString());
    Assert.assertEquals(sample1, HD.parse(sample2).toString());
    Assert.assertEquals(sample1, HD.parse(sample3).toString());
  }

  @Test
  public void testParseNsId() throws Exception {
    String sample1 = "8003615833334118^1.2.36.1.2001.1003.0.8003615833334118";
    String sample2 = "8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^";
    Assert.assertEquals(sample1, HD.parse(sample1).toString());
    Assert.assertEquals(sample1, HD.parse(sample2).toString());
  }

  @Test
  public void testParseNsIdT() throws Exception {
    String sample1 = "8003615833334118^^ISO";
    Assert.assertEquals(sample1, HD.parse(sample1).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseInvalidFieldNumber() throws Exception {
    String sample1 = "8003615833334118^^ISO^SomethingElse";
    HD.parse(sample1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseNull() throws Exception {
    String sample1 = null;
    HD.parse(sample1);
  }

  @Test
  public void testGetNamespace() throws Exception {
    String sample1 = "8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO";
    HD hd = HD.parse(sample1);
    Assert.assertEquals("8003615833334118", hd.getNamespace());
  }

  @Test
  public void testGetIdentifier() throws Exception {
    String sample1 = "8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO";
    HD hd = HD.parse(sample1);
    Assert.assertEquals("1.2.36.1.2001.1003.0.8003615833334118", hd.getIdentifier());
  }

  @Test
  public void testGetIdentifierType() throws Exception {
    String sample1 = "8003615833334118^1.2.36.1.2001.1003.0.8003615833334118^ISO";
    HD hd = HD.parse(sample1);
    Assert.assertEquals("ISO", hd.getIdentifierType());
  }

  @Test (expected = MDMValidationException.class)
  public void testNullField() throws Exception {
    HD.Builder builder = new HD.Builder();
    builder.identifier(null);
    builder.build();
  }
}
