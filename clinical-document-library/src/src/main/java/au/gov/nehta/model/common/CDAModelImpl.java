package au.gov.nehta.model.common;

import au.gov.nehta.model.cda.common.custodian.Custodian;
import au.gov.nehta.model.cda.common.document.BaseClinicalDocument;
import au.gov.nehta.model.cda.common.id.LegalAuthenticator;

public class CDAModelImpl implements CDAModel {

    protected BaseClinicalDocument clinicalDocument;
    protected LegalAuthenticator legalAuthenticator;
    protected Custodian custodian;

    public CDAModelImpl() {  }
    
    public CDAModelImpl(BaseClinicalDocument clinicalDocument,LegalAuthenticator legalAuthenticator,Custodian custodian) {
        this.clinicalDocument=clinicalDocument;
        this.legalAuthenticator=legalAuthenticator;
        this.custodian=custodian;
    }
    
    /* (non-Javadoc)
	 * @see au.gov.nehta.model.common.bCDAModel#getBaseClinicalDocument()
	 */
    @Override
	public BaseClinicalDocument getBaseClinicalDocument() {
    	return clinicalDocument;
    }

    /* (non-Javadoc)
	 * @see au.gov.nehta.model.common.bCDAModel#getLegalAuthenticator()
	 */
    @Override
	public LegalAuthenticator getLegalAuthenticator() {
    	return legalAuthenticator;
    }

    /* (non-Javadoc)
	 * @see au.gov.nehta.model.common.bCDAModel#getCustodian()
	 */
    @Override
	public Custodian getCustodian() {
    	return custodian;
    }

   


}
