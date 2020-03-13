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

import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchOperationParmNames.BATCH_PROCESS_NAME;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchOperationParmNames.FIELDS_SET;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchOperationParmNames.OPERATION_CLASS;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchOperationParmNames.POST_PROCESSING_CLASS;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchOperationParmNames.PRE_PROCESSING_CLASS;
import static gr.interamerican.bo2.utils.CollectionUtils.getMandatoryProperty;
import static gr.interamerican.bo2.utils.StringUtils.isNullOrBlank;
import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.modifications.CriteriaAwareCopyFromProperties;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.utils.adapters.Modification;
import gr.interamerican.bo2.utils.adapters.mod.CopyFromProperties;

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * implementation of {@link OperationParmFactory}.
 */
public class OperationParmFactoryImpl implements OperationParmFactory {

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public OperationParm<?> createParameter(Properties properties) {
		OperationParm<?> input = Factory.create(OperationParm.class);
		String name = getMandatoryProperty(properties, BATCH_PROCESS_NAME).trim();
		input.setName(name);
		String operationClassName = getMandatoryProperty(properties, OPERATION_CLASS).trim();
		Class operationClass = Factory.getType(operationClassName);
		input.setOperationClass(operationClass);
		Modification<Object> copyToOperation = createModification(properties, operationClass);
		input.setOperationParametersSetter(copyToOperation);
		String preProcessClassName = properties.getProperty(PRE_PROCESSING_CLASS);
		if (!isNullOrBlank(preProcessClassName)) {
			Operation preProcess = (Operation) Factory.create(preProcessClassName.trim());
			input.setPreProcessing(preProcess);
			Modification<Object> copyToPreProcess = createModification(properties,
					preProcess.getClass());
			input.setPreOperationParametersSetter(copyToPreProcess);
		}
		String postProcessClassName = properties.getProperty(POST_PROCESSING_CLASS);
		if (!isNullOrBlank(postProcessClassName)) {
			Operation postProcess = (Operation) Factory.create(postProcessClassName.trim());
			Modification<Object> copyToPostProcess = createModification(properties,
					postProcess.getClass());
			input.setPostProcessing(postProcess);
			input.setPostOperationParametersSetter(copyToPostProcess);
		}
		List<NamedStreamDefinition> namedStreamDefinitions = createNamedStreamDefitions();
		input.setNamedStreamDefinitions(namedStreamDefinitions);
		return input;
	}

	/**
	 * Creates the {@link Modification} that will copy the additional
	 * parameters.
	 *
	 * @param properties
	 *            Properties from which the object will be modified.
	 * @param clazz
	 *            Class of the object.
	 *
	 * @return Returns the modification.
	 */
	@SuppressWarnings("unchecked")
	protected Modification<Object> createModification(Properties properties, Class<?> clazz) {
		Properties p = new Properties();
		Enumeration<?> names = properties.propertyNames();
		int count = 0;
		while (names.hasMoreElements()) {
			String key = (String) names.nextElement();
			if (!FIELDS_SET.contains(key)) {
				String value = properties.getProperty(key);
				p.setProperty(key, value);
				count++;
			}
		}
		if (count == 0) {
			return null;
		}
		Modification<Object> copy;
		if (CriteriaDependent.class.isAssignableFrom(clazz)) {
			@SuppressWarnings("rawtypes")
			CriteriaAwareCopyFromProperties cacfp = new CriteriaAwareCopyFromProperties(p);
			copy = cacfp;
		} else {
			copy = new CopyFromProperties<Object>(p);
		}
		return copy;
	}

	/**
	 * Creates the list of named stream definitions.
	 *
	 * The default implementation of this method, returns null.
	 * Sub-classes can override this method to give a different
	 * implementation.
	 *
	 * @return Returns a list of named stream definitions or
	 *         null, if there is no named stream definition for
	 *         for the batch process.
	 */
	protected List<NamedStreamDefinition> createNamedStreamDefitions() {
		return null;
	}
}
