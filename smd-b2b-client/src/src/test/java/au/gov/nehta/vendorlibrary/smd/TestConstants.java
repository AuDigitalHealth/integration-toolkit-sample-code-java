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
import au.gov.nehta.vendorlibrary.common.security.SignedContainerProfileUtil;
import au.gov.nehta.vendorlibrary.common.security.SignedEncryptedContainerProfileUtil;
import au.gov.nehta.vendorlibrary.ws.TimeUtility;
import au.gov.nehta.xsp.XspException;
import au.net.electronichealth.ns.smd.core.types.qualifiedcertref.V2010.QualifiedCertRefType;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import au.net.electronichealth.ns.smd.els.types.V2010.ElsCertRefType;
import au.net.electronichealth.ns.smd.els.types.V2010.InteractionType;
import au.net.electronichealth.ns.smd.tls.v2010.DeliverStatusType;
import au.net.electronichealth.ns.smd.tls.v2010.StandardErrorMsg;
import au.net.electronichealth.ns.smd.tls.v2010.DeliverErrorMsg;
import au.net.electronichealth.ns.smd.tls.v2010.SealedMessageDelivery;
import au.net.electronichealth.ns.smd.tls.v2010.SealedMessageDeliveryService;
import au.net.electronichealth.ns.smd.message.v2010.MessageType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.MessageMetadataType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.OtherTransportMetadataEntryType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.RouteRecordEntryType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.SealedMessageType;

import javax.net.ssl.SSLSocketFactory;
import javax.security.auth.x500.X500PrivateCredential;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Test Utils class which provide HI testing utility methods and constants.
 */
public class TestConstants {

 // public static final String DRP_SMD_SERVICE_ENDPOINT = "https://nehta-drp-iis.nehta.net.au/SMD2010/SealedMessageDeliveryXsp/service.svc";
 // public static final String TRD_ENDPOINT = "https://nehta-drp-iis.nehta.net.au/SMD2010/TransportResponseDeliveryXsp/Service.svc";
	
	 public static final String DRP_SMD_SERVICE_ENDPOINT = "https://nehta-drp-iis.nehta.net.au/SecureMessaging/Services/Smd/SealedMessageDeliveryService.svc";
  public static final String TRD_ENDPOINT ="https://nehta-drp-iis.nehta.net.au/SecureMessaging/Services/Smd/TransportResponseDeliveryService.svc";
  
  
  
  public static final String SENSITIVE_DATA = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><text>hello world</text>";


  public static final String EMPTY = "";

  public static final String RESOURCES_DIR = "./src/test/resources/";

  public static final String PRIVATE_KEY_STORE_TYPE = "JKS";
  public static final String PRIVATE_KEY_STORE_FILE = RESOURCES_DIR + "keystore.jks";
  public static final String PRIVATE_KEY_STORE_PASSWORD = "password";
  public static final String DRP_PRIVATE_KEY_ALIAS ="8003628233352432";//"8003630000000004";
  public static final String PRIVATE_KEY_PASSWORD = "password";

  public static final String TRUST_STORE_TYPE = "JKS";
  public static final String TRUST_STORE_FILE = RESOURCES_DIR + "truststore.jks";
  public static final String TRUST_STORE_PASSWORD = "password";


  public static final String RECEIVER_PAYLOAD_ENCRYPTING_CERTIFICATE_ALIAS = "8003620000030499";
  public static final String SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS = "8003628233352432";//"8003630000000004";
  public static final String SENDER_TLS_CERTIFICATE_ALIAS = SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS;

  public static final String INVALID_KEYSTORE_FILE = PRIVATE_KEY_STORE_FILE + "Invalid";
  public static final String BLANK_KEYSTORE_FILENAME = RESOURCES_DIR + "TestTruststore.jks";


