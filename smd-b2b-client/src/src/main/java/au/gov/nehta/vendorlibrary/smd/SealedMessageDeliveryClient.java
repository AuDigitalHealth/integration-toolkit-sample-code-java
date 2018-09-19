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
import au.gov.nehta.common.utils.DomUtils;
import au.gov.nehta.vendorlibrary.common.JAXBUtil;
import au.gov.nehta.vendorlibrary.common.security.SignedEncryptedContainerProfileUtil;
import au.gov.nehta.vendorlibrary.ws.WebServiceClientUtil;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.gov.nehta.xsp.XspException;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.net.ssl.SSLSocketFactory;
import javax.security.auth.x500.X500PrivateCredential;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An implementation of a Sealed Message Delivery client.
 */
public class SealedMessageDeliveryClient {
  /**
   * Validates parameters are correct for the various operations.
   */
  public static class ArgumentValidator {
    /**
     * Ensures that a Sealed Message is valid.
     *
     * @param sealedMessage The sealed message being checked (Mandatory)
     */
    public final void checkSealedMessage(SealedMessageType sealedMessage) {
      ArgumentUtils.checkNotNull(sealedMessage, "Sealed Message");

      MessageMetadataType metadata = sealedMessage.getMetadata();
      Element encryptedPayload = sealedMessage.getEncryptedPayload();

      ArgumentUtils.checkNotNull(encryptedPayload, "Encrypted Payload");

      checkSealedMessageMetadata(metadata);
    }

    /**
     * Ensures a Sealed Message's metadata is valid.
     *
     * @param metadata The sealed message metadata being checked (Mandatory)
     */
    private void checkSealedMessageMetadata(MessageMetadataType metadata) {
      ArgumentUtils.checkNotNull(metadata, "Message Metadata");
      ArgumentUtils.checkNotNull(metadata.getCreationTime(), "Creation Time");
      ArgumentUtils.checkNotNullNorBlank(metadata.getInvocationId(), "Invocation ID");
      ArgumentUtils.checkNotNullNorBlank(metadata.getReceiverOrganisation(), "Receiver Organisation");
      ArgumentUtils.checkNotNullNorBlank(metadata.getSenderOrganisation(), "Sender Organisation");
      ArgumentUtils.checkNotNullNorBlank(metadata.getServiceCategory(), "Service Category");
      ArgumentUtils.checkNotNullNorBlank(metadata.getServiceInterface(), "Service Interface");

      ArgumentUtils.checkNotNull(metadata.getRouteRecord(), "Route record(s)");
      if (metadata.getRouteRecord().size() < 1) {
        throw new IllegalArgumentException("At lease one route record needs to be provided in the Sealed Message.");
      }

      for (RouteRecordEntryType routeRecord : metadata.getRouteRecord()) {
        ArgumentUtils.checkNotNull(routeRecord.getInteraction(), "Interaction");
        checkInteraction(routeRecord.getInteraction());
      }

      // Optional fields.
      checkOptionalSealedMessageFields(metadata);
    }

    /**
     * Ensures that a Sealed Message's optional fields are valid if provided.
     *
     * @param metadata The sealed message metadata being checked (Mandatory)
     */
    private void checkOptionalSealedMessageFields(MessageMetadataType metadata) {

      checkOptionalSenderAndReceiverIndividuals(metadata);

      if (metadata.getOtherTransportMetadata() != null) {
        for (OtherTransportMetadataEntryType otherMetadata : metadata.getOtherTransportMetadata()) {
          ArgumentUtils.checkNotNull(otherMetadata.getMetadataValue(), "Other Metadata Value");
          ArgumentUtils.checkNotNullNorBlank(otherMetadata.getMetadataType(), "Other Metadata Type");
        }
      }
    }

    /**
     * Ensure that sender and receiver individuals are valid if provided.
     *
     * @param metadata The sealed message metadata being checked (Mandatory)
     */
    private void checkOptionalSenderAndReceiverIndividuals(MessageMetadataType metadata) {

      if (metadata.getReceiverIndividual() != null) {
        ArgumentUtils.checkNotNullNorBlank(metadata.getReceiverIndividual(), "Receiver Individual");
      }

      if (metadata.getSenderIndividual() != null) {
        ArgumentUtils.checkNotNullNorBlank(metadata.getSenderIndividual(), "Sender Individual");
      }
    }

