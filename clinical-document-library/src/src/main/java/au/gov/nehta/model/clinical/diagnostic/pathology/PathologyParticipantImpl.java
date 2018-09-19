package au.gov.nehta.model.clinical.diagnostic.pathology;

import java.util.ArrayList;
import java.util.List;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.model.cda.common.telecom.Telecom;
import au.gov.nehta.model.cda.common.telecom.TelecomMedium;
import au.gov.nehta.model.clinical.common.participation.AddressPurpose;
import au.gov.nehta.model.clinical.common.types.HPII;
import au.gov.nehta.model.clinical.etp.common.participation.Entitlement;
import au.gov.nehta.model.clinical.etp.common.participation.ProviderAddress;
import au.gov.nehta.model.clinical.etp.common.participation.ProviderPerson;
import au.gov.nehta.model.clinical.etp.common.participation.QualifiedParticipantImpl;

public class PathologyParticipantImpl extends QualifiedParticipantImpl implements AuthorParticipant {
	private HPII entityIdentifier;
    private ProviderPerson person;
    private List<Entitlement> entitlements;
    private EmploymentOrganisation employment;
  
    
	public PathologyParticipantImpl( HPII entityIdentifier, ProviderPerson person,EmploymentOrganisation employment,  ProviderAddress address, List<Telecom> electronicCommunicationDetail) {
		ArgumentUtils.checkNotNull( entityIdentifier, "entityIdentifier" );
		ArgumentUtils.checkNotNull( address, "address" );
		ArgumentUtils.checkNotNullNorEmpty( electronicCommunicationDetail, "electronicCommunicationDetail" );
		ArgumentUtils.checkNotNull( person, "person" );
		ArgumentUtils.checkNotNull( employment, "EmploymentOrganisation" );

		if (!address.getAddressPurpose().equals( AddressPurpose.BUSINESS )) {
			throw new IllegalArgumentException( "address must have an address purpose of BUSINESS" );
		}

		boolean containsTelephoneOrMobile = false;

	 
		for (Telecom electronicCommunicationDetailItem : electronicCommunicationDetail) {
			if (containsTelephoneOrMobile) {
				break;
			} else {
				TelecomMedium medium = electronicCommunicationDetailItem.getTelecomMedium();
				if (medium.equals( TelecomMedium.TELEPHONE ) || medium.equals( TelecomMedium.MOBILE )) {
					containsTelephoneOrMobile = true;
				}
			}
		}
		 
		
		if (containsTelephoneOrMobile == false) {
			throw new IllegalArgumentException( "electronicCommunicationDetail list must contain at least one item with an Electronic Communication Medium value of 'Telephone' or 'Mobile'" );
		}

		this.entityIdentifier = entityIdentifier;
		this.address = address;
		this.electronicCommunicationDetail = electronicCommunicationDetail;
		this.person=person;
		this.employment=employment;
	}

 
	
	public HPII getEntityIdentifiers() {
		return entityIdentifier;
	}


    @Override
    public ProviderPerson getPerson() {
        return person;
    }
    
    public List<Entitlement> getEntitlement() {
        return entitlements;
    }
    

    public void setEntitlement( List<Entitlement> entitlement ) {
        ArgumentUtils.checkNotNull( entitlement, "entitlement" );
        this.entitlements = entitlement;
    }

    public void addEntitlement( Entitlement entitlement ) {
        ArgumentUtils.checkNotNull( entitlement, "entitlement" );
        if (this.entitlements == null) {
            this.entitlements = new ArrayList<Entitlement>();
        }
        this.entitlements.add( entitlement );
    }



    public EmploymentOrganisation getEmploymentOrganisation() {
        return employment;
    }
    
    
}
