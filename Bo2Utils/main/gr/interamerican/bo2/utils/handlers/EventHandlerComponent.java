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
 * Concrete AbstractEventHandler without any additional method or field.
 * 
 * @param <C> 
 */
public class EventHandlerComponent<C> 
extends AbstractEventHandler<C> {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new EventHandlerComponent object. 
	 * 
	 * @param exceptionHandler
	 */
	public EventHandlerComponent(ExceptionHandler exceptionHandler) {
		super(exceptionHandler);
	}
	
	/**
	 * Creates a new EventHandlerComponent object. 
	 * 
	 * @param caller 
	 *        Caller object.
	 * @param exceptionHandler
	 *        Exception handler. 
	 */
	public EventHandlerComponent(C caller, ExceptionHandler exceptionHandler) {
		this(exceptionHandler);
		setCaller(caller);
	}
	
	


}
