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
package au.gov.nehta.vendorlibrary.clinicalpackage.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.bouncycastle.util.encoders.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import _org.w3.DigestMethodType;
import _org.w3.ManifestType;
import _org.w3.ReferenceType;
import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.vendorlibrary.clinicalpackage.core.Member;
import au.gov.nehta.vendorlibrary.clinicalpackage.core.SubmissionSet;
import au.gov.nehta.vendorlibrary.clinicalpackage.enums.ExpressionType;
import au.gov.nehta.vendorlibrary.clinicalpackage.enums.UriTypes;
import au.gov.nehta.vendorlibrary.clinicalpackage.enums.XMLNamespaces;
import au.gov.nehta.vendorlibrary.common.security.SignedContainerProfileUtil;
import au.gov.nehta.xsp.CertificateValidator;
import au.gov.nehta.xsp.SignatureValidationException;
import au.gov.nehta.xsp.XspException;
import au.net.electronichealth.ns.cdapackage.xsd.esignature._2012.ApproverType;
import au.net.electronichealth.ns.cdapackage.xsd.esignature._2012.ESignatureType;
import au.net.electronichealth.ns.cdapackage.xsd.esignature._2012.ObjectFactory;
import au.net.electronichealth.ns.cdapackage.xsd.esignature._2012.PersonNameType;
import au.net.electronichealth.ns.xsp.xsd.signedpayload._2010.SignedPayloadDataType;
import au.net.electronichealth.ns.xsp.xsd.signedpayload._2010.SignedPayloadType;

/**
 * Provides various utility methods required to create and extract a package.
 */
public final class PackagingUtility {

  private static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");

/**
   * I/O buffer.
   */
  private static final int BUFFER = 2048;

  private static final String FQ_ENTRY_FORMAT = "%s%s";

  /**
   * SHA-1 digest method.
   */
  private static final String DIGEST_METHOD_SHA1 = "sha1";

  /**
   * FQ pattern.
   */
  private static final String FQ_PATTERN = ".+[/\\\\].+[/\\\\]";

  /**
   * Private constructor prevents instantiation.
   */
  private PackagingUtility() {
  }

  /**
   * Creates ZIP file byte array.
   *
   * @param submissionSet Populated Submission set.
   * @return byte array
   * @throws IOException Thrown if problem occurs creating ZIP file.
   */
  public static byte[] createZip(final SubmissionSet submissionSet) throws IOException {

    ArgumentUtils.checkNotNull(submissionSet, "submissionSet");

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ZipOutputStream zip = new ZipOutputStream(bos);

    // Retrieve package components.
    Member rootDocument = submissionSet.getRootDocument();
    List<Member> attachments = submissionSet.getAttachments();
    Member signature = submissionSet.getSignature();

    // Add directory entries.
    zip.putNextEntry(new ZipEntry("IHE_XDM/"));
    zip.putNextEntry(new ZipEntry("IHE_XDM/SUBSET01/"));

    // Add root file to submission set directory.
    zip.putNextEntry(new ZipEntry(String.format(FQ_ENTRY_FORMAT, UriTypes.FQ_SUBSET_DIR.getUri(), rootDocument.getUri())));
    zip.write(rootDocument.getFileContent());

    // If it exists, add signature to submission set directory.
    if (submissionSet.getSignature() != null) {
      zip.putNextEntry(new ZipEntry(String.format(FQ_ENTRY_FORMAT, UriTypes.FQ_SUBSET_DIR.getUri(), signature.getUri())));
      zip.write(signature.getFileContent());
    }

    // If they exist, add each attachment to the submission set directory.
    if (attachments != null && !attachments.isEmpty()) {

      // Consolidated list of entries.
      List<String> entries = new ArrayList<String>();


      for (Member file : attachments) {

        // Add directory entries if path is present, removing duplicates.
        List<String> currentEntries = getPathLevels(file.getUri());
        currentEntries.removeAll(entries);
        entries.addAll(currentEntries);

        zip.putNextEntry(new ZipEntry(String.format(FQ_ENTRY_FORMAT, UriTypes.FQ_SUBSET_DIR.getUri(), file.getUri())));
        zip.write(file.getFileContent());
      }

      for (String entry : entries) {
        zip.putNextEntry(new ZipEntry(String.format(FQ_ENTRY_FORMAT, UriTypes.FQ_SUBSET_DIR.getUri(), entry)));
      }
    }

    // Close ZIP output stream.
    zip.close();

    return bos.toByteArray();
  }

