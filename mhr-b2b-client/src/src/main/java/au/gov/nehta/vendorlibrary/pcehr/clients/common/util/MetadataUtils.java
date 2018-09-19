/*
 * Copyright 2012 NEHTA
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

package au.gov.nehta.vendorlibrary.pcehr.clients.common.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.Separators;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XDSConstants;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XPathExpressions;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CX;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CodedValue;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentMetadata;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.DocumentTypeCodes;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HD;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.HD.Builder;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.XCN;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.XON;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;

/**
 * Metadata utilities.
 */
public final class MetadataUtils {

	private static final String DIAGNOSTIC_IMAGE = "100.16957";
	private static final String PATHOLOGY_DOCUMENT_CODE = "100.32001";
	private static final String DISPENSE_DOCUMENT_CODE = "100.16765";
	private static final String PRESCRIPTION_DOCUMENT_CODE = "100.16764";
	private static final String DEFAULT_LANGUAGE_CODE = "en-AU";
    private static final int YMD_DATE_FORMAT = 8;
	private static final int YMDHM_DATE_FORMAT = 12;
	private static final int YMDHMS_DATE_FORMAT = 14;
	private static final int TIMEZONE_FORMAT = 5;
	private static final int YMDHM_WITH_TIMEZONE_FORMAT = YMDHM_DATE_FORMAT + TIMEZONE_FORMAT;
	private static final int YMDHMS_WITH_TIMEZONE_FORMAT = YMDHMS_DATE_FORMAT + TIMEZONE_FORMAT;

	/**
	 * Private constructor.
	 */
	private MetadataUtils() {
	}

	/**
	 * Calculates SHA-1 hash from a byte array.
	 * 
	 * @param value
	 *            Input byte array.
	 * @return SHA-1 hash.
	 */
	public static byte[] calculateSha1Hash( byte[] value ) {
		try {
			MessageDigest md = MessageDigest.getInstance( "sha1" );
			return md.digest( value );
		} catch (NoSuchAlgorithmException e) {
			// This should never be encountered given we are using a valid
			// constant
			throw new IllegalStateException( "Unexpected digest method encountered.", e );
		}
	}

	/**
	 * Helper method used to construct a DocumentMetadata object.
	 * 
	 * @param commonHeader
	 *            PCEHR request common header.
	 * @param cdaDocument
	 *            CDA root document.
	 * @return {@link DocumentMetadata} populated.
	 * @throws ParserConfigurationException
	 *             Thrown in the event
	 * @throws IOException
	 * @throws SAXException
	 * @throws XPathExpressionException
	 */
	public static DocumentMetadata toDocumentMetadata( PCEHRHeader commonHeader, final byte[] cdaDocument ) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {

		// Load file into DOM.
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse( new ByteArrayInputStream( cdaDocument ) );

		// Populate metadata using DOM.
		return toDocumentMetadata( commonHeader, doc );
	}

