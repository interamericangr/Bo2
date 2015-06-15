package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

/**
 * Utility class with validators intended to be used by {@link NamedStreamFactory}
 * implementations.
 *
 */
class StreamResourceValidator {

	/**
	 * Hidden constructor of utility class.
	 */
	private StreamResourceValidator() { /* empty */ }

	/**
	 * @param actual
	 * @return message
	 */
	static String message(StreamResource actual) {
		return "Invalid stream resource type " + actual.toString(); //$NON-NLS-1$
	}

	/**
	 * Validates that the actual StreamResource is equal with the valid one.
	 * @param actual
	 * @param valid
	 * @throws CouldNotCreateNamedStreamException
	 */
	public static void onCreate(StreamResource actual, StreamResource valid)
			throws CouldNotCreateNamedStreamException {
		if (actual!=valid) {
			throw new CouldNotCreateNamedStreamException(message(actual));
		}
	}

	/**
	 * Validates that the actual StreamResource is equal with the valid one.
	 * @param actual
	 * @param valid
	 * @throws CouldNotConvertNamedStreamException
	 */
	public static void onConvert(StreamResource actual, StreamResource valid)
			throws CouldNotConvertNamedStreamException {
		if (actual!=valid) {
			throw new CouldNotConvertNamedStreamException(message(actual));
		}
	}

}
