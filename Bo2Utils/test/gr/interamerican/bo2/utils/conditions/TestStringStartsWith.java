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
package gr.interamerican.bo2.utils.conditions;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Unit Test of {@link StringStartsWith}.
 */
public class TestStringStartsWith {

	/**
	 * Test method for {@link StringStartsWith#check(String)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCheck() {
		Set<String> prefixes = new HashSet<String>();
		prefixes.add("foo");
		prefixes.add("mpe");
		Condition<String> condition = new StringStartsWith(prefixes);
		assertTrue(condition.check("foow"));
		assertFalse(condition.check("wfoo"));
		assertTrue(condition.check("mpe"));
	}
}