	public static DocumentMetadata toDocumentMetadata( PCEHRHeader commonHeader, Document rootDocument ) throws XPathExpressionException {

		// Instantiate document metadata object.
		DocumentMetadata documentMetadata = new DocumentMetadata();

		// Determine whether ID is an oid or a UUID.

		String docId = XPathUtils.evaluateXPath( XPathExpressions.DOCUMENT_ID, rootDocument );

		UUID docUuid = null;
		try {
			docUuid = UUID.fromString( docId );
		} catch (IllegalArgumentException e) {
			// do nothing. oid.
		}

		// UUID --> OID
		if (docUuid != null) {
			documentMetadata.setUniqueId( OIDUtil.convertUUIDToOIDIntegerPair( docId ) );
		} else {
			documentMetadata.setUniqueId( docId );
		}

		// Extract values from the supplied CDA root document and populate the
		// metadata.
		documentMetadata.setCreationTime( DateUtils.toUtcDate( XPathUtils.evaluateXPath( XPathExpressions.CREATION_TIME, rootDocument ) ) );
		String languageCode = XPathUtils.evaluateXPath( XPathExpressions.LANGUAGE_CODE, rootDocument );
        
		if(languageCode == null || languageCode.length() == 0){
		    languageCode=DEFAULT_LANGUAGE_CODE;
		}
		documentMetadata.setLanguageCode( languageCode );
		
		// Create patient ID CX.
		documentMetadata.setPatientId( getPatientId( rootDocument ) );
		documentMetadata.setSourcePatientId( getPatientId( rootDocument ) );

		setStartStopTime( rootDocument, documentMetadata );

		//Try to determin org in the following order
		//1 current author
		//2 custodian author
		//3 HCF author
		// also take the name
		
		String orgId = XPathUtils.evaluateXPath( XPathExpressions.ORG_ID_AUTHOR, rootDocument );
		String orgName = XPathUtils.evaluateXPath( XPathExpressions.ORG_ID_AUTHOR_NAME, rootDocument );
		if(isEmpty(orgId)){
		    orgId = XPathUtils.evaluateXPath( XPathExpressions.ORG_ID_CUSTODIAN, rootDocument );
		    orgName = XPathUtils.evaluateXPath( XPathExpressions.ORG_ID_CUSTODIAN_NAME, rootDocument );
		    if(isEmpty(orgId)){
		        orgId = XPathUtils.evaluateXPath( XPathExpressions.ORG_ID_HCF, rootDocument );
		        orgName = XPathUtils.evaluateXPath( XPathExpressions.ORG_ID_HCF_NAME, rootDocument );
		    }
		}
		
		
		// Extract organisation identifier.
		XON.Builder xonBuilder = new XON.Builder();
		xonBuilder.organisationName( orgName );
		xonBuilder.organisationIdentifier( orgId );
		documentMetadata.setAuthorInstitution( xonBuilder.build() );

		documentMetadata.setAuthorSpecialty( XPathUtils.evaluateXPath( XPathExpressions.AUTHOR_SPECIALTY_NAME, rootDocument ) );

		// Extract author person.
		XCN.Builder xcnBuilder = new XCN.Builder();
		xcnBuilder.familyName( XPathUtils.evaluateXPath( XPathExpressions.AUTHOR_FAMILY_NAME, rootDocument ) );
		xcnBuilder.givenName( XPathUtils.evaluateXPath( XPathExpressions.AUTHOR_GIVEN_NAME, rootDocument ) );
		xcnBuilder.prefix( XPathUtils.evaluateXPath( XPathExpressions.AUTHOR_PREFIX, rootDocument ) );

		/**
		 * There may be many asEntityIdentifier per assigned person. Here we
		 * prefer HPI-I over PAI-D over 'LocalSystemIdentifier'
		 */
		String hpii = XPathUtils.evaluateXPath( XPathExpressions.HPII, rootDocument );

		Builder hdBuilder = new HD.Builder();
		if (isNotEmpty(hpii)) {
			hdBuilder.identifier( hpii ).identifierType( XDSConstants.ISO_IDENTIFIER_TYPE );

		} else {
			String pai_d = XPathUtils.evaluateXPath( XPathExpressions.PAI_D, rootDocument );

			if (isNotEmpty(pai_d)) {
				hdBuilder.identifier( pai_d ).identifierType( XDSConstants.ISO_IDENTIFIER_TYPE );
			} else {
				String root = XPathUtils.evaluateXPath( XPathExpressions.AUTHOR_IDENTITY_AUTHORITY_ROOT, rootDocument );
				String ext = XPathUtils.evaluateXPath( XPathExpressions.AUTHOR_IDENTITY_AUTHORITY_EXTENSION, rootDocument );

				hdBuilder.namespace( root + "^" ).identifier( ext ).identifierType( XDSConstants.ISO_IDENTIFIER_TYPE );
			}
		}
		xcnBuilder.assigningAuthority( hdBuilder.build() );

		documentMetadata.setAuthorPerson( xcnBuilder.build() );

		DocumentTypeCodes type = getDocumentTypeCode( rootDocument );

		documentMetadata.setDocumentClass( getDocumentClass( type ) );
		documentMetadata.setDocumentType( getDocumentType( type ) );


		//never set the title
		//documentMetadata.setTitle( XPathUtils.evaluateXPath( XPathExpressions.TITLE, rootDocument ) );

		// Return populated document metadata object.
		return documentMetadata;
	}
	
	
	// Set service start and stop time.
	private static void setStartStopTime( Document rootDocument, DocumentMetadata documentMetadata ) throws XPathExpressionException {
		//get the document type code
		String documentTypeCode = XPathUtils.evaluateXPath(XPathExpressions.DOCUMENT_TYPE_CODE, rootDocument);

		// if Prescription Start Time
		if (  PRESCRIPTION_DOCUMENT_CODE.equals(documentTypeCode) ) {
			String prescriptionTime = XPathUtils.evaluateXPath( XPathExpressions.PRESCRIPTION_TIME, rootDocument );
			if (isNotEmpty( prescriptionTime )) {
				String utcDate = DateUtils.toUtcDate( prescriptionTime );
				documentMetadata.setServiceStartTime( utcDate );
				documentMetadata.setServiceStopTime( utcDate );
			} else {
				addGenericStartTime( rootDocument, documentMetadata );
				addGenricStopTime( documentMetadata,rootDocument);
			}
			
		 
			
			// else if it's a Dispense Start Time
		} else if (  DISPENSE_DOCUMENT_CODE.equals(documentTypeCode)) {

			String disepnseTime = XPathUtils.evaluateXPath( XPathExpressions.DISPENSE_TIME, rootDocument );
			if (isNotEmpty( disepnseTime )) {
				String utcDate = DateUtils.toUtcDate( disepnseTime );
				documentMetadata.setServiceStartTime( utcDate );
				documentMetadata.setServiceStopTime( utcDate );
			} else {
				addGenericStartTime( rootDocument, documentMetadata );
				addGenricStopTime( documentMetadata,rootDocument);
			}
			
			
			
			//else if pathology report
		} else if (  PATHOLOGY_DOCUMENT_CODE.equals(documentTypeCode)) {
			String collectionTime = XPathUtils.evaluateXPath( XPathExpressions.PATHOLOGY_COLLECTION_TIME, rootDocument );
			if (isNotEmpty( collectionTime )) {
				String utcDate = DateUtils.toUtcDate( collectionTime );
				documentMetadata.setServiceStartTime( utcDate );
				documentMetadata.setServiceStopTime( utcDate );
			} else {
				addGenericStartTime( rootDocument, documentMetadata );
				addGenricStopTime( documentMetadata,rootDocument);
			}
          
            		
		} else if (  DIAGNOSTIC_IMAGE.equals(documentTypeCode)) {
			String imageTime = XPathUtils.evaluateXPath( XPathExpressions.DIAGNOSTIC_IMAGE_TIME, rootDocument );
			if (isNotEmpty( imageTime )) {
				String utcDate = DateUtils.toUtcDate( imageTime );
				documentMetadata.setServiceStartTime( utcDate );
				documentMetadata.setServiceStopTime( utcDate );
			} else {
				addGenericStartTime( rootDocument, documentMetadata );
				addGenricStopTime( documentMetadata,rootDocument);
			}
			
			// else is some other document start time
		} else {
			addGenericStartTime( rootDocument, documentMetadata );
			addGenricStopTime( documentMetadata,rootDocument);
		}
	}
	
