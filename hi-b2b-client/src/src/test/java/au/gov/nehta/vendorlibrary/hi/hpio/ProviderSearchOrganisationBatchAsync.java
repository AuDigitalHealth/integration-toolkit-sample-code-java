package au.gov.nehta.vendorlibrary.hi.hpio;

import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.MEDICARE_ENDPOINT_URL;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getProductHeader;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getSigningCertificateKeyForMedicare;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getSigningPrivateKeyForMedicare;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getSslSocketFactoryForMedicare;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getUserQualifiedId;
import static au.gov.nehta.vendorlibrary.hi.test.utils.TestConstants.getWrappedProductHeader;

import java.util.UUID;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.test.AssertThrows;

import au.net.electronichealth.ns.hi.svc.providerbatchasyncsearchforproviderorganisation._5_1.RetrieveSearchForProviderOrganisation;
import au.net.electronichealth.ns.hi.svc.providerbatchasyncsearchforproviderorganisation._5_1.RetrieveSearchForProviderOrganisationResponse;
import au.net.electronichealth.ns.hi.svc.providerbatchasyncsearchforproviderorganisation._5_1.StandardErrorMsg;
import au.net.electronichealth.ns.hi.svc.providerbatchasyncsearchforproviderorganisation._5_1.SubmitSearchForProviderOrganisationResponse;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.SeverityType;
import au.net.electronichealth.ns.hi.xsd.providercore.organisationdetails._5_1.BatchSearchForProviderOrganisationCriteriaType;
import au.net.electronichealth.ns.hi.xsd.providermessages.searchorganisation._5_0.SearchForProviderOrganisation;
import static au.gov.nehta.vendorlibrary.hi.test.utils.HPIOHPIITestConstants.*;

public class ProviderSearchOrganisationBatchAsync {

	SearchForProviderOrganisationBatchAsyncClient client =  getMedicareTestClient();
	SearchForProviderOrganisationBatchAsyncClient clientNoID =  getMedicareTestClientNoId();
	
	@Test
	public void testHPIOBatchSingle() throws StandardErrorMsg{
		ProviderOrganisationBatchSearch request = new ProviderOrganisationBatchSearch();
		BatchSearchForProviderOrganisationCriteriaType idSearch = new BatchSearchForProviderOrganisationCriteriaType();
		idSearch.setRequestIdentifier(UUID.randomUUID().toString());
		SearchForProviderOrganisation hpioSearch = new SearchForProviderOrganisation();
		hpioSearch.setHpioNumber(MCA_HPIO);
		idSearch.setSearchForProviderOrganisation(hpioSearch );
		request.identifierSearch(idSearch );
		SubmitSearchForProviderOrganisationResponse response = client.submitSearch(request);
		Assert.assertTrue(response.getSubmitSearchForProviderOrganisationResult().getServiceMessages().getHighestSeverity().equals(SeverityType.INFORMATIONAL));
	}
	
	@Test
	public void testRetrieveHPIOBatchSingle() throws StandardErrorMsg{
		//do a batch search
		ProviderOrganisationBatchSearch request = new ProviderOrganisationBatchSearch();
		BatchSearchForProviderOrganisationCriteriaType idSearch = new BatchSearchForProviderOrganisationCriteriaType();
		idSearch.setRequestIdentifier(UUID.randomUUID().toString());
		SearchForProviderOrganisation hpioSearch = new SearchForProviderOrganisation();
		hpioSearch.setHpioNumber(MCA_HPIO);
		idSearch.setSearchForProviderOrganisation(hpioSearch );
		request.identifierSearch(idSearch );
		SubmitSearchForProviderOrganisationResponse response = client.submitSearch(request);
		Assert.assertTrue(response.getSubmitSearchForProviderOrganisationResult().getServiceMessages().getHighestSeverity().equals(SeverityType.INFORMATIONAL));
	
		
		RetrieveSearchForProviderOrganisation requestSearch = new RetrieveSearchForProviderOrganisation();
		requestSearch.setBatchIdentifier(response.getSubmitSearchForProviderOrganisationResult().getBatchIdentifier());
		RetrieveSearchForProviderOrganisationResponse retrieveSearchResponse = client.retrieveSearch(requestSearch );
		Assert.assertTrue(retrieveSearchResponse.getRetrieveSearchForProviderOrganisationResult().getBatchSearchForProviderOrganisationResult().size() ==1);
		Assert.assertTrue(retrieveSearchResponse.getRetrieveSearchForProviderOrganisationResult().getBatchSearchForProviderOrganisationResult().get(0).getSearchForProviderOrganisationResult().getHpioNumber().equals(MCA_HPIO));
		
	}
	
	
	protected SearchForProviderOrganisationBatchAsyncClient getMedicareTestClient()  {
		try{
		return new SearchForProviderOrganisationBatchAsyncClient(MEDICARE_ENDPOINT_URL, getUserQualifiedId(), getProductHeader(), getSigningPrivateKeyForMedicare(),
				getSigningCertificateKeyForMedicare(), getSslSocketFactoryForMedicare());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	protected SearchForProviderOrganisationBatchAsyncClient getMedicareTestClientNoId()  {
		try{
		return new SearchForProviderOrganisationBatchAsyncClient(MEDICARE_ENDPOINT_URL,  getWrappedProductHeader(), getSigningPrivateKeyForMedicare(),
				getSigningCertificateKeyForMedicare(), getSslSocketFactoryForMedicare(),null);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
