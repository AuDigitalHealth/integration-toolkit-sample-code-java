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
package au.gov.nehta.vendorlibrary.clinicalpackage.core;

import au.gov.nehta.vendorlibrary.clinicalpackage.enums.UriTypes;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.SubmissionSetException;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.Validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Encapsulates members that comprise a package's submission set.
 */
public final class SubmissionSet {

  /**
   * Default hash value for hash code calculation.
   */
  private static final int HASH_VALUE = 31;

  /**
   * Root CDA XML document member.
   */
  private final Member rootDocument;

  /**
   * Optional and repeatable packaged members.
   */
  private final List<Member> attachments;

  /**
   * eSignature member.
   */
  private final Member signature;

  /**
   * Private constructor to build a <code>SubmissionSet</code> object from a valid {@link Builder} object.
   *
   * @param builder Populated {@link Builder} object.
   */
  private SubmissionSet(final Builder builder) {
    this.rootDocument = builder.rootDocument;
    this.attachments = builder.attachments;
    this.signature = builder.signature;
  }

  /**
   * Returns this <code>SubmissionSet</code> object's root document {@link Member} object.
   *
   * @return the root document (type {@link Member}) object of this <code>SubmissionSet</code> object.
   */
  public Member getRootDocument() {
    return rootDocument;
  }

  /**
   * Returns the list of attachments associated with this <code>SubmissionSet</code> object.
   *
   * @return the attachment list (type {@link List}) of this SubmissionSet.
   */
  public List<Member> getAttachments() {
    return attachments;
  }

  /**
   * Returns this <code>SubmissionSet</code> object's signature {@link Member} object.
   *
   * @return the signature (type {@link Member}) object of this <code>SubmissionSet</code> object.
   */
  public Member getSignature() {
    return signature;
  }

  /**
   * Indicates whether a supplied object is equal to the current object.
   *
   * @param obj the reference object with which to compare this object.
   * @return <code>true</code> if this object is the same as the supplied object argument; <code>false</code> otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    SubmissionSet that = (SubmissionSet) obj;

    Collections.sort(attachments, new Comparator<Member>() {
      public int compare(Member o1, Member o2) {
        return o1.getUri().compareTo(o2.getUri());
      }
    });

    Collections.sort(that.attachments, new Comparator<Member>() {
      public int compare(Member o1, Member o2) {
        return o1.getUri().compareTo(o2.getUri());
      }
    });

    if (attachments != null ? !attachments.equals(that.attachments) : that.attachments != null) {
      return false;
    }
    if (!rootDocument.equals(that.rootDocument)) {
      return false;
    }
    if (signature != null ? !signature.equals(that.signature) : that.signature != null) {
      return false;
    }

    return true;
  }

  /**
   * Returns a hash code value for this object.
   *
   * @return hash code for this object.
   */
  @Override
  public int hashCode() {
    int result = rootDocument.hashCode();
    result = HASH_VALUE * result + (attachments != null ? attachments.hashCode() : 0);
    result = HASH_VALUE * result + (signature != null ? signature.hashCode() : 0);
    return result;
  }

  /**
   * Builder class used to construct a valid {@link SubmissionSet} object.
   */
  public static class Builder {

    private Member rootDocument;
    private final List<Member> attachments;
    private Member signature;

    /**
     * Constructs an empty Builder object.
     */
    public Builder() {
      attachments = new ArrayList<Member>();
    }

    /**
     * Sets the <code>Builder</code> object's root document.
     *
     * @param fileContent byte array comprising the root document's file content (not <code>null</code>).
     * @return the current <code>Builder</code> being populated.
     */
    public final Builder rootDocument(final byte[] fileContent) {
      this.rootDocument = new Member.Builder()
        .uri(UriTypes.ROOT_DOCUMENT.getUri())
        .fileContent(fileContent)
        .build();
      return this;
    }

    /**
     * Adds an attachment to the <code>Builder</code> object's attachment list.
     *
     * @param uri         String of attachment's URI (not <code>null</code> nor empty).
     * @param fileContent byte array comprising the attachment's file content (not <code>null</code>).
     * @return the current <code>Builder</code> being populated.
     */
    public final Builder attachment(final String uri, final byte[] fileContent) {
      attachments.add(new Member.Builder()
        .uri(uri)
        .fileContent(fileContent)
        .build()
      );
      return this;
    }

    /**
     * Adds an existing signature to the <code>Builder</code> object.
     * 
     * Signature is an optional {@link SubmissionSet} {@link Member} object. If supplying a signature, it must contain file content.
     *
     * @param fileContent byte array comprising the signature's file content (not <code>null</code>).
     * @return the current <code>Builder</code> being populated.
     */
    public final Builder signature(final byte[] fileContent) {
      this.signature = new Member.Builder()
        .uri(UriTypes.SIGNATURE.getUri())
        .fileContent(fileContent)
        .build();
      return this;
    }

    /**
     * Builds a validated <code>SubmissionSet</code> object.
     *
     * @return populated and valid <code>SubmissionSet</code> object.
     */
    public final SubmissionSet build() {

      Map<String, List<String>> errors = new HashMap<String, List<String>>();

      // Check root document.
      errors.putAll(Validation.checkNotNull("rootDocument", rootDocument));

      // Check attachments.
      if (!attachments.isEmpty()) {
        errors.putAll(Validation.checkNotNullNorEmpty("attachments", attachments));
      }

      if (errors.isEmpty()) {
        return new SubmissionSet(this);
      } else {
        throw new SubmissionSetException(errors);
      }
    }
  }
}
