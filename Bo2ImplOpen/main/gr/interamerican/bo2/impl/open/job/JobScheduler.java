package gr.interamerican.bo2.impl.open.job;

import gr.interamerican.bo2.arch.exceptions.DataException;

import java.util.List;

/**
 * Job scheduler. Submits a job to a job execution API.
 */
public interface JobScheduler {

	/**
	 * Submits the job.
	 * 
	 * @param jobDescriptions
	 * 
	 * @throws DataException
	 */
	public void submitJobs(List<JobDescription> jobDescriptions) throws DataException;
}
