/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 ******************************************************************************/
package gr.interamerican.bo2.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;



/**
 * Utilities relevant with Exceptions.
 */
public class ExceptionUtils {
	
	/**
	 * Creates a new Debug object.
	 * 
	 * Private hidden constructor of a utility class. 
	 */
	private ExceptionUtils() {
		/* empty */
	}
	
	/**
	 * Indicates if a thrown was caused by a Throwable of a specified type.
	 * 
	 * @param thrown Throwable.
	 * @param causeType Type
	 * 
	 * @return Returns true if the initial cause of the thrown was of type causeType.
	 */
	public static boolean isCausedBy
	(Throwable thrown, Class<? extends Throwable> causeType) {
		if (causeType.isAssignableFrom(thrown.getClass())) {
			return true;	
		} else {
			Throwable cause = thrown.getCause();
			if (cause==null) {
				return false;
			} else {
				return isCausedBy(cause, causeType);
			}
		}
	}
	
	/**
	 * Gets the initial cause of a thrown.
	 * 
	 * @param thrown
	 * @return Returns the Throwable that initiated the thrown.
	 */
	public static Throwable initialCause(Throwable thrown) {
		Throwable cause = thrown.getCause();
		if (cause==null) {
			return thrown;
		} else {
			return initialCause(cause);
		}
	}
	
	/**
	 * Gets the cause in the stack trace chain that belongs to a specified Throwable
	 * type.
	 * 
	 * 
	 * @param thrown Thrown 
	 * @param causeType Type of Throwable.
	 * @param <T> Type of Throwable.
	 * 
	 * @return Returns The throwable in the chain of causes of this Exception that belongs to the
	 *         specified type. If there is no cause that belongs to the specified type, then 
	 *         returns null.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Throwable> T causeInTheChain
	(Throwable thrown, Class<T> causeType) {
		if (causeType.isAssignableFrom(thrown.getClass())) {
			return (T)thrown;	
		} else {
			Throwable cause = thrown.getCause();
			if (cause==null) {
				return null;
			} else {
				return causeInTheChain(cause, causeType);
			}
		}
	}
	
	/**
	 * Returns a string with the stack trace of a {@link Throwable} 
	 * 
	 * @param t 
	 * @return the stack trace
	 */
	public static String getThrowableStackTrace(Throwable t) {
		StringWriter writer = new StringWriter();
		t.printStackTrace(new PrintWriter(writer));
		return writer.getBuffer().toString();
	}
	
	/**
	 * Returns the value or throws a RuntimeException if it is null.
	 * 
	 * @param value
	 * @return not null value or throw RuntimeException.
	 */
	public static <T> T notNull(T value) {
		if(value==null) {
			throw new RuntimeException("Bad input. Null is not allowed"); //$NON-NLS-1$
		}
		return value;
	}
	
	/**
	 * Transforms a checked Throwable to an unchecked {@link RuntimeException}.
	 * 
	 * If t is a {@link RuntimeException} the method will return it. 
	 * If it an instance of any type of checked {@link Exception}
	 * the method will throw a RuntimeException with t as its cause.
	 * 
	 * @param t Throwable that has to be transformed to an unchecked Exception.
	 *  
	 * @return Returns t wrapped inside a runtime exception.
	 */
	public static RuntimeException runtimeException(Throwable t) {
		if (t instanceof RuntimeException) {
			return (RuntimeException) t;
		} else {
			return new RuntimeException(t);
		}
	}
	
	/**
	 * Transforms a {@link RuntimeException} to its cause.
	 * 
	 * @param t Throwable that has to be transformed.
	 *  
	 * @return Returns t or its cause.
	 */
	public static Throwable unwrap(Throwable t) {
		if (t instanceof RuntimeException) {
			Throwable cause = t.getCause();
			if (cause==null) {
				return t;
			}
			return unwrap(cause);			
		} else {
			if (t instanceof InvocationTargetException) {
				InvocationTargetException itex = (InvocationTargetException)t;
				Throwable targetEx = itex.getTargetException();
				return unwrap(targetEx);				
			}			
			return t;
		}
	}

}
