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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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
	 * job
	 */
	JobDescription nonTxJob = Factory.create(JobDescription.class);
	
	/**
	 * create job sample and reset TestOperation.executed
	 */
	@Before
	@SuppressWarnings("nls")
	public void before() {
		TestOperation.times = new AtomicInteger(0);
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("foo", "a");
		
		job.setOperationClass(TestOperation.class);
		job.setParameters(parameters);
		job.setSynchronous(false);
		
		parameters = new HashMap<String, Object>();
		parameters.put("foo", "b");
		
		nonTxJob.setOperationClass(TestOperation.class);
		nonTxJob.setNonTransactional(true);
		nonTxJob.setParameters(parameters);
		nonTxJob.setSynchronous(false);
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
				prov.scheduleJob(nonTxJob);
				
				Assert.assertEquals(0, TestOperation.times.get());
				Assert.assertEquals(1, tm.schedulerHandlers.size());
				Assert.assertEquals(2, tm.schedulerHandlers.iterator().next().getScheduledJobs().size());
			}
		}.execute();
		
		QuartzUtils.waitGroupToComplete(null);
		
		Assert.assertEquals(0, tm.schedulerHandlers.size());
		
		Assert.assertEquals(2, TestOperation.times.get());
		
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
					prov.scheduleJob(nonTxJob);
					
					Assert.assertEquals(0, TestOperation.times.get());
					Assert.assertEquals(1, tm.schedulerHandlers.size());
					Assert.assertEquals(2, tm.schedulerHandlers.iterator().next().getScheduledJobs().size());
					
					throw new RuntimeException(); //fail the uow
				}
			}.execute();
		} catch(UnexpectedException rtex) {/* ok */}
		
		QuartzUtils.waitGroupToComplete(null);
		
		Assert.assertEquals(1, tm.schedulerHandlers.size());
		
		Assert.assertEquals(1, TestOperation.times.get());
		
	}
	
	@SuppressWarnings("javadoc")
	public static class TestOperation extends AbstractOperation {
		static AtomicInteger times;
		
		String foo;

		@Override
		public void execute() throws LogicException, DataException {
			System.out.println(times.incrementAndGet() + foo);
		}
		
		public void setFoo(String foo) {
			this.foo = foo;
		}
	}
	
}
