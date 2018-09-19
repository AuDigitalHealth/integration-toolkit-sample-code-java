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
package au.gov.nehta.vendorlibrary.pcehr.sample.view;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.net.ssl.SSLSocketFactory;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExtrinsicObjectType;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentQueryParams;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentStatus;
import au.gov.nehta.vendorlibrary.pcehr.clients.view.GetDocumentListClient;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.constants.SampleEndpoints;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.constants.SampleValues;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.SecurityUtil;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;

import com.sun.xml.internal.ws.developer.JAXWSProperties;

/**
 * An example class showing usage of the Get Document List web service client.
 */
public final class GetDocumentListSample {

  private GetDocumentListSample() {
  }

  public static void main(String[] args) throws IOException, GeneralSecurityException {

    GetDocumentListClient client;

    // Sets the newly created sslsocketfactory as the default for all instances of the HttpsURLConnection class.
    SSLSocketFactory sslSocketFactory = SecurityUtil.getSslSocketFactory();

    // For testing purposes.
    System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");

    // Instantiate client, providing:
    //
    //  * socket factory;
    //  * endpoint URI string; and
    //  * set logging on/off.
    client = new GetDocumentListClient(
      sslSocketFactory,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      SampleEndpoints.GET_DOCUMENT_LIST,
      true);
    
    //allow SSL connections to be set on a per client basis 
    client.setProperty(JAXWSProperties.SSL_SOCKET_FACTORY, sslSocketFactory);

    // Create sample document query parameter(s).
    DocumentQueryParams queryParams = new DocumentQueryParams();
    queryParams.getStatuses().add(DocumentStatus.DEPRECATED);

    // Create a sample user, using helper method.
    PCEHRHeader.User user = MessageComponents.createUser(
      SampleValues.USER_ID_TYPE,
      SampleValues.USER_ID,
      SampleValues.USER_ROLE,
      SampleValues.USER_NAME,
      SampleValues.USER_USE_ROLE_FOR_AUDIT
    );

    // Create a sample product type, using helper method.
    PCEHRHeader.ProductType productType = MessageComponents.createProductType(
      SampleValues.PRODUCT_TYPE_VENDOR,
      SampleValues.PRODUCT_NAME,
      SampleValues.PRODUCT_VERSION,
      SampleValues.PRODUCT_PLATFORM
    );

    // Create a sample accessing organisation, using helper method.
    PCEHRHeader.AccessingOrganisation accessingOrganisation = MessageComponents.createAccessingOrganisation(
      SampleValues.ACCESSING_ORGANISATION_ID,
      SampleValues.ACCESSING_ORGANISATION_NAME,
      SampleValues.ACCESSING_ORGANISATION_ALTERNATE_NAME
    );

    // Create a sample PCEHR request header, using helper method.
    PCEHRHeader commonHeader = MessageComponents.createRequest(
      user,
      SampleValues.IHI_NUMBER,
      productType,
      SampleValues.CLIENT_SYSTEM_TYPE_CIS,
      accessingOrganisation
    );

    // Call operation - get all PCEHR documents associated with given IHI.
    AdhocQueryResponse responseAll = client.getDocumentList(commonHeader);

    // This operation takes query parameters. Using this example code, the check on each document
    // would be for a status of XDSConstants.EOT_STATUS_DEPRECATED.
    //
    // Call operation - get PCEHR documents associated with given IHI that match query
    //                  parameters.
    // AdhocQueryResponse responseRestricted = client.getDocumentList(commonHeader, queryParams);

    // Process response.
    System.out.println("Response Status: " + responseAll.getStatus());

    System.out.println("Response Document Count : " + responseAll.getRegistryObjectList().getExtrinsicObjects().size());

    // Get list of documents.
    List<ExtrinsicObjectType> docs = responseAll.getRegistryObjectList().getExtrinsicObjects();

    // Get document status.
    for (ExtrinsicObjectType doc : docs) {
      System.out.println("Document Status: " + doc.getStatus());
    }
  }
}
