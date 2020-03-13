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
 * The Class SampleClassKeyImpl.
 */
public class SampleClassKeyImpl implements SampleClassKey{
	
	/** sample field. */
	private String field1;
	
	/** sample field. */
	private Integer field2;

	@Override
	public String getField1() {
		return field1;
	}

	@Override
	public void setField1(String field1) {
		this.field1=field1;
		
	}

	@Override
	public Integer getField2() {
		return field2;
	}

	@Override
	public void setField2(Integer field2) {
		this.field2=field2;
		
	}
	

}
