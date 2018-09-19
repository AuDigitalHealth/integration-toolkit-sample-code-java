package au.gov.nehta.vendorlibrary.mdm.test;

import au.gov.nehta.vendorlibrary.mdm.enums.DocumentCompletionStatus;
import au.gov.nehta.vendorlibrary.mdm.enums.DocumentContentPresentation;
import au.gov.nehta.vendorlibrary.mdm.enums.DocumentType;
import au.gov.nehta.vendorlibrary.mdm.segments.TXA;
import au.gov.nehta.vendorlibrary.mdm.util.MDMConstants;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidationException;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class TXATest {
  @Test
  public void testGetSetId() throws Exception {
    String sample1 = "TXA|1|NEHTA|AP|20120930131036||||||||449ee02f-6c06-4c6d-b7e9-2e093af45ed5||||PACKAGE.ZIP|LA";
    TXA txa = TXA.parse(sample1);
    Assert.assertEquals(1, txa.getSetId());
  }

  @Test
  public void testGetDocumentType() throws Exception {
    String sample1 = "TXA|1|NEHTA|AP|20120930131036||||||||449ee02f-6c06-4c6d-b7e9-2e093af45ed5||||PACKAGE.ZIP|LA";
    TXA txa = TXA.parse(sample1);
    Assert.assertEquals(DocumentType.NEHTA, txa.getDocumentType());
  }

  @Test
  public void testGetDocumentContentPresentation() throws Exception {
    String sample1 = "TXA|1|NEHTA|AP|20120930131036||||||||449ee02f-6c06-4c6d-b7e9-2e093af45ed5||||PACKAGE.ZIP|LA";
    TXA txa = TXA.parse(sample1);
    Assert.assertEquals(DocumentContentPresentation.AP, txa.getDocumentContentPresentation());
  }

  @Test
  public void testGetActivityDateTime() throws Exception {
    String sample1 = "TXA|1|NEHTA|AP|20120930131036||||||||449ee02f-6c06-4c6d-b7e9-2e093af45ed5||||PACKAGE.ZIP|LA";
    TXA txa = TXA.parse(sample1);
    Assert.assertEquals("20120930131036", new SimpleDateFormat("yyyyMMddHHmmss").format(txa.getActivityDateTime()));
  }

  @Test
  public void testGetUniqueDocumentNumber() throws Exception {
    String sample1 = "TXA|1|NEHTA|AP|20120930131036||||||||449ee02f-6c06-4c6d-b7e9-2e093af45ed5||||PACKAGE.ZIP|LA";
    TXA txa = TXA.parse(sample1);
    Assert.assertEquals(UUID.fromString("449ee02f-6c06-4c6d-b7e9-2e093af45ed5"), txa.getUniqueDocumentNumber());
  }

  @Test
  public void testGetUniqueDocumentFileName() throws Exception {
    String sample1 = "TXA|1|NEHTA|AP|20120930131036||||||||449ee02f-6c06-4c6d-b7e9-2e093af45ed5||||PACKAGE.ZIP|LA";
    TXA txa = TXA.parse(sample1);
    Assert.assertEquals("PACKAGE.ZIP", txa.getUniqueDocumentFileName());
  }

  @Test
  public void testGetDocumentCompletionStatus() throws Exception {
    String sample1 = "TXA|1|NEHTA|AP|20120930131036||||||||449ee02f-6c06-4c6d-b7e9-2e093af45ed5||||PACKAGE.ZIP|LA";
    TXA txa = TXA.parse(sample1);
    Assert.assertEquals(DocumentCompletionStatus.LA, txa.getDocumentCompletionStatus());
  }

  @Test
  public void testToString() throws Exception {
    String sample1 = "TXA|1|NEHTA|AP|20120930131036||||||||449ee02f-6c06-4c6d-b7e9-2e093af45ed5||||PACKAGE.ZIP|LA";
    TXA txa = TXA.parse(sample1);
    Assert.assertEquals(sample1 + MDMConstants.SEGMENT_TERMINATOR, txa.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseInvalidDate() throws Exception {
    String sample1 = "TXA|1|NEHTA|AP|Q||||||||449ee02f-6c06-4c6d-b7e9-2e093af45ed5||||PACKAGE.ZIP|LA";
    TXA.parse(sample1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseInvalidSegmentName() throws Exception {
    String sample1 = "SomethingElse|1|NEHTA|AP|20120930131036||||||||449ee02f-6c06-4c6d-b7e9-2e093af45ed5||||PACKAGE.ZIP|LA";
    TXA.parse(sample1);
  }

  // TODO: This restriction has been relaxed and as such, no exception is expected.
  @Test//(expected = IllegalArgumentException.class)
  public void testParseInvalidFieldCount() throws Exception {
    String sample1 = "TXA|1|NEHTA|AP|20120930131036||||||||449ee02f-6c06-4c6d-b7e9-2e093af45ed5||||PACKAGE.ZIP|LA|SomethingElse";
    TXA.parse(sample1);
  }

  @Test(expected = MDMValidationException.class)
  public void testNullFields() throws Exception {
    TXA.Builder builder = new TXA.Builder();
    builder.activityDateTime(null);
    builder.build();

  }

}
