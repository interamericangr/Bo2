package gr.interamerican.bo2.legacy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.samples.MockLegacyPw;
import gr.interamerican.bo2.samples.MockLegacyWorkerThrowsOnClose;
import gr.interamerican.bo2.samples.MockLegacyWorkerThrowsOnOpen;
import interamerican.archimpl.workers.DataWorker;

import org.junit.Test;

/**
 * Unit test for {@link LegacyWorkerAdapter}.
 */
public class TestLegacyWorkerAdapter {
	
	/**
	 * Test init
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 * 
	 */
	@Test
	public void testInit() 
	throws UnexpectedException, DataException, LogicException{		
		new AbstractBo2RuntimeCmd() { 			
			@Override 
			public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				Adapter adapter = new Adapter();
				adapter.init(this.getProvider());
				DataWorker dw = (DataWorker) adapter.legacy;
				assertNotNull(dw.getConnection());
			}
		}.execute();
	}
	
	/**
	 * Test open
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testOpen() 
	throws UnexpectedException, DataException, LogicException{
		
		new AbstractBo2RuntimeCmd() { 			
			@Override 
			public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				Adapter adapter = new Adapter();
				adapter.init(this.getProvider());
				adapter.open();
				DataWorker dw = (DataWorker) adapter.legacy;
				assertTrue(dw.isOpen());
			}
		}.execute();
	}
	
	/**
	 * Test close
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test
	public void testClose() 
	throws UnexpectedException, DataException, LogicException{
		
		new AbstractBo2RuntimeCmd() { 			
			@Override
			public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				Adapter adapter = new Adapter();
				adapter.init(this.getProvider());
				adapter.open();
				adapter.close();
				DataWorker dw = (DataWorker) adapter.legacy;
				assertFalse(dw.isOpen());
			}
		}.execute();
	}
	
	/**
	 * Test close
	 * @throws DataException
	 * @throws LogicException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=DataException.class)
	public void testOpen_failing() 
	throws DataException, LogicException, UnexpectedException {
		new AbstractBo2RuntimeCmd() { 			
			@Override
			public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				FailingOnOpenAdapter adapter = new FailingOnOpenAdapter();
				adapter.init(this.getProvider());
				adapter.open();				
			}
		}.execute();		
	}
	
	/**
	 * Test close
	 * @throws DataException
	 * @throws LogicException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=DataException.class)
	public void testClose_failing() 
	throws DataException, LogicException, UnexpectedException {
		new AbstractBo2RuntimeCmd() { 			
			@Override
			public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				FailingOnCloseAdapter adapter = new FailingOnCloseAdapter();
				adapter.init(this.getProvider());
				adapter.open();		
				adapter.close();
			}
		}.execute();		
	}


	/**
	 * Adapter for tests
	 */
	@ManagerName("LOCALDB")
	class Adapter extends LegacyWorkerAdapter{ 
		/**
		 * Creates a new Adapter object.
		 */
		Adapter(){
			super(new MockLegacyPw());
		}
   }

	/**
	 * Adapter for tests
	 */
	@ManagerName("LOCALDB")
	class FailingOnOpenAdapter extends LegacyWorkerAdapter{	
	     /**
		 * Creates a new FailingAdapter object. 
		 *
		 */
		FailingOnOpenAdapter(){
			super(new MockLegacyWorkerThrowsOnOpen());			
		}
	}
	
	/**
	 * Adapter for tests
	 */
	@ManagerName("LOCALDB")
	class FailingOnCloseAdapter extends LegacyWorkerAdapter{	
	     /**
		 * Creates a new FailingAdapter object. 
		 *
		 */
		FailingOnCloseAdapter(){
			super(new MockLegacyWorkerThrowsOnClose());			
		}
	}

}