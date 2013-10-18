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

import java.io.PrintStream;
import java.nio.charset.Charset;

/**
 * Implementation of NamedStream for InputStream.
 * 
 *
 */
public class NamedPrintStream extends AbstractNamedStream<PrintStream> {
	
	/**
	 * Creates a new NamedBufferedReader object.
	 * 
	 * @param resourceType
	 * @param stream
	 * @param name
	 * @param resource 
	 * @param encoding 
	 */
	NamedPrintStream(
			StreamResource resourceType, PrintStream stream, 
			String name, Object resource, Charset encoding) {
		super(StreamType.PRINTSTREAM, resourceType, stream, name, 0, resource, encoding);
	}
	
	public boolean find(byte[] key) 
	throws DataException, DataOperationNotSupportedException {
		throw Exceptions.dataOperationNotSupported(stream);
	}

	public byte[] readRecord() 
	throws DataException {
		throw Exceptions.dataOperationNotSupported(stream);
	}
	
	public String readString() 
	throws DataException {
		throw Exceptions.dataOperationNotSupported(stream);
	}

	public void writeRecord(byte[] record) {
		stream.println(new String(record, encoding));
	}

	public void writeString(String string) {
		stream.println(string);
	}

	public void close() throws DataException {
		stream.close();
	}
	
}
