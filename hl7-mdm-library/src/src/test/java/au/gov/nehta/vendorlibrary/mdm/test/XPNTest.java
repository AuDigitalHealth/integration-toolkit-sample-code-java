package au.gov.nehta.vendorlibrary.mdm.test;

import au.gov.nehta.vendorlibrary.mdm.datatypes.XPN;
import au.gov.nehta.vendorlibrary.mdm.util.MDMConstants;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidationException;
import org.junit.Assert;
import org.junit.Test;

public class XPNTest {
  @Test
  public void testGetFamilyName() throws Exception {
    String sample1 = "Grant^Sally^Middle^Esq^Dr";
    XPN xpn = XPN.parse(sample1);
    Assert.assertEquals("Grant", xpn.getFamilyName());
  }

  @Test
  public void testGetGivenName() throws Exception {
    String sample1 = "Grant^Sally^Middle^Esq^Dr";
    XPN xpn = XPN.parse(sample1);
    Assert.assertEquals("Sally", xpn.getGivenName());
  }

  @Test
  public void testGetMiddleInitialOrName() throws Exception {
    String sample1 = "Grant^Sally^Middle^Esq^Dr";
    XPN xpn = XPN.parse(sample1);
    Assert.assertEquals("Middle", xpn.getMiddleInitialOrName());
  }

  @Test
  public void testGetSuffix() throws Exception {
    String sample1 = "Grant^Sally^Middle^Esq^Dr";
    XPN xpn = XPN.parse(sample1);
    Assert.assertEquals("Esq", xpn.getSuffix());
  }

  @Test
  public void testGetPrefix() throws Exception {
    String sample1 = "Grant^Sally^Middle^Esq^Dr";
    XPN xpn = XPN.parse(sample1);
    Assert.assertEquals("Dr", xpn.getPrefix());
  }

  @Test
  public void testParseFnGnMnSP() throws Exception {
    String sample1 = "Grant^Sally^Middle^Esq^Dr";
    Assert.assertEquals(sample1, XPN.parse(sample1).toString());
  }

  @Test
  public void testParseFnGn() throws Exception {
    String sample1 = "Grant^Sally^^";
    Assert.assertEquals(sample1.replaceAll(String.format("\\%s*$", MDMConstants.VALUE_SEPARATOR), ""), XPN.parse(sample1).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseInvalidFieldNumber() throws Exception {
    String sample1 = "Grant^Sally^Middle^Esq^Dr^SomethingElse";
    XPN.parse(sample1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseNull() throws Exception {
    String sample1 = null;
    XPN.parse(sample1);
  }

  @Test(expected = MDMValidationException.class)
  public void testNullFields() throws Exception {
    XPN.Builder builder = new XPN.Builder();
    builder.familyName(null);
    builder.build();
  }

  @Test
  public void testParseEmpty() throws Exception {
    String sample1 = "";
    XPN.parse(sample1);
  }
}
