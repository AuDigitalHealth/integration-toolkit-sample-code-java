package au.gov.nehta.vendorlibrary.mdm.test;

import au.gov.nehta.vendorlibrary.mdm.datatypes.HD;
import au.gov.nehta.vendorlibrary.mdm.datatypes.XCN;
import org.junit.Assert;
import org.junit.Test;

public class XCNTest {
  @Test
  public void testGetIdentifier() throws Exception {
    String sample1 = "0302523B^Doctor^Good^Man^III^Dr^^^AUSHICPR";
    XCN xcn = XCN.parse(sample1);
    Assert.assertEquals("0302523B", xcn.getIdentifier());
  }

  @Test
  public void testGetFamilyName() throws Exception {
    String sample1 = "0302523B^Doctor^Good^Man^III^Dr^^^AUSHICPR";
    XCN xcn = XCN.parse(sample1);
    Assert.assertEquals("Doctor", xcn.getFamilyName());
  }

  @Test
  public void testGetGivenName() throws Exception {
    String sample1 = "0302523B^Doctor^Good^Man^III^Dr^^^AUSHICPR";
    XCN xcn = XCN.parse(sample1);
    Assert.assertEquals("Good", xcn.getGivenName());
  }

  @Test
  public void testGetMiddleInitialOrName() throws Exception {
    String sample1 = "0302523B^Doctor^Good^Man^III^Dr^^^AUSHICPR";
    XCN xcn = XCN.parse(sample1);
    Assert.assertEquals("Man", xcn.getMiddleInitialOrName());
  }

  @Test
  public void testGetSuffix() throws Exception {
    String sample1 = "0302523B^Doctor^Good^Man^III^Dr^^^AUSHICPR";
    XCN xcn = XCN.parse(sample1);
    Assert.assertEquals("III", xcn.getSuffix());
  }

  @Test
  public void testGetPrefix() throws Exception {
    String sample1 = "0302523B^Doctor^Good^Man^III^Dr^^^AUSHICPR";
    XCN xcn = XCN.parse(sample1);
    Assert.assertEquals("Dr", xcn.getPrefix());
  }

  @Test
  public void testGetAssigningAuthority() throws Exception {
    String sample1 = "0302523B^Doctor^Good^Man^III^Dr^^^AUSHICPR";
    XCN xcn = XCN.parse(sample1);
    Assert.assertEquals("AUSHICPR", xcn.getAssigningAuthority().toString());
  }

  @Test
  public void testGetIdentifierTypeCode() throws Exception {
    String sample1 = "800361xxxxxxxxxx^Doctor^Good^^^Dr^^^AUSHIC^^^^NPI";
    XCN xcn = XCN.parse(sample1);
    Assert.assertEquals("NPI", xcn.getIdentifierTypeCode());
  }

  @Test
  public void testParseIdFnGn() throws Exception {
    String sample1 = "0302523B^Doctor^Good^Man^III^Dr^^^AUSHICPR";
    Assert.assertEquals(sample1, XCN.parse(sample1).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseNull() throws Exception {
    String sample1 = null;
    XCN.parse(sample1);
  }

  @Test
  public void testParseEmpty() throws Exception {
    String sample1 = "";
    XCN xcn = XCN.parse(sample1);
    System.out.println("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseInvalidFieldNumber() throws Exception {
    String sample1 = "800361xxxxxxxxxx^Doctor^Good^Man^III^Dr^^^AUSHICPR^^^^NPI^SomethingElse";
    XCN.parse(sample1);
  }

  @Test
  public void testEmptyIdentifier() throws Exception {
//    "0302523B^Doctor^Good^Man^III^Dr^^^AUSHICPR""
    XCN.Builder builder = new XCN.Builder();
    builder.familyName("Doctor");
    builder.givenName("Good");
    builder.middleInitialOrName("Man");
    builder.suffix("III");
    builder.prefix("Dr");
    builder.assigningAuthority(new HD.Builder().namespace("AUSHICPR").build());
    builder.build();
  }
}
