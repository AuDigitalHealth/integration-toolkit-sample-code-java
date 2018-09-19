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
 * Constants used by IHE XDS services.
 */
public final class XDSConstants {

  /**
   * Response status - success.
   */
  public static final String RESPONSE_STATUS_SUCCESS = "urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success";


  /**
   * Response status - partial success.
   */
  public static final String RESPONSE_STATUS_PARTIAL_SUCCESS = "urn:ihe:iti:2007:ResponseStatusType:PartialSuccess";


  /**
   * Response status - failure.
   */
  public static final String RESPONSE_STATUS_FAILURE = "urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure";

  /**
   * ExtrinsicObjectType (document) Status - deprecated.
   */
  public static final String EOT_STATUS_DEPRECATED = "urn:oasis:names:tc:ebxml-regrep:StatusType:Deprecated";

  /**
   * ExtrinsicObjectType (document) Status - approved.
   */
  public static final String EOT_STATUS_APPROVED = "urn:oasis:names:tc:ebxml-regrep:StatusType:Approved";

  /**
   * XDS registry error severity type.
   */
  public static final String ERROR_SEVERITY_ERROR = "urn:oasis:names:tc:ebxml-regrep:ErrorSeverityType:Error";

  /**
   * XDSSubmissionSet UUID - set.
   */
  public static final String XDS_SUBMISSION_SET_UUID = "urn:uuid:a54d6aa5-d40d-43f9-88c5-b4633d873bdd";


  /**
   * XDSSubmissionSet UUID - author.
   */
  public static final String XDS_SUBMISSION_SET_AUTHOR_UUID = "urn:uuid:a7058bb9-b4e4-4307-ba5b-e3f0ab85e12d";


  /**
   * XDSSubmissionSet UUID - content type code.
   */
  public static final String XDS_SUBMISSION_SET_CONTENT_TYPE_CODE_UUID = "urn:uuid:aa543740-bdda-424e-8c96-df4873be8500";


  /**
   * XDSSubmissionSet UUID - patient id.
   */
  public static final String XDS_SUBMISSION_SET_PATIENT_ID_UUID = "urn:uuid:6b5aea1a-874d-4603-a4bc-96a0a7b38446";


  /**
   * XDSSubmissionSet UUID - source id.
   */
  public static final String XDS_SUBMISSION_SET_SOURCE_ID_UUID = "urn:uuid:554ac39e-e3fe-47fe-b233-965d2a147832";

  /**
   * XDSSubmissionSet UUID - unique id.
   */
  public static final String XDS_SUBMISSION_SET_UNIQUE_ID_UUID = "urn:uuid:96fdda7c-d067-4183-912e-bf5ee74998a8";

  /**
   * XDSSubmissionSet UUID - limited metadata id.
   */
  public static final String XDS_SUBMISSION_SET_LIMITED_METADATA_UUID = "urn:uuid:5003a9db-8d8d-49e6-bf0c-990e34ac7707";

  /**
   * XDSDocumentEntry UUID - entry.
   */
  public static final String XDS_DOCUMENT_ENTRY_UUID = "urn:uuid:7edca82f-054d-47f2-a032-9b2a5b5186c1";

  /**
   * XDSDocumentEntry UUID - author.
   */
  public static final String XDS_DOCUMENT_ENTRY_AUTHOR_UUID = "urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d";

  /**
   * XDSDocumentEntry UUID - class code.
   */
  public static final String XDS_DOCUMENT_ENTRY_CLASS_CODE_UUID = "urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a";

  /**
   * XDSDocumentEntry UUID - confidentiality code.
   */
  public static final String XDS_DOCUMENT_ENTRY_CONFIDENTIALITY_CODE_UUID = "urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840f";

  /**
   * XDSDocumentEntry UUID - event code list.
   */
  public static final String XDS_DOCUMENT_ENTRY_EVENT_CODE_LIST_UUID = "urn:uuid:2c6b8cb7-8b2a-4051-b291-b1ae6a575ef4";

  /**
   * XDSDocumentEntry UUID - format code.
   */
  public static final String XDS_DOCUMENT_ENTRY_FORMAT_CODE_UUID = "urn:uuid:a09d5840-386c-46f2-b5ad-9c3699a4309d";

