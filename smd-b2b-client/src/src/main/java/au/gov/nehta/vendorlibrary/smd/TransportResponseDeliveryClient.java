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
import au.gov.nehta.xsp.XspException;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.MessageMetadataType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.OtherTransportMetadataEntryType;
import au.net.electronichealth.ns.smd.types.sealedmessage.V2010.SealedMessageType;
import au.net.electronichealth.ns.smd.xsd.transportresponse._2010.DeliveryResponseType;
import au.net.electronichealth.ns.smd.xsd.transportresponse._2010.ResponseClassType;
import au.net.electronichealth.ns.smd.xsd.transportresponse._2010.TransportResponseMetadataType;
import au.net.electronichealth.ns.smd.xsd.transportresponse._2010.TransportResponseType;
import au.net.electronichealth.ns.trd.tls.v2010.DeliverErrorMsg;
import au.net.electronichealth.ns.trd.tls.v2010.DeliverStatusType;
import au.net.electronichealth.ns.trd.tls.v2010.StandardErrorMsg;
import au.net.electronichealth.ns.trd.tls.v2010.TransportResponseDelivery;
import au.net.electronichealth.ns.trd.tls.v2010.TransportResponseDeliveryService;
import org.w3c.dom.Document;

import javax.net.ssl.SSLSocketFactory;
import javax.security.auth.x500.X500PrivateCredential;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * An implementation of a Transport Response Delivery client.
 * <br>
 * Code Example:<br>
 * <pre>
 *   TransportResponseDeliveryClient trdClient = new TransportResponseDeliveryClient(sslSocketFactory);
 *
 *   TransportResponseMetadataType transportResponseMetadataType = getTransportResponseMetadataType(MessageMetadataType,
 * sourceOrganisation, transportResponseTime);
 *
 * DeliverStatusType deliverStatusType = trdClient.deliver(transportResponseMetadataTypeList, trdEndpointURL);
 *
 * </pre>
 */
public class TransportResponseDeliveryClient {

  /**
   * Validates that the parameters are correct for the various operations.
   */
  public static final class ArgumentValidator {

    //Default private constructor for ArgumentValidator
    private ArgumentValidator() {

    }

    /**
     * Ensures that a responseId optional list is valid if provided.
     *
     * @param transportResponseTypeList the transport responseType
     *                                  list to be validated (Mandatory)
     */
    public void checkTransportResponseTypeList(List<TransportResponseType> transportResponseTypeList) {

      ArgumentUtils.checkNotNull(transportResponseTypeList, "transportResponseTypeList");
      for (TransportResponseType transportResponseType : transportResponseTypeList) {
        checkTransportResponseType(transportResponseType);
      }
    }

    /**
     * Ensures that a transportResponseMetadataType is valid
     *
     * @param transportResponseMetadataType the metadata for the transport response (Mandatory)
     */
    private void checkTransportResponseMetadataType(final TransportResponseMetadataType transportResponseMetadataType) {
      ArgumentUtils.checkNotNull(transportResponseMetadataType, "transportResponseMetadataType");
      ArgumentUtils.checkNotNull(transportResponseMetadataType.getTransportResponseTime(), "Transport response Time");
      ArgumentUtils.checkNotNullNorBlank(transportResponseMetadataType.getInvocationId(), "Invocation ID");
      ArgumentUtils.checkNotNullNorBlank(transportResponseMetadataType.getReceiverOrganisation(), "Receiver Organisation");
      ArgumentUtils.checkNotNullNorBlank(transportResponseMetadataType.getSenderOrganisation(), "Sender Organisation");
      ArgumentUtils.checkNotNullNorBlank(transportResponseMetadataType.getSourceOrganisation(), "Source Organisation");
      ArgumentUtils.checkNotNullNorBlank(transportResponseMetadataType.getServiceCategory(), "Service Category");
      ArgumentUtils.checkNotNullNorBlank(transportResponseMetadataType.getResponseId(), "Response");
    }


