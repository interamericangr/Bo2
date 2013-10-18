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
package gr.interamerican.bo2.utils.matching;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.samples.bean.ClassA;
import gr.interamerican.bo2.samples.bean.ClassB;

import org.junit.Test;

/**
 * 
 */
public class TestEqualPropertyMatchingRule {
	
	/**
	 * rule.
	 */
	private EqualPropertyMatchingRule<ClassA, ClassB> rule;
	
	/**
	 * tests isMatch()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testIsMatch() {
		ClassA a = new ClassA("a", 1);
		ClassB b = new ClassB("b", 1);
		
		rule = new EqualPropertyMatchingRule<ClassA, ClassB>(
				ClassA.class, ClassB.class, "stringA", "stringB");
		assertFalse(rule.isMatch(a, b));
		
		rule = new EqualPropertyMatchingRule<ClassA, ClassB>(
				ClassA.class, ClassB.class, "integerA", "integerB");
		assertTrue(rule.isMatch(a, b));
	}
	
	/**
	 * tests isMatch()
	 */
	@SuppressWarnings("nls")
	@Test (expected = RuntimeException.class)
	public void testErrors() {
		rule = new EqualPropertyMatchingRule<ClassA, ClassB>(
				ClassA.class, ClassB.class, "nonExisting", "nonExisting");
	}


}
