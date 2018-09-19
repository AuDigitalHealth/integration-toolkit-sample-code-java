package au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.tests.template;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.TemplateServiceRequestorOption;
import au.gov.nehta.vendorlibrary.pcehr.clients.template.GetTemplateClient;
import au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.AllTests;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.net.electronichealth.ns.tplt.xsd.interfaces.gettemplate._1.GetTemplateResponse;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetTemplateClientTest {

  private GetTemplateClient client;

  @Before
  public final void setUp() throws Exception {
    AllTests.setUp();
    client = new GetTemplateClient(
       AllTests.getSslSocketFactory(),
       AllTests.getCertificate(),
       AllTests.getPrivateKey(),
       Endpoints.REGRESSION_GET_TEMPLATE,
       Logging.GET_TEMPLATE
     );
  }

  @After
  public final void tearDown() throws Exception {
    AllTests.tearDown();
    client = null;
  }

  @Test
  public void test_140() throws Exception {

    GetTemplateResponse response = client.getTemplate(AllTests.getDefaultRequest(), "1.2.36.1.2001.1006.1.20000.3", TemplateServiceRequestorOption.MachineUsable);
    Assert.assertNotNull(response);
    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
    Assert.assertEquals("SUCCESS", response.getResponseStatus().getDescription());
    Assert.assertNotNull(response.getTemplate().getTemplateMetadata());
    Assert.assertNotNull(response.getTemplate().getPackage());
  }
}
