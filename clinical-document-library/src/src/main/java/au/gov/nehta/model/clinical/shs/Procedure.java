package au.gov.nehta.model.clinical.shs;

import au.gov.nehta.model.cda.common.code.Coded;
import au.gov.nehta.model.cda.common.time.PreciseDate;
import au.gov.nehta.model.clinical.common.types.UniqueIdentifier;

/**
 * A clinical activity carried out for therapeutic, evaluative,
 * investigative, screening or diagnostic purposes.
 */
public interface Procedure {

	/**
	 * The name of the procedure (to be) performed.
	 */
	public Coded getName();
	
	/**
	 * optional 
	 * Additional narrative about the procedure not captured in other fields.
	 */
	public String getComment();
	
	/**
	 * optional 
	 * The start date and/or time for the procedure.
	 */
	public PreciseDate getDateTimeStarted();
	
	/**
	 * This is a technical identifier that is used for system purposes such as
	 * matching. If a suitable internal key is not available, a UUID may be
	 * used.
	 */
	public UniqueIdentifier getID();
}
