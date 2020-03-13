package gr.interamerican.bo2.impl.open.workers;

import java.util.Collection;

import gr.interamerican.bo2.arch.utils.beans.Invoker;

/**
 * Base class for business logic layer objects (rules and operations). <br>
 */
@SuppressWarnings("deprecation")
public abstract class AbstractBusinessLogic extends AbstractResourceConsumer {
	
	/**
	 * Invoker.
	 * 
	 * @deprecated Use Native Java8 Functionality
	 *             {@link Collection#forEach(java.util.function.Consumer)}
	 */
	@Deprecated
	protected Invoker invoker;

	/**
	 * Creates a new AbstractOperation object.
	 */
	public AbstractBusinessLogic() {
		super();
		invoker = new Invoker(this);	
	}


}
