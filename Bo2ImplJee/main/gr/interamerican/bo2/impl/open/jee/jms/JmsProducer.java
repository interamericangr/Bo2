package gr.interamerican.bo2.impl.open.jee.jms;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.workers.AbstractResourceConsumer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

/**
 * A {@link Worker} that sends a {@link Message} to a {@link Destination}
 */
public abstract class JmsProducer extends AbstractResourceConsumer {
	
	/**
	 * JMS resource name.
	 */
	String jmsResourceName;
	
	/**
	 * JMS session.
	 */
	Session session;
	
	/**
	 * Actual JMS producer.
	 */
	MessageProducer producer;
	
	/**
	 * Creates a new JmsProducer object.
	 * 
	 * @param jmsResourceName
	 *        The logical name for the jms resource 
	 */
	public JmsProducer(String jmsResourceName) {
		this.jmsResourceName = jmsResourceName;
	}
	
	@Override
	public void init(Provider parent) throws InitializationException {
		super.init(parent);
		JmsProvider jmsProvider = getResource(JmsProvider.class);
		JmsResource jmsResource = jmsProvider.getResource(jmsResourceName);
		try {
			session = jmsResource.getSession();
			producer = session.createProducer(jmsResource.getDestination());
		} catch (JMSException e) {
			throw new InitializationException(e);
		}
	}
	
	/**
	 * Sends the message to the {@link Destination}
	 * 
	 * @throws DataException 
	 */
	public final void execute() throws DataException {
		try {
			Message jmsMessage = createJmsMessage();
			producer.send(jmsMessage);
		} catch (JMSException e) {
			throw new DataException(e);
		}
	}
	
	/**
	 * Creates the JMS message using the current jms {@link Session}.
	 * Implementors override this.
	 * 
	 * @return JMS message
	 * 
	 * @throws JMSException 
	 * @throws DataException 
	 */
	public abstract Message createJmsMessage() throws JMSException, DataException;
	
	/**
	 * Gets the {@link Session}. Use this to create the message.
	 * 
	 * @return the Session.
	 */
	public Session getSession() {
		return session;
	}
	
}
