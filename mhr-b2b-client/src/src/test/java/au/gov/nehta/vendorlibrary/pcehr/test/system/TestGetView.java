package au.gov.nehta.vendorlibrary.pcehr.test.system;

import java.util.Calendar;
import java.util.TimeZone;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import au.gov.nehta.vendorlibrary.common.security.KeystoreUtil;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.TypedViewResponse;
import au.gov.nehta.vendorlibrary.pcehr.clients.view.GetViewClient;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.SecurityConstants;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.SecurityUtil;
import au.net.electronichealth.ns.pcehr.svc.getview._1.StandardErrorMsg;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.diagnosticimagingreportview._1.DiagnosticImagingReportView;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.diagnosticimagingreportview._1.DiagnosticImagingReportViewResponse;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.getview._1.GetViewResponse;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.healthcheckscheduleview._1.HealthCheckScheduleView;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.healthrecordoverview._1.HealthRecordOverView;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.healthrecordoverview._1.HealthRecordOverviewResponse;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.medicareoverview._1.MedicareOverview;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.observationview._1.ObservationView;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.pathologyreportview._1.PathologyReportView;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.pathologyreportview._1.PathologyReportViewResponse;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.prescriptionanddispenseview._1.PrescriptionAndDispenseView;

public class TestGetView {
	  private static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone( "UTC" );
	private static final String DRP_PORTAL_ENDPOINT= "https://portal2-nehta-drp-iis.nehta.net.au/PcehrViewService/GetView.svc";
	private GetViewClient client;
	private static SSLSocketFactory sslSocketFactory;

	private static final String IHI_FOR_DRP2_USE = "8003601243017691";
	private static final String IHI_FOR_DRP2_USE2 = "8003601243017717";
	private static final String IHI_FOR_DRP2_USE3 = "8003604570901339";
	 static PCEHRHeader request;
	 
	@BeforeClass
	public static void initialSetup() throws Exception {

		HttpsURLConnection.setDefaultHostnameVerifier( new HostnameVerifier() {
			public boolean verify( String s, SSLSession sslSession ) {
				return true;
			}
		} );

		sslSocketFactory = SecurityUtil.getSslSocketFactory();

		// Sets the newly created sslsocketfactory as the default for all
		// instances OF the HttpsURLConnection class.
		HttpsURLConnection.setDefaultSSLSocketFactory( sslSocketFactory );

		// For testing purposes.
		System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
		
		
	  }

