package gr.interamerican.bo2.utils.adapters.trans;

import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.exc.ExceptionHandler;
import gr.interamerican.bo2.utils.handlers.ConcreteMethodInvocator;

/**
 * {@link MethodBasedTransformation}
 *
 * @param <A>
 * @param <R> 
 */
public class MethodBasedTransformation<A, R> 
implements Transformation<A, R> {
	
	/**
	 * Method invocator.
	 */
	ConcreteMethodInvocator cmi;
	
	/**
	 * Creates a new ConcreteMethodInvocator.
	 * 
	 * @param handler
	 * @param methodName
	 * @param owner
	 */
	public MethodBasedTransformation (ExceptionHandler handler, String methodName, Object owner) {
		cmi = new ConcreteMethodInvocator(handler, methodName, owner);		
	}
	
	/**
	 * Creates a new ConcreteMethodInvocator with a default exception handler.
	 * 
	 * @param methodName
	 * @param owner
	 */
	public MethodBasedTransformation (String methodName, Object owner) {
		cmi = new ConcreteMethodInvocator(methodName, owner);
	}

	@Override
	public R execute(A a) {
		Object[] args = {a};
		cmi.setArguments(args);
		@SuppressWarnings("unchecked")
		R r = (R) cmi.invoke(); 
		return r;
	}
	
	

}
