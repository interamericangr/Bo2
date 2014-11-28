package gr.interamerican.bo2.arch.utils.adapters;

import gr.interamerican.bo2.arch.LogicOperation;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.utils.adapters.VoidOperation;
import gr.interamerican.bo2.utils.beans.InputBean;

/**
 * Adapts a {@link VoidOperation} to the {@link LogicOperation}
 * interface.
 * 
 * The argument of the {@link VoidOperation#execute(Object)} method
 * is specified by the {@link InputBean#setInput(Object)} method
 * of this class.
 * 
 * @param <T> 
 *        Type of input.
 *
 */
public class VoidOperationAsLogicOperation<T>
extends InputBean<T>
implements LogicOperation {
	
	/**
	 * ExceptionTranslator.
	 */
	DataLogicExTranslator extra = new DataLogicExTranslator();
	
	/**
	 * Void operation.
	 */
	VoidOperation<T> operation;
	
	/**
	 * Creates a new VoidOperationAsLogicOperation object.
	 * 
	 * @param operation
	 */
	public VoidOperationAsLogicOperation(VoidOperation<T> operation) {
		super();
		this.operation = operation;
	}

	@Override
	public void execute() throws DataException, LogicException {
		T t = getInput();
		try {
			operation.execute(t);
		} catch (Exception e) {
			extra.rethrow(e);
		}
	}
	
	
	
	

}
