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

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.Separators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CX}
 * Encapsulates a HL7 V2 CX identifier.
 */
public final class CX {

  /**
   * CX string component count.
   */
  private static final int CX_COMPONENT_COUNT = 5;
  private static final int ASSIGNING_AUTHORITY_INDEX = 3;
  private static final int IDENTIFIER_INDEX = 0;
  private static final int IDENTIFIER_TYPE_CODE_INDEX = 4;

  /**
   * Identifier.
   */
  private final String identifier;

  /**
   * Assigning authority.
   */
  private final HD assigningAuthority;

  /**
   * Identifier type code.
   */
  private final String identifierTypeCode;

  /**
   * Private constructor to set {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CX} instance variables.
   *
   * @param builder {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CX.Builder} to construct
   *                {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CX} from.
   */
  private CX(Builder builder) {
    this.identifier = builder.identifier;
    this.assigningAuthority = builder.assigningAuthority;
    this.identifierTypeCode = builder.identifierTypeCode;
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
   * Retrieve assigning authority.
   *
   * @return Assigning authority designator.
   */
  public HD getAssigningAuthority() {
    return assigningAuthority;
  }

  /**
   * Retrieve identifier type code.
   *
   * @return Identifier type code.
   */
  public String getIdentifierTypeCode() {
    return identifierTypeCode;
  }


  /**
   * Parse multiple {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CX} objects from string.
   *
   * @param value     String to be parsed (not null nor blank).
   * @param separator {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CX} list delimiter (not null or blank).
   * @return List of {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CX}.
   */
  public static List<CX> parseMultiple(String value, String separator) {
    ArgumentUtils.checkNotNullNorBlank(value, "value");
    ArgumentUtils.checkNotNullNorBlank(separator, "separator");

    List<CX> cxs = new ArrayList<CX>();
    List<String> values = Arrays.asList(value.split(separator));

    for (String rawCx : values) {
      cxs.add(parse(rawCx));
    }
    return cxs;
  }

  /**
   * Outputs a {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CX} in the following format:
   * <p/>
   * identifier^^^assigningAuthority^identifierTypeCode
   * <p/>
   * Variables that are not set are denoted by an empty string.
   * <p/>
   * Trailing '^' symbols are trimmed. For example:
   * <p/>
   * 'identifier^^^^' becomes 'identifier'
   *
   * @return Formatted string.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(identifier);
    sb.append(Separators.VALUE);
    sb.append(Separators.VALUE);
    sb.append(Separators.VALUE);
    sb.append(assigningAuthority.toString());
    sb.append(Separators.VALUE);
    sb.append(identifierTypeCode);
    return sb.toString().replaceAll(String.format("\\%s*$", Separators.VALUE), "");
  }

  /**
   * Parse {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CX} from string.
   *
   * @param parseValue String to be parsed (not null).
   * @return {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CX}
   */
  public static CX parse(String parseValue) {
    ArgumentUtils.checkNotNull(parseValue, "parseValue");

    // Split string in to components.
    List<String> values = new ArrayList<String>(Arrays.asList(parseValue.split("\\" + Separators.VALUE)));

    // Pad empty values.
    while (values.size() < CX_COMPONENT_COUNT) {
      values.add("");
    }

    if (values.size() == CX_COMPONENT_COUNT) {
      return new Builder()
        .identifier(values.get(IDENTIFIER_INDEX))
        .assigningAuthority(HD.parse(values.get(ASSIGNING_AUTHORITY_INDEX)))
        .identifierTypeCode(values.get(IDENTIFIER_TYPE_CODE_INDEX))
        .build();
    } else {
      throw new IllegalArgumentException("Unable to parse CX - Invalid number of value components:\n\t"
        + "Actual: "
        + values.size()
        + "\n\tExpected: "
        + CX_COMPONENT_COUNT
      );
    }
  }

  /**
   * {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CX.Builder}
   * Build class used to construct and validate a {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CX} object.
   */
  public static final class Builder {

    private String identifier;
    private HD assigningAuthority;
    private String identifierTypeCode;

    /**
     * Default constructor.
     */
    public Builder() {
      this.identifier = "";
      this.identifierTypeCode = "";
    }

    /**
     * Set identifier.
     *
     * @param value Identifier (not null).
     * @return {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CX.Builder}
     */
    public Builder identifier(String value) {
      this.identifier = value;
      return this;
    }

    /**
     * Set assigning authority.
     *
     * @param value {@link HD} (not null).
     * @return {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CX.Builder}
     */
    public Builder assigningAuthority(HD value) {
      this.assigningAuthority = value;
      return this;
    }

    /**
     * Set identifier type code.
     *
     * @param value Identifier type code (not null).
     * @return {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CX.Builder}
     */
    public Builder identifierTypeCode(String value) {
      this.identifierTypeCode = value;
      return this;
    }

    /**
     * Build {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CX} and validate necessary fields.
     *
     * @return {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CX}
     */
    public CX build() {
      return new CX(this);
    }
  }
}
