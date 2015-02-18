package gr.interamerican.bo2.impl.open.job;

import gr.interamerican.bo2.arch.ResourceWrapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Means to schedule jobs in a unit of work. This resource wrapper
 * is enListed to the current transaction and the jobs are fired
 * using the {@link JobScheduler} provided by this provider only
 * if the commit succeeds. The jobs are cleared after they are fired
 * or in case of a rollback.
 */
public interface JobSchedulerProvider extends ResourceWrapper {
	
	/**
	 * Logger for implementing classes.
	 */
	Logger LOGGER = LoggerFactory.getLogger(JobSchedulerProvider.class);
	
	/**
	 * Gets the scheduler that will be used to submit all scheduled jobs
	 * to the underlying scheduling implementation. This method is for
	 * internal use only and is not part of the public API.
	 *
	 * @return Returns the scheduler
	 */
	JobScheduler getScheduler();
	
	/**
	 * API users use this to schedule a job to be executed after the end 
	 * of the current unit of work.
	 * 
	 * @param job
	 */
	void scheduleJob(JobDescription job);
	
	/**
	 * @return Returns all scheduled jobs. Implementations should never return null.
	 */
	List<JobDescription> getScheduledJobs();
	
	/**
	 * Clears all scheduled jobs. Again, this is not meant for API users.
	 */
	void clearJobs();

}
