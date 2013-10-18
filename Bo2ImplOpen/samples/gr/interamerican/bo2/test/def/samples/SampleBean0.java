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
package gr.interamerican.bo2.test.def.samples;

import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.impl.open.po.AbstractBasePo;
import gr.interamerican.bo2.utils.annotations.Child;

import java.util.Set;

/**
 * 
 */
public class SampleBean0 {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * field1
	 */
	@Property
	public String field1;
	
	/**
	 * field2
	 */
	@Property
	public Long field2;
	
	/**
	 * sample child po
	 */
	@Child
	public AbstractBasePo<?> sampleChildPo;
	
	/**
	 * sample child set
	 */
	@Child
	@Property
	public Set<?> sampleChildSet;
	
	/**
	 * Gets the field1.
	 * 
	 * @return Returns the field1
	 */
	public String getField1() {
		return field1;
	}

	/**
	 * Assigns a new value to the field1.
	 * 
	 * @param field1
	 *            the field1 to set
	 */
	public void setField1(String field1) {
		this.field1 = field1;
	}
	
	/**
	 * Gets the field2.
	 *
	 * @return Returns the field2
	 */
	public Long getField2() {
		return field2;
	}

	/**
	 * Assigns a new value to the field2.
	 *
	 * @param field2 the field2 to set
	 */
	public void setField2(Long field2) {
		this.field2 = field2;
	}

	/**
	 * Sample logic method
	 * 
	 * @param a
	 * @param b
	 * @return a+b
	 */
	public Integer add(Integer a, Integer b) {
		return a+ b;
	}
	
	/**
	 * void method with no arguments
	 * that does nothing
	 */
	public void doNothing(){
		/*empty*/
	}

}
