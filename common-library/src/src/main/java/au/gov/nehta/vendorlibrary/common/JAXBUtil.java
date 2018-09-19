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
import au.gov.nehta.common.utils.JaxbUtils;
import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

/**
 * {@link au.gov.nehta.vendorlibrary.common.JAXBUtil} implementation for JAXB object marshal and unmarshal.
 */
public final class JAXBUtil {

  /**
   * Constant for {@link java.util.logging.Logger} instance
   */
  private static final Logger LOG = Logger
      .getLogger(JAXBUtil.class.getName());


  /**
   * Default private constructor for Utility class.
   */
  private JAXBUtil() {

  }

  /**
   * Marshall the provided object and return the XML content of the object.
   *
   * @param instanceClass The class instance of the object to be marshalled (Mandatory).
   * @param qName         the namespace URI of the object to be marshalled. <br>
   *                      <br>
   *                      If the Namespace URI is null, it is set to
   *                      XMLConstants.NULL_NS_URI. This value represents no explicitly
   *                      defined Namespace as defined by the Namespaces in XML
   *                      specification. This action preserves compatible behaviour with
   *                      QName 1.0. Explicitly providing the XMLConstants.NULL_NS_URI
   *                      value is the preferred coding style
   * @param className     the Class name of the object to be marshalled (Mandatory).
   * @param <T>           instance value of T
   * @param object        the instance to be marshalled (Mandatory).
   *                      <br> {@link IllegalArgumentException} will be thrown if the object is null
   * @return XML source of the marshalled object.
   * @throws JAXBException in an event of error.
   */
  public static <T> String marshaller(final Class instanceClass, final String qName,
                                      final String className, final T object) throws JAXBException {
    ArgumentUtils.checkNotNull(instanceClass, "instanceClass");
    ArgumentUtils.checkNotNull(className, "className");
    ArgumentUtils.checkNotNull(object, "object");

    String qNameValue = qName;
    if (qNameValue == null) {
      qNameValue = XMLConstants.NULL_NS_URI;
    }
    QName qNameInstance = new QName(qNameValue, className);

    return JaxbUtils.marshalToString(new JAXBElement(qNameInstance, instanceClass, object));
  }

  /**
   * Marshalls the provided JAXWS object to String[XML String].
   *
   * @param <T>
   * @param instanceClass of type {@link Class}
   * @param className     of the object to be marshalled
   * @param <T>           instance value of T
   * @param object        the instance to be marshalled (Mandatory).
   *                      <br> {@link IllegalArgumentException} will be thrown if the object is null
   * @return Serialized value of T. Else returns null.
   * @throws JAXBException in an event of error.
   */
  @SuppressWarnings("unchecked")
  public static <T> String marshaller(final Class instanceClass, final String className,
                                      final T object) throws JAXBException {
    return marshaller(instanceClass, null, className, object);
  }

  /**
   * Gets the JAXB/JAXWS instance for the provided XML data and jaxws class.
   *
   * @param <T>             JAXWS generic type for unmarshalling xml data.
   * @param parameterObject of type {@link Class}
   * @param xmlSource       of type {@link String}
   * @return <T> of type parameterObject
   * @throws JAXBException in an event of error.
   */
  public static <T> T unmarshaller(final Class parameterObject,
                                   final String xmlSource) throws JAXBException {
    ArgumentUtils.checkNotNull(parameterObject, "parameterObject");
    ArgumentUtils.checkNotNull(xmlSource, "xmlSource");
    InputStream is = null;
    T obj = null;
    try {
      is = new ByteArrayInputStream(xmlSource
          .getBytes("UTF-8"));

      if (is != null) {
        obj = (T) JaxbUtils.unmarshal(new InputStreamReader(is), parameterObject);
      }
    } catch (UnsupportedEncodingException ex) {
      throw new JAXBException("Unsupported encoding format", ex);
    } finally {
      try {
        if (is != null) {
          is.close();
        }
      } catch (IOException ex) {
        throw new JAXBException(ex);
      }
    }
    return obj;
  }

  /**
   * Gets the JAXB/JAXWS instance for the provided XML data and jaxws class
   * object.
   *
   * @param <T>             jaxws generic type for un-marshalling xml data.
   * @param parameterObject of type {@link Class}
   * @param doc             // * @param xmlSource of type {@link String}
   * @return <T> of type parameterObject Note: throws
   *         IllegalArgumentExceptuion if either of the arguments are null.
   * @throws JAXBException in an event of error.
   */
  @SuppressWarnings("unchecked")
  public static <T> T unmarshaller(final Class parameterObject,
                                   final Document doc) throws JAXBException {
    ArgumentUtils.checkNotNull(parameterObject, "parameterObject");
    ArgumentUtils.checkNotNull(doc, "doc");
    doc.normalizeDocument();
    try {
      return (T) unmarshaller(parameterObject, DOMUtil.serialiseToString(doc));
    } catch (TransformerException e) {
      throw new JAXBException("Failed to serialise DOM to XML string data", e);
    } catch (IOException e) {
      throw new JAXBException("Failed to serialise DOM to XML string data", e);
    }
  }

}
