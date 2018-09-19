/*
 * Copyright 2012 NEHTA
 *
 * Licensed under the NEHTA Open Source (Apache) License; you may not use this
 * file except in compliance with the License. A copy of the License is in the
 * 'license.txt' file, which should be provided with this work.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.tests.view;

import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBElement;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AssociationType1;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ClassificationType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExtrinsicObjectType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.IdentifiableType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.RegistryPackageType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.Slot;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryError;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XDSConstants;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.Author;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CodedValue;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentQueryParams;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentStatus;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.FormatCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.PracticeSettingTypeCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.SubmissionMetadata;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.MetadataUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.XDSMapper;
import au.gov.nehta.vendorlibrary.pcehr.clients.view.GetDocumentListClient;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.AllTests;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.tplt.xsd.common.templatescoreelements._1.TemplateMetadataType.Metadata;

/**
 * Test cases for the {@link au.gov.nehta.vendorlibrary.pcehr.clients.view.GetDocumentListClient} class.
 */
public class GetDocumentListClientTest {

  private GetDocumentListClient client;

  @Before
  public final void setUp() throws Exception {
    AllTests.setUp();

    client = new GetDocumentListClient(
      AllTests.getSslSocketFactory(),
      AllTests.getCertificate(),
      AllTests.getPrivateKey(),
      Endpoints.ACCENTURE_GET_DOCUMENT_LIST,
     // Logging.GET_DOCUMENT_LIST
      true
    );
  }
  


  @After
  public final void tearDown() throws Exception {
    client = null;
  }

  @Test
  public void test_Approved() throws Exception {

    DocumentQueryParams queryParams = new DocumentQueryParams();
    queryParams.getStatuses().add(DocumentStatus.APPROVED);
    
    
    PCEHRHeader request = MessageComponents.createRequest
    (
      MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
      "8003603459803467",
      MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
      PCEHRHeader.ClientSystemType.CIS,
      MessageComponents.createAccessingOrganisation(/*"8003620833337558 "8003628233352432"*/ "8003628233352432", "Medicare305", null)
    );
    
    
    AdhocQueryResponse response = client.getDocumentList(request, queryParams);
    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
    Assert.assertTrue(response.getRegistryObjectList().getExtrinsicObjects().size() > 0);
    List<ExtrinsicObjectType> docs = response.getRegistryObjectList().getExtrinsicObjects();
    for (ExtrinsicObjectType doc : docs) {
      Assert.assertEquals(XDSConstants.EOT_STATUS_APPROVED, doc.getStatus());
    }
  }
  
  @Test
  public void test_getMetaData() throws Exception {

	DocumentQueryParams queryParams = new DocumentQueryParams();
	queryParams.getStatuses().add(DocumentStatus.DELETED);
   // queryParams.setDocumentCreationTimeFrom("20130501000000");
    
    PCEHRHeader request = MessageComponents.createRequest
    (
      MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
      "8003601243017691",
      MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
      PCEHRHeader.ClientSystemType.CIS,
      MessageComponents.createAccessingOrganisation( "8003628233352432", "Medicare305", null)
    );
    
    
    AdhocQueryResponse response = client.getDocumentList(request,queryParams);

    ExtrinsicObjectType first = response.getRegistryObjectList().getExtrinsicObjects().get(0);
   
    Date serviceStartTime = null;
     
    
    for(Slot slot : first.getSlots()){
    	
        //get the correct Slot
    	if(XDSConstants.SERVICE_START_TIME_SLOT.equals(slot.getName())){
    		List<String>values = slot.getValueList().getValues();
  
    		if(!values.isEmpty()){
	    		//parse the date
	    		try{
	    			serviceStartTime = MetadataUtils.parseDate(values.get(0));
	    		}catch(IllegalArgumentException ie){
	    			//do something for bad dates
	    		}
    		}//else no date value
    	}
    	
    }
   
    if(serviceStartTime == null) // no date was present
    
    System.out.println(serviceStartTime);
    
    
    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
    Assert.assertTrue(response.getRegistryObjectList().getExtrinsicObjects().size() > 0);
    List<ExtrinsicObjectType> docs = response.getRegistryObjectList().getExtrinsicObjects();
    for (ExtrinsicObjectType doc : docs) {
      Assert.assertEquals(XDSConstants.EOT_STATUS_APPROVED, doc.getStatus());
    }
  }
  
