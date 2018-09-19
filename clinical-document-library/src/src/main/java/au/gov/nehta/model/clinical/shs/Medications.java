package au.gov.nehta.model.clinical.shs;

import java.util.List;

/*
 * Medicine which the subject of care is using, this includes
 * self-prescribed, clinician prescribed and nonprescription medicines.
 */
public interface Medications  extends ExclusionStatement {
	
	/**
	 * Medicines which the subject of care is using, this includes
	 * self-prescribed, clinician prescribed and nonprescription medicines.
	 */
	public List<KnownMedication> getKnownMedications();
}
