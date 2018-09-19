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
package au.gov.nehta.vendorlibrary.pcehr.sample.view;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Calendar;

import javax.net.ssl.SSLSocketFactory;

import junit.framework.Assert;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.TypedViewResponse;
import au.gov.nehta.vendorlibrary.pcehr.clients.view.GetViewClient;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.constants.SampleEndpoints;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.constants.SampleValues;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.SecurityUtil;
import au.net.electronichealth.ns.pcehr.svc.getview._1.StandardErrorMsg;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.diagnosticimagingreportview._1.DiagnosticImagingReportView;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.diagnosticimagingreportview._1.DiagnosticImagingReportViewResponse;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.getview._1.GetViewResponse;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.getview._1.GetViewResponse.View;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.healthcheckscheduleview._1.HealthCheckScheduleView;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.healthrecordoverview._1.HealthRecordOverView;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.healthrecordoverview._1.HealthRecordOverviewResponse;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.medicareoverview._1.MedicareOverview;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.observationview._1.ObservationView;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.pathologyreportview._1.PathologyReportView;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.pathologyreportview._1.PathologyReportViewResponse;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.prescriptionanddispenseview._1.PrescriptionAndDispenseView;

import com.sun.xml.internal.ws.developer.JAXWSProperties;

/**
 * An example class showing usage of the Get View web service client.
 */
public class GetViewSample {

  private GetViewSample() {
  }

  public static void main(String args[]) throws IOException, GeneralSecurityException, StandardErrorMsg {

    GetViewClient client;

    // Sets the newly created sslsocketfactory as the default for all instances of the HttpsURLConnection class.
    SSLSocketFactory sslSocketFactory = SecurityUtil.getSslSocketFactory();
    

    // For testing purposes.
    System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");

    // Instantiate client, providing:
    //
    //  * socket factory;
    //  * endpoint URI string; and
    //  * set logging on/off.
    client = new GetViewClient(
      sslSocketFactory,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      SampleEndpoints.GET_REPRESENTATIVE_LIST,
      true);
    
    //allow SSL connections to be set on a per client basis 
    client.setProperty(JAXWSProperties.SSL_SOCKET_FACTORY, sslSocketFactory);

    // Create a sample user, using helper method.
    PCEHRHeader.User user = MessageComponents.createUser(
      SampleValues.USER_ID_TYPE,
      SampleValues.USER_ID,
      SampleValues.USER_ROLE,
      SampleValues.USER_NAME,
      SampleValues.USER_USE_ROLE_FOR_AUDIT
    );

    // Create a sample product type, using helper method.
    PCEHRHeader.ProductType productType = MessageComponents.createProductType(
      SampleValues.PRODUCT_TYPE_VENDOR,
      SampleValues.PRODUCT_NAME,
      SampleValues.PRODUCT_VERSION,
      SampleValues.PRODUCT_PLATFORM
    );

    // Create a sample accessing organisation, using helper method.
    PCEHRHeader.AccessingOrganisation accessingOrganisation = MessageComponents.createAccessingOrganisation(
      SampleValues.ACCESSING_ORGANISATION_ID,
      SampleValues.ACCESSING_ORGANISATION_NAME,
      SampleValues.ACCESSING_ORGANISATION_ALTERNATE_NAME
    );

    // Create a sample PCEHR request header, using helper method.
    PCEHRHeader commonHeader = MessageComponents.createRequest(
      user,
      SampleValues.IHI_NUMBER,
      productType,
      SampleValues.CLIENT_SYSTEM_TYPE_CIS,
      accessingOrganisation
    );


    //medicareOverview view
    MedicareOverview medicareOverview = new MedicareOverview();
    Calendar OneJan1900 = Calendar.getInstance();
    OneJan1900.set( 1900, 0, 1 ); //from 1 January 1900
	medicareOverview.setFromDate( OneJan1900 );
	medicareOverview.setToDate( Calendar.getInstance() ); //to current time
	medicareOverview.setVersionNumber( "1.0" );
	// Call operation - get all PCEHR documents associated with given IHI.
    GetViewResponse response = client.getView( commonHeader, medicareOverview);

	
	//Prescription And Dispense 
	PrescriptionAndDispenseView pdView = new PrescriptionAndDispenseView();
	pdView.setFromDate( OneJan1900 );
	pdView.setToDate( Calendar.getInstance() );
	pdView.setVersionNumber( "1.0" );
	response = client.getView( commonHeader, pdView);
	
	//Observation Schedule
	ObservationView observationview = new ObservationView();
	observationview.setFromDate( OneJan1900 );
	observationview.setToDate( Calendar.getInstance() );
	observationview.setVersionNumber( "1.0" );
    response = client.getView( commonHeader, observationview);

    
    //Health Check Schedule
	HealthCheckScheduleView healthCheckScheduleView = new HealthCheckScheduleView();
	healthCheckScheduleView.setVersionNumber( "1.0" );
	healthCheckScheduleView.setJurisdiction( "QLD" ); //must be a state code all caps
	response = client.getView( commonHeader, healthCheckScheduleView);
	

	///
	// Typed View response requests
	///
	
	//PathologyReport
	PathologyReportView patholgyReportview = new PathologyReportView();
	patholgyReportview.setFromDate( OneJan1900 );
	patholgyReportview.setToDate( Calendar.getInstance() ); //to current time
	patholgyReportview.setVersionNumber( "1.0" );
	
	//strongly typed view response object
	TypedViewResponse<PathologyReportViewResponse> pathologyResponse = client.getView(commonHeader,patholgyReportview);
	PathologyReportViewResponse pathologyReportViewResponse = pathologyResponse.getResponseObject();
	pathologyReportViewResponse.getPathologyReports();
	pathologyReportViewResponse.getViewMetadata();
	
	//the original viewResponse with template IDs and getResponseStatus
	response = pathologyResponse.getGetViewResponse();
	
	
	
	//Diagnostic Image
	DiagnosticImagingReportView diView = new DiagnosticImagingReportView();
	diView.setFromDate( OneJan1900 );
	diView.setToDate( Calendar.getInstance() ); //to current time
	diView.setVersionNumber( "1.0" );
	
	TypedViewResponse<DiagnosticImagingReportViewResponse> diResponse = client.getView(commonHeader,diView);
	
	DiagnosticImagingReportViewResponse diagnosticImagingReportViewResponse = diResponse.getResponseObject();
	diagnosticImagingReportViewResponse.getDiagnosticImagingReports();
	diagnosticImagingReportViewResponse.getViewMetadata();
	
	//the raw response object is still available:
	response = diResponse.getGetViewResponse();
	response.getResponseStatus();
	
	
	
	//Health Record Overview
	HealthRecordOverView hroView = new HealthRecordOverView();
    hroView.setClinicalSynopsisLength(1000);
    hroView.setVersionNumber("1.0");
	
	TypedViewResponse<HealthRecordOverviewResponse> hroRsponse =client.getView(commonHeader,hroView);
	HealthRecordOverviewResponse hroResponse = hroRsponse.getResponseObject();
	hroResponse.getRecentDocuments();
	hroResponse.getSharedHealthSummary();
	hroResponse.getNewDocuments();
	hroResponse.getOtherLinks();
	hroResponse.getViewMetadata();
	
	//the raw response object is still available:
	response = hroRsponse.getGetViewResponse();
	response.getResponseStatus();

    // Process response.
    System.out.println("Response Status: " + response.getResponseStatus().getCode());

    // Get view from the response.
    View view = response.getView();

    // Get view detials.
    System.out.println("Template ID: " + view.getTemplateID());
  }
}