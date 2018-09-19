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

import javax.xml.datatype.DatatypeConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Parameters used to query for documents.
 * <p/>
 * The IHINumber of the PCEHR ($XDSDocumentEntryPatientId) is present in the PCEHRHeader.
 */
public final class DocumentQueryParams {
  // private List<String> authorOrganisations;
  // TODO: authorOrganisations does not seem to have an XDS equivalent.

  /**
   * $XDSDocumentEntryAuthorPerson (O,M).
   */
  private List<Author> authorIndividuals;

  /**
   * $XDSDocumentEntryTypeCode (O,M).
   */
  private List<CodedValue> documentTypes;
  
  /**
   * $XDSDocumentEntryClassCode (O,M).
   */
  private List<CodedValue> documentClass;

  /**
   * $XDSDocumentEntryFormatCode (O,M).
   */
  private List<CodedValue> templateIds;

  /**
   * $XDSDocumentEntryCreationTimeFrom (O).
   */
  private String documentCreationTimeFrom;

  /**
   * $XDSDocumentEntryCreationTimeTo (O).
   */
  private String documentCreationTimeTo;

  /**
   * $XDSDocumentEntryServiceStartTimeFrom (O).
   */
  private String serviceStartTimeFrom;

  /**
   * $XDSDocumentEntryServiceStartTimeTo (O).
   */
  private String serviceStartTimeTo;

  /**
   * $XDSDocumentEntryServiceStopTimeFrom (O).
   */
  private String serviceStopTimeFrom;

  /**
   * $XDSDocumentEntryServiceStopTimeTo (O)
   */
  private String serviceStopTimeTo;

  /**
   * $XDSDocumentEntryHealthcareFacilityTypeCode (O,M).
   */
  private List<CodedValue> healthcareFacilityTypes;

  /**
   * $XDSDocumentEntryPracticeSettingCode (O,M).
   */
  private List<CodedValue> clinicalSpecialties;

  /**
   * $XDSDocumentEntryEventCodeList (O,M).
   */
  private Set<String> keywords;

  /**
   * $XDSDocumentEntryStatus(R,M).
   */
  private Set<DocumentStatus> statuses;

  /**
   * Default constructor.
   */
  public DocumentQueryParams() {
  }

  /**
   * Constructor.
   *
   * @param status variable list of {@link DocumentStatus} types.
   */
  public DocumentQueryParams(DocumentStatus... status) {
    if ((status != null) && (status.length > 0)) {
      getStatuses().addAll(Arrays.asList(status));
    }
  }

  /**
   * Are there any <code>authorIndividuals</code> in the query parameters?
   *
   * @return true if there are any <code>authorIndividuals</code> in the query
   *         parameters.
   */
  public boolean hasAuthorIndividuals() {
    return (this.authorIndividuals != null) && !this.authorIndividuals.isEmpty();
  }

  /**
   * Gets the value of the <code>authorIndividuals</code> property.
   * <p/>
   * If multiple values are specified than they will be considered to represent
   * 'OR' semantics.
   * <p/>
   * The value for this parameter is a pattern compatible with the SQL keyword
   * LIKE which allows the use of the following wildcard characters: % to match
   * any (or no) characters and _ to match a single character.
   * <p/>
   * This accessor method returns a reference to the live list, not a snapshot.
   * This is why there is not a <code>set</code> method for the
   * <code>authorIndividuals</code> property.
   * <p/>
   * For example, to add a new item, do as follows:
   * <p/>
   * <pre>
   * getAuthorIndividuals().add(newItem);
   * </pre>
   * <p/>
   * Equivalent to the <code>$XDSDocumentEntryAuthorPerson</code> query
   * parameter.
   *
   * @return value of authorIndividuals
   */
  public List<Author> getAuthorIndividuals() {
    if (this.authorIndividuals == null) {
      this.authorIndividuals = new ArrayList<Author>();
    }
    return this.authorIndividuals;
  }

  /**
   * Are there any <code>documentTypes</code> in the query parameters?
   *
   * @return true if there are any <code>documentTypes</code> in the query
   *         parameters.
   */
  public boolean hasDocumentTypes() {
    return (this.documentTypes != null) && !this.documentTypes.isEmpty();
  }

