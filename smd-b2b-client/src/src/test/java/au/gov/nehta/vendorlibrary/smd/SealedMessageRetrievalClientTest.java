package au.gov.nehta.vendorlibrary.smd;

import au.gov.nehta.common.utils.DomUtils;
import au.gov.nehta.vendorlibrary.common.security.KeystoreUtil;
import au.gov.nehta.vendorlibrary.common.security.SignedContainerProfileUtil;
import au.gov.nehta.vendorlibrary.ws.TimeUtility;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.net.electronichealth.ns.smd.els.types.V2010.ElsCertRefType;
import au.net.electronichealth.ns.smd.els.types.V2010.InteractionType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.OtherTransportMetadataEntryType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.RouteRecordEntryType;
import au.net.electronichealth.ns.smr.tls.v2010.*;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.SealedMessageType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.MessageMetadataType;
import au.net.electronichealth.ns.smd.tls.v2010.DeliverStatusType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.w3c.dom.Document;

import javax.net.ssl.SSLSocketFactory;
import javax.security.auth.x500.X500PrivateCredential;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.List;

import static au.gov.nehta.vendorlibrary.smd.TestConstants.setSystemVariablesForTest;

public class SealedMessageRetrievalClientTest {
  private final String RESOURCES_DIR = "./src/test/resources/";

  private final String PRIVATE_KEY_STORE_TYPE = "JKS";
  private final String PRIVATE_KEY_STORE_FILE = RESOURCES_DIR + "keystore.jks";
  private final String PRIVATE_KEY_STORE_PASSWORD = "password";
  private final String PRIVATE_KEY_PASSWORD = "password";

  private final String TRUST_STORE_TYPE = "JKS";
  private final String TRUST_STORE_FILE = RESOURCES_DIR + "truststore.jks";
  private final String TRUST_STORE_PASSWORD = "password";

  private final String DRP_SMR_SERVICE_ENDPOINT = "https://nehta-drp-iis.nehta.net.au/SecureMessaging/Services/Smd/SealedMessageRetrievalService.svc";
  private final String DRP_SMD_SERVICE_ENDPOINT = "https://nehta-drp-iis.nehta.net.au/SecureMessaging/Services/Smd/SealedMessageDeliveryService.svc";

  private final String DRP_RECEIVER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS = "8003628233352432";//"8003620000030499";
  private final String DRP_RECEIVER_TLS_CERTIFICATE_ALIAS = DRP_RECEIVER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS;

  // SealedMessageType values
  private final String RECEIVER_ORGANISATION = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003620000030499";

  // SMD Helper constants.
  private final String DRP_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS = DRP_RECEIVER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS;
  private final String DRP_SENDER_TLS_CERTIFICATE_ALIAS = DRP_RECEIVER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS;
  private final String RECEIVER_PAYLOAD_ENCRYPTING_CERTIFICATE_ALIAS = DRP_RECEIVER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS;

  private final String TRANSPORT_RESPONSE_SERVICE_CATEGORY = "http://ns.electronichealth.net.au/smd/sc/TransportResponseDelivery/2010";
  private final String TRANSPORT_RESPONSE_SERVICE_INTERFACE = "http://ns.electronichealth.net.au/smd/intf/TransportResponseDelivery/TLS/2010";
  private final String TRANSPORT_RESPONSE_SERVICE_ENDPOINT = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003630000000822";
  private final String TRANSPORT_RESPONSE_SERVICE_PROVIDER = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003630000000822";

  private final String TEST_CERTIFICATE_REFERENCE_USE_QUALIFIER = "testUseQualifier";
  private final String TEST_CERTIFICATE_REFERENCE_TYPE = "testType";
  private final String TEST_CERTIFICATE_REFERENCE_VALUE = "testValue";

  private final String TARGET_SERVICE_CATEGORY = "http://ns.electronichealth.net.au/etp/sc/prescriptionrequesting/1.1";
  private final String TARGET_SERVICE_INTERFACE = "http://ns.electronichealth.net.au/smd/intf/SealedMessageDelivery/TLS/2010";
  private final String SENDER_ORGANISATION = RECEIVER_ORGANISATION;

