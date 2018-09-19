/*
 * Copyright 2015 NEHTA
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
package au.gov.nehta.vendorlibrary.hi.sample;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLSocketFactory;

import au.gov.nehta.vendorlibrary.common.security.KeystoreUtil;
import au.gov.nehta.vendorlibrary.hi.hpio.ProviderManageProviderDirectoryEntryClient;
import au.net.electronichealth.ns.hi.svc.providermanageproviderdirectoryentry._3_2.ManageProviderDirectoryEntry;
import au.net.electronichealth.ns.hi.svc.providermanageproviderdirectoryentry._3_2.ManageProviderDirectoryEntryResponse;
import au.net.electronichealth.ns.hi.svc.providermanageproviderdirectoryentry._3_2.StandardErrorMsg;
import au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType;
import au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId;

/**
 * <b> Requirements:</b> <br/>
 * <br/>
 * a) A Transport Layer Security (TLS) public/private key pair and its
 * associated public certificate. These are used to authenticate the client to
 * the HI Service server instance being used during the Transport Layer Security
 * (TLS) handshake. They are typically stored in a Java key store file.<br/>
 * The user's certificate, private and public keys go into keystore.jks, while
 * certificates of external parties goes into truststore.jks Java key store
 * file.<br/>
 * <br/>
 * <p/>
 * b) A signing public/private key pair and its associated public certificate.
 * These are used by the client to sign all Web Service requests to the HI
 * Service server. The associated public certificate is always an organisation
 * certificate provided by a recognized Certificate Authority. Store the public
 * and private signing key in truststore.jks and keystore.jks Java key store
 * file, which may be the same as the one used for the key pair in step (a).<br/>
 * <br/>
 * <p/>
 * c) The certificate of the Certificate Authority (CA) which signed the HI
 * Service server's TLS certificate. This certificate is used to authenticate
 * the HI Service server certificate during the TLS handshake. The Medicare
 * vendor environment certificate has been stored in the truststore.jks public
 * Java key store file. <br/>
 * <br/>
 * <p/>
 * d) Medicare authentication details. These will be provided by Medicare,
 * include a Qualified Identifier identifying you to Medicare. These details
 * should be included as a Java QualifiedId object. <br/>
 * <br/>
 * <p/>
 * e) Client product information details (PCIN) These include a Qualified
 * Identifier for the product, the product name and version, and the product
 * platform. These should all be instantiated in a Java Holder<ProductType>
 * object.<br/>
 * <br/>
 * f) The endpoint URLs for the HI Service. <br/>
 * <br/>
 * <p/>
 * g) Parameters for Provider organisation (HPIO) Search criteria. <br/>
 * <br/>
 * h) If required, update the class variable if the chosen values are different
 * to those provided. <br/>
 * <br/>
 */
public final class ProviderManageProviderDirectoryEntryClientSample {
	// Example values for Client product information (PCIN).
	/**
	 * Vendor product platform. <br/>
	 * user defined value. ( Can be any value)
	 */
	private static final String PRODUCT_PLATFORM = "Windows XP SP3";

	/**
	 * Vendor product name. Provided by Medicare
	 */
	private static final String PRODUCT_NAME = "Product Name";

	/**
	 * Vendor product version. Provided by Medicare
	 */
	private static final String PRODUCT_VERSION = "Product Version";
	/**
	 * Vendor Qualifier ID provided by Medicare Australia.
	 */
	private static final String VENDOR_QUALIFIFER_ID = "NEHTA00001";
	/**
	 * Vendor Qualifier provided by Medicare Australia.
	 */
	private static final String VENDOR_QUALIFIER = "http://ns.medicareaustralia.gov.au/mcaVendorId/1.0";

