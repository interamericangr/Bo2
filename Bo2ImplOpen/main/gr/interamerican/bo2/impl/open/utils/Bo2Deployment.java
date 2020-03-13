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
package gr.interamerican.bo2.impl.open.utils;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.creation.ObjectFactory;
import gr.interamerican.bo2.impl.open.runtime.ProviderImpl;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StreamUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Deployment of a Bo2 system.
 */
public class Bo2Deployment {
	
	/**
	 * JVM user argument to override deployment.properties settings
	 */
	private static final String PATH_TO_MANAGER_ALIASES = "pathToManagerAliases"; //$NON-NLS-1$

	/**
	 * Default logger for Bo2.
	 */
	private static final Logger logger = LoggerFactory.getLogger(Bo2Deployment.class);
	
	/**
	 * Bo2 deployment properties.
	 */
	Properties properties;
	
	/**
	 * Bo2 deployment parameters.
	 */
	Bo2DeploymentParams deploymentBean;
	
	/**
	 * Manager aliases.
	 */
	Map<String, String> managerAliases;
	
	/**
	 * Manager aliases.
	 */
	Map<String, ObjectFactory> managerFactories;

	/**
	 * Creates a new Bo2Deployment object. 
	 * 
	 * @param pathToDeploymentProperties
	 *        Path to the resource file that contains the deployment parameters.
	 *
	 */
	Bo2Deployment(String pathToDeploymentProperties) {
		super();
		
		try {
			properties = CollectionUtils.readProperties(pathToDeploymentProperties);
			deploymentBean = new Bo2DeploymentParams(properties);
			
			/*
			 * Managers initialization.
			 */
			String path = deploymentBean.getPathToManagersList();
			String[] managers = StreamUtils.readResourceFile(path);
			managerFactories = Bo2Utils.getFactoriesMapFromProperties(managers);
			
			loadManagerAliases();
			
			/*
			 * Classes with static initializers to pre-load. OPTIONAL
			 */
			String pathToPreloadedClasses  = deploymentBean.getPathToPreLoadClasses();
			String[] classes = StreamUtils.readResourceFile(pathToPreloadedClasses, true, true);
			if(classes != null) {
				for (String className : classes) {
					loadClass(className);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}			
	}

	/**
	 * Loads manager aliases.
	 */
	private void loadManagerAliases() {
		managerAliases = new HashMap<String, String>();
		String managerAliasesPath = Utils.notNull(System.getProperty(PATH_TO_MANAGER_ALIASES), deploymentBean.getPathToManagerAliases());
		Properties prop = CollectionUtils.readProperties(managerAliasesPath);
		for (Map.Entry<Object, Object> entry: prop.entrySet()) {
			String alias = (String)entry.getKey();
			String name = (String)entry.getValue();
			managerAliases.put(alias, name);
		}
	}
	
	/**
	 * Gets the deployment properties.
	 * 
	 * @return Returns the deployment properties.
	 */
	public Properties getProperties() {
		return properties;
	}

	
	/**
	 * Loads a class.
	 *
	 * @param className the class name
	 * @throws ClassNotFoundException the class not found exception
	 */
	private static void loadClass(String className) throws ClassNotFoundException {
		if (!StringUtils.isNullOrBlank(className)) {
			logger.info("About to load initializer class " + className); //$NON-NLS-1$
			ReflectionUtils.forName(className);
		}
	}

	/**
	 * Gets the deployments parameters bean.
	 * 
	 * @return Returns the deployments parameters bean.
	 */
	public Bo2DeploymentParams getDeploymentBean() {
		return deploymentBean;
	}
	
	/**
	 * Creates a new Provider for this Bo2Deployment.
	 *
	 * @return Returns a new provider.
	 * @throws InitializationException the initialization exception
	 */
	public Provider getProvider() throws InitializationException {		
		return new ProviderImpl	(managerFactories, 
				managerAliases, deploymentBean.getTransactionManagerClass());		
	}
}