  /**
   * XDSDocumentEntry UUID - healthcare facility type code.
   */
  public static final String XDS_DOCUMENT_ENTRY_HEALTHCARE_FACILITY_TYPE_CODE_UUID = "urn:uuid:f33fb8ac-18af-42cc-ae0e-ed0b0bdb91e1";

  /**
   * XDSDocumentEntry UUID - patient id.
   */
  public static final String XDS_DOCUMENT_ENTRY_PATIENT_ID_UUID = "urn:uuid:58a6f841-87b3-4a3e-92fd-a8ffeff98427";

  /**
   * XDSDocumentEntry UUID - practice setting code.
   */
  public static final String XDS_DOCUMENT_ENTRY_PRACTICE_SETTING_CODE_UUID = "urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead";

  /**
   * XDSDocumentEntry UUID - entry type code.
   */
  public static final String XDS_DOCUMENT_ENTRY_TYPE_CODE_UUID = "urn:uuid:f0306f51-975f-434e-a61c-c59651d33983";

  /**
   * XDSDocumentEntry UUID - unique id.
   */
  public static final String XDS_DOCUMENT_ENTRY_UNIQUE_ID_UUID = "urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab";

  /**
   * XDSDocumentEntry UUID - limited metadata.
   */
  public static final String XDS_DOCUMENT_ENTRY_LIMITED_METADATA_UUID = "urn:uuid:ab9b591b-83ab-4d03-8f5d-f93b1fb92e85";

  /**
   * Object type URN - association.
   */
  public static final String OBJECT_TYPE_ASSOCIATION = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Association";

  /**
   * Object type URN - classification.
   */
  public static final String OBJECT_TYPE_CLASSIFICATION = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification";
  /**
   * Object type URN - external identifier.
   */
  public static final String OBJECT_TYPE_EXTERNAL_IDENTIFIER = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExternalIdentifier";

  /**
   * Object type URN - registry package.
   */
  public static final String OBJECT_TYPE_REGISTRY_PACKAGE = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:RegistryPackage";

  /**
   * Slot Name - author person.
   */
  public static final String AUTHOR_PERSON_SLOT = "authorPerson";

  /**
   * Slot Name - author speciality.
   */
  public static final String AUTHOR_SPECIALITY_SLOT = "authorSpecialty";

  
  /**
   * Slot Name - author institution.
   */
  public static final String AUTHOR_INSTITUTION_SLOT = "authorInstitution";

  /**
   * Slot Name - coding scheme.
   */
  public static final String CODING_SCHEME_SLOT = "codingScheme";

  /**
   * Slot Name - creation time.
   */
  public static final String CREATION_TIME_SLOT = "creationTime";

  /**
   * Slot Name - hash.
   */
  public static final String HASH_SLOT = "hash";

  /**
   * Slot Name - language code.
   */
  public static final String LANGUAGE_CODE_SLOT = "languageCode";

  /**
   * Slot Name - repository unique ID.
   */
  public static final String REPOSITORY_UNIQUE_ID = "repositoryUniqueId";

  /**
   * Slot Name - service start time.
   */
  public static final String SERVICE_START_TIME_SLOT = "serviceStartTime";

  /**
   * Slot Name - service stop time.
   */
  public static final String SERVICE_STOP_TIME_SLOT = "serviceStopTime";

  /**
   * Slot Name - size.
   */
  public static final String SIZE_SLOT = "size";

  /**
   * Slot Name - source patient id.
   */
  public static final String SOURCE_PATIENT_ID_SLOT = "sourcePatientId";

  /**
   * Slot Name - submission set status.
   */
  public static final String SUBMISSION_SET_STATUS_SLOT = "SubmissionSetStatus";

  /**
   * Slot Name - submission time.
   */
  public static final String SUBMISSION_TIME_SLOT = "submissionTime";

  /**
   * IHI suffix.
   */
  public static final String IHI_SUFFIX = "^^^&1.2.36.1.2001.1003.0&ISO";

  /**
   * Association Type - has member.
   */
  public static final String ASSOCIATION_TYPE_HAS_MEMBER = "urn:oasis:names:tc:ebxml-regrep:AssociationType:HasMember";

  /**
   * Association Type - replaces.
   */
  public static final String ASSOCIATION_TYPE_REPLACES = "urn:ihe:iti:2007:AssociationType:RPLC";

  /**
   * Association Type Name - replaces.
   */
  public static final String ASSOCIATION_TYPE_REPLACES_NAME = "Replace Document";

