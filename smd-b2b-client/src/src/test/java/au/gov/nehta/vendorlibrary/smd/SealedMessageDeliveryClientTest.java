package au.gov.nehta.vendorlibrary.smd;

import au.gov.nehta.common.utils.DomUtils;
import au.gov.nehta.vendorlibrary.common.security.KeystoreUtil;
import au.gov.nehta.vendorlibrary.common.security.SignedEncryptedContainerProfileUtil;
import au.gov.nehta.vendorlibrary.ws.TimeUtility;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.gov.nehta.xsp.XspException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import au.net.electronichealth.ns.smd.els.types.V2010.ElsCertRefType;
import au.net.electronichealth.ns.smd.els.types.V2010.InteractionType;
import au.net.electronichealth.ns.smd.tls.v2010.DeliverStatusType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.MessageMetadataType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.OtherTransportMetadataEntryType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.RouteRecordEntryType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.SealedMessageType;

import javax.net.ssl.SSLSocketFactory;
import javax.security.auth.x500.X500PrivateCredential;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.TransformerException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

public class SealedMessageDeliveryClientTest {
  private final String RESOURCES_DIR = "./src/test/resources/";

  private final String PRIVATE_KEY_STORE_TYPE = "JKS";
  private final String PRIVATE_KEY_STORE_FILE = RESOURCES_DIR + "keystore.jks";
  private final String PRIVATE_KEY_STORE_PASSWORD = "password";
  private final String PRIVATE_KEY_PASSWORD = "password";

  private final String TRUST_STORE_TYPE = "JKS";
  private final String TRUST_STORE_FILE = RESOURCES_DIR + "truststore.jks";
  private final String TRUST_STORE_PASSWORD = "password";

  private final String FAIRHILL_HPIO = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003630000000822";
  private final String FAIRHILL_NEW_SERVICE_CATEGORY = "http://ns.electronichealth.net.au/smd/sc/TransportResponseDelivery/2010";
  private final String FAIRHILL_NEW_SERVICE_INTERFACE = "http://ns.electronichealth.net.au/smd/intf/TransportResponseDelivery/TLS/2010";
  private final String FAIRHILL_NEW_SERVICE_ENDPOINT_URL = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003630000000822";
  private final String FAIRHILL_NEW_SERVICE_PROVIDER = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003630000000822";

  private final String TEST_CERTIFICATE_REFERENCE_USE_QUALIFIER = "testUseQualifier";
  private final String TEST_CERTIFICATE_REFERENCE_TYPE = "testType";
  private final String TEST_CERTIFICATE_REFERENCE_VALUE = "testValue";

  //private final String DRP_SMD_SERVICE_ENDPOINT = "https://nehta-drp-iis.nehta.net.au/smd2010/SealedMessageDeliveryXsp/service.svc";
  private final String DRP_SMD_SERVICE_ENDPOINT = "https://nehta-drp-iis.nehta.net.au/SecureMessaging/Services/Smd/SealedMessageDeliveryService.svc";
  private final String DRP_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS = "8003628233352432";
  private final String DRP_SENDER_TLS_CERTIFICATE_ALIAS = DRP_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS;
  private final String RECEIVER_PAYLOAD_ENCRYPTING_CERTIFICATE_ALIAS = "8003620000030499";

  // SealedMessageType values
  private final String TARGET_SERVICE_CATEGORY = "http://ns.electronichealth.net.au/etp/sc/prescriptionrequesting/1.1";
  private final String TARGET_SERVICE_INTERFACE = "http://ns.electronichealth.net.au/smd/intf/SealedMessageDelivery/TLS/2010";
  private final String SENDER_ORGANISATION = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003630000000004";
  private final String RECEIVER_ORGANISATION = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003620000030499";

  private final String SENDER_INDIVIDUAL = "http://ns.electronichealth.net.au/id/hi/hpii/1.0/8003610000010401";
  private final String RECEIVER_INDIVIDUAL = "http://ns.electronichealth.net.au/id/hi/hpii/1.0/8003610000010401";

