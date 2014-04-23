package gr.interamerican.bo2.arch.utils;

import static gr.interamerican.bo2.arch.utils.Bo2ExceptionUtils.throwDataLogicOrUnexpectedException;
import static gr.interamerican.bo2.arch.utils.Bo2ExceptionUtils.throwDataOrLogicException;
import static gr.interamerican.bo2.arch.utils.Bo2ExceptionUtils.throwIfIsDataException;
import static gr.interamerican.bo2.arch.utils.Bo2ExceptionUtils.throwIfIsError;
import static gr.interamerican.bo2.arch.utils.Bo2ExceptionUtils.throwIfIsInitializationException;
import static gr.interamerican.bo2.arch.utils.Bo2ExceptionUtils.throwIfIsLogicException;
import static gr.interamerican.bo2.arch.utils.Bo2ExceptionUtils.throwInitDataLogicBo2dliException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;

import org.junit.Test;

/**
 * Tests for {@link Bo2ExceptionUtils}.
 */
public class TestBo2ExceptionUtils {
	
	/**
	 * Tests throwIfIsInitializationException().
	 * 
	 * @throws InitializationException
	 */
	@Test(expected=InitializationException.class)
	public void testThrowIfIsInitializationException_throwing() throws InitializationException {
		throwIfIsInitializationException(new InitializationException());
	}
	
	/**
	 * Tests throwIfIsInitializationException().
	 * 
	 * @throws InitializationException
	 */
	@Test()
	public void testThrowIfIsInitializationException_doingNothing() throws InitializationException {
		throwIfIsInitializationException(new Exception());
		/* no assertion. Nothing should happen. */
	}
	
	
	/**
	 * Tests throwIfIsInitializationException().
	 * 
	 * @throws LogicException
	 */
	@Test(expected=LogicException.class)
	public void testThrowIfIsLogicException_throwing() throws LogicException {
		throwIfIsLogicException(new LogicException());
	}
	
	/**
	 * Tests throwIfIsInitializationException().
	 * 
	 * @throws LogicException
	 */
	@Test()
	public void testThrowIfIsLogicException_doingNothing() throws LogicException {
		throwIfIsLogicException(new Exception());
		/* no assertion. Nothig should happen. */
	}
	
	/**
	 * Tests throwIfIsInitializationException().
	 * 
	 * @throws DataException
	 */
	@Test(expected=DataException.class)
	public void testThrowIfIsDataException_throwing() throws DataException {
		throwIfIsDataException(new DataException());
	}
	
	/**
	 * Tests throwIfIsDataException().
	 * @throws DataException 
	 */
	@Test()
	public void testThrowIfIsDataException_doingNothing() throws DataException {		
		throwIfIsDataException(new Exception());
		/* no assertion. Nothig should happen. */
	}
	
	
	
	
	/**
	 * Tests throwIfIsError().
	 */
	@Test(expected=Error.class)
	public void testThrowIfIsError_throwing() {		
		throwIfIsError(new Error());
	}
	
	/**
	 * Tests throwIfIsError().
	 * 
	 */
	@Test()
	public void testThrowIfIsError_doingNothing()  {
		Exception t = new Exception();
		throwIfIsError(t);
		/* no assertion. Nothig should happen. */
	}
	
	
	
	
	
	/**
	 * Tests throwDataLogicOrUnexpectedException().
	 * 
	 * @throws UnexpectedException 
	 * @throws LogicException 
	 * @throws DataException 
	 */
	@Test(expected=Error.class)
	public void testThrowDataLogicOrUnexpectedException_withError() 
	throws DataException, LogicException, UnexpectedException {		
		throwDataLogicOrUnexpectedException(new Error());
	}
	
	/**
	 * Tests throwDataLogicOrUnexpectedException().
	 * 
	 * @throws UnexpectedException 
	 * @throws LogicException 
	 * @throws DataException 
	 */
	@Test(expected=UnexpectedException.class)
	public void testThrowDataLogicOrUnexpectedException_withInitialization() 
	throws DataException, LogicException, UnexpectedException {		
		throwDataLogicOrUnexpectedException(new InitializationException());
	}
	
	/**
	 * Tests throwDataLogicOrUnexpectedException().
	 * 
	 * @throws UnexpectedException 
	 * @throws LogicException 
	 * @throws DataException 
	 */
	@Test(expected=UnexpectedException.class)
	public void testThrowDataLogicOrUnexpectedException_withRuntime() 
	throws DataException, LogicException, UnexpectedException {		
		throwDataLogicOrUnexpectedException(new RuntimeException());
	}
	
	/**
	 * Tests throwDataLogicOrUnexpectedException().
	 * 
	 * @throws UnexpectedException 
	 * @throws LogicException 
	 * @throws DataException 
	 */
	@Test(expected=LogicException.class)
	public void testThrowDataLogicOrUnexpectedException_withLogic() 
	throws DataException, LogicException, UnexpectedException {		
		throwDataLogicOrUnexpectedException(new LogicException());
	}
	
	/**
	 * Tests throwDataLogicOrUnexpectedException().
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 */
	@Test(expected=LogicException.class)
	public void testThrowDataOrLogic_withLogic() 
	throws DataException, LogicException {		
		throwDataOrLogicException(new LogicException());
	}
	
	/**
	 * Tests throwDataLogicOrUnexpectedException().
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 */
	@Test(expected=DataException.class)
	public void testThrowDataOrLogic_withData() 
	throws DataException, LogicException {		
		throwDataOrLogicException(new DataException());
	}
	
	/**
	 * Tests throwDataLogicOrUnexpectedException().
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 */
	@Test(expected=RuntimeException.class)
	public void testThrowDataOrLogic_withException() 
	throws DataException, LogicException {		
		throwDataOrLogicException(new Exception());
	}
	
	/**
	 * Tests throwDataLogicOrUnexpectedException().
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=RuntimeException.class)
	public void testThrowInitDataOrLogic_withException() 
	throws DataException, LogicException, InitializationException {		
		throwInitDataLogicBo2dliException(new Exception());
	}
	
	/**
	 * Tests throwDataLogicOrUnexpectedException().
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=InitializationException.class)
	public void testThrowInitDataOrLogic_withInitalization() 
	throws DataException, LogicException, InitializationException {		
		throwInitDataLogicBo2dliException(new InitializationException());
	}
	
	

}
