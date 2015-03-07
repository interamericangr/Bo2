package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import static gr.interamerican.bo2.impl.open.namedstreams.types.NamedStreamFactoryUtil.httpBufferedReader;
import static gr.interamerican.bo2.impl.open.namedstreams.types.NamedStreamFactoryUtil.httpInputStream;
import static gr.interamerican.bo2.impl.open.namedstreams.types.NamedStreamFactoryUtil.input;
import static gr.interamerican.bo2.impl.open.namedstreams.types.NamedStreamFactoryUtil.output;
import static gr.interamerican.bo2.impl.open.namedstreams.types.NamedStreamFactoryUtil.print;
import static gr.interamerican.bo2.impl.open.namedstreams.types.NamedStreamFactoryUtil.reader;
import static gr.interamerican.bo2.impl.open.namedstreams.types.NamedStreamFactoryUtil.syserr;
import static gr.interamerican.bo2.impl.open.namedstreams.types.NamedStreamFactoryUtil.sysout;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.AbstractNamedStreamsManager;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedBufferedReader;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedInputStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedOutputStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedPrintStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedStreamFactoryUtil;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;
import gr.interamerican.bo2.utils.StringUtils;

public class FooNsFactory 
 {

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
		case HTTP:
			return openHttpStream(def);
		default:
			throw invalid("Invalid stream type in definition", def.getName()); //$NON-NLS-1$
		}
	}
	
	
	
	
	
	/**
	 * Opens a system stream (System.out or System.err) as NamedPrintStream.
	 * 
	 * @param def
	 *        Definition for the named stream.
	 *        
	 * @return Returns the stream.
	 * @throws InitializationException
	 */
	@SuppressWarnings("nls")
	protected NamedPrintStream openSystemStream (NamedStreamDefinition def) 
	throws InitializationException {		
		if ("sysout".equalsIgnoreCase(def.getUri())) {
			return sysout(def.getName(), def.getEncoding());
		}
		if ("syserr".equalsIgnoreCase(def.getUri())) {
			return syserr(def.getName(), def.getEncoding());	
		}		
		throw invalid("Invalid system stream ", def.getName());
		
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
		return NamedStreamFactoryUtil.systemStream(nameOfNewStream, nps.getStream(), nps.getEncoding());
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
	
	
	
	
	
	
	



	/**
	 * Opens an http resource based named stream.
	 * 
	 * @param def
	 * 
	 * @return Returns the NamedStream.
	 * 
	 * @throws InitializationException
	 */
	protected NamedStream<?> openHttpStream(NamedStreamDefinition def) throws InitializationException {
		StreamType type = def.getType();
		switch (type) {
		case INPUTSTREAM:
			return httpInputStream(def);
		case BUFFEREDREADER:
			return httpBufferedReader(def);
		default:
			throw invalid("Invalid type", def.getName());  //$NON-NLS-1$
		}
	}
	
	
	/**
	 * Creates an initialization exception.
	 * 
	 * @param name
	 * @param problem
	 * 
	 * @return Returns the exception.
	 */
	static InitializationException 
	invalid(String problem, String name) {
		@SuppressWarnings("nls")
		String msg = StringUtils.concat(
			problem, " for the named stream ", name);
		return new InitializationException(msg);
	}
	
	
	
	

}
