package gr.interamerican.bo2.utils.exc;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.utils.adapters.ExceptionTranslator;
import gr.interamerican.bo2.utils.exc.ExceptionUnwrapper;

/**
 * Tests for {@link SimpleExceptionTranslator}.
 */
public class TestExceptionTranslator {
	
	 
	
	/**
	 * Tests rethrow(t).
	 * @throws DataException 
	 * @throws LogicException 
	 */
	@Test(expected=DataException.class)
	public void testRethrow_data() throws LogicException, DataException {
		SimpleExceptionTranslator et = new SimpleExceptionTranslator();
		et.rethrow(new DataException());
	}
	
	/**
	 * Tests rethrow(t).
	 * @throws DataException 
	 * @throws LogicException 
	 */
	@Test(expected=LogicException.class)
	public void testRethrow_logic() throws LogicException, DataException {
		SimpleExceptionTranslator et = new SimpleExceptionTranslator();
		et.rethrow(new LogicException());
	}
	
	/**
	 * Tests rethrow(t).
	 * @throws DataException 
	 * @throws LogicException 
	 */
	@Test(expected=RuntimeException.class)
	public void testRethrow_rte() throws LogicException, DataException {
		SimpleExceptionTranslator et = new SimpleExceptionTranslator();
		et.rethrow(new RuntimeException());
	}
	
	/**
	 * Tests rethrow(t).
	 * 
	 * @throws DataException 
	 * @throws LogicException 
	 */
	@Test(expected=Error.class)
	public void testRethrow_error() throws LogicException, DataException {
		SimpleExceptionTranslator et = new SimpleExceptionTranslator();
		et.rethrow(new Error());
	}
	
	/**
	 * Tests rethrow(t).
	 * 
	 * @throws DataException 
	 * @throws LogicException 
	 */
	@Test(expected=LogicException.class)
	public void testRethrow_withCauser() throws LogicException, DataException {
		SimpleExceptionTranslator et = new SimpleExceptionTranslator();
		et.rethrow(new RuntimeException(new LogicException()));
	}
	
	
	

}
