package gr.interamerican.wicket.markup.html;

import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.functions.SerializableUnaryOperator;

/**
 * Common tags.
 */
@SuppressWarnings("nls")
public enum Markup implements SerializableUnaryOperator<String> {

	/**
	 * div
	 */
	div(w -> "<div wicket:id=" + StringUtils.doubleQuotes(w) + "></div>"),

	/**
	 * select
	 */
	select(w -> "<select wicket:id=" + StringUtils.doubleQuotes(w) + "></select>"),

	/**
	 * table
	 */
	table(w -> "<table wicket:id=" + StringUtils.doubleQuotes(w) + "></table>"),

	/**
	 * input
	 */
	input(w -> "<input wicket:id=" + StringUtils.doubleQuotes(w) + "></input>"),
	
	/**
	 * td
	 */
	td(w -> "<td wicket:id=" + StringUtils.doubleQuotes(w) + "></td>");

	/**
	 * The {@link SerializableUnaryOperator} to apply
	 */
	private SerializableUnaryOperator<String> delegate;

	/**
	 * Constructor
	 * 
	 * @param delegate
	 *            The {@link SerializableUnaryOperator} to apply
	 *
	 */
	private Markup(SerializableUnaryOperator<String> delegate) {
		this.delegate = delegate;
	}

	@Override
	public String apply(String t) {
		return delegate.apply(t);
	}
}