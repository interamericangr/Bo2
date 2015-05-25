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
public class FileNsFactory 
implements NamedStreamFactory {

	@Override
	public NamedStream<?> create(NamedStreamDefinition def)
	throws CouldNotCreateNamedStreamException {	
		onCreate(def.getResourceType(), StreamResource.FILE);
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
		onConvert(ns.getResourceType(), StreamResource.FILE);		
		try {
			NamedStreamDefinition def = new NamedStreamDefinition();
			ReflectionUtils.copyProperties(ns, def);
			def.setName(name);
			def.setType(type);
			File file = (File) ns.getResource();
			return createNs(file, def);			
		} catch (IOException ioe) {			
			throw new CouldNotConvertNamedStreamException(ioe);
		} catch (CouldNotCreateNamedStreamException cncnse) {			
			throw new CouldNotConvertNamedStreamException(cncnse);
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
		if(uri.contains(TIMESTAMP)) {
			uri = uri.replace(TIMESTAMP, currentTimestamp());
		}		
		if(uri.contains(DATE)) {
			uri = uri.replace(DATE, currentDate());
		}
		File file = new File(uri);	
		return createNs(file, def);		
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
	NamedStream<?> createNs(File file, NamedStreamDefinition def) 
	throws IOException, CouldNotCreateNamedStreamException {				
		StreamType type = def.getType();		
		switch (type) {		
		case BUFFEREDREADER:
			return reader(file, def.getName(), def.getEncoding(), def.getUri());			
		case INPUTSTREAM:
			return input(file, def.getName(), def.getRecordLength(), def.getEncoding(), def.getUri());			
		case OUTPUTSTREAM:
			return output(file, def.getName(), def.getRecordLength(), def.getEncoding(), def.getUri());			
		case PRINTSTREAM:			
			return print(file, def.getName(), def.getEncoding(), def.getUri());			
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
	 * @param uri
	 *        File path        
	 * 
	 * @return Returns the NamedBufferedReader.
	 * 
	 * @throws IOException
	 */
	NamedBufferedReader reader(File file, String name, Charset encoding, String uri)
	throws IOException {
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader insr = new InputStreamReader(fis, encoding);
		BufferedReader br = new BufferedReader(insr);
		return new NamedBufferedReader(StreamResource.FILE, br, name, file, encoding, uri);
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
	 * @param uri
	 *        File path 
	 * 
	 * @return Returns the NamedInputStream.
	 * @throws FileNotFoundException
	 */
	NamedInputStream input(File file, String name, int recordLength, Charset encoding, String uri)
	throws FileNotFoundException {
		InputStream in = new FileInputStream(file);
		return new NamedInputStream(StreamResource.FILE, in, name, recordLength, file, encoding, uri);
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
	 * @param uri
	 *        File path         
	 * 
	 * @return Returns the NamedOutputStream.
	 * 
	 * @throws IOException
	 */
	NamedOutputStream output(File file, String name, int recordLength, Charset encoding, String uri) 
	throws IOException {
		OutputStream out = FileUtils.openOutputStream(file);
		return new NamedOutputStream(StreamResource.FILE, out, name, recordLength, file, encoding, uri);
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
	 * @param uri
	 *        File path         
	 * 
	 * @return Returns the NamedOutputStream.
	 * 
	 * @throws IOException
	 */
	NamedPrintStream print(File file, String name, Charset encoding, String uri)
	throws IOException {
		OutputStream os = FileUtils.openOutputStream(file);
		PrintStream out = new PrintStream(os, false, encoding.name());
		return new NamedPrintStream(StreamResource.FILE, out, name, file, encoding, uri);
	}
	
	/**
	 * Gets a string based on the current timestamp.
	 * 
	 * timestamp format is yyyyMMddhhmmss, e.g. 20141103124700
	 * 
	 * @return Returns a string based on the current timestamp.
	 */
	String currentTimestamp() {
		String tmstmp = new SimpleDateFormat("yyyyMMddhhmmss").format(Calendar.getInstance().getTime()); //$NON-NLS-1$
		return tmstmp;
	}
	
	/**
	 * Gets a string based on the current date.
	 * 
	 * date format is yyyyMMdd, e.g. 20141103
	 * 
	 * @return Returns a string based on the current timestamp.
	 */
	String currentDate() {
		String date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()); //$NON-NLS-1$
		return date;
	}
	
	
	

}
