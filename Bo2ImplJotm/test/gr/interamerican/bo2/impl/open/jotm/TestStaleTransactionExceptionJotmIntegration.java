package gr.interamerican.bo2.impl.open.jotm;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.StaleTransactionException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.jdbc.JdbcSimpleCommand;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;

import org.junit.Test;

/**
 * Integration test for the case of a StaleTransactionManagerException.
 */
public class TestStaleTransactionExceptionJotmIntegration {
	
	/**
	 * testStaleTransactionJotmExceptionIntegration.
	 *
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 * @throws UnexpectedException the unexpected exception
	 */
	@Test(expected=StaleTransactionException.class)
	public void testStaleTransactionJotmExceptionIntegration() throws DataException, LogicException, UnexpectedException {
		new AbstractBo2RuntimeCmd(UtilityForBo2Test.BATCH_JTA_TRAN) {
			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				W w = open(new W());
				
				try {
					JotmTransactionManager.JOTM.getUserTransaction().setRollbackOnly();
				} catch (Exception e) {
					throw new UnexpectedException(e);
				}
				
				w.execute(); //will throw staletransactionexception
			}
		}.execute();
	}
	
	/**
	 * Sample worker.
	 */
	@ManagerName("LOCALDB")
	static class W extends JdbcSimpleCommand {
		
		@Override
		protected String sql() {
			return "select * from X__X.USERS"; //$NON-NLS-1$
		}
		
		@Override
		protected Object[] parameters() {
			return null;
		}
	}

}
