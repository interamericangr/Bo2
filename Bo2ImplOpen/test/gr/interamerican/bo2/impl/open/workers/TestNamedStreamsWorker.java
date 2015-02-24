package gr.interamerican.bo2.impl.open.workers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.utils.StringConstants;

import org.junit.Before;
import org.junit.Test;

/**
 * test {@link NamedStreamsWorker}
 */
public class TestNamedStreamsWorker {
	
	/**
	 * 
	 */
	NamedStreamsWorker impl;
	
	/**
	 * 
	 */
	NamedStreamsProvider mock;
	
	/**
	 * setup
	 */
	@Before
	public void before() {
		mock = mock(NamedStreamsProvider.class);
		impl = new NamedStreamsWorker();
		impl.nsp = mock;
	}
	
	/**
	 * @throws InitializationException
	 */
	@Test
	public void testGetSharedStream() throws InitializationException {
		impl.nsp = mock;
		impl.getSharedStream(StringConstants.EMPTY);
		verify(mock, times(1)).getSharedStream(StringConstants.EMPTY);
	}
	
	/**
	 * @throws InitializationException
	 */
	@Test
	public void testGetStream() throws InitializationException {
		impl.nsp = mock;
		impl.getStream(StringConstants.EMPTY);
		verify(mock, times(1)).getStream(StringConstants.EMPTY);
	}

}
