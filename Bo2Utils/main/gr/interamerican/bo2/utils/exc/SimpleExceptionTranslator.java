package gr.interamerican.bo2.utils.exc;



/**
 * Translates throwables to a specific exception type.
 * 
 * @param <E>
 *        Type of exception. 
 * 
 */
public class SimpleExceptionTranslator<E extends Exception> 
extends MultipleExceptionTranslator {
	
		
	/**
	 * Class.
	 */
	Class<E> clazz;
	
	
	/**
	 * Rethrows exception types not covered by the unwrappers.
	 *
	 * @param t the t
	 * @throws E the e
	 */
	
	@Override
	public void rethrow(Throwable t) throws E {
		rethrow(clazz,t);		
		rethrowRest(t);
	}
	
	
	/**
	 * Creates a new SimpleExceptionTranslator object.
	 *
	 * @param clazz the clazz
	 */
	@SuppressWarnings({ "unchecked" })
	public SimpleExceptionTranslator(Class<E> clazz) {
		super(new Class[]{clazz});
		this.clazz = clazz;
	}
	
	
	

}
