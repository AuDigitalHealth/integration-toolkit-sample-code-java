package au.gov.nehta.vendorlibrary.pcehr.test.utils;

import java.util.Date;


public final class Endpoints {

  /**
   * DEFAULT OPERATIONS
   */
  public static final String DOES_PCEHR_EXIST = "/doesPCEHRExist";
  public static final String GAIN_PCEHR_ACCESS = "/gainPCEHRAccess";
  public static final String GET_DOCUMENT_LIST = "/getDocumentList";
  public static final String REMOVE_DOCUMENT = "/removeDocument";
  public static final String UPLOAD_DOCUMENT = "/uploadDocument";
  public static final String GET_DOCUMENT = "/getDocument";
  public static final String GET_CHANGE_HISTORY_VIEW = "/getChangeHistoryView";
  public static final String GET_AUDIT_VIEW = "/getAuditView";
  public static final String GET_VIEW = "/getView";
  public static final String GET_CONSOLIDATED_VIEW = "/getConsolidatedView";
  public static final String SEARCH_TEMPLATE = "/searchTemplate";
  public static final String GET_TEMPLATE = "/getTemplate";
  public static final String UPLOAD_DOCUMENT_METADATA = "/uploadDocumentMetadata";
  public static final String REGISTER_PCEHR = "/registerPCEHR";
  public static final String GET_REPRESENTATIVE_LIST = "/getRepresentativeList";

  public static final String ACCENTURE_NOC_HOST = "https://b2b.ehealthvendortest.health.gov.au";
  public static final String ACCENTURE_DOES_PCEHR_EXIST = ACCENTURE_NOC_HOST + DOES_PCEHR_EXIST;
  public static final String ACCENTURE_GAIN_PCEHR_ACCESS = ACCENTURE_NOC_HOST + GAIN_PCEHR_ACCESS;
  public static final String ACCENTURE_GET_DOCUMENT_LIST = ACCENTURE_NOC_HOST + GET_DOCUMENT_LIST;
  public static final String ACCENTURE_REMOVE_DOCUMENT = ACCENTURE_NOC_HOST + REMOVE_DOCUMENT;
  public static final String ACCENTURE_UPLOAD_DOCUMENT = ACCENTURE_NOC_HOST + UPLOAD_DOCUMENT;
  public static final String ACCENTURE_GET_DOCUMENT = ACCENTURE_NOC_HOST + GET_DOCUMENT;
  public static final String ACCENTURE_GET_CHANGE_HISTORY_VIEW = ACCENTURE_NOC_HOST + GET_CHANGE_HISTORY_VIEW;
  public static final String ACCENTURE_GET_AUDIT_VIEW = ACCENTURE_NOC_HOST + GET_AUDIT_VIEW;
  public static final String ACCENTURE_GET_VIEW = ACCENTURE_NOC_HOST + GET_VIEW;
  public static final String ACCENTURE_GET_CONSOLIDATED_VIEW = ACCENTURE_NOC_HOST + GET_CONSOLIDATED_VIEW;
  public static final String ACCENTURE_SEARCH_TEMPLATE = ACCENTURE_NOC_HOST + SEARCH_TEMPLATE;
  public static final String ACCENTURE_GET_TEMPLATE = ACCENTURE_NOC_HOST + GET_TEMPLATE;
  public static final String ACCENTURE_UPLOAD_DOCUMENT_METADATA = ACCENTURE_NOC_HOST + UPLOAD_DOCUMENT_METADATA;
  public static final String ACCENTURE_REGISTER_PCEHR = ACCENTURE_NOC_HOST + REGISTER_PCEHR;
  public static final String ACCENTURE_GET_REPRESENTATIVE_LIST = ACCENTURE_NOC_HOST + GET_REPRESENTATIVE_LIST;

  public static final String ACCENTURE__VPN__GET_VIEW = "https://10.81.144.51/getView";
  public static final String LOCAL_TCP_MONITOR_INTERCEPTOR_GET_VIEW = "http://localhost:8099/getView";
  
  public static final String PM1_HOST = "https://144.140.140.134";
  public static final String PM1_DOES_PCEHR_EXIST = PM1_HOST + DOES_PCEHR_EXIST;
  public static final String PM1_GAIN_PCEHR_ACCESS = PM1_HOST + GAIN_PCEHR_ACCESS;
  public static final String PM1_GET_DOCUMENT_LIST = PM1_HOST + GET_DOCUMENT_LIST;
  public static final String PM1_REMOVE_DOCUMENT = PM1_HOST + REMOVE_DOCUMENT;
  public static final String PM1_UPLOAD_DOCUMENT = PM1_HOST + UPLOAD_DOCUMENT;
  public static final String PM1_GET_DOCUMENT = PM1_HOST + GET_DOCUMENT;
  public static final String PM1_GET_CHANGE_HISTORY_VIEW = PM1_HOST + GET_CHANGE_HISTORY_VIEW;
  public static final String PM1_GET_AUDIT_VIEW = PM1_HOST + GET_AUDIT_VIEW;
  public static final String PM1_GET_CONSOLIDATED_VIEW = PM1_HOST + GET_CONSOLIDATED_VIEW;
  public static final String PM1_SEARCH_TEMPLATE = PM1_HOST + SEARCH_TEMPLATE;
  public static final String PM1_GET_TEMPLATE = PM1_HOST + GET_TEMPLATE;
  public static final String PM1_UPLOAD_DOCUMENT_METADATA = PM1_HOST + UPLOAD_DOCUMENT_METADATA;
  public static final String PM1_REGISTER_PCEHR = PM1_HOST + REGISTER_PCEHR;
  public static final String PM1_GET_REPRESENTATIVE_LIST = PM1_HOST + GET_REPRESENTATIVE_LIST;