    /**
     * Ensures that an interaction is valid.
     *
     * @param interaction The interaction being checked (Mandatory)
     */
    private void checkInteraction(InteractionType interaction) {

      ArgumentUtils.checkNotNull(interaction, "interaction");
      ArgumentUtils.checkNotNullNorBlank(interaction.getTarget(), "target");
      ArgumentUtils.checkNotNullNorBlank(interaction.getServiceCategory(), "serviceCategory");
      ArgumentUtils.checkNotNullNorBlank(interaction.getServiceInterface(), "serviceInterface");
      ArgumentUtils.checkNotNullNorBlank(interaction.getServiceEndpoint(), "serviceEndpoint");
      ArgumentUtils.checkNotNullNorBlank(interaction.getServiceProvider(), "serviceProvider");

      if (interaction.getCertRef() != null && interaction.getCertRef().size() > 0) {
        for (ElsCertRefType certRef : interaction.getCertRef()) {
          ArgumentUtils.checkNotNull(certRef, "Certificate reference");
          ArgumentUtils.checkNotNullNorBlank(certRef.getUseQualifier(), "Certificare reference: use qualifier");
          ArgumentUtils.checkNotNull(certRef.getQualifiedCertRef(), "Certificate reference: qualified cert ref");
          ArgumentUtils.checkNotNullNorBlank(certRef.getQualifiedCertRef().getType(), "Certificate reference: qualified cert ref type");
          ArgumentUtils.checkNotNullNorBlank(certRef.getQualifiedCertRef().getValue(), "Certificate reference: qualified cert ref value");
        }
      }
    }
  }

  /**
   * Validates the arguments passed for the various operations
   */
  private ArgumentValidator argumentValidator = new ArgumentValidator();

  /**
   * The Logging handler instance for logging SOAP request and Response messages.
   */
  private LoggingHandler loggingHandler;

  /**
   * The Web Services port for the Sealed Message Delivery service
   */
  private SealedMessageDelivery sealedMessageDeliveryPort;

  /**
   * Default constructor.
   *
   * @param sslSocketFactory the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
   */
  public SealedMessageDeliveryClient(SSLSocketFactory sslSocketFactory) {

    // Add argument validation.
    this.loggingHandler = new LoggingHandler(false);
    List<Handler> handlerChain = new ArrayList<Handler>();
    handlerChain.add(loggingHandler);
    this.sealedMessageDeliveryPort = WebServiceClientUtil.getPort(SealedMessageDelivery.class,
        SealedMessageDeliveryService.class,
        sslSocketFactory,
        handlerChain);
  }

  /**
   * Performs the Sealed Message Delivery deliver operation.
   *
   * @param sealedMessage the sealed message to be delivered (Mandatory)
   * @param endpoint      the Sealed Message Delivery (SMD) endpoint URL (Mandatory)
   *
   * @return DeliverStatusType the delivery status of the method invocation.
   *
   * @throws StandardErrorMsg if the web service invocation fails due to a standard error
   * @throws DeliverErrorMsg  if the web service invocation fails due to a deliver error
   */
  public final DeliverStatusType deliver(SealedMessageType sealedMessage, String endpoint) throws StandardErrorMsg, DeliverErrorMsg {

    argumentValidator.checkSealedMessage(sealedMessage);
    ArgumentUtils.checkNotNullNorBlank(endpoint, "Endpoint");
    configurePortWithEndpoint(endpoint);
    return sealedMessageDeliveryPort.deliver(sealedMessage);
  }

  /**
   * Configured the Sealed Message Delivery port with the correct endpoint.
   *
   * @param endpoint the endpoint to be configured (Mandatory)
   */
  private void configurePortWithEndpoint(final String endpoint) {
    final BindingProvider bindingProvider = (BindingProvider) sealedMessageDeliveryPort;
    final Map<String, Object> requestContext = bindingProvider.getRequestContext();
    requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint.trim());
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
   * @return lastSoapRequest the lastSoapRequest instance variable
   */
  public final String getLastSoapRequest() {
    if (loggingHandler != null) {
      return loggingHandler.getLastSoapRequest();
    } else {
      return LoggingHandler.EMPTY;
    }
  }

