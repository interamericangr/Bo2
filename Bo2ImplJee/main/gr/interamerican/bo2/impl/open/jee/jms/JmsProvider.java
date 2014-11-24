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
	 * @param name
	 * 
	 * @return JmsResource
	 * 
	 * @throws InitializationException
	 */
	JmsResource getResource(String name) throws InitializationException;
	
}
