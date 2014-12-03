package gr.interamerican.bo2.impl.open.workers;

import gr.interamerican.bo2.arch.Rule;
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
extends AbstractConditionValidator<T> 
implements Rule, Input<T> {
	
	

	/**
	 * Condition to check.
	 */
	Condition<T> condition;
	
	
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
		super(failOn,messages,messageKey);		
		this.condition = condition;
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
	
	@Override
	protected Condition<T> getCondition() {	
		return condition;
	}

}
