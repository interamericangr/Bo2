package gr.interamerican.bo2.quartz;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.impl.open.job.JobScheduler;
import gr.interamerican.bo2.impl.open.job.JobStatus;
import gr.interamerican.bo2.quartz.samples.SampleOperation;
import gr.interamerican.bo2.quartz.util.QuartzUtils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

/**
 * test suite to stress quartz
 */
public class TestStressQuartz {

	/**
	 * test method that schedules 1000 jobs and expects them to finisk ok.
	 *
	 * @throws DataException
	 */
	// @Test // DO NOT UNCOMMENT ME
	public void testSchedule1000() throws DataException {
		final int times = 1000;
		List<JobDescription> list = new ArrayList<JobDescription>();
		for (int i = 0; i < times; i++) {
			JobDescription bean = Factory.create(JobDescription.class);
			bean.setOperationClass(SampleOperation.class);
			list.add(bean);
		}
		JobScheduler jobScheduler = new QuartzJobSchedulerImpl();
		jobScheduler.submitJobs(list);
		Assert.assertEquals(times, QuartzUtils.getNumberOfScheduledJobs(null));
		QuartzUtils.waitGroupToComplete(QuartzUtils.getJobGroupName(SampleOperation.class));
		Assert.assertEquals(times,
				QuartzSchedulerRegistry.getJobDescriptionBasedOnStatus(JobStatus.OK).size());
	}
}
