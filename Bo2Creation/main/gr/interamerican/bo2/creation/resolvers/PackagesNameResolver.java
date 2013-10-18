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

import gr.interamerican.bo2.creation.NameResolver;

/**
 * Implementation of {@link NameResolver}.
 * 
 * This implementation expects that interfaces are placed inside a package
 * structure that has a <i>def</> package in its root. Implementations 
 * of the interfaces are placed inside an identical package structure that
 * has an <i>impl</> package in its root. Additionally the implementation
 * classes always have the <i>Impl suffix</i>. <br/.
 * Examples <br/>
 * The interface com.foo.def.Person is implemented by the class 
 * com.foo.impl.PersonImpl. <br/>  
 * The interface com.foo.def.domain.Address is implemented by the class 
 * com.foo.impl.domain.AddressImpl. <br/>  
 * 
 */
public class PackagesNameResolver implements NameResolver {
	
	/**
	 * dot.
	 */
	private static final String DOT = "."; //$NON-NLS-1$
	


	/**
	 * package for interfaces.
	 */
	private String interfacePackage; 
	/**
	 * package for implementations.
	 */
	private String implementationPackage; 

	/**
	 * Creates a new DefImplPackageNameResolver object. 
	 *
	 * @param interfacePackage
	 * @param implementationPackage
	 */
	public PackagesNameResolver(
			String interfacePackage,
			String implementationPackage) {
		super();
		this.interfacePackage = DOT + interfacePackage + DOT;
		this.implementationPackage = DOT + implementationPackage + DOT;
	}

	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.impl.open.creation.NameResolver#getImplementationFromInterface(java.lang.String)
	 */	
	public String getImplementationName(
			String interfaceName) {
		if (interfaceName == null) {
			return null;
		}
		if (interfaceName.contains(interfacePackage)) {
			return  
			interfaceName.replaceFirst(interfacePackage, implementationPackage);
		}
		return interfaceName; 
	}

	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.impl.open.creation.NameResolver#getInterfaceFromImplementation(java.lang.String)
	 */	
	public String getDeclarationName(String implementationName) {
		if (implementationName == null) {
			return null;
		}
		if (implementationName.contains(implementationPackage)) {
			return implementationName.replaceFirst
				(implementationPackage, interfacePackage).trim();
		}
		return implementationName;
	}

}
