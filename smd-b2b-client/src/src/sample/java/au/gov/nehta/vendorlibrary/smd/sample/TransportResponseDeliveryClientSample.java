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

import au.gov.nehta.vendorlibrary.common.security.KeystoreUtil;
import au.gov.nehta.vendorlibrary.smd.TransportResponseDeliveryClient;
import au.gov.nehta.vendorlibrary.ws.TimeUtility;
import au.net.electronichealth.ns.smd.xsd.transportresponse._2010.DeliveryResponseType;
import au.net.electronichealth.ns.smd.xsd.transportresponse._2010.ResponseClassType;
import au.net.electronichealth.ns.smd.xsd.transportresponse._2010.TransportResponseMetadataType;
import au.net.electronichealth.ns.smd.xsd.transportresponse._2010.TransportResponseType;
import au.net.electronichealth.ns.trd.tls.v2010.DeliverErrorMsg;
import au.net.electronichealth.ns.trd.tls.v2010.DeliverStatusType;
import au.net.electronichealth.ns.trd.tls.v2010.StandardErrorMsg;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.UUID;

/**
 * Requirements
 * <p>
 * a) The endpoint URLs for your Transport Response Delivery Web Service providers i.e. the endpoint URL of the
 * the receiver of the Transport Response or that of its intermediary. <br/> <br/>
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
 * d) Receiver organisations encrypting public certificate.These are used to encrypted the signed sensitive data usig XSP profile.
 * These certificates of external parties goes into truststore.jks Java key store file.<br/><br/>
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
 * <p>
 * g) The endpoint URLs and interaction details of for your Transport Response Retrieval Web Service providers.
 * </p>
 */
public final class TransportResponseDeliveryClientSample {
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
   * A URI value indicating the service category  same value received in the original Sealed message.
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
   * Receiver endpoint URL for Transport response delivery service. User defined server URL.
   */
  private static final String TRD_ENDPOINT_URL = "https://ServiceHostname:portnumber/servicecontext";

  /**
   * An URI value indicating how to interpret the certificate value type
   */
  private static final String CERTIFICATE_REFERENCE_TYPE = "http://ns.electronichealth.net.au/qcr/type/pem/2010";

  /**
   * Certificate value for the the specified certificate value type. Can be a HTTP URL, LDAP, URL, or a certificate in PEM format.
   */
  private static final String CERTIFICATE_REFERENCE_VALUE = "-----BEGIN CERTIFICATE----- "
    + "WFuIGlzIGRpc3Rpbmd1aXNoZWQsIG5vdCBvbmx5IGJ5IGhpcyByZWFzb24sIGJ1dCBieSB0aGlz   -----END CERTIFICATE-----";

  /**
   * An URI value indicating the certificate use.
   */
  private static final String CERTIFICATE_REFERENCE_USEQUALIFIER = "http://ns.electronichealth.net.au/qcr/xsd/QualifiedCertRef/2010";

  /**
   * Identifier for the target service owner. This is represented as a qualified identifier
   */
  private static final String TARGET = HPIO_QUALIFIER + "8003630000000822";

  /**
   * Address for invoking the target service endpoint
   */
  private static final String SERVICE_ENDPOINT = "https://ServiceHostname:portnumber/context-root";

  /**
   * Identity for the target service operator.
   */
  private static final String SERVICE_PROVIDER = HPIO_QUALIFIER + "8003620000000004";

  /**
   * URN UUID identifier prefix.
   */
  private static final String URN_UUID_PREFIX = "urn:uuid:";

  /**
   * The transport response response code to indicate whether the Transport response delivery status.
   */
  private static final String RESPONSE_CODE = ResponseClassType.SUCCESS.toString();

  /**
   * The transport response response message to be delivered.
   */
  private static final String RESPONSE_MESSAGE = "";

  /**
   * Digest value of the original Sealed Message.  Set the appropriate digest value from the corresponding sealed message.
   */
  private static final byte[] RESPONSE_DIGEST_VALUE = null;

  /**
   * Default private constructor.
   */
  private TransportResponseDeliveryClientSample() {

  }

  /**
   * Main method to perform Transport Response Delivery Service client deliver operation.
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

    //Instantiate the Transport Response Delivery client
    TransportResponseDeliveryClient testClient = new TransportResponseDeliveryClient(sslSocketFactory);

    //Set the Transport response type for Transport Response Delivery.
    TransportResponseType responseType = new TransportResponseType();
    responseType.setMetadata(getDefaultTransportResponseMessageMetadata());
    responseType.setDeliveryResponse(getDeliveryResponseType());

    // Optionally the TransportResponseType can be obtain from the corresponding sealed message using the following snippet:
    //    TransportResponseType responseType =  TransportResponseDeliveryClient.getTransportResponseType(
    //      sealedMessageType, responseClassType,
    //      deliveryResponseCode, deliveryResponseMessage, transportResponseTime,
    //      responseId, isFinal, sourceOrganisation,x500PrivateCredential);

    try {
      DeliverStatusType deliverStatusType = testClient.deliver(Arrays.asList(responseType), TRD_ENDPOINT_URL);
    } catch (StandardErrorMsg standardErrorMsg) {
      standardErrorMsg.printStackTrace();
    } catch (DeliverErrorMsg deliverErrorMsg) {
      deliverErrorMsg.printStackTrace();
    }

    //Dump SOAP request and response to variables. This is independent of HttpTransportPipe dump
    String lastSoapRequest = testClient.getLastSoapRequest();
    String lastSoapResponse = testClient.getLastSoapResponse();
  }


  /**
   * Returns the default Transport response Metadata of the sender and receiver organisation provided as class constants.
   *
   * @return The constructed MessageMetadataType instance
   */
  private static TransportResponseMetadataType getDefaultTransportResponseMessageMetadata() {
    TransportResponseMetadataType metadata = new TransportResponseMetadataType();
    metadata.setTransportResponseTime(TimeUtility.nowXMLGregorianCalendar());
    metadata.setInvocationId(URN_UUID_PREFIX + UUID.randomUUID().toString());
    metadata.setResponseId(metadata.getInvocationId());
    metadata.setReceiverOrganisation(RECEIVER_ORGANISATION_HPIO);
    metadata.setResponseId(metadata.getInvocationId());
    metadata.setSenderOrganisation(SENDER_ORGANISATION_HPIO);
    metadata.setSourceOrganisation(SENDER_ORGANISATION_HPIO);
    metadata.setServiceCategory(SERVICE_CATEGORY);
    return metadata;
  }

  /**
   * Returns the Transport Response DeliveryResponseType
   *
   * @return The constructed DeliveryResponseType instance.
   */
  private static DeliveryResponseType getDeliveryResponseType() {
    DeliveryResponseType deliveryResponseType = new DeliveryResponseType();
    deliveryResponseType.setResponseCode(RESPONSE_CODE);
    deliveryResponseType.setMessage(RESPONSE_MESSAGE);
    deliveryResponseType.setDigestValue(RESPONSE_DIGEST_VALUE);
    return deliveryResponseType;
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