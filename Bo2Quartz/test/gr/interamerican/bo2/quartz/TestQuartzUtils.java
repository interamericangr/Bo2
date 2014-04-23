/**
 * 
 */
package gr.interamerican.bo2.quartz;

import static org.junit.Assert.fail;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.impl.open.job.JobScheduler;
import gr.interamerican.bo2.quartz.samples.SampleOperation;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;


/**
 * 
 */
public class TestQuartzUtils {

	/**
	 * bean without parameters
	 */
	public static final JobDescription bean;
	static {
		bean = Factory.create(JobDescription.class);
		bean.setOperationClass(SampleOperation.class);
	}
	/**
	 * bean with parameters
	 */
	public static final JobDescription beanWithParams;
	static {
		beanWithParams = Factory.create(JobDescription.class);
		beanWithParams.setOperationClass(SampleOperation.class);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("waitTime", 100); //$NON-NLS-1$
		beanWithParams.setParameters(params);
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.QuartzUtils#getJobName(JobDescription))}.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testGetJobName() throws DataException {
		JobScheduler jobScheduler = Factory.create(JobScheduler.class);
		jobScheduler.submitJob(bean);
		Assert.assertNotNull(QuartzUtils.getJobName(bean));
		Assert.assertEquals(bean.getOperationClass().getName(), QuartzUtils.getJobName(bean));
		jobScheduler.submitJob(beanWithParams);
		Assert.assertNotNull(QuartzUtils.getJobName(beanWithParams));
		Assert.assertNotEquals(beanWithParams.getOperationClass().getName(), QuartzUtils.getJobName(beanWithParams));
		Assert.assertTrue(QuartzUtils.getJobName(beanWithParams).contains("100")); //$NON-NLS-1$
		Assert.assertTrue(QuartzUtils.getJobName(beanWithParams).contains("waitTime")); //$NON-NLS-1$
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.bo2.quartz.QuartzUtils#getJobGroupName(gr.interamerican.bo2.quartz.JobDescription)}.
	 */
	@Test
	public void testGetJobGroupNameJobDescription() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.QuartzUtils#getJobGroupName(java.lang.Class)}.
	 */
	@Test
	public void testGetJobGroupNameClassOfQextendsOperation() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.QuartzUtils#getScheduledJobKeys(java.lang.String)}.
	 */
	@Test
	public void testGetScheduledJobKeys() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.QuartzUtils#getNumberOfScheduledJobs(java.lang.String)}.
	 */
	@Test
	public void testGetNumberOfScheduledJobs() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.QuartzUtils#getMaximumNumberOfThreads()}.
	 */
	@Test
	public void testGetMaximumNumberOfThreads() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.QuartzUtils#isJobScheduled(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testIsJobScheduled() {
		fail("Not yet implemented");
	}
}
