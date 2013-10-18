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

import gr.interamerican.bo2.arch.ResourceWrapper;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;

/**
 * Provider of named streams.
 * 
 * This provider provides a map with {@link NamedStream} objects.
 */
public interface NamedStreamsProvider extends ResourceWrapper {
	
	/**
	 * Gets a named stream with a specified name.
	 * 
	 * @param name name of the stream.
	 * 
	 * @return returns the stream with the specified name.
	 * 
	 * @throws InitializationException 
	 *         When the provider fails to return the stream.
	 */
	public NamedStream<?> getStream(String name)
	throws InitializationException;
	
	/**
	 * A global stream is a stream that is shared by more than one {@link NamedStreamsProvider}
	 * instances. These streams may be closed when all NamedStreamsProvider instances accessing
	 * them have #close() invoked.
	 * 
	 * @param name
	 *        Stream name.
	 *        
	 * @return Returns the stream.
	 * @throws InitializationException
	 */
	public NamedStream<?> getSharedStream(String name) 
	throws InitializationException;
	
	/**
	 * Registers the specified {@link NamedStream} on this 
	 * Provider.
	 * 
	 * @param stream
	 *        Stream to register.
	 */
	public void registerStream(NamedStream<?> stream);
	
	/**
	 * Registers the specified {@link NamedStream} as a shared stream. 
	 * This provider is the first to have access to the shared stream.
	 * 
	 * @param stream
	 *        Stream to register.
	 */
	public void registerSharedStream(NamedStream<?> stream);
	
	/**
	 * Creates a new stream by converting the stream with the specified name
	 * that is managed by this {@link NamedStreamsProvider} to the specified
	 * {@link StreamType}.
	 * 
	 * The new named stream is automatically registered to this NamedStreamsProvider.
	 *   
	 * @param nameOfStreamToConvert
	 *        Name of the stream that will be converted.
	 * @param typeOfNewStream
	 *        Type of the new NamedStream. 
	 * @param nameOfNewStream
	 *        Name of the new stream. 
	 * 
	 * @return Returns a new NamedStream with the specified name of new stream.
	 * 
	 * @throws DataException
	 *         <li>If this NamedStreamsProvider does not support the specified conversion.</li>
	 *         <li>If this NamedStreamsProvider does not manage a stream with the specified name.</li>
	 *         
	 *  
	 */
	public NamedStream<?> convert(String nameOfStreamToConvert, StreamType typeOfNewStream, String nameOfNewStream)
	throws DataException;
	
}
