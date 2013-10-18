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
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Unit test for {@link PackagesNameResolver}.
 */
public class TestPackagesNameResolver {
	
	/**
	 * NameResolver being tested.
	 */
	@SuppressWarnings("nls")
	private PackagesNameResolver resolver = 
		new PackagesNameResolver("def","impl");
	
	/**
	 * Tests getDeclaration() when the implementation type name
	 * contains only one occurence of the def package.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetDeclarationOneOccurence() {		
		String implementationName = "com.foo.impl.domain.Product";
		String expected = "com.foo.def.domain.Product";		
		String actual = 
			resolver.getDeclarationName(implementationName); 
		assertEquals(expected, actual);
	}
	
	
	/**
	 * Tests getDeclaration() when the implementation type name
	 * contains only one occurence of the def package.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetDeclarationMultipleOccurences() {		
		String implementationName = "com.foo.impl.domain.impl.Product";
		String expected = "com.foo.def.domain.impl.Product";		
		String actual = 
			resolver.getDeclarationName(implementationName); 
		assertEquals(expected, actual);
	}
	
	
	/**
	 * Tests getDeclaration() when the implementation type name
	 * contains only one occurence of the def package.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetDeclarationNoOccurence() {		
		String implementationName = "com.foo.implementation.domain.Product";
		String expected = "com.foo.implementation.domain.Product";		
		String actual = 
			resolver.getDeclarationName(implementationName); 
		assertEquals(expected, actual);
	}
	
	
		
	/**
	 * Tests getImplementationFromInterface() when there is only
	 * one occurence of the def package in the interface name.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetImplementationOneOccurence() {		
		String interfaceName = "com.foo.def.domain.Product";
		String expected = "com.foo.impl.domain.Product";		
		String actual =
			resolver.getImplementationName(interfaceName);
		assertEquals(expected, actual);
	}
	
	
	/**
	 * Tests getImplementationFromInterface() when there no
	 * occurence of the def package in the interface name.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetImplementationNoOccurence() {		
		String interfaceName = "com.foo.bizdef.domain.Product";
		String expected = "com.foo.bizdef.domain.Product";		
		String actual =
			resolver.getImplementationName(interfaceName);
		assertEquals(expected, actual);
	}
	
	/**
	 * Tests getImplementationFromInterface() when there no
	 * occurence of the def package in the interface name.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetImplementationMultipleOccurences() {		
		String interfaceName = "com.foo.def.domain.def.Product";
		String expected = "com.foo.impl.domain.def.Product";		
		String actual =
			resolver.getImplementationName(interfaceName);
		assertEquals(expected, actual);
	}

	/**
	 * Tests getImplementationName() when interfaceName is null
	 */
	@Test
	public void testGetImplementationName_NullValue() {		
		assertNull(resolver.getImplementationName(null));
	}
	
	/**
	 * Tests getImplementationName() when implementationName is null
	 */
	@Test
	public void testGetDeclarationName_NullValue() {		
		assertNull(resolver.getDeclarationName(null));
	}

}
