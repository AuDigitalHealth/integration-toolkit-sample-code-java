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
 * Template package identifiers for the various CDA documents and associated
 * conformance levels.
 */
public enum FormatCodes {

    /**
     * TODO: Final template package identifiers to be defined.
     */

    /**
     * Shared health summary 3A.
     */
    SHARED_HEALTH_SUMMARY_3A( "1.2.36.1.2001.1006.1.16565.3", DocumentTypeCodes.SHARED_HEALTH_SUMMARY, ConformanceLevels.THREE_A ),

    /**
     * Event summary 3A.
     */
    EVENT_SUMMARY_3A( "1.2.36.1.2001.1006.1.16473.6", DocumentTypeCodes.EVENT_SUMMARY, ConformanceLevels.THREE_A ),

    /**
     * Consumer entered notes 3A.
     */
    CONSUMER_ENTERED_NOTES_3A( "1.2.36.1.2001.1006.1.16681.2", DocumentTypeCodes.CONSUMER_ENTERED_NOTES, ConformanceLevels.THREE_A ),

    /**
     * Consumer entered health summary 3A.
     */
    CONSUMER_ENTERED_HEALTH_SUMMARY_3A( "1.2.36.1.2001.1006.1.16685.2", DocumentTypeCodes.CONSUMER_ENTERED_HEALTH_SUMMARY, ConformanceLevels.THREE_A ),

    /**
     * Advanced care directive custodian record 3A.
     */
    ADVANCED_CARE_DIRECTIVE_CUSTODIAN_RECORD_3A( "1.2.36.1.2001.1006.1.16696.2", DocumentTypeCodes.ADVANCED_CARE_DIRECTIVE_CUSTODIAN_RECORD, ConformanceLevels.THREE_A ),

    /**
     * Specialist letter 1A.
     */
    SPECIALIST_LETTER_1A( "1.2.36.1.2001.1006.1.16615.12", DocumentTypeCodes.SPECIALIST_LETTER, ConformanceLevels.ONE_A ),

    /**
     * Specialist letter 1B.
     */
    SPECIALIST_LETTER_1B( "1.2.36.1.2001.1006.1.16615.9", DocumentTypeCodes.SPECIALIST_LETTER, ConformanceLevels.ONE_B ),

    /**
     * Specialist letter 2.
     */
    SPECIALIST_LETTER_2( "1.2.36.1.2001.1006.1.16615.10", DocumentTypeCodes.SPECIALIST_LETTER, ConformanceLevels.TWO ),

    /**
     * Specialist letter 3A.
     */
    SPECIALIST_LETTER_3A( "1.2.36.1.2001.1006.1.16615.11", DocumentTypeCodes.SPECIALIST_LETTER, ConformanceLevels.THREE_A ),

    /**
     * E-referral 1A.
     */
    EREFERRAL_1A( "1.2.36.1.2001.1006.1.21000.12", DocumentTypeCodes.EREFERRAL, ConformanceLevels.ONE_A ),

    /**
     * E-referral 1B.
     */
    EREFERRAL_1B( "1.2.36.1.2001.1006.1.21000.9", DocumentTypeCodes.EREFERRAL, ConformanceLevels.ONE_B ),

    /**
     * E-referral 2.
     */
    EREFERRAL_2( "1.2.36.1.2001.1006.1.21000.10", DocumentTypeCodes.EREFERRAL, ConformanceLevels.TWO ),

    /**
     * E-referral 3A.
     */
    EREFERRAL_3A( "1.2.36.1.2001.1006.1.21000.11", DocumentTypeCodes.EREFERRAL, ConformanceLevels.THREE_A ),

    /**
     * Discharge summary 1A.
     */
    DISCHARGE_SUMMARY_1A( "1.2.36.1.2001.1006.1.20000.12", DocumentTypeCodes.DISCHARGE_SUMMARY, ConformanceLevels.ONE_A ),

    /**
     * Discharge summary 1B.
     */
    DISCHARGE_SUMMARY_1B( "1.2.36.1.2001.1006.1.20000.9", DocumentTypeCodes.DISCHARGE_SUMMARY, ConformanceLevels.ONE_B ),

    /**
     * Discharge summary 2.
     */
    DISCHARGE_SUMMARY_2( "1.2.36.1.2001.1006.1.20000.10", DocumentTypeCodes.DISCHARGE_SUMMARY, ConformanceLevels.TWO ),

    /**
     * Discharge summary 3A.
     */
    DISCHARGE_SUMMARY_3A( "1.2.36.1.2001.1006.1.20000.11", DocumentTypeCodes.DISCHARGE_SUMMARY, ConformanceLevels.THREE_A ),

    /**
     *PCEHR Prescription
     */
     PCEHR_PRESCRIPTION( "1.2.36.1.2001.1006.1.170.1", DocumentTypeCodes.PCEHR_PRESCRIPTION, ConformanceLevels.THREE_A ),

