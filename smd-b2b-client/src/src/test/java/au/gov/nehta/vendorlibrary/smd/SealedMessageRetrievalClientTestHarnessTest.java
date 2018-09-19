package au.gov.nehta.vendorlibrary.smd;

import au.gov.nehta.common.utils.DomUtils;
import au.gov.nehta.common.utils.JaxbUtils;
import au.gov.nehta.vendorlibrary.common.security.EncryptedContainerProfileUtil;
import au.gov.nehta.vendorlibrary.common.security.KeystoreUtil;
import au.gov.nehta.vendorlibrary.common.security.SignedContainerProfileUtil;
import au.gov.nehta.vendorlibrary.ws.TimeUtility;
import au.net.electronichealth.ns.smd.message.v2010.MessageType;
import au.net.electronichealth.ns.smd.xsd.transportresponse._2010.DeliveryResponseType;
import au.net.electronichealth.ns.smd.xsd.transportresponse._2010.ResponseClassType;
import au.net.electronichealth.ns.smd.xsd.transportresponse._2010.TransportResponseMetadataType;
import au.net.electronichealth.ns.smd.xsd.transportresponse._2010.TransportResponseType;
import au.net.electronichealth.ns.smr.tls.v2010.*;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.SealedMessageType;
import au.net.electronichealth.ns.trd.tls.v2010.DeliverStatusType;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.net.ssl.SSLSocketFactory;
import javax.security.auth.x500.X500PrivateCredential;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Ignore
public class SealedMessageRetrievalClientTestHarnessTest {
  private final String RESOURCES_DIR = "./src/test/resources/";

  private final String PRIVATE_KEY_STORE_TYPE = "JKS";
  private final String PRIVATE_KEY_STORE_FILE = RESOURCES_DIR + "keystore.jks";
  private final String PRIVATE_KEY_STORE_PASSWORD = "password";
  private final String PRIVATE_KEY_PASSWORD = "password";

  private final String TRUST_STORE_TYPE = "JKS";
  private final String TRUST_STORE_FILE = RESOURCES_DIR + "truststore.jks";
  private final String TRUST_STORE_PASSWORD = "password";

  private final String TEST_HARNESS_SMR_ENDPOINT = "https://th.nehta.secure-messaging.net:9191/smd/services/SealedMessageRetrieval";

  private final String TEST_HARNESS_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS = "8003620000002605";
  private final String TEST_HARNESS_SENDER_TLS_CERTIFICATE_ALIAS = TEST_HARNESS_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS;

  // SealedMessageType values
  private final String RECEIVER_ORGANISATION = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003620000002605";

