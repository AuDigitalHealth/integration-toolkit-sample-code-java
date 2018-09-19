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
package au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.tests.recordaccess;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import au.gov.nehta.vendorlibrary.pcehr.clients.recordaccess.DoesPCEHRExistClient;
import au.gov.nehta.vendorlibrary.pcehr.test._20120718_regression.AllTests;

import au.net.electronichealth.ns.pcehr.b2b.svc.pcehrprofile._1.StandardErrorMsg;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.pcehrprofile._1.DoesPCEHRExistResponse;

public class DoesPCHERExistClientTest {
  private static final Logger log = Logger.getLogger( "test" );
  private DoesPCEHRExistClient client;

  @Before
  public final void setUp() throws Exception {
    AllTests.setUp();
    client = new DoesPCEHRExistClient(
      AllTests.getSslSocketFactory(),
      AllTests.getCertificate(),
   
      AllTests.getPrivateKey(),
      "https://b2b.ehealthvendortest.health.gov.au/doesPCEHRExist",
     true
    );
  }

  @After
  public final void tearDown() throws Exception {
    AllTests.tearDown();
    client = null;
  }

  @Test
  public void test_CustomHandlerChain() throws StandardErrorMsg{
      
      SOAPHandler<SOAPMessageContext> myHandler = new SOAPHandler<SOAPMessageContext>(){

        @Override
        public boolean handleMessage( SOAPMessageContext context ) {
            System.out.println( "I'm handeling it" );
            return false;
        }

        @Override
        public boolean handleFault( SOAPMessageContext context ) {return false;}

        @Override
        public void close( MessageContext context ) {}

        @Override
        public Set<QName> getHeaders() { return null; }

     
        };
        
    BindingProvider bindingProvider =  (BindingProvider) client.getPort();
    List<Handler> preExistingHandlerChain = bindingProvider.getBinding().getHandlerChain( );
    preExistingHandlerChain.add( myHandler );
   // bindingProvider.getBinding().setHandlerChain( preExistingHandlerChain );
    System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
	  System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
	 
	  System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
	  					           
	  System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
    try{
	    DoesPCEHRExistResponse doesPCEHRExist = client.doesPCEHRExist(AllTests.getDefaultRequest());
	    System.out.println(doesPCEHRExist.isPCEHRExists());
    }catch(StandardErrorMsg e){
    	System.out.println(e.getFaultInfo().getMessage());
    	System.out.println(e.getFaultInfo().getErrorCode());
    	throw e;
    }
   // 
    
    
    
  }
  
  @Test
  public void test_SocketTimeout() throws StandardErrorMsg{
      
     //request timeout
    client.setProperty( "com.sun.xml.internal.ws.request.timeout", 2000);
    client.setProperty( "com.sun.xml.ws.request.timeout", 2000);
    
    //connect timeouts
    client.setProperty( "com.sun.xml.internal.ws.connect.timeout", 2000);
    client.setProperty( "com.sun.xml.ws.connect.timeout", 2000);
    DoesPCEHRExistResponse doesPCEHRExist = client.doesPCEHRExist(AllTests.getDefaultRequest());
    System.out.println(doesPCEHRExist.isPCEHRExists());
    
    
    
  }
  
  @Test
  public void test_030() throws Exception {
//	  System.out.println();
	  
//	   BindingProvider bindingProvider = (BindingProvider) client.getPort();
//
//	    // Set details on request context
//	    final Map<String, Object> requestContext = bindingProvider.getRequestContext();
//	    String s =(String) requestContext.get( BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
//	    System.out.println("endpoint:"+s);
	  
    DoesPCEHRExistResponse response = client.doesPCEHRExist(AllTests.getDefaultRequest());
    Assert.assertEquals(true, response.isPCEHRExists());
    Assert.assertEquals(DoesPCEHRExistResponse.AccessCodeRequired.ACCESS_GRANTED, response.getAccessCodeRequired());
  }
}
