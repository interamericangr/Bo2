package gr.interamerican.bo2.utils.handlers;

/**
 * ConcreteMethodInvocator is an AbstractMethodInvocator that has
 * th method arguments specified by a setArguments(Object[] args) method.
 */
public class ConcreteMethodInvocator extends AbstractMethodInvocator {
	
	/**
	 * Arguments.
	 */
	Object[] arguments;

	

	/**
	 * Creates a new ConcreteMethodInvocator.
	 * 
	 * @param handler
	 * @param methodName
	 * @param owner
	 */
	public ConcreteMethodInvocator (ExceptionHandler handler, String methodName, Object owner) {
		super(handler, methodName, owner);		
	}
	
	/**
	 * Creates a new ConcreteMethodInvocator with a default exception handler.
	 * 
	 * @param methodName
	 * @param owner
	 */
	public ConcreteMethodInvocator (String methodName, Object owner) {
		this(ThrowingExceptionHandler.INSTANCE, methodName, owner);		
	}

	/**
	 * Sets the arguments.
	 * 
	 */
	@Override
	protected Object[] getArguments() {		
		return arguments;
	}
	
	/**
	 * Sets the arguments.
	 *
	 * @param arguments the arguments to set
	 */
	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	
	

}
