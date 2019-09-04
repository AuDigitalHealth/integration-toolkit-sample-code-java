package au.gov.nehta.schematron;

/**
 * A Simple transfer object to encapsulate Schema errors
 * 
 * @author NeHTA
 *
 */
public class SchemaError {

    public final String line;
    public final String position;
    public final String message;
    
    public SchemaError(String line, String position, String message){
        this.line=line;
        this.position=position;
        this.message=message;
    }
    
    @Override
    public String toString() {
        return message;
    }
}
