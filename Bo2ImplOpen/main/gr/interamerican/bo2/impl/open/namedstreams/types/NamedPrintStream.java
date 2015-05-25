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
	 * @param uri 
	 */
	public NamedPrintStream(
			StreamResource resourceType, PrintStream stream, 
			String name, Object resource, Charset encoding, String uri) {
		super(StreamType.PRINTSTREAM, resourceType, stream, name, 0, resource, encoding, uri);
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
	public void writeRecord(byte[] record) {
		stream.println(new String(record, encoding));
	}

	@Override
	public void writeString(String string) {
		stream.println(string);
	}

	@Override
	public void close() throws DataException {
		stream.close();
	}
	
}
