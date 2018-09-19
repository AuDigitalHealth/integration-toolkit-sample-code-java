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
package au.gov.nehta.vendorlibrary.pcehr.clients.documentexchange;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.Holder;

import org.apache.commons.lang.Validate;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.Client;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.CommonHeaderValidator;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.DateUtils;
import au.gov.nehta.xsp.CertificateValidator;
import au.net.electronichealth.ns.pcehr.svc.removedocument._1.RemoveDocumentPortType;
import au.net.electronichealth.ns.pcehr.svc.removedocument._1.RemoveDocumentService;
import au.net.electronichealth.ns.pcehr.svc.removedocument._1.StandardErrorMsg;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.Signature;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.removedocument._1.RemoveDocument;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.removedocument._1.RemoveDocument.DocumentRemovalReason;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.removedocument._1.RemoveDocumentResponse;

/**
 * A JAX-WS client to the PCEHR 'Remove Document' web service.
 */
public final class RemoveDocumentClient extends Client<RemoveDocumentPortType> {

  /**
   * Constructor - no certificate verification performed.
   *
   * @param sslSocketFactory  the {@link SSLSocketFactory} to be used when connecting to the web service provider (mandatory).
   * @param x509Certificate   the certificate key to be used for signing (mandatory)
   * @param privateKey        the private key to be used for signing (mandatory)
   * @param endpointAddress   the endpoint address of the web service (mandatory).
   * @param setLoggingEnabled set to <code>true</code> to enable logging (mandatory).
   */
  public RemoveDocumentClient(
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {
    super(
      RemoveDocumentService.class,
      RemoveDocumentPortType.class,
      sslSocketFactory,
      x509Certificate,
      privateKey,
      endpointAddress,
      setLoggingEnabled
    );
  }

  /**
   * Constructor - accepts an optional certificate verifier.
   *
   * @param sslSocketFactory    the {@link SSLSocketFactory} to be used when connecting to the web service provider (mandatory).
   * @param x509Certificate     the certificate key to be used for signing (mandatory)
   * @param certificateVerifier CertificateVerifier implementation (optional).
   * @param privateKey          the private key to be used for signing (mandatory)
   * @param endpointAddress     the endpoint address of the web service (mandatory).
   * @param setLoggingEnabled   set to <code>true</code> to enable logging (mandatory).
   */
  public RemoveDocumentClient(
    final SSLSocketFactory sslSocketFactory,
    final X509Certificate x509Certificate,
    final CertificateValidator certificateVerifier,
    final PrivateKey privateKey,
    final String endpointAddress,
    final boolean setLoggingEnabled
  ) {
    super(
      RemoveDocumentService.class,
      RemoveDocumentPortType.class,
      sslSocketFactory,
      x509Certificate,
      certificateVerifier,
      privateKey,
      endpointAddress,
      setLoggingEnabled
    );
  }

  /**
   * Remove a document from the document repository/registry.
   *
   * @param commonHeader  PCEHR common header
   * @param documentId    Unique identifier of the document to be removed.
   * @param removalReason Reason for the removal
   * @return Response status type
   * @throws StandardErrorMsg On operation failure.
   */
  public RemoveDocumentResponse removeDocument(
    PCEHRHeader commonHeader,
    String documentId,
    DocumentRemovalReason removalReason
  ) throws StandardErrorMsg {

    Validate.notNull(commonHeader, "'commonHeader' must be specified.");
    Validate.notEmpty(documentId, "'documentId' must be specified.");
    Validate.notNull(removalReason, "'removalReason' must be specified.");
    CommonHeaderValidator.validate(commonHeader, true); // IHINumber is required.

    RemoveDocument removeDocument = new RemoveDocument();
    removeDocument.setDocumentID(documentId);
    removeDocument.setReasonForRemoval(removalReason);

    Holder<RemoveDocumentResponse> removeDocumentResponseHolder = new Holder<RemoveDocumentResponse>();
    Holder<Signature> signatureHolder = null;

    getPort().removeDocument(
      removeDocument,
      removeDocumentResponseHolder,
      DateUtils.generateTimestamp(),
      signatureHolder,
      commonHeader
    );

    return removeDocumentResponseHolder.value;
  }
}
