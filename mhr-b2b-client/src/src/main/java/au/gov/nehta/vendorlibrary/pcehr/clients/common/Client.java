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
package au.gov.nehta.vendorlibrary.pcehr.clients.common;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.commons.lang.Validate;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.handler.IMTOMHandler;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.handler.SecurityHandler;
import au.gov.nehta.vendorlibrary.ws.WebServiceClientUtil;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.gov.nehta.xsp.CertificateValidationException;
import au.gov.nehta.xsp.CertificateValidator;
import au.gov.nehta.xsp.XspException;

/**
 * Provides a base class to simplify the creation of a JAX-WS Web Service client.
 *
 * @param <PortType> JAX-WS port class.
 */
public abstract class Client<PortType> {

  /**
   * Logging handler instance for SOAP request and response messages.
   */
  private LoggingHandler loggingHandler;

  /**
   * JAX-WS handler chain.
   */
  private List<Handler> handlerChain;

  /**
   * JAX-WS port class.
   */
  private final PortType port;

  /**
   * Constructor - accepts an optional certificate verifier.
   *
   * @param serviceClass        the JAX-WS service class.
   * @param portClass           the JAX-WS port class.
   * @param sslSocketFactory    the {@link SSLSocketFactory} to be used when connecting to the web service provider (mandatory).
   * @param x509Certificate     the certificate key to be used for signing (mandatory)
   * @param certificateVerifier CertificateVerifier implementation (optional).
   * @param privateKey          the private key to be used for signing (mandatory)
   * @param endpointAddress     the endpoint address of the web service (mandatory).
   * @param setLoggingEnabled   set to <code>true</code> to enable logging (mandatory).
   */
  public Client(
    final Class<? extends Service> serviceClass,
    final Class<PortType> portClass,
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final CertificateValidator certificateVerifier,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {
    Validate.notNull(serviceClass, "'serviceClass' cannot be null.");
    Validate.notNull(portClass, "'portClass' cannot be null.");
    Validate.notNull(sslSocketFactory, "'sslSocketFactory' cannot be null.");
    Validate.notNull(x509Certificate, "'x509Certificate' cannot be null.");
    Validate.notNull(certificateVerifier, "'certificateVerifier' cannot be null.");
    Validate.notNull(privateKey, "'privateKey' cannot be null.");
    Validate.notEmpty(endpointAddress, "'endpointAddress' cannot be null nor empty.");

    this.loggingHandler = new LoggingHandler(setLoggingEnabled);
    SecurityHandler securityHandler = new SecurityHandler(x509Certificate, privateKey, certificateVerifier);
    handlerChain = new ArrayList<Handler>();
    handlerChain.add(securityHandler);
    handlerChain.add(loggingHandler);

    this.port = WebServiceClientUtil.getPort(
      portClass,
      serviceClass,
      sslSocketFactory,
      endpointAddress,
      handlerChain,
      true
    );
  }
  
  
  /**
   * Constructor - accepts an optional certificate verifier.
   *
   * @param serviceClass        the JAX-WS service class.
   * @param portClass           the JAX-WS port class.
   * @param sslSocketFactory    the {@link SSLSocketFactory} to be used when connecting to the web service provider (mandatory).
   * @param x509Certificate     the certificate key to be used for signing (mandatory)
   * @param certificateVerifier CertificateVerifier implementation (optional).
   * @param privateKey          the private key to be used for signing (mandatory)
   * @param endpointAddress     the endpoint address of the web service (mandatory).
   * @param setLoggingEnabled   set to <code>true</code> to enable logging (mandatory).
   * @param customMTOMHandler 
   */
  public Client(
    final Class<? extends Service> serviceClass,
    final Class<PortType> portClass,
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final CertificateValidator certificateVerifier,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled,
    final IMTOMHandler customMtomHandler
  ) {
    Validate.notNull(serviceClass, "'serviceClass' cannot be null.");
    Validate.notNull(portClass, "'portClass' cannot be null.");
    Validate.notNull(sslSocketFactory, "'sslSocketFactory' cannot be null.");
    Validate.notNull(x509Certificate, "'x509Certificate' cannot be null.");
    Validate.notNull(certificateVerifier, "'certificateVerifier' cannot be null.");
    Validate.notNull(privateKey, "'privateKey' cannot be null.");
    Validate.notEmpty(endpointAddress, "'endpointAddress' cannot be null nor empty.");

    this.loggingHandler = new LoggingHandler(setLoggingEnabled);
    SecurityHandler securityHandler = new SecurityHandler(x509Certificate, privateKey, certificateVerifier);
    Handler<?> mtomHandler = customMtomHandler;
    handlerChain = new ArrayList<Handler>();
    handlerChain.add(securityHandler);
    handlerChain.add(mtomHandler);
    handlerChain.add(loggingHandler);

    
    this.port = WebServiceClientUtil.getPort(
      portClass,
      serviceClass,
      sslSocketFactory,
      endpointAddress,
      handlerChain,
      true
    );
    
    //if an IMTOM was provided then set MTOM handling by default.
    setMTOMEnabled();
  }

  /**
   * Constructor - no certificate verification performed.
   *
   * @param sslSocketFactory  the {@link SSLSocketFactory} to be used when connecting to the web service provider (mandatory).
   * @param x509Certificate   the certificate key to be used for signing (mandatory)
   * @param privateKey        the private key to be used for signing (mandatory)
   * @param endpointAddress   the endpoint address of the web service (mandatory).
   * @param setLoggingEnabled set to <code>true</code> to enable logging (mandatory).
   */
  public Client(
    final Class<? extends Service> serviceClass,
    final Class<PortType> portClass,
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {
    this(
      serviceClass,
      portClass,
      sslSocketFactory,
      x509Certificate,
      new CertificateValidator() {

		@Override
		public void validate(X509Certificate certificate) throws CertificateValidationException, XspException {
			 // Do no verification.
			
		}
      },
      privateKey,
      endpointAddress,
      setLoggingEnabled);
  }
  
  
  /**
   * Constructor - no certificate verification performed.
   *
   * @param sslSocketFactory  the {@link SSLSocketFactory} to be used when connecting to the web service provider (mandatory).
   * @param x509Certificate   the certificate key to be used for signing (mandatory)
   * @param privateKey        the private key to be used for signing (mandatory)
   * @param endpointAddress   the endpoint address of the web service (mandatory).
   * @param setLoggingEnabled set to <code>true</code> to enable logging (mandatory).
   */
  public Client(
    final Class<? extends Service> serviceClass,
    final Class<PortType> portClass,
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled,
    final IMTOMHandler customMtomHandler
  ) {
    this(
      serviceClass,
      portClass,
      sslSocketFactory,
      x509Certificate,
      new CertificateValidator() {

  		@Override
  		public void validate(X509Certificate certificate) throws CertificateValidationException, XspException {
  			 // Do no verification.
  		}
      },
      privateKey,
      endpointAddress,
      setLoggingEnabled,
      customMtomHandler);
  }

  /**
   * Retrieves the current JAX-WS handler chain.
   *
   * @return List containing the handler chain.
   */
  public final List<Handler> getHandlerChain() {
    return handlerChain;
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
   * @return lastSoapRequest the lastSoapRequest instance variable
   */
  public final String getLastSoapRequest() {

    if (loggingHandler != null) {
      return loggingHandler.getLastSoapRequest();
    } else {
      return LoggingHandler.EMPTY;
    }
  }

  /**
   * Get port's binding provider.
   *
   * @return binding provider.
   */
  private BindingProvider getBindingProvider() {
    return (BindingProvider) port;
  }

  /**
   * Exposes the ability to set a request context property.
   *
   * @param propertyKey   request property name (Mandatory).
   * @param propertyValue request property value (Mandatory).
   */
  public final void setProperty(String propertyKey, Object propertyValue) {
    Validate.notNull(propertyKey, "'propertyKey' must be specified.");
    Validate.notNull(propertyValue, "'propertyValue' must be specified.");
    getBindingProvider().getRequestContext().put(propertyKey, propertyValue);
  }

  /**
   * Enable MTOM for client port.
   */
  public final void setMTOMEnabled() {
    SOAPBinding binding = (SOAPBinding) getBindingProvider().getBinding();
    binding.setMTOMEnabled(true);
  }

  /**
   * Get client port.
   *
   * @return the client port.
   */
  public final PortType getPort() {
    return port;
  }
}
