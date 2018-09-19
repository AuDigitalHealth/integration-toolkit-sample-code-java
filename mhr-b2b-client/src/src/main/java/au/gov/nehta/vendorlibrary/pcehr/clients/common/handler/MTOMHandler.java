/*
 * Copyright 2012 NEHTA
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
package au.gov.nehta.vendorlibrary.pcehr.clients.common.handler;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XMLNamespaces;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.exception.MTOMException;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.HandlerUtils;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

/**
 * This SOAP handler functions to workaround limitations of SOAPHandlers that breaks MTOM,
 * resulting in binary SOAP message attachments being inlined into a single SOAP MIME part as
 * a Base64 encoded string.If a SOAP handler is specified in the handler chain of a JAX-WS client
 * or service, JAX-WS will call the SOAP handler before a SOAP message is sent and after a SOAP message
 * has been received.
 * 
 * @deprecated prefer {@link ConfigurableMTOMHandler} over this class with an explicitly set  MTOM element.
 */
public class MTOMHandler implements SOAPHandler<SOAPMessageContext> {

  /**
   * Document XML element local name.
   */
  public static final String DOCUMENT_ELEM = "Document";

  /**
   * Provide and Register Document Set Request XML element local name.
   */
  public static final String UPLOAD_REQUEST_ELEM = "ProvideAndRegisterDocumentSetRequest";

  
  /**
   * Include XML element local name.
   */
  public static final String INCLUDE_ELEM = "Include";

  /**
   * XOP include attribute string format (reference ID is denoted by '%s'.
   */
  public static final String XOP_INCLUDE = "cid:%s";

  /**
   * XOP include HREF content ID attribute name.
   */
  private static final String CONTENT_ID_ATTR = "href";

  /**
   * 'application/octet-stream' content type.
   */
  private static final String OCTET_STREAM_CONTENT_TYPE = "application/octet-stream";

  /**
   * Updates the request to ensure byte content is correctly passed as a MTOM part.
   *
   * @param context the incoming / outgoing soap message context
   * @return true Always returns true.
   * @see javax.xml.ws.handler.Handler#handleMessage(javax.xml.ws.handler.MessageContext)
   */
  @Override
  public final boolean handleMessage(SOAPMessageContext context) {

    if (HandlerUtils.isOutgoing(context)) {

      if (HandlerUtils.isResponseType(context, XMLNamespaces.IHE.getNamespace(), UPLOAD_REQUEST_ELEM)) {
        try {

          SOAPEnvelope envelope = context.getMessage().getSOAPPart().getEnvelope();
          SOAPBody body = envelope.getBody();

          // New reference ID for use in include and attachment part.
          UUID referenceId = UUID.randomUUID();

          // Retrieve the Base64 document content string.
          String documentContent = HandlerUtils.extractElementContent(body, XMLNamespaces.IHE.getNamespace(), DOCUMENT_ELEM);

          // Convert to an Input Stream, as required to add an attachment.
          InputStream is = IOUtils.toInputStream(documentContent);

          // Create a new attachment for the document content.
          createDocumentAttachmentPart(context, is, referenceId);

          // Replace document content in SOAP envelope with XOP reference.
          replaceDocumentContent(body, referenceId);

        } catch (SOAPException e) {
          throw new MTOMException("Unable to extract SOAP components", e);
        }
      }
    }

    return true;
  }

  /**
   * Replaces inline Base64 content of a 'Document' element and adds an XOP include reference to the SOAP binary attachment.
   *
   * @param body        the SOAP body to be altered.
   * @param referenceId the reference ID corresponding to the content-id in the SOAP attachment to contain the binary document content.
   */
  private void replaceDocumentContent(SOAPBody body, UUID referenceId) {

    NodeList nodeList = body.getElementsByTagNameNS(XMLNamespaces.IHE.getNamespace(), DOCUMENT_ELEM);
    SOAPBodyElement documentElement = (SOAPBodyElement) HandlerUtils.getFirstElementFromNodeList(nodeList);

    // Need to do this otherwise the Base64 content still appears inline.
    documentElement.removeContents();

    // Add the new element.
    try {
      documentElement
        .addChildElement(INCLUDE_ELEM, XMLNamespaces.XOP.getPrefix(), XMLNamespaces.XOP.getNamespace())
        .setAttribute(CONTENT_ID_ATTR, String.format(XOP_INCLUDE, referenceId.toString()));
    } catch (SOAPException e) {
      throw new MTOMException("Failed to add the new XOP include element to the SOAP body.", e);
    }
  }

  /**
   * Creates a new attachment part that contains document element's binary content.
   *
   * @param context     context the incoming / outgoing soap message context
   * @param is          the {@link InputStream} encapsulating the Base64 encoded binary content.
   * @param referenceId the reference ID corresponding to the content-id in the SOAP attachment to contain the binary document content.
   */
  private void createDocumentAttachmentPart(SOAPMessageContext context, InputStream is, UUID referenceId) {

    AttachmentPart attachment = context.getMessage().createAttachmentPart();

    // The attachment is able to accept a Base64 encoded string and transform that to binary in the SOAP attachment part.
    try {
      attachment.setBase64Content(is, OCTET_STREAM_CONTENT_TYPE);
    } catch (SOAPException e) {
      throw new MTOMException("Failed to create a new attachment part", e);
    }

    // Set the reference ID to match that being used in the XOP include.
    attachment.setContentId(referenceId.toString());

    // Add the new attachment part to the SOAP message.
    context.getMessage().addAttachmentPart(attachment);
  }

  /**
   * Ignore Fault and continues with processing logical handling of message.
   *
   * @param context the incoming / outgoing soap message context
   * @return true if the handle signature check is successful.
   * @see javax.xml.ws.handler.Handler#handleFault(javax.xml.ws.handler.MessageContext)
   */
  @Override
  public final boolean handleFault(SOAPMessageContext context) {
    // Verifies the inbound fault signature.
    // Do nothing. Implement in production code.
    return true;
  }

  /**
   * Does nothing <br>
   * Not utilised for dumping SOAP message.
   *
   * @param context @see javax.xml.ws.handler.Handler#close(javax.xml.ws.handler.MessageContext)
   */
  @Override
  public void close(MessageContext context) {
    // Do nothing.
  }

  /**
   * Does nothing returns null.<br>
   * Ignore processing of SOAP header as the primary intention is just to
   * 'Dump' the SOAP message
   *
   * @return @see javax.xml.ws.handler.soap.SOAPHandler#getHeaders()
   */
  public final Set<QName> getHeaders() {
    return null;
  }
}
