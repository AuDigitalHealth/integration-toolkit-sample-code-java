package au.gov.nehta.vendorlibrary.mdm.test;

import au.gov.nehta.vendorlibrary.mdm.datatypes.CE;
import au.gov.nehta.vendorlibrary.mdm.enums.CodingSystem;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidationException;
import org.junit.Assert;
import org.junit.Test;

public class CETest {

  @Test
  public void testGetIdentifier() throws Exception {
    String sample1 = "1232134^Identifier for X^LN";
    CE ce = CE.parse(sample1);
    Assert.assertEquals("1232134", ce.getIdentifier());
  }

  @Test
  public void testGetIdentifierEmpty() throws Exception {
    String sample1 = "^Identifier for X^LN";
    CE ce = CE.parse(sample1);
    Assert.assertEquals("", ce.getIdentifier());
  }

  @Test (expected = MDMValidationException.class)
  public void testGetIdentifierNull() throws Exception {
    new CE.Builder().identifier(null).text("Identifier for X").codingSystem(CodingSystem.LN).build();
  }

  @Test
  public void testGetText() throws Exception {
    String sample1 = "1232134^Identifier for X^LN";
    CE ce = CE.parse(sample1);
    Assert.assertEquals("Identifier for X", ce.getText());
  }

  @Test
  public void testGetTextEmpty() throws Exception {
    String sample1 = "1232134^^LN";
    CE ce = CE.parse(sample1);
    Assert.assertEquals("", ce.getText());
  }

  @Test(expected = MDMValidationException.class)
  public void testGetTextNull() throws Exception {
    new CE.Builder().identifier("1232134").text(null).codingSystem(CodingSystem.LN).build();
  }

  @Test
  public void testGetCodingSystem() throws Exception {
    String sample1 = "1232134^Identifier for X^LN";
    CE ce = CE.parse(sample1);
    Assert.assertEquals(CodingSystem.LN, ce.getCodingSystem());
  }


  @Test (expected = IllegalArgumentException.class)
  public void testGetCodingSystemEmpty() throws Exception {
    String sample1 = "1232134^Identifier for X";
    CE ce = CE.parse(sample1);
    Assert.assertEquals("", ce.getText());
  }

  @Test
  public void testParseValid() throws Exception {
    String sample1 = "1232134^Identifier for X^LN";
    CE ce = CE.parse(sample1);
    Assert.assertEquals(sample1, ce.toString());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testParseUndefinedCodingSystem() throws Exception {
    String sample1 = "1232134^Identifier for X^ISO";
    CE.parse(sample1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testParseNull() throws Exception {
    String sample1 = null;
    CE.parse(sample1);
  }
}
