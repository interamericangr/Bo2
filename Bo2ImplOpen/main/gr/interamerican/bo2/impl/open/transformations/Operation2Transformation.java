package gr.interamerican.bo2.impl.open.transformations;

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * Adapts an {@link Operation} to a {@link Transformation}.
 * 
 * The {@link Transformation#execute(Object)} method of this class,  
 * invokes the {@link Operation#execute()} method of the Operation.
 * The input arguments of the operation are transfered by copying
 * the <code>inputProperties</code> from the input object to the 
 * operation. <br/>
 * The return object of the <code>execute(object)</code> method
 * is determined by the <code>outputProperty</code> property. <br/>
 * The operation must be ready for execution. The operation methods
 * declared by the {@link Worker} interface are not invoked by the
 * {@link Operation2Transformation}. <br/>
 * Possible {@link LogicException} or {@link DataException} thrown by
 * the operation's <code>execute()</code> method are wrapped in a 
 * {@link RuntimeException} that has the thrown exception as cause. 
 * 
 * @param <A>
 *        Type of operation argument.
 * @param <R> 
 *        Type of operation result.
 */
public class Operation2Transformation<A, R>
implements Transformation<A, R> {
	
	/**
	 * Creates a new Operation2Transformation object. 
	 *
	 * @param operation
	 * @param inputProperties
	 * @param outputProperty
	 */
	public Operation2Transformation(Operation operation, String outputProperty, String... inputProperties) {
		super();
		this.operation = operation;
		this.inputProperties = inputProperties;
		this.outputProperty = outputProperty;
	}

	/**
	 * Operation.
	 */
	Operation operation;
	
	/**
	 * Names of input properties.
	 */
	String[] inputProperties;
	
	/**
	 * Name of output property.
	 */
	String outputProperty;
	
	
	/**
	 * Executes the operation.
	 */
	void executeOperation() {
		try {
			operation.execute();
		} catch (LogicException le) {
			throw new RuntimeException(le);
		} catch (DataException de) {
			throw new RuntimeException(de);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public R execute(A a) {
		Object result = null;
		if (inputProperties.length>0) {
			ReflectionUtils.copyProperties(a, operation, inputProperties);
		}
		executeOperation();
		if (outputProperty!=null) {
			result = ReflectionUtils.getProperty(outputProperty, operation);
		}
		return (R) result;
	}
	
	

}
