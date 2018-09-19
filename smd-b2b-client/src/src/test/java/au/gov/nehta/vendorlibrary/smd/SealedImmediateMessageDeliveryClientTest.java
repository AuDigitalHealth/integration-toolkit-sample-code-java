package au.gov.nehta.vendorlibrary.smd;

import au.gov.nehta.common.utils.DomUtils;
import au.gov.nehta.vendorlibrary.common.security.KeystoreUtil;
import au.gov.nehta.vendorlibrary.common.security.SignedEncryptedContainerProfileUtil;
import au.gov.nehta.vendorlibrary.ws.TimeUtility;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.gov.nehta.xsp.XspException;
import au.net.electronichealth.ns.smd.els.types.V2010.ElsCertRefType;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
import java.util.*;

import au.net.electronichealth.ns.smd.els.types.V2010.InteractionType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.*;


public class SealedImmediateMessageDeliveryClientTest {
  private final String RESOURCES_DIR = "./src/test/resources/";

  private final String PRIVATE_KEY_STORE_TYPE = "JKS";
  private final String PRIVATE_KEY_STORE_FILE = RESOURCES_DIR + "keystore.jks";
  private final String PRIVATE_KEY_STORE_PASSWORD = "password";
  private final String PRIVATE_KEY_PASSWORD = "password";

  private final String TRUST_STORE_TYPE = "JKS";
  private final String TRUST_STORE_FILE = RESOURCES_DIR + "truststore.jks";
  private final String TRUST_STORE_PASSWORD = "password";

  private final String DRP_SIMD_SERVICE_ENDPOINT = "https://nehta-drp-iis.nehta.net.au/smd2010/SealedImmediateMessageDeliveryXsp/service.svc";

  private final String DRP_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS = "8003620000030499";
  private final String DRP_SENDER_TLS_CERTIFICATE_ALIAS = DRP_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS;
  private final String DRP_RECEIVER_PAYLOAD_ENCRYPTING_CERTIFICATE_ALIAS = "8003620000030556";

  // SealedMessageType values
  private final String TARGET_SERVICE_CATEGORY = "http://ns.electronichealth.net.au/etp/sc/prescriptionrequesting/1.1";
  private final String TARGET_SERVICE_INTERFACE = "http://ns.electronichealth.net.au/smd/intf/SealedMessageDelivery/TLS/2010";
  private final String SENDER_ORGANISATION = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003620000030499";
  private final String RECEIVER_ORGANISATION = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003620000030556";
  private final String SENDER_INDIVIDUAL = "http://ns.electronichealth.net.au/id/hi/hpii/1.0/8003610000010401";
  private final String RECEIVER_INDIVIDUAL = "http://ns.electronichealth.net.au/id/hi/hpii/1.0/8003610000010401";

  @Test
  @Ignore // SIMD not currently supported by DRP   
  public void testDeliverOnDrp() throws Exception {
    System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
    //System.setProperty("javax.net.debug", "ssl, handshake");
    System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");

    SSLSocketFactory sslSocketFactory = getDrpSslSocketFactory();

    MessageMetadataType metadata = getDefaultMessageMetadata();

    Element payload = getDefaultMessagePayload(DRP_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS, DRP_RECEIVER_PAYLOAD_ENCRYPTING_CERTIFICATE_ALIAS);

    SealedMessageType sealedMessage = new SealedMessageType();
    sealedMessage.setMetadata(metadata);
    sealedMessage.setEncryptedPayload(payload);

    SealedImmediateMessageDeliveryClient testClient = new SealedImmediateMessageDeliveryClient(sslSocketFactory);
    SealedMessageType responseMessage = testClient.deliver(sealedMessage, DRP_SIMD_SERVICE_ENDPOINT);

    Assert.assertNotNull(testClient.getLastSoapRequest());
    Assert.assertFalse(testClient.getLastSoapRequest().equals(LoggingHandler.EMPTY));
    Assert.assertTrue(testClient.getLastSoapRequest().length() > 0);

    Assert.assertNotNull(testClient.getLastSoapResponse());
    Assert.assertFalse(testClient.getLastSoapResponse().equals(LoggingHandler.EMPTY));
    Assert.assertTrue(testClient.getLastSoapResponse().length() > 0);

    Assert.assertNotNull(responseMessage);
  }

