package gr.interamerican.bo2.samples;

import interamerican.architecture.Provider;
import interamerican.architecture.Worker;
import interamerican.architecture.exceptions.DataException;

/**
 * Mock Legacy Pw that throws an Exception on every operation.
 */
public class MockLegacyWorker implements Worker {	
	public void init(Provider parent) {/*empty*/}	
	public void open() throws DataException {/*empty*/}
	public void close() throws DataException {/*empty*/}	
}
