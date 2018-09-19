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
 * Enumeration of Healthcare Facility Type Codes.
 */
public enum HealthcareFacilityTypeCodes {
  /**
   * Aged Care Residential Services code.
   */
  AGED_CARE_RESIDENTIAL_SERVICES("ANZSIC", "8601", "Aged Care Residential Services"),

  /**
   * Ambulance Services code.
   */
  AMBULANCE_SERVICES("ANZSIC", "8591", "Ambulance Services"),

  /**
   * Call Centre Operation code.
   */
  CALL_CENTRE_OPERATION("ANZSIC", "7294", "Call Centre Operation"),

  /**
   * Central Government Healthcare Administration code.
   */
  CENTRAL_GOVERNMENT_HEALTHCARE_ADMINISTRATION("ANZSIC", "7511", "Central Government Healthcare Administration"),

  /**
   * Child Care Services code.
   */
  CHILD_CARE_SERVICES("ANZSIC", "8710", "Child Care Services"),

  /**
   * Chiropractic and Osteopathic Services code.
   */
  CHIROPRACTIC_AND_OSTEOPATHIC_SERVICES("ANZSIC", "8534", "Chiropractic and Osteopathic Services"),

  /**
   * Computer System Design and Related Services code.
   */
  COMPUTER_SYSTEM_DESIGN_AND_RELATED_SERVICES("ANZSIC", "7000", "Computer System Design and Related Services"),

  /**
   * Corporate Head Office Management Services code.
   */
  CORPORATE_HEAD_OFFICE_MANAGEMENT_SERVICES("ANZSIC", "6961", "Corporate Head Office Management Services"),

  /**
   * Data Processing and Web Hosting Services code.
   */
  DATA_PROCESSING_AND_WEB_HOSTING_SERVICES("ANZSIC", "5921", "Data Processing and Web Hosting Services"),

  /**
   * Dental Services code.
   */
  DENTAL_SERVICES("ANZSIC", "8531", "Dental Services"),

  /**
   * Electronic Information Storage Services code.
   */
  ELECTRONIC_INFORMATION_STORAGE_SERVICES("ANZSIC", "5922", "Electronic Information Storage Services"),

  /**
   * General Health Administration code.
   */
  GENERAL_HEALTH_ADMINISTRATION("ANZSIC", "7561", "General Health Administration"),

  /**
   * General Practice code.
   */
  GENERAL_PRACTICE("ANZSIC", "8511", "General Practice"),

  /**
   * Health and Fitness Centres and Gymnasia Operation code.
   */
  HEALTH_AND_FITNESS_CENTRES_AND_GYMNASIA_OPERATION("ANZSIC", "9111", "Health and Fitness Centres and Gymnasia Operation"),

  /**
   * Health Insurance code.
   */
  HEALTH_INSURANCE("ANZSIC", "6321", "Health Insurance"),

  /**
   * Higher Education code.
   */
  HIGHER_EDUCATION("ANZSIC", "8102", "Higher Education"),

  /**
   * Hospitals (except Psychiatric Hospitals) code.
   */
  HOSPITALS("ANZSIC", "8401", "Hospitals (except Psychiatric Hospitals)"),

  /**
   * Internet Service Providers and Web Search Portals code.
   */
  INTERNET_SERVICE_PROVIDERS_AND_WEB_SEARCH_PORTALS("ANZSIC", "5910", "Internet Service Providers and Web Search Portals"),

  /**
   * Local Government Healthcare Administration code.
   */
  LOCAL_GOVERNMENT_HEALTHCARE_ADMINISTRATION("ANZSIC", "7531", "Local Government Healthcare Administration"),

  /**
   * Mental Health Hospitals code.
   */
  MENTAL_HEALTH_HOSPITALS("ANZSIC", "8402", "Mental Health Hospitals"),

  /**
   * Office Administrative Services code.
   */
  OFFICE_ADMINISTRATIVE_SERVICES("ANZSIC", "7291", "Office Administrative Services"),

  /**
   * Optometry and Optical Dispensing code.
   */
  OPTOMETRY_AND_OPTICAL_DISPENSING("ANZSIC", "8532", "Optometry and Optical Dispensing"),

  /**
   * Other Allied Health Services code.
   */
  OTHER_ALLIED_HEALTH_SERVICES("ANZSIC", "8539", "Other Allied Health Services"),

  /**
   * Other Healthcare Services nec code.
   */
  OTHER_HEALTHCARE_SERVICES_NEC("ANZSIC", "8599", "Other Healthcare Services nec"),

  /**
   * Other Professional, Scientific and Technical Services n.e.c. code.
   */
  OTHER_PROFESSIONAL_SCIENTIFIC_AND_TECHNICAL_SERVICES_NEC("ANZSIC", "6999", "Other Professional, Scientific and Technical Services n.e.c."),

  /**
   * Other Residential Care Services code.
   */
  OTHER_RESIDENTIAL_CARE_SERVICES("ANZSIC", "8609", "Other Residential Care Services"),

  /**
   * Other Social Assistance Services code.
   */
  OTHER_SOCIAL_ASSISTANCE_SERVICES("ANZSIC", "8790", "Other Social Assistance Services"),

  /**
   * Pathology and Diagnostic Imaging Services code.
   */
  PATHOLOGY_AND_DIAGNOSTIC_IMAGING_SERVICES("ANZSIC", "8520", "Pathology and Diagnostic Imaging Services"),

  /**
   * Physiotherapy Services code.
   */
  PHYSIOTHERAPY_SERVICES("ANZSIC", "8533", "Physiotherapy Services"),

  /**
   * Provision and administration of public health program code.
   */
  PROVISION_AND_ADMINISTRATION_OF_PUBLIC_HEALTH_PROGRAM("ANZSIC", "7562", "Provision and administration of public health program"),

  /**
   * Retail Pharmacy code.
   */
  RETAIL_PHARMACY("ANZSIC", "4271", "Retail Pharmacy"),

  /**
   * Scientific Research Services code.
   */
  SCIENTIFIC_RESEARCH_SERVICES("ANZSIC", "6910", "Scientific Research Services"),

  /**
   * Specialist Medical Services code.
   */
  SPECIALIST_MEDICAL_SERVICES("ANZSIC", "8512", "Specialist Medical Services"),

  /**
   * State Government Healthcare Administration code.
   */
  STATE_GOVERNMENT_HEALTHCARE_ADMINISTRATION("ANZSIC", "7521", "State Government Healthcare Administration"),

  /**
   * Transport code.
   */
  TRANSPORT("ANZSIC", "4623", "Transport");

  private String codingSystem;
  private String conceptCode;
  private String conceptName;

  HealthcareFacilityTypeCodes(String codingSystem, String conceptCode, String conceptName) {
    this.codingSystem = codingSystem;
    this.conceptCode = conceptCode;
    this.conceptName = conceptName;
  }

  public CodedValue getCodedValue() {
    return new CodedValue(codingSystem, conceptCode, conceptName);
  }

  private String getConceptCode() {
    return conceptCode;
  }

  /**
   * Retrieves a {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HealthcareFacilityTypeCodes} based on a supplied concept code.
   *
   * @param conceptCode Concept to search for.
   * @return Corresponding {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HealthcareFacilityTypeCodes} or null.
   */
  public static HealthcareFacilityTypeCodes findByConceptCode(String conceptCode) {
    for (HealthcareFacilityTypeCodes v : values()) {
      if (v.getConceptCode().equals(conceptCode)) {
        return v;
      }
    }
    return null;
  }
}