  /**
   * Helper method to output a {@link au.gov.nehta.vendorlibrary.clinicalpackage.core.SubmissionSet} ZIP file to a specific file
   * path.
   *
   * @param submissionSet  {@link SubmissionSet}
   * @param outputFilePath Fully qualified file path to create the output ZIP file.
   * @throws IOException Thrown if I/O operations fail.
   */
  public static void writeZip(final SubmissionSet submissionSet, String outputFilePath) throws IOException {

    FileOutputStream out = null;

    try {
      out = new FileOutputStream(new File(outputFilePath));
      out.write(createZip(submissionSet));
      out.flush();
    } catch (IOException e) {
      throw new IOException("Error writing package to file", e);
    } finally {
      if (out != null) {
        out.close();
      }
    }
  }

  /**
   * Retrieve a named entry from the package entry list.
   *
   * @param entries   List of all entries.
   * @param searchKey Relative file name search key.
   * @return Matching entry.
   */
  private static Map.Entry<String, byte[]> findKnownEntry(final Map<String, byte[]> entries, final String searchKey) {
    for (Map.Entry<String, byte[]> entry : entries.entrySet()) {
      Pattern pattern = Pattern.compile(FQ_PATTERN + searchKey, Pattern.CASE_INSENSITIVE);
      Matcher matcher = pattern.matcher(entry.getKey());
      if (matcher.matches()) {
        return entry;
      }
    }
    return null;
  }

  /**
   * Retrieve all attachments from the package entry list.
   *
   * @param entries List of all entries.
   * @return Subset of entries.
   */
  private static Map<String, byte[]> findAttachments(final Map<String, byte[]> entries) {

    Map<String, byte[]> subset = new HashMap<String, byte[]>();

    for (Map.Entry<String, byte[]> entry : entries.entrySet()) {
      Pattern pattern = Pattern.compile(FQ_PATTERN + "*", Pattern.CASE_INSENSITIVE);
      Matcher matcher = pattern.matcher(entry.getKey());
      if (matcher.matches()) {
        subset.put(entry.getKey().replaceAll(UriTypes.FQ_SUBSET_DIR.getUri(), ""), entry.getValue());
      }
    }

    return subset;
  }

  /**
   * Retrieve
   *
   * @param entries
   * @return
   */
  private static Map.Entry<String, byte[]> getDocument(final Map<String, byte[]> entries, final UriTypes documentUri) {
    return findKnownEntry(entries, documentUri.getUri());
  }

  /**
   * Extract a package from a collection of fully qualified file names and file content.
   *
   * @param path Output path.
   * @return Populated {@link SubmissionSet} or null.
   * @throws IOException Thrown if package cannot be extracted.
   */
  public static SubmissionSet extractPackage(final String path) throws IOException {

    Map<String, byte[]> entries = PackagingUtility.extractZipEntries(path);

    // Builder instance to which package contents is added, supplying root document file content.
    SubmissionSet.Builder packageBuilder = new SubmissionSet.Builder();

    // Package components to populate.

    Map.Entry<String, byte[]> rootDocument = getDocument(entries, UriTypes.ROOT_DOCUMENT);

    // Populate and build root document, if it exists.
    if (rootDocument != null) {

      packageBuilder.rootDocument(rootDocument.getValue());

      // Remove processed root document entry.
      entries.remove(rootDocument.getKey());
    }

    // Add signature if it exists
    Map.Entry<String, byte[]> signature = getDocument(entries, UriTypes.SIGNATURE);

    if (signature != null) {

      // Populate and build signature.
      packageBuilder.signature(signature.getValue());

      // Remove processed signature entry.
      entries.remove(signature.getKey());
    }

    // Populate and build attachments.
    Map<String, byte[]> attachments = findAttachments(entries);

    if (attachments != null && !attachments.isEmpty()) {
      for (Map.Entry<String, byte[]> entry : attachments.entrySet()) {
        packageBuilder.attachment(entry.getKey(), entry.getValue());
      }
    }

    // Populate and build
    return packageBuilder.build();
  }

