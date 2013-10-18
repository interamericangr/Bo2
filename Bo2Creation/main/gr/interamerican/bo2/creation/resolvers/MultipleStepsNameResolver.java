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
 * Implementation of {@link NameResolver} based on an array
 * of other NameResolvers.
 * 
 * The NameResolvers are applied sequentially.
 */
public class MultipleStepsNameResolver 
implements NameResolver {
	
	/**
	 * Resolvers used sequentially to resolve the declaration name.
	 */
	private NameResolver[] resolvers; 


	/**
	 * Creates a new MultipleStepsNameResolver object.
	 *  
	 * @param resolvers 
	 */
	public MultipleStepsNameResolver(NameResolver... resolvers) {
		super();
		this.resolvers = resolvers;
	}

	
	public String getImplementationName(String interfaceName) {
		String result=interfaceName;
		for (int i = 0; i < resolvers.length; i++) {
			result = resolvers[i].getImplementationName(result);
		}
		return result;
	}

	public String getDeclarationName(String implementationName) {		
		String result=implementationName;
		for (int i = resolvers.length-1; i >=0; i--) {
			result = resolvers[i].getDeclarationName(result);
		}
		return result;
	}
	
	

}
