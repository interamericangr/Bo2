package gr.interamerican.bo2.legacy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.samples.MockFailingLegacyQuery;
import gr.interamerican.bo2.samples.MockLegacyQuery;
import gr.interamerican.bo2.samples.SampleLegacyBoDataQuery;
import gr.interamerican.bo2.utils.DateUtils;
import interamerican.archimpl.records.Record;
import interamerican.archimpl.workers.StreamQuery;
import interamerican.architecture.exceptions.DataAccessException;

import java.util.Calendar;

import org.junit.Test;

/**
 * 
 */
@SuppressWarnings("nls")
public class TestLegacyQueryAdapter {
	
	/**
	 * sample legacy query
	 */
	SampleLegacyBoDataQuery leg;
	/**
	 * sample adapter.
	 */
	LegacyQueryAdapter lqa;
	
	/**
	 * Creates a new TestLegacyQueryAdapter object. 
	 *
	 */
	public TestLegacyQueryAdapter() {
		super();
		leg = new SampleLegacyBoDataQuery();
		lqa = new LegacyQueryAdapter(leg);
		lqa.setManagerName("LOCALDB"); //$NON-NLS-1$
	}

	/**
	 * Unit test for the whole lifecycle of a query.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testQueryLifecycle() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				
				try {
					lqa.init(getProvider());				
					lqa.open();
					assertTrue(leg.isOpen());
					lqa.execute();
					while (lqa.next()) {
						int legRow = leg.getRow();
						int lqaRow = lqa.getRow();
						assertEquals(legRow, lqaRow);					
					}
					assertFalse(leg.next());
					lqa.close();
				} catch (DataAccessException e) {
					throw new gr.interamerican.bo2.arch.exceptions.DataAccessException(e);
				}
				
			}
		}.execute();
	}
	
	/**
	 * Unit test for the whole lifecycle of a query.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=DataException.class)
	public void testExecute_failing() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				LegacyQueryAdapter adapter = 
					new LegacyQueryAdapter(new QueryFailingOnExecute());
				adapter.init(getProvider());				
				adapter.open();
				adapter.execute();				
			}
		}.execute();
	}
	
	/**
	 * Unit test for the whole lifecycle of a query.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=DataException.class)
	public void testNext_failing() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				LegacyQueryAdapter adapter = 
					new LegacyQueryAdapter(new QueryFailingOnNext());
				adapter.init(getProvider());				
				adapter.open();
				adapter.execute();	
				adapter.next();
			}
		}.execute();
	}
	
	/**
	 * Unit test for the get methods.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testGetMethods() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				MockLegacyQuery mock = new MockLegacyQuery();
				LegacyQueryAdapter adapter = new LegacyQueryAdapter(mock);
				adapter.setManagerName("LOCALDB");
				adapter.init(getProvider());				
				adapter.open();
				adapter.execute();	
				adapter.next();
				String field = "col"; //$NON-NLS-1$
				
				try {
					assertEquals(adapter.getString(field), mock.getString(field));
					assertEquals(adapter.getBigDecimal(field), mock.getBigDecimal(field));
					assertEquals(adapter.getBoolean(field), mock.getBoolean(field));
					assertEquals(adapter.getByte(field), mock.getByte(field));
					assertEquals(adapter.getBytes(field), mock.getBytes(field));				
					assertEquals(adapter.getDate(field), mock.getDate(field));
					assertEquals(Double.valueOf(adapter.getDouble(field)), Double.valueOf(mock.getDouble(field)));				
					assertEquals(Float.valueOf(adapter.getFloat(field)), Float.valueOf(mock.getFloat(field)));
					assertEquals(adapter.getInt(field), mock.getInt(field));
					assertEquals(adapter.getLong(field), mock.getLong(field));
					assertEquals(adapter.getObject(field), mock.getObject(field));
					assertEquals(adapter.getShort(field), mock.getShort(field));
					Calendar cal = DateUtils.getCalendar(mock.getDate(field));
					assertEquals(adapter.getCalendar(field), cal);
					assertEquals(adapter.getDate(field), mock.getDate(field));
				} catch (DataAccessException e) {
					throw new DataException(e);
				}
				
			}
		}.execute();
	}
	
	
	/**
	 * Unit test for the get methods failing.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=gr.interamerican.bo2.arch.exceptions.DataAccessException.class)
	public void testGetString_fail() 
	throws UnexpectedException, DataException, LogicException {
		new MockFailingQueryCmd () {
			@Override
			void task() throws DataException {
				adapter.getString("col");
			}
		}.execute();
	}
	
	/**
	 * Unit test for the get methods failing.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=gr.interamerican.bo2.arch.exceptions.DataAccessException.class)
	public void testGetDouble_fail() 
	throws UnexpectedException, DataException, LogicException {
		new MockFailingQueryCmd () {
			@Override
			void task() throws DataException {
				adapter.getDouble("col");
			}
		}.execute();
	}
	
	/**
	 * Unit test for the get methods failing.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=gr.interamerican.bo2.arch.exceptions.DataAccessException.class)
	public void testGetInt_fail() 
	throws UnexpectedException, DataException, LogicException {
		new MockFailingQueryCmd () {
			@Override
			void task() throws DataException {
				adapter.getInt("col");
			}
		}.execute();
	}
	
	/**
	 * Unit test for the get methods failing.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=gr.interamerican.bo2.arch.exceptions.DataAccessException.class)
	public void testGetLong_fail() 
	throws UnexpectedException, DataException, LogicException {
		new MockFailingQueryCmd () {
			@Override
			void task() throws DataException {
				adapter.getLong("col");
			}
		}.execute();
	}
	
	/**
	 * Unit test for the get methods failing.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=gr.interamerican.bo2.arch.exceptions.DataAccessException.class)
	public void testGetShort_fail() 
	throws UnexpectedException, DataException, LogicException {
		new MockFailingQueryCmd () {
			@Override
			void task() throws DataException {
				adapter.getShort("col");
			}
		}.execute();
	}
	
	/**
	 * Unit test for the get methods failing.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=gr.interamerican.bo2.arch.exceptions.DataAccessException.class)
	public void testGetDate_fail() 
	throws UnexpectedException, DataException, LogicException {
		new MockFailingQueryCmd () {
			@Override
			void task() throws DataException {
				adapter.getDate("col");
			}
		}.execute();
	}
	
	/**
	 * Unit test for the get methods failing.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=gr.interamerican.bo2.arch.exceptions.DataAccessException.class)
	public void testGetBoolean_fail() 
	throws UnexpectedException, DataException, LogicException {
		new MockFailingQueryCmd () {
			@Override
			void task() throws DataException {
				adapter.getBoolean("col");
			}
		}.execute();
	}
	
	/**
	 * Unit test for the get methods failing.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=gr.interamerican.bo2.arch.exceptions.DataAccessException.class)
	public void testGetCalendar_fail() 
	throws UnexpectedException, DataException, LogicException {
		new MockFailingQueryCmd () {
			@Override
			void task() throws DataException {
				adapter.getCalendar("col");
			}
		}.execute();
	}
	
	/**
	 * Unit test for the get methods failing.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=gr.interamerican.bo2.arch.exceptions.DataAccessException.class)
	public void testGetBigDecimal_fail() 
	throws UnexpectedException, DataException, LogicException {
		new MockFailingQueryCmd () {
			@Override
			void task() throws DataException {
				adapter.getBigDecimal("col");
			}
		}.execute();
	}
	
	/**
	 * Unit test for the get methods failing.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=gr.interamerican.bo2.arch.exceptions.DataAccessException.class)
	public void testGetByte_fail() 
	throws UnexpectedException, DataException, LogicException {
		new MockFailingQueryCmd () {
			@Override
			void task() throws DataException {
				adapter.getByte("col");
			}
		}.execute();
	}
	
	/**
	 * Unit test for the get methods failing.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=gr.interamerican.bo2.arch.exceptions.DataAccessException.class)
	public void testGetBytes_fail() 
	throws UnexpectedException, DataException, LogicException {
		new MockFailingQueryCmd () {
			@Override
			void task() throws DataException {
				adapter.getBytes("col");
			}
		}.execute();
	}
	
	/**
	 * Unit test for the get methods failing.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=gr.interamerican.bo2.arch.exceptions.DataAccessException.class)
	public void testGetFloat_fail() 
	throws UnexpectedException, DataException, LogicException {
		new MockFailingQueryCmd () {			
			@Override
			void task() throws DataException {
				adapter.getFloat("col");
			}
		}.execute();
	}
	
	
	
	/**
	 * tests setAvoidLock.
	 */
	@Test
	public void testSetAvoidLock() {
		lqa.setAvoidLock(true);
		assertTrue(leg.isWithUR());
		lqa.setAvoidLock(false);
		assertFalse(leg.isWithUR());
	}
	