  /**
   * Read ZIP entries and file content into memory.
   *
   * @param path Location of the package ZIP file.
   * @return {@link Map} containing list of matching file names and file content byte arrays, or null.
   * @throws IOException Thrown if ZIP I/O operations fail.
   */
  public static Map<String, byte[]> extractZipEntries(final String path) throws IOException {

    Map<String, byte[]> result = new HashMap<String, byte[]>();
    ZipFile zip = null;
		try {
			zip = new ZipFile(path);

			Enumeration<? extends ZipEntry> e = zip.entries();

			// Loop through ZIP entries, extracting names and content
			while (e.hasMoreElements()) {
				ZipEntry zipFile = e.nextElement();

				// Skip directories and only read files
				if (!zipFile.isDirectory()) {
					result.put(zipFile.getName(), readZipEntry(zip.getInputStream(zipFile)));
				}
			}
    }finally{
    	if(zip != null){
    		zip.close();
    	}
    }
    
    // Determine whether to return a populated Map or null.
    if (result.isEmpty()) {
      return null;
    } else {
      return result;
    }
   
  }

  /**
   * Reads {@link ZipEntry}  from the zip file's input stream.
   *
   * @param zis {@link InputStream}
   * @return File content as a byte array.
   * @throws IOException Thrown if ZIP I/O operations fail.
   */
  private static byte[] readZipEntry(final InputStream zis) throws IOException {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] buffer = new byte[BUFFER];
    int count;

