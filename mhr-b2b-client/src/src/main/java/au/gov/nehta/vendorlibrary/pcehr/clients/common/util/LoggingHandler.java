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
package au.gov.nehta.vendorlibrary.pcehr.clients.common.util;


import java.io.ByteArrayOutputStream;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.ws.handler.MessageContext;

import com.sun.xml.ws.api.handler.MessageHandler;
import com.sun.xml.ws.api.handler.MessageHandlerContext;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.streaming.XMLStreamWriterFactory;

/**
 * This SOAP handler logs incoming and outgoing SOAP messages. If a SOAP handler
 * is specified in the handler chain of a JAX-WS client or service, JAX-WS will
 * call the SOAP handler before a SOAP message is sent (request) and after a
 * SOAP message has been received (response).<br>
 */
public final class LoggingHandler implements MessageHandler<MessageHandlerContext> {

  /**
   * Constant for empty string.
   */
  public static final String EMPTY = "";

  /**
   * Character set encoding to use when serialising the SOAP XML.
   */
  public static final String ENCODING = "utf-8";

  /**
   * Logger instance for Logging messages to log outputstream
   */
  private static final Logger LOG = Logger.getLogger(LoggingHandler.class
    .getName());


  /**
   * The SOAP request message corresponding to the most recent web service invocation
   */
  private String lastSoapRequest;

  /**
   * The SOAP response message corresponding to the most recent web service invocation
   */
  private String lastSoapResponse;

  private boolean dump;

  /**
   * Default constructor.
   *
   * @param dump whether or not logging is required.
   */
  public LoggingHandler(boolean dump) {
    this.dump = dump;
  }

  /**
   * Getter for lastSoapResponse.
   *
   * @return lastSoapResponse the lastSoapResponse instance variable
   */
  public String getLastSoapResponse() {
    return lastSoapResponse;
  }

  /**
   * Getter for lastSoapRequest.
   *
   * @return lastSoapRequest the lastSoapRequest instance variable (Mandatory)
   */
  public String getLastSoapRequest() {
    return lastSoapRequest;
  }


  /**
   * Logs outgoing and incoming messages.
   *
   * @param context The SOAP Message context.
   * @return {@link Boolean}.TRUE handle message is successful else return
   *         FALSE
   * @see javax.xml.ws.handler.Handler#handleMessage(javax.xml.ws.handler.MessageContext)
   */
  public boolean handleMessage(final MessageHandlerContext context) {
    // log to SOAP message to console
    logSOAPMessage(context);
    return true; // Continue processing
  }

  /**
   * Logs outgoing and incoming faults.
   *
   * @param context the SOAP message context object to provide access to the SOAP message for either RPC request or
   *                response
   * @return {@link Boolean}.TRUE if handleFault is successful, else returns
   *         {@link Boolean}.FALSE
   * @see javax.xml.ws.handler.Handler#handleFault(javax.xml.ws.handler.MessageContext)
   */
  public boolean handleFault(final MessageHandlerContext context) {
    // log to SOAP message to console
    logSOAPMessage(context);
    return true; // Continue processing
  }

  /**
   * Logs outgoing and incoming faults.
   *
   * @param context the SOAP Message context
   */
  private void logSOAPMessage(final MessageHandlerContext context) {
    boolean outgoing = (Boolean) context
      .get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
    if (outgoing) {
    	lastSoapRequest = getSoapAsString(context);
      if (dump) {
        LOG.info("Outgoing" + lastSoapRequest);
      }
    } else {
    	lastSoapResponse = getSoapAsString(context);
      if (dump) {
        LOG.info("Incoming" + lastSoapResponse);
      }
    }
  }

  private String getSoapAsString(MessageHandlerContext mhc) {

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      
      //IMPORTANT: always work from the copy!
      Message m = mhc.getMessage().copy();
      
      XMLStreamWriter writer = XMLStreamWriterFactory.create(baos);
      String soap ="";
      try {
          m.writeTo(writer);
          soap = baos.toString();
          
      } catch (XMLStreamException e) {
    	  LOG.severe("Error logging soap message: "+e.getMessage());
      }
      
      
	return soap;
}

/**
   * Ignore processing of SOAP header as the primary intention is just to
   * 'Dump' the SOAP message.
   *
   * @see javax.xml.ws.handler.soap.SOAPHandler#getHeaders()
   *
   * @return Set<QName> the headers for this handler.
   */
  public Set<QName> getHeaders() {
    return null;
  }


  /**
   * Does nothing <br>
   * Not utilised for dumping SOAP message.
   *
   * @param context the SOAP Message context
   *
   * @see javax.xml.ws.handler.Handler#close(javax.xml.ws.handler.MessageContext)
   */
  public void close(final MessageContext context) {
  }
}
