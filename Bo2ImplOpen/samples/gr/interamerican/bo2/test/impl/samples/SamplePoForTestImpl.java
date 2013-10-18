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

import gr.interamerican.bo2.arch.ModificationRecord;
import gr.interamerican.bo2.arch.utils.beans.ModificationRecordImpl;
import gr.interamerican.bo2.creation.annotations.DelegateMethods;
import gr.interamerican.bo2.creation.annotations.DelegateProperties;
import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.impl.open.annotations.DelegateKeyProperties;
import gr.interamerican.bo2.impl.open.annotations.TypedSelectableProperties;
import gr.interamerican.bo2.impl.open.beans.ConstantType;
import gr.interamerican.bo2.impl.open.po.AbstractBasePo;
import gr.interamerican.bo2.samples.bean.IBeanWithIdAndNameImpl;
import gr.interamerican.bo2.samples.ibean.IBeanWithIdAndName;
import gr.interamerican.bo2.test.def.samples.SampleCalculator;
import gr.interamerican.bo2.test.def.samples.SamplePoForTest;
import gr.interamerican.bo2.test.def.samples.SamplePoKey;

/**
 * With order of priority
 *  
 * DelegateKeyProperties: 
 * getId/setId and getName/setName must be successfully generated and
 * delegated to the Key. Setters must also call fixKeysofChildren()
 * 
 * Property:
 * getSerialNo/setSerialNo must be successfully generated
 * 
 * DelegateProperties:
 * Delegate setters and getters must be generated for SampleBean properties
 * 
 * DelegateMethods:
 * Delegate methods for SampleCalculator must be generated
 * 
 * TypedSelectableProperties:
 * - getCode/setCode must be successfully generated and delegated to 
 *   getSerialNo/setSerialNo
 * - name="" is ignored
 * - subtype=null setter must be empty, getter must return null
 * - type=X must be ignored (no getTypeId() or setTypeId() generated)
 *  
 */
@SuppressWarnings("unused")
@DelegateKeyProperties("id, name")
@TypedSelectableProperties(type="X", subtype="null", code="serialNo", name="")
public abstract class SamplePoForTestImpl
extends AbstractBasePo<SamplePoKey>
implements SamplePoForTest
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * serialNo
	 */
	@Property
	private Long serialNo;
	
	/**
	 * TypeId.
	 */
	@DelegateProperties("typeId")
	private static ConstantType ct = new ConstantType(10L);
	
	/**
	 * bean
	 */	
	@DelegateProperties("")
	private IBeanWithIdAndName bean = new IBeanWithIdAndNameImpl();
	
	/**
	 * calc
	 */
	@DelegateMethods("")
	private SampleCalculator calc = new SampleCalculatorImpl();
	
	/**
	 * Modification.
	 */
	@DelegateProperties({})
	ModificationRecord mdf = new ModificationRecordImpl();
	
	/**
	 * method not declared in the interface with access to local fields.
	 * 
	 * @return a simple calculation result involving
	 */
	public Long twoTimesSerialNoPlusSeven() {
		return this.serialNo * 2 + 7L;
	}
	
	
	public void init() {
		/* empty */
	}
	
}
