package gr.interamerican.bo2.impl.open.transformations;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.samples.bean.BeanWithString;
import gr.interamerican.bo2.samples.implopen.runtime.concurrent.PrintStringOperation;

import org.junit.Assert;
import org.junit.Test;


/**
 * Test class for {@link Operation2Transformation}.
 */
public class TestOperation2Transformation {
	
	/**
	 * Tests the constructor.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testConstructor() {
		String[] inputs = {"X", "Y"};
		String output = "out";
		Operation op = mock(Operation.class);
		Operation2Transformation<Object, Object> trans = 
				new Operation2Transformation<Object, Object>(op, output, inputs);
		Assert.assertEquals(op,trans.operation);
		Assert.assertEquals(output,trans.outputProperty);
		Assert.assertArrayEquals(inputs, trans.inputProperties);
	}
	
	/**
	 * Tests executeOperation() when the operation throws an exception.
	 * 
	 * @param clazz 
	 *        Class of thrown exception.
	 * 
	 * @throws DataException 
	 * @throws LogicException 
	 */		
	void testExecuteOperation_throwing(Class<? extends Exception> clazz) throws LogicException, DataException {		
		String output = "out"; //$NON-NLS-1$
		Operation op = mock(Operation.class);
		doThrow(clazz).when(op).execute();		
		Operation2Transformation<Object, Object> trans = 
				new Operation2Transformation<Object, Object>(op, output);
		RuntimeException ex = null;
		try {
			trans.executeOperation();
		} catch (RuntimeException re) {
			ex = re;
		}
		Assert.assertNotNull(ex);
		Throwable cause = ex.getCause();
		Assert.assertNotNull(cause);
		Assert.assertTrue(cause.getClass().isAssignableFrom(clazz));		
		verify(op,times(1)).execute();
	}
	
	/**
	 * Tests executeOperation().
	 * 
	 * @throws DataException 
	 * @throws LogicException 
	 */		
	@Test
	public void testExecuteOperation_throwLogic() throws LogicException, DataException {		
		testExecuteOperation_throwing(LogicException.class);
	}
	
	/**
	 * Tests executeOperation().
	 * 
	 * @throws DataException 
	 * @throws LogicException 
	 */		
	@Test
	public void testExecuteOperation_throwData() throws LogicException, DataException {		
		testExecuteOperation_throwing(DataException.class);
	}
	
	/**
	 * Tests executeOperation().
	 * 
	 * @throws DataException 
	 * @throws LogicException 
	 */		
	@Test(expected=RuntimeException.class)
	public void testExecuteOperation_throwingRuntime() throws LogicException, DataException {		
		String output = "out"; //$NON-NLS-1$
		Operation op = mock(Operation.class);
		doThrow(RuntimeException.class).when(op).execute();		
		Operation2Transformation<Object, Object> trans = 
				new Operation2Transformation<Object, Object>(op, output);
		trans.executeOperation();
	}
	
	/**
	 * Tests executeOperation().
	 * 
	 * @throws DataException 
	 * @throws LogicException 
	 */		
	void testExecuteOperation_notThrowing() throws LogicException, DataException {		
		String output = "out"; //$NON-NLS-1$
		Operation op = mock(Operation.class);				
		Operation2Transformation<Object, Object> trans = 
				new Operation2Transformation<Object, Object>(op, output);
		trans.executeOperation();
		verify(op,times(1)).execute();
	}
	
	/**
	 * Tests execute(o).
	 * 
	 * @throws DataException 
	 * @throws LogicException 
	 */		
	@Test
	public void testExecute_withMock() throws LogicException, DataException {
		Operation op = mock(Operation.class);				
		Operation2Transformation<Object, Object> trans = 
				new Operation2Transformation<Object, Object>(op, null);
		Object res = trans.execute(new Object());
		verify(op,times(1)).execute();
		Assert.assertNull(res);
	}
	
	/**
	 * Tests execute(o).
	 * 
	 * @throws DataException 
	 * @throws InitializationException 
	 */		
	@SuppressWarnings("nls")
	@Test
	public void testExecute2() throws DataException, InitializationException {
		PrintStringOperation op = new PrintStringOperation();
		BeanWithString bean = new BeanWithString();
		String string = "X";
		bean.setString(string);
		Operation2Transformation<Object, Object> trans = 
			new Operation2Transformation<Object, Object>(op, "string", "string");
		Provider p = mock(Provider.class);
		op.init(p);
		op.open();
		Object result = trans.execute(bean);
		op.close();
		Assert.assertEquals(string, result);
	}


}
