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

import au.gov.nehta.vendorlibrary.common.security.SignedContainerProfileUtil;
import au.gov.nehta.vendorlibrary.ws.TimeUtility;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.net.electronichealth.ns.smd.xsd.transportresponse._2010.DeliveryResponseType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.MessageMetadataType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.SealedMessageType;
import au.net.electronichealth.ns.smd.xsd.transportresponse._2010.TransportResponseType;
import au.net.electronichealth.ns.trr.tls.v2010.RemoveResultType;
import au.net.electronichealth.ns.trr.tls.v2010.TransportResponseListType;
import au.net.electronichealth.ns.smd.xsd.transportresponse._2010.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.net.ssl.SSLSocketFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static au.gov.nehta.vendorlibrary.smd.TestConstants.*;
import static org.junit.Assert.*;

public class TransportResponseRetrievalClientTest {

  public static final String TRR_ENDPOINT = "https://nehta-drp-iis.nehta.net.au/SecureMessaging/Services/Smd/TransportResponseRetrievalService.svc";


  @Test
  public void testNullLoggingHandler() throws Exception {
    SSLSocketFactory sslSocketFactory = getDRPSslSocketFactory();
    TransportResponseRetrievalClient testClient = new TransportResponseRetrievalClient(sslSocketFactory);

    ReflectionTestUtils.setField(testClient, "loggingHandler", null);

    String lastSoapRequest = testClient.getLastSoapRequest();
    Assert.assertTrue(lastSoapRequest.equals(LoggingHandler.EMPTY));

    String lastSoapResponse = testClient.getLastSoapResponse();
    Assert.assertTrue(lastSoapResponse.equals(LoggingHandler.EMPTY));
  }


  @Test
  public void testTRRRetrieveBlankOrNullOrganisation() throws Exception {
    setSystemVariablesForTest();
    TransportResponseRetrievalClient trrClient = new TransportResponseRetrievalClient(getDRPSslSocketFactory());
    try {
      TransportResponseListType responseListType = trrClient.retrieve(EMPTY, false, 0, TRR_ENDPOINT);
    } catch (IllegalArgumentException ex) {
      Assert.assertEquals("organisation cannot be a blank string.", ex.getMessage());
    }
    try {
      TransportResponseListType responseListType = trrClient.retrieve(null, false, 0, TRR_ENDPOINT);
    } catch (IllegalArgumentException ex) {
      Assert.assertEquals("organisation cannot be null.", ex.getMessage());
    }
  }

  @Test
  public void testTRRRetrieveBlankEndpoint() throws Exception {
    setSystemVariablesForTest();
    TransportResponseRetrievalClient trrClient = new TransportResponseRetrievalClient(getDRPSslSocketFactory());
    try {
      trrClient.retrieve(RECEIVER_ORGANISATION, false, 0, EMPTY);
    } catch (IllegalArgumentException ex) {
      Assert.assertEquals("endpoint cannot be a blank string.", ex.getMessage());
    }

    try {
      trrClient.retrieve(RECEIVER_ORGANISATION, false, 0, null);
    } catch (IllegalArgumentException ex) {
      Assert.assertEquals("endpoint cannot be null.", ex.getMessage());
    }
  }