    /**
     * Ensures that a transportResponseType is valid
     *
     * @param transportResponseType the response type for a transport response metadata (Mandatory)
     */
    private void checkTransportResponseType(final TransportResponseType transportResponseType) {
      ArgumentUtils.checkNotNull(transportResponseType, "transportResponseType");

      TransportResponseMetadataType metadata = transportResponseType.getMetadata();
      DeliveryResponseType deliveryResponseType = transportResponseType.getDeliveryResponse();

      ArgumentUtils.checkNotNull(deliveryResponseType, " Deliver Response");
      checkTransportResponseMetadataType(metadata);

      checkOtherTransportMetadataEntryType(metadata.getOtherTransportMetadata());
    }

    /**
     * Ensures that the provided otherTransportMetadataEntryTypes optional field is valid if provided
     *
     * @param otherTransportMetadataEntryTypes
     *         the other transport metadata for a transport response metadata (Mandatory)
     */
    private void checkOtherTransportMetadataEntryType(
      final List<OtherTransportMetadataEntryType> otherTransportMetadataEntryTypes) {
      //Optional field
      if (otherTransportMetadataEntryTypes != null && otherTransportMetadataEntryTypes.size() > 0) {
        for (OtherTransportMetadataEntryType otherTransportMetadataEntryType : otherTransportMetadataEntryTypes) {
          ArgumentUtils.checkNotNull(otherTransportMetadataEntryType, "Other Transport Metadata Entry");
          ArgumentUtils.checkNotNull(otherTransportMetadataEntryType.getMetadataValue(),
            "Other Transport Metadata Entry Value");
          ArgumentUtils.checkNotNullNorBlank(otherTransportMetadataEntryType.getMetadataType(),
            "Other Transport Metadata Entry Type");
        }
      }

    }

  }

  /**
   * Constant for Blank String.
   */
  private static final String EMPTY = "";

  /**
   * Constant for UUID prefix.
   */
  private static final String URN_PREFIX = "urn:uuid:";
  /**
   * Validates the arguments passed for the various operations.
   */
  private ArgumentValidator argumentValidator = new ArgumentValidator();

  /**
   * The Logging handler instance for logging SOAP request and Response messages.
   */
  private LoggingHandler loggingHandler;

  /**
   * The SOAP request message corresponding to the most recent web service invocation
   */
  private String lastSoapRequest;

  /**
   * The SOAP response message corresponding to the most recent web service invocation
   */
  private String lastSoapResponse;

  /**
   * The Web Services port for the Transport Response Delivery service
   */
  private TransportResponseDelivery transportResponseDeliveryPort;

  /**
   * Default constructor.
   *
   * @param sslSocketFactory the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
   */
  public TransportResponseDeliveryClient(SSLSocketFactory sslSocketFactory) {

    ArgumentUtils.checkNotNull(sslSocketFactory, "sslSocketFactory");
    this.loggingHandler = new LoggingHandler(false);
    List<Handler> handlerChain = new ArrayList<Handler>();
    handlerChain.add(loggingHandler);
    this.transportResponseDeliveryPort = WebServiceClientUtil.getPort(TransportResponseDelivery.class,
      TransportResponseDeliveryService.class,
      sslSocketFactory,
      handlerChain);
  }


  /**
   * Creates a valid TransportResponseType with the provided Sealed message and transport response attributes.
   *
   * @param sealedMessageType       the sealed Message from corresponding Sealed Message (Mandatory)
   * @param responseClassType       the transport response responseClass type (Mandatory)
   * @param deliveryResponseCode    the transport response responseCode (Mandatory)
   * @param deliveryResponseMessage the transport response sensitive payload  (Mandatory)
   * @param transportResponseTime   the transport response timestamp (Mandatory)
   * @param responseId              the the transport response responseId (Mandatory)
   * @param isFinal                 boolean flag to indicate final transport response (Mandatory)
   * @param sourceOrganisation      the transport response source organisation (Mandatory)
   * @param x500PrivateCredential   the Sealed message payload decrypting credentials(Mandatory)
   * @return TransportResponseType instance.
   * @throws XspException if the provided Sealed message payload / decrypting credentials are invalid
   */
  public static TransportResponseType getTransportResponseType(
    final SealedMessageType sealedMessageType, final ResponseClassType responseClassType,
    final String deliveryResponseCode, final String deliveryResponseMessage, final XMLGregorianCalendar transportResponseTime,
    final String responseId, boolean isFinal, final String sourceOrganisation,
    final X500PrivateCredential x500PrivateCredential) throws XspException {

    new SealedMessageDeliveryClient.ArgumentValidator().checkSealedMessage(sealedMessageType);
    ArgumentUtils.checkNotNull(x500PrivateCredential, "x500PrivateCredential");
    ArgumentUtils.checkNotNull(responseClassType, "responseClassType");
    ArgumentUtils.checkNotNullNorBlank(deliveryResponseMessage, "deliveryResponseMessage");
    ArgumentUtils.checkNotNullNorBlank(sourceOrganisation, "sourceOrganisation");

    MessageMetadataType messageMetadataType = sealedMessageType.getMetadata();

    byte[] digest = getDigestValueFromSealedMessage(sealedMessageType, x500PrivateCredential);

    TransportResponseType transportResponse = new TransportResponseType();
    transportResponse.setFinal(isFinal);
    transportResponse.setDeliveryResponse(getDeliveryResponseType(digest, responseClassType, deliveryResponseCode));
    transportResponse.setMetadata(getTransportResponseMetadataType(messageMetadataType, sourceOrganisation,
      transportResponseTime));
    return transportResponse;
  }


