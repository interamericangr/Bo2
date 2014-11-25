package gr.interamerican.bo2.arch.utils.adapters;

import gr.interamerican.bo2.arch.LogicOperation;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.utils.Bo2ExceptionUtils;
import gr.interamerican.bo2.utils.adapters.VoidOperation;
import gr.interamerican.bo2.utils.beans.InputBean;

/**
 * @param <T> 
 *
 */
public class VoidOperationAsDataOperation<T>
extends InputBean<T>
implements LogicOperation {
	
	/**
	 * Void operation.
	 */
	VoidOperation<T> operation;

	@Override
	public void execute() throws DataException, LogicException {
		T t = getInput();
		try {
			operation.execute(t);
		} catch (Exception e) {
			Bo2ExceptionUtils.throwDataOrLogicException(e);
		}
	}
	
	
	
	

}
