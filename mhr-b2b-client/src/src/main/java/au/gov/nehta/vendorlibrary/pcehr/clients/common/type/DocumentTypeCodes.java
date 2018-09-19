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
package au.gov.nehta.vendorlibrary.pcehr.clients.common.type;

/**
 * Enumeration of Document Class, Document Type and Content Type Codes.
 */
public enum DocumentTypeCodes {

  /**
   * Shared health summary code.
   */
  SHARED_HEALTH_SUMMARY("LOINC", "2.16.840.1.113883.6.1", "60591-5", "Patient Summary", "Shared Health Summary"),

  /**
   * eReferral code.
   */
  EREFERRAL("LOINC", "2.16.840.1.113883.6.1", "57133-1", "Referral Note", "e-Referral"),

  /**
   * Specialist letter code.
   */
  SPECIALIST_LETTER("LOINC", "2.16.840.1.113883.6.1", "51852-2", "Letter", "Specialist Letter"),

  /**
   * Discharge summary code.
   */
  DISCHARGE_SUMMARY("LOINC", "2.16.840.1.113883.6.1", "18842-5", "Discharge Summarization Note", "Discharge Summary"),

  /**
   * Event summary code.
   */
  EVENT_SUMMARY("LOINC", "2.16.840.1.113883.6.1", "34133-9", "Summarization of episode note", "Event Summary"),

  
  ///
  /// These codes are not currently used by PCEHR
  ///also linked to FormatCodes
  
  /**
   * ePrescription code.
   */
//  EPRESCRIPTION("NCTIS", "1.2.36.1.2001.1001.101", "100.16764", "e-Prescription", "e-Prescription"),

  /**
   * Dispense record code.
   */
//  DISPENSE_RECORD("NCTIS", "1.2.36.1.2001.1001.101", "100.16765", "Dispense Record", "Dispense Record"),

  /**
   * Prescription request code.
   */
//  PRESCRIPTION_REQUEST("NCTIS", "1.2.36.1.2001.1001.101", "100.16285", "Prescription Request", "Prescription Request"),
  
  
  /**
   * Pharmaceutical benefits report code.
   */
  PHARMACEUTICAL_BENEFITS_REPORT("NCTIS", "1.2.36.1.2001.1001.101", "100.16650", "Pharmaceutical Benefits Report", "Pharmaceutical Benefits Report"),

  /**
   * Australian organ donor register code.
   */
  AUSTRALIAN_ORGAN_DONOR_REGISTER("NCTIS", "1.2.36.1.2001.1001.101", "100.16671", "Australian Organ Donor Register", "Australian Organ Donor Register"),

  /**
   * Australian childhood immunisation register code.
   */
  AUSTRALIAN_CHILDHOOD_IMMUNISATION_REGISTER("NCTIS", "1.2.36.1.2001.1001.101", "100.16659", "Australian Childhood Immunisation Register",
    "Australian Childhood Immunisation Register"),

  /**
   * Australian immunisation register code.
   */
  AUSTRALIAN_IMMUNISATION_REGISTER("NCTIS", "1.2.36.1.2001.1001.101", "100.17042", "Australian Immunisation Register",
    "Australian Immunisation Register"),
	
  /**
   * Medicare/DVA benefits report code.
   */
  MEDICARE_DVA_BENEFITS_REPORT("NCTIS", "1.2.36.1.2001.1001.101", "100.16644", "Medicare/DVA Benefits Report", "Medicare/DVA Benefits Report"),

  /**
   * Consumer entered notes code.
   */
  CONSUMER_ENTERED_NOTES("NCTIS Data Components", "1.2.36.1.2001.1001.101", "100.16681", "Consumer Entered Notes", "Personal Health Note"),

  /**
   * Consumer entered health summary code.
   */
  CONSUMER_ENTERED_HEALTH_SUMMARY("NCTIS Data Components", "1.2.36.1.2001.1001.101", "100.16685", "Consumer Entered Health Summary", "Personal Health Summary"),

  /**
   * Advanced care directive custodian record code.
   */
  ADVANCED_CARE_DIRECTIVE_CUSTODIAN_RECORD("NCTIS Data Components", "1.2.36.1.2001.1001.101", "100.16696", "Advance Care Directive Custodian Record",
    "Advance Care Directive Custodian Record"),

  /**
   * PCEHR Prescription code.
   */
  PCEHR_PRESCRIPTION("NCTIS Data Components", "1.2.36.1.2001.1001.101", "100.16764", "PCEHR Prescription Record", "eHealth Prescription Record"),

