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
 * Enumeration of the various XML attributes used in package generation and creation.
 */
public enum XMLAttributes {

  /**
   * Digital signature reference URI attribute.
   */
  DS_REFERENCE_URI("URI"),

  /**
   * Digital signature digest method algorithm attribute.
   */
  DS_DIGEST_METHOD_ALGORITHM("Algorithm");

  private final String name;

  private XMLAttributes(String name) {
    this.name = name;
  }

  /**
   * Returns the name of the XML attribute name value.
   *
   * @return XML attribute name (type String).
   */
  public String getName() {
    return name;
  }
}
