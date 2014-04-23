package gr.interamerican.bo2.quartz;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.impl.open.job.JobScheduler;
import gr.interamerican.bo2.quartz.samples.SampleOperation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.quartz.JobKey;


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
	 * Test method for {@link gr.interamerican.bo2.quartz.QuartzUtils#getJobName(JobDescription)}.
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
		QuartzUtils.waitJobToComplete(bean);
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.QuartzUtils#getJobGroupName(JobDescription)}.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testGetJobGroupNameJobDescription() throws DataException {
		Assert.assertNotNull(QuartzUtils.getJobGroupName(bean));
		Assert.assertEquals(bean.getOperationClass().getName(), QuartzUtils.getJobGroupName(bean));
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.QuartzUtils#getJobGroupName(java.lang.Class)}.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testGetJobGroupNameClassOfQextendsOperation() throws DataException {
		Assert.assertNotNull(QuartzUtils.getJobGroupName(SampleOperation.class));
		Assert.assertEquals(SampleOperation.class.getName(), QuartzUtils.getJobGroupName(SampleOperation.class));
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.QuartzUtils#getScheduledJobKeys(java.lang.String)}.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testGetScheduledJobKeys() throws DataException {
		JobScheduler jobScheduler = Factory.create(JobScheduler.class);
		jobScheduler.submitJob(bean);
		Set<JobKey> s = QuartzUtils.getScheduledJobKeys(QuartzUtils.getJobGroupName(bean));
		Assert.assertNotNull(s);
		QuartzUtils.waitJobToComplete(bean);
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.QuartzUtils#getNumberOfScheduledJobs(java.lang.String)}.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testGetNumberOfScheduledJobs() throws DataException {
		JobScheduler jobScheduler = Factory.create(JobScheduler.class);
		jobScheduler.submitJob(bean);
		Assert.assertEquals(1, QuartzUtils.getNumberOfScheduledJobs(QuartzUtils.getJobGroupName(bean)));
		Assert.assertEquals(1, QuartzUtils.getNumberOfScheduledJobs(null));
		QuartzUtils.waitJobToComplete(bean);
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.QuartzUtils#getMaximumNumberOfThreads()}.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testGetMaximumNumberOfThreads() throws DataException {
		Assert.assertEquals(QuartzUtils.getMaximumNumberOfThreads(), 50);
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.bo2.quartz.QuartzUtils#isJobScheduled(java.lang.String, java.lang.String)}.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testIsJobScheduled() throws DataException {
		JobScheduler jobScheduler = Factory.create(JobScheduler.class);
		jobScheduler.submitJob(bean);
		Assert.assertTrue(QuartzUtils.isJobScheduled(QuartzUtils.getJobGroupName(SampleOperation.class),
				bean.getJobName()));
		Assert.assertTrue(QuartzUtils.isJobScheduled(null, bean.getJobName()));
		QuartzUtils.waitJobToComplete(bean);
	}
}
