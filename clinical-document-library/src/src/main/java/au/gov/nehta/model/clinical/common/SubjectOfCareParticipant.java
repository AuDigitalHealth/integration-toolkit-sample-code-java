package au.gov.nehta.model.clinical.common;

import java.util.List;

import au.gov.nehta.model.cda.common.id.AsEntityIdentifier;
import au.gov.nehta.model.cda.common.telecom.Telecom;
import au.gov.nehta.model.clinical.etp.common.participation.AddressContext;
import au.gov.nehta.model.clinical.etp.common.participation.Entitlement;


/**
 * 
 * This interface is designed to model a generic Subject Of Care and be
 * reusable across CDA Documents.
 * 
 * Individual document types may impose stricter constraints
 * than this class, itself, imposes. For example, allow only one address. 
 * 
 * .<br><br>
 * IMPORTANT: <br>
 * 
 * <strong>
 *  To ensure that a valid subject of care is
 * created, run the CDA creator with .useStrict() when testing
 * </strong>
 * 
 */
public interface SubjectOfCareParticipant {
    public void setPatientRoleId(String id);
    public String getPatientRoleId();
    
    public List<? extends AsEntityIdentifier> getEntityIdentifiers();

    public List<AddressContext> getAddresses();

    public List<Telecom> getElectronicCommunicationDetail();

    public SubjectOfCarePerson getPerson();

    public List<Entitlement> getEntitlements();

    public void setElectronicCommunicationDetails( List<Telecom> electronicCommunicationDetail );

    public void addElectronicCommunicationDetail( Telecom electronicCommunicationDetail );

    public void setEntitlements( List<Entitlement> entitlement );

    public void addEntitlement( Entitlement entitlement );
}
