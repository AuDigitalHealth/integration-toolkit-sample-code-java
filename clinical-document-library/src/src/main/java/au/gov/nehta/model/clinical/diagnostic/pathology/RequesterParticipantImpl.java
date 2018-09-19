package au.gov.nehta.model.clinical.diagnostic.pathology;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.model.cda.common.code.Coded;
import au.gov.nehta.model.cda.common.id.AsEntityIdentifier;
import au.gov.nehta.model.clinical.common.participation.Organisation;
import au.gov.nehta.model.clinical.common.types.HPII;
import au.gov.nehta.model.clinical.common.types.UniqueIdentifier;
import au.gov.nehta.model.clinical.etp.common.participation.Entitlement;
import au.gov.nehta.model.clinical.etp.common.participation.PathologyOrganisationParticipantImpl;
import au.gov.nehta.model.clinical.etp.common.participation.ProviderPerson;

public class RequesterParticipantImpl extends PathologyOrganisationParticipantImpl implements RequesterParticipant {
	private HPII entityIdentifier;
	private List<? extends AsEntityIdentifier> organisationIdentifiers;
	private ProviderPerson person;
	private List<Entitlement> entitlement;
	private String qualifications;
	private String id = UUID.randomUUID().toString(); 
	
	
	public RequesterParticipantImpl( HPII personIdentifier, List<? extends AsEntityIdentifier> organisationIdentifiers, Organisation org, ProviderPerson person ) {
		ArgumentUtils.checkNotNull( personIdentifier, "entityIdentifier" );
		ArgumentUtils.checkNotNull( person, "person" );
	    ArgumentUtils.checkNotNull( org, "organisation" );
	    ArgumentUtils.checkNotNullNorEmpty( organisationIdentifiers, "organisationIdentifiers" );
	    
		this.entityIdentifier = personIdentifier;
		this.person = person;
		this.organisationIdentifiers=organisationIdentifiers;
		this.organisation=org;
	}
	
	@Override
	public HPII getEntityIdentifiers() {
	    return entityIdentifier;
	}

	public ProviderPerson getPerson() {
		return person;
	}

	public List<Entitlement> getEntitlement() {
		return entitlement;
	}

	public String getQualifications() {
		return qualifications;
	}

	public void setEntitlement( List<Entitlement> entitlement ) {
		ArgumentUtils.checkNotNull( entitlement, "entitlement" );
		this.entitlement = entitlement;
	}

	public void addEntitlement( Entitlement entitlement ) {
		ArgumentUtils.checkNotNull( entitlement, "entitlement" );
		if (this.entitlement == null) {
			this.entitlement = new ArrayList<Entitlement>();
		}
		this.entitlement.add( entitlement );
	}


	public void setQualifications( String qualifications ) {
		ArgumentUtils.checkNotNull( qualifications, "qualifications" );
		this.qualifications = qualifications;
	}

    @Override
    public UniqueIdentifier getRoleId() {
        return null;
    }

    @Override
    public Coded getHealthcareFacilityType() {
        return null;
    }

    @Override
    public List<? extends AsEntityIdentifier>  getOrganisationIdentifiers() {
        return organisationIdentifiers;
    }
    
    public void setOrganisation(Organisation org) {
        this.organisation=org;
    }

    /**
     * Retuurns a random UUID unless explicitly set
     */
	@Override
	public String getAssignedAuthorId() {
		return id;
	}

	@Override
	public void setAssignedAuthorId(String id) {
		this.id=id;
	}

   
}
