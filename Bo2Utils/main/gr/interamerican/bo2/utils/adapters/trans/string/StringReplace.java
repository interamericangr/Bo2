package gr.interamerican.bo2.utils.adapters.trans.string;

import gr.interamerican.bo2.utils.adapters.Modification;
import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * {@link Transformation} that replaces some part of a {@link String} with
 * another.
 */
public class StringReplace implements Modification<String> {

	/**
	 * From value
	 */
	private String from;
	/**
	 * To Value
	 */
	private String to;

	/**
	 * Creates a new StringReplace object.
	 * 
	 * @param from
	 *            From Value
	 * @param to
	 *            To value
	 */
	public StringReplace(String from, String to) {
		super();
		this.from = from;
		this.to = to;
	}
	
	@Override
	public String execute(String a) {
		if (a == null) {
			return null;
		}
		return a.replace(from, to);
	}

}
