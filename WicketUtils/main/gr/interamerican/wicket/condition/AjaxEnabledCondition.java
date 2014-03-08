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
package gr.interamerican.wicket.condition;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Interface for a condition check on an object that may
 * cause an AJAX update if necessary.
 * 
 * @param <T>
 *        Type of object being checked. 
 */
public interface AjaxEnabledCondition<T> extends Serializable {
	
	/**
	 * Checks if the condition is met for the specified object.
	 * 
	 * @param t
	 *        Object being checked against the condition.
	 * @param target
	 *        AjaxRequestTarget.
	 *        
	 * @return Returns true if the condition is fulfilled by the
	 *         specified object.
	 */
	public boolean check(T t, AjaxRequestTarget target);
	
	

}
