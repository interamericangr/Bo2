package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import static gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResourceValidator.*;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedOutputStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedPrintStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

/**
 * A factory for creating WriteOnlyNs objects.
 */
public abstract class WriteOnlyNsFactory implements NamedStreamFactory {

	/**
	 * ResourceType of this factory.
	 */
	StreamResource resourceType;

	/**
	 * Creates a new ReadOnlyNsFactory.
	 *
	 * @param resourceType the resource type
	 */
	public WriteOnlyNsFactory(StreamResource resourceType) {
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
		OutputStream out = (OutputStream) ns.getResource();
		try {
			return createNs(out, nsd);
		} catch (CouldNotCreateNamedStreamException e) {
			throw new CouldNotConvertNamedStreamException(e);
		}
	}

	/**
	 * Creates a new NamedStream.
	 *
	 * @param def the def
	 * @return Returns the {@link NamedStream}.
	 * @throws CouldNotCreateNamedStreamException
	 */
	NamedStream<?> createNs(NamedStreamDefinition def) throws CouldNotCreateNamedStreamException {
		OutputStream stream = openOutputStream(def);
		return createNs(stream, def);
	}

	/**
	 * Creates a new NamedStream.
	 *
	 * @param stream the stream
	 * @param def the def
	 * @return Returns the {@link NamedStream}.
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 */
	NamedStream<?> createNs(OutputStream stream, NamedStreamDefinition def)
			throws CouldNotCreateNamedStreamException {
		StreamType type = def.getType();
		switch (type) {
		case OUTPUTSTREAM:
			return output(stream, def.getName(), def.getRecordLength(), def.getEncoding(),
					def.getUri());
		case PRINTSTREAM:
			return writer(stream, def.getName(), def.getEncoding(), def.getUri());
		default:
			String msg = "Invalid NamedStream type " + StringUtils.toString(type); //$NON-NLS-1$
			throw new CouldNotCreateNamedStreamException(msg);
		}
	}

	/**
	 * Creates a new NamedOutputStream that wraps a file.
	 * 
	 * The specified file provides the underlying resource of the NamedStream.
	 * 
	 * The file is read using the user-defined encoding in the Bo2 configuration.
	 *
	 * @param stream
	 *
	 * @param name
	 *            Stream name.
	 * @param recordLength
	 * @param encoding
	 *            Encoding (if applicable).
	 * @param uri
	 *            Stream URI.
	 *
	 * @return Returns the NamedBufferedReader.
	 */
	NamedOutputStream output(OutputStream stream, String name, int recordLength, Charset encoding,
			String uri) {
		return new NamedOutputStream(resourceType, stream, name, recordLength, stream, encoding,
				uri);
	}

	/**
	 * Creates a new NamedPrintStream that wraps a file.
	 * 
	 * The specified file provides the underlying resource of the NamedStream.
	 *
	 * @param stream the stream
	 * @param name            Stream name.
	 * @param encoding            Encoding (if applicable).
	 * @param uri            Stream URI.
	 * @return Returns the NamedInputStream.
	 */
	NamedPrintStream writer(OutputStream stream, String name, Charset encoding, String uri) {
		PrintStream br = new PrintStream(stream);
		return new NamedPrintStream(resourceType, br, name, stream, encoding, uri);
	}

	/**
	 * Creates an InputStream according to the specified {@link NamedStreamDefinition}.
	 *
	 * @param def            NamedStreamDefinition with the necessary information for the
	 *            creation of the stream.
	 * @return Returns the InputStream.
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 */
	protected abstract OutputStream openOutputStream(NamedStreamDefinition def)
			throws CouldNotCreateNamedStreamException;
}
