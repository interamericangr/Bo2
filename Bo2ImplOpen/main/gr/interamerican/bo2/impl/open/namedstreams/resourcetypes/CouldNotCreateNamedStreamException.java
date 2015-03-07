package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;

/**
 * CouldNotCreateNamedStreamException occurs when a {@link NamedStream}
 * fails to be created.
 */
public class CouldNotCreateNamedStreamException extends Exception {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new CouldNotCreateNamedStreamException.
	 */
	public CouldNotCreateNamedStreamException() {
		super();
	}

	/**
	 * Creates a new CouldNotCreateNamedStreamException.
	 * 
	 * @param message
	 * @param cause
	 */
	public CouldNotCreateNamedStreamException(String message, Throwable cause) {
		super(message, cause);	
	}

	/**
	 * Creates a new CouldNotCreateNamedStreamException.
	 * 
	 * @param message
	 */
	public CouldNotCreateNamedStreamException(String message) {
		super(message);
	}

	/**
	 * Creates a new CouldNotCreateNamedStreamException..
	 * 
	 * @param cause
	 */
	public CouldNotCreateNamedStreamException(Throwable cause) {
		super(cause);
	}

}
