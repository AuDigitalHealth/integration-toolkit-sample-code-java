package au.gov.nehta.vendorlibrary.smd;

import au.gov.nehta.vendorlibrary.common.security.KeystoreUtil;
import au.gov.nehta.vendorlibrary.common.security.SignedEncryptedContainerProfileUtil;
import au.gov.nehta.vendorlibrary.ws.TimeUtility;
import au.gov.nehta.xsp.XspException;
import au.net.electronichealth.ns.smd.els.types.V2010.InteractionType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.*;

import org.junit.Assert;
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

@Ignore
public class SealedImmediateMessageDeliveryClientTestHarnessTest {
  private final String RESOURCES_DIR = "./src/test/resources/";

  private final String PRIVATE_KEY_STORE_TYPE = "JKS";
  private final String PRIVATE_KEY_STORE_FILE = RESOURCES_DIR + "keystore.jks";
  private final String PRIVATE_KEY_STORE_PASSWORD = "password";
  private final String PRIVATE_KEY_PASSWORD = "password";

  private final String TRUST_STORE_TYPE = "JKS";
  private final String TRUST_STORE_FILE = RESOURCES_DIR + "truststore.jks";
  private final String TRUST_STORE_PASSWORD = "password";

  private final String TEST_HARNESS_SIMD_ENDPOINT = "https://th.nehta.secure-messaging.net:9191/smd/services/SealedImmediateMessageDelivery";

  private final String TEST_HARNESS_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS = "8003620000002605";
  private final String TEST_HARNESS_SENDER_TLS_CERTIFICATE_ALIAS = TEST_HARNESS_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS;
  private final String TEST_HARNESS_RECEIVER_PAYLOAD_ENCRYPTING_CERTIFICATE_ALIAS = "8003623456791937";


  // SealedMessageType values
  private final String TARGET_SERVICE_CATEGORY = "http://ns.electronichealth.net.au/etp/sc/prescriptionrequesting/1.1";
  private final String TARGET_SERVICE_INTERFACE = "http://ns.electronichealth.net.au/smd/intf/SealedMessageDelivery/TLS/2010";
  private final String SENDER_ORGANISATION = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003630000000004";
  private final String RECEIVER_ORGANISATION = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003620000030556";
  private final String SENDER_INDIVIDUAL = "http://ns.electronichealth.net.au/id/hi/hpii/1.0/8003610000010401";
  private final String RECEIVER_INDIVIDUAL = "http://ns.electronichealth.net.au/id/hi/hpii/1.0/8003610000010401";

  @Test
  public void testHarness_S06_00() throws Exception {
    System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
    //System.setProperty("javax.net.debug", "ssl, handshake");
    System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");

    SSLSocketFactory sslSocketFactory = getTestHarnessSslSocketFactory();

    MessageMetadataType metadata = getDefaultMessageMetadata();

    Element payload = getDefaultMessagePayload(TEST_HARNESS_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS, TEST_HARNESS_RECEIVER_PAYLOAD_ENCRYPTING_CERTIFICATE_ALIAS);

    SealedMessageType sealedMessage = new SealedMessageType();
    sealedMessage.setMetadata(metadata);
    sealedMessage.setEncryptedPayload(payload);

    SealedImmediateMessageDeliveryClient testClient = new SealedImmediateMessageDeliveryClient(sslSocketFactory);
    SealedMessageType responseMessage = testClient.deliver(sealedMessage, TEST_HARNESS_SIMD_ENDPOINT);

    Assert.assertNotNull(responseMessage);
  }

  // Helper methods
  private SSLSocketFactory getTestHarnessSslSocketFactory() throws GeneralSecurityException, IOException {
    return getSslSocketFactory(TEST_HARNESS_SENDER_TLS_CERTIFICATE_ALIAS);
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




}