  // SealedMessageType values
  public static final String TARGET_SERVICE_CATEGORY = "http://ns.electronichealth.net.au/etp/sc/prescriptionrequesting/1.1";
  public static final String TARGET_SERVICE_INTERFACE = "http://ns.electronichealth.net.au/smd/intf/SealedMessageDelivery/TLS/2010";
  public static final String SENDER_ORGANISATION = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003628233352432";
  public static final String RECEIVER_ORGANISATION = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003620000030499";
  public static final String SENDER_INDIVIDUAL = "http://ns.electronichealth.net.au/id/hi/hpii/1.0/8003610000010401";
  public static final String RECEIVER_INDIVIDUAL = "http://ns.electronichealth.net.au/id/hi/hpii/1.0/8003610000010401";


  public static final String TEST_CERTIFICATE_REFERENCE_USE_QUALIFIER = "testUseQualifier";
  public static final String TEST_CERTIFICATE_REFERENCE_TYPE = "testType";
  public static final String TEST_CERTIFICATE_REFERENCE_VALUE = "testValue";

  public static final String FAIRHILL_HPIO = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003630000000822";
  public static final String FAIRHILL_NEW_SERVICE_CATEGORY = "http://ns.electronichealth.net.au/etp/sc/prescriptionowing/1.1/45";
  public static final String FAIRHILL_NEW_SERVICE_INTERFACE = "http://ns.electronichealth.net.au/smd/intf/SealedMessageDelivery/TLS/2010/34";
  public static final String FAIRHILL_NEW_SERVICE_ENDPOINT_URL = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003630000000822";
  public static final String FAIRHILL_NEW_SERVICE_PROVIDER = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003630000000822";


  public static final String INVALID = "Invalid";


  public static void setSystemVariablesForTest() {
    System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
    //Enable this for SSL debugging purpose.
//    System.setProperty("javax.net.debug", "ssl, handshake");
    System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
  }


  /**
   * Provides DRP SSLSocketFactory Instance for testing
   *
   * @return {@link SSLSocketFactory} instance for DRP TLS configuration
   * @throws IOException
   * @throws GeneralSecurityException
   */
  public static SSLSocketFactory getDRPSslSocketFactory() throws IOException, GeneralSecurityException {
    return KeystoreUtil.getSslSocketFactory(PRIVATE_KEY_STORE_TYPE,
        PRIVATE_KEY_STORE_FILE,
        PRIVATE_KEY_STORE_PASSWORD,
        PRIVATE_KEY_PASSWORD,
        DRP_PRIVATE_KEY_ALIAS,
        TRUST_STORE_TYPE,
        TRUST_STORE_FILE,
        TRUST_STORE_PASSWORD);
  }

  public static ElsCertRefType getElsCertificateReference(String certificateReferenceUseQualifier, String certificateReferenceType, String certificateReferenceValue) {
    QualifiedCertRefType qualifiedCertRef = new QualifiedCertRefType();
    qualifiedCertRef.setType(certificateReferenceType);
    qualifiedCertRef.setValue(certificateReferenceValue);
    ElsCertRefType elsCertRef = new ElsCertRefType();
    elsCertRef.setQualifiedCertRef(qualifiedCertRef);
    elsCertRef.setUseQualifier(certificateReferenceUseQualifier);
    return elsCertRef;
  }

  public static InteractionType getInteraction(String target, String serviceCategory, String serviceInterface, String serviceEndpoint, String serviceProvider) {
    InteractionType interaction = new InteractionType();
    interaction.setTarget(target);
    interaction.setServiceCategory(serviceCategory);
    interaction.setServiceInterface(serviceInterface);
    interaction.setServiceEndpoint(serviceEndpoint);
    interaction.setServiceProvider(serviceProvider);
//    interaction.getCertRef().add(new ElsCertRefType());

    return interaction;
  }

  public static InteractionType getInteraction(String target, String serviceCategory, String serviceInterface, String serviceEndpoint, String serviceProvider, List<ElsCertRefType> certRefList) {
    InteractionType interaction = getInteraction(target, serviceCategory, serviceInterface, serviceEndpoint, serviceProvider);
    interaction.getCertRef().addAll(certRefList);
    return interaction;
  }


