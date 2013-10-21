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

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.exceptions.TransactionManagerException;
import gr.interamerican.bo2.utils.adapters.Modification;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;

import java.util.Map;

/**
 * Parameters for the creation of a {@link BatchProcess}.
 * 
 *   
 * @param <T>
 *        Type of object being processed by the batch process. 
 */
public interface BatchProcessParm<T> {

	/**
	 * Gets the name of this BatchProcess.
	 *
	 * @return Returns the name
	 */
	String getName();

	/**
	 * Assigns a new value to the name.
	 *
	 * @param name the name to set
	 */
	void setName(String name);

	/**
	 * Gets the initialThreads.
	 * 
	 * Initial count of queue processors in the batch process. 
	 * The BatchProcess starts with as many processors as this
	 * number specifies.
	 *
	 * @return Returns the initialThreads
	 */
	int getInitialThreads();

	/**
	 * Assigns a new value to the initialThreads.
	 *
	 * @param initialThreads the initialThreads to set
	 */
	void setInitialThreads(int initialThreads);

	/**
	 * Gets the query that fetches the elements that will be
	 * processed by the batch process.
	 *
	 * @return Returns the query
	 */
	EntitiesQuery<T> getQuery();

	/**
	 * Assigns a new value to the query.
	 *
	 * @param query the query to set
	 */
	void setQuery(EntitiesQuery<T> query);

	/**
	 * Gets the operationClass.
	 * 
     * Class of the operation that operates on each elements fetched 
	 * by the query of the batch process. 
	 *
	 * @return Returns the operationClass
	 */
	Class<? extends Operation> getOperationClass();

	/**
	 * Assigns a new value to the operationClass.
	 *
	 * @param operationClass the operationClass to set
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
	 * @param preProcessing the preProcessing operation to set
	 */
	void setPreProcessing(Operation preProcessing);

	/**
	 * Gets the postProcessing operation.
	 * 
	 *  Operation that runs after the main processing has
	 *  ended. This operation should take over the actions necessary
	 *  after the end of the main-processing.
	 *
	 * @return Returns the postProcessing operation
	 */
	Operation getPostProcessing();

	/**
	 * Assigns a new value to the postProcessing operation.
	 *
	 * @param postProcessing the postProcessing operation to set
	 */
	void setPostProcessing(Operation postProcessing);

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
	Formatter<T> getFormatter();

	/**
	 * Assigns a new value to the formatter.
	 * 
	 * Must be a thread safe formatter, because it is being used
	 * by all queueProcessors.
	 *
	 * @param formatter the formatter to set
	 */
	void setFormatter(Formatter<T> formatter);
	
	/**
	 * Modification that set any input parameters to the query.
	 * 
	 * @return Returns a modification that sets any input parameters to the query.
	 */
	Modification<Object> getQueryParametersSetter(); 
	
	/**
	 * Sets the modification that will set any input parameter to the query.
	 * 
	 * @param queryParametersSetter
	 */
	void setQueryParametersSetter(Modification<Object> queryParametersSetter);
	
	/**
	 * Modification that set any input parameters to the operation.
	 * 
	 * @return Returns a modification that sets any input parameters to the operation.
	 */
	Modification<Object> getOperationParametersSetter(); 
	
	/**
	 * Sets the modification that will set any input parameter to the operation.
	 * 
	 * @param operationParametersSetter
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
	 * @param preOperationParametersSetter
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
	 * @param postOperationParametersSetter
	 */
	void setPostOperationParametersSetter(Modification<Object> postOperationParametersSetter);
	
	/**
	 * Gets a string that contains a comma separated list of recipiens 
	 * for a status mail. <br/>
	 * 
	 * If this parameter is not empty, then a monitoring mail will be
	 * sent periodically. The interval between two subsequent messages
	 * is defined by {@link #setMonitoringMailInterval(int)}.
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
	int getMonitoringMailInterval();
	
	/**
	 * Sets the time interval for a monitoring mail.
	 * 
	 * @param monitoringMailInterval
	 *        Interval in minutes
	 */
	void setMonitoringMailInterval(int monitoringMailInterval);
	
	/**
	 * Indicates if the operator can add threads using the UI.
	 * 
	 * @return Returns true if the operator can increase the number
	 *         of threads using the UI.
	 */
	boolean getUiCanAddThreads();
	
	/**
	 * Specifies if the operator can add threads from the UI.
	 *  
	 * @param uiCanAddThreads
	 */
	void setUiCanAddThreads(boolean uiCanAddThreads);
	
	/**
	 * Gets the input files for this batch process. The key
	 * is the logical name of the stream and the value the actual
	 * filesystem path.
	 * <br/>
	 * This may be null indicating that there are no input
	 * files to setup.
	 * 
	 * TODO: This property exists for the support of submitted jobs running in parallel. It will be refactored.  
	 *       
	 * 
	 * @return Returns input files for this batch process.
	 */
	Map<String, String> getNamedInputFiles();
	
	/**
	 * Sets the input files for this batch process.
	 * 
	 * TODO: This property exists for the support of submitted jobs running in parallel. It will be refactored.
	 * 
	 * @param namedInputFiles
	 */
	void setNamedInputFiles(Map<String, String> namedInputFiles);
	
	/**
	 * Gets the time interval in minutes between two subsequent 
	 * calls to tidy.
	 *  
	 * @return Returns the monitoring mail interval.
	 */
	int getTidyInterval();
	
	/**
	 * Sets the time interval for tidying the batch process.
	 * 
	 * @param tidyInterval
	 *        Interval in minutes
	 */
	void setTidyInterval(int tidyInterval);
	
	/**
	 * Gets the header row to print on the log files.
	 * 
	 * @return returns the entity header.
	 */
	String getEntityHeader();
	
	/**
	 * Sets the entityHeader.
	 * 
	 * @param entityHeader
	 */
	void setEntityHeader(String entityHeader);
	
	/**
	 * Gets the names of shared streams.
	 * 
	 * @return returns the names of shared streams.
	 */
	String[] getSharedStreamNames();
	
	/**
	 * Sets the sharedStreamNames.
	 * 
	 * @param sharedStreamNames
	 */
	void setSharedStreamNames(String[] sharedStreamNames);
	
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
