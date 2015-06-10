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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.CouldNotCreateNamedStreamException;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.NamedStreamFactory;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResource;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedPrintStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link NamedStreamsManagerImpl}.
 */
@SuppressWarnings({"rawtypes", "unchecked", "nls"})
public class TestNamedStreamsManagerImpl {
	
	
	
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		Properties p = new Properties();
		NamedStreamsManagerImpl impl = new NamedStreamsManagerImpl(p);
		Assert.assertEquals(p,impl.properties);		
		Assert.assertNotNull(impl.streams);
		Assert.assertNotNull(impl.nsdFactory);
	}
	
	/**
	 * Test for getDefinition(name).
	 * @throws InitializationException 
	 */
	@Test
	public void testGetDefinition_succeed() throws InitializationException {
		Properties p = new Properties();		
		NamedStreamsManagerImpl impl = new NamedStreamsManagerImpl(p);
		NsDefinitionFactory nsdf = mock(NsDefinitionFactory.class);
		impl.nsdFactory = nsdf;
		NamedStreamDefinition def = mock(NamedStreamDefinition.class);
		String name = "name"; 
		when(nsdf.create(name, p)).thenReturn(def);		
		NamedStreamDefinition actual = impl.getDefinition(name);
		Assert.assertEquals(def, actual);
	}
	
	/**
	 * Test for getDefinition(name).
	 * 
	 * @throws InitializationException 
	 */	
	@Test(expected=InitializationException.class)
	public void testGetDefinition_fail() throws InitializationException {
		Properties p = new Properties();		
		NamedStreamsManagerImpl impl = new NamedStreamsManagerImpl(p);
		NsDefinitionFactory nsdf = mock(NsDefinitionFactory.class);
		impl.nsdFactory = nsdf;
		String name = "name"; 
		when(nsdf.create(name, p)).thenThrow(InitializationException.class);	
		impl.getDefinition(name);		
	}
	
	/**
	 * Tests open(namedStreamDefinition).
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 * @throws InitializationException 
	 * 
	 */	
	@Test
	public void testOpen_succeed() throws CouldNotCreateNamedStreamException, InitializationException {
		Properties p = new Properties();		
		NamedStreamsManagerImpl impl = new NamedStreamsManagerImpl(p);
		NamedStreamDefinition def = new NamedStreamDefinition();
		StreamResource resourceType = mock(StreamResource.class);
		NamedStreamFactory factory = mock(NamedStreamFactory.class);
		when(resourceType.getFactory()).thenReturn(factory);
		NamedStream ns = mock(NamedStream.class);
		when(factory.create(def)).thenReturn(ns);
		def.setResourceType(resourceType);		
		NamedStream<?> actual = impl.open(def);
		Assert.assertEquals(ns, actual);
	}
	
	
	/**
	 * Tests open(namedStreamDefinition).
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 * @throws InitializationException 
	 * 
	 */
	@Test(expected=InitializationException.class)
	public void testOpen_fail() throws CouldNotCreateNamedStreamException, InitializationException {
		Properties p = new Properties();		
		NamedStreamsManagerImpl impl = new NamedStreamsManagerImpl(p);
		NamedStreamDefinition def = new NamedStreamDefinition();
		StreamResource resourceType = mock(StreamResource.class);
		NamedStreamFactory factory = mock(NamedStreamFactory.class);
		when(resourceType.getFactory()).thenReturn(factory);
		when(factory.create(def)).thenThrow(CouldNotCreateNamedStreamException.class);
		def.setResourceType(resourceType);		
		impl.open(def);
	}
	
	/**
	 * Tests getStream(name).
	 * @throws InitializationException 
	 */	
	@Test
	public void testGetStream_withOpenStream() throws InitializationException {
		Properties p = new Properties();
		NamedStreamsManagerImpl impl = new NamedStreamsManagerImpl(p);
		String name = "foo";
		NamedStream ns = mock(NamedStream.class);
		impl.streams.put(name, ns);
		NamedStream<?> actual = impl.getStream(name);
		Assert.assertEquals(ns, actual);
	}
	
	/**
	 * Tests getStream(name).
	 * @throws InitializationException 
	 * @throws CouldNotCreateNamedStreamException 
	 */	
	@Test
	public void testGetStream_withNewStream() throws InitializationException, CouldNotCreateNamedStreamException {
		Properties p = new Properties();
		NamedStreamsManagerImpl impl = new NamedStreamsManagerImpl(p);
		NsDefinitionFactory nsdf = mock(NsDefinitionFactory.class);
		impl.nsdFactory = nsdf;
		NamedStreamDefinition def = new NamedStreamDefinition();
		String name = "foo";
		when(nsdf.create(name, p)).thenReturn(def);		
		StreamResource resourceType = mock(StreamResource.class);
		NamedStreamFactory factory = mock(NamedStreamFactory.class);
		NamedStream ns = mock(NamedStream.class);
		when(ns.getName()).thenReturn(name);
		when(resourceType.getFactory()).thenReturn(factory);
		when(factory.create(def)).thenReturn(ns);
		def.setResourceType(resourceType);		
		NamedStream<?> actual = impl.getStream(name);
		Assert.assertEquals(ns, actual);
		NamedStream<?> registered = impl.streams.get(name);
		Assert.assertEquals(ns, registered);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	

	

	

}
