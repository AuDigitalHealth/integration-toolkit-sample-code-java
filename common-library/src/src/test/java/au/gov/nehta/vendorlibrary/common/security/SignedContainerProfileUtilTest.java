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
package au.gov.nehta.vendorlibrary.common.security;

import au.gov.nehta.vendorlibrary.common.DOMUtil;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

import java.lang.reflect.Constructor;
import java.security.cert.X509Certificate;
import java.util.List;

import static au.gov.nehta.vendorlibrary.common.TestConstant.*;
import static org.junit.Assert.assertNotNull;

public class SignedContainerProfileUtilTest {


  @Test
  public void testSignedContainerProfileUtilPrivateConstructor() throws Exception {
    Constructor[] constructors = SignedContainerProfileUtil.class.getDeclaredConstructors();
    constructors[0].setAccessible(true);
    SignedContainerProfileUtil currentInstance = (SignedContainerProfileUtil) constructors[0].newInstance(null);
    assertNotNull(currentInstance);
  }

  @Test
  public void testGetSignedEncryptedPayload() throws Exception {

    Document signedDocument = SignedContainerProfileUtil.getSignedPayload(SENSITIVE_PAYLOAD,
        getSigningCertificate(DRP_PRIVATE_KEY_ALIAS), getSigningPrivateKey(DRP_PRIVATE_KEY_ALIAS));
    Assert.assertNotNull(signedDocument);
    String signedDocContent = DOMUtil.serialiseToString(signedDocument);
    System.out.println(signedDocContent);
    Assert.assertNotNull(signedDocContent);
    Assert.assertTrue(signedDocContent.contains("<sample>This is a sensitive data</sample>"));

  }


  @Test
  public void testGetDataFromSignedPayload() throws Exception {
    Document signedDocument = SignedContainerProfileUtil.getSignedPayload(SENSITIVE_PAYLOAD,
        getSigningCertificate(DRP_PRIVATE_KEY_ALIAS), getSigningPrivateKey(DRP_PRIVATE_KEY_ALIAS));

    Document extractedPayload = SignedContainerProfileUtil.getDataFromSignedPayload(signedDocument);
    Assert.assertNotNull(extractedPayload);
    Assert.assertNotNull(DOMUtil.serialiseToString(extractedPayload));
    Assert.assertTrue(DOMUtil.serialiseToString(extractedPayload).contains(SENSITIVE_PAYLOAD));
  }

  @Test
  public void testGetDigestValue() throws Exception {
    Document signedDocument = SignedContainerProfileUtil.getSignedPayload(SENSITIVE_PAYLOAD,
        getSigningCertificate(DRP_PRIVATE_KEY_ALIAS), getSigningPrivateKey(DRP_PRIVATE_KEY_ALIAS));

    List<byte[]> digestList = SignedContainerProfileUtil.getDigestValue(signedDocument);
    Assert.assertNotNull(digestList);
    Assert.assertTrue(digestList.size() == 1);
  }


  @Test
  public void testGetSigningCertificates() throws Exception {
    Document signedDocument = SignedContainerProfileUtil.getSignedPayload(SENSITIVE_PAYLOAD,
        getSigningCertificate(DRP_PRIVATE_KEY_ALIAS), getSigningPrivateKey(DRP_PRIVATE_KEY_ALIAS));

    List<X509Certificate> x509Certificates = SignedContainerProfileUtil.getSigningCertificates(signedDocument);
    Assert.assertNotNull(x509Certificates);
    Assert.assertTrue(x509Certificates.size() == 1);
  }


  @Test
  public void testVerifySignature() throws Exception {
    Document signedDocument = SignedContainerProfileUtil.getSignedPayload(SENSITIVE_PAYLOAD,
        getSigningCertificate(DRP_PRIVATE_KEY_ALIAS), getSigningPrivateKey(DRP_PRIVATE_KEY_ALIAS));
    Assert.assertTrue(SignedContainerProfileUtil.verifySignature(signedDocument));
  }


}
