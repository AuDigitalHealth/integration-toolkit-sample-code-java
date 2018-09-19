package au.gov.nehta.model.clinical.etp.common.participation;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.model.cda.common.id.AsEntityIdentifier;
import au.gov.nehta.model.cda.common.time.RestrictedTimeInterval;

public class EntitlementImpl implements Entitlement {
	private AsEntityIdentifier entitlementNumber;
	private EntitlementType entitlementType;
	private RestrictedTimeInterval entitlementValidityDuration;

	public EntitlementImpl( AsEntityIdentifier entitlementNumber, EntitlementType entitlementType ) {
		ArgumentUtils.checkNotNull( entitlementNumber, "entitlementNumber" );
		ArgumentUtils.checkNotNull( entitlementType, "entitlementType" );
		this.entitlementNumber = entitlementNumber;
		this.entitlementType = entitlementType;
	}

	public AsEntityIdentifier getEntitlementNumber() {
		return entitlementNumber;
	}

	public EntitlementType getEntitlementType() {
		return entitlementType;
	}

	public RestrictedTimeInterval getEntitlementValidityDuration() {
		return entitlementValidityDuration;
	}

	public void setEntitlementValidityDuration( RestrictedTimeInterval duration ) {
		ArgumentUtils.checkNotNull( duration, "entitlementValidityDuration" );
		
		if((duration.getLow() != null && duration.getHigh() != null)
		        && duration.getLow().getDateTime().isAfter(duration.getHigh().getDateTime()) ){
		    throw new IllegalArgumentException("EntitlementImpl,  EntitlementValidityDuration: low value must be less than high value");
		}
		
		this.entitlementValidityDuration = duration;
	}
}
