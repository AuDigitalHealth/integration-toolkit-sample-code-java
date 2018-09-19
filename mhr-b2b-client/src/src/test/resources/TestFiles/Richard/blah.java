package au.gov.nehta.xsp.impl.v1;

import java.io.ByteArrayInputStream;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.security.auth.x500.X500PrivateCredential;
import javax.xml.crypto.Data;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.OctetStreamData;
import javax.xml.crypto.URIDereferencer;
import javax.xml.crypto.URIReference;
import javax.xml.crypto.URIReferenceException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.common.utils.DomUtils;
import au.gov.nehta.xsp.CertificateVerificationException;
import au.gov.nehta.xsp.CertificateVerifier;
import au.gov.nehta.xsp.SignatureValidationException;
import au.gov.nehta.xsp.XmlSignatureProfileService;
import au.gov.nehta.xsp.XspException;
import au.gov.nehta.xsp.impl.CertificateUtils;
import au.gov.nehta.xsp.impl.TextUtils;

/**
 * Implementation of XmlSignatureProfileService interface that supports
 * <em>XML Secured Payload Profile</em>, NEHTA version 1.2 (30 June 2009) and
 * Standards Australia version 2010.
 */
public class XmlSignatureProfileServiceImpl implements XmlSignatureProfileService {

  private static final String XML_ID_LOCAL_NAME = "id";

  private static final String XML_ID_QNAME = "xml:id";

  private static final String XML_NAMESPACE = "http://www.w3.org/XML/1998/namespace";

  private static final List<String> ID_ATTR_TAGS = Arrays.asList("id", "Id",
      "ID");

  private static final XMLSignatureFactory XML_SIGNATURE_FACTORY = XMLSignatureFactory
      .getInstance("DOM");

  private static final KeyInfoFactory KEY_INFO_FACTORY = XML_SIGNATURE_FACTORY
      .getKeyInfoFactory();

  @Override
  public void sign(Element elementToAddSigTo, Element elementToSign,
      X500PrivateCredential credential) throws XspException {
    ArgumentUtils.checkNotNull(elementToSign, "elementToSign");
    ArgumentUtils.checkNotNull(credential, "credential");

    sign(elementToAddSigTo, Arrays.asList(elementToSign), Arrays
        .asList(credential));
  }

  @Override
  public void sign(Element elementToAddSigTo, List<Element> elementsToSign,
      X500PrivateCredential credential) throws XspException {
    ArgumentUtils.checkNotNull(credential, "credential");

    sign(elementToAddSigTo, elementsToSign, Arrays.asList(credential));
  }

  @Override
  public void sign(Element elementToAddSigTo, Element elementToSign,
      List<X500PrivateCredential> credentials) throws XspException {
    ArgumentUtils.checkNotNull(elementToSign, "elementToSign");

    sign(elementToAddSigTo, Arrays.asList(elementToSign), credentials);
  }

