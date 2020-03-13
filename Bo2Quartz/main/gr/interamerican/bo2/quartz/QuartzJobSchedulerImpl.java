package gr.interamerican.bo2.quartz;

import static org.quartz.JobBuilder.newJob;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.impl.open.job.JobScheduler;
import gr.interamerican.bo2.impl.open.job.JobStatus;
import gr.interamerican.bo2.quartz.schedule.GenericQuartzJob;
import gr.interamerican.bo2.quartz.util.QuartzUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;

import java.util.List;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;


/**
 * implementation of {@link JobScheduler}.
 */
public class QuartzJobSchedulerImpl implements JobScheduler {
	
	/**
	 * submits a single job.
	 *
	 * @param jobDescription the job description
	 * @throws DataException the data exception
	 */
	void submitJob(JobDescription jobDescription) throws DataException {
		QuartzjobDescription quartzjobDescription = Factory.create(QuartzjobDescription.class);
		// ReflectionUtils.copyProperties(jobDescription, quartzjobDescription);
		quartzjobDescription.setJobDescriptionDigest(QuartzUtils
				.getDigestFromJobDescription(jobDescription));
		Scheduler scheduler = QuartzSchedulerRegistry.getScheduler();
		quartzjobDescription.setExecutionStatus(JobStatus.SCHEDULED);
		JobDataMap map = new JobDataMap();
		map.put(QuartzConstants.BEAN_PROP, jobDescription);
		map.put(QuartzConstants.SCHEDULED_BEAN_PROP, quartzjobDescription);
		map.put(QuartzConstants.SESSION_PROP, Bo2Session.getSession());
		String jobName = QuartzUtils.generateRandomQuartzJobName(jobDescription);
		String groupName = QuartzUtils.getJobGroupName(jobDescription);
		JobDetail job = newJob().withIdentity(jobName, groupName).withDescription(groupName).usingJobData(map)
				.ofType(GenericQuartzJob.class).build();
		Trigger trigger = TriggerBuilder.newTrigger().startNow().build();
		try {
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			throw new DataException(e);
		}
		quartzjobDescription.setJobName(jobName);
		QuartzSchedulerRegistry.appendJobDescription(quartzjobDescription);
		ReflectionUtils.set("jobName", jobName, jobDescription); //$NON-NLS-1$
	}

	@Override
	public void submitJobs(List<JobDescription> jobDescriptions) throws DataException {
		QuartzUtils.LOGGER.debug(jobDescriptions.size() + " jobs submitted."); //$NON-NLS-1$
		for (JobDescription description : jobDescriptions) {
			submitJob(description);
		}
		for (JobDescription description : jobDescriptions) {
			if (description.isSynchronous()) {
				QuartzUtils.waitJobToComplete(description);
			}
		}
	}
}
