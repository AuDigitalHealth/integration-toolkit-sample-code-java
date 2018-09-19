package au.gov.nehta.builder.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.gov.nehta.model.cda.common.address.PostalAddress;
import au.gov.nehta.model.cda.common.custodian.CustodianOrganization;
import au.gov.nehta.model.cda.common.id.AssignedEntity;
import au.gov.nehta.model.cda.common.org.Organization;
import au.gov.nehta.model.cda.common.person.Person;
import au.gov.nehta.model.cda.common.telecom.Telecom;
import au.gov.nehta.model.clinical.common.participation.PersonName;
import au.gov.nehta.model.clinical.common.types.UniqueIdentifier;
import au.net.electronichealth.ns.cda._2_0.AD;
import au.net.electronichealth.ns.cda._2_0.II;
import au.net.electronichealth.ns.cda._2_0.POCDMT000040AssignedCustodian;
import au.net.electronichealth.ns.cda._2_0.POCDMT000040AssignedEntity;
import au.net.electronichealth.ns.cda._2_0.POCDMT000040CustodianOrganization;
import au.net.electronichealth.ns.cda._2_0.POCDMT000040Organization;
import au.net.electronichealth.ns.cda._2_0.POCDMT000040Person;
import au.net.electronichealth.ns.cda._2_0.TEL;

public class CDAModelConverter {
	public static POCDMT000040AssignedEntity convertAssignedEntity( AssignedEntity cdaModelAssignedEntity ) {
		POCDMT000040AssignedEntity assignedEntity = new POCDMT000040AssignedEntity();
		assignedEntity.getId().add( CDATypeUtil.getII( cdaModelAssignedEntity.getId() ) );
		
		if(cdaModelAssignedEntity.getCode() != null){
		    assignedEntity.setCode( Converter.convertToCECode( cdaModelAssignedEntity.getCode() ) );
		}
		
		if(cdaModelAssignedEntity.getAssignedPerson() != null ){
			assignedEntity.setAssignedPerson( convertPerson( cdaModelAssignedEntity.getAssignedPerson() ) );
		}
		if(cdaModelAssignedEntity.getAddress() != null){
			assignedEntity.getAddr().addAll( convertAssignedEntityAddress( cdaModelAssignedEntity.getAddress() ) );
		}
		if(cdaModelAssignedEntity.getTelecom() != null){
			assignedEntity.getTelecom().addAll( convertTelecom( cdaModelAssignedEntity.getTelecom() ) );
		}
		if( cdaModelAssignedEntity.getRepresentedOrganization() != null){
			assignedEntity.setRepresentedOrganization( convertOrganization( cdaModelAssignedEntity.getRepresentedOrganization() ) );
		}

		return assignedEntity;
	}

	public static POCDMT000040Person convertPerson( Person cdaModelPerson ) {
		POCDMT000040Person assignedPerson = new POCDMT000040Person();
		assignedPerson.getAsEntityIdentifier().addAll( Arrays.asList( Converter.convert( cdaModelPerson.getAsEntityIdentifier() ) ) );

		for (PersonName n : cdaModelPerson.getName()) {
			assignedPerson.getName().add( Converter.getPersonName( n ) );
		}

		return assignedPerson;
	}

	public static List<AD> convertAssignedEntityAddress( List<PostalAddress> cdaModelAddress ) {
		List<AD> address = new ArrayList<AD>();

		for (PostalAddress cdaModelPostalAddress : cdaModelAddress) {
			address.add( Converter.getAddress( cdaModelPostalAddress ) );
		}

		return address;
	}

	public static List<TEL> convertTelecom( List<Telecom> cdaModelLegalAuthenticatorAssignedEntityTelecom ) {
		List<TEL> telecom = new ArrayList<TEL>();
		for (Telecom cdaModelTelecom : cdaModelLegalAuthenticatorAssignedEntityTelecom) {
			telecom.add( Converter.convert( cdaModelTelecom ) );
		}

		return telecom;
	}

	public static POCDMT000040Organization convertOrganization( Organization cdaModelOrganisation ) {
		POCDMT000040Organization organization = new POCDMT000040Organization();
		organization.getAsEntityIdentifier().add( Converter.convert( cdaModelOrganisation.getAsEntityIdentifier() ) );
		
		if(cdaModelOrganisation.getName() != null){
		    organization.getName().add( Converter.getOrganizationNameAndUse( cdaModelOrganisation.getName() ) );
		}
		return organization;
	}

	public static POCDMT000040AssignedCustodian convertAssignedCustodian( CustodianOrganization custodianOrg ) {
		POCDMT000040AssignedCustodian assignedCustodian = new POCDMT000040AssignedCustodian();
		POCDMT000040CustodianOrganization assignedCustodianOrganization = new POCDMT000040CustodianOrganization();
		assignedCustodianOrganization.getId().addAll( getCustodianAssignedCustodianRepresentedOrganizationId( custodianOrg ) );
		
		if( custodianOrg.getAsEntityIdentifier() != null){
		    assignedCustodianOrganization.getAsEntityIdentifier().add( Converter.convert( custodianOrg.getAsEntityIdentifier() ) );
		}
		
		if(custodianOrg.getName() != null){
		    assignedCustodianOrganization.setName( Converter.getOrganizationName( custodianOrg.getName() ) );
		}
		if(custodianOrg.getTelecom() != null){
		    assignedCustodianOrganization.setTelecom( Converter.convert( custodianOrg.getTelecom() ) );
		}
		
		if(custodianOrg.getAddress() != null){
		    assignedCustodianOrganization.setAddr( Converter.getAddress( custodianOrg.getAddress() ) );
		}
		
		if(assignedCustodianOrganization != null){
		    assignedCustodian.setRepresentedCustodianOrganization( assignedCustodianOrganization );
		}
		return assignedCustodian;
	}

	public static List<II> getCustodianAssignedCustodianRepresentedOrganizationId( CustodianOrganization org ) {
		List<II> id = new ArrayList<II>();

		for (UniqueIdentifier Id : org.getIds()) {
			
			id.add( Converter.getII(Id) );
		}

		return id;
	}
}