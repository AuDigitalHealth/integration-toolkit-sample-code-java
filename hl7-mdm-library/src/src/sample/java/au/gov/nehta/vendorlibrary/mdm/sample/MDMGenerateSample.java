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
package au.gov.nehta.vendorlibrary.mdm.sample;

import au.gov.nehta.vendorlibrary.mdm.core.Message;
import au.gov.nehta.vendorlibrary.mdm.datatypes.CE;
import au.gov.nehta.vendorlibrary.mdm.datatypes.CX;
import au.gov.nehta.vendorlibrary.mdm.datatypes.HD;
import au.gov.nehta.vendorlibrary.mdm.datatypes.XAD;
import au.gov.nehta.vendorlibrary.mdm.datatypes.XCN;
import au.gov.nehta.vendorlibrary.mdm.datatypes.XPN;
import au.gov.nehta.vendorlibrary.mdm.enums.CodingSystem;
import au.gov.nehta.vendorlibrary.mdm.enums.CountryCode;
import au.gov.nehta.vendorlibrary.mdm.enums.PatientClass;
import au.gov.nehta.vendorlibrary.mdm.enums.Sex;
import au.gov.nehta.vendorlibrary.mdm.segments.EVN;
import au.gov.nehta.vendorlibrary.mdm.segments.MSH;
import au.gov.nehta.vendorlibrary.mdm.segments.OBX;
import au.gov.nehta.vendorlibrary.mdm.segments.PID;
import au.gov.nehta.vendorlibrary.mdm.segments.PV1;
import au.gov.nehta.vendorlibrary.mdm.segments.TXA;
import au.gov.nehta.vendorlibrary.mdm.util.MDMUtil;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidationException;
import org.bouncycastle.util.encoders.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * {@link MDMGenerateSample}
 * Sample code for populating and generating an MDM message.
 */
public class MDMGenerateSample {

  public static void main(String[] args) throws MDMValidationException, IOException {

    // MDM message header segment.
    MSH msh = populateMsh();

    // MDM event type segment.
    EVN evn = populateEvn();

    // MDM patient identification segment.
    PID pid = populatePid();

    // MDM patient visit segment.
    PV1 pv1 = populatePv1();

    // MDM document notification segment.
    TXA txa = populateTxa();

    // MDM observation segment.
    OBX obx = populateObx();

    // MDM message message.
    Message message;

    // Populate MDM message.
    message = new Message(msh, evn, pid, pv1, txa, obx);

    // Generate MDM message string and write to sample file location.
    MDMUtil.writeMDMMessageToFile(message, "C://projects//tmp//MDM//mdmmessage.txt");
  }

  /**
   * Helper method to populate an MSH segment.
   *
   * @return MSH segment.
   * @throws MDMValidationException Thrown if an MSH component fails to build.
   */
  private static MSH populateMsh() throws MDMValidationException {

    HD sendingFacility = new HD.Builder()
      .namespace("8003615833334118")
      .identifier("1.2.36.1.2001.1003.0.8003615833334118")
      .identifierType("ISO")
      .build();

    HD receivingFacility = new HD.Builder()
      .namespace("800362000022222")
      .identifier("1.2.36.1.2001.1003.0.800362000022222")
      .identifierType("ISO")
      .build();

    return new MSH.Builder()
      .sendingApplication(new HD.Builder().namespace("Burrill Lake Medical Centre").build())
      .sendingFacility(sendingFacility)
      .receivingApplication(new HD.Builder().namespace("Foot Rehab Department").build())
      .receivingFacility(receivingFacility)
      .messageDateTime(new Date())
      .messageControlId("NEHTA_0000000001")
      .build();
  }

  /**
   * Helper method to populate a PID segment.
   *
   * @return PID segment.
   * @throws MDMValidationException Thrown if a PID component fails to build.
   */
  private static PID populatePid() throws MDMValidationException {

    List<CX> patientIdentifiers = new ArrayList<CX>();

    HD aushicHd = new HD.Builder()
      .namespace("AUSHIC")
      .build();

    CX patientId1 = new CX.Builder().identifier("8921319895").assigningAuthority(aushicHd).identifierTypeCode("MC").build();
    CX patientId2 = new CX.Builder().identifier("8003600000022222").assigningAuthority(aushicHd).identifierTypeCode("NI").build();

    patientIdentifiers.add(patientId1);
    patientIdentifiers.add(patientId2);

    XPN patientName = new XPN.Builder().familyName("Grant").givenName("Sally").prefix("Dr").build();

    XAD patientAddress = new XAD.Builder()
      .firstLine("1 Clinician Street")
      .city("Nehtaville")
      .state("QLD")
      .postCode(5555)
      .country(CountryCode.AUSTRALIA.getCode())
      .build();

    return new PID.Builder()
      .patientIdentifiers(patientIdentifiers)
      .dateTimeOfBirth(new Date())
      .patientName(patientName)
      .sex(Sex.A)
      .patientAddress(patientAddress)
      .build();
  }

  /**
   * Helper method to populate an EVN segment.
   *
   * @return EVN segment.
   * @throws MDMValidationException Thrown if an EVN component fails to build.
   */
  private static EVN populateEvn() throws MDMValidationException {
    return new EVN.Builder()
      .recordedDateTime(new Date())
      .build();
  }

  /**
   * Helper method to populate a PV1 segment.
   *
   * @return PV1 segment.
   * @throws MDMValidationException Thrown if a PV1 component fails to build.
   */
  private static PV1 populatePv1() throws MDMValidationException {

    HD aushicprHd = new HD.Builder().namespace("AUSHICPR").build();

    XCN consultingDoctor = new XCN.Builder()
      .identifier("800361xxxxxxxxxx")
      .familyName("Doctor")
      .givenName("Good")
      .prefix("Dr")
      .assigningAuthority(aushicprHd)
      .identifierTypeCode("NPI")
      .build();

    return new PV1.Builder()
      .patientClass(PatientClass.N)
      .consultingDoctor(consultingDoctor)
      .build();
  }

  /**
   * Helper method to populate a TXA segment.
   *
   * @return TXA segment.
   * @throws MDMValidationException Thrown if a TXA component fails to build.
   */
  private static TXA populateTxa() throws MDMValidationException {
    return new TXA.Builder()
      .uniqueDocumentNumber(UUID.fromString("449ee02f-6c06-4c6d-b7e9-2e093af45ed5"))
      .activityDateTime(new Date())
      .uniqueDocumentFileName("PACKAGE.ZIP")
      .build();
  }

  private static OBX populateObx() throws MDMValidationException, IOException {

    CE observationId = new CE.Builder()
      .identifier("18842-5")
      .text("Discharge Summarisation Note")
      .codingSystem(CodingSystem.LN)
      .build();

    // Populate this with your CDA package zip file content.
    String encodedData = new String(Base64.encode(readFile("C://projects//tmp//old//CDAPackageTest.zip")));

    return new OBX.Builder()
      .observationIdentifier(observationId)
      .encodedData(encodedData)
      .build();
  }

  /**
   * Helper method to read file.
   *
   * @param path Location of the file.
   * @return byte array.
   * @throws IOException           Unable to read file.
   * @throws FileNotFoundException Unable to located file.
   */
  private static byte[] readFile(final String path) throws IOException, FileNotFoundException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    InputStream is = new FileInputStream(path);
    byte[] buffer = new byte[2048];

    int count = 0;

    count = is.read(buffer);
    while (count != -1) {
      baos.write(buffer, 0, count);
      count = is.read(buffer);
    }
    return baos.toByteArray();
  }
}
