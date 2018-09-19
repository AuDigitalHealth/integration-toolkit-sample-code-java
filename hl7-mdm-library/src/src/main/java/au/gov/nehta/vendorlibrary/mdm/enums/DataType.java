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
package au.gov.nehta.vendorlibrary.mdm.enums;

/**
 * {@link Result}
 * Enumeration of data types.
 */
public enum DataType {

  /**
   * ZIP file.
   */
  ZIP("application", "zip");

  private final String dataType;
  private final String dataSubType;

  /**
   * Constructor used to create an {@link DataType} enum.
   *
   * @param dataType    Data primary type.
   * @param dataSubType Data sub type.
   */
  private DataType(String dataType, String dataSubType) {
    this.dataType = dataType;
    this.dataSubType = dataSubType;
  }

  /**
   * Retrieve data type.
   *
   * @return Data type string.
   */
  public String getDataType() {
    return dataType;
  }

  /**
   * Retrieve data sub type.
   *
   * @return Data subtype string.
   */
  public String getDataSubType() {
    return dataSubType;
  }
}
