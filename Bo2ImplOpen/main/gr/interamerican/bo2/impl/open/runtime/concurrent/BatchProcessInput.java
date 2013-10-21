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

import gr.interamerican.bo2.arch.exceptions.TransactionManagerException;
import gr.interamerican.bo2.utils.attributes.Named;

/**
 * Bean that can be transformed to a {@link BatchProcessParm}.
 */
public interface BatchProcessInput extends Named {
	
	/**
	 * Gets the initialThreads.
	 * 
	 * Initial count of queue processors in the batch process. 
	 * The BatchProcess starts with as many processors as this
	 * number specifies.
	 *
	 * @return Returns the initialThreads
	 */
	Integer getInitialThreads();

	/**
	 * Assigns a new value to the initialThreads.
	 *
	 * @param initialThreads the initialThreads to set
	 */
	void setInitialThreads(Integer initialThreads);
	
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
	 * @param operationClassName the operationClassName to set
	 */
	void setOperationClassName(String operationClassName);
	
	/**
	 * Gets the queryClassName.
	 * 
     * Class name of the query of the batch process. 
	 *
	 * @return Returns the QueryClass
	 */
	String getQueryClassName();

	/**
	 * Assigns a new value to the queryClassName.
	 *
	 * @param queryClassName the queryClassName to set
	 */
	void setQueryClassName(String queryClassName);

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
	 * @param preProcessingOperationClassName the preProcessingOperationClassName to set
	 */
	void setPreProcessingOperationClassName(String preProcessingOperationClassName);

	/**
	 * Gets the postProcessingOperationClassName operation.
	 * 
	 *  Operation that runs after the main processing has
	 *  ended. This operation should take over the actions necessary
	 *  after the end of the main-processing.
	 *
	 * @return Returns the postProcessingOperationClassName
	 */
	String getPostProcessingOperationClassName();

	/**
	 * Assigns a new value to the postProcessingOperationClassName.
	 *
	 * @param postProcessingOperationClassName the postProcessingOperationClassName operation to set
	 */
	void setPostProcessingOperationClassName(String postProcessingOperationClassName);
	
	/**
	 * Gets the inputPropertyName.
	 * 
     * Name of the property of the operationClass that takes the
	 * input. Elements fetched by the query are set to this property
	 * before being processed by the operation. The type of this
	 * property must be such that it accepts the elements fetched
	 * by the query. 	  
	 *
	 * @return Returns the inputPropertyName
	 */
	String getInputPropertyName();

	/**
	 * Assigns a new value to the inputPropertyName.
	 *
	 * @param inputPropertyName the inputPropertyName to set
	 */
	void setInputPropertyName(String inputPropertyName);
	
	/**
	 * Gets the formatter.
	 * 
     * Class of the formatter. Each queue processor of this batch
	 * process has its own formatter that is instantiated by the 
	 * default factory. The formatters are used to print the
	 * processed elements in the log files.
	 * The formatter can be null. In this case a default formatter
	 * will be used that prints the result of the element's 
	 * <code>toString()</code> method.	  
	 *
	 * @return Returns the formatter
	 */
	String getFormatterClassName();

	/**
	 * Assigns a new value to the formatter class name.
	 * 
	 * Must be a thread safe formatter, because it is being used
	 * by all queueProcessors.
	 *
	 * @param formatterClassName the formatter to set
	 */
	void setFormatterClassName(String formatterClassName);
	
	/**
	 * Gets a string that contains a comma separated list of recipients 
	 * for a status mail. <br/>
	 * 
	 * If this parameter is not empty, then a monitoring mail will be
	 * sent periodically. The interval between two subsequent messages
	 * is defined by {@link #setMonitoringMailInterval(Integer)}.
	 *  
	 * @return Returns a string with the recipient mail addresses of the
	 *         monitoring mail.  
	 */
	String getMonitoringMailRecipients();
	
	/**
	 * Sets the recipients for a monitoring mail.
	 * 
	 * @param monitoringMailRecipients
	 *        List with the mail recipients. If this is null
	 *        or empty ,then no message will be sent.
	 */
	void setMonitoringMailRecipients(String monitoringMailRecipients);
	
	/**
	 * Gets the time interval in minutes between two subsequent 
	 * monitoring messages. If this parameter is set to null or
	 * empty, then no message will be sent.
	 *  
	 * @return Returns the monitoring mail interval.
	 */
	Integer getMonitoringMailInterval();
	
	/**
	 * Sets the time interval for a monitoring mail.
	 * 
	 * @param monitoringMailInterval
	 *        Interval in minutes
	 */
	void setMonitoringMailInterval(Integer monitoringMailInterval);
	
	/**
	 * Indicates if the operator can add threads using the UI.
	 * 
	 * @return Returns true if the operator can increase the number
	 *         of threads using the UI.
	 */
	Boolean getUiCanAddThreads();
	
	/**
	 * Specifies if the operator can add threads from the UI.
	 *  
	 * @param uiCanAddThreads
	 */
	void setUiCanAddThreads(Boolean uiCanAddThreads);
	
	/**
	 * Gets the time interval for refreshing the UI automatically
	 * in seconds. <br/>
	 * 
	 * If this property is zero or negative, then the UI will not
	 * be refreshed automatically.
	 * 
	 * @return Returns the time interval between two automatic 
	 *         refreshments of the UI.
	 */
	Integer getUiRefreshInterval();
	
	/**
	 * Gets the time interval for refreshing the UI automatically
	 * in seconds. <br/>
	 * 
	 * @param uiRefreshInterval
	 *        Time interval in seconds. If this value is zero or
	 *        negative, then there will be no automatic refreshing
	 *        of the UI.
	 */
	void setUiRefreshInterval(Integer uiRefreshInterval);
	
	/**
	 * Indicates if the batch process re-attempts when a 
	 * {@link TransactionManagerException} is caught.
	 * 
	 * @return Returns true if the batch process re-attempts when a 
	 *         {@link TransactionManagerException} is caught.
	 */
	Boolean getReattemptOnTmex();
	
	/**
	 * Specifies if the batch process re-attempts when a 
	 * {@link TransactionManagerException} is caught.
	 *  
	 * @param reattemptOnTmex
	 */
	void setReattemptOnTmex(Boolean reattemptOnTmex);

}
