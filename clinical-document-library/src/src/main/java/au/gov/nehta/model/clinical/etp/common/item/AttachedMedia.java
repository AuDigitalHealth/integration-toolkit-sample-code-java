package au.gov.nehta.model.clinical.etp.common.item;

import java.io.File;

import javax.activation.MimetypesFileTypeMap;

import au.gov.nehta.common.utils.ArgumentUtils;

public class AttachedMedia {
    private String fileName;
    private String mediaType;
    private byte[] integrityCheck;
    private IntegerityCheckType integrityCheckType;
    
    
   public AttachedMedia(File file) {  
        this(file, IntegerityCheckType.SHA_1);
    }
    
    public AttachedMedia(File file, IntegerityCheckType checkType) {
        ArgumentUtils.checkNotNull( file, "logo file" );
        integrityCheck = checkType.check( file );
        integrityCheckType=checkType;
        
         mediaType = new MimetypesFileTypeMap().getContentType(file);
        fileName=file.getName();
    }
    
    
    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return the mediaType
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * @return the integrityCheck
     */
    public byte[] getIntegrityCheck() {
        return integrityCheck.clone();
    }
    
    /**
     * @return the integrityCheck
     */
    public IntegerityCheckType getIntegrityCheckType()  {
        return integrityCheckType;
    }
}