  /**
   * Verifies a transport response message and metadata for the provided Sealed Message Delivery and transport
   * response attributes.
   *
   * @param transportResponseType   the transport response to be verified (Mandatory)
   * @param sealedMessageType       the sealed message type from the corresponding Sealed Message (Mandatory)
   * @param responseClassType       the transport response responseClass type (Mandatory)
   * @param sourceOrganisation      the transport response source organisation (Mandatory)
   * @param x500PrivateCredential   the Sealed message payload decrypting credentials(Mandatory)
   * @param deliveryResponseMessage the transport response message (Mandatory)
   * @param deliveryResponseCode    the transport response code (Mandatory)
   * @throws XspException if the provided Sealed message payload / decrypting credentials are invalid
   */
  public static void verifyTransportResponseType(
    final TransportResponseType transportResponseType, final SealedMessageType sealedMessageType,
    final ResponseClassType responseClassType, final String sourceOrganisation,
    final X500PrivateCredential x500PrivateCredential, final String deliveryResponseMessage,
    final String deliveryResponseCode) throws XspException {

    ArgumentUtils.checkNotNull(responseClassType, "responseClassType");
    ArgumentValidator argumentValidator = new ArgumentValidator();
    SealedMessageDeliveryClient.ArgumentValidator sealedMessageArgumentValidator =
      new SealedMessageDeliveryClient.ArgumentValidator();
    argumentValidator.checkTransportResponseType(transportResponseType);
    sealedMessageArgumentValidator.checkSealedMessage(sealedMessageType);

    verifyTransportResponseWithSealedMessage(transportResponseType, sealedMessageType, sourceOrganisation);

    verifyDeliveryResponseType(transportResponseType.getDeliveryResponse(),
      getDigestValueFromSealedMessage(sealedMessageType, x500PrivateCredential), deliveryResponseMessage,
      deliveryResponseCode);
  }


  /**
   * Returns the digest value of the sealedMessageType payload.
   *
   * @param sealedMessageType     the sealed message instance (Mandatory)
   * @param x500PrivateCredential the Sealed message payload decrypting credentials(Mandatory)
   * @return digest value from the sealedMessageType XSP payload
   * @throws XspException if the provided Sealed message payload / decrypting credentials are invalid
   */
  public static byte[] getDigestValueFromSealedMessage(
    final SealedMessageType sealedMessageType, final X500PrivateCredential x500PrivateCredential) throws
    XspException {
    byte[] digest = null;
    Document decryptedSignedPayload = EncryptedContainerProfileUtil.getDecryptedPayload(
      sealedMessageType.getEncryptedPayload().getOwnerDocument(), x500PrivateCredential);
    List<byte[]> digests = SignedContainerProfileUtil.getDigestValue(decryptedSignedPayload);
    digest = digests.get(0);
    return digest;
  }

