package au.gov.nehta.model.clinical.diagnostic.imaging;

import au.gov.nehta.model.cda.common.code.SNOMED_AU_Code;

/**
 * Details about the anatomical locations
 *
 */
public interface AnatomicalSite {

    
    /** The name of the anatomical location. */
    public SNOMED_AU_Code getSpecificLocationName();
    
    /** The laterality of the anatomical location. */
    public SNOMED_AU_Code getSpecificLocationSide();
    
    /**
     * Description of the anatomical location.
     */
    public String getDescription();
}
