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
package gr.interamerican.bo2.utils.adapters;

import gr.interamerican.bo2.utils.Utils;

/**
 * Modification that checks if the specified value is equal to
 * the value specified in the constructor as <code>replace</code>
 * and if so, it replaces it with the value specified in the
 * constructor as <code>with</code>.
 * 
 * @param <T> 
 */
public class ReplaceWith<T> 
implements Modification<T> {
	/**
	 * Object to find and replace.
	 */
	T what;
	/**
	 * Object to replace with.
	 */
	T with;
	
	

	/**
	 * Creates a new ReplaceWith object. 
	 *
	 * @param what
	 * @param with
	 */
	public ReplaceWith(T what, T with) {
		super();
		this.what = what;
		this.with = with;
	}
	
	public T execute(T a) {
		if (Utils.equals(what, a)) {
			return with;
		}		
		return a;
	}

}
