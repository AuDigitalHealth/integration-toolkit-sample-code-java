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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBElement;
import javax.xml.ws.Holder;

import oasis.names.tc.ebxml_regrep.xsd.lcm._3.SubmitObjectsRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.ResponseOption;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AdhocQueryType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AssociationType1;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ClassificationType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExternalIdentifierType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExtrinsicObjectType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.IdentifiableType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.InternationalStringType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.LocalizedString;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ObjectFactory;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.RegistryObjectList;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.RegistryPackageType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.Slot;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.time.FastDateFormat;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XDSConstants;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.Author;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CodedValue;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentMetadata;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentQueryParams;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentStatus;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.SubmissionMetadata;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;

/**
 * A utility class to map IHE XDS.b classes to local classes.
 */
public final class XDSMapper {

  private static final String LINE_SEPARATOR = System.getProperty("line.separator");
  private static final int EIGHT_DATE_FORMAT = 8;
  private static final int TWELVE_DATE_FORMAT = 12;
  private static final FastDateFormat DATETIME_FORMATTER = FastDateFormat
    .getInstance("yyyyMMddHHmmss");

  /**
   * Hide the constructor - this class should be accessed via static methods.
   */
  private XDSMapper() {
  }

  /**
   * Map to an XDS {@link SubmitObjectsRequest}.
   *
   * @param commonHeader       the PCEHR common header
   * @param submissionMetadata Submission metadata
   * @param documentMetadata   Document metadata
   * @param replacesDocumentId The unique identifier of the document which this document
   *                           replaces (may be null for a new document).
   * @return An XDS {@link SubmitObjectsRequest}.
   */
  public static SubmitObjectsRequest toSubmitObjectsRequest(PCEHRHeader commonHeader,
                                                            SubmissionMetadata submissionMetadata,
                                                            DocumentMetadata documentMetadata,
                                                            String replacesDocumentId) {
    Validate.notNull(commonHeader, "'commonHeader' must be specified.");
    Validate.notNull(submissionMetadata, "'submissionMetadata' must be specified.");
    Validate.notNull(documentMetadata, "'documentMetadata' must be specified.");

    // SubmitObjectsRequest
    SubmitObjectsRequest sor = new SubmitObjectsRequest();

    // RegistryObjectsList
    RegistryObjectList rol = new RegistryObjectList();
    sor.setRegistryObjectList(rol);

    Holder<Integer> idClCounter = new Holder<Integer>(0);
    Holder<Integer> idAsCounter = new Holder<Integer>(0);
    Holder<Integer> idEiCounter = new Holder<Integer>(0);

    // XDSDocumentEntry
    ExtrinsicObjectType xdsDocumentEntry = toXDSDocumentEntry(commonHeader, documentMetadata, idClCounter, idEiCounter);
    rol.getIdentifiables().add(new ObjectFactory().createExtrinsicObject(xdsDocumentEntry));

    // XDSSubmissionSet
    RegistryPackageType xdsSubmissionSet = toXDSSubmissionSet(submissionMetadata, documentMetadata, idClCounter, idEiCounter);
    rol.getIdentifiables().add(new ObjectFactory().createRegistryPackage(xdsSubmissionSet));

    // Classify the RegistryType as an XDSSubmissionSet.
    ClassificationType classification = new ClassificationType();
    classification.setClassifiedObject(submissionMetadata.getEntryUuid());
    classification.setClassificationNode(XDSConstants.XDS_SUBMISSION_SET_UUID);
    classification.setObjectType(XDSConstants.OBJECT_TYPE_CLASSIFICATION);
    classification.setId(XDSFactory.toClassificationIdString(++idClCounter.value));
    rol.getIdentifiables().add(new ObjectFactory().createClassification(classification));

    // Association (HasMember)
    AssociationType1 association = new AssociationType1();
    association.setId(XDSFactory.toAssociationIdentifierString(++idAsCounter.value));
    association.setAssociationType(XDSConstants.ASSOCIATION_TYPE_HAS_MEMBER);
    association.setObjectType(XDSConstants.OBJECT_TYPE_ASSOCIATION);
    association.setSourceObject(xdsSubmissionSet.getId());
    association.setTargetObject(xdsDocumentEntry.getId());
    association.getSlots().add(XDSFactory.createSlot(XDSConstants.SUBMISSION_SET_STATUS_SLOT, "Original"));
    rol.getIdentifiables().add(new ObjectFactory().createAssociation(association));

    // Associate (Replaces)
    if (StringUtils.isNotBlank(replacesDocumentId)) {
      association = new AssociationType1();
      association.setId(XDSFactory.toAssociationIdentifierString(++idAsCounter.value));
      association.setAssociationType(XDSConstants.ASSOCIATION_TYPE_REPLACES);
      association.setObjectType(XDSConstants.OBJECT_TYPE_ASSOCIATION);
      association.setSourceObject(xdsDocumentEntry.getId());
      association.setTargetObject(replacesDocumentId);
      association.setName(XDSFactory.createInternationalString(XDSConstants.ASSOCIATION_TYPE_REPLACES_NAME));
      rol.getIdentifiables().add(new ObjectFactory().createAssociation(association));
    }

    return sor;
  }

