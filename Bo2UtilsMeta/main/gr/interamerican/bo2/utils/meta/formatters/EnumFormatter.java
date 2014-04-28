package gr.interamerican.bo2.utils.meta.formatters;

/**
 * Enum formatter.
 * @param <E> 
 */
public class EnumFormatter<E extends Enum<E>> implements Formatter<E> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public String format(E t) {
		return t.toString();
	}

}