  /**
   * Verifies a transport response with the provided Sealed Message Delivery.
   *
   * @param transportResponseType the transport response to be verified (Mandatory)
   * @param sealedMessageType     the sealed message type from the corresponding Sealed Message (Mandatory)
   * @param sourceOrganisation    the transport response source organisation (Mandatory)
   */
  public static void verifyTransportResponseWithSealedMessage(
    final TransportResponseType transportResponseType,
    final SealedMessageType sealedMessageType,
    final String sourceOrganisation) {

    //Validation calls
    ArgumentValidator argumentValidator = new ArgumentValidator();
    SealedMessageDeliveryClient.ArgumentValidator sealedMessageArgumentValidator =
      new SealedMessageDeliveryClient.ArgumentValidator();
    TransportResponseMetadataType transportResponseMetadataType = transportResponseType.getMetadata();
    MessageMetadataType sealedMessageMetadataType = sealedMessageType.getMetadata();
    argumentValidator.checkTransportResponseMetadataType(transportResponseMetadataType);
    sealedMessageArgumentValidator.checkSealedMessage(sealedMessageType);

    if (!ArgumentUtils.isEqual(transportResponseMetadataType.getInvocationId(),
      sealedMessageMetadataType.getInvocationId())) {
      throw new IllegalArgumentException("transport response metadata InvocationId must match "
        + "sealed message metadata InvocationId. Found " + transportResponseMetadataType.getInvocationId()
        + " instead of " + sealedMessageMetadataType.getInvocationId());
    }
    if (!ArgumentUtils.isEqual(transportResponseMetadataType.getReceiverOrganisation(),
      sealedMessageMetadataType.getReceiverOrganisation())) {
      throw new IllegalArgumentException("transport response metadata ReceiverOrganisation must match "
        + "sealed message metadata ReceiverOrganisation. Found "
        + transportResponseMetadataType.getReceiverOrganisation()
        + " instead of " + sealedMessageMetadataType.getReceiverOrganisation());
    }
    if (
      !ArgumentUtils.isEqual(transportResponseMetadataType.getSenderOrganisation(),
        sealedMessageMetadataType.getSenderOrganisation())) {
      throw new IllegalArgumentException("transport response metadata SenderOrganisation must match "
        + "sealed message metadata SenderOrganisation. Found " + transportResponseMetadataType.getSenderOrganisation()
        + " instead of " + sealedMessageMetadataType.getSenderOrganisation());
    }
    if (
      !ArgumentUtils.isEqual(transportResponseMetadataType.getServiceCategory(),
        sealedMessageMetadataType.getServiceCategory())) {
      throw new IllegalArgumentException("transport response metadata ServiceCategory must match "
        + "sealed message metadata ServiceCategory. Found " + transportResponseMetadataType.getServiceCategory()
        + " instead of " + sealedMessageMetadataType.getServiceCategory());
    }
    if (
      !ArgumentUtils.isEqual(transportResponseMetadataType.getSourceOrganisation(),
        sourceOrganisation)) {
      throw new IllegalArgumentException("transport response metadata SourceOrganisation must match "
        + "the SourceOrganisation. Found " + transportResponseMetadataType.getSourceOrganisation()
        + " instead of " + sourceOrganisation);
    }
  }

  /**
   * Verifies a transport Delivery Response for the provided transport response artifacts.
   *
   * @param deliveryResponseType     the transport delivery response to be verified (Mandatory)
   * @param sealedMessageDigestValue the digest value from the corresponding sealed message (Mandatory)
   * @param deliveryResponseMessage  the transport delivery response message to check against deliveryResponseType (Mandatory)
   * @param deliveryResponseCode     the transport response code to check against deliveryResponseCode (Mandatory)
   */
  public static void verifyDeliveryResponseType(
    final DeliveryResponseType deliveryResponseType, final byte[] sealedMessageDigestValue,
    final String deliveryResponseMessage, final String deliveryResponseCode) {
    ArgumentUtils.checkNotNull(deliveryResponseType, "deliveryResponseType");

    if (!Arrays.equals(deliveryResponseType.getDigestValue(), sealedMessageDigestValue)) {
      throw new IllegalArgumentException("delivery response digest value must match "
        + "the sealed message digest value. Found " + new String(deliveryResponseType.getDigestValue())
        + " instead of " + new String(sealedMessageDigestValue));
    }
    if (
      !ArgumentUtils.isEqual(deliveryResponseType.getMessage(),
        deliveryResponseMessage)) {
      throw new IllegalArgumentException("delivery response message value must match "
        + "the deliveryResponseMessage value. Found " + deliveryResponseType.getMessage()
        + " instead of " + deliveryResponseMessage);
    }
    if (
      !ArgumentUtils.isEqual(deliveryResponseType.getResponseCode().toString(),
        deliveryResponseCode)) {
      throw new IllegalArgumentException("delivery response code value must match "
        + "the deliveryResponseCode value. Found " + deliveryResponseType.getResponseCode()
        + " instead of " + deliveryResponseCode);
    }
  }

