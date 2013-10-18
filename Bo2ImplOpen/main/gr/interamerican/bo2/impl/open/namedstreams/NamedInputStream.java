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
import gr.interamerican.bo2.arch.exceptions.DataOperationNotSupportedException;
import gr.interamerican.bo2.impl.open.utils.Exceptions;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Implementation of NamedStream for InputStream.
 * 
 *
 */
public class NamedInputStream extends AbstractNamedStream<InputStream> {
	
	/**
	 * Creates a new NamedInputStream object.
	 * 
	 * @param resourceType
	 * @param stream
	 * @param name
	 * @param recordLength
	 * @param resource 
	 * @param encoding 
	 */
	NamedInputStream(
			StreamResource resourceType, InputStream stream, 
			String name, int recordLength, Object resource, Charset encoding) {
		super(StreamType.INPUTSTREAM, resourceType, stream, name, recordLength, resource, encoding);
	}
	

	public boolean find(byte[] key) 
	throws DataException, DataOperationNotSupportedException {
		throw Exceptions.dataOperationNotSupported(stream);
	}

	public byte[] readRecord() 
	throws DataException {
		try {
			byte[] record=new byte[recordLength];
			int byteCount=stream.read(record);
			if (byteCount<=0) {
			    return null;
			} else {
			    return record;
			}
		} catch (IOException e) {
			throw new DataException(e);
		}        
	}

	public String readString() 
	throws DataException {
		byte[] rec=readRecord();
		if (rec==null) return null;
		/*
		 * Use the user defined encoding to convert the bytes to a String.
		 */
		return new String(rec, encoding);
	}
	
	public void writeRecord(byte[] record) 
	throws DataException, DataOperationNotSupportedException {
		throw Exceptions.dataOperationNotSupported(stream);	
	}

	public void writeString(String string) 
	throws DataException, DataOperationNotSupportedException {
		throw Exceptions.dataOperationNotSupported(stream);
	}

	public void close() throws DataException {
		try {
			stream.close();
		} catch (IOException e) {
			throw new DataException(e);
		}
	}
	
}
