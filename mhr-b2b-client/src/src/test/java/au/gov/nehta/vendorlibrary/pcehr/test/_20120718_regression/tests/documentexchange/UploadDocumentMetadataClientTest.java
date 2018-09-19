package au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.tests.documentexchange;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XDSConstants;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.FormatCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HealthcareFacilityTypeCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.PracticeSettingTypeCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.FileUtils;
import au.gov.nehta.vendorlibrary.pcehr.clients.documentexchange.UploadDocumentMetadataClient;
import au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.AllTests;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class UploadDocumentMetadataClientTest {
  private UploadDocumentMetadataClient client;

  @Before
  public final void setUp() throws Exception {
    AllTests.setUp();
    client = new UploadDocumentMetadataClient(
      AllTests.getSslSocketFactory(),
      AllTests.getCertificate(),
      AllTests.getPrivateKey(),
      Endpoints.REGRESSION_UPLOAD_DOCUMENT_METADATA,
      Logging.UPLOAD_DOCUMENT_METADATA
    );
  }

  @After
  public final void tearDown() throws Exception {
    AllTests.tearDown();
    client = null;
  }

  @Test
  public void test_011() throws Exception {

    byte[] packageContent = FileUtils.loadFile(new File("./src/test/resources/TestFiles/Phil/Completed Packages - Phil/SHS10/CDA.zip"));

    RegistryResponseType response = client.uploadDocumentMetadata(
      AllTests.getDefaultRequest(),
      packageContent,
      "1.2.36.1.2001.1007.10",
      HealthcareFacilityTypeCodes.HOSPITALS.getCodedValue(),
      PracticeSettingTypeCodes.HOSPITAL.getCodedValue(),
      FormatCodes.DISCHARGE_SUMMARY_3A.getCodedValue()
    );

    Assert.assertEquals(XDSConstants.RESPONSE_STATUS_SUCCESS, response.getStatus());
  }
}
