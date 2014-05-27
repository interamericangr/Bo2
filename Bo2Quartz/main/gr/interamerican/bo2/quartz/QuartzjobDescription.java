package gr.interamerican.bo2.quartz;

import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.impl.open.job.JobStatus;

/**
 * job description including all information needed.
 */
public interface QuartzjobDescription extends JobDescription {

	/**
	 * Gets the executionStatus.
	 * 
	 * @return Returns the executionStatus
	 */
	JobStatus getExecutionStatus();

	/**
	 * Assigns a new value to the executionStatus.
	 * 
	 * @param executionStatus
	 *            the executionStatus to set
	 */
	void setExecutionStatus(JobStatus executionStatus);

	/**
	 * Gets the jobName.
	 * 
	 * @return Returns the jobName
	 */
	String getJobName();

	/**
	 * Assigns a new value to the jobName.
	 * 
	 * @param jobName
	 *            the jobName to set
	 */
	void setJobName(String jobName);

	/**
	 * @return the throwable.
	 */
	Throwable getThrowable();

	/**
	 * sets the throwable.
	 * 
	 * @param t
	 */
	void setThrowable(Throwable t);
}
