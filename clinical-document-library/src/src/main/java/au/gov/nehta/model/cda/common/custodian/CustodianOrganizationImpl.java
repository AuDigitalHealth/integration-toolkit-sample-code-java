package au.gov.nehta.model.cda.common.custodian;

import java.util.Arrays;
import java.util.List;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.model.cda.common.address.PostalAddress;
import au.gov.nehta.model.cda.common.id.AsEntityIdentifier;
import au.gov.nehta.model.cda.common.org.OrganizationName;
import au.gov.nehta.model.cda.common.telecom.Telecom;
import au.gov.nehta.model.clinical.common.types.UniqueIdentifier;

public class CustodianOrganizationImpl implements CustodianOrganization {
	private List<UniqueIdentifier> id;
	private AsEntityIdentifier asEntityIdentifier;
	private OrganizationName name;
	private Telecom telecom;
	private PostalAddress address;

	private CustodianOrganizationImpl( List<UniqueIdentifier> custodianOrganizationIds, AsEntityIdentifier asEntityIdentifier ) {
		this.id = custodianOrganizationIds;
		this.asEntityIdentifier = asEntityIdentifier;
	}

	public static CustodianOrganizationImpl getInstance( List<UniqueIdentifier> custodianOrganizationIds, AsEntityIdentifier asEntityIdentifier ) {
		ArgumentUtils.checkNotNullNorEmpty( custodianOrganizationIds, "CustodianOrganization id" );
		ArgumentUtils.checkNotNull( asEntityIdentifier, "CustodianOrganization entityIdentifier" );

		return new CustodianOrganizationImpl( custodianOrganizationIds, asEntityIdentifier );
	}
	
	/**
	 * The minimum required for a custodian
	 */
    public static CustodianOrganizationImpl getInstance(  UniqueIdentifier  custodianOrganizationId) {
        ArgumentUtils.checkNotNull( custodianOrganizationId, "CustodianOrganization id" );

        return new CustodianOrganizationImpl( Arrays.asList(custodianOrganizationId), null );
    }

	public List<UniqueIdentifier> getIds() {
		return id;
	}

	public AsEntityIdentifier getAsEntityIdentifier() {
		return asEntityIdentifier;
	}

	public OrganizationName getName() {
		return name;
	}

	public Telecom getTelecom() {
		return telecom;
	}

	public PostalAddress getAddress() {
		return address;
	}

	public void addId( UniqueIdentifier id ) {
		this.id.add( id );
	}

	public void setAsEntityIdentifier( AsEntityIdentifier asEntityIdentifier ) {
		this.asEntityIdentifier = asEntityIdentifier;
	}

	public void setName( OrganizationName name ) {
		this.name = name;
	}

	public void setTelecom( Telecom telecom ) {
		this.telecom = telecom;
	}

	public void setAddress( PostalAddress address ) {
		this.address = address;
	}

}
