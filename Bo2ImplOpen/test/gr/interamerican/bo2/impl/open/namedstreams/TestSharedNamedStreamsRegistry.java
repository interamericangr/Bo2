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

import static org.junit.Assert.*;

import java.util.Properties;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResourceEnum;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;
import gr.interamerican.bo2.samples.implopen.mocks.MockNamedStream;
import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;

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
	 * Test registerStream.
	 */
	@Test
	public void testRegisterStream() {
		String name = "streamName";
		NamedStreamsProvider nsp = new NamedStreamsManagerImpl(new Properties());
		NamedStream<?> stream = new MockNamedStream(StreamType.INPUTSTREAM, StreamResourceEnum.FILE, null, name, 5, Bo2UtilsEnvironment.get().getDefaultTextCharset(),"");
		SharedNamedStreamsRegistry.register(name, stream, nsp);
		assertEquals(stream, SharedNamedStreamsRegistry.name2stream.get(name));
		assertEquals(name, SharedNamedStreamsRegistry.stream2name.get(stream));
		Set<NamedStreamsProvider> providers = SharedNamedStreamsRegistry.providersAccessingStream.get(stream);
		assertNotNull(providers);
		assertTrue(providers.size()==1);
		assertEquals(nsp, providers.iterator().next());
	}

	/**
	 * Test getStream.
	 */
	@Test
	public void testGetStream() {
		String name = "streamName";
		NamedStreamsProvider nsp = new NamedStreamsManagerImpl(new Properties());
		NamedStream<?> stream = new MockNamedStream(StreamType.INPUTSTREAM, StreamResourceEnum.FILE, null, name, 5,
				Bo2UtilsEnvironment.get().getDefaultTextCharset(), "");
		SharedNamedStreamsRegistry.register(name, stream, nsp);
		NamedStreamsProvider nsp1 = new NamedStreamsManagerImpl(new Properties());
		assertEquals(stream, SharedNamedStreamsRegistry.getStream(name, nsp1));
		assertTrue(SharedNamedStreamsRegistry.providersAccessingStream.get(stream).size()==2);
	}

	/**
	 * Test releaseSharedStreams.
	 *
	 * @throws DataException the data exception
	 */
	@Test
	public void testReleaseSharedStreams() throws DataException {
		String name = "streamName";
		NamedStreamsProvider nsp = new NamedStreamsManagerImpl(new Properties());
		NamedStream<?> stream = new MockNamedStream(StreamType.INPUTSTREAM, StreamResourceEnum.FILE, null, name, 5, Bo2UtilsEnvironment.get().getDefaultTextCharset(),"");
		SharedNamedStreamsRegistry.register(name, stream, nsp);
		NamedStreamsProvider nsp1 = new NamedStreamsManagerImpl(new Properties());
		assertEquals(stream, SharedNamedStreamsRegistry.getStream(name, nsp1));
		SharedNamedStreamsRegistry.releaseSharedStreams(nsp);
		assertTrue(SharedNamedStreamsRegistry.providersAccessingStream.get(stream).size()==1);
		assertEquals(stream, SharedNamedStreamsRegistry.name2stream.get(name));
		assertEquals(name, SharedNamedStreamsRegistry.stream2name.get(stream));
		SharedNamedStreamsRegistry.releaseSharedStreams(nsp1);
		assertNull(SharedNamedStreamsRegistry.providersAccessingStream.get(stream));
	}
}