    count = zis.read(buffer);
    while (count != -1) {
      baos.write(buffer, 0, count);
      count = zis.read(buffer);
    }
    return baos.toByteArray();
  }

  /**
   * Creates a signature of the package's root CDA document.
   *
   * @param fileContent  Root document member file content to sign.
   * @param signingCert  Signing certificate.
   * @param privateKey   Private key used to sign payload.
   * @param approverId   Approver ID URI.
   * @param approverName Aprrover name object.
   * @return Byte array of signed payload container XML.
   * @throws SignatureGenerationException Thrown when signature generation fails.
   */
  public static byte[] generateSignature(
    final byte[] fileContent,
    final X509Certificate signingCert,
    final PrivateKey privateKey,
    final String approverId,
    final PersonNameType approverName) throws SignatureGenerationException {

    ArgumentUtils.checkNotNull(fileContent, "fileContent");
    ArgumentUtils.checkNotNull(signingCert, "signingCert");
    ArgumentUtils.checkNotNull(privateKey, "privateKey");
    ArgumentUtils.checkNotNull(approverId, "approverId");
    ArgumentUtils.checkNotNull(approverName, "approverName");

    ESignatureType eSignature = new ESignatureType();

    // Set signing time to now.
    Calendar signingTime = Calendar.getInstance(UTC_TIME_ZONE);
    eSignature.setSigningTime(signingTime);

    // Create hash and Base64 encode it.
    try {
      eSignature.setManifest(createManifest(
        createReference(
          UriTypes.ROOT_DOCUMENT.getUri(),
          calculateSha1Hash(fileContent)
        )
      )
      );
    } catch (URISyntaxException e) {
      throw new SignatureGenerationException("Invalid URI", e);
    }

    eSignature.setApprover(createApprover(approverId, approverName));

    // Generate signed payload content
    Document signedContainer;
    try {
      signedContainer = SignedContainerProfileUtil.getSignedPayload(marshalESignature(eSignature), signingCert, privateKey);
    } catch (XspException e) {
      throw new SignatureGenerationException("Unable to produce signed payload container", e);
    } catch (JAXBException e) {
      throw new SignatureGenerationException("Unable to marshal eSignature", e);
    }

    // Transform XML and output as byte[]
    try {
      return transform(signedContainer);
    } catch (TransformerException e) {
      throw new SignatureGenerationException("Unable to transform container", e);
    }
  }

  /**
   * Helper method to populate manifest reference list.
   *
   * @param reference Populated reference.
   * @return Populated manifest type.
   */
  private static ManifestType createManifest(ReferenceType reference) {
    ManifestType manifest = new ManifestType();
    manifest.getReference().add(reference);
    return manifest;
  }

  /**
   * Helper method to populate reference.
   *
   * @param cdaIdentifier CDA URI.
   * @param digestValue   digest value byte array.
   * @return Populated {@link ReferenceType} object.
   * @throws URISyntaxException Thrown when URI cannot be parsed.
   */
  private static ReferenceType createReference(final String cdaIdentifier, final byte[] digestValue) throws URISyntaxException {

    ArgumentUtils.checkNotNull(cdaIdentifier, "cdaIdentifier");

    ReferenceType reference = new ReferenceType();
    reference.setDigestMethod(createDigestMethod(XMLNamespaces.DS.getNamespace() + DIGEST_METHOD_SHA1));
    reference.setURI(cdaIdentifier);
    reference.setDigestValue(digestValue);

    return reference;
  }

  /**
   * Helper method to populate digest method.
   *
   * @param digestMethod Digest method URI.
   * @return Populated {@link DigestMethodType} object.
   */
  private static DigestMethodType createDigestMethod(final String digestMethod) {
    DigestMethodType type = new DigestMethodType();
    type.setAlgorithm(digestMethod);
    return type;
  }

  /**
   * Helper method to populate eSignature Approver.
   *
   * @param personId   Approver ID URI.
   * @param personName Approver name details.
   * @return Populated {@link ApproverType} object.
   */
  private static ApproverType createApprover(final String personId, final PersonNameType personName) {
    ApproverType approver = new ApproverType();
    approver.setPersonId(personId);
    approver.setPersonName(personName);
    return approver;
  }

  /**
   * Helper method to remove XML declaration.
   *
   * @param doc XML doc.
   * @return XML byte array.
   * @throws TransformerException Thrown if XML cannot be transformed.
   */
  private static byte[] transform(Document doc) throws TransformerException {
    Transformer transformer;
    try {
      transformer = TransformerFactory.newInstance().newTransformer();
    } catch (TransformerConfigurationException e) {
      throw new TransformerFactoryConfigurationError(e, "Unexpected problem encountered when configuring the transformer factory.");
    }
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    StreamResult transformedDoc = new StreamResult(new StringWriter());
    DOMSource source = new DOMSource(doc);

    try {
      transformer.transform(source, transformedDoc);
    } catch (TransformerException e) {
      throw new TransformerException("Unexpected problem encountered when transforming XML.", e);
    }
    // Output signed payload to byte array
    return transformedDoc.getWriter().toString().getBytes();
  }

  /**
   * Calculates SHA-1 hash from a byte array.
   *
   * @param value Input byte array.
   * @return SHA-1 hash.
   */
  private static byte[] calculateSha1Hash(byte[] value) {
    try {
      MessageDigest md = MessageDigest.getInstance(DIGEST_METHOD_SHA1);
      return md.digest(value);
    } catch (NoSuchAlgorithmException e) {
      // This should never be encountered given we are using a valid constant
      throw new IllegalStateException("Unexpected digest method encountered.", e);
    }
  }

  /**
   * Helper method to produce an XML fragment containing an eSignature.
   *
   * @param eSignature eSignature to marshal to string.
   * @return XML string.
   * @throws JAXBException Thrown if marshalling fails.
   */
  private static String marshalESignature(ESignatureType eSignature) throws JAXBException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    JAXBContext jc = JAXBContext.newInstance(ESignatureType.class);

    // Create marshaller
    Marshaller marshaller = jc.createMarshaller();

    // Marshal object to byte[]
    ObjectFactory of = new ObjectFactory();
    marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
    marshaller.marshal(of.createESignature(eSignature), outputStream);
    return outputStream.toString();
  }
  
  /**
   * Verifies a package, comparing root hash and certificate verification.
   *
   * @param submissionSet Extracted package.
   * @param validator  Validation param
   * @throws SignatureGenerationException Thrown if signature generation fails.
   * @throws PackageExtractionException   Thrown if signature extraction fails.
   * @throws VerificationException        Thrown if package verification fails.
   */
  public static void verifyPackage(final SubmissionSet submissionSet, final CertificateValidator validator) throws
    SignatureGenerationException,
    PackageExtractionException,
    VerificationException {

    // Verify that the root document file content matches the signature hash value.
    Document signature = extractFile(submissionSet.getSignature().getFileContent());
    try {
      verifyRootHash(signature, submissionSet.getRootDocument().getFileContent());
    } catch (JAXBException e) {
      throw new VerificationException("Unable to verify root document hash.", e);
    }

    // Verify the signature is valid.
    try {
      verifySignature(signature, validator);
    } catch (SignatureValidationException e) {
      throw new VerificationException("Unable to verify signature.", e);
    }

    // Only perform attachment verification if attachments are present.
    if (!submissionSet.getAttachments().isEmpty()) {
      try {
        verifyAttachments(submissionSet);
      } catch (AttachmentVerificationException e) {
        throw new VerificationException("Unable to verify package attachment(s).", e);
      }
    }
  }

  /**
   * @param subset
   */
  private static void verifyAttachments(final SubmissionSet subset) throws AttachmentVerificationException, PackageExtractionException {

    Document rootDocument;

    // Parse the CDA root document file content.
    try {
      rootDocument = extractFile(subset.getRootDocument().getFileContent());
    } catch (PackageExtractionException e) {
      throw new PackageExtractionException("Unable to extract root document.", e);
    }

    if (rootDocument == null) {
      throw new PackageExtractionException("Unable to extract root document - null value.");
    }

    // Assuming we now have the DOM for rootDocument, extract all integrityChecks.
    try {
      NodeList integrityChecks = extractIntegrityChecks(rootDocument);

      Map<String, String> integrityDetails = getIntegrityDetails(integrityChecks);

      // If an integrity check has a matching attachment, verify that digest value.
      for (Map.Entry<String, String> entry : integrityDetails.entrySet()) {

        // Check for a corresponding attachment member.
        for (Member member : subset.getAttachments()) {
          if (entry.getKey().compareToIgnoreCase(member.getUri()) == 0) {

            // Given that it exists, compare the hashes.
            String actualHash = new String(Base64.encode(calculateSha1Hash(member.getFileContent())));
            String expectedHash = entry.getValue();

            if (actualHash.compareTo(expectedHash) != 0) {
              throw new AttachmentVerificationException(
                String.format("Attachment verification failed  - integrity check digest value mismatch on '%s'\n\tActual: %s\n\tExpected: %s",
                  entry.getKey(),
                  actualHash,
                  expectedHash
                ));
            }
          }
        }
      }
    } catch (XPathExpressionException e) {
      throw new PackageExtractionException("Unexpected error encountered when extracting attachment integrity checks nodes from CDA document XML.");
    }
  }

  private static Map<String, String> getIntegrityDetails(final NodeList nodeList) throws AttachmentVerificationException {

    Map<String, String> integrityDetails = new HashMap<String, String>();

    for (int i = 0; i < nodeList.getLength(); i++) {

      Node node = nodeList.item(i);
      String integrityCheck = getAttributeValue(node, "integrityCheck");
      String integrityCheckAlgorithm = getAttributeValue(node, "integrityCheckAlgorithm");

      // Given that SHA-1 is the only supported (and default, if null) algorithm type, throw an exception if neither case is met.
      if (integrityCheckAlgorithm == null || integrityCheckAlgorithm.compareToIgnoreCase("sha-1") == 0) {

        // Retrieve the attachment reference.
        integrityDetails.put(getAttachmentReference(node), integrityCheck);
      }
    }
    return integrityDetails;
  }

  private static String getAttachmentReference(final Node node) {

    NodeList childNodes = node.getChildNodes();

    for (int i = 0; i < childNodes.getLength(); i++) {
      Node childNode = childNodes.item(i);

      if (childNode != null && childNode.getNodeName().compareToIgnoreCase("reference") == 0) {
        return getAttributeValue(childNode, "value");
      }
    }

    return null;
  }

  /**
   * Retrieve a given node attribute value.
   *
   * @param node          Node to retrieve from.
   * @param attributeName Specific attribute name.
   * @return String, or null in the event that there was no attribute present.
   */

  private static String getAttributeValue(final Node node, final String attributeName) {

    String result = null;
    NamedNodeMap attributes = node.getAttributes();

    if (attributes != null && attributes.getLength() > 0) {
      Node attribute = attributes.getNamedItem(attributeName);

      if (attribute != null) {
        result = attribute.getNodeValue();
      }
    }

    return result;
  }

  /**
   * Extracts a list of the CDA document's integrity checks for attachments.
   *
   * @param rootDocument root document document object model.
   * @return Extracted node list.
   * @throws XPathExpressionException
   */
  private static NodeList extractIntegrityChecks(final Document rootDocument) throws XPathExpressionException {
    XPath xPath = XPathFactory.newInstance().newXPath();
    xPath.setNamespaceContext(new CDANamespaceContext());

    XPathExpression expression = xPath.compile(ExpressionType.INTEGRITY_CHECK.getExpression());

    return (NodeList) expression.evaluate(rootDocument, XPathConstants.NODESET);
  }


  /**
   * Verifies the hash value of the extract root document file content vs. the eSignature's hash value.
   *
   * @param signature       Signature DOM.
   * @param rootFileContent Root document file content.
   * @throws JAXBException              Thrown in the event of an unmarshalling error.
   * @throws PackageExtractionException Thrown if signature extraction fails.
   */
  private static void verifyRootHash(final Document signature, final byte[] rootFileContent) throws
    JAXBException,
    SignatureGenerationException,
    PackageExtractionException,
    VerificationException {

    // Calculate extracted root document hash.
    final byte[] currentRootHash = calculateSha1Hash(rootFileContent);
    byte[] expectedRootHash;

    // Retrieve eSignature hash.
    Unmarshaller unmarshaller = JAXBContext.newInstance(SignedPayloadType.class).createUnmarshaller();
    SignedPayloadType signedPayload = unmarshaller.unmarshal(signature, SignedPayloadType.class).getValue();

    SignedPayloadDataType signedPayloadData = signedPayload.getSignedPayloadData();

    Element eSignatureElement;

    // Ensure the signature element is present. There are potential exceptions that can occur here otherwise.
    try {
      eSignatureElement = (Element) signedPayloadData.getAny();
    } catch (Exception e) {
      throw new VerificationException("Unexpected XML content encountered when attempting to verify root document hash.", e);
    }

    // Unmarshal eSignature element
    unmarshaller = JAXBContext.newInstance(ESignatureType.class).createUnmarshaller();
    ESignatureType eSignature = unmarshaller.unmarshal(eSignatureElement, ESignatureType.class).getValue();

    // Retrieve hash value
    expectedRootHash = eSignature.getManifest().getReference().get(0).getDigestValue();

    // Compare the two hashes for equality.
    if (expectedRootHash == null || !Arrays.equals(expectedRootHash, currentRootHash)) {
      throw new PackageExtractionException("Root hash in the eSignature does not match the package's root document.");
    }
  }

  /**
   * Helper method to retrieve the file's document object model.
   *
   * @param fileContent File content byte array.
   * @return Extracted {@link Document} object.
   * @throws PackageExtractionException Thrown when signature extraction fails.
   */
  private static Document extractFile(final byte[] fileContent) throws PackageExtractionException {

    // Parse XML and create XML document object
    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
    domFactory.setNamespaceAware(true);
    DocumentBuilder domBuilder;
    try {
      domBuilder = domFactory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new PackageExtractionException("Unable to extract file.", e);
    }

    // Parse file.
    Document doc;
    try {
      doc = domBuilder.parse(new ByteArrayInputStream(fileContent));
    } catch (SAXException e) {
      throw new PackageExtractionException("Unable to parse XML.", e);
    } catch (IOException e) {
      throw new PackageExtractionException("Unable to extract file.", e);
    }
    return doc;
  }

  /**
   * Verifies a package signature.
   *
   * @param signature {@link Member} containing signature to verify (not null).
   * @param verifier  Optional certificate validator.
   * @throws SignatureValidationException Thrown when signature validation fails.
   */
  private static void verifySignature(final Document signature, final CertificateValidator validator) throws SignatureValidationException {

    ArgumentUtils.checkNotNull(signature, "signature");

    if (validator != null) {

      SignedContainerProfileUtil.verifySignature(signature, validator);

    } else {
      // Attempt to verify the root document against signature.
      SignedContainerProfileUtil.verifySignature(signature);
    }
  }

  /**
   * Takes a path and breaks it up into the various levels of directory hierarchy.
   * <p/>
   * eg. src/test/something/file.txt
   * <p/>
   * 1. src/
   * 2. src/test
   * 3. src/test/something
   * 4. src/test/something/file.txt
   *
   * @param uri URI to process.
   * @return List of compounding directories.
   */
  private static List<String> getPathLevels(final String uri) {
    ArgumentUtils.checkNotNull(uri, "uri");

    List<String> result = new ArrayList<String>();

	/* PAW -REMOVED ) in the split characters 23/2/17*/
    String[] dirs = uri.split("/|\\");

    if (dirs.length != 0) {

      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < dirs.length - 1; i++) {
        sb.append(dirs[i]).append("/");
        result.add(sb.toString());
      }
    }

    return result;
  }
}
