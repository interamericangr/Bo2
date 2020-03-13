package gr.interamerican.bo2.utils.beans;

import gr.interamerican.bo2.utils.attributes.Input;

/**
 * {@link Input} java bean.
 *
 * @param <T> the generic type
 */
public class InputBean<T> implements Input<T> {
	/**
	 * Input.
	 */
	T input;

	/**
	 * Gets the input.
	 *
	 * @return Returns the input
	 */
	public T getInput() {
		return input;
	}
	
	@Override
	public void setInput(T input) {
		this.input = input;
	}

}