  // Transport Response values
  /*
  private final String TRANSPORT_RESPONSE_SERVICE_CATEGORY = "http://ns.electronichealth.net.au/smd/sc/TransportResponseDelivery/2010";
  private final String TRANSPORT_RESPONSE_SERVICE_INTERFACE = "http://ns.electronichealth.net.au/smd/intf/TransportResponseDelivery/TLS/2010";
  private final String TRANSPORT_RESPONSE_SERVICE_PROVIDER = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003620000030499";
  private final String TRANSPORT_RESPONSE_SERVICE_ENDPOINT = "https://nehta-drp-iis.nehta.net.au/smd2010/SealedMessageDeliveryXsp/service.svc";
*/

  @Test
  public void testXmlStringDeliverOnDrp() throws Exception {
	  System.setProperty( "javax.net.debug","ssl,handshake" );
	  System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
    MessageMetadataType metadata = getDefaultMessageMetadata();
    SealedMessageType sealedMessage = getSealedMessageForXmlStringPayload(metadata, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><text>hello world</text>");
    doDeliverOnDrp(sealedMessage);
  }

  @Test
  public void testNonXmlStringDeliverOnDrp() throws Exception {
    MessageMetadataType metadata = getDefaultMessageMetadata();
    SealedMessageType sealedMessage = getSealedMessageForNonXmlStringPayload(metadata, "hello world");
    doDeliverOnDrp(sealedMessage);
  }

  @Test
  public void testXmlElementDeliverOnDrp() throws Exception {
    MessageMetadataType metadata = getDefaultMessageMetadata();
    Document xmlPayloadDocument = DomUtils.newDocument();
    Element root = xmlPayloadDocument.createElement("root");
    root.setAttribute("name", "TestElement");
    xmlPayloadDocument.appendChild(root);

    SealedMessageType sealedMessage = getSealedMessageForXmlElementPayload(metadata, xmlPayloadDocument.getDocumentElement());
    doDeliverOnDrp(sealedMessage);
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

  @Test (expected = IllegalArgumentException.class)
  public void testNoRouteRecord() throws Exception {
    SSLSocketFactory sslSocketFactory = getDrpSslSocketFactory();

    MessageMetadataType metadata = getDefaultMessageMetadata();
    metadata.getRouteRecord().clear();

    Element payload = getDefaultMessagePayload();

    SealedMessageType sealedMessage = new SealedMessageType();
    sealedMessage.setMetadata(metadata);
    sealedMessage.setEncryptedPayload(payload);

    SealedMessageDeliveryClient testClient = new SealedMessageDeliveryClient(sslSocketFactory);
    DeliverStatusType deliverStatusType = testClient.deliver(sealedMessage, DRP_SMD_SERVICE_ENDPOINT);
  }

  @Test
  public void testNullLoggingHandler() throws Exception {
    SSLSocketFactory sslSocketFactory = getDrpSslSocketFactory();
    SealedMessageDeliveryClient testClient = new SealedMessageDeliveryClient(sslSocketFactory);

    ReflectionTestUtils.setField(testClient, "loggingHandler", null);

    String lastSoapRequest = testClient.getLastSoapRequest();
    Assert.assertTrue(lastSoapRequest.equals(LoggingHandler.EMPTY));

    String lastSoapResponse = testClient.getLastSoapResponse();
    Assert.assertTrue(lastSoapResponse.equals(LoggingHandler.EMPTY));
  }

  // Helper methods
  private SSLSocketFactory getDrpSslSocketFactory() throws GeneralSecurityException, IOException {
    return getSslSocketFactory(DRP_SENDER_TLS_CERTIFICATE_ALIAS);
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
    InteractionType interaction = TestConstants.getInteraction(FAIRHILL_HPIO, FAIRHILL_NEW_SERVICE_CATEGORY, FAIRHILL_NEW_SERVICE_INTERFACE, FAIRHILL_NEW_SERVICE_ENDPOINT_URL, FAIRHILL_NEW_SERVICE_PROVIDER, certRefList);
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

  private Element getDefaultMessagePayload() throws GeneralSecurityException, XspException {
    KeyStore senderKeyStore = KeystoreUtil.loadKeyStore(PRIVATE_KEY_STORE_TYPE, PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE);
    X509Certificate senderCert = (X509Certificate) senderKeyStore.getCertificate(DRP_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);
    PrivateKey senderPrivateKey = KeystoreUtil.getSigningPrivateKey(senderKeyStore, PRIVATE_KEY_PASSWORD, DRP_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);
    X500PrivateCredential privateCredential = new X500PrivateCredential(senderCert, senderPrivateKey);
    List<X500PrivateCredential> certificatePairs = new ArrayList<X500PrivateCredential>();
    certificatePairs.add(privateCredential);
    KeyStore trustStore = KeystoreUtil.loadKeyStore(TRUST_STORE_TYPE, TRUST_STORE_PASSWORD, TRUST_STORE_FILE);
    X509Certificate receiverCert = (X509Certificate) trustStore.getCertificate(RECEIVER_PAYLOAD_ENCRYPTING_CERTIFICATE_ALIAS);

    Document payloadDocument = SignedEncryptedContainerProfileUtil.getSignedEncryptedPayload("<?xml version=\"1.0\" encoding=\"UTF-8\"?><text>hello world</text>", certificatePairs, receiverCert);
    return payloadDocument.getDocumentElement();
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

  private SealedMessageType getSealedMessageForNonXmlStringPayload(MessageMetadataType metadata, String unencryptedNonXmlPayload) throws GeneralSecurityException, IOException, JAXBException, TransformerException {
    KeyStore senderKeyStore = KeystoreUtil.loadKeyStore(PRIVATE_KEY_STORE_TYPE, PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE);
    X509Certificate senderCert = (X509Certificate) senderKeyStore.getCertificate(DRP_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);
    PrivateKey senderPrivateKey = KeystoreUtil.getSigningPrivateKey(senderKeyStore, PRIVATE_KEY_PASSWORD, DRP_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);
    X500PrivateCredential privateCredential = new X500PrivateCredential(senderCert, senderPrivateKey);
    List<X500PrivateCredential> certificatePairs = new ArrayList<X500PrivateCredential>();
    certificatePairs.add(privateCredential);
    KeyStore trustStore = KeystoreUtil.loadKeyStore(TRUST_STORE_TYPE, TRUST_STORE_PASSWORD, TRUST_STORE_FILE);
    X509Certificate receiverCert = (X509Certificate) trustStore.getCertificate(RECEIVER_PAYLOAD_ENCRYPTING_CERTIFICATE_ALIAS);

    SealedMessageType sealedMessage = SealedMessageDeliveryClient.getSealedMessage(unencryptedNonXmlPayload.getBytes("UTF-16"), metadata, certificatePairs, receiverCert);

    return sealedMessage;
  }

  private SealedMessageType getSealedMessageForXmlElementPayload(MessageMetadataType metadata, Element unencryptedXmlPayload) throws GeneralSecurityException, IOException, JAXBException, TransformerException {
    KeyStore senderKeyStore = KeystoreUtil.loadKeyStore(PRIVATE_KEY_STORE_TYPE, PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE);
    X509Certificate senderCert = (X509Certificate) senderKeyStore.getCertificate(DRP_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);
    PrivateKey senderPrivateKey = KeystoreUtil.getSigningPrivateKey(senderKeyStore, PRIVATE_KEY_PASSWORD, DRP_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);
    X500PrivateCredential privateCredential = new X500PrivateCredential(senderCert, senderPrivateKey);
    List<X500PrivateCredential> certificatePairs = new ArrayList<X500PrivateCredential>();
    certificatePairs.add(privateCredential);
    KeyStore trustStore = KeystoreUtil.loadKeyStore(TRUST_STORE_TYPE, TRUST_STORE_PASSWORD, TRUST_STORE_FILE);
    X509Certificate receiverCert = (X509Certificate) trustStore.getCertificate(RECEIVER_PAYLOAD_ENCRYPTING_CERTIFICATE_ALIAS);

    SealedMessageType sealedMessage = SealedMessageDeliveryClient.getSealedMessage(unencryptedXmlPayload, metadata,
      certificatePairs, receiverCert);

    return sealedMessage;
  }
}
