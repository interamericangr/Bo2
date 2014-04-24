package gr.interamerican.bo2.impl.open.job;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.quartz.QuartzJobSchedulerProviderImpl;
import gr.interamerican.bo2.quartz.util.QuartzUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Integration test 
 */
public class TestQuartzJobSchedulerProvider {
	
	/**
	 * tm
	 */
	JobCapableTransactionManager tm;
	
	/**
	 * job
	 */
	JobDescription job = Factory.create(JobDescription.class);
	
	/**
	 * create job sample and reset TestOperation.executed
	 */
	@Before
	public void before() {
		TestOperation.executed = false;
		job.setOperationClass(TestOperation.class);
	}
	
	/**
	 * integration test
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException
	 */
	@Test
	public void integrationTest() throws DataException, LogicException, UnexpectedException {
		new AbstractBo2RuntimeCmd() { 			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				tm = (JobCapableTransactionManager) getProvider().getTransactionManager();
				
				Assert.assertEquals(0, tm.schedulerHandlers.size());
				
				JobSchedulerProvider prov = getProvider().getResource("LOCALDB", JobSchedulerProvider.class); //$NON-NLS-1$
				Assert.assertTrue(prov instanceof QuartzJobSchedulerProviderImpl);
				Assert.assertEquals(1, tm.schedulerHandlers.size());
				
				prov.scheduleJob(job);
				
				Assert.assertFalse(TestOperation.executed);
			}
		}.execute();
		
		Assert.assertEquals(0, tm.schedulerHandlers.size());
		
		QuartzUtils.waitJobToComplete(job);
		
		Assert.assertTrue(TestOperation.executed);
		
		
	}
	
	/**
	 * integration test
	 * @throws LogicException 
	 * @throws DataException 
	 */
	@Test
	public void integrationTest_rollback() throws DataException, LogicException {
		try {
			new AbstractBo2RuntimeCmd() { 			
				@Override public void work() throws LogicException, 
				DataException, InitializationException, UnexpectedException {
					tm = (JobCapableTransactionManager) getProvider().getTransactionManager();
					
					Assert.assertEquals(0, tm.schedulerHandlers.size());
					
					JobSchedulerProvider prov = getProvider().getResource("LOCALDB", JobSchedulerProvider.class); //$NON-NLS-1$
					
					Assert.assertTrue(prov instanceof QuartzJobSchedulerProviderImpl);
					Assert.assertEquals(1, tm.schedulerHandlers.size());
					
					prov.scheduleJob(job);
					
					Assert.assertFalse(TestOperation.executed);
					
					throw new RuntimeException(); //fail the uow
				}
			}.execute();
		} catch(UnexpectedException rtex) {/* ok */}
		
		Assert.assertEquals(0, tm.schedulerHandlers.size());
		
		QuartzUtils.waitJobToComplete(job);
		
		Assert.assertFalse(TestOperation.executed);
		
	}
	
	@SuppressWarnings("javadoc")
	public static class TestOperation extends AbstractOperation {
		static boolean executed = false;

		@Override
		public void execute() throws LogicException, DataException {
			executed = true;
		}
	}
	
}
