package gr.interamerican.bo2.impl.open.workers;

import gr.interamerican.bo2.arch.Rule;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.RuleException;
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
public abstract class AbstractConditionValidator<T> 
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
	
	/**
	 * 
	 */
	boolean failOn;

	
	/**
	 * Object being validated.
	 */
	T validatedObject;
	
	
	/**
	 * Gets the condition to validate.
	 * 
	 * @return Returns the condition.
	 */
	protected abstract Condition<T> getCondition();
	
	
	/**
	 * Creates a new ConditionValidator object.
	 * 
	 * @param condition
	 *        Condition to check.
	 * @param failOn 
	 *        Defines when the Rule fails. A <code>true</code> value indicates
	 *        that the rule throws an exception when the condition is met.
	 *        A <code>false</code> value indicates that the rule throws an exception 
	 *        when the condition is not met.
	 * @param messages
	 *        MessagesBean that fetches the messages.
	 * @param messageKey 
	 *        Message key used to fetch the key from the message bean.  
	 */
	public AbstractConditionValidator
	(boolean failOn, MessagesBean messages, String messageKey) {
		super();
		this.messageKey = messageKey;
		this.messages = messages;
		this.failOn = failOn;
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
		return messages.getString(messageKey, validatedObject); 
	}
	
	@Override
	public void apply() throws RuleException, DataException {
		Condition<T> condition = getCondition();		
		boolean result = condition.check(validatedObject);
		boolean trigger = failOn ? result : !result;		
		if (trigger) {			
			throw new RuleException(getMessage());
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

	@Override
	public void setInput(T input) {
		setValidatedObject(input);		
	}	

}
