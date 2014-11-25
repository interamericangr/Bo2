package gr.interamerican.bo2.arch;

import gr.interamerican.bo2.arch.exceptions.DataException;

/**
 * {@link DataOperation} defines an interface that models
 * the simple command pattern (command without undo) for
 * data layer operations.
 *  
 */
public interface DataOperation {
	
	/**
	 * Executes the operation.
	 * 
	 * @throws DataException 
	 */
	public void execute() throws DataException;

}
