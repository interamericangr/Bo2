package gr.interamerican.bo2.samples;

import interamerican.archimpl.workers.DataPersistenceWorker;
import interamerican.architecture.PersistentObject;
import interamerican.architecture.Provider;
import interamerican.architecture.exceptions.DataException;
import interamerican.architecture.exceptions.PoNotFoundException;

/**
 * Mock implementation of LegacyPw.
 */
@SuppressWarnings({ "nls", "rawtypes" })
public class MockLegacyPw extends DataPersistenceWorker {
	
	/**
	 * existing id.
	 */
	public static final Integer ID = 1;
	/**
	 * string1.
	 */
	public static final String STRING1 = "S1";
	/**
	 * string2.
	 */
	public static final String STRING2 = "S2";
	/**
	 * not existing id.
	 */
	public static final Integer NTF_ID = 107;
	
	/**
	 * indicator for succesful store.
	 */
	boolean stored = false;
	/**
	 * indicator for succesful store.
	 */
	boolean deleted = false;
	
	/**
	 * provider.
	 */
	@SuppressWarnings("unused") private Provider prov;
	
	
	
	
	/**
	 * Checks the id of o.
	 * @param o
	 * @throws PoNotFoundException if o.id is not 1.
	 */
	private void check(PersistentObject o) throws PoNotFoundException {
		SampleLegacyPo po = (SampleLegacyPo) o;
		if (po.getId()!=ID) {
			throw new PoNotFoundException();
		}
	}

	/* (non-Javadoc)
	 * @see interamerican.architecture.PersistenceWorker#ignoresSomething()
	 */
	public boolean ignoresSomething() {		
		return false;
	}

	/* (non-Javadoc)
	 * @see interamerican.architecture.PersistenceWorker#read(interamerican.architecture.PersistentObject)
	 */	
	public void read(PersistentObject o) throws DataException,
			PoNotFoundException {
		check(o);		
		SampleLegacyPo po = (SampleLegacyPo) o;
		po.setId(1);
		po.setString1(STRING1);
		po.setString2(STRING2);		
	}

	/* (non-Javadoc)
	 * @see interamerican.architecture.PersistenceWorker#store(interamerican.architecture.PersistentObject)
	 */
	public void store(PersistentObject o) throws DataException,
			PoNotFoundException {
		check(o);
		stored = true;
	}

	/* (non-Javadoc)
	 * @see interamerican.architecture.PersistenceWorker#delete(interamerican.architecture.PersistentObject)
	 */
	public void delete(PersistentObject o) throws DataException,
			PoNotFoundException {
		check(o);		
		deleted = true;
	}
	
	/**
	 * Gets the stored.
	 *
	 * @return Returns the stored
	 */
	public boolean isStored() {
		return stored;
	}

	/**
	 * Gets the deleted.
	 *
	 * @return Returns the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

	
	
	
}
