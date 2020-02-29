package gr.interamerican.bo2.impl.open.utils;

import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.creation.ObjectFactory;
import gr.interamerican.bo2.creation.beans.ObjectFactoryImpl;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.Defaults;
import gr.interamerican.bo2.utils.GenericsUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Utility class.
 */
public class Bo2Utils {
	
	/**
	 * Resource wrapper manager name key in object factory configuration properties.
	 */
	public static final String RW_MANAGER_NAME_KEY = "resourceWrappersManagerName"; //$NON-NLS-1$

	/**
	 * Gets the criteria of a {@link CriteriaDependent} object,
	 * if it's not null, otherwise creates a new criteria object.
	 * 
	 * This method does not modify the CriteriaDependent argument. 
	 *
	 * @param <C> the generic type
	 * @param cd        CriteriaDependent to get it's criteria.
	 *        
	 * @return Returns the criteria of the CriteriaDependent argument.
	 *         If it is null, then creates a new valid criteria object
	 *         and returns it.
	 */
	@SuppressWarnings("unchecked")
	public static <C> C getCriteria(CriteriaDependent<C> cd) {
		C c = cd.getCriteria();
		if (c!=null) {
			return c;
		}
		Class<?> cdClass = cd.getClass();
		Class<?> cClass = GenericsUtils.findTypeParameter(cdClass, CriteriaDependent.class, Object.class);		
		c = (C) Defaults.getDefaultValue(cClass);
		if (c==null) {			
			c = (C)Factory.create(cClass);
		}
		return c;
	}

	/**
	 * Creates a map of ObjectFactories.
	 *
	 * @param resourceManagerDefPaths the resource manager def paths
	 * @return Returns a map of named object factories.
	 */
	public static Map<String, ObjectFactory> getFactoriesMapFromProperties(String[] resourceManagerDefPaths) {
		Map<String, ObjectFactory> factories = new HashMap<String, ObjectFactory>();
		for (String resourceManagerDefPath : resourceManagerDefPaths) {	
			Properties p = CollectionUtils.readEnhancedProperties(resourceManagerDefPath);
			String name = p.getProperty(RW_MANAGER_NAME_KEY);
			ObjectFactory factory = new ObjectFactoryImpl(p);
			ObjectFactory existing = factories.put(name, factory);
			if(existing != null) {
				String msg = "Duplicate manager declaration on Bo2 configuration: " + name; //$NON-NLS-1$
				throw new RuntimeException(msg);
			}
		}
		return factories;
	}
}