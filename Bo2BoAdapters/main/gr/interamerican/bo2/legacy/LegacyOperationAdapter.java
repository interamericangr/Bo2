package gr.interamerican.bo2.legacy;

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.DataOperationNotSupportedException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.exceptions.RuleException;


/**
 * Adapter to a legacy operation.
 * 
 * Usually this class must be overriden in order to add methods that
 * set inputs and get outputs to and from the legacy operation.
 */
public class LegacyOperationAdapter 
extends LegacyWorkerAdapter 
implements Operation {
	
	/**
	 * Legacy operation.
	 */
	private interamerican.architecture.Operation operation;
	
	/**
	 * Creates a new LegacyOperationAdapter object. 
	 *
	 * @param operation Legacy operation adapted by this object.
	 */
	public LegacyOperationAdapter(interamerican.architecture.Operation operation) {
		super(operation);
		this.operation = operation;
	}
	
	public void execute() throws LogicException, DataException {		
		try {
			operation.execute();
		} catch (interamerican.architecture.exceptions.PoNotFoundException pnfe) {
			throw new PoNotFoundException(pnfe);	
		} catch (interamerican.architecture.exceptions.DataOperationNotSupportedException donse) {
			String message = donse.getMessage();
			throw new DataOperationNotSupportedException(message);			
		} catch (interamerican.architecture.exceptions.DataAccessException donse) {
			throw new DataAccessException(donse);					
		} catch (interamerican.architecture.exceptions.DataException de) {
			throw new DataException(de);
		} catch (interamerican.architecture.exceptions.RuleException re) {
			throw new RuleException(re.getMessage());
		} catch (interamerican.architecture.exceptions.LogicException le) {
			String msg = le.getMessage();
			if (msg!=null && le.getCause()==null) {
				throw new LogicException(msg);
			} else {
				throw new LogicException(le);
			}
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} catch (Error err) {
			throw err;			
		} catch (Throwable thr) {
			throw new RuntimeException(thr); //Never happens.
		}
	}
	
	
	
	
	
	

}
