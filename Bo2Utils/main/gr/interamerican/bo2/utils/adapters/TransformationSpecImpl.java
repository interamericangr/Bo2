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
 * Simple implementation of {@link TransformationSpec}.
 */
public class TransformationSpecImpl {
	
	/**
	 * Type of the argument.
	 */
	Class<?> argumentType;
	
	/**
	 * Type of the result.
	 */
	Class<?> resultType;

	/**
	 * Creates a new AnyOperationSpecImpl object. 
	 *
	 * @param argumentType
	 * @param resultType
	 */
	public TransformationSpecImpl(Class<?> argumentType, Class<?> resultType) {
		super();
		this.argumentType = argumentType;
		this.resultType = resultType;
	}
	
	/**
	 * Creates a new AnyOperationSpecImpl object. 
	 *
	 */
	public TransformationSpecImpl() {
		super();
	}

	/**
	 * Gets the argumentType.
	 *
	 * @return Returns the argumentType
	 */
	public Class<?> getArgumentType() {
		return argumentType;
	}

	/**
	 * Assigns a new value to the argumentType.
	 *
	 * @param argumentType the argumentType to set
	 */
	public void setArgumentType(Class<?> argumentType) {
		this.argumentType = argumentType;
	}

	/**
	 * Gets the resultType.
	 *
	 * @return Returns the resultType
	 */
	public Class<?> getResultType() {
		return resultType;
	}

	/**
	 * Assigns a new value to the resultType.
	 *
	 * @param resultType the resultType to set
	 */
	public void setResultType(Class<?> resultType) {
		this.resultType = resultType;
	}
	
	

}
