package gr.interamerican.bo2.arch.enums;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataException;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests for {@link CrudOperations}.
 */
public class TestCrudOperations {

	/**
	 * Unit test for perform.
	 * 
	 * @throws DataException
	 */
	@Test
	@SuppressWarnings("unchecked")
	public <P extends PersistentObject<?>> void testPerform() throws DataException {
		P po = (P) Mockito.mock(PersistentObject.class);
		PersistenceWorker<P> pw = Mockito.mock(PersistenceWorker.class);
		CrudOperations.STORE.perform(po, pw);
		CrudOperations.DELETE.perform(po, pw);
		CrudOperations.READ.perform(po, pw);
		CrudOperations.UPDATE.perform(po, pw);
		
		Mockito.verify(pw, Mockito.times(1)).read(po);
		Mockito.verify(pw, Mockito.times(1)).store(po);
		Mockito.verify(pw, Mockito.times(1)).delete(po);
		Mockito.verify(pw, Mockito.times(1)).update(po);
	}

}
