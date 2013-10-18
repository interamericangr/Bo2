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
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.utils.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

/**
 * Implementation of {@link NamedStreamsProvider}. 
 */
public class NamedStreamsManagerImpl 
extends AbstractNamedStreamsManager {
	
	/**
	 * Creates a new NamedStreamsCreator that is reads input by a Properties
	 * object.
	 * 
	 * @param properties
	 *        Properties object with input for this NamedStreamCreator.
	 */
	public NamedStreamsManagerImpl(Properties properties) {
		super(properties);
	}
	
	public NamedStream<?> convert
	(String nameOfStreamToConvert, StreamType typeOfNewStream, String nameOfNewStream)
	throws DataException {
		try {
			NamedStream<?> ns = getStream(nameOfStreamToConvert);
			return convert(ns, typeOfNewStream, nameOfNewStream);			
		} catch (InitializationException e) {
			throw new DataException(e);
		}
		
	}

	@Override
	protected NamedStream<?> open(NamedStreamDefinition def) 
	throws InitializationException {
		StreamResource resourceType = def.getResourceType();
		switch (resourceType) {
		case SYSTEM:
			return openSystemStream(def);
		case CLASSPATH:
			return openClasspathStream(def);			
		case FILE:
			return openFileStream(def);
		case BYTES:
			return openInMemoryStream(def);		
			
		default:
			throw invalid("Invalid stream type in definition", def.getName()); //$NON-NLS-1$
		}
	}
	
	/**
	 * Creates a new NamedStream that has a new type.
	 * 
	 * @param streamToConvert
	 *        NamedStream to convert.
	 * @param nameOfNewStream
	 *        Name of the new stream.
	 * @param typeOfNewStream
	 *        Type of the new NamedStream.
	 * @return Returns the new NamedStream.
	 * 
	 * @throws DataException
	 */
	NamedStream<?> convertFileStream
	(NamedStream<?> streamToConvert, String nameOfNewStream, StreamType typeOfNewStream) 
	throws DataException {
		try {
			NamedStreamDefinition nsd = new NamedStreamDefinition();
			nsd.setName(nameOfNewStream);
			nsd.setRecordLength(streamToConvert.getRecordLength());
			nsd.setResourceType(streamToConvert.getResourceType());
			nsd.setType(typeOfNewStream);
			File file = (File) streamToConvert.getResource();
			String path = file.getPath();
			nsd.setUri(path);
			return createNameStreamForFile(nsd);
		} catch (IOException e) {
			throw new DataException(e);
		}
	}
	
	/**
	 * Creates a new NamedStream that has a new type.
	 * 
	 * @param streamToConvert
	 *        NamedStream to convert.
	 * @param nameOfNewStream
	 *        Name of the new stream.
	 * @param typeOfNewStream
	 *        Type of the new NamedStream.
	 * @return Returns the new NamedStream.
	 * 
	 * @throws DataException
	 */
	NamedStream<?> convertSystemStream
	(NamedStream<?> streamToConvert, String nameOfNewStream, StreamType typeOfNewStream) 
	throws DataException {
		if (!StreamType.PRINTSTREAM.equals(typeOfNewStream)) {
			throw notSupported(streamToConvert, typeOfNewStream);
		}
		NamedPrintStream nps = (NamedPrintStream) streamToConvert;
		return NamedStreamFactory.systemStream(nameOfNewStream, nps.getStream(), nps.getEncoding());
	}
	
	/**
	 * Creates a new NamedStream that has a new type.
	 * 
	 * @param streamToConvert
	 *        NamedStream to convert.
	 * @param nameOfNewStream
	 *        Name of the new stream.
	 * @param typeOfNewStream
	 *        Type of the new NamedStream.
	 * @return Returns the new NamedStream.
	 * 
	 * @throws DataException
	 */
	NamedStream<?> convertInMemoryStream
	(NamedStream<?> streamToConvert, String nameOfNewStream, StreamType typeOfNewStream) 
	throws DataException {
		StreamType type = streamToConvert.getType();
		switch (type) {
		case INPUTSTREAM:
		case BUFFEREDREADER:	
			return convertBytesNamedStream
				(streamToConvert, nameOfNewStream, typeOfNewStream);
		case OUTPUTSTREAM:
		case PRINTSTREAM:	
			return convertByteArrayOutputStream
				(streamToConvert, nameOfNewStream, typeOfNewStream);
		}
		throw notSupported(streamToConvert, typeOfNewStream);
	}
	
	/**
	 * Converts a NamedStream based on an array of bytes.
	 * 
	 * @param streamToConvert
	 *        NamedStream to convert.
	 * @param nameOfNewStream
	 *        Name of the new stream.
	 * @param typeOfNewStream
	 *        Type of the new NamedStream.
	 * @return Returns the new NamedStream.
	 * 
	 * @throws DataException
	 */
	NamedStream<?> convertBytesNamedStream
	(NamedStream<?> streamToConvert, String nameOfNewStream, StreamType typeOfNewStream) 
	throws DataException {
		byte[] bytes = (byte[]) streamToConvert.getResource();
		switch (typeOfNewStream) {
		case INPUTSTREAM:
			return NamedStreamFactory.input
				(bytes, nameOfNewStream, streamToConvert.getRecordLength(), streamToConvert.getEncoding());
		case BUFFEREDREADER:
			return NamedStreamFactory.reader(bytes, nameOfNewStream, streamToConvert.getEncoding());
		}
		throw notSupported(streamToConvert, typeOfNewStream);
	}
	
	/**
	 * Creates a new NamedStream that has a new type.
	 * 
	 * @param streamToConvert
	 *        NamedStream to convert.
	 * @param nameOfNewStream
	 *        Name of the new stream.
	 * @param typeOfNewStream
	 *        Type of the new NamedStream.
	 * @return Returns the new NamedStream.
	 * 
	 * @throws DataException
	 */
	NamedStream<?> convertByteArrayOutputStream
	(NamedStream<?> streamToConvert, String nameOfNewStream, StreamType typeOfNewStream) 
	throws DataException {
		ByteArrayOutputStream baos = (ByteArrayOutputStream) streamToConvert.getResource();
		byte[] bytes;
		switch (typeOfNewStream) {
		case INPUTSTREAM:
			bytes = baos.toByteArray();
			return NamedStreamFactory.input
				(bytes, nameOfNewStream, streamToConvert.getRecordLength(), streamToConvert.getEncoding());
		case BUFFEREDREADER:
			bytes = baos.toByteArray();
			return NamedStreamFactory.reader(bytes, nameOfNewStream, streamToConvert.getEncoding());
		case OUTPUTSTREAM:
			return new NamedOutputStream(
				StreamResource.BYTES, baos, nameOfNewStream, 
				streamToConvert.getRecordLength(), baos, streamToConvert.getEncoding());
		case PRINTSTREAM:
			PrintStream ps = new PrintStream(baos);
			return new NamedPrintStream(StreamResource.BYTES, ps, nameOfNewStream, baos, streamToConvert.getEncoding());			
		}
		throw notSupported(streamToConvert, typeOfNewStream);
	}
	
	/**
	 * Converts the specified NamedStream to a new type.
	 * 
	 * @param ns
	 *        NamedStream to convert.
	 * @param typeOfNewStream
	 *        Type of the new NamedStream.
	 * @param nameOfNewStream
	 *        Name of the new NamedStream.
	 *        
	 * @return Returns the new NamedStream.
	 * 
	 * @throws DataException
	 */
	NamedStream<?> convert
	(NamedStream<?> ns, StreamType typeOfNewStream, String nameOfNewStream) 
	throws DataException {
		NamedStream<?> newStream;
		StreamResource resourceType = ns.getResourceType();
		switch (resourceType) {
		case FILE:
			newStream = convertFileStream(ns, nameOfNewStream, typeOfNewStream);
			break;
		case SYSTEM:			
			newStream = convertSystemStream(ns, nameOfNewStream, typeOfNewStream);
			break;
		case BYTES:
			newStream = convertInMemoryStream(ns, nameOfNewStream, typeOfNewStream);
			break;
		default:
			throw notSupported(ns, typeOfNewStream);
		}
		registerStream(newStream);
		return newStream;
	}
	
	/**
	 * Creates a DataException for the case that a conversion
	 * is not supported.
	 * 
	 * @param stream
	 * @param type
	 * 
	 * @return Returns the DataException.
	 */
	DataException notSupported(NamedStream<?> stream, StreamType type) {
		@SuppressWarnings("nls")
		String msg = StringUtils.concat(
			"Can't convert the stream ", stream.getName(),
			" with type ", stream.getType().toString(),
			" and resource type ", stream.getResourceType().toString(),
			" to type ", type.toString());
		return new DataException(msg);
	}
	
	
}
