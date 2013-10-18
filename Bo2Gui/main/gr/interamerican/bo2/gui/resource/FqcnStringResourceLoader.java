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
package gr.interamerican.bo2.gui.resource;

import gr.interamerican.bo2.gui.util.Bo2GuiUtils;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.Utils;

import java.awt.Component;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Loads resources based on the component class name. The resource is queried
 * from resource bundles that exist on the component's class package and have
 * the same name with the component's class.
 * <br/>
 * Priority is given to resource bundles of components that are higher in the
 * GUI hierarchy. This allows the author of a component to set default values
 * for the resource keys, but a developer re-using this component in another
 * application to override the values of these resource keys in the resource
 * bundle of any container that encloses this component.
 */
public class FqcnStringResourceLoader implements StringResourceLoader {

	/**
	 * Internal cache.
	 */
	static Map<String, Properties> cache = new HashMap<String, Properties>();
	
	/**
	 * Singleton.
	 */
	static FqcnStringResourceLoader SINGLETON = new FqcnStringResourceLoader();
	
	@Override
	public String loadStringResource(Component component, String key) {
		return Utils.notNull(loadStringResource0(component, key), key);
	}
	
	/**
	 * Internal implementation.
	 * @param component 
	 * @param key 
	 * @return Located resource.
	 */
	String loadStringResource0(Component component, String key) {
		List<Component> topDownHierarchy = Bo2GuiUtils.getContainerTopDownHierarchy(component);
		for(Component c : topDownHierarchy) {
			String candidate = (String) getBundle(c.getClass().getName()).get(key);
			if(candidate!=null) {
				return candidate;
			}
		}
		return null;
	}
	
	/**
	 * Gets the bundle for the fqcn.
	 * 
	 * @param fqcn
	 * 
	 * @return resource bundle.
	 */
	Properties getBundle(String fqcn) {
		return Utils.notNull(cache.get(fqcn), createBundleForFqcn(fqcn));
	}
	
	/**
	 * Creates and caches the bundle.
	 * 
	 * @param fqcn
	 * 
	 * @return bundle.
	 */
	Properties createBundleForFqcn(String fqcn) {
		Properties p;
		try {
			String propertiesPath = StringConstants.SLASH + fqcn.replace(StringConstants.DOT, StringConstants.SLASH) + ".properties"; //$NON-NLS-1$
			p = CollectionUtils.readProperties(propertiesPath);
		} catch (RuntimeException e) {
			boolean wasIOE = e.getCause() instanceof IOException; 
			if(!wasIOE) {
				throw e;
			}
			/*
			 * try once more with XML
			 */
			try {
				String xmlPath = StringConstants.SLASH + fqcn.replace(StringConstants.DOT, StringConstants.SLASH) + ".xml"; //$NON-NLS-1$
				p = CollectionUtils.readPropertiesFromXML(xmlPath);
			}catch (RuntimeException e2) {
				return new Properties();
			}
		}
		cache.put(fqcn, p);
		return p;
	}
	
	/**
	 * @return Singleton accessor.
	 */
	public static FqcnStringResourceLoader get() {
		return SINGLETON;
	}

}
