package au.gov.nehta.vendorlibrary.pcehr.test.unittests;

import static junit.framework.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import junit.framework.Assert;

import org.junit.Test;
import org.xml.sax.SAXException;

import au.gov.nehta.common.utils.IOUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentMetadata;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.MetadataUtils;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;

public class MetadataUtilsOrgIDNameTest{

	private static PCEHRHeader exampleHeader=null ;
	static{
		exampleHeader= MessageComponents.createRequest
				      (
				        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
				        "8003602348687628",
				        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
				        PCEHRHeader.ClientSystemType.CIS,
				        MessageComponents.createAccessingOrganisation("8003624166667177", "Medicare305", null)
				      );
	}
	
		 
	 @Test
     public void test_Organisation_CUSTODIAN_NameId() throws XPathExpressionException, ParserConfigurationException, IOException, SAXException{
         String doc = IOUtils.read( new File("src/test/resources/TestFiles/metadataTest/CUSTODIAN_ORG_NAME_TEST.xml") );
         DocumentMetadata documentMetadata = MetadataUtils.toDocumentMetadata( exampleHeader, doc.getBytes() );
         
         assertEquals("General Practice Clinic",documentMetadata.getAuthorInstitution().getOrganisationName() );
         assertEquals("1.2.36.1.2001.1003.0.8003620833333789",documentMetadata.getAuthorInstitution().getOrganisationIdentifier() );


     }
	 
	   
     @Test
     public void test_Organisation_HCF_NameId() throws XPathExpressionException, ParserConfigurationException, IOException, SAXException{
         String doc = IOUtils.read( new File("src/test/resources/TestFiles/metadataTest/HCF_ORG_NAME_TEST.xml") );
         DocumentMetadata documentMetadata = MetadataUtils.toDocumentMetadata( exampleHeader, doc.getBytes() );
         
         assertEquals("West End Healthiness",documentMetadata.getAuthorInstitution().getOrganisationName() );
         assertEquals("1.2.36.1.2001.1003.0.8003620833333782",documentMetadata.getAuthorInstitution().getOrganisationIdentifier() );
     }
     
     
     @Test
     public void test_Organisation_AUTHOR_NameId() throws XPathExpressionException, ParserConfigurationException, IOException, SAXException{
         String doc = IOUtils.read( new File("src/test/resources/TestFiles/metadataTest/Author_ORG_NAME_TEST.xml") );
         DocumentMetadata documentMetadata = MetadataUtils.toDocumentMetadata( exampleHeader, doc.getBytes() );
         
         assertEquals("Author Good Hospital",documentMetadata.getAuthorInstitution().getOrganisationName() );
         assertEquals("1.2.36.1.2001.1003.0.8013620833333787",documentMetadata.getAuthorInstitution().getOrganisationIdentifier() );
     }
}
