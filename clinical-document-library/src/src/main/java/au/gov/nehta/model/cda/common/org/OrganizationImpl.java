package au.gov.nehta.model.cda.common.org;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.model.cda.common.id.AsEntityIdentifier;

public class OrganizationImpl implements Organization {
	private AsEntityIdentifier asEntityIdentifier;
	private String organizationName;

	
	
	private OrganizationImpl( AsEntityIdentifier asEntityIdentifier, String organizationName ) {
		this.asEntityIdentifier = asEntityIdentifier;
		this.organizationName = organizationName;
	}

	private OrganizationImpl( AsEntityIdentifier asEntityIdentifier ) {
        this.asEntityIdentifier = asEntityIdentifier;
    }

    public static Organization getInstance( AsEntityIdentifier asEntityIdentifier, String organizationName ) {
		ArgumentUtils.checkNotNull( asEntityIdentifier, "Organsiation asEntityIdentifier" );
		ArgumentUtils.checkNotNull( asEntityIdentifier, "Organsiation name" );
		return (Organization) new OrganizationImpl( asEntityIdentifier, organizationName );
	}
	
	public static Organization getInstance( AsEntityIdentifier asEntityIdentifier ) {
        ArgumentUtils.checkNotNull( asEntityIdentifier, "Organsiation asEntityIdentifier" );
        return (Organization) new OrganizationImpl( asEntityIdentifier );
    }


	public AsEntityIdentifier getAsEntityIdentifier() {
		return asEntityIdentifier;
	}

	public String getName() {
		return organizationName;
	}
}
