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

import gr.interamerican.bo2.utils.StringConstants;

/**
 * sample class with a two parameter constructor
 */
@SuppressWarnings("all")
public class SampleMultiConstructorClass {
	private Integer i;
	private String s;

	public SampleMultiConstructorClass(Integer i, String s) {
		this.i = i;
		this.s = s;
	}
	
	/**
	 * Creates a new SampleMultiConstructorClass object. 
	 *
	 */
	public SampleMultiConstructorClass() {
		this.i = 0;
		this.s = StringConstants.ZERO;
	}
	
	public SampleMultiConstructorClass(Integer i) {
		this.i = i;
		s = i.toString();
	}

	public Integer getI() {
		return i;
	}

	public String getS() {
		return s;
	}
	
}
