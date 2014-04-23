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
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.MONITOR_MESSAGE_INTERVAL;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.MONITOR_MESSAGE_RECIPIENTS;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.OPERATION_CLASS;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.POST_PROCESSING_CLASS;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.PRE_PROCESSING_CLASS;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.PROCESSORS_COUNT;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.QUERY_CLASS;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.REATTEMPT_ON_TMEX;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.SHARED_STREAM_NAMES;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.TIDY_INTERVAL;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames.UI_CAN_ADD_THREADS;
import static gr.interamerican.bo2.utils.CollectionUtils.getMandatoryProperty;
import static gr.interamerican.bo2.utils.StringConstants.COMMA;
import static gr.interamerican.bo2.utils.StringUtils.isNullOrBlank;
import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.modifications.CriteriaAwareCopyFromProperties;
import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.adapters.CopyFromBeans;
import gr.interamerican.bo2.utils.adapters.CopyFromProperties;
import gr.interamerican.bo2.utils.adapters.Modification;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;

import java.util.Enumeration;
import java.util.Map;
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
		String name = getMandatoryProperty(properties, BATCH_PROCESS_NAME).trim();
		String queryClassName = getMandatoryProperty(properties, QUERY_CLASS).trim();
		String operationClassName = getMandatoryProperty(properties, OPERATION_CLASS).trim();
		String inputProperty = getMandatoryProperty(properties, INPUT_PROPERTY).trim();
		EntitiesQuery query = (EntitiesQuery<?>)Factory.create(queryClassName);
		Class operationClass = Factory.getType(operationClassName);
		Class queryClass = query.getClass();
		input.setName(name);
		input.setQuery(query);
		input.setOperationClass(operationClass);
		input.setInputPropertyName(inputProperty);
		
		int initialThreads = getInitialThreads(properties);
		input.setInitialThreads(initialThreads);
		
		String entityHeader = properties.getProperty(ENTITY_HEADER);
		input.setEntityHeader(entityHeader);

		String formatterClassName = properties.getProperty(FORMATTER_CLASS);		
		Formatter formatter = createFormatter(formatterClassName);
		input.setFormatter(formatter);		
		
		
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
		
		String mailRecipients = properties.getProperty(MONITOR_MESSAGE_RECIPIENTS);
		input.setMonitoringMailRecipients(mailRecipients);
		
		String strMailInterval = properties.getProperty(MONITOR_MESSAGE_INTERVAL);
		int mailInterval = NumberUtils.string2Int(strMailInterval);
		input.setMonitoringMailInterval(mailInterval);
		
		String strAddThreads = properties.getProperty(UI_CAN_ADD_THREADS);
		boolean addTheads = StringUtils.string2Bool(strAddThreads);
		input.setUiCanAddThreads(addTheads);
		
		String strTidyInterval = properties.getProperty(TIDY_INTERVAL);
		int tidyInterval = NumberUtils.string2Int(strTidyInterval);
		input.setTidyInterval(tidyInterval);
		
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
		} else {
			return (Formatter<?>) Factory.create(formatterClassName.trim());			
		}	
	}
	
	/**
	 * Gets the initial threads.
	 * 
	 * @param properties	   
	 * 
	 * @return Returns the initial threads.
	 */	
	protected int getInitialThreads(Properties properties) {
		String strInitialThreads = properties.getProperty(PROCESSORS_COUNT);
		int initialThreads = NumberUtils.string2Int(strInitialThreads);
		if (initialThreads==0) {
			initialThreads=1;
		}
		return initialThreads;
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BatchProcessParm<?> createParameter(BatchProcessInput input, Object criteria, Map<String, String> inputFileDefinitions) {
		/*
		 * TODO: Remove this method.
		 */
		BatchProcessParm output = Factory.create(BatchProcessParm.class);
		
		output.setName(ExceptionUtils.notNull(input.getName()));
		output.setFormatter((Formatter)createFormatter(input.getFormatterClassName()));
		output.setInitialThreads(Utils.notNull(input.getInitialThreads(), 1));
		output.setInputPropertyName(ExceptionUtils.notNull(input.getInputPropertyName()));
		output.setMonitoringMailInterval(Utils.notNull(input.getMonitoringMailInterval(), 0)); //0 is never send
		output.setMonitoringMailRecipients(input.getMonitoringMailRecipients());
		
		
		String operationClassName = ExceptionUtils.notNull(input.getOperationClassName()).trim();
		output.setOperationClass((Class<? extends Operation>) Factory.getType(operationClassName));
		
		output.setPostProcessing(optionalOperation(input.getPostProcessingOperationClassName()));
		output.setPreProcessing(optionalOperation(input.getPreProcessingOperationClassName()));
		
		String queryClassName = ExceptionUtils.notNull(input.getQueryClassName());
		output.setQuery((EntitiesQuery<?>) Factory.create(queryClassName.trim()));
		
		output.setUiCanAddThreads(Utils.notNull(input.getUiCanAddThreads(), false));
		
		
		
		if(criteria!=null) {
			Modification<Object> copy = new CopyFromBeans<Object>(criteria);
			output.setOperationParametersSetter(copy);
			output.setPostOperationParametersSetter(copy);
			output.setPreOperationParametersSetter(copy);
			output.setQueryParametersSetter(copy);
		}
		
		output.setNamedInputFiles(inputFileDefinitions);
		
		output.setReattemptOnTmex(Utils.notNull(input.getReattemptOnTmex(), true));
		
		return output;
	}
	
	/**
	 * Returns an optional operation instance or null.
	 * 
	 * @param operationClassName
	 * @return Operation instance.
	 */
	Operation optionalOperation(String operationClassName) {
		if (!isNullOrBlank(operationClassName)) {
			return (Operation) Factory.create(operationClassName.trim());
		}
		return null;
	}

}
