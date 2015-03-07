package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;


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
		onCreate(def.getResourceType(), StreamResource.BYTES);
		return createNs(def);
	}
	
	@Override
	public NamedStream<?> convert(NamedStream<?> ns, StreamType type, String name)
	throws CouldNotConvertNamedStreamException {
		onConvert(ns.getResourceType(), StreamResource.BYTES);
		try {
			return convertNs(ns, type, name);
		} catch (CouldNotCreateNamedStreamException e) {
			throw new CouldNotConvertNamedStreamException(e);
		}
	}
	
	
	NamedStream<?> convertNs(NamedStream<?> ns, StreamType type, String name)
	throws CouldNotCreateNamedStreamException {	
		ByteArrayOutputStream baos = (ByteArrayOutputStream) ns.getResource();		
		
		switch (type) {		
		case BUFFEREDREADER:
			return reader(baos, name, ns.getEncoding());					
			
		case INPUTSTREAM:
			return input(baos, name, ns.getRecordLength(), ns.getEncoding());
						
		case OUTPUTSTREAM:
			return new NamedOutputStream(StreamResource.BYTES, baos, name, ns.getRecordLength(), baos, ns.getEncoding());
						
		case PRINTSTREAM:			
			return print(baos,name,ns.getEncoding());
		}
		return null;
	}
	
	
	/**
	 * Creates a new NamedStream.
	 * 
	 * @param def
	 * @return
	 * @throws CouldNotCreateNamedStreamException
	 */
	NamedStream<?> createNs(NamedStreamDefinition def)
	throws CouldNotCreateNamedStreamException {	
		StreamType type = def.getType();		
		switch (type) {
			
		case OUTPUTSTREAM:			
			OutputStream outo = new ByteArrayOutputStream();
			return output(outo, def.getName(), def.getRecordLength(), def.getEncoding());
			
		case PRINTSTREAM:			
			OutputStream outp = new ByteArrayOutputStream();
			return print(outp, def.getName(), def.getEncoding());
			
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
	 * @return Returns the NamedPrintStream.
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 */
	NamedPrintStream print(OutputStream out, String name, Charset charset) throws CouldNotCreateNamedStreamException {
		try {
			PrintStream print = new PrintStream(out, false, charset.name());
			return new NamedPrintStream(StreamResource.BYTES, print, name, out, charset);
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
	 * 
	 * @return Returns the NamedPrintStream.
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 */
	NamedOutputStream output(OutputStream out, String name, int recordLength, Charset charset) throws CouldNotCreateNamedStreamException {
		return new NamedOutputStream(StreamResource.BYTES, out, name, recordLength, out, charset);	
	}
	

	/**
	 * Creates a new NamedInputStream that reads the data of an existing ByteArrayOutputStream.
	 * 
	 * @param baos
	 * @param name
	 * @param recordLength
	 * @param charset
	 * 
	 * @return Returns the NamedInputStream.
	 * 
	 * @throws CouldNotCreateNamedStreamException
	 */
	NamedInputStream input(ByteArrayOutputStream baos, String name, int recordLength, Charset charset) throws CouldNotCreateNamedStreamException {
		byte[] bytes = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		return new NamedInputStream(StreamResource.BYTES, bais, name,recordLength, baos, charset);		
	}
	
	/**
	 * Creates a new NamedBufferedReader that reads the data of an existing ByteArrayOutputStream.
	 * 
	 * @param baos
	 * @param name
	 * @param charset
	 * 
	 * @return Returns the NamedInputStream.
	 * 
	 * @throws CouldNotCreateNamedStreamException
	 */
	NamedBufferedReader reader(ByteArrayOutputStream baos, String name, Charset charset) throws CouldNotCreateNamedStreamException {
		byte[] bytes = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		InputStreamReader inr = new InputStreamReader(bais, charset);
		BufferedReader reader = new BufferedReader(inr);
		return new NamedBufferedReader(StreamResource.BYTES, reader, name, baos, charset);
	}
	
	
	
	
	
	
	
	

	
	

}