  /**
   * Map to an XDS {@link ExtrinsicObjectType} containing an XDSDocumentEntry.
   *
   * @param commonHeader     the PCEHR common header
   * @param documentMetadata Document metadata
   *                         identifiers.
   * @return an XDS {@link ExtrinsicObjectType} containing an XDSDocumentEntry.
   */
  public static ExtrinsicObjectType toXDSDocumentEntry(
    PCEHRHeader commonHeader,
    DocumentMetadata documentMetadata,
    Holder<Integer> idClCounter,
    Holder<Integer> idEiCounter
  ) {

    ExtrinsicObjectType xo = new ExtrinsicObjectType();
    xo.setId(documentMetadata.getEntryUuid());
    xo.setObjectType(XDSConstants.XDS_DOCUMENT_ENTRY_UUID);

    // Assume CDA package (ZIP file)
    xo.setMimeType("application/zip");

    //never add the title to cds metadata
//    // title
//    if (StringUtils.isNotBlank(documentMetadata.getTitle())) {
//      xo.setName(XDSFactory.createInternationalString(documentMetadata.getTitle()));
//    }

    // documentMetadata.documentCreationTime -> XDSDocumentEntry.creationTime
    if (documentMetadata.getCreationTime() != null) {
      xo.getSlots().add(
        XDSFactory.createSlot(XDSConstants.CREATION_TIME_SLOT,
          documentMetadata.getCreationTime()));
    }

    // documentMetadata.langaugeCode -> language code
    xo.getSlots().add(XDSFactory.createSlot(XDSConstants.LANGUAGE_CODE_SLOT, documentMetadata.getLanguageCode()));

    if (documentMetadata.getRepositoryUniqueId() != null) {
      xo.getSlots().add(
        XDSFactory.createSlot(XDSConstants.REPOSITORY_UNIQUE_ID, documentMetadata.getRepositoryUniqueId())
      );
    }

    // documentMetadata.serviceStartTime -> XDSDocumentEntry.serviceStartTime
    if (documentMetadata.getServiceStartTime() != null) {
      xo.getSlots().add(
        XDSFactory.createSlot(XDSConstants.SERVICE_START_TIME_SLOT,
          documentMetadata.getServiceStartTime()));
    }

    // documentMetadata.serviceStopTime -> XDSDocumentEntry.serviceStopTime
    if (documentMetadata.getServiceStopTime() != null) {
      xo.getSlots().add(
        XDSFactory.createSlot(XDSConstants.SERVICE_STOP_TIME_SLOT,
          documentMetadata.getServiceStopTime()));
    }

    // documentMetadata.sourcePatientId -> XDSDocumentEntry.sourcePatientId
    if (StringUtils.isNotBlank(documentMetadata.getSourcePatientId().toString())) {
      xo.getSlots().add(
        XDSFactory.createSlot(XDSConstants.SOURCE_PATIENT_ID_SLOT, documentMetadata.getSourcePatientId().toString()));
    }

    //removed to align with C# code
//    if (StringUtils.isNotBlank(documentMetadata.getDocumentClass().toString())) {
//      xo.setName(XDSFactory.createInternationalString(documentMetadata.getDocumentClass().getDisplayName()));
//    }

    // documentMetadata.documentHash -> XDSDocumentEntry.hash
    byte[] documentHash = documentMetadata.getDocumentHash();
    if (documentHash != null && documentHash.length > 0) {
      xo.getSlots().add(
        XDSFactory.createSlot(XDSConstants.HASH_SLOT,
          Base64.encodeBase64String(documentHash)));
    }

    // documentMetadata.documentSize -> XDSDocumentEntry.size
    if (documentMetadata.getDocumentSize() != null) {
      xo.getSlots().add(
        XDSFactory.createSlot(XDSConstants.SIZE_SLOT, documentMetadata.getDocumentSize().toString())
      );
    }

    // documentMetadata.authorInstitution -> XDSDocumentEntry.authorInstitution
    // documentMetadata.authorPerson -> XDSDocumentEntry.authorPerson
    if (StringUtils.isNotBlank(documentMetadata.getAuthorPerson().toString())
      || StringUtils.isNotBlank(documentMetadata.getAuthorInstitution().toString())) {
      ClassificationType classification = XDSFactory.createClassification(
        XDSConstants.XDS_DOCUMENT_ENTRY_AUTHOR_UUID, documentMetadata.getEntryUuid(), "",
        ++idClCounter.value);
      if (StringUtils.isNotBlank(documentMetadata.getAuthorInstitution().toString())) {
        classification.getSlots().add(
          XDSFactory.createSlot(XDSConstants.AUTHOR_INSTITUTION_SLOT,
            documentMetadata.getAuthorInstitution().toString()));
      }
      if (StringUtils.isNotBlank(documentMetadata.getAuthorPerson().toString())) {
        classification.getSlots().add(
          XDSFactory.createSlot(XDSConstants.AUTHOR_PERSON_SLOT,
            documentMetadata.getAuthorPerson().toString()));
      }
      
      //author specialty
      if (StringUtils.isNotBlank(documentMetadata.getAuthorSpecialty())) {
          classification.getSlots().add(
            XDSFactory.createSlot(XDSConstants.AUTHOR_SPECIALITY_SLOT,
              documentMetadata.getAuthorSpecialty()));
      }
      
      xo.getClassifications().add(classification);
    }

    // documentMetadata.documentType -> XDSDocumentEntry.classCode
    if (documentMetadata.getDocumentClass() != null) {
      ClassificationType classification = XDSFactory.createClassification(
        XDSConstants.XDS_DOCUMENT_ENTRY_CLASS_CODE_UUID, documentMetadata.getEntryUuid(),
        documentMetadata.getDocumentClass().getValue(), ++idClCounter.value);
      classification.getSlots().add(
        XDSFactory.createSlot(XDSConstants.CODING_SCHEME_SLOT,
          documentMetadata.getDocumentClass().getCodeSystem()));
      if (StringUtils.isNotBlank(documentMetadata.getDocumentClass().getDisplayName())) {
        classification.setName(XDSFactory.createInternationalString(documentMetadata
        		
        		//14-10-2012 change to typecode from class code to align with C#
          .getDocumentType().getDisplayName()));
      }
      xo.getClassifications().add(classification);
    }

    // documentMetadata.confidentialityCode -> XDSDocumentEntry.confidentialityCode
    // TODO: node representation is not officially documented
    ClassificationType classification = XDSFactory.createClassification(
      XDSConstants.XDS_DOCUMENT_ENTRY_CONFIDENTIALITY_CODE_UUID,
      documentMetadata.getEntryUuid(), "1.3.6.1.4.1.21367.2006.7.101", ++idClCounter.value);
    classification.getSlots().add(XDSFactory.createSlot(
      XDSConstants.CODING_SCHEME_SLOT,
      documentMetadata.getConfidentialityCode().getCodedValue().getCodeSystem()));
    classification.setName(XDSFactory.createInternationalString(documentMetadata.getConfidentialityCode().getCodedValue().getDisplayName()));
    xo.getClassifications().add(classification);


    // TODO: template id is not officially documented
    // documentMetadata.templateId -> XDSDocumentEntry.formatCode
    if (documentMetadata.getFormatCode() != null) {
      classification = XDSFactory.createClassification(
        XDSConstants.XDS_DOCUMENT_ENTRY_FORMAT_CODE_UUID,
        documentMetadata.getEntryUuid(),
        documentMetadata.getFormatCode().getValue(),
        ++idClCounter.value);
      classification.getSlots().add(
        XDSFactory.createSlot(XDSConstants.CODING_SCHEME_SLOT, documentMetadata.getFormatCode().getCodeSystem()));
      classification.setName(XDSFactory.createInternationalString(documentMetadata.getFormatCode().getDisplayName()));
      xo.getClassifications().add(classification);
    }

    // documentMetadata.healthcareFacilityType -> XDSDocumentEntry.healthcareFacilityTypeCode
    if (documentMetadata.getHealthcareFacilityType() != null) {
      classification = XDSFactory.createClassification(
        XDSConstants.XDS_DOCUMENT_ENTRY_HEALTHCARE_FACILITY_TYPE_CODE_UUID,
        documentMetadata.getEntryUuid(), documentMetadata.getHealthcareFacilityType().getValue(), ++idClCounter.value);
      classification.getSlots().add(
        XDSFactory.createSlot(XDSConstants.CODING_SCHEME_SLOT,
          documentMetadata.getHealthcareFacilityType().getCodeSystem()));
      if (StringUtils.isNotBlank(documentMetadata.getHealthcareFacilityType().getDisplayName())) {
        classification.setName(XDSFactory.createInternationalString(documentMetadata.getHealthcareFacilityType().getDisplayName()));
      }
      xo.getClassifications().add(classification);
    }

    // documentMetadata.practiceSettingType -> XDSDocumentEntry.practiceSettingCode
    if (documentMetadata.getPracticeSetting() != null) {
      classification = XDSFactory.createClassification(
        XDSConstants.XDS_DOCUMENT_ENTRY_PRACTICE_SETTING_CODE_UUID,
        documentMetadata.getEntryUuid(), documentMetadata.getPracticeSetting().getValue(), ++idClCounter.value);
      classification.getSlots().add(
        XDSFactory.createSlot(XDSConstants.CODING_SCHEME_SLOT,
          documentMetadata.getPracticeSetting().getCodeSystem()));
      if (StringUtils.isNotBlank(documentMetadata.getPracticeSetting().getDisplayName())) {
        classification.setName(XDSFactory.createInternationalString(documentMetadata.getPracticeSetting().getDisplayName()));
      }
      xo.getClassifications().add(classification);
    }

    // documentMetadata.patientId -> XDSDocumentEntry.patientId
    if (StringUtils.isNotBlank(commonHeader.getIhiNumber())) {
      ExternalIdentifierType externalId = XDSFactory.createExternalIdentifier(
        XDSConstants.XDS_DOCUMENT_ENTRY_PATIENT_ID_UUID, documentMetadata.getEntryUuid(),
        "XDSDocumentEntry.patientId", documentMetadata.getPatientId().toString(), ++idEiCounter.value);
      xo.getExternalIdentifiers().add(externalId);
    }

    // documentMetadata.documentId -> XDSDocumentEntry.uniqueId
    if (StringUtils.isNotBlank(documentMetadata.getEntryUuid())) {
      ExternalIdentifierType externalId = XDSFactory.createExternalIdentifier(
        XDSConstants.XDS_DOCUMENT_ENTRY_UNIQUE_ID_UUID, documentMetadata.getEntryUuid(),
        "XDSDocumentEntry.uniqueId", documentMetadata.getUniqueId(), ++idEiCounter.value);
      xo.getExternalIdentifiers().add(externalId);
    }

    // documentMetadata.keyword -> XDSDocumentEntry.eventCodeList
    if (documentMetadata.hasKeywords()) {
      // According to IHE Technical Framework Vol3, when multiple keywords are
      // provided, they should be represented as multiple Classification objects.
      for (String keyword : documentMetadata.getKeywords()) {
        classification = XDSFactory.createClassification(
          XDSConstants.XDS_DOCUMENT_ENTRY_EVENT_CODE_LIST_UUID, documentMetadata.getEntryUuid(),
          keyword, ++idClCounter.value);
        classification.getSlots().add(
          XDSFactory.createSlot(XDSConstants.CODING_SCHEME_SLOT, "NA"));
        classification.setName(XDSFactory.createInternationalString(keyword));
        xo.getClassifications().add(classification);
      }
    }

    // documentMetadata.documentType -> XDSDocumentEntry.typeCode
    if (documentMetadata.getDocumentClass() != null) {
      classification = XDSFactory.createClassification(
        XDSConstants.XDS_DOCUMENT_ENTRY_TYPE_CODE_UUID, documentMetadata.getEntryUuid(),
        documentMetadata.getDocumentType().getValue(), ++idClCounter.value);
      classification.getSlots().add(
        XDSFactory.createSlot(XDSConstants.CODING_SCHEME_SLOT,
          documentMetadata.getDocumentClass().getCodeSystem()));
      if (StringUtils.isNotBlank(documentMetadata.getDocumentType().getDisplayName())) {
        classification.setName(XDSFactory.createInternationalString(documentMetadata.getDocumentType().getDisplayName()));
      }
      xo.getClassifications().add(classification);
    }

    return xo;
  }

