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
import gr.interamerican.bo2.utils.meta.descriptors.StringBoPropertyDescriptor;
import gr.interamerican.samples.utils.meta.SamplePropertyDefinition;

import org.junit.Test;

/**
 * 
 */
public class TestStringBoPDFactory {

	
	
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
		
		pd.setMinLength(10);
		pd.setMaxLength(100);
		pd.fillSamplePropertyDefinition();
		pd.setDefaultValue("stringValue");
		StringBoPropertyDescriptor descriptor = StringBoPDFactory.create(pd);
		
		assertEquals((Integer)10,(Integer)descriptor.getMinLength());
		assertEquals((Integer)100,(Integer)descriptor.getMaxLength());
		assertEquals("pdName",descriptor.getName());
	}	
	
 
}
