package au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.tests.template;

import au.gov.nehta.vendorlibrary.pcehr.clients.template.SearchTemplateClient;
import au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.AllTests;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.net.electronichealth.ns.tplt.xsd.common.templatescoreelements._1.TemplateMetadataType;
import au.net.electronichealth.ns.tplt.xsd.interfaces.searchtemplate._1.SearchTemplateResponse;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SearchTemplateClientTest {
  private SearchTemplateClient client;

  @Before
  public final void setUp() throws Exception {
    AllTests.setUp();
    client = new SearchTemplateClient(
      AllTests.getSslSocketFactory(),
      AllTests.getCertificate(),
      AllTests.getPrivateKey(),
      Endpoints.REGRESSION_SEARCH_TEMPLATE,
      Logging.SEARCH_TEMPLATE
    );
  }

  @After
  public final void tearDown() throws Exception {
    AllTests.tearDown();
    client = null;
  }

  @Test
  public void test_130() throws Exception {

    TemplateMetadataType.Metadata metadata = new TemplateMetadataType.Metadata();
    metadata.setName("TemplateFormatType");
    metadata.setValue("CDA");

    TemplateMetadataType templateMetadata = new TemplateMetadataType();
    templateMetadata.getMetadatas().add(metadata);

    SearchTemplateResponse response = client.searchTemplate(AllTests.getDefaultRequest(), null, templateMetadata);

    for (SearchTemplateResponse.Template template : response.getTemplates()) {
      for (TemplateMetadataType.Metadata templateDetails : template.getTemplateMetadata().getMetadatas()) {
        System.out.println(templateDetails.getName() + ": " + templateDetails.getValue());
      }
    }

    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
    Assert.assertEquals("SUCCESS", response.getResponseStatus().getDescription());
    System.out.println(response.getTemplates().size());
  }
}
