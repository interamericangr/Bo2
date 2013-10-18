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
package gr.interamerican.bo2.gui.components;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link BCheckBox}.
 */
public class TestBCheckBox {
	/**
	 * Sample name.
	 */
	String name = "sample"; //$NON-NLS-1$
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		BCheckBox sample = new BCheckBox(name);
		Assert.assertEquals(name, sample.componentName);		
	}
	
	/**
	 * Tests getName().
	 */
	@Test
	public void testGetComponentName() {
		BCheckBox sample = new BCheckBox("X"); //$NON-NLS-1$
		Assert.assertEquals(sample.componentName,sample.getComponentName());		
	}
	
	/**
	 * Tests getValue().
	 */
	@Test
	public void testGetValue() {
		BCheckBox sample = new BCheckBox(name);
		sample.setSelected(true);		
		Assert.assertEquals(Boolean.TRUE,sample.getValue());		
	}
	
	/**
	 * Tests setValue().
	 */
	@Test
	public void testSetValue_true() {
		BCheckBox sample = new BCheckBox(name);		
		sample.setValue(true);
		Assert.assertTrue(sample.isSelected());		
	}
	
	/**
	 * Tests setValue().
	 */
	@Test
	public void testSetValue_false() {
		BCheckBox sample = new BCheckBox(name);		
		sample.setValue(false);
		Assert.assertFalse(sample.isSelected());		
	}
	
	/**
	 * Tests setValue().
	 */
	@Test
	public void testSetValue_null() {
		BCheckBox sample = new BCheckBox(name);		
		sample.setValue(null);
		Assert.assertFalse(sample.isSelected());		
	}
	
	

}
