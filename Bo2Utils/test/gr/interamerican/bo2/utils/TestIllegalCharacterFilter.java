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

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link IllegalCharacterFilter}.
 */
public class TestIllegalCharacterFilter {
	
	/**
	 * test filter()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testFilter() {
		String s = "";
		Assert.assertEquals(s, IllegalCharacterFilter.SINGLETON.filter(s));
		
		s = "¢Û‰asd_.!";
		Assert.assertEquals(s, IllegalCharacterFilter.SINGLETON.filter(s));
		
		s = " poi$%^324";
		Assert.assertEquals(s, IllegalCharacterFilter.SINGLETON.filter(s));
		
		s = "\u0004legal";
		Assert.assertEquals("legal", IllegalCharacterFilter.SINGLETON.filter(s));
		
		IllegalCharacterFilter.registerAssociation('\u0004', " ");
		Assert.assertEquals(" legal", IllegalCharacterFilter.SINGLETON.filter(s));
	}

}
