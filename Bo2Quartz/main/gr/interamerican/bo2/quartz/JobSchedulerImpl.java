package gr.interamerican.bo2.quartz;

import static org.quartz.JobBuilder.newJob;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.impl.open.job.JobScheduler;
import gr.interamerican.bo2.impl.open.job.JobStatus;
import gr.interamerican.bo2.quartz.schedule.GenericQuartzJob;
import gr.interamerican.bo2.quartz.util.QuartzUtils;

import java.util.List;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;


/**
 * implementation of {@link JobScheduler}
 */
public class JobSchedulerImpl implements JobScheduler {

	/**
	 * submits a single job.
	 * 
	 * @param jobDescription
	 * @throws DataException
	 */
	void submitJob(JobDescription jobDescription) throws DataException {
		Scheduler scheduler = QuartzSchedulerRegistry.getScheduler();
		jobDescription.setExecutionStatus(JobStatus.SCHEDULED);
		JobDataMap map = new JobDataMap();
		map.put(QuartzConstants.BEAN_PROP, jobDescription);
		map.put(QuartzConstants.SESSION_PROP, Bo2Session.getSession());
		String jobName = QuartzUtils.getJobName(jobDescription);
		String groupName = QuartzUtils.getJobGroupName(jobDescription);
		JobDetail job = newJob().withIdentity(jobName, groupName).withDescription(groupName).usingJobData(map)
				.ofType(GenericQuartzJob.class).build();
		Trigger trigger = TriggerBuilder.newTrigger().startNow().build();
		try {
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			throw new DataException(e);
		}
		jobDescription.setJobName(jobName);
	}

	@Override
	public void submitJobs(List<JobDescription> jobDescriptions) throws DataException {
		for (JobDescription description : jobDescriptions) {
			submitJob(description);
		}
		QuartzSchedulerRegistry.appendJobDescription(jobDescriptions);
		for (JobDescription description : jobDescriptions) {
			if (description.isSynchronous()) {
				QuartzUtils.waitJobToComplete(description);
			}
		}
	}
}
