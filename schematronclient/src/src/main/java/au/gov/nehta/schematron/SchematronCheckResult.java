package au.gov.nehta.schematron;

import java.util.List;

/**
 * A Class than encapsulates Schema errors and Schematron errors, if any
 * 
 * @author NeHTA
 */
public class SchematronCheckResult {
    public final  List<SchematronError> schematronErrors;  
    public final List<SchemaError> schemaErrors;
    
    public SchematronCheckResult( List<SchematronError> schematronErrors, List<SchemaError> schemaErrors){
        this.schematronErrors=schematronErrors;
        this.schemaErrors=schemaErrors;
    }
}