  @Test
  public void testTestHarness_R02_00() throws Exception {
    System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
    //System.setProperty("javax.net.debug", "ssl, handshake");
    System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");

    SSLSocketFactory sslSocketFactory = getTestHarnessSslSocketFactory();

    SealedMessageRetrievalClient testClient = new SealedMessageRetrievalClient(sslSocketFactory);
    MessageListType messageListType = testClient.list(RECEIVER_ORGANISATION, true, 10, TEST_HARNESS_SMR_ENDPOINT);

    Assert.assertNotNull(messageListType);
    Assert.assertNotNull(messageListType.getRetrievalRecord());
    Assert.assertTrue(messageListType.getRetrievalRecord().size() > 0);
    Assert.assertNotNull(messageListType.getRetrievalRecord().get(0));
    Assert.assertNotNull(messageListType.getRetrievalRecord().get(0).getMetadata());
    Assert.assertNotNull(messageListType.getRetrievalRecord().get(0).getMetadata().getInvocationId());

    String invocationId = messageListType.getRetrievalRecord().get(0).getMetadata().getInvocationId();
    List<String> invocationIdentifiers = new ArrayList<String>();
    invocationIdentifiers.add(invocationId);

    List<SealedMessageType> sealedMessages = testClient.retrieve(RECEIVER_ORGANISATION, invocationIdentifiers, TEST_HARNESS_SMR_ENDPOINT);

    Assert.assertNotNull(sealedMessages);
    Assert.assertTrue(sealedMessages.size() > 0);

    SealedMessageType sealedMessage = sealedMessages.get(0);

    KeyStore receiverKeyStore = KeystoreUtil.loadKeyStore(PRIVATE_KEY_STORE_TYPE, PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE);
    X509Certificate senderCert = (X509Certificate) receiverKeyStore.getCertificate("8003620000002605");
    PrivateKey senderPrivateKey = KeystoreUtil.getSigningPrivateKey(receiverKeyStore, PRIVATE_KEY_PASSWORD, "8003620000002605");
    X500PrivateCredential privateCredential = new X500PrivateCredential(senderCert, senderPrivateKey);

    Document decryptedSignedPayload = testClient.getDecryptedSignedPayload(sealedMessages.get(0), privateCredential);
    Document payload = testClient.getPayload(decryptedSignedPayload);
    String base64PayloadString = DomUtils.serialiseToString(payload);
    MessageType messageType = JaxbUtils.unmarshal(payload, MessageType.class);

    boolean validSignature = testClient.verifyPayloadSignature(decryptedSignedPayload, SignedContainerProfileUtil.NULL_CERTIFICATE_VERIFIER);
    Assert.assertTrue(validSignature);


    TransportResponseDeliveryClient trdClient = new TransportResponseDeliveryClient(sslSocketFactory);
    List<TransportResponseType> transportResponses = new ArrayList<TransportResponseType>();
    DeliveryResponseType deliveryResponseType = new DeliveryResponseType();
    TransportResponseMetadataType transportResponseMetadataType = new TransportResponseMetadataType();
    transportResponseMetadataType.setInvocationId(sealedMessage.getMetadata().getInvocationId());
    transportResponseMetadataType.setResponseId("urn:uuid:" + UUID.randomUUID().toString());
    Calendar now = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
    now.setTime(new Date(System.currentTimeMillis()));
    transportResponseMetadataType.setTransportResponseTime(TimeUtility.nowXMLGregorianCalendar());
    transportResponseMetadataType.setReceiverOrganisation(sealedMessage.getMetadata().getReceiverOrganisation());
    transportResponseMetadataType.setSenderOrganisation(sealedMessage.getMetadata().getSenderOrganisation());
    transportResponseMetadataType.setSourceOrganisation(sealedMessage.getMetadata().getReceiverOrganisation());
    transportResponseMetadataType.setServiceCategory(sealedMessage.getMetadata().getServiceCategory());

    Document decryptedPayload = EncryptedContainerProfileUtil.getDecryptedPayload(sealedMessage.getEncryptedPayload().getOwnerDocument(), getPrivateCredential());
    List<byte[]> digests = SignedContainerProfileUtil.getDigestValue(decryptedPayload);

    byte[] digest = digests.get(0);

    deliveryResponseType.setDigestValue(digest);
    deliveryResponseType.setResponseClass(ResponseClassType.SUCCESS);
    deliveryResponseType.setResponseCode("SUCCESS");
    deliveryResponseType.setMessage("SUCCESS");

    TransportResponseType transportResponse = new TransportResponseType();
    transportResponse.setFinal(true);
    transportResponse.setDeliveryResponse(deliveryResponseType);
    transportResponse.setMetadata(transportResponseMetadataType);
    transportResponses.add(transportResponse);


    DeliverStatusType deliverStatusType = trdClient.deliver(transportResponses, sealedMessage.getMetadata().getRouteRecord().get(0).getInteraction().getServiceEndpoint());
    Assert.assertTrue(deliverStatusType == DeliverStatusType.OK);

    messageListType = testClient.list(RECEIVER_ORGANISATION, false, 10, TEST_HARNESS_SMR_ENDPOINT);

    Assert.assertTrue(messageListType.getRetrievalRecord().size() == 0);
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

  private X500PrivateCredential getPrivateCredential() throws GeneralSecurityException {
    KeyStore senderKeyStore = KeystoreUtil.loadKeyStore(PRIVATE_KEY_STORE_TYPE, PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE);
    X509Certificate senderCert = (X509Certificate) senderKeyStore.getCertificate(TEST_HARNESS_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);
    PrivateKey senderPrivateKey = KeystoreUtil.getSigningPrivateKey(senderKeyStore, PRIVATE_KEY_PASSWORD, TEST_HARNESS_SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);
    X500PrivateCredential privateCredential = new X500PrivateCredential(senderCert, senderPrivateKey);

    return privateCredential;
  }
}
