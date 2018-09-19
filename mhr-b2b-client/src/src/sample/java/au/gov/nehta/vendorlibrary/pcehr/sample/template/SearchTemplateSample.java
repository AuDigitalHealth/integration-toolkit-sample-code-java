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
package au.gov.nehta.vendorlibrary.pcehr.sample.template;

import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.clients.template.SearchTemplateClient;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.constants.SampleEndpoints;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.constants.SampleValues;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.SecurityUtil;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.tplt.svc.searchtemplate._1.StandardErrorMsg;
import au.net.electronichealth.ns.tplt.xsd.interfaces.searchtemplate._1.SearchTemplateResponse;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * An example class showing usage of the Search Template web service client.
 */
public final class SearchTemplateSample {

  private SearchTemplateSample() {
  }

  public static void main(String[] args) throws IOException, GeneralSecurityException, StandardErrorMsg {

    SearchTemplateClient client;

    // Sets the newly created sslsocketfactory as the default for all instances of the HttpsURLConnection class.
    SSLSocketFactory sslSocketFactory = SecurityUtil.getSslSocketFactory();
    HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);

    // For testing purposes.
    System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");

    // Instantiate client, providing:
    //
    //  * socket factory;
    //  * endpoint URI string; and
    //  * set logging on/off.
    client = new SearchTemplateClient(
      sslSocketFactory,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      SampleEndpoints.SEARCH_TEMPLATE,
      true
    );

    // Create a sample template metadata list.
    //
    // Note: The following code is an example of how to build up the metadata list.
    //       Please refer to the TSS documentation for an exhaustive list of possible
    //       entries.
    //
    //    TemplateMetadataType metadataList = new TemplateMetadataType();
    //    TemplateMetadataType.Metadata metadata = new TemplateMetadataType.Metadata();
    //    metadata.setName("name");
    //    metadata.setValue("value");
    //    metadataList.getMetadatas().add(metadata);

    // Create a sample PCEHR header consisting of:
    //
    //  * User;
    //  * IHI number;
    //  * Product type;
    //  * Client system type; and
    //  * Accessing organisation.

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

    // Call operation, with no template metadata.
    SearchTemplateResponse response = client.searchTemplate(commonHeader, SampleValues.SEARCH_TEMPLATE_ID, SampleValues.SEARCH_TEMPLATE_METADATA);

    // Process response.
    System.out.println("Search Template Result: " + response.getResponseStatus().getCode());
  }
}
