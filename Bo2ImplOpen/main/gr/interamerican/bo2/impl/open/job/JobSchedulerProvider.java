package gr.interamerican.bo2.impl.open.job;

import java.util.List;

import gr.interamerican.bo2.arch.ResourceWrapper;

/**
 * Means to schedule jobs in a unit of work. This resource wrapper
 * is enListed to the current transaction and the jobs are fired
 * using the {@link JobScheduler} provided by this provider.
 * if the commit succeeds. The jobs are cleared after they are fired
 * or in case of a rollback.
 */
public interface JobSchedulerProvider extends ResourceWrapper {
	
	/**
	 * Gets the scheduler that will be used to submit all scheduled jobs
	 * to the underlying scheduling implementation.
	 *
	 * @return Returns the scheduler
	 */
	JobScheduler getScheduler();
	
	/**
	 * @return Returns all scheduled jobs
	 */
	List<JobDescription> getScheduledJobs();
	
	/**
	 * Clears all scheduled jobs.
	 */
	void clearJobs();

}
