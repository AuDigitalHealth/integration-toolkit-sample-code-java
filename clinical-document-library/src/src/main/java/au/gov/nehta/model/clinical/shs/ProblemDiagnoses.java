package au.gov.nehta.model.clinical.shs;

import java.util.List;


/**
 * The problems and/or diagnoses that form part of the current and past
 * medical history of the subject of care.
 */
public interface ProblemDiagnoses extends ExclusionStatement {
	
	public List<ProblemDiagnosis> getDiagnoses();
	
}
