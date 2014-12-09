package gr.interamerican.bo2.impl.open.jee.jms;

import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

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
	 * Creates the JMS message based on the {@link #createMessage()} result.
	 * 
	 * @return JMS message
	 * 
	 * @throws JMSException 
	 * @throws DataException 
	 */
	Message createJmsMessage() throws JMSException, DataException {
		Object message = createMessage();
		
		if(message==null) {
			throw new DataException("null message"); //$NON-NLS-1$
		}
		
		if(message instanceof String) {
			TextMessage jmsMessage = session.createTextMessage((String) message);
			return jmsMessage;
		}
		else if (message instanceof byte[]) {
			BytesMessage jmsMessage = session.createBytesMessage();
			jmsMessage.writeBytes((byte[]) message);
			return jmsMessage;
		}
		else if (message instanceof Serializable) {
			ObjectMessage jmsMessage = session.createObjectMessage((Serializable) message);
			return jmsMessage;
		}
		
		throw new DataException("Unsupported message of type " + message.getClass().getName()); //$NON-NLS-1$
	}
	
	/**
	 * Implementors override this in order to create the message.
	 * Only String, byte[] and Serializable java Objects are supported.
	 * 
	 * @return Message to submit to the destination.
	 * 
	 * @throws DataException 
	 */
	public abstract Object createMessage() throws DataException;
	
}