  /**
   * Map to an XDS {@link RegistryPackageType} containing an XDSSubmissionSet.
   *
   * @param submissionMetadata Submission metadata
   * @param documentMetadata   Document metadata
   *                           identifiers.
   * @return An XDS {@link RegistryPackageType} containing an XDSSubmissionSet.
   */
  public static RegistryPackageType toXDSSubmissionSet(
    SubmissionMetadata submissionMetadata,
    DocumentMetadata documentMetadata,
    Holder<Integer> idClCounter,
    Holder<Integer> idEiCounter
  ) {
    RegistryPackageType rp = new RegistryPackageType();
    rp.setObjectType(XDSConstants.OBJECT_TYPE_REGISTRY_PACKAGE);

    // submissionMetadata.entryId -> XDSSubmissionSet.entryUUID
    rp.setId(submissionMetadata.getEntryUuid());

    // submissionMetadata.submissionTime -> XDSSubmissionSet.submissionTime
    if (submissionMetadata.getSubmissionTime() != null) {
      rp.getSlots().add(
        XDSFactory.createSlot(XDSConstants.SUBMISSION_TIME_SLOT,
          toDateTimeString(submissionMetadata.getSubmissionTime())));
    }

    // submissionSet.comments -> XDSSubmissionSet.comments
    if (StringUtils.isNotBlank(submissionMetadata.getComments())) {
      rp.setDescription(XDSFactory.createInternationalString(submissionMetadata.getComments()));
    }

    // documentMetadata.authorIndividual -> XDSSubmissionSet.authorPerson
    // documentMetadata.authorOrganisation -> XDSSubmissionSet.authorInstitution
    if (StringUtils.isNotBlank(documentMetadata.getAuthorPerson().toString())
      || StringUtils.isNotBlank(documentMetadata.getAuthorInstitution().toString())) {
      ClassificationType classification = XDSFactory.createClassification(
        XDSConstants.XDS_SUBMISSION_SET_AUTHOR_UUID, submissionMetadata.getEntryUuid(), "",
        ++idClCounter.value);
      if (StringUtils.isNotBlank(documentMetadata.getAuthorInstitution().toString())) {
        classification.getSlots().add(
          XDSFactory.createSlot(XDSConstants.AUTHOR_INSTITUTION_SLOT,
            documentMetadata.getAuthorInstitution().toString()));
      }
      if (StringUtils.isNotBlank(documentMetadata.getAuthorPerson().toString())) {
        classification.getSlots().add(
          XDSFactory.createSlot(XDSConstants.AUTHOR_PERSON_SLOT,
            documentMetadata.getAuthorPerson().toString()));
      }
      //author specialty
      if (StringUtils.isNotBlank(documentMetadata.getAuthorSpecialty())) {
          classification.getSlots().add(XDSFactory.createSlot(
            XDSConstants.AUTHOR_SPECIALITY_SLOT, documentMetadata.getAuthorSpecialty()));
      }
      
      rp.getClassifications().add(classification);
    }

    // documentMetadata.documentType -> XDSSubmissionSet.contentTypeCode
    if (documentMetadata.getDocumentType() != null) {
      ClassificationType classification = XDSFactory.createClassification(
        XDSConstants.XDS_SUBMISSION_SET_CONTENT_TYPE_CODE_UUID,
        submissionMetadata.getEntryUuid(),
        documentMetadata.getDocumentClass().getValue(),
        ++idClCounter.value
      );
      classification.getSlots().add(XDSFactory.createSlot(XDSConstants.CODING_SCHEME_SLOT, documentMetadata.getDocumentClass().getCodeSystem()));
      if (StringUtils.isNotBlank(documentMetadata.getDocumentClass().getDisplayName())) {
    	  //change from class code to type code to align with C#
        classification.setName(XDSFactory.createInternationalString(documentMetadata.getDocumentType().getDisplayName()));
      }
      rp.getClassifications().add(classification);
    }

    // documentMetadata.uniqueId -> XDSSubmissionSet.uniqueId
    {
      ExternalIdentifierType externalId = XDSFactory.createExternalIdentifier(
        XDSConstants.XDS_SUBMISSION_SET_UNIQUE_ID_UUID, submissionMetadata.getEntryUuid(),
        "XDSSubmissionSet.uniqueId", documentMetadata.getUniqueId(), ++idEiCounter.value);
      rp.getExternalIdentifiers().add(externalId);
    }

    // documentMetadata.authorOrganisation -> XDSSubmissionSet.sourceId
    if (StringUtils.isNotBlank(documentMetadata.getAuthorInstitution().toString())) {
      // TODO: why are we mixing ID representations, XON vs original.
      ExternalIdentifierType externalId = XDSFactory.createExternalIdentifier(
        XDSConstants.XDS_SUBMISSION_SET_SOURCE_ID_UUID, submissionMetadata.getEntryUuid(),
        "XDSSubmissionSet.sourceId", documentMetadata.getAuthorInstitution().getOrganisationIdentifier(), ++idEiCounter.value);
      rp.getExternalIdentifiers().add(externalId);
    }

    // documentMetadata.patientId -> XDSSubmissionSet.patientId
    if (StringUtils.isNotBlank(documentMetadata.getPatientId().toString())) {
      ExternalIdentifierType externalId = XDSFactory.createExternalIdentifier(
        XDSConstants.XDS_SUBMISSION_SET_PATIENT_ID_UUID, submissionMetadata.getEntryUuid(),
        "XDSSubmissionSet.patientId", documentMetadata.getPatientId().toString(), ++idEiCounter.value);
      rp.getExternalIdentifiers().add(externalId);
    }
    return rp;
  }

