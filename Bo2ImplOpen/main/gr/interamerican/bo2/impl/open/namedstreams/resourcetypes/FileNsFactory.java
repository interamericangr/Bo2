package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import static gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition.DATE;
import static gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition.TIMESTAMP;
import static gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResourceEnum.FILE;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedBufferedReader;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedInputStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedOutputStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedPrintStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.SystemUtils;

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
extends AbstractNsFactory {

	/**
	 * Creates a new FileNsFactory.
	 */
	public FileNsFactory() {
		super(FILE);
	}

	@Override
	protected void onConvert(StreamType from, StreamType to) {
		/* All conversions are allowed  */
	}

	@Override
	protected NamedStream<?> convertNs(NamedStream<?> ns, StreamType type, String name)
			throws CouldNotConvertNamedStreamException {
		try {
			NamedStreamDefinition def = new NamedStreamDefinition();
			ReflectionUtils.copyProperties(ns, def);
			def.setName(name);
			def.setType(type);
			File file = (File) ns.getResource();
			return createNs(file, def);
		} catch (CouldNotCreateNamedStreamException cncnse) {
			throw new CouldNotConvertNamedStreamException(cncnse);
		}
	}

	@Override
	protected NamedStream<?> createNs(NamedStreamDefinition def)
			throws CouldNotCreateNamedStreamException {

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
	 *            the file
	 * @param def
	 *            the def
	 * @return Returns the {@link NamedStream}.
	 * @throws CouldNotCreateNamedStreamException
	 *             the could not create named stream exception
	 */
	NamedStream<?> createNs(File file, NamedStreamDefinition def) throws CouldNotCreateNamedStreamException {
		StreamType type = def.getType();
		String uri = fileUriModification(def.getUri());

		try {
			switch (type) {
			case BUFFEREDREADER:
				return reader(file, def.getName(), def.getEncoding(), uri);
			case INPUTSTREAM:
				return input(file, def.getName(), def.getRecordLength(), def.getEncoding(), uri);
			case OUTPUTSTREAM:
				return output(file, def.getName(), def.getRecordLength(), def.getEncoding(), uri);
			case PRINTSTREAM:
				return print(file, def.getName(), def.getEncoding(), uri);
			default:
				String msg = "Invalid NamedStream type " + StringUtils.toString(type); //$NON-NLS-1$
				throw new CouldNotCreateNamedStreamException(msg);
			}
		} catch (IOException ioe) {
			throw new CouldNotCreateNamedStreamException(ioe);
		}

	}

	/**
	 * Creates a new NamedBufferedReader that wraps a file.
	 * 
	 * The specified file provides the underlying resource of the NamedStream.
	 * 
	 * The file is read using the user-defined encoding in the Bo2
	 * configuration.
	 *
	 * @param file
	 *            File.
	 * @param name
	 *            Stream name.
	 * @param encoding
	 *            Encoding (if applicable).
	 * @param uri
	 *            File path
	 * @return Returns the NamedBufferedReader.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	NamedBufferedReader reader(File file, String name, Charset encoding, String uri) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader insr = new InputStreamReader(fis, encoding);
		BufferedReader br = new BufferedReader(insr);
		return new NamedBufferedReader(FILE, br, name, file, encoding, uri);
	}

	/**
	 * Creates a new NamedInputStream that wraps a file.
	 * 
	 * The specified file provides the underlying resource of the NamedStream.
	 *
	 * @param file
	 *            File.
	 * @param name
	 *            Stream name.
	 * @param recordLength
	 *            Record length.
	 * @param encoding
	 *            Encoding (if applicable).
	 * @param uri
	 *            File path
	 * @return Returns the NamedInputStream.
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	NamedInputStream input(File file, String name, int recordLength, Charset encoding, String uri)
			throws FileNotFoundException {
		InputStream in = new FileInputStream(file);
		return new NamedInputStream(FILE, in, name, recordLength, file, encoding, uri);
	}

	/**
	 * Creates a new NamedOutputStream that wraps a file.
	 * 
	 * The specified file provides the underlying resource of the NamedStream.
	 *
	 * @param file
	 *            File.
	 * @param name
	 *            Stream name.
	 * @param recordLength
	 *            Record length.
	 * @param encoding
	 *            Encoding (if applicable).
	 * @param uri
	 *            File path
	 * @return Returns the NamedOutputStream.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	NamedOutputStream output(File file, String name, int recordLength, Charset encoding, String uri)
			throws IOException {
		OutputStream out = FileUtils.openOutputStream(file);
		return new NamedOutputStream(FILE, out, name, recordLength, file, encoding, uri);
	}

	/**
	 * Creates a new NamedPrintStream that wraps a file.
	 * 
	 * The specified file provides the underlying resource of the NamedStream.
	 *
	 * @param file
	 *            File.
	 * @param name
	 *            Stream name.
	 * @param encoding
	 *            Encoding (if applicable).
	 * @param uri
	 *            File path
	 * @return Returns the NamedOutputStream.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	NamedPrintStream print(File file, String name, Charset encoding, String uri) throws IOException {
		OutputStream os = FileUtils.openOutputStream(file);
		PrintStream out = new PrintStream(os, false, encoding.name());
		return new NamedPrintStream(FILE, out, name, file, encoding, uri);
	}

	/**
	 * Gets a string based on the current timestamp.
	 *
	 * timestamp format is yyyyMMddhhmmss, e.g. 20141103124700
	 *
	 * @return Returns a string based on the current timestamp.
	 */
	String currentTimestamp() {
		return new SimpleDateFormat("yyyyMMddhhmmss").format(Calendar.getInstance().getTime()); //$NON-NLS-1$
	}

	/**
	 * Gets a string based on the current date.
	 *
	 * date format is yyyyMMdd, e.g. 20141103
	 *
	 * @return Returns a string based on the current timestamp.
	 */
	String currentDate() {
		return new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()); //$NON-NLS-1$
	}

	/**
	 * Prepends C: if the running OS is not unix...
	 *
	 * @param fileUri
	 *            the file uri
	 * @return modified URI.
	 */
	String fileUriModification(String fileUri) {
		String uri = fileUri.trim();
		uri = uri.replaceAll(DATE, currentDate());
		uri = uri.replaceAll(TIMESTAMP, currentTimestamp());

		if (SystemUtils.isWindows()) {
			if (uri.startsWith("/")) { //$NON-NLS-1$
				uri = "C:" + uri.trim(); //$NON-NLS-1$
			}
		}
		return uri;
	}
}