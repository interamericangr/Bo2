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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.Query;
import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.arch.batch.MultiThreadedLongProcess;
import gr.interamerican.bo2.arch.batch.MultithreadLongProcessExecutionStatus;
import gr.interamerican.bo2.arch.exceptions.CouldNotBeginException;
import gr.interamerican.bo2.arch.exceptions.CouldNotCommitException;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.TransactionManagerException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedPrintStream;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.impl.open.workers.WorkerUtils;
import gr.interamerican.bo2.utils.ArrayUtils;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.adapters.Modification;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;

/**
 * {@link BatchProcess} executes a batch process using multiple threads. <br>
 *
 * The BatchProcess is designed upon the pattern of an operation that is being
 * executed as an atomic transaction once for each row fetched by a cursor. The
 * cursor is realized by an {@link EntitiesQuery}. The entities fetched by the
 * query are fed to a thread safe queue. The operation that is to be performed
 * for each entity fetched by the query is encapsulated in a
 * {@link QueueProcessor}. The BatchProcess can use more than one
 * QueueProcessors, with each QueueProcessor running in its own thread. Each
 * QueueProcessor polls the queue for an entity to process. The entity is set to
 * the operation using the appropriate setter defined by the argument
 * <code>inputPropertyName</code>. The operation is then executed. The results
 * of each operation execution are logged by the queue processor that owns the
 * operation. <br>
 *
 * @param <T>
 *            Type of object being processed by the batch process.
 */
public class BatchProcess<T> implements Runnable, MultiThreadedLongProcess {

	/**
	 * Time interval between two checks that check if the queue processors have
	 * finished processing.
	 */
	private static final int INTERVAL = 10;

	/**
	 * Default logger for Bo2.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(BatchProcess.class);

	/**
	 * Queue with input elements.
	 */
	final Queue<T> inputQueue;

	/**
	 * Batch process parameters.
	 */
	BatchProcessParm<T> parameters;

	/**
	 * queue processors.
	 */
	List<QueueProcessor<T>> queueProcessors;

	/**
	 * Count of processors to add.
	 */
	int countOfProcessorsToAdd;

	/**
	 * Count of processors. BOTWO-21
	 */
	int processorsCount;

	/**
	 * Start time.
	 */
	Date startTime;

	/**
	 * End time.
	 *
	 * @see #isFinished()
	 */
	Date endTime;

	/**
	 * Total elements count.
	 */
	long totalElementsCount = 0;
	/**
	 * Exception message.
	 */
	String exceptionMessage = null;

	/**
	 * Session.
	 */
	Session<?, ?> session;

	/**
	 * End of query indicator.
	 */
	boolean eod;
	/**
	 * current status of batch process
	 */
	MultithreadLongProcessExecutionStatus status = MultithreadLongProcessExecutionStatus.START;

	/**
	 * Creates an instance of the specified class.
	 *
	 * @param className
	 *            Either fully qualified name of the class who's instance is to
	 *            be created, or null.
	 *
	 * @return If the specified className is not blank or null then returns a
	 *         new instance of it, otherwise returns null.
	 */
	static Object safeCreate(String className) {
		if (StringUtils.isNullOrBlank(className)) {
			return null;
		}
		return Factory.create(className);
	}

