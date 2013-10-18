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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.samples.utils.meta.Bean1;
import gr.interamerican.bo2.samples.utils.meta.Bean1descriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BigDecimalBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.StringBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.exceptions.MultipleValidationsException;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * 
 */
public class TestMediator {

	 /**
	 * Mediator
	 */
	Mediator mediator = Mediator.getInstance();
	/**
	 * Bean1
	 */
	Bean1 bean1 = getMyFullBean();
	/**
	 * Lista me descriptors
	 */
	List<BoPropertyDescriptor<?>> descriptors = getDescriptors();

	
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
	 * Test GetValue
	 * Gets the value of a property defined by a BoPropertyDescriptor
	 * on an object.
	 * 
	 */
	@Test
	public void testGetValue() {
		
	   StringBoPropertyDescriptor stringDesc =  (StringBoPropertyDescriptor) descriptors.get(1);	   
	   BigDecimalBoPropertyDescriptor doubleDesc =  (BigDecimalBoPropertyDescriptor) descriptors.get(5);

	   assertEquals("description1", mediator.getValue(stringDesc, bean1)); //$NON-NLS-1$ 
	   assertEquals(new BigDecimal("10000"), mediator.getValue(doubleDesc, bean1)); //$NON-NLS-1$

	}
	
	/**
	 * Test Set Value
	 * Sets the value to the property defined by the {@link BoPropertyDescriptor}.
	 * @throws ValidationException 
	 */
	@Test
	public void testSetValue() throws ValidationException {
		
		StringBoPropertyDescriptor stringDesc =  (StringBoPropertyDescriptor) descriptors.get(1);	
		String value = "description of buiding"; //$NON-NLS-1$
		mediator.setValue(stringDesc, value, bean1);
	}
	
	/**
	 * Test GetValues
	 * Gets the values of a beans properties as they are described
	 * by a {@link BusinessObjectDescriptor} that describes the bean.
	 */
	@Test
	public void testGetValues(){
		
		BusinessObjectDescriptor<Bean1> busDesc = new BasicBusinessObjectDescriptor<Bean1>();
		busDesc.setPropertyDescriptors(descriptors);
		assertNotNull(mediator.getValues(busDesc,bean1));
        
	}
	
	/**
	 * Test setValues
	 * Gets the values of a beans properties as they are described
	 * by a {@link BusinessObjectDescriptor} that describes the bean.
	 * @throws MultipleValidationsException 
	 */
	@Test
	public void testSetValues() throws MultipleValidationsException{
		
        Map<BoPropertyDescriptor<?>, Object> propertyValues = new HashMap<BoPropertyDescriptor<?>,Object>();
        StringBoPropertyDescriptor stringDesc =  (StringBoPropertyDescriptor) descriptors.get(1);	
		String value = "description"; //$NON-NLS-1$
		propertyValues.put(stringDesc, value);
		mediator.setValues(propertyValues,bean1);
	}
	
	/**
	 *  Test Validate
	 *  Validates a set of values each against a BoPropertyDescriptor.
	 * @throws MultipleValidationsException 
	 */
	@Test
	public void testValidateValues() throws MultipleValidationsException{
		
		BoPropertyDescriptor<String> stringDesc = new StringBoPropertyDescriptor();
		stringDesc.setNullAllowed(false);
		Map<BoPropertyDescriptor<?>, Object> propertyValues = new HashMap<BoPropertyDescriptor<?>,Object>();
        String value = "notNull"; //$NON-NLS-1$
    	propertyValues.put(stringDesc, value);
    	mediator.validate(propertyValues);
	}
	
		
	/**
	 *  Test Validate
	 *  Validates a set of values each against a BoPropertyDescriptor.
	 * @throws MultipleValidationsException 
	 */
	@Test(expected=MultipleValidationsException.class )
	public void testValidateNullValues() throws MultipleValidationsException{
		
		BoPropertyDescriptor<String> stringDesc = new StringBoPropertyDescriptor();
		stringDesc.setNullAllowed(false);
		Map<BoPropertyDescriptor<?>, Object> propertyValues = new HashMap<BoPropertyDescriptor<?>,Object>();
		propertyValues.put(stringDesc, null);
        mediator.validate(propertyValues);
	}
	
	
	/**
	 * Test Validate
	 * Validates the values of a bean against a {@link BusinessObjectDescriptor}
	 * that describes it.
	 * @throws MultipleValidationsException
	 */
	@Test (expected = MultipleValidationsException.class)
	public void testValidateDescriptor() throws MultipleValidationsException{
		
		BusinessObjectDescriptor<Bean1> busDesc = new BasicBusinessObjectDescriptor<Bean1>();
		busDesc.setPropertyDescriptors(descriptors);
		mediator.validate(busDesc,bean1);
	}
	
	/**
	 * Test Validate
	 * Validates the values of a bean against a {@link BusinessObjectDescriptor}
	 * that describes it.
	 * @throws MultipleValidationsException
	 */

	@Test
	public void testValidate() throws MultipleValidationsException{
		
		BusinessObjectDescriptor<Bean1> busDesc = new BasicBusinessObjectDescriptor<Bean1>();
		StringBoPropertyDescriptor stringDesc = (StringBoPropertyDescriptor) descriptors.get(1);
		List<BoPropertyDescriptor<?>> list = new ArrayList<BoPropertyDescriptor<?>>();
		list.add(stringDesc);
		busDesc.setPropertyDescriptors(list);
		mediator.validate(busDesc,bean1);
	}	
	
}
