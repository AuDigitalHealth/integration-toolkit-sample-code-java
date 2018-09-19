import au.gov.nehta.vendorlibrary.clinicalpackage.core.Member;
import au.gov.nehta.vendorlibrary.clinicalpackage.core.SubmissionSet;
import au.gov.nehta.vendorlibrary.clinicalpackage.enums.UriTypes;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.MemberException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class SubmissionSetTest {

  SubmissionSet.Builder builder;

  @After
  public void tearDown() throws Exception {
    builder = null;
  }

  @Test
  public void testRootDocumentMemberAddByFileContent() throws Exception {
    new SubmissionSet.Builder().rootDocument("root fileContent".getBytes()).attachment("attachment1.txt", "attachment1.txt".getBytes()).build();
  }

  @Test
  public void testRootDocumentMemberAddByMember() throws Exception {
    new SubmissionSet.Builder().rootDocument("root content".getBytes()).attachment("attachment1.txt", "attachment1.txt".getBytes()).build();
  }

  @Test(expected = MemberException.class)
  public void testNullRootDocument() throws Exception {
    new SubmissionSet.Builder().rootDocument(null).attachment("attachment1.txt", "attachment1.txt".getBytes()).build();
  }

  @Test
  public void testGetRootDocument() throws Exception {
    Member root1 = new Member.Builder().uri(UriTypes.ROOT_DOCUMENT.getUri()).fileContent("root fileContent".getBytes()).build();
    SubmissionSet submissionSet = new SubmissionSet.Builder().rootDocument("root fileContent".getBytes()).attachment("attachment1.txt", "attachment1.txt".getBytes()).build();
    Assert.assertEquals(root1, submissionSet.getRootDocument());
  }

  @Test
  public void testGetAttachments() throws Exception {
    Member attachment = new Member.Builder().uri("attachment1.txt").fileContent("attachment1.txt".getBytes()).build();
    SubmissionSet submissionSet = new SubmissionSet.Builder().rootDocument("root fileContent".getBytes()).attachment("attachment1.txt", "attachment1.txt".getBytes()).build();
    Assert.assertEquals(1, submissionSet.getAttachments().size());
    Assert.assertEquals(attachment, submissionSet.getAttachments().get(0));
  }

  @Test
  public void testHashCode() throws Exception {
    SubmissionSet submissionSet1 = new SubmissionSet.Builder().rootDocument("filecontent".getBytes()).build();
    SubmissionSet submissionSet2 = new SubmissionSet.Builder().rootDocument("filecontent".getBytes()).build();
    Assert.assertEquals(submissionSet1.hashCode(), submissionSet2.hashCode());
  }
}
