package au.gov.nehta.vendorlibrary.mdm.test;

import au.gov.nehta.vendorlibrary.mdm.enums.PatientClass;
import au.gov.nehta.vendorlibrary.mdm.segments.PV1;
import au.gov.nehta.vendorlibrary.mdm.util.MDMConstants;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidationException;
import org.junit.Assert;
import org.junit.Test;

public class PV1Test {
  @Test
  public void testGetSetId() throws Exception {
    String sample1 = "PV1|1|N|||||||0302523B^Doctor^Good^^^Dr^^^AUSHICPR";
    PV1 pv1 = PV1.parse(sample1);
    Assert.assertEquals(1, pv1.getSetId());
  }

  @Test
  public void testGetPatientClass() throws Exception {
    String sample1 = "PV1|1|N|||||||0302523B^Doctor^Good^^^Dr^^^AUSHICPR";
    PV1 pv1 = PV1.parse(sample1);
    Assert.assertEquals(PatientClass.N, pv1.getPatientClass());
  }

  @Test
  public void testGetConsultingDoctor() throws Exception {
    String sample1 = "PV1|1|N|||||||0302523B^Doctor^Good^^^Dr^^^AUSHICPR";
    PV1 pv1 = PV1.parse(sample1);
    Assert.assertEquals("0302523B^Doctor^Good^^^Dr^^^AUSHICPR", pv1.getConsultingDoctor().toString());
  }

  @Test
  public void testToString() throws Exception {
    String sample1 = "PV1|1|N|||||||0302523B^Doctor^Good^^^Dr^^^AUSHICPR";
    PV1 pv1 = PV1.parse(sample1);
    Assert.assertEquals(String.format("%s%s", sample1, MDMConstants.SEGMENT_TERMINATOR), pv1.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseInvalidSegmentName() throws Exception {
    String sample1 = "SomethingElse|1|N|||||||0302523B^Doctor^Good^^^Dr^^^AUSHICPR";
    PV1.parse(sample1);
  }

  // TODO: This restriction has been relaxed and as such, no exception is expected.
  @Test //(expected = IllegalArgumentException.class)
  public void testParseInvalidFieldCount() throws Exception {
    String sample1 = "PV1|1|N|||||||0302523B^Doctor^Good^^^Dr^^^AUSHICPR|SomethingElse";
    PV1.parse(sample1);
  }

  @Test(expected = MDMValidationException.class)
  public void testNullFields() throws Exception {
    PV1.Builder builder = new PV1.Builder();
    builder.consultingDoctor(null);
    builder.build();
  }

}
