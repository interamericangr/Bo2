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

import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResource;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

/**
 * Factory for NamedStreams.
 */
public class NamedStreamFactoryUtil {




	











	

	



	

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
	
	/**
	 * Creates a NamedInputStream for a url resource over HTTP.
	 * 
	 * @param def
	 * @return NamedInputStream
	 * @throws InitializationException
	 */
	public static NamedInputStream httpInputStream(NamedStreamDefinition def) throws InitializationException {
		return new NamedInputStream(StreamResource.HTTP, httpStream(def.getUri()), def.getName(), def.getRecordLength(), null, def.getEncoding());
	}
	
	/**
	 * Creates a NamedBufferedReader for a url resource over HTTP.
	 * 
	 * @param def
	 * @return NamedBufferedReader
	 * @throws InitializationException
	 */
	public static NamedBufferedReader httpBufferedReader(NamedStreamDefinition def) throws InitializationException {
		InputStreamReader insr = new InputStreamReader(httpStream(def.getUri()), def.getEncoding());
		BufferedReader br = new BufferedReader(insr);
		return new NamedBufferedReader(StreamResource.HTTP, br, def.getName(), null, def.getEncoding());
	}
	
	/**
	 * Opens an InputStream to an HTTP url.
	 * 
	 * @param httpUrl
	 * @return stream
	 * @throws InitializationException
	 */
	static InputStream httpStream(String httpUrl) throws InitializationException {
		try {
			URL url = new URL(httpUrl);
			URLConnection connection = url.openConnection();
			return connection.getInputStream();
		} catch (IOException ioex) {
			throw new InitializationException("Error loading stream from " + httpUrl, ioex); //$NON-NLS-1$
		}
		
	}

}