  private final String RECEIVER_INDIVIDUAL = "http://ns.electronichealth.net.au/id/hi/hpii/1.0/8003610000010401";
  private final String SENDER_INDIVIDUAL = RECEIVER_INDIVIDUAL;


  @Test
  public void testListAndRetrieveOnDrp() throws Exception {
    setSystemVariablesForTest();
    SSLSocketFactory sslSocketFactory = getDrpSslSocketFactory();

    MessageMetadataType metadata = getDefaultMessageMetadata();
    String invocationId = metadata.getInvocationId();
    String payload = "<text>hello world</text>";
    SealedMessageType sealedMessage = getSealedMessageForXmlStringPayload(metadata, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + payload);
    doDeliverOnDrp(sealedMessage);

    SealedMessageRetrievalClient testClient = new SealedMessageRetrievalClient(sslSocketFactory);

    MessageListType messageListType = testClient.list(RECEIVER_ORGANISATION, true, 10, DRP_SMR_SERVICE_ENDPOINT);

    Assert.assertTrue(messageListType != null);

    Assert.assertNotNull(testClient.getLastSoapRequest());
    Assert.assertFalse(testClient.getLastSoapRequest().equals(LoggingHandler.EMPTY));
    Assert.assertTrue(testClient.getLastSoapRequest().length() > 0);

    Assert.assertNotNull(testClient.getLastSoapResponse());
    Assert.assertFalse(testClient.getLastSoapResponse().equals(LoggingHandler.EMPTY));
    Assert.assertTrue(testClient.getLastSoapResponse().length() > 0);

    boolean foundMessage = false;
    for (RetrievalRecordEntryType entry : messageListType.getRetrievalRecord()) {
      if (entry.getMetadata().getInvocationId().equals(invocationId)) {
        foundMessage = true;
      }
    }

    Assert.assertTrue(foundMessage);

    List<String> invocationIdentifiers = new ArrayList<String>();
    invocationIdentifiers.add(invocationId);

    List<SealedMessageType> sealedMessages = testClient.retrieve(RECEIVER_ORGANISATION, invocationIdentifiers, DRP_SMR_SERVICE_ENDPOINT);

    Assert.assertNotNull(testClient.getLastSoapRequest());
    Assert.assertFalse(testClient.getLastSoapRequest().equals(LoggingHandler.EMPTY));
    Assert.assertTrue(testClient.getLastSoapRequest().length() > 0);

    Assert.assertNotNull(testClient.getLastSoapResponse());
    Assert.assertFalse(testClient.getLastSoapResponse().equals(LoggingHandler.EMPTY));
    Assert.assertTrue(testClient.getLastSoapResponse().length() > 0);

    Assert.assertNotNull(sealedMessages);
    Assert.assertTrue(sealedMessages.size() > 0);

    KeyStore receiverKeyStore = KeystoreUtil.loadKeyStore(PRIVATE_KEY_STORE_TYPE, PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE);
    X509Certificate senderCert = (X509Certificate) receiverKeyStore.getCertificate("8003628233352432");
    PrivateKey senderPrivateKey = KeystoreUtil.getSigningPrivateKey(receiverKeyStore, PRIVATE_KEY_PASSWORD, "8003628233352432");
    X500PrivateCredential privateCredential = new X500PrivateCredential(senderCert, senderPrivateKey);

    Document decryptedSignedPayload = testClient.getDecryptedSignedPayload(sealedMessages.get(0), privateCredential);
    boolean validSignature = testClient.verifyPayloadSignature(decryptedSignedPayload, SignedContainerProfileUtil.NULL_CERTIFICATE_VERIFIER);
    Assert.assertTrue(validSignature);

    Document retrievedPayload = SealedMessageRetrievalClient.getPayload(decryptedSignedPayload);
    String retrievedPayloadString = DomUtils.serialiseToString(retrievedPayload.getDocumentElement());
    Assert.assertTrue(retrievedPayloadString.equals(payload));
  }

