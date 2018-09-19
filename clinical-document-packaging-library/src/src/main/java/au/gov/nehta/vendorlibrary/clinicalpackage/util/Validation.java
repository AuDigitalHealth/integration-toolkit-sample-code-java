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
package au.gov.nehta.vendorlibrary.clinicalpackage.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for validation of a package and its contents.
 */
public final class Validation {

  /**
   * Default private constructor to prevent instantiation of utility class.
   */
  private Validation() {
  }

  /**
   * Confirms a string is <em>not</em> null or blank.
   *
   * @param name  Name of the variable to be checked.
   * @param value String to be checked.
   * @return the validation failures (type {@link Map}) encountered or an empty map.
   */
  public static Map<String, List<String>> checkNotNullNorBlank(String name, String value) {
    Map<String, List<String>> result = new HashMap<String, List<String>>();
    List<String> messages = new ArrayList<String>();

    if (isNullOrBlank(value)) {
      messages.add(name + " cannot be null nor blank");
      result.put(name, messages);
    }

    return result;
  }

  /**
   * Confirms an object is <em>not</em> null.
   *
   * @param name  Name of the variable to be checked.
   * @param value Object to be checked.
   * @return the validation failures (type {@link Map}) encountered or an empty map.
   */
  public static Map<String, List<String>> checkNotNull(String name, Object value) {
    Map<String, List<String>> result = new HashMap<String, List<String>>();
    List<String> messages = new ArrayList<String>();

    if (isNull(value)) {
      messages.add(name + " cannot be null");
      result.put(name, messages);
    }

    return result;
  }

  /**
   * Confirms a {@link Collection} is <em>not</em> null <em>nor</em> empty null.
   *
   * @param name       Name of the variable to be checked.
   * @param collection Collection to be checked.
   * @return the validation failures (type {@link Map}) encountered or an empty map.
   */
  public static Map<String, List<String>> checkNotNullNorEmpty(String name, Collection collection) {
    Map<String, List<String>> result = new HashMap<String, List<String>>();
    List<String> messages = new ArrayList<String>();

    if (isNullOrEmpty(collection)) {
      messages.add(name + " collection cannot be null nor empty");
      result.put(name, messages);
    }

    return result;
  }

  /**
   * Confirms a URI only contains a filename, with no directories or additional path elements.
   *
   * @param name Name to be checked.
   * @param uri  URI to be checked.
   * @return the validation failures (type {@link Map}) encountered or an empty map.
   */
  public static Map<String, List<String>> checkFileNameOnlyUri(String name, String uri) {
    Map<String, List<String>> result = new HashMap<String, List<String>>();
    List<String> messages = new ArrayList<String>();

    if (uri.contains("/") || uri.contains("\\")) {
      messages.add(name + " is not valid. It contains path-specific characters.");
      messages.add("Expected format: <filename>.<file_extension>");
      messages.add("Actual value: " + uri);
      result.put(name, messages);
    }

    return result;
  }

  /**
   * Confirms a supplied string matches the expected string.
   *
   * @param name     Name of the actual variable to be checked.
   * @param actual   Supplied String to compare to the expected value.
   * @param expected Expected String value.
   * @return the validation failures (type {@link Map}) encountered or an empty map.
   */
  public static Map<String, List<String>> checkMatches(String name, String actual, String expected) {
    Map<String, List<String>> result = new HashMap<String, List<String>>();
    List<String> messages = new ArrayList<String>();

    if (actual.compareTo(expected) != 0) {
      messages.add(name + "is not valid");
      messages.add("Expected value: " + expected);
      messages.add("Actual value: " + actual);
      result.put(name, messages);
    }

    return result;
  }

  /**
   * Checks if a collection is null or empty.
   *
   * @param collection Collection to be checked.
   * @return <code>true</code> if the collection is null or empty (ie. size == 0); otherwise <code>false</code>.
   */
  public static boolean isNullOrEmpty(Collection collection) {
    return ((collection == null) || (collection.size() == 0));
  }

  /**
   * Checks if a string is null or blank.
   * 
   * This process includes trimming Strings for leadeing and trailing whitespace to confirm whether or not they are blank.
   *
   * @param value String to be checked.
   * @return <code>true</code> if the String is null, empty/blank; otherwise <code>false</code>.
   */
  public static boolean isNullOrBlank(String value) {
    return value == null || value.length() == 0 || value.trim().length() == 0;
  }

  /**
   * Checks if an object is null.
   *
   * @param value Object to be checked.
   * @return <code>true</code> if the object is null; otherwise <code>false</code>.
   */
  public static boolean isNull(Object value) {
    return value == null;
  }

  /**
   * Checks if an object is <em>not</em> null.
   *
   * @param value Object to be checked.
   * @return <code>true</code> if the object is <em>not</em> null; otherwise <code>false</code>.
   */
  public static boolean isNotNull(Object value) {
    return value != null;
  }
}
