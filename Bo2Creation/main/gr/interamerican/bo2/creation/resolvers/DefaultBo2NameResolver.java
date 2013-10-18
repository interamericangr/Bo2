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
 * Default {@link NameResolver} for Bo2. 
 */
public class DefaultBo2NameResolver 
extends MultipleStepsNameResolver {

	/**
	 * Creates a new DefaultBo2Resolver object. 
	 */
	@SuppressWarnings("nls")
	public DefaultBo2NameResolver() {
		super(
			new PackagesNameResolver("def", "impl"),
			new SuffixNameResolver("Impl"));
	}
	
	

}
