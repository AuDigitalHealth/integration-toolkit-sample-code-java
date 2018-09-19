package au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.documentexchange;

import au.gov.nehta.vendorlibrary.clinicalpackage.core.SubmissionSet;
import au.gov.nehta.vendorlibrary.clinicalpackage.util.PackagingUtility;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.FileUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.documentexchange.RemoveDocumentClient;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.SecurityUtil;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.TestUtils;
import au.net.electronichealth.ns.cdapackage.xsd.esignature._2012.PersonNameType;
import au.net.electronichealth.ns.pcehr.svc.removedocument._1.StandardErrorMsg;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.removedocument._1.RemoveDocument;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.removedocument._1.RemoveDocumentResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.Holder;
import java.io.File;
import java.util.Date;

public class RemoveDocumentTest_NOC {

  private Holder<String> currentId;
  private RemoveDocumentClient client;
  private static SSLSocketFactory sslSocketFactory;

  @BeforeClass
  public static void initialSetup() throws Exception {

    HttpsURLConnection.setDefaultHostnameVerifier(
      new HostnameVerifier() {
        public boolean verify(String s, SSLSession sslSession) {
          return true;
        }
      }
    );

    sslSocketFactory = SecurityUtil.getSslSocketFactory();

    // Sets the newly created sslsocketfactory as the default for all instances OF the HttpsURLConnection class.
    HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);

    // For testing purposes.
    System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
  }

  @Before
  public final void setUp() throws Exception {
    client = new RemoveDocumentClient(
      sslSocketFactory,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      Endpoints.ACCENTURE_REMOVE_DOCUMENT,
      Logging.REMOVE_DOCUMENT
    );

    currentId = new Holder<String>();
  }

  @After
  public final void tearDown() throws Exception {
    client = null;
  }

  @Test
  public void test_035() throws Exception {

    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003603459803467",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );

    byte[] rootDocument = TestUtils.modifyDocId(FileUtils.loadFile(new File("./src/test//resources/TestFiles/20120509/SHS1 - scen 28/CDA_ROOT.xml")), currentId);

    // Approver.
    PersonNameType approverName = new PersonNameType();
    approverName = new PersonNameType();
    approverName.setFamilyName("John");
    approverName.getNameTitle().add("Dr");
    approverName.getGivenName().add("Ross");

    SubmissionSet subset = TestUtils.createPackage(rootDocument, "8003620833337558", approverName);

    // Build ZIP from package.
    byte[] packageContent = PackagingUtility.createZip(subset);

    // Write out to file for debug purposes.
    PackagingUtility.writeZip(subset, "./src/test/resources/TestFiles/Generated/out_" + new Date().getTime() + ".zip");

    RemoveDocumentResponse removeResponse = client.removeDocument(request, currentId.value, RemoveDocument.DocumentRemovalReason.WITHDRAWN);

    Assert.assertEquals("PCEHR_SUCCESS", removeResponse.getResponseStatus().getCode());
  }

  @Test
  public void test_036() throws Exception {
    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003606792133153",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null)
      );

    RemoveDocumentResponse response = client.removeDocument(request, "1.3.16.1.38818.1690000000000000", RemoveDocument.DocumentRemovalReason.WITHDRAWN);
  }

  @Test(expected = StandardErrorMsg.class)
  public void test_037() throws Exception {
    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003606792133153",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8000627500003640", "Medicare305", null)
      );
    try {
      RemoveDocumentResponse response = client.removeDocument(request, "1.3.16.1.38818.125555433322", RemoveDocument.DocumentRemovalReason.WITHDRAWN);
    } catch (StandardErrorMsg e) {
      junit.framework.Assert.assertEquals("badParam", e.getFaultInfo().getErrorCode().value());
      junit.framework.Assert.assertEquals("PCEHR_ERROR_0505 - Invalid HPI-O", e.getFaultInfo().getMessage());
      throw e;
    }
  }
}
