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
public class Father extends GrandFather {

	/**
	 * sample class field
	 */
	private Object field4;

	/**
	 * sample class field
	 */
	@SuppressWarnings("unused")
	private Object field5;

	/**
	 * Assigns a new value to the field4.
	 * 
	 * @param field4
	 *            the field4 to set
	 */
	@Override
	@MethodAnno
	public void setField4(Object field4) {
		this.field4 = field4;
	}

	/**
	 * Gets the field4.
	 * 
	 * @return Returns the field4
	 */
	@Override
	public Object getField4() {
		return field4;
	}

	/**
	 * sample method
	 * 
	 * @param s
	 */
	public void sampleMethod(String s) {
		// empty
	}

}
