package gr.interamerican.bo2.legacy;

import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;

/**
 * {@link PersistenceWorker} based on a legacy persistence worker.
 * 
 * This implementation assumes that the implementation of the 
 * {@link PersistentObject} is a {@link LegacyPoAdapter} to the
 * actual legacy Bo persistent object. <br/>
 * The <code>getDetachStrategy()</code> method of this {@link PersistenceWorker}
 * will always return null, since there was nu such feature as a 
 * {@link DetachStrategy} in legacy Bo persistent objects. 
 * 
 * @param <P> Type of persistent object.
 * 
 */
public class LegacyPwAdapter <P extends PersistentObject<?>> 
extends LegacyWorkerAdapter
implements PersistenceWorker<P> {

	/**
	 * Legacy persistence worker.
	 */
	@SuppressWarnings("rawtypes")
	private interamerican.architecture.PersistenceWorker pw;
	
	/**
	 * Creates a new LegacyPwAdapter object. 
	 *
	 * @param pw
	 */
	@SuppressWarnings("rawtypes")
	public LegacyPwAdapter(interamerican.architecture.PersistenceWorker pw) {
		super(pw);
		this.pw = pw;
	}	
	
	@SuppressWarnings("unchecked")
	public P read(P o) 
	throws DataException ,PoNotFoundException {		
		try {
			@SuppressWarnings("rawtypes")
			LegacyPoAdapter adapter = (LegacyPoAdapter) o;
			pw.read(adapter.getLegacyPo());
			return o;
		} catch (interamerican.architecture.exceptions.PoNotFoundException pe) {
			throw new PoNotFoundException(pe);
		} catch (interamerican.architecture.exceptions.DataException de) {
			throw new DataException(de);
		}
	}
	
	@SuppressWarnings("unchecked")
	public P store(P o) 
	throws DataException ,PoNotFoundException {		
		try {
			@SuppressWarnings("rawtypes")
			LegacyPoAdapter adapter = (LegacyPoAdapter) o;
			pw.store(adapter.getLegacyPo());
			return o;
		} catch (interamerican.architecture.exceptions.PoNotFoundException pe) {
			throw new PoNotFoundException(pe);
		} catch (interamerican.architecture.exceptions.DataException de) {
			throw new DataException(de);
		}
	}
		
	@SuppressWarnings("unchecked")
	public P delete(P o) 
	throws DataException ,PoNotFoundException {		
		try {
			@SuppressWarnings("rawtypes")
			LegacyPoAdapter adapter = (LegacyPoAdapter) o;
			pw.delete(adapter.getLegacyPo());
			return o;
		} catch (interamerican.architecture.exceptions.PoNotFoundException pe) {
			throw new PoNotFoundException(pe);
		} catch (interamerican.architecture.exceptions.DataException de) {
			throw new DataException(de);
		}
	}	
	
	@SuppressWarnings("unchecked")
	public P update(P o) 
	throws DataException ,PoNotFoundException {		
		try {
			@SuppressWarnings("rawtypes")
			LegacyPoAdapter adapter = (LegacyPoAdapter) o;
			pw.update(adapter.getLegacyPo());
			return o;
		} catch (interamerican.architecture.exceptions.PoNotFoundException pe) {
			throw new PoNotFoundException(pe);
		} catch (interamerican.architecture.exceptions.DataException de) {
			throw new DataException(de);
		}
	}	
	
	public boolean ignoresSomething() {		
		return pw.ignoresSomething();
	}
	
	public DetachStrategy getDetachStrategy() {		
		return null;
	}

}
