package gr.interamerican.bo2.quartz.schedule;

import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.samples.providers.EmptyProvider;

import org.junit.Test;

/**
 * Unit Tests of {@link GenericQuartzJob}.
 */
public class TestGenericQuartzJob {

	/**
	 * Test method for {@link GenericQuartzJob#GenericQuartzJob()}.
	 */
	@Test
	public void testGenericQuartzJob() {
		Bo2Session.setProvider(new EmptyProvider());
		GenericQuartzJob job = new GenericQuartzJob();
		assertNotNull(job.t);
	}

//	/**
//	 * Test method for {@link GenericQuartzJob#initialize()}.
//	 */
//	@Test
//	public void testInitialize() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link GenericQuartzJob#generateOperationFromBean(JobDescription)}.
//	 */
//	@Test
//	public void testGenerateOperationFromBean() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link GenericQuartzJob#logMe(Throwable, QuartzjobDescription)}.
//	 */
//	@Test
//	public void testLogMe() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link GenericQuartzJob#execute(JobExecutionContext)}.
//	 */
//	@Test
//	public void testExecute() {
//		fail("Not yet implemented");
//	}
}