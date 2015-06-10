package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;


import static gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResourceValidator.onCreate;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedPrintStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;

import java.io.PrintStream;
import java.nio.charset.Charset;

/**
 * {@link NamedStreamFactory} for system streams. <br/>
 * 
 * The <code>create(NamedStreamDefinition def)</code> method can create only
 * NamedPrintStreams for System.out and System.err. <br/>
 * The <code>convert(NamedStream<?> ns, StreamType type, String name)</code>
 * method will always throw an exception. <br/>  
 *  
 */
public class SystemStreamNsFactory 
implements NamedStreamFactory {

	@Override
	public NamedStream<?> create(NamedStreamDefinition def)
	throws CouldNotCreateNamedStreamException {
		onCreate(def.getResourceType(), StreamResourceEnum.SYSTEM);
		return systemStream(def.getName(), def.getEncoding(), def.getUri());
	}
	
	@Override
	public NamedStream<?> convert(NamedStream<?> ns, StreamType type, String name)
	throws CouldNotConvertNamedStreamException {
		String msg = "Can't convert a system stream.";		 //$NON-NLS-1$
		throw new CouldNotConvertNamedStreamException(msg);
	}
	
	
	/**
	 * Creates a new NamedPrintStream that prints on an OutputStream.
	 * 
	 * @param out
	 * @param name
	 * @param charset
	 * @param uri
	 * 
	 * @return Returns the NamedPrintStream.
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 */
	@SuppressWarnings("nls")
	NamedPrintStream systemStream(String name, Charset charset, String uri) throws CouldNotCreateNamedStreamException {
		PrintStream ps;
		if ("sysout".equalsIgnoreCase(uri)) {
			ps = System.out;						
		} else if ("syserr".equalsIgnoreCase(uri)) {
			ps = System.err;
		} else {
			String msg = "Invalid system stream uri " + uri;
			throw new CouldNotCreateNamedStreamException(msg);
		}
		return new NamedPrintStream(StreamResourceEnum.SYSTEM, ps, name, ps, charset, uri);
	}
	
	
	
	
	

}
