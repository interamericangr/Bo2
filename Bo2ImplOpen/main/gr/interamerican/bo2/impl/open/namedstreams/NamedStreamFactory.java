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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Factory for NamedStreams.
 */
public class NamedStreamFactory {

	/**
	 * Creates a new NamedInputStream that wraps a file.
	 * 
	 * The specified file provides the underlying resource of the NamedStream.
	 * 
	 * @param file
	 *        File.
	 * @param name
	 *        Stream name.
	 * @param recordLength
	 *        Record length.
	 * @param encoding
	 *        Encoding (if applicable).
	 * 
	 * @return Returns the NamedInputStream.
	 * @throws FileNotFoundException
	 */
	public static NamedInputStream input(File file, String name, int recordLength, Charset encoding)
			throws FileNotFoundException {
		InputStream in = new FileInputStream(file);
		return new NamedInputStream(StreamResource.FILE, in, name, recordLength, file, encoding);
	}

	/**
	 * Creates a new NamedInputStream that wraps an input stream.
	 * 
	 * The specified stream provides the underlying resource of the NamedStream.
	 * 
	 * @param in
	 *        Stream.
	 * @param name
	 *        Stream name.
	 * @param recordLength
	 *        Record length.
	 * @param encoding
	 *        Encoding (if applicable).
	 * 
	 * @return Returns the NamedInputStream.
	 */
	public static NamedInputStream input(InputStream in, String name, int recordLength, Charset encoding) {
		return new NamedInputStream(StreamResource.OBJECT, in, name, recordLength, in, encoding);
	}

	/**
	 * Creates a new NamedInputStream that wraps an input stream that reads
	 * a byte array.
	 * 
	 * The specified byte array provides the underlying resource of the NamedStream.
	 * 
	 * @param buffer
	 *        Byte array providing data to the stream.
	 * @param name
	 *        Stream name.
	 * @param recordLength
	 *        Record length.
	 * @param encoding
	 *        Encoding (if applicable).
	 * 
	 * @return Returns the NamedInputStream.
	 */
	public static NamedInputStream input(byte[] buffer, String name, int recordLength, Charset encoding) {
		ByteArrayInputStream in = new ByteArrayInputStream(buffer);
		return new NamedInputStream(StreamResource.BYTES, in, name, recordLength, buffer, encoding);
	}

	/**
	 * Creates a new NamedOutputStream that wraps a file.
	 * 
	 * The specified file provides the underlying resource of the NamedStream.
	 * 
	 * @param file
	 *        File.
	 * @param name
	 *        Stream name.
	 * @param recordLength
	 *        Record length.
	 * @param encoding
	 *        Encoding (if applicable).
	 * 
	 * @return Returns the NamedOutputStream.
	 * 
	 * @throws FileNotFoundException
	 */
	public static NamedOutputStream output(File file, String name, int recordLength, Charset encoding)
			throws FileNotFoundException {
		OutputStream out = new FileOutputStream(file);
		return new NamedOutputStream(StreamResource.FILE, out, name, recordLength, file, encoding);
	}

	/**
	 * Creates a new NamedOutputStream that wraps an output stream.
	 * 
	 * The specified output stream provides the underlying resource of the NamedStream.
	 * 
	 * @param out
	 *        out.
	 * @param name
	 *        Stream name.
	 * @param recordLength
	 *        Record length.
	 * @param encoding
	 *        Encoding (if applicable).
	 * 
	 * @return Returns the NamedOutputStream.
	 */
	public static NamedOutputStream output(OutputStream out, String name, int recordLength, Charset encoding) {
		return new NamedOutputStream(StreamResource.OBJECT, out, name, recordLength, out, encoding);
	}

	/**
	 * Creates a new NamedOutputStream that wraps an output stream that
	 * writes in memory.
	 * 
	 * The output stream provides the underlying resource of the NamedStream.
	 *
	 * @param name
	 *        Stream name.
	 * @param recordLength
	 *        Record length.
	 * @param encoding
	 *        Encoding (if applicable).
	 * 
	 * @return Returns the NamedOutputStream.
	 */
	public static NamedOutputStream output(String name, int recordLength, Charset encoding) {
		OutputStream out = new ByteArrayOutputStream();
		return new NamedOutputStream(StreamResource.BYTES, out, name, recordLength, out, encoding);
	}

	/**
	 * Creates a new NamedBufferedReader that wraps a file.
	 * 
	 * The specified file provides the underlying resource of the NamedStream.
	 * 
	 * The file is read using the user-defined encoding in the Bo2 configuration.
	 * 
	 * @param file
	 *        File.
	 * @param name
	 *        Stream name.
	 * @param encoding
	 *        Encoding (if applicable).
	 * 
	 * @return Returns the NamedBufferedReader.
	 * 
	 * @throws IOException
	 */
	public static NamedBufferedReader reader(File file, String name, Charset encoding)
			throws IOException {
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader insr = new InputStreamReader(fis, encoding);
		BufferedReader br = new BufferedReader(insr);
		return new NamedBufferedReader(StreamResource.FILE, br, name, file, encoding);
	}

