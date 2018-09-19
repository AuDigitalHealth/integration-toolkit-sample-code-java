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
package au.gov.nehta.vendorlibrary.pcehr.clients.common.constant;

/**
 * XPath expression strings used throughout the client libraries.
 */
public final class XPathExpressions {

  /**
   * Authoring Organisation - HPI-O.
   */
  public static final String ORG_ID_AUTHOR = 
   "/ClinicalDocument/author/assignedAuthor/assignedPerson/asEmployment/employerOrganization/asOrganizationPartOf/wholeOrganization/asEntityIdentifier[@classCode='IDENT']/id[@assigningAuthorityName='HPI-O']/@root";

  /**
   * Authoring Organisation Name 
   */  
  public static final String ORG_ID_AUTHOR_NAME = 
  "/ClinicalDocument/author/assignedAuthor/assignedPerson/asEmployment/employerOrganization/asOrganizationPartOf/wholeOrganization/name";
  
  /**
   * Custodian ORG ID - HPIO 
   */
  public static final String ORG_ID_CUSTODIAN =  
  "/ClinicalDocument/custodian/assignedCustodian/representedCustodianOrganization/asEntityIdentifier[@classCode='IDENT']/id[@assigningAuthorityName='HPI-O']/@root";

  /**
   * Custodian ORG Name 
   */
  public static final String ORG_ID_CUSTODIAN_NAME =  
  "/ClinicalDocument/custodian/assignedCustodian/representedCustodianOrganization/name";
  
  
  /**
   * Health Care Facility ORG ID - HPIO 
   */
  public static final String ORG_ID_HCF =  
    "/ClinicalDocument/componentOf/encompassingEncounter/location/healthCareFacility/serviceProviderOrganization/asOrganizationPartOf/wholeOrganization/asEntityIdentifier[@classCode='IDENT']/id[@assigningAuthorityName='HPI-O']/@root";
  
  
  /**
   * Health Care Facility ORG name  
   */
  public static final String ORG_ID_HCF_NAME =  
    
 "/ClinicalDocument/componentOf/encompassingEncounter/location/healthCareFacility/serviceProviderOrganization/asOrganizationPartOf/wholeOrganization/name";
  
  
  /**
   * Authoring Individual - HPI-I.
   */
  public static final String HPII = 
		 "/ClinicalDocument/author/assignedAuthor/assignedPerson/asEntityIdentifier[@classCode='IDENT']/id[@assigningAuthorityName='HPI-I']/@root";

  /**
   * A PAI-D identifier.
   */
  public static final String PAI_D = 
		 "/ClinicalDocument/author/assignedAuthor/assignedPerson/asEntityIdentifier[@classCode='IDENT']/id[@assigningAuthorityName='PAI-D']/@root";

  
  
  /**
   * Authoring Authority Name, for use when the Identifier is a LocalIdentityType
   */
  public static final String AUTHOR_IDENTITY_AUTHORITY_ROOT =
  "/ClinicalDocument/author/assignedAuthor/assignedPerson/asEntityIdentifier[@classCode='IDENT']/id/@root";
  
  /**
   * Authoring Authority Name  Extension
   */
  public static final String AUTHOR_IDENTITY_AUTHORITY_EXTENSION =
  "/ClinicalDocument/author/assignedAuthor/assignedPerson/asEntityIdentifier[@classCode='IDENT']/id/@extension";
  
  
  /**
   * Author Specialty .
   */
  public static final String AUTHOR_SPECIALTY_NAME = "/ClinicalDocument/author/assignedAuthor/code/@displayName";

  
  /**
   * Authoring Individual - Family name.
   */
  public static final String AUTHOR_FAMILY_NAME = "/ClinicalDocument/author/assignedAuthor/assignedPerson/name/family";

  /**
   * Authoring Individual - Given name.
   */
  public static final String AUTHOR_GIVEN_NAME = "/ClinicalDocument/author/assignedAuthor/assignedPerson/name/given[1]";

  /**
   * Authoring Individual - Prefix.
   */
  public static final String AUTHOR_PREFIX = "/ClinicalDocument/author/assignedAuthor/assignedPerson/name/prefix";


  /**
   * Document Type - Code system name.
   */
  public static final String DOCUMENT_TYPE_CODING_SYSTEM_NAME = "/ClinicalDocument/code/@codeSystemName";

  /**
   * Document Type - Code.
   */
  public static final String DOCUMENT_TYPE_CODE = "/ClinicalDocument/code/@code";

  /**
   * Document Type - Display name.
   */
  public static final String DOCUMENT_TYPE_DISPLAY_NAME = "/ClinicalDocument/code/@displayName";

  /**
   * Creation time.
   */
  public static final String CREATION_TIME = "/ClinicalDocument/effectiveTime/@value";

  /**
   * Service start time.
   */
  public static final String SERVICE_START_TIME = "/ClinicalDocument/componentOf/encompassingEncounter/effectiveTime/low/@value";

  /**
   * Service stop time.
   */
  public static final String SERVICE_STOP_TIME = "/ClinicalDocument/componentOf/encompassingEncounter/effectiveTime/high/@value";

  /**
   * Service fixed time.
   */
  public static final String SERVICE_FIXED_TIME = "/ClinicalDocument/componentOf/encompassingEncounter/effectiveTime/@value";

  /**
   * Title.
   */
  public static final String TITLE = "/ClinicalDocument/title";

  /**
   * Patient ID - IHI.
   */
  public static final String PATIENT_ID = "/ClinicalDocument/recordTarget/patientRole/patient/asEntityIdentifier/id[@assigningAuthorityName='IHI']/@root";

  /**
   * Source Patient ID.
   */
  //public static final String SOURCE_PATIENT_ID = "/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/ cda:id";

  /**
   * Document ID.
   */
  public static final String DOCUMENT_ID = "/ClinicalDocument/id/@root";

  /**
   * Language code.
   */
  public static final String LANGUAGE_CODE = "/ClinicalDocument/languageCode/@code";

  
  /**
   * Prescription code
   * 
   */
  public static final String PRESCRIPTION_DOCUMENT_CODE="/ClinicalDocument/code/@code='100.16764'";
  
  
  /**
   * Dispense code
   * 
   */
  public static final String DISPENSE_DOCUMENT_CODE="/ClinicalDocument/code/@code='100.16765'";
  
  /**
   * Prescription Time 
   */
  public static final String PRESCRIPTION_TIME =  "/ClinicalDocument/author/time/@value";
  
  /**
   * Dispense Time 
   */
  public static final String DISPENSE_TIME =  "/ClinicalDocument/component/structuredBody/component/section[code/@code='102.16210' and code/@codeSystem='1.2.36.1.2001.1001.101']/entry/substanceAdministration/entryRelationship/supply/effectiveTime/@value";
  
  /**
   * Pathology report, pathology collection time
   */
  public static final String PATHOLOGY_COLLECTION_TIME ="/ClinicalDocument/component/structuredBody/component/section[code/@code='101.20018']/component/section[code/@code='102.16144']/entry/observation/entryRelationship/observation[code/@code='102.16156']/effectiveTime/@value";
  
  /**
   *Diagnostic Image collection time
   */
  public static final String DIAGNOSTIC_IMAGE_TIME =  "/ClinicalDocument/component/structuredBody/component/section/component/section/entry/observation/entryRelationship/act[code/@code='102.16511']/entryRelationship/observation/effectiveTime/@value";

  
  
 
  
  /**
   * This class should not be instantiated.
   */
  private XPathExpressions() {
  }
}