  /**
   * Gets the value of the <code>documentTypes</code> property.
   * <p/>
   * If multiple values are specified than they will be considered to represent
   * 'OR' semantics.
   * <p/>
   * This accessor method returns a reference to the live list, not a snapshot.
   * This is why there is not a <code>set</code> method for the
   * <code>documentTypes</code> property.
   * <p/>
   * For example, to add a new item, do as follows:
   * <p/>
   * <pre>
   * getDocumentTypes().add(newItem);
   * </pre>
   * <p/>
   * Equivalent to the <code>$XDSDocumentEntryTypeCode</code> query parameter.
   *
   * @return value of documentTypes
   */
  public List<CodedValue> getDocumentTypes() {
    if (this.documentTypes == null) {
      this.documentTypes = new ArrayList<CodedValue>();
    }
    return this.documentTypes;
  }
  
  /**
   * Are there any <code>documentTypes</code> in the query parameters?
   *
   * @return true if there are any <code>documentTypes</code> in the query
   *         parameters.
   */
  public boolean hasDocumentClasses() {
    return (this.documentClass != null) && !this.documentClass.isEmpty();
  }

  /**
   * Gets the value of the <code>documentClass</code> property.
   * <p/>
   * If multiple values are specified than they will be considered to represent
   * 'OR' semantics.
   * <p/>
   * This accessor method returns a reference to the live list, not a snapshot.
   * This is why there is not a <code>set</code> method for the
   * <code>documentTypes</code> property.
   * <p/>
   * For example, to add a new item, do as follows:
   * <p/>
   * <pre>
   * getDocumentTypes().add(newItem);
   * </pre>
   * <p/>
   * Equivalent to the <code>$XDSDocumentEntryClassCode</code> query parameter.
   *
   * @return value of documentTypes
   */
  public List<CodedValue> getDocumentClasses() {
    if (this.documentClass == null) {
      this.documentClass = new ArrayList<CodedValue>();
    }
    return this.documentClass;
  }

  /**
   * Are there any <code>templateIds</code> in the query parameters?
   *
   * @return true if there are any <code>templateIds</code> in the query
   *         parameters.
   */
  public boolean hasTemplateIds() {
    return (this.templateIds != null) && !this.templateIds.isEmpty();
  }

  /**
   * Gets the value of the <code>templateIds</code> property.
   * <p/>
   * If multiple values are specified than they will be considered to represent
   * 'OR' semantics.
   * <p/>
   * This accessor method returns a reference to the live list, not a snapshot.
   * This is why there is not a <code>set</code> method for the
   * <code>templateIds</code> property.
   * <p/>
   * For example, to add a new item, do as follows:
   * <p/>
   * <pre>
   * getTemplateIds().add(newItem);
   * </pre>
   * <p/>
   * Equivalent to the <code>$XDSDocumentEntryFormatCode</code> query parameter.
   *
   * @return value of templateIds
   */
  public List<CodedValue> getTemplateIds() {
    if (this.templateIds == null) {
      this.templateIds = new ArrayList<CodedValue>();
    }
    return this.templateIds;
  }

  /**
   * Returns the lower value of <code>documentCreationTime</code>
   * <p/>
   * Equivalent to the <code>$XDSDocumentEntryCreationTimeFrom</code> query
   * parameter.
   *
   * @return lower value of <code>documentCreationTime</code>
   */
  public String getDocumentCreationTimeFrom() {
    return this.documentCreationTimeFrom;
  }

  /**
   * Sets the lower value of <code>documentCreationTime</code>
   * <p/>
   * Equivalent to the <code>$XDSDocumentEntryCreationTimeFrom</code> query
   * parameter.
   *
   * @param documentCreationTimeFrom lower value of <code>documentCreationTime</code>
   */
  public void setDocumentCreationTimeFrom(String documentCreationTimeFrom) {
    this.documentCreationTimeFrom = documentCreationTimeFrom;
  }