	/**
	 * Creates a new NamedBufferedReader that wraps a buffered reader.
	 * 
	 * The specified reader provides the underlying resource of the NamedStream.
	 * 
	 * It is the responsibility of the developer that uses this method to use
	 * the user-defined encoding from the Bo2 configuration.
	 * 
	 * @param br
	 *        Stream.
	 * @param name
	 *        Stream name.
	 * @param encoding
	 * 
	 * @return Returns the NamedBufferedReader.
	 */
	public static NamedBufferedReader reader(BufferedReader br, String name, Charset encoding) {
		return new NamedBufferedReader(StreamResource.OBJECT, br, name, br, encoding);
	}

	/**
	 * Creates a new NamedInputStream that wraps an input stream that reads
	 * a byte array.
	 * 
	 * The specified byte array provides the underlying resource of the NamedStream.
	 * 
	 * @param buffer
	 *        Byte array providing data to the stream.
	 * @param name
	 *        Stream name.
	 * @param encoding
	 *        Encoding (if applicable).
	 * 
	 * @return Returns the NamedInputStream.
	 */
	public static NamedBufferedReader reader(byte[] buffer, String name, Charset encoding) {
		ByteArrayInputStream in = new ByteArrayInputStream(buffer);
		InputStreamReader inr = new InputStreamReader(in, encoding);
		BufferedReader br = new BufferedReader(inr);
		return new NamedBufferedReader(StreamResource.BYTES, br, name, buffer, encoding);
	}

	/**
	 * Creates a new NamedPrintStream that wraps a file.
	 * 
	 * The specified file provides the underlying resource of the NamedStream.
	 * 
	 * @param file
	 *        File.
	 * @param name
	 *        Stream name.
	 * @param encoding
	 *        Encoding (if applicable).
	 * 
	 * @return Returns the NamedOutputStream.
	 * 
	 * @throws IOException
	 */
	public static NamedPrintStream print(File file, String name, Charset encoding)
			throws IOException {
		PrintStream out = new PrintStream(file, encoding.name());
		return new NamedPrintStream(StreamResource.FILE, out, name, file, encoding);
	}

	/**
	 * Creates a new NamedPrintStream that wraps an output stream.
	 * 
	 * The specified output stream provides the underlying resource of the NamedStream.
	 * 
	 * @param out
	 *        out.
	 * @param name
	 *        Stream name.
	 * @param encoding
	 *        Encoding (if applicable).
	 * 
	 * @return Returns the NamedPrintStream.
	 */
	public static NamedPrintStream print(PrintStream out, String name, Charset encoding) {
		return new NamedPrintStream(StreamResource.OBJECT, out, name, out, encoding);
	}

	/**
	 * Creates a new NamedPrintStream that wraps an output stream that
	 * writes in memory.
	 * 
	 * The output stream provides the underlying resource of the NamedStream.
	 *
	 * @param name
	 *        Stream name.
	 * @param encoding
	 * 
	 * @return Returns the NamedPrintStream.
	 */
	public static NamedPrintStream print(String name, Charset encoding) {
		OutputStream out = new ByteArrayOutputStream();
		try {
			PrintStream print = new PrintStream(out, false, encoding.name());
			return new NamedPrintStream(StreamResource.BYTES, print, name, out, encoding);
		}catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Creates a new NamedPrintStream that wraps System.out.
	 * 
	 * System.out is the underlying resource of the NamedStream.
	 *
	 * @param name
	 *        Stream name.
	 * @param encoding
	 * 
	 * @return Returns the NamedPrintStream.
	 */
	public static NamedPrintStream sysout(String name, Charset encoding) {
		return systemStream(name, System.out, encoding);
	}

	/**
	 * Creates a new NamedPrintStream that wraps System.err.
	 * 
	 * System.err is the underlying resource of the NamedStream.
	 *
	 * @param name
	 *        Stream name.
	 * @param encoding
	 * 
	 * @return Returns the NamedPrintStream.
	 */
	public static NamedPrintStream syserr(String name, Charset encoding) {
		return systemStream(name, System.err, encoding);
	}

	/**
	 * TODO write me.
	 * 
	 * @param name
	 *            Stream name.
	 * @param stream
	 *            Stream
	 * @param encoding
	 * 
	 * @return Returns the NamedPrintStream.
	 */
	public static NamedPrintStream systemStream(String name, PrintStream stream, Charset encoding) {
		return new NamedPrintStream(StreamResource.SYSTEM, stream, name, stream, encoding);
	}

}
