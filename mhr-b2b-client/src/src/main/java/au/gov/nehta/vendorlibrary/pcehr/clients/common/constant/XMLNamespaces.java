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
package au.gov.nehta.vendorlibrary.pcehr.clients.common.constant;

/**
 * {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XMLNamespaces} XML namespaces used in generating a package.
 */
public enum XMLNamespaces {

  /**
   * Registry Services namespace.
   */
  RS("rs", "urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0"),

  /**
   * Lifecycle Management namespace.
   */
  LCM("lcm", "urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0"),

  /**
   * XML schema instance namespace.
   */
  XSI("xsi", "http://www.w3.org/2001/XMLSchema-instance"),

  /**
   * Registry Information Model namespace.
   */
  RIM("rim", "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0"),

  /**
   * Digital signature namespace.
   */
  DS("ds", "http://www.w3.org/2000/09/xmldsig#"),

  /**
   * Signed Payload namespace.
   */
  SP("sp", "http://ns.electronichealth.net.au/xsp/xsd/SignedPayload/2010"),

  /**
   * Common core elements namespace.
   */
  COMMON("", "http://ns.electronichealth.net.au/pcehr/xsd/common/CommonCoreElements/1.0"),
  
  /**
   * Register elements namespace.
   */
  REGISTER("", "http://ns.electronichealth.net.au/pcehr/xsd/interfaces/RegisterPCEHR/2.0"),

  /**
   * CDA namespace.
   */
  CDA("cda", "urn:hl7-org:v3"),

  /**
   * CDA Extensions namespace.
   */
  EXT("ext", "http://ns.electronichealth.net.au/Ci/Cda/Extensions/3.0"),

  /**
   * XOP namespace.
   */
  XOP("xop", "http://www.w3.org/2004/08/xop/include"),

  /**
   * IHE namespace.
   */
  IHE("ihe", "urn:ihe:iti:xds-b:2007");

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
   * Retrieves a {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XMLNamespaces} based on a supplied prefix.
   *
   * @param prefix Prefix to search for.
   * @return Corresponding {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XMLNamespaces} or null.
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
   * Retrieves a {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XMLNamespaces} based on a supplied namespace URI.
   *
   * @param namespace Namespace URI to search for.
   * @return Corresponding {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XMLNamespaces} or null.
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
