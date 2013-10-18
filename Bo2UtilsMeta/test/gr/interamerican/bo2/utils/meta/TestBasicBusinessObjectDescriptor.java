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
package gr.interamerican.bo2.utils.meta;

import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.samples.utils.meta.Bean1;
import gr.interamerican.bo2.samples.utils.meta.Bean1descriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.StringBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.exceptions.MultipleValidationsException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import org.junit.Test;

/**
 * BasicBusinessObjectDescriptorTest
 */
public class TestBasicBusinessObjectDescriptor {
	
	
	/**
	 * BasicBusinessObjectDescriptor
	 */
	BasicBusinessObjectDescriptor<Bean1> busDesc = new  BasicBusinessObjectDescriptor<Bean1>();
	/**
	 * Bean1
	 */
	Bean1 bean = getMyFullBean();
	/**
	 * Lista me BoPropertyDescriptors
	 */
	List<BoPropertyDescriptor<?>>  descriptors =  getDescriptors();
	
	/**
	 * Create a Bean
	 * @return bean1
	 * 
	 */
	private Bean1 getMyFullBean() {
		Bean1 bean1 = new Bean1();
		bean1.setDescription("description1"); //$NON-NLS-1$
		bean1.setAmount(new BigDecimal(10000));
		return bean1;
	}
    
	/**
	 * Return one list with BoPropertyDescriptors
	 * @return List<BoPropertyDescriptor<?>>
	 */
	private List<BoPropertyDescriptor<?>> getDescriptors(){
		
		 Bean1descriptor descriptor =  new Bean1descriptor();
		 List<BoPropertyDescriptor<?>> descriptors = descriptor.getPropertyDescriptors();
		 return descriptors;
	}
	
	
	/**
	 * Test Get
	 */
	@Test 
	public void testGet(){
	
		busDesc.setPropertyDescriptors(descriptors);
	    assertNotNull(busDesc.get(bean));
	}
	
	
	/**
	 * Test Set
	 * @throws MultipleValidationsException
	 */
	@Test
	public void testSet() throws MultipleValidationsException{
		
		busDesc.setPropertyDescriptors(descriptors);
		Map<BoPropertyDescriptor<?>, Object> propertyValues = new HashMap<BoPropertyDescriptor<?>,Object>();
		
		String value = "building description"; //$NON-NLS-1$
		propertyValues.put(descriptors.get(1), value);
		
		busDesc.set(bean, propertyValues);
	}
	
	
	/**
	 * Test Validate
	 * @throws MultipleValidationsException 
	 */
	@Test 
	public void testValidate() throws MultipleValidationsException{
		
		StringBoPropertyDescriptor stringDesc = (StringBoPropertyDescriptor) descriptors.get(1);
		List<BoPropertyDescriptor<?>> list = new ArrayList<BoPropertyDescriptor<?>>();
		list.add(stringDesc);
		busDesc.setPropertyDescriptors(list);
		busDesc.validate(bean);
		
	}
	
	/**
	 * Test Validate
	 * @throws MultipleValidationsException 
	 */
	@Test (expected=MultipleValidationsException.class )
	public void testValidateDescriptor() throws MultipleValidationsException{
		
		busDesc.setPropertyDescriptors(descriptors);
		busDesc.validate(bean);
		
	}
	
	/**
	 * Test getLabel
	 */
	@Test 
	public void testGetLabel(){
		busDesc.setName("testName"); //$NON-NLS-1$
		busDesc.setLabel(null);
		Assert.assertNotNull(busDesc.getLabel());
		busDesc.setLabel("testName"); //$NON-NLS-1$
		Assert.assertNotNull(busDesc.getLabel());
	}

}
