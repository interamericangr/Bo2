package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;

/**
 * Utility class with validators intended to be used by {@link NamedStreamFactory}
 * implementations.
 *
 */
abstract class AbstractNsFactory
implements NamedStreamFactory {

	/**
	 * Valid stream resource type.
	 */
	private StreamResource valid;

	/**
	 * Hidden constructor of utility class.
	 *
	 * @param valid the valid
	 */
	protected AbstractNsFactory(StreamResource valid) {
		super();
		this.valid = valid;
	}


	/**
	 * Creates the validation failure message.
	 *
	 * @param actual the actual
	 * @return Creates the validation failure message.
	 */
	@SuppressWarnings("nls")
	protected String invalidResourceMessage(StreamResource actual) {
		return StringUtils.concat(
				"Invalid stream resource type ", StringUtils.toString(actual),
				" .Valid type for this factory is ", StringUtils.toString(valid));
	}

	/**
	 * Creates a message indicating a conversion is not supported.
	 * 
	 * This message is meant to be used by the
	 * <code>onConvert(StreamType from, StreamType to)</code> method.
	 *
	 * @param from the from
	 * @param to the to
	 * @return Returns the message.
	 */
	protected String conversionNotSupportedMessage(StreamType from, StreamType to) {
		@SuppressWarnings("nls")
		String msg = StringUtils.concat(
				"Conversion from ", StringUtils.toString(from),
				" to ", StringUtils.toString(to),
				" not supported");
		return msg;
	}

	/**
	 * Validates that the actual StreamResource is equal with the valid one.
	 * @param actual
	 * @throws CouldNotCreateNamedStreamException
	 */
	protected void onCreate(StreamResource actual)
			throws CouldNotCreateNamedStreamException {
		if (!Utils.equals(actual,valid)) {
			String msg = invalidResourceMessage(actual);
			throw new CouldNotCreateNamedStreamException(msg);
		}
	}

	/**
	 * Validates that the actual StreamResource is equal with the valid one.
	 * @param actual
	 * @throws CouldNotConvertNamedStreamException
	 */
	protected void onConvert(StreamResource actual)
			throws CouldNotConvertNamedStreamException {
		if (!Utils.equals(actual,valid)) {
			String msg = invalidResourceMessage(actual);
			throw new CouldNotConvertNamedStreamException(msg);
		}
	}

	/**
	 * Validates that the actual StreamResource is equal with the valid one.
	 *
	 * @param from
	 * @param to
	 */
	protected abstract void onConvert(StreamType from, StreamType to);


	/**
	 * Creates a new NamedStream.
	 *
	 * @param def
	 *
	 * @return Returns the {@link NamedStream}.
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 */
	protected abstract NamedStream<?> createNs(NamedStreamDefinition def)
			throws CouldNotCreateNamedStreamException;

	/**
	 * Convert ns.
	 *
	 * @param ns
	 * @param type
	 * @param name
	 * @return The {@link NamedStream}
	 * @throws CouldNotConvertNamedStreamException
	 */
	protected abstract NamedStream<?> convertNs(NamedStream<?> ns, StreamType type, String name)
			throws CouldNotConvertNamedStreamException;


	@Override
	public NamedStream<?> create(NamedStreamDefinition def)
			throws CouldNotCreateNamedStreamException {
		onCreate(def.getResourceType());
		NamedStream<?> ns = createNs(def);
		return ns;
	}

	@Override
	public NamedStream<?> convert(NamedStream<?> ns, StreamType type, String name)
			throws CouldNotConvertNamedStreamException {
		onConvert(ns.getResourceType());
		onConvert(ns.getType(), type);
		return convertNs(ns,type,name);
	}

}
