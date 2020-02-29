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

import gr.interamerican.bo2.utils.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * Names of {@link BatchOperation} parameters.
 */
public class BatchOperationParmNames {
	
	/**
	 * Property key for name.
	 */
	public static final String BATCH_PROCESS_NAME = "batchProcessName"; //$NON-NLS-1$
	/**
	 * Property key for operation class.
	 */
	public static final String OPERATION_CLASS = "operationClass"; //$NON-NLS-1$
	/**
	 * Property key for the pre-processing operation class.
	 */
	public static final String PRE_PROCESSING_CLASS = "preProcessingClass"; //$NON-NLS-1$
	/**
	 * Property key for the post processing operation class.
	 */
	public static final String POST_PROCESSING_CLASS = "postProcessingClass"; //$NON-NLS-1$
	
	/**
	 * Property key for operation class.
	 */
	public static final String PARAMETERS_FACTORY_CLASS = "parametersFactoryClass"; //$NON-NLS-1$
	
	/**
	 * Fields.
	 */
	public static final String[] FIELDS = { BATCH_PROCESS_NAME, OPERATION_CLASS, PRE_PROCESSING_CLASS,
			POST_PROCESSING_CLASS, PARAMETERS_FACTORY_CLASS

	};

	/**
	 * Temporary set.
	 */
	private static final Set<String> FSET = 
		CollectionUtils.addAll(new HashSet<String>(), FIELDS);
	
	
	/**
	 * Fields set.
	 */
	public static final Set<String> FIELDS_SET = Collections.unmodifiableSet(FSET);
}