  /**
   * Returns Transport ResponseMetadataType for the provided sealed messageMetaDataType.
   *
   * @param messageMetadataType   the sealed messageMetadataType instance from the Sealed Message (Mandatory)
   * @param sourceOrganisation    the transport response source organisation (Mandatory)
   * @param transportResponseTime the transport response timestamp (Mandatory)
   * @return TransportResponseMetadataType  instance
   */
  public static TransportResponseMetadataType getTransportResponseMetadataType(
    final MessageMetadataType messageMetadataType, final String sourceOrganisation,
    final XMLGregorianCalendar transportResponseTime) {
    TransportResponseMetadataType transportResponseMetadataType = new TransportResponseMetadataType();
    transportResponseMetadataType.setInvocationId(messageMetadataType.getInvocationId());
    transportResponseMetadataType.setResponseId(URN_PREFIX + UUID.randomUUID().toString());

    transportResponseMetadataType.setTransportResponseTime(transportResponseTime);

    transportResponseMetadataType.setReceiverOrganisation(messageMetadataType.getReceiverOrganisation());
    transportResponseMetadataType.setSenderOrganisation(messageMetadataType.getSenderOrganisation());
    transportResponseMetadataType.setSourceOrganisation(sourceOrganisation);
    transportResponseMetadataType.setServiceCategory(messageMetadataType.getServiceCategory());
    return transportResponseMetadataType;
  }

  /**
   * Returns DeliveryResponseType for the provided digest and transport response artifacts.
   *
   * @param digest            the  SealedMessage digest value (Mandatory)
   * @param responseClassType the ResponseClassType to be in the transport response (Mandatory)
   * @param responseCode      the response code to the transport response (Mandatory)
   * @return DeliveryResponseType  instance
   */
  public static DeliveryResponseType getDeliveryResponseType(
    final byte[] digest, final ResponseClassType responseClassType, final String responseCode) {
    DeliveryResponseType deliveryResponseType = new DeliveryResponseType();
    deliveryResponseType.setDigestValue(digest);
    deliveryResponseType.setResponseClass(responseClassType);
    deliveryResponseType.setResponseCode(responseCode);
    deliveryResponseType.setMessage(responseClassType.value());
    return deliveryResponseType;
  }


  /**
   * Performs the Transport Response Delivery deliver operation.
   *
   * @param transportResponseTypeList the list of Transport Response to be delivered (Mandatory)
   * @param endpoint                  the Transport Response Delivery (TRD) endpoint (Mandatory)
   * @return DeliverStatusType        if the web service invocation is successful.
   * @throws StandardErrorMsg if the web service invocation fails due to a standard error.
   * @throws DeliverErrorMsg  if the web service invocation fails due to a deliver error.
   */
  public final DeliverStatusType deliver(List<TransportResponseType> transportResponseTypeList,
                                         String endpoint) throws StandardErrorMsg, DeliverErrorMsg {

    ArgumentUtils.checkNotNull(transportResponseTypeList, "transportResponseTypeList");
    argumentValidator.checkTransportResponseTypeList(transportResponseTypeList);
    configurePort(endpoint);

    return transportResponseDeliveryPort.deliver(transportResponseTypeList);
  }

  /**
   * Configured the Transport Response Delivery port with the correct endpoint.
   *
   * @param endpoint the endpoint (Mandatory)
   */
  private void configurePort(String endpoint) {
    ArgumentUtils.checkNotNullNorBlank(endpoint, "endpoint");

    final BindingProvider bindingProvider = (BindingProvider) transportResponseDeliveryPort;
    final Map<String, Object> requestContext = bindingProvider.getRequestContext();

    //Set the endpoint address a ClientContextPoperties
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
      return EMPTY;
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
      return EMPTY;
    }
  }

}
