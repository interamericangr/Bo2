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
package gr.interamerican.bo2.impl.open.operations;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;

/**
 * Abstract operation that uses a NamedStream. <br/>
 * 
 * @param <T> 
 *        Type of stream.
 */
public abstract class AbstractNamedStreamOperation<T> 
extends AbstractOperation {
	
	/**
	 * The named stream.
	 */
	protected NamedStream<T> stream;
	
	/**
	 * Stream name used to initialize the NamedStream.
	 */
	protected String streamName;
	
	/**
	 * Creates a new AbstractNamedStreamOperation object. 
	 *
	 * @param streamName
	 */
	protected AbstractNamedStreamOperation(String streamName) {
		super();
		this.streamName = streamName;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(Provider parent) throws InitializationException {	
		super.init(parent);
		NamedStreamsProvider nsp = getResource(NamedStreamsProvider.class);
    	stream = (NamedStream<T>) nsp.getStream(streamName);
	}
	
	

	

}
