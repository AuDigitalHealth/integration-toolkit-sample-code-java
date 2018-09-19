package au.gov.nehta.cda.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class TestHelper {
    /**
     * print the string to a file
     */
    public static final void printToFile( String s,  String fileName) {
        FileOutputStream fs = null;
       
        try {
            fs = new FileOutputStream( new File(fileName) );
            fs.write( s.getBytes() );
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Convert a Document to a String
     */
    public static final String documentToXML( Document clinicalDocument ) {
        String cdaXml="";
        try {
            
            TransformerFactory factory = TransformerFactory.newInstance();
            //factory.setAttribute( "indent-number", new Integer( 4 ) );
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty( OutputKeys.OMIT_XML_DECLARATION, "no" );
            transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
            
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult( writer );
            
            DOMSource domSource = new DOMSource( clinicalDocument );
            transformer.transform( domSource, result );
            
            writer.flush();
            writer.close();
            cdaXml = writer.toString();
        
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return cdaXml;
    }
}