	/**
	 * tests setAvoidLock.
	 */
	@Test
	public void testGetAvoidLock() {
		leg.setWithUR(true);
		assertTrue(lqa.isAvoidLock());
		leg.setWithUR(false);
		assertFalse(lqa.isAvoidLock());
	}
	
	/**
	 * Legacy query that fails on execute.
	 */
	@SuppressWarnings("rawtypes")
	private class QueryFailingOnExecute extends StreamQuery {
		@Override 
		public void execute()
		throws interamerican.architecture.exceptions.DataException {		
			throw new interamerican.architecture.exceptions.DataException();
		}
		@Override
		protected Record emptyRecord() {
			return null;
		}		
	}
	
	
	
	/**
	 * Legacy query that fails on execute.
	 */
	@SuppressWarnings("rawtypes")
	private class QueryFailingOnNext extends StreamQuery {
		@Override 
		public void execute()
		throws interamerican.architecture.exceptions.DataException {		
			/*empty*/
		}
		@Override
		protected Record emptyRecord() {
			return null;
		}
		@Override
		public boolean next() throws DataAccessException {			
			throw new DataAccessException();
		}
	}
	
	/**
	 * Command for tests that use use a MockFailingLegacyQuery.
	 */
	private static abstract class MockFailingQueryCmd extends AbstractBo2RuntimeCmd  {
		/**
		 * Mock query.
		 */
		MockFailingLegacyQuery mock = new MockFailingLegacyQuery();
		/**
		 * Query adapter to test.
		 */
		LegacyQueryAdapter adapter = new LegacyQueryAdapter(mock);
		
		@Override public void work() throws LogicException, 
		DataException, InitializationException, UnexpectedException {	
			adapter.setManagerName("LOCALDB"); //$NON-NLS-1$
			adapter.init(getProvider());				
			adapter.open();
			adapter.execute();	
			adapter.next();
			task();
		}
		/**
		 * Put test code here.
		 * 
		 * @throws DataException
		 */
		abstract void task() throws DataException;
		
	}
}
