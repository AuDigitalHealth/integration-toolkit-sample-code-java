package au.gov.nehta.vendorlibrary.pcehr.test._20120724_noc.tests.template;

import au.gov.nehta.vendorlibrary.pcehr.clients.template.SearchTemplateClient;
import au.gov.nehta.vendorlibrary.pcehr.sample.common.util.MessageComponents;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Endpoints;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.Logging;
import au.gov.nehta.vendorlibrary.pcehr.test.utils.SecurityUtil;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.tplt.xsd.common.templatescoreelements._1.TemplateMetadataType;
import au.net.electronichealth.ns.tplt.xsd.interfaces.searchtemplate._1.SearchTemplateResponse;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchTemplateClientTest_NOC {

  private SearchTemplateClient client;
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
    client = new SearchTemplateClient(
      sslSocketFactory,
      SecurityUtil.getCertificate(),
      SecurityUtil.getPrivateKey(),
      Endpoints.ACCENTURE_SEARCH_TEMPLATE,
      Logging.SEARCH_TEMPLATE
    );
  }

  @After
  public final void tearDown() throws Exception {
    client = null;
  }

  @Test
  public void test_046() throws Exception {

    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003602348687628",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null) 
      );

    TemplateMetadataType.Metadata metadata = new TemplateMetadataType.Metadata();
    metadata.setName("TemplateFormatType");
    metadata.setValue("CDA");

    TemplateMetadataType templateMetadata = new TemplateMetadataType();
    templateMetadata.getMetadatas().add(metadata);

    SearchTemplateResponse response = client.searchTemplate(request, null, templateMetadata);


    StringBuilder builder = new StringBuilder();

    int flag = 0;

    // Get possible keys
    List<String> keys = populateKeys(response.getTemplates());




    FileOutputStream out = null;

    try {
      out = new FileOutputStream(new File("C:/tmp/template.csv"));
      out.write(builder.toString().getBytes());
      out.flush();
    } catch (IOException e) {
      throw new IOException("Error writing package to file", e);
    } finally {
      if (out != null) {
        out.close();
      }
    }

    System.out.println(response.getTemplates().size());

    Assert.assertEquals("PCEHR_SUCCESS", response.getResponseStatus().getCode());
    Assert.assertEquals("SUCCESS", response.getResponseStatus().getDescription());
  }

  @Test
  public void test_047() throws Exception {
    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003602348687628",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null) 
      );

    TemplateMetadataType.Metadata metadata = new TemplateMetadataType.Metadata();
    metadata.setName("TemplateFormatType1");
    metadata.setValue("CCC");

    TemplateMetadataType templateMetadata = new TemplateMetadataType();
    templateMetadata.getMetadatas().add(metadata);

    SearchTemplateResponse response = client.searchTemplate(request, "913", null);
    Assert.assertEquals("PCEHR_ERROR_1803", response.getResponseStatus().getCode());
    Assert.assertEquals("Invalid search criteria", response.getResponseStatus().getDescription());
  }

  @Test
  public void test_048() throws Exception {

    PCEHRHeader request = MessageComponents.createRequest
      (
        MessageComponents.createUser(PCEHRHeader.User.IDType.HPII, "8003619166674595", null, "Ross John", false),
        "8003602348687628",
        MessageComponents.createProductType("NeHTA", "Test Harness", "1.0", "Windows 7 - Java"),
        PCEHRHeader.ClientSystemType.CIS,
        MessageComponents.createAccessingOrganisation("8003628233352432", "Medicare305", null) 
      );

    SearchTemplateResponse response = client.searchTemplate(request, "1.2.36.1.2001.1006.1.16565.1322", null);
    Assert.assertEquals("PCEHR_ERROR_1802", response.getResponseStatus().getCode());
    Assert.assertEquals("No search item", response.getResponseStatus().getDescription());
  }

  private List<String> populateKeys(List<SearchTemplateResponse.Template> templates) {
    List<String> keys = new ArrayList<String>();

    for (SearchTemplateResponse.Template templateMetadataType : templates) {

      for (TemplateMetadataType.Metadata metadata : templateMetadataType.getTemplateMetadata().getMetadatas()) {
        String name = metadata.getName();

        if (!contains(keys, name)) {
          keys.add(name);
        }
      }
    }
    return keys;
  }

  private boolean contains(List<String> items, String key) {
    for (String item : items) {
      if (item.compareToIgnoreCase(key) == 0) {
        return true;
      }
    }
    return false;
  }
}
