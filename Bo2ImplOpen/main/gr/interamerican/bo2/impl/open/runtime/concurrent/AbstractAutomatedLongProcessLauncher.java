package gr.interamerican.bo2.impl.open.runtime.concurrent;

import java.util.Properties;
import java.util.Set;

import gr.interamerican.bo2.arch.batch.AutomatedLaunchedProcess;
import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.arch.batch.LongProcessLauncher;
import gr.interamerican.bo2.utils.runnables.Monitor;
import gr.interamerican.bo2.utils.runnables.MonitoringOperation;

/**
 * The Class AbstractAutomatedLongProcessLauncher.
 */
public abstract class AbstractAutomatedLongProcessLauncher implements LongProcessLauncher, AutomatedLaunchedProcess {

	/** batchProcessThread. */
	protected Thread batchProcessThread;
	
	/** monitorThread. */
	protected Thread monitorThread;
	/**
	 * batch process.
	 */
	protected BatchProcess<?> batch;
	/**
	 * monitors
	 */
	protected Monitor<LongProcess> monitor;
	/**
	 * extra monitoring Operations
	 */
	protected Set<MonitoringOperation<LongProcess>> extraMonitoringOperation;

	/**
	 * waits for the batchprocess thread.
	 */
	public void waitForThread() {
		try {
			batchProcessThread.join();
			monitorThread.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * waits for the threads and then shutdowns the vm.
	 */
	public void waitForThreadsAndShutdownVM() {
		waitForThread();
		Integer exitValue = BatchProcessConstants.getExitValue(batch);
		if (exitValue != null) {
			System.exit(exitValue.intValue());
		}
		System.exit(-1);// something totally wrong
	}

	@Override
	public Integer waitForThreadAndGetProcessExitValue() {
		waitForThread();
		Integer exitValue = BatchProcessConstants.getExitValue(batch);
		return exitValue;
	}

	/**
	 * @param monitoringOperation
	 */
	public void setExtraMonitoringMonitoringOperation(Set<MonitoringOperation<LongProcess>> monitoringOperation) {
		extraMonitoringOperation = monitoringOperation;
}


	/**
	 * @param properties
	 * @param controller
	 */
	protected void creatAndStartBatchProcessThread(Properties properties, BatchProcessUtility controller) {
		batch = controller.createBatchProcess(properties);
		batchProcessThread = controller.startBatchProcess(batch);
	}
}
