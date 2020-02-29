package gr.interamerican.bo2.gui.batch.model;

import gr.interamerican.bo2.arch.batch.MultiThreadedLongProcess;
import gr.interamerican.bo2.arch.batch.MultithreadLongProcessExecutionStatus;

/**
 * model
 */
public class BatchProcessExecutionStatusPanelModel {

	/**
	 * model
	 */
	private MultiThreadedLongProcess model;

	/**
	 * contructor
	 *
	 * @param model
	 */
	public BatchProcessExecutionStatusPanelModel(MultiThreadedLongProcess model) {
		this.model = model;
	}

	/**
	 * @return if model has started
	 */
	public boolean getStart() {
		return MultithreadLongProcessExecutionStatus.START.compareTo(model.getExecutionStatus()) < 0;
	}

	/**
	 * @return if model PREPROCESS_EXECUTION
	 */
	public boolean getPreProcessExecution() {
		return MultithreadLongProcessExecutionStatus.PREPROCESS_EXECUTION.compareTo(model.getExecutionStatus()) < 0;
	}

	/**
	 * @return if model MAIN_QUERY_EXECUTION
	 */
	public boolean getMainQueryExecution() {
		return MultithreadLongProcessExecutionStatus.MAIN_QUERY_EXECUTION.compareTo(model.getExecutionStatus()) < 0;
	}

	/**
	 * @return if model QPROCESSORS_EXECUTION
	 */
	public boolean getQprocessorsExecution() {
		return MultithreadLongProcessExecutionStatus.QPROCESSORS_EXECUTION.compareTo(model.getExecutionStatus()) < 0;
	}

	/**
	 * @return if model POST_PROCESS
	 */
	public boolean getPostProcess() {
		return MultithreadLongProcessExecutionStatus.POST_PROCESS.compareTo(model.getExecutionStatus()) < 0;
	}

	/**
	 * @return if model has ended
	 */
	public boolean getEnd() {
		return MultithreadLongProcessExecutionStatus.END.compareTo(model.getExecutionStatus()) < 0;
	}
}
