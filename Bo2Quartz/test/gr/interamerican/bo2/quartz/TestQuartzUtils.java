package gr.interamerican.bo2.quartz;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.impl.open.job.JobScheduler;
import gr.interamerican.bo2.quartz.samples.SampleOperation;
import gr.interamerican.bo2.quartz.util.QuartzUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		params.put("waitTime", 5); //$NON-NLS-1$
		beanWithParams.setParameters(params);
	}

	/**
	 * single bean list
	 */
	public static final List<JobDescription> singleBeanList;
	static{
		singleBeanList=new ArrayList<JobDescription>();
		singleBeanList.add(bean);
	}
	/**
	 * single bean list
	 */
	public static final List<JobDescription> dualBeanList;
	static{
		dualBeanList=new ArrayList<JobDescription>();
		dualBeanList.add(bean);
		dualBeanList.add(beanWithParams);
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.util.QuartzUtils#getJobName(JobDescription)}.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testGetJobName() throws DataException {
		JobScheduler jobScheduler = new QuartzJobSchedulerImpl();
		jobScheduler.submitJobs(dualBeanList);
		Assert.assertNotNull(QuartzUtils.getJobName(bean));
		Assert.assertEquals(bean.getOperationClass().getName(), QuartzUtils.getJobName(bean));
		Assert.assertNotNull(QuartzUtils.getJobName(beanWithParams));
		Assert.assertNotEquals(beanWithParams.getOperationClass().getName(), QuartzUtils.getJobName(beanWithParams));
		Assert.assertTrue(QuartzUtils.getJobName(beanWithParams).contains("5")); //$NON-NLS-1$
		Assert.assertTrue(QuartzUtils.getJobName(beanWithParams).contains("waitTime")); //$NON-NLS-1$
		QuartzUtils.waitJobToComplete(bean);
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.util.QuartzUtils#getJobGroupName(JobDescription)}.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testGetJobGroupNameJobDescription() throws DataException {
		Assert.assertNotNull(QuartzUtils.getJobGroupName(bean));
		Assert.assertEquals(bean.getOperationClass().getName(), QuartzUtils.getJobGroupName(bean));
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.util.QuartzUtils#getJobGroupName(java.lang.Class)}.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testGetJobGroupNameClassOfQextendsOperation() throws DataException {
		Assert.assertNotNull(QuartzUtils.getJobGroupName(SampleOperation.class));
		Assert.assertEquals(SampleOperation.class.getName(), QuartzUtils.getJobGroupName(SampleOperation.class));
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.util.QuartzUtils#getScheduledJobKeys(java.lang.String)}.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testGetScheduledJobKeys() throws DataException {
		JobScheduler jobScheduler = new QuartzJobSchedulerImpl();
		jobScheduler.submitJobs(singleBeanList);
		Set<JobKey> s = QuartzUtils.getScheduledJobKeys(QuartzUtils.getJobGroupName(bean));
		Assert.assertNotNull(s);
		QuartzUtils.waitJobToComplete(bean);
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.util.QuartzUtils#getNumberOfScheduledJobs(java.lang.String)}.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testGetNumberOfScheduledJobs() throws DataException {
		JobScheduler jobScheduler = new QuartzJobSchedulerImpl();
		jobScheduler.submitJobs(singleBeanList);
		Assert.assertEquals(1, QuartzUtils.getNumberOfScheduledJobs(QuartzUtils.getJobGroupName(bean)));
		Assert.assertEquals(1, QuartzUtils.getNumberOfScheduledJobs(null));
		QuartzUtils.waitJobToComplete(bean);
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.util.QuartzUtils#getMaximumNumberOfThreads()}.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testGetMaximumNumberOfThreads() throws DataException {
		Assert.assertEquals(QuartzUtils.getMaximumNumberOfThreads(), 50);
	}

	/**
	 * Test method for {@link QuartzUtils#isJobScheduled(String, String)}.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testIsJobScheduled() throws DataException {
		JobScheduler jobScheduler = new QuartzJobSchedulerImpl();
		jobScheduler.submitJobs(singleBeanList);
		Assert.assertTrue(QuartzUtils.isJobScheduled(QuartzUtils.getJobGroupName(SampleOperation.class),
				QuartzUtils.getJobName(bean)));
		Assert.assertTrue(QuartzUtils.isJobScheduled(null, QuartzUtils.getJobName(bean)));
		QuartzUtils.waitJobToComplete(bean);
	}

	/**
	 * test method for {@link QuartzUtils#getParamFromQuartzDescriptionBean(QuartzjobDescription, String)}
	 */
	@Test
	public void testGetParamFromQuartzDescriptionBean() {
		String keyName = "a";//$NON-NLS-1$
		QuartzjobDescription bean = Factory.create(QuartzjobDescription.class);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(keyName, new Long(1));
		bean.setParameters(map);
		Long a = (Long) QuartzUtils.getParamFromQuartzDescriptionBean(bean, keyName);
		Assert.assertEquals(a, new Long(1));
	}
}
