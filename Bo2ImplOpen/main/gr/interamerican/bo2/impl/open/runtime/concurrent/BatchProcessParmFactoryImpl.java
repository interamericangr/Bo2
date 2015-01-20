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

import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.BATCH_PROCESS_NAME;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.ENTITY_HEADER;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.FIELDS_SET;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.FORMATTER_CLASS;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.INPUT_PROPERTY;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.OPERATION_CLASS;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.POST_PROCESSING_CLASS;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.PRE_PROCESSING_CLASS;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.PROCESSORS_COUNT;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.QUERY_CLASS;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.REATTEMPT_ON_TMEX;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.SHARED_STREAM_NAMES;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.UI_CAN_ADD_THREADS;
import static gr.interamerican.bo2.utils.CollectionUtils.getMandatoryProperty;
import static gr.interamerican.bo2.utils.StringConstants.COMMA;
import static gr.interamerican.bo2.utils.StringUtils.isNullOrBlank;
import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.modifications.CriteriaAwareCopyFromProperties;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.adapters.Modification;
import gr.interamerican.bo2.utils.adapters.mod.CopyFromProperties;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * Implementation of {@link BatchProcessParmsFactory}.
 *
 */
public class BatchProcessParmFactoryImpl
implements BatchProcessParmsFactory {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public BatchProcessParm createParameter(Properties properties) {

		BatchProcessParm<?> input = Factory.create(BatchProcessParm.class);
		input.setBatchProcessInputAsText(String.valueOf(properties));

		String name = getMandatoryProperty(properties, BATCH_PROCESS_NAME).trim();
		input.setName(name);

		String queryClassName = getMandatoryProperty(properties, QUERY_CLASS).trim();
		EntitiesQuery query = (EntitiesQuery<?>)Factory.create(queryClassName);
		input.setQuery(query);

		String operationClassName = getMandatoryProperty(properties, OPERATION_CLASS).trim();
		Class operationClass = Factory.getType(operationClassName);
		input.setOperationClass(operationClass);

		String inputProperty = getMandatoryProperty(properties, INPUT_PROPERTY).trim();
		input.setInputPropertyName(inputProperty);

		int initialThreads = calculateInitialThreads(properties);
		input.setInitialThreads(initialThreads);

		String entityHeader = properties.getProperty(ENTITY_HEADER);
		input.setEntityHeader(entityHeader);

		String formatterClassName = properties.getProperty(FORMATTER_CLASS);
		Formatter formatter = createFormatter(formatterClassName);
		input.setFormatter(formatter);

		Class queryClass = query.getClass();
		Modification<Object> copyToQuery = createModification(properties,queryClass);
		input.setQueryParametersSetter(copyToQuery);

		Modification<Object> copyToOperation = createModification(properties,operationClass);
		input.setOperationParametersSetter(copyToOperation);

		String preProcessClassName = properties.getProperty(PRE_PROCESSING_CLASS);
		if (!isNullOrBlank(preProcessClassName)) {
			Operation preProcess = (Operation) Factory.create(preProcessClassName.trim());
			input.setPreProcessing(preProcess);
			Modification<Object> copyToPreProcess = createModification(properties,preProcess.getClass());
			input.setPreOperationParametersSetter(copyToPreProcess);
		}

		String postProcessClassName = properties.getProperty(POST_PROCESSING_CLASS);
		if (!isNullOrBlank(postProcessClassName)) {
			Operation postProcess = (Operation) Factory.create(postProcessClassName.trim());
			Modification<Object> copyToPostProcess = createModification(properties,postProcess.getClass());
			input.setPostProcessing(postProcess);
			input.setPostOperationParametersSetter(copyToPostProcess);
		}

		String strAddThreads = properties.getProperty(UI_CAN_ADD_THREADS);
		boolean addTheads = StringUtils.string2Bool(strAddThreads);
		input.setUiCanAddThreads(addTheads);

		String strNamedStreams = Utils.notNull(properties.getProperty(SHARED_STREAM_NAMES), StringConstants.EMPTY);
		String[] sharedStreams = TokenUtils.splitTrim(strNamedStreams, COMMA, false);
		input.setSharedStreamNames(sharedStreams);

		String strReattemptOnTmex = properties.getProperty(REATTEMPT_ON_TMEX);
		if(StringUtils.isNullOrBlank(strReattemptOnTmex)) {
			input.setReattemptOnTmex(true);
		} else {
			boolean reattemptOnTmex = StringUtils.string2Bool(strReattemptOnTmex);
			input.setReattemptOnTmex(reattemptOnTmex);
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
	 *        Properties from which the object will be modified.
	 * @param clazz
	 *        Class of the object.
	 *
	 * @return Returns the modification.
	 */
	@SuppressWarnings("unchecked")
	protected Modification<Object> createModification(Properties properties, Class<?> clazz) {
		Properties p = new Properties();
		Enumeration<?> names = properties.propertyNames();
		int count = 0;
		while (names.hasMoreElements()) {
			String key = (String)names.nextElement();
			if (!FIELDS_SET.contains(key)) {
				String value = properties.getProperty(key);
				p.setProperty(key, value);
				count++;
			}
		}
		if (count==0) {
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
	 * Creates the Formatter according to the specified class name.
	 *
	 * @param formatterClassName
	 *
	 * @return Returns the modification.
	 */
	protected Formatter<?> createFormatter(String formatterClassName) {
		if (isNullOrBlank(formatterClassName)) {
			return ObjectFormatter.INSTANCE;
		}
		return (Formatter<?>) Factory.create(formatterClassName.trim());
	}

	/**
	 * Gets the initial threads.
	 *
	 * @param properties
	 *
	 * @return Returns the initial threads.
	 */
	protected int calculateInitialThreads(Properties properties) {
		String strInitialThreads = properties.getProperty(PROCESSORS_COUNT);
		int initialThreads = NumberUtils.string2Int(strInitialThreads);
		if (initialThreads==0) {
			initialThreads=1;
		}
		return initialThreads;
	}

	/**
	 * Returns an optional operation instance or null.
	 *
	 * @param operationClassName
	 * @return Operation instance.
	 */
	Operation createOptionalOperation(String operationClassName) {
		if (!isNullOrBlank(operationClassName)) {
			return (Operation) Factory.create(operationClassName.trim());
		}
		return null;
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
