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
package gr.interamerican.bo2.samples;

import gr.interamerican.bo2.samples.anno.Anno;

/**
 * sample class
 */
@SuppressWarnings("unused")
public class GrandFather {

	/**
	 * sample class field
	 */	
	private String field1 = "field1"; //$NON-NLS-1$

	/**
	 * sample class field
	 */

	@Anno
	private String field2 = "field2"; //$NON-NLS-1$

	/**
	 * sample class field
	 */
	@Anno
	private Object field3;
	

	/**
	 * sample class field
	 */
	private Object field4;
	
	/**
	 * Assigns a new value to the field4.
	 * 
	 * @param field4
	 *            the field4 to set
	 */
	public void setField4(Object field4) {
		this.field4 = field4;
	}

	/**
	 * Gets the field4.
	 * 
	 * @return Returns the field4
	 */
	public Object getField4() {
		return field4;
	}
}
