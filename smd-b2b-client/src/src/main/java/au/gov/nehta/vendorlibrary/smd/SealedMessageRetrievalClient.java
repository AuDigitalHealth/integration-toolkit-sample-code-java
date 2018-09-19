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

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.vendorlibrary.common.security.EncryptedContainerProfileUtil;
import au.gov.nehta.vendorlibrary.common.security.SignedContainerProfileUtil;
import au.gov.nehta.vendorlibrary.ws.WebServiceClientUtil;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.gov.nehta.xsp.CertificateVerificationException;
import au.gov.nehta.xsp.CertificateVerifier;
import au.gov.nehta.xsp.SignatureValidationException;
import au.gov.nehta.xsp.XspException;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.SealedMessageType;
import au.net.electronichealth.ns.smr.tls.v2010.ListErrorMsg;
import au.net.electronichealth.ns.smr.tls.v2010.MessageListType;
import au.net.electronichealth.ns.smr.tls.v2010.RetrieveErrorMsg;
import au.net.electronichealth.ns.smr.tls.v2010.SealedMessageRetrieval;
import au.net.electronichealth.ns.smr.tls.v2010.SealedMessageRetrievalService;
import au.net.electronichealth.ns.smr.tls.v2010.StandardErrorMsg;
import org.w3c.dom.Document;

import javax.net.ssl.SSLSocketFactory;
import javax.security.auth.x500.X500PrivateCredential;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An implementation of a Sealed Message Retrieval client.
 */
public class SealedMessageRetrievalClient {
  /**
   * The Logging handler instance for logging SOAP request and Response messages.
   */
  private LoggingHandler loggingHandler;

  /**
   * The Web Services port for the Sealed Message Retrieval service
   */
  private SealedMessageRetrieval sealedMessageRetrievalPort;

  /**
   * Default constructor.
   *
   * @param sslSocketFactory the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
   */
  public SealedMessageRetrievalClient(SSLSocketFactory sslSocketFactory) {

    // Add argument validation.
    this.loggingHandler = new LoggingHandler(false);
    List<Handler> handlerChain = new ArrayList<Handler>();
    handlerChain.add(loggingHandler);
    this.sealedMessageRetrievalPort = WebServiceClientUtil.getPort(SealedMessageRetrieval.class,
                                                                   SealedMessageRetrievalService.class,
                                                                   sslSocketFactory,
                                                                   handlerChain);
  }

  /**
   * Performs the Sealed Message Retrieval retrieve operation.
   *
   * @param receiverOrganisation the receiver organisation. (Mandatory)
   * @param invocationIdentifiers the list of invocation identifiers to retrieve. (Mandatory)
   * @param endpoint the Sealed Message Delivery (SMD) endpoint. (Mandatory)
   *
   * @return List<SealedMessageType> the list of Sealed Messages being retrieved.
   *
   * @throws StandardErrorMsg if the web service invocation fails due to a standard error
   * @throws RetrieveErrorMsg if the web service invocation fails due to a retrieve error
   */
  public final List<SealedMessageType> retrieve(String receiverOrganisation, List<String> invocationIdentifiers, String endpoint)
    throws RetrieveErrorMsg, StandardErrorMsg {

    ArgumentUtils.checkNotNullNorBlank(receiverOrganisation, "Receiver Organisation");
    ArgumentUtils.checkNotNullNorEmpty(invocationIdentifiers, "Invocation Identifiers");
    ArgumentUtils.checkNotNullNorBlank(endpoint, "Endpoint");
    configurePortWithEndpoint(endpoint);

    return sealedMessageRetrievalPort.retrieve(receiverOrganisation, invocationIdentifiers);
  }