  /**
   * Document Entry Query Parameter Name - patient id.
   */
  public static final String QUERY_PARAM_DOCUMENT_ENTRY_PATIENT_ID = "$XDSDocumentEntryPatientId";

  /**
   * Document Entry Query Parameter Name - class code.
   */
  public static final String QUERY_PARAM_DOCUMENT_ENTRY_CLASS_CODE = "$XDSDocumentEntryClassCode";

  /**
   * Document Entry Query Parameter Name - type code.
   */
  public static final String QUERY_PARAM_DOCUMENT_ENTRY_TYPE_CODE = "$XDSDocumentEntryTypeCode";

  /**
   * Document Entry Query Parameter Name - practice setting code.
   */
  public static final String QUERY_PARAM_DOCUMENT_ENTRY_PRACTICE_SETTING_CODE = "$XDSDocumentEntryPracticeSettingCode";

  /**
   * Document Entry Query Parameter Name - creation time (from).
   */
  public static final String QUERY_PARAM_DOCUMENT_ENTRY_CREATION_TIME_FROM = "$XDSDocumentEntryCreationTimeFrom";

  /**
   * Document Entry Query Parameter Name - creation time (to).
   */
  public static final String QUERY_PARAM_DOCUMENT_ENTRY_CREATION_TIME_TO = "$XDSDocumentEntryCreationTimeTo";

  /**
   * Document Entry Query Parameter Name - service start time (from).
   */
  public static final String QUERY_PARAM_DOCUMENT_ENTRY_SERVICE_START_TIME_FROM = "$XDSDocumentEntryServiceStartTimeFrom";

  /**
   * Document Entry Query Parameter Name - service start time (to).
   */
  public static final String QUERY_PARAM_DOCUMENT_ENTRY_SERVICE_START_TIME_TO = "$XDSDocumentEntryServiceStartTimeTo";

  /**
   * Document Entry Query Parameter Name - service stop time (from).
   */
  public static final String QUERY_PARAM_DOCUMENT_ENTRY_SERVICE_STOP_TIME_FROM = "$XDSDocumentEntryServiceStopTimeFrom";

  /**
   * Document Entry Query Parameter Name - service stop time (to).
   */
  public static final String QUERY_PARAM_DOCUMENT_ENTRY_SERVICE_STOP_TIME_TO = "$XDSDocumentEntryServiceStopTimeTo";

  /**
   * Document Entry Query Parameter Name - healthcare facility type code.
   */
  public static final String QUERY_PARAM_DOCUMENT_ENTRY_HEALTHCARE_FACILITY_TYPE_CODE = "$XDSDocumentEntryHealthcareFacilityTypeCode";

  /**
   * Document Entry Query Parameter Name - event code list.
   */
  public static final String QUERY_PARAM_DOCUMENT_ENTRY_EVENT_CODE_LIST = "$XDSDocumentEntryEventCodeList";

  /**
   * Document Entry Query Parameter Name - confidentiality code.
   */
  public static final String QUERY_PARAM_DOCUMENT_ENTRY_CONFIDENTIALITY_CODE = "$XDSDocumentEntryConfidentialityCode";

  /**
   * Document Entry Query Parameter Name - author person.
   */
  public static final String QUERY_PARAM_DOCUMENT_ENTRY_AUTHOR_PERSON = "$XDSDocumentEntryAuthorPerson";

  /**
   * Document Entry Query Parameter Name - format code.
   */
  public static final String QUERY_PARAM_DOCUMENT_ENTRY_FORMAT_CODE = "$XDSDocumentEntryFormatCode";

  /**
   * Document Entry Query Parameter Name - status.
   */
  public static final String QUERY_PARAM_DOCUMENT_ENTRY_STATUS = "$XDSDocumentEntryStatus";

  /**
   * Universal Identifer Type - ISO.
   */
  public static final String ISO_IDENTIFIER_TYPE = "ISO";

  /**
   * Adhoc query ID - findDocuments/getDocumentList.
   */
  public static final String ADHOC_QUERY_ID_FIND_DOCUMENTS = "urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d";

  /**
   * XDS document entryId.
   */
  public static final String DOCUMENT_ENTRY_ID = "DOCUMENT_SYMBOLICID_01";

  /**
   * XDS document entryId.
   */
  public static final String SUBSET_ENTRY_ID = "SUBSET_SYMBOLICID_01";

  /**
   * This class should not be instantiated.
   */
  private XDSConstants() {
  }
}
