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
 * The Class TestQuartzSchedulerRegistry.
 */
public class TestQuartzSchedulerRegistry {

	/**
	 * Test method for {@link QuartzSchedulerRegistry#getScheduler()}.
	 *
	 * @throws SchedulerException the scheduler exception
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
	 * @throws DataException the data exception
	 */
	@Test
	public void testGetJobDescriptionBasedOnStatus() throws DataException {
		QuartzSchedulerRegistry.clearScheduledJobDescriptions();
		JobScheduler jobScheduler = new QuartzJobSchedulerImpl();
		assertJobsWithStatusCount(JobStatus.OK, 0);
		assertJobsWithStatusCount(JobStatus.SCHEDULED, 0);
		assertJobsWithStatusCount(JobStatus.RUNNING, 0);
		
		jobScheduler.submitJobs(TestQuartzUtils.singleBeanList);
		int sched = QuartzSchedulerRegistry.getJobDescriptionBasedOnStatus(JobStatus.SCHEDULED).size();
		int runn = QuartzSchedulerRegistry.getJobDescriptionBasedOnStatus(JobStatus.RUNNING).size();
		Assert.assertTrue(sched==1 || runn==1);
		QuartzUtils.waitGroupToComplete(null);
		
		assertJobsWithStatusCount(JobStatus.OK, 1);
		assertJobsWithStatusCount(JobStatus.SCHEDULED, 0);
		assertJobsWithStatusCount(JobStatus.RUNNING, 0);
		
		jobScheduler.submitJobs(TestQuartzUtils.dualBeanList);
		QuartzUtils.waitGroupToComplete(null);
		
		assertJobsWithStatusCount(JobStatus.OK, 3);
		assertJobsWithStatusCount(JobStatus.SCHEDULED, 0);
		assertJobsWithStatusCount(JobStatus.RUNNING, 0);
	}
	
	/**
	 * Assert jobs with status count.
	 *
	 * @param status the status
	 * @param expectedCount the expected count
	 */
	void assertJobsWithStatusCount(JobStatus status, int expectedCount) {
		Assert.assertEquals(expectedCount, QuartzSchedulerRegistry.getJobDescriptionBasedOnStatus(status).size());
	}
	
}
