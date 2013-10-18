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

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.ResourceWrapper;
import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.exceptions.CouldNotEnlistException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.creation.ObjectFactory;
import gr.interamerican.bo2.creation.beans.ObjectFactoryImpl;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Base implementation of {@link Provider}.
 * 
 * This class realizes a basic implementation of Provider.
 * Different subclasses of Provider are stored in a Map
 * with the Provider subclass as key. The <code>getProvider(subclass)</code>
 * method will try to get a Provider of the specified subclass
 * from the Map. If no such provider is found, then the object
 * will try to create a Provider of this type. 
 */
public class ProviderImpl 
implements Provider {
	
	/**
	 * Named ResourceWrapper managers.
	 */
	Map<String, NamedResourceWrapperManager> managers;
	
	/**
	 * Transaction manager.
	 */
	TransactionManager transactionManager;
	
	/**
	 * Map with aliases for the managers.
	 */
	Map<String, String> managerAliases;
	
	
	/**
	 * Creates a map of ObjectFactories.
	 * 
	 * @param resourceManagerDefPaths
	 * 
	 * @return Returns a map of named object factories.
	 */
	public static Map<String, ObjectFactory> getFactoriesMapFromProperties(String[] resourceManagerDefPaths) {
		Map<String, ObjectFactory> factories = new HashMap<String, ObjectFactory>();
		for (int i = 0; i < resourceManagerDefPaths.length; i++) {	
			Properties p = CollectionUtils.readEnhancedProperties(resourceManagerDefPaths[i]);
			String name = p.getProperty("resourceWrappersManagerName"); //$NON-NLS-1$
			ObjectFactory factory = new ObjectFactoryImpl(p);
			factories.put(name, factory);
		}
		return factories;
	}
	
	
		
	/**
	 * Creates a new AbstractBaseProvider object.
	 * 
	 * This constructor initializes the object. Subclasses
	 * should declare a constructor that calls the super constructor.
	 * 
	 * @param factories 
	 *        Map that maps factories with the names of the resources.
	 * @param managerAliases 
	 *        Map with manager aliases
	 * @param tmClass 
	 *        Class of TransactionManager.
	 * @throws InitializationException 
	 */
	public ProviderImpl(Map<String, ObjectFactory> factories, 
			Map<String, String> managerAliases, String tmClass) 
	throws InitializationException {
		super();
		this.managers =	new HashMap<String, NamedResourceWrapperManager>();
		this.managerAliases = managerAliases;
		
		initializeManagers(factories);
		initializeTransactionManager(tmClass);
	}	
	
	/**
	 * Initializes the managers.
	 * 
	 * @param factories
	 */
	void initializeManagers(Map<String, ObjectFactory> factories) {
		for (Map.Entry<String, ObjectFactory> entry : factories.entrySet()) {
			String name = entry.getKey();
			ObjectFactory factory = entry.getValue();
			NamedResourceWrapperManager manager = new NamedResourceWrapperManager(name, factory);
			managers.put(name, manager);
		}
	}
	
	/**
	 * Initializes the transaction manager. TransactionManager implementations
	 * with no-arg constructors and constructors accepting a single Provider
	 * argument are supported. The latter take precedence.
	 * 
	 * @param tmClass 
	 *        Class of transaction manager.
	 *        
	 * @throws InitializationException 
	 */	
	void initializeTransactionManager(String tmClass) throws InitializationException {
		if (StringUtils.isNullOrBlank(tmClass)) {
			return;
		}
		
		String tmClassLocal = tmClass.trim();
		
		try {
			if(ReflectionUtils.getConstructor(tmClassLocal, Provider.class)!=null) {
				transactionManager = ReflectionUtils.newInstance(tmClassLocal, this);
			} else {
				transactionManager = ReflectionUtils.newInstance(tmClassLocal);
			}
		} catch (RuntimeException e) {
			throw new InitializationException(e);
		}					
	}
	
	
	
	/**
	 * Registers a transactional resource.
	 * 
	 * @param resource 
	 * 
	 * @throws InitializationException 
	 */
	void registerTransactionalResource(ResourceWrapper resource) 
	throws InitializationException {
		if (transactionManager != null) {
			try {
				transactionManager.enList(resource);
			} catch (CouldNotEnlistException e) {
				throw new InitializationException(e);
			}
			
		}
	}
	
	@Override
	public <C extends ResourceWrapper> C getResource(String resourceName, Class<C> subclass) 
	throws InitializationException {
		String managerName = managerAliases.get(resourceName);
		if (managerName==null) {
			managerName = resourceName;
		}
		return getResourceByName(managerName, subclass);
	}	
	
	@Override
	public void close() throws DataException {
		for (NamedResourceWrapperManager manager : managers.values()) {
			manager.close();
		}
		if(transactionManager!=null) {
			transactionManager.close();
			transactionManager = null;
		}
	}
	
	/**
	 * Gets the resource with the specified name and type.
	 * 
	 * @param resourceName
	 *        Name of the resource.
	 * @param subclass
	 *        Type of resource.
	 * @param <C>
	 *        Type of resource.
	 *         
	 * @return Returns the resource of the specified type and name.
	 * 
	 * @throws InitializationException
	 *         If there is no {@link NamedResourceWrapperManager} with the 
	 *         specified name, of if the NamedResourceWrapperManager can't 
	 *         provide the {@link ResourceWrapper} of the specified type.
	 */
	public <C extends ResourceWrapper> C getResourceByName(String resourceName, Class<C> subclass) 
	throws InitializationException {
		NamedResourceWrapperManager manager = managers.get(resourceName);
		if (manager==null) {
			String msg = "Resource " + resourceName + " not found"; //$NON-NLS-1$ //$NON-NLS-2$
			throw new InitializationException(msg);
		}
		C resource = manager.getResource(subclass);		
		registerTransactionalResource(resource);
		return resource;
	}	
	
	
	
	@Override
	public TransactionManager getTransactionManager() {		
		return transactionManager;
	}
	
	/**
	 * Sets an alias for a manager name.
	 * 
	 * @param alias
	 *        Manager alias.
	 * @param name
	 *        Manager name.
	 */
	public void setManagerAlias(String alias, String name) {
		managerAliases.put(alias, name);
	}
	
}
