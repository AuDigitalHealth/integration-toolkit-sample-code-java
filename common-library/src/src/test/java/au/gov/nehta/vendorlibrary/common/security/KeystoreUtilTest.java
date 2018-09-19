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

import static au.gov.nehta.vendorlibrary.common.TestConstant.*;

import au.gov.nehta.vendorlibrary.common.TestConstant;
import org.junit.Test;
import org.junit.experimental.theories.Theories;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class KeystoreUtilTest {

  @Test
  public void testSubjectAltenativeNameForCertificates() throws Exception{

    X509Certificate x509Certificate = TestConstant.getSigningCertificate(TestConstant.MEDICARE_CURRENT_PRIVATE_KEY_ALIAS);
    Collection altNames=null;
    Collection issuerAltNames=null;
    issuerAltNames= x509Certificate.getIssuerAlternativeNames();

    Principal princple = x509Certificate.getSubjectDN();
    princple.getName();

    System.out.println("The subject name is "+ princple.getName());

    String[] subjectContents = princple.getName().split(", ");
    String subjectKey="CN=";
    for(String uniqueId: subjectContents){
      if(uniqueId.indexOf(subjectKey)>=0){
        System.out.println("the CN name is " + uniqueId.substring((uniqueId.lastIndexOf("=") + 1)));
      }
    }

    altNames =  x509Certificate.getSubjectAlternativeNames();
    //populate alternativeNames
    if(altNames!= null){
      Iterator itAltNames  = altNames.iterator();
      while(itAltNames.hasNext()){
        List extensionEntry = (List)itAltNames.next();
        Integer nameType = (Integer) extensionEntry.get(0);
        String name = (String) extensionEntry.get(1);
        System.out.println("The name is "+ name);
      }
    }

    if(issuerAltNames!= null){
      Iterator issuerAltNamesItr  = issuerAltNames.iterator();
      while(issuerAltNamesItr.hasNext()){
        List extensionEntry = (List)issuerAltNamesItr.next();
        Integer nameType = (Integer) extensionEntry.get(0);
        String name = (String) extensionEntry.get(1);
        System.out.println("The name is "+ name);
      }
    }

  }


  @Test
  public void testSubjectAlternativeNameForMedicareNewCertificates() throws Exception{

    X509Certificate x509Certificate = TestConstant.getSigningCertificate(TestConstant.MEDICARE_NEW_PRIVATE_KEY_ALIAS);
    Collection altNames=null;
    Collection issuerAltNames=null;
    issuerAltNames= x509Certificate.getIssuerAlternativeNames();

    Principal princple = x509Certificate.getSubjectDN();
    princple.getName();

    System.out.println("The subject name is "+ princple.getName());

    String[] subjectContents = princple.getName().split(", ");
    String subjectKey="CN=";
    for(String uniqueId: subjectContents){
      if(uniqueId.indexOf(subjectKey)>=0){
        System.out.println("the CN name is "+uniqueId.substring((uniqueId.lastIndexOf("=")+1)));
      }
    }

    altNames =  x509Certificate.getSubjectAlternativeNames();
    //populate alternativeNames
    if(altNames!= null){
      Iterator itAltNames  = altNames.iterator();
      while(itAltNames.hasNext()){
        List extensionEntry = (List)itAltNames.next();
        Integer nameType = (Integer) extensionEntry.get(0);
        String name = (String) extensionEntry.get(1);
        System.out.println("The name is "+ name);
      }
    }

    if(issuerAltNames!= null){
      Iterator issuerAltNamesItr  = issuerAltNames.iterator();
      while(issuerAltNamesItr.hasNext()){
        List extensionEntry = (List)issuerAltNamesItr.next();
        Integer nameType = (Integer) extensionEntry.get(0);
        String name = (String) extensionEntry.get(1);
        System.out.println("The name is "+ name);
      }
    }

  }
  //URN:NEHTA:HPI-O:8003622345681622

  private void  getKeystore(KeyStore keyStore, String saName) throws KeyStoreException, CertificateParsingException {
    Enumeration<String> aliases = keyStore.aliases();

    Collection altNames=null;

    while(aliases.hasMoreElements())
    {
      String alias = aliases.nextElement();
      X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);

      altNames =  certificate.getSubjectAlternativeNames();
      //populate alternativeNames
      if(altNames!= null){
        Iterator itAltNames  = altNames.iterator();
        while(itAltNames.hasNext()){
          List extensionEntry = (List)itAltNames.next();
          Integer nameType = (Integer) extensionEntry.get(0);
          String name = (String) extensionEntry.get(1);
          if(name.equals(saName))
          {
            System.out.println("the name matched "+ name);
          }

        }
      }

    }

  }

  @Test
  public void testGetCertificatesByAlternativeNames() throws Exception{
    KeyStore keyStore = KeystoreUtil.loadKeyStore(PRIVATE_KEY_STORE_TYPE, PRIVATE_KEY_STORE_PASSWORD,
      PRIVATE_KEY_STORE_FILE);
    String subjectAlternateName = "URN:NEHTA:HPI-O:8003622345681622";
    getKeystore(keyStore,subjectAlternateName);
  }


}
