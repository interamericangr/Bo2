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
import gr.interamerican.bo2.samples.bean.ClassA;
import gr.interamerican.bo2.samples.bean.ClassB;

import org.junit.Test;

/**
 * 
 */
public class TestEqualPropertiesMatchingRule {
	
	/**
	 * rule.
	 */
	private EqualPropertiesMatchingRule<ClassA, ClassB> rule;
	
	/**
	 * tests isMatch()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testIsMatch() {
		ClassA a = new ClassA("a", 1);
		ClassB b = new ClassB("b", 1);
		String[] properties = {"stringA"};
		
		rule = new EqualPropertiesMatchingRule<ClassA, ClassB>(
				ClassA.class, ClassB.class, properties);
		assertFalse(rule.isMatch(a, b));
		
	}
	
	/**
	 * tests isMatch()
	 */
	@SuppressWarnings("nls")
	@Test (expected = RuntimeException.class)
	public void testErrors() {
		String[] properties = {"integerA"};
		rule = new EqualPropertiesMatchingRule<ClassA, ClassB>(
				ClassA.class, ClassB.class,properties );
	}
	

}
