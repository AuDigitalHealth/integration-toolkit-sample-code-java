package au.gov.nehta.vendorlibrary.pcehr.test.utils;

import au.gov.nehta.vendorlibrary.clinicalpackage.core.SubmissionSet;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.PackagingUtility;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.SignatureGenerationException;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XPathExpressions;
import au.net.electronichealth.ns.cdapackage.xsd.esignature._2012.PersonNameType;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.UUID;

public final class TestUtils {
  private TestUtils() {

  }

  public static void writeDoc(byte[] content, String testCaseId, String optionalSuffix) throws IOException {
    OutputStream out = new FileOutputStream("./src/test/resources/TestFiles/Generated/GetDocument/" + testCaseId + (optionalSuffix == null ? new Date().getTime() : optionalSuffix) + ".zip");
    try {
      out.write(content);
    } finally {
      out.close();
    }
  }

  public static SubmissionSet createPackage(final byte[] rootDocument, String approverId, PersonNameType approverName) throws GeneralSecurityException, SignatureGenerationException {
    // Begin building package.
    SubmissionSet.Builder subsetBuilder = new SubmissionSet.Builder();
    subsetBuilder.rootDocument(rootDocument);

    // Add signature to package.
    subsetBuilder.signature(PackagingUtility.generateSignature(
      rootDocument,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      approverId,
      approverName
    ));

    // Build package.
    return subsetBuilder.build();
  }

  public static byte[] modifyDocId(byte[] templateContent, Holder<String> currentId) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(new ByteArrayInputStream(templateContent));
    XPathFactory xPathFactory = XPathFactory.newInstance();
    XPath xPath = xPathFactory.newXPath();
    NodeList nodes = (NodeList) xPath
      .compile(XPathExpressions.DOCUMENT_ID)
      .evaluate(doc, XPathConstants.NODESET);

    // Check if ID is OID or UUID.
    String presentId = nodes.item(0).getNodeValue();

    UUID presentIdUuid = null;

    try {
      presentIdUuid = UUID.fromString(presentId);
    } catch (IllegalArgumentException e) {
      // Do nothing as this just means it's another format.
      System.out.println();
    }

    String newId = null;

    if (presentIdUuid != null) {
      // Generate a new doc ID.
      newId = UUID.randomUUID().toString();
    } else {
      newId = nodes.item(0).getNodeValue() + "." + new Date().getTime();
    }
    currentId.value = newId;
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
