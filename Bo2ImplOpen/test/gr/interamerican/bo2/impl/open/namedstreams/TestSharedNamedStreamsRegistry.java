/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 ******************************************************************************/
package gr.interamerican.bo2.impl.open.namedstreams;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.samples.implopen.mocks.MockAbstractNamedStreamsManager;
import gr.interamerican.bo2.samples.implopen.mocks.MockNamedStream;
import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;

import java.util.Properties;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link SharedNamedStreamsRegistry}.
 */
@SuppressWarnings({ "nls", "unchecked", "rawtypes" })
public class TestSharedNamedStreamsRegistry {
	
	/**
	 * Clear the registry.
	 */
	@Before @After
	public void cleanup() {
		SharedNamedStreamsRegistry.name2stream.clear();
		SharedNamedStreamsRegistry.stream2name.clear();
		SharedNamedStreamsRegistry.providersAccessingStream.clear();
	}
	
	/**
	 * Test registerStream
	 */
	@Test
	public void testRegisterStream() {
		String name = "streamName";
		NamedStreamsProvider nsp = new MockAbstractNamedStreamsManager(new Properties());
		NamedStream<?> stream = new MockNamedStream(StreamType.INPUTSTREAM, StreamResource.FILE, null, name, 5, Bo2UtilsEnvironment.getDefaultTextCharset());
		SharedNamedStreamsRegistry.register(name, stream, nsp);
		Assert.assertEquals(stream, SharedNamedStreamsRegistry.name2stream.get(name));
		Assert.assertEquals(name, SharedNamedStreamsRegistry.stream2name.get(stream));
		Set<NamedStreamsProvider> providers = SharedNamedStreamsRegistry.providersAccessingStream.get(stream);
		Assert.assertNotNull(providers);
		Assert.assertTrue(providers.size()==1);
		Assert.assertEquals(nsp, providers.iterator().next());
	}
	
	/**
	 * Test getStream
	 */
	@Test
	public void testGetStream() {
		String name = "streamName";
		NamedStreamsProvider nsp = new MockAbstractNamedStreamsManager(new Properties());
		NamedStream<?> stream = new MockNamedStream(StreamType.INPUTSTREAM, StreamResource.FILE, null, name, 5, Bo2UtilsEnvironment.getDefaultTextCharset());
		SharedNamedStreamsRegistry.register(name, stream, nsp);
		NamedStreamsProvider nsp1 = new MockAbstractNamedStreamsManager(new Properties());
		Assert.assertEquals(stream, SharedNamedStreamsRegistry.getStream(name, nsp1));
		Assert.assertTrue(SharedNamedStreamsRegistry.providersAccessingStream.get(stream).size()==2);
	}
	
	/**
	 * Test releaseSharedStreams
	 * 
	 * @throws DataException
	 */
	@Test
	public void testReleaseSharedStreams() throws DataException {
		String name = "streamName";
		NamedStreamsProvider nsp = new MockAbstractNamedStreamsManager(new Properties());
		NamedStream<?> stream = new MockNamedStream(StreamType.INPUTSTREAM, StreamResource.FILE, null, name, 5, Bo2UtilsEnvironment.getDefaultTextCharset());
		SharedNamedStreamsRegistry.register(name, stream, nsp);
		NamedStreamsProvider nsp1 = new MockAbstractNamedStreamsManager(new Properties());
		Assert.assertEquals(stream, SharedNamedStreamsRegistry.getStream(name, nsp1));
		SharedNamedStreamsRegistry.releaseSharedStreams(nsp);
		Assert.assertTrue(SharedNamedStreamsRegistry.providersAccessingStream.get(stream).size()==1);
		Assert.assertEquals(stream, SharedNamedStreamsRegistry.name2stream.get(name));
		Assert.assertEquals(name, SharedNamedStreamsRegistry.stream2name.get(stream));
		SharedNamedStreamsRegistry.releaseSharedStreams(nsp1);
		Assert.assertNull(SharedNamedStreamsRegistry.providersAccessingStream.get(stream));
	}

}
