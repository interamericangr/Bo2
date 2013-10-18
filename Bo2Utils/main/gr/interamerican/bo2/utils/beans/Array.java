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
package gr.interamerican.bo2.utils.beans;

import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;

/**
 * Wrapper around an array of objects.
 * 
 * Can be used as key for maps.
 */
public class Array {
	/**
	 * Array.
	 */
	Object[] array;
	
	@Override
	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (!(obj instanceof Array)) {
			return false;
		}
		Array other = (Array) obj;
		return Utils.equals(this.array, other.array);		
	}
	
	@Override
	public int hashCode() {		
		return Utils.generateHashCode(array);
	}

	/**
	 * Creates a new Array object. 
	 *
	 * @param array
	 */
	public Array(Object... array) {
		super();
		this.array = array;
	}
	
	
	@Override
	public String toString() {		
		return StringUtils.toString(array);
	}
	
	
	

	
	

}
