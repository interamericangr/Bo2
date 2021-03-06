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
package gr.interamerican.bo2.samples.implopen.mocks;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.DataOperationNotSupportedException;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResource;
import gr.interamerican.bo2.impl.open.namedstreams.types.AbstractNamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;

import java.nio.charset.Charset;

/**
 * Mock {@link AbstractNamedStream}.
 * @param <T> 
 * 
 * Not only instances of this class are used by the tests, but also
 * the class itself. This is why it can't be removed and replaced
 * by mock objects created by a mocking framework.
 */
public class MockNamedStream<T>
extends AbstractNamedStream<T> {
	
	/**
	 * Creates a new MockNamedStream object. 
	 *
	 * @param streamType the stream type
	 * @param resourceType the resource type
	 * @param stream the stream
	 * @param name the name
	 * @param recordLength the record length
	 * @param encoding the encoding
	 * @param uri the uri
	 */
	public MockNamedStream(StreamType streamType, StreamResource resourceType, T stream, String name, int recordLength, Charset encoding, String uri) {
		super(streamType, resourceType, stream, name, recordLength, stream, encoding, uri);
	}

	@Override
	protected void validate() {
		/* no checks */
	}

	@Override
	public byte[] readRecord() throws DataException, DataOperationNotSupportedException {
		return null;
	}

	@Override
	public String readString() throws DataException, DataOperationNotSupportedException {
		return null;
	}

	@Override
	public void writeRecord(byte[] record) throws DataException, DataOperationNotSupportedException {
		/* empty */		
	}

	@Override
	public void writeString(String string) throws DataException, DataOperationNotSupportedException {
		/* empty */				
	}

	@Override
	public boolean find(byte[] key) throws DataException, DataOperationNotSupportedException {
		return false;
	}


	@Override
	public void close() throws DataException, DataOperationNotSupportedException {
		/* empty */				
	}
}