  @Test
  public void test_080() throws Exception {

    DocumentQueryParams queryParams = new DocumentQueryParams();
    queryParams.getStatuses().add(DocumentStatus.DELETED);
    //queryParams.setServiceStartTimeFrom( "YYYYmmddd000000" );
  //  queryParams.setServiceStartTimeTo( "" );
    
    
    PCEHRHeader request = MessageComponents.createRequest
    (
      MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
      "8003603459803467",
      MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
      PCEHRHeader.ClientSystemType.CIS,
      MessageComponents.createAccessingOrganisation(/*"8003620833337558"*/ "8003628233352432", "Medicare305", null)
    );
    
    
    AdhocQueryResponse response = client.getDocumentList(request, queryParams);
    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
    Assert.assertTrue(response.getRegistryObjectList().getExtrinsicObjects().size() == 0);
    List<ExtrinsicObjectType> docs = response.getRegistryObjectList().getExtrinsicObjects();
    for (ExtrinsicObjectType doc : docs) {
      Assert.assertEquals(XDSConstants.EOT_STATUS_APPROVED, doc.getStatus());
    }
  }
  
  @Test
  public void test_ServiceStartTime() throws Exception{
      DocumentQueryParams queryParams = new DocumentQueryParams();
      queryParams.getStatuses().add(DocumentStatus.APPROVED);
      queryParams.setServiceStartTimeFrom( "20130727000000" );
      queryParams.setServiceStartTimeTo( "20130729000000" );
      
      
      
      PCEHRHeader request = getKnownIHIHeader();
      
      
      AdhocQueryResponse response = client.getDocumentList(request, queryParams);
      if("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure".equals(response.getStatus())){
          String highestSeverity = response.getRegistryErrorList().getHighestSeverity();
          System.out.println(highestSeverity);
          List<RegistryError> registryErrors = response.getRegistryErrorList().getRegistryErrors();
          for(RegistryError e:  registryErrors){
              System.out.println(e.getErrorCode());
              System.out.println(e.getSeverity());
              System.out.println(e.getValue());
              
          } 
      }
            
      debugOut( response );
      
      Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
      Assert.assertEquals(2,response.getRegistryObjectList().getExtrinsicObjects().size() );
  }
  
  @Test
  public void test_ServiceStartTimeNoEnd() throws Exception{
      DocumentQueryParams queryParams = new DocumentQueryParams();
      queryParams.getStatuses().add(DocumentStatus.APPROVED);
      queryParams.setServiceStartTimeFrom( "20130727000000" );
      //queryParams.setServiceStartTimeTo( "20130729000000" );
      
      
      
      PCEHRHeader request = getKnownIHIHeader();
      
      
      AdhocQueryResponse response = client.getDocumentList(request, queryParams);
      if("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure".equals(response.getStatus())){
          String highestSeverity = response.getRegistryErrorList().getHighestSeverity();
          System.out.println(highestSeverity);
          List<RegistryError> registryErrors = response.getRegistryErrorList().getRegistryErrors();
          for(RegistryError e:  registryErrors){
              System.out.println(e.getErrorCode());
              System.out.println(e.getSeverity());
              System.out.println(e.getValue());
              
          } 
      }
            
      debugOut( response );
      
      Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
      Assert.assertEquals(5,response.getRegistryObjectList().getExtrinsicObjects().size() );
  }
  
  @Test
  public void test_ServiceStartTimeNoBegin() throws Exception{
      DocumentQueryParams queryParams = new DocumentQueryParams();
      queryParams.getStatuses().add(DocumentStatus.APPROVED);
      //queryParams.setServiceStartTimeFrom( "20130727000000" );
      queryParams.setServiceStartTimeTo( "20130729000000" );
      
      
      
      PCEHRHeader request = getKnownIHIHeader();
      
      
      AdhocQueryResponse response = client.getDocumentList(request, queryParams);
      if("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure".equals(response.getStatus())){
          String highestSeverity = response.getRegistryErrorList().getHighestSeverity();
          System.out.println(highestSeverity);
          List<RegistryError> registryErrors = response.getRegistryErrorList().getRegistryErrors();
          for(RegistryError e:  registryErrors){
              System.out.println(e.getErrorCode());
              System.out.println(e.getSeverity());
              System.out.println(e.getValue());
              
          } 
      }
            
      debugOut( response );
      
      Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
      Assert.assertTrue(response.getRegistryObjectList().getExtrinsicObjects().size() >0);
  }


