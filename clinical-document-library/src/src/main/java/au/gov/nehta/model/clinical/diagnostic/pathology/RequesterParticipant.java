package au.gov.nehta.model.clinical.diagnostic.pathology;

import java.util.List;

import au.gov.nehta.model.cda.common.id.AsEntityIdentifier;
import au.gov.nehta.model.clinical.common.types.HPII;
import au.gov.nehta.model.clinical.etp.common.participation.Entitlement;
import au.gov.nehta.model.clinical.etp.common.participation.PathologyOrganisationParticipant;
import au.gov.nehta.model.clinical.etp.common.participation.ProviderPerson;
import au.gov.nehta.model.clinical.etp.common.participation.QualifiedParticipant;

public interface RequesterParticipant extends PathologyOrganisationParticipant,QualifiedParticipant {
	public HPII getEntityIdentifiers();
    public List<? extends AsEntityIdentifier>  getOrganisationIdentifiers();
    
	public ProviderPerson getPerson();

	public List<Entitlement> getEntitlement();

	public void setEntitlement( List<Entitlement> entitlement );

	public void addEntitlement( Entitlement entitlement );

  
	
	

}
