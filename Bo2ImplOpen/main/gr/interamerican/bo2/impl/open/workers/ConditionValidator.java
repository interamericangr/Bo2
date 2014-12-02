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
 * @param <T> 
 *        Type of object being validated.
 */
public class ConditionValidator<T> 
extends AbstractResourceConsumer 
implements Rule, Input<T> {
	
	

	/**
	 * Condition to check.
	 */
	Condition<T> condition;
	
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
	public ConditionValidator
	(Condition<T> condition, boolean failOn, MessagesBean messages, String messageKey) {
		super();
		this.messageKey = messageKey;
		this.condition = condition;
		this.messages = messages;
		this.failOn = failOn;
	}
	
	
	/**
	 * Creates a new ConditionValidator object.
	 * 
	 * The ConditionValidator throws an exception fails if the condition fails.  
	 *
	 * @param message
	 *        Message for RuleExceptions 
	 * @param condition
	 *        Condition to check.
	 */
	public ConditionValidator(String message, Condition<T> condition) {
		this(condition, false, null, message);		
	}
	
	
	/**
	 * Gets the message.
	 * 
	 * @return Returns the message.
	 */
	String getMessage() {
		if (messages==null) {
			return messageKey;
		}
		return messages.getString(messageKey, validatedObject); 
	}
	
	@Override
	public void apply() throws RuleException, DataException {
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
