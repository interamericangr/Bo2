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
package gr.interamerican.bo2.impl.open.runtime;

import gr.interamerican.bo2.arch.ResourceWrapper;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.creation.ObjectFactory;
import gr.interamerican.bo2.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Named manager of {@link ResourceWrapper} objects.
 * 
 */
public class NamedResourceWrapperManager {
	
	/**
	 * Name of this manager.
	 */
	String name;
	
	/**
	 * Factory that creates resource wrappers.
	 */
	ObjectFactory factory;
		
	/**
	 * Map with all ResourceWrapper implementation types that can be
	 * provided by this provider. 
	 * 
	 * Note that this map, maps ResourceWrapper instances to their classes.
	 * This means that if two provider types are implemented by the same
	 * class, then this map will only contain one entry, that is the 
	 * ResourceWrapper instance as value, and its class as key.
	 * The association between the ResourceWrapper declaration class
	 * and the ResourceWrapper implementation class can be found by
	 * this manager's object factory.  
	 * 
	 * This map contains 
	 */
	Map<Class<? extends ResourceWrapper>, ResourceWrapper> wrappers = 
		new HashMap<Class<? extends ResourceWrapper>, ResourceWrapper>(); 
	
	/**
	 * Creates a new AbstractBaseProvider object.
	 * 
	 * This constructor initializes the object. Subclasses
	 * should declare a constructor that calls the super constructor.
	 * 
	 * @param name 
	 *        Name of this manager.
	 * @param factory 
	 *        Object factory for ResourceWrapper objects.
	 */
	public NamedResourceWrapperManager(String name, ObjectFactory factory) {
		this.name = name;
		this.factory = factory;
	} 
	
	/**
	 * Gets the {@link ResourceWrapper} of the specified class.
	 * 
	 * @param subclass
	 * @param <C>
	 * 
	 * @return Returns the {@link ResourceWrapper} of the specified class.
	 * 
	 * @throws InitializationException
	 *         If this manager can't create the required {@link ResourceWrapper}.
	 */
	@SuppressWarnings("unchecked")
	public <C extends ResourceWrapper> C getResource(Class<C> subclass) 
	throws InitializationException {
		Class<? extends ResourceWrapper> implementationType = 
			(Class<? extends ResourceWrapper>) factory.getImplementationType(subclass);
		if (implementationType==null) {
			@SuppressWarnings("nls")
			String msg = StringUtils.concat(
					"ResourceWrapper type ",
					subclass.getName(),
					" is not supported by the ResourceWrapperManager with name ",
					name);
			throw new InitializationException(msg);
		}
		ResourceWrapper wrapper = wrappers.get(implementationType);
		if (wrapper == null) {
			wrapper = createResourceWrapper(subclass);
			wrappers.put(implementationType, wrapper);
		}		
		return (C)wrapper;
	}	
	
	/**
	 * Creates a new {@link ResourceWrapper} of a specific type.
	 * 
	 * @param type 
	 *        Specific ResourceWrapper sub-type.
	 * 
	 * @return Returns a ResourceWrapper of the specified type.
	 * 
	 * @throws InitializationException
	 *         If the creation fails.
	 */
	ResourceWrapper createResourceWrapper(Class<? extends ResourceWrapper> type) 
	throws InitializationException {
		ResourceWrapper wrapper;
		try {
			wrapper = factory.create(type);			
		} catch (RuntimeException rte) {
			if (rte.getCause()!=null) {
				throw new InitializationException(rte.getCause());				
			} else {
				throw new InitializationException(rte);
			}			
		}
		if (wrapper==null) {
			String msg = "Could not create ResourceWrapper"; //$NON-NLS-1$
			throw new InitializationException(msg);
		}
		return wrapper;
	}
	/**
	 * Closes all resource wrappers.
	 * 
	 * @throws DataException
	 */
	public void close() throws DataException {
		for (ResourceWrapper wrapper : wrappers.values()) {
			wrapper.close();
		}		
	}
	
	
	
}
