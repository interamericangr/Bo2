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
import gr.interamerican.bo2.utils.meta.descriptors.DateBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.samples.utils.meta.SamplePropertyDefinition;

import org.junit.Test;

/**
 * 
 */
public class TestDateBoPDFactory {

	
	
	/**
	 * SamplePropertyDefinition
	 */
	SamplePropertyDefinition pd =  new SamplePropertyDefinition();
	

	/**
	 * Test Create
	 * @throws ParseException 
	 */
	@SuppressWarnings({ "nls" })
	@Test
	public void testCreate() throws ParseException{
		
		pd.fillSamplePropertyDefinition();
		String defaultValue = "2011-12-06";
		pd.setDefaultValue(defaultValue);
		DateBoPropertyDescriptor descriptor = DateBoPDFactory.create(pd);
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
		pd.setDefaultValue("noDate");
		DateBoPDFactory.create(pd);
	}
 
}
