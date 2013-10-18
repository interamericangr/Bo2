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
 * Resolver between Interface class names and Implementation class names.
 * 
 * This implementation just adds a suffix to the declaration type name.
 * 
 */
public class SuffixNameResolver implements NameResolver {
	
	/**
	 * suffix for implementations.
	 */
	private String suffix; 

	/**
	 * Creates a new SuffixNameResolver object. 
	 *
	 * @param suffix Suffix
	 */
	public SuffixNameResolver(String suffix) {
		super();
		this.suffix = suffix;
	}

	
	public String getImplementationName(
			String interfaceName) {
		if (interfaceName == null) {
			return null;
		}
		return interfaceName.trim() + suffix; 
	}

	
	public String getDeclarationName(String implementationName) {
		if (implementationName == null) {
			return null;
		}
		if (implementationName.endsWith(suffix)) {
			int index = implementationName.lastIndexOf(suffix);
			if (index>0) {
				return implementationName.substring(0, index); 
			}
		}		 
		return implementationName;
	}

}
