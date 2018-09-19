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

public class MetadataUtilsTimeTest {

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
	 public void testPrescriptionTime() throws XPathExpressionException, ParserConfigurationException, IOException, SAXException{
		 String doc = IOUtils.read( new File("src/test/resources/TestFiles/metadataTest/PCEHRPrescriptionRecord_3A_Min.xml") );
		 DocumentMetadata documentMetadata = MetadataUtils.toDocumentMetadata( exampleHeader, doc.getBytes() );
		 //actuall time 20130226155637+1000
		 assertEquals("20130226055637",documentMetadata.getServiceStartTime());
		 assertEquals("20130226055637",documentMetadata.getServiceStopTime());
	 }
	 
	 @Test
	 public void testDispenseTime() throws XPathExpressionException, ParserConfigurationException, IOException, SAXException{
		 String doc = IOUtils.read( new File("src/test/resources/TestFiles/metadataTest/PCEHRDispenseRecord_3A_Min.xml") );
		 DocumentMetadata documentMetadata = MetadataUtils.toDocumentMetadata( exampleHeader, doc.getBytes() );
		 
		 ///actual time 20130226115638+1000
		 assertEquals("20130226015638",documentMetadata.getServiceStartTime());
		 assertEquals("20130226015638",documentMetadata.getServiceStopTime());
	 }

}