  /**
   * Returns the upper value of <code>documentCreationTime</code>
   * <p/>
   * Equivalent to the <code>$XDSDocumentEntryCreationTimeTo</code> query
   * parameter.
   *
   * @return upper value of <code>documentCreationTime</code>
   */
  public String getDocumentCreationTimeTo() {
    return this.documentCreationTimeTo;
  }

  /**
   * Sets the upper value of <code>documentCreationTime</code>
   * <p/>
   * Equivalent to the <code>$XDSDocumentEntryCreationTimeTo</code> query
   * parameter.
   *
   * @param documentCreationTimeTo upper value of <code>documentCreationTime</code>
   */
  public void setDocumentCreationTimeTo(String documentCreationTimeTo) {
    this.documentCreationTimeTo = documentCreationTimeTo;
  }

  /**
   * Returns the lower value of <code>serviceStartTime</code>
   * <p/>
   * Equivalent to the <code>$XDSDocumentEntryServiceStartTimeFrom</code> query
   * parameter.
   *
   * @return lower value of <code>serviceStartTime</code>
   */
  public String getServiceStartTimeFrom() {
    return this.serviceStartTimeFrom;
  }

  /**
   * Sets the lower value of <code>serviceStartTime</code>
   * <p/>
   * Equivalent to the <code>$XDSDocumentEntryServiceStartTimeFrom</code> query
   * parameter.
   *
   * @param serviceStartTimeFrom lower value of <code>serviceStartTime</code>
   */
  public void setServiceStartTimeFrom(String serviceStartTimeFrom) {
    this.serviceStartTimeFrom = serviceStartTimeFrom;
  }

  /**
   * Returns the upper value of <code>serviceStartTime</code>
   * <p/>
   * Equivalent to the <code>$XDSDocumentEntryServiceStartTimeTo</code> query
   * parameter.
   *
   * @return upper value of <code>serviceStartTime</code>
   */
  public String getServiceStartTimeTo() {
    return this.serviceStartTimeTo;
  }

  /**
   * Sets the upper value of <code>serviceStartTime</code>
   * <p/>
   * Equivalent to the <code>$XDSDocumentEntryServiceStartTimeTo</code> query
   * parameter.
   *
   * @param serviceStartTimeTo upper value of <code>serviceStartTime</code>
   */
  public void setServiceStartTimeTo(String serviceStartTimeTo) {
    this.serviceStartTimeTo = serviceStartTimeTo;
  }

  /**
   * Returns the lower value of <code>serviceStopTime</code>
   * <p/>
   * Equivalent to the <code>$XDSDocumentEntryServiceStopTimeFrom</code> query
   * parameter.
   *
   * @return lower value of <code>serviceStopTime</code>
   */
  public String getServiceStopTimeFrom() {
    return this.serviceStopTimeFrom;
  }

  /**
   * Sets the lower value of <code>serviceStopTime</code>
   * <p/>
   * Equivalent to the <code>$XDSDocumentEntryServiceStopTimeFrom</code> query
   * parameter.
   *
   * @param serviceStopTimeFrom lower value of <code>serviceStopTime</code>
   */
  public void setServiceStopTimeFrom(String serviceStopTimeFrom) {
    this.serviceStopTimeFrom = serviceStopTimeFrom;
  }

  /**
   * Returns the upper value of <code>serviceStopTime</code>
   * <p/>
   * Equivalent to the <code>$XDSDocumentEntryServiceStopTimeTo</code> query
   * parameter.
   *
   * @return upper value of <code>serviceStopTime</code>
   */
  public String getServiceStopTimeTo() {
    return this.serviceStopTimeTo;
  }

  /**
   * Sets the upper value of <code>serviceStopTime</code>
   * <p/>
   * Equivalent to the <code>$XDSDocumentEntryServiceStopTimeTo</code> query
   * parameter.
   *
   * @param serviceStopTimeTo upper value of <code>serviceStopTime</code>
   */
  public void setServiceStopTimeTo(String serviceStopTimeTo) {
    this.serviceStopTimeTo = serviceStopTimeTo;
  }

  /**
   * Are there any <code>healthcareFacilityTypes</code> in the query
   * parameters?
   *
   * @return true if there are any <code>healthcareFacilityTypes</code> in
   *         the query parameters.
   */
  public boolean hasHealthcareFacilityTypes() {
    return (this.healthcareFacilityTypes != null)
      && !this.healthcareFacilityTypes.isEmpty();
  }

