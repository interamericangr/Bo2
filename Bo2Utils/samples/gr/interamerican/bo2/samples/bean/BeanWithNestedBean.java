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
package gr.interamerican.bo2.samples.bean;

import java.io.Serializable;

/**
 * Bean that has a field that is another bean.
 */
public class BeanWithNestedBean implements Serializable {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Property 1.
	 */
	public static final Integer PROPERTY1 = 1;
	
	/**
	 * Nested bean.  
	 */
	BeanWith2Fields nested;
	
	/**
	 * Property1.
	 */
	Integer property1 = PROPERTY1;
	
	/**
	 * Gets the property1.
	 *
	 * @return Returns the property1
	 */
	public Integer getProperty1() {
		return property1;
	}

	/**
	 * Assigns a new value to the property1.
	 *
	 * @param property1 the property1 to set
	 */
	public void setProperty1(Integer property1) {
		this.property1 = property1;
	}

	/**
	 * Creates a new BeanWithNestedBean object. 
	 * @param field1 
	 * @param field2 
	 */
	public BeanWithNestedBean(String field1, Integer field2) {
		this.nested = new BeanWith2Fields(field1, field2);
	}

	/**
	 * Gets the nested.
	 *
	 * @return Returns the nested
	 */
	public BeanWith2Fields getNested() {
		return nested;
	}

	/**
	 * Assigns a new value to the nested.
	 *
	 * @param nested the nested to set
	 */
	public void setNested(BeanWith2Fields nested) {
		this.nested = nested;
	}

}
