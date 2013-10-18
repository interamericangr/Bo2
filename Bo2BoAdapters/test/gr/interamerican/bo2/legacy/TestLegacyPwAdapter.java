package gr.interamerican.bo2.legacy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.samples.MockFailingLegacyPw;
import gr.interamerican.bo2.samples.MockLegacyPw;
import gr.interamerican.bo2.samples.SampleLegacyPoAdapter;
import gr.interamerican.bo2.samples.SampleLegacyPwAdapter;
import gr.interamerican.bo2.samples.SamplePo;

import org.junit.Test;

/**
 * 
 */
public class TestLegacyPwAdapter {
	/**
	 * po for tests.
	 */
	SamplePo po = new SampleLegacyPoAdapter();
	/**
	 * pw for tests.
	 */
	SampleLegacyPwAdapter pw = new SampleLegacyPwAdapter(new MockLegacyPw());
	

	/**
	 * test for read.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testRead() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() { 			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				po.setKey(MockLegacyPw.ID);
				pw.init(this.getProvider());
				pw.open();
				po = pw.read(po);
				assertEquals(MockLegacyPw.ID, po.getId());
				assertEquals(MockLegacyPw.STRING1, po.getString1());
				assertEquals(MockLegacyPw.STRING2, po.getString2());
				pw.close();
			}
		}.execute();
	}

	/**
	 * test for read.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=PoNotFoundException.class)
	public void testRead_WithPoNotFoundException() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() { 			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				po.setKey(MockLegacyPw.NTF_ID);
				pw.init(this.getProvider());
				pw.open();
				po = pw.read(po);
			}
		}.execute();
	}
	
	/**
	 * test for store.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testStore() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() { 			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				assertFalse(pw.getPw().isStored());
				po.setKey(MockLegacyPw.ID);				
				pw.init(this.getProvider());
				pw.open();
				po = pw.store(po);				
				pw.close();
				assertTrue(pw.getPw().isStored());
			}
		}.execute();
	}

	/**
	 * test for store.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=PoNotFoundException.class)
	public void testStore_WithPoNotFoundException() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() { 			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {				
				po.setKey(MockLegacyPw.NTF_ID);
				pw.init(this.getProvider());
				pw.open();
				po = pw.store(po);
			}
		}.execute();
	}
		
	/**
	 * test for delete.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testDelete() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() { 			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				assertFalse(pw.getPw().isDeleted());
				po.setKey(MockLegacyPw.ID);				
				pw.init(this.getProvider());
				pw.open();
				po = pw.delete(po);				
				pw.close();
				assertTrue(pw.getPw().isDeleted());
			}
		}.execute();
	}

	/**
	 * test for delete.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=PoNotFoundException.class)
	public void testDelete_WithPoNotFoundException() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() { 			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {				
				po.setKey(MockLegacyPw.NTF_ID);
				pw.init(this.getProvider());
				pw.open();
				po = pw.delete(po);
			}
		}.execute();
	}
			
	/**
	 * test for update.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testUpdate() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() { 			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				assertFalse(pw.getPw().isDeleted());
				assertFalse(pw.getPw().isStored());
				po.setKey(MockLegacyPw.ID);				
				pw.init(this.getProvider());
				pw.open();
				po = pw.update(po);				
				pw.close();
				assertTrue(pw.getPw().isStored());
				assertTrue(pw.getPw().isDeleted());
			}
		}.execute();
	}

	/**
	 * test for update.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=PoNotFoundException.class)
	public void testUpdate_WithPoNotFoundException() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() { 			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {				
				po.setKey(MockLegacyPw.NTF_ID);
				pw.init(this.getProvider());
				pw.open();
				po = pw.delete(po);
			}
		}.execute();
	}
	
	/**
	 * test for ignores something.
	 */
	@Test
	public void testIgnoresSomething() {
		assertEquals(pw.getPw().ignoresSomething(), pw.ignoresSomething());
	}
	