  @Test
  public void test_DocumentCreationTime() throws Exception{
      DocumentQueryParams queryParams = new DocumentQueryParams();
      queryParams.getStatuses().add(DocumentStatus.APPROVED);
      queryParams.setDocumentCreationTimeFrom( "20130727000000" );
      queryParams.setDocumentCreationTimeTo( "20130729000000" );
      
      
      
      PCEHRHeader request = getKnownIHIHeader();
      
      
      AdhocQueryResponse response = client.getDocumentList(request, queryParams);
      if("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure".equals(response.getStatus())){
          String highestSeverity = response.getRegistryErrorList().getHighestSeverity();
          System.out.println(highestSeverity);
          List<RegistryError> registryErrors = response.getRegistryErrorList().getRegistryErrors();
          for(RegistryError e:  registryErrors){
              System.out.println(e.getErrorCode());
              System.out.println(e.getSeverity());
              System.out.println(e.getValue());
              
          } 
      }
            
      debugOut( response );
      
      Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
      Assert.assertEquals(2,response.getRegistryObjectList().getExtrinsicObjects().size() );
  }
  
  @Test
  public void test_DocumentCreationTimeNoEnd() throws Exception{
      DocumentQueryParams queryParams = new DocumentQueryParams();
      queryParams.getStatuses().add(DocumentStatus.APPROVED);
      queryParams.setDocumentCreationTimeFrom( "20130727000000" );
     // queryParams.setDocumentCreationTimeTo( "20130729000000" );
      
      
      
      PCEHRHeader request = getKnownIHIHeader();
      
      
      AdhocQueryResponse response = client.getDocumentList(request, queryParams);
      if("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure".equals(response.getStatus())){
          String highestSeverity = response.getRegistryErrorList().getHighestSeverity();
          System.out.println(highestSeverity);
          List<RegistryError> registryErrors = response.getRegistryErrorList().getRegistryErrors();
          for(RegistryError e:  registryErrors){
              System.out.println(e.getErrorCode());
              System.out.println(e.getSeverity());
              System.out.println(e.getValue());
              
          } 
      }
            
      debugOut( response );
      
      Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
      Assert.assertEquals(5,response.getRegistryObjectList().getExtrinsicObjects().size() );
  }
  
  
  @Test
  public void test_DocumentCreationTimeNoBegin() throws Exception{
      DocumentQueryParams queryParams = new DocumentQueryParams();
      queryParams.getStatuses().add(DocumentStatus.APPROVED);
     // queryParams.setDocumentCreationTimeFrom( "20130727000000" );
      queryParams.setDocumentCreationTimeTo( "20130729000000" );
      
      
      
      PCEHRHeader request = getKnownIHIHeader();
      
      
      AdhocQueryResponse response = client.getDocumentList(request, queryParams);
      if("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure".equals(response.getStatus())){
          String highestSeverity = response.getRegistryErrorList().getHighestSeverity();
          System.out.println(highestSeverity);
          List<RegistryError> registryErrors = response.getRegistryErrorList().getRegistryErrors();
          for(RegistryError e:  registryErrors){
              System.out.println(e.getErrorCode());
              System.out.println(e.getSeverity());
              System.out.println(e.getValue());
              
          } 
      }
            
      debugOut( response );
      
      Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
      Assert.assertTrue(response.getRegistryObjectList().getExtrinsicObjects().size() >0 );
  }
  
  
  @Test
  public void test_ServiceStopTime() throws Exception{
      DocumentQueryParams queryParams = new DocumentQueryParams();
      queryParams.getStatuses().add(DocumentStatus.APPROVED);
      queryParams.setServiceStopTimeFrom( "20130127000000" );
      queryParams.setServiceStopTimeTo( "20130729000000" );
      
      
      
      PCEHRHeader request = getKnownIHIHeader();
      
      
      AdhocQueryResponse response = client.getDocumentList(request, queryParams);
      if("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure".equals(response.getStatus())){
          String highestSeverity = response.getRegistryErrorList().getHighestSeverity();
          System.out.println(highestSeverity);
          List<RegistryError> registryErrors = response.getRegistryErrorList().getRegistryErrors();
          for(RegistryError e:  registryErrors){
              System.out.println(e.getErrorCode());
              System.out.println(e.getSeverity());
              System.out.println(e.getValue());
              
          } 
      }
            
      debugOut( response );
      
      Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
      Assert.assertEquals(4,response.getRegistryObjectList().getExtrinsicObjects().size() );
  }
  