    /**
     *  PCEHR Dispense Record
     */
     PCEHR_DISPENSE_RECORD( "1.2.36.1.2001.1006.1.171.1", DocumentTypeCodes.PCEHR_DISPENSE_RECORD, ConformanceLevels.THREE_A ),

    /**
     * Medicare DVA benefits record 3A.
     */
    MEDICARE_DVA_BENEFITS_RECORD_3A( "1.2.36.1.2001.1006.1.16644.5", DocumentTypeCodes.MEDICARE_DVA_BENEFITS_REPORT, ConformanceLevels.THREE_A ),

    /**
     * Pharmaceutical benefits report 3A.
     */
    PHARMACEUTICAL_BENEFITS_REPORT_3A( "1.2.36.1.2001.1006.1.16650.5", DocumentTypeCodes.PHARMACEUTICAL_BENEFITS_REPORT, ConformanceLevels.THREE_A ),

    /**
     * Australian childhood immunisation register 3A.
     */
    AUSTRALIAN_CHILDHOOD_IMMUNISATION_REGISTER_3A( "1.2.36.1.2001.1006.1.16659.5", DocumentTypeCodes.AUSTRALIAN_CHILDHOOD_IMMUNISATION_REGISTER, ConformanceLevels.THREE_A ),

    /**
     * Australian organ donor register 3A.
     */
    AUSTRALIAN_ORGAN_DONOR_REGISTER_3A( "1.2.36.1.2001.1006.1.16671.5", DocumentTypeCodes.AUSTRALIAN_ORGAN_DONOR_REGISTER, ConformanceLevels.THREE_A ),

    /**
     * Pathology Report 3A.
     */
    PATHOLOGY_REPORT_3A( "1.2.36.1.2001.1006.1.220.4", DocumentTypeCodes.PATHOLOGY_REPORT, ConformanceLevels.THREE_A ),

    /**
     * Diagnostic Image Report 3A.
     */
    DIAGNOSTIC_IMAGE_REPORT_3A( "1.2.36.1.2001.1006.1.222.4", DocumentTypeCodes.DIAGNOSTIC_IMAGE_REPORT, ConformanceLevels.THREE_A );
    
    
    /**
     * Default format code identifier.
     */
    private static final String DEFAULT_FORMAT_CODING_SYSTEM = "PCEHR_FormatCodes";

    /**
     * Conformance level output label.
     */
    private static final String CONFORMANCE_LABEL            = "Conformance Level";

    /**
     * Coding system.
     */
    private String              codingSystem;

    /**
     * Template package ID.
     */
    private String              templateId;

    /**
     * Document type code.
     */
    private DocumentTypeCodes   documentTypeCode;

    /**
     * Conformance level.
     */
    private ConformanceLevels   conformanceLevel;

    /**
     * Constructor.
     * 
     * @param codingSystem
     *            Coding system.
     * @param templateId
     *            template package ID.
     * @param documentTypeCode
     *            document type code.
     * @param conformanceLevel
     *            conformance level.
     */
    private FormatCodes( String codingSystem, String templateId, DocumentTypeCodes documentTypeCode, ConformanceLevels conformanceLevel ) {
        this.codingSystem = codingSystem;
        this.templateId = templateId;
        this.documentTypeCode = documentTypeCode;
        this.conformanceLevel = conformanceLevel;
    }

    /**
     * Constructor.
     * 
     * @param templateId
     *            template package ID.
     * @param documentTypeCode
     *            document type code.
     * @param conformanceLevel
     *            conformance level.
     */
    private FormatCodes( String templateId, DocumentTypeCodes documentTypeCode, ConformanceLevels conformanceLevel ) {
        this( DEFAULT_FORMAT_CODING_SYSTEM, templateId, documentTypeCode, conformanceLevel );
    }

    /**
     * Retrieve template ID.
     * 
     * @return Concept code string.
     */
    private String getTemplateId() {
        return templateId;
    }

    /**
     * Retrieves a coded value, representing the template ID.
     * 
     * @return {@link CodedValue}
     */
    public CodedValue getCodedValue() {
        String formattedConceptName;
        if (conformanceLevel.equals( ConformanceLevels.UNDEFINED )) {
            formattedConceptName = documentTypeCode.getTypeCodeDisplayName();
        } else {
            formattedConceptName = String.format( "%s - %s %s", documentTypeCode.getTypeCodeDisplayName(), CONFORMANCE_LABEL, conformanceLevel.getLevel() );
        }
        return new CodedValue( codingSystem, templateId, formattedConceptName );
    }

    /**
     * Retrieves a {@link FormatCodes} based on a supplied concept code.
     * 
     * @param templateId
     *            Template identifier to search for.
     * @return Corresponding {@link FormatCodes} or null.
     */
    public static FormatCodes findByTemplateId( String templateId ) {
        for (FormatCodes v : values()) {
            if (v.templateId.equals( templateId )) {
                return v;
            }
        }
        return null;
    }
}
