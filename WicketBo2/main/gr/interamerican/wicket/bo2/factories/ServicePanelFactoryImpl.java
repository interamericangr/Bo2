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
package gr.interamerican.wicket.bo2.factories;

import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.impl.open.utils.Util;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.wicket.factories.ServicePanelFactory;
import gr.interamerican.wicket.factories.ServicePanelFixtureResolver;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanelDef;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link ServicePanelFactory}.
 * 
 * This Factory expects to find a key with name pathToDefaultPanelFactoryDefinition
 * in the Bo2 deployment parameters file. The key's value is a file that contains
 * the resource path of all properties files that declare associations of panelIds
 * and ServicePanel implementation classes.
 */
public class ServicePanelFactoryImpl implements ServicePanelFactory {
	
	/**
	 * Logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(ServicePanelFactoryImpl.class);
	
	/**
	 * Service panel associations found in the current deployment.
	 * This associates panelId --> panel implementation class.
	 */
	private static Map<String, Class<?>> servicePanelAssociations;
	
	/**
	 * Find and prepare the service panel associations of the current deployment. 
	 */
	static {
		servicePanelAssociations = new HashMap<String, Class<?>>();
		try {
			String definitionPath = Bo2.getDefaultDeployment().
				getDeploymentBean().getPathToDefaultPanelFactoryDefinition();
			String[] paths = Util.readFile(definitionPath);		
			for (int i = 0; i < paths.length; i++) {
				String path = paths[i].trim();
				if(path.length()>0) {
					Properties properties = loadPropertiesIfAvailable(path);
					for (Map.Entry<Object, Object> entry : properties.entrySet()) {
						String left = StringUtils.trim((String) entry.getKey());			
						String right = StringUtils.trim((String) entry.getValue());
						Class<?> panelClass = loadClassIfPossible(right);
						if(panelClass != null) {
							servicePanelAssociations.put(left, panelClass);
						}
					}
				}
			}	
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	/**
	 * Loads a class if it is available in the classpath. If unavailable,
	 * a warning is printed. This is not normally acceptable and should be 
	 * considered a fatal error when in production. 
	 * 
	 * @param name
	 * @return panel implementation class with the supplied name
	 */
	@SuppressWarnings("nls")
	static Class<?> loadClassIfPossible(String name) {
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException cnfe) {
			String msg = StringUtils.concat(
					"Non existant class name: ",
					name,
					". This is acceptable for unit tests, but FATAL in every other case and should be investigated.");
			LOGGER.error(msg);
		}
		return null;
	}
	
	/**
	 * Loads a properties file resource, if it is available. If unavailable,
	 * a warning is printed. This is not normally acceptable and should be 
	 * considered a fatal error when in production. 
	 * 
	 * @param path
	 *        Resource path.
	 * 
	 * @return loaded properties.
	 */
	@SuppressWarnings("nls")
	static Properties loadPropertiesIfAvailable(String path) {
		try {
			Properties p = CollectionUtils.readProperties(path);
			return p;
		} catch(RuntimeException rtex) {
			if(ExceptionUtils.isCausedBy(rtex, IOException.class)) {
				String msg = StringUtils.concat(
						"Non existant mappings file: ",
						path,
						". This is acceptable for unit tests, but FATAL in every other case and should be investigated.");
				LOGGER.error(msg);
			} else {
				throw rtex;
			}
		}
		return new Properties();
	}
	
	/**
	 * Fixture resolver (for tests).
	 */
	ServicePanelFixtureResolver resolver = new DefaultServicePanelFixtureResolver();

	@SuppressWarnings("unchecked")
	public <P extends ServicePanel> P createPanel(ServicePanelDef definition) {
		String panelId = definition.getPanelId();
		
		P fixture = getFixtureResolver().resolveFixture(panelId);
		if(fixture != null) { //possible fixture
			return fixture;
		}
		
		Class<P> panelCls = (Class<P>) servicePanelAssociations.get(panelId);
		if(panelCls == null) {
			creationFailed(definition);
		}
		
		Constructor<P> constructor = null;
		Constructor<P>[] constructors = (Constructor<P>[])panelCls.getConstructors();
		for (Constructor<P> c : constructors) {
			if(c.getParameterTypes().length==1 && ServicePanelDef.class.isAssignableFrom(c.getParameterTypes()[0])) {
				constructor = c;
				break;
			}
		}
		if(constructor==null) {
			creationFailed(definition);
		}
		
		return ReflectionUtils.newInstance(constructor, definition);
	}
	
	/**
	 * Failure to create. Throws a RuntimeException.
	 * 
	 * @param def
	 */
	@SuppressWarnings("nls")
	private void creationFailed(ServicePanelDef def) {
		String msg = StringUtils.concat(
				"Failed to create panel with input definition of type ",
				def.getClass().getName() + " and panelId " + def.getPanelId());
		throw new RuntimeException(msg);
	}

	public ServicePanelFixtureResolver getFixtureResolver() {
		return resolver;
	}

}