  @Override
  public void sign(Element elementToAddSigTo, List<Element> elementsToSign,
      List<X500PrivateCredential> credentials) throws XspException {
    ArgumentUtils.checkNotNull(elementToAddSigTo, "elementToAddSigTo");
    ArgumentUtils.checkNotNullNorEmpty(elementsToSign, "elementsToSign");
    ArgumentUtils.checkNotNullNorEmpty(credentials, "credentials");

    // Get the container document
    Document containerDoc = elementToAddSigTo.getOwnerDocument();

    // Check elements to sign are in the same document as the element to add
    // signatures to
    for (Element elementToSign : elementsToSign) {
      if (elementToSign == null) {
        String errMsg = "The 'elementsToSign' list has a null value.";
        throw new IllegalArgumentException(errMsg);
      }

      if (!containerDoc.equals(elementToSign.getOwnerDocument())) {
        String errMsg = "The element to sign, " + TextUtils.getDesc(elementToSign)
            + ", must belong to the same document as the element to add "
            + "the signatures to, " + TextUtils.getDesc(elementToAddSigTo) + ".";
        throw new IllegalArgumentException(errMsg);
      }
    }

    // Check no nulls in credentials.
    for (X500PrivateCredential credential : credentials) {
      if (credential == null) {
        String errMsg = "The 'credentials' list has a null value.";
        throw new IllegalArgumentException(errMsg);
      }
      // Note: Can only create a X500PrivateCredential that has both certificate
      // and private key set
    }

    // For each element to sign, create a reference.
    List<Reference> referenceList = new ArrayList<Reference>();
    for (Element elementToSign : elementsToSign) {
      // Get reference ID to the element to sign
      String referenceId = null;
      List<String> elemIdValues = getIdValues(elementToSign);
      if (!elemIdValues.isEmpty()) {
        // Element has an ID value, pick the first one
        referenceId = elemIdValues.get(0);
      } else {
        // Element has no ID value, so add one to the element

        // Generate a UUID for the reference ID value
        referenceId = "_" + UUID.randomUUID().toString();

        // Set the 'xml:id' attribute on element
        elementToSign.setAttributeNS(XML_NAMESPACE, XML_ID_QNAME, referenceId);
      }

      // Create an XML DSIG Reference containing the reference ID
      Reference reference = newReference(referenceId);
      referenceList.add(reference);
    }

    // Create an XML DSIG SignedInfo containing the list of References.
    SignedInfo signedInfo = newSignedInfo(referenceList);

    // Note: the following line is important when serializing the container.
    // If it is not called and the container is serialized, the signature
    // will become invalid.
    containerDoc.normalizeDocument();

    // Create the custom URI dereferencer since the default behaviour doesn't
    // recognise 'xml:id'
    URIDereferencer uriDereferencer = new CustomUriDereferencer();

    // For each credential, generate a signature
    for (X500PrivateCredential credential : credentials) {
      X509Certificate signingCertificate = credential.getCertificate();
      PrivateKey signingPrivateKey = credential.getPrivateKey();

      // Create an XML DSIG KeyInfo
      KeyInfo keyInfo = newKeyInfo(signingCertificate);

      // Create the XMLSignature object
      XMLSignature signature = XML_SIGNATURE_FACTORY.newXMLSignature(
          signedInfo, keyInfo);

      // Create a signing context
      DOMSignContext signContext = new DOMSignContext(signingPrivateKey,
          elementToAddSigTo);
      signContext.setURIDereferencer(uriDereferencer);
      signContext.setDefaultNamespacePrefix(XmlSigConstants.PREFIX_XML_SIGNATURE);

      // Generated XML DSIG signature. It will be added as a child element to
      // the 'sp:signatures' element.
      try {
        signature.sign(signContext);
      } catch (Exception e) {
        throw new XspException("Couldn't create signature with credential, "
            + TextUtils.getDesc(signingCertificate) + ".", e);
      }
    }
  }

  @Override
  public void check(Element signatureElem,
      CertificateVerifier certificateVerifier)
      throws SignatureValidationException,
      CertificateVerificationException, XspException {
    ArgumentUtils.checkNotNull(signatureElem, "signatureElem");

    check(Arrays.asList(signatureElem), certificateVerifier);
  }

  @Override
  public void check(List<Element> signatureElems,
      CertificateVerifier certificateVerifier)
      throws SignatureValidationException,
      CertificateVerificationException, XspException {
    ArgumentUtils.checkNotNullNorEmpty(signatureElems, "signatureElems");
    ArgumentUtils.checkNotNull(certificateVerifier, "certificateVerifier");

    // Check the 'signatureElems' list
    for (Element signatureElem : signatureElems) {
      if (signatureElem == null) {
        String errMsg = "The 'signatureElems' list has a null value.";
        throw new IllegalArgumentException(errMsg);
      }

      DomUtils.checkElement(signatureElem, XmlSigConstants.TAG_DS_SIGNATURE,
          XmlSigConstants.NS_XML_SIGNATURE);
    }

    // Create the custom URI dereferencer since the default behaviour doesn't
    // recognise 'xml:id'
    URIDereferencer uriDereferencer = new CustomUriDereferencer();

    // Verify each of the signatures.
    for (Element signatureElem : signatureElems) {
      // Unmarshal the DOM 'Signature' element.
      XMLSignature signature = unmarshalSignature(signatureElem);

      // Get the signing certificate from the XMLSignature.
      X509Certificate certificate = getSigningCertificate(signature);

      // Create a validation context that uses the custom URI dereferencer
      DOMValidateContext validateContext = new DOMValidateContext(certificate
          .getPublicKey(), signatureElem);
      validateContext.setURIDereferencer(uriDereferencer);

      // Validate the XMLSignature using the validation context.
      boolean valid = false;
      try {
        valid = signature.validate(validateContext);
      } catch (XMLSignatureException e) {
        throw new XspException("Couldn't do validation on an XML Signature.", e);
      }
      if (!valid) {
        throw new SignatureValidationException(
            "Invalid XML Signature signed by certificate: "
                + CertificateUtils.getSubjectName(certificate));
      }

      // Verify the certificate in the signature
      certificateVerifier.verify(certificate);
    }
  }

