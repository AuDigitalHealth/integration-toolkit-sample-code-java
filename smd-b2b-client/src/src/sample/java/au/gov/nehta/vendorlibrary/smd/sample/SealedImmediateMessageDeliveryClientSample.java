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
package au.gov.nehta.vendorlibrary.smd.sample;

import au.gov.nehta.vendorlibrary.common.DOMUtil;
import au.gov.nehta.vendorlibrary.common.security.KeystoreUtil;
import au.gov.nehta.vendorlibrary.smd.SealedImmediateMessageDeliveryClient;
import au.gov.nehta.vendorlibrary.smd.SealedMessageDeliveryClient;
import au.gov.nehta.vendorlibrary.ws.TimeUtility;
import au.net.electronichealth.ns.simd.tls.v2010.DeliverErrorMsg;
import au.net.electronichealth.ns.simd.tls.v2010.StandardErrorMsg;
import au.net.electronichealth.ns.simd.tls.v2010.TimeoutErrorMsg;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.MessageMetadataType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.SealedMessageType;
import org.w3c.dom.Element;

import javax.net.ssl.SSLSocketFactory;
import javax.security.auth.x500.X500PrivateCredential;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.UUID;

/**
 * Requirements
 * <p>
 * a) The endpoint URLs for your Sealed Immediate Message Delivery Web Service providers i.e. the receiver of your
 * sealed message who will synchronously respond with a Sealed Message. <br/> <br/>
 * </p>
 * <p>
 * b) A Transport Layer Security (TLS) public/private key pair and its associated public certificate.
 * These are used to authenticate the client to the Secure Message Delivery  server instance during the Transport Layer
 * Security (TLS) handshake. They are typically stored in a Java key store file.<br/>
 * The user's certificate, private and public keys go into keystore.jks,  while certificates of external parties goes
 * into truststore.jks Java key store file.<br/><br/>
 * </p>
 * <p>
 * c) Your organisation public/private key pair and its associated public certificate.
 * These are used to sign the sensitive payload using XSP profile. They are typically stored in a Java key store file.<br/>
 * The organisation certificate, private and public keys go into keystore.jks,
 * <br/><br/>
 * d) Receiver organisations encrypting public certificate.These are used to encrypted the signed sensitive xml data using
 * XSP profile. These certificates of external parties goes into truststore.jks Java key store file.<br/><br/>
 * </p>
 * <p>
 * e) The digital certificate of the Certificate Authority (CA) which signed the SMD Web Service providers TLS certificate.
 * This certificate is used to authenticate the SMD Web Service provider to the clients during the TLS handshake.
 * This certificate is typically stored in a Java trust store file.
 * </p>
 * <p>
 * f) Your organisation's fully qualified Healthcare Provider Identifier or HPI-O (hereinafter referred to as
 * SENDER_ORGANISATION_HPIO) and those to whom you wish to send and receive messages from (hereinafter referred to
 * as RECEIVER_ORGANISATION_HPIO ).
 * </p>
 */
public final class SealedImmediateMessageDeliveryClientSample {

  /**
   * Private keystore type.
   */
  private static final String PRIVATE_KEY_STORE_TYPE = "JKS";

  /**
   * Private keystore password.
   */
  private static final String PRIVATE_KEY_STORE_PASSWORD = "changeit";

  /**
   * Private keystore filename
   */
  private static final String PRIVATE_KEY_STORE_FILE = "keystore.jks";

  /**
   * Private key alias name.
   */
  private static final String PRIVATE_KEY_ALIAS_NAME = "8003624166667003";

  /**
   * Truststore  type.
   */
  private static final String TRUSTSTORE_TYPE = "JKS";

  /**
   * Private Key password.
   */
  private static final String PRIVATE_KEY_PASSWORD = "changeit";

  /**
   * Truststore  filename.
   */
  private static final String TRUSTSTORE_FILE = "truststore.jks";

  /**
   * Truststore password.
   */
  private static final String TRUSTSTORE_PASSWORD = "changeit";

  /**
   * Sensitive Paylaod XML as string.
   */
  private static final String XML_STRING = "<ClinicalDocument>Patient sensitive data</ClinicalDocument>";

  /**
   * Organisation HealthCare Identifier qualifier value.
   */
  private static final String HPIO_QUALIFIER = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/";

