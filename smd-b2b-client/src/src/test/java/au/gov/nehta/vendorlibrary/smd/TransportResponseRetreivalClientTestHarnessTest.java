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

import au.gov.nehta.vendorlibrary.common.security.KeystoreUtil;
import au.gov.nehta.vendorlibrary.common.security.SignedEncryptedContainerProfileUtil;
import au.gov.nehta.vendorlibrary.ws.TimeUtility;
import au.gov.nehta.xsp.XspException;
import au.net.electronichealth.ns.smd.els.types.V2010.ElsCertRefType;
import au.net.electronichealth.ns.smd.els.types.V2010.InteractionType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.MessageMetadataType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.OtherTransportMetadataEntryType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.RouteRecordEntryType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.SealedMessageType;
import au.net.electronichealth.ns.smd.xsd.transportresponse._2010.TransportResponseType;
import au.net.electronichealth.ns.trr.tls.v2010.RemoveResultType;
import au.net.electronichealth.ns.trr.tls.v2010.TransportResponseListType;

import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.net.ssl.SSLSocketFactory;
import javax.security.auth.x500.X500PrivateCredential;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.*;

import static au.gov.nehta.vendorlibrary.smd.TestConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Ignore
public class TransportResponseRetreivalClientTestHarnessTest {


  public static final String SENDER_ORGANISATION = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003620000002605";

  private final String TEST_HARNESS_SMD_ENDPOINT = "https://th.nehta.secure-messaging.net:9191/smd/services/SealedMessageDelivery";
  private final String TEST_HARNESS_TRR_ENDPOINT = "https://th.nehta.secure-messaging.net:9191/smd/services/TransportResponseRetrieval";

  private final String TEST_HARNESS_SENDER_INTERMEDIARY_PAYLOAD_SIGNING_CERTIFICATE_ALIAS = "8003620000002605";
  private final String TEST_HARNESS_SENDER_INTERMEDIARY_TLS_CERTIFICATE_ALIAS = TEST_HARNESS_SENDER_INTERMEDIARY_PAYLOAD_SIGNING_CERTIFICATE_ALIAS;


  private final String TEST_HARNESS_RECEIVER_PAYLOAD_ENCRYPTING_CERTIFICATE_ALIAS = "8003623456791937";

  // Transport Response values
  private final String TRANSPORT_RESPONSE_SERVICE_CATEGORY = "http://ns.electronichealth.net.au/smd/sc/TransportResponseDelivery/2010";
  private final String TRANSPORT_RESPONSE_SERVICE_INTERFACE = "http://ns.electronichealth.net.au/smd/intf/TransportResponseDelivery/TLS/2010";
  private final String TRANSPORT_RESPONSE_SERVICE_PROVIDER = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003620000030499";
  private final String TRANSPORT_RESPONSE_SERVICE_ENDPOINT = "https://nehta-drp-iis.nehta.net.au/SecureMessaging/Services/Smd/TransportResponseDeliveryService.svc";


  /**
   * TS_SMD_S_02 - Send message and receive final transport response
   * IUT (sender)    :  Test Harness (Sender Intermediary)
   * -> Interaction 1:  TRR - retrieve
   * -> Step 1       :  SMD - deliver (msg)
   * -> Step 2       :  TRR - retrieve (Success)
   * -> Step 3       :  TRR - remove (Success)
   *
   * @throws Exception in an event of error/Exception
   */
  @Test
  public void testTestHarness_S02_00() throws Exception {

    setSystemVariablesForTest();

    SSLSocketFactory sslSocketFactory = getTestHarnessSslSocketFactory();
    //Inital setup : SMD artifacts
    MessageMetadataType metadata = getDefaultMessageMetadata();
    Element payload = getDefaultMessagePayload();
    SealedMessageType sealedMessage = new SealedMessageType();
    sealedMessage.setMetadata(metadata);
    sealedMessage.setEncryptedPayload(payload);


    //Interaction 1 - TRR - retrieve
    TransportResponseRetrievalClient trrClient = new TransportResponseRetrievalClient(getDRPSslSocketFactory());
    TransportResponseListType responseListType = trrClient.retrieve(SENDER_ORGANISATION, true, 10, TEST_HARNESS_TRR_ENDPOINT);
    assertTransportResponseListType(responseListType);


    //Step 1: SMD - deliver (msg)

    SealedMessageDeliveryClient testClient = new SealedMessageDeliveryClient(sslSocketFactory);
    au.net.electronichealth.ns.smd.tls.v2010.DeliverStatusType deliverStatusType =
        testClient.deliver(sealedMessage, TEST_HARNESS_SMD_ENDPOINT);
    assertNotNull(deliverStatusType);
    assertEquals(deliverStatusType.value().toUpperCase(), au.net.electronichealth.ns.smd.tls.v2010.DeliverStatusType.OK.toString());

    //Step 2: TRR - retrieve (Success)
    responseListType = trrClient.retrieve(SENDER_ORGANISATION, true, 10, TEST_HARNESS_TRR_ENDPOINT);
    assertTransportResponseListType(responseListType);


    //Step 3: TRR - remove (Success)
    List<String> responseId = new ArrayList<String>();
    for (TransportResponseType listType : responseListType.getResponse()) {
      responseId.add(listType.getMetadata().getResponseId());
    }
    responseListType.getResponse().get(0).getMetadata().getResponseId();
    List<RemoveResultType> removeResultTypes = trrClient.remove(false, SENDER_ORGANISATION, responseId, TEST_HARNESS_TRR_ENDPOINT);
    assertNotNull(removeResultTypes);
    assertNotNull(removeResultTypes.size() > 0);

  }