  @Test
  @Ignore // SIMD not currently supported by DRP
  public void testXmlStringDeliverOnDrp() throws Exception {
    MessageMetadataType metadata = getDefaultMessageMetadata();
    SealedMessageType sealedMessage = getSealedMessageForXmlStringPayload(metadata, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><text>hello world</text>");
    doDeliverOnDrp(sealedMessage);
  }


  @Test
  @Ignore // SIMD not currently supported by DRP
  public void testNonXmlStringDeliverOnDrp() throws Exception {
    MessageMetadataType metadata = getDefaultMessageMetadata();
    SealedMessageType sealedMessage = getSealedMessageForNonXmlStringPayload(metadata, "hello world");
    doDeliverOnDrp(sealedMessage);
  }

  @Test
  @Ignore // SIMD not currently supported by DRP
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

    SealedImmediateMessageDeliveryClient testClient = new SealedImmediateMessageDeliveryClient(sslSocketFactory);
    SealedMessageType deliverStatusType = testClient.deliver(sealedMessage, DRP_SIMD_SERVICE_ENDPOINT);

    Assert.assertNotNull(deliverStatusType);
    Assert.assertNotNull(deliverStatusType.getMetadata());
    Assert.assertNotNull(deliverStatusType.getEncryptedPayload());

    Assert.assertNotNull(testClient.getLastSoapRequest());
    Assert.assertFalse(testClient.getLastSoapRequest().equals(LoggingHandler.EMPTY));
    Assert.assertTrue(testClient.getLastSoapRequest().length() > 0);

    Assert.assertNotNull(testClient.getLastSoapResponse());
    Assert.assertFalse(testClient.getLastSoapResponse().equals(LoggingHandler.EMPTY));
    Assert.assertTrue(testClient.getLastSoapResponse().length() > 0);
  }

