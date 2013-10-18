package gr.interamerican.bo2.legacy;

import static gr.interamerican.bo2.utils.StringConstants.COMMA;
import gr.interamerican.bo2.arch.exceptions.DataOperationNotSupportedException;
import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.arch.ext.User;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.TokenUtils;
import interamerican.archimpl.providers.CicsProvider;
import interamerican.archimpl.providers.CurrentUserProvider;
import interamerican.archimpl.providers.DatabaseProvider;
import interamerican.archimpl.providers.PropertiesProvider;
import interamerican.archimpl.providers.StreamsProvider;
import interamerican.archimpl.providers.streams.StreamInfoMap;
import interamerican.archimpl.tl.CurrentUser;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

import jwf.HConnect;

/**
 * This is a Bo-1.5 provider. In order to be usable resources must be set e.g.
 * for provision of database services a <code>java.sql.Connection</code> object
 * and a dbSchema
 * 
 * TODO: create methods to set resources for other types of services.
 */
class LegacyBoProviderImpl 
implements DatabaseProvider, CicsProvider, StreamsProvider, 
CurrentUserProvider, PropertiesProvider {
	/**
	 * Transaction ID property key.
	 */
	private static final String TRANS_ID = "TRANS_ID"; //$NON-NLS-1$
	/**
	 * CICS ID property key.
	 */
	private static final String CICS_ID = "CICS_ID"; //$NON-NLS-1$
	/**
	 * CICS user ID.
	 */
	private static final String CICS_USER = "CICS_USER"; //$NON-NLS-1$
	/**
	 * CICS password.
	 */
	private static final String CICS_PASS = "CICS_PASS"; //$NON-NLS-1$
	/**
	 * CICS transaction business exception return codes property key.
	 */
	private static final String CICS_BE_RC = "CICS_BE_RC"; //$NON-NLS-1$

	/**
	 * JDBC connection.
	 */
	private Connection connection;
	/**
	 * Database schema.
	 */
	private String dbSchema;
	
	/**
	 * Properties.
	 */
	private Properties properties;
	
	/**
	 * Creates a new LegacyBoProviderImpl object.
	 * 
	 */
	public LegacyBoProviderImpl() {
		super();
	}

	/**
	 * Gets the properties.
	 *
	 * @return Returns the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * Assigns a new value to the properties.
	 *
	 * @param properties the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * @param conn
	 *            sets the connection
	 */
	public void setConnection(Connection conn) {
		this.connection = conn;
	}

	/**
	 * @param schema
	 *            sets the db schema
	 */
	public void setDbSchema(String schema) {
		this.dbSchema = schema;
	}

	public String getDbSchema() {
		return dbSchema;
	}

	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * Creates a "not supported" exception.
	 *  
	 * @param clazz
	 * @return Returns the exception.
	 */
	DataOperationNotSupportedException notSupported(Class<?> clazz) {
		String message = "No provider implementation for type " + clazz.getName(); //$NON-NLS-1$
		return new DataOperationNotSupportedException(message);		
	}

	public StreamInfoMap getStreams() {
		throw notSupported(StreamsProvider.class);
	}

	public HConnect getHConnect() {
		HConnect hConnect = new HConnect();
		String cicsId = getPropertyValue(CICS_ID);
		if (cicsId!=null) {
			hConnect.setCicsid(cicsId);			
		}
		String cicsUser = getPropertyValue(CICS_USER);
		if (cicsUser!=null) {
			hConnect.setUserid(cicsUser);
		}
		String cicsPass = getPropertyValue(CICS_PASS);
		if (cicsPass!=null) {
			hConnect.setPassword(cicsPass);
		}
		
		if (cicsUser!=null && cicsPass!=null) {
			try {
				hConnect.authenticate(cicsId, cicsPass);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}		
		return hConnect;
	}

	@SuppressWarnings("rawtypes")
	public Collection getBusinessExceptionReturnCodes() {
		String berc = getPropertyValue(CICS_BE_RC);
		if (berc!=null) {
			String[] codes = TokenUtils.splitTrim(berc, COMMA);
			return Arrays.asList(codes);
		}
		return null;
	}

	public String getTransId() {		
		return getPropertyValue(TRANS_ID);
	}
	
	/**
	 * Sets the current user to the thread local that is used
	 * by legacy APIs.
	 */
	public void setCurrentUser() {
		Session<?, ?> session = Bo2Session.<Object, Object>getSession();
		if(session != null) {
			User<?> user = session.getUser();
			if(user!=null) {
				CurrentUser.set(user.getUserId());
			}
		}
	}

	/**
	 * Gets the current user.
	 */
	public String getCurrentUser() {
		Session<?, ?> session = Bo2Session.<Object, Object>getSession();
		if(session != null) {
			User<?> user = Bo2Session.getUser();
			if(user!=null) {
				return user.getUserId();
			}
		}
		return StringConstants.EMPTY;
	}

	public String getPropertyValue(String key) {
		if (properties==null) {
			return null;
		}
		return properties.getProperty(key);
	}

}
