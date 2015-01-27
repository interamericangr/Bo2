package gr.interamerican.bo2.impl.open.job;

import gr.interamerican.bo2.arch.ResourceWrapper;
import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.exceptions.CouldNotDelistException;
import gr.interamerican.bo2.arch.exceptions.CouldNotEnlistException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.utils.SelectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Abstract base implementation of a {@link TransactionManager} that knows
 * how to handle jobs.
 * <br/>
 * Jobs are scheduled within a UOW. Users extending this class should
 * <br/>
 * <li/> Submit any scheduled jobs by calling {@link #submitScheduledJobsOnCommit()}
 *       if the transaction commits successfully. After submitting, jobs are
 *       cleared by calling {@link #clearScheduledJobs()}.
 * <li/> Submit any non transactional scheduled jobs by calling {@link #submitScheduledJobsOnRollback()}
 *       if the transaction rollbacks. After submitting, jobs are cleared by calling {@link #clearScheduledJobs()}.
 * <li/> Clear the scheduled jobs anyway when closed.
 * 
 */
public abstract class JobCapableTransactionManager implements TransactionManager {
	
	/**
	 * Registered {@link JobSchedulerProvider}s for this unit of work.
	 */
	Set<JobSchedulerProvider> schedulerHandlers = new HashSet<JobSchedulerProvider>();

	public void enList(ResourceWrapper resource) throws CouldNotEnlistException {
		if(resource instanceof JobSchedulerProvider) {
			JobSchedulerProvider scheduler = (JobSchedulerProvider) resource;
			schedulerHandlers.add(scheduler);
		}
	}

	public void deList(ResourceWrapper resource) throws CouldNotDelistException {
		if(resource instanceof JobSchedulerProvider) {
			JobSchedulerProvider scheduler = (JobSchedulerProvider) resource;
			schedulerHandlers.remove(scheduler);
			scheduler.clearJobs(); //TODO: should we do this here?
		}
	}

	public void close() {
		clearScheduledJobs();
	}
	
	/**
	 * Submits the scheduled jobs of this UOW. This must be called after successful commit.
	 * 
	 * @throws DataException
	 */
	protected void submitScheduledJobsOnCommit() throws DataException {
		for(JobSchedulerProvider jsp : schedulerHandlers) {
			jsp.getScheduler().submitJobs(jsp.getScheduledJobs());
		}
	}
	
	/**
	 * Submits the scheduled jobs of this UOW that are not transactional. 
	 * This must be called after a rollback. 
	 * 
	 * @see JobDescription#isNonTransactional()
	 * 
	 * @throws DataException
	 */
	protected void submitScheduledJobsOnRollback() throws DataException {
		for(JobSchedulerProvider jsp : schedulerHandlers) {
			List<JobDescription> scheduledJobs = jsp.getScheduledJobs();
			List<JobDescription> nonTransactionalJobs = SelectionUtils.selectByProperty("nonTransactional", true, scheduledJobs, JobDescription.class); //$NON-NLS-1$
			jsp.getScheduler().submitJobs(nonTransactionalJobs);
		}
	}
	
	/**
	 * Clears the scheduled jobs. This must be called after rollback or commit.
	 */
	protected void clearScheduledJobs() {
		for(JobSchedulerProvider jobScheduler : schedulerHandlers) {
			jobScheduler.clearJobs();
		}
		schedulerHandlers.clear();
	}

}
