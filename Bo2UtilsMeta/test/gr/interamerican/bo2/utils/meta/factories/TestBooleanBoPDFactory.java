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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.utils.meta.descriptors.BooleanBoPropertyDescriptor;
import gr.interamerican.samples.utils.meta.SamplePropertyDefinition;

import org.junit.Test;

/**
 * 
 */
public class TestBooleanBoPDFactory {
	
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
		pd.setDefaultValue("true");
		BooleanBoPropertyDescriptor descriptor = BooleanBoPDFactory.create(pd);
		assertTrue(descriptor.getDefaultValue());
	}
	
	/**
	 * test Create when fails
	 * 
	 */
	@SuppressWarnings({ "nls" })
	@Test
	public void testCreate_fail(){
		
		pd.fillSamplePropertyDefinition();
		pd.setDefaultValue("noBooleanValue");
		BooleanBoPropertyDescriptor descriptor = BooleanBoPDFactory.create(pd);
		assertFalse(descriptor.getDefaultValue());
	}

}
