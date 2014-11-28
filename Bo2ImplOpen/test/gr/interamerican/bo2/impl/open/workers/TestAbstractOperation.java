package gr.interamerican.bo2.impl.open.workers;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link AbstractOperation}.
 */
public class TestAbstractOperation {
	
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		AbstractOperation op = new ConcreteOperation();
		Assert.assertNotNull(op.invoker);
	}
	
	/**
	 * Implementation of AbstractOperation.
	 */
	class ConcreteOperation extends AbstractOperation {
		@Override
		public void execute() throws LogicException, DataException {
			/*empty*/
		}		
	}

}