  /**
   * HealthCare provider individual Identifier qualifier value.
   */
  private static final String HPII_QUALIFIER = "http://ns.electronichealth.net.au/id/hi/hpiI/1.0/";

  /**
   * Sender organisation identifier (HPIO.)
   */
  private static final String SENDER_ORGANISATION_HPIO = HPIO_QUALIFIER + "8003620000000004";

  /**
   * Receiver organisation identifier (HPIO)
   */
  private static final String RECEIVER_ORGANISATION_HPIO = HPIO_QUALIFIER + "8003620000030499";

  /**
   * Sender individual identifier (HPII)
   */
  private static final String SENDER_INDIVIDUAL = HPII_QUALIFIER + "8003610000010401";

  /**
   * Receiver Individual identifier (HPII)
   */
  private static final String RECEIVER_INDIVIDUAL = HPII_QUALIFIER + "8003610000010401";

  /**
   * A URI value indicating the service category.
   */
  private static final String SERVICE_CATEGORY = "http://ns.electronichealth.net.au/smd/sc/TransportResponseDelivery/2010";

  /**
   * A URI value indicating the service interface.
   */
  private static final String SERVICE_INTERFACE = "http://ns.electronichealth.net.au/smd/intf/SealedMessageDelivery/TLS/2010";


  /**
   * Sender signing certificate alias name. This is a implementation specific value to identify the certificate from Java keystore.
   */
  private static final String SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS = PRIVATE_KEY_ALIAS_NAME;

  /**
   * Alias name for the receiver/destination  public certificate. This is an implementation specific value to identify the certificate from the
   * java keystore.
   */
  private static final String RECEIVER_PAYLOAD_ENCRYPTING_CERTIFICATE_ALIAS = "8003620000030499";

  /**
   * Receiver endpoint URL for Sealed message delivery service. User defined server URL.
   */
  private static final String SMD_SERVICE_ENDPOINT_URL = "https://ServiceHostname:portnumber/context-root";

  /**
   * URN UUID identifier prefix.
   */
  private static final String URN_UUID_PREFIX = "urn:uuid:";

  /**
   * Default private constructor.
   */
  private SealedImmediateMessageDeliveryClientSample() {

  }

  /**
   * Main method to perform Sealed Immediate Message Delivery service client deliver operation.
   *
   * @param args (NOT REQUIRED)
   * @throws IOException              in an event of IO error.
   * @throws GeneralSecurityException in an event of Security error.
   * @throws TransformerException     in an event of XML to Document or Document to XML transformation error.
   * @throws JAXBException            in an event of JAXB operation error.
   */
  public static void main(String[] args) throws IOException, GeneralSecurityException, TransformerException,
    JAXBException {

    //Set the SSLSocketFactory instance for the TLS connection.
    SSLSocketFactory sslSocketFactory = getSSLSocketFactory();

    //Instantiate the Sealed Immediate Message delivery client
    SealedImmediateMessageDeliveryClient testClient = new SealedImmediateMessageDeliveryClient(sslSocketFactory);

    //Set the Sealed message delivery metadata.
    MessageMetadataType metadata = getDefaultMessageMetadata();

    //Construct the SealedMessageType from message metadata and sensitive payload using NEHTA XSP library.
    SealedMessageType sealedMessage = getSealedMessageForXmlElementPayload(metadata,
      DOMUtil.getDocumentFromXML(XML_STRING).getDocumentElement());

    try {
      SealedMessageType sealedMessageType = testClient.deliver(sealedMessage, SMD_SERVICE_ENDPOINT_URL);
    } catch (StandardErrorMsg standardErrorMsg) {
      standardErrorMsg.printStackTrace();
    } catch (DeliverErrorMsg deliverErrorMsg) {
      deliverErrorMsg.printStackTrace();
    } catch (TimeoutErrorMsg timeoutErrorMsg) {
      timeoutErrorMsg.printStackTrace();
    }

    //Dump SOAP request and response to variables. This is independent of HttpTransportPipe dump
    String lastSoapRequest = testClient.getLastSoapRequest();
    String lastSoapResponse = testClient.getLastSoapResponse();
  }


