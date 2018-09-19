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
package au.gov.nehta.vendorlibrary.ws;

import javax.net.SocketFactory;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.soap.AddressingFeature;

import com.sun.xml.ws.developer.JAXWSProperties;

import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Creates ports for web service clients.
 */
public final class WebServiceClientUtil {

  /**
   * SSl Socket Factory request context property
   */
  private static final String REQUEST_CONTEXT_SSL_SOCKET_FACTORY_PROPERTY_NAME = "com.sun.xml.ws.transport.https.client.SSLSocketFactory";
  /**
   * SSl Socket Factory request context property for the INTERNAL version of jaxws
   */
  private static final String INTERNAL_REQUEST_CONTEXT_SSL_SOCKET_FACTORY_PROPERTY_NAME = "com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory";
                                                                            

  /**
   * Private constructor for utility class.
   */
  private WebServiceClientUtil() {
  }

  /**
   * Returns a Web Service client port based on the parameters passed.
   *
   * @param serviceInterface the Web Server's Java Service interface class (Mandatory)
   * @param serviceClass     the Web Server's Java Service service class (Mandatory)
   * @param sslSocketFactory the SSL Socket Factory to be used (Mandatory)
   * @param endpoint         the Web Service endpoint URL (Mandatory)
   * @param handlerChain     the handler chain to be used with the port (Mandatory)
   * @param <T>              the Web Service's Java Service interface type
   * @return <T> the Web Service client port
   */
  public static <T> T getPort(java.lang.Class<T> serviceInterface,
                              java.lang.Class<? extends Service> serviceClass,
                              SocketFactory sslSocketFactory,
                              String endpoint,
                              List<Handler> handlerChain) {

    T port = getPort(serviceInterface, serviceClass, sslSocketFactory, endpoint);
    addHandlerChain(port, handlerChain);

    return port;
  }

  /**
   * Returns a Web Service client port based on the parameters passed.
   *
   * @param serviceInterface the Web Server's Java Service interface class (Mandatory)
   * @param serviceClass     the Web Server's Java Service interface class (Mandatory)
   * @param sslSocketFactory the SSL Socket Factory to be used (Mandatory)
   * @param endpoint         the Web Service endpoint URL (Mandatory)
   * @param <T>              the Web Service's Java Service interface type
   * @return <T> the Web Service client port
   */
  public static <T> T getPort(java.lang.Class<T> serviceInterface,
                              java.lang.Class<? extends Service> serviceClass,
                              SocketFactory sslSocketFactory,
                              String endpoint) {
    WebServiceClient annotation = serviceClass.getAnnotation(WebServiceClient.class);
    String wsdlFileLoc = annotation.wsdlLocation();
    Service service = getService(annotation, wsdlFileLoc);
    T port = service.getPort(serviceInterface);
    configurePortWithEndpoint(port, endpoint);
    configurePortWithSslSocketFactory(port, sslSocketFactory);

    return port;
  }

  /**
   * Returns a Web Service client port based on the parameters passed.
   *
   * @param serviceInterface  the Web Server's Java Service interface class (Mandatory)
   * @param serviceClass      the Web Server's Java Service interface class (Mandatory)
   * @param sslSocketFactory  the SSL Socket Factory to be used (Mandatory)
   * @param endpoint          the Web Service endpoint URL (Mandatory)
   * @param addressingFeature addressing web service feature setting true/false (Mandatory)
   * @param <T>               the Web Service's Java Service interface type
   * @return <T> the Web Service client port
   */
  public static <T> T getPort(java.lang.Class<T> serviceInterface,
                              java.lang.Class<? extends Service> serviceClass,
                              SocketFactory sslSocketFactory,
                              String endpoint,
                              List<Handler> handlerChain,
                              boolean addressingFeature) {
    WebServiceClient annotation = serviceClass.getAnnotation(WebServiceClient.class);
    String wsdlFileLoc = annotation.wsdlLocation();
    Service service = getService(annotation, wsdlFileLoc);
    T port = service.getPort(serviceInterface, new AddressingFeature(addressingFeature));
    configurePortWithEndpoint(port, endpoint);
    configurePortWithSslSocketFactory(port, sslSocketFactory);
    addHandlerChain(port, handlerChain);

    return port;
  }

