package gr.interamerican.bo2.impl.open.workers;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.utils.annotations.Child;

/**
 * OperationEntitiesQuery is a query that executes an operation
 * in order to fetch each row.
 * 
 * <p>
 * The current row entity is extracted from properties of the operation
 * after each execution. The query stops when the entity extracted from
 * the operation is <code>null</code>. </p>
 * <p>
 *  
 *
 * @param <O> 
 *        Type of operation.
 * @param <T>
 *        Type of entity.
 */
public abstract class OperationEntitiesQuery<O extends Operation, T>
extends AbstractBaseWorker
implements EntitiesQuery<T> {
	
	/**
	 * Operation.
	 */
	@Child O operation;	
	
	/**
	 * Current row.
	 */
	int row=0;	
	
	/**
	 * Current entity.
	 */
	T entity=null;
	
	/**
	 * Extracts the current entity from properties of the operation.
	 * 
	 * @param op
	 * 
	 * @return Returns the entity.
	 */
	protected abstract T extractEntity(O op);  
	
	
	/**
	 * Creates a new OperationWrapperQuery object.
	 * @param operation
	 * @param condition
	 */
	public OperationEntitiesQuery(O operation) {
		super();
		this.operation = operation;
	}
	
	/**
	 * Gets the current operation.
	 * 
	 * @return Returns the operation.
	 */
	protected O getOperation() {		
		return operation;
	}

	@Override
	public void execute() throws DataException {
		row=0;		
		entity=null;
		executeOperation();
	}

	@Override
	public boolean next() throws DataAccessException {
		entity = extractEntity(operation);		
		boolean isNext = (entity!=null);
		if (isNext) {
			row++;			
			executeOperation();			
		}					
		return isNext;
	}
	
	/**
	 * Executes the operation.
	 * 
	 * @throws DataAccessException
	 */
	void executeOperation() throws DataAccessException {
		try {
			operation.execute();
		} catch (LogicException e) {			
			throw new DataAccessException(e);
		} catch (DataException e) {			
			throw new DataAccessException(e);
		}	
	}

	@Override
	public int getRow() throws DataAccessException {
		return row;
	}

	@Override
	public void setAvoidLock(boolean avoidLock) {
		/* do nothing */		
	}

	@Override
	public boolean isAvoidLock() {		
		return true;
	}

	@Override
	public T getEntity() throws DataAccessException {
		return entity;
	}
	

}
