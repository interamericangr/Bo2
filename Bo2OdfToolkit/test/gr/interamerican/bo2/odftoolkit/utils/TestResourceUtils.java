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
package gr.interamerican.bo2.odftoolkit.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * The Class TestResourceUtils.
 */
public class TestResourceUtils {
	
	/**
	 * Unit test for oneLevelUp(string).
	 */
	@SuppressWarnings("nls")
	@Test
	public void testOneLevelUp() {
		Assert.assertEquals("", ResourceUtils.oneLevelUp("/"));
		Assert.assertEquals("", ResourceUtils.oneLevelUp("c:/"));
		Assert.assertEquals("/", ResourceUtils.oneLevelUp("/home/"));		
		Assert.assertEquals("/home/", ResourceUtils.oneLevelUp("/home/bin/"));
		Assert.assertEquals("/home/bin/", ResourceUtils.oneLevelUp("/home/bin/classes/"));
	}

}
