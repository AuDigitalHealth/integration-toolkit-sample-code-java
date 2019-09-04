package au.gov.nehta.schematron;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.validation.ValidatorHandler;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * A utility class for performing Schema and Schematron checks
 * @author Nehta
 *
 */
public class Schematron {

    private static final String                SCHEMATRON_SKELETON_REL_LOCATION = "/schematron/schematron-Validator-report.xsl";
    private static final String                SCHEMATRON_DIR                   = "/schematron/";
    private static final String                EXTENSION_XSD_REL_LOCATION       = "/cdar2/EXTENSION.xsd";
    private static final String                CDA_XSD_REL_LOCATION             = "/cdar2/CDA.xsd";
    
    private static final HashMap<String, Node> transformCache                   = new HashMap<String, Node>();
    private static final TransformerFactory    factory                          = getFactory();   

   
   
    //for static use only
    private Schematron() {}
   
    private static TransformerFactory getFactory() {
        System.setProperty( "javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl" );
        return TransformerFactory.newInstance();
    }
    
    
    /**
     * Perform a schema and schematron check assuming the default template layout 
     * that is: 
     * <pre>
     * [templatePath]/cdar2/CDA.xsd
     * [templatePath]/cdar2/EXTENSION.xsd
     * [templatePath]/schematron/schematron-Validator-report.xsl
     * [templatePath]/schematron/[schematronName]
     * </pre>
     * 
     * @param templatePath          the path of the root Schematron template, for example "C:/mySchematron/e-Discharge Summary/"
     * @param schematronName        the location Schematron file to use for this test, relative to '[templatePath]/schematron' .
     *                              eg: "ccd-3B.sch" 
     * @param XmlToCheck            the path of the XML file to check
     * @return                      SchematronCheckResult containing both schema and schematron errors.
     * @throws RuntimeException     If a specified or required file cannot be found;
     */
    public static SchematronCheckResult check(String templatePath, String schematronName, String XmlToCheck) {
        
        String schemaLocation = templatePath+CDA_XSD_REL_LOCATION;
        String schemaExtensionLocation = templatePath+EXTENSION_XSD_REL_LOCATION;
        String schematronPath = templatePath+SCHEMATRON_DIR+schematronName;
        String skeletonPath = templatePath+SCHEMATRON_SKELETON_REL_LOCATION;
        
        return doCheck(templatePath,schemaLocation,schemaExtensionLocation,schematronPath,skeletonPath,XmlToCheck);
    }
    
    /**
     * Perform a schema and schematron check assuming the default template layout 
     *  that is: 
     * <pre>
     * [templatePath]/cdar2/CDA.xsd
     * [templatePath]/cdar2/EXTENSION.xsd
     * [templatePath]/schematron/schematron-Validator-report.xsl
     * [templatePath]/schematron/[schematronName]
     * </pre>
     * 
     * @param templatePath          the path of the root Schematron template, for example "C:/mySchematron/e-Discharge Summary/"
     * @param schematronName        the location Schematron file to use for this test, relative to '[templatePath]/schematron' .
     *                              eg: "ccd-3B.sch" 
     * @param XmlToCheck            w3c Document XML file to check
     * @return                      SchematronCheckResult containing both schema and schematron errors.
     * @throws RuntimeException      If a specified or required file cannot be found;
     */
    public static SchematronCheckResult check(String templatePath, String schematronName, Document XmlToCheck) {
        
        String schemaLocation = templatePath+CDA_XSD_REL_LOCATION;
        String schemaExtensionLocation = templatePath+EXTENSION_XSD_REL_LOCATION;
        String schematronPath = templatePath+SCHEMATRON_DIR+schematronName;
        String skeletonPath = templatePath+SCHEMATRON_SKELETON_REL_LOCATION;
        
        return doCheck(templatePath,schemaLocation,schemaExtensionLocation,schematronPath,skeletonPath,XmlToCheck);
    }
    
