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
		InputStream in = (InputStream) ns.getResource();
		try {
			return createNs(in, nsd);
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
	 * @throws CouldNotCreateNamedStreamException
	 */
	NamedStream<?> createNs(NamedStreamDefinition def)
			throws CouldNotCreateNamedStreamException {
		InputStream stream = openInputStream(def);
		return createNs(stream, def);
	}


	/**
	 * Creates a new NamedStream.
	 *
	 * @param stream
	 *
	 * @param def
	 *
	 * @return Returns the {@link NamedStream}.
	 * @throws CouldNotCreateNamedStreamException
	 */
	NamedStream<?> createNs(InputStream stream, NamedStreamDefinition def)
			throws CouldNotCreateNamedStreamException {
		StreamType type = def.getType();
		switch (type) {
		case BUFFEREDREADER:
			return reader(stream, def.getName(), def.getEncoding(), def.getUri());
		case INPUTSTREAM:
			return input(stream, def.getName(), def.getRecordLength(), def.getEncoding(), def.getUri());
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
	 * @param stream
	 *            stream.
	 * @param name
	 *            Stream name.
	 * @param encoding
	 *            Encoding (if applicable).
	 * @param uri
	 *            Stream URI.
	 *
	 * @return Returns the NamedBufferedReader.
	 *
	 * @throws IOException
	 */
	NamedBufferedReader reader(InputStream stream, String name, Charset encoding, String uri) {
		InputStreamReader insr = new InputStreamReader(stream, encoding);
		BufferedReader br = new BufferedReader(insr);
		return new NamedBufferedReader(resourceType, br, name, stream, encoding, uri);
	}

	/**
	 * Creates a new NamedInputStream that wraps a file.
	 *
	 * The specified file provides the underlying resource of the NamedStream.
	 *
	 * @param stream
	 *            stream.
	 * @param name
	 *            Stream name.
	 * @param recordLength
	 *            Record length.
	 * @param encoding
	 *            Encoding (if applicable).
	 * @param uri
	 *            Stream URI.
	 *
	 * @return Returns the NamedInputStream.
	 * @throws FileNotFoundException
	 */
	NamedInputStream input(InputStream stream, String name, int recordLength, Charset encoding, String uri) {
		return new NamedInputStream(resourceType, stream, name, recordLength, stream, encoding, uri);
	}


}
