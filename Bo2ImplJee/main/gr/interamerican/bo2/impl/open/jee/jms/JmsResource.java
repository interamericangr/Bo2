package gr.interamerican.bo2.impl.open.jee.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Session;

/**
 * Describes a JMS resource in terms of a {@link Connection},
 * a {@link Session} and a {@link Destination}.
 * <br>
 * This class is not part of the public API. It is meant to facilitate
 * integration with JMS 1.1.
 */
public class JmsResource {
	
	/** connection. */
	Connection connection;
	
	/** destination. */
	Destination destination;
	
	/** session. */
	Session session;
	
	/**
	 * Creates a new JmsResource object. 
	 *
	 * @param connection the connection
	 * @param destination the destination
	 * @param session the session
	 */
	public JmsResource(Connection connection, Destination destination, Session session) {
		super();
		this.connection = connection;
		this.destination = destination;
		this.session = session;
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

	/**
	 * Gets the session.
	 *
	 * @return Returns the session
	 */
	public Session getSession() {
		return session;
	}
	
}
