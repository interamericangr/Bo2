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
import gr.interamerican.bo2.impl.open.utils.Exceptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;


/**
 * Implementation of NamedStream for BufferedReader.
 */
public class NamedBufferedReader extends AbstractNamedStream<BufferedReader> {
	
	/**
	 * Last line read. Used in case of a {@link SocketTimeoutException} 
	 * when consuming a stream over a network socket.
	 */
	private String lastLine = null;
	
	/**
	 * Creates a new NamedBufferedReader object.
	 * 
	 * @param resourceType
	 * @param stream
	 *        BufferedReader using the user defined encoding.
	 * @param name
	 * @param resource 
	 * @param encoding 
	 */
	public NamedBufferedReader(
			StreamResource resourceType, BufferedReader stream, 
			String name, Object resource, Charset encoding) {
		super(StreamType.BUFFEREDREADER, resourceType, stream, name, 0, resource, encoding);
	}

	public boolean find(byte[] key) 
	throws DataException, DataOperationNotSupportedException {
		throw Exceptions.dataOperationNotSupported(getStream());
	}

	public byte[] readRecord() 
	throws DataException {
		String record=readString();
		if (record==null) {
			return null;
		}
		/*
		 * We have to specify the encoding here. The String#getBytes()
		 * call uses the default platform charset.
		 */
		return record.getBytes(encoding);
	}
	
	public String readString() 
	throws DataException {
		try {
			/*
			 * This BufferedReader should already have the user defined encoding.
			 */
			String record=stream.readLine();
			lastLine = record;
			return record;
		} catch (IOException e) {
			throw new DataException(e.getClass().getSimpleName() + "caught. Last line read: " + lastLine, e); //$NON-NLS-1$
		}        
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
