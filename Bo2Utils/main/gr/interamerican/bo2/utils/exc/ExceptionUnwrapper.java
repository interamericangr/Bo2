package gr.interamerican.bo2.utils.exc;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * {@link ExceptionUnwrapper} unwraps an exception of a specified type
 * that could be wrapped inside a RuntimeException or an InvocationTargetException.
 *
 * @param <E>
 */
public class ExceptionUnwrapper<E extends Exception> 
implements Serializable {
	

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Exception class.
	 */
	Class<E> exceptionClass;
	
	/**
	 * Rethrows the {@link Exception} returned by the
	 * specified {@link ExceptionUnwrapper}, if it is not null.
	 * 
	 * @param unwrapper
	 *        Unwrapper. 
	 * @param t 
	 *        Throwable being analyzed.
	 *        
	 * @throws Z
	 *         Type of exception being rethrown.  
	 */
	public static <Z extends Exception> 
	void rethrow(ExceptionUnwrapper<Z> unwrapper, Throwable t) throws Z{
		Z z = unwrapper.get(t);
		if (z!=null) {
			throw z;
		}
	}
	
	
	/**
	 * Creates a new ExceptionUnwrapper object.
	 * 
	 * @param clazz
	 */
	public ExceptionUnwrapper(Class<E> clazz) {
		super();
		this.exceptionClass = clazz;
	}

	/**
	 * Gets the argument throwable if it is an instance
	 * of the class specified in the constructor of this
	 * ExceptionUnwrapper.
	 * 
	 * @param t
	 *        Throwable
	 *        
	 * @return Returns the method argument if it is an instance
	 *         of the Exception class of this {@link ExceptionUnwrapper}.
	 *         Otherwise returns null.
	 */
	@SuppressWarnings("unchecked")
	E getMe(Throwable t) {
		if (exceptionClass.isAssignableFrom(t.getClass())) {
			return (E) t;
		}
		return null;
	}
	
	/**
	 * Gets the target exception of the specified argument 
	 * if it is an instance of the class specified in the 
	 * constructor of this ExceptionUnwrapper.
	 * 
	 * @param t
	 *        Throwable
	 *        
	 * @return Returns the target exception of the specified argument 
	 *         if it is an instance of the class specified in the 
	 *         constructor of this ExceptionUnwrapper. 
	 *         Otherwise returns null.
	 */
	E getTarget(InvocationTargetException ite) {
		Throwable target = ite.getTargetException();
		if (target!=null) {
			if (target instanceof RuntimeException) {
				RuntimeException rte = (RuntimeException) target;
				return getCause(rte);
			}
			return getMe(target);
		}
		return null;
	}
	
	/**
	 * Gets the cause exception of the specified argument 
	 * if it is an instance of the class specified in the 
	 * constructor of this ExceptionUnwrapper.
	 * 
	 * @param t
	 *        Throwable
	 *        
	 * @return Returns the cause exception of the specified argument 
	 *         if it is an instance of the class specified in the 
	 *         constructor of this ExceptionUnwrapper. 
	 *         Otherwise returns null.
	 */
	E getCause(RuntimeException rte) {
		Throwable cause = rte.getCause();
		if (cause!=null) {
			if (cause instanceof InvocationTargetException) {
				InvocationTargetException ite = (InvocationTargetException) cause;
				return getTarget(ite);
			}	
			return getMe(cause);
		}
		return null;
	}
	
	/**
	 * Unwraps the exception of the type defined in this
	 * {@link ExceptionUnwrapper}'s constructor.
	 * 
	 * @param t
	 * 
	 * @return Returns the unwrapped exception of the specified type,
	 *         if there is any. Otherwise returns null.
	 */
	public E get(Throwable t) {
		if (t instanceof RuntimeException) {
			RuntimeException rte = (RuntimeException) t;
			return getCause(rte);
		}
		if (t instanceof InvocationTargetException) {
			InvocationTargetException ite = (InvocationTargetException) t;
			return getTarget(ite);
		}		
		return getMe(t);
	}
	
	/**
	 * Gets the clazz.
	 *
	 * @return Returns the clazz
	 */
	public Class<E> getExceptionClass() {
		return exceptionClass;
	}
	
	
}
