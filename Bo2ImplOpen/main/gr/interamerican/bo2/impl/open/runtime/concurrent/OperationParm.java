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

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.utils.adapters.Modification;

import java.util.List;

/**
 * parameters for an operation so as to be launched with a runtime command.
 *
 * @param <T> the generic type
 */
public interface OperationParm<T> {

	/**
	 * Gets the name of this Operation.
	 *
	 * @return Returns the name
	 */
	String getName();

	/**
	 * Assigns a new value to the name.
	 *
	 * @param name
	 *            the name to set
	 */
	void setName(String name);

	/**
	 * Gets the operationClass.
	 *
	 * @return Returns the operationClass
	 */
	Class<? extends Operation> getOperationClass();

	/**
	 * Assigns a new value to the operationClass.
	 *
	 * @param operationClass
	 *            the operationClass to set
	 */
	void setOperationClass(Class<? extends Operation> operationClass);

	/**
	 * Gets the preProcessing operation.
	 *
	 * Operation that runs before the main processing.
	 * This operation can contain any initialization required before
	 * the main processing. If this parameter is null, then the
	 * batch process will omit the pre-processing step.
	 *
	 * @return Returns the preProcessing operation
	 */
	Operation getPreProcessing();

	/**
	 * Assigns a new value to the preProcessing operation.
	 *
	 * @param preProcessing
	 *            the preProcessing operation to set
	 */
	void setPreProcessing(Operation preProcessing);

	/**
	 * Gets the postProcessing operation.
	 *
	 * Operation that runs after the main processing has
	 * ended. This operation should take over the actions necessary
	 * after the end of the main-processing.
	 *
	 * @return Returns the postProcessing operation
	 */
	Operation getPostProcessing();

	/**
	 * Assigns a new value to the postProcessing operation.
	 *
	 * @param postProcessing
	 *            the postProcessing operation to set
	 */
	void setPostProcessing(Operation postProcessing);

	/**
	 * Modification that set any input parameters to the operation.
	 *
	 * @return Returns a modification that sets any input parameters to the operation.
	 */
	Modification<Object> getOperationParametersSetter();

	/**
	 * Sets the modification that will set any input parameter to the operation.
	 *
	 * @param operationParametersSetter the new operation parameters setter
	 */
	void setOperationParametersSetter(Modification<Object> operationParametersSetter);

	/**
	 * Modification that set any input parameters to the operation.
	 *
	 * @return Returns a modification that sets any input parameters to the operation.
	 */
	Modification<Object> getPreOperationParametersSetter();

	/**
	 * Sets the modification that will set any input parameter to the
	 * pre-processing operation.
	 *
	 * @param preOperationParametersSetter the new pre operation parameters setter
	 */
	void setPreOperationParametersSetter(Modification<Object> preOperationParametersSetter);

	/**
	 * Modification that set any input parameters to the
	 * pre-processing operation.
	 *
	 * @return Returns a modification that sets any input parameters
	 *         to the operation.
	 */
	Modification<Object> getPostOperationParametersSetter();

	/**
	 * Sets the modification that will set any input parameter to the
	 * post-processing operation.
	 *
	 * @param postOperationParametersSetter the new post operation parameters setter
	 */
	void setPostOperationParametersSetter(Modification<Object> postOperationParametersSetter);

	/**
	 * Gets a list of {@link NamedStreamDefinition}s that will be available
	 * for the Operation.
	 *
	 * @return Returns the named stream definitions for this batch process.
	 */
	List<NamedStreamDefinition> getNamedStreamDefinitions();

	/**
	 * Sets the named stream definitions.
	 *
	 * @param namedStreamDefinitions
	 *            The named stream definitions to set.
	 */
	void setNamedStreamDefinitions(List<NamedStreamDefinition> namedStreamDefinitions);
}
