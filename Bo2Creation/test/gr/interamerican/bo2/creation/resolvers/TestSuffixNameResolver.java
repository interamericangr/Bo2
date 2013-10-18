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
import gr.interamerican.bo2.creation.NameResolver;

import org.junit.Test;

/**
 * Unit test for {@link SuffixNameResolver}.
 */
public class TestSuffixNameResolver {
	
	/**
	 * Name resolver being tested.
	 */
	private NameResolver resolver = 
		new SuffixNameResolver("Impl"); //$NON-NLS-1$
	
	/**
	 * Tests getInterfaceFromImplementation() when the implementation
	 * class contains more than one times the suffix. 
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetDeclarationMoreSuffixOccurences() {		
		String implementationName = "com.foo.domain.TheImplementedProductImpl";
		String expected = "com.foo.domain.TheImplementedProduct";		
		String actual =
			resolver.getDeclarationName(implementationName);			
		assertEquals(expected, actual);
	}
	
		
	/**
	 * Tests getInterfaceFromImplementation() when the implementation
	 * class contains no suffix. 
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetDeclarationNoSuffixOccurence() {		
		String implementationName = "com.foo.domain.Product";
		String expected = "com.foo.domain.Product";		
		String actual =
			resolver.getDeclarationName(implementationName);			
		assertEquals(expected, actual);
	}
	
	/**
	 * Tests getInterfaceFromImplementation() when the implementation
	 * class just ends with suffix. 
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetDeclarationOneSuffixOccurence() {		
		String implementationName = "com.foo.domain.ProductImpl";
		String expected = "com.foo.domain.Product";		
		String actual =
			resolver.getDeclarationName(implementationName);			
		assertEquals(expected, actual);
	}
	
	
	/**
	 * Tests getImplementationFromInterface()
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetImplementationFromInterface() {		
		String interfaceName = "com.foo.domain.Product";
		String expected = "com.foo.domain.ProductImpl";		
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
	 * Tests getImplementationName() when interfaceName is null
	 */
	@Test
	public void testGetDeclarationName_NullValue() {		
		assertNull(resolver.getDeclarationName(null));
	}

}
