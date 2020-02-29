package gr.interamerican.bo2.arch;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;

/**
 * {@link LogicOperation} defines an interface that models
 * the simple command pattern (command without undo) for
 * business logic layer operations.
 *  
 */
public interface LogicOperation {
	
	/**
	 * Executes the operation. 
	 *
	 * @throws LogicException the logic exception
	 * @throws DataException the data exception
	 */
	public void execute() throws LogicException, DataException;

}
