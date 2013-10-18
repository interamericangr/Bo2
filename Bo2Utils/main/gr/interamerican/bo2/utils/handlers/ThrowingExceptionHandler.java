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
package gr.interamerican.bo2.utils.handlers;

/**
 * <p>{@link ExceptionHandler} that re-throws the thrown
 * it handles. <br/>
 * 
 * <p>If the thrown is an Error or a RuntimeException, it is 
 * re-thrown as is. Otherwise, it is wrapped inside a 
 * RuntimeException. 
 */
public class ThrowingExceptionHandler 
implements ExceptionHandler {
	
	/**
	 * Instance.
	 */
	public static final ThrowingExceptionHandler INSTANCE = 
		new ThrowingExceptionHandler(); 
	
	@Override
	public void handle(Throwable t) {
		if (t instanceof Error) {
			throw (Error) t;
		} 
		if (t instanceof RuntimeException) {
			throw (RuntimeException) t;
		}
		throw new RuntimeException(t);
	}

}
