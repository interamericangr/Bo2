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
package gr.interamerican.wicket.samples.bean;

import gr.interamerican.bo2.arch.ext.TranslatableEntry;

import java.io.Serializable;

/**
 * Bean with two two fields.
 */
public class BeanWith2TranslatableEntries
implements Serializable {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Creates a new BeanWith2TranslatableEntrys object. 
	 *
	 */
	public BeanWith2TranslatableEntries() {
		super();
	}
	
	/**
	 * Creates a new BeanWith2TranslatableEntrys object. 
	 *
	 * @param field1
	 * @param field2
	 */
	public BeanWith2TranslatableEntries(TranslatableEntry<Long, Long, Long> field1, TranslatableEntry<Long, Long, Long> field2) {
		super();
		this.field1 = field1;
		this.field2 = field2;
	}

	/**
	 * field 1.
	 */
	private TranslatableEntry<Long, Long, Long> field1;
	
	/**
	 * field 2.
	 */
	private TranslatableEntry<Long, Long, Long> field2;
	
	/**
	 * field 1.
	 * @return field 1.
	 */
	public TranslatableEntry<Long, Long, Long> getField1() {
		return field1;
	}

	/**
	 * Sets field 1.
	 * 
	 * @param field1
	 */
	public void setField1(TranslatableEntry<Long, Long, Long> field1) {
		this.field1=field1;		
	}
	
	/**
	 * field 2.
	 * @return field 2.
	 */	
	public TranslatableEntry<Long, Long, Long> getField2() {
		return field2;
	}
	
	/**
	 * Sets field 2.
	 * 
	 * @param field2
	 */
	public void setField2(TranslatableEntry<Long, Long, Long> field2) {
		this.field2=field2;
		
	}

}