	/**
	 * Creates a new BatchProcessor object.
	 *
	 *
	 *
	 * @param parameters
	 *            Batch process parameters. <br>
	 *            The values of this {@link BatchProcessParm} properties have
	 *            the following effect on the {@link BatchProcess} that is
	 *            created. <br>
	 *            <ul>
	 *            <li><code>name</code> <br>
	 *            Name of the batch process. <code>countOfProcessors</code> <br>
	 *            Initial count of processors. Processing starts with as many
	 *            queue processors as this property specifies. Additional queue
	 *            processors can be added with {@link #addQueueProcessor()} or
	 *            {@link #addQueueProcessors(int)}.</li>
	 *            <li><code>operationClass</code> <br>
	 *            Class of the operation that operates on each elements fetched
	 *            by the query. <code>inputPropertyName</code> <br>
	 *            Name of the property of the operationClass that takes the
	 *            input. Elements fetched by the query are set to this property
	 *            before being processed by the operation. The type of this
	 *            property must be such that it accepts the elements fetched by
	 *            the query.</li>
	 *            <li><code>formatter</code> <br>
	 *            Class of the formatter. Each queue processor of this batch
	 *            process has its own formatter that is instantiated by the
	 *            default factory. The formatters are used to print the
	 *            processed elements in the log files. The formatter can be
	 *            null. In this case a default formatter will be used that
	 *            prints the result of the element's <code>toString()</code>
	 *            method.</li>
	 *            <li><code>preProcessing</code> <br>
	 *            Operation that runs before the main processing. This operation
	 *            can contain any initialization required before the main
	 *            processing. If this parameter is null, then the pre-processing
	 *            step is omitted.</li>
	 *            <li><code>postProcessing</code> <br>
	 *            Operation that runs after the main processing has ended. This
	 *            operation should take over the actions necessary after the end
	 *            of the main-processing.</li>
	 *            <li><code>queryParametersSetter</code> <br>
	 *            {@link Modification} that will set any required criteria to
	 *            the query. If the query needs no criteria, then this should be
	 *            null.</li>
	 *            <li><code>operationParametersSetter</code> <br>
	 *            {@link Modification} that will set any required parameters to
	 *            the main operation of the batch process. If the operation does
	 *            not need other parameters that the one defined by
	 *            <code>inputPropertyName</code> then this should be null.</li>
	 *            <li><code>preOperationParametersSetter</code> <br>
	 *            {@link Modification} that will set any required parameters to
	 *            the pre-processing operation. If the pre-processing operation
	 *            does not need other parameters or if there is no
	 *            pre-processing operation, then this should be null.</li>
	 *            <li><code>postOperationParametersSetter</code> <br>
	 *            {@link Modification} that will set any required parameters to
	 *            the post-processing operation. If the post-processing
	 *            operation does not need other parameters or if there is no
	 *            post-processing operation, then this should be null.</li>
	 *            </ul>
	 *
	 */
	public BatchProcess(BatchProcessParm<T> parameters) {
		super();
		this.parameters = parameters;
		this.inputQueue = new ConcurrentLinkedQueue<T>();
		this.queueProcessors = new CopyOnWriteArrayList<QueueProcessor<T>>();
		this.session = Bo2Session.getSession();
	}

	/**
	 * Creates a new queue processor and starts it.
	 *
	 * This method must be called from within the <code>execute()</code> method,
	 * so that it runs on the same thread as the query
	 *
	 */
	void addQueueProcessor() {
		Operation op = Factory.create(parameters.getOperationClass());
		int id = queueProcessors.size();
		QueueProcessor<T> qp = new QueueProcessor<T>(inputQueue, parameters.getName(), Integer.toString(id), op,
				parameters.getInputPropertyName(), parameters.getFormatter(), parameters.getOperationParametersSetter(),
				parameters.getReattemptOnTmex());
		queueProcessors.add(qp);
		if (eod) {
			qp.signalEndOfData();
		}
		Thread thread = new Thread(qp);
		thread.start();
	}

	/**
	 * Instructs this batch process to add processors. <br>
	 *
	 * @param count
	 *            Count of processors to be added.
	 */
	public void addQueueProcessors(int count) {
		countOfProcessorsToAdd += count;
	}

	/**
	 * Pauses the specified queue processor.
	 *
	 * @param id
	 *            Id of the queue processor to be paused.
	 */
	public void pauseQueueProcessor(int id) {
		QueueProcessor<T> qp = queueProcessors.get(id);
		if (qp != null) {
			qp.pause();
		}
	}

	/**
	 * Resumes the specified queue processor.
	 *
	 * @param id
	 *            Id of the queue processor to be resumed.
	 */
	public void resumeQueueProcessor(int id) {
		QueueProcessor<T> qp = queueProcessors.get(id);
		if (qp != null) {
			qp.resume();
		}
	}

	/**
	 * Forces the specified queue processor to quit.
	 *
	 * @param id
	 *            Id of the queue processor to quit.
	 */
	public void quitQueueProcessor(int id) {
		QueueProcessor<T> qp = queueProcessors.get(id);
		if (qp != null) {
			qp.forceQuit();
		}
	}

	/**
	 * Checks if there is a command to add new processors and if so, adds the
	 * required number of processors.
	 */
	void addNewProcessorsIfCommanded() {
		if (countOfProcessorsToAdd != 0) {
			int count = countOfProcessorsToAdd;
			countOfProcessorsToAdd = 0;
			for (int i = 0; i < count; i++) {
				processorsCount++;
				addQueueProcessor();
			}
		}
	}