	// User information
	/**
	 * User Qualifier ID.<br/>
	 * User Identifier defined by user.
	 */
	private static final String USER_QUALIFIED_ID = "CN=OMO Test Certificate 01 :5975467761,O=Health,ST=ACT,C=AU";
	/**
	 * User qualifier value. <br>
	 * user qualifier defined by user.
	 */
	private static final String USER_QUALIFIER = "http://ns.medicareaustralia.gov.au/id/hi/distinguishedname/1.0";

	/**
	 * Private keystore type.
	 */
	private static final String PRIVATE_KEY_STORE_TYPE = "JKS";

	/**
	 * Private keystore password.
	 */
	private static final String PRIVATE_KEY_STORE_PASSWORD = "changeit";

	/**
	 * Private keystore filename
	 */
	private static final String PRIVATE_KEY_STORE_FILE = "keystore.jks";

	/**
	 * Private key alias name.
	 */
	private static final String PRIVATE_KEY_ALIAS_NAME = "8003624166667003";

	/**
	 * Private key certificate Alias.
	 */
	private static final String PRIVATE_KEY_CERTIFICATE_ALIAS_NAME = "8003624166667003";

	/**
	 * Truststore type.
	 */
	private static final String TRUSTSTORE_TYPE = "JKS";

	/**
	 * Private Key password.
	 */
	private static final String PRIVATE_KEY_PASSWORD = "changeit";

	/**
	 * Truststore filename.
	 */
	private static final String TRUSTSTORE_FILE = "truststore.jks";

	/**
	 * Truststore password.
	 */
	private static final String TRUSTSTORE_PASSWORD = "changeit";

	/**
	 * Medicare endpoint url.
	 */
	private static final String MEDICARE_ENDPOINT_URL = "https://www5.medicareaustralia.gov.au/cert/soap/services/";

	// Provider Organisation HPIO search parameters
	/**
	 * Provider Organisation fully qualified HPIO. <br/>
	 * Example provided here
	 */
	private static final String QAULIFIED_HPIO = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003624166667003";

	/**
	 * Default private constructor.
	 */
	private ProviderManageProviderDirectoryEntryClientSample() {

	}

	/**
	 * Main method to invoke Provider Directory Search for Organisation. (HPIO
	 * search)
	 *
	 * @param args
	 *            user arguments for main method invocation (NOT REQUIRED)
	 * @throws java.security.GeneralSecurityException
	 *             in an event of security / certificate error.
	 * @throws java.io.IOException
	 *             in an event of IO exception.
	 */
	public static void main(String[] args) throws GeneralSecurityException, IOException {

		// Set user QualifiedId
		QualifiedId userQualifiedId = getUserQualifedId();

		// Set vendor QualifiedId
		QualifiedId vendorQualifiedId = getVendorQualifiedId();

		// Set up the client product information.
		ProductType productHeader = getProduct(vendorQualifiedId);

		// Set the client signing privateKey
		PrivateKey clientSigningPrivateKey = getPrivateKey();

		// Set the client signing public key/ certificate
		X509Certificate clientSigningCertificate = getSigningCertificate();

		// Set the SSLSocketFactory instance for the TLS connection.
		SSLSocketFactory sslSocketFactory = getSSLSocketFactory();


		/**
		 * For detailed business rules refer to 
		 * Manage provider directory entry TECH.SIS.HI.19
		 */
		
		ProviderManageProviderDirectoryEntryClient testClient = new ProviderManageProviderDirectoryEntryClient(MEDICARE_ENDPOINT_URL, userQualifiedId, productHeader, clientSigningPrivateKey,
				clientSigningCertificate, sslSocketFactory);

		Integer individualToDelete = 1;
		
		ManageProviderDirectoryEntry request = new ManageProviderDirectoryEntry();
		request.setQualifiedIdentifier(QAULIFIED_HPIO);
		request.getIndividualDeleteEntry().add(individualToDelete);
	 
		 
		
		// Set system variables to dump SOAP message to console.
		System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");


		try {
			ManageProviderDirectoryEntryResponse searchHIProviderDirectoryForOrganisationResponse = testClient.manageProviderDirectoryEntry(request);
		} catch (StandardErrorMsg standardErrorMsg) {
			standardErrorMsg.printStackTrace();
		}

		// Dump SOAP request and response to variables. This is independent of
		// HttpTransportPipe dump
		String lastSoapRequest = testClient.getLastSoapRequest();
		String lastSoapResponse = testClient.getLastSoapResponse();
	}

