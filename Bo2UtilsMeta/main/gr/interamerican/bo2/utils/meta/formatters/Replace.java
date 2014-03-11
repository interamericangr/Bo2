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
package gr.interamerican.bo2.utils.meta.formatters;

/**
 * Formatter that always returns the same value.
 * 
 * @param <T> 
 *        Type of objects being formatted.
 */
public class Replace<T> implements Formatter<T> {	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constant value being returned by this formatter.
	 */
	String value;
	
	/**
	 * Creates a new Replace object. 
	 *
	 * @param value
	 */
	public Replace(String value) {
		super();
		this.value = value;
	}
	
	public String format(T t) {
		return value;
	}

}
