import au.gov.nehta.vendorlibrary.clinicalpackage.core.Member;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.MemberException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MemberTest {

  private Member.Builder builder;

  @Before
  public void setUp() throws Exception {
    builder = new Member.Builder();
  }

  @After
  public void tearDown() throws Exception {
    builder = null;
  }

  @Test
  public void testGetUri() throws Exception {
    Member member1 = builder.fileContent("example file content".getBytes()).uri("example.xml").build();
    Assert.assertEquals("example.xml", member1.getUri());
  }

  @Test
  public void testGetFileContent() throws Exception {
    Member member1 = builder.fileContent("example file content".getBytes()).uri("example.xml").build();
    Assert.assertEquals("example file content", new String(member1.getFileContent()));
  }

  @Test(expected = MemberException.class)
  public void testMissingFileContent() throws Exception {
    builder.uri("file.xml").build();
  }

  @Test(expected = MemberException.class)
  public void testNullFileContent() throws Exception {
    builder.uri("file.xml").fileContent(null).build();
  }

  @Test(expected = MemberException.class)
  public void testMissingUri() throws Exception {
    builder.fileContent("example file content".getBytes()).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullUri() throws Exception {
    builder.fileContent("example file content".getBytes()).uri(null).build();
  }


  @Test(expected = IllegalArgumentException.class)
  public void testEmptyUri() throws Exception {
    builder.fileContent("example file content".getBytes()).uri("").build();
  }

  @Test(expected = MemberException.class)
  public void testNullEverything() throws Exception {
    builder.build();
  }

  @Test
  public void testEquals() throws Exception {
    Member member1 = builder.fileContent("example file content".getBytes()).uri("example.xml").build();
    Member member2 = builder.fileContent("example file content".getBytes()).uri("example.xml").build();
    Assert.assertEquals(member1, member2);
  }

  @Test
  public void testHashCode() throws Exception {
    Member member1 = builder.fileContent("example file content".getBytes()).uri("example.xml").build();
    Member member2 = builder.fileContent("example file content".getBytes()).uri("example.xml").build();
    Assert.assertEquals(member1.hashCode(), member2.hashCode());
  }
}
