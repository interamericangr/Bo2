package gr.interamerican.bo2.impl.open.job;

import gr.interamerican.bo2.arch.exceptions.DataException;

import java.util.List;

/**
 * Job scheduler.
 */
public interface JobScheduler {

	/**
	 * Submits the job.
	 * 
	 * @param jobDescriptions
	 * @param synchronous
	 *            If this is true, the scheduler will wait for the jobs to finish
	 * 
	 * 
	 * @throws DataException
	 */
	public void submitJobs(List<JobDescription> jobDescriptions, boolean synchronous) throws DataException;

	/**
	 * submits an asynchronous single job
	 * 
	 * @param jobDescription
	 * @throws DataException
	 */
	public void submitJob(JobDescription jobDescription) throws DataException;

}
