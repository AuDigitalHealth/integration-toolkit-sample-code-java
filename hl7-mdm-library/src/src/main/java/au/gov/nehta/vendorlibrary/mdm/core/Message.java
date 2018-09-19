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
package au.gov.nehta.vendorlibrary.mdm.core;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.vendorlibrary.mdm.segments.EVN;
import au.gov.nehta.vendorlibrary.mdm.segments.MSH;
import au.gov.nehta.vendorlibrary.mdm.segments.OBX;
import au.gov.nehta.vendorlibrary.mdm.segments.PID;
import au.gov.nehta.vendorlibrary.mdm.segments.PV1;
import au.gov.nehta.vendorlibrary.mdm.segments.TXA;
import au.gov.nehta.vendorlibrary.mdm.util.MDMConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@link Message}
 * Encapsulates MDM message components.
 */
public class Message {

  /**
   * Expected MDM message segment count.
   */
  private static final int SEGMENT_COUNT = 6;

  /**
   * MSH segment index.
   */
  private static final int MSH_INDEX = 0;

  /**
   * EVN segment index.
   */
  private static final int EVN_INDEX = 1;

  /**
   * PID segment index.
   */
  private static final int PID_INDEX = 2;

  /**
   * PV1 segment index.
   */
  private static final int PV1_INDEX = 3;

  /**
   * TXA segment index.
   */
  private static final int TXA_INDEX = 4;

  /**
   * OBX segment index.
   */
  private static final int OBX_INDEX = 5;

  /**
   * MSH (message header) segment.
   */
  private final MSH mshSegment;

  /**
   * EVN (event type) segment.
   */
  private final EVN evnSegment;

  /**
   * PID (patient identification) segment.
   */
  private final PID pidSegment;

  /**
   * PV1 (patient visit) segment.
   */
  private final PV1 pv1Segment;

  /**
   * TXA (document notification) segment.
   */
  private final TXA txaSegment;

  /**
   * OBX (observation) segment.
   */
  private final OBX obxSegment;

  /**
   * Default constructor.
   *
   * @param mshSegment Populated message header segment.
   * @param evnSegment Populated event type segment.
   * @param pidSegment Populated patient identification segment.
   * @param pv1Segment Populated patient visit segment.
   * @param txaSegment Populated document notification segment.
   * @param obxSegment Populated observation segment.
   */
  public Message(MSH mshSegment, EVN evnSegment, PID pidSegment, PV1 pv1Segment, TXA txaSegment, OBX obxSegment) {

    ArgumentUtils.checkNotNull(mshSegment, "mshSegment");
    ArgumentUtils.checkNotNull(evnSegment, "evnSegment");
    ArgumentUtils.checkNotNull(pidSegment, "pidSegment");
    ArgumentUtils.checkNotNull(pv1Segment, "pv1Segment");
    ArgumentUtils.checkNotNull(txaSegment, "txaSegment");
    ArgumentUtils.checkNotNull(obxSegment, "obxSegment");

    this.mshSegment = mshSegment;
    this.evnSegment = evnSegment;
    this.pidSegment = pidSegment;
    this.pv1Segment = pv1Segment;
    this.txaSegment = txaSegment;
    this.obxSegment = obxSegment;
  }

  /**
   * Retrieve message header segment.
   *
   * @return {@link MSH} object.
   */
  public final MSH getMshSegment() {
    return mshSegment;
  }

  /**
   * Retrieve event type segment.
   *
   * @return {@link EVN} object.
   */
  public final EVN getEvnSegment() {
    return evnSegment;
  }

  /**
   * Retrieve patient identification segment.
   *
   * @return {@link PID} object.
   */
  public final PID getPidSegment() {
    return pidSegment;
  }

  /**
   * Retrieve patient visit segment.
   *
   * @return {@link PV1} object.
   */
  public final PV1 getPv1Segment() {
    return pv1Segment;
  }

  /**
   * Retrieve document notification segment.
   *
   * @return {@link TXA} object.
   */
  public final TXA getTxaSegment() {
    return txaSegment;
  }

  /**
   * Retrieve observation segment.
   *
   * @return {@link OBX} object.
   */
  public final OBX getObxSegment() {
    return obxSegment;
  }

  /**
   * Outputs an MDM {@link Message} as valid HL7v2-formatted string.
   *
   * @return Formatted string.
   */
  @Override
  public final String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(mshSegment.toString());
    sb.append(evnSegment.toString());
    sb.append(pidSegment.toString());
    sb.append(pv1Segment.toString());
    sb.append(txaSegment.toString());
    sb.append(obxSegment.toString());
    return sb.toString();
  }

  /**
   * Parse MDM {@link Message} from string.
   *
   * @param parseValue String to be parsed (not null).
   * @return MDM {@link Message}.
   */
  public static Message parse(String parseValue) {
    ArgumentUtils.checkNotNullNorBlank(parseValue, "parseValue");

    // Split string in to components.
    List<String> segments = new ArrayList<String>(Arrays.asList(parseValue.split(MDMConstants.SEGMENT_TERMINATOR)));

    if (segments.size() == SEGMENT_COUNT) {
      return new Message(
        MSH.parse(segments.get(MSH_INDEX)),
        EVN.parse(segments.get(EVN_INDEX)),
        PID.parse(segments.get(PID_INDEX)),
        PV1.parse(segments.get(PV1_INDEX)),
        TXA.parse(segments.get(TXA_INDEX)),
        OBX.parse(segments.get(OBX_INDEX))
      );
    } else {
      throw new IllegalArgumentException("Unable to parse message - Invalid number of segments:\n\t"
        + "Actual: "
        + segments.size()
        + "\n\tExpected: "
        + SEGMENT_COUNT
      );
    }
  }
}
