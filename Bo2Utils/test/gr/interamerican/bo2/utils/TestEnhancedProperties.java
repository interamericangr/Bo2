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
package gr.interamerican.bo2.utils;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestEnhancedProperties {
	
	@SuppressWarnings("nls")
	@Test
	public void testGetProperty() throws IOException {
		EnhancedProperties dp = new EnhancedProperties();
		dp.load(this.getClass().getResourceAsStream("/gr/interamerican/bo2/utils/dprops1.properties"));
		Assert.assertEquals("a", dp.get("a"));
		Assert.assertEquals("b", dp.get("b"));
		
		dp.load(this.getClass().getResourceAsStream("/gr/interamerican/bo2/utils/dprops2.properties"));
		Assert.assertEquals("a", dp.get("imported.property"));
		Assert.assertEquals("", dp.get("imported.property.null"));
		Assert.assertEquals("", dp.get("b"));
		Assert.assertEquals("a", dp.get("c"));
		Assert.assertEquals("a", dp.get("d"));
		Assert.assertEquals("a", dp.get("e"));
		Assert.assertEquals("a", dp.get("f"));
		Assert.assertEquals("g", dp.get("g"));
		Assert.assertEquals("aaaaag", dp.get("h"));
		
	}
	
	@SuppressWarnings("nls")
	@Test(expected = RuntimeException.class)
	public void testLoad_cyclic() throws IOException {
		EnhancedProperties dp = new EnhancedProperties();
		dp.load(this.getClass().getResourceAsStream("/gr/interamerican/bo2/utils/dprops3.properties"));
	}
	
	@SuppressWarnings("nls")
	@Test(expected = RuntimeException.class)
	public void testLoad_unreachable() throws IOException {
		EnhancedProperties dp = new EnhancedProperties();
		dp.load(this.getClass().getResourceAsStream("/gr/interamerican/bo2/utils/dprops4.properties"));
	}

}
