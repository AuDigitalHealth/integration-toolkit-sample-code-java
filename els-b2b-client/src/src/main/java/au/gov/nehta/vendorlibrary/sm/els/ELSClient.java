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
package au.gov.nehta.vendorlibrary.sm.els;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.vendorlibrary.ws.WebServiceClientUtil;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.net.electronichealth.els.datatypes.v2010.ElsCertRefType;
import au.net.electronichealth.els.datatypes.v2010.InteractionRequestType;
import au.net.electronichealth.els.datatypes.v2010.InteractionType;
import au.net.electronichealth.els.operation.lookup.v2010.Lookup;
import au.net.electronichealth.els.operation.lookup.v2010.LookupErrorMsg;
import au.net.electronichealth.els.operation.lookup.v2010.LookupService;
import au.net.electronichealth.els.operation.publish.v2010.Publish;
import au.net.electronichealth.els.operation.publish.v2010.PublishErrorMsg;
import au.net.electronichealth.els.operation.publish.v2010.PublishReturnCodeType;
import au.net.electronichealth.els.operation.publish.v2010.PublishService;
import au.net.electronichealth.els.operation.publish.v2010.StandardErrorMsg;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.handler.Handler;
import java.util.List;
import java.util.ArrayList;

/**
 * An implementation of a client for the Endpoint Location Service (ELS) client. This class may be used
 * to connect to an ELS Server to do lookups and validations of interactions, or to publish interactions.
 * <br/><br/>
 * <b>Example client instantiation:</b>
 * <br/>
 * <pre>
   // "Lookup Only" mode:
   final String ELS_LOOKUP_ENDPOINT_URL = "https://www.example.com/els/Lookup";
   ELSClient testClient = new ELSClient(ELS_LOOKUP_ENDPOINT_URL, sslSocketFactory);

   // "Lookup and Publish" mode:
   final String ELS_LOOKUP_ENDPOINT_URL = "https://www.example.com/els/Lookup";
   final String ELS_PUBLISH_ENDPOINT_URL = "https://www.example.com/els/Publish";
   ELSClient testClient = new ELSClient(ELS_LOOKUP_ENDPOINT_URL, ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);
   </pre>
 */
