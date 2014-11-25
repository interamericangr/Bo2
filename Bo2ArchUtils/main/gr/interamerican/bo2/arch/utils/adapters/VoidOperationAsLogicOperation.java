package gr.interamerican.bo2.arch.utils.adapters;

import gr.interamerican.bo2.arch.DataOperation;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.utils.Bo2ExceptionUtils;
import gr.interamerican.bo2.utils.adapters.VoidOperation;
import gr.interamerican.bo2.utils.beans.InputBean;

/**
 * @param <T> 
 *
 */
public class VoidOperationAsLogicOperation<T>
extends InputBean<T>
implements DataOperation {
	
	/**
	 * Void operation.
	 */
	VoidOperation<T> operation;

	@Override
	public void execute() throws DataException {
		T t = getInput();
		try {
			operation.execute(t);
		} catch (Exception e) {
			Bo2ExceptionUtils.throwDataException(e);
		}
	}
	
	
	
	

}
