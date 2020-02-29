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
import gr.interamerican.bo2.arch.Query;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.workers.AbstractResourceConsumer;
import gr.interamerican.bo2.utils.StringUtils;

/**
 * Abstract base for queries based on a NamedStream. <br>
 * 
 * This class serves a base for queries based on named stream
 * regardless of the type of stream (text or binary).  
 */
public abstract class AbstractStreamBasedQuery 
extends AbstractResourceConsumer 
implements Query {
	
	/**
	 * stream name.
	 */
	String streamName;
	
	/**
	 * stream.
	 */
	protected NamedStream<?> stream;
	
	/**
	 * Indicates if the underlying stream should be opened as a shared stream.
	 */
	boolean isSharedStream = false;
	
	@Override
	public boolean isAvoidLock() {		
		return true;
	}

	@Override
	public void setAvoidLock(boolean avoidLock) {/* ignore it */}


	@Override
	public void init(Provider parent) throws InitializationException {		
		super.init(parent);
		NamedStreamsProvider provider = getResource(NamedStreamsProvider.class);
		if(isSharedStream) {
			stream = provider.getSharedStream(streamName);
		} else {
			stream = provider.getStream(streamName);
		}
		
		if (stream==null) {
			String manager = getManagerName();
			@SuppressWarnings("nls")
			String msg = StringUtils.concat(
				"Stream ", streamName,
				" not registered with manager " , manager);
			throw new InitializationException(msg);			
		}
	}

	/**
	 * Gets the logical name of the stream.
	 * 
	 * @return the logical name of the stream.
	 */
	public String getStreamName() {
		return streamName;
	}

	/**
	 * Sets the logical name of the stream.
	 * 
	 * @param streamName the new logical name of the stream.
	 */
	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}
	
	/**
	 * Assigns a new value to the isSharedStream. The value indicates if the underlying stream 
	 * should be opened as a shared stream.
	 *
	 * @param isSharedStream the isSharedStream to set
	 */
	public void setSharedStream(boolean isSharedStream) {
		this.isSharedStream = isSharedStream;
	}

}
