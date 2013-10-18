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
package gr.interamerican.bo2.test.impl.samples;

import gr.interamerican.bo2.test.def.samples.SampleClassKey;

/**
 * 
 */
public class SampleClassKeyImpl implements SampleClassKey{
	
	/**
	 * sample field
	 */
	private String field1;
	
	/**
	 * sample field
	 */
	private Integer field2;

	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.test.samples.SampleClassKP#getField1()
	 */
	public String getField1() {
		return field1;
	}

	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.test.samples.SampleClassKP#setField1(java.lang.String)
	 */
	public void setField1(String field1) {
		this.field1=field1;
		
	}

	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.test.samples.SampleClassKP#getField2()
	 */
	public Integer getField2() {
		return field2;
	}

	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.test.samples.SampleClassKP#setField2(java.lang.Integer)
	 */
	public void setField2(Integer field2) {
		this.field2=field2;
		
	}
	

}
