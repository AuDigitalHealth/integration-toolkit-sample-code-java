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
 * Encapsulates a partial implementation of an HL7 V2 Extended Organisation Name XON identifier.
 */
public final class XON {

  /**
   * XON string component count.
   */
  private static final int XON_COMPONENT_COUNT = 10;

  /**
   * Organisation name.
   */
  private final String organisationName;

  /**
   * Organisation name type code.
   */
  private final String organisationNameTypeCode;

  /**
   * ID number.
   */
  private final String idNumber;

  /**
   * Check digit.
   */
  private final String checkDigit;

  /**
   * Code identifying the check digit scheme employed.
   */
  private final String checkDigitScheme;

  /**
   * Assigning authority.
   */
  private final HD assigningAuthority;

  /**
   * Identifier type code.
   */
  private final String identifierTypeCode;

  /**
   * Assigning Facility ID.
   */
  private final HD assigningFacilityId;

  /**
   * Name representation code.
   */
  private final String nameRepresentationCode;

  /**
   * Organisation identifier.
   */
  private final String organisationIdentifier;

  /**
   * Private constructor to set {@link XON} instance variables.
   *
   * @param builder Builder to construct {@link XON} from.
   */
  private XON(Builder builder) {
    this.organisationName = builder.organisationName;
    this.organisationNameTypeCode = builder.organisationNameTypeCode;
    this.idNumber = builder.idNumber;
    this.checkDigit = builder.checkDigit;
    this.checkDigitScheme = builder.checkDigitScheme;
    this.assigningAuthority = builder.assigningAuthority;
    this.identifierTypeCode = builder.identifierTypeCode;
    this.assigningFacilityId = builder.assigningFacilityId;
    this.nameRepresentationCode = builder.nameRepresentationCode;
    this.organisationIdentifier = builder.organisationIdentifier;
  }

  /**
   * Retrieve organisation identifier.
   *
   * @return Organisation identifier.
   */
  public String getOrganisationIdentifier() {
    return organisationIdentifier;
  }

  /**
   * Retrieve organisation name.
   *
   * @return Organisation name.
   */
  public String getOrganisationName() {
    return organisationName;
  }

  /**
   * Outputs an {@link XON} in the following format:
   * <p/>
   * organisationName^organisationNameTypeCode^idNumber^checkDigit^checkDigitScheme^assigningAuthority^identifierTypeCode^assigningFacilityId
   * ^nameRepresentationCode^organisationIdentifier
   * <p/>
   * Variables that are not set are denoted by an empty string.
   * <p/>
   * Trailing separators are trimmed. For example:
   * <p/>
   * 'organisationName^^^checkDigit^^^^^^' becomes 'organisationName^^^checkDigit'
   *
   * @return Formatted string.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(organisationName);
    sb.append(Separators.VALUE);
    sb.append(organisationNameTypeCode);
    sb.append(Separators.VALUE);
    sb.append(idNumber);
    sb.append(Separators.VALUE);
    sb.append(checkDigit);
    sb.append(Separators.VALUE);
    sb.append(checkDigitScheme);
    sb.append(Separators.VALUE);
    sb.append(assigningAuthority);
    sb.append(Separators.VALUE);
    sb.append(identifierTypeCode);
    sb.append(Separators.VALUE);
    sb.append(assigningFacilityId);
    sb.append(Separators.VALUE);
    sb.append(nameRepresentationCode);
    sb.append(Separators.VALUE);
    sb.append(organisationIdentifier);

    return sb.toString().replaceAll(String.format("\\%s*$", Separators.VALUE), "");
  }

  /**
   * Builder class used to construct and validate an {@link XON} object.
   */
  public static final class Builder {

    private String organisationName;
    private String organisationNameTypeCode;
    private String idNumber;
    private String checkDigit;
    private String checkDigitScheme;
    private HD assigningAuthority;
    private String identifierTypeCode;
    private HD assigningFacilityId;
    private String nameRepresentationCode;
    private String organisationIdentifier;

    /**
     * Default constructor.
     */
    public Builder() {
      this.organisationName = "";
      this.organisationNameTypeCode = "";
      this.idNumber = "";
      this.checkDigit = "";
      this.checkDigitScheme = "";
      this.assigningAuthority = new HD.Builder().identifier("").namespace("").identifierType("").build();
      this.identifierTypeCode = "";
      this.assigningFacilityId = new HD.Builder().identifier("").namespace("").identifierType("").build();
      this.nameRepresentationCode = "";
      this.organisationIdentifier = "";
    }

    /**
     * Set the organisation name.
     *
     * @param organisationName Organisation name string.
     * @return {@link Builder}.
     */
    public Builder organisationName(final String organisationName) {
      this.organisationName = organisationName;
      return this;
    }

    /**
     * Set the organisation identifier.
     *
     * @param organisationIdentifier Organisation identifier string.
     * @return {@link Builder}
     */
    public Builder organisationIdentifier(final String organisationIdentifier) {
      this.organisationIdentifier = organisationIdentifier;
      return this;
    }

    /**
     * Build {@link XON} and validate necessary fields.
     *
     * @return {@link XON} object.
     */
    public XON build() {
      return new XON(this);
    }
  }
}
