package gr.interamerican.bo2.arch.utils.ext;

import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.util.Date;

/**
 * Information related to a Worker execution.
 */
public class WorkerExecutionDetails {
	
	/**
	 * Info for the task this worker executed ,e.g. an SQL statement.
	 */
	String taskInfo;
	
	/**
	 * Worker class
	 */
	Class<? extends Worker> workerClass;
	
	/**
	 * Worker execution start time.
	 */
	Date startTime = new Date();
	
	/**
	 * Assigns a new value to the taskInfo.
	 *
	 * @param taskInfo the taskInfo to set
	 */
	public void setTaskInfo(String taskInfo) {
		this.taskInfo = taskInfo;
	}

	/**
	 * Assigns a new value to the workerClass.
	 *
	 * @param workerClass the workerClass to set
	 */
	public void setWorkerClass(Class<? extends Worker> workerClass) {
		this.workerClass = workerClass;
	}

	/**
	 * Assigns a new value to the startTime.
	 *
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@Override
	public String toString() {
		return StringUtils.concat(
				String.valueOf(startTime),
				StringConstants.COMMA + StringConstants.SPACE,
				workerClass != null ? workerClass.getName() : "N/A", //$NON-NLS-1$
				StringConstants.COMMA + StringConstants.SPACE,
				taskInfo);
	}
	
}
