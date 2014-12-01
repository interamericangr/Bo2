package gr.interamerican.bo2.impl.open.jee.jms;

import java.io.Serializable;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.workers.AbstractResourceConsumer;

/**
 * A {@link Worker} that sends a {@link Message} to a {@link Destination}
 */
public abstract class JmsProducer extends AbstractResourceConsumer {
	
	/**
	 * the priority level of the message
	 */
	private static final int PRIORITY_LEVEL = 4;

	/**
	 * message expiration time in milliseconds
	 */
    private static final long TIME_TO_LIVE = 300000; 
	
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
			producer.setPriority(PRIORITY_LEVEL);
			producer.setTimeToLive(TIME_TO_LIVE);
		} catch (JMSException e) {
			throw new InitializationException(e);
		}
	}
	
	/**
	 * Sends the message to the {@link Destination}
	 * 
	 * @throws DataException 
	 * 
	 * @see #createMessage()
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
	 * Creates a JMS message. Only String and Serializable java Objects
	 * are supported.
	 * 
	 * @return JMS message
	 * 
	 * @throws JMSException 
	 */
	Message createJmsMessage() throws JMSException {
		Serializable message = createMessage();
		Message jmsMessage = null;
		
		if(message instanceof String) {
			jmsMessage = session.createTextMessage((String) message);
		} else {
			jmsMessage = session.createObjectMessage(message);
		}
		
		return jmsMessage;
	}
	
	/**
	 * Implementors override this in order to create the message.
	 * 
	 * @return Message to submit to the destination.
	 */
	public abstract Serializable createMessage();
	
}