  /**
   * Map a {@link DocumentQueryParams} into an XDS {@link AdhocQueryRequest}.
   *
   * @param commonHeader the PCEHR common header
   * @param queryParams  additional query parameters.
   * @return The {@link AdhocQueryRequest} containing the {@link DocumentQueryParams}.
   */
  public static AdhocQueryRequest toAdhocQueryRequest(PCEHRHeader commonHeader,
                                                      String queryId,
                                                      DocumentQueryParams queryParams) {
    Validate.notNull(commonHeader, "'commonHeader' must be specified.");
    Validate.notNull(queryParams, "'queryParams' must be specified.");
    Validate.notNull(queryId, "'queryId' must be specified.");

    AdhocQueryRequest request = new AdhocQueryRequest();

    ResponseOption responseOption = new ResponseOption();
    responseOption.setReturnComposedObjects(Boolean.TRUE);
    responseOption.setReturnType("LeafClass");
    request.setResponseOption(responseOption);

    AdhocQueryType query = new AdhocQueryType();
    query.setId(queryId);
    request.setAdhocQuery(query);

    // commonHeader.IHINumber -> $XDSDocumentEntryPatientId(R)
    if (StringUtils.isNotBlank(commonHeader.getIhiNumber())) {
      query.getSlots().add(
        XDSFactory.createSingleValuedSlot(XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_PATIENT_ID,
          String.format("%s%s", commonHeader.getIhiNumber(), XDSConstants.IHI_SUFFIX)));
    }

    // queryParams.authorIndividuals -> $XDSDocumentEntryAuthorPerson(O,M)
    if (queryParams.hasAuthorIndividuals()) {
    	
    	 List<String> authors = new ArrayList<String>();
    	 
    	 for(Author author:  queryParams.getAuthorIndividuals()){
    		 authors.add( author.toXCNFormatString() );
    	 }
    	
      query.getSlots().add(
        XDSFactory.createQuerySlot(XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_AUTHOR_PERSON,
        		authors));
    }

    // queryParams.documentTypeCodes -> $XDSDocumentEntryTypeCode(O,M)
    if (queryParams.hasDocumentTypes()) {
      query.getSlots().addAll(
        XDSFactory.createQuerySlots(XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_TYPE_CODE,
          queryParams.getDocumentTypes()));
    }
    
    // queryParams.documentTypeCodes -> $XDSDocumentEntryClassCode(O,M)
    if (queryParams.hasDocumentClasses()) {
      query.getSlots().addAll(
        XDSFactory.createQuerySlots(XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_CLASS_CODE,
          queryParams.getDocumentClasses()));
    }

    // queryParams.templateIds -> $XDSDocumentEntryFormatCode(O,M)
    if (queryParams.hasTemplateIds()) {
      query.getSlots().addAll(
        XDSFactory.createQuerySlots(XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_FORMAT_CODE,
          queryParams.getTemplateIds()));
    }

    // queryParams.documentCreationTimeFrom -> $XDSDocumentEntryCreationTimeFrom(O)
    if (queryParams.getDocumentCreationTimeFrom() != null) {
      query.getSlots().add(
        XDSFactory.createSlot(XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_CREATION_TIME_FROM,
          queryParams.getDocumentCreationTimeFrom()));
    }

    // queryParams.documentCreationTimeTo -> $XDSDocumentEntryCreationTimeTo(O)
    if (queryParams.getDocumentCreationTimeTo() != null) {
      query.getSlots().add(
        XDSFactory.createSlot(XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_CREATION_TIME_TO,
          queryParams.getDocumentCreationTimeTo()));
    }

    // queryParams.serviceStartTimeFrom -> $XDSDocumentEntryServiceStartTimeFrom(O)
    if (queryParams.getServiceStartTimeFrom() != null) {
      query.getSlots().add(
        XDSFactory.createSlot(
          XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_SERVICE_START_TIME_FROM,
          queryParams.getServiceStartTimeFrom()));
    }

    // queryParams.serviceStartTimeTo -> $XDSDocumentEntryServiceStartTimeTo(O)
    if (queryParams.getServiceStartTimeTo() != null) {
      query.getSlots().add(
        XDSFactory.createSlot(XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_SERVICE_START_TIME_TO,
          queryParams.getServiceStartTimeTo()));
    }

    // queryParams.serviceStopTimeFrom -> $XDSDocumentEntryServiceStopTimeFrom(O)
    if (queryParams.getServiceStopTimeFrom() != null) {
      query.getSlots().add(
        XDSFactory.createSlot(
          XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_SERVICE_STOP_TIME_FROM,
          queryParams.getServiceStopTimeFrom()));
    }

    // queryParams.serviceStopTimeTo -> $XDSDocumentEntryServiceStopTimeTo(O)
    if (queryParams.getServiceStopTimeTo() != null) {
      query.getSlots().add(
        XDSFactory.createSlot(XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_SERVICE_STOP_TIME_TO,
          queryParams.getServiceStopTimeTo()));
    }

    // queryParams.healthcareFacilityTypes -> $XDSDocumentEntryHealthcareFacilityTypeCode(O,M)
    if (queryParams.hasHealthcareFacilityTypes()) {
      query.getSlots().addAll(
        XDSFactory.createQuerySlots(
          XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_HEALTHCARE_FACILITY_TYPE_CODE,
          queryParams.getHealthcareFacilityTypes()));
    }

    // queryParams.clinicalSpecialties -> $XDSDocumentEntryPracticeSettingCode(O,M)
    if (queryParams.hasClinicalSpecialties()) {
      query.getSlots().addAll(
        XDSFactory.createQuerySlots(XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_PRACTICE_SETTING_CODE,
          queryParams.getClinicalSpecialties()));
    }

    // queryParams.keywords -> $XDSDocumentEntryEventCodeList(O,M)
    if (queryParams.hasKeywords()) {
      query.getSlots().add(
        XDSFactory.createQuerySlot(XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_EVENT_CODE_LIST,
          queryParams.getKeywords()));
    }

    // queryParams.status -> $XDSDocumentEntryStatus(R)
    if (queryParams.hasStatuses()) {
      Set<String> statusCodes = new HashSet<String>();
      for (DocumentStatus status : queryParams.getStatuses()) {
        statusCodes.add(status.getCode());
      }
      query.getSlots().add(
        XDSFactory.createQuerySlot(XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_STATUS,
          statusCodes));
    }

    return request;
  }

