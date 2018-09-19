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
package au.gov.nehta.vendorlibrary.common;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.common.utils.DomUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link au.gov.nehta.vendorlibrary.common.DOMUtil}
 * provides utility classes for converting a XML document to DOM
 * instance and vice versa.
 */
public final class DOMUtil {

  /**
   * Constant for logging
   */
  private static final Logger LOG = Logger.getLogger(DOMUtil.class.getName());

  /**
   * Default private constructor for Utility class.
   */
  private DOMUtil() {

  }

  /**
   * Returns the provided XML source as {@link org.w3c.dom.Element}.
   *
   * @param xmlSource the XML document source as {@link String}
   * @return {@link org.w3c.dom.Element} instance for the provided XML Source
   */
  public static Element getDocumentElementFromXML(final String xmlSource) {
    Document doc = getDocumentFromXML(xmlSource);
    Element elem = null;
    if (doc != null) {
      elem = doc.getDocumentElement();
    }
    return elem;
  }

  /**
   * Returns the provided XML source as {@link org.w3c.dom.Document}.
   *
   * @param xmlSource the XML document source as {@link String}
   * @return {@link org.w3c.dom.Document} instance for the provided XML Source
   */
  public static Document getDocumentFromXML(final String xmlSource) {
    try {
      DocumentBuilderFactory docBuilder = DocumentBuilderFactory
          .newInstance();
      docBuilder.setNamespaceAware(true);
      docBuilder.setIgnoringElementContentWhitespace(false);
      DocumentBuilder builder = docBuilder.newDocumentBuilder();
      Document doc = builder.parse(new InputSource(new StringReader(
          xmlSource)));
      if (doc != null) {
        return doc;
      }
    } catch (SAXException ex) {
      LOG.log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      LOG.log(Level.SEVERE, null, ex);
    } catch (ParserConfigurationException ex) {
      LOG.log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   * Obtains the {@link org.w3c.dom.Element} instance for the provided AJX instance.
   *
   * @param <T>           the JAXB generic type for marshalling
   * @param instanceClass the JAXB object class
   * @param qName         of the element element
   * @param className     local classname for the JAXB object [Non- fully qualified
   *                      class name]
   * @param object        the JAXB instance to be marshalled to {@link org.w3c.dom.Element}
   * @return {@link org.w3c.dom.Document} instance for the marshalled JAXB instance
   * @throws JAXBException in an event of error.
   */
  @SuppressWarnings("unchecked")
  public static <T> Element getDocumentElementFromInstance(
      final Class instanceClass, final String qName,
      final String className, final T object) throws JAXBException {
    String xmlSource = JAXBUtil.marshaller(instanceClass, qName, className,
        object);
    ArgumentUtils.checkNotNull(xmlSource, "xmlSource");
    return getDocumentElementFromXML(xmlSource);

  }

  /**
   * Obtains the {@link org.w3c.dom.Document} instance for the provided AJX instance.
   *
   * @param <T>           the JAXB generic type for marshalling
   * @param instanceClass the JAXB object class
   * @param qName         of the element element
   * @param className     local classname for the JAXB object [Non- fully qualified
   *                      class name]
   * @param object        the JAXB instance to be marshalled to {@link org.w3c.dom.Document}
   * @return {@link org.w3c.dom.Document} instance for the marshalled JAXB instance
   * @throws JAXBException in an event of error.
   */
  @SuppressWarnings("unchecked")
  public static <T> Document getDocumentFromInstance(
      final Class instanceClass, final String qName,
      final String className, final T object) throws JAXBException {

    String xmlSource = JAXBUtil.marshaller(instanceClass, qName, className,
        object);
    ArgumentUtils.checkNotNull(xmlSource, "xmlSource");
    return getDocumentFromXML(xmlSource);

  }

  /**
   * Converts the provided {@link org.w3c.dom.Document} into a XML data string.
   *
   * @param xmlDoc the document object
   * @return XML data as String
   * @throws {@link TransformerException}, {@link IOException} in an event of error.
   */
  public static String serialiseToString(final Document xmlDoc) throws TransformerException, IOException {
    return DomUtils.serialiseToString(xmlDoc);

  }
}
