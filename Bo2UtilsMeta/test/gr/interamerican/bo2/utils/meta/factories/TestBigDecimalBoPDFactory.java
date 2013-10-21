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
import gr.interamerican.bo2.utils.meta.descriptors.BigDecimalBoPropertyDescriptor;
import gr.interamerican.samples.utils.meta.SamplePropertyDefinition;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestBigDecimalBoPDFactory {

	
	
	/**
	 * SamplePropertyDefinition
	 */
	SamplePropertyDefinition pd =  new SamplePropertyDefinition();
	
	
	/**
	 * Test create
	 */
	@SuppressWarnings({ "nls" })
	@Test
	public void testCreate(){
		

		pd.fillSamplePropertyDefinition();
		pd.setDefaultValue("100");
		BigDecimalBoPropertyDescriptor descriptor = BigDecimalBoPDFactory.create(pd);
		
		assertEquals((Integer)2,(Integer)descriptor.getLengthOfDecimalPart());
		assertEquals((Integer)10,(Integer)descriptor.getLengthOfIntegerPart());
		assertEquals("pdName",descriptor.getName());
	}
	
	/**
	 * test Create when fails
	 * 
	 */
	@SuppressWarnings({ "nls" })
	@Test(expected = RuntimeException.class)
	public void testCreate_fail() {
		
		pd.fillSamplePropertyDefinition();
		pd.setDefaultValue("noNumber");
		BigDecimalBoPDFactory.create(pd);
	}
	
	/**
	 * Test for the generated max length
	 */
	@Test
	public void testMaxLength(){
		pd =  new SamplePropertyDefinition();
		pd.fillSamplePropertyDefinitionForMaxLength();
		BigDecimalBoPropertyDescriptor descriptor = BigDecimalBoPDFactory.create(pd);
		Assert.assertEquals(descriptor.getMaxLength(), 10);
		pd.setNegativeAllowed(true);
		descriptor = BigDecimalBoPDFactory.create(pd);
		Assert.assertEquals(descriptor.getMaxLength(), 11);
		pd.setDecimalLength(10);
		descriptor = BigDecimalBoPDFactory.create(pd);
		Assert.assertEquals(descriptor.getMaxLength(), 22);
	}

 
}