  @Test
  public void test_ServiceStopTimeNoEnd() throws Exception{
      DocumentQueryParams queryParams = new DocumentQueryParams();
      queryParams.getStatuses().add(DocumentStatus.APPROVED);
      queryParams.setServiceStopTimeFrom( "20130127000000" );
      //queryParams.setServiceStopTimeTo( "20130729000000" );
      
      
      
      PCEHRHeader request = getKnownIHIHeader();
      
      
      AdhocQueryResponse response = client.getDocumentList(request, queryParams);
      if("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure".equals(response.getStatus())){
          String highestSeverity = response.getRegistryErrorList().getHighestSeverity();
          System.out.println(highestSeverity);
          List<RegistryError> registryErrors = response.getRegistryErrorList().getRegistryErrors();
          for(RegistryError e:  registryErrors){
              System.out.println(e.getErrorCode());
              System.out.println(e.getSeverity());
              System.out.println(e.getValue());
              
          } 
      }
            
      debugOut( response );
      
      Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
      Assert.assertTrue( response.getRegistryObjectList().getExtrinsicObjects().size() > 0);
  }
  
  @Test
  public void test_MultipleStatus() throws Exception{
      DocumentQueryParams queryParams = new DocumentQueryParams();
      queryParams.getStatuses().add(DocumentStatus.APPROVED);
      queryParams.getStatuses().add(DocumentStatus.DELETED);
        
      
      PCEHRHeader request = getKnownIHIHeader();
      
      
      AdhocQueryResponse response = client.getDocumentList(request, queryParams);
      if("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure".equals(response.getStatus())){
          String highestSeverity = response.getRegistryErrorList().getHighestSeverity();
          System.out.println(highestSeverity);
          List<RegistryError> registryErrors = response.getRegistryErrorList().getRegistryErrors();
          for(RegistryError e:  registryErrors){
              System.out.println(e.getErrorCode());
              System.out.println(e.getSeverity());
              System.out.println(e.getValue());
              
          } 
      }
            
      debugOut( response );
      
      Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
      Assert.assertTrue( response.getRegistryObjectList().getExtrinsicObjects().size() >0 );
  }
  
  
  @Test
  public void test_FormatCodes() throws Exception{
      DocumentQueryParams queryParams = new DocumentQueryParams();
      queryParams.getStatuses().add(DocumentStatus.APPROVED);
      queryParams.getTemplateIds().add( FormatCodes.PCEHR_PRESCRIPTION.getCodedValue() );
     
      
      PCEHRHeader request = getKnownIHIHeader();
      
      
      AdhocQueryResponse response = client.getDocumentList(request, queryParams);
      if("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure".equals(response.getStatus())){
          String highestSeverity = response.getRegistryErrorList().getHighestSeverity();
          System.out.println(highestSeverity);
          List<RegistryError> registryErrors = response.getRegistryErrorList().getRegistryErrors();
          for(RegistryError e:  registryErrors){
              System.out.println(e.getErrorCode());
              System.out.println(e.getSeverity());
              System.out.println(e.getValue());
              
          } 
      }
            
      debugOut( response );
      
      Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
      Assert.assertTrue( response.getRegistryObjectList().getExtrinsicObjects().size() >0 );
  }
  
