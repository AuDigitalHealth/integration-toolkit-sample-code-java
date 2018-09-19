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

import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.security.auth.x500.X500PrivateCredential;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.commons.lang.Validate;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XMLNamespaces;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.exception.CertificateVerificationException;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.exception.SignatureValidationException;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.exception.SignatureVerificationException;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.HandlerUtils;
import au.gov.nehta.xsp.CertificateValidationException;
import au.gov.nehta.xsp.CertificateValidator;
import au.gov.nehta.xsp.XmlSignatureProfileService;
import au.gov.nehta.xsp.XspException;
import au.gov.nehta.xsp.XspFactory;
import au.gov.nehta.xsp.XspVersion;

/**
 * This SOAP handler constructs the security header elements in the
 * outgoing SOAP requests and verifies incoming SOAP Response from the PCEHR services
 * to ensure conformance. If a SOAP handler is specified in the handler chain of a
 * JAX-WS client or service, JAX-WS will call the SOAP handler before a SOAP message
 * is sent and after a SOAP message has been received.
 */
public class SecurityHandler implements SOAPHandler<SOAPMessageContext> {

  /**
   * Timestamp XML element local name.
   */
  public static final String TIMESTAMP = "timestamp";

  /**
   * Signature XML element local name.
   */
  public static final String SIGNATURE_ELEM = "signature";

  /**
   * Retrieve Document Set Response (ie. getDocument) XML element local name.
   */
  public static final String GET_DOCUMENT_ELEM = "RetrieveDocumentSetResponse";

  /**
   * Digital signature element name.
   */
  public static final String DSIG_ELEM = "Signature";

  /**
   * PCEHRHeader XML element local name.
   */
  public static final String PCEHRH_HEADER_ELEM = "PCEHRHeader";

  /**
   * Expected digest value count.
   */
  private static final int DIGEST_VALUE_COUNT = 1;

  /**
   * Timestamp ID attribute prefix
   */
  private static final String TS_PREFIX = "t_";

  /**
   * Body ID attribute prefix
   */
  private static final String BODY_PREFIX = "b_";

  /**
   * Header ID attribute prefix.
   */
  private static final String HEADER_PREFIX = "h_";

  /**
   * XML Id body attribute name
   */
  private static final String ID_ATTR = "xml:id";

  /**
   * The signing certificate key
   */
  private final X509Certificate x509Certificate;


  /**
   * The signing privatekey
   */
  private final PrivateKey privateKey;

  /**
   * The certificate verifier.
   */
  private final CertificateValidator certificateVerifier;

  /**
   * Constructor.
   *
   * @param x509Certificate     the certificate key to be used for signing (mandatory).
   * @param privateKey          the private key to be used for signing (mandatory).
   * @param certificateVerifier the CertificateVerifier to use for certificate verification (mandatory).
   */
  public SecurityHandler(X509Certificate x509Certificate, PrivateKey privateKey, CertificateValidator certificateVerifier) {
    Validate.notNull(x509Certificate, "'x509Certificate' cannot be null.");
    Validate.notNull(certificateVerifier, "'certificateVerifier' cannot be null. Consider an empty implementation if verification is not required.");
    Validate.notNull(privateKey, "'privateKey' cannot be null.");
    this.x509Certificate = x509Certificate;
    this.privateKey = privateKey;
    this.certificateVerifier = certificateVerifier;
  }

  /**
   * Updates the SOAP headers in outgoing SOAP requests to ensure conformance
   * to NEHTA's guidelines.
   *
   * @param context the incoming / outgoing soap message context
   * @return true Always returns true.
   * @see javax.xml.ws.handler.Handler#handleMessage(javax.xml.ws.handler.MessageContext)
   */
  public final boolean handleMessage(final SOAPMessageContext context) {

    if (HandlerUtils.isOutgoing(context)) {
      signBodyAndSOAPHeaders(context);
    } else {
      try {
       checkResponse(context);
      } catch (CertificateVerificationException e) {
        throw new SignatureVerificationException("Certificate verification failed", e);
      }
    }
    return true;
  }

