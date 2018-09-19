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

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@link DocumentMetadata} encapsulates the set of metadata associated with
 * a document.
 */
public final class DocumentMetadata {

  private String creationTime;
  private String languageCode;
  private CX patientId;
  private CX sourcePatientId;
  private String uniqueId;

  
  private XON authorInstitution;
  private XCN authorPerson;
  private String authorSpecialty;

private CodedValue documentClass;
  private ConfidentialityCodes confidentialityCode;
  private String entryUuid;
  private CodedValue healthcareFacilityType;
  private String mimeType;
  private CodedValue practiceSetting;
  private CodedValue documentType;
  private String uri;
  private String repositoryUniqueId;
  private BigInteger documentSize;

  private CodedValue formatCode;
  private byte[] documentHash = new byte[0];
  private String title;
  private String serviceStartTime;
  private String serviceStopTime;
  private Set<String> keywords;

  /**
   * Returns the identifier of the organisation that authored the document.
   *
   * @return the identifier of the organisation that authored the document
   */
  public XON getAuthorInstitution() {
    return this.authorInstitution;
  }

  /**
   * Sets the identifier of the organisation that authored the document.
   *
   * @param authorInstitution the identifier of the organisation that authored the document
   */
  public void setAuthorInstitution(XON authorInstitution) {
    this.authorInstitution = authorInstitution;
  }

  /**
   * Returns the identifier of the individual that authored the document.
   *
   * @return the identifier of the individual that authored the document.
   */
  public XCN getAuthorPerson() {
    return this.authorPerson;
  }

  /**
   * Sets the identifier of the individual that authored the document.
   *
   * @param authorPerson the identifier of the individual that authored the document.
   */
  public void setAuthorPerson(XCN authorPerson) {
    this.authorPerson = authorPerson;
  }

  /**
   * Returns the type of document being submitted.
   *
   * @return the the type of document being submitted.
   */
  public CodedValue getDocumentClass() {
    return this.documentClass;
  }

  /**
   * Sets the type of document being submitted.
   *
   * @param documentClass the type of document being submitted.
   */
  public void setDocumentClass(CodedValue documentClass) {
    this.documentClass = documentClass;
  }

  /**
   * Returns the identifier of the template this document conforms to.
   *
   * @return the identifier of the template this document conforms to.
   */
  public CodedValue getFormatCode() {
    return this.formatCode;
  }

  /**
   * Sets the identifier of the template this document conforms to.
   *
   * @param formatCode the identifier of the template this document conforms to.
   */
  public void setFormatCode(CodedValue formatCode) {
    this.formatCode = formatCode;
  }

  /**
   * Returns the SHA-1 hash of the document.
   *
   * @return value of documentHash
   */
  public byte[] getDocumentHash() {
    byte[] copy = documentHash.clone();
    return copy;
  }

  /**
   * Sets the SHA-1 hash of the document.
   *
   * @param documentHash the documentHash to set
   */
  public void setDocumentHash(final byte[] documentHash) {
    this.documentHash = documentHash.clone();
  }

  /**
   * Returns the title for the given document.
   *
   * @return the title for the given document.
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Sets the title for the given document.
   *
   * @param title the title for the given document.
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Returns the time the document was created.
   *
   * @return the time the document was created.
   */
  public String getCreationTime() {
    return this.creationTime;
  }

  /**
   * Sets the time the document was created.
   *
   * @param creationTime the time the document was created.
   */
  public void setCreationTime(String creationTime) {
    this.creationTime = creationTime;
  }

  /**
   * Returns the datetime that the service being performed, which caused the
   * document to be created, started.
   *
   * @return the datetime that the service being performed, which caused the
   *         document to be created, started.
   */
  public String getServiceStartTime() {
    return this.serviceStartTime;
  }

  /**
   * Sets the datetime that the service being performed, which caused the
   * document to be created, started.
   *
   * @param serviceStartTime the datetime that the service being performed, which caused the
   *                         document to be created, started.
   */
  public void setServiceStartTime(String serviceStartTime) {
    this.serviceStartTime = serviceStartTime;
  }

