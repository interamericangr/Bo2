package gr.interamerican.bo2.impl.open.workers;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link AbstractBusinessLogic}.
 */
public class TestAbstractBusinessLogic {
	
	
	/**
	 * Tests the constructor.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testConstructor() {
		AbstractBusinessLogic bl = new ConcreteBl();
		Assert.assertNotNull(bl.invoker);
	}
	
	/**
	 * Implementation of AbstractOperation.
	 */
	class ConcreteBl extends AbstractBusinessLogic {/*empty*/}

}
