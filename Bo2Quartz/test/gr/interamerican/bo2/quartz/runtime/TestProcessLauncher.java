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
 * The Class TestProcessLauncher.
 */
public class TestProcessLauncher {

	/** The out content. */
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	/** The err content. */
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	
	/** original syso. */
	private PrintStream origout = null;
	
	/** original syserr. */
	private PrintStream origerr = null;

	/**
	 * Sets the up streams.
	 */
	@Before
	public void setUpStreams() {
		origout = System.out;
		origerr = System.err;
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	/**
	 * Clean up streams.
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
	 * @throws DataException the data exception
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
	 * @throws DataException the data exception
	 */
	@Test
	public void testMemorySettings() throws DataException {
		MemorySetting settings = Factory.create(MemorySetting.class);
		settings.setMinMemory(100L);
		QuartzSchedulerRegistry.clearScheduledJobDescriptions();
		ProcessLauncher.launchMultilauncher(SampleRunTimeCommand.class, settings);
		Assert.assertTrue(QuartzUtils.getNumberOfScheduledJobs(StreamRedirectOperation.class
				.getName()) == 1);
		QuartzUtils.waitGroupToComplete(StreamRedirectOperation.class.getName());
		Assert.assertTrue(outContent.toString().contains(SampleRunTimeCommand.class.getName()));
		// maybe 96??
		// Assert.assertTrue(outContent.toString().contains("95")); //$NON-NLS-1$ // due to rounding
		QuartzSchedulerRegistry.clearScheduledJobDescriptions();
	}

	/**
	 * test method for {@link ProcessLauncher#extractProcessFromJobDescription(JobDescription)}.
	 *
	 * @throws Exception the data exception
	 */
	@Test
	public void testExtractProcessFromJobDescription() throws Exception {
		JobDescription bean = ProcessLauncher.launchMultilauncher(SampleRunTimeCommand.class, null);
		Process p = ProcessLauncher.extractProcessFromJobDescription(bean);
		Assert.assertNotNull(p);
		p.waitFor();
		Assert.assertEquals(0, p.exitValue());
	}

	/**
	 * test method for {@link ProcessLauncher#killProcessFromJobDescription(JobDescription)}.
	 *
	 * @throws DataException the data exception
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