  /**
   * Extract the {@link DocumentQueryParams} from the {@link AdhocQueryRequest}.
   *
   * @param commonHeader the PCEHR common header
   * @param queryRequest An XDS {@link DocumentQueryParams}.
   * @return The {@link DocumentQueryParams} extracted from the {@link AdhocQueryRequest}.
   */
  public static DocumentQueryParams getDocumentQueryParams(PCEHRHeader commonHeader, AdhocQueryRequest queryRequest) {
    Validate.notNull(commonHeader, "'commonHeader' must be specified.");
    Validate.notNull(queryRequest, "'queryRequest' must be specified.");
    Validate.notNull(queryRequest.getAdhocQuery(), "'queryRequest.adhocQuery' must be specified.");

    DocumentQueryParams queryParams = new DocumentQueryParams();

    AdhocQueryType query = queryRequest.getAdhocQuery();

    // commonHeader.ihiNumber -> $XDSDocumentEntryPatientId(R)
    String patientId = getQuerySlotValue(query.getSlots(),
      XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_PATIENT_ID);
    Validate.notEmpty(
      patientId,
      String.format("'%s' must be specified in the AdhocQuery.", XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_PATIENT_ID)
    );
    if (!StringUtils.equals(commonHeader.getIhiNumber(), patientId)) {
      throw new IllegalArgumentException(
        String.format(
          "The PCEHRHeader.IHINumber [%s] does not equal the %s parameter in the AdhocQuery [%s].",
          commonHeader.getIhiNumber(),
          XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_PATIENT_ID,
          patientId
        )
      );
    }

    // queryParams.authorIndividuals -> $XDSDocumentEntryAuthorPerson(O,M)
    List<String> authorIndividuals = getQuerySlotValues(query.getSlots(),
      XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_AUTHOR_PERSON);
    if (!authorIndividuals.isEmpty()) {
    	for(String a: authorIndividuals){
    		Author author = new Author(a);
    		queryParams.getAuthorIndividuals().add( author );
    	}
    }

    // queryParams.documentTypes -> $XDSDocumentEntryClassCode(O,M)
    List<CodedValue> documentTypeCodes = getQuerySlotCodedValues(query.getSlots(),
      XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_CLASS_CODE);
    if (!documentTypeCodes.isEmpty()) {
      queryParams.getDocumentTypes().addAll(documentTypeCodes);
    }

    // queryParams.templateIds -> $XDSDocumentEntryFormatCode(O,M)
    List<CodedValue> templateIds = getQuerySlotCodedValues(query.getSlots(),
      XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_FORMAT_CODE);
    if (!templateIds.isEmpty()) {
      queryParams.getTemplateIds().addAll(templateIds);
    }

    // queryParams.documentCreationTimeFrom -> $XDSDocumentEntryCreationTimeFrom(O)
    String documentCreationTimeFrom = getQuerySlotDateTimeValue(query.getSlots(),
      XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_CREATION_TIME_FROM);
    if (documentCreationTimeFrom != null) {
      queryParams.setDocumentCreationTimeFrom(documentCreationTimeFrom);
    }

    // queryParams.documentCreationTimeTo -> $XDSDocumentEntryCreationTimeTo(O)
    String documentCreationTimeTo = getQuerySlotDateTimeValue(query.getSlots(),
      XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_CREATION_TIME_TO);
    if (documentCreationTimeTo != null) {
      queryParams.setDocumentCreationTimeTo(documentCreationTimeTo);
    }

    // queryParams.serviceStartTimeFrom -> $XDSDocumentEntryServiceStartTimeFrom(O)
    String serviceStartTimeFrom = getQuerySlotDateTimeValue(query.getSlots(),
      XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_SERVICE_START_TIME_FROM);
    if (serviceStartTimeFrom != null) {
      queryParams.setServiceStartTimeFrom(serviceStartTimeFrom);
    }

    // queryParams.serviceStartTimeTo -> $XDSDocumentEntryServiceStartTimeTo(O)
    String serviceStartTimeTo = getQuerySlotDateTimeValue(query.getSlots(),
      XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_SERVICE_START_TIME_TO);
    if (serviceStartTimeTo != null) {
      queryParams.setServiceStartTimeTo(serviceStartTimeTo);
    }

    // queryParams.serviceStopTimeFrom -> $XDSDocumentEntryServiceStopTimeFrom(O)
    String serviceStopTimeFrom = getQuerySlotDateTimeValue(query.getSlots(),
      XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_SERVICE_STOP_TIME_FROM);
    if (serviceStopTimeFrom != null) {
      queryParams.setServiceStopTimeFrom(serviceStopTimeFrom);
    }

    // queryParams.serviceStopTimeTo -> $XDSDocumentEntryServiceStopTimeTo(O)
    String serviceStopTimeTo = getQuerySlotDateTimeValue(query.getSlots(),
      XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_SERVICE_STOP_TIME_TO);
    if (serviceStopTimeTo != null) {
      queryParams.setServiceStopTimeTo(serviceStopTimeTo);
    }

    // queryParams.healthcareFacilityTypes -> $XDSDocumentEntryHealthcareFacilityTypeCode(O,M)
    List<CodedValue> healthcareFacilityTypes = getQuerySlotCodedValues(query.getSlots(),
      XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_HEALTHCARE_FACILITY_TYPE_CODE);
    if (!healthcareFacilityTypes.isEmpty()) {
      queryParams.getHealthcareFacilityTypes().addAll(healthcareFacilityTypes);
    }

    // queryParams.clinicalSpecialties -> $XDSDocumentEntryPracticeSettingCode(O,M)
    List<CodedValue> clinicalSpecialties = getQuerySlotCodedValues(query.getSlots(),
      XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_PRACTICE_SETTING_CODE);
    if (!clinicalSpecialties.isEmpty()) {
      queryParams.getClinicalSpecialties().addAll(clinicalSpecialties);
    }

    // queryParams.keywords -> $XDSDocumentEntryEventCodeList(O,M)
    List<String> keywords = getQuerySlotValues(query.getSlots(),
      XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_EVENT_CODE_LIST);
    if (!keywords.isEmpty()) {
      queryParams.getKeywords().addAll(keywords);
    }

    // queryParams.status -> $XDSDocumentEntryStatus(R,M)
    List<String> statusCodes = getQuerySlotValues(query.getSlots(),
      XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_STATUS);
    Validate.notEmpty(statusCodes, "'" + XDSConstants.QUERY_PARAM_DOCUMENT_ENTRY_STATUS + "' must be specified in the AdhocQuery.");
    for (String statusCode : statusCodes) {
      queryParams.getStatuses().add(DocumentStatus.fromCode(statusCode));
    }

    return queryParams;
  }

