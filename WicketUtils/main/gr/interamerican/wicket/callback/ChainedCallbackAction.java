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
package gr.interamerican.wicket.callback;

/**
 * Chain of responsibility enhancement of {@link CallbackAction}.
 * <br/>
 * A {@link ChainedCallbackAction} is a {@link CallbackAction} that provides
 * facilities for inversion of control by allowing components to chain, before 
 * or after, other {@link CallbackAction}s.
 * <br/>
 * These methods should be used before execution of the callback. If they are
 * used after, a {@link RuntimeException} should be thrown. 
 */
public interface ChainedCallbackAction extends CallbackAction {
	
	/**
	 * Chains the specified action before any other actions currently
	 * chained.
	 * @param action
	 */
	void chainBefore(CallbackAction action);
	
	/**
	 * Chains the specified action after any other actions currently
	 * chained.
	 * @param action
	 */
	void chainAfter(CallbackAction action);

}
