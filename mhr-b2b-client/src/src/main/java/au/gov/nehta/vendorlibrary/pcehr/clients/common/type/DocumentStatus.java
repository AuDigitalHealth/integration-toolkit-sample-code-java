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

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * A status of a document in the document repository.
 */
public enum DocumentStatus {

  /**
   * Submitted document status.
   */
  SUBMITTED("urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted"),

  /**
   * Approved document status.
   */
  APPROVED("urn:oasis:names:tc:ebxml-regrep:StatusType:Approved"),

  /**
   * Deleted document status.
   */
  DELETED("urn:orcl.reg:names:StatusType:Deleted"),

  /**
   * Deprecated document status.
   */
  DEPRECATED("urn:oasis:names:tc:ebxml-regrep:StatusType:Deprecated");

  private static final Map<String, DocumentStatus> CODE_MAP = new HashMap<String, DocumentStatus>();

  static {
    for (DocumentStatus s : EnumSet.allOf(DocumentStatus.class)) {
      CODE_MAP.put(s.getCode(), s);
    }
  }

  private final String code;

  private DocumentStatus(String code) {
    this.code = code;
  }

  /**
   * Returns the value of 'code'.
   *
   * @return value of code
   */
  public String getCode() {
    return this.code;
  }

  /**
   * Checks that supplied status is valid.
   *
   * @param code String code to check against list of available enum entries.
   * @return Corresponding {@link DocumentStatus} if it exists.
   */
  public static DocumentStatus fromCode(String code) {
    if (!CODE_MAP.containsKey(code)) {
      throw new IllegalArgumentException("'" + code + "' is not a valid DocumentStatus code.");
    }
    return CODE_MAP.get(code);
  }

}
