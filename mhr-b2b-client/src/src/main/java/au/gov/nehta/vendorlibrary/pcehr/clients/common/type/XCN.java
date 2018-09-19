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

import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.Separators;

/**
 * Encapsulates a partial implementation of an HL7 V2 Extended Person Name XCN identifier.
 */
public final class XCN {

  /**
   * Identifier.
   */
  private final String identifier;

  /**
   * Family name.
   */
  private final String familyName;

  /**
   * Given name.
   */
  private final String givenName;

  /**
   * Middle initial or name.
   */
  private final String middleInitialOrName;

  /**
   * Suffix.
   */
  private final String suffix;

  /**
   * Prefix.
   */
  private final String prefix;

  /**
   * Assigning authority.
   */
  private final HD assigningAuthority;

  /**
   * Private constructor to set {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.XCN} instance variables.
   *
   * @param builder Builder to construct {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.XCN} from.
   */
  private XCN(Builder builder) {
    identifier = builder.identifier;
    familyName = builder.familyName;
    givenName = builder.givenName;
    middleInitialOrName = builder.middleInitialOrName;
    suffix = builder.suffix;
    prefix = builder.prefix;
    assigningAuthority = builder.assigningAuthority;
  }

  /**
   * Retrieve unique identifier.
   *
   * @return Unique identifier.
   */
  public String getIdentifier() {
    return identifier;
  }

  /**
   * Retrieve family name.
   *
   * @return Family name.
   */
  public String getFamilyName() {
    return familyName;
  }

  /**
   * Retrieve given name.
   *
   * @return Given name.
   */
  public String getGivenName() {
    return givenName;
  }

  /**
   * Retrieve middle initial or name.
   *
   * @return Middle initial or name.
   */
  public String getMiddleInitialOrName() {
    return middleInitialOrName;
  }

  /**
   * Retrieve suffix.
   *
   * @return Suffix.
   */
  public String getSuffix() {
    return suffix;
  }

  /**
   * Retrieve prefix.
   *
   * @return Prefix.
   */
  public String getPrefix() {
    return prefix;
  }

  /**
   * Retrieve assigning authority.
   *
   * @return Assigning authority designator.
   */
  public HD getAssigningAuthority() {
    return assigningAuthority;
  }

  /**
   * Outputs an {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.XCN} in the following format:
   * <p/>
   * identifier^familyName^givenName^middleInitialOrName^^suffix^pefix^^^assigningAuthority
   * <p/>
   * Variables that are not set are denoted by an empty string.
   * <p/>
   * Trailing '^' symbols are trimmed. For example:
   * <p/>
   * 'identifier^familyName^^^^^^^' becomes 'identifier^familyName'
   *
   * @return Formatted string.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(identifier);
    sb.append(Separators.VALUE);
    sb.append(familyName);
    sb.append(Separators.VALUE);
    sb.append(givenName);
    sb.append(Separators.VALUE);
    sb.append(middleInitialOrName);
    sb.append(Separators.VALUE);
    sb.append(suffix);
    sb.append(Separators.VALUE);
    sb.append(prefix);
    sb.append(Separators.VALUE);
    sb.append(Separators.VALUE);
    sb.append(Separators.VALUE);
    sb.append(assigningAuthority.toString());
    return sb.toString().replaceAll(String.format("\\%s*$", Separators.VALUE), "");
  }

  /**
   * {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.XCN.Builder}
   * Build class used to construct and validate an {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.XCN} object.
   */
  public static final class Builder {

    private String identifier;
    private String familyName;
    private String givenName;
    private String middleInitialOrName;
    private String suffix;
    private String prefix;
    private HD assigningAuthority;

    /**
     * Default constructor.
     */
    public Builder() {
      this.identifier = "";
      this.familyName = "";
      this.givenName = "";
      this.middleInitialOrName = "";
      this.suffix = "";
      this.prefix = "";
    }

    /**
     * Set the identifier.
     *
     * @param value Identifier (not null).
     * @return {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.XCN.Builder}
     */
    public Builder identifier(String value) {
      this.identifier = value;
      return this;
    }

    /**
     * Set the family name (not null).
     *
     * @param value Family name.
     * @return {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.XCN.Builder}
     */
    public Builder familyName(String value) {
      this.familyName = value;
      return this;
    }

    /**
     * Set the given name.
     *
     * @param value Given name (not null).
     * @return {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.XCN.Builder}
     */
    public Builder givenName(String value) {
      this.givenName = value;
      return this;
    }

    /**
     * Set the middle initial/name.
     *
     * @param value Middle initial/name (not null).
     * @return {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.XCN.Builder}
     */
    public Builder middleInitialOrName(String value) {
      this.middleInitialOrName = value;
      return this;
    }

    /**
     * Set the suffix.
     *
     * @param value Suffix (not null).
     * @return {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.XCN.Builder}
     */
    public Builder suffix(String value) {
      this.suffix = value;
      return this;
    }

    /**
     * Set the prefix.
     *
     * @param value Prefix (not null).
     * @return {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.XCN.Builder}
     */
    public Builder prefix(String value) {
      this.prefix = value;
      return this;
    }

    /**
     * Set the assigning authority.
     *
     * @param value Assigning authority (not null).
     * @return {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.XCN.Builder}
     */
    public Builder assigningAuthority(HD value) {
      this.assigningAuthority = value;
      return this;
    }

    /**
     * Build {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.XCN} and validate necessary fields.
     *
     * @return {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.XCN}
     */
    public XCN build() {

      return new XCN(this);
    }
  }
}
