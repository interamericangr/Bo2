package gr.interamerican.bo2.impl.open.jee.jms;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Implementation of {@link JmsProvider}
 */
public class JmsProviderImpl implements JmsProvider {
	
	/**
	 * Configuration property key for initial context factory implementation class.
	 */
	static final String INITIAL_CONTEXT_FACTORY_KEY = "initialCtxFactory"; //$NON-NLS-1$
	
	/**
	 * Configuration property key for context lookup url.
	 */
	static final String LOOKUP_URL_KEY = "lookupUrl"; //$NON-NLS-1$
	
	/**
	 * Properties of the manager this resource wrapper is created from.
	 */
	Properties properties;
	
	/**
	 * Cached resources
	 */
	Map<String, JmsResource> resources;
	
	/**
	 * Creates a new JmsProviderImpl object.
	 *  
	 * @param properties 
	 *        Properties of the manager this resource wrapper is created from.
	 */
	public JmsProviderImpl(Properties properties) {
		this.properties = properties;
		resources = new HashMap<String, JmsResource>();
	}

	public JmsResource getResource(String name) throws InitializationException {
		JmsResource resource = resources.get(name);
		if(resource==null) {
			resource = createResource(name);
			resources.put(name, resource);
		}
		return resource;
	}
	
	public void close() throws DataException {
		for(Map.Entry<String, JmsResource> entry : resources.entrySet()) {
			Connection connection = entry.getValue().getConnection();
			try {
				connection.close();
			} catch (JMSException e) {
				throw new DataException(e);
			}
		}
	}

	/**
	 * Creates a {@link JmsResource} for the given name.
	 * @param name
	 * @return Created {@link JmsResource}
	 * @throws InitializationException 
	 */
	JmsResource createResource(String name) throws InitializationException {
		String[] attributes = getAttributes(name);
		try {
			Context initialContext = getContext();
			ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup(attributes[0]);
			Destination destination = (Destination) initialContext.lookup(attributes[1]);
			Connection connection = connectionFactory.createConnection();
			
			/*
			 * If the runtime is the container, the session is transacted. If it is a thin client
			 * the session is not transacted. The thin client will certainly have to set an initial
			 * context factory to the Context. When running inside a container, the JNDI resources
			 * should always be accessible locally, even when they are, in fact, remote.
			 */
			boolean containerRuntime = properties.getProperty(INITIAL_CONTEXT_FACTORY_KEY)==null;
			Session session = null;
			if(containerRuntime) {
				session = connection.createSession(true, Session.SESSION_TRANSACTED);
			} else {
				session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			}

			return new JmsResource(connection, destination, session);
		} catch (NamingException e) {
			throw new InitializationException(e);
		} catch (JMSException e) {
			throw new InitializationException(e);
		}
	}
	
	/**
	 * Creates the Context for the given configuration
	 * 
	 * @return the Context for the given configuration
	 * 
	 * @throws NamingException
	 */
	Context getContext() throws NamingException {
		String initialCtxFactory = CollectionUtils.getOptionalProperty(properties, INITIAL_CONTEXT_FACTORY_KEY);
		if(StringUtils.isNullOrBlank(initialCtxFactory)) {
			return new InitialContext();
		}
		
		String lookupUrl = CollectionUtils.getMandatoryProperty(properties, LOOKUP_URL_KEY);
		
		Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, initialCtxFactory);
        env.put(Context.PROVIDER_URL, lookupUrl);
        
        return new InitialContext(env);
	}
	
	/**
	 * Validates and gets the attributes for the specified name.
	 * 
	 * @param name
	 * 
	 * @return the attributes for the specified name.
	 * 
	 * @throws InitializationException
	 */
	String[] getAttributes(String name) throws InitializationException {
		String descriptor = properties.getProperty(name);
		if (descriptor==null) {
			String problem = "No description"; //$NON-NLS-1$
			throw invalid(problem, name);
		}
		String[] attributes = TokenUtils.splitTrim(descriptor, StringConstants.COMMA);
		if (attributes.length!=2) {
			String problem = "Invalid description. Valid descriptions are CONNECTION_FACTORY,DESTINATION jndi names"; //$NON-NLS-1$
			throw invalid(problem, name);
		}
		
		return attributes;
	}
	
	/**
	 * Creates an initialization exception.
	 * 
	 * @param name
	 * @param problem
	 * 
	 * @return Returns the exception.
	 */
	static InitializationException 
	invalid(String problem, String name) {
		@SuppressWarnings("nls")
		String msg = StringUtils.concat(
			problem, " for the named jms resource ", name);
		return new InitializationException(msg);
	}
	
}
