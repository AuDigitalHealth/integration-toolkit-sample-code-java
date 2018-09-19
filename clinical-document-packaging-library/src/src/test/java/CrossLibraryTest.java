import au.gov.nehta.vendorlibrary.clinicalpackage.core.SubmissionSet;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.PackagingUtility;
import org.junit.Test;

public class CrossLibraryTest {

  @Test
  public void test_missingAttachments() throws Exception {
    SubmissionSet pkg = PackagingUtility.extractPackage("src/test/resources/CrossLanguageTests/MissingAttachments.zip");
    PackagingUtility.verifyPackage(pkg, null);
  }

  @Test
  public void test_relativeApproverId() throws Exception {
    SubmissionSet pkg = PackagingUtility.extractPackage("src/test/resources/CrossLanguageTests/RelativeApproverId.zip");
    PackagingUtility.verifyPackage(pkg, null);
  }

  @Test
   public void test_SHA256References() throws Exception {
     SubmissionSet pkg = PackagingUtility.extractPackage("src/test/resources/CrossLanguageTests/SHA256References.zip");
     PackagingUtility.verifyPackage(pkg, null);
   }

  @Test
   public void test_validReferences() throws Exception {
     SubmissionSet pkg = PackagingUtility.extractPackage("src/test/resources/CrossLanguageTests/ValidReferences.zip");
     PackagingUtility.verifyPackage(pkg, null);
   }

}