	/**
	 * test pw operations throwing exceptions.
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Test
	public void testRead_withExceptions() throws InstantiationException, IllegalAccessException {
		testReadThrowing(interamerican.architecture.exceptions.PoNotFoundException.class, PoNotFoundException.class);
		testReadThrowing(interamerican.architecture.exceptions.DataException.class, DataException.class);
	}
	
	/**
	 * test pw operations throwing exceptions.
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Test
	public void testStore_withExceptions() throws InstantiationException, IllegalAccessException {
		testStoreThrowing(interamerican.architecture.exceptions.PoNotFoundException.class, PoNotFoundException.class);
		testStoreThrowing(interamerican.architecture.exceptions.DataException.class, DataException.class);
	}
	
	/**
	 * test pw operations throwing exceptions.
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Test
	public void testDelete_withExceptions() throws InstantiationException, IllegalAccessException {
		testDeleteThrowing(interamerican.architecture.exceptions.PoNotFoundException.class, PoNotFoundException.class);
		testDeleteThrowing(interamerican.architecture.exceptions.DataException.class, DataException.class);
	}
	
	/**
	 * test pw operations throwing exceptions.
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Test
	public void testUpdate_withExceptions() throws InstantiationException, IllegalAccessException {
		testUpdateThrowing(interamerican.architecture.exceptions.PoNotFoundException.class, PoNotFoundException.class);
		testUpdateThrowing(interamerican.architecture.exceptions.DataException.class, DataException.class);
	}
	
	
	/**
	 * Tests pw operation throwing exception.
	 * 
	 * @param cause
	 * @param expected
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private void testReadThrowing
	(Class<? extends interamerican.architecture.exceptions.DataException> cause, 
	 Class<? extends DataException> expected) 
	throws InstantiationException, IllegalAccessException {
		interamerican.architecture.exceptions.DataException t = cause.newInstance();
		MockFailingLegacyPw legPw = new MockFailingLegacyPw(t);
		LegacyPwAdapter<SamplePo> adapter = new LegacyPwAdapter<SamplePo>(legPw);
		try {			
			adapter.read(new SampleLegacyPoAdapter());			
		} catch (DataException thrown) {
			Class<? extends DataException> actual = thrown.getClass();
			assertEquals(expected, actual);
		}		
	}
	
	/**
	 * Tests pw operation throwing exception.
	 * 
	 * @param cause
	 * @param expected
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private void testStoreThrowing
	(Class<? extends interamerican.architecture.exceptions.DataException> cause, 
	 Class<? extends DataException> expected) 
	throws InstantiationException, IllegalAccessException {
		interamerican.architecture.exceptions.DataException t = cause.newInstance();
		MockFailingLegacyPw legPw = new MockFailingLegacyPw(t);
		LegacyPwAdapter<SamplePo> adapter = new LegacyPwAdapter<SamplePo>(legPw);
		try {
			adapter.store(new SampleLegacyPoAdapter());			
		} catch (DataException thrown) {
			Class<? extends DataException> actual = thrown.getClass();
			assertEquals(expected, actual);
		}		
	}
	
	/**
	 * Tests pw operation throwing exception.
	 * 
	 * @param cause
	 * @param expected
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private void testUpdateThrowing
	(Class<? extends interamerican.architecture.exceptions.DataException> cause, 
	 Class<? extends DataException> expected) 
	throws InstantiationException, IllegalAccessException {
		interamerican.architecture.exceptions.DataException t = cause.newInstance();
		MockFailingLegacyPw legPw = new MockFailingLegacyPw(t);
		LegacyPwAdapter<SamplePo> adapter = new LegacyPwAdapter<SamplePo>(legPw);
		try {
			adapter.update(new SampleLegacyPoAdapter());			
		} catch (DataException thrown) {
			Class<? extends DataException> actual = thrown.getClass();
			assertEquals(expected, actual);
		}		
	}
	
	/**
	 * Tests pw operation throwing exception.
	 * 
	 * @param cause
	 * @param expected
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private void testDeleteThrowing
	(Class<? extends interamerican.architecture.exceptions.DataException> cause, 
	 Class<? extends DataException> expected) 
	throws InstantiationException, IllegalAccessException {
		interamerican.architecture.exceptions.DataException t = cause.newInstance();
		MockFailingLegacyPw legPw = new MockFailingLegacyPw(t);
		LegacyPwAdapter<SamplePo> adapter = new LegacyPwAdapter<SamplePo>(legPw);
		try {
			adapter.delete(new SampleLegacyPoAdapter());			
		} catch (DataException thrown) {
			Class<? extends DataException> actual = thrown.getClass();
			assertEquals(expected, actual);
		}		
	}
	
	/**
	 * Tests getDetachStrategy()
	 */
	@Test
	public void testGetDetachStrategy() {
		assertNull(pw.getDetachStrategy());
	}
	
				
	

}
