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
package gr.interamerican.bo2.impl.open.annotations;

import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.utils.TokenUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Utilities about Bo2 annotations.
 */
public class Bo2AnnoUtils {
	
	/**
	 * Makes it possible to set or change the managerName
	 * for a class. 
	 */
	private static Map<Class<?>, String> overridenManagers = 
		new HashMap<Class<?>, String>();
	
	/**
	 * Hidden constructor of a utility class. 
	 *
	 */
	private Bo2AnnoUtils() {/* empty */}
	
	/**
	 * Sets the manager name for the specified class.
	 * 
	 * This can be used to set the manager name for a class that can't be
	 * modified in order to annotate it with a {@link ManagerName} annotation.
	 *  
	 * @param clazz
	 *        Class to set the manager name. 
	 * @param managerName
	 *        Manager name.
	 */
	public static void setManagerName(Class<?> clazz, String managerName) {
		overridenManagers.put(clazz, managerName);
	}
	
	/**
	 * Sets the manager name of a class to be the same as the manager name
	 * of another class.
	 * 
	 * @param getTheManager
	 *        The manager name is read from this class. 
	 * @param setTheManager 
	 *        The manager name of the <code>getManager</code> class
	 *        is set to this class.
	 */
	public static void copyManagerName(Class<?> getTheManager, Class<?> setTheManager) {
		String manager = getManagerName(getTheManager);
		if (manager!=null) {
			setManagerName(setTheManager, manager);			
		}
	}

	/**
	 * Gets the value of the {@link ManagerName} annotation that has been
	 * set on the specified class, or its package.
	 * 
	 * @param clazz 
	 *        Class. 
	 * 
	 * @return Returns the value of the ManagerName annotation set to the
	 *         specified class or its package. If neither the specified 
	 *         not its package have the ManagerName annotation, then 
	 *         returns null.
	 */
	public static String getManagerName(Class<?> clazz) {
		if (!isClassWithManager(clazz)) {			
			return null;
		}
		
		String managerName = overridenManagers.get(clazz);
		if (managerName!=null) {
			return managerName;
		}
		
		ManagerName anno; 
		anno = clazz.getAnnotation(ManagerName.class);		
		if (anno!=null) {
			return anno.value();
		}
		Package pack = clazz.getPackage();
		anno = pack.getAnnotation(ManagerName.class);
		if (anno!=null) {			
			return anno.value();
		}
		Class<?>[] implementedInterfaces = clazz.getInterfaces();
		if (implementedInterfaces!=null) {
			for (Class<?> inter : implementedInterfaces) {
				String managerOfInterface = getManagerName(inter);
				if (managerOfInterface != null) {
					return managerOfInterface;
				}
			}
		}
		
		Class<?> supertype = clazz.getSuperclass();		
		if (supertype!=null) {			
			return getManagerName(supertype);
		}
		
		return null;
	}
	
	/**
	 * Checks if the specified class needs a manager.
	 * 
	 * @param clazz
	 * 
	 * @return Returns true if clazz has a manager.
	 */
	private static boolean isClassWithManager(Class<?> clazz) {
		return PersistentObject.class.isAssignableFrom(clazz)
			|| Worker.class.isAssignableFrom(clazz);
	}
	
	/**
	 * Gets the names of the key properties of the specified class.
	 * 
	 * @param clazz
	 *        Class for which the key properties are retrieved.
	 * 
	 * @return Returns an array with the property names that have
	 *         been set in the {@link KeyProperties} annotation of 
	 *         the specified class. If the specified class is not
	 *         marked with the KeyProperties annotation, then returns
	 *         null.
	 */
	public static String[] getKeyProperties(Class<?> clazz) {
		KeyProperties anno;
		anno = clazz.getAnnotation(KeyProperties.class);		
		if (anno!=null) {
			return TokenUtils.tokenize(anno.value());
		}		
		return null;
	}
	
	/**
	 * Gets an array that contains the names of a class' parameters
	 * as defined by a {@link ParametersOrder} annotation.
	 * 
	 * @param clazz
	 * 
	 * @return Returns the names of the parameters.
	 */
	public static String[] getParameterNames(Class<?> clazz) {
		ParametersOrder anno = clazz.getAnnotation(ParametersOrder.class);
		if (anno!=null) {			
			return TokenUtils.tokenize(anno.value());			
		}
		Class<?> supertype = clazz.getSuperclass();
		if (supertype!=null) {
			return getParameterNames(supertype);
		}
		return null;
	}
	
	
	
	
	

}
