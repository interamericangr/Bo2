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
package gr.interamerican.bo2.creation.resolvers;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.creation.NameResolver;

import org.junit.Test;

/**
 * Unit test for {@link PackagesNameResolver}.
 */
public class TestMultipleStepsNameResolver {
	
	
	
	/**
	 * Suffixes added by the resolvers.
	 */
	@SuppressWarnings("nls")
	private static String[] suffixes = {"suf", "ser"};
	
	/**
	 * NameResolvers.
	 */
	NameResolver[] resolvers = {
		new SuffixNameResolver(suffixes[0]),
		new SuffixNameResolver(suffixes[1]),
	};
	
	/**
	 * NameResolver being tested.
	 */	
	MultipleStepsNameResolver resolver = 
		new MultipleStepsNameResolver(resolvers);
	
	/**
	 * Suffix.
	 */
	private String suffix = suffixes[0] + suffixes[1];
	
	
	/**
	 * Tests getDeclarationName().
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetDeclarationName() {		
		String name = "com.foo.def.domain.Product";
		String expected = name;		
		String actual = resolver.getDeclarationName(name + suffix); 
		assertEquals(expected, actual);
	}

	
	
		
	/**
	 * Tests getImplementationName().
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetImplementationOneOccurence() {		
		String name = "com.foo.impl.domain.Product";
		String expected = name + suffix;		
		String actual = resolver.getImplementationName(name); 
		assertEquals(expected, actual);
	}


}
