/*
 * Copyright 2011 NEHTA
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
package au.gov.nehta.vendorlibrary.sm.els;

import au.gov.nehta.vendorlibrary.common.security.KeystoreUtil;
import au.gov.nehta.vendorlibrary.ws.handler.LoggingHandler;
import au.net.electronichealth.els.datatypes.v2010.ElsCertRefType;
import au.net.electronichealth.els.datatypes.v2010.InteractionRequestType;
import au.net.electronichealth.els.datatypes.v2010.InteractionType;
import au.net.electronichealth.els.operation.lookup.v2010.Lookup;
import au.net.electronichealth.els.operation.publish.v2010.Publish;
import au.net.electronichealth.els.operation.publish.v2010.PublishReturnCodeType;
import au.net.electronichealth.qcr.qualifiedcertref.v2010.QualifiedCertRefType;
import com.sun.xml.wss.XWSSConstants;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.Assert;
import org.springframework.test.util.ReflectionTestUtils;

import javax.net.ssl.*;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class ELSClientTest {

  private final String TEST_CERTIFICATE_REFERENCE_USE_QUALIFIER = "testUseQualifier";
  private final String TEST_CERTIFICATE_REFERENCE_TYPE = "testType";
  private final String TEST_CERTIFICATE_REFERENCE_VALUE = "testValue";

  private final String RESOURCES_DIR = "./src/test/resources/";

  private final String PRIVATE_KEY_STORE_TYPE = "JKS";
  private final String PRIVATE_KEY_STORE_FILE = RESOURCES_DIR + "keystore.jks";
  private final String PRIVATE_KEY_STORE_PASSWORD = "password";
  private final String PRIVATE_KEY_ALIAS = "8003630000000004";
  private final String PRIVATE_KEY_PASSWORD = "password";

  private final String TRUST_STORE_TYPE = "JKS";
  private final String TRUST_STORE_FILE = RESOURCES_DIR + "truststore.jks";
  private final String TRUST_STORE_PASSWORD = "password";

  private final String DRP_ELS_LOOKUP_ENDPOINT_URL = "https://nehta-drp-iis.nehta.net.au/ELS2010/LookupTls/Service.svc";
  private final String DRP_ELS_PUBLISH_ENDPOINT_URL = "https://nehta-drp-iis.nehta.net.au/ELS2010/PublishTls/Service.svc";

  private final String FAIRHILL_HPIO = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003630000000822";
  private final String FAIRHILL_NEW_SERVICE_CATEGORY = "http://ns.electronichealth.net.au/etp/sc/prescriptionowing/1.1/45";
  private final String FAIRHILL_NEW_SERVICE_INTERFACE = "http://ns.electronichealth.net.au/smd/intf/SealedMessageDelivery/TLS/2010/34";
  private final String FAIRHILL_NEW_SERVICE_ENDPOINT_URL = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003630000000822";
  private final String FAIRHILL_NEW_SERVICE_PROVIDER = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003630000000822";

  @Test
  public void testLookupOnlyConstructor() throws Exception {
    SSLSocketFactory sslSocketFactory = getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, sslSocketFactory);

    boolean lookupOnlyAttribute = (Boolean) ReflectionTestUtils.getField(testClient, "lookupOnly");
    Lookup elsLookupPortAttribute = (Lookup)ReflectionTestUtils.getField(testClient, "elsLookupPort");
    Publish elsPublishPortAttribuate = (Publish)ReflectionTestUtils.getField(testClient, "elsPublishPort");

    Assert.assertTrue(lookupOnlyAttribute);
    Assert.assertNotNull(elsLookupPortAttribute);
    Assert.assertNull(elsPublishPortAttribuate);
    Assert.assertEquals(sslSocketFactory, ((BindingProvider)elsLookupPortAttribute).getRequestContext().get("com.sun.xml.ws.transport.https.client.SSLSocketFactory"));
  }

  @Test
  public void testPublishAndLookupConstructor() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    Lookup elsLookupPortAttribute = (Lookup)ReflectionTestUtils.getField(testClient, "elsLookupPort");
    Publish elsPublishPortAttribute = (Publish)ReflectionTestUtils.getField(testClient, "elsPublishPort");
    boolean lookupOnlyAttribute = (Boolean)ReflectionTestUtils.getField(testClient, "lookupOnly");

    Assert.assertNotNull(elsLookupPortAttribute);
    Assert.assertNotNull(elsPublishPortAttribute);
    Assert.assertTrue(lookupOnlyAttribute == false);
    Assert.assertEquals(sslSocketFactory, ((BindingProvider)elsLookupPortAttribute).getRequestContext().get("com.sun.xml.ws.transport.https.client.SSLSocketFactory"));
    Assert.assertEquals(sslSocketFactory, ((BindingProvider) elsPublishPortAttribute).getRequestContext().get("com.sun.xml.ws.transport.https.client.SSLSocketFactory"));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCheckInteractionRequestParametersWithEmptyServiceInterfaces() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    String target = null;
    List<String> serviceCategories = new ArrayList<String>();
    serviceCategories.add(FAIRHILL_NEW_SERVICE_CATEGORY);
    List<String> serviceInterfaces = new ArrayList<String>();
    serviceInterfaces.add(FAIRHILL_NEW_SERVICE_INTERFACE);

    InteractionRequestType interactionRequest = getInteractionRequest(target, serviceCategories, serviceInterfaces);

    testClient.listInteractions(interactionRequest);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCheckInteractionRequestParametersWithEmptyTarget() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    String target = new String();
    List<String> serviceCategories = new ArrayList<String>();
    serviceCategories.add(FAIRHILL_NEW_SERVICE_CATEGORY);
    List<String> serviceInterfaces = new ArrayList<String>();
    serviceInterfaces.add(FAIRHILL_NEW_SERVICE_INTERFACE);

    InteractionRequestType interactionRequest = getInteractionRequest(target, serviceCategories, serviceInterfaces);

    testClient.listInteractions(interactionRequest);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCheckInteractionRequestParametersWithNullServiceCateogories() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    String target = FAIRHILL_HPIO;
    List<String> serviceCategories = null;
    List<String> serviceInterfaces = new ArrayList<String>();
    serviceInterfaces.add(FAIRHILL_NEW_SERVICE_INTERFACE);

    InteractionRequestType interactionRequest = getInteractionRequest(target, serviceCategories, serviceInterfaces);

    testClient.listInteractions(interactionRequest);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCheckInteractionRequestParametersWithEmptyServiceCategories() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    String target = FAIRHILL_HPIO;
    List<String> serviceCategories = new ArrayList<String>();
    List<String> serviceInterfaces = new ArrayList<String>();
    serviceInterfaces.add(FAIRHILL_NEW_SERVICE_INTERFACE);

    InteractionRequestType interactionRequest = getInteractionRequest(target, serviceCategories, serviceInterfaces);

    testClient.listInteractions(interactionRequest);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCheckInteractionRequestParametersWithNullServiceInterfaces() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    String target = FAIRHILL_HPIO;
    List<String> serviceCategories = new ArrayList<String>();
    serviceCategories.add(FAIRHILL_NEW_SERVICE_CATEGORY);
    List<String> serviceInterfaces = null;

    InteractionRequestType interactionRequest = getInteractionRequest(target, serviceCategories, serviceInterfaces);

    testClient.listInteractions(interactionRequest);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCheckInterfactionParametersNullTarget() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    String target = null;
    String serviceCategory = FAIRHILL_NEW_SERVICE_CATEGORY;
    String serviceInterface = FAIRHILL_NEW_SERVICE_INTERFACE;
    String serviceEndpoint = FAIRHILL_NEW_SERVICE_ENDPOINT_URL;
    String serviceProvider = FAIRHILL_NEW_SERVICE_PROVIDER;

    InteractionType interaction = getInteraction(target, serviceCategory, serviceInterface, serviceEndpoint, serviceProvider);

    testClient.validateInteraction(interaction);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCheckInterfactionParametersZeroLengthTarget() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    String target = new String();
    String serviceCategory = FAIRHILL_NEW_SERVICE_CATEGORY;
    String serviceInterface = FAIRHILL_NEW_SERVICE_INTERFACE;
    String serviceEndpoint = FAIRHILL_NEW_SERVICE_ENDPOINT_URL;
    String serviceProvider = FAIRHILL_NEW_SERVICE_PROVIDER;

    InteractionType interaction = getInteraction(target, serviceCategory, serviceInterface, serviceEndpoint, serviceProvider);

    testClient.validateInteraction(interaction);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCheckInterfactionParametersNullServiceCategory() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    String target = FAIRHILL_HPIO;
    String serviceCategory = null;
    String serviceInterface = FAIRHILL_NEW_SERVICE_INTERFACE;
    String serviceEndpoint = FAIRHILL_NEW_SERVICE_ENDPOINT_URL;
    String serviceProvider = FAIRHILL_NEW_SERVICE_PROVIDER;

    InteractionType interaction = getInteraction(target, serviceCategory, serviceInterface, serviceEndpoint, serviceProvider);

    testClient.validateInteraction(interaction);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCheckInterfactionParametersZeroLengthServiceCategory() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    String target = FAIRHILL_HPIO;
    String serviceCategory = new String();
    String serviceInterface = FAIRHILL_NEW_SERVICE_INTERFACE;
    String serviceEndpoint = FAIRHILL_NEW_SERVICE_ENDPOINT_URL;
    String serviceProvider = FAIRHILL_NEW_SERVICE_PROVIDER;

    InteractionType interaction = getInteraction(target, serviceCategory, serviceInterface, serviceEndpoint, serviceProvider);

    testClient.validateInteraction(interaction);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCheckInterfactionParametersNullServiceInterface() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    String target = FAIRHILL_HPIO;
    String serviceCategory = FAIRHILL_NEW_SERVICE_CATEGORY;
    String serviceInterface = null;
    String serviceEndpoint = FAIRHILL_NEW_SERVICE_ENDPOINT_URL;
    String serviceProvider = FAIRHILL_NEW_SERVICE_PROVIDER;

    InteractionType interaction = getInteraction(target, serviceCategory, serviceInterface, serviceEndpoint, serviceProvider);

    testClient.validateInteraction(interaction);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCheckInterfactionParametersNullServiceEndpoint() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    String target = FAIRHILL_HPIO;
    String serviceCategory = FAIRHILL_NEW_SERVICE_CATEGORY;
    String serviceInterface = FAIRHILL_NEW_SERVICE_INTERFACE;
    String serviceEndpoint = null;
    String serviceProvider = FAIRHILL_NEW_SERVICE_PROVIDER;

    InteractionType interaction = getInteraction(target, serviceCategory, serviceInterface, serviceEndpoint, serviceProvider);

    testClient.validateInteraction(interaction);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCheckInterfactionParametersZeroLengthServiceEndpoint() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    String target = FAIRHILL_HPIO;
    String serviceCategory = null;
    String serviceInterface = FAIRHILL_NEW_SERVICE_INTERFACE;
    String serviceEndpoint = new String();
    String serviceProvider = FAIRHILL_NEW_SERVICE_PROVIDER;

    InteractionType interaction = getInteraction(target, serviceCategory, serviceInterface, serviceEndpoint, serviceProvider);

    testClient.validateInteraction(interaction);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCheckInterfactionParametersNullServiceProvider() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    String target = FAIRHILL_HPIO;
    String serviceCategory = FAIRHILL_NEW_SERVICE_CATEGORY;
    String serviceInterface = FAIRHILL_NEW_SERVICE_INTERFACE;
    String serviceEndpoint = FAIRHILL_NEW_SERVICE_ENDPOINT_URL;
    String serviceProvider = null;

    InteractionType interaction = getInteraction(target, serviceCategory, serviceInterface, serviceEndpoint, serviceProvider);

    testClient.validateInteraction(interaction);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCheckInterfactionParametersZeroLengthServiceProvider() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    String target = FAIRHILL_HPIO;
    String serviceCategory = FAIRHILL_NEW_SERVICE_CATEGORY;
    String serviceInterface = FAIRHILL_NEW_SERVICE_INTERFACE;
    String serviceEndpoint = FAIRHILL_NEW_SERVICE_ENDPOINT_URL;
    String serviceProvider = new String();

    InteractionType interaction = getInteraction(target, serviceCategory, serviceInterface, serviceEndpoint, serviceProvider);

    testClient.validateInteraction(interaction);
  }

  @Test
  public void testListInteractions() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    Lookup elsLookupPortMock = EasyMock.createMock(Lookup.class);
    ReflectionTestUtils.setField(testClient, "elsLookupPort", elsLookupPortMock);

    final Capture<InteractionRequestType> interactionRequestCapture = new Capture<InteractionRequestType>();
    final List<InteractionType> interactions = new ArrayList<InteractionType>();

    EasyMock.expect(elsLookupPortMock.listInteractions(EasyMock.capture(interactionRequestCapture))).andReturn(interactions);
    EasyMock.replay(elsLookupPortMock);

    List<String> serviceCategories = new ArrayList<String>();
    List<String> serviceInterfaces = new ArrayList<String>();

    serviceCategories.add(FAIRHILL_NEW_SERVICE_CATEGORY);
    serviceInterfaces.add(FAIRHILL_NEW_SERVICE_INTERFACE);

    InteractionRequestType interactionRequest = getInteractionRequest(FAIRHILL_HPIO, serviceCategories, serviceInterfaces);

    testClient.listInteractions(interactionRequest);


    EasyMock.verify(elsLookupPortMock);
    Assert.assertEquals(serviceCategories, interactionRequestCapture.getValue().getServiceCategory());
    Assert.assertEquals(serviceInterfaces, interactionRequestCapture.getValue().getServiceInterface());
  }

  @Test
  public void testValidateInteractionCallWithCertificateRefs() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    Lookup elsLookupPortMock = EasyMock.createMock(Lookup.class);
    ReflectionTestUtils.setField(testClient, "elsLookupPort", elsLookupPortMock);

    final Capture<InteractionType> interactionCapture = new Capture<InteractionType>();
    final boolean validationResult = true;

    EasyMock.expect(elsLookupPortMock.validateInteraction(EasyMock.capture(interactionCapture))).andReturn(validationResult);
    EasyMock.replay(elsLookupPortMock);

    ElsCertRefType elsCertRef = getElsCertificateReference(TEST_CERTIFICATE_REFERENCE_USE_QUALIFIER, TEST_CERTIFICATE_REFERENCE_TYPE, TEST_CERTIFICATE_REFERENCE_VALUE);
    List<ElsCertRefType> certRefList = new ArrayList<ElsCertRefType>();
    certRefList.add(elsCertRef);

    InteractionType interaction = getInteraction(FAIRHILL_HPIO, FAIRHILL_NEW_SERVICE_CATEGORY, FAIRHILL_NEW_SERVICE_INTERFACE, FAIRHILL_NEW_SERVICE_ENDPOINT_URL, FAIRHILL_NEW_SERVICE_PROVIDER, certRefList);

    testClient.validateInteraction(interaction);

    EasyMock.verify(elsLookupPortMock);
    verifyInteraction(interactionCapture);
  }

  @Test
  public void testValidateInteractionWithoutCertificateRefs() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    Lookup elsLookupPortMock = EasyMock.createMock(Lookup.class);
    ReflectionTestUtils.setField(testClient, "elsLookupPort", elsLookupPortMock);

    final Capture<InteractionType> interactionCapture = new Capture<InteractionType>();
    final boolean validationResult = true;

    EasyMock.expect(elsLookupPortMock.validateInteraction(EasyMock.capture(interactionCapture))).andReturn(validationResult);
    EasyMock.replay(elsLookupPortMock);

    InteractionType interaction = getInteraction(FAIRHILL_HPIO, FAIRHILL_NEW_SERVICE_CATEGORY, FAIRHILL_NEW_SERVICE_INTERFACE, FAIRHILL_NEW_SERVICE_ENDPOINT_URL, FAIRHILL_NEW_SERVICE_PROVIDER);

    testClient.validateInteraction(interaction);

    EasyMock.verify(elsLookupPortMock);
    verifyInteraction(interactionCapture);
  }

  @Test
  public void testAddInteractionCallsCorrectMethodOnPublishPortWithCertificateRefs() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    Publish elsPublishPortMock = EasyMock.createMock(Publish.class);
    ReflectionTestUtils.setField(testClient, "elsPublishPort", elsPublishPortMock);

    final Capture<InteractionType> interactionCapture = new Capture<InteractionType>();
    final PublishReturnCodeType addInteractionResult = PublishReturnCodeType.OK;

    EasyMock.expect(elsPublishPortMock.addInteraction(EasyMock.capture(interactionCapture))).andReturn(addInteractionResult);
    EasyMock.replay(elsPublishPortMock);

    ElsCertRefType elsCertRef = getElsCertificateReference(TEST_CERTIFICATE_REFERENCE_USE_QUALIFIER, TEST_CERTIFICATE_REFERENCE_TYPE, TEST_CERTIFICATE_REFERENCE_VALUE);
    List<ElsCertRefType> certRefList = new ArrayList<ElsCertRefType>();
    certRefList.add(elsCertRef);

    InteractionType interaction = getInteraction(FAIRHILL_HPIO, FAIRHILL_NEW_SERVICE_CATEGORY, FAIRHILL_NEW_SERVICE_INTERFACE, FAIRHILL_NEW_SERVICE_ENDPOINT_URL, FAIRHILL_NEW_SERVICE_PROVIDER, certRefList);

    testClient.addInteraction(interaction);

    EasyMock.verify(elsPublishPortMock);
    verifyInteraction(interactionCapture);
  }

  @Test
  public void testAddInteractionCallWithoutCertificateRefs() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    Publish elsPublishPortMock = EasyMock.createMock(Publish.class);
    ReflectionTestUtils.setField(testClient, "elsPublishPort", elsPublishPortMock);

    final Capture<InteractionType> interactionCapture = new Capture<InteractionType>();
    final PublishReturnCodeType addInteractionResult = PublishReturnCodeType.OK;

    EasyMock.expect(elsPublishPortMock.addInteraction(EasyMock.capture(interactionCapture))).andReturn(addInteractionResult);
    EasyMock.replay(elsPublishPortMock);

    InteractionType interaction = getInteraction(FAIRHILL_HPIO, FAIRHILL_NEW_SERVICE_CATEGORY, FAIRHILL_NEW_SERVICE_INTERFACE, FAIRHILL_NEW_SERVICE_ENDPOINT_URL, FAIRHILL_NEW_SERVICE_PROVIDER);

    testClient.addInteraction(interaction);

    EasyMock.verify(elsPublishPortMock);
    verifyInteraction(interactionCapture);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testAddInteractionFailsWhenCalledOnALookupOnlyElsClientWithCertificateRefs() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, sslSocketFactory);

    ElsCertRefType elsCertRef = getElsCertificateReference(TEST_CERTIFICATE_REFERENCE_USE_QUALIFIER, TEST_CERTIFICATE_REFERENCE_TYPE, TEST_CERTIFICATE_REFERENCE_VALUE);
    List<ElsCertRefType> certRefList = new ArrayList<ElsCertRefType>();
    certRefList.add(elsCertRef);

    InteractionType interaction = getInteraction(FAIRHILL_HPIO, FAIRHILL_NEW_SERVICE_CATEGORY, FAIRHILL_NEW_SERVICE_INTERFACE, FAIRHILL_NEW_SERVICE_ENDPOINT_URL, FAIRHILL_NEW_SERVICE_PROVIDER, certRefList);

    testClient.addInteraction(interaction);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testAddInteractionFailsWhenCalledOnALookupOnlyElsClientWithoutCertificateRefs() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, sslSocketFactory);

    InteractionType interaction = getInteraction(FAIRHILL_HPIO, FAIRHILL_NEW_SERVICE_CATEGORY, FAIRHILL_NEW_SERVICE_INTERFACE, FAIRHILL_NEW_SERVICE_ENDPOINT_URL, FAIRHILL_NEW_SERVICE_PROVIDER);

    testClient.addInteraction(interaction);
  }

  @Test
  public void testRemoveInteractionCallWithCertificateRefs() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    Publish elsPublishPortMock = EasyMock.createMock(Publish.class);
    ReflectionTestUtils.setField(testClient, "elsPublishPort", elsPublishPortMock);

    final Capture<InteractionType> interactionCapture = new Capture<InteractionType>();
    final PublishReturnCodeType removeInteractionResult = PublishReturnCodeType.OK;

    EasyMock.expect(elsPublishPortMock.removeInteraction(EasyMock.capture(interactionCapture))).andReturn(removeInteractionResult);
    EasyMock.replay(elsPublishPortMock);

    ElsCertRefType elsCertRef = getElsCertificateReference(TEST_CERTIFICATE_REFERENCE_USE_QUALIFIER, TEST_CERTIFICATE_REFERENCE_TYPE, TEST_CERTIFICATE_REFERENCE_VALUE);
    List<ElsCertRefType> certRefList = new ArrayList<ElsCertRefType>();
    certRefList.add(elsCertRef);

    InteractionType interaction = getInteraction(FAIRHILL_HPIO, FAIRHILL_NEW_SERVICE_CATEGORY, FAIRHILL_NEW_SERVICE_INTERFACE, FAIRHILL_NEW_SERVICE_ENDPOINT_URL, FAIRHILL_NEW_SERVICE_PROVIDER, certRefList);

    testClient.removeInteraction(interaction);

    EasyMock.verify(elsPublishPortMock);
    verifyInteraction(interactionCapture);
  }

  @Test
  public void testRemoveInteractionCallWithoutCertificateRefs() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    Publish elsPublishPortMock = EasyMock.createMock(Publish.class);
    ReflectionTestUtils.setField(testClient, "elsPublishPort", elsPublishPortMock);

    final Capture<InteractionType> interactionCapture = new Capture<InteractionType>();
    final PublishReturnCodeType removeInteractionResult = PublishReturnCodeType.OK;

    EasyMock.expect(elsPublishPortMock.removeInteraction(EasyMock.capture(interactionCapture))).andReturn(removeInteractionResult);
    EasyMock.replay(elsPublishPortMock);

    InteractionType interaction = getInteraction(FAIRHILL_HPIO, FAIRHILL_NEW_SERVICE_CATEGORY, FAIRHILL_NEW_SERVICE_INTERFACE, FAIRHILL_NEW_SERVICE_ENDPOINT_URL, FAIRHILL_NEW_SERVICE_PROVIDER);

    testClient.removeInteraction(interaction);

    EasyMock.verify(elsPublishPortMock);
    verifyInteraction(interactionCapture);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testRemoveInteractionFailsWhenCalledOnALookupOnlyElsClientWithCertificateRefs() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, sslSocketFactory);

    ElsCertRefType elsCertRef = getElsCertificateReference(TEST_CERTIFICATE_REFERENCE_USE_QUALIFIER, TEST_CERTIFICATE_REFERENCE_TYPE, TEST_CERTIFICATE_REFERENCE_VALUE);
    List<ElsCertRefType> certRefList = new ArrayList<ElsCertRefType>();
    certRefList.add(elsCertRef);

    InteractionType interaction = getInteraction(FAIRHILL_HPIO, FAIRHILL_NEW_SERVICE_CATEGORY, FAIRHILL_NEW_SERVICE_INTERFACE, FAIRHILL_NEW_SERVICE_ENDPOINT_URL, FAIRHILL_NEW_SERVICE_PROVIDER, certRefList);

    testClient.removeInteraction(interaction);
  }

  
/* THIS TEST FAILS
  @Test
  public void testElsWebServiceOperationsOnDrpReferencePlatform() throws Exception {
    System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
    System.setProperty("javax.net.debug", "ssl, handshake");
    System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");

    SSLSocketFactory sslSocketFactory = getSslSocketFactory();

    ELSClient elsClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

    // Create and add a new interaction to the ELS Server
    ElsCertRefType elsCertRef = getElsCertificateReference(TEST_CERTIFICATE_REFERENCE_USE_QUALIFIER, TEST_CERTIFICATE_REFERENCE_TYPE, TEST_CERTIFICATE_REFERENCE_VALUE);
    List<ElsCertRefType> certRefList = new ArrayList<ElsCertRefType>();
    certRefList.add(elsCertRef);

    InteractionType interaction = getInteraction(FAIRHILL_HPIO, FAIRHILL_NEW_SERVICE_CATEGORY, FAIRHILL_NEW_SERVICE_INTERFACE, FAIRHILL_NEW_SERVICE_ENDPOINT_URL, FAIRHILL_NEW_SERVICE_PROVIDER, certRefList);

    // Add interaction
    PublishReturnCodeType addInteractionResult = elsClient.addInteraction(interaction);
    Assert.assertEquals(PublishReturnCodeType.OK, addInteractionResult);
    Assert.assertNotNull(elsClient.getLastSoapRequest());
    Assert.assertTrue(elsClient.getLastSoapRequest().indexOf("addInteractionRequest") > 0);
    Assert.assertNotNull(elsClient.getLastSoapResponse());
    Assert.assertTrue(elsClient.getLastSoapResponse().indexOf("addInteractionResponse") > 0);

    // Validate interaction
    boolean validInteraction = elsClient.validateInteraction(interaction);
    Assert.assertTrue(validInteraction);
    Assert.assertNotNull(elsClient.getLastSoapRequest());
    Assert.assertTrue(elsClient.getLastSoapRequest().indexOf("validateInteractionRequest") > 0);
    Assert.assertNotNull(elsClient.getLastSoapResponse());
    Assert.assertTrue(elsClient.getLastSoapResponse().indexOf("validateInteractionResponse") > 0);

    // Check that interaction is present after a list interactions invocation
    List<String> serviceCategories = new ArrayList<String>();
    serviceCategories.add(FAIRHILL_NEW_SERVICE_CATEGORY);
    List<String> serviceInterfaces = new ArrayList<String>();
    serviceInterfaces.add(FAIRHILL_NEW_SERVICE_INTERFACE);

    InteractionRequestType interactionRequest = getInteractionRequest(FAIRHILL_HPIO, serviceCategories, serviceInterfaces);

    List<InteractionType> interactions = elsClient.listInteractions(interactionRequest);
    Assert.assertNotNull(elsClient.getLastSoapRequest());
    Assert.assertTrue(elsClient.getLastSoapRequest().indexOf("listInteractionsRequest") > 0);
    Assert.assertNotNull(elsClient.getLastSoapResponse());
    Assert.assertTrue(elsClient.getLastSoapResponse().indexOf("listInteractionsResponse") > 0);
    Assert.assertNotNull(interactions);

    Assert.assertTrue(containsInteraction(certRefList, interactions));

    // Check that removal of the interaction works
    PublishReturnCodeType removeInteractionResult = elsClient.removeInteraction(interaction);
    Assert.assertEquals(PublishReturnCodeType.OK, removeInteractionResult);
    Assert.assertNotNull(elsClient.getLastSoapRequest());
    Assert.assertTrue(elsClient.getLastSoapRequest().indexOf("removeInteractionRequest") > 0);
    Assert.assertNotNull(elsClient.getLastSoapResponse());
    Assert.assertTrue(elsClient.getLastSoapResponse().indexOf("removeInteractionResponse") > 0);

    // Check that interaction has in fact been removed form the ELS server through validation of the interaction
    boolean stillValidInteraction = elsClient.validateInteraction(interaction);
    Assert.assertTrue(stillValidInteraction == false);
    Assert.assertNotNull(elsClient.getLastSoapRequest());
    Assert.assertTrue(elsClient.getLastSoapRequest().indexOf("validateInteractionRequest") > 0);
    Assert.assertNotNull(elsClient.getLastSoapResponse());
    Assert.assertTrue(elsClient.getLastSoapResponse().indexOf("validateInteractionResponse") > 0);


    // Check that interaction is no longer present after a list interactions invocation
    List<InteractionType> updatedInteractions = elsClient.listInteractions(interactionRequest);
    Assert.assertNotNull(updatedInteractions);
    Assert.assertNotNull(elsClient.getLastSoapRequest());
    Assert.assertTrue(elsClient.getLastSoapRequest().indexOf("listInteractionsRequest") > 0);
    Assert.assertNotNull(elsClient.getLastSoapResponse());
    Assert.assertTrue(elsClient.getLastSoapResponse().indexOf("listInteractionsResponse") > 0);

    Assert.assertTrue(containsInteraction(certRefList, updatedInteractions) == false);
  }
*/

  @Test(expected = UnsupportedOperationException.class)
  public void testRemoveInteractionFailsWhenCalledOnALookupOnlyElsClientWithoutCertificateRefs() throws Exception {
    SSLSocketFactory sslSocketFactory= getSslSocketFactory();
    ELSClient testClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, sslSocketFactory);

    InteractionType interaction = getInteraction(FAIRHILL_HPIO, FAIRHILL_NEW_SERVICE_CATEGORY, FAIRHILL_NEW_SERVICE_INTERFACE, FAIRHILL_NEW_SERVICE_ENDPOINT_URL, FAIRHILL_NEW_SERVICE_PROVIDER);

    testClient.removeInteraction(interaction);
  }

  @Test
  public void testSoapHandlerWithNullLoggingHandler() throws Exception {
    SSLSocketFactory sslSocketFactory = getSslSocketFactory();
    ELSClient elsClient = new ELSClient(DRP_ELS_LOOKUP_ENDPOINT_URL, DRP_ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);
    ReflectionTestUtils.setField(elsClient, "loggingHandler", null);

    String lastSoapRequest = elsClient.getLastSoapRequest();
    Assert.assertEquals(lastSoapRequest, LoggingHandler.EMPTY);

    String lastSoapResponse = elsClient.getLastSoapResponse();
    Assert.assertEquals(lastSoapResponse, LoggingHandler.EMPTY);
  }

  
  private boolean containsInteraction(List<ElsCertRefType> certRefList, List<InteractionType> interactions) {
    boolean result = false;
    for (InteractionType interaction : interactions) {
      if (!result) {
        result = (interaction.getTarget().equals(FAIRHILL_HPIO)) &&
                           (interaction.getServiceCategory().equals(FAIRHILL_NEW_SERVICE_CATEGORY)) &&
                           (interaction.getServiceInterface().equals(FAIRHILL_NEW_SERVICE_INTERFACE)) &&
                           (interaction.getServiceEndpoint().equals(FAIRHILL_NEW_SERVICE_ENDPOINT_URL)) &&
                           (interaction.getServiceProvider().equals(FAIRHILL_NEW_SERVICE_PROVIDER)) &&
                           (interaction.getCertRef().size() == certRefList.size() &&
                           (interaction.getCertRef().get(0).getQualifiedCertRef() != null) &&
                           (certRefList.get(0).getUseQualifier()).equals(interaction.getCertRef().get(0).getUseQualifier()) &&
                           (certRefList.get(0).getQualifiedCertRef().getType()).equals(interaction.getCertRef().get(0).getQualifiedCertRef().getType()) &&
                           (certRefList.get(0).getQualifiedCertRef().getValue().equals(interaction.getCertRef().get(0).getQualifiedCertRef().getValue())));
      } else {
        break;
      }
    }

    return result;
  }

  private void verifyInteraction(Capture<InteractionType> interactionCapture) {
    Assert.assertEquals(this.FAIRHILL_HPIO, interactionCapture.getValue().getTarget());
    Assert.assertEquals(this.FAIRHILL_NEW_SERVICE_ENDPOINT_URL, interactionCapture.getValue().getServiceEndpoint());
    Assert.assertEquals(this.FAIRHILL_NEW_SERVICE_CATEGORY, interactionCapture.getValue().getServiceCategory());
    Assert.assertEquals(this.FAIRHILL_NEW_SERVICE_INTERFACE, interactionCapture.getValue().getServiceInterface());
    Assert.assertEquals(this.FAIRHILL_NEW_SERVICE_PROVIDER, interactionCapture.getValue().getServiceProvider());
  }

  private InteractionRequestType getInteractionRequest(String target, List<String> serviceCategories, List<String> serviceInterfaces) {
    InteractionRequestType interactionRequest = new InteractionRequestType();
    interactionRequest.setTarget(target);
    interactionRequest.setServiceCategory(serviceCategories);
    interactionRequest.setServiceInterface(serviceInterfaces);

    return interactionRequest;
  }

  private InteractionType getInteraction(String target, String serviceCategory, String serviceInterface, String serviceEndpoint, String serviceProvider) {
    InteractionType interaction = new InteractionType();
    interaction.setTarget(target);
    interaction.setServiceCategory(serviceCategory);
    interaction.setServiceInterface(serviceInterface);
    interaction.setServiceEndpoint(serviceEndpoint);
    interaction.setServiceProvider(serviceProvider);
    interaction.setCertRef(new ArrayList<ElsCertRefType>());
    
    return interaction;
  }

  private InteractionType getInteraction(String target, String serviceCategory, String serviceInterface, String serviceEndpoint, String serviceProvider, List<ElsCertRefType> certRefList) {
    InteractionType interaction = getInteraction(target, serviceCategory, serviceInterface, serviceEndpoint, serviceProvider);
    interaction.setCertRef(certRefList);
    return interaction;
  }

  private ElsCertRefType getElsCertificateReference(String certificateReferenceUseQualifier, String certificateReferenceType, String certificateReferenceValue) {
    QualifiedCertRefType qualifiedCertRef = new QualifiedCertRefType();
    qualifiedCertRef.setType(certificateReferenceType);
    qualifiedCertRef.setValue(certificateReferenceValue);
    ElsCertRefType elsCertRef = new ElsCertRefType();
    elsCertRef.setQualifiedCertRef(qualifiedCertRef);
    elsCertRef.setUseQualifier(certificateReferenceUseQualifier);
    return elsCertRef;
  }

  private SSLSocketFactory getSslSocketFactory() throws GeneralSecurityException, IOException {
    return KeystoreUtil.getSslSocketFactory(PRIVATE_KEY_STORE_TYPE,
                                            PRIVATE_KEY_STORE_FILE,
                                            PRIVATE_KEY_STORE_PASSWORD,
                                            PRIVATE_KEY_PASSWORD,
                                            PRIVATE_KEY_ALIAS,
                                            TRUST_STORE_TYPE,
                                            TRUST_STORE_FILE,
                                            TRUST_STORE_PASSWORD);
  }
}
