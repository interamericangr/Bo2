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
public class BeanWith2Fields implements Serializable {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * field 1.
	 */
	public String field1;
	
	/**
	 * field 2.
	 */
	private Integer field2;
	
	/**
	 * Creates a new BeanWith2Fields object. 
	 *
	 */
	public BeanWith2Fields() {
		super();
	}
	
	/**
	 * Creates a new BeanWith2Fields object. 
	 * @param field1 
	 * @param field2 
	 */
	public BeanWith2Fields(String field1, Integer field2) {
		super();
		this.field1 = field1;
		this.field2 = field2;
	}

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
	 * Default method.
	 * @return field2 string
	 */
	protected String getField1String() {
		return field1;
	}
	
	/**
	 * Protected method.
	 * @return field2 string
	 */
	protected String getField2AsString() {
		return field2.toString();
	}

}
