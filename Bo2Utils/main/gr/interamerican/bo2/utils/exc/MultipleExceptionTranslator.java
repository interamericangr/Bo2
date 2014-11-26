package gr.interamerican.bo2.utils.exc;

import java.util.HashMap;
import java.util.Map;


/**
 * Translates throwables to specific exception types.
 * 
 */
public class MultipleExceptionTranslator {
	
	/**
	 * Exception unwwrappers.
	 */
	@SuppressWarnings("rawtypes")
	protected Map<Class, ExceptionUnwrapper> unwrappers;
	
	/**
	 * Handler for the rest exceptions.
	 */
	protected ThrowingExceptionHandler handler = new ThrowingExceptionHandler();
	
	/**
	 * Creates a new ExceptionTranslator object.
	 * 
	 * @param classes
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MultipleExceptionTranslator(Class<? extends Exception>... classes) {
		super();
		unwrappers = new HashMap<Class, ExceptionUnwrapper>();
		for (Class<? extends Exception> clazz : classes) {
			ExceptionUnwrapper unwrapper = new ExceptionUnwrapper(clazz);			
			unwrappers.put(clazz, unwrapper);
		}
	}
	
	
	
	/**
	 * Rethrows a specific Exception type.
	 * 
	 * @param clazz
	 * @param t
	 * @throws E
	 */
	@SuppressWarnings("unchecked")
	protected <E extends Exception> void rethrow(Class<E> clazz, Throwable t) throws E {
		ExceptionUnwrapper<E> unwrapper = unwrappers.get(clazz);
		ExceptionUnwrapper.rethrow(unwrapper, t);		
	}
	
	/**
	 * Rethrows exception types not covered by the unwrappers.
	 * 
	 * @param t
	 */
	protected void rethrowRest(Throwable t) {
		handler.rethrow(t);
	}
	
	/**
	 * Rethrows exception types not covered by the unwrappers.
	 * 
	 * @param t
	 * 
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void rethrow(Throwable t) throws Exception {
		for (Class clazz : unwrappers.keySet()) {
			rethrow(clazz,t);			
		}
		rethrowRest(t);
	}
	
	
	
	
	

}
