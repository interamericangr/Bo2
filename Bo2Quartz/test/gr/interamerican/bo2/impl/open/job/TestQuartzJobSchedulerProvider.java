package gr.interamerican.bo2.impl.open.job;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.quartz.QuartzJobSchedulerProviderImpl;

import org.junit.Assert;
import org.junit.Test;

/**
 * Integration test 
 */
public class TestQuartzJobSchedulerProvider {
	
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
				JobCapableTransactionManager tm = (JobCapableTransactionManager) getProvider().getTransactionManager();
				
				Assert.assertEquals(0, tm.schedulerHandlers.size());
				JobSchedulerProvider prov = getProvider().getResource("LOCALDB", JobSchedulerProvider.class); //$NON-NLS-1$
				Assert.assertTrue(prov instanceof QuartzJobSchedulerProviderImpl);
				Assert.assertEquals(1, tm.schedulerHandlers.size());
			}
		}.execute();
	}
	
}