	private static void addGenricStopTime( DocumentMetadata documentMetadata,Document rootDocument ) throws XPathExpressionException {
		String cdaStopTime = XPathUtils.evaluateXPath( XPathExpressions.SERVICE_STOP_TIME, rootDocument );
		String cdaFixedTime = XPathUtils.evaluateXPath( XPathExpressions.SERVICE_FIXED_TIME, rootDocument );
		
		if (isNotEmpty( cdaStopTime )) {
			documentMetadata.setServiceStopTime( DateUtils.toUtcDate( cdaStopTime ) );
		} else if (isNotEmpty( cdaFixedTime )) {
			documentMetadata.setServiceStopTime( DateUtils.toUtcDate( cdaFixedTime ) );
		} else {
			documentMetadata.setServiceStopTime( documentMetadata.getCreationTime() );
		}
	}

	private static void addGenericStartTime( Document rootDocument, DocumentMetadata documentMetadata ) throws XPathExpressionException {
		documentMetadata.setServiceStartTime( getGenericStartTime(rootDocument, documentMetadata));
	}
	
	private static String getGenericStartTime( Document rootDocument, DocumentMetadata documentMetadata ) throws XPathExpressionException {
		String cdaStartTime = XPathUtils.evaluateXPath( XPathExpressions.SERVICE_START_TIME, rootDocument );
		String cdaFixedTime = XPathUtils.evaluateXPath( XPathExpressions.SERVICE_FIXED_TIME, rootDocument );

		if (isNotEmpty( cdaStartTime )) {
			return DateUtils.toUtcDate( cdaStartTime );
		} else if (isNotEmpty( cdaFixedTime )) {
			return DateUtils.toUtcDate( cdaFixedTime ) ;
		} else {
			return documentMetadata.getCreationTime();
		}
	}
	
	


