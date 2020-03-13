package gr.interamerican.bo2.quartz;

import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.impl.open.job.JobStatus;

import java.io.Serializable;

/**
 * job description including all information needed.
 */
public interface QuartzjobDescription extends Serializable {
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
	 * Assigns a new value to the jobName.
	 *
	 * @param jobName
	 *            the jobName to set
	 */
	void setJobName(String jobName);

	/**
	 * Gets the job name.
	 *
	 * @return the jobName assigned during the creation.
	 */
	String getJobName();

	/**
	 * Gets the throwable.
	 *
	 * @return the throwable.
	 */
	Throwable getThrowable();

	/**
	 * sets the throwable.
	 *
	 * @param t the new throwable
	 */
	void setThrowable(Throwable t);

	/**
	 * Gets the job description digest.
	 *
	 * @return the digested version of a {@link JobDescription}
	 */
	String getJobDescriptionDigest();

	/**
	 * sets the digested version of a {@link JobDescription}.
	 *
	 * @param digest the new job description digest
	 */
	void setJobDescriptionDigest(String digest);
}
