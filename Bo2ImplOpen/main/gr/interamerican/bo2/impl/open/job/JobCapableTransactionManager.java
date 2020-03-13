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
 * <br>
 * Jobs are scheduled within a UOW. Users extending this class should
 * <br>
 * <ul>
 * <li> Submit any scheduled jobs by calling {@link #submitScheduledJobsOnCommit()}
 *       if the transaction commits successfully. After submitting, jobs are
 *       cleared by calling {@link #clearScheduledJobs()}.</li>
 * <li> Submit any non transactional scheduled jobs by calling {@link #submitScheduledJobsOnRollback()}
 *       if the transaction rollbacks. After submitting, jobs are cleared by calling {@link #clearScheduledJobs()}.</li>
 * <li> Clear the scheduled jobs anyway when closed. </li>
 * </ul>
 */
public abstract class JobCapableTransactionManager implements TransactionManager {
	
	/**
	 * Registered {@link JobSchedulerProvider}s for this transaction manager.
	 */
	Set<JobSchedulerProvider> schedulerHandlers = new HashSet<JobSchedulerProvider>();

	@Override
	public void enList(ResourceWrapper resource) throws CouldNotEnlistException {
		if(resource instanceof JobSchedulerProvider) {
			JobSchedulerProvider scheduler = (JobSchedulerProvider) resource;
			schedulerHandlers.add(scheduler);
			LOGGER.trace("enlisted JobSchedulerProvider " + scheduler); //$NON-NLS-1$
		}
	}
	@Override
	public void deList(ResourceWrapper resource) throws CouldNotDelistException {
		if(resource instanceof JobSchedulerProvider) {
			JobSchedulerProvider scheduler = (JobSchedulerProvider) resource;
			schedulerHandlers.remove(scheduler);
			LOGGER.trace("delisted JobSchedulerProvider " + scheduler); //$NON-NLS-1$
			scheduler.clearJobs(); //TODO: should we do this here?
		}
	}

	@Override
	public void close() {
		schedulerHandlers.clear();
	}
	
	/**
	 * Submits the scheduled jobs of this UOW. This must be called after successful commit.
	 *
	 * @throws DataException the data exception
	 */
	@SuppressWarnings("nls")
	protected void submitScheduledJobsOnCommit() throws DataException {
		for(JobSchedulerProvider jsp : schedulerHandlers) {
			jsp.getScheduler().submitJobs(jsp.getScheduledJobs());
			logJobs("submitted jobs after commit", jsp.getScheduledJobs().size());
		}
	}
	
	/**
	 * Submits the scheduled jobs of this UOW that are not transactional. 
	 * This must be called after a rollback. 
	 *
	 * @throws DataException the data exception
	 * @see JobDescription#isNonTransactional()
	 */
	@SuppressWarnings("nls")
	protected void submitScheduledJobsOnRollback() throws DataException {
		for(JobSchedulerProvider jsp : schedulerHandlers) {
			List<JobDescription> scheduledJobs = jsp.getScheduledJobs();
			List<JobDescription> nonTransactionalJobs = SelectionUtils.selectByProperty(JobDescription::isNonTransactional, true, scheduledJobs);
			jsp.getScheduler().submitJobs(nonTransactionalJobs);
			logJobs("submitted jobs after rollback", nonTransactionalJobs.size());
		}
	}
	
	/**
	 * Clears the scheduled jobs. This must be called after rollback or commit.
	 */
	@SuppressWarnings("nls")
	protected void clearScheduledJobs() {
		for(JobSchedulerProvider jsp : schedulerHandlers) {
			logJobs("clearing jobs", jsp.getScheduledJobs().size());
			jsp.clearJobs();
		}
	}
	
	/**
	 * Logs the job count of if it is greater than 0 with a message.
	 *
	 * @param msg the msg
	 * @param jobs the jobs
	 */
	void logJobs(String msg, int jobs) {
		if(jobs > 0) {
			LOGGER.debug(msg + ": " + jobs); //$NON-NLS-1$
		}
	}
}