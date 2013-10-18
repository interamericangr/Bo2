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
import gr.interamerican.bo2.impl.open.namedstreams.AbstractNamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.StreamResource;
import gr.interamerican.bo2.impl.open.namedstreams.StreamType;

import java.nio.charset.Charset;

/**
 * Mock {@link AbstractNamedStream}.
 * @param <T> 
 * 
 * Not only instances of this class are used by the tests, but also
 * the class itself. This is why it can't be removed and replaced
 * by mock objects created by a mocking framework.
 */
public class MockNamedStream<T> extends AbstractNamedStream<T> {

	
	/**
	 * Creates a new MockNamedStream object. 
	 *
	 * @param streamType
	 * @param resourceType
	 * @param stream
	 * @param name
	 * @param recordLength
	 * @param encoding 
	 */
	public MockNamedStream(StreamType streamType, StreamResource resourceType, T stream, String name, int recordLength, Charset encoding) {
		super(streamType, resourceType, stream, name, recordLength, stream, encoding);
	}
	
	
	@Override
	protected void validate() {
		/* no checks */
	}

	public byte[] readRecord() throws DataException, DataOperationNotSupportedException {
		return null;
	}
	
	public String readString() throws DataException, DataOperationNotSupportedException {
		return null;
	}

	
	public void writeRecord(byte[] record) throws DataException, DataOperationNotSupportedException {
		/* empty */		
	}
	
	public void writeString(String string) throws DataException, DataOperationNotSupportedException {
		/* empty */				
	}

	public boolean find(byte[] key) throws DataException, DataOperationNotSupportedException {
		return false;
	}

	public void close() throws DataException, DataOperationNotSupportedException {
		/* empty */				
	}

}