  /**
   * Returns a Web Service client port based on the parameters passed.
   *
   * @param serviceInterface the Web Server's Java Service interface class (Mandatory)
   * @param serviceClass     the Web Server's Java Service service class (Mandatory)
   * @param sslSocketFactory the SSL Socket Factory to be used (Mandatory)
   * @param handlerChain     the handler chain to be used with the port (Mandatory)
   * @param <T>              the Web Service's Java Service interface type
   * @return <T> the Web Service client port
   */
  public static <T> T getPort(java.lang.Class<T> serviceInterface,
                              java.lang.Class<? extends Service> serviceClass,
                              SocketFactory sslSocketFactory,
                              List<Handler> handlerChain) {

    T port = getPort(serviceInterface, serviceClass, sslSocketFactory);
    addHandlerChain(port, handlerChain);

    return port;
  }

  /**
   * Returns a Web Service client port based on the parameters passed.
   *
   * @param serviceInterface the Web Server's Java Service interface class (Mandatory)
   * @param serviceClass     the Web Server's Java Service interface class (Mandatory)
   * @param sslSocketFactory the SSL Socket Factory to be used (Mandatory)
   * @param <T>              the Web Service's Java Service interface type
   * @return <T> the Web Service client port
   */
  public static <T> T getPort(java.lang.Class<T> serviceInterface,
                              java.lang.Class<? extends Service> serviceClass,
                              SocketFactory sslSocketFactory) {
    WebServiceClient annotation = serviceClass.getAnnotation(WebServiceClient.class);
    String wsdlFileLoc = annotation.wsdlLocation();
    Service service = getService(annotation, wsdlFileLoc);
    T port = service.getPort(serviceInterface);
    configurePortWithSslSocketFactory(port, sslSocketFactory);

    return port;
  }

  /**
   * Creates a web service Service.
   *
   * @param annotation  the associated WebServiceClient the location of the Service's WSDL file (Mandatory)
   * @param wsdlFileLoc the location of the Service's WSDL file (Mandatory)
   * @return the created web service interface
   */
  private static Service getService(final WebServiceClient annotation, final String wsdlFileLoc) {

    final URL wsdlURL = retrieveWsdlUrl(wsdlFileLoc);
    final QName serviceQName = new QName(annotation.targetNamespace(), annotation.name());
    return Service.create(wsdlURL, serviceQName);
  }

  /**
   * Obtain a URL to a WSDL file in the classpath
   *
   * @param filePath the path to WSDL file (Mandatory)
   * @return result the URL to the WSDL file
   */
  private static URL retrieveWsdlUrl(String filePath) {
    URL result = null;
    ClassLoader classLoader;
    if (filePath != null) {
      classLoader = Thread.currentThread().getContextClassLoader();
      result = classLoader.getResource(filePath);
    }

    return result;
  }

  /**
   * Configure the endpoint with the provided SslSocketFactory.
   *
   * @param servicePort      the service definition. (Mandatory)
   * @param sslSocketFactory the SSL Socket Factory to associate with this endpoint. (Mandatory)
   */
  private static void configurePortWithSslSocketFactory(final Object servicePort,
                                                        final SocketFactory sslSocketFactory) {
    final BindingProvider bindingProvider = (BindingProvider) servicePort;

    // Set details on request context
    final Map<String, Object> requestContext = bindingProvider.getRequestContext();
    requestContext.put(REQUEST_CONTEXT_SSL_SOCKET_FACTORY_PROPERTY_NAME, sslSocketFactory);
    requestContext.put(INTERNAL_REQUEST_CONTEXT_SSL_SOCKET_FACTORY_PROPERTY_NAME, sslSocketFactory);
  }

  /**
   * Configure the port with the provided endpoint.
   *
   * @param servicePort the service definition. (Mandatory)
   * @param endpoint    the URL for the Web service endpoint. (Mandatory)
   */
  private static void configurePortWithEndpoint(final Object servicePort,
                                                final String endpoint) {
    final BindingProvider bindingProvider = (BindingProvider) servicePort;

    // Set details on request context
    final Map<String, Object> requestContext = bindingProvider.getRequestContext();
    requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint.trim());
  }

  /**
   * Add a handler chain to a Web Service port.
   *
   * @param servicePort  the service definition. (Mandatory)
   * @param handlerChain the list of handlers to associate with this endpoint. (Mandatory)
   */
  private static void addHandlerChain(final Object servicePort, final List<Handler> handlerChain) {

    final BindingProvider bindingProvider = (BindingProvider) servicePort;
    bindingProvider.getBinding().setHandlerChain(handlerChain);
  }
}