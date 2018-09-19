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
package au.gov.nehta.vendorlibrary.pcehr.test.utils;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.SubmissionMetadata;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader.AccessingOrganisation;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader.ClientSystemType;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader.ProductType;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader.User;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader.User.IDType;
import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 *
 */
public final class TestDataFactory {

  public static PCEHRHeader createValidPCEHRHeader() {
    User user = new User();
    user.setIDType(IDType.HPII);
    user.setID("{ID}");
    user.setUserName("{UserName}");

    ProductType productType = new ProductType();
    productType.setVendor("{Vendor}");
    productType.setProductName("{ProductName}");
    productType.setProductVersion("{ProductVersion}");
    productType.setPlatform("{Platform}");

    AccessingOrganisation org = new AccessingOrganisation();
    org.setOrganisationID("{OrganisationID}");
    org.setOrganisationName("{OrganisationName}");
    org.setAlternateOrganisationName("{AlternateOrganisationName}");

    PCEHRHeader header = new PCEHRHeader();
    header.setIhiNumber("{IHINumber}");
    header.setClientSystemType(ClientSystemType.OTHER);
    header.setUser(user);
    header.setProductType(productType);
    header.setAccessingOrganisation(org);
    return header;
  }

  public static SubmissionMetadata createValidSubmissionMetadata() {
    SubmissionMetadata subMetadata = new SubmissionMetadata();
    subMetadata.setSubmissionTime(DateUtils.truncate(new Date(), Calendar.SECOND));
    subMetadata.setComments("{Comments}");
    return subMetadata;
  }

//  public static DocumentMetadata createValidDocumentMetadata(byte[] documentData) {
//    DocumentMetadata docMetadata = new DocumentMetadata();
//    docMetadata.setAuthorInstitution("{AuthorOrganisation}");
//    docMetadata.setAuthorPerson("{AuthorIndividual}");
//    docMetadata.setDocumentClass(new CodedValue("{CodeSystem}", "{DocumentTypeCode}", "{DocumentTypeDisplayName}"));
//    docMetadata.setTemplateId("{TemplateID}");
//    docMetadata.setEntryId(UUID.randomUUID().toString());
//    docMetadata.setDocumentHash(DigestUtils.sha(documentData));
//    docMetadata.setTitle("{Title}");
//    docMetadata.setCreationTime(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
//    docMetadata.setServiceStartTime(DateUtils.truncate(new Date(), Calendar.SECOND));
//    docMetadata.setServiceStopTime(DateUtils.truncate(new Date(), Calendar.SECOND));
//    docMetadata.setHealthcareFacilityType(new CodedValue("{CodeSystem}", "{HealthcareFacilityTypeCode}", "{HealthcareFacilityTypeName}"));
//    docMetadata.setPracticeSetting(new CodedValue("{CodeSystem}", "{ClinicalSpecialtyCode}", "{ClinicalSpecialtyDisplayName}"));
//    docMetadata.getKeywords().add("{Keyword1}");
//    docMetadata.getKeywords().add("{Keyword2}");
//    return docMetadata;
//  }

//  public static DocumentQueryParams createDocumentQueryParams(DocumentMetadata docMetadata) {
//    DocumentQueryParams queryParams = new DocumentQueryParams();
//
//    queryParams.getAuthorIndividuals().add(docMetadata.getAuthorPerson().toString());
//    queryParams.getDocumentTypes().add(docMetadata.getDocumentClass());
//    queryParams.getTemplateIds().add(docMetadata.getFormatCode());
//    queryParams.setDocumentCreationTimeFrom(DateUtils.ceiling(DateUtils.addDays(docMetadata.getCreationTime(), -1), Calendar.DAY_OF_MONTH));
//    queryParams.setDocumentCreationTimeTo(DateUtils.ceiling(DateUtils.addDays(docMetadata.getCreationTime(), 1), Calendar.DAY_OF_MONTH));
//    queryParams.setServiceStartTimeFrom(DateUtils.ceiling(DateUtils.addDays(docMetadata.getServiceStartTime(), -1), Calendar.DAY_OF_MONTH));
//    queryParams.setServiceStartTimeTo(DateUtils.ceiling(DateUtils.addDays(docMetadata.getServiceStartTime(), 1), Calendar.DAY_OF_MONTH));
//    queryParams.setServiceStopTimeFrom(DateUtils.ceiling(DateUtils.addDays(docMetadata.getServiceStopTime(), -1), Calendar.DAY_OF_MONTH));
//    queryParams.setServiceStopTimeTo(DateUtils.ceiling(DateUtils.addDays(docMetadata.getServiceStopTime(), 1), Calendar.DAY_OF_MONTH));
//    queryParams.getHealthcareFacilityTypes().add(docMetadata.getHealthcareFacilityType());
//    queryParams.getClinicalSpecialties().add(docMetadata.getPracticeSetting());
//    queryParams.getKeywords().addAll(docMetadata.getKeywords());
//    queryParams.getStatuses().add(DocumentStatus.APPROVED);
//
//    Validate.isTrue(queryParams.matches(docMetadata));
//
//    return queryParams;
//  }

  public static byte[] createValidDocument() {
    return "<document />".getBytes();

  }
}