	/**
	 * Get Vendor Product header.
	 *
	 * @param vendorQualifiedId
	 *            vendor qualified ID as vendorQualifiedId
	 * @return the default ProductType
	 */
	private static ProductType getProduct(QualifiedId vendorQualifiedId) {
		ProductType productHeader = new ProductType();
		productHeader.setPlatform(PRODUCT_PLATFORM);
		productHeader.setProductName(PRODUCT_NAME);
		productHeader.setProductVersion(PRODUCT_VERSION);
		productHeader.setVendor(vendorQualifiedId);
		return productHeader;
	}

	/**
	 * Returns the constructed user QualifiedId
	 *
	 * @return user qualified identifier as QualifiedId
	 */
	private static QualifiedId getUserQualifedId() {
		QualifiedId userQualifiedId = new QualifiedId();
		userQualifiedId.setId(USER_QUALIFIED_ID);
		userQualifiedId.setQualifier(USER_QUALIFIER);
		return userQualifiedId;
	}

	/**
	 * Returns the constructed vendor QualifiedId
	 *
	 * @return vendor qualified identifier as QualifiedId
	 */
	private static QualifiedId getVendorQualifiedId() {
		QualifiedId vendorQualifiedId = new QualifiedId();
		vendorQualifiedId.setId(VENDOR_QUALIFIFER_ID);
		vendorQualifiedId.setQualifier(VENDOR_QUALIFIER);
		return vendorQualifiedId;
	}

	/**
	 * Returns the generated default client private key as PrivateKey
	 *
	 * @return client private key as PrivateKey
	 * @throws GeneralSecurityException
	 *             in a event of error.
	 */
	private static PrivateKey getPrivateKey() throws GeneralSecurityException {
		PrivateKey clientSigningPrivateKey = KeystoreUtil.getSigningPrivateKey(PRIVATE_KEY_STORE_TYPE, PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE, PRIVATE_KEY_ALIAS_NAME);

		return clientSigningPrivateKey;
	}

	/**
	 * Returns the generated default client private key as PrivateKey
	 *
	 * @return client private key as PrivateKey
	 * @throws GeneralSecurityException
	 */
	private static X509Certificate getSigningCertificate() throws GeneralSecurityException {
		X509Certificate clientSigningCertificate = KeystoreUtil.getSigningCertificate(PRIVATE_KEY_STORE_TYPE, PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_STORE_FILE, PRIVATE_KEY_CERTIFICATE_ALIAS_NAME);

		return clientSigningCertificate;
	}

	/**
	 * Returns the client ssl socket factory instance for TLS connection.
	 *
	 * @return client ssl socket factory credentials as SSLSocketFactory
	 * @throws IOException
	 *             in an event of IO error.
	 * @throws GeneralSecurityException
	 *             in an event of Security error.
	 */
	private static SSLSocketFactory getSSLSocketFactory() throws IOException, GeneralSecurityException {
		// Set the SSLSocketFactory instance for the TLS connection.
		SSLSocketFactory sslSocketFactory = KeystoreUtil.getSslSocketFactory(PRIVATE_KEY_STORE_TYPE, PRIVATE_KEY_STORE_FILE, PRIVATE_KEY_STORE_PASSWORD, PRIVATE_KEY_PASSWORD, PRIVATE_KEY_ALIAS_NAME,
				TRUSTSTORE_TYPE, TRUSTSTORE_FILE, TRUSTSTORE_PASSWORD);
		return sslSocketFactory;
	}
}
