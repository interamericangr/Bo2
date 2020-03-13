package gr.interamerican.bo2.impl.open.workers;

import gr.interamerican.bo2.arch.Rule;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.RuleException;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.attributes.Input;
import gr.interamerican.bo2.utils.beans.MessagesBean;
import gr.interamerican.bo2.utils.conditions.Condition;

/**
 * Validates an object against a condition.
 * 
 * The condition can be dynamically created.
 * 
 * @param <T> 
 *        Type of object being validated.
 */
public abstract class DynamicConditionValidator<T> 
extends AbstractResourceConsumer 
implements Rule, Input<T> {	

	
	/**
	 * Message to return if the validationfails.
	 */
	String messageKey;
	
	/**
	 * Messages bean.
	 */
	MessagesBean messages;
	
	/** The fail on. */
	boolean failOn;

	
	/**
	 *  Property name of the validated object.
	 */
	String validatedObjectProperty;
	
	
	/**
	 * Gets the condition to validate.
	 * 
	 * @return Returns the condition.
	 */
	protected abstract Condition<T> getCondition();
	
	
	/**
	 * Creates a new ConditionValidator object.
	 * 
	 *        Condition to check.
	 * @param failOn 
	 *        Defines when the Rule fails. A <code>true</code> value indicates
	 *        that the rule throws an exception when the condition is met.
	 *        A <code>false</code> value indicates that the rule throws an exception 
	 *        when the condition is not met.
	 * @param messages        MessagesBean that fetches the messages.
	 * @param messageKey        Message key used to fetch the key from the message bean.  
	 * @param validatedObjectProperty        Property name of the validated object.
	 */
	protected DynamicConditionValidator
	(boolean failOn, MessagesBean messages, String messageKey, String validatedObjectProperty) {
		this.messageKey = messageKey;
		this.messages = messages;
		this.failOn = failOn;
		this.validatedObjectProperty = validatedObjectProperty; 
	}
	
	
	/**
	 * Gets the message.
	 * 
	 * @return Returns the message.
	 */
	protected String getMessage() {
		if (messages==null) {
			return messageKey;
		}
		T validatedObject = getValidatedObject();
		return messages.getString(messageKey, validatedObject); 
	}
	
	@Override
	public void apply() throws RuleException, DataException {
		Condition<T> condition = getCondition();
		T validatedObject = getValidatedObject();
		boolean result = condition.check(validatedObject);
		boolean trigger = failOn ? result : !result;		
		if (trigger) {			
			throw new RuleException(getMessage());
		}		
	}

	
	/**
	 * Gets the validated object.
	 * 
	 * @return Returns the validated object.
	 */
	@SuppressWarnings("unchecked")
	T getValidatedObject() {		
		return (T) ReflectionUtils.getProperty(validatedObjectProperty, this);
	}

	/**
	 * Sets the validated object.
	 *
	 * @param validatedObject the new validated object
	 */
	public void setValidatedObject(T validatedObject) {
		ReflectionUtils.setProperty(validatedObjectProperty, validatedObject, this);
	}

	@Override
	public void setInput(T input) {
		setValidatedObject(input);		
	}	

}
