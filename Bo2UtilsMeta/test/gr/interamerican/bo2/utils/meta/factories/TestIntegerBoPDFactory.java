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
package gr.interamerican.bo2.utils.meta.factories;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.utils.meta.descriptors.IntegerBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.samples.utils.meta.SamplePropertyDefinition;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestIntegerBoPDFactory {

	
	
	/**
	 * SamplePropertyDefinition
	 */
	SamplePropertyDefinition pd =  new SamplePropertyDefinition();
	
	
	/**
	 * Test create
	 * @throws ParseException 
	 */
	@SuppressWarnings({ "nls" })
	@Test
	public void testCreate() throws ParseException{
		

		pd.fillSamplePropertyDefinition();
		pd.setDefaultValue("100");
		IntegerBoPropertyDescriptor descriptor = IntegerBoPDFactory.create(pd);
		
		assertEquals((Integer)0,(Integer)descriptor.getLengthOfDecimalPart());
		assertEquals((Integer)10,(Integer)descriptor.getLengthOfIntegerPart());
		assertEquals("pdName",descriptor.getName());
	}
	
	/**
	 * test Create when fails
	 * @throws ParseException 
	 * 
	 */
	@SuppressWarnings({ "nls" })
	@Test(expected = ParseException.class)
	public void testCreate_fail() throws ParseException{
		
		pd.fillSamplePropertyDefinition();
		pd.setDefaultValue("noNumber");
		IntegerBoPDFactory.create(pd);
	}
	
	/**
	 * Test for the generated max length
	 * @throws ParseException 
	 */
	@Test
	public void testMaxLength() throws ParseException{
		pd =  new SamplePropertyDefinition();
		pd.fillSamplePropertyDefinitionForMaxLength();
		IntegerBoPropertyDescriptor descriptor = IntegerBoPDFactory.create(pd);
		Assert.assertEquals(descriptor.getMaxLength(), 10);
		pd.setNegativeAllowed(true);
		descriptor = IntegerBoPDFactory.create(pd);
		Assert.assertEquals(descriptor.getMaxLength(), 11);
		pd.setDecimalLength(10);
		descriptor = IntegerBoPDFactory.create(pd);
		Assert.assertEquals(descriptor.getMaxLength(), 11);
	}	
 
}