  /**
   * Extract the {@link SubmissionMetadata} from the XDSSubmissionEntry objects
   * in the {@link RegistryObjectList}.
   *
   * @param registryObjectList A {@link RegistryObjectList} containing zero or more
   *                           XDSSubmissionEntry extrinsic objects.
   * @return The {@link SubmissionMetadata} extracted from the
   *         XDSSubmissionEntry objects in the {@link RegistryObjectList}.
   */
  public static List<SubmissionMetadata> getSubmissionMetadata(RegistryObjectList registryObjectList) {
    Validate.notNull(registryObjectList, "'registryObjectList' must be specified.");

    List<SubmissionMetadata> metadata = new ArrayList<SubmissionMetadata>();

    for (JAXBElement<? extends IdentifiableType> identifiable : registryObjectList.getIdentifiables()) {
      if (identifiable.getValue() instanceof RegistryPackageType) {
        RegistryPackageType rp = (RegistryPackageType) identifiable.getValue();
        metadata.add(XDSMapper.getSubmissionMetadata(rp));
      }
    }

    return metadata;
  }

  /**
   * Extract the {@link SubmissionMetadata} from the {@link RegistryPackageType}
   * .
   *
   * @param registryPackage A {@link RegistryPackageType}
   * @return The {@link SubmissionMetadata} extracted from the
   *         {@link RegistryPackageType}.
   */
  public static SubmissionMetadata getSubmissionMetadata(RegistryPackageType registryPackage) {
    SubmissionMetadata sm = new SubmissionMetadata();

    // submissionTime
    sm.setSubmissionTime(getSlotDate(registryPackage.getSlots(), XDSConstants.SUBMISSION_TIME_SLOT));

    // comments
    sm.setComments(getString(registryPackage.getDescription()));

    return sm;
  }

