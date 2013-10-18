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

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests form {@link RegexUtils}.
 */
public class TestRegexUtils {
	
	/**
	 * Test getMatches().
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetMatches() {
		String value = "this is a ${var} and a 2nd ${var2} reference";
		String pattern = "\\$\\{.*?\\}";
		List<String> matches = RegexUtils.getMatches(value, pattern);
		Assert.assertEquals("${var}", matches.get(0));
		Assert.assertEquals("${var2}", matches.get(1));
		
		value = "no match";
		matches = RegexUtils.getMatches(value, pattern);
		Assert.assertTrue(matches.isEmpty());
	}

}
