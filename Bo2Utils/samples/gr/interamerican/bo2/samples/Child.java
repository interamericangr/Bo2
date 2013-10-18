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

import gr.interamerican.bo2.samples.anno.MethodAnno;

/**
 * sample class
 */
public class Child extends Father {

	/**
	 * sample class field marked protected to allow reflective access
	 */
	protected Object field6;

	/**
	 * Assigns a new value to the field6.
	 * 
	 * @param field6
	 *            the field6 to set
	 */
	@MethodAnno
	public void setField6(Object field6) {
		this.field6 = field6;
	}

	/**
	 * Gets the field6.
	 * 
	 * @return Returns the field6
	 */
	@MethodAnno
	public Object getField6() {
		return field6;
	}

	@Override
	public void sampleMethod(String s) {
		// empty
	}

}
