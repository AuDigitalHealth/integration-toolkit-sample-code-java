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
package au.gov.nehta.vendorlibrary.pcehr.clients.common.util;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentMetadata;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.SubmissionMetadata;
import org.apache.commons.lang.Validate;

/**
 * A utility class to validate PCEHR metadata.
 */
public final class MetadataValidator {

  /**
   * Hide the constructor - this class only contains static methods.
   */
  private MetadataValidator() {
  }

  /**
   * Validate the contents of the {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentMetadata}.
   *
   * @param documentMetadata A {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentMetadata}.
   */
  public static void validate(DocumentMetadata documentMetadata) {
    Validate.notNull(documentMetadata, "'documentMetadata' must be specified.");

    Validate.notNull(documentMetadata.getDocumentClass(), "'documentMetadata.documentType' must be specified.");
    Validate.notEmpty(documentMetadata.getDocumentClass().getCodeSystem(), "'documentMetadata.documentType.codeSystem' must be specified.");
    Validate.notEmpty(documentMetadata.getDocumentClass().getValue(), "'documentMetadata.documentType.value' must be specified.");
    Validate.notNull(documentMetadata.getFormatCode(), "'documentMetadata.templateId' must be specified.");
    Validate.notEmpty(documentMetadata.getEntryUuid(), "'documentMetadata.documentId' must be specified.");
    Validate.notNull(documentMetadata.getDocumentHash(), "'documentMetadata.documentHash' must be specified.");
    Validate.notNull(documentMetadata.getCreationTime(), "'documentMetadata.documentCreationTime' must be specified.");
    Validate.notNull(documentMetadata.getServiceStartTime(), "'documentMetadata.serviceStartTime' must be specified.");
    Validate.notNull(documentMetadata.getServiceStopTime(), "'documentMetadata.serviceStopTime' must be specified.");
    Validate.isTrue(
      !(documentMetadata.getServiceStartTime().compareTo(documentMetadata.getServiceStopTime()) < 0),
      "'documentMetadata.serviceStopTime' must be greater than or equal to 'documentMetadata.serviceStartTime'."
    );
    Validate.notNull(documentMetadata.getHealthcareFacilityType(), "'documentMetadata.healthcareFacilityType' must be specified.");
    Validate.notEmpty(
      documentMetadata.getHealthcareFacilityType().getCodeSystem(),
      "'documentMetadata.healthcareFacilityType.codeSystem' must be specified."
    );
    Validate.notEmpty(documentMetadata.getHealthcareFacilityType().getValue(), "'documentMetadata.healthcareFacilityType.value' must be specified.");
    Validate.notEmpty(
      documentMetadata.getHealthcareFacilityType().getDisplayName(),
      "'documentMetadata.healthcareFacilityType.displayName' must be specified."
    );
    Validate.notNull(documentMetadata.getPracticeSetting(), "'documentMetadata.clinicalSpecialty' must be specified.");
    Validate.notEmpty(documentMetadata.getPracticeSetting().getCodeSystem(), "'documentMetadata.clinicalSpecialty.codeSystem' must be specified.");
    Validate.notEmpty(documentMetadata.getPracticeSetting().getValue(), "'documentMetadata.clinicalSpecialty.value' must be specified.");
    Validate.notEmpty(
      documentMetadata.getPracticeSetting().getDisplayName(),
      "'documentMetadata.clinicalSpecialty.displayName' must be specified."
    );
  }

  /**
   * Validate the contents of the {@link SubmissionMetadata}.
   *
   * @param submissionMetadata A {@link SubmissionMetadata}.
   */
  public static void validate(SubmissionMetadata submissionMetadata) {
    Validate.notNull(submissionMetadata, "'submissionMetadata' must be specified.");
    Validate.notNull(submissionMetadata.getSubmissionTime(), "'submissionMetadata.submissionTime' must be specified.");
  }
}