  /**
   * Returns the default Metadata of the sender and receiver organisation provided as class constants.
   *
   * @return The constructed MessageMetadataType instance
   */
  private static MessageMetadataType getDefaultMessageMetadata() {
    MessageMetadataType metadata = new MessageMetadataType();
    metadata.setInvocationId(URN_UUID_PREFIX + UUID.randomUUID().toString());
    metadata.setReceiverOrganisation(RECEIVER_ORGANISATION_HPIO);
    metadata.setReceiverIndividual(RECEIVER_INDIVIDUAL);
    metadata.setSenderOrganisation(SENDER_ORGANISATION_HPIO);
    metadata.setSenderIndividual(SENDER_INDIVIDUAL);
    metadata.setServiceCategory(SERVICE_CATEGORY);
    metadata.setServiceInterface(SERVICE_INTERFACE);

    metadata.setCreationTime(TimeUtility.nowXMLGregorianCalendar());
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

  /**
   * Returns current date time as Calendar instance.
   *
   * @return The current date time as Calendar instance.
   */
  private static Calendar getCreationTime() {
    Calendar now = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
    now.setTime(new Date(System.currentTimeMillis()));
    return now;
  }

  /**
   * Returns SealedMessageType for the provided message metadata and sensitive xml payload element.
   *
   * @param metadata               the sealed message metadata instance.
   * @param nonEncryptedXmlPayload sensitive payload as XML Element
   * @return The constructed SealedMessageType instance.
   * @throws GeneralSecurityException in an event of Security error.
   * @throws IOException              in an event of IO error.
   * @throws JAXBException            in an event of JAXB operation error.
   * @throws TransformerException     in an event of XML to Document or Document to XML transformation error.
   */
  private static SealedMessageType getSealedMessageForXmlElementPayload(
    MessageMetadataType metadata, Element nonEncryptedXmlPayload) throws GeneralSecurityException, IOException,
    JAXBException, TransformerException {

    //Load the sender's private keystore ot obtain signing public certificate and private key.
    KeyStore senderKeyStore = KeystoreUtil.loadKeyStore(PRIVATE_KEY_STORE_TYPE, PRIVATE_KEY_STORE_PASSWORD,
      PRIVATE_KEY_STORE_FILE);

    //Obtain the sender's public signing certificate for verification purpose.
    X509Certificate senderCert = (X509Certificate) senderKeyStore.getCertificate(
      SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);

    //Obtain the sender's private key entry for signing the sensitive payload.
    PrivateKey senderPrivateKey = KeystoreUtil.getSigningPrivateKey(senderKeyStore, PRIVATE_KEY_PASSWORD,
      SENDER_PAYLOAD_SIGNING_CERTIFICATE_ALIAS);

    X500PrivateCredential privateCredential = new X500PrivateCredential(senderCert, senderPrivateKey);

    //Load sender truststore to obtain receiver's public certificate.
    KeyStore trustStore = KeystoreUtil.loadKeyStore(TRUSTSTORE_TYPE, TRUSTSTORE_PASSWORD, TRUSTSTORE_FILE);

    X509Certificate receiverCert = (X509Certificate) trustStore.getCertificate(
      RECEIVER_PAYLOAD_ENCRYPTING_CERTIFICATE_ALIAS);

    //Construct the sealed message using NETHA XSP profile.
    SealedMessageType sealedMessage = SealedMessageDeliveryClient.getSealedMessage(nonEncryptedXmlPayload, metadata,
      Arrays.asList(privateCredential), receiverCert);

    return sealedMessage;
  }

  /**
   * Returns the client ssl socket factory instance for TLS connection.
   *
   * @return client ssl socket factory credentials as SSLSocketFactory
   * @throws java.io.IOException in an event of IO error.
   * @throws java.security.GeneralSecurityException
   *                             in an event of Security error.
   */
  private static SSLSocketFactory getSSLSocketFactory() throws IOException, GeneralSecurityException {
    //Set the SSLSocketFactory instance for the TLS connection.
    SSLSocketFactory sslSocketFactory = KeystoreUtil.getSslSocketFactory(PRIVATE_KEY_STORE_TYPE,
      PRIVATE_KEY_STORE_FILE,
      PRIVATE_KEY_STORE_PASSWORD,
      PRIVATE_KEY_PASSWORD,
      PRIVATE_KEY_ALIAS_NAME,
      TRUSTSTORE_TYPE,
      TRUSTSTORE_FILE,
      TRUSTSTORE_PASSWORD);
    return sslSocketFactory;
  }
}