  /**
   * Gets the value of the <code>healthcareFacilityTypes</code> property.
   * <p/>
   * If multiple values are specified than they will be considered to represent
   * 'OR' semantics.
   * <p/>
   * This accessor method returns a reference to the live list, not a snapshot.
   * This is why there is not a <code>set</code> method for the
   * <code>healthcareFacilityTypes</code> property.
   * <p/>
   * For example, to add a new item, do as follows:
   * <p/>
   * <pre>
   * getHealthcareFacilityTypes().add(newItem);
   * </pre>
   * <p/>
   * Equivalent to the <code>$XDSDocumentEntryHealthcareFacilityTypeCode</code>
   * query parameter.
   *
   * @return value of healthcareFacilityTypes
   */
  public List<CodedValue> getHealthcareFacilityTypes() {
    if (this.healthcareFacilityTypes == null) {
      this.healthcareFacilityTypes = new ArrayList<CodedValue>();
    }
    return this.healthcareFacilityTypes;
  }

  /**
   * Are there any <code>clinicalSpecialties</code> in the query parameters?
   *
   * @return true if there are any <code>clinicalSpecialties</code> in the
   *         query parameters.
   */
  public boolean hasClinicalSpecialties() {
    return (this.clinicalSpecialties != null) && !this.clinicalSpecialties.isEmpty();
  }

  /**
   * Gets the value of the <code>clinicalSpecialties</code> property.
   * <p/>
   * If multiple values are specified than they will be considered to represent
   * 'OR' semantics.
   * <p/>
   * This accessor method returns a reference to the live list, not a snapshot.
   * This is why there is not a <code>set</code> method for the
   * <code>clinicalSpecialties</code> property.
   * <p/>
   * For example, to add a new item, do as follows:
   * <p/>
   * <pre>
   * getClinicalSpecialties().add(newItem);
   * </pre>
   * <p/>
   * Equivalent to the <code>$XDSDocumentEntryPracticeSettingCode</code> query
   * parameter.
   *
   * @return value of clinicalSpecialties
   */
  public List<CodedValue> getClinicalSpecialties() {
    if (this.clinicalSpecialties == null) {
      this.clinicalSpecialties = new ArrayList<CodedValue>();
    }
    return this.clinicalSpecialties;
  }

  /**
   * Are there any <code>keywords</code> in the query parameters?
   *
   * @return true if there are any <code>keywords</code> in the query
   *         parameters.
   */
  public boolean hasKeywords() {
    return (this.keywords != null) && !this.keywords.isEmpty();
  }

  /**
   * Gets the value of the <code>keywords</code> property.
   * <p/>
   * If multiple values are specified than they will be considered to represent
   * 'OR' semantics.
   * <p/>
   * This accessor method returns a reference to the live list, not a snapshot.
   * This is why there is not a <code>set</code> method for the
   * <code>keywords</code> property.
   * <p/>
   * For example, to add a new item, do as follows:
   * <p/>
   * <pre>
   * getKeywords().add(newItem);
   * </pre>
   * <p/>
   * Equivalent to the <code>$XDSDocumentEntryEventCodeList</code> query
   * parameter.
   *
   * @return value of keywords
   */
  public Set<String> getKeywords() {
    if (this.keywords == null) {
      this.keywords = new HashSet<String>();
    }
    return this.keywords;
  }

  /**
   * Are there any <code>statuses</code> in the query parameters?
   *
   * @return true if there are any <code>statuses</code> in the query
   *         parameters.
   */
  public boolean hasStatuses() {
    return (this.statuses != null) && !this.statuses.isEmpty();
  }

  /**
   * Gets the value of the <code>statuses</code> property.
   * <p/>
   * If multiple values are specified than they will be considered to represent
   * 'OR' semantics.
   * <p/>
   * This accessor method returns a reference to the live list, not a snapshot.
   * This is why there is not a <code>set</code> method for the
   * <code>keywords</code> property.
   * <p/>
   * For example, to add a new item, do as follows:
   * <p/>
   * <pre>
   * getStatuses().add(newItem);
   * </pre>
   * <p/>
   * Equivalent to the <code>$XDSDocumentEntryStatus</code> query parameter.
   *
   * @return value of statuses
   */
  public Set<DocumentStatus> getStatuses() {
    if (this.statuses == null) {
      this.statuses = new HashSet<DocumentStatus>();
    }
    return this.statuses;
  }

