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
import gr.interamerican.bo2.arch.PoDependent;
import gr.interamerican.bo2.arch.utils.beans.ModificationRecordImpl;
import gr.interamerican.bo2.creation.annotations.DelegateMethods;
import gr.interamerican.bo2.creation.annotations.DelegateProperties;
import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.impl.open.annotations.DelegateKeyProperties;
import gr.interamerican.bo2.impl.open.annotations.TypedSelectableProperties;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.AbstractBasePo;
import gr.interamerican.bo2.samples.bean.IBeanWithIdAndNameImpl;
import gr.interamerican.bo2.samples.ibean.IBeanWithIdAndName;
import gr.interamerican.bo2.test.def.samples.SampleCalculator;
import gr.interamerican.bo2.test.def.samples.SampleJavaBean3;
import gr.interamerican.bo2.test.def.samples.SamplePo;
import gr.interamerican.bo2.test.def.samples.SamplePoForTest;
import gr.interamerican.bo2.test.def.samples.SamplePoKey;
import gr.interamerican.bo2.test.def.samples.enums.Sex;

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
 * - name=beanName must be ignored, as getName/setName exist already
 * - subtype=null setter must be empty, getter must return null
 * - type=X must be ignored (no getTypeId() or setTypeId() generated)
 *  
 */
@SuppressWarnings("unused")
@DelegateKeyProperties("id, name")
@TypedSelectableProperties(type="", subtype="null", code="serialNo", name="beanName")
public abstract class SamplePoImpl
extends AbstractBasePo<SamplePoKey>
implements SamplePo
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * delegate properties, methods here
	 */
	@DelegateProperties("picture")
	@DelegateMethods("getMeasurements, setMeasurements")
	SampleJavaBean3 sjb3 = Factory.create(SampleJavaBean3.class);
	
	/**
	 * serialNo
	 */
	@Property private Long serialNo;
	
	/**
	 * serialNo
	 */
	@Property private Long typeId;
	
	/**
	 * implements {@link PoDependent}
	 */
	
	@Property private SamplePoForTest po;
	
	/**
	 * Object array.
	 */
	@Property private Long[] longs;
	
	/**
	 * primitive array.
	 */
	@Property byte[] bytes;
	
	/**
	 * primitive array.
	 */	
	@Property int i;
	
	/**
	 * Sex
	 */
	@Property private Sex sex;
	
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
	 * {@link ModificationRecord} implementation.
	 */
	@DelegateMethods("")
	ModificationRecord mod = new ModificationRecordImpl();
	
	/**
	 * method not declared in the interface with access to local fields.
	 * 
	 * @return a simple calculation result involving
	 */
	public Long twoTimesSerialNoPlusSeven() {
		return this.serialNo * 2 + 7L;
	}
	
	
	public void init() {
		System.out.println("initialized"); //$NON-NLS-1$
	}
	
	
	

}
