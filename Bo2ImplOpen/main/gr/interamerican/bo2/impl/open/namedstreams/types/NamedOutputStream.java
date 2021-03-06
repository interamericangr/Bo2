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
package gr.interamerican.bo2.impl.open.namedstreams.types;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.DataOperationNotSupportedException;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResource;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;


/**
 * Implementation of NamedStream for OutputStream.
 * 
 *
 */
public class NamedOutputStream extends AbstractNamedStream<OutputStream> {
	
	/**
	 * Creates a new NamedOutputStream object.
	 *
	 * @param resourceType the resource type
	 * @param stream the stream
	 * @param name the name
	 * @param recordLength the record length
	 * @param resource the resource
	 * @param encoding the encoding
	 * @param uri the uri
	 */
	public NamedOutputStream(
			StreamResource resourceType, OutputStream stream, 
			String name, int recordLength, Object resource, Charset encoding, String uri) {
		super(StreamType.OUTPUTSTREAM, resourceType, stream, name, recordLength, resource, encoding, uri);
	}

	@Override
	public boolean find(byte[] key) 
	throws DataException, DataOperationNotSupportedException {
		throw new DataOperationNotSupportedException();
	}
	
	@Override
	public byte[] readRecord() 
	throws DataException {
		throw new DataOperationNotSupportedException();
	}
	
	@Override
	public String readString() 
	throws DataException {
		throw new DataOperationNotSupportedException();
	}
	
	@Override
	public void writeRecord(byte[] record) 
	throws DataException, DataOperationNotSupportedException {
		try {
			stream.write(record);
		} catch (IOException e) {
			throw new DataException(e);
		}	
	}

	@Override
	public void writeString(String string) 
	throws DataException, DataOperationNotSupportedException {
		writeRecord(string.getBytes(encoding));
	}

	@Override
	public void close() throws DataException {
		try {
			stream.close();
		} catch (IOException e) {
			throw new DataException(e);
		}
	}
	
}
