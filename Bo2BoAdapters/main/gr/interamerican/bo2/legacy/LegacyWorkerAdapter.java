package gr.interamerican.bo2.legacy;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.workers.AbstractResourceConsumer;

/**
 * Wrapper around a legacy (Bo1) worker.
 */
public abstract class LegacyWorkerAdapter
extends AbstractResourceConsumer
implements Worker {
	
	/**
	 * Legacy object.
	 */
	protected interamerican.architecture.Worker legacy;
	
	/**
	 * Creates a new LegacyWorkerWrapper object. 
	 *
	 * @param legacy
	 */
	public LegacyWorkerAdapter(interamerican.architecture.Worker legacy) {
		super();
		this.legacy = legacy;
	}
	
	@Override
	public void init(Provider parent) throws InitializationException {
		super.init(parent);		
		if (managerName==null) {
			String msg = managerNameNotSet();
			throw new InitializationException(msg);
		}
		interamerican.architecture.Provider legacyProvider = 
			LegacyBoUtils.getLegacyProvider(managerName,parent);
		legacy.init(legacyProvider);	
	}
	
	@Override
	public void open() throws DataException {
		super.open();
		try {
			legacy.open();
		} catch (interamerican.architecture.exceptions.DataException e) {
			throw new DataException(e);
		}
	}	
	
	@Override
	public void close() throws DataException {
		try {
			legacy.close();
		} catch (interamerican.architecture.exceptions.DataException e) {
			throw new DataException(e);
		}
		super.close();
	}
	
	


}
