package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;


import static gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResourceEnum.BYTES;
import static gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResourceValidator.onConvert;
import static gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResourceValidator.onCreate;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedBufferedReader;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedInputStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedOutputStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedPrintStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;
import gr.interamerican.bo2.utils.StringUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * {@link NamedStreamFactory} for byte array streams. <br/>
 * 
 * The <code>create(NamedStreamDefinition def)</code> method can create only
 * NamedStreams with type OUTPUTSTREAM or PRINTSTREAM. The <code>resource</code>
 * in both cases will be a ByteArrayOutputStream. <br/>
 * The <code>convert(NamedStream<?> ns, StreamType type, String name)</code>
 * method create NamedStreams with type INPUTSTREAM or BUFFEREDREADER
 * that read the byte array of the ByteArrayOutputStream resource
 * of a NamedStream with type OUTPUTSTREAM or PRINTSTREAM. <br/>  
 *  
 */
public class ByteNsFactory 
implements NamedStreamFactory {

	@Override
	public NamedStream<?> create(NamedStreamDefinition def)
	throws CouldNotCreateNamedStreamException {
		onCreate(def.getResourceType(), BYTES);
		return createNs(def);
	}
	
	@Override
	public NamedStream<?> convert(NamedStream<?> ns, StreamType type, String name)
	throws CouldNotConvertNamedStreamException {
		onConvert(ns.getResourceType(), BYTES);
		try {
			return convertNs(ns, type, name);
		} catch (CouldNotCreateNamedStreamException e) {
			throw new CouldNotConvertNamedStreamException(e);
		}
	}
	
	
	/**
	 * Creates a new NamedStream.
	 * 
	 * @param def
	 *        {@link NamedStreamDefinition} for the stream. 
	 * @param bytes
	 *        Byte array with the stream's data 
	 *        
	 * @return Returns a new NamedStream.
	 * 
	 * @throws CouldNotCreateNamedStreamException
	 */	
	public NamedStream<?> createWithBytes(NamedStreamDefinition def, byte[] bytes)
	throws CouldNotCreateNamedStreamException {
		onCreate(def.getResourceType(), BYTES);
		
		StreamType type = def.getType();		
		ByteArrayOutputStream out = new InitializedByteArrayOutputStream(bytes);
		
		switch (type) {			
		
		case OUTPUTSTREAM:
			return output(out, def.getName(), def.getRecordLength(), def.getEncoding(), def.getUri());
			
		case PRINTSTREAM:
			return print(out, def.getName(), def.getEncoding(), def.getUri());
			
		case INPUTSTREAM:
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			return new NamedInputStream(BYTES, bais, def.getName(),def.getRecordLength(), out, def.getEncoding(), def.getUri());
			
		case BUFFEREDREADER:			
			ByteArrayInputStream bain = new ByteArrayInputStream(bytes);
			InputStreamReader inr = new InputStreamReader(bain, def.getEncoding());
			BufferedReader reader = new BufferedReader(inr);
			return new NamedBufferedReader(BYTES, reader, def.getName(), out, def.getEncoding(), def.getUri());
			
		default:
			String sType = StringUtils.toString(type);
			String msg = "Can't create a NamedStream with type " + sType; //$NON-NLS-1$			
			throw new CouldNotCreateNamedStreamException(msg);			
		}		

	}
	
	
	
	/**
	 * Creates a new NamedStream by converting an existing one to a 
	 * NamedStream that has the same resource but different type.
	 * 
	 * @param ns
	 * @param type
	 * @param name
	 * 
	 * @return Returns the new NamedStream.
	 * 
	 * @throws CouldNotCreateNamedStreamException
	 */
	NamedStream<?> convertNs(NamedStream<?> ns, StreamType type, String name)
	throws CouldNotCreateNamedStreamException {	
		ByteArrayOutputStream baos = (ByteArrayOutputStream) ns.getResource();		
		
		switch (type) {		
		case BUFFEREDREADER:
			return reader(baos, name, ns.getEncoding(), ns.getUri());					
			
		case INPUTSTREAM:
			return input(baos, name, ns.getRecordLength(), ns.getEncoding(), ns.getUri());
						
		case OUTPUTSTREAM:
			return new NamedOutputStream(BYTES, baos, name, ns.getRecordLength(), baos, ns.getEncoding(), ns.getUri());
						
		case PRINTSTREAM:			
			return print(baos,name,ns.getEncoding(), ns.getUri());
		}
		return null;
	}
	
		
	
	/**
	 * Creates a new NamedStream.
	 * 
	 * @param def
	 * 
	 * @return Returns the NamedStream.
	 * 
	 * @throws CouldNotCreateNamedStreamException
	 */
	NamedStream<?> createNs(NamedStreamDefinition def)
	throws CouldNotCreateNamedStreamException {	
		StreamType type = def.getType();		
		switch (type) {
			
		case OUTPUTSTREAM:			
			OutputStream outo = new ByteArrayOutputStream();
			return output(outo, def.getName(), def.getRecordLength(), def.getEncoding(), def.getUri());
			
		case PRINTSTREAM:			
			OutputStream outp = new ByteArrayOutputStream();
			return print(outp, def.getName(), def.getEncoding(), def.getUri());
			
		default:
			String sType = StringUtils.toString(type);
			String msg = "Can't create a NamedStream with type " + sType; //$NON-NLS-1$
			throw new CouldNotCreateNamedStreamException(msg);			
		}		
	}
	
	
	
	/**
	 * Creates a new NamedPrintStream that prints on an OutputStream.
	 * 
	 * @param out
	 * @param name
	 * @param charset
	 * @param charset
	 * 
	 * @return Returns the NamedPrintStream.
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 */
	NamedPrintStream print(OutputStream out, String name, Charset charset, String uri) throws CouldNotCreateNamedStreamException {
		try {
			PrintStream print = new PrintStream(out, false, charset.name());
			return new NamedPrintStream(BYTES, print, name, out, charset, uri);
		}catch (UnsupportedEncodingException e) {
			throw new CouldNotCreateNamedStreamException(e);
		}
	}
	
	/**
	 * Creates a new NamedOutputStream for an existing OutputStream.
	 * 
	 * @param out
	 * @param name
	 * @param charset
	 * @param charset
	 * 
	 * @return Returns the NamedPrintStream.
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 */
	NamedOutputStream output(OutputStream out, String name, int recordLength, Charset charset, String uri) throws CouldNotCreateNamedStreamException {
		return new NamedOutputStream(BYTES, out, name, recordLength, out, charset, uri);	
	}
	

	/**
	 * Creates a new NamedInputStream that reads the data of an existing ByteArrayOutputStream.
	 * 
	 * @param baos
	 * @param name
	 * @param recordLength
	 * @param charset
	 * @param charset
	 * 
	 * @return Returns the NamedInputStream.
	 * 
	 * @throws CouldNotCreateNamedStreamException
	 */
	NamedInputStream input(ByteArrayOutputStream baos, String name, int recordLength, Charset charset, String uri) throws CouldNotCreateNamedStreamException {
		byte[] bytes = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		return new NamedInputStream(BYTES, bais, name,recordLength, baos, charset, uri);		
	}
	
	/**
	 * Creates a new NamedBufferedReader that reads the data of an existing ByteArrayOutputStream.
	 * 
	 * @param baos
	 * @param name
	 * @param charset
	 * @param uri
	 * 
	 * @return Returns the NamedInputStream.
	 * 
	 * @throws CouldNotCreateNamedStreamException
	 */
	NamedBufferedReader reader(ByteArrayOutputStream baos, String name, Charset charset, String uri) throws CouldNotCreateNamedStreamException {
		byte[] bytes = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		InputStreamReader inr = new InputStreamReader(bais, charset);
		BufferedReader reader = new BufferedReader(inr);
		return new NamedBufferedReader(BYTES, reader, name, baos, charset, uri);
	}
	
	
	
	
	
	
	
	

	
	

}
