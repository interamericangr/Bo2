package gr.interamerican.bo2.utils.adapters;

import gr.interamerican.bo2.utils.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


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
implements Transformation<A, R> {
	
	/**
	 * Class that has the method to invoke.
	 */
	Class<?> clazz;

	/**
	 * method to be invoked.
	 */
	Method method;
	
	/**
	 * Method name.
	 */
	String methodName;
	
	/**
	 * Target object.
	 */
	Object target;
	
	/**
	 * Argument type.
	 */
	Class<?> argumentType;
	
	/**
	 * Creates a new InvokeMethod object. 
	 *
	 * @param clazz
	 *        Class that has a public method that will be invoked.
	 * @param methodName
	 *        name of public method.
	 * @param argumentType
	 *        Argument type of the method. The method must have only one argument.
	 * @param target
	 *        Target object on which the method will be invoked.
	 * 
	 *        
	 */
	public InvokeMethod(Class<?> clazz, String methodName, Class<?> argumentType, Object target) {
		super();
		this.clazz = clazz;
		this.methodName = methodName;
		this.target = target;
		this.argumentType = argumentType;
		try {
			method = clazz.getMethod(methodName, argumentType);		 
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
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
		this(clazz, methodName, argumentType, null);
		if (!Modifier.isStatic(method.getModifiers())) {
			String msg = "Method " + methodName + " is not static"; //$NON-NLS-1$ //$NON-NLS-2$
			throw new RuntimeException(msg);
		}
		
	}
		
	@SuppressWarnings("unchecked")
	public R execute(A a) {
		return (R) ReflectionUtils.invoke(method, target, a);
	}

	
	
}
