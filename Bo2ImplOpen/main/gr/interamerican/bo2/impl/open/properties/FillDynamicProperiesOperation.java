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
/*
 * Created on 5 …·Ì 2005
 *
 *
 */
package gr.interamerican.bo2.impl.open.properties;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.utils.Util;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;

import java.util.Map;
import java.util.Properties;

/**
 * Operation that fills dynamic properties in a properties object.
 * 
 * Dynamic properties are properties that have a value returned by a
 * {@link Parameter} object or that have their value copied from another
 * property. <br/>
 * Dynamic properties are marked with a specific prefix. The value of this
 * prefix is defined by the static member <code>DYNAPARM_PREFIX</code>.
 * Similarly, copied properties are marked with a prefix that is defined by the
 * static member <code>COPYPARM_PREFIX</code>. The default values of this
 * prefixes are defined in the file paramteres.properties file that exists in
 * the package <code>gr.interamerican.bo2.impl.open.utils</code>.
 * 
 */
public class FillDynamicProperiesOperation extends AbstractOperation {

	/**
	 * prefix for dynamic parameters.
	 */
	public static final String DYNAPARM_PREFIX = Util
			.getParameter("Program.DYNAPARM_PREFIX"); //$NON-NLS-1$


	/**
	 * prefix for copied parameters.
	 */
	public static final String COPYPARM_PREFIX = Util
			.getParameter("Program.COPYPARM_PREFIX"); //$NON-NLS-1$

	/**
	 * empty string.
	 */
	private static final String EMPTY = ""; //$NON-NLS-1$

	/**
	 * input properties as loaded from the input properties file.
	 */
	private Properties inputProperties;

	/**
	 * input properties after parsing the input properties file and replacing
	 * dynamic parameters with their values.
	 */
	private Properties outputProperties;
	
	/**
	 * Initializes the simple properties from the simple input parameters.
	 * 
	 * Simple are properties that are not dynamic or copied. These are the first
	 * properties to initialize.
	 * 
	 * @see #outputProperties
	 * @see #inputProperties
	 */
	private void initializeSimpleProperties() {		
		for (Map.Entry<Object, Object> entry : inputProperties.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			boolean isSimple = (!isDynamicParameter(value))
					&& (!isCopiedParameter(value));
			if (isSimple) {
				outputProperties.put(key, value);
			}
		}
	}

	/**
	 * Initializes the imput properties from the dynamic input parameters.
	 * 
	 * Dynamic are properties that have their value calculated by a
	 * {@link Parameter} object.
	 * 
	 * @throws DataException
	 * @throws InitializationException
	 * 
	 * @see #outputProperties
	 * @see #inputProperties
	 * @see #isDynamicParameter(String)
	 */
	private void initializeDynamicProperties() 
	throws DataException, InitializationException {		
		for (Map.Entry<Object, Object> entry : inputProperties.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			if (isDynamicParameter(value)) {
				Object calculatedValue = getPropertyFromParameterClass(value);
				outputProperties.put(key, calculatedValue);
			}
		}
	}

	/**
	 * Initializes the copied properties from the copied input parameters.
	 * 
	 * Copied are properties that get the same value as another input parameter.
	 * 
	 * @see #outputProperties
	 * @see #inputProperties
	 */
	private void initializeCopiedProperties() {		
		for (Map.Entry<Object, Object> entry : inputProperties.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			if (isCopiedParameter(value)) {
				String keyForCopy = value.replace(COPYPARM_PREFIX, EMPTY);
				Object calculatedValue = outputProperties.get(keyForCopy);
				/*
				 * calculated value can be null.
				 */
				outputProperties.put(key, calculatedValue);
			}
		}
	}

	/**
	 * Indicates if the value of a program parameter defines a dynamic value
	 * that will be calculated by a {@link Parameter} object.
	 * 
	 * Dynamic parameters are defined with a specific prefix that indicates that
	 * this value will be calculated by a Parameter object. The prefix is
	 * defined by the static field <code>DYNAPARM_PREFIX</code>.
	 * 
	 * 
	 * @param value
	 *            String for the value of the parameter.
	 * 
	 * @return Returns trueif the value does start with a literal that indicates
	 *         dynamic parameter.
	 * 
	 * @see #DYNAPARM_PREFIX
	 */
	private boolean isDynamicParameter(String value) {
		return value.startsWith(DYNAPARM_PREFIX);
	}

	/**
	 * Indicates if the value of a program parameter defines a copy of another
	 * parameter.
	 * 
	 * Copied parameters are defined with a specific prefix that indicates that
	 * this value will be calculated by a Parameter object. The prefix is
	 * defined by the static field <code>DYNAPARM_PREFIX</code>.
	 * 
	 * 
	 * @param value
	 *            String for the value of the parameter.
	 * 
	 * @return Returns trueif the value does start with a literal that indicates
	 *         dynamic parameter.
	 * 
	 * @see #COPYPARM_PREFIX
	 */
	private boolean isCopiedParameter(String value) {
		return value.startsWith(COPYPARM_PREFIX);
	}

	/**
	 * Gets the value of a dynamic input parameter from a {@link Parameter}
	 * object.
	 * 
	 * @param value
	 * @return Returns the value of the dynamic parameter.
	 * 
	 * @throws DataException
	 * @throws InitializationException
	 */
	private Object getPropertyFromParameterClass(String value)
			throws DataException, InitializationException {
		try {
			String class_ = value.replace(DYNAPARM_PREFIX, EMPTY);
			Parameter<?> parameter = 
				(Parameter<?>) Class.forName(class_).newInstance();
			parameter.init(getProvider());
			parameter.open();
			Object v = parameter.getValue();
			parameter.close();
			return v;
		} catch (InstantiationException e) {
			throw new DataException(e);
		} catch (IllegalAccessException e) {
			throw new DataException(e);
		} catch (ClassNotFoundException e) {
			throw new DataException(e);
		}
	}

	
	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.impl.open.AbstractOperation#execute()
	 */
	@Override
	public void execute() 
	throws LogicException, DataException {	
		outputProperties = new Properties();
		initializeSimpleProperties();
		try {
			initializeDynamicProperties();
		} catch (InitializationException e) {
			throw new DataException(e);
		} 		
		initializeCopiedProperties();
	}
	
	/**
	 * Gets the inputProperties.
	 *
	 * @return Returns the inputProperties
	 */
	public Properties getInputProperties() {
		return inputProperties;
	}

	/**
	 * Assigns a new value to the inputProperties.
	 *
	 * @param inputProperties the inputProperties to set
	 */
	public void setInputProperties(Properties inputProperties) {
		this.inputProperties = inputProperties;
	}
	
	/**
	 * Gets the output properties of this operation.
	 * 
	 * @return Returns the the output properties of this operation.
	 */
	public Properties getOutputProperties() {
		return outputProperties;
	}

}
