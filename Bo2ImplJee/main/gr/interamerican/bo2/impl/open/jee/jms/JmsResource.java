package gr.interamerican.bo2.impl.open.jee.jms;

import javax.jms.Connection;
import javax.jms.Destination;

/**
 * Describes a JMS resource in terms of a {@link Connection}
 * and a {@link Destination}.
 * <br/>
 * This class is not part of the public API. It is meant to facilitate
 * integration with JMS 1.1.
 */
public class JmsResource {
	
	/**
	 * connection
	 */
	Connection connection;
	
	/**
	 * destination
	 */
	Destination destination;
	
	/**
	 * Creates a new JmsResource object. 
	 *
	 * @param connection
	 * @param destination
	 */
	public JmsResource(Connection connection, Destination destination) {
		super();
		this.connection = connection;
		this.destination = destination;
	}
	
	/**
	 * Gets the connection.
	 *
	 * @return Returns the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Gets the destination.
	 *
	 * @return Returns the destination
	 */
	public Destination getDestination() {
		return destination;
	}
	
}