	  @Before
	  public final void setUp() throws Exception {
	    client = new GetViewClient(
	      sslSocketFactory,
	      SecurityUtil.getCertificate(),
	      SecurityUtil.getPrivateKey(),
//	      DRP_PORTAL_ENDPOINT,
//	      Endpoints.LOCAL_TCP_MONITOR_INTERCEPTOR_GET_VIEW,
	      Endpoints.SVT_GET_VIEW,
	      Logging.GET_AUDIT_VIEW
	    );
	    
	    request = MessageComponents.createRequest(
	    	       MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "ROSS JOHN", false),
	               "8003608666701594",
	               MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 8.1"),
	               PCEHRHeader.ClientSystemType.CIS,
	               MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null));	
		
	  }

	  @After
	  public final void tearDown() throws Exception {
	    client = null;
	  }

	  @Test
	  public void test_MedicareOverview() throws Exception {
		  

	    MedicareOverview medicareOverview = new MedicareOverview();
	    Calendar OneJan1900 = Calendar.getInstance();
	    OneJan1900.set( 1900, 0, 1 ); //from 1 January 1900
		medicareOverview.setFromDate( OneJan1900 );
		medicareOverview.setToDate( Calendar.getInstance() ); //to current time
		medicareOverview.setVersionNumber( "1.0" );
		
		GetViewResponse response = client.getView(request,medicareOverview);
	    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
	  }
	  
	  

	  
	 /* @Test*/
	  public void test_ObservationView() throws Exception {
		  

	    ObservationView view = new ObservationView();
	    Calendar OneJan1900 = Calendar.getInstance();
	    OneJan1900.set( 1900, 0, 1 ); //from 1 January 1900
		view.setFromDate( OneJan1900 );
		view.setToDate( Calendar.getInstance() ); //to current time
		view.setVersionNumber( "1.0" );
		view.setObservationType( "HEADCIRCUMFERENCE" ); //other valid values are HEIGHT,WEIGHT,BMI
		view.setDocumentSource( "ALL" ); //other valid values are PERSONAL, PROVIDER
		
//		may  be released in the future
//		GetViewResponse response = client.getView(request,view);
//	    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
	  }
	  
	  
	  @Test
	  public void test_PrescriptionAndDispenseView() throws Exception {


	    PrescriptionAndDispenseView view = new PrescriptionAndDispenseView();
	    Calendar OneJan1900 = Calendar.getInstance();
	    OneJan1900.set( 1900, 0, 1 ); //from 1 January 1900
		view.setFromDate( OneJan1900 );
		view.setToDate( Calendar.getInstance() ); //to current time
		view.setVersionNumber( "1.0" );
		
		GetViewResponse response = client.getView(request,view);
	    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
	  }
	  
	
	  
	  @Test
	  public void test_HealthCheckScheduleView() throws Exception {
		  

	    //one of 5 available views
	    HealthCheckScheduleView view = new HealthCheckScheduleView();
		view.setVersionNumber( "1.0" );
		view.setJurisdiction( "QLD" ); //must be a state code all caps
		
		GetViewResponse response = client.getView(request,view);
	    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
	  }
	
	  @Test
	  public void test_PathologyReportView() throws Exception {
		  
	    PathologyReportView view = new PathologyReportView();
	    
	    Calendar OneJan1900 = Calendar.getInstance();
	    OneJan1900.set( 1900, 0, 1 ); //from 1 January 1900
		view.setFromDate( OneJan1900 );
		view.setToDate( Calendar.getInstance() ); //to current time
		view.setVersionNumber( "1.0" );
		
		TypedViewResponse<PathologyReportViewResponse> response = client.getView(request,view);
		PathologyReportViewResponse responseObject = response.getResponseObject();
		responseObject.getPathologyReports();
		responseObject.getViewMetadata();
		
	    Assert.assertEquals("PCEHR_SUCCESS", response.getCode());
	  }
	  
	  @Test(expected=StandardErrorMsg.class)
	  public void test_PathologyReportViewUnderError() throws Exception {
		  
	    PathologyReportView view = new PathologyReportView();
	    
	    Calendar OneJan1900 = Calendar.getInstance();
	    OneJan1900.set( 1900, 0, 1 ); //from 1 January 1900
		view.setFromDate( OneJan1900 );
		view.setToDate( Calendar.getInstance() ); //to current time
		view.setVersionNumber( "1.0" );
		
		//to fail the request
		request.setIhiNumber("8003601111111111");
		
		TypedViewResponse<PathologyReportViewResponse> response = client.getView(request,view);
		PathologyReportViewResponse responseObject = response.getResponseObject();
		responseObject.getPathologyReports();
		responseObject.getViewMetadata();
		
	    Assert.assertEquals("PCEHR_ERROR", response.getCode());
	  }
	  
	  
	  @Test
	  public void test_DIReportView() throws Exception {
	    DiagnosticImagingReportView view = new DiagnosticImagingReportView();
	    Calendar OneJan1900 = Calendar.getInstance();
	    OneJan1900.set( 1900, 0, 1 ); //from 1 January 1900
		view.setFromDate( OneJan1900 );
		view.setToDate( Calendar.getInstance() ); //to current time
		view.setVersionNumber( "1.0" );
		
		TypedViewResponse<DiagnosticImagingReportViewResponse> response = client.getView(request,view);
		
		//the raw response object is still available:
		GetViewResponse getViewResponse = response.getGetViewResponse();
		getViewResponse.getResponseStatus();
		
		DiagnosticImagingReportViewResponse responseObject = response.getResponseObject();
		
		responseObject.getDiagnosticImagingReports();
		responseObject.getViewMetadata();
		
 
		
	    Assert.assertEquals("PCEHR_SUCCESS", response.getCode());
	  }
	  
	  @Test
	  public void testHROView() throws Exception {
	    HealthRecordOverView view = new HealthRecordOverView();
	    view.setClinicalSynopsisLength(1000);
	    view.setVersionNumber("1.1");
		
		TypedViewResponse<HealthRecordOverviewResponse> response = client.getView(request,view);
		HealthRecordOverviewResponse hroResponse = response.getResponseObject();
		hroResponse.getRecentDocuments();
		hroResponse.getSharedHealthSummary();
		
		//hroResponse.getSharedHealthSummary().getSharedHealthSummaryAtomicData().
		
	    Assert.assertEquals("PCEHR_SUCCESS", response.getCode());
	  }
	  
	  
	  
	  @Test @Ignore
	  public void test__PCEHR_ACCENTURE_VPN__PrescriptionAndDispenseView() throws Exception {
		  client=null;
		  
		sslSocketFactory=  KeystoreUtil.getSslSocketFactory(
			      SecurityConstants.PRIVATE_KEY_STORE_TYPE,
			      SecurityConstants.PRIVATE_KEY_STORE_PATH,
			      SecurityConstants.PRIVATE_KEY_STORE_PASSWORD,
			      SecurityConstants.PRIVATE_KEY_PASSWORD,
			      SecurityConstants.ALIAS_8003624166667177,
			      SecurityConstants.TRUST_STORE_TYPE,
			      SecurityConstants.TRUST_STORE_PATH,
			      SecurityConstants.TRUST_STORE_PASSWORD
			    );
		
		
		  HttpsURLConnection.setDefaultSSLSocketFactory( sslSocketFactory );
		  
		  client = new GetViewClient(
				  sslSocketFactory,
		    
		    
		    KeystoreUtil.getSigningCertificate(
		    	      SecurityConstants.PRIVATE_KEY_STORE_TYPE,
		    	      SecurityConstants.PRIVATE_KEY_PASSWORD,
		    	      SecurityConstants.PRIVATE_KEY_STORE_PATH,
		    	      SecurityConstants.ALIAS_8003624166667177
		    	    ),
		      KeystoreUtil.getSigningPrivateKey(
				      SecurityConstants.PRIVATE_KEY_STORE_TYPE,
				      SecurityConstants.PRIVATE_KEY_PASSWORD,
				      SecurityConstants.PRIVATE_KEY_STORE_PATH,
				      SecurityConstants.ALIAS_8003624166667177
				    ),
				    
//		      Endpoints.SVT_GET_VIEW,
//				    Endpoints.ACCENTURE__VPN__GET_VIEW,
				    "https://144.140.140.147:443/getView",
		      Logging.GET_AUDIT_VIEW
		    );
		  
			
		  
		  
	  
	    PCEHRHeader request = MessageComponents.createRequest
	      (
	        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "pwilford",null , "PhilipWilford", false),
	        "8003602345690302",
	        MessageComponents.createProductType("NeHTA", "dummyCISusr1", "dummyCISusrV1", "Windows 7"),
	        PCEHRHeader.ClientSystemType.CIS,
	        MessageComponents.createAccessingOrganisation("8003629900024122", "Medicare-305", null)
	      );


	    //one of 5 available views
