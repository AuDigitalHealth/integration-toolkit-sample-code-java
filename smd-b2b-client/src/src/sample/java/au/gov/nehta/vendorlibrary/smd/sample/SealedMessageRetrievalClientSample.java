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
import au.gov.nehta.vendorlibrary.smd.SealedMessageRetrievalClient;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.SealedMessageType;
import au.net.electronichealth.ns.smr.tls.v2010.ListErrorMsg;
import au.net.electronichealth.ns.smr.tls.v2010.RetrieveErrorMsg;
import au.net.electronichealth.ns.smr.tls.v2010.StandardErrorMsg;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Requirements
 * <p>
 * a) The endpoint URL for your Sealed Message Retrieval Web Service providers i.e. the endpoint URL of the client
 * system's intermediary. <br/> <br/>
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
 * as SENDER_ORGANISATION_HPIO ).
 * </p>
 */
public final class SealedMessageRetrievalClientSample {

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
   * Organisation HealthCare Identifier qualifier value.
   */
  private static final String HPIO_QUALIFIER = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/";

  /**
   * HealthCare provider individual Identifier qualifier value.
   */
  private static final String HPII_QUALIFIER = "http://ns.electronichealth.net.au/id/hi/hpiI/1.0/";

  /**
   * Sender organisation identifier (HPIO)
   */
  private static final String SENDER_ORGANISATION_HPIO = HPIO_QUALIFIER + "8003620000030499";

  /**
   * Receiver endpoint URL for Sealed message retrieval service. User defined server URL.
   */
  private static final String SMR_SERVICE_ENDPOINT_URL = "https://ServiceHostname:portnumber/context-root";

  /**
   * URN UUID identifier prefix.
   */
  private static final String URN_UUID_PREFIX = "urn:uuid:";

  /**
   * Whether or not to list all Sealed Messages which are available.
   */
  private static final boolean ALL_AVAILABLE = Boolean.TRUE;

  /**
   * Limit the number of Sealed Messages to be the retrieved.
   */
  private static final int LIMIT = 10;


  /**
   * Default private constructor.
   */
  private SealedMessageRetrievalClientSample() {

  }

  /**
   * Main method to perform Sealed Message Retrieval service client operations (list and deliver).
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

    //Instantiate the Sealed Message Retrieval client
    SealedMessageRetrievalClient testClient = new SealedMessageRetrievalClient(sslSocketFactory);

    //Retrieve Sealed message
    try {
      List<SealedMessageType> sealedMessages = testClient.retrieve(SENDER_ORGANISATION_HPIO,
        Arrays.asList(URN_UUID_PREFIX + UUID.randomUUID().toString()), SMR_SERVICE_ENDPOINT_URL);
    } catch (StandardErrorMsg standardErrorMsg) {
      standardErrorMsg.printStackTrace();
    } catch (RetrieveErrorMsg retrieveErrorMsg) {
      retrieveErrorMsg.printStackTrace();
    }

    //List Sealed message
    try {
      testClient.list(SENDER_ORGANISATION_HPIO, ALL_AVAILABLE, LIMIT, SMR_SERVICE_ENDPOINT_URL);
    } catch (StandardErrorMsg standardErrorMsg) {
      standardErrorMsg.printStackTrace();
    } catch (ListErrorMsg listErrorMsg) {
      listErrorMsg.printStackTrace();
    }

    //Dump SOAP request and response to variables. This is independent of HttpTransportPipe dump
    String lastSoapRequest = testClient.getLastSoapRequest();
    String lastSoapResponse = testClient.getLastSoapResponse();
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
