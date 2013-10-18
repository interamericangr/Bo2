package gr.interamerican.bo2.impl.corp;

import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.jotm.JotmTransactionManager;
import gr.interamerican.bo2.impl.open.jotm.JotmUtils;

import java.sql.Connection;

import org.enhydra.jdbc.standard.StandardXADataSource;

/**
 * Extension of {@link ZLinuxConnectionStrategy} that provides Jdbc Connections
 * enlisted in the {@link JotmTransactionManager} instance of the current thread.
 */
public class JotmZLinuxConnectionStrategy extends ZLinuxConnectionStrategy {
	
	@Override
	public Connection doConnect() throws InitializationException {
		/*
		 * synchronized statically due to a bug in jdbcconn.jar
		 */
		synchronized (JotmZLinuxConnectionStrategy.class) {
			try {
				StandardXADataSource ds = new StandardXADataSource();
				JotmUtils.enListTransactionalDataSource(ds);
				ds.setUrl(dbUrl);
				ds.setDriverName(dbDriver);
				return getJdbcProviderForBatch().doConnect(ds);
			}
			catch (Exception e) {
				throw new InitializationException(e);
			}
		}
	}
	
	@Override
	protected Class<?>[] compatibleTransactionManagerImplementations() {
		return new Class<?>[]{JotmTransactionManager.class};
	}
	
}
