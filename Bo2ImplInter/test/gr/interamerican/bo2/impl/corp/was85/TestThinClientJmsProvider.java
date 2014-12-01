package gr.interamerican.bo2.impl.corp.was85;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.jee.jms.JmsProducer;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;

import java.io.Serializable;

/**
 * Integration test of {@link JmsProducer} when using a WAS 8.5 thin client.
 */
public class TestThinClientJmsProvider extends AbstractBo2RuntimeCmd {

	@Override
	public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
		JmsProducer0 producer = open(new JmsProducer0());
		producer.execute();
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
		public Serializable createMessage() {
			return "message"; //$NON-NLS-1$
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