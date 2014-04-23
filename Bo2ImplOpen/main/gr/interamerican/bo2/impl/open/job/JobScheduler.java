package gr.interamerican.bo2.impl.open.job;

import gr.interamerican.bo2.arch.exceptions.DataException;

/**
 * Job scheduler.
 */
public interface JobScheduler {

	/**
	 * Submits the job.
	 * 
	 * @param jobDescription
	 * 
	 * @return the job name scheduled.
	 * 
	 * @throws DataException
	 */
	public String submitJob(JobDescription jobDescription) throws DataException;

}
