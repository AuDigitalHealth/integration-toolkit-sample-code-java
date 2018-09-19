package au.gov.nehta.vendorlibrary.mdm.test;

import au.gov.nehta.vendorlibrary.mdm.datatypes.CX;
import au.gov.nehta.vendorlibrary.mdm.util.MDMConstants;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidationException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CXTest {

  @Test
  public void testParseIdAaIdT() throws Exception {
    String sample1 = "8921319895^^^AUSHIC^MC";
    Assert.assertEquals(sample1, CX.parse(sample1).toString());
  }

  @Test
  public void testParseIdAa() throws Exception {
    String sample1 = "67^^^LOCAL";
    String sample2 = "67^^^LOCAL^";
    Assert.assertEquals(sample1, CX.parse(sample1).toString());
    Assert.assertEquals(sample1, CX.parse(sample2).toString());
  }

  @Test
  public void testParseId() throws Exception {
    String sample1 = "8921319895";
    String sample2 = "8921319895^^^^";
    Assert.assertEquals(sample1, CX.parse(sample1).toString());
    Assert.assertEquals(sample1, CX.parse(sample2).toString());
  }

  @Test
  public void testParseMultiple() throws Exception {
    String sample1 = "67^^^LOCAL^~8921319895^^^AUSHIC^MC~8003600000022222^^^AUSHIC^NI";
    String subSample1 = "67^^^LOCAL^".replaceAll(String.format("\\%s*$", MDMConstants.VALUE_SEPARATOR), "");
    String subSample2 = "8921319895^^^AUSHIC^MC";
    String subSample3 = "8003600000022222^^^AUSHIC^NI";
    List<CX> cxs = CX.parseMultiple(sample1, MDMConstants.ID_SEPARATOR);
    Assert.assertNotNull(cxs);
    Assert.assertEquals(3, cxs.size());
    System.out.println(cxs.get(0).toString());
    Assert.assertEquals(subSample1, cxs.get(0).toString());
    Assert.assertEquals(subSample2, cxs.get(1).toString());
    Assert.assertEquals(subSample3, cxs.get(2).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseNull() throws Exception {
    String sample1 = null;
    Assert.assertEquals(sample1, CX.parse(sample1).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseInvalidFieldNumber() throws Exception {
    String sample1 = "8921319895^^^AUSHIC^MC^123";
    CX.parse(sample1);
  }

  @Test
  public void testGetIdentifier() throws Exception {
    String sample1 = "8921319895^^^AUSHIC^MC";
    CX cx = CX.parse(sample1);
    Assert.assertEquals("8921319895", cx.getIdentifier());
  }

  @Test
  public void testGetAssigningAuthority() throws Exception {
    String sample1 = "8921319895^^^AUSHIC^MC";
    CX cx = CX.parse(sample1);
    Assert.assertEquals("AUSHIC", cx.getAssigningAuthority().toString());
  }

  @Test
  public void testGetIdentifierTypeCode() throws Exception {
    String sample1 = "8921319895^^^AUSHIC^MC";
    CX cx = CX.parse(sample1);
    Assert.assertEquals("MC", cx.getIdentifierTypeCode());
  }

  @Test (expected = MDMValidationException.class)
  public void testNullCXValues() throws Exception {
    CX.Builder builder = new CX.Builder();
    builder.identifier(null);
    builder.assigningAuthority(null);
    builder.identifierTypeCode(null);
    builder.build();
  }
}