  private static ClassificationType getClassfication(List<ClassificationType> classifications,
                                                     String classificationScheme) {
    for (ClassificationType c : classifications) {
      if (StringUtils.equals(c.getClassificationScheme(), classificationScheme)) {
        return c;
      }
    }
    return null;
  }


  private static Slot getSlot(List<Slot> slots, String slotName) {
    for (Slot slot : slots) {
      if (StringUtils.equals(slot.getName(), slotName)) {
        return slot;
      }
    }
    return null;
  }

  private static String getSlotValue(List<Slot> slots, String slotName) {
    Slot slot = getSlot(slots, slotName);
    if (slot != null) {
      return getSlotValue(slot);
    }
    return null;
  }

  private static String getSlotValue(Slot slot) {
    if ((slot.getValueList() != null) && !slot.getValueList().getValues().isEmpty()) {
      return slot.getValueList().getValues().get(0);
    }
    return null;
  }

  private static List<String> getQuerySlotValues(Slot slot) {
    List<String> values = new ArrayList<String>();
    for (String queryValue : slot.getValueList().getValues()) {
      if (StringUtils.isNotBlank(queryValue)) {
        if (queryValue.startsWith("('")) {
          queryValue = queryValue.substring(2);
        }
        if (queryValue.endsWith("')")) {
          queryValue = queryValue.substring(0, queryValue.length() - 2);
        }
        values.add(queryValue);
      }
    }
    return values;
  }

