package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import static gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition.DATE;
import static gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition.TIMESTAMP;
import static gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResourceValidator.onConvert;
import static gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResourceValidator.onCreate;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedBufferedReader;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedInputStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedOutputStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedPrintStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;

/**
 * {@link NamedStreamFactory} for File streams.
 */
public class ClasspathNsFactory 
implements NamedStreamFactory {

	@Override
	public NamedStream<?> create(NamedStreamDefinition def)
	throws CouldNotCreateNamedStreamException {	
		onCreate(def.getResourceType(), StreamResource.CLASSPATH);
		try {
			NamedStream<?> ns = createNs(def);
			return ns;
		} catch (IOException ioe) {
			throw new CouldNotCreateNamedStreamException(ioe);
		}	
	}
	
	@Override
	public NamedStream<?> convert(NamedStream<?> ns, StreamType type, String name)
	throws CouldNotConvertNamedStreamException {
		onConvert(ns.getResourceType(), StreamResource.CLASSPATH);		
		NamedStreamDefinition nsd = new NamedStreamDefinition();
		ReflectionUtils.copyProperties(ns, nsd);
		try {
			return create(nsd);
		} catch (CouldNotCreateNamedStreamException e) {
			throw new CouldNotConvertNamedStreamException(e);
		}
	}
	
	/**
	 * Creates a new NamedStream.
	 * 
	 * @param def
	 * 
	 * @return Returns the {@link NamedStream}.
	 * @throws IOException
	 * @throws CouldNotConvertNamedStreamException 
	 */
	NamedStream<?> createNs(NamedStreamDefinition def) 
	throws IOException, CouldNotCreateNamedStreamException {	
		String uri = def.getUri();		
		InputStream stream = Class.class.getResourceAsStream(uri);
		if (stream==null) {
			@SuppressWarnings("nls")
			String msg = "Resource " + uri + " not found in classpath";
			throw new CouldNotCreateNamedStreamException(msg);
		}
		return createNs(stream, def);		
	}
	
	
	/**
	 * Creates a new NamedStream.
	 * 
	 * @param file
	 * @param def
	 * 
	 * @return Returns the {@link NamedStream}.
	 * @throws IOException
	 */
	NamedStream<?> createNs(InputStream stream, NamedStreamDefinition def) 
	throws IOException, CouldNotCreateNamedStreamException {				
		StreamType type = def.getType();		
		switch (type) {		
		case BUFFEREDREADER:
			return reader(stream, def.getName(), def.getEncoding());			
		case INPUTSTREAM:
			return input(stream, def.getName(), def.getRecordLength(), def.getEncoding());			
		default:
			String msg = "Invalid NamedStream type " + StringUtils.toString(type); //$NON-NLS-1$
			throw new CouldNotCreateNamedStreamException(msg);			
		}		
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
	NamedBufferedReader reader(InputStream stream, String name, Charset encoding)
	throws IOException {
		InputStreamReader insr = new InputStreamReader(stream, encoding);
		BufferedReader br = new BufferedReader(insr);
		return new NamedBufferedReader(StreamResource.FILE, br, name, stream, encoding);
	}
	
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
	NamedInputStream input(InputStream stream, String name, int recordLength, Charset encoding)
	throws FileNotFoundException {		
		return new NamedInputStream(StreamResource.FILE, stream, name, recordLength, stream, encoding);
	}
	
	
	
	
	
	
	
	

}
