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
package gr.interamerican.bo2.utils.adapters;

/**
 * Specification for a simple operation.
 */
public interface TransformationSpec {
	/**
	 * Gets the type of the argument.
	 * 
	 * @return Returns the type of the argument.
	 */
	public Class<?> getArgumentType();
	
	/**
	 * Gets the type of the result.
	 * 
	 * @return Returns the type of the result.
	 */
	public Class<?> getResultType();

}
