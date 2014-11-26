package gr.interamerican.bo2.arch.utils.adapters;

import gr.interamerican.bo2.arch.DataOperation;
import gr.interamerican.bo2.arch.LogicOperation;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.utils.adapters.VoidOperation;
import gr.interamerican.bo2.utils.beans.InputBean;
import gr.interamerican.bo2.utils.exc.SimpleExceptionTranslator;

/**
 * Adapts a {@link VoidOperation} to the {@link DataOperation}
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
public class VoidOperationAsDataOperation<T>
extends InputBean<T>
implements LogicOperation {
	
	/**
	 * ExceptionTranslator.
	 */
	SimpleExceptionTranslator<DataException> extra = 
		new SimpleExceptionTranslator<DataException>(DataException.class);
	
	/**
	 * Creates a new VoidOperationAsDataOperation object.
	 * 
	 * @param operation
	 */
	public VoidOperationAsDataOperation(VoidOperation<T> operation) {
		super();
		this.operation = operation;
	}

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
			extra.rethrow(e);
		}
	}

}
