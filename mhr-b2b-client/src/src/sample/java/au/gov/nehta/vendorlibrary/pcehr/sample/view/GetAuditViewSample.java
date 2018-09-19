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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.SSLSocketFactory;

import au.gov.nehta.vendorlibrary.pcehr.clients.view.GetAuditViewClient;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.constants.SampleEndpoints;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.constants.SampleValues;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.SecurityUtil;
import au.net.electronichealth.ns.pcehr.svc.getauditview._1.StandardErrorMsg;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.getauditview._1.GetAuditViewResponse;

import com.sun.xml.internal.ws.developer.JAXWSProperties;

/**
 * An example class showing usage of the Get Audit View web service client.
 */
public final class GetAuditViewSample {

  private GetAuditViewSample() {
  }

  public static void main(String[] args) throws IOException, GeneralSecurityException, ParseException, StandardErrorMsg {

    GetAuditViewClient client;

    // Sets the newly created sslsocketfactory as the default for all instances of the HttpsURLConnection class.
    SSLSocketFactory sslSocketFactory = SecurityUtil.getSslSocketFactory();

    // For testing purposes.
    System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");

    // Instantiate client, providing:
    //
    //  * socket factory;
    //  * endpoint URI string; and
    //  * set logging on/off.
    client = new GetAuditViewClient(
      sslSocketFactory,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      SampleEndpoints.GET_AUDIT_VIEW,
      true
    );

    //allow SSL connections to be set on a per client basis 
    client.setProperty(JAXWSProperties.SSL_SOCKET_FACTORY, sslSocketFactory);
    
    
    // Create a sample from date.
    Date fromDate = convertFromString(SampleValues.AUDIT_VIEW_FROM_DATE);

    // Create a sample to date.
    Date toDate = convertFromString(SampleValues.AUDIT_VIEW_TO_DATE);

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

    // Call operation.
    GetAuditViewResponse response = client.getAuditView(commonHeader, fromDate, toDate);

    // Process response.
    System.out.println("Response Status: " + response.getResponseStatus().getCode());
    System.out.println("Response Description: " + response.getResponseStatus().getDescription());

    if (!response.getAuditView().getEventTrails().isEmpty()) {
      System.out.println("Audit View Business Event: " + response.getAuditView().getEventTrails().get(0).getBusinessEvent());
    }
  }

  /**
   * Helper method to get date strings into the required type.
   *
   * @param dateString Unformatted date string.
   * @return Parsed Date object.
   * @throws ParseException thrown in the event that parsing fails.
   */
  private static Date convertFromString(String dateString) throws ParseException {
    return new SimpleDateFormat("yyyyMMddHHmm").parse(dateString);
  }

}
