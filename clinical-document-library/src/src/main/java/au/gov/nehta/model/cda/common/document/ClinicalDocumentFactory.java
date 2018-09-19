package au.gov.nehta.model.cda.common.document;

import au.gov.nehta.model.cda.common.id.TemplateId;
import au.gov.nehta.model.cda.common.id.TemplateIdImpl;

/**
 * A factory to create the common clincal document objects
 */
public class ClinicalDocumentFactory {
    
    public static final TemplateId EPRESCRIPTION_TEMPLATE_ID = TemplateIdImpl.getInstance( "2.1", "1.2.36.1.2001.1001.101.100.1002.74" );
    public static final TemplateId NPDR_EPRESCRIPTION_TEMPLATE_ID = TemplateIdImpl.getInstance( "1.0", "1.2.36.1.2001.1001.100.1002.170" );
    public static final TemplateId DISPENSE_RECORD_TEMPLATE_ID = TemplateIdImpl.getInstance( "2.1", "1.2.36.1.2001.1001.101.100.1002.75" );
    public static final TemplateId NPDR_DISPENSE_RECORD_TEMPLATE_ID = TemplateIdImpl.getInstance( "1.0", "1.2.36.1.2001.1001.100.1002.171" );
    public static final TemplateId PRESCRIPTION_REQUEST_TEMPLATE_ID = TemplateIdImpl.getInstance( "1.1", "1.2.36.1.2001.1001.101.100.1002.101" );
    public static final TemplateId PATHOLOGY_REPORT_TEMPLATE_ID = TemplateIdImpl.getInstance( "1.0", "1.2.36.1.2001.1001.100.1002.220" ); 
    public static final TemplateId DIAGNOSTIC_IMAGING_TEMPLATE_ID = TemplateIdImpl.getInstance("1.0", "1.2.36.1.2001.1001.100.1002.222");
    public static final TemplateId E_REFERRAL = TemplateIdImpl.getInstance("2.2", "1.2.36.1.2001.1001.101.100.1002.2");
    public static final TemplateId SHARED_HEALTH_SUMMARY = TemplateIdImpl.getInstance("1.4", "1.2.36.1.2001.1001.101.100.1002.120");
    
    
    public static  ClinicalDocument getSharedHealthSummary() {
        ClinicalDocument clinicalDoc = new ClinicalDocumentImpl( SHARED_HEALTH_SUMMARY, "Shared Health Summary"  );
        return clinicalDoc;
    }

    
    public static  ClinicalDocument getEReferral() {
        ClinicalDocument clinicalDoc = new ClinicalDocumentImpl( E_REFERRAL, "e-Referral"  );
        return clinicalDoc;
    }

    public static ClinicalDocument getDiagnosticImagingReport() {
        ClinicalDocumentImpl clinicalDoc = new ClinicalDocumentImpl( DIAGNOSTIC_IMAGING_TEMPLATE_ID,"Diagnostic Imaging Result Report");
        return clinicalDoc;
    }
    
    public static ClinicalDocument getPathologyReport() {
        ClinicalDocumentImpl clinicalDoc = new ClinicalDocumentImpl( PATHOLOGY_REPORT_TEMPLATE_ID, "Pathology Result Report");
        return clinicalDoc;
    }
    
    public static BaseClinicalDocument getEPrescription() {
        BaseClinicalDocumentImpl clinicalDoc = new BaseClinicalDocumentImpl( EPRESCRIPTION_TEMPLATE_ID,"Prescripition Document" );
        return  clinicalDoc;
    }

    public static BaseClinicalDocument getNPDREPrescription() {
        BaseClinicalDocumentImpl clinicalDoc = new BaseClinicalDocumentImpl( NPDR_EPRESCRIPTION_TEMPLATE_ID,  "Prescripition Document" );
        return clinicalDoc;
    }
    
    
    public static BaseClinicalDocument getDispenseRecord() {
        BaseClinicalDocumentImpl clinicalDoc = new BaseClinicalDocumentImpl( DISPENSE_RECORD_TEMPLATE_ID,"Dispense Document" );
        return clinicalDoc;
    }
    
   public static BaseClinicalDocument getNPDRDispenseRecord() {
        BaseClinicalDocumentImpl clinicalDoc = new BaseClinicalDocumentImpl( NPDR_DISPENSE_RECORD_TEMPLATE_ID , "Dispense Document" );
        return clinicalDoc;
    }

    public static BaseClinicalDocument getPrescriptionRequest() {
        BaseClinicalDocumentImpl clinicalDoc = new BaseClinicalDocumentImpl( PRESCRIPTION_REQUEST_TEMPLATE_ID,"Prescription Request"  );
        return clinicalDoc;
    }
}
