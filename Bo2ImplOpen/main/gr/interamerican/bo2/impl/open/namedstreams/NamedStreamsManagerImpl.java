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
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.CouldNotConvertNamedStreamException;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.CouldNotCreateNamedStreamException;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.NamedStreamFactory;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResource;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;

import java.util.HashMap;
import java.util.Properties;

/**
 * Implementation of NamedStreamsProvider.
 */
public class NamedStreamsManagerImpl 
implements NamedStreamsProvider {
	
	/**
	 * Properties containing NamedStream specifications.
	 */ 
	Properties properties;

	/** Streams opened by the program. */
	HashMap<String, NamedStream<?>> streams;
	
	/**
	 * Factory for {@link NamedStreamDefinition} objects.
	 */
	NsDefinitionFactory nsdFactory = new NsDefinitionFactory();

	/**
	 * Creates a new NamedStreamsCreator that is reads input by a Properties
	 * object.
	 * 
	 * @param properties
	 *            Properties object with input for this NamedStreamCreator.
	 */
	public NamedStreamsManagerImpl(Properties properties) {
		this.properties = properties;
		this.streams = new HashMap<String, NamedStream<?>>();
	}
	
	
	/**
	 * Gets the definition of the stream with the specified logical name.
	 *
	 * @param name        Stream name.
	 *        
	 * @return Returns the definition.
	 * @throws InitializationException the initialization exception
	 */
	NamedStreamDefinition getDefinition(String name) throws InitializationException {
		return nsdFactory.create(name, properties);
	}
	
	/**
	 * Opens a NamedStream.
	 *
	 * @param def the def
	 * @return Returns the NamedStream.
	 * @throws InitializationException the initialization exception
	 */
	NamedStream<?> open(NamedStreamDefinition def) throws InitializationException {
		try {
			StreamResource resourceType = def.getResourceType();
			NamedStreamFactory factory = resourceType.getFactory();
			return factory.create(def);
		} catch (CouldNotCreateNamedStreamException e) {			
			throw new InitializationException(e);
		}
	}

	@Override
	public NamedStream<?> getStream(String name) throws InitializationException {
		NamedStream<?> ns = streams.get(name);
		if (ns==null) {
			NamedStreamDefinition def = getDefinition(name);
			ns = open(def);		
			registerStream(ns);
		}
		return ns;
	}

	@Override
	public NamedStream<?> getSharedStream(String name) throws InitializationException {
		/*
		 * This needs to be synchronized across all instances of this class, because
		 * it is possible that two competing threads may open two different streams
		 * to the same resource described by the logical name, even though, in the
		 * end only one will be registered with the registry and used.
		 */
		synchronized (NamedStreamsManagerImpl.class) {
			NamedStream<?> ns = SharedNamedStreamsRegistry.getStream(name, this);
			if (ns==null) {		
				NamedStreamDefinition def = getDefinition(name);
				ns = open(def);			
				registerSharedStream(ns);
			}
			return SharedNamedStreamsRegistry.getStream(name, this);
		}
	}

	@Override
	public void close() throws DataException {
		for (NamedStream<?> stream : streams.values()) {
			stream.close();
		}
		streams.clear();
		SharedNamedStreamsRegistry.releaseSharedStreams(this);
	}

	@Override
	public void registerStream(NamedStream<?> stream) {
		streams.put(stream.getName(), stream);
	}

	@Override
	public void registerSharedStream(NamedStream<?> stream) {
		SharedNamedStreamsRegistry.register(stream.getName(), stream, this);
	}

	@Override
	public void registerStreamDefinition(NamedStreamDefinition definition) {
		properties.setProperty(definition.getName(), definition.getSpecsString());
	}

	@Override
	public NamedStream<?> convert(String nameOfStreamToConvert, StreamType typeOfNewStream, String nameOfNewStream)
	throws DataException {
		try {
			NamedStream<?> ns = getStream(nameOfStreamToConvert);
			StreamResource resourceType = ns.getResourceType();
			NamedStreamFactory factory = resourceType.getFactory();
			NamedStream<?> converted = factory.convert(ns, typeOfNewStream, nameOfNewStream);
			registerStream(converted);
			return converted;
		} catch (CouldNotConvertNamedStreamException cncnsex) {			
			throw new DataException(cncnsex);
		} catch (InitializationException iex) {			
			throw new DataException(iex);
		}
	}
}