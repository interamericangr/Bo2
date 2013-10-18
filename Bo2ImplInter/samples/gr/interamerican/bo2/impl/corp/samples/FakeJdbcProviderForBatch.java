package gr.interamerican.bo2.impl.corp.samples;

import gr.interamerican.bo2.creation.proxies.Mocks;

import java.sql.Connection;

import javax.sql.XADataSource;

import jwf.JdbcProviderForBatch;

/**
 * Fake implemenation of {@link JdbcProviderForBatch}.
 */
public class FakeJdbcProviderForBatch 
implements JdbcProviderForBatch {

	public Connection doConnect(String url) throws Exception {
		return Mocks.fake(Connection.class);
	}

	@Override
	public Connection doConnect(XADataSource ds) {
		return Mocks.fake(Connection.class);
	}
	
}
