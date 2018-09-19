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
package au.gov.nehta.vendorlibrary.pcehr.sample.documentexchange;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CodedValue;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.FormatCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HealthcareFacilityTypeCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.PracticeSettingTypeCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.FileUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.documentexchange.UploadDocumentClient;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.constants.SampleEndpoints;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.constants.SampleValues;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.SecurityUtil;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;
import org.xml.sax.SAXException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * An example class showing usage of the Upload Document web service client.
 */
public class UploadDocumentSample {

  public static void main(String[] args) throws IOException, GeneralSecurityException, SAXException, XPathExpressionException, ParserConfigurationException {

    UploadDocumentClient client;

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
    client = new UploadDocumentClient(
      sslSocketFactory,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      SampleEndpoints.UPLOAD_DOCUMENT,
      true
    );

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

    // Load package file.
    byte[] packageContent = FileUtils.loadFile(new File("<enter path here>"));

    //this should be stored somewhere as it is is highly reused.
	String healthSummarytemplateID = "1.2.36.1.2001.1006.1.16565.3";     
     
    
    //this might also be a canned reference like FormatCodes.SHARED_HEALTH_SUMMARY_3A.getCodedValue()
	CodedValue myFormatCode = new CodedValue( "PCEHR_FormatCodes", healthSummarytemplateID, "Shared Health Summary" );
	
	
    // Call operation.
    RegistryResponseType response = client.uploadDocument(
      commonHeader,
      packageContent,
      HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
      PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
      myFormatCode
    );
    
    //alternatively the call may contain a replacement id, using this overloaded method
    //where "replacementDocumentId"  is repository $XDSDocumentEntry.EntryUUID for document to be replaced
    String replacementDocumentId = "replacementDocumentId";
    RegistryResponseType response2 = client.uploadDocument(
            commonHeader,
            packageContent,
            replacementDocumentId,
            HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
            PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
            myFormatCode
          );
    
    
    //Yet another alternative is to supply a repository ID
    //If this is done the client will fill out document Hash and Size
    String repositoryId = "1.2.REPOSITORY.ID";
    RegistryResponseType response3 = client.uploadDocument(
            commonHeader,
            packageContent,
            null,
            repositoryId,
            HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
            PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
            myFormatCode
          );
    
    // Process response.
    System.out.println("Response status: " + response.getStatus());
    System.out.println("Response status: " + response2.getStatus());
    System.out.println("Response status: " + response3.getStatus());
  }
}
