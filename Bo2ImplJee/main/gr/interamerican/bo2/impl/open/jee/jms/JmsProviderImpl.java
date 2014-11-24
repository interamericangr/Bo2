package gr.interamerican.bo2.impl.open.jee.jms;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Implementation of {@link JmsProvider}
 */
public class JmsProviderImpl implements JmsProvider {
	
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
			InitialContext initialContext = new InitialContext();
			ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup(attributes[0]);
			Destination destination = (Destination) initialContext.lookup(attributes[1]);
			Connection connection = connectionFactory.createConnection();
			return new JmsResource(connection, destination);
		} catch (NamingException e) {
			throw new InitializationException(e);
		} catch (JMSException e) {
			throw new InitializationException(e);
		}
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
