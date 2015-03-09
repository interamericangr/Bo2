package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import static gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResourceValidator.onConvert;
import static gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResourceValidator.onCreate;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedBufferedReader;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedInputStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * {@link NamedStreamFactory} for read only resource types.
 * 
 * This type of factory can only create {@link NamedInputStream}s and
 * {@link NamedBufferedReader}s.
 */
public abstract class ReadOnlyNsFactory 
implements NamedStreamFactory {
	
	/**
	 * ResourceType of this factory. 
	 */
	StreamResource resourceType;

	/**
	 * Creates a new ReadOnlyNsFactory.
	 * 
	 * @param resourceType
	 */
	public ReadOnlyNsFactory(StreamResource resourceType) {
		super();
		this.resourceType = resourceType;
	}

	@Override
	public NamedStream<?> create(NamedStreamDefinition def)
	throws CouldNotCreateNamedStreamException {	
		onCreate(def.getResourceType(), resourceType);
		return createNs(def);
	}
	
	@Override
	public NamedStream<?> convert(NamedStream<?> ns, StreamType type, String name)
	throws CouldNotConvertNamedStreamException {
		onConvert(ns.getResourceType(), resourceType);		
		NamedStreamDefinition nsd = new NamedStreamDefinition();
		ReflectionUtils.copyProperties(ns, nsd);
		nsd.setName(name);
		nsd.setType(type);
		try {
			return create(nsd);
		} catch (CouldNotCreateNamedStreamException e) {
			throw new CouldNotConvertNamedStreamException(e);
		}
	}
	
	/**
	 * Creates an InputStream according to the specified {@link NamedStreamDefinition}.
	 * 
	 * @param def
	 *        NamedStreamDefinition with the necessary information for the
	 *        creation of the stream. 
	 * 
	 * @return Returns the InputStream.
	 * @throws CouldNotCreateNamedStreamException
	 */
	protected abstract InputStream openInputStream(NamedStreamDefinition def)
	throws CouldNotCreateNamedStreamException;
	
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
	throws CouldNotCreateNamedStreamException {
		InputStream stream = openInputStream(def);
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
	throws CouldNotCreateNamedStreamException {				
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
	NamedBufferedReader reader(InputStream stream, String name, Charset encoding) {
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
	NamedInputStream input(InputStream stream, String name, int recordLength, Charset encoding) {		
		return new NamedInputStream(StreamResource.FILE, stream, name, recordLength, stream, encoding);
	}
	

}