  /**
   * Check whether the query parameters match the document metadata.
   *
   * @param documentMetadata Document metadata.
   * @return true if the query parameters match the document metadata.
   */
  public boolean matches(DocumentMetadata documentMetadata) {

    // authorIndividuals
    if (hasAuthorIndividuals()) {
      boolean found=false;
      //Assumes documentMetadata.getAuthorPerson() is in XCN format!
      if (documentMetadata.getAuthorPerson() != null) {
        String authorIndividual = documentMetadata.getAuthorPerson().toString();
       
        for (Author author : this.authorIndividuals) {
          if(author.toXCNFormatString().equals(authorIndividual)) {
        	  found=true;
        	  break;
          }
        }
        
        
      }

      if(!found) return false;
      //else continue checking
      
     
    }

    // documentTypes
    if (hasDocumentTypes()) {
      if (!this.documentTypes.contains(documentMetadata.getDocumentClass())) {
        return false;
      }
    }

    // templateIds
    if (hasTemplateIds()) {
      if (!this.templateIds.contains(documentMetadata.getFormatCode())) {
        return false;
      }
    }

    // documentCreationTimeFrom
    if (this.documentCreationTimeFrom != null) {
      if ((documentMetadata.getCreationTime() == null)
        || this.documentCreationTimeFrom.compareTo(documentMetadata.getCreationTime()) > DatatypeConstants.EQUAL) {
        return false;
      }
    }

    // documentCreationTimeTo
    if (this.documentCreationTimeTo != null) {
      if ((documentMetadata.getCreationTime() == null)
        || this.documentCreationTimeFrom.compareTo(documentMetadata.getCreationTime()) < DatatypeConstants.EQUAL) {
        return false;
      }
    }

    // serviceStartTimeFrom
    if (this.serviceStartTimeFrom != null) {
      if ((documentMetadata.getServiceStartTime() == null)
        || this.serviceStartTimeFrom.compareTo(documentMetadata.getServiceStartTime()) < DatatypeConstants.EQUAL) {
        return false;
      }
    }

    // serviceStartTimeTo
    if (this.serviceStartTimeTo != null) {
      if ((documentMetadata.getServiceStartTime() == null)
        || this.serviceStartTimeTo.compareTo(documentMetadata.getServiceStartTime()) < DatatypeConstants.EQUAL) {
        return false;
      }
    }

    // serviceStopTimeFrom
    if (this.serviceStopTimeFrom != null) {
      if ((documentMetadata.getServiceStopTime() == null)
        || this.serviceStopTimeFrom.compareTo(documentMetadata.getServiceStopTime()) > DatatypeConstants.EQUAL) {
        return false;
      }
    }

    // serviceStopTimeTo
    if (this.serviceStopTimeTo != null) {
      if ((documentMetadata.getServiceStopTime() == null)
        || this.serviceStopTimeTo.compareTo(documentMetadata.getServiceStopTime()) < DatatypeConstants.EQUAL) {
        return false;
      }
    }

    // healthcareFacilityTypes
    if (hasHealthcareFacilityTypes()) {
      if (!this.healthcareFacilityTypes.contains(documentMetadata.getHealthcareFacilityType())) {
        return false;
      }
    }

    // clinicalSpecialties
    if (hasClinicalSpecialties()) {
      if (!this.clinicalSpecialties.contains(documentMetadata.getPracticeSetting())) {
        return false;
      }
    }

    // keywords
    if (hasKeywords()) {
      boolean matchesAnyKeyword = false;
      for (String docKeyword : documentMetadata.getKeywords()) {
        if (this.keywords.contains(docKeyword)) {
          matchesAnyKeyword = true;
          break;
        }
      }
      if (!matchesAnyKeyword) {
        return false;
      }
    }

    return true;
  }
}
