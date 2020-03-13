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
	protected Map<Class<? extends Exception>, ExceptionUnwrapper<? extends Exception>> unwrappers;
	
	/**
	 * Handler for the rest exceptions.
	 */
	protected ThrowingExceptionHandler handler = new ThrowingExceptionHandler();
	
	/**
	 * Creates a new ExceptionTranslator object.
	 *
	 * @param classes the classes
	 */
	@SafeVarargs
	public MultipleExceptionTranslator(Class<? extends Exception>... classes) {
		super();
		unwrappers = new HashMap<>();
		for (Class<? extends Exception> clazz : classes) {
			ExceptionUnwrapper<?> unwrapper = new ExceptionUnwrapper<>(clazz);			
			unwrappers.put(clazz, unwrapper);
		}
	}
	
	
	
	/**
	 * Rethrows a specific Exception type.
	 *
	 * @param <E> the element type
	 * @param clazz the clazz
	 * @param t the t
	 * @throws E the e
	 */
	protected <E extends Exception> void rethrow(Class<E> clazz, Throwable t) throws E {
		@SuppressWarnings("unchecked")
		ExceptionUnwrapper<E> unwrapper = (ExceptionUnwrapper<E>) unwrappers.get(clazz);
		ExceptionUnwrapper.rethrow(unwrapper, t);		
	}
	
	/**
	 * Rethrows exception types not covered by the unwrappers.
	 *
	 * @param t the t
	 */
	protected void rethrowRest(Throwable t) {
		handler.rethrow(t);
	}
	
	/**
	 * Rethrows exception types not covered by the unwrappers.
	 *
	 * @param t the t
	 * @throws Exception the exception
	 */
	public void rethrow(Throwable t) throws Exception {
		for (Class<? extends Exception> clazz : unwrappers.keySet()) {
			rethrow(clazz,t);			
		}
		rethrowRest(t);
	}
}