public class ELSClient {
  /**
   * Validates parameters are correct for the various operations.
   */
  public static class ArgumentValidator {
    /**
     * Ensures that an interaction is valid.
     *
     * @param interaction The interaction being checked (Mandatory)
     */
    public final void checkInteraction(InteractionType interaction) {

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
          ArgumentUtils.checkNotNull(certRef.getQualifiedCertRef(), "Certificare reference: qualified cert ref");
          ArgumentUtils.checkNotNullNorBlank(certRef.getQualifiedCertRef().getType(), "Certificare reference: qualified cert ref type");
          ArgumentUtils.checkNotNullNorBlank(certRef.getQualifiedCertRef().getValue(), "Certificare reference: qualified cert ref value");
        }
      }
    }

    /**
     * Ensures that an interaction request is valid.
     *
     * @param interactionRequest The interaction request being checked (Mandatory)
     */
    public final void checkInteractionRequest(InteractionRequestType interactionRequest) {
      ArgumentUtils.checkNotNull(interactionRequest, "Interaction Request");
      ArgumentUtils.checkNotNullNorBlank(interactionRequest.getTarget(), "target");

      ArgumentUtils.checkNotNullNorEmpty(interactionRequest.getServiceCategory(), "service categories");
      for (String serviceCategory : interactionRequest.getServiceCategory()) {
        ArgumentUtils.checkNotNullNorBlank(serviceCategory, "Interaction request: service category");
      }

      ArgumentUtils.checkNotNullNorEmpty(interactionRequest.getServiceInterface(), "service interfaces");
      if (interactionRequest.getServiceInterface().size() > 0) {
        for (String serviceInterface : interactionRequest.getServiceInterface()) {
          ArgumentUtils.checkNotNullNorBlank(serviceInterface, "Interaction request: service interface");
        }
      }
    }
  }

  /**
   * Validates the arguments passed for the various operations
   */
  private ArgumentValidator argumentValidator = new ArgumentValidator();

  /**
   * The Web Services port for the ELS Lookup service
   */
  private Lookup elsLookupPort;

  /**
   * The Web Services port for the ELS Lookup service
   */
  private Publish elsPublishPort;

  /**
   * Flag to indicate the mode the the client has been initialised with. Lookup-only mode (when set to <i>true</i>, or
   * Lookup-and-publish mode (when set to <i>false</i>.
   */
  private boolean lookupOnly = true;

  /**
   * The Logging handler instance for logging SOAP request and Response messages.
   */
  private LoggingHandler loggingHandler;

  /**
   * Constructor for Lookup-only mode.
   *
   * @param elsLookupEndpoint the Web Service endpoint for the ELS Server's lookup interface (Mandatory)
   * @param sslSocketFactory the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
   */
  public ELSClient(final String elsLookupEndpoint,
                   final SSLSocketFactory sslSocketFactory) {

    ArgumentUtils.checkNotNullNorBlank(elsLookupEndpoint, "elsLookupEndpoint");
    ArgumentUtils.checkNotNull(sslSocketFactory, "sslSocketFactory may not be null");

    this.lookupOnly = true;
    this.loggingHandler = new LoggingHandler(false);
    List<Handler> handlerChain = new ArrayList<Handler>();
    handlerChain.add(loggingHandler);
    this.elsLookupPort = WebServiceClientUtil.getPort(Lookup.class,
                                                      LookupService.class,
                                                      sslSocketFactory,
                                                      elsLookupEndpoint,
                                                      handlerChain);
  }

  /**
   * Constructor for Lookup-and-publish mode.
   *
   * @param elsLookupEndpoint the Web Service endpoint for the ELS Server's Lookup interface (Mandatory)
   * @param elsPublishEndpoint the Web Service endpoint for the ELS Server's Publish interface (Mandatory for Lookup-and-pushlish mode, else Optional)
   * @param sslSocketFactory the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
   */
  public ELSClient(final String elsLookupEndpoint,
                   final String elsPublishEndpoint,
                   final SSLSocketFactory sslSocketFactory) {

    ArgumentUtils.checkNotNullNorBlank(elsLookupEndpoint, "elsLookupEndpoint");
    ArgumentUtils.checkNotNullNorBlank(elsPublishEndpoint, "elsPublishEndpoint");
    ArgumentUtils.checkNotNull(sslSocketFactory, "sslSocketFactory may not be null");

    this.lookupOnly = false;
    this.loggingHandler = new LoggingHandler(false);
    List<Handler> handlerChain = new ArrayList<Handler>();
    handlerChain.add(loggingHandler);
    this.elsPublishPort = WebServiceClientUtil.getPort(Publish.class,
                                                       PublishService.class,
                                                       sslSocketFactory,
                                                       elsPublishEndpoint,
                                                       handlerChain);

    this.elsLookupPort = WebServiceClientUtil.getPort(Lookup.class,
                                                      LookupService.class,
                                                      sslSocketFactory,
                                                      elsLookupEndpoint,
                                                      handlerChain);
  }

  /**
   * Queries an ELS Server for interactions for a target organisation.
   *
   * @param interactionRequest The interaction request object (Mandatory)
   *
   * @return a List of matching interactions (if any)
   *
   * @throws au.net.electronichealth.els.operation.lookup.v2010.LookupErrorMsg when a Web Service LookupErrorMsg SOAP Fault occurs
   * @throws au.net.electronichealth.els.operation.lookup.v2010.StandardErrorMsg when a Web Service StandardErrorMsg SOAP Fault occurs
   */
  public final List<InteractionType> listInteractions(InteractionRequestType interactionRequest)
    throws LookupErrorMsg, au.net.electronichealth.els.operation.lookup.v2010.StandardErrorMsg {
    // Check input parameters
    argumentValidator.checkInteractionRequest(interactionRequest);

    // Invoke Web Service call
    return elsLookupPort.listInteractions(interactionRequest);
  }

  /**
   * Queries an ELS Server to validate an interaction.
   *
   * @param interaction The interaction bject (Mandatory)
   *
   * @return <i>true</i> if the interaction is valid, otherwise <i>false</i>
   *
   * @throws au.net.electronichealth.els.operation.lookup.v2010.LookupErrorMsg when a Web Service LookupErrorMsg SOAP Fault occurs
   * @throws au.net.electronichealth.els.operation.lookup.v2010.StandardErrorMsg when a Web Service StandardErrorMsg SOAP Fault occurs
   */
  public final boolean validateInteraction(InteractionType interaction)
    throws LookupErrorMsg, au.net.electronichealth.els.operation.lookup.v2010.StandardErrorMsg {
    // Check input parameters
    argumentValidator.checkInteraction(interaction);

    // Invoke Web Service call
    return elsLookupPort.validateInteraction(interaction);
  }

  /**
   * Adds an interaction to an ELS Server.
   *
   * @param interaction The interaction to add (Mandatory)
   *
   * @return the outcome of the web service operation as one of <i>PublishReturnCodeType.OK</i>,
   *         <i>PublishReturnCodeType.DUPLICATE</i> or <i>PublishReturnCodeType.NOT_FOUND</i>
   *
   * @throws PublishErrorMsg when a Web Service LookupErrorMsg SOAP Fault occurs
   * @throws au.net.electronichealth.els.operation.publish.v2010.StandardErrorMsg when a Web Service StandardErrorMsg SOAP Fault occurs
   */
  public final PublishReturnCodeType addInteraction(InteractionType interaction)
    throws PublishErrorMsg, StandardErrorMsg {
    if (!this.lookupOnly) {
      argumentValidator.checkInteraction(interaction);
      return elsPublishPort.addInteraction(interaction);
    } else {
      throw new UnsupportedOperationException("This client is in Lookup-only mode and thus publishing operations are not allowed");
    }
  }

  /**
   * Removes an interaction from an ELS Server.
   *
   * @param interaction The interaction to be removed. (Mandatory)
   *
   * @return the outcome of the web service operation as one of <i>PublishReturnCodeType.OK</i>,
   *         <i>PublishReturnCodeType.DUPLICATE</i> or <i>PublishReturnCodeType.NOT_FOUND</i>
   *
   * @throws PublishErrorMsg when a Web Service LookupErrorMsg SOAP Fault occurs
   * @throws au.net.electronichealth.els.operation.publish.v2010.StandardErrorMsg when a Web Service StandardErrorMsg SOAP Fault occurs
   */
  public final PublishReturnCodeType removeInteraction(InteractionType interaction)
    throws PublishErrorMsg, StandardErrorMsg {
    if (!this.lookupOnly) {
      argumentValidator.checkInteraction(interaction);
      return elsPublishPort.removeInteraction(interaction);
    } else {
      throw new UnsupportedOperationException("This client is in Lookup-only mode and thus publishing operations are not allowed");
    }
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