  @Test
  public void testNullLoggingHandler() throws Exception {
    SSLSocketFactory sslSocketFactory = getDrpSslSocketFactory();
    SealedImmediateMessageDeliveryClient testClient = new SealedImmediateMessageDeliveryClient(sslSocketFactory);

    ReflectionTestUtils.setField(testClient, "loggingHandler", null);

    String lastSoapRequest = testClient.getLastSoapRequest();
    Assert.assertTrue(lastSoapRequest.equals(LoggingHandler.EMPTY));

    String lastSoapResponse = testClient.getLastSoapResponse();
    Assert.assertTrue(lastSoapResponse.equals(LoggingHandler.EMPTY));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRouteRecordProvided() throws Exception {
    System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
    //System.setProperty("javax.net.debug", "ssl, handshake");
    System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");

    SSLSocketFactory sslSocketFactory = getDrpSslSocketFactory();

    MessageMetadataType metadata = getDefaultMessageMetadata();

    ElsCertRefType elsCertRef = TestConstants.getElsCertificateReference(TestConstants.TEST_CERTIFICATE_REFERENCE_USE_QUALIFIER, TestConstants.TEST_CERTIFICATE_REFERENCE_TYPE, TestConstants.TEST_CERTIFICATE_REFERENCE_VALUE);
    List<ElsCertRefType> certRefList = new ArrayList<ElsCertRefType>();
    certRefList.add(elsCertRef);
    InteractionType interaction = TestConstants.getInteraction(TestConstants.FAIRHILL_HPIO, TestConstants.FAIRHILL_NEW_SERVICE_CATEGORY, TestConstants.FAIRHILL_NEW_SERVICE_INTERFACE, TestConstants.FAIRHILL_NEW_SERVICE_ENDPOINT_URL, TestConstants.FAIRHILL_NEW_SERVICE_PROVIDER, certRefList);
    RouteRecordEntryType routeRecord = new RouteRecordEntryType();
    routeRecord.setInteraction(interaction);
    routeRecord.setSendIntermediateResponses(true);
    List<RouteRecordEntryType> routeRecords = new ArrayList<RouteRecordEntryType>();
    routeRecords.add(routeRecord);

    metadata.getRouteRecord().addAll(routeRecords);


    Element payload = getDefaultMessagePayload(DRP_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS, DRP_RECEIVER_PAYLOAD_ENCRYPTING_CERTIFICATE_ALIAS);

    SealedMessageType sealedMessage = new SealedMessageType();
    sealedMessage.setMetadata(metadata);
    sealedMessage.setEncryptedPayload(payload);

    SealedImmediateMessageDeliveryClient testClient = new SealedImmediateMessageDeliveryClient(sslSocketFactory);
    SealedMessageType responseMessage = testClient.deliver(sealedMessage, DRP_SIMD_SERVICE_ENDPOINT);

    Assert.assertNotNull(testClient.getLastSoapRequest());
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

    OtherTransportMetadataEntryType otherTransportMetadataEntryType = new OtherTransportMetadataEntryType();
    otherTransportMetadataEntryType.setMetadataType("TEST");
    otherTransportMetadataEntryType.setMetadataValue("VALUE");
    List<OtherTransportMetadataEntryType> otherTransportMetadataEntryTypeList = new ArrayList<OtherTransportMetadataEntryType>();
    otherTransportMetadataEntryTypeList.add(otherTransportMetadataEntryType);
    metadata.getOtherTransportMetadata().addAll(otherTransportMetadataEntryTypeList);

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
    X509Certificate receiverCert = (X509Certificate) trustStore.getCertificate(receiverPayloadEncryptingCertificateAlias);

    Document payloadDocument = SignedEncryptedContainerProfileUtil.getSignedEncryptedPayload("<?xml version=\"1.0\" encoding=\"UTF-8\"?><text>hello world</text>", certificatePairs, receiverCert);
    return payloadDocument.getDocumentElement();
  }



  private Element getDefaultMessagePayload() throws GeneralSecurityException, XspException {
    KeyStore senderKeyStore = KeystoreUtil.loadKeyStore(PRIVATE_KEY_STORE_TYPE, PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE);
    X509Certificate senderCert = (X509Certificate) senderKeyStore.getCertificate(TestConstants.SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);
    PrivateKey senderPrivateKey = KeystoreUtil.getSigningPrivateKey(senderKeyStore, PRIVATE_KEY_PASSWORD, TestConstants.SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);
    X500PrivateCredential privateCredential = new X500PrivateCredential(senderCert, senderPrivateKey);
    List<X500PrivateCredential> certificatePairs = new ArrayList<X500PrivateCredential>();
    certificatePairs.add(privateCredential);
    KeyStore trustStore = KeystoreUtil.loadKeyStore(TRUST_STORE_TYPE, TRUST_STORE_PASSWORD, TRUST_STORE_FILE);
    X509Certificate receiverCert = (X509Certificate) trustStore.getCertificate(TestConstants.SENDER_TLS_CERTIFICATE_ALIAS);

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
    X509Certificate receiverCert = (X509Certificate) trustStore.getCertificate(DRP_RECEIVER_PAYLOAD_ENCRYPTING_CERTIFICATE_ALIAS);

    SealedMessageType sealedMessage = SealedImmediateMessageDeliveryClient.getSealedMessage(unencryptedXmlPayload, metadata, certificatePairs, receiverCert);

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
    X509Certificate receiverCert = (X509Certificate) trustStore.getCertificate(DRP_RECEIVER_PAYLOAD_ENCRYPTING_CERTIFICATE_ALIAS);

    SealedMessageType sealedMessage = SealedImmediateMessageDeliveryClient.getSealedMessage(unencryptedNonXmlPayload.getBytes("UTF-16"), metadata, certificatePairs, receiverCert);

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
    X509Certificate receiverCert = (X509Certificate) trustStore.getCertificate(DRP_RECEIVER_PAYLOAD_ENCRYPTING_CERTIFICATE_ALIAS);

    SealedMessageType sealedMessage = SealedImmediateMessageDeliveryClient.getSealedMessage(unencryptedXmlPayload, metadata, certificatePairs, receiverCert);

    return sealedMessage;
  }
}
