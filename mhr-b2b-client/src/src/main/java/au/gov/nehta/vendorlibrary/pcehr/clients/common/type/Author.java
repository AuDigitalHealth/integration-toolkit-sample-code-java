package au.gov.nehta.vendorlibrary.pcehr.clients.common.type;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.Separators;

public class Author {
	public static final String XCN_FORMAT_STRING ="^%s^%s^^^%s^^^&1.2.36.1.2001.1003.0.%s&ISO";
	private String familyName="";
	private String givenName="";
	private String prefix="";
	private String hpii="";
	
	public Author(String familyName, String givenName, String prefix, String hpii){
		this.familyName=familyName;
		this.givenName=givenName;
		this.prefix=prefix;
		this.hpii=hpii;
	}
	
	public Author( String XCNFormattedAuthor ) {
		String[] split = XCNFormattedAuthor.split( "\\^" );
		if(split.length !=9 || !XCNFormattedAuthor.endsWith( "&ISO" )){
			throw new IllegalArgumentException( "XCN not in a valid format" );
		}
		
		
		
		familyName=split[1];
		givenName=split[2];
		prefix=split[5];
		
		String[] hpiiSplit = split[8].split( "&" );
		if(hpiiSplit.length != 3){
			throw new IllegalArgumentException( "XCN not in a valid format" );
		}
		
		hpii=hpiiSplit[1];
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName( String familyName ) {
		this.familyName = familyName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName( String givenName ) {
		this.givenName = givenName;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}

	public String getHpii() {
		return hpii;
	}

	public void setHpii( String hpii ) {
		this.hpii = hpii;
	}

	public String toXCNFormatString() {
		return 	String.format(XCN_FORMAT_STRING,familyName,givenName,prefix,hpii);
	}

}
