package au.gov.nehta.vendorlibrary.pcehr.test.unittests;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import au.gov.nehta.common.utils.IOUtils;
import au.gov.nehta.vendorlibrary.clinicalpackage.core.SubmissionSet;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.PackagingUtility;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.SignatureGenerationException;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentMetadata;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.FileUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.MetadataUtils;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.test._20120926_dev.AllTests;
import au.net.electronichealth.ns.cdapackage.xsd.esignature._2012.PersonNameType;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;

public class MetadataStartStopTimeTest {

	static final PCEHRHeader commonHeader = getHeader();
	static final PersonNameType personNameType = getPersonNameType();
	@Before
	public void setup() {
		
	}
	
	
	
	private static PersonNameType getPersonNameType() {
	    // Approver.
		PersonNameType personNameType = new PersonNameType();
	    personNameType.setFamilyName("John");
	    personNameType.getGivenName().add("Ross");
		return personNameType;
	}



	@Test
	public void testPathologyMinDocumentStartTime() throws Exception{
		 String doc = IOUtils.read( new File("src/test/resources/TestFiles/pathologyDI/PathologyResultReport_3A_Min.xml") );
	    DocumentMetadata documentMetadata = MetadataUtils.toDocumentMetadata(commonHeader, doc.getBytes());
	   
	    Assert.assertEquals("20070227030000",documentMetadata.getServiceStartTime());
	    Assert.assertEquals(documentMetadata.getServiceStartTime(),documentMetadata.getServiceStopTime());
	}
	
	@Test
	public void testPathologyMaxDocumentStartTime() throws Exception{
		 String doc = IOUtils.read( new File("src/test/resources/TestFiles/pathologyDI/PathologyResultReport_3A_Max.xml") );
	    DocumentMetadata documentMetadata = MetadataUtils.toDocumentMetadata(commonHeader, doc.getBytes());
	   
	    Assert.assertEquals("20140227030000",documentMetadata.getServiceStartTime());
	    Assert.assertEquals(documentMetadata.getServiceStartTime(),documentMetadata.getServiceStopTime());
	}
	

	@Test
	public void testDIMinDocumentStartTime() throws Exception{
		 String doc = IOUtils.read( new File("src/test/resources/TestFiles/pathologyDI/DiagnosticImagingReport_3A_Min.xml") );
	    DocumentMetadata documentMetadata = MetadataUtils.toDocumentMetadata(commonHeader, doc.getBytes());
	    
	    //this document has not image time, start time, or fixed time  value should be creation time 
	    Assert.assertEquals(documentMetadata.getCreationTime(),documentMetadata.getServiceStartTime());
	    Assert.assertEquals(documentMetadata.getServiceStartTime(),documentMetadata.getServiceStopTime());
	}
	
	@Test
	public void testDIMaxDocumentStartTime() throws Exception{
		 String doc = IOUtils.read( new File("src/test/resources/TestFiles/pathologyDI/DiagnosticImagingReport_3A_Max.xml") );
	    DocumentMetadata documentMetadata = MetadataUtils.toDocumentMetadata(commonHeader, doc.getBytes());
	    
	    //this datetime has rolled back to the previous day becuase of +10 timezone conversion to utc
	    Assert.assertEquals("20140930223702",documentMetadata.getServiceStartTime());
	    Assert.assertEquals(documentMetadata.getServiceStartTime(),documentMetadata.getServiceStopTime());
	}


 
	
	
	
	
	
	
	
	private static PCEHRHeader getHeader() {
		return MessageComponents.createRequest
			      (
			        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003618334357646", null, "Ross John", false),
			        "8003601243017717",
			        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
			        PCEHRHeader.ClientSystemType.CIS,
			        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
			      );
	}
	
	

	  private static SubmissionSet createPackage(final byte[] rootDocument, X509Certificate certificate, PrivateKey privateKey, String approverId, PersonNameType approverName) throws GeneralSecurityException, SignatureGenerationException {
	    // Begin building package.
	    SubmissionSet.Builder subsetBuilder = new SubmissionSet.Builder();
	    subsetBuilder.rootDocument(rootDocument);

	    // Add signature to package.
	    subsetBuilder.signature(PackagingUtility.generateSignature(
	            rootDocument,
	            certificate,
	            privateKey,
	            approverId,
	            approverName
	    ));

	    // Build package.
	    return subsetBuilder.build();
	  }


}
