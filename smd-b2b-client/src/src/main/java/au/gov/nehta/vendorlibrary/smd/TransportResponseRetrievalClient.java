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
import au.gov.nehta.vendorlibrary.ws.WebServiceClientUtil;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.net.electronichealth.ns.trr.tls.v2010.RemoveErrorMsg;
import au.net.electronichealth.ns.trr.tls.v2010.RemoveResultType;
import au.net.electronichealth.ns.trr.tls.v2010.RetrieveErrorMsg;
import au.net.electronichealth.ns.trr.tls.v2010.StandardErrorMsg;
import au.net.electronichealth.ns.trr.tls.v2010.TransportResponseListType;
import au.net.electronichealth.ns.trr.tls.v2010.TransportResponseRetrieval;
import au.net.electronichealth.ns.trr.tls.v2010.TransportResponseRetrievalService;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An implementation of a Transport Response Retrieval client.
 * <br>
 * Code Example: <br>
 * <pre>
 *     TransportResponseRetrievalClient trrClient = new TransportResponseRetrievalClient(sslSocketFactory);
 *
 * List<RemoveResultType> removeResultTypes = trrClient.remove(forceRemoval, trrReceiverOrganisation, trasnportResponseIdList,
 * trrEndpointURL);
 *
 * TransportResponseListType transportResponseListType = trrClient.retrieve(trrReceiverOrganisation, allAvailable, limit,
 * trrEndpointURL);
 *
 *     </pre>
 */
public class TransportResponseRetrievalClient {

  /**
   * Validates parameters are correct for the various operations.
   */
  public static final class ArgumentValidator {

    //Default private constructor for ArgumentValidator
    private ArgumentValidator() {

    }

    /**
     * Ensures that a responseId optional list is valid if provided.
     *
     * @param responseIdList the responseIdList list to be validated (Optional)
     */
    public void checkOptionalResponseId(List<String> responseIdList) {

      if (responseIdList != null && responseIdList.size() > 0) {
        for (String responseId : responseIdList) {
          ArgumentUtils.checkNotNullNorBlank(responseId, "responseId");
        }
      }
    }
  }


  /**
   * Constant for Blank String
   */
  private static final String EMPTY = "";

  /**
   * Validates the arguments passed for the various operations
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
   * The Web Services port for the Transport Response Retrieval service
   */
  private TransportResponseRetrieval transportResponseRetrievalPort;

  /**
   * Default constructor.
   *
   * @param sslSocketFactory the SSL Socket Factory to be used when connecting to the Web Service provider (Mandatory)
   */
  public TransportResponseRetrievalClient(SSLSocketFactory sslSocketFactory) {

    ArgumentUtils.checkNotNull(sslSocketFactory, "sslSocketFactory");

    this.loggingHandler = new LoggingHandler(false);
    List<Handler> handlerChain = new ArrayList<Handler>();
    handlerChain.add(loggingHandler);
    this.transportResponseRetrievalPort = WebServiceClientUtil.getPort(TransportResponseRetrieval.class,
        TransportResponseRetrievalService.class,
        sslSocketFactory,
        handlerChain);
  }


  /**
   * Performs the Transport Response Retrieval retrieve operation.
   *
   * @param organisation the invoker's organisation identifier (Mandatory)
   * @param allAvailable flag to retrieve previously removed transport responses if desired.
   * @param limit        zero or a positive integer to indicate the maximum number of items to retrieve.
   * @param endpoint     the Transport Response Retrieval (TRR) endpoint (Mandatory)
   * @return a list of   TransportResponseListType for a successful retrieval
   * @throws RetrieveErrorMsg if an error occurred in processing the retrieve request
   * @throws StandardErrorMsg in an event of error other than RetrieveErrorMsg
   */
  public final TransportResponseListType retrieve(String organisation, boolean allAvailable, int limit,
                                                  String endpoint) throws RetrieveErrorMsg, StandardErrorMsg {
    ArgumentUtils.checkNotNullNorBlank(organisation, "organisation");
    ArgumentUtils.checkNotNullNorBlank(endpoint, "endpoint");
    configurePort(endpoint);

    return transportResponseRetrievalPort.retrieve(organisation, allAvailable, limit);
  }

  /**
   * Performs the Transport Response Retrieval remove operation.
   *
   * @param force        flag to force removal
   * @param organisation the invoker's organisation identifier (Mandatory)
   * @param responseId   list of responseId to be removed (Mandatory)
   * @param endpoint     the Transport Response Retrieval (TRR) endpoint (Mandatory)
   * @return List<RemoveResultType> if the web service invocation is successful.
   * @throws RemoveErrorMsg   if one ore more responseId do not identify a TransportResponse
   * @throws StandardErrorMsg if the web service invocation fails due to a standard error
   */
  public final List<RemoveResultType> remove(final boolean force, final String organisation,
                                             final List<String> responseId, final String endpoint)
    throws RemoveErrorMsg, StandardErrorMsg {

    ArgumentUtils.checkNotNullNorBlank(organisation, "organisation");
    ArgumentUtils.checkNotNullNorBlank(endpoint, "endpoint");
    argumentValidator.checkOptionalResponseId(responseId);
    configurePort(endpoint);
    
    return transportResponseRetrievalPort.remove(force, organisation, responseId);
  }


  /**
   * Configured the Transport Response Retrieval port with the correct endpoint.
   *
   * @param endpoint the endpoint (Mandatory)
   */
  private void configurePort(String endpoint) {
    ArgumentUtils.checkNotNullNorBlank(endpoint, "endpoint");

    final BindingProvider bindingProvider = (BindingProvider) transportResponseRetrievalPort;
    final Map<String, Object> requestContext = bindingProvider.getRequestContext();

    //Set the endpoint address a ClientContextProperties
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

