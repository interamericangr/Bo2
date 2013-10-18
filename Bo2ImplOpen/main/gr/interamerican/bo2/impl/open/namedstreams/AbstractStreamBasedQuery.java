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
import gr.interamerican.bo2.impl.open.utils.Exceptions;
import gr.interamerican.bo2.impl.open.utils.Messages;
import gr.interamerican.bo2.impl.open.workers.AbstractResourceConsumer;

/**
 * Basic query that browses a sequential file.
 */
public abstract class AbstractStreamBasedQuery 
extends AbstractResourceConsumer 
implements Query {
	
	/**
	 * stream name.
	 */
	private String streamName;
	
	/**
	 * stream.
	 */
	protected NamedStream<?> stream;
	
	public boolean isAvoidLock() {		
		return true;
	}

	public void setAvoidLock(boolean avoidLock) {/* ignore it */}


	@Override
	public void init(Provider parent) throws InitializationException {		
		super.init(parent);
		NamedStreamsProvider provider = getResource(NamedStreamsProvider.class);
		stream = provider.getStream(streamName);
		if (stream==null) {
			throw Exceptions.runtime(Messages.STREAM_NOT_FOUND, streamName);
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

}
