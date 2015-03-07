package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;

/**
 * CouldNotCreateNamedStreamException occurs when a {@link NamedStream}
 * fails to be created.
 */
public class CouldNotConvertNamedStreamException extends Exception {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new CouldNotCreateNamedStreamException.
	 */
	public CouldNotConvertNamedStreamException() {
		super();
	}

	/**
	 * Creates a new CouldNotCreateNamedStreamException.
	 * 
	 * @param message
	 * @param cause
	 */
	public CouldNotConvertNamedStreamException(String message, Throwable cause) {
		super(message, cause);	
	}

	/**
	 * Creates a new CouldNotCreateNamedStreamException.
	 * 
	 * @param message
	 */
	public CouldNotConvertNamedStreamException(String message) {
		super(message);
	}

	/**
	 * Creates a new CouldNotCreateNamedStreamException..
	 * 
	 * @param cause
	 */
	public CouldNotConvertNamedStreamException(Throwable cause) {
		super(cause);
	}

}