  public static final String PTC_HOST = "https://10.81.150.4";
  public static final String PTC_DOES_PCEHR_EXIST = PTC_HOST + DOES_PCEHR_EXIST;
  public static final String PTC_GAIN_PCEHR_ACCESS = PTC_HOST + GAIN_PCEHR_ACCESS;
  public static final String PTC_GET_DOCUMENT_LIST = PTC_HOST + GET_DOCUMENT_LIST;
  public static final String PTC_REMOVE_DOCUMENT = PTC_HOST + REMOVE_DOCUMENT;
  public static final String PTC_UPLOAD_DOCUMENT = PTC_HOST + UPLOAD_DOCUMENT;
  public static final String PTC_GET_DOCUMENT = PTC_HOST + GET_DOCUMENT;
  public static final String PTC_GET_CHANGE_HISTORY_VIEW = PTC_HOST + GET_CHANGE_HISTORY_VIEW;
  public static final String PTC_GET_AUDIT_VIEW = PTC_HOST + GET_AUDIT_VIEW;
  public static final String PTC_GET_CONSOLIDATED_VIEW = PTC_HOST + GET_CONSOLIDATED_VIEW;
  public static final String PTC_SEARCH_TEMPLATE = PTC_HOST + SEARCH_TEMPLATE;
  public static final String PTC_GET_TEMPLATE = PTC_HOST + GET_TEMPLATE;
  public static final String PTC_UPLOAD_DOCUMENT_METADATA = PTC_HOST + UPLOAD_DOCUMENT_METADATA;
  public static final String PTC_REGISTER_PCEHR = PTC_HOST + REGISTER_PCEHR;
  public static final String PTC_GET_REPRESENTATIVE_LIST = PTC_HOST + GET_REPRESENTATIVE_LIST;

  public static final String REGRESSION_HOST = "https://144.140.140.218:443";
  public static final String REGRESSION_DOES_PCEHR_EXIST = REGRESSION_HOST + DOES_PCEHR_EXIST;
  public static final String REGRESSION_GAIN_PCEHR_ACCESS = REGRESSION_HOST + GAIN_PCEHR_ACCESS;
  public static final String REGRESSION_GET_DOCUMENT_LIST = REGRESSION_HOST + GET_DOCUMENT_LIST;
  public static final String REGRESSION_REMOVE_DOCUMENT = REGRESSION_HOST + REMOVE_DOCUMENT;
  public static final String REGRESSION_UPLOAD_DOCUMENT = REGRESSION_HOST + UPLOAD_DOCUMENT;
  public static final String REGRESSION_GET_DOCUMENT = REGRESSION_HOST + GET_DOCUMENT;
  public static final String REGRESSION_GET_CHANGE_HISTORY_VIEW = REGRESSION_HOST + GET_CHANGE_HISTORY_VIEW;
  public static final String REGRESSION_GET_AUDIT_VIEW = REGRESSION_HOST + GET_AUDIT_VIEW;
  public static final String REGRESSION_GET_CONSOLIDATED_VIEW = REGRESSION_HOST + GET_CONSOLIDATED_VIEW;
  public static final String REGRESSION_SEARCH_TEMPLATE = REGRESSION_HOST + SEARCH_TEMPLATE;
  public static final String REGRESSION_GET_TEMPLATE = REGRESSION_HOST + GET_TEMPLATE;
  public static final String REGRESSION_UPLOAD_DOCUMENT_METADATA = REGRESSION_HOST + UPLOAD_DOCUMENT_METADATA;
  public static final String REGRESSION_REGISTER_PCEHR = REGRESSION_HOST + REGISTER_PCEHR;
  public static final String REGRESSION_GET_REPRESENTATIVE_LIST = REGRESSION_HOST + GET_REPRESENTATIVE_LIST;

  public static final String DEV_HOST= "https://10.81.144.5";
  public static final String DEV_UPLOAD_DOCUMENT = DEV_HOST + UPLOAD_DOCUMENT;

  public static final String XRP_HOST = "https://portal.xrp.nehta.org.au";
  public static final String IRP_HOST = "https://portal2-nehta-rp-db1.nehta.net.au";
  public static final String DRP_HOST = "https://portal2-nehta-drp-iis.nehta.net.au";

  public static final String DRP_VE2E_UPLOAD_DOCUMENT = DRP_HOST + "/VendorEnd2End/PCEHR/DocumentRepositoryService.svc";
  public static final String IRP_VE2E_UPLOAD_DOCUMENT = IRP_HOST + "/VendorEnd2End/PCEHR/DocumentRepositoryService.svc";
  public static final String XRP_VE2E_UPLOAD_DOCUMENT = XRP_HOST + "/VendorEnd2End/PCEHR/DocumentRepositoryService.svc";
  public static final String DRP_VE2E_GET_DOCUMENT = DRP_HOST + "/VendorEnd2End/PCEHR/DocumentRepositoryService.svc";
  public static final String IRP_VE2E_GET_DOCUMENT = IRP_HOST + "/VendorEnd2End/PCEHR/DocumentRepositoryService.svc";
  public static final String XRP_VE2E_GET_DOCUMENT = XRP_HOST + "/VendorEnd2End/PCEHR/DocumentRepositoryService.svc";

  public static final String SVT = "https://b2b.ehealthvendortest.health.gov.au";
  public static final String SVT_UPLOAD_DOCUMENT = SVT+UPLOAD_DOCUMENT;
  public static final String SVT_GET_VIEW = SVT+GET_VIEW;
  
 
  private Endpoints() {
  }
}