  /**
   * PCEHR Dispense code.
   */
  PCEHR_DISPENSE_RECORD("NCTIS Data Components", "1.2.36.1.2001.1001.101", "100.16765", "PCEHR Dispense Record", "eHealth Dispense Record"),
  
  /**
   * Pathology Report
   */
  PATHOLOGY_REPORT("NCTIS Data Components", "1.2.36.1.2001.1001.101", "100.32001", "Pathology Report", "Pathology Report"),
  
  /**
   * DI Report
   */
  DIAGNOSTIC_IMAGE_REPORT("NCTIS Data Components", "1.2.36.1.2001.1001.101", "100.16957", "Diagnostic Imaging Report", "Diagnostic Imaging Report"),
  
  /**
   *Advance Care Information
   */
  ADVANCED_CARE_DIRECTIVE("NCTIS Data Components", "1.2.36.1.2001.1001.101", "100.16975", "Advance Care Information", "Advance Care Information"),
  

  //cehr documents
  
  /**
   *Health Check Assessment
   */
 HEALTH_CHECK_ASSESMENT("NCTIS Data Components", "1.2.36.1.2001.1001.101", "100.16920", "Health Check Assessment", "Health Check Assessment"),
  
 

 /**
  *Child Parent Questionnaire
  */
 CHILD_PARENT_QUESTIONNAIRE("NCTIS Data Components", "1.2.36.1.2001.1001.101", "100.16919", "Child Parent Questionnaire", "Child Parent Questionnaire"),


 /**
  *Consumer Entered Achievements
  */
 CONSUMER_ENTERED_ACHIEVEMENTS("NCTIS Data Components", "1.2.36.1.2001.1001.101", "100.16812", "Consumer Entered Achievements", "Consumer Entered Achievements"),
 
 /**
  *Birth Details
  */
 BIRTH_DETAILS("NCTIS Data Components", "1.2.36.1.2001.1001.101", "100.16812", "Birth Details", "Birth Details"),
 
 /**
  *Medicare Overview - all
  */
 MEDICARE_OVERVIEW_ALL("NCTIS Data Components", "1.2.36.1.2001.1001.101", "100.16767.1", "Medicare Overview - all", "Medicare Overview - all"),
 
 /**
  *Medicare Overview - past 12 months
  */
 MEDICARE_OVERVIEW_PAST_12_MONTHS("NCTIS Data Components", "1.2.36.1.2001.1001.101", "100.16767.2", "Medicare Overview - past 12 months", "Medicare Overview - past 12 months"),
 
 /**
  *Medicines View
  */
 MEDICINES_VIEW("NCTIS Data Components", "1.2.36.1.2001.1001.101", "100.32002", "Medicines View", "Medicines View"),

 /**
  *Pharmacist Curated Medicines List
  */
 PHARMACIST_CURATED_MEDICINES_LIST("LOINC", "2.16.840.1.113883.6.1", "56445-0", "Pharmacist Curated Medicines List", "Pharmacist Curated Medicines List"),

  
;


  private String codingSystem;
  private String codingSystemOid;
  private String code;
  private String classCodeDisplayName;
  private String typeCodeDisplayName;

  private DocumentTypeCodes(String codingSystem, String codingSystemOid, String code, String classCodeDisplayName, String typeCodeDisplayName) {
    this.codingSystem = codingSystem;
    this.codingSystemOid = codingSystemOid;
    this.code = code;
    this.classCodeDisplayName = classCodeDisplayName;
    this.typeCodeDisplayName = typeCodeDisplayName;
  }

  /**
   * Retrieve the coding scheme.
   *
   * @return {@link CodedValue}.
   */
  public CodedValue getCodingSystem() {
    return new CodedValue(codingSystem, codingSystemOid, code, classCodeDisplayName);
  }

  private String getCode() {
    return code;
  }

  /**
   * Retrieves a {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentTypeCodes} based on a supplied concept code.
   *
   * @param conceptCode Concept to search for.
   * @return Corresponding {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentTypeCodes} or null.
   */
  public static DocumentTypeCodes findByConceptCode(String conceptCode) {
    for (DocumentTypeCodes v : values()) {
      if (v.getCode().equals(conceptCode)) {
        return v;
      }
    }
    return null;
  }

  /**
   * Retrieve type code display name.
   *
   * @return Type code display name.
   */
  public String getTypeCodeDisplayName() {
    return typeCodeDisplayName;
  }
}
