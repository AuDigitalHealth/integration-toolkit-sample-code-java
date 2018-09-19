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

package au.gov.nehta.vendorlibrary.pcehr.clients.common.util;

import org.apache.commons.lang.Validate;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public final class HandlerUtils {

  /**
   * Private constructor to prevent class instantiation.
   */
  private HandlerUtils() {
  }

  /**
   * Returns the first element from the provided nodeList
   *
   * @param nodeList containing the SOAP header/body element.
   * @return the first element from the nodeList
   */
  public static Element getFirstElementFromNodeList(NodeList nodeList) {
    Validate.notNull(nodeList, "'nodeList' cannot be null.");
    Element element = null;
    if (nodeList.getLength() > 0) {
      Validate.notNull(nodeList.item(0), "'nodeList.item' cannot be null.");
      element = (Element) nodeList.item(0);
    }
    return element;
  }

  /**
   * Determines whether a SOAP request/response is of a given type.
   *
   * @param context   context the incoming / outgoing soap message context.
   * @param namespace namespace of the response/request type.
   * @param localName local name of the element.
   * @return <code>true</code> if request/response is of the type defined by namespace/local name; otherwise <code>false</code>
   */
  public static boolean isResponseType(SOAPMessageContext context, String namespace, String localName) {

    Validate.notNull(context, "SOAPMessageContext cannot be null");

    boolean result = false;

    try {
      // Local variables for signing the SOAP header and body elements.
      SOAPEnvelope envelope = context.getMessage().getSOAPPart().getEnvelope();
      SOAPBody body = envelope.getBody();

      NodeList responseType = body.getElementsByTagNameNS(namespace, localName);

      if (responseType.getLength() > 0) {
        result = true;
      }
    } catch (SOAPException ex) {
      throw new RuntimeException(ex.getMessage(), ex);
    }

    return result;
  }

  /**
   * Checks whether a SOAP message is incoming/outgoing.
   *
   * @param context context the incoming/outgoing soap message context.
   * @return <code>true</code> if the message is outgoing; otherwise <code>false</code>.
   */
  public static Boolean isOutgoing(SOAPMessageContext context) {
    return (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
  }

  /**
   * Extracts a given element's node value.
   *
   * @param body      the SOAP body to retrieve the element node value from.
   * @param namespace the {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XMLNamespaces} namespace of the element.
   * @param localName the local name of the element.
   * @return Element's node value.
   */
  public static String extractElementContent(SOAPBody body, String namespace, String localName) {
    NodeList nodeList = body.getElementsByTagNameNS(namespace, localName);
    Element documentElement = HandlerUtils.getFirstElementFromNodeList(nodeList);
    if(documentElement != null && documentElement.getFirstChild() != null ){
        return documentElement.getFirstChild().getNodeValue();
    }else{
        return null;
    }
    
  }

}
