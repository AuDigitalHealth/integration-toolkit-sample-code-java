package au.gov.nehta.model.clinical.etp.common.participation;

import au.gov.nehta.model.cda.common.id.AsEntityIdentifier;
import au.gov.nehta.model.cda.common.time.RestrictedTimeInterval;

public interface Entitlement {
    public AsEntityIdentifier getEntitlementNumber();

    public EntitlementType getEntitlementType();

    public RestrictedTimeInterval getEntitlementValidityDuration();

    public void setEntitlementValidityDuration( RestrictedTimeInterval entitlementValidityDuration );
}
