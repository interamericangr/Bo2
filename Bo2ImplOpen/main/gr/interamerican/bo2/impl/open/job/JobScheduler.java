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
	 *        If this is true, the scheduler will wait for the jobs to finish
	 * 
	 * @return the job name scheduled.
	 * 
	 * @throws DataException
	 */
	public String submitJobs(List<JobDescription> jobDescriptions, boolean synchronous) throws DataException;

}
