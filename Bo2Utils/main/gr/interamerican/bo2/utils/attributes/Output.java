package gr.interamerican.bo2.utils.attributes;

/**
 * A type that produces an output.
 *
 * @param <O> the generic type
 */
public interface Output<O> {
	
	/**
	 * Gets the output.
	 *
	 * @return output
	 */
	O getOutput();

}
