package gr.interamerican.bo2.utils.conditions;

import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.adapters.trans.InvokeMethod;

/**
 * Condition that is based on the invocation of a method that returns a boolean.
 * 
 * @param <T> 
 *        Type of object checked by the condition.
 *        This must be the argument type of the boolean method.
 */
public class InvokeMethodCondition<T> 
extends InvokeMethod<T, Boolean>
implements Condition<T> {

	/**
	 * Creates a new InvokeMethodCondition object.
	 * 
	 * This constructor will create a condition that invokes a boolean
	 * method on the specified <code>target</code> object. 
	 *
	 * @param target        Target object on which the method will be invoked.
	 * @param methodName        name of public method.
	 * @param argumentType        Argument type of the method. The method must have only one argument.
	 */
	public InvokeMethodCondition(Object target, String methodName, Class<?> argumentType) {
		super(target, methodName, argumentType);
	}

	/**
	 * Creates a new InvokeMethodCondition object . 
	 * 
	 * This constructor will create a condition that invokes a static boolean
	 * method on the specified class. 
	 *
	 * @param clazz
	 *        Class that has a public static method that will be invoked.
	 * @param methodName
	 *        name of public static method.
	 * @param argumentType
	 *        Argument type of the method. The method must have only one argument.
	 */
	public InvokeMethodCondition(Class<?> clazz, String methodName, Class<?> argumentType) {
		super(clazz, methodName, argumentType);
	}

	@Override
	public boolean check(T t) {
		Boolean b = execute(t);
		b = Utils.notNull(b, Boolean.FALSE);
		return b;
	}

	

}
