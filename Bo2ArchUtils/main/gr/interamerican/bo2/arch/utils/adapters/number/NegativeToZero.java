package gr.interamerican.bo2.arch.utils.adapters.number;


/**
 * Money aware {@link gr.interamerican.bo2.utils.adapters.number.AbsoluteValue}.
 */
public class NegativeToZero extends MoneyAwareNumberTransformation {

	/**
	 * Creates a new AbsoluteValue object.
	 * 
	 */
	public NegativeToZero() {
		super(new gr.interamerican.bo2.utils.adapters.number.NegativeToZero());
	}

}
