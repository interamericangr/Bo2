package gr.interamerican.bo2.utils.meta.formatters.nr;

import gr.interamerican.bo2.utils.meta.formatters.EnumFormatter;

/**
 * Null returning enum formatter.
 * @param <E> 
 */
public class NrEnumFormatter<E extends Enum<E>> extends NullReturningFormatter<E> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new NrEnumFormatter object. 
	 */
	public NrEnumFormatter() {
		super(new EnumFormatter<E>());
	}

}
