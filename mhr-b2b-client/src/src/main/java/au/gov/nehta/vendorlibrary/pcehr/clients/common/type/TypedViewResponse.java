package au.gov.nehta.vendorlibrary.pcehr.clients.common.type;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.exception.ViewUnmarshallException;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.getview._1.GetViewResponse;

/**
 * 
 * This immutable  class holds an unmarshalled response object and the original response view object
 * 
 * @param <T>  The type held in the "data" tag of the view
 * 
 */
public class TypedViewResponse<T> {
	private final T responseObject;
	private final GetViewResponse getViewResponse;
	
	public TypedViewResponse(GetViewResponse getViewResponse, T responseObject){
		this.getViewResponse=getViewResponse;
		this.responseObject=responseObject;
	}
	
	/**
	 * Create a typed view response
	 * 
	 * @param clazz the Type to unmarshall held in the response object
	 * @param getViewResponse the full view response; 
	 * @return a typed holder object,TypedViewResponse<T>
	 */
	@SuppressWarnings("unchecked")
	public static <T> TypedViewResponse<T> unmarshall(Class<T> clazz, GetViewResponse getViewResponse ){
		try {
			
			T innerObject = null;
			
			if(getViewResponse.getView() != null && getViewResponse.getView().getData() != null){
				JAXBContext context = JAXBContext.newInstance(clazz);
				Unmarshaller u = context.createUnmarshaller();
				String xml = new String(getViewResponse.getView().getData());
				innerObject = (T) u.unmarshal(new StringReader(xml));
			}
			
			return new TypedViewResponse<T>(getViewResponse,  innerObject); 
		} catch (JAXBException e) {
			throw new ViewUnmarshallException(e);
		}   
	}

	/**
	 * Get the typed response object held the the "data" tag
	 */
	public T getResponseObject() {
		return responseObject;
	}

	/**
	 * Get the original GetViewResponse object
	 */
	public GetViewResponse getGetViewResponse() {
		return getViewResponse;
	}

	/**
	 * A convenience accessor to getViewResponse.getResponseStatus().getCode()
	 */
	public String getCode() {
		return getViewResponse.getResponseStatus().getCode();
	}
	
}
