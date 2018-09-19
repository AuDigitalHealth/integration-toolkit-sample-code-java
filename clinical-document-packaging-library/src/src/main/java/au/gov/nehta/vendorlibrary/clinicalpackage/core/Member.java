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

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.MemberException;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.Validation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Encapsulates data pertaining to a clinical package member.
 */
public final class Member {

  /**
   * Default hash value for hash code calculation.
   */
  private static final int HASH_VALUE = 31;

  /**
   * Member URI.
   * <p/>
   * e.g. file name.
   */
  private final String uri;

  /**
   * Byte array containing member file content.
   */
  private final byte[] fileContent;

  /**
   * Private constructor used to build a <code>Member</code> object from a valid {@link Builder} object.
   *
   * @param builder Populated {@link Builder} object.
   */
  private Member(final Builder builder) {
    uri = builder.uri;
    fileContent = builder.fileContent;
  }

  /**
   * Returns the file content of this <code>Member</code> object.
   *
   * @return the file content (type byte array) of this <code>Member</code> object.
   */
  public byte[] getFileContent() {
    byte[] result = new byte[this.fileContent.length];
    System.arraycopy(this.fileContent, 0, result, 0, result.length);
    return result;
  }

  /**
   * Returns the URI of this <code>Member</code> object.
   *
   * @return the URI (type String) of this <code>Member</code> object.
   */
  public String getUri() {
    return uri;
  }

  /**
   * Indicates whether a supplied object is equal to the current object.
   *
   * @param obj the reference object with which to compare this object.
   * @return <code>true</code> if this object is the same as the supplied object argument; <code>false</code> otherwise.
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Member that = (Member) obj;
    if (!Arrays.equals(fileContent, that.fileContent)) {
      return false;
    }
    if (uri.compareToIgnoreCase(that.uri) != 0) {
      return false;
    }
    return true;
  }

  /**
   * Returns a hash code value for this object.
   *
   * @return hash code value (type int) for this object.
   */
  @Override
  public int hashCode() {
    return HASH_VALUE * uri.hashCode() + Arrays.hashCode(fileContent);
  }

  /**
   * Builder class used to construct a valid {@link Member} object.
   */
  public static class Builder {

    private String uri;
    private byte[] fileContent;

    /**
     * Sets the <code>Builder</code> object's uri.
     *
     * @param uri of {@link Member} object being built (not <code>null</code> nor empty).
     * @return the current <code>Builder</code> being populated.
     */
    public final Builder uri(final String uri) {
      ArgumentUtils.checkNotNullNorBlank(uri, "uri");
      this.uri = uri;
      return this;
    }

    /**
     * Sets the <code>Builder</code> object's file content.
     *
     * @param fileContent of {@link Member} object being built (not <code>null</code>).
     * @return the current <code>Builder</code> being populated.
     */
    public final Builder fileContent(final byte[] fileContent) {
      if (fileContent != null) {
        this.fileContent = new byte[fileContent.length];
        System.arraycopy(fileContent, 0, this.fileContent, 0, fileContent.length);
      }
      return this;
    }

    /**
     * Builds a validated <code>Member</code> object.
     *
     * @return populated and valid <code>Member</code> object.
     */
    public final Member build() {
      Map<String, List<String>> errors = new HashMap<String, List<String>>();

      // Check uri.
      errors.putAll(Validation.checkNotNullNorBlank("uri", uri));
//      errors.putAll(Validation.checkFileNameOnlyUri("uri", uri));

      // Check fileContent.
      errors.putAll(Validation.checkNotNull("fileContent", fileContent));

      if (!errors.isEmpty()) {
        throw new MemberException(errors);
      }

      return new Member(this);
    }
  }
}
