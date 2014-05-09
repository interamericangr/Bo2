package gr.interamerican.bo2.utils.attributes;

/**
 * {@link Input} models any class that requires an input.
 * 
 * @param <I>
 *        Type of input object. 
 */
public interface Input<I> {
	/**
	 * Sets the input.
	 * 
	 * @param input
	 */
	public void setInput(I input);

}
