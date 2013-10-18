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
 * Generic interface for called objects.
 * 
 * @param <T> 
 *        Type of caller object.
 */
public interface Called<T> {
	
	/**
	 * The component that has received and may call this action.
	 * Typically, this component will set itself to the callback.
	 * 
	 * @param caller
	 *        The caller to set.
	 */
	public void setCaller(T caller);
	
	/**
	 * Returns the calling component.
	 * 
	 * @return Returns caller.
	 */
	public T getCaller();

}
