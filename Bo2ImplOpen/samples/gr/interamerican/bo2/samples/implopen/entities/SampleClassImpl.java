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
package gr.interamerican.bo2.samples.implopen.entities;

import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.creation.annotations.DelegateMethods;
import gr.interamerican.bo2.creation.annotations.DelegateProperties;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.test.def.samples.SampleCalculator;
import gr.interamerican.bo2.test.impl.samples.SampleCalculatorImpl;

/**
 * sample abstract class
 */
public abstract class SampleClassImpl implements SampleClass , SampleClass2, SampleCalculator, TypedSelectable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * a sample key field declared as an inteface
	 */
	@SuppressWarnings({ "unused", "cast" })
	@DelegateProperties("field1, field2")
	private BeanWith2Fields sampleBean = new BeanWith2Fields();
	
	@DelegateMethods("")
	private SampleCalculator sampleCalculator = (SampleCalculator) new SampleCalculatorImpl();
	
	private Long codebar;
	
	private String descr;
	
	private Long typeId;

	/**
	 * Gets the codebar.
	 *
	 * @return Returns the codebar
	 */
	public Long getCodebar() {
		return codebar;
	}

	/**
	 * Assigns a new value to the codebar.
	 *
	 * @param codebar the codebar to set
	 */
	public void setCodebar(Long codebar) {
		this.codebar = codebar;
	}

	/**
	 * Gets the descr.
	 *
	 * @return Returns the descr
	 */
	public String getDescr() {
		return descr;
	}

	/**
	 * Assigns a new value to the descr.
	 *
	 * @param descr the descr to set
	 */
	public void setDescr(String descr) {
		this.descr = descr;
	}

	/**
	 * Gets the typeId.
	 *
	 * @return Returns the typeId
	 */
	public Long getTypeId() {
		return typeId;
	}

	/**
	 * Assigns a new value to the typeId.
	 *
	 * @param typeId the typeId to set
	 */
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	
	
	
	
	
}
