package gr.interamerican.bo2.arch.utils;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.utils.ExceptionUtils;

/**
 * Utility class for 
 */
public class Bo2ExceptionUtils {
	
	/**
	 * Creates a new Bo2ExceptionUtils object.
	 */
	private Bo2ExceptionUtils(){}
	
	/**
	 * Throws an exception, it is an {@link InitializationException}, otherwise does nothing.
	 * 
	 * @param t
	 * @throws InitializationException
	 */
	public static void throwIfIsInitializationException(Throwable t) throws InitializationException {
		if (t instanceof InitializationException) {
			throw (InitializationException)t;
		}
	}
	
	/**
	 * Throws an exception, it is an {@link DataException}, otherwise does nothing.
	 * 
	 * @param t
	 * @throws DataException
	 */
	public static void throwIfIsDataException(Throwable t) throws DataException {
		if (t instanceof DataException) {
			throw (DataException)t;
		}
	}
	
	/**
	 * Throws an exception, it is an {@link LogicException}, otherwise does nothing.
	 * 
	 * @param t
	 * @throws LogicException
	 */
	public static void throwIfIsLogicException(Throwable t) throws LogicException {
		if (t instanceof LogicException) {
			throw (LogicException)t;
		}
	}
	
	/**
	 * Throws an exception, it is an {@link LogicException}, otherwise does nothing.
	 * 
	 * @param t
	 * @throws Error 
	 */
	public static void throwIfIsError(Throwable t) throws Error {
		if (t instanceof Error) {
			throw (Error)t;
		}
	}
	
	
	/**
	 * Wraps and rethrows a thrown, so that it matches the signature of a 
	 * method that throws the following checked exceptions:
	 * {@link DataException}, {@link LogicException}, {@link InitializationException}. 
	 * 
	 * @param t
	 * @throws DataException 
	 * @throws LogicException
	 * @throws InitializationException 
	 */
	public static void throwInitDataLogicBo2dliException(Throwable t) 
	throws DataException, LogicException, InitializationException {
		throwIfIsInitializationException(t);
		throwIfIsDataException(t);
		throwIfIsLogicException(t);
		throwIfIsError(t);
		throw ExceptionUtils.runtimeException(t);
	}
	
	/**
	 * Wraps and rethrows a thrown, so that it matches the signature of a 
	 * method that throws the following checked exceptions:
	 * {@link DataException}, {@link LogicException}, {@link UnexpectedException}. 
	 * 
	 * @param t
	 * @throws DataException 
	 * @throws LogicException
	 * @throws UnexpectedException 
	 */
	public static void throwDataLogicOrUnexpectedException(Throwable t) 
	throws DataException, LogicException, UnexpectedException {		
		throwIfIsDataException(t);
		throwIfIsLogicException(t);
		throwIfIsError(t);
		throw new UnexpectedException(t);
	}
	
	/**
	 * Wraps and rethrows a thrown, so that it matches the signature of a 
	 * method that throws the following checked exceptions:
	 * {@link DataException}, {@link LogicException}. 
	 * 
	 * @param t
	 * @throws DataException 
	 * @throws LogicException
	 */
	public static void throwDataOrLogicException(Throwable t) 
	throws DataException, LogicException  {		
		throwIfIsDataException(t);
		throwIfIsLogicException(t);
		throwIfIsError(t);
		throw ExceptionUtils.runtimeException(t);
	}
	
	

}
