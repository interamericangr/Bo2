package gr.interamerican.bo2.utils.exc;

import gr.interamerican.bo2.utils.handlers.ThrowingExceptionHandler;


/**
 * Translates throwables to a specific exception type.
 * 
 * @param <A> 
 */
public class SimpleExceptionTranslator <A extends Exception> {
	
	/**
	 * Unwrapper for data exceptions.
	 */
	ExceptionUnwrapper<A> uwA; 
	
	
	
	
	/**
	 * Creates a new ExceptionTranslator object.
	 * @param clazzA
	 */
	public SimpleExceptionTranslator(Class<A> clazzA) {
		super();
		this.uwA = new ExceptionUnwrapper<A>(clazzA);
	}
	/**
	 * Rethrows a throwable.
	 * 
	 * @param t
	 * 
	 * @throws LogicException
	 * @throws DataException
	 */
	public void rethrow(Throwable t) throws A {
		A a = uwA.get(t);
		if (a!=null) {
			throw a;
		}
		if (t instanceof Error) {
			throw (Error) t;
		}
		if (t instanceof RuntimeException) {
			throw (RuntimeException) t;
		}
		throw new RuntimeException(t);		
	}

}
