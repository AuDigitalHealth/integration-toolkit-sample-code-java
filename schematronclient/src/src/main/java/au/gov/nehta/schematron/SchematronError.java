package au.gov.nehta.schematron;

/**
 * A Simple transfer object to encapsulate Schematron errors
 * 
 * @author NeHTA
 *
 */
public class SchematronError {

    public final String message;
    public final String context;
    public final String test;
    
    public SchematronError(String message, String context, String test){
        this.message = message;
        this.context=context;
        this.test=test;
    }
    
    @Override
    public String toString() {
        return message;
    }
}
