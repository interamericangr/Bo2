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
package gr.interamerican.bo2.arch.utils;

import gr.interamerican.bo2.utils.ExceptionUtils;


/**
 * Bo2Arch Utilities
 */
public class Bo2ArchUtils {
	
	/**
	 * private empty constructor 
	 */
	private Bo2ArchUtils() {/* empty */}
	
	/**
	 * Transforms a checked Throwable to an unchecked {@link RuntimeException}.
	 * 
	 * If t is a {@link RuntimeException} or {@link Error}, the method will 
	 * rethrow t. If it an instance of any type of checked {@link Exception}
	 * the method will throw a RuntimeException with t as its cause.
	 * 
	 * @param t Throwable that has to be transformed to an unchecked Exception.
	 *  
	 * @return Returns t wrapped inside a runtime exception.
	 * 
	 * @deprecated Use {@link ExceptionUtils#runtimeException(Throwable)} instead.
	 */
	public static RuntimeException runtimeException(Throwable t) {		
		return ExceptionUtils.runtimeException(t);
	}
	
	/**
	 * Transforms a {@link RuntimeException} to its cause.
	 * 
	 * @param t Throwable that has to be transformed.
	 *  
	 * @return Returns t or its cause.
	 * 
	 * @deprecated Use {@link ExceptionUtils#unwrap(Throwable)} instead.
	 */
	public static Throwable unwrap(Throwable t) {
		return ExceptionUtils.unwrap(t);
	}
	
}
