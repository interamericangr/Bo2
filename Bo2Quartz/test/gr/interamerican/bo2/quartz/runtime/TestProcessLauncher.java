/**
 *
 */
package gr.interamerican.bo2.quartz.runtime;

import gr.interamerican.bo2.arch.exceptions.DataException;
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
	}
	/**
	 * Test method for
	 * {@link gr.interamerican.bo2.quartz.runtime.ProcessLauncher#launch(java.util.Map, java.lang.String[])}
	 * @throws DataException
	 */
	@Test
	public void testLaunch() throws DataException {
		QuartzSchedulerRegistry.clearScheduledJobDescriptions();
		ProcessLauncher.launchMultilauncher(SampleRunTimeCommand.class);
		Assert.assertTrue(QuartzUtils.getNumberOfScheduledJobs(StreamRedirectOperation.class
				.getName()) == 1);
		QuartzUtils.waitGroupToComplete(StreamRedirectOperation.class.getName());
		Assert.assertTrue(outContent.toString().contains(SampleRunTimeCommand.class.getName()));
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
		JobDescription bean = ProcessLauncher.launchMultilauncher(SampleRunTimeCommand.class);
		Process p = ProcessLauncher.extractProcessFromJobDescription(bean);
		Assert.assertNotNull(p);
		QuartzUtils.waitGroupToComplete(StreamRedirectOperation.class.getName());
		Assert.assertEquals(0, p.exitValue());
		QuartzSchedulerRegistry.clearScheduledJobDescriptions();
	}
}