  /**
   * Verifies a sealed message's metadata for Sealed Message Delivery.
   *
   * @param metadata The Sealed Message metadata to be checked (Mandatory)
   */
  public static void verifyMetadata(MessageMetadataType metadata) {
    ArgumentValidator argumentValidator = new ArgumentValidator();
    argumentValidator.checkSealedMessageMetadata(metadata);
  }

  /**
   * Creates a valid Sealed Message with an unencrypted XML payload.
   *
   * @param unencryptedXmlPayload the unencrypted XML payload as a String
   *                              (NOTE: the string should include the XML prolog) (Mandatory)
   * @param metadata              the Sealed Message metadata (Mandatory)
   * @param privateCredentials    the sender's private key and certificate (Mandatory)
   * @param encryptionCertificate the receiver's certificate (Mandatory)
   *
   * @return sealedMessage a valid sealed message.
   */
  public static SealedMessageType getSealedMessage(String unencryptedXmlPayload,
                                                   MessageMetadataType metadata,
                                                   List<X500PrivateCredential> privateCredentials,
                                                   X509Certificate encryptionCertificate) {
    SealedMessageDeliveryClient.verifyMetadata(metadata);

    Document encryptedPayloadDocument;
	try {
		encryptedPayloadDocument = SignedEncryptedContainerProfileUtil.getSignedEncryptedPayload(
		    unencryptedXmlPayload, privateCredentials, encryptionCertificate);
	} catch (XspException e) {
		throw new SMDRuntimeException(e);
	}
	
    Element encryptedPayloadElement = encryptedPayloadDocument.getDocumentElement();
    SealedMessageType sealedMessage = new SealedMessageType();
    sealedMessage.setMetadata(metadata);
    sealedMessage.setEncryptedPayload(encryptedPayloadElement);

    return sealedMessage;
  }

  /**
   * Creates a valid Sealed Message with an unencrypted non-XML payload.
   *
   * @param unencryptedNonXmlPayload the unencrypted non-XML payload as a byte array (Mandatory)
   * @param metadata                 the Sealed Message metadata (Mandatory)
   * @param privateCredentials       the sender's private key and certificate (Mandatory)
   * @param encryptionCertificate    the receiver's certificate (Mandatory)
   *
   * @return sealedMessage a valid sealed message.
   *
   * @throws javax.xml.bind.JAXBException if the non-XML payload cannot be serialised into XML.
   */
  public static SealedMessageType getSealedMessage(byte[] unencryptedNonXmlPayload,
                                                   MessageMetadataType metadata,
                                                   List<X500PrivateCredential> privateCredentials,
                                                   X509Certificate encryptionCertificate) throws JAXBException {
    MessageType messageType = new MessageType();
    messageType.setData(unencryptedNonXmlPayload);
    String messageTypeXml = JAXBUtil.marshaller(MessageType.class,
        "http://ns.electronichealth.net.au/smd/xsd/Message/2010",
        "au.net.electronichealth.smd.message.v2010",
        messageType);
    return getSealedMessage(messageTypeXml, metadata, privateCredentials, encryptionCertificate);
  }

  /**
   * Creates a valid Sealed Message with an unencrypted non-XML payload.
   *
   * @param unencryptedXmlPayload the unencrypted XML payload as an Element (Mandatory)
   * @param metadata              the Sealed Message metadata (Mandatory)
   * @param privateCredentials    the sender's private key and certificate (Mandatory)
   * @param encryptionCertificate the receiver's certificate (Mandatory)
   *
   * @return sealedMessage a valid sealed message.
   *
   * @throws IOException          if the XML payload cannot be serialised into a String due to an I/O error.
   * @throws TransformerException if the XML payload cannot be serialised into a String due to an XML Transformation error..
   */
  public static SealedMessageType getSealedMessage(Element unencryptedXmlPayload,
                                                   MessageMetadataType metadata,
                                                   List<X500PrivateCredential> privateCredentials,
                                                   X509Certificate encryptionCertificate) throws IOException, TransformerException {

    String unencryptedXmlPayloadString = DomUtils.serialiseToString(unencryptedXmlPayload);
    return getSealedMessage(unencryptedXmlPayloadString, metadata, privateCredentials, encryptionCertificate);
  }
}
