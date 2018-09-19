package au.gov.nehta.vendorlibrary.mdm.test;

import au.gov.nehta.vendorlibrary.mdm.datatypes.XAD;
import au.gov.nehta.vendorlibrary.mdm.enums.CountryCode;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidationException;
import org.junit.Assert;
import org.junit.Test;

public class XADTest {
  @Test
  public void testGetFirstLine() throws Exception {
    String sample1 = "1 Clinician Street^Elsewhere Building^Nehtaville^QLD^5555^AUS";
    XAD xad = XAD.parse(sample1);
    Assert.assertEquals("1 Clinician Street", xad.getFirstLine());
  }

  @Test
  public void testGetSecondLine() throws Exception {
    String sample1 = "1 Clinician Street^Elsewhere Building^Nehtaville^QLD^5555^AUS";
    XAD xad = XAD.parse(sample1);
    Assert.assertEquals("Elsewhere Building", xad.getSecondLine());
  }

  @Test
  public void testGetCity() throws Exception {
    String sample1 = "1 Clinician Street^Elsewhere Building^Nehtaville^QLD^5555^AUS";
    XAD xad = XAD.parse(sample1);
    Assert.assertEquals("Nehtaville", xad.getCity());
  }

  @Test
  public void testGetState() throws Exception {
    String sample1 = "1 Clinician Street^Elsewhere Building^Nehtaville^QLD^5555^AUS";
    XAD xad = XAD.parse(sample1);
    Assert.assertEquals("QLD", xad.getState());
  }

  @Test
  public void testGetPostCode() throws Exception {
    String sample1 = "1 Clinician Street^Elsewhere Building^Nehtaville^QLD^5555^AUS";
    XAD xad = XAD.parse(sample1);
    Assert.assertEquals(5555, xad.getPostCode());
  }

  @Test
  public void testGetCountry() throws Exception {
    String sample1 = "1 Clinician Street^Elsewhere Building^Nehtaville^QLD^5555^AUS";
    XAD xad = XAD.parse(sample1);
    Assert.assertEquals(CountryCode.AUSTRALIA.getCode(), xad.getCountry());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseInvalidFieldNumber() throws Exception {
    String sample1 = "1 Clinician Street^Elsewhere Building^Nehtaville^QLD^5555^AUS^SomethingElse";
    XAD.parse(sample1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseNull() throws Exception {
    String sample1 = null;
    XAD.parse(sample1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseEmpty() throws Exception {
    String sample1 = "";
    XAD.parse(sample1);
  }

  @Test
  public void testParseFlSlCitySPcCountry() throws Exception {
    String sample1 = "1 Clinician Street^Elsewhere Building^Nehtaville^QLD^5555^AUS";
    Assert.assertEquals(sample1, XAD.parse(sample1).toString());
  }

  @Test
  public void testParseFlSlCitySPc() throws Exception {
    String sample1 = "1 Clinician Street^Elsewhere Building^Nehtaville^QLD^5555";
    String sample2 = "1 Clinician Street^Elsewhere Building^Nehtaville^QLD^5555^";
    Assert.assertEquals(sample1, XAD.parse(sample1).toString());
    Assert.assertEquals(sample1, XAD.parse(sample2).toString());
  }

  @Test
  public void testParseFlSPc() throws Exception {
    String sample1 = "1 Clinician Street^^^QLD^5555";
    Assert.assertEquals(sample1, XAD.parse(sample1).toString());
  }

  @Test
  public void testParseFlCityPc() throws Exception {
    String sample1 = "1 Clinician Street^^Nehtaville^^5555";
    Assert.assertEquals(sample1, XAD.parse(sample1).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseFlCountry() throws Exception {
    String sample1 = "1 Clinician Street^^^^^Country";
    Assert.assertEquals(sample1, XAD.parse(sample1).toString());
  }

  @Test(expected = MDMValidationException.class)
  public void testNullXADElement() throws Exception {
    XAD.Builder builder = new XAD.Builder();
    builder.firstLine(null);
    builder.secondLine(null);
    builder.city(null);
    builder.state(null);
    builder.postCode(0000);
    builder.country(null);
    builder.build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadPostCodeType() throws Exception {
    String sample1 = "1 Clinician Street^^Nehtaville^^not an int";
    XAD.parse(sample1);
  }

  @Test
  public void testEmptyCountry() throws Exception {
    XAD.Builder builder = new XAD.Builder();
    builder.firstLine("1 Clinician Street");
    builder.secondLine("Nehtaville");
    builder.city("Brisbane");
    builder.state("Queensland");
    builder.postCode(4000);
    builder.build();
  }
}
