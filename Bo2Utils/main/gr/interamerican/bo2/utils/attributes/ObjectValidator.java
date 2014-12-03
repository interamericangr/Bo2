package gr.interamerican.bo2.utils.attributes;

/**
 * Interface for a validator that validates an Object.
 */
public interface ObjectValidator<T> {
	
	/**
	 * Gets the validatedObject.
	 *
	 * @return Returns the validatedObject
	 */
	public T getValidatedObject();
	
	/**
	 * Assigns a new value to the validatedObject.
	 *
	 * @param validatedObject the validatedObject to set
	 */
	public void setValidatedObject(T validatedObject);

}