    /**
     * Perform a schema and schematron check using the absolute paths of required files 
     *
     * @param templatePath              the path of the working directory for schematrons eg: "C:/mySchematron/e-Discharge Summary/schematron/" 
     * @param schemaLocation            the path of the CDA schema file eg  "C:/mySchematron/e-Discharge Summary/cdar2/CDA.xsd"
     * @param schemaExtensionLocation   the path of the CDA extension file eg  "C:/mySchematron/e-Discharge Summary/cdar2/EXTENSION.xsd"
     * @param schematronPath            the path of the schematron file eg: "C:/mySchematron/e-Discharge Summary/schematron/ccd-3B.sch"
     * @param skeletonPath              the path of the schematron skeleton file 'schematron-Validator-report.xsl' 
     * @param xmlDocumentToCheck        the path of the XML file to check
     * @return                          SchematronCheckResult containing both schema and schematron errors.
     * @throws RuntimeException         If a specified or required file cannot be found;
     */
    public static SchematronCheckResult check(String templatePath, String schemaLocation, String schemaExtensionLocation, String schematronPath, String skeletonPath, String xmlDocumentToCheck ) {
       
        //proxy to the underlying check incase of changes
        return doCheck(templatePath,schemaLocation,schemaExtensionLocation,schematronPath,skeletonPath,xmlDocumentToCheck);
    }
    
    
    /**
     * Alternative interface to Schematron.check() that takes an existing in-memory XML Document
     *
     * @param templatePath              the path of the working directory for schematrons eg: "C:/mySchematron/e-Discharge Summary/schematron/" 
     * @param schemaLocation            the path of the CDA schema file eg  "C:/mySchematron/e-Discharge Summary/cdar2/CDA.xsd"
     * @param schemaExtensionLocation   the path of the CDA extension file eg  "C:/mySchematron/e-Discharge Summary/cdar2/EXTENSION.xsd"
     * @param schematronPath            the path of the schematron file eg: "C:/mySchematron/e-Discharge Summary/schematron/ccd-3B.sch"
     * @param skeletonPath              the path of the schematron skeleton file 'schematron-Validator-report.xsl' 
     * @param xmlDocumentToCheck        w3c XML Document to check
     * @return                          SchematronCheckResult containing both schema and schematron errors.
     */
    public static SchematronCheckResult check(String templatePath, String schemaLocation, String schemaExtensionLocation, String schematronPath, String skeletonPath, Document xmlDocumentToCheck ) {
       
        //proxy to the underlying check in case of changes
        return doCheck(templatePath,schemaLocation,schemaExtensionLocation,schematronPath,skeletonPath,xmlDocumentToCheck);
    }
    

    private static final SchematronCheckResult doCheck( String templatePath, String schemaLocation, String schemaExtensionLocation, String schematronPath, String skeletonPath, String xmlDocumentToCheck ) {
        
        File schematron = new File(schematronPath);
        File skeleton = new File(skeletonPath);
        File schemaFile = new File(schemaLocation);
        
        SchemaCheckResult schemacheck = getDoc(xmlDocumentToCheck, schemaFile.getAbsolutePath());
        
        
        
        // step 1 compile the schematron against the skeleton
        if(! transformCache.containsKey( schematronPath )){
           Node schematronTransform = doTransform(schematron,skeleton);
           transformCache.put( schematronPath, schematronTransform );
        }
        
        Node schematronTransform  = transformCache.get( schematronPath );
        
        
        // step2 apply the schematron transform to the document to check
        Node result = doTransform( schemacheck.doc, schematronTransform, templatePath );
        List<SchematronError> schematronErrors = getSchematronErrors((Document) result);
        return new SchematronCheckResult( schematronErrors, schemacheck.error );
    }
    
   //alternative for existing XMLDocument 
   private static final SchematronCheckResult doCheck( String templatePath, String schemaLocation, String schemaExtensionLocation, String schematronPath, String skeletonPath, Document xmlDocumentToCheck )  {
        
        File schematron = new File(schematronPath);
        File skeleton = new File(skeletonPath);
        File schemaFile = new File(schemaLocation);
        
        SchemaCheckResult schemacheck = parseDoc(xmlDocumentToCheck, schemaFile.getAbsolutePath());
        
        
        
        // step 1 compile the schematron against the skeleton
        if(! transformCache.containsKey( schematronPath )){
           Node schematronTransform = doTransform(schematron,skeleton);
           transformCache.put( schematronPath, schematronTransform );
        }
        
        Node schematronTransform  = transformCache.get( schematronPath );
        
        
        // step2 apply the schematron transform to the document to check
        Node result = doTransform( schemacheck.doc, schematronTransform, templatePath );
        List<SchematronError> schematronErrors = getSchematronErrors((Document) result);
        return new SchematronCheckResult( schematronErrors, schemacheck.error );
    }
    

    private static Node doTransform(File originalXml, File transform)  {

        DOMResult result = new DOMResult();
        try {
            Source xmlSource = new StreamSource(originalXml);
            Source xsltSource = new StreamSource(transform);

            
            Transformer transformer = getFactory().newTransformer(xsltSource);
            transformer.transform(xmlSource, result);
            
        }catch(Exception e){
            throw new RuntimeException( e.getMessage() );
        }
        
        return result.getNode();
    }
   