  /**
   * Performs the Sealed Message Retrieval list operation.
   *
   * @param receiverOrganisation the receiver organisation (Mandatory)
   * @param allAvailable whether or not to list all Sealed Messages which are available (Mandatory)
   * @param limit limit the number of Sealed Messages in the list (Mandatory)
   * @param endpoint the Sealed Message Delivery (SMD) endpoint (Mandatory)
   *
   * @return MessageListType the list of messages available for retrieval.
   *
   * @throws StandardErrorMsg if the web service invocation fails due to a standard error
   * @throws ListErrorMsg if the web service invocation fails due to a list error
   */
  public final MessageListType list(String receiverOrganisation, boolean allAvailable, int limit, String endpoint)
    throws ListErrorMsg, StandardErrorMsg {

    ArgumentUtils.checkNotNullNorBlank(receiverOrganisation, "Receiver Organisation");
    ArgumentUtils.checkNotNullNorBlank(endpoint, "Endpoint");
    configurePortWithEndpoint(endpoint);

    return sealedMessageRetrievalPort.list(receiverOrganisation, allAvailable, limit);
  }

  /**
   * Configures the Sealed Message Retrieval port with the correct endpoint.
   *
   * @param endpoint the endpoint to be configured (Mandatory)
   */
  private void configurePortWithEndpoint(String endpoint) {
    final BindingProvider bindingProvider = (BindingProvider) sealedMessageRetrievalPort;
    final Map<String, Object> requestContext = bindingProvider.getRequestContext();
    requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint.trim());
  }

  /**
   * Obtains the decrypted signed payload data from a Sealed Message.
   *
   * @param sealedMessage the sealed message containing the encrypted payload (Mandatory)
   * @param privateCredential the private credentials (private key and certificate) required to decrypt the
   *                          sealed message's encrypted payload (Mandatory)
   *
   * @return the payload
   *
   * @throws XspException if an XSP error occurs while trying to decrypt the payload.
   */
  public static Document getDecryptedSignedPayload(SealedMessageType sealedMessage, X500PrivateCredential privateCredential) throws XspException {
    Document encryptedPayloadDocument = sealedMessage.getEncryptedPayload().getOwnerDocument();
    return EncryptedContainerProfileUtil.getDecryptedPayload(encryptedPayloadDocument, privateCredential);
  }

  /**
   * Obtains payload data from a decrypted signed payload.
   *
   * @param decryptedSignedPayload the decrypted signed payload from the received sealed message (Mandatory)
   *
   * @return the payload
   *
   * @throws XspException if an XSP error occurs while trying to decrypt the payload.
   */
  public static Document getPayload(Document decryptedSignedPayload) throws XspException {
    return SignedContainerProfileUtil.getDataFromSignedPayload(decryptedSignedPayload);
  }

  /**
   * Verifies the signature of a decrypted payload.
   *
   * @param decryptedSignedPayload the decrypted signed payload from the received sealed message (Mandatory)
   * @param certificateVerifier the CertificateVerifier implementation which should be used to verify the certificate
   *                            used to verify the signature (Mandatory)
   *
   * @return validSignature <i>true</i> if the signature is valid, otherwise <i>false</i>
   *
   * @throws XspException if an XSP error occurs while trying to obtain the signature of the payload.
   * @throws SignatureValidationException if a Signature Validation exception error occurs while trying
   *                                      to obtain the signature of the payload.
   * @throws CertificateVerificationException if a Certificate Verifier exception error occurs
   */
  public static boolean verifyPayloadSignature(Document decryptedSignedPayload, CertificateVerifier certificateVerifier)
    throws XspException, SignatureValidationException, CertificateVerificationException {
    ArgumentUtils.checkNotNull(decryptedSignedPayload, "decryptedSignedPayload");
    ArgumentUtils.checkNotNull(certificateVerifier, "certificateVerifier");

    return SignedContainerProfileUtil.verifySignature(decryptedSignedPayload, certificateVerifier);
  }

  /**
   * Getter for lastSoapResponse.
   *
   * @return lastSoapResponse the lastSoapResponse instance variable
   */
  public final String getLastSoapResponse() {
    if (loggingHandler != null) {
      return loggingHandler.getLastSoapResponse();
    } else {
      return LoggingHandler.EMPTY;
    }
  }

  /**
   * Getter for lastSoapRequest.
   *
   * @return lastSoapRequest the lastSoapRequest instance variable (Mandatory)
   */
  public final String getLastSoapRequest() {
    if (loggingHandler != null) {
      return loggingHandler.getLastSoapRequest();
    } else {
      return LoggingHandler.EMPTY;
    }
  }
}
