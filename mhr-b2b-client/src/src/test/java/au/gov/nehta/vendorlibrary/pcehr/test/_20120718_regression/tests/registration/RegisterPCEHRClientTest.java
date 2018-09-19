package au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.tests.registration;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.IVCCorrespondeceChannelCode;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.IndigenousStatusCode;
import au.gov.nehta.vendorlibrary.pcehr.clients.registration.RegisterPCEHRClient;
import au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.AllTests;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.ContactDetailsType;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHR;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHR.Assertions.Identity;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHR.Assertions.Identity.EvidenceOfIdentity;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHR.Assertions.IvcCorrespondence;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.registerpcehr._2.RegisterPCEHRResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

public class RegisterPCEHRClientTest {

  private RegisterPCEHRClient client;

  @Before
  public final void setUp() throws Exception {
    AllTests.setUp();
    client = new RegisterPCEHRClient(
      AllTests.getSslSocketFactory(),
      AllTests.getCertificate(),
      AllTests.getPrivateKey(),
      Endpoints.REGRESSION_REGISTER_PCEHR,
 //     Endpoints.SVT_UPLOAD_DOCUMENT+Endpoints.REGISTER_PCEHR,
      Logging.REGISTER_PCEHR
    );
  }

  @After
  public final void tearDown() throws Exception {
    AllTests.tearDown();
    client = null;
  }

  @Test
  public void test_010() throws Exception {

    // This test registers a PCEHR with an IHI.
    RegisterPCEHR registrationDetails = new RegisterPCEHR();
    RegisterPCEHR.Assertions assertions = new RegisterPCEHR.Assertions();
IvcCorrespondence ivc = new IvcCorrespondence();
ivc.setChannel( IVCCorrespondeceChannelCode.email.getCode() );
ContactDetailsType details = new ContactDetailsType();
details.setEmailAddress( "test@test.com" );
ivc.setContactDetails( details  );
    //    assertions.setIdentityVerifiedByProvider(true);
//    assertions.setMedicareConsent(true);
//    assertions.setIVCCommunicationMethod("email");
//    assertions.setVersionOfTermsAndConditionsAgreed(BigInteger.valueOf(1));
    assertions.setIvcCorrespondence( ivc  );
    assertions.setAcceptedTermsAndConditions(true);
    //assertions.setRepresentativeDeclaration(true);
    Identity id = new Identity();
    EvidenceOfIdentity ev = new EvidenceOfIdentity();
    ev.setType( "passport" );
    id.setEvidenceOfIdentity( ev  );
    id.setIndigenousStatus( IndigenousStatusCode.indigenousStatus1.getCode() );
    assertions.setIdentity( id  );
    
    //TODO:this
    registrationDetails.setAssertions(assertions);

    RegisterPCEHRResponse response = client.registerPCEHR(AllTests.getDefaultRequest(), registrationDetails);
    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
  }
}