	/**
	 * Prints the headers in the log files.
	 *
	 * @param provider
	 *            Provider used by this BatchProcess
	 * @throws InitializationException
	 *             the initialization exception
	 */
	@SuppressWarnings("nls")
	void printHeaders(Provider provider) throws InitializationException {
		NamedPrintStream successes = SharedStreams.successes(provider, this.getName());
		NamedPrintStream failures = SharedStreams.failures(provider, this.getName());
		String header = parameters.getEntityHeader();
		if (!StringUtils.isNullOrBlank(header)) {
			successes.writeString(header);
			header = header+StringConstants.SEMICOLON+"Error message";
			failures.writeString(header);
		}
		NamedPrintStream stackTraces = SharedStreams.optionalStacktraces(provider, this.getName());
		if (stackTraces != null) {
			String stackHead = ">>>Stack traces of batch process:" + this.getName();
			stackHead = stackHead + StringConstants.SPACE + "v" + System.getProperty(BatchProcessConstants.BATCH_VERSION, "N/A");
			stackTraces.writeString(stackHead);
			String input = parameters.getBatchProcessInputAsText();
			stackTraces.writeString(input + "<<<");
		}
	}

	/**
	 * Writes a summary of statistics for this batch process in the stacktraces
	 * log file at the end of its execution.
	 * 
	 * @param provider
	 *            Provider used by this BatchProcess
	 */
	@SuppressWarnings("nls")
	void writeStatistics(Provider provider) {
		NamedPrintStream stackTraces = SharedStreams.optionalStacktraces(provider, this.getName());
		if (stackTraces == null) {
			return;
		}

		String statistics = StringUtils.concat(">>>>statistics - post process has not run yet!!! <<<<",
				"start date: " + String.valueOf(startTime) + StringConstants.NEWLINE,
				"end date: " + String.valueOf(new Date()) + StringConstants.NEWLINE,
				"processors count: " + String.valueOf(processorsCount));

		stackTraces.writeString(statistics);
	}

	/**
	 * Opens shared streams used by QueueProcessor operations.
	 *
	 * @param provider
	 *            the provider
	 * @throws InitializationException
	 *             the initialization exception
	 */
	void openSharedStreams(Provider provider) throws InitializationException {
		if (ArrayUtils.isNullOrEmpty(parameters.getSharedStreamNames())) {
			return;
		}
		String defaultStreamsProvider = Bo2.getDefaultDeployment().getDeploymentBean().getStreamsManagerName();
		NamedStreamsProvider nsp = provider.getResource(defaultStreamsProvider, NamedStreamsProvider.class);
		for (String streamName : parameters.getSharedStreamNames()) {
			nsp.getSharedStream(streamName);
		}
	}

	/**
	 * Initializes the query and starts the initial processor threads.
	 *
	 * @return The Current Provider of the {@link BatchProcess}
	 * @throws InitializationException
	 *             the initialization exception
	 * @throws DataException
	 *             the data exception
	 * @throws CouldNotBeginException
	 *             the could not begin exception
	 */
	Provider initialize() throws InitializationException, DataException, CouldNotBeginException {
		Provider provider = Bo2.getProvider();
		Bo2Session.setProvider(provider);
		provider.getTransactionManager().begin();
		printHeaders(provider);
		openSharedStreams(provider);
		Query query = parameters.getQuery();
		Modification<Object> setCriteria = parameters.getQueryParametersSetter();
		if (setCriteria != null) {
			setCriteria.execute(query);
		}
		query.init(provider);
		query.open();

		createInitialQueueProcessors();

		// TODO: is it necessary?
		long interval = queueProcessors.size() * 10;
		ThreadUtils.sleepMillis(interval);
		return provider;

	}

	/**
	 * Registers the streams contained in the {@link BatchProcessParm}s.<br>
	 * The manager for the streams is the default streams manager of the Bo2
	 * deployment.
	 *
	 * @throws InitializationException
	 *             the initialization exception
	 * @throws DataException
	 *             the data exception
	 */
	void registerNamedStreams() throws InitializationException, DataException {
		Provider provider = Bo2.getProvider();
		try {
			Bo2Session.setProvider(provider);
			List<NamedStreamDefinition> namedStreamDefinitions = parameters.getNamedStreamDefinitions();
			if (!CollectionUtils.isNullOrEmpty(namedStreamDefinitions)) {
				String managerName = Bo2.getDefaultDeployment().getDeploymentBean().getStreamsManagerName();
				NamedStreamsProvider nsp = provider.getResource(managerName, NamedStreamsProvider.class);
				for (NamedStreamDefinition def : namedStreamDefinitions) {
					nsp.registerStreamDefinition(def);
				}
			}
		} finally {
			provider.close();
			Bo2Session.setProvider(null);
		}
	}

	/**
	 * Creates the initial QueueProcessors.
	 */
	void createInitialQueueProcessors() {
		processorsCount = parameters.getInitialThreads();
		for (int i = 0; i < parameters.getInitialThreads(); i++) {
			addQueueProcessor();
		}
	}

