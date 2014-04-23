/**
 * 
 */
package gr.interamerican.bo2.quartz;


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
}
