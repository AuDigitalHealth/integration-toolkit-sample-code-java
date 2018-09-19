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
 * {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HD}
 * Encapsulates an HL7 V2 hierarchic designator HD identifier.
 */
public final class HD {

  /**
   * HD string component count.
   */
  private static final int HD_COMPONENT_COUNT = 3;

  /**
   * Namespace identifier.
   */
  private final String namespace;

  /**
   * Universal identifier.
   */
  private final String identifier;

  /**
   * Universal identifier type.
   */
  private final String identifierType;

  /**
   * Private constructor to set {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HD} instance variables.
   *
   * @param builder {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HD.Builder} to construct
   *                {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HD} from.
   */
  private HD(Builder builder) {
    namespace = builder.namespace;
    identifier = builder.identifier;
    identifierType = builder.identifierType;
  }

  /**
   * Retrieve namespace.
   *
   * @return Namespace.
   */
  public String getNamespace() {
    return namespace;
  }

  /**
   * Retrieve universal identifier.
   *
   * @return Identifier.
   */
  public String getIdentifier() {
    return identifier;
  }

  /**
   * Retrieve universal identifier type.
   *
   * @return Identifier type.
   */
  public String getIdentifierType() {
    return identifierType;
  }

  /**
   * Parse {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HD} from string.
   *
   * @param parseValue String to be parsed (not null).
   * @return {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HD}
   */
  public static HD parse(String parseValue) {
    ArgumentUtils.checkNotNull(parseValue, "parseValue");

    // Split string in to components.
    List<String> values = new ArrayList<String>(Arrays.asList(parseValue.split("\\" + Separators.NESTED_VALUE)));

    // Pad empty values.
    while (values.size() < HD_COMPONENT_COUNT) {
      values.add("");
    }

    if (values.size() == HD_COMPONENT_COUNT) {
      return new Builder()
        .namespace(values.get(0))
        .identifier(values.get(1))
        .identifierType(values.get(2))
        .build();
    } else {
      throw new IllegalArgumentException("Unable to parse HD - Invalid number of value components:\n\t"
        + "Actual: "
        + values.size()
        + "\n\tExpected: "
        + HD_COMPONENT_COUNT
      );
    }
  }

  /**
   * Outputs an {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HD} in the following format:
   * <p/>
   * namespace^identifier^identifierType
   * <p/>
   * Variables that are not set are denoted by an empty string.
   * <p/>
   * Trailing '^' symbols are trimmed. For example:
   * <p/>
   * 'namespace^^' becomes 'namespace'
   *
   * @return Formatted string.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(namespace);
    sb.append(Separators.NESTED_VALUE);
    sb.append(identifier);
    sb.append(Separators.NESTED_VALUE);
    sb.append(identifierType);
    return sb.toString().replaceAll(String.format("\\%s*$", Separators.NESTED_VALUE), "");
  }

  /**
   * {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HD.Builder}
   * Build class used to construct and validate an {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HD} object.
   */
  public static final class Builder {

    private String namespace;
    private String identifier;
    private String identifierType;

    /**
     * Default constructor.
     */
    public Builder() {
      this.namespace = "";
      this.identifier = "";
      this.identifierType = "";
    }

    /**
     * Set the namespace.
     *
     * @param value Namespace (not null).
     * @return {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HD.Builder}
     */
    public Builder namespace(String value) {
      this.namespace = value;
      return this;
    }

    /**
     * Set the universal identifier.
     *
     * @param value Universal identifier (not null).
     * @return {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HD.Builder}
     */
    public Builder identifier(String value) {
      this.identifier = value;
      return this;
    }

    /**
     * Set the universal identifier type.
     *
     * @param value Universal identifier type (not null).
     * @return {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HD.Builder}
     */
    public Builder identifierType(String value) {
      this.identifierType = value;
      return this;
    }

    /**
     * Build HD and validate necessary fields.
     *
     * @return {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HD}
     */
    public HD build() {
      return new HD(this);
    }
  }
}
