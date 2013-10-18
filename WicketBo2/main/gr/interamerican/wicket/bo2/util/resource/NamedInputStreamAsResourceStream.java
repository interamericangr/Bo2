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
package gr.interamerican.wicket.bo2.util.resource;

import gr.interamerican.bo2.arch.ResourceWrapper;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.NamedInputStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketRequestCycle;

import java.io.IOException;
import java.io.InputStream;

import org.apache.wicket.util.resource.AbstractResourceStream;

/**
 * InputStreamAsResourceStream is a simple AbstractResourceStream
 * that takes an InputStream as argument to its constructor.
 */
public class NamedInputStreamAsResourceStream 
extends AbstractResourceStream {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * InputStream.
	 */
	NamedInputStream nis;

	/**
	 * Creates a new NamedInputStreamAsResourceStream object. 
	 *
	 * @param managerName
	 *        Name of {@link ResourceWrapper}. 
	 * @param streamName
	 *        Logical name of named stream.
	 */
	public NamedInputStreamAsResourceStream(String managerName, String streamName) {
		super();
		try {
			NamedStreamsProvider nsp = Bo2WicketRequestCycle.provider().getResource
				(managerName, NamedStreamsProvider.class);
			nis = (NamedInputStream) nsp.getStream(streamName);
		} catch (InitializationException e) {
			throw new RuntimeException(e);
		}

	}

	public InputStream getInputStream() {
		return nis.getStream();
	}

	public void close() throws IOException {
		try {
			nis.close();
		} catch (DataException e) {
			IOException ioe = ExceptionUtils.causeInTheChain(e, IOException.class);
			if (ioe!=null) {
				throw ioe;
			}
			throw new IOException(e.toString());
		}		
	}

}
