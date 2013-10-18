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
package gr.interamerican.bo2.utils.meta.ext.factories;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.ext.descriptors.MoneyBoPropertyDescriptor;
import gr.interamerican.samples.utils.meta.SamplePropertyDefinition;

import org.junit.Test;

/**
 * 
 */
public class TestMoneyBoPDFactory {

	
	
	/**
	 * SamplePropertyDefinition
	 */
	SamplePropertyDefinition pd =  new SamplePropertyDefinition();
	
	/**
	 * decimalLength
	 */
	Integer decimalLength = 2;
	/**
	 * integerLength
	 */
	Integer integerLength = 10;
	
	/**
	 * name
	 */
	@SuppressWarnings("nls")
	String name = "moneyPd";
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@SuppressWarnings({ "nls" })
	@Test
	public void testCreate() throws ParseException{
		
		pd.fillSamplePropertyDefinition();
		pd.setDefaultValue("100");
		MoneyBoPropertyDescriptor descriptor = MoneyBoPDFactory.create(pd);
		
		assertEquals((Integer)2,(Integer)descriptor.getLengthOfDecimalPart());
		assertEquals((Integer)10,(Integer)descriptor.getLengthOfIntegerPart());
		assertEquals(descriptor.getName(),"pdName");
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
		MoneyBoPDFactory.create(pd);
	}
	

}
