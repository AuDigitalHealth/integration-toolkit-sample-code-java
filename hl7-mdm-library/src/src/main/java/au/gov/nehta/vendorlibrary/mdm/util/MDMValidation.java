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
package au.gov.nehta.vendorlibrary.mdm.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link MDMValidation}
 * Utility class for validation of a package and its contents.
 */
public final class MDMValidation {

  /**
   * Default private constructor to prevent instantiation of utility class.
   */
  private MDMValidation() {
  }

  /**
   * Confirms a string is not null or blank.
   *
   * @param name     Variable name.
   * @param value    String value.
   * @param required Defines whether the variable requires a value or not.
   * @return Map of validation failures or an empty map.
   */
  public static Map<String, List<String>> confirmNotNullNorBlank(String name, String value, boolean required) {
    Map<String, List<String>> result = new HashMap<String, List<String>>();

    List<String> messages = new ArrayList<String>();

    // Check if variable is required.
    if (isNullOrBlank(value) && required) {
      messages.add(name + " cannot be null nor blank");
      result.put(name, messages);
    } else if (isNullOrBlank(value) && !required) {
      result.clear();
    }

    if (messages.size() > 0) {
      result.put(name, messages);
    }

    return result;
  }

  /**
   * Validates an object is not null.
   *
   * @param name     Variable name.
   * @param value    Object value.
   * @param required Defines whether the variable requires a value or not.
   * @return Map of validation failures or an empty map.
   */
  public static Map<String, List<String>> confirmNotNull(String name, Object value, boolean required) {
    Map<String, List<String>> result = new HashMap<String, List<String>>();

    List<String> messages = new ArrayList<String>();

    // Check if variable is required.
    if (isNull(value) && required) {
      messages.add(name + " cannot be null");
      result.put(name, messages);
    } else if (isNull(value) && !required) {
      result.clear();
    }

    if (messages.size() > 0) {
      result.put(name, messages);
    }

    return result;
  }

  /**
   * Confirms an object is null.
   *
   * @param name       Variable name.
   * @param collection Object value.
   * @param required   Defines whether the variable requires a value or not.
   * @return Map of validation failures or an empty map.
   */
  public static Map<String, List<String>> confirmNotNullNorEmpty(String name, Collection collection, boolean required) {
    Map<String, List<String>> result = new HashMap<String, List<String>>();

    List<String> messages = new ArrayList<String>();

    // Check if collection is required to be not null and not empty.
    if (isNullOrEmpty(collection) && required) {
      messages.add(name + " collection cannot be null nor empty");
      result.put(name, messages);
    } else if (isNotNull(collection) && !required) {
      result.clear();
    }

    if (messages.size() > 0) {
      result.put(name, messages);
    }

    return result;
  }

  /**
   * Checks if a collection is null or empty.
   *
   * @param collection Collection.
   * @return true/false.
   */
  public static boolean isNullOrEmpty(Collection collection) {
    return ((collection == null) || (collection.size() == 0));
  }

  /**
   * Checks if a string is null or blank.
   *
   * @param value String value.
   * @return true/false.
   */
  private static boolean isNullOrBlank(String value) {
    return ((value == null)
      || (value.length() == 0)
      || (value.trim().length() == 0));
  }

  /**
   * Checks if an object is null.
   *
   * @param value Object value.
   * @return true/false.
   */
  private static boolean isNull(Object value) {
    return value == null;
  }

  /**
   * Checks if an object is not null.
   *
   * @param value Object value.
   * @return true/false.
   */
  public static boolean isNotNull(Object value) {
    return value != null;
  }
}
