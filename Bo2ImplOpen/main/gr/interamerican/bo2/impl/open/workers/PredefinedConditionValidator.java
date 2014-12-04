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
public abstract class PredefinedConditionValidator<T> 
extends DynamicConditionValidator<T> 
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
	 * @param validatedObjectProperty 
	 *        Property name of the validated object.        
	 */
	protected PredefinedConditionValidator (Condition<T> condition, boolean failOn, 
			MessagesBean messages, String messageKey, String validatedObjectProperty) {
		super(failOn,messages,messageKey,validatedObjectProperty);		
		this.condition = condition;
	}
	
	@Override
	protected Condition<T> getCondition() {	
		return condition;
	}

}
