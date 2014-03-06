package gr.interamerican.bo2.samples.workers;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;

/**
 * Empty Worker.
 */
public class EmptyWorker implements Worker {
	
	@Override
	public void open() throws DataException {
		/*empty*/		
	}
	
	@Override
	public void close() throws DataException {
		/*empty*/		
	}
	
	@Override
	public void init(Provider parent) throws InitializationException {
		/*empty*/		
	}
	
	@Override
	public Provider getProvider() {
		return null;
	}

}
