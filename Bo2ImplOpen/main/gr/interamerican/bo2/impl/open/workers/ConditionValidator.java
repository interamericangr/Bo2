package gr.interamerican.bo2.impl.open.workers;

import gr.interamerican.bo2.arch.Rule;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.RuleException;
import gr.interamerican.bo2.utils.conditions.Condition;

/**
 * Validates an object against a condition.
 * 
 * @param <T> 
 *        Type of object being validated.
 */
public class ConditionValidator<T> 
extends AbstractResourceConsumer 
implements Rule {
	
	

	/**
	 * Message to return if the validationfails.
	 */
	String message;
	/**
	 * Condition to check.
	 */
	Condition<T> condition;
	
	/**
	 * Object being validated.
	 */
	T validatedObject;
	
	/**
	 * Creates a new ConditionValidator object. 
	 *
	 * @param message
	 * @param condition
	 */
	public ConditionValidator(String message, Condition<T> condition) {
		super();
		this.message = message;
		this.condition = condition;
	}
	
	@Override
	public void apply() throws RuleException, DataException {
		if (!condition.check(validatedObject)) {
			throw new RuleException(message);
		}		
	}

	/**
	 * Gets the validatedObject.
	 *
	 * @return Returns the validatedObject
	 */
	public T getValidatedObject() {
		return validatedObject;
	}

	/**
	 * Assigns a new value to the validatedObject.
	 *
	 * @param validatedObject the validatedObject to set
	 */
	public void setValidatedObject(T validatedObject) {
		this.validatedObject = validatedObject;
	}
	
	
	
	

}
