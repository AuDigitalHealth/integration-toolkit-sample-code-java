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
 * Enumeration of the various URI types used in package generation and extraction.
 */
public enum UriTypes {

  /**
   * Name of the root CDA document.
   */
  ROOT_DOCUMENT("CDA_ROOT.XML"),

  /**
   * Name of the {@link #ROOT_DOCUMENT}'s signature file.
   */
  SIGNATURE("CDA_SIGN.XML"),

  /**
   * Name of the file providing an HTML entry point into the package contents.
   */
  INDEX("INDEX.HTM"),

  /**
   * Name of the file providing informative information about the package.
   */
  README("README.TXT"),

  /**
   * Name of the root package contents directory.
   */
  ROOT_DIR("IHE_XDM"),

  /**
   * Name of the submission set directory.
   */
  SUBSET_DIR("SUBSET01"),

  /**
   * Fully qualified name of the submission set directory.
   */
  FQ_SUBSET_DIR(String.format("%s/%s/", ROOT_DIR.getUri(), SUBSET_DIR.getUri()));

  private final String uri;

  private UriTypes(String uri) {
    this.uri = uri;
  }

  /**
   * Returns the URI type.
   * @return URI (type String).
   */
  public String getUri() {
    return uri;
  }
}