  /**
   * Validates and verifies specific elements of the SOAP response.
   *
   * @param context the incoming / outgoing soap message context.
   */
  private void checkResponse(final SOAPMessageContext context) {

    // Validate the signature and return the fragment.
    Element sigElement = validateSignature(context);

    // Verify the signature's content.
    XmlSignatureProfileService xmlSignatureProfileService = null;
    try {
      xmlSignatureProfileService = XspFactory.getInstance().getXmlSignatureProfileService(XspVersion.V_2010);
    } catch (XspException e) {
      throw new SignatureValidationException("Unable to validate signature", e);
    }

    try {
      xmlSignatureProfileService.check(sigElement, certificateVerifier);
    } catch (au.gov.nehta.xsp.SignatureValidationException e) {
      throw new SignatureValidationException("Signature validation failed.", e);
    } catch (au.gov.nehta.xsp.CertificateVerificationException e) {
      throw new SignatureValidationException("Signature validation failed.", e);
    } catch (XspException e) {
      throw new SignatureValidationException("Signature validation failed.", e);
    } catch (CertificateValidationException e) {
    	 throw new SignatureValidationException("Signature validation failed.", e);
	}
  }

  /**
   * Ignore Fault and continues with processing logical handling of message.
   *
   * @param context the incoming / outgoing soap message context
   * @return true if the handle signature check is successful.
   * @see javax.xml.ws.handler.Handler#handleFault(javax.xml.ws.handler.MessageContext)
   */
  public final boolean handleFault(final SOAPMessageContext context) {
    // Verifies the inbound fault signature.
    // Do nothing. Implement in production code.
    return true;
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

  /**
   * Does nothing <br>
   * Not utilised for dumping SOAP message.
   *
   * @param context @see javax.xml.ws.handler.Handler#close(javax.xml.ws.handler.MessageContext)
   */
  public void close(final MessageContext context) {
    //Do nothing
  }

  /**
   * Signs the SOAP message parts.
   *
   * @param context of type {@link javax.xml.ws.handler.soap.SOAPMessageContext} (Mandatory)
   */
  private void signBodyAndSOAPHeaders(final SOAPMessageContext context) {

    Validate.notNull(context, "SOAPMessageContext cannot be null");

    try {
      // Local variables for signing the SOAP header and body elements.
      SOAPEnvelope envelope = context.getMessage().getSOAPPart().getEnvelope();
      SOAPHeader header = envelope.getHeader();
      SOAPBody body = envelope.getBody();

      extractAndSignSoapElements(header, body);
    } catch (SOAPException ex) {
      throw new RuntimeException(ex.getMessage(), ex);
    } catch (XspException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * Extracts elements from SOAP header and SOAP body and signs them.
   *
   * @param header      the SOAP header to be signed
   * @param bodyElement the SOAP body element to be signed.
   * @throws XspException in an event of signature failure.
   */
  private void extractAndSignSoapElements(SOAPHeader header, Element bodyElement) throws XspException {

    Validate.notNull(header, "Outgoing message SOAP header cannot be null.");
    Validate.notNull(bodyElement, "Outgoing message SOAP body cannot be null.");

    // Sign the body and the SOAP header elements to include message integrity
    NodeList timestampNodeList = header.getElementsByTagNameNS(XMLNamespaces.COMMON.getNamespace(), TIMESTAMP);
    NodeList sigNodeList = header.getElementsByTagNameNS(XMLNamespaces.COMMON.getNamespace(), SIGNATURE_ELEM);
    NodeList headerNodeList = header.getElementsByTagNameNS(XMLNamespaces.COMMON.getNamespace(), PCEHRH_HEADER_ELEM);

    Element timestampElement = HandlerUtils.getFirstElementFromNodeList(timestampNodeList);
    Element sigElement = HandlerUtils.getFirstElementFromNodeList(sigNodeList);
    Element headerElement = HandlerUtils.getFirstElementFromNodeList(headerNodeList);


   // addSignatureReferenceId(bodyElement, timestampElement, headerElement);
    signSoapElements(timestampElement, headerElement, bodyElement, sigElement);
  }

	private void registerIDAtttributes(Document doc) {
		NodeList bodyTags = doc.getDocumentElement().getElementsByTagNameNS("http://www.w3.org/2003/05/soap-envelope", "Body");
	   for (int i = 0; i < bodyTags.getLength(); i++) {
		   Element element = (Element)bodyTags.item(i);
		   Attr xmlid= element.getAttributeNode("xml:id");
		   element.setIdAttributeNode(xmlid, true);
	   }
	
	}
  
  
  /**
   * Signs the provided SOAP elements.
   *
   * @param timestampElement the header timestamp element to be signed
   * @param headerElement    the header element to be signed
   * @param bodyElement      the SOAP body element to be signed.
   * @param sigElement       the header signature element to be signed.
   * @throws XspException in an event of signature failure.
   */
  private void signSoapElements(Element timestampElement, Element headerElement, Element bodyElement, Element sigElement) throws XspException {

    List<Element> elementsToSign = Arrays.asList(timestampElement, headerElement, bodyElement);

    // Obtain the required public certificate and private key for
    // performing XML digital signing
    List<X500PrivateCredential> certificateKeyPairs = Arrays.asList(new X500PrivateCredential(x509Certificate, privateKey));

    // Sign the Body , timestamp and user elements.
    // Add the signed value to signature element for remote
    // endpoint verification
    XmlSignatureProfileService xmlSignatureProfileService = XspFactory.getInstance().getXmlSignatureProfileService(XspVersion.V_2010);
    xmlSignatureProfileService.sign(sigElement, elementsToSign, certificateKeyPairs);
  }

  /**
   * Helper method to retrieve the signature's document object model.
   *
   * @param context @see javax.xml.ws.handler.Handler#close(javax.xml.ws.handler.MessageContext).
   * @return Extracted {@link org.w3c.dom.Document} object.
   * @throws au.gov.nehta.vendorlibrary.pcehr.clients.common.exception.CertificateVerificationException
   *          thrown when signature extraction fails.
   */
  private Element validateSignature(SOAPMessageContext context) {

    Validate.notNull(context, "'context' cannot be null.");

    SOAPHeader header = null;
    try {
      header = context.getMessage().getSOAPHeader();
    } catch (SOAPException e) {
      throw new SignatureValidationException("Unable to extract SOAP header.", e);
    }

    if (header == null) {
      throw new SignatureValidationException("Unable to extract SOAP header.");
    }

    registerIDAtttributes(header.getOwnerDocument());
    
    // Sign the body and the SOAP header elements to include message integrity
    NodeList sigNodeList = header.getElementsByTagNameNS(XMLNamespaces.DS.getNamespace(), DSIG_ELEM);
    Element sigElement = HandlerUtils.getFirstElementFromNodeList(sigNodeList);

    if (sigElement == null) {
      throw new CertificateVerificationException("Unable to extract signature fragment.");
    }

    return sigElement;
  }

  /**
   * Adds signature reference identifier for the provided elements to be signed.
   *
   * @param bodyElement      the SOAP body element
   * @param timestampElement the header timestamp element
   * @param headerElement    the header user element
   */
  private void addSignatureReferenceId(Element bodyElement, Element timestampElement, Element headerElement) {

    Validate.notNull(bodyElement, "'bodyElement' cannot be null.");
    ArgumentUtils.checkNotNull(timestampElement, "'timestampElement' cannot be null.");
    ArgumentUtils.checkNotNull(headerElement, "'headerElement' cannot be null.");

    // Set default elements ID attributes for elements that needs to be
    // signed. This is optional as the XMLDSignature library
    // generates one if these values are not specified.
    headerElement.setAttribute(ID_ATTR, HEADER_PREFIX + UUID.randomUUID().toString());
    bodyElement.setAttribute(ID_ATTR, BODY_PREFIX + UUID.randomUUID().toString());
    timestampElement.setAttribute(ID_ATTR, TS_PREFIX + UUID.randomUUID().toString());
  }
}
