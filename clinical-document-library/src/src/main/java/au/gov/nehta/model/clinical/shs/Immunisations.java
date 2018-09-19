package au.gov.nehta.model.clinical.shs;

import java.util.List;

/**
 * Information about the immunisation history of the subject of care.
 *
 */
public interface Immunisations extends ExclusionStatement {

	/**
	 * The act of administering a dose of a vaccine to a person for the purpose
	 * of preventing or minimising the effects of a disease by producing
	 * immunity and/or to counter the effects of an infectious organism or
	 * insult.
	 */
	public List<Immunisation> getAdministeredImmunisations();

}
