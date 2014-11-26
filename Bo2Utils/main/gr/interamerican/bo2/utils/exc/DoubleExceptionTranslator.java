package gr.interamerican.bo2.utils.exc;


/**
 * Translates throwables to a specific exception type.
 * 
 * @param <A> 
 * @param <B> 
 */
public class DoubleExceptionTranslator 
<A extends Exception, B extends Exception> {
	
	/**
	 * Unwrapper for A exceptions.
	 */
	ExceptionUnwrapper<A> uwA; 
	
	/**
	 * Unwrapper for B exceptions.
	 */
	ExceptionUnwrapper<B> uwB; 
	
	
	/**
	 * Creates a new ExceptionTranslator object.
	 * @param clazzA
	 */
	public DoubleExceptionTranslator(Class<A> clazzA, Class<B> clazzB) {
		super();
		this.uwA = new ExceptionUnwrapper<A>(clazzA);
		this.uwB = new ExceptionUnwrapper<B>(clazzB);
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
