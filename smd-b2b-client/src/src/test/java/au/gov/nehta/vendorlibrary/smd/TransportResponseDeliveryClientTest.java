/*
* Copyright 2011 NEHTA
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
package au.gov.nehta.vendorlibrary.smd;

import au.gov.nehta.vendorlibrary.common.security.EncryptedContainerProfileUtil;
import au.gov.nehta.vendorlibrary.common.security.SignedContainerProfileUtil;
import au.gov.nehta.vendorlibrary.ws.TimeUtility;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.MessageMetadataType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.OtherTransportMetadataEntryType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.SealedMessageType;
import au.net.electronichealth.ns.smd.xsd.transportresponse._2010.TransportResponseType;
import org.w3c.dom.Document;
import au.net.electronichealth.ns.smd.xsd.transportresponse._2010.*;
import au.net.electronichealth.ns.trd.tls.v2010.*;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.w3c.dom.Element;

import javax.net.ssl.SSLSocketFactory;
import javax.security.auth.x500.X500PrivateCredential;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static au.gov.nehta.vendorlibrary.smd.TestConstants.*;
import static au.gov.nehta.vendorlibrary.smd.TestConstants.getDefaultSignatureCertificate;
import static org.junit.Assert.*;

public class TransportResponseDeliveryClientTest {


  @Test
  public void testNullLoggingHandler() throws Exception {
    SSLSocketFactory sslSocketFactory = getDRPSslSocketFactory();
    TransportResponseDeliveryClient testClient = new TransportResponseDeliveryClient(sslSocketFactory);

    ReflectionTestUtils.setField(testClient, "loggingHandler", null);

    String lastSoapRequest = testClient.getLastSoapRequest();
    Assert.assertTrue(lastSoapRequest.equals(LoggingHandler.EMPTY));

    String lastSoapResponse = testClient.getLastSoapResponse();
    Assert.assertTrue(lastSoapResponse.equals(LoggingHandler.EMPTY));
  }

  @Test
  public void testTRDDeliverBlankOrganisation() throws Exception {
    setSystemVariablesForTest();
    try {
      TransportResponseDeliveryClient trdClient = new TransportResponseDeliveryClient(getDRPSslSocketFactory());
      DeliverStatusType deliverStatusType = trdClient.deliver(null, TRD_ENDPOINT);
    } catch (IllegalArgumentException ex) {
      Assert.assertEquals("transportResponseTypeList cannot be null.", ex.getMessage());
    }
  }

  @Test
  public void testTRDDeliverBlankOrNullEndpoint() throws Exception {
    setSystemVariablesForTest();
    TransportResponseDeliveryClient trdClient = new TransportResponseDeliveryClient(getDRPSslSocketFactory());
    try {
      DeliverStatusType deliverStatusType = trdClient.deliver(new ArrayList<TransportResponseType>(), EMPTY);
    } catch (IllegalArgumentException ex) {
      Assert.assertEquals("endpoint cannot be a blank string.", ex.getMessage());
    }

    try {
      DeliverStatusType deliverStatusType = trdClient.deliver(new ArrayList<TransportResponseType>(), null);
    } catch (IllegalArgumentException ex) {
      Assert.assertEquals("endpoint cannot be null.", ex.getMessage());
    }
  }

  @Test
  public void testTRDDeliverToDRP() throws Exception {
    setSystemVariablesForTest();
    TransportResponseDeliveryClient trdClient = new TransportResponseDeliveryClient(getDRPSslSocketFactory());
    List<TransportResponseType> transportResponseTypes = new ArrayList<TransportResponseType>();

    TransportResponseType responseType = new TransportResponseType();

    responseType.setMetadata(getDefaultMessageMetadata());
    DeliveryResponseType deliveryResponseType = new DeliveryResponseType();
    deliveryResponseType.setResponseCode("");
    deliveryResponseType.setMessage("");
    deliveryResponseType.setDigestValue("".getBytes());
    responseType.setDeliveryResponse(deliveryResponseType);
    transportResponseTypes.add(responseType);
    DeliverStatusType deliverStatusType = trdClient.deliver(transportResponseTypes, TRD_ENDPOINT);
    assertDeliverStatusTypeOk(deliverStatusType);
    verifySoapMessages(trdClient);
  }

  @Test
  public void testGetTransportResponseType() throws Exception {

    String invocationId = "urn:uuid:" + UUID.randomUUID().toString();
    String responseId = "urn:uuid:" + UUID.randomUUID().toString();
    MessageMetadataType metadata = getSMDDefaultMessageMetadata();
    metadata.setInvocationId(invocationId); //Overwrite the invocation id

    Element payload = getDefaultMessagePayload();

    SealedMessageType sealedMessage = new SealedMessageType();
    sealedMessage.setMetadata(metadata);
    sealedMessage.setEncryptedPayload(payload);

    X500PrivateCredential x500PrivateCredential = getDefaultSignatureCertificate().get(0);


    TransportResponseType responseType = new TransportResponseType();

    //Set the InvocationId Id to retrieve TRR
    TransportResponseMetadataType transportResponseMetadataType = getDefaultMessageMetadata();
    //Overwrite default values.
    transportResponseMetadataType.setInvocationId(metadata.getInvocationId());

    responseType.setMetadata(transportResponseMetadataType);

    DeliveryResponseType deliveryResponseType = new DeliveryResponseType();
    deliveryResponseType.setResponseCode(ResponseClassType.SUCCESS.toString());
    deliveryResponseType.setMessage(SENSITIVE_DATA);
    Document signedDoc = EncryptedContainerProfileUtil.getDecryptedPayload(
      sealedMessage.getEncryptedPayload().getOwnerDocument(),
      x500PrivateCredential);

    deliveryResponseType.setDigestValue(SignedContainerProfileUtil.getDigestValue(signedDoc).get(0));
    responseType.setDeliveryResponse(deliveryResponseType);

    TransportResponseDeliveryClient.getTransportResponseType(
      sealedMessage, ResponseClassType.SUCCESS, ResponseClassType.SUCCESS.toString(), SENSITIVE_DATA, TimeUtility.nowXMLGregorianCalendar(),
      responseId, true, RECEIVER_ORGANISATION, x500PrivateCredential);

    TransportResponseDeliveryClient.verifyTransportResponseType(
      responseType, sealedMessage, ResponseClassType.SUCCESS, SENDER_ORGANISATION,
      x500PrivateCredential, SENSITIVE_DATA, ResponseClassType.SUCCESS.toString());


  }

  @Test
  public void testVerifyTransportResponseType() throws Exception {
    String invocationId = "urn:uuid:" + UUID.randomUUID().toString();

    X500PrivateCredential x500PrivateCredential = getDefaultSignatureCertificate().get(0);
    SealedMessageType sealedMessageType = getDefaultSealedMessageType(invocationId);

    TransportResponseDeliveryClient.verifyTransportResponseType(
      getDefaultTransportResponseType(invocationId, sealedMessageType), sealedMessageType, ResponseClassType.SUCCESS, SENDER_ORGANISATION,
      x500PrivateCredential, SENSITIVE_DATA, ResponseClassType.SUCCESS.toString());

  }

  @Test
  public void testVerifyTransportResponseTypeIncorrectInvocationId() throws Exception {
    boolean pass = false;

    String invocationId = "urn:uuid:" + UUID.randomUUID().toString();
    String newInvocationId = "urn:uuid:" + UUID.randomUUID().toString();

    X500PrivateCredential x500PrivateCredential = getDefaultSignatureCertificate().get(0);
    SealedMessageType sealedMessageType = getDefaultSealedMessageType(invocationId);

    try {
      TransportResponseDeliveryClient.verifyTransportResponseType(
        getDefaultTransportResponseType(newInvocationId, sealedMessageType), sealedMessageType, ResponseClassType.SUCCESS, SENDER_ORGANISATION,
        x500PrivateCredential, SENSITIVE_DATA, ResponseClassType.SUCCESS.toString());
    } catch (IllegalArgumentException ex) {
      pass = true;
      assertEquals("transport response metadata InvocationId must match sealed message metadata InvocationId. " +
        "Found " + newInvocationId + " instead of " + invocationId, ex.getMessage());
    }
    assertTrue(pass);
  }

  @Test
  public void testVerifyTransportResponseTypeIncorrectReceiverOrg() throws Exception {
    boolean pass = false;
    String invocationId = "urn:uuid:" + UUID.randomUUID().toString();
    X500PrivateCredential x500PrivateCredential = getDefaultSignatureCertificate().get(0);
    SealedMessageType sealedMessageType = getDefaultSealedMessageType(invocationId);
    TransportResponseType transportResponseType = getDefaultTransportResponseType(invocationId, sealedMessageType);
    transportResponseType.getMetadata().setReceiverOrganisation(SENDER_ORGANISATION);

    try {
      TransportResponseDeliveryClient.verifyTransportResponseType(
        transportResponseType, sealedMessageType, ResponseClassType.SUCCESS, SENDER_ORGANISATION,
        x500PrivateCredential, SENSITIVE_DATA, ResponseClassType.SUCCESS.toString());
    } catch (IllegalArgumentException ex) {
      pass = true;
      assertEquals("transport response metadata ReceiverOrganisation must match sealed message metadata "
        + "ReceiverOrganisation. Found " + SENDER_ORGANISATION + " instead of " +
        RECEIVER_ORGANISATION, ex.getMessage())
      ;
    }
    assertTrue(pass);
  }

  @Test
  public void testVerifyTransportResponseTypeIncorrectSenderOrg() throws Exception {
    boolean pass = false;
    String invocationId = "urn:uuid:" + UUID.randomUUID().toString();
    X500PrivateCredential x500PrivateCredential = getDefaultSignatureCertificate().get(0);
    SealedMessageType sealedMessageType = getDefaultSealedMessageType(invocationId);
    TransportResponseType transportResponseType = getDefaultTransportResponseType(invocationId, sealedMessageType);
    transportResponseType.getMetadata().setSenderOrganisation(RECEIVER_ORGANISATION);

    try {
      TransportResponseDeliveryClient.verifyTransportResponseType(
        transportResponseType, sealedMessageType, ResponseClassType.SUCCESS, SENDER_ORGANISATION,
        x500PrivateCredential, SENSITIVE_DATA, ResponseClassType.SUCCESS.toString());
    } catch (IllegalArgumentException ex) {
      pass = true;
      assertEquals("transport response metadata SenderOrganisation must match sealed message metadata "
        + "SenderOrganisation. Found " + RECEIVER_ORGANISATION + " instead of " +
        SENDER_ORGANISATION, ex.getMessage())
      ;
    }
    assertTrue(pass);
  }

  @Test
  public void testVerifyTransportResponseTypeIncorrectServiceCategory() throws Exception {
    boolean pass = false;
    String invocationId = "urn:uuid:" + UUID.randomUUID().toString();
    X500PrivateCredential x500PrivateCredential = getDefaultSignatureCertificate().get(0);
    SealedMessageType sealedMessageType = getDefaultSealedMessageType(invocationId);
    TransportResponseType transportResponseType = getDefaultTransportResponseType(invocationId, sealedMessageType);
    transportResponseType.getMetadata().setServiceCategory(TARGET_SERVICE_INTERFACE);

    try {
      TransportResponseDeliveryClient.verifyTransportResponseType(
        transportResponseType, sealedMessageType, ResponseClassType.SUCCESS, SENDER_ORGANISATION,
        x500PrivateCredential, SENSITIVE_DATA, ResponseClassType.SUCCESS.toString());
    } catch (IllegalArgumentException ex) {
      pass = true;
      assertEquals("transport response metadata ServiceCategory must match sealed message metadata "
        + "ServiceCategory. Found " + TARGET_SERVICE_INTERFACE + " instead of " +
        TARGET_SERVICE_CATEGORY, ex.getMessage())
      ;
    }
    assertTrue(pass);
  }

  @Test
  public void testVerifyTransportResponseTypeIncorrectSourceOrg() throws Exception {
    boolean pass = false;
    String invocationId = "urn:uuid:" + UUID.randomUUID().toString();
    X500PrivateCredential x500PrivateCredential = getDefaultSignatureCertificate().get(0);
    SealedMessageType sealedMessageType = getDefaultSealedMessageType(invocationId);
    TransportResponseType transportResponseType = getDefaultTransportResponseType(invocationId, sealedMessageType);
    transportResponseType.getMetadata().setSourceOrganisation(RECEIVER_ORGANISATION);

    try {
      TransportResponseDeliveryClient.verifyTransportResponseType(
        transportResponseType, sealedMessageType, ResponseClassType.SUCCESS, SENDER_ORGANISATION,
        x500PrivateCredential, SENSITIVE_DATA, ResponseClassType.SUCCESS.toString());
    } catch (IllegalArgumentException ex) {
      pass = true;
      assertEquals("transport response metadata SourceOrganisation must match the SourceOrganisation. Found "
        + RECEIVER_ORGANISATION + " instead of " + SENDER_ORGANISATION, ex.getMessage())
      ;
    }
    assertTrue(pass);
  }

  @Test
  public void testVerifyTransportResponseTypeIncorrectDigest() throws Exception {
    boolean pass = false;
    String invocationId = "urn:uuid:" + UUID.randomUUID().toString();
    byte[] newDigest = new String("New Digest").getBytes();
    X500PrivateCredential x500PrivateCredential = getDefaultSignatureCertificate().get(0);
    SealedMessageType sealedMessageType = getDefaultSealedMessageType(invocationId);
    TransportResponseType transportResponseType = getDefaultTransportResponseType(invocationId, sealedMessageType);

    Document decryptedSignedPayload = EncryptedContainerProfileUtil.getDecryptedPayload(
      sealedMessageType.getEncryptedPayload().getOwnerDocument(), getDefaultSignatureCertificate().get(0)
    );
    byte[] sealedMessageDigestValue = SignedContainerProfileUtil.getDigestValue(decryptedSignedPayload).get(0);
    transportResponseType.getDeliveryResponse().setDigestValue(newDigest);

    try {
      TransportResponseDeliveryClient.verifyTransportResponseType(
        transportResponseType, sealedMessageType, ResponseClassType.SUCCESS, SENDER_ORGANISATION,
        x500PrivateCredential, SENSITIVE_DATA, ResponseClassType.SUCCESS.toString());
    } catch (IllegalArgumentException ex) {
      pass = true;
      assertEquals("delivery response digest value must match the sealed message digest value. Found "
        + new String(newDigest) + " instead of " + new String(sealedMessageDigestValue), ex.getMessage())
      ;
    }
    assertTrue(pass);
  }


  @Test
  public void testVerifyTransportResponseTypeIncorrectResponseMessage() throws Exception {
    boolean pass = false;
    String invocationId = "urn:uuid:" + UUID.randomUUID().toString();

    String newResponseMessage = ResponseClassType.ERROR.toString();
    X500PrivateCredential x500PrivateCredential = getDefaultSignatureCertificate().get(0);
    SealedMessageType sealedMessageType = getDefaultSealedMessageType(invocationId);
    TransportResponseType transportResponseType = getDefaultTransportResponseType(invocationId, sealedMessageType);
    transportResponseType.getDeliveryResponse().setMessage(newResponseMessage);

    try {
      TransportResponseDeliveryClient.verifyTransportResponseType(
        transportResponseType, sealedMessageType, ResponseClassType.SUCCESS, SENDER_ORGANISATION,
        x500PrivateCredential, SENSITIVE_DATA, ResponseClassType.SUCCESS.toString());
    } catch (IllegalArgumentException ex) {
      pass = true;
      assertEquals("delivery response message value must match the deliveryResponseMessage value. Found "
        + newResponseMessage + " instead of " + SENSITIVE_DATA, ex.getMessage())
      ;
    }
    assertTrue(pass);
  }

  @Test
  public void testVerifyTransportResponseTypeIncorrectResponseCode() throws Exception {
    boolean pass = false;
    String invocationId = "urn:uuid:" + UUID.randomUUID().toString();

    String newResponseCode = ResponseClassType.ERROR.toString();
    X500PrivateCredential x500PrivateCredential = getDefaultSignatureCertificate().get(0);
    SealedMessageType sealedMessageType = getDefaultSealedMessageType(invocationId);
    TransportResponseType transportResponseType = getDefaultTransportResponseType(invocationId, sealedMessageType);
    transportResponseType.getDeliveryResponse().setResponseCode(newResponseCode);

    try {
      TransportResponseDeliveryClient.verifyTransportResponseType(
        transportResponseType, sealedMessageType, ResponseClassType.SUCCESS, SENDER_ORGANISATION,
        x500PrivateCredential, SENSITIVE_DATA, ResponseClassType.SUCCESS.toString());
    } catch (IllegalArgumentException ex) {
      pass = true;
      assertEquals("delivery response code value must match the deliveryResponseCode value. Found "
        + newResponseCode + " instead of " + ResponseClassType.SUCCESS.toString(), ex.getMessage())
      ;
    }
    assertTrue(pass);
  }


  @Test
  public void testSMDDeliverAndTRDDeliverOnDRP() throws Exception {

    setSystemVariablesForTest();
    //Deliver SMD message to retrieve
    String invocationId = "urn:uuid:" + UUID.randomUUID().toString();

    SealedMessageDeliveryClient smdClient = new SealedMessageDeliveryClient(getDRPSslSocketFactory());
    au.net.electronichealth.ns.smd.tls.v2010.DeliverStatusType smddeliverStatusType =
      smdClient.deliver(getDefaultSealedMessageType(invocationId), DRP_SMD_SERVICE_ENDPOINT);

    //Perform TRD Retrieve
    TransportResponseDeliveryClient trdClient = new TransportResponseDeliveryClient(getDRPSslSocketFactory());
    List<TransportResponseType> transportResponseTypes = new ArrayList<TransportResponseType>();

    TransportResponseType responseType = new TransportResponseType();

    //Set the InvocationId Id to retrieve TRR
    TransportResponseMetadataType transportResponseMetadataType = getDefaultMessageMetadata();
    //Overwrite default values.
    transportResponseMetadataType.setResponseId(invocationId);
    transportResponseMetadataType.setReceiverOrganisation(SENDER_ORGANISATION);
    transportResponseMetadataType.setSenderOrganisation(RECEIVER_ORGANISATION);
    responseType.setMetadata(transportResponseMetadataType);

    DeliveryResponseType deliveryResponseType = new DeliveryResponseType();
    deliveryResponseType.setResponseCode("");
    deliveryResponseType.setMessage(SENSITIVE_DATA);
    Document signedDoc = SignedContainerProfileUtil.getSignedPayload(SENSITIVE_DATA, getDefaultSignatureCertificate());
    deliveryResponseType.setDigestValue(SignedContainerProfileUtil.getDigestValue(signedDoc).get(0));

    responseType.setDeliveryResponse(deliveryResponseType);
    transportResponseTypes.add(responseType);
    DeliverStatusType deliverStatusType = trdClient.deliver(transportResponseTypes, TRD_ENDPOINT);
    assertDeliverStatusTypeOk(deliverStatusType);
    verifySoapMessages(trdClient);
  }

  @Test
  public void testSMDDeliverAndTRDDeliverWithOtherTransportMetadataOnDRP() throws Exception {

    setSystemVariablesForTest();
    //Deliver SMD message to retrieve
    String invocationId = "urn:uuid:" + UUID.randomUUID().toString();
    MessageMetadataType metadata = getSMDDefaultMessageMetadata();
    metadata.setInvocationId(invocationId); //Overwrite Invocation Id

    Element payload = getDefaultMessagePayload();

    SealedMessageType sealedMessage = new SealedMessageType();
    sealedMessage.setMetadata(metadata);
    sealedMessage.setEncryptedPayload(payload);

    SealedMessageDeliveryClient smdClient = new SealedMessageDeliveryClient(getDRPSslSocketFactory());
    au.net.electronichealth.ns.smd.tls.v2010.DeliverStatusType smddeliverStatusType =
      smdClient.deliver(sealedMessage, DRP_SMD_SERVICE_ENDPOINT);

    //Perform TRD Retrieve
    TransportResponseDeliveryClient trdClient = new TransportResponseDeliveryClient(getDRPSslSocketFactory());
    List<TransportResponseType> transportResponseTypes = new ArrayList<TransportResponseType>();

    transportResponseTypes.add(getDefaultTransportResponseType(invocationId, sealedMessage));
    DeliverStatusType deliverStatusType = trdClient.deliver(transportResponseTypes, TRD_ENDPOINT);
    assertDeliverStatusTypeOk(deliverStatusType);
    verifySoapMessages(trdClient);
  }


  private void assertDeliverStatusTypeOk(DeliverStatusType deliverStatusType) {
    assertNotNull(deliverStatusType);
    assertNotNull(deliverStatusType.value());
    assertEquals(DeliverStatusType.OK.toString().toLowerCase(), deliverStatusType.value().toLowerCase());
  }


  private void verifySoapMessages(TransportResponseDeliveryClient testClient) {
    assertNotNull(testClient.getClass());
    assertNotNull(testClient.getLastSoapResponse());
    assertFalse(testClient.getLastSoapRequest().equals(EMPTY));
    assertFalse(testClient.getLastSoapResponse().equals(EMPTY));
  }

  //Helper method
  private static TransportResponseMetadataType getDefaultMessageMetadata() {
    TransportResponseMetadataType metadata = new TransportResponseMetadataType();
    metadata.setTransportResponseTime(TimeUtility.nowXMLGregorianCalendar());
    metadata.setInvocationId("urn:uuid:" + UUID.randomUUID().toString());
    metadata.setResponseId(metadata.getInvocationId());
    metadata.setReceiverOrganisation(RECEIVER_ORGANISATION);
    metadata.setResponseId(metadata.getInvocationId());
    metadata.setSenderOrganisation(SENDER_ORGANISATION);
    metadata.setSourceOrganisation(SENDER_ORGANISATION);
    metadata.setServiceCategory(TARGET_SERVICE_CATEGORY);
    return metadata;
  }

  private static SealedMessageType getDefaultSealedMessageType(final String invocationId) throws Exception {
    SealedMessageType sealedMessageType = new SealedMessageType();
    MessageMetadataType metadata = getSMDDefaultMessageMetadata();
    metadata.setInvocationId(invocationId); //Overwrite Invocation Id
    Element payload = getDefaultMessagePayload();
    sealedMessageType.setMetadata(metadata);
    sealedMessageType.setEncryptedPayload(payload);
    return sealedMessageType;
  }

  private static TransportResponseType getDefaultTransportResponseType(
    final String invocationId, final SealedMessageType sealedMessageType) throws Exception {
    TransportResponseType transportResponseType = new TransportResponseType();

    //Set the InvocationId Id to retrieve TRR
    TransportResponseMetadataType transportResponseMetadataType = getDefaultMessageMetadata();
    //Overwrite default values.
    transportResponseMetadataType.setInvocationId(invocationId);
    transportResponseType.setMetadata(transportResponseMetadataType);
    List<OtherTransportMetadataEntryType> otherTransportMetadataEntryTypes = new ArrayList<OtherTransportMetadataEntryType>();
    OtherTransportMetadataEntryType otherTransportMetadataEntryType = new OtherTransportMetadataEntryType();
    otherTransportMetadataEntryType.setMetadataType(OtherTransportMetadataEntryType.class.getName());
    otherTransportMetadataEntryType.setMetadataValue(new OtherTransportMetadataEntryType());

    otherTransportMetadataEntryTypes.add(otherTransportMetadataEntryType);
    transportResponseMetadataType.getOtherTransportMetadata().addAll(otherTransportMetadataEntryTypes);

    DeliveryResponseType deliveryResponseType = new DeliveryResponseType();
    deliveryResponseType.setResponseCode(ResponseClassType.SUCCESS.toString());
    deliveryResponseType.setMessage(SENSITIVE_DATA);

    Document signedDoc = SignedContainerProfileUtil.getSignedPayload(SENSITIVE_DATA, getDefaultSignatureCertificate());

    Document decryptedSignedPayload = EncryptedContainerProfileUtil.getDecryptedPayload(
      sealedMessageType.getEncryptedPayload().getOwnerDocument(), getDefaultSignatureCertificate().get(0)
    );
    List<byte[]> digests = SignedContainerProfileUtil.getDigestValue(decryptedSignedPayload);
    deliveryResponseType.setDigestValue(SignedContainerProfileUtil.getDigestValue(decryptedSignedPayload).get(0));

    transportResponseType.setDeliveryResponse(deliveryResponseType);
    return transportResponseType;


  }

}
