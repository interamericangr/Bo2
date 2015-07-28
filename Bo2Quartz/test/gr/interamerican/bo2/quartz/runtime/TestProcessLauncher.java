/**
 *
 */
package gr.interamerican.bo2.quartz.runtime;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.quartz.QuartzSchedulerRegistry;
import gr.interamerican.bo2.quartz.util.QuartzUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 *
 */
public class TestProcessLauncher {

	/**
	 *
	 */
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	/**
	 *
	 */
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	/**
	 * original syso
	 */
	private PrintStream origout = null;
	/**
	 * original syserr
	 */
	private PrintStream origerr = null;

	/**
	 *
	 */
	@Before
	public void setUpStreams() {
		origout = System.out;
		origerr = System.err;
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	/**
	 *
	 */
	@After
	public void cleanUpStreams() {
		System.setOut(origout);
		System.setErr(origerr);
		System.out.println(outContent.toString());
		System.out.println(errContent.toString());
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.bo2.quartz.runtime.ProcessLauncher#launchMultilauncher(Class,MemorySetting)}
	 *
	 * @throws DataException
	 */
	@Test
	public void testLaunch() throws DataException {
		QuartzSchedulerRegistry.clearScheduledJobDescriptions();
		ProcessLauncher.launchMultilauncher(SampleRunTimeCommand.class, null);
		Assert.assertTrue(QuartzUtils.getNumberOfScheduledJobs(StreamRedirectOperation.class
				.getName()) == 1);
		QuartzUtils.waitGroupToComplete(StreamRedirectOperation.class.getName());
		Assert.assertTrue(outContent.toString().contains(SampleRunTimeCommand.class.getName()));
		QuartzSchedulerRegistry.clearScheduledJobDescriptions();
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.bo2.quartz.runtime.ProcessLauncher#launchMultilauncher(Class,MemorySetting)}
	 * tests memory settings.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testMemorySettings() throws DataException {
		MemorySetting settings = Factory.create(MemorySetting.class);
		settings.setMinMemory(105L);// due to rounding
		QuartzSchedulerRegistry.clearScheduledJobDescriptions();
		ProcessLauncher.launchMultilauncher(SampleRunTimeCommand.class, settings);
		Assert.assertTrue(QuartzUtils.getNumberOfScheduledJobs(StreamRedirectOperation.class
				.getName()) == 1);
		QuartzUtils.waitGroupToComplete(StreamRedirectOperation.class.getName());
		Assert.assertTrue(outContent.toString().contains(SampleRunTimeCommand.class.getName()));
		Assert.assertTrue(outContent.toString().contains("100")); //$NON-NLS-1$
		QuartzSchedulerRegistry.clearScheduledJobDescriptions();
	}

	/**
	 * test method for {@link ProcessLauncher#extractProcessFromJobDescription(JobDescription)}
	 *
	 * @throws DataException
	 */
	@Test
	public void testExtractProcessFromJobDescription() throws DataException {
		QuartzSchedulerRegistry.clearScheduledJobDescriptions();
		JobDescription bean = ProcessLauncher.launchMultilauncher(SampleRunTimeCommand.class, null);
		Process p = ProcessLauncher.extractProcessFromJobDescription(bean);
		Assert.assertNotNull(p);
		QuartzUtils.waitGroupToComplete(StreamRedirectOperation.class.getName());
		Assert.assertEquals(0, p.exitValue());
		QuartzSchedulerRegistry.clearScheduledJobDescriptions();
	}

	/**
	 * test method for {@link ProcessLauncher#killProcessFromJobDescription(JobDescription)}
	 *
	 * @throws DataException
	 */
	@Test
	public void testKillProcessFromJobDescription() throws DataException {
		QuartzSchedulerRegistry.clearScheduledJobDescriptions();
		JobDescription bean = ProcessLauncher.launchMultilauncher(SampleRunTimeCommand.class, null);
		int e = ProcessLauncher.killProcessFromJobDescription(bean);
		Assert.assertNotEquals(e, 0);
		QuartzSchedulerRegistry.clearScheduledJobDescriptions();
	}
}
