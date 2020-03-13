package gr.interamerican.bo2.utils.meta.formatters.nf;

import gr.interamerican.bo2.utils.meta.formatters.EnumFormatter;

/**
 * Null returning enum formatter.
 *
 * @param <E> the element type
 */
public class NfEnumFormatter<E extends Enum<E>> extends NullFilteringFormatter<E> {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new NrEnumFormatter object. 
	 */
	public NfEnumFormatter() {
		super(new EnumFormatter<E>());
	}

}
