/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 ******************************************************************************/
package gr.interamerican.bo2.impl.open.workers;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.ResourceWrapper;
import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.annotations.Bo2AnnoUtils;
import gr.interamerican.bo2.utils.StringUtils;

/**
 * Resource consumer that knows the name of its resource wrapper
 * manager.
 */
public class AbstractResourceConsumer extends AbstractBaseWorker {
	/**
	 * Creates a new AbstractResourceConsumer object. 
	 *
	 */
	public AbstractResourceConsumer() {
		super();
		managerName = Bo2AnnoUtils.getManagerName(this.getClass());
	}

	/**
	 * Name of resource manager.
	 */
	protected String managerName;
	
	/**
	 * Gets the name of the ResourceWrapper manager that will
	 * provide this worker with the appropriate resources.
	 * 
	 * @return Returns the name of the resource manager.
	 */
	public String getManagerName() {
		return managerName;
	}
	
	/**
	 * Sets the name of the ResourceWrapper manager that will
	 * provide this worker with the appropriate resources.
	 * 
	 * This method is provided, in order to support a case, where
	 * an {@link AbstractResourceConsumer} needs to set the resource
	 * manager name to some of its children.  
	 * 
	 * @param managerName 
	 *        The managerName to set.
	 */
	public void setManagerName(String managerName) {		
		this.managerName = managerName;
	}
	
	/**
	 * Gets the specified resource.
	 * 
	 * @param <R>
	 * @param clazz
	 * @return Returns the resource.
	 * 
	 * @throws InitializationException 
	 */
	@SuppressWarnings("nls")
	protected <R extends ResourceWrapper>
	R getResource(Class<R> clazz) throws InitializationException {
		String resourceName = getManagerName();
		if (resourceName==null) {
			throw new InitializationException(managerNameNotSet());
		}
		try {
			R r = this.getProvider().getResource(resourceName, clazz);
			return r;
		} catch (InitializationException e) {
			String msg = StringUtils.concat(
					"Failed to create resource " + clazz.getName(),
					" for worker " + this.getClass().getName(),
					" on manager " + resourceName);
			throw new InitializationException(msg, e);
		}
	}
	
	/**
	 * Creates a message that indicates that the manager name has not
	 * been defined .
	 * 
	 * @return Returns the message.
	 */
	protected String managerNameNotSet() {
		return managerNameNotSetToClass(this.getClass());
	}
	
	/**
	 * Creates a message that indicates that the manager name has not
	 * been defined for a class.
	 * 
	 * @param clazz
	 * 
	 * @return Returns the message.
	 */
	protected String managerNameNotSetToClass(Class<?> clazz) {
		@SuppressWarnings("nls")
		String msg = StringUtils.concat(
			"ManagerName annotation not set to class ",
			clazz.getName(),
			" or package ",
			clazz.getPackage().getName());
		return msg;
	}
	
	@Override
	public void init(Provider parent) throws InitializationException {
		for (Worker child : getChildren()) {
			if (child instanceof AbstractResourceConsumer) {
				AbstractResourceConsumer consumer = (AbstractResourceConsumer) child;
				String managerNm = getManagerName();
				if (consumer.getManagerName()==null && managerNm!=null) {
					consumer.setManagerName(managerNm);
				}
			}
			child.init(parent);
		}
		provider = parent;
		initialized = true;
	}
	
	

}
