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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Stream types.
 */
public enum StreamType {
	/**
	 * Input stream.
	 */
	INPUTSTREAM(InputStream.class),
	
	/**
	 * Output stream.
	 */
	OUTPUTSTREAM(OutputStream.class),
	
	/**
	 * Buffered reader.
	 */
	BUFFEREDREADER(BufferedReader.class),
	
	/**
	 * Print stream.
	 */
	PRINTSTREAM(PrintStream.class);
	
	/**
	 * Class of stream.
	 */
	private Class<?> streamType;
	
	/**
	 * Creates a new StreamType object. 
	 *
	 * @param streamType
	 */
	private StreamType(Class<?> streamType) {
		this.streamType = streamType;
	}

	/**
	 * Checks that this stream is supported by the type.
	 * 
	 * @param stream 
	 *        Stream to check.
	 *         
	 * @return Returns true if the stream is supported by the type.
	 */
	boolean isOk(Object stream) {
		if (stream==null) {
			return false;
		}
		Class<?> streamClass = stream.getClass();
		return streamType.isAssignableFrom(streamClass);
	}
	
}