  /*
   * @see au.gov.nehta.xsp.XmlSignatureProfileService#getSigningCertificate(org.w3c.dom.Element)
   */
  public X509Certificate getSigningCertificate(Element signatureElem) throws XspException {
    DomUtils.checkElement(signatureElem, XmlSigConstants.TAG_DS_SIGNATURE,
        XmlSigConstants.NS_XML_SIGNATURE);

    // Unmarshal the DOM 'Signature' element.
    XMLSignature signature = unmarshalSignature(signatureElem);

    return getSigningCertificate(signature);
  }

  /*
   * @see au.gov.nehta.xsp.XmlSignatureProfileService#getDigestValues(org.w3c.dom.Element)
   */
  public Map<String, byte[]> getDigestValues(Element signatureElem) throws XspException {
    DomUtils.checkElement(signatureElem, XmlSigConstants.TAG_DS_SIGNATURE,
        XmlSigConstants.NS_XML_SIGNATURE);

    // Unmarshal the DOM 'Signature' element.
    XMLSignature signature = unmarshalSignature(signatureElem);

    return getDigestValues(signature);
  }

  /*
   * Find the possible values that can be used to identify the element in a URI
   * reference.
   */
  private static List<String> getIdValues(Element element) {
    assert (element != null);

    List<String> elemIdValues = new ArrayList<String>();
    if (element.hasAttributeNS(XML_NAMESPACE, XML_ID_LOCAL_NAME)) {
      elemIdValues
          .add(element.getAttributeNS(XML_NAMESPACE, XML_ID_LOCAL_NAME));
    }
    if (element.hasAttribute(XML_ID_QNAME)) {
      elemIdValues.add(element.getAttribute(XML_ID_QNAME));
    }
    for (String idTag : ID_ATTR_TAGS) {
      if (element.hasAttributeNS(null, idTag)) {
        elemIdValues.add(element.getAttributeNS(null, idTag));
      }
      if (element.hasAttribute(idTag)) {
        elemIdValues.add(element.getAttribute(idTag));
      }
    }
    return elemIdValues;
  }

  /*
   * Create a {@code Reference} object that has a URI of referenceId.
   */
  private static Reference newReference(String referenceId) throws XspException {
    assert ((referenceId != null) && (!referenceId.trim().isEmpty()));

    try {
      DigestMethod digestMethod = XML_SIGNATURE_FACTORY.newDigestMethod(
          DigestMethod.SHA1, null);

      Transform transform = XML_SIGNATURE_FACTORY.newTransform(
          CanonicalizationMethod.EXCLUSIVE, (TransformParameterSpec) null);

      return XML_SIGNATURE_FACTORY.newReference("#" + referenceId,
          digestMethod, Collections.singletonList(transform), null, null);
    } catch (Exception ex) {
      throw new XspException("Unable to create 'Reference'. " + ex.getMessage());
    }
  }