  /**
   * Returns the datetime that the service being performed, which caused the
   * document to be created, stopped.
   *
   * @return the datetime that the service being performed, which caused the
   *         document to be created, stopped.
   */
  public String getServiceStopTime() {
    return this.serviceStopTime;
  }

  /**
   * Sets the datetime that the service being performed, which caused the
   * document to be created, stopped.
   * <p/>
   * The Service Stop Time may be set to the same value as the Service Start
   * Time in order to indicate the datetime of an event.
   *
   * @param serviceStopTime the datetime that the service being performed, which caused the
   *                        document to be created, stopped.
   */
  public void setServiceStopTime(String serviceStopTime) {
    this.serviceStopTime = serviceStopTime;
  }

  /**
   * Returns the type of healthcare facility where the event relating to this
   * document submission request initiated.
   *
   * @return the type of healthcare facility where the event relating to this
   *         document submission request initiated.
   */
  public CodedValue getHealthcareFacilityType() {
    return this.healthcareFacilityType;
  }

  /**
   * Sets the type of healthcare facility where the event relating to this
   * document submission request initiated.
   *
   * @param healthcareFacilityType the type of healthcare facility where the event relating to this
   *                               document submission request initiated.
   */
  public void setHealthcareFacilityType(CodedValue healthcareFacilityType) {
    this.healthcareFacilityType = healthcareFacilityType;
  }

  /**
   * Returns clinical specialty where the event relating to this document
   * submission request initiated.
   *
   * @return the clinical specialty where the event relating to this document
   *         submission request initiated.
   */
  public CodedValue getPracticeSetting() {
    return this.practiceSetting;
  }

  /**
   * Sets the clinical specialty where the event relating to this document
   * submission request initiated.
   *
   * @param practiceSetting the clinical specialty where the event relating to this document
   *                        submission request initiated.
   */
  public void setPracticeSetting(CodedValue practiceSetting) {
    this.practiceSetting = practiceSetting;
  }

  /**
   * Are there any keywords related to the document?
   *
   * @return true if there are any keywords related to the document.
   */
  public boolean hasKeywords() {
    return (this.keywords != null) && (this.keywords.size() > 0);
  }

  /**
   * Get the working set of keywords which are related to the document
   * submission.
   *
   * @return the working set of keywords which are related to the document
   *         submission.
   */
  public Set<String> getKeywords() {
    if (this.keywords == null) {
      this.keywords = new HashSet<String>();
    }
    return this.keywords;
  }

  public String getLanguageCode() {
    return languageCode;
  }

  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }
  
  public String getAuthorSpecialty() {
    return authorSpecialty;
  }

  public void setAuthorSpecialty( String authorSpecialty ) {
    this.authorSpecialty = authorSpecialty;
  }

  public CX getPatientId() {
    return patientId;
  }

  public void setPatientId(CX patientId) {
    this.patientId = patientId;
  }

  public CX getSourcePatientId() {
    return sourcePatientId;
  }

  public void setSourcePatientId(CX sourcePatientId) {
    this.sourcePatientId = sourcePatientId;
  }

  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public ConfidentialityCodes getConfidentialityCode() {
    return confidentialityCode;
  }

  public void setConfidentialityCode(ConfidentialityCodes confidentialityCode) {
    this.confidentialityCode = confidentialityCode;
  }

  public String getEntryUuid() {
    return entryUuid;
  }

  public void setEntryUuid(String entryUuid) {
    this.entryUuid = entryUuid;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public CodedValue getDocumentType() {
    return documentType;
  }

  public void setDocumentType(CodedValue documentType) {
    this.documentType = documentType;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getRepositoryUniqueId() {
    return repositoryUniqueId;
  }

  public void setRepositoryUniqueId(String repositoryUniqueId) {
    this.repositoryUniqueId = repositoryUniqueId;
  }

  public BigInteger getDocumentSize() {
    return documentSize;
  }

  public void setDocumentSize(BigInteger documentSize) {
    this.documentSize = documentSize;
  }
}
