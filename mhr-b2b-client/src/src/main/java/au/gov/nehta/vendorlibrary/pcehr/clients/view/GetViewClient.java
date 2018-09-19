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
package au.gov.nehta.vendorlibrary.pcehr.clients.view;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.bind.JAXBException;
import javax.xml.ws.Holder;

import org.apache.commons.lang.Validate;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.Client;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.TypedViewResponse;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.CommonHeaderValidator;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.DateUtils;
import au.gov.nehta.xsp.CertificateValidator;
import au.net.electronichealth.ns.pcehr.svc.getview._1.GetViewPortType;
import au.net.electronichealth.ns.pcehr.svc.getview._1.GetViewService;
import au.net.electronichealth.ns.pcehr.svc.getview._1.StandardErrorMsg;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.Signature;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.diagnosticimagingreportview._1.DiagnosticImagingReportView;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.diagnosticimagingreportview._1.DiagnosticImagingReportViewResponse;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.getview._1.GetView;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.getview._1.GetViewResponse;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.healthcheckscheduleview._1.HealthCheckScheduleView;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.healthrecordoverview._1.HealthRecordOverView;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.healthrecordoverview._1.HealthRecordOverviewResponse;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.medicareoverview._1.MedicareOverview;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.observationview._1.ObservationView;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.pathologyreportview._1.PathologyReportView;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.pathologyreportview._1.PathologyReportViewResponse;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.prescriptionanddispenseview._1.PrescriptionAndDispenseView;

/**
 * A JAX-WS client to the PCEHR 'Get View' web service.
 */
public class GetViewClient extends Client<GetViewPortType> {

