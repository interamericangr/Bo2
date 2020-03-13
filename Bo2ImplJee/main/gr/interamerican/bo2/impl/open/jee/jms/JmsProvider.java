package gr.interamerican.bo2.impl.open.jee.jms;

import gr.interamerican.bo2.arch.ResourceWrapper;
import gr.interamerican.bo2.arch.exceptions.InitializationException;

/**
 * {@link ResourceWrapper} for JMS.
 */
public interface JmsProvider extends ResourceWrapper {
	
	/**
	 * Gets the JMS resources for the specified name.
	 *
	 * @param name the name
	 * @return JmsResource
	 * @throws InitializationException the initialization exception
	 */
	JmsResource getResource(String name) throws InitializationException;
	
}