//	    PrescriptionAndDispenseView view = new PrescriptionAndDispenseView();
//	    Calendar begin = Calendar.getInstance();
//	    begin.set( 2013, 1, 8 ); //from 1 January 1900
//	    begin.setTimeZone( TimeZone.getTimeZone( "0" ) );
//	    
//	    
//		view.setFromDate( begin ); 
//		view.setToDate( Calendar.getInstance() ); //to current time
//		view.setVersionNumber( "1.0" );
		

	    MedicareOverview medicareOverview = new MedicareOverview();
	    Calendar OneJan1900 = Calendar.getInstance(UTC_TIME_ZONE);
	    OneJan1900.set( 2013, 0, 1 ); //from 1 January 2013
		medicareOverview.setFromDate( OneJan1900 );
		medicareOverview.setToDate( Calendar.getInstance(UTC_TIME_ZONE) ); //to current time
		medicareOverview.setVersionNumber( "1.0" );
		
		
		HealthCheckScheduleView hcs = new HealthCheckScheduleView();
		hcs.setJurisdiction("QLD");
		hcs.setVersionNumber( "1.0" );
		
		PrescriptionAndDispenseView pd = new PrescriptionAndDispenseView();
		pd.setFromDate( OneJan1900 );
		pd.setToDate( Calendar.getInstance(UTC_TIME_ZONE) );
		pd.setVersionNumber( "1.0" );
		
		GetViewResponse response = client.getView(request,pd);
	    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
	    
//		GetViewResponse response = client.getView(request,hcs);
//	    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
	    
//		GetViewResponse response = client.getView(request,medicareOverview);
//	    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
	    
	    
	  }
	  
}

