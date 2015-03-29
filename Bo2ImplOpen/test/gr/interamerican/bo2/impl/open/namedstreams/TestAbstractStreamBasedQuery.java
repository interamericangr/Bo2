package gr.interamerican.bo2.impl.open.namedstreams;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link AbstractStreamBasedQuery}.
 */
public class TestAbstractStreamBasedQuery {
	
	
	/**
	 * Test for init().
	 * @throws InitializationException 
	 */
	@SuppressWarnings({"unchecked", "rawtypes", "nls", })
	@Test
	public void testInit_ok() throws InitializationException {
		String name = "name";
		String manager = "manager";
		ConcreteQuery q = new ConcreteQuery();
		Provider p = mock(Provider.class);
		NamedStreamsProvider nsp = mock(NamedStreamsProvider.class);
		NamedStream ns = mock(NamedStream.class);		
		when(nsp.getStream(name)).thenReturn(ns);
		
		when(p.getResource(manager, NamedStreamsProvider.class)).thenReturn(nsp);
		q.setManagerName(manager);
		q.setStreamName(name);
		q.init(p);
		Assert.assertEquals(ns, q.stream);
	}
	
	/**
	 * Test for init().
	 * @throws InitializationException 
	 */
	@SuppressWarnings({"nls"})
	@Test(expected=InitializationException.class)
	public void testInit_fail() throws InitializationException {
		String name = "name";
		String manager = "manager";
		ConcreteQuery q = new ConcreteQuery();
		Provider p = mock(Provider.class);
		NamedStreamsProvider nsp = mock(NamedStreamsProvider.class);				
		when(nsp.getStream(name)).thenReturn(null);		
		when(p.getResource(manager, NamedStreamsProvider.class)).thenReturn(nsp);
		q.setManagerName(manager);
		q.setStreamName(name);
		q.init(p);		
	}
	
	/**
	 * Test for setStreamName(n).
	 *  
	 */	
	@Test
	public void testSetStreamName() {
		String name = "name";		 //$NON-NLS-1$
		ConcreteQuery q = new ConcreteQuery();
		q.setStreamName(name);
		Assert.assertEquals(name, q.streamName);
	}
	
	/**
	 * Test for getStreamName().
	 *  
	 */	
	@Test
	public void testGetStreamName() {
		ConcreteQuery q = new ConcreteQuery();
		q.streamName =  "name"; //$NON-NLS-1$
		Assert.assertEquals(q.streamName, q.getStreamName());
	}
	
	
	
	
	class ConcreteQuery extends AbstractStreamBasedQuery {

		@Override
		public boolean next() throws DataAccessException {
			return false;
		}

		@Override
		public int getRow() throws DataAccessException {
			return 0;
		}

		@Override
		public void execute() throws DataException {
			/* do nothing */
		}
		
	}

}