   private static Node doTransform(Document originalXml, Node transform, final String relativePath)   {

       DOMResult result = new DOMResult();
       try {
           Source xmlSource = new DOMSource(originalXml);
           Source xsltSource = new DOMSource(transform);

           Transformer transformer = factory.newTransformer(xsltSource);
           
               URIResolver uriResolver = (href, base) -> {
                   Source s = new StreamSource(relativePath + File.separator + base + href);
                   return s;
               };
           
           transformer.setURIResolver( uriResolver );
           
           transformer.transform(xmlSource, result);
       } catch(Exception e){
           throw new RuntimeException( e.getMessage() );
       } 
       
       
       return result.getNode();
   }
   
   /**
    * Parse the results of schematron checking and collect a list of schematron errors
    * @param document
    * @return
    */
   private static List<SchematronError>  getSchematronErrors(Document document) {

       NodeList elementsByTagName = document.getElementsByTagName( "issue" );
      List<SchematronError> errors = new ArrayList<SchematronError>(); 

       for (int i = 0; i < elementsByTagName.getLength(); i++) {
           /* issue */
           Node n = elementsByTagName.item( i );
           Node message = n.getFirstChild();/* message */
           String messageText = message.getFirstChild().getNodeValue(); /* text */
           
           Node context = message.getNextSibling();
           String contextText = context.getFirstChild().getNodeValue();
           
           Node test = context.getNextSibling();
           String testText = test.getFirstChild().getNodeValue();
           errors.add( new SchematronError( messageText, contextText, testText ) );
       }
       return errors;
   }
   
   /**
    * Get a Document from and XML string.
    * 
    * @param xml       A string representation of an XML document
    * @param schema     The XSD Schema against which the Xml document will be validated
    * @return the Document and results of Schema validation
    */
   private static SchemaCheckResult getDoc(String xml, String schema) {
       DocumentBuilderFactory DocumentBuilderfactory = getDocumentBuilder( schema );
       DocumentBuilder builder = null;
       Document doc = null;
       
       CustomErrorHandler handler = new CustomErrorHandler();
       
        try {
            builder = DocumentBuilderfactory.newDocumentBuilder();
            builder.setErrorHandler( handler );
            doc = builder.parse( xml );
        } catch (Exception e) {
            throw new RuntimeException( e );
        }
           
       return  new SchemaCheckResult(doc, handler.error );
   }
   
   
   /**
    * Validate an XML Document 
    * 
    * @param xml       A string representation of an XML document
    * @param schema     The XSD Schema against which the XML document will be validated
    * @return the Document and results of Schema validation
    */
   private static SchemaCheckResult parseDoc(Document xml, String schemaFile) {
       
       SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
       CustomErrorHandler handler = new CustomErrorHandler();
      
        try {
            
            Schema schema = sf.newSchema( new File( schemaFile ) );
            Validator validator = schema.newValidator();
            
            ValidatorHandler vh = schema.newValidatorHandler();
            vh.setErrorHandler( handler );
            
            validator.setErrorHandler( handler );
            //choose the validator that doesnt throw sax exception
            validator.validate( new DOMSource(xml)  , null );
            
        } catch (SAXException e) {
            throw new RuntimeException( e );
            //this is expected for bad documents
            //the handler will report errors
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
      
           
       return  new SchemaCheckResult(xml, handler.error );
   }

    private static DocumentBuilderFactory getDocumentBuilder( String schema ) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
           documentBuilderFactory.setNamespaceAware(true);
           documentBuilderFactory.setValidating(true);
           documentBuilderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",XMLConstants.W3C_XML_SCHEMA_NS_URI);
           
           documentBuilderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", schema);
           documentBuilderFactory.setIgnoringElementContentWhitespace(true);
        return documentBuilderFactory;
    }
   
  
   
}

//A local transfer obejct
//just for use in this class
class SchemaCheckResult{
    public final List<SchemaError> error;
    public final Document doc;
    
    public SchemaCheckResult(Document doc,  List<SchemaError> error ){
        this.doc=doc;
        this.error=error;
    }
    
}

//A simple SAX error handler for saving Schema errors
class CustomErrorHandler  implements ErrorHandler {

    List<SchemaError> error   = new ArrayList<SchemaError>();
    
    @Override
    public void warning( SAXParseException e ) throws SAXException {
        error.add( new SchemaError(Integer.toString( e.getLineNumber()), Integer.toString(e.getColumnNumber()), e.getLocalizedMessage() )  );
    }

    @Override
    public void error( SAXParseException e ) throws SAXException {
        error.add( new SchemaError(Integer.toString( e.getLineNumber()), Integer.toString(e.getColumnNumber()), e.getLocalizedMessage() )  );
    }

    @Override
    public void fatalError( SAXParseException e ) throws SAXException {
        error.add(  new SchemaError(Integer.toString( e.getLineNumber()), Integer.toString(e.getColumnNumber()), e.getLocalizedMessage() ) );
    }


    
}