	private static CX getPatientId( Document rootDocument ) throws XPathExpressionException {
		// Retrieve and split CDA value.
		CX.Builder builder = new CX.Builder();

		// CDA ID.
		String cdaId = XPathUtils.evaluateXPath( XPathExpressions.PATIENT_ID, rootDocument );

		// CX components.
		String[] components = StringUtils.split( cdaId, Separators.OID );
		builder.identifier( components[components.length - 1] );
		builder.assigningAuthority( new HD.Builder().identifier( StringUtils.substringBeforeLast( cdaId, Separators.OID ) ).identifierType( XDSConstants.ISO_IDENTIFIER_TYPE ).build() );

		// Build and return CX.
		return builder.build();
	}

	private static CodedValue getDocumentClass( DocumentTypeCodes docType ) {
		CodedValue codingSystem = docType.getCodingSystem();
        return new CodedValue( codingSystem.getCodeSystem(), codingSystem.getValue(), codingSystem.getDisplayName() );
	}

	private static CodedValue getDocumentType( DocumentTypeCodes docType ) {
		CodedValue codingSystem = docType.getCodingSystem();
        return new CodedValue( codingSystem.getCodeSystem(), codingSystem.getValue(), docType.getTypeCodeDisplayName() );
	}

	private static DocumentTypeCodes getDocumentTypeCode( Document rootDocument ) throws XPathExpressionException {
		String extractedCode = XPathUtils.evaluateXPath( XPathExpressions.DOCUMENT_TYPE_CODE, rootDocument );
		return DocumentTypeCodes.findByConceptCode( extractedCode );
	}

	public static Date parseDate( String str ) {

		String formatStr;
		if (str.length() == YMD_DATE_FORMAT) {
			formatStr = "yyyyMMdd";
		} else if (str.length() == YMDHM_DATE_FORMAT) {
			formatStr = "yyyyMMddHHmm";
		} else if (str.length() == YMDHMS_DATE_FORMAT) {
			formatStr = "yyyyMMddHHmmss";
		} else if (str.length() == YMDHM_WITH_TIMEZONE_FORMAT) {
			formatStr = "yyyyMMddHHmmz";
		} else if (str.length() == YMDHMS_WITH_TIMEZONE_FORMAT) {
			formatStr = "yyyyMMddHHmmssz";
		} else {
			formatStr = "";
		}

		DateFormat df = new SimpleDateFormat( formatStr );
		try {

			Date tmp = df.parse( str );

			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			gregorianCalendar.setTime( tmp );

			return df.parse( str );
		} catch (ParseException e) {
			throw new IllegalArgumentException( "Value [" + str + "] did not contain a valid date/time.", e );
		}
	}
	
	
	   
    private static boolean isEmpty( String string ) {
        return string == null || "".equals( string );
    }
	
	private static boolean isNotEmpty( String string ) {
		return string != null && !string.isEmpty();
	}
}