  /**
   * Constructor - no certificate verification performed.
   *
   * @param sslSocketFactory  the {@link SSLSocketFactory} to be used when connecting to the web service provider (mandatory).
   * @param x509Certificate   the certificate key to be used for signing (mandatory)
   * @param privateKey        the private key to be used for signing (mandatory)
   * @param endpointAddress   the endpoint address of the web service (mandatory).
   * @param setLoggingEnabled set to <code>true</code> to enable logging (mandatory).
   */
  public GetViewClient(
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {
    super(
      GetViewService.class,
      GetViewPortType.class,
      sslSocketFactory,
      x509Certificate,
      privateKey,
      endpointAddress,
      setLoggingEnabled
    );
    setMTOMEnabled();
  }

  /**
   * Constructor - accepts an optional certificate verifier.
   *
   * @param sslSocketFactory    the {@link SSLSocketFactory} to be used when connecting to the web service provider (mandatory).
   * @param x509Certificate     the certificate key to be used for signing (mandatory)
   * @param certificateVerifier CertificateVerifier implementation (optional).
   * @param privateKey          the private key to be used for signing (mandatory)
   * @param endpointAddress     the endpoint address of the web service (mandatory).
   * @param setLoggingEnabled   set to <code>true</code> to enable logging (mandatory).
   */
  public GetViewClient(
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final CertificateValidator certificateValidator,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {

    super(
      GetViewService.class,
      GetViewPortType.class,
      sslSocketFactory,
      x509Certificate,
      certificateValidator,
      privateKey,
      endpointAddress,
      setLoggingEnabled
    );
    setMTOMEnabled();
  }

  /**
   * Invokes the web service operation that returns a list of representatives associated with a particular individual's PCEHR.
   *
   * @param commonHeader populated {@link PCEHRHeader} request object (Mandatory).
   * @param getViewRequestObject an AchievementDiaryView object for request
   * @return response (type {@link GetRepresentativeListResponse}) 
   * @throws StandardErrorMsg Thrown in the event that the operation fails.
   */
  /*
   * NOT YET SUPPORTED
   *
  public final GetViewResponse getView(PCEHRHeader commonHeader, AchievementDiaryView getViewRequestObject) throws StandardErrorMsg {

    return getView( commonHeader, (Object) getViewRequestObject );

    return responseHolder.value;
  }
  */
  
  
  /**
   * Invokes the web service operation that returns a list of representatives associated with a particular individual's PCEHR.
   *
   * @param commonHeader populated {@link PCEHRHeader} request object (Mandatory).
   * @param getViewRequestObject a MedicareOverview object for request
   * @return response (type {@link GetViewResponse})  
   * @throws StandardErrorMsg Thrown in the event that the operation fails.
   */
  public final GetViewResponse getView(PCEHRHeader commonHeader, MedicareOverview getViewRequestObject) throws StandardErrorMsg {
      return getView( commonHeader, (Object) getViewRequestObject );
  }
  
  
  
  /**
   * Invokes the web service operation that returns a list of representatives associated with a particular individual’s PCEHR.
   *
   * @param commonHeader populated {@link PCEHRHeader} request object (Mandatory).
   * @param getViewRequestObject a ObservationView object for request
   * @return response (type {@link GetViewResponse})  
   * @throws StandardErrorMsg Thrown in the event that the operation fails.
   */
  public final GetViewResponse getView(PCEHRHeader commonHeader, ObservationView getViewRequestObject) throws StandardErrorMsg {
      return getView( commonHeader, (Object)getViewRequestObject );
  }
  
  
  
  /**
   * Invokes the web service operation that returns a list of representatives associated with a particular individual's PCEHR.
   *
   * @param commonHeader populated {@link PCEHRHeader} request object (Mandatory).
   * @param getViewRequestObject a PrescriptionAndDispenseView object for request
   * @return response (type {@link GetViewResponse})  
   * @throws StandardErrorMsg Thrown in the event that the operation fails.
   */
  public final GetViewResponse getView(PCEHRHeader commonHeader, PrescriptionAndDispenseView getViewRequestObject) throws StandardErrorMsg {
      return getView( commonHeader,(Object) getViewRequestObject );
  }
  
  
  /**
   * Invokes the web service operation that returns a list of representatives associated with a particular individual's PCEHR.
   *
   * @param commonHeader populated {@link PCEHRHeader} request object (Mandatory).
   * @param getViewRequestObject a PathologyReportView object for request
   * @return response (type {@link TypedViewResponse<PathologyReportViewResponse> } 
   * @throws StandardErrorMsg Thrown in the event that the operation fails.
   */
  public final TypedViewResponse<PathologyReportViewResponse> getView(PCEHRHeader commonHeader,PathologyReportView getViewRequestObject) throws StandardErrorMsg {
      GetViewResponse viewResponse = getView( commonHeader,(Object) getViewRequestObject );
      return TypedViewResponse.unmarshall(PathologyReportViewResponse.class, viewResponse);  
  }
  
  
  /**
   * Invokes the web service operation that returns a list of representatives associated with a particular individual's PCEHR.
   *
   * @param commonHeader populated {@link PCEHRHeader} request object (Mandatory).
   * @param getViewRequestObject a DiagnosticImagingReportView object for request
   * @return response (type {@link TypedViewResponse<DiagnosticImagingReportViewResponse>})  
   * @throws StandardErrorMsg Thrown in the event that the operation fails.
   */
  public final TypedViewResponse<DiagnosticImagingReportViewResponse> getView(PCEHRHeader commonHeader,DiagnosticImagingReportView getViewRequestObject) throws StandardErrorMsg {
	  GetViewResponse viewResponse = getView( commonHeader,(Object) getViewRequestObject );
      return TypedViewResponse.unmarshall(DiagnosticImagingReportViewResponse.class, viewResponse);  
  }
  
  /**
   * Invokes the web service operation that returns a list of representatives associated with a particular individual's PCEHR.
   *
   * @param commonHeader populated {@link PCEHRHeader} request object (Mandatory).
   * @param getViewRequestObject a HealthRecordOverView object for request
   * @return response (type {@link  TypedViewResponse<HealthRecordOverviewResponse>})  
   * @throws StandardErrorMsg Thrown in the event that the operation fails.
   */
  public final TypedViewResponse<HealthRecordOverviewResponse> getView(PCEHRHeader commonHeader,HealthRecordOverView getViewRequestObject) throws StandardErrorMsg {
	  GetViewResponse viewResponse = getView( commonHeader,(Object) getViewRequestObject );
      return TypedViewResponse.unmarshall(HealthRecordOverviewResponse.class, viewResponse);  
  }
  
  
  /**
   * Invokes the web service operation that returns a list of representatives associated with a particular individual's PCEHR.
   *
   * This is the type-safe interface that will guarantee no JAXB exceptions
   *
   * @param commonHeader populated {@link PCEHRHeader} request object (Mandatory).
   * @param getViewRequestObject a HealthCheckScheduleView object for request
   * @return response (type {@link GetViewResponse})  
   * @throws StandardErrorMsg Thrown in the event that the operation fails.
   */
  public final GetViewResponse getView(PCEHRHeader commonHeader, HealthCheckScheduleView getViewRequestObject) throws StandardErrorMsg {
    return getView( commonHeader,(Object) getViewRequestObject );
  }

  
  /**
   * 
   * @param commonHeader             populated {@link PCEHRHeader} request object (Mandatory).
   * @param getViewRequestObject     getView Request Object, this object must be 'known' to the client software otherwise there will be 
   *                                 a binding error {@link JAXBException}
   * 
   * @return                         response (type {@link GetViewResponse}) 
   * 
   * @throws StandardErrorMsg        thrown in the event that the operation fails.
   * @throws JAXBException           thrown if there is a binding error or the GetView request object is not known to the JAXB context
   */
  public GetViewResponse getView( PCEHRHeader commonHeader, Object getViewRequestObject ) throws StandardErrorMsg {
    Validate.notNull(commonHeader, "'commonHeader' must be specified.");
    CommonHeaderValidator.validate(commonHeader, true); // IHINumber is required.

    // Response holder variables.

    Holder<GetViewResponse> responseHolder = new Holder<GetViewResponse>();
    Holder<Signature> signatureHolder = null;

    
	GetView getView = new GetView();
	getView.setView( getViewRequestObject  );
	
	
	// Attempt to call the doesPCEHRExist operation, storing response message values in the supplied holders.
    getPort().getView(
    		getView ,
    		responseHolder,
    		DateUtils.generateTimestamp(),
    		signatureHolder,
    		commonHeader );
    
    

    return responseHolder.value;
  }
}
