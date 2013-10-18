package gr.interamerican.bo2.samples;

import interamerican.archimpl.workers.DataPersistenceWorker;
import interamerican.architecture.PersistentObject;
import interamerican.architecture.exceptions.DataException;
import interamerican.architecture.exceptions.PoNotFoundException;

/**
 * Mock Legacy Pw that throws an Exception on every operation.
 */
@SuppressWarnings("rawtypes")
public class MockFailingLegacyPw extends DataPersistenceWorker {
	/**
	 * Creates a new MockFailingLegacyPw object. 
	 *
	 * @param de
	 */
	public MockFailingLegacyPw(DataException de) {
		super();
		this.de = de;
	}
	/**
	 * this will be thrown.
	 */
	private DataException de;

	public boolean ignoresSomething() {		
		return false;
	}
	/**
	 * throws.
	 * @throws DataException
	 */
	private void throwDe() throws DataException {
		if (de!=null) {
			throw de;
		}
	}
	public void read(PersistentObject o) throws DataException, PoNotFoundException {
		throwDe();		
	}
	public void store(PersistentObject o) throws DataException,	PoNotFoundException {
		throwDe();
	}
	public void delete(PersistentObject o) throws DataException, PoNotFoundException {
		throwDe();
	}
	
}
