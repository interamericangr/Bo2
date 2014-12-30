package gr.interamerican.bo2.utils.runnables;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import gr.interamerican.bo2.utils.adapters.VoidOperation;

import org.junit.Test;

/**
 * Tests for {@link ConcreteMonitoringOperation}.
 */
public class TestConcreteMonitoringOperation {
	
	
	
	/**
	 * Test for the constructor.
	 */
	@Test
	public void testConstructor() {
		@SuppressWarnings("unchecked") VoidOperation<Object> vo = mock(VoidOperation.class);		
		ConcreteMonitoringOperation<Object> cmp = new ConcreteMonitoringOperation<Object>(vo);
		assertEquals(vo, cmp.vo);
	}
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute() {
		@SuppressWarnings("unchecked") VoidOperation<Object> vo = mock(VoidOperation.class);		
		ConcreteMonitoringOperation<Object> cmp = new ConcreteMonitoringOperation<Object>(vo);
		Object o = new Object();
		cmp.execute(o);
		verify(vo, times(1)).execute(o);
	}
	
	

}