	/**
	 * Cleanup.
	 *
	 * @param provider
	 *            the provider
	 * @throws DataException
	 *             the data exception
	 * @throws CouldNotCommitException
	 *             the could not commit exception
	 */
	void close(Provider provider) throws DataException, CouldNotCommitException {
		provider.getTransactionManager().commit();
		parameters.getQuery().close();
		provider.close();
		Bo2Session.setProvider(null);
	}

	/**
	 * Forces the queue processors to quit.
	 */
	@Override
	public void forceQuit() {
		for (QueueProcessor<T> qp : queueProcessors) {
			qp.forceQuit();
		}
		endTime = new Date();
	}

	/**
	 * Forces the queue processors to quit.
	 */
	void endOfQuery() {
		eod = true;
		for (QueueProcessor<T> qp : queueProcessors) {
			qp.signalEndOfData();
		}
	}

	/**
	 * Checks to make sure that all queue processors are stopped.
	 *
	 * @return Returns true if all queue processors are stopped.
	 */
	boolean isAllQueueProcessorsStopped() {
		for (QueueProcessor<T> qp : queueProcessors) {
			if (!qp.isFinished()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Waits until all queue processors have finished.
	 */
	void waitQueueProcessors() {
		while (!isAllQueueProcessorsStopped()) {
			addNewProcessorsIfCommanded();
			ThreadUtils.sleep(INTERVAL);
		}
	}

	/**
	 * Fetches the elements of the query and puts them in the input queue.
	 *
	 * @throws DataAccessException
	 *             the data access exception
	 */
	void crawlQuery() throws DataAccessException {
		EntitiesQuery<T> query = parameters.getQuery();
		while (query.next()) {
			totalElementsCount++;
			T input = query.getEntity();
			inputQueue.add(input);
			addNewProcessorsIfCommanded();
		}
		endOfQuery();
	}

	/**
	 * Processes the query.
	 *
	 * @throws DataAccessException
	 *             the data access exception
	 */
	void processQuery() throws DataAccessException {
		try {
			crawlQuery();
			waitQueueProcessors();
		} catch (DataAccessException e) {
			e.printStackTrace();
			forceQuit();
			throw e;
		}
	}

	/**
	 * Executes the batch process.
	 *
	 * @throws InitializationException
	 *             the initialization exception
	 * @throws DataException
	 *             the data exception
	 * @throws LogicException
	 *             the logic exception
	 * @throws UnexpectedException
	 *             the unexpected exception
	 */
	public void execute() throws InitializationException, DataException, LogicException, UnexpectedException {
		eod = false;
		status = MultithreadLongProcessExecutionStatus.START;
		startTime = new Date();
		registerNamedStreams();
		status = MultithreadLongProcessExecutionStatus.PREPROCESS_EXECUTION;
		WorkerUtils.executeOperation(parameters.getPreProcessing(), parameters.getPreOperationParametersSetter(),
				LOGGER);
		try {
			Provider provider = initialize();
			status = MultithreadLongProcessExecutionStatus.MAIN_QUERY_EXECUTION;
			parameters.getQuery().execute();
			status = MultithreadLongProcessExecutionStatus.QPROCESSORS_EXECUTION;
			processQuery();

			writeStatistics(provider);

			close(provider);
		} catch (TransactionManagerException e) {
			throw new UnexpectedException(e);
		}
		status = MultithreadLongProcessExecutionStatus.POST_PROCESS;
		WorkerUtils.executeOperation(parameters.getPostProcessing(), parameters.getPostOperationParametersSetter(),
				LOGGER);

		endTime = new Date();
		status = MultithreadLongProcessExecutionStatus.END;
	}

	@Override
	public void run() {
		try {
			Bo2Session.setSession(session);
			execute();
		} catch (Exception e) {
			endTime = new Date();
			e.printStackTrace();
			String msg = ExceptionUtils.getThrowableStackTrace(e);
			exceptionMessage = msg;
		}
	}

	@Override
	public String getName() {
		return parameters.getName();
	}

	/**
	 * Gets the number of initial threads.
	 *
	 * @return returns the number of initial threads
	 */
	public int getInitialThreads() {
		return parameters.getInitialThreads();
	}

	@Override
	public Date getStartTime() {
		return startTime;
	}

	@Override
	public Date getEndTime() {
		return endTime;
	}

	@Override
	public synchronized boolean isFinished() {
		List<LongProcess> subProcesses = getSubProcesses();
		if ((subProcesses == null) || subProcesses.isEmpty()) {
			/*
			 * If we have no queues, then a. if we have an exception message, it
			 * means ex exception occured during initialization, or pre-process,
			 * so finished = true. b. if we don't have an exception message,
			 * then processing didn't start yet, so finished=false.
			 */
			return (exceptionMessage != null);
		}
		for (LongProcess thread : subProcesses) {
			if (!thread.isFinished()) {
				return false;
			}
		}
		return endTime != null;
	}

	@Override
	public boolean isFinishedAbnormally() {
		if (isFinished()) {
			return (exceptionMessage != null) || (!inputQueue.isEmpty());
		}
		return false;
	}

	@Override
	public synchronized boolean isPaused() {
		for (LongProcess thread : getSubProcesses()) {
			if (!thread.isPaused()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public long getTotalElementsCount() {
		return totalElementsCount;
	}

	@Override
	public long getProcessedCount() {
		return CollectionUtils.sumL(getSubProcesses(), LongProcess::getProcessedCount);
	}

	@Override
	public long getSuccessesCount() {
		return CollectionUtils.sumL(getSubProcesses(), LongProcess::getSuccessesCount);
	}

	@Override
	public long getFailuresCount() {
		return CollectionUtils.sumL(getSubProcesses(), LongProcess::getFailuresCount);
	}

	@Override
	public String getExceptionMessage() {
		return exceptionMessage;
	}

	@Override
	public void pause() {
		for (LongProcess thread : getSubProcesses()) {
			if ((!thread.isFinished()) && (!thread.isPaused())) {
				thread.pause();
			}
		}
	}

	@Override
	public void resume() {
		for (LongProcess thread : getSubProcesses()) {
			if ((!thread.isFinished()) && thread.isPaused()) {
				thread.resume();
			}
		}
	}

	@Override
	public List<LongProcess> getSubProcesses() {
		return Utils.cast(queueProcessors);
	}

	@Override
	public int getCountOfSubProcesses() {
		return queueProcessors.size();
	}

	@Override
	public int getCountOfFinishedSubProcesses() {
		return CollectionUtils.countByProperty(LongProcess::isFinished, true, queueProcessors);
	}

	@Override
	public int getCountOfPausedSubProcesses() {
		return CollectionUtils.countByProperty(LongProcess::isPaused, true, queueProcessors);
	}

	@Override
	public int getCountOfAbnormallyFinishedSubProcesses() {
		return CollectionUtils.countByProperty(LongProcess::isFinishedAbnormally, true, queueProcessors);
	}

	@Override
	public synchronized void tidy() {
		for (LongProcess thread : getSubProcesses()) {
			if (!thread.isFinished()) {
				thread.tidy();
			}
		}
	}

	/**
	 * Gets the parameters.
	 *
	 * @return Returns the parameters
	 */
	public BatchProcessParm<T> getParameters() {
		return parameters;
	}

	@Override
	public boolean isCanIncreaseSubProcesses() {
		return parameters.getUiCanAddThreads();
	}

	/**
	 * Gets the next element to process.
	 *
	 * @return the next element in queue to be processed without removing it
	 *         from the queue.
	 */
	public T getNextElementToProcess() {
		return inputQueue.peek();
	}

	@Override
	public String getNextElementToProcessFormatted() {
		T t = getNextElementToProcess();
		if (t == null) {
			return StringConstants.EMPTY;
		}
		Formatter<T> formatter = parameters.getFormatter();
		if (formatter != null) {
			return formatter.format(t);
		}
		return t.toString();
	}

	@Override
	public int getQueueSize() {
		if (eod) {
			return inputQueue.size();
		}
		return -1;
	}

	@Override
	public long getProcessTime() {
		long total = 0;
		int count = 0;
		for (LongProcess thread : getSubProcesses()) {
			if (!thread.isFinished()) {
				double qpt = thread.getProcessTime();
				if (qpt > 0) {
					total += qpt;
					count++;
				}
			}
		}
		if (count > 0) {
			return total / count;
		}
		return -1;
	}

	@Override
	public double getThroughput() {
		double t = (1000.0 * getCountOfSubProcesses()) / getProcessTime();
		if (t > 0) {
			return t;
		}
		return -1;
	}

	@Override
	public Date getEta() {
		double throughput = getThroughput();
		int queueSize = getQueueSize();
		if ((throughput < 0) || (queueSize < 0)) {
			return null;
		}
		double secondsRemaining = queueSize / throughput;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, (int) Math.round(secondsRemaining));
		return calendar.getTime();
	}

	@Override
	public MultithreadLongProcessExecutionStatus getExecutionStatus() {
		return status;
	}
}
