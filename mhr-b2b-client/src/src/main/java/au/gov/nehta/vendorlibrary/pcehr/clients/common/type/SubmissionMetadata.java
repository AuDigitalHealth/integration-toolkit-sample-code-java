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

import java.util.Date;

/**
 * The {@link SubmissionMetadata} encapsulates the set of metadata that
 * describes a Document Submission. Additional data is provided at the document
 * level in the {@link DocumentMetadata}.
 */
public final class SubmissionMetadata {
  private Date submissionTime;
  private String comments;
  private String entryUuid;

  /**
   * Returns the date and time that the document was submitted.
   *
   * @return the date and time that the document was submitted
   */
  public Date getSubmissionTime() {
    //cloning time to prevent find bugs malicious code warning
    return new Date(this.submissionTime.getTime());
  }

  /**
   * Sets the date and time that the document was submitted.
   *
   * @param submissionTime the date and time that the document was submitted
   */
  public void setSubmissionTime(Date submissionTime) {
    //cloning time to prevent find bugs malicious code warning
    this.submissionTime = new Date(submissionTime.getTime());
  }

  /**
   * Returns the comments deemed relevant by the entity submitting or
   * registering the document.
   *
   * @return the comments deemed relevant by the entity submitting or
   *         registering the document.
   */
  public String getComments() {
    return this.comments;
  }

  /**
   * Sets the comments deemed relevant by the entity submitting or registering
   * the document.
   *
   * @param comments the comments deemed relevant by the entity submitting or
   *                 registering the document.
   */
  public void setComments(String comments) {
    this.comments = comments;
  }

  public String getEntryUuid() {
    return entryUuid;
  }

  public void setEntryUuid(String entryUuid) {
    this.entryUuid = entryUuid;
  }
}
