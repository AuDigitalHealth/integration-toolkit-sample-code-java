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
 * The various date parse patterns supported in the context of CDA documents.
 */
public enum DateParsePatterns {

  /**
   * Year.
   */
  YEAR("yyyy", "yyyy"),

  /**
   * Year, month.
   */
  YEAR_MONTH("yyyyMM", "yyyyMM"),

  /**
   * Year, month, day.
   */
  YEAR_MONTH_DAY("yyyyMMdd", "yyyyMMdd"),

  /**
   * Year, month, day, hour.
   */
  YEAR_MONTH_DAY_HOUR("yyyyMMddHH", "yyyyMMddHH"),

  /**
   * Year, month, day, hour, minute.
   */
  YEAR_MONTH_DAY_HOUR_MINUTE("yyyyMMddHHmm", "yyyyMMddHHmm"),

  /**
   * Year, month, day, hour, minute, second.
   */
  YEAR_MONTH_DAY_HOUR_MINUTE_SECOND("yyyyMMddHHmmss", "yyyyMMddHHmmss"),

  /**
   * Year, month, day, hour, minute, second, millisecond.
   */
  YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_MILLISECOND("yyyyMMddHHmmss.SSSS", "yyyyMMddHHmmss'.'SSSS");

  /**
   * Match pattern label.
   */
  private final String matchPattern;

  /**
   * The actual value. This is required as pattern lengths are used and some escaped characters would
   * cause issues.
   */
  private final String patternValue;

  /**
   * Constructor.
   *
   * @param matchPattern match pattern label.
   * @param patternValue pattern value.
   */
  private DateParsePatterns(String matchPattern, String patternValue) {
    this.matchPattern = matchPattern;
    this.patternValue = patternValue;
  }

  /**
   * Retrieves the match pattern label.
   *
   * @return label.
   */
  public String getMatchPattern() {
    return matchPattern;
  }

  /**
   * Retrieves the match pattern.
   *
   * @return match pattern.
   */
  public String getPatternValue() {
    return patternValue;
  }

  /**
   * Get the match pattern label length.
   *
   * @return length.
   */
  public int getMatchPatternLength() {
    return matchPattern.length();
  }

  /**
   * Get the match pattern value length.
   *
   * @return length.
   */
  public int getPatternValueLength() {
    return patternValue.length();
  }

  /**
   * Retrieves a given pattern based on is label length.
   *
   * @param matchPattern pattern label.
   * @return {@link DateParsePatterns}
   */
  public static DateParsePatterns findByMatchPatternLength(final String matchPattern) {
    for (DateParsePatterns v : values()) {
      if (v.getMatchPatternLength() == matchPattern.length()) {
        return v;
      }
    }
    throw new IllegalArgumentException(String.format("'matchPattern' - %s does not match an expected match pattern length.", matchPattern.length()));
  }

  /**
   * Retrieves a given pattern based on its pattern value length.
   *
   * @param patternValue pattern value.
   * @return {@link DateParsePatterns}
   */
  public static DateParsePatterns findByPatternValueLength(final String patternValue) {
    for (DateParsePatterns v : values()) {
      if (v.getPatternValueLength() == patternValue.length()) {
        return v;
      }
    }
    throw new IllegalArgumentException(String.format("'patternValue' - %s does not match an expected pattern value length.", patternValue.length()));
  }

  /**
   * Retrieves a given pattern based on its label.
   *
   * @param matchPattern pattern label.
   * @return {@link DateParsePatterns}
   */
  public static DateParsePatterns findByMatchPattern(final String matchPattern) {
    for (DateParsePatterns v : values()) {
      if (v.getMatchPattern().compareTo(matchPattern) == 0) {
        return v;
      }
    }
    throw new IllegalArgumentException(String.format("'matchPattern' - %s does not match an expected match pattern.", matchPattern));
  }

  /**
   * Retrieves a given pattern based on its pattern value.
   *
   * @param patternValue pattern value.
   * @return {@link DateParsePatterns}
   */
  public static DateParsePatterns findByPatternValue(final String patternValue) {
    for (DateParsePatterns v : values()) {
      if (v.getPatternValue().compareTo(patternValue) == 0) {
        return v;
      }
    }
    throw new IllegalArgumentException(String.format("'patternValue' - %s does not match an expected pattern value.", patternValue));
  }
}
