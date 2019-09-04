package au.gov.nehta.test;

import static org.junit.Assert.assertTrue;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import au.gov.nehta.schematron.Schematron;
import au.gov.nehta.schematron.SchematronCheckResult;

public class TestSchematronCheck {

    @Test
    // test the schema check against a CDA document with a known
    // number of schema and schematron errors
    public void testSimpleInterface() {

        SchematronCheckResult result = null;

        result = Schematron.check( "resources/test/ePrescription/", "ccd-3b.scha", "resources/test/eprescription-max.xml" );

        assertTrue( result.schemaErrors.size() == 1 );
        assertTrue( result.schematronErrors.size() == 6 );
    }

    @Test
    // test the schema check against a CDA document with a known
    // number of schema and schematron errors
    public void testSimpleInterfaceWithDocument() {

        SchematronCheckResult result = null;
        Document ePrescription = readDocument( "resources/test/eprescription-max.xml" );
        result = Schematron.check( "resources/test/ePrescription/", "ccd-3b.sch", ePrescription );

        assertTrue( result.schemaErrors.size() == 1 );
        assertTrue( result.schematronErrors.size() == 6 );
    }

    @Test
    // test the schema check against a CDA document with a known
    // number of schema and schematron errors
    public void testLargeInterface() {
        SchematronCheckResult result = null;
        result = Schematron.check( "resources/test/ePrescription/", "resources/test/ePrescription/cdar2/CDA.xsd", "resources/test/ePrescription/cdar2/EXTENSION.xsd",
                "resources/test/ePrescription/schematron/ccd-3B.sch", "resources/test/ePrescription/schematron/schematron-Validator-report.xsl", "resources/test/eprescription-max.xml" );

        assertTrue( result.schemaErrors.size() == 1 );
        assertTrue( result.schematronErrors.size() == 6 );
    }

    @Test
    // run the schematron check a number of times to test compiled performance
    public void testAverageExecutionTime() {

        long t = System.currentTimeMillis();
        int runs = 10;
        for (int i = 0; i < runs; i++) {
            SchematronCheckResult result = 
                    Schematron.check( 
                            "resources/test/ePrescription/", 
                            "resources/test/ePrescription/cdar2/CDA.xsd",
                            "resources/test/ePrescription/cdar2/EXTENSION.xsd",
                            "resources/test/ePrescription/schematron/ccd-3B.sch", 
                            "resources/test/ePrescription/schematron/schematron-Validator-report.xsl",
                            "resources/test/eprescription-max.xml" );
            assertTrue( result.schemaErrors.size() == 1 );
            assertTrue( result.schematronErrors.size() == 6 );

        }

        long b = System.currentTimeMillis() - t;
        long avgPerRun = b / runs;
        System.out.println( "Average Execution time: " + avgPerRun + "ms" );

        // should be subsecond performance
        assertTrue( avgPerRun < 1000 );

    }

    private Document readDocument( String xmlLocation ) {
        DocumentBuilderFactory DocumentBuilderfactory = getDocumentBuilder();
        DocumentBuilder builder = null;
        Document doc = null;

        try {
            builder = DocumentBuilderfactory.newDocumentBuilder();

            // use a null error handler
            builder.setErrorHandler( new ErrorHandler() {

                @Override
                public void warning( SAXParseException exception ) throws SAXException {
                    // no-op
                }

                @Override
                public void fatalError( SAXParseException exception ) throws SAXException {
                    // no-op
                }

                @Override
                public void error( SAXParseException exception ) throws SAXException {
                    // no-op
                }
            } );

            doc = builder.parse( xmlLocation );
        } catch (Exception e) {
            throw new RuntimeException( e );
        }
        return doc;
    }

    public static DocumentBuilderFactory getDocumentBuilder() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware( true );
        documentBuilderFactory.setValidating( true );
        documentBuilderFactory.setAttribute( "http://java.sun.com/xml/jaxp/properties/schemaLanguage", XMLConstants.W3C_XML_SCHEMA_NS_URI );

        // documentBuilderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource",
        // schema);
        documentBuilderFactory.setIgnoringElementContentWhitespace( true );
        return documentBuilderFactory;
    }

}
