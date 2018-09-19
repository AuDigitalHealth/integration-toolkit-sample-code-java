package au.gov.nehta.vendorlibrary.pcehr.sample.common.util;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XPathExpressions;
import org.apache.commons.lang.Validate;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.Holder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class DocumentUtils {

  /**
   * Helper method to take an existing CDA document, load it into memory and modify the root ID.
   *
   * @param templateContent root document byte content to be used as a template for the new document content.
   * @param currentId       Holder value to allow the new, modified ID to be fed back to the calling code.
   * @return byte array of CDA document containing the newly modified ID.
   * @throws IOException
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws XPathExpressionException
   * @throws TransformerException
   */
  private byte[] modifyDocId(byte[] templateContent, Holder<String> currentId) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException {

    Validate.notNull(templateContent, "'templateContent' must be specified");
    Validate.notNull(templateContent, "'currentId' must be specified");

    // Parse and retrieve document nodes from byte content.
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(new ByteArrayInputStream(templateContent));
    XPathFactory xPathFactory = XPathFactory.newInstance();
    XPath xPath = xPathFactory.newXPath();

    NodeList nodes = (NodeList) xPath
      .compile(XPathExpressions.DOCUMENT_ID)
      .evaluate(doc, XPathConstants.NODESET);

    // Check if ID is OID or UUID.
    // TODO: currently, if used for documentUpload, only OIDs are supported.
    String presentId = nodes.item(0).getNodeValue();

    UUID presentIdUuid = null;

    try {
      presentIdUuid = UUID.fromString(presentId);
    } catch (IllegalArgumentException e) {
      // Do nothing as this just means it's another format.
    }

    // Create a new ID.
    String newId = null;

    if (presentIdUuid != null) {
      // Generate a new doc ID.
      newId = UUID.randomUUID().toString();
    } else {
      newId = nodes.item(0).getNodeValue() + "." + new Date().getTime();
    }

    currentId.value = newId;

    // Modify the current ID and return a byte array of the XML content.
    nodes.item(0).setNodeValue(newId);
    Source source = new DOMSource(doc);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Result result = new StreamResult(out);
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.transform(source, result);

    return out.toByteArray();
  }
}
