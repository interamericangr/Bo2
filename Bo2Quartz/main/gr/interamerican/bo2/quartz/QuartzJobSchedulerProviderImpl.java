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

	/**
	 * Scheduled jobs
	 */
	List<JobDescription> jobs = new ArrayList<JobDescription>();

	/**
	 * Concrete scheduler
	 */
	JobScheduler scheduler;

	/**
	 * Creates a new JobSchedulerProviderImpl object.
	 * 
	 * @param p
	 */
	public QuartzJobSchedulerProviderImpl(Properties p) {
		scheduler = new QuartzJobSchedulerImpl();
	}

	public List<JobDescription> getScheduledJobs() {
		return Collections.unmodifiableList(jobs);
	}

	public void clearJobs() {
		jobs.clear();
	}

	public void close() throws DataException {
		clearJobs();
	}

	public JobScheduler getScheduler() {
		return scheduler;
	}

	public void scheduleJob(JobDescription job) {
		jobs.add(job);
	}

}
