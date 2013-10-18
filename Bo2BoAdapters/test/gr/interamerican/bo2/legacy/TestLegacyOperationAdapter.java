package gr.interamerican.bo2.legacy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.DataOperationNotSupportedException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.exceptions.RuleException;
import gr.interamerican.bo2.samples.SampleLegacyBoOperation;

import org.junit.Test;

/**
 * LegacyOperationAdapterTest
 */
public class TestLegacyOperationAdapter {
	
	
	/**
	 * Bo operations for tests
	 */
	SampleLegacyBoOperation operation = new SampleLegacyBoOperation();
	
	/**
	 * Legacy operation for tests
	 */
	LegacyOperationAdapter operationAdap = new LegacyOperationAdapter(operation);
	
	
	/**
	 * Tests execute.
	 * @throws DataException 
	 * @throws LogicException 
	 * 
	 */
	@Test
	public void testExecute_ok() throws LogicException, DataException{
		operationAdap.execute();
		assertTrue(operation.isSuccess());
	}
	
	/**
	 * Tests that the right exception is caught when a throwable is thrown by the
	 * legacy operation.
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * 
	 */
	@Test
	public void testExecute_throwing() 
	throws InstantiationException, IllegalAccessException {
		
		testThrowing(interamerican.architecture.exceptions.PoNotFoundException.class, PoNotFoundException.class);
		testThrowing(interamerican.architecture.exceptions.DataOperationNotSupportedException.class, DataOperationNotSupportedException.class);
		testThrowing(interamerican.architecture.exceptions.DataAccessException.class, DataAccessException.class);
		testThrowing(interamerican.architecture.exceptions.DataException.class, DataException.class);
		testThrowing(interamerican.architecture.exceptions.LogicException.class, LogicException.class);		
		testThrowing(interamerican.architecture.exceptions.RuleException.class, RuleException.class);
		
		testThrowing(ClassNotFoundException.class, RuntimeException.class);
		testThrowing(RuntimeException.class, RuntimeException.class);
		testThrowing(Exception.class, RuntimeException.class);
		testThrowing(Error.class, Error.class);
		testThrowing(Throwable.class, RuntimeException.class);
	}

	
	/**
	 * Executes an operation that 
	 * @param cause
	 * @param expected
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private void testThrowing
	(Class<? extends Throwable> cause, Class<? extends Throwable> expected) 
	throws InstantiationException, IllegalAccessException {
		Throwable t = cause.newInstance();
		SampleLegacyBoOperation leg = new SampleLegacyBoOperation(t);
		LegacyOperationAdapter op = new LegacyOperationAdapter(leg);
		try {
			op.execute();			
		} catch (Throwable thrown) {
			Class<? extends Throwable> actual = thrown.getClass();
			assertEquals(expected, actual);
		}	
	}

}
