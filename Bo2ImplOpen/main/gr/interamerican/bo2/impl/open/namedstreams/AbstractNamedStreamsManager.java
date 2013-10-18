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

import static gr.interamerican.bo2.impl.open.namedstreams.NamedStreamFactory.input;
import static gr.interamerican.bo2.impl.open.namedstreams.NamedStreamFactory.output;
import static gr.interamerican.bo2.impl.open.namedstreams.NamedStreamFactory.print;
import static gr.interamerican.bo2.impl.open.namedstreams.NamedStreamFactory.reader;
import static gr.interamerican.bo2.impl.open.namedstreams.NamedStreamFactory.syserr;
import static gr.interamerican.bo2.impl.open.namedstreams.NamedStreamFactory.sysout;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.utils.ArrayUtils;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Properties;

/**
 * This class creates {@link NamedStream} objects.
 * 
 */
public abstract class AbstractNamedStreamsManager 
implements NamedStreamsProvider {
	
	/**
	 * Prefix of encoding attribute of a stream definition description.
	 */
	static final String ENCODING_PREFIX = "enc:"; //$NON-NLS-1$
	
	/**
	 * Prefix of record length attribute of a stream definition description.
	 */
	static final String RECORD_LENGTH_PREFIX = "rec:"; //$NON-NLS-1$

	/**
	 * Streams opened by the program
	 */
	HashMap<String, NamedStream<?>> streams;

	/**
	 * Program for which the NamedStreamsCreator operates.
	 */
	protected Properties properties;

	/**
	 * Creates a new NamedStreamsCreator that is reads input by a Properties
	 * object.
	 * 
	 * @param properties
	 *            Properties object with input for this NamedStreamCreator.
	 */
	public AbstractNamedStreamsManager(Properties properties) {
		this.properties = properties;
		this.streams = new HashMap<String, NamedStream<?>>();
	}
	
	public NamedStream<?> getStream(String name) throws InitializationException {
		NamedStream<?> ns = streams.get(name);
		if (ns==null) {
			NamedStreamDefinition def = getDefinition(name);
			ns = open(def);			
			streams.put(name, ns);
		}
		return ns;
	}
	
	public NamedStream<?> getSharedStream(String name) throws InitializationException {
		/*
		 * This needs to be synchronized across all instances of this class, because
		 * it is possible that two competing threads may open two different streams
		 * to the same resource described by the logical name, even though, in the
		 * end only one will be registered with the registry and used.
		 */
		synchronized (AbstractNamedStreamsManager.class) {
			NamedStream<?> ns = SharedNamedStreamsRegistry.getStream(name, this);
			if (ns==null) {		
				NamedStreamDefinition def = getDefinition(name);
				ns = open(def);			
				SharedNamedStreamsRegistry.register(name, ns, this);
			}
			return SharedNamedStreamsRegistry.getStream(name, this);
		}
	}
		
	public void close() throws DataException {
		for (NamedStream<?> stream : streams.values()) {
			stream.close();
		}
		streams.clear();
		SharedNamedStreamsRegistry.releaseSharedStreams(this);
	}
	
	public void registerStream(NamedStream<?> stream) {
		streams.put(stream.getName(), stream);
	}
	
	@Override
	public void registerSharedStream(NamedStream<?> stream) {
		SharedNamedStreamsRegistry.register(stream.getName(), stream, this);
	}

	/**
	 * Opens a {@link NamedInputStream}.
	 * 
	 * @param def
	 *        Definition for the named stream.
	 *        
	 * @return Returns the stream.
	 * @throws InitializationException
	 */
	protected abstract NamedStream<?> open (NamedStreamDefinition def) 
	throws InitializationException;
	
	/**
	 * Gets the definition of the stream with the specified logical name.
	 * 
	 * @param name
	 *        Stream name.
	 *        
	 * @return Returns the definition.
	 * 
	 * @throws InitializationException
	 */
	protected NamedStreamDefinition getDefinition(String name) 
	throws InitializationException {
		String definition = properties.getProperty(name);
		if (definition==null) {
			String problem = "No description"; //$NON-NLS-1$
			throw invalid(problem, name);
		}
		String[] attributes = TokenUtils.splitTrim(definition, StringConstants.COMMA);
		if (attributes.length<3) {
			String problem = "Invalid description"; //$NON-NLS-1$
			throw invalid(problem, name);
		}
		StreamType type = StringUtils.ignoreCaseValueOf
			(StreamType.values(), attributes[1]);
		if (type==null) {
			String problem = "Unknown type " + attributes[1]; //$NON-NLS-1$
			throw invalid(problem, name);
		}
		StreamResource resourceType = StringUtils.ignoreCaseValueOf
			(StreamResource.values(), attributes[2]);
		if (resourceType==null) {
			String problem = "Unknown resource type " + attributes[2]; //$NON-NLS-1$
			throw invalid(problem, name);
		}

		NamedStreamDefinition nsd = new NamedStreamDefinition();
		nsd.setName(name);
		nsd.setUri(attributes[0]);		
		nsd.setType(type);
		nsd.setResourceType(resourceType);
		nsd.setRecordLength(0); //initialize with zero
		
		String optionalAttribute = ArrayUtils.safeGet(attributes, 3);
		nsd = handleOptionalDefinitionElement(nsd, optionalAttribute);
		optionalAttribute = ArrayUtils.safeGet(attributes, 4);
		nsd = handleOptionalDefinitionElement(nsd, optionalAttribute);
		
		return nsd;
	}
	
	/**
	 * The first three definition attributes are mandatory. There are two more
	 * optional attributes, recordLength and encoding. RecordLength is an integer
	 * encoding is a Charset name starting with the prefix {@value #ENCODING_PREFIX}
	 * <br/>
	 * For example enc:UTF-8 signifies the UTF-8 encoding.
	 * 
	 *  @see Charset
	 * 
	 * @param nsd
	 * @param attribute
	 * @return NamedStreamDefinition instance for testability.
	 */
	NamedStreamDefinition handleOptionalDefinitionElement(NamedStreamDefinition nsd, String attribute) {
		if(attribute==null) {
			return nsd;
		}
		
		if(attribute.trim().startsWith(ENCODING_PREFIX)) {
			String charset = attribute.trim().replace(ENCODING_PREFIX, StringConstants.EMPTY);
			nsd.setEncoding(Charset.forName(charset));
		} else if(attribute.trim().startsWith(RECORD_LENGTH_PREFIX)) {
			String recordLength = attribute.trim().replace(RECORD_LENGTH_PREFIX, StringConstants.EMPTY);
			int iLen = NumberUtils.string2Int(recordLength);
			nsd.setRecordLength(iLen);
		}
		return nsd;
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
	 * Opens a file based named stream.
	 * 
	 * @param def
	 * 
	 * @return Returns the NamedStream.
	 * @throws InitializationException
	 */
	protected NamedStream<?> openFileStream(NamedStreamDefinition def) 
	throws InitializationException {
		try {
			NamedStream<?> ns = createNameStreamForFile(def);
			if (ns==null) {
				throw invalid("Invalid definition", def.getName()); //$NON-NLS-1$				
			}
			return ns;
		} catch (IOException ioe) {
			throw new InitializationException(ioe);
		}		
	}
	
	
	/**
	 * Creates a NamedStream for the specified NamedStreamDefinition
	 * that has resourceType = FILE.
	 * 
	 * @param def
	 * 
	 * @return Returns the NamedStream.
	 * 
	 * @throws IOException
	 */
	protected NamedStream<?> createNameStreamForFile(NamedStreamDefinition def) 
	throws IOException {
		File file = new File(def.getUri());		
		StreamType type = def.getType();
		
		switch (type) {
		
		case BUFFEREDREADER:
			return reader(file, def.getName(), def.getEncoding());
			
		case INPUTSTREAM:
			return input(file, def.getName(), def.getRecordLength(), def.getEncoding());
			
		case OUTPUTSTREAM:
			return output(file, def.getName(), def.getRecordLength(), def.getEncoding());
			
		case PRINTSTREAM:			
			return print(file, def.getName(), def.getEncoding());
			
		default:
			return null;
			
		}
		
	}
	
	/**
	 * Opens a stream for the specified definition.
	 * 
	 * @param def 
	 *
	 * @return returns the stream.
	 * 
	 * @throws InitializationException 
	 */
	protected NamedStream<?> openClasspathStream(NamedStreamDefinition def)
	throws InitializationException {
		InputStream in = AbstractNamedStreamsManager.class.getResourceAsStream(def.getUri());
		if (in==null) {
			throw invalid("Classpath resource " + def.getUri(), def.getName()); //$NON-NLS-1$
		}
		StreamType type = def.getType();
		
		switch (type) {
		case BUFFEREDREADER:
			InputStreamReader isr = new InputStreamReader(in, def.getEncoding());
			BufferedReader stream = new BufferedReader(isr);
			NamedBufferedReader nbr = reader(stream, def.getName(), def.getEncoding());
			nbr.resourceType = StreamResource.CLASSPATH;
			return nbr;
						
		case INPUTSTREAM:
			NamedInputStream nis = input(in, def.getName(), def.getRecordLength(), def.getEncoding());
			nis.resourceType = StreamResource.CLASSPATH;
			return nis;
			
		default:
			throw invalid("Invalid type", def.getName());  //$NON-NLS-1$
		}
	}
	
	/**
	 * Opens a file based named stream.
	 * 
	 * @param def
	 * 
	 * @return Returns the NamedStream.
	 * @throws InitializationException
	 */
	protected NamedStream<?> openInMemoryStream(NamedStreamDefinition def) 
	throws InitializationException {
		StreamType type = def.getType();
		switch (type) {
		case OUTPUTSTREAM:
			return output(def.getName(), def.getRecordLength(), def.getEncoding()); 					
		case PRINTSTREAM:			
			return print(def.getName(), def.getEncoding());
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