  @Test
  public void test_MultipleFormatCodes() throws Exception{
      DocumentQueryParams queryParams = new DocumentQueryParams();
      queryParams.getStatuses().add(DocumentStatus.APPROVED);
      queryParams.getTemplateIds().add( FormatCodes.PCEHR_PRESCRIPTION.getCodedValue() );
      queryParams.getTemplateIds().add( FormatCodes.EVENT_SUMMARY_3A.getCodedValue() );
     
      
      PCEHRHeader request = getKnownIHIHeader();
      
      
      AdhocQueryResponse response = client.getDocumentList(request, queryParams);
      if("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure".equals(response.getStatus())){
          String highestSeverity = response.getRegistryErrorList().getHighestSeverity();
          System.out.println(highestSeverity);
          List<RegistryError> registryErrors = response.getRegistryErrorList().getRegistryErrors();
          for(RegistryError e:  registryErrors){
              System.out.println(e.getErrorCode());
              System.out.println(e.getSeverity());
              System.out.println(e.getValue());
              
          } 
      }
            
      debugOut( response );
      
      Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
    //  Assert.assertTrue( response.getRegistryObjectList().getExtrinsicObjects().size() >0 );
      
  }
  

  @Test 
  public void test_ClinicalSpecialty_PracticeSettingCode() throws Exception{
      //ClinicalSpecialty is practice setting code in the DocumentQueryParams
      DocumentQueryParams queryParams = new DocumentQueryParams();
      queryParams.getStatuses().add(DocumentStatus.APPROVED);
      queryParams.getClinicalSpecialties().add( PracticeSettingTypeCodes.HOSPITAL.getCodedValue() );
     
      
      PCEHRHeader request = getKnownIHIHeader();
      
      
      AdhocQueryResponse response = client.getDocumentList(request, queryParams);
      if("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure".equals(response.getStatus())){
          String highestSeverity = response.getRegistryErrorList().getHighestSeverity();
          System.out.println(highestSeverity);
          List<RegistryError> registryErrors = response.getRegistryErrorList().getRegistryErrors();
          for(RegistryError e:  registryErrors){
              System.out.println(e.getErrorCode());
              System.out.println(e.getSeverity());
              System.out.println(e.getValue());
              
          } 
      }
            
      debugOut( response );
      
      Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
      Assert.assertTrue( response.getRegistryObjectList().getExtrinsicObjects().size() > 0);
      System.out.println(response.getRegistryObjectList().getExtrinsicObjects().size());
  }
  
  @Ignore // EntryEventCode not supported
  @Test 
  public void test_Keywords_EntryEventCode() throws Exception{
      //keywords is EventCode in the DocumentQueryParams
      DocumentQueryParams queryParams = new DocumentQueryParams();
      queryParams.getStatuses().add(DocumentStatus.APPROVED);
      queryParams.getKeywords().add(   "Event"  );
     
      
      PCEHRHeader request = getKnownIHIHeader();
      
      
      AdhocQueryResponse response = client.getDocumentList(request, queryParams);
      if("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure".equals(response.getStatus())){
          String highestSeverity = response.getRegistryErrorList().getHighestSeverity();
          System.out.println(highestSeverity);
          List<RegistryError> registryErrors = response.getRegistryErrorList().getRegistryErrors();
          for(RegistryError e:  registryErrors){
              System.out.println(e.getErrorCode());
              System.out.println(e.getSeverity());
              System.out.println(e.getValue());
              
          } 
      }
            
      debugOut( response );
      
      Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
      Assert.assertTrue(response.getRegistryObjectList().getExtrinsicObjects().size() >0 );
      System.out.println(response.getRegistryObjectList().getExtrinsicObjects().size());
  }
  
  @Test 
  public void test_AuthorIndividual_AuthorPerson() throws Exception{
      //AuthorIndividual is AuthorPerson in the DocumentQueryParams
      DocumentQueryParams queryParams = new DocumentQueryParams();
      queryParams.getStatuses().add(DocumentStatus.APPROVED);
      queryParams.getAuthorIndividuals().add(   new Author("Black","Ely","Dr","8003618334357646") );
      
     
      PCEHRHeader request = getKnownIHIHeader();
      
      
      AdhocQueryResponse response = client.getDocumentList(request, queryParams);
      if("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure".equals(response.getStatus())){
          String highestSeverity = response.getRegistryErrorList().getHighestSeverity();
          System.out.println(highestSeverity);
          List<RegistryError> registryErrors = response.getRegistryErrorList().getRegistryErrors();
          for(RegistryError e:  registryErrors){
              System.out.println(e.getErrorCode());
              System.out.println(e.getSeverity());
              System.out.println(e.getValue());
              
          } 
      }
            
      debugOut( response );
      
      Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
      Assert.assertTrue(response.getRegistryObjectList().getExtrinsicObjects().size()>0 );
      System.out.println(response.getRegistryObjectList().getExtrinsicObjects().size());
  }
  
