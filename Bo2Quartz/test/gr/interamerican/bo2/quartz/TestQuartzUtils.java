package gr.interamerican.bo2.quartz;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.impl.open.job.JobScheduler;
import gr.interamerican.bo2.impl.open.job.JobStatus;
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
 * The Class TestQuartzUtils.
 */
public class TestQuartzUtils {

	/** bean without parameters. */
	static final JobDescription bean;
	static {
		bean = Factory.create(JobDescription.class);
		bean.setOperationClass(SampleOperation.class);
	}
	/**
	 * same as above.
	 */
	static final JobDescription duplicateBean;
	static {
		duplicateBean = Factory.create(JobDescription.class);
		duplicateBean.setOperationClass(SampleOperation.class);
	}
	/**
	 * parameter name for bean with param.
	 */
	private static final String PARAM_FOR_BEAN = "waitTime"; //$NON-NLS-1$
	/**
	 * parameter value for bean with param.
	 */
	private static final int PARAM_VALUE_FOR_BEAN = 5;
	
	/** bean with parameters. */
	public static final JobDescription beanWithParams;
	static {
		beanWithParams = Factory.create(JobDescription.class);
		beanWithParams.setOperationClass(SampleOperation.class);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PARAM_FOR_BEAN, PARAM_VALUE_FOR_BEAN);
		beanWithParams.setParameters(params);
	}

	/** single bean list. */
	public static final List<JobDescription> singleBeanList;
	static{
		singleBeanList=new ArrayList<JobDescription>();
		singleBeanList.add(bean);
	}
	
	/** single bean list. */
	public static final List<JobDescription> singleBeanListWithParam;
	static {
		singleBeanListWithParam = new ArrayList<JobDescription>();
		singleBeanListWithParam.add(beanWithParams);
	}
	
	/** single bean list. */
	public static final List<JobDescription> dualBeanList;
	static{
		dualBeanList=new ArrayList<JobDescription>();
		dualBeanList.add(bean);
		dualBeanList.add(beanWithParams);
	}
	
	/** single bean list. */
	public static final List<JobDescription> dualDuplicateBeanList;
	static {
		dualDuplicateBeanList = new ArrayList<JobDescription>();
		dualDuplicateBeanList.add(bean);
		dualDuplicateBeanList.add(duplicateBean);
	}

	/**
	 * Test schedule duplicate job description bean.
	 *
	 * @throws DataException the data exception
	 */
	@Test
	public void testScheduleDuplicateJobDescriptionBean() throws DataException {
		JobScheduler jobScheduler = new QuartzJobSchedulerImpl();
		jobScheduler.submitJobs(dualDuplicateBeanList);
		Assert.assertTrue(QuartzUtils.getNumberOfScheduledJobs(null) == 2);
		QuartzUtils.waitJobToComplete(bean);
	}
	
	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.util.QuartzUtils#getJobName(JobDescription)}.
	 *
	 * @throws DataException the data exception
	 */
	@Test
	public void testGetJobName() throws DataException {
		JobScheduler jobScheduler = new QuartzJobSchedulerImpl();
		jobScheduler.submitJobs(dualBeanList);
		Assert.assertNotNull(bean.getJobName());
		Assert.assertTrue(bean.getJobName().contains(bean.getOperationClass().getName()));
		Assert.assertNotNull(beanWithParams.getJobName());
		Assert.assertNotEquals(beanWithParams.getOperationClass().getName(),
				beanWithParams.getJobName());
		Assert.assertTrue(beanWithParams.getJobName()
				.contains(String.valueOf(PARAM_VALUE_FOR_BEAN)));
		Assert.assertTrue(beanWithParams.getJobName().contains(PARAM_FOR_BEAN));
		QuartzUtils.waitJobToComplete(bean);
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.util.QuartzUtils#getJobGroupName(JobDescription)}.
	 */
	@Test
	public void testGetJobGroupNameJobDescription() {
		Assert.assertNotNull(QuartzUtils.getJobGroupName(bean));
		Assert.assertEquals(bean.getOperationClass().getName(), QuartzUtils.getJobGroupName(bean));
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.util.QuartzUtils#getJobGroupName(java.lang.Class)}.
	 */
	@Test
	public void testGetJobGroupNameClassOfQextendsOperation() {
		Assert.assertNotNull(QuartzUtils.getJobGroupName(SampleOperation.class));
		Assert.assertEquals(SampleOperation.class.getName(), QuartzUtils.getJobGroupName(SampleOperation.class));
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.util.QuartzUtils#getScheduledJobKeys(java.lang.String)}.
	 *
	 * @throws DataException the data exception
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
	 * @throws DataException the data exception
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
	 * @throws DataException the data exception
	 */
	@Test
	public void testGetMaximumNumberOfThreads() throws DataException {
		Assert.assertEquals(QuartzUtils.getMaximumNumberOfThreads(), 50);
	}

	/**
	 * Test method for {@link QuartzUtils#isJobScheduled(String, String)}.
	 *
	 * @throws DataException the data exception
	 */
	@Test
	public void testIsJobScheduled() throws DataException {
		JobScheduler jobScheduler = new QuartzJobSchedulerImpl();
		jobScheduler.submitJobs(singleBeanList);
		Assert.assertTrue(QuartzUtils.isJobScheduled(QuartzUtils.getJobGroupName(SampleOperation.class),
				bean.getJobName()));
		Assert.assertTrue(QuartzUtils.isJobScheduled(null, bean.getJobName()));
		QuartzUtils.waitJobToComplete(bean);
	}

	/**
	 * test method for {@link QuartzUtils#getParamFromJobDescriptionBean(JobDescription, String)}.
	 */
	@Test
	public void testGetParamFromQuartzDescriptionBean() {
		String keyName = "a";//$NON-NLS-1$
		JobDescription bean1 = Factory.create(JobDescription.class);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(keyName, new Long(1));
		bean1.setParameters(map);
		Long a = (Long) QuartzUtils.getParamFromJobDescriptionBean(bean1, keyName);
		Assert.assertEquals(a, new Long(1));
	}

	/**
	 * test method for
	 * {@link QuartzUtils#getStringParamFromQuartzDescriptionBean(QuartzjobDescription, String)}.
	 *
	 * @throws DataException the data exception
	 */
	@Test
	public void testGetStringParamFromQuartzDescriptionBean() throws DataException {
		QuartzSchedulerRegistry.clearScheduledJobDescriptions();
		JobScheduler jobScheduler = new QuartzJobSchedulerImpl();
		jobScheduler.submitJobs(singleBeanListWithParam);
		List<QuartzjobDescription> descriptions = new ArrayList<QuartzjobDescription>();
		descriptions.addAll(QuartzSchedulerRegistry
				.getJobDescriptionBasedOnStatus(JobStatus.SCHEDULED));
		descriptions.addAll(QuartzSchedulerRegistry
				.getJobDescriptionBasedOnStatus(JobStatus.RUNNING));
		QuartzjobDescription bean1 = descriptions.get(0);
		String result = QuartzUtils.getStringParamFromQuartzDescriptionBean(bean1, PARAM_FOR_BEAN);
		Assert.assertNotNull(result);
		Assert.assertEquals(result, String.valueOf(PARAM_VALUE_FOR_BEAN));
		QuartzUtils.waitJobToComplete(singleBeanListWithParam.get(0));
		QuartzSchedulerRegistry.clearScheduledJobDescriptions();
	}
}
