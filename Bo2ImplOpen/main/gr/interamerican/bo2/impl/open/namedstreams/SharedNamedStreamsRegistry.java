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
import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.bo2.utils.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registry for shared named streams.
 */
public class SharedNamedStreamsRegistry {
	
	/**
	 * Logger.
	 */
	static final Logger LOG = LoggerFactory.getLogger(SharedNamedStreamsRegistry.class.getName());

	/**
	 * Name to stream.
	 */
	static Map<String, NamedStream<?>> name2stream = new HashMap<String, NamedStream<?>>();
	
	/**
	 * Stream to name.
	 */
	static Map<NamedStream<?>, String> stream2name = new HashMap<NamedStream<?>, String>();
	
	/**
	 * Map keeping track of which {@link NamedStreamsProvider}s access a specific {@link NamedStream}.
	 */
	static Map<NamedStream<?>, Set<NamedStreamsProvider>> providersAccessingStream = new HashMap<NamedStream<?>, Set<NamedStreamsProvider>>();

	/**
	 * Registers a stream with its name. The {@link NamedStreamsProvider} performing the
	 * registration is added to the providers accessing it. If the stream name is already
	 * registered this invocation should be effectively a no-op.
	 * 
	 * @param name
	 * @param stream
	 * @param nsp 
	 */
	@SuppressWarnings("nls")
	public static synchronized void register(String name, NamedStream<?> stream, NamedStreamsProvider nsp) {
		NamedStream<?> possiblyExistingStream = name2stream.get(name);
		if(possiblyExistingStream!=null && possiblyExistingStream!=stream) {
			String msg = StringUtils.concat(
				"Two different stream instances, ",
				possiblyExistingStream + " and " + stream,
				"have been opened for logical name " + name);
			throw new RuntimeException(msg);
		}
		if(!name2stream.containsKey(name)) {
			LOG.debug("Registered shared stream with name " + name);
			name2stream.put(name, stream);
			stream2name.put(stream, name);
		}
		if(!providersAccessingStream.containsKey(stream)) {
			providersAccessingStream.put(stream, new HashSet<NamedStreamsProvider>());
		}
		Set<NamedStreamsProvider> providersOfStream = providersAccessingStream.get(stream);
		providersOfStream.add(nsp);
		String msg = stream.getName() + " [+] now shared by " + providersOfStream.size();
		LOG.debug(msg);
	}
	
	/**
	 * Gets a stream with its name. The {@link NamedStreamsProvider} requesting the
	 * stream is added to the providers accessing it. If the stream does not exist,
	 * null is returned. This should notify the provider, that he must create and
	 * register the stream himself.
	 * 
	 * @param name
	 * @param nsp 
	 * 
	 * @return Returns the stream registered with the specified name.
	 */
	public static synchronized NamedStream<?> getStream(String name, NamedStreamsProvider nsp) {
		NamedStream<?> result = name2stream.get(name);
		if(result!=null) {
			providersAccessingStream.get(result).add(nsp);
		}
		return result;
	}
	
	/**
	 * Releases the shared streams accessed by the specified {@link NamedStreamsProvider}.
	 * If this was the last provider accessing the stream, the stream is unregistered and
	 * closed.
	 *  
	 * @param nsp
	 * @throws DataException
	 */
	@SuppressWarnings("nls")
	public static synchronized void releaseSharedStreams(NamedStreamsProvider nsp) throws DataException {
		Set<NamedStream<?>> releasedStreams = new HashSet<NamedStream<?>>();
		for(Map.Entry<NamedStream<?>, Set<NamedStreamsProvider>> entry : providersAccessingStream.entrySet()) {
			NamedStream<?> stream = entry.getKey();
			Set<NamedStreamsProvider> providers = entry.getValue();
			providers.remove(nsp);
			String msg = stream.getName() + " [-] now shared by " + providers.size();
			LOG.debug(msg);
			if(providers.isEmpty()) {
				String name = stream2name.remove(stream);
				name2stream.remove(name);
				LOG.debug(name + " closed\n" + ExceptionUtils.getThrowableStackTrace(new Throwable("This is not an error. It's for debugging purposes.")));
				stream.close();
				releasedStreams.add(stream);
			}
		}
		for(NamedStream<?> ns : releasedStreams) {
			providersAccessingStream.remove(ns);
		}
	}

	/**
	 * Hidden constructor.
	 */
	private SharedNamedStreamsRegistry() { /* hidden */ }

}
