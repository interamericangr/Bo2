package gr.interamerican.bo2.impl.open.jdbc;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * JdbcPropertiesLoaderQuestion test.
 */
public class TestJdbcPropertiesLoaderQuestion {
	
	/** test subject. */
	JdbcPropertiesLoaderQuestion impl = new JdbcPropertiesLoaderQuestionImpl();
	
	/**
	 * test ask.
	 *
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 * @throws UnexpectedException the unexpected exception
	 */
	@Test
	public void testAsk() throws DataException, LogicException, UnexpectedException {
		new AbstractBo2RuntimeCmd() {
			
			@SuppressWarnings("nls")
			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				open(impl);
				impl.ask();
				Properties p = impl.getAnswer();
				Assert.assertFalse(p.isEmpty());
				Assert.assertEquals("1", p.getProperty("1"));
			}
		}.execute();
	}
	
	/**
	 * implementation to test.
	 */
	@ManagerName("LOCALDB")
	private class JdbcPropertiesLoaderQuestionImpl extends JdbcPropertiesLoaderQuestion {	
		/**
		 * sgl.
		 */		
		@Sql private String sql = "select '1','1' from X__X.users"; //$NON-NLS-1$
		
		@Override
		protected void handleDuplicateKey(Object dulpicateKey) throws DataException {
			/* empty */
		}
	}

}