  public static MessageMetadataType getSMDDefaultMessageMetadata() {
    MessageMetadataType metadata = new MessageMetadataType();
    metadata.setCreationTime(TimeUtility.nowXMLGregorianCalendar());
    metadata.setInvocationId("urn:uuid:" + UUID.randomUUID().toString());
    metadata.setReceiverOrganisation(RECEIVER_ORGANISATION);
    metadata.setReceiverIndividual(RECEIVER_INDIVIDUAL);
    metadata.setSenderOrganisation(SENDER_ORGANISATION);
    metadata.setSenderIndividual(SENDER_INDIVIDUAL);
    metadata.setServiceCategory(TARGET_SERVICE_CATEGORY);
    metadata.setServiceInterface(TARGET_SERVICE_INTERFACE);

    ElsCertRefType elsCertRef = getElsCertificateReference(TEST_CERTIFICATE_REFERENCE_USE_QUALIFIER, TEST_CERTIFICATE_REFERENCE_TYPE, TEST_CERTIFICATE_REFERENCE_VALUE);
    List<ElsCertRefType> certRefList = new ArrayList<ElsCertRefType>();
    certRefList.add(elsCertRef);
    InteractionType interaction = getInteraction(FAIRHILL_HPIO, FAIRHILL_NEW_SERVICE_CATEGORY,
        FAIRHILL_NEW_SERVICE_INTERFACE, FAIRHILL_NEW_SERVICE_ENDPOINT_URL, FAIRHILL_NEW_SERVICE_PROVIDER, certRefList);
    RouteRecordEntryType routeRecord = new RouteRecordEntryType();
    routeRecord.setInteraction(interaction);
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

  public static Element getDefaultMessagePayload() throws GeneralSecurityException, XspException {
    KeyStore senderKeyStore = KeystoreUtil.loadKeyStore(PRIVATE_KEY_STORE_TYPE, PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE);
    X509Certificate senderCert = (X509Certificate) senderKeyStore.getCertificate(SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);
    PrivateKey senderPrivateKey = KeystoreUtil.getSigningPrivateKey(senderKeyStore, PRIVATE_KEY_PASSWORD, SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);
    X500PrivateCredential privateCredential = new X500PrivateCredential(senderCert, senderPrivateKey);
    List<X500PrivateCredential> certificatePairs = new ArrayList<X500PrivateCredential>();
    certificatePairs.add(privateCredential);
    KeyStore trustStore = KeystoreUtil.loadKeyStore(TRUST_STORE_TYPE, TRUST_STORE_PASSWORD, TRUST_STORE_FILE);
    X509Certificate receiverCert = (X509Certificate) trustStore.getCertificate(SENDER_TLS_CERTIFICATE_ALIAS);

    Document payloadDocument = SignedEncryptedContainerProfileUtil.getSignedEncryptedPayload(SENSITIVE_DATA,
        certificatePairs, receiverCert);

    return payloadDocument.getDocumentElement();
  }


  public static List<X500PrivateCredential> getDefaultSignatureCertificate() throws GeneralSecurityException {
    KeyStore senderKeyStore = KeystoreUtil.loadKeyStore(PRIVATE_KEY_STORE_TYPE, PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE);
    X509Certificate senderCert = (X509Certificate) senderKeyStore.getCertificate(SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);
    PrivateKey senderPrivateKey = KeystoreUtil.getSigningPrivateKey(senderKeyStore, PRIVATE_KEY_PASSWORD, SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);
    X500PrivateCredential privateCredential = new X500PrivateCredential(senderCert, senderPrivateKey);
    List<X500PrivateCredential> certificatePairs = new ArrayList<X500PrivateCredential>();
    certificatePairs.add(privateCredential);
    return certificatePairs;
  }

}