  @Test
  public void testTRRRetrieveAndRemoveOnDRP() throws Exception {

    setSystemVariablesForTest();
    //Deliver SMD message to retrieve
    String invocationId = "urn:uuid:" + UUID.randomUUID().toString();
    MessageMetadataType metadata = getSMDDefaultMessageMetadata();
    metadata.setInvocationId(invocationId); //Overwrite the invocation id

    Element payload = getDefaultMessagePayload();

    SealedMessageType sealedMessage = new SealedMessageType();
    sealedMessage.setMetadata(metadata);
    sealedMessage.setEncryptedPayload(payload);

    SealedMessageDeliveryClient smdClient = new SealedMessageDeliveryClient(getDRPSslSocketFactory());
    au.net.electronichealth.ns.smd.tls.v2010.DeliverStatusType smddeliverStatusType =
        smdClient.deliver(sealedMessage, DRP_SMD_SERVICE_ENDPOINT);

    //Perform TRD Retrieve
    TransportResponseDeliveryClient trdClient =
        new TransportResponseDeliveryClient(getDRPSslSocketFactory());
    List<TransportResponseType> transportResponseTypes = new ArrayList<TransportResponseType>();

    TransportResponseType responseType = new TransportResponseType();

    //Set the InvocationId Id to retrieve TRR
    TransportResponseMetadataType transportResponseMetadataType = getDefaultMessageMetadata();

    //Overwrite default values.
    transportResponseMetadataType.setResponseId(metadata.getInvocationId());
    transportResponseMetadataType.setReceiverOrganisation(SENDER_ORGANISATION);
    transportResponseMetadataType.setSourceOrganisation(SENDER_ORGANISATION);
    transportResponseMetadataType.setSenderOrganisation(RECEIVER_ORGANISATION);
    responseType.setMetadata(transportResponseMetadataType);


    DeliveryResponseType deliveryResponseType = new DeliveryResponseType();
    deliveryResponseType.setResponseCode("");
    deliveryResponseType.setMessage(SENSITIVE_DATA);
    Document signedDoc = SignedContainerProfileUtil.getSignedPayload(SENSITIVE_DATA, getDefaultSignatureCertificate());
    deliveryResponseType.setDigestValue(SignedContainerProfileUtil.getDigestValue(signedDoc).get(0));

    responseType.setDeliveryResponse(deliveryResponseType);
    transportResponseTypes.add(responseType);
    au.net.electronichealth.ns.trd.tls.v2010.DeliverStatusType deliverStatusType = trdClient.deliver(transportResponseTypes, TRD_ENDPOINT);
    assertDeliverStatusTypeOk(deliverStatusType);

    //Perform TRR Retrieve
    TransportResponseRetrievalClient trrClient = new TransportResponseRetrievalClient(getDRPSslSocketFactory());
    TransportResponseListType responseListType = trrClient.retrieve(SENDER_ORGANISATION , true, 10, TRR_ENDPOINT);
    assertTransportResponseListType(responseListType);
    verifySoapMessages(trrClient);


    //Perform TRR remove
    List<String> responseId = new ArrayList<String>();
    for (TransportResponseType listType : responseListType.getResponse()) {
      responseId.add(listType.getMetadata().getResponseId());
    }
    responseListType.getResponse().get(0).getMetadata().getResponseId();
    List<RemoveResultType> removeResultTypes = trrClient.remove(false,SENDER_ORGANISATION , responseId, TRR_ENDPOINT);

  }




  @Test
  public void testTRRRemoveBlankResponseId() throws Exception {
    boolean pass = false;
    setSystemVariablesForTest();
    TransportResponseRetrievalClient trrClient = new TransportResponseRetrievalClient(getDRPSslSocketFactory());
    List<String> responseId = new ArrayList<String>();
    responseId.add("");
    try {
      trrClient.remove(false, RECEIVER_ORGANISATION, responseId, TRR_ENDPOINT);
    } catch (IllegalArgumentException ex) {
      pass = true;
      assertNotNull(ex);
      assertEquals("responseId cannot be a blank string.", ex.getMessage());
    }
    assertTrue(pass);
  }

  //Assert methods
  private void assertTransportResponseListType(TransportResponseListType responseListType) {
    assertNotNull(responseListType);
    assertNotNull(responseListType.getTotalNumberAvailable());
  }

  private void assertDeliverStatusTypeOk(au.net.electronichealth.ns.trd.tls.v2010.DeliverStatusType deliverStatusType) {
    assertNotNull(deliverStatusType);
    assertNotNull(deliverStatusType.value());
    assertEquals(au.net.electronichealth.ns.trd.tls.v2010.DeliverStatusType.OK.toString(), deliverStatusType.value().toUpperCase());

  }

  private void verifySoapMessages(TransportResponseRetrievalClient testClient) {
    assertNotNull(testClient.getClass());
    assertNotNull(testClient.getLastSoapResponse());
    assertFalse(testClient.getLastSoapRequest().equals(EMPTY));
    assertFalse(testClient.getLastSoapResponse().equals(EMPTY));
  }

  private static TransportResponseMetadataType getDefaultMessageMetadata() {
    TransportResponseMetadataType metadata = new TransportResponseMetadataType();
    metadata.setTransportResponseTime(TimeUtility.nowXMLGregorianCalendar());
    metadata.setInvocationId("urn:uuid:" + UUID.randomUUID().toString());
    metadata.setReceiverOrganisation(RECEIVER_ORGANISATION);
    metadata.setSenderOrganisation(SENDER_ORGANISATION);
    metadata.setServiceCategory(TARGET_SERVICE_CATEGORY);
    return metadata;
  }

}
