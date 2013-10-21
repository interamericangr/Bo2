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

import static gr.interamerican.bo2.utils.StringConstants.NEWLINE;
import static gr.interamerican.bo2.utils.StringConstants.SEMICOLON;
import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.arch.exceptions.CouldNotRollbackException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.TransactionManagerException;
import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.namedstreams.NamedPrintStream;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.adapters.Modification;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;

import java.util.Date;
import java.util.Queue;

/**
 * QueueProcessor is a Runnable object that pulls a queue with the input
 * objects of a batch process and processes each of them in an atomic
 * transaction.
 * If processing fails, then the element is counted as a failure.
 * If processing succeeds but a {@link TransactionManagerException}
 * occurs, then the element is returned to the queue.
 * 
 * @param <T>
 *        Type of objects in the queue. 
 * 
 */
public class QueueProcessor<T> 
implements Runnable, LongProcess {
		
	/**
	 * Name of this QueueProcessor.
	 */
	String streamname;
	/**
	 * Name of this QueueProcessor.
	 */
	String name;
	/**
	 * Operation to execute for each object in the queue.
	 */
	Operation operation;
	/**
	 * Name of the operation's property for setting the input.
	 */
	String inputPropertyName;	
	/**
	 * Formatter used to print the elements.
	 */
	Formatter<T> formatter;	
	
	/**
	 * Queue with input elements.
	 */
	Queue<T> inputQueue;
	
	/**
	 * Start time.
	 */
	Date startTime;
	
	/**
	 * endTime. 
	 */
	Date endTime;
	
	
	/**
	 * End of data;
	 */
	boolean eod = false;
	/**
	 * Quit indicator.
	 */
	boolean quit = false;
	/**
	 * Indicates that the processing has finished.
	 */
	boolean finished = false;
	/**
	 * Indicates that processing has been paused.
	 */
	boolean paused = false;
	/**
	 * Indicates that processing has been paused.
	 */
	boolean reattemptOnTmex = true;	
	/**
	 * Operation to replace the currently running operation.
	 */
	Operation newOperation = null;		
	
	/**
	 * Count of objects processed
	 */
	long processedCount = 0;
	/**
	 * Count of successes.
	 */
	long successesCount = 0;
	/**
	 * Count of failures.
	 */
	long failuresCount = 0;	

	
	/**
	 * Log file for successfully processed elements.
	 */
	NamedPrintStream successesLog;
	/**
	 * Log file for not successfully processed elements.
	 */
	NamedPrintStream failuresLog;
	/**
	 * Log file for stack traces of not successfully processed elements.
	 * This log file is optional, the NamedStream may not exist.
	 */
	NamedPrintStream stacktracesLog;
	
	/**
	 * Provider used by this processor.
	 */
	Provider provider;
	/**
	 * TransactionManager.
	 */
	TransactionManager tm;
	/**
	 * Exception message.
	 */
	String exceptionMessage = null;
	
	/**
	 * Session.
	 */
	Session<?, ?> session;
	
	/**
	 * Modification that will set any input parameters to the operation.
	 */
	Modification<Object> operationParametersSetter;
	
	/**
	 * Creates a new QueueProcessor object. 
	 * @param inputQueue 
	 *        Queue with the inputs to be processed.
	 *        If this queue processor is going to operate in a multi threaded
	 *        environment, then this queue should be a thread safe queue.
	 * @param streamname 
	 *        Stream name. 
	 * @param name 
	 *        Name of this QueueProcessor. 
	 *        The name is used to distinguish this QueueProcessor.
	 * @param operation
	 *        Operation used by this QueueProcessor to process the 
	 *        elements in the queue. <br/>
	 *        The operation must have only one setter method that is used to 
	 *        set its input. I.e. the operation must take only one input that 
	 *        is set by the setter of the property specified with 
	 *        <code>inputPropertyName</code> 
	 * @param inputPropertyName
	 *        Name of the property of the operation class that describes
	 *        the input.  
	 * @param formatter 
	 *        Formatter used to create a string representation of each
	 *        input object. The string representation is used for writing
	 *        in the log file.
	 * @param operationParametersSetter 
	 *        Modification that will set any input parameters to the operation.
	 *        Can be null, if the operation needs no additional parameters
	 *        that the input fetched by the queue. 
	 * @param reattemptOnTmex 
	 *        Indicates if rows for which a {@link TransactionManagerException}
	 *        is caught are re-attempted.
	 */
	public QueueProcessor(Queue<T> inputQueue, String streamname, String name, Operation operation, String inputPropertyName, 
			Formatter<T> formatter, Modification<Object> operationParametersSetter, Boolean reattemptOnTmex) {
		super();
		this.inputQueue = inputQueue; 
		this.name = name;
		this.streamname = streamname; 		
		this.operation = operation;
		this.inputPropertyName = inputPropertyName;		
		this.formatter = formatter; 
		this.operationParametersSetter = operationParametersSetter;
		this.session = Bo2Session.getSession();
		ReflectionUtils.mandatoryPropertyOfClass(inputPropertyName, operation.getClass());
		startTime = new Date();
		if(reattemptOnTmex!=null) {
			this.reattemptOnTmex = reattemptOnTmex;
		}
	}
	
	/**
	 * Sets the input object.
	 * @param input
	 */
	void setInput(T input) {
		ReflectionUtils.setProperty(inputPropertyName, input, operation);
	}
	
	/**
	 * Initializes this QueueProcessor. <br/>
	 * 
	 * This method must be called only from within the <code>run()</code>
	 * method, so that it is executed to the main thread of the queue
	 * processor.
	 * 
	 * @return Returns true if initialization was successful, otherwise 
	 *         returns false.
	 */
	boolean initialize() {
		try {
			/*
			 * Copy the session from the owner's thread where the constructor
			 * was executed to the this processor's main thread where the
			 * run method executes.
			 */
			Bo2Session.setSession(session);  
			
			provider = Bo2.getDefaultDeployment().getProvider();
			Bo2Session.setProvider(provider);
			tm = provider.getTransactionManager();			
			if (operationParametersSetter!=null) {
				operationParametersSetter.execute(operation);				
			}			
			operation.init(provider);
			operation.open();
			
			successesLog = SharedStreams.successes(provider, streamname);
			failuresLog = SharedStreams.failures(provider, streamname);
			stacktracesLog = SharedStreams.optionalStacktraces(provider, streamname);
			
			return true;
		} catch (DataException de) {
			handle(de);
			forceQuit();
			return false;
		} catch (InitializationException ie) {
			handle(ie);
			forceQuit();
			return false;
		}
	}
	
	/**
	 * Cleanup that must be done after processing.
	 * 
	 * @throws DataException
	 */
	public void close() 
	throws DataException {		
		operation.close();
		provider.close();
		Bo2Session.setProvider(null);
	}
	
	@Override
	public void run() {		
		initialize();
		loop();
	}
	
	/**
	 * Exception safe call to <code>close()</code>.
	 * 
	 * DataException thrown by close() are loged to the System.err
	 * stream. <br/>
	 * This method is called only <code>renewPollAndProcess()</code>.
	 */
	private void safeClose() {
		try {
			close();			
		} catch (DataException de) {
			de.printStackTrace();
		}
	}
	
	/**
	 * Handles the main loop that is executed inside <code>run()</code>.
	 */
	void loop() {
		finished = false;
		while (doContinue()) {
			if (paused) {
				ThreadUtils.sleep(1);
			} else {				
				keepProcessing();				
			}
		}
		end();
	}
	
	/**
	 * Keeps processing.
	 */
	void keepProcessing() {
		if (newOperation!=null) {
			renewPollAndProcess();
		} else {
			pollAndProcess();					
		}
	}
	
	/**
	 * Renews the operation and then polls and processes the
	 * next element in the queue.
	 */
	void renewPollAndProcess() {
		safeClose();
		operation = newOperation;
		newOperation=null;
		if (initialize()) { 
			pollAndProcess();
		}
	}
	
	/**
	 * Polls the next element from the queue and processes it.
	 */
	void pollAndProcess() {
		T input = inputQueue.poll();
		if (input!=null) {				
			try {
				process(input);
			} catch (TransactionManagerException e) {
				if(reattemptOnTmex) {
					inputQueue.add(input);
				}
				exceptionMessage = ExceptionUtils.getThrowableStackTrace(e);
				tidy();
				logFailure(input, e);
			}
		}
	}
	
	/**
	 * Continuation condition.
	 * 
	 * @return Returns true if the processing has to be continued.
	 */
	boolean doContinue() {
		if (eod && inputQueue.isEmpty()) {
			return false;
		}
		return !quit;
	}
	
	/**
	 * Process the specified input.
	 * 
	 * @param input
	 * 
	 * @throws TransactionManagerException 
	 */
	void process(T input) 
	throws TransactionManagerException {
		try {
			processedCount++;
			tm.begin();
			setInput(input);			
			operation.execute();
			tm.commit();	
			successesCount++;
			logSuccess(input);
		} catch (RuntimeException re) {
			handleFailure(input,re);
		} catch (DataException de) {
			handleFailure(input,de);
		} catch (LogicException le) {
			handleFailure(input,le);
		}
	}
	
	/**
	 * Handles a failure. In scenarios where a PersistenceContext
	 * is used, it is necessary to use tidy() to destroy any context
	 * that may contain database modifications of the failed
	 * transaction not flushed yet.
	 * 
	 * @param input
	 * @param ex
	 * @throws CouldNotRollbackException 
	 */
	void handleFailure(T input, Exception ex) throws CouldNotRollbackException {
		failuresCount++;
		logFailure(input, ex);
		tm.rollback();
		tidy();
	}
	
	/**
	 * Logs a successful process.
	 * 
	 * @param input
	 */
	void logSuccess(T input) {
		String msg = safeToString(input); 			
		successesLog.getStream().println(msg);
	}
	
	/**
	 * Logs a failed process.
	 * 
	 * @param input
	 * @param ex 
	 */
	@SuppressWarnings("nls")
	void logFailure(T input, Exception ex) {
		String addendum = " REATTEMPTED AUTOMATICALLY (failure was TransactionManagerException)";
		boolean tmEx = ex instanceof TransactionManagerException;
		String inputString = safeToString(input);
		
		String msg = StringUtils.concat (			 
			inputString, SEMICOLON,
			ex.toString(), SEMICOLON);
		if(tmEx) {
			msg = msg + addendum;
		}
		failuresLog.getStream().println(msg);
		
		if(stacktracesLog != null) {
			String trace = StringUtils.concat (
				inputString, NEWLINE,
				ExceptionUtils.getThrowableStackTrace(ex), NEWLINE);
			if(tmEx) {
				trace = trace + addendum + NEWLINE;
			}
			stacktracesLog.getStream().println(trace);
		}
	}
	
	/**
	 * Log status at end.
	 */
	void end() {		
		try {			
			close();
		} catch (DataException de) {			
			handle(de);
		} 
		endTime = new Date();
		finished = true;
				
	}
	
	@Override
	public void forceQuit() {
		quit = true;
	}
	
	/**
	 * Signals the end of data being added for processing.
	 */
	public void signalEndOfData() {
		eod = true;
	}	

	@Override
	public boolean isFinished() {
		return finished;
	}	
	
	@Override
	public boolean isFinishedAbnormally() {
		return exceptionMessage!=null;
	}
	
	@Override
	public boolean isPaused() {
		return paused;
	}

	@Override
	public void pause() {
		paused = true;
	}
	
	@Override
	public void resume() {
		paused = false;
	}	
	
	@Override
	public long getProcessedCount() {
		return processedCount;		
	}	
	
	@Override
	public long getSuccessesCount() {
		return successesCount;		
	}	
	
	@Override
	public long getFailuresCount() {
		return failuresCount;		
	}

	/**
	 * Gets the name.
	 *
	 * @return Returns the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Safe toString method for input.
	 * 
	 * @param input
	 * 
	 * @return Returns the string representation of input.
	 */
	String safeToString(T input) {
		try {
			return formatter.format(input);
		} catch (RuntimeException e) {
			return "Failed to write input"; //$NON-NLS-1$
		}
	}
	
	/**
	 * Puts the stack trace of the specified thrown to the
	 * <code>exceptionMessage</code> variable.
	 * 
	 * @param t
	 *        Thrown exception to handle.
	 */
	void handle(Throwable t) {
		String deStack = ExceptionUtils.getThrowableStackTrace(t);
		if (exceptionMessage!=null) {
			exceptionMessage += StringUtils.sameCharacterString(60, '-');
			exceptionMessage += deStack;
		} else {
			exceptionMessage = deStack;
		}		
	}

	/**
	 * Gets the exceptionMessage.
	 *
	 * @return Returns the exceptionMessage
	 */
	@Override
	public String getExceptionMessage() {
		return exceptionMessage;
	}

	
	@Override
	public long getTotalElementsCount() {	
		return 0;
	}

	@Override
	public Date getStartTime() {
		return startTime;
	}

	@Override
	public Date getEndTime() {
		return endTime;
	}

	
	
	/**
	 * Sets a new operation to replace the current operation.
	 * 
	 * @param newOperation
	 *        New operation.
	 */
	public void setNewOperation(Operation newOperation) {
		ReflectionUtils.mandatoryPropertyOfClass(inputPropertyName, newOperation.getClass());
		this.newOperation = newOperation;		
	}
	
	@Override
	public void tidy() {
		if (operation!=null) {
			Operation op = Factory.create(operation.getClass());
			setNewOperation(op);
		}		
	}
	
	

}