  // Helper methods

  private SSLSocketFactory getTestHarnessSslSocketFactory() throws GeneralSecurityException, IOException {
    return getSslSocketFactory(TEST_HARNESS_SENDER_INTERMEDIARY_TLS_CERTIFICATE_ALIAS);
  }

  private SSLSocketFactory getSslSocketFactory(String privateKeyCertificateAlias) throws GeneralSecurityException, IOException {
    return KeystoreUtil.getSslSocketFactory(PRIVATE_KEY_STORE_TYPE,
        PRIVATE_KEY_STORE_FILE,
        PRIVATE_KEY_STORE_PASSWORD,
        PRIVATE_KEY_PASSWORD,
        privateKeyCertificateAlias,
        TRUST_STORE_TYPE,
        TRUST_STORE_FILE,
        TRUST_STORE_PASSWORD);
  }

  private MessageMetadataType getDefaultMessageMetadata() {
    MessageMetadataType metadata = new MessageMetadataType();
    metadata.setInvocationId("urn:uuid:" + UUID.randomUUID().toString());
    metadata.setReceiverOrganisation(RECEIVER_ORGANISATION);
    metadata.setReceiverIndividual(RECEIVER_INDIVIDUAL);
    metadata.setSenderOrganisation(SENDER_ORGANISATION);
    metadata.setSenderIndividual(SENDER_INDIVIDUAL);
    metadata.setServiceCategory(TARGET_SERVICE_CATEGORY);
    metadata.setServiceInterface(TARGET_SERVICE_INTERFACE);

    Calendar now = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
    now.setTime(new Date(System.currentTimeMillis()));
    metadata.setCreationTime(TimeUtility.nowXMLGregorianCalendar());

    Calendar oneMonthFromNow = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
    oneMonthFromNow.add(Calendar.MONTH, 1);

     XMLGregorianCalendar expiryTime = TimeUtility.nowXMLGregorianCalendar();
    // Duration to increment the calendar by one day
    try {
      expiryTime.add(DatatypeFactory.newInstance().newDurationDayTime(true, 1, 0, 0, 0));
    } catch (DatatypeConfigurationException e) {
      e.printStackTrace();
    }
    metadata.setExpiryTime(expiryTime);

    ElsCertRefType elsCertRef = TestConstants.getElsCertificateReference(TEST_CERTIFICATE_REFERENCE_USE_QUALIFIER, TEST_CERTIFICATE_REFERENCE_TYPE, TEST_CERTIFICATE_REFERENCE_VALUE);
    List<ElsCertRefType> certRefList = new ArrayList<ElsCertRefType>();
    certRefList.add(elsCertRef);
    InteractionType interaction = TestConstants.getInteraction(SENDER_ORGANISATION, TRANSPORT_RESPONSE_SERVICE_CATEGORY, TRANSPORT_RESPONSE_SERVICE_INTERFACE, TRANSPORT_RESPONSE_SERVICE_ENDPOINT, TRANSPORT_RESPONSE_SERVICE_PROVIDER, certRefList);
    RouteRecordEntryType routeRecord = new RouteRecordEntryType();
    routeRecord.setInteraction(interaction);
    routeRecord.setSendIntermediateResponses(true);
    List<RouteRecordEntryType> routeRecords = new ArrayList<RouteRecordEntryType>();
    routeRecords.add(routeRecord);
    metadata.getRouteRecord().addAll(routeRecords);

    OtherTransportMetadataEntryType otherTransportMetadataEntryType = new OtherTransportMetadataEntryType();
    otherTransportMetadataEntryType.setMetadataType("TEST");
    otherTransportMetadataEntryType.setMetadataValue("VALUE");
    List<OtherTransportMetadataEntryType> otherTransportMetadataEntryTypes = new ArrayList<OtherTransportMetadataEntryType>();
    otherTransportMetadataEntryTypes.add(otherTransportMetadataEntryType);
    metadata.getOtherTransportMetadata().addAll(otherTransportMetadataEntryTypes);

    return metadata;
  }

