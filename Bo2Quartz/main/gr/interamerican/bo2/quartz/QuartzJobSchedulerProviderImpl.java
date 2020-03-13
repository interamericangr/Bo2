package gr.interamerican.bo2.quartz;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.impl.open.job.JobScheduler;
import gr.interamerican.bo2.impl.open.job.JobSchedulerProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Implementation of {@link JobSchedulerProvider} for Quartz.
 */
public class QuartzJobSchedulerProviderImpl implements JobSchedulerProvider {

	/** Scheduled jobs. */
	List<JobDescription> jobs = new ArrayList<JobDescription>();

	/** Concrete scheduler. */
	JobScheduler scheduler;

	/**
	 * Creates a new JobSchedulerProviderImpl object.
	 *
	 * @param p the p
	 */
	@SuppressWarnings("unused")
	public QuartzJobSchedulerProviderImpl(Properties p) {
		scheduler = new QuartzJobSchedulerImpl();
	}

	@Override
	public List<JobDescription> getScheduledJobs() {
		return Collections.unmodifiableList(jobs);
	}

	@Override
	public void clearJobs() {
		jobs.clear();
	}

	@Override
	public void close() throws DataException {
		clearJobs();
	}

	@Override
	public JobScheduler getScheduler() {
		return scheduler;
	}

	@Override
	public void scheduleJob(JobDescription job) {
		jobs.add(job);
	}
}