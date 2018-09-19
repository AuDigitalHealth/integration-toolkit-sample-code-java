package au.gov.nehta.vendorlibrary.pcehr.clients.common.exception;


/**
 * Thrown if there were complications unmarshalling a view response 
 */
public class ViewUnmarshallException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ViewUnmarshallException(Exception e) {
		super(e);
	}

}
