package gr.interamerican.bo2.impl.corp.was85;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.jee.jms.JmsProducer;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.impl.open.utils.Bo2;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * Integration test of {@link JmsProducer} when using a WAS 8.5 thin client.
 */
public class TestThinClientJmsProvider extends AbstractBo2RuntimeCmd {

	/**
	 * Creates a new TestThinClientJmsProvider object. 
	 *
	 */
	public TestThinClientJmsProvider() {
		super(Bo2.getDefaultDeployment().getDeploymentBean().getPathToSecondaryBatchDeployment());
	}
	
	@Override
	public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
		JmsProducer0 producer = open(new JmsProducer0());
		producer.execute();
		System.out.println("done");
	}
	
	/**
	 * JmsProducer implementation.
	 */
	@ManagerName("LOCALDB")
	public static class JmsProducer0 extends JmsProducer {

		/**
		 * Creates a new JmsProducer0 object. 
		 */
		public JmsProducer0() {
			super("jms0"); //$NON-NLS-1$
		}

		@Override
		public Message createJmsMessage() throws DataException, JMSException {
			TextMessage jmsMessage = getSession().createTextMessage("message"); //$NON-NLS-1$
			return jmsMessage;
		}
		
	}

	/**
	 * Main.
	 * @param args
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException
	 */
	public static void main(String[] args) throws DataException, LogicException, UnexpectedException {
		new TestThinClientJmsProvider().execute();
	}

}