  private static List<String> getQuerySlotValues(List<Slot> slots, String slotName) {
    Slot slot = getSlot(slots, slotName);
    if (slot != null) {
      return getQuerySlotValues(slot);
    }
    return Collections.emptyList();
  }

  private static List<CodedValue> getQuerySlotCodedValues(List<Slot> slots, String slotName) {
    Slot valueSlot = getSlot(slots, slotName);
    Slot codeSystemSlot = getSlot(slots, slotName + "Scheme");

    List<CodedValue> codedValues = Collections.emptyList();

    if (valueSlot != null) {
      List<String> values = getQuerySlotValues(valueSlot);
      List<String> codeSystems = Collections.emptyList();
      if (codeSystemSlot != null) {
        codeSystems = getQuerySlotValues(codeSystemSlot);
      }

      codedValues = new ArrayList<CodedValue>(values.size());
      for (int i = 0; i < values.size(); ++i) {
        if (codeSystems.size() >= i + 1) {
          codedValues.add(new CodedValue(codeSystems.get(i), values.get(i), null));
        } else {
          codedValues.add(new CodedValue(null, values.get(i), null));
        }
      }
    }
    return codedValues;
  }

  private static String getQuerySlotValue(List<Slot> slots, String slotName) {
    List<String> values = getQuerySlotValues(slots, slotName);
    if (!values.isEmpty()) {
      return values.get(0);
    }
    return null;
  }

  private static String getQuerySlotDateTimeValue(List<Slot> slots, String slotName) {
    String value = getQuerySlotValue(slots, slotName);
    if (StringUtils.isNotBlank(value)) {
      return DateUtils.toUtcDate(value);
    }
    return null;
  }

  private static Date getSlotDate(List<Slot> slots, String slotName) {
    String strValue = getSlotValue(slots, slotName);
    if (strValue != null) {
      return MetadataUtils.parseDate(strValue);
    }
    return null;
  }

  private static String getString(InternationalStringType internationalString) {
    StringBuilder sb = new StringBuilder();
    if (internationalString != null) {
      for (LocalizedString ls : internationalString.getLocalizedStrings()) {
        if (sb.length() > 0) {
          sb.append(LINE_SEPARATOR);
        }
        sb.append(ls.getValue());
      }
    }
    if (sb.length() > 0) {
      return sb.toString();
    }
    return null;
  }

  private static String toDateTimeString(Date date) {
    return DATETIME_FORMATTER.format(date);
  }
}