  @Test 
  public void test_AuthorIndividual_AuthorPerson_WildCards() throws Exception{
      //AuthorIndividual is AuthorPerson in the DocumentQueryParams
      DocumentQueryParams queryParams = new DocumentQueryParams();
      queryParams.getStatuses().add(DocumentStatus.APPROVED);
      queryParams.getAuthorIndividuals().add(   new Author("%","%","%","%1") );
      //queryParams.getAuthorIndividuals().add(   new Author("%ck","_ly","%r","8003618334357646") );
     
      PCEHRHeader request = getKnownIHIHeader();
      
      
      AdhocQueryResponse response = client.getDocumentList(request, queryParams);
      if("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure".equals(response.getStatus())){
          String highestSeverity = response.getRegistryErrorList().getHighestSeverity();
          System.out.println(highestSeverity);
          List<RegistryError> registryErrors = response.getRegistryErrorList().getRegistryErrors();
          for(RegistryError e:  registryErrors){
              System.out.println(e.getErrorCode());
              System.out.println(e.getSeverity());
              System.out.println(e.getValue());
              
          } 
      }
            
      debugOut( response );
      
      Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
      Assert.assertTrue(response.getRegistryObjectList().getExtrinsicObjects().size()>0 );
      System.out.println(response.getRegistryObjectList().getExtrinsicObjects().size());
  }
  
  @Test 
  public void test_DocumentClasses() throws Exception{
      //This is to test tha the document class can be requested, not that it returns results
      DocumentQueryParams queryParams = new DocumentQueryParams();
      queryParams.getStatuses().add(DocumentStatus.APPROVED);
      queryParams.getDocumentClasses().add(   new CodedValue("MADE_UP","1.2.3.4.5.6.7.8.9") );
      
     
      PCEHRHeader request = getKnownIHIHeader();
      
      
      AdhocQueryResponse response = client.getDocumentList(request, queryParams);
      if("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure".equals(response.getStatus())){
          String highestSeverity = response.getRegistryErrorList().getHighestSeverity();
          System.out.println(highestSeverity);
          List<RegistryError> registryErrors = response.getRegistryErrorList().getRegistryErrors();
          for(RegistryError e:  registryErrors){
              System.out.println(e.getErrorCode());
              System.out.println(e.getSeverity());
              System.out.println(e.getValue());
              
          } 
      }
            
      debugOut( response );
      
      Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
     // Assert.assertTrue(response.getRegistryObjectList().getExtrinsicObjects().size()>0 );
      System.out.println(response.getRegistryObjectList().getExtrinsicObjects().size());
  }
  
  

  @Test 
  public void test_muliple_DocumentClasses() throws Exception{
      //This is to test tha the document class can be requested, not that it returns results
      DocumentQueryParams queryParams = new DocumentQueryParams();
      queryParams.getStatuses().add(DocumentStatus.APPROVED);
      queryParams.getDocumentClasses().add(   new CodedValue("MADE_UP","1.2.3.4.5.6.7.8.9") );
      queryParams.getDocumentClasses().add(   new CodedValue("MADE_UP","2.2.3.4.5.6.7.8.9") );
      
     
      PCEHRHeader request = getKnownIHIHeader();
      
      
      AdhocQueryResponse response = client.getDocumentList(request, queryParams);
      if("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure".equals(response.getStatus())){
          String highestSeverity = response.getRegistryErrorList().getHighestSeverity();
          System.out.println(highestSeverity);
          List<RegistryError> registryErrors = response.getRegistryErrorList().getRegistryErrors();
          for(RegistryError e:  registryErrors){
              System.out.println(e.getErrorCode());
              System.out.println(e.getSeverity());
              System.out.println(e.getValue());
              
          } 
      }
            
      debugOut( response );
      
      Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
     // Assert.assertTrue(response.getRegistryObjectList().getExtrinsicObjects().size()>0 );
      System.out.println(response.getRegistryObjectList().getExtrinsicObjects().size());
  }
 
