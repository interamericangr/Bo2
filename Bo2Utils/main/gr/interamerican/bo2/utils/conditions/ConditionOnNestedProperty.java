package gr.interamerican.bo2.utils.conditions;

import org.apache.commons.beanutils.NestedNullException;

/**
 * Checks a condition on a nested property of the specified object.
 * 
 * This condition delegates the check to a condition that checks the
 * value of a property of the specified object. The property does not
 * need to be a nested one. If the property is simple, then the
 * behavior of this class is exactly the same as of its superclass. 
 * This class has different behavior than {@link ConditionOnProperty}
 * in cases where a {@link NestedNullException} is thrown on accessing 
 * the nested property. In these cases, this exception does not rethrow
 * the thrown exception, but returns <code>false</code> instead.   
 * 
 * @param <T> 
 *        Type of object being checked by the condition.
 * 
 */
public class ConditionOnNestedProperty<T> extends ConditionOnProperty<T> {
	
	/**
	 * Creates a new ConditionOnProperty object. 
	 * 
	 * @param property 
	 *        Property name.
	 * @param clazz
	 *        Type of argument.
	 * @param condition
	 *        Condition to check on the property. This condition must be
	 *        applicable to the type of the specified property.
	 */
	public ConditionOnNestedProperty(String property, Class<T> clazz, Condition<?> condition) {		
		super(property, clazz, condition);
	}
	
	@Override
	public boolean check(T t) {	
		try {
			return super.check(t);
		} catch (NestedNullException e) {
			return false;
		}
	}

	

}
