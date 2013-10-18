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
 * Unit test for {@link SuffixNameResolver}.
 */
public class TestDefaultBo2NameResolver {
	
	/**
	 * Name resolver being tested.
	 */
	private NameResolver resolver = 
		new DefaultBo2NameResolver();
	
	
	/**
	 * Tests getInterfaceFromImplementation() when the implementation
	 * class has not been created by Javassist. 
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetInterfaceFromImplementationForConcreteClasses() {		
		String implementationName = "com.foo.domain.Product";
		String expected = "com.foo.domain.Product";		
		String actual = 
			resolver.getDeclarationName(implementationName); 
		assertEquals(expected, actual);
	}
	
	
	
	/**
	 * Tests getInterfaceFromImplementation() when the implementation
	 * class has not been created by Javassist. 
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetInterfaceFromImplementationForFactored() {		
		String implementationName = "com.foo.impl.domain.ProductImpl";
		String expected = "com.foo.def.domain.Product";		
		String actual = 
			resolver.getDeclarationName(implementationName); 
		assertEquals(expected, actual);
	}
	
	/**
	 * Tests getInterfaceFromImplementation() when the implementation
	 * class has been created by Javassist. 
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetInterfaceFromImplementationForConcrete() {		
		String implementationName = "com.foo.impl.domain.ProductImpl";
		String expected = "com.foo.def.domain.Product";		
		String actual =
			resolver.getDeclarationName(implementationName);			
		assertEquals(expected, actual);
	}
	
	/**
	 * Tests getImplementationFromInterface() when interface name 
	 * contains a def package.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetImplementationFromInterfaceWithDef() {		
		String interfaceName = "com.foo.def.domain.Product";
		String expected = "com.foo.impl.domain.ProductImpl";		
		String actual =
			resolver.getImplementationName(interfaceName);
		assertEquals(expected, actual);
	}
	
	/**
	 * Tests getImplementationFromInterface() when interface name 
	 * does not contains a def package.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetImplementationFromInterfaceWithoutDef() {		
		String interfaceName = "com.foo.domain.Product";
		String expected = "com.foo.domain.ProductImpl";		
		String actual =
			resolver.getImplementationName(interfaceName);
		assertEquals(expected, actual);
	}

}
