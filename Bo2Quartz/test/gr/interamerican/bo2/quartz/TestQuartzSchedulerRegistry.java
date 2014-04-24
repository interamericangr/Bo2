/**
 * 
 */
package gr.interamerican.bo2.quartz;


import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.job.JobScheduler;
import gr.interamerican.bo2.impl.open.job.JobStatus;
import gr.interamerican.bo2.quartz.util.QuartzUtils;

import org.junit.Assert;
import org.junit.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;


/**
 * 
 */
public class TestQuartzSchedulerRegistry {

	/**
	 * Test method for {@link QuartzSchedulerRegistry#getScheduler()}.
	 * 
	 * @throws SchedulerException
	 */
	@Test
	public void testGetScheduler() throws SchedulerException {
		Scheduler s = QuartzSchedulerRegistry.getScheduler();
		Assert.assertNotNull(s);
		Assert.assertEquals(QuartzConstants.SCHEDULER_NAME, s.getSchedulerName());
		Assert.assertTrue(s.isStarted());
	}

	/**
	 * test method for
	 * {@link QuartzSchedulerRegistry#getJobDescriptionBasedOnStatus(gr.interamerican.bo2.impl.open.job.JobStatus)}
	 * 
	 * @throws DataException
	 */
	@Test
	public void testGetJobDescriptionBasedOnStatus() throws DataException {
		JobScheduler jobScheduler = new QuartzJobSchedulerImpl();
		jobScheduler.submitJobs(TestQuartzUtils.singleBeanList);
		Assert.assertTrue(QuartzSchedulerRegistry.getJobDescriptionBasedOnStatus(JobStatus.SCHEDULED).size() == 1);
		QuartzUtils.waitGroupToComplete(null);
		Assert.assertTrue(QuartzSchedulerRegistry.getJobDescriptionBasedOnStatus(JobStatus.OK).size() == 1);
	}
}
