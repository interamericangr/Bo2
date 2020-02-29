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
package gr.interamerican.bo2.impl.open.runtime.concurrent;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedPrintStream;
import static gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessConstants.*;

/**
 * Utility class to help create stream names.
 */
class SharedStreams {
	
	/**
	 * Gets the stream name of the successes stream.
	 *
	 * @param prefix the prefix
	 * @param postFix the post fix
	 * @return Gets the stream name of the successes stream.
	 */
	private static final String streamName(String prefix, String postFix) {
		return prefix.trim() + postFix;		
	}
	
	/**
	 * Gets the stream from the specified provider, using the
	 * specified prefix and postfix.
	 *
	 * @param p        Provider.
	 * @param prefix        Prefix of stream name.
	 * @param postfix        Postfix of stream name.
	 *        
	 * @return Returns the stream.
	 * @throws InitializationException the initialization exception
	 */
	private static NamedPrintStream stream(Provider p, String prefix, String postfix) 
	throws InitializationException {		
		NamedStreamsProvider nsp = p.getResource(LOGFILES_PROVIDER_NAME, NamedStreamsProvider.class);	
		String logname = streamName(prefix, postfix); 
		return (NamedPrintStream) nsp.getSharedStream(logname);
	}
	
	/**
	 * Gets the successes stream from the specified provider, using the
	 * specified stream name prefix.
	 *
	 * @param p        Provider.
	 * @param prefix        Prefix of stream name.
	 *        
	 * @return Returns the stream.
	 * @throws InitializationException the initialization exception
	 */
	public static NamedPrintStream successes(Provider p, String prefix) 
	throws InitializationException {		
		return stream(p, prefix, SUCCESSES);
	}
	
	/**
	 * Gets the successes stream from the specified provider, using the
	 * specified stream name prefix.
	 *
	 * @param p        Provider.
	 * @param prefix        Prefix of stream name.
	 *        
	 * @return Returns the stream.
	 * @throws InitializationException the initialization exception
	 */
	public static NamedPrintStream failures(Provider p, String prefix) 
	throws InitializationException {		
		return stream(p, prefix, FAILURES);
	}
	
	/**
	 * Gets the stacktraces stream from the specified provider, using the
	 * specified stream name prefix.
	 *
	 * @param p        Provider.
	 * @param prefix        Prefix of stream name.
	 *        
	 * @return Returns the stream.
	 * @throws InitializationException the initialization exception
	 */
	public static NamedPrintStream stacktraces(Provider p, String prefix) 
	throws InitializationException {		
		return stream(p, prefix, STACKTRACES);
	}
	
	/**
	 * Gets the stacktraces stream from the specified provider, using the
	 * specified stream name prefix.
	 * 
	 * @param p
	 *        Provider.
	 * @param prefix
	 *        Prefix of stream name.
	 *        
	 * @return Returns the stream. If an InitializationException occurs during\
	 *         stream initialization, then returns null.
	 */
	public static NamedPrintStream optionalStacktraces(Provider p, String prefix) {
		try {
			return stacktraces(p, prefix);
		} catch (InitializationException e) {
			return null;
		}
	}
	
	/**
	 * Gets the stream with the specified name from the specified provider.
	 *
	 * @param p        Provider.
	 * @param streamName        stream name.
	 *        
	 * @return Returns the stream.
	 * @throws InitializationException the initialization exception
	 */
	public static NamedStream<?> sharedStream(Provider p, String streamName) 
	throws InitializationException {		
		NamedStreamsProvider nsp = p.getResource(LOGFILES_PROVIDER_NAME, NamedStreamsProvider.class);
		return nsp.getSharedStream(streamName);
	}


	

}
