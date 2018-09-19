package au.gov.nehta.vendorlibrary.mdm.test;

import au.gov.nehta.vendorlibrary.mdm.enums.Sex;
import au.gov.nehta.vendorlibrary.mdm.segments.PID;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidationException;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;

public class PIDTest {
  @Test
  public void testGetSetId() throws Exception {
    String sample1 = "PID|1||67^^^LOCAL^~8921319895^^^AUSHIC^MC~8003600000022222^^^AUSHIC^NI||Grant^Sally^^^Dr||20120202|F|||1 Clinician Street^^Nehtaville^QLD^5555";
    PID pid = PID.parse(sample1);
    Assert.assertEquals(1, pid.getSetId());
  }

  @Test
  public void testGetPatientIdentifiers() throws Exception {
    String sample1 = "PID|1||67^^^LOCAL^~8921319895^^^AUSHIC^MC~8003600000022222^^^AUSHIC^NI||Grant^Sally^^^Dr||20120202|F|||1 Clinician Street^^Nehtaville^QLD^5555";
    PID pid = PID.parse(sample1);
    Assert.assertEquals(3, pid.getPatientIdentifiers().size());
  }

  @Test
  public void testGetPatientName() throws Exception {
    String sample1 = "PID|1||67^^^LOCAL^~8921319895^^^AUSHIC^MC~8003600000022222^^^AUSHIC^NI||Grant^Sally^^^Dr||20120202|F|||1 Clinician Street^^Nehtaville^QLD^5555";
    PID pid = PID.parse(sample1);
    Assert.assertEquals("Grant^Sally^^^Dr", pid.getPatientName().toString());
  }

  @Test
  public void testGetDateTimeOfBirth() throws Exception {
    String sample1 = "PID|1||67^^^LOCAL^~8921319895^^^AUSHIC^MC~8003600000022222^^^AUSHIC^NI||Grant^Sally^^^Dr||20120202|F|||1 Clinician Street^^Nehtaville^QLD^5555";
    PID pid = PID.parse(sample1);
    Assert.assertEquals("20120202", new SimpleDateFormat("yyyyMMdd").format(pid.getDateTimeOfBirth()));
  }

  @Test
  public void testGetSex() throws Exception {
    String sample1 = "PID|1||67^^^LOCAL^~8921319895^^^AUSHIC^MC~8003600000022222^^^AUSHIC^NI||Grant^Sally^^^Dr||20120202|F|||1 Clinician Street^^Nehtaville^QLD^5555";
    PID pid = PID.parse(sample1);
    Assert.assertEquals(Sex.F, pid.getSex());
  }

  @Test
  public void testGetPatientAddress() throws Exception {
    String sample1 = "PID|1||67^^^LOCAL^~8921319895^^^AUSHIC^MC~8003600000022222^^^AUSHIC^NI||Grant^Sally^^^Dr||20120202|F|||1 Clinician Street^^Nehtaville^QLD^5555";
    PID pid = PID.parse(sample1);
    Assert.assertEquals("1 Clinician Street^^Nehtaville^QLD^5555", pid.getPatientAddress().toString());
  }

//  @Ignore
//  @Test
//  public void testToString() throws Exception {
//    String sample1 = "PID|1||67^^^LOCAL^~8921319895^^^AUSHIC^MC~8003600000022222^^^AUSHIC^NI||Grant^Sally^^^Dr||20121001|F|||1 Clinician Street^^Nehtaville^QLD^5555";
//    PID pid = PID.parse(sample1);
//    Assert.assertEquals(sample1 + MDMConstants.SEGMENT_TERMINATOR, pid.toString());
//  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseInvalidSegmentName() throws Exception {
    String sample1 = "SomethingElse|1||67^^^LOCAL^~8921319895^^^AUSHIC^MC~8003600000022222^^^AUSHIC^NI||Grant^Sally^^^Dr||20120202|F|||1 Clinician Street^^Nehtaville^QLD^5555";
    PID.parse(sample1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseInvalidDate() throws Exception {
    String sample1 = "PID|1||67^^^LOCAL^~8921319895^^^AUSHIC^MC~8003600000022222^^^AUSHIC^NI||Grant^Sally^^^Dr||Q|F|||1 Clinician Street^^Nehtaville^QLD^5555";
    PID.parse(sample1);
  }

  // TODO: This restriction has been relaxed and as such, no exception is expected.
  @Test//(expected = IllegalArgumentException.class)
  public void testParseInvalidFieldCount() throws Exception {
    String sample1 = "PID|1||67^^^LOCAL^~8921319895^^^AUSHIC^MC~8003600000022222^^^AUSHIC^NI||Grant^Sally^^^Dr||2012020|F|||1 Clinician Street^^Nehtaville^QLD^5555|SomethingElse";
    PID.parse(sample1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseNoPatientIds() throws Exception {
    String sample1 = "PID|1||||Grant^Sally^^^Dr||2012020|F|||1 Clinician Street^^Nehtaville^QLD^5555";
    PID.parse(sample1);
  }

  @Test(expected = MDMValidationException.class)
  public void testNullFields() throws Exception {
    PID.Builder builder = new PID.Builder();
    builder.patientAddress(null);
    builder.build();
  }
}