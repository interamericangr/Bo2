package gr.interamerican.bo2.arch.utils.adapters;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.utils.exc.MultipleExceptionTranslator;

/**
 * Transalates exceptions to the standard exceptions signature
 * of Bo2 operations.
 *  
 */
public class DataLogicExTranslator 
extends MultipleExceptionTranslator {

	/**
	 * Creates a new DataLogicExTranslator.
	 */
	@SuppressWarnings("unchecked")
	public DataLogicExTranslator() {
		super(DataException.class, LogicException.class);		
	}
	
	@Override
	public void rethrow(Throwable t) throws DataException, LogicException {
		rethrow(DataException.class, t);
		rethrow(LogicException.class, t);
		rethrowRest(t);
	}

}
