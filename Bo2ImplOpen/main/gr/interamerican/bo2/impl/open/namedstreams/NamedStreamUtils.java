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

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.InitializationException;

/**
 * Utilities for NamedStreams.
 * 
 * 
 * 
 * 
	 * Register:
	 *    InputStream etc
	 *    byte[]
	 *    
	 *    byte[] from Input to Output and vice versa
	 *    File[] from Input to Output and vice versa
	 *    
	 *    copyStream (old name, new name)
	 *    NamedStream transform(NamedStream, StreamType);
 * 
 */
public class NamedStreamUtils {
	
	/**
	 * Registers a stream in a {@link NamedStreamsProvider} that it finds
	 * in a Provider.
	 * 
	 * This operation can be used to make a NamedStreamsProvider aware of
	 * a stream that was created during runtime, or that was opened by
	 * another NamedStreamsProvider.
	 * 
	 * @param stream
	 *        Stream to register.
	 * @param provider
	 *        Provider.
	 * @param managerName
	 *        Manager name.
	 *        
	 * @throws InitializationException
	 */
	public static void registerStream
	(NamedStream<?> stream, Provider provider, String managerName) 
	throws InitializationException {
		NamedStreamsProvider nsp = provider.getResource(managerName, NamedStreamsProvider.class);
		nsp.registerStream(stream);
	}

}
