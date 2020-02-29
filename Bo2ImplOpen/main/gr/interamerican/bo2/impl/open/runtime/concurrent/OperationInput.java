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
package gr.interamerican.bo2.impl.open.runtime.concurrent;

import gr.interamerican.bo2.arch.Named;

/**
 * Bean that can be transformed to a
 * {@link gr.interamerican.bo2.impl.open.runtime.concurrent.OperationParm}.
 */
public interface OperationInput extends Named {

	/**
	 * Gets the operationClassName.
	 *
	 * Class name of the operation that operates on each elements fetched
	 * by the query of the batch process.
	 *
	 * @return Returns the operationClass
	 */
	String getOperationClassName();

	/**
	 * Assigns a new value to the operationClassName.
	 *
	 * @param operationClassName
	 *            the operationClassName to set
	 */
	void setOperationClassName(String operationClassName);

	/**
	 * Gets the preProcessingOperationClassName.
	 *
	 * Class name of the operation that runs before the main processing.
	 * This operation can contain any initialization required before
	 * the main processing. If this parameter is null, then the
	 * batch process will omit the pre-processing step.
	 *
	 * @return Returns the preProcessing operation
	 */
	String getPreProcessingOperationClassName();

	/**
	 * Assigns a new value to the preProcessingOperationClassName.
	 *
	 * @param preProcessingOperationClassName
	 *            the preProcessingOperationClassName to set
	 */
	void setPreProcessingOperationClassName(String preProcessingOperationClassName);

	/**
	 * Gets the postProcessingOperationClassName operation.
	 *
	 * Operation that runs after the main processing has
	 * ended. This operation should take over the actions necessary
	 * after the end of the main-processing.
	 *
	 * @return Returns the postProcessingOperationClassName
	 */
	String getPostProcessingOperationClassName();

	/**
	 * Assigns a new value to the postProcessingOperationClassName.
	 *
	 * @param postProcessingOperationClassName
	 *            the postProcessingOperationClassName operation to set
	 */
	void setPostProcessingOperationClassName(String postProcessingOperationClassName);
}
