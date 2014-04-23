/**
 * 
 */
package gr.interamerican.bo2.quartz;

import static org.junit.Assert.fail;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.quartz.samples.SampleOperation;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


/**
 * 
 */
public class TestQuartzUtils {

	/**
	 * bean without parameters
	 */
	public static final QuartzJobDescritpionBean bean;
	static {
		bean = Factory.create(QuartzJobDescritpionBean.class);
		bean.setOperationClass(SampleOperation.class);
	}
	/**
	 * bean with parameters
	 */
	public static final QuartzJobDescritpionBean beanWithParams;
	static {
		beanWithParams = Factory.create(QuartzJobDescritpionBean.class);
		beanWithParams.setOperationClass(SampleOperation.class);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("waitTime", 100); //$NON-NLS-1$
		beanWithParams.setParameters(params);
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.bo2.quartz.QuartzUtils#getJobName(gr.interamerican.bo2.quartz.QuartzJobDescritpionBean)}.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testGetJobName() throws DataException {
		QuartzUtils.submitJob(bean);
		System.out.println(QuartzUtils.getJobName(bean));
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.quartz.QuartzUtils#getJobGroupName(gr.interamerican.bo2.quartz.QuartzJobDescritpionBean)}.
	 */
	@Test
	public void testGetJobGroupNameQuartzJobDescritpionBean() {
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
