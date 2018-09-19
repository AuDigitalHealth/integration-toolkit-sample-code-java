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
package au.gov.nehta.vendorlibrary.clinicalpackage.enums;

/**
 * XML namespaces used in generating a package.
 */
public enum XMLNamespaces {

  /**
   * Digital signature namespace.
   */
  DS("ds", "http://www.w3.org/2000/09/xmldsig#"),

  /**
   * HL7 default namespace.
   */
  HL7("hl7", "urn:hl7-org:v3");


  private final String prefix;
  private final String namespace;

  private XMLNamespaces(String prefix, String namespace) {
    this.prefix = prefix;
    this.namespace = namespace;
  }

  /**
   * Retrieve namespace prefix.
   *
   * @return Prefix string.
   */
  public String getPrefix() {
    return prefix;
  }

  /**
   * Retrieve namespace URI.
   *
   * @return Namespace URI string.
   */
  public String getNamespace() {
    return namespace;
  }

  /**
   * Retrieves a {@link XMLNamespaces} based on a supplied prefix.
   *
   * @param prefix Prefix to search for.
   * @return Corresponding {@link XMLNamespaces} or null.
   */
  public static XMLNamespaces findByPrefix(String prefix) {
    for (XMLNamespaces v : values()) {
      if (v.getPrefix().equals(prefix)) {
        return v;
      }
    }
    return null;
  }

  /**
   * Retrieves a {@link XMLNamespaces} based on a supplied namespace URI.
   *
   * @param namespace Namespace URI to search for.
   * @return Corresponding {@link XMLNamespaces} or null.
   */
  public static XMLNamespaces findByNamespace(String namespace) {
    for (XMLNamespaces v : values()) {
      if (v.getNamespace().equals(namespace)) {
        return v;
      }
    }
    return null;
  }
}