  @Test
  public void testNullLoggingHandler() throws Exception {
    SSLSocketFactory sslSocketFactory = getDrpSslSocketFactory();
    SealedMessageRetrievalClient testClient = new SealedMessageRetrievalClient(sslSocketFactory);

    ReflectionTestUtils.setField(testClient, "loggingHandler", null);

    String lastSoapRequest = testClient.getLastSoapRequest();
    Assert.assertTrue(lastSoapRequest.equals(LoggingHandler.EMPTY));

    String lastSoapResponse = testClient.getLastSoapResponse();
    Assert.assertTrue(lastSoapResponse.equals(LoggingHandler.EMPTY));
  }

  // Helper methods
  private SSLSocketFactory getDrpSslSocketFactory() throws GeneralSecurityException, IOException {
    return getSslSocketFactory(DRP_RECEIVER_TLS_CERTIFICATE_ALIAS);
  }

  public void doDeliverOnDrp(SealedMessageType sealedMessage) throws Exception {
    System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
    //System.setProperty("javax.net.debug", "ssl, handshake");
    System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");

    SSLSocketFactory sslSocketFactory = getDrpSslSocketFactory();

    SealedMessageDeliveryClient testClient = new SealedMessageDeliveryClient(sslSocketFactory);
    DeliverStatusType deliverStatusType = testClient.deliver(sealedMessage, DRP_SMD_SERVICE_ENDPOINT);

    Assert.assertTrue(deliverStatusType == DeliverStatusType.OK);

    Assert.assertNotNull(testClient.getLastSoapRequest());
    Assert.assertFalse(testClient.getLastSoapRequest().equals(LoggingHandler.EMPTY));
    Assert.assertTrue(testClient.getLastSoapRequest().length() > 0);

    Assert.assertNotNull(testClient.getLastSoapResponse());
    Assert.assertFalse(testClient.getLastSoapResponse().equals(LoggingHandler.EMPTY));
    Assert.assertTrue(testClient.getLastSoapResponse().length() > 0);
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
    InteractionType interaction = TestConstants.getInteraction(RECEIVER_ORGANISATION, TRANSPORT_RESPONSE_SERVICE_CATEGORY, TRANSPORT_RESPONSE_SERVICE_INTERFACE, TRANSPORT_RESPONSE_SERVICE_ENDPOINT, TRANSPORT_RESPONSE_SERVICE_PROVIDER, certRefList);
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

  private SealedMessageType getSealedMessageForXmlStringPayload(MessageMetadataType metadata, String unencryptedXmlPayload) throws GeneralSecurityException, UnsupportedEncodingException, JAXBException {
    KeyStore senderKeyStore = KeystoreUtil.loadKeyStore(PRIVATE_KEY_STORE_TYPE, PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE);
    X509Certificate senderCert = (X509Certificate) senderKeyStore.getCertificate(DRP_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);
    PrivateKey senderPrivateKey = KeystoreUtil.getSigningPrivateKey(senderKeyStore, PRIVATE_KEY_PASSWORD, DRP_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);
    X500PrivateCredential privateCredential = new X500PrivateCredential(senderCert, senderPrivateKey);
    List<X500PrivateCredential> certificatePairs = new ArrayList<X500PrivateCredential>();
    certificatePairs.add(privateCredential);
    KeyStore trustStore = KeystoreUtil.loadKeyStore(TRUST_STORE_TYPE, TRUST_STORE_PASSWORD, TRUST_STORE_FILE);
    X509Certificate receiverCert = (X509Certificate) trustStore.getCertificate(RECEIVER_PAYLOAD_ENCRYPTING_CERTIFICATE_ALIAS);

    SealedMessageType sealedMessage = SealedMessageDeliveryClient.getSealedMessage(unencryptedXmlPayload, metadata, certificatePairs, receiverCert);

    return sealedMessage;
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
}
