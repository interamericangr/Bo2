package gr.interamerican.bo2.utils.adapters;

import gr.interamerican.bo2.utils.handlers.ConcreteMethodInvocator;
import gr.interamerican.bo2.utils.handlers.ExceptionHandler;


/**
 * {@link MethodBasedVoidOperation}
 *
 * @param <A>
 */
public class MethodBasedVoidOperation<A> 
implements VoidOperation<A> {
	
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
	public MethodBasedVoidOperation (ExceptionHandler handler, String methodName, Object owner) {
		cmi = new ConcreteMethodInvocator(handler, methodName, owner);		
	}
	
	/**
	 * Creates a new ConcreteMethodInvocator with a default exception handler.
	 * 
	 * @param methodName
	 * @param owner
	 */
	public MethodBasedVoidOperation (String methodName, Object owner) {
		cmi = new ConcreteMethodInvocator(methodName, owner);
	}

	@Override
	public void execute(A a) {
		Object[] args = {a};
		cmi.setArguments(args);
		cmi.execute();
	}

	
	
	
	
	


	
	
	

}
