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


/**
 * Suffix name resolver, that has a default constructor.
 * 
 * The suffix can be defined with a call to the static method
 * <code>predefineSuffix(string)</code> at system initialization.
 * 
 */
public class PredefinedSuffixNameResolver extends SuffixNameResolver {
	
	/**
	 * Suffix.
	 */
	private static String predefinedSuffix = "Implementation"; //$NON-NLS-1$

	/**
	 * Gets the predefinedSuffix.
	 *
	 * @return Returns the predefinedSuffix
	 */
	public static String getPredefinedSuffix() {
		return predefinedSuffix;
	}

	/**
	 * Assigns a new value to the predefinedSuffix.
	 *
	 * @param predefinedSuffix the predefinedSuffix to set
	 */
	public static void setPredefinedSuffix(String predefinedSuffix) {
		PredefinedSuffixNameResolver.predefinedSuffix = predefinedSuffix;
	}
	
	/**
	 * Creates a new PredefinedSuffixNameResolver object. 
	 */
	public PredefinedSuffixNameResolver() {
		super(predefinedSuffix);
	}
	
	
}