  private Element getDefaultMessagePayload(String senderPayloadSigningCertificateAlias, String receiverPayloadEncryptingCertificateAlias) throws GeneralSecurityException, XspException {
    KeyStore senderKeyStore = KeystoreUtil.loadKeyStore(PRIVATE_KEY_STORE_TYPE, PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE);
    X509Certificate senderCert = (X509Certificate) senderKeyStore.getCertificate(senderPayloadSigningCertificateAlias);
    PrivateKey senderPrivateKey = KeystoreUtil.getSigningPrivateKey(senderKeyStore, PRIVATE_KEY_PASSWORD, senderPayloadSigningCertificateAlias);
    X500PrivateCredential privateCredential = new X500PrivateCredential(senderCert, senderPrivateKey);
    List<X500PrivateCredential> certificatePairs = new ArrayList<X500PrivateCredential>();
    certificatePairs.add(privateCredential);
    KeyStore trustStore = KeystoreUtil.loadKeyStore(TRUST_STORE_TYPE, TRUST_STORE_PASSWORD, TRUST_STORE_FILE);
    X509Certificate receiverCert = (X509Certificate) trustStore.getCertificate(
        receiverPayloadEncryptingCertificateAlias);

    Document payloadDocument = SignedEncryptedContainerProfileUtil.getSignedEncryptedPayload(
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?><text>hello world</text>", certificatePairs, receiverCert);
    return payloadDocument.getDocumentElement();
  }

  private X500PrivateCredential getPrivateCredential() throws GeneralSecurityException {
    KeyStore senderKeyStore = KeystoreUtil.loadKeyStore(PRIVATE_KEY_STORE_TYPE, PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE);
    X509Certificate senderCert = (X509Certificate) senderKeyStore.getCertificate(TEST_HARNESS_SENDER_INTERMEDIARY_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);
    PrivateKey senderPrivateKey = KeystoreUtil.getSigningPrivateKey(senderKeyStore, PRIVATE_KEY_PASSWORD, TEST_HARNESS_SENDER_INTERMEDIARY_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);
    X500PrivateCredential privateCredential = new X500PrivateCredential(senderCert, senderPrivateKey);

    return privateCredential;
  }

  private Element getDefaultMessagePayload() throws GeneralSecurityException, XspException {
    KeyStore senderKeyStore = KeystoreUtil.loadKeyStore(PRIVATE_KEY_STORE_TYPE, PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE);
    X509Certificate senderCert = (X509Certificate) senderKeyStore.getCertificate(TEST_HARNESS_SENDER_INTERMEDIARY_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);
    PrivateKey senderPrivateKey = KeystoreUtil.getSigningPrivateKey(senderKeyStore, PRIVATE_KEY_PASSWORD, TEST_HARNESS_SENDER_INTERMEDIARY_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);
    X500PrivateCredential privateCredential = new X500PrivateCredential(senderCert, senderPrivateKey);
    List<X500PrivateCredential> certificatePairs = new ArrayList<X500PrivateCredential>();
    certificatePairs.add(privateCredential);
    KeyStore trustStore = KeystoreUtil.loadKeyStore(TRUST_STORE_TYPE, TRUST_STORE_PASSWORD, TRUST_STORE_FILE);
    X509Certificate receiverCert = (X509Certificate) trustStore.getCertificate(TEST_HARNESS_RECEIVER_PAYLOAD_ENCRYPTING_CERTIFICATE_ALIAS);

    Document payloadDocument = SignedEncryptedContainerProfileUtil.getSignedEncryptedPayload("<?xml version=\"1.0\" encoding=\"UTF-8\"?><text>hello world</text>", certificatePairs, receiverCert);
    return payloadDocument.getDocumentElement();
  }

  //Assert methods

  private void assertTransportResponseListType(TransportResponseListType responseListType) {
    assertNotNull(responseListType);
    assertNotNull(responseListType.getTotalNumberAvailable());
  }

}
