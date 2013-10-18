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
 * Bean with two two fields.
 */
public class BeanWith3Fields
implements Serializable {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Creates a new BeanWith3Fields object. 
	 *
	 */
	public BeanWith3Fields() {
		super();
	}
	
	/**
	 * Creates a new BeanWith3Fields object. 
	 *
	 * @param field1
	 * @param field2
	 * @param field3
	 */
	public BeanWith3Fields(String field1, Integer field2, Double field3) {
		super();
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
	}

	/**
	 * field 1.
	 */
	private String field1;
	
	/**
	 * field 2.
	 */
	private Integer field2;
	
	/**
	 * field 3.
	 */
	private Double field3;
	

	/**
	 * field 1.
	 * @return field 1.
	 */
	public String getField1() {
		return field1;
	}

	/**
	 * Sets field 1.
	 * 
	 * @param field1
	 */
	public void setField1(String field1) {
		this.field1=field1;		
	}
	
	/**
	 * field 2.
	 * @return field 2.
	 */	
	public Integer getField2() {
		return field2;
	}
	
	/**
	 * Sets field 2.
	 * 
	 * @param field2
	 */
	public void setField2(Integer field2) {
		this.field2=field2;
		
	}

	/**
	 * Gets the field3.
	 *
	 * @return Returns the field3
	 */
	public Double getField3() {
		return field3;
	}

	/**
	 * Assigns a new value to the field3.
	 *
	 * @param field3 the field3 to set
	 */
	public void setField3(Double field3) {
		this.field3 = field3;
	}

}
