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
 * 
 */
public class TestBLabel {
	
	/**
	 * Sample name.
	 */
	String name = "sample"; //$NON-NLS-1$
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		BLabel sample = new BLabel(name);
		Assert.assertEquals(name, sample.name);		
	}
	
	/**
	 * Tests getName().
	 */
	@Test
	public void testGetName() {
		BLabel sample = new BLabel("X"); //$NON-NLS-1$
		Assert.assertEquals(sample.name,sample.getComponentName());		
	}
	
	/**
	 * Tests getValue().
	 */
	@Test
	public void testGetValue() {
		BLabel sample = new BLabel(name);
		String text = "text"; //$NON-NLS-1$
		sample.setText(text);
		Assert.assertEquals(text,sample.getValue());		
	}
	
	/**
	 * Tests getValue().
	 */
	@Test
	public void testSetValue() {
		BLabel sample = new BLabel(name);
		String text = "text"; //$NON-NLS-1$
		sample.setValue(text);
		Assert.assertEquals(text,sample.getText());		
	}

}
