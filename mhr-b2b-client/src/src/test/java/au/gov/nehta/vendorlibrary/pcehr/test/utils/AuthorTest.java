package au.gov.nehta.vendorlibrary.pcehr.test.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.Author;

public class AuthorTest {

	@Test
	public void test_xcninput_validSpaces(){
		Author a = new Author("^abcd ef^ghij'k^^^lm nop^^^&qrstuv&ISO");
		assertEquals(a.getFamilyName(),"abcd ef");
		assertEquals(a.getGivenName(),"ghij'k");
		assertEquals(a.getPrefix(),"lm nop");
		assertEquals(a.getHpii(),"qrstuv");
	}
	
	@Test
	public void test_xcninput_valid(){
		Author a = new Author("^abcdef^ghijk^^^lmnop^^^&qrstuv&ISO");
		assertEquals(a.getFamilyName(),"abcdef");
		assertEquals(a.getGivenName(),"ghijk");
		assertEquals(a.getPrefix(),"lmnop");
		assertEquals(a.getHpii(),"qrstuv");
	}
	
	@Test
	public void test_xcninput_valid_no_fn(){
		Author a = new Author("^^ghijk^^^lmnop^^^&qrstuv&ISO");
		assertEquals(a.getFamilyName(),"");
		assertEquals(a.getGivenName(),"ghijk");
		assertEquals(a.getPrefix(),"lmnop");
		assertEquals(a.getHpii(),"qrstuv");
	}
	
	
	@Test
	public void test_xcninput_valid_no_gn(){
		Author a = new Author("^a^^^^lmnop^^^&qrstuv&ISO");
		assertEquals(a.getFamilyName(),"a");
		assertEquals(a.getGivenName(),"");
		assertEquals(a.getPrefix(),"lmnop");
		assertEquals(a.getHpii(),"qrstuv");
	}
	
	@Test
	public void test_xcninput_valid_no_prefix(){
		Author a = new Author("^a^^^^^^^&qrstuv&ISO");
		assertEquals(a.getFamilyName(),"a");
		assertEquals(a.getGivenName(),"");
		assertEquals(a.getPrefix(),"");
		assertEquals(a.getHpii(),"qrstuv");
	}
	
	@Test
	public void test_xcninput_valid_onlyHPII(){
		Author a = new Author("^^^^^^^^&qrstuv&ISO");
		assertEquals(a.getFamilyName(),"");
		assertEquals(a.getGivenName(),"");
		assertEquals(a.getPrefix(),"");
		assertEquals(a.getHpii(),"qrstuv");
	}
	
	@Test
	public void test_xcninput_valid_big_constructor(){
		Author a = new Author("1","2","3","4");
		assertEquals(a.getFamilyName(),"1");
		assertEquals(a.getGivenName(),"2");
		assertEquals(a.getPrefix(),"3");
		assertEquals(a.getHpii(),"4");
		assertEquals(a.toXCNFormatString(),"^1^2^^^3^^^&1.2.36.1.2001.1003.0.4&ISO");
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void test_xcninput_invalid_tooManyCarets(){
		new Author("^smith^alex^^^Mr^^^^&qrstuv&ISO");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_xcninput_invalid_tooFewCarets(){
		new Author("^smith^alex^^^Mr^^&qrstuv&ISO");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_xcninput_invalid_tooFewAmpersands(){
		new Author("^smith^alex^^^Mr^^^qrstuv&ISO");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_xcninput_invalid_tooFewAmpersands2(){
		new Author("^smith^alex^^^Mr^^^&qrstuvISO");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_xcninput_invalid_noISOEnding(){
		new Author("^smith^alex^^^Mr^^^&qrstuv&i");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_xcninput_invalid_toomanyAmpersands(){
		new Author("^smith^alex^^^Mr^^^&qrst&uv&ISO");
	}
	

}
