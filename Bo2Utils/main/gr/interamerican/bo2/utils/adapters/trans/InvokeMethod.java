package gr.interamerican.bo2.utils.adapters.trans;

import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.exc.ThrowingExceptionHandler;
import gr.interamerican.bo2.utils.handlers.AbstractMethodInvocator;

import java.lang.reflect.Method;


/**
 * {@link InvokeMethod} adapts the invocation of a {@link Method}
 * to the {@link Transformation} interface.
 * 
 * 
 * @param <A>
 *        Type of operation argument.
 * @param <R> 
 *        Type of operation result.
 */
public class InvokeMethod<A, R>
extends AbstractMethodInvocator
implements Transformation<A, R> 
{
	/**
	 * Arguments.
	 */
	Object[] arguments;
	
	
	/**
	 * Creates a new InvokeMethod object. 
	 *
	 * @param target        Target object on which the method will be invoked.
	 * @param methodName        name of public method.
	 * @param argumentType        Argument type of the method. The method must have only one argument.
	 */
	public InvokeMethod(Object target, String methodName, Class<?> argumentType) {
		super(new ThrowingExceptionHandler(), methodName, target, argumentType);
	}
	
	/**
	 * Creates a new InvokeMethod object for a static method. 
	 *
	 * @param clazz
	 *        Class that has a publicstatic  method that will be invoked.
	 * @param methodName
	 *        name of public static method.
	 * @param argumentType
	 *        Argument type of the method. The method must have only one argument.
	 *        
	 */
	public InvokeMethod(Class<?> clazz, String methodName, Class<?> argumentType) {
		super(new ThrowingExceptionHandler(), methodName, clazz, argumentType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public R execute(A a) {
		Object[] args = {a};
		arguments = args;
		return (R) invoke();
	}

	@Override
	protected Object[] getArguments() {		
		return arguments;
	}
		
	

	
	
}
