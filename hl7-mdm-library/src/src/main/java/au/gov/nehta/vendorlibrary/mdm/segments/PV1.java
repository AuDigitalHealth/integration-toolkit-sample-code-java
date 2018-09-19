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
package au.gov.nehta.vendorlibrary.mdm.segments;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.vendorlibrary.mdm.datatypes.XCN;
import au.gov.nehta.vendorlibrary.mdm.enums.PatientClass;
import au.gov.nehta.vendorlibrary.mdm.util.MDMConstants;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidation;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link PV1}
 * Encapsulates MDM patient visit.
 */
public final class PV1 {

  /**
   * PV1 segment name.
   */
  private static final String PV1_SEGMENT_NAME = "PV1";

  /**
   * Expected segment field count.
   */
  private static final int PV1_FIELD_COUNT = 10;

  /**
   * PV1.1 - Set ID.
   */
  private static final int SET_ID = 1;

  /**
   * PV1.2 - Patient class.
   */
  private final PatientClass patientClass;

  /**
   * PV1.9 - Consulting doctor.
   */
  private final XCN consultingDoctor;

  /**
   * Private constructor to set {@link PV1} instance variables.
   *
   * @param builder {@link Builder} to construct PV1 from.
   */
  private PV1(Builder builder) {
    patientClass = builder.patientClass;
    consultingDoctor = builder.consultingDoctor;
  }

  /**
   * Retrieve set ID.
   *
   * @return Set ID integer.
   */
  public int getSetId() {
    return SET_ID;
  }

  /**
   * Retrieve patient class.
   *
   * @return {@link PatientClass}.
   */
  public PatientClass getPatientClass() {
    return patientClass;
  }

  /**
   * Retrieve consulting doctor.
   *
   * @return {@link XCN}.
   */
  public XCN getConsultingDoctor() {
    return consultingDoctor;
  }

  /**
   * Outputs a formatted {@link PV1} segment.
   *
   * @return Formatted string.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(PV1_SEGMENT_NAME);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(SET_ID);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(patientClass);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(MDMConstants.FIELD_SEPARATOR);
    sb.append(consultingDoctor);
    sb.append(MDMConstants.SEGMENT_TERMINATOR);
    return sb.toString();
  }

  /**
   * Parse {@link PV1} from string.
   *
   * @param parseValue String to be parsed (not null).
   * @return {@link PV1}
   */
  public static PV1 parse(String parseValue) {
    ArgumentUtils.checkNotNull(parseValue, "parseValue");

    // Split string in to components.
    List<String> fields = new ArrayList<String>(Arrays.asList(parseValue.split("\\" + MDMConstants.FIELD_SEPARATOR)));

    if (fields.size() >= PV1_FIELD_COUNT) {
      if (fields.get(0).compareTo(PV1_SEGMENT_NAME) != 0) {
        throw new IllegalArgumentException(String.format(
          "Unable to parse PV1 - Segment name is incorrect: \n\tActual Value: %s\n\tExpected Value: %s",
          fields.get(0),
          PV1_SEGMENT_NAME
        ));
      }
      try {
        return new Builder()
          .patientClass(PatientClass.valueOf(fields.get(2)))
          .consultingDoctor(XCN.parse(fields.get(9)))
          .build();
      } catch (MDMValidationException e) {
        throw new IllegalArgumentException("Unable to parse PV1", e);
      }
    } else {
      throw new IllegalArgumentException("Unable to parse PV1 - Invalid number of value components:\n\t"
        + "Actual: "
        + fields.size()
        + "\n\tExpected: "
        + PV1_FIELD_COUNT
      );
    }
  }

  /**
   * {@link Builder}
   * Build class used to construct and validate an {@link PV1} object.
   */
  public static final class Builder {

    private PatientClass patientClass;
    private XCN consultingDoctor;

    /**
     * Set patient class.
     *
     * @param value Patient class (not null).
     * @return {@link Builder}
     */
    public Builder patientClass(PatientClass value) {
      this.patientClass = value;
      return this;
    }

    /**
     * Set consulting doctor.
     *
     * @param value Consulting doctor (not null).
     * @return {@link Builder}
     */
    public Builder consultingDoctor(XCN value) {
      this.consultingDoctor = value;
      return this;
    }

    /**
     * Build {@link PV1} and validate necessary fields.
     *
     * @return {@link PV1}
     * @throws MDMValidationException Thrown when validation of the PV1 contents fails.
     */
    public PV1 build() throws MDMValidationException {

      Map<String, List<String>> failedVariables = new HashMap<String, List<String>>();

      failedVariables.putAll(MDMValidation.confirmNotNull("patientClass", patientClass, true));
      failedVariables.putAll(MDMValidation.confirmNotNull("consultingDoctor", consultingDoctor, true));

      // Confirm whether any variables have failed.
      if (!failedVariables.isEmpty()) {
        throw new MDMValidationException("Invalid PV1 segment", failedVariables);
      }

      return new PV1(this);
    }
  }
}
