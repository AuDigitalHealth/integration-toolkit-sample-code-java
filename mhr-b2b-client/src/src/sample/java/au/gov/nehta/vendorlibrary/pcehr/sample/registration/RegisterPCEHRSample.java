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
package au.gov.nehta.vendorlibrary.pcehr.sample.registration;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentConsentCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.EvidenceOfIdentityCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.IVCCorrespondeceChannelCode;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.IndigenousStatusCode;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.DateUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.FileUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.registration.RegisterPCEHRClient;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.constants.SampleEndpoints;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.constants.SampleValues;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.SecurityUtil;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.ContactDetailsType;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.NameTypeSupp;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.Sex;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHR;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHR.Representative;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHR.Assertions.DocumentConsent;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHR.Assertions.Identity;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHR.Assertions.IvcCorrespondence;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHR.Assertions.DocumentConsent.Document;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHR.Assertions.Identity.EvidenceOfIdentity;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHRResponse;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * An example class showing usage of the Register PCEHR web service client.
 */
public class RegisterPCEHRSample {

  private RegisterPCEHRSample() {
  }

  public static void main(String[] args) throws
    IOException,
    GeneralSecurityException,
    au.net.electronichealth.ns.pcehr.svc.registerpcehr._2.StandardErrorMsg {

    RegisterPCEHRClient client;

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
    client = new RegisterPCEHRClient(
      sslSocketFactory,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      SampleEndpoints.REGISTER_PCEHR,
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

    /**
     * In terms of the information required to register a PCEHR, there are two distinct options:
     *
     * 1. An IHI is provided in the PCEHRHeader (or in the child IHI, if registering a child for a PCEHR).
     * 2. Demographic information in instances where the IHI is not specified (or in the child demographics,
     * if registering a child for a PCEHR).
     *
     * To this end, examples of both are provided in the following code blocks and can be commented/used as
     * required. A subset of these use cases are shown in this sample code.
     */

    // Register PCEHR - child w/IHI.
    RegisterPCEHR registrationDetails = generateChildWithIHI();

    // Register PCEHR - person w/demographics.
    // ** Uncomment ** RegisterPCEHR registrationDetails = generatePersonWithDemographics();

    // Call operation.
    RegisterPCEHRResponse response = client.registerPCEHR(commonHeader, registrationDetails);

    // Process response.
    System.out.println("Registration Status: " + response.getResponseStatus().getCode());
  }

  /**
   * Generate the registration for a child.
   *
   * @return Populated {@link RegisterPCEHR}
   */
  private static RegisterPCEHR generateChildWithIHI() {

    RegisterPCEHR registrationDetails = new RegisterPCEHR();

    RegisterPCEHR.Assertions assertions = new RegisterPCEHR.Assertions();

    // Note the need to define this registration request as a parent on behalf of their child.
    
    assertions.setAcceptedTermsAndConditions( true );

    DocumentConsent dc = new DocumentConsent();
    Document document = new Document();
    document.setType( DocumentConsentCodes.Type.PBS.toString()); 
    document.setStatus( DocumentConsentCodes.Status.ConsentGiven.toString() );
	dc.getDocuments().add( document  );
	assertions.setDocumentConsent( dc  );
    
	Identity identity = new Identity();
	identity.setIndigenousStatus( IndigenousStatusCode.indigenousStatus1.getCode() );
	try {
		identity.setSignedConsentForm( FileUtils.loadFile(new File("someSignedConsentForm.pdf") ));
	} catch ( IOException e ) {
		e.printStackTrace();
	}
	EvidenceOfIdentity evidence = new EvidenceOfIdentity();
	evidence.setType( EvidenceOfIdentityCodes.IdentityVerificationMethod1.getCode() );
	identity.setEvidenceOfIdentity( evidence );
	assertions.setIdentity( identity  );
	IvcCorrespondence ivcCorrespondence = new IvcCorrespondence();
	ivcCorrespondence.setChannel( IVCCorrespondeceChannelCode.email.getCode() );
	ContactDetailsType contactDetails = new ContactDetailsType();
	contactDetails.setEmailAddress( "example@test.com" );
	contactDetails.setMobilePhoneNumber( "041234567" );
	ivcCorrespondence.setContactDetails( contactDetails  );
	assertions.setIvcCorrespondence( ivcCorrespondence  );
	assertions.setRepresentativeDeclaration( true );
	
	registrationDetails.setAssertions(assertions);
    /**
     * Populate a new child.
     */
    //RegisterPCEHR.Child child = new RegisterPCEHR.Child();

   // child.setIhiNumber("<change to child's IHI>");
    registrationDetails.setAssertions(assertions);
   // registrationDetails.setChild(child);

    return registrationDetails;
  }

  /**
   * Generate the registration for a person.
   *
   * @return Populated {@link RegisterPCEHR}
   */
  private static RegisterPCEHR generatePersonWithDemographics() {

    RegisterPCEHR registrationDetails = new RegisterPCEHR();

    RegisterPCEHR.Assertions assertions = new RegisterPCEHR.Assertions();
    
    assertions.setAcceptedTermsAndConditions( true );

    DocumentConsent dc = new DocumentConsent();
    Document document = new Document();
    document.setType( DocumentConsentCodes.Type.PBS.toString()); 
    document.setStatus( DocumentConsentCodes.Status.ConsentGiven.toString() );
	dc.getDocuments().add( document  );
	assertions.setDocumentConsent( dc  );
    
	Identity identity = new Identity();
	identity.setIndigenousStatus( IndigenousStatusCode.indigenousStatus1.getCode() );
	try {
		identity.setSignedConsentForm( FileUtils.loadFile(new File("someSignedConsentForm.pdf") ));
	} catch ( IOException e ) {
		e.printStackTrace();
	}
	EvidenceOfIdentity evidence = new EvidenceOfIdentity();
	evidence.setType( EvidenceOfIdentityCodes.IdentityVerificationMethod1.getCode() );
	identity.setEvidenceOfIdentity( evidence );
	assertions.setIdentity( identity  );
	
	IvcCorrespondence ivcCorrespondence = new IvcCorrespondence();
	ivcCorrespondence.setChannel( IVCCorrespondeceChannelCode.email.getCode() );
	
	ContactDetailsType contactDetails = new ContactDetailsType();
	contactDetails.setEmailAddress( "example@test.com" );
	contactDetails.setMobilePhoneNumber( "041234567" );
	ivcCorrespondence.setContactDetails( contactDetails  );
	assertions.setIvcCorrespondence( ivcCorrespondence  );
	
	assertions.setRepresentativeDeclaration( true );
	
	registrationDetails.setAssertions(assertions);
    
    
    /**
     * Populate a new individual.
     */
    RegisterPCEHR.Individual individual = new RegisterPCEHR.Individual();

    RegisterPCEHR.Individual.Demographics demographics = new RegisterPCEHR.Individual.Demographics();

    // Populate the individual's name.
    NameTypeSupp name = new NameTypeSupp();
    name.getGivenNames().add("<change to individual's given name>");
    name.setFamilyName("<change to individual's last name>");
    demographics.setName(name);
    demographics.setSex(Sex.F);
    Calendar dob = GregorianCalendar.getInstance();
    dob.set( 2009, 11 , 9); //December 9th 2009
	demographics.setDateOfBirth(dob );

    individual.setDemographics(demographics);
    registrationDetails.setAssertions(assertions);
    registrationDetails.setIndividual(individual);

    
    //If a Representative exists
    Representative representative = new Representative();
    RegisterPCEHR.Representative.Demographics repDemographics = new RegisterPCEHR.Representative.Demographics();

    // Populate the representative's name.
    NameTypeSupp repName = new NameTypeSupp();
    repName.getGivenNames().add("<change to representative's given name>");
    repName.setFamilyName("<change to representative's last name>");
    repDemographics.setName(repName);
    repDemographics.setSex(Sex.M);
    Calendar repDOB = GregorianCalendar.getInstance();
    repDOB.set( 1980, 0, 1 ); // Jan 1st 1980
	repDemographics.setDateOfBirth(repDOB );
    representative.setDemographics(repDemographics  );
    representative.setIhiNumber( "<Reps IHI Number>" );
	registrationDetails.setRepresentative( representative  );
    

    return registrationDetails;
  }
}