  /*
   * Create a {@code SignedInfo} object that contains the given {@code
   * Reference}.
   */
  private static SignedInfo newSignedInfo(List<Reference> referenceList)
      throws XspException {
    assert (referenceList != null);

    try {
      SignatureMethod signatureMethod = XML_SIGNATURE_FACTORY
          .newSignatureMethod(SignatureMethod.RSA_SHA1, null);

      CanonicalizationMethod canonicalisationMethod = XML_SIGNATURE_FACTORY
          .newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE,
              (C14NMethodParameterSpec) null);

      return XML_SIGNATURE_FACTORY.newSignedInfo(canonicalisationMethod,
          signatureMethod, referenceList);
    } catch (Exception ex) {
      throw new XspException("Unable to create 'SignedInfo'. "
          + ex.getMessage(), ex);
    }
  }

  /*
   * Create a {@code KeyInfo} object for a given certificate.
   */
  private static KeyInfo newKeyInfo(X509Certificate certificate) {
    assert (certificate != null);

    X509Data x509Data = KEY_INFO_FACTORY.newX509Data(Collections
        .singletonList(certificate));
    return KEY_INFO_FACTORY.newKeyInfo(Collections.singletonList(x509Data));
  }

  /*
   * Extract the {@code X509Certificate} from the {@code XMLSignature}.
   */
  private static X509Certificate getSigningCertificate(XMLSignature signature)
      throws XspException {
    assert (signature != null);

    // Get the KeyInfo
    KeyInfo keyInfo = signature.getKeyInfo();
    if (keyInfo == null) {
      throw new XspException("A 'Signature' doesn't have a 'KeyInfo'.");
    }

    // Get the list of 'ds:X509Data' from 'ds:KeyInfo'
    List<X509Data> x509DataObjects = new ArrayList<X509Data>();
    for (Object content : keyInfo.getContent()) {
      if (content instanceof X509Data) {
        x509DataObjects.add((X509Data) content);
      }
    }

    // Check there is only one 'ds:X509Data'
    if (x509DataObjects.size() == 0) {
      throw new XspException(
          "The 'KeyInfo' in a 'Signature' doesn't specify an 'X509Data'.");
    }
    if (x509DataObjects.size() > 1) {
      throw new XspException(
          "The 'KeyInfo' in a 'Signature' specifies multiple 'X509Data'.");
    }

    // Get the 'ds:X509Data'
    X509Data x509Data = x509DataObjects.get(0);

    // Get the 'ds:X509Certificate' within the 'ds:X509Data'
    List<X509Certificate> certificates = new ArrayList<X509Certificate>();
    for (Object content : x509Data.getContent()) {
      if (content instanceof X509Certificate) {
        certificates.add((X509Certificate) content);
      }
    }

    // Check there is only one 'ds:X509Certificate'
    if (certificates.size() == 0) {
      throw new XspException("The 'X509Data' in a 'Signature' doesn't "
          + "specify an 'X509Certificate'.");
    }
    if (certificates.size() > 1) {
      throw new XspException("The 'X509Data' in a 'Signature' specifies "
          + "multiple 'X509Certificate'.");
    }

    // Return the X.509 certificate
    return certificates.get(0);
  }

  /*
   * Extract the {@code DigestValue}s from the {@code XMLSignature}.
   */
  @SuppressWarnings("unchecked")
  private static Map<String, byte[]> getDigestValues(XMLSignature signature)
      throws XspException {

    assert (signature != null);

    SignedInfo signedInfo = signature.getSignedInfo();
    if (signedInfo == null) {
      throw new XspException("A 'Signature' does not have a 'SignedInfo'.");
    }

    List<Reference> referenceList = signedInfo.getReferences();
    if ((referenceList == null) || (referenceList.size() == 0)) {
      throw new XspException("The 'SignedInfo' in a 'Signature' does not have a 'Reference'.");
    }
    Map<String, byte[]> resultMap = new HashMap<String, byte[]>();
    for (Reference reference : referenceList) {
      String uri = reference.getURI();
      if (ArgumentUtils.isNullOrBlank(uri)) {
        throw new XspException("A 'Reference' in the 'Signature/SignedInfo' does not have a 'URI'.");
      }
      byte[] digestValue = reference.getDigestValue();
      if (digestValue == null) {
        throw new XspException("A 'Reference' in the 'Signature/SignedInfo' does not have a 'DigestValue'.");
      }
      resultMap.put(uri, digestValue);
    }

    return resultMap;
  }

  /*
   * Unmarshals the 'ds:Signature' DOM element into an XMLSignature object in
   * the Apache XML Signature library.
   */
  private static XMLSignature unmarshalSignature(Element signatureElem)
      throws XspException {
    assert (signatureElem != null);

    try {
      DOMStructure domStructure = new DOMStructure(signatureElem);
      return XML_SIGNATURE_FACTORY.unmarshalXMLSignature(domStructure);
    } catch (MarshalException ex) {
      throw new XspException("Couldn't unmarshall signature element. "
          + ex.getMessage(), ex);
    }
  }

  /*
   * Custom URI dereferencer to handle "xml:id" attribute.
   */
  private class CustomUriDereferencer implements URIDereferencer {

    /**
     * @see URIDereferencer#dereference(URIReference, XMLCryptoContext)
     */
    public Data dereference(URIReference uriReference, XMLCryptoContext context)
        throws URIReferenceException {
      assert (uriReference != null);
      assert (uriReference.getURI() != null);
      assert (context != null);

      // Get URI value checking it starts with a #
      String uriValue = uriReference.getURI().trim();
      if (!uriValue.startsWith("#")) {
        String errMsg = "Cannot look up the reference to '" + uriValue + "'."
            + " Only local URI references (starts with '#') are permitted.";
        throw new URIReferenceException(errMsg);
      }

      // Get the ID value reference by the URI by removing the '#' character
      String idValue = uriValue.substring(1);
      if (idValue.length() == 0) {
        String errMsg = "Cannot look up the reference to '" + uriValue + "'."
            + " No ID value was provided after the '#'.";
        throw new URIReferenceException(errMsg);
      }

      // Get container document
      Document containerDoc = getContainerDocument(context);
      if (containerDoc == null) {
        String errMsg = "Cannot look up the reference to '" + uriValue + "'."
            + " Couldn't retrieve the container document.";
        throw new URIReferenceException(errMsg);
      }

      // Find the element referenced by the ID value
      Element foundElem = findElementById(containerDoc, idValue);
      if (foundElem == null) {
        String errMsg = "Cannot look up the reference to '" + uriValue + "'."
            + " Couldn't find an XML element with the matching ID.";
        throw new URIReferenceException(errMsg);
      }

      try {
        String foundElemStr = DomUtils.serialiseToString(foundElem);
        return new OctetStreamData(new ByteArrayInputStream(foundElemStr
            .getBytes()));
      } catch (Exception e) {
        String errMsg = "Cannot look up the reference to '" + uriValue + "'."
            + " Couldn't serialse the XML element with the matching ID.";
        throw new URIReferenceException(errMsg);
      }
    }

    private Document getContainerDocument(XMLCryptoContext context) {
      if (context instanceof DOMSignContext) {
        DOMSignContext domSignContext = (DOMSignContext) context;
        Node parentNode = domSignContext.getParent();
        if (parentNode != null) {
          return parentNode.getOwnerDocument();
        }
      } else if (context instanceof DOMValidateContext) {
        DOMValidateContext domValidateContext = (DOMValidateContext) context;
        Node node = domValidateContext.getNode();
        if (node != null) {
          return node.getOwnerDocument();
        }
      }
      return null;
    }

    private Element findElementById(Document doc, String idValue) {
      assert (doc != null);
      assert (idValue != null);

      return findElementById(doc.getDocumentElement(), idValue);
    }

    private Element findElementById(Element element, String idValue) {
      assert (element != null);
      assert (idValue != null);

      // Get all ID values for this element
      List<String> elemIdValues = getIdValues(element);

      // Check if one of the element's ID values matches
      if (elemIdValues.contains(idValue)) {
        return element;
      }

      // Check for element with ID in child nodes
      NodeList nodeList = element.getChildNodes();
      for (int idx = 0; idx < nodeList.getLength(); idx++) {
        Node childNode = nodeList.item(idx);
        if (childNode instanceof Element) {
          Element foundElem = findElementById((Element) childNode, idValue);
          if (foundElem != null) {
            return foundElem;
          }
        }
      }
      return null;
    }
  }

}