  @Test 
  public void test_multiple_AuthorIndividual_AuthorPerson() throws Exception{
      //AuthorIndividual is AuthorPerson in the DocumentQueryParams
      DocumentQueryParams queryParams = new DocumentQueryParams();
      queryParams.getStatuses().add(DocumentStatus.APPROVED);
      queryParams.getAuthorIndividuals().add(   new Author("Smith","John",null,null) );
      queryParams.getAuthorIndividuals().add(   new Author("Doe","Jane",null,null) );
     
      
      PCEHRHeader request = getKnownIHIHeader();
      
      
      AdhocQueryResponse response = client.getDocumentList(request, queryParams);
      if("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure".equals(response.getStatus())){
          String highestSeverity = response.getRegistryErrorList().getHighestSeverity();
          System.out.println(highestSeverity);
          List<RegistryError> registryErrors = response.getRegistryErrorList().getRegistryErrors();
          for(RegistryError e:  registryErrors){
              System.out.println(e.getErrorCode());
              System.out.println(e.getSeverity());
              System.out.println(e.getValue());
              
          } 
      }
            
      debugOut( response );
      
      Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
      Assert.assertEquals(0,response.getRegistryObjectList().getExtrinsicObjects().size() );
      System.out.println(response.getRegistryObjectList().getExtrinsicObjects().size());
  }
  
  
  //Bodalla Clinic^^^^^^^^^1.2.36.1.2001.1003.0.8003620833333789
  //author institution
  
 
  
/**
 * Get the header of an IHI expected to work (in terms of extrinsic objects retrieved)
 * for the above tests  
 * @return
 */
public PCEHRHeader getKnownIHIHeader() {
    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003606789133695",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation(/*"8003620833337558"*/ "8003628233352432", "Medicare305", null)
      );
    return request;
}


    public void debugOut( AdhocQueryResponse response ) {
        if (null != response.getResponseSlotList()) {
            System.out.println( "===SlotList===" );
            List<Slot> slots = response.getResponseSlotList().getSlots();
            System.out.println( "slots:" + slots.size() );
            for (Slot s : slots) {
                System.out.println( s.getName() + " " + s.getSlotType() );
            }
        }

        System.out.println( "===RegistryObjectList===" );
        List<RegistryPackageType> packages = response.getRegistryObjectList().getRegistryPackages();
        System.out.println( "packages:" + packages.size() );
        for (RegistryPackageType s : packages) {
            System.out.println( s.getName() + " " + s.getStatus() );
        }
        List<ClassificationType> classes = response.getRegistryObjectList().getClassifications();
        System.out.println( "classes:" + classes.size() );
        for (ClassificationType s : classes) {
            System.out.println( s.getStatus() + " " + s.getClassificationNode() );
        }

        List<AssociationType1> ass = response.getRegistryObjectList().getAssociations();
        System.out.println( "associations:" + ass.size() );
        for (AssociationType1 s : ass) {
            System.out.println( s.getStatus() + " " + s.getAssociationType() );
        }

        List<ExtrinsicObjectType> ex = response.getRegistryObjectList().getExtrinsicObjects();
        System.out.println( "ExtrinsicObjectType:" + ex.size() );
        for (ExtrinsicObjectType s : ex) {
        	if(s.getName() != null && s.getName().getLocalizedStrings().size() > 0)
            System.out.println( s.getStatus() + " " + s.getName().getLocalizedStrings().get( 0 ).getValue() );
        }

        List<JAXBElement<? extends IdentifiableType>> ids = response.getRegistryObjectList().getIdentifiables();
        System.out.println( "IdentifiableType:" + ids.size() );
        for (JAXBElement<? extends IdentifiableType> s : ids) {
            System.out.println( s.getName() + " " + s.getValue().getId() );
        }

        System.out.println( "=== errors === " );
        if (null != response.getRegistryErrorList()) {
            List<RegistryError> errors = response.getRegistryErrorList().getRegistryErrors();
            for (RegistryError s : errors) {
                System.out.println( s.getErrorCode() + " " + s.getLocation() );
            }
        }
    }
}


