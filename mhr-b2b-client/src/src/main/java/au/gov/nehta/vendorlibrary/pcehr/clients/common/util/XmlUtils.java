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

import org.w3c.dom.Node;

import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;

/**
 * A helper class to obtain easy-to-read XML from SOAPMessage objects.
 */
public final class XmlUtils {
  private XmlUtils() {
  }

  /**
   * Returns an easy to read String representation of the SOAP XML Message.
   *
   * @param message the SOAP XML Message (Mandatory)
   * @return an easy to read XML document as a String
   */
  public static String serialiseSoapXml(final SOAPMessage message) {
    final SOAPPart soapPart = message.getSOAPPart();
    final SOAPEnvelope soapEnv;
    try {
      soapEnv = soapPart.getEnvelope();
      return serialiseToString(soapEnv);
    } catch (SOAPException e) {
      return null;
    } catch (IOException e) {
      return null;
    }
  }

  /**
   * Prints an xml document which is indented for easier reading.
   *
   * @param soapEnv the SOAP envelope ((Mandatory)
   * @return the XML document as a String
   */
  private static String serialiseToString(final Node soapEnv) throws IOException {
    final StringWriter writer = new StringWriter();
    final StreamResult result = new StreamResult(writer);
    try {
      final DOMSource source = new DOMSource(soapEnv);
      getTransformer().transform(source, result);
      writer.flush();
    } catch (TransformerConfigurationException e) {
      return null;
    } catch (TransformerException e) {
      return null;
    } finally {
      writer.close();
    }
    return writer.toString();
  }

  /**
   * Returns a configured Transformer object.
   *
   * @return transformer the Transformer object
   * @throws javax.xml.transform.TransformerConfigurationException
   *          if any attempts to configure the transformer fail
   */
  private static Transformer getTransformer() throws TransformerConfigurationException {

    final TransformerFactory factory = TransformerFactory.newInstance();
    final Transformer transformer = factory.newTransformer();
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");

    return transformer;
  }
}