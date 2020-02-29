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
package gr.interamerican.bo2.utils.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This bean provides an easy solution to selecting a value
 * that depends on an object being an instance of a type.
 * 
 * @param <T>
 *        Type of selection.
 */
public class TypeBasedSelection<T> implements Serializable {
	
	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * maps the classes with the selections.
	 */
	Map<Class<?>, T> selections = new HashMap<Class<?>, T>();
	
	/**
	 * Registers a selection for a class.
	 *
	 * @param clazz the clazz
	 * @param t the t
	 */
	public void registerSelection(Class<?> clazz, T t) {
		selections.put(clazz, t);
	}
	
	/**
	 * Gets the selection for a class.
	 * 
	 * If a selection is not explicitly defined for the specified type,
	 * this method attempts to find a registered selection for the interfaces
	 * declared on this type in the order these are returned by {@link Class#getInterfaces()}.
	 * 
	 * If no match is found, the process is repeated for the superClass of the type.
	 *
	 * @param clazz the clazz
	 * @return Returns the selection associated with this class.
	 * @see Class#getInterfaces()
	 */
	public T selectionForType(Class<?> clazz) {
		T t = selections.get(clazz);
		if (t!=null) {
			return t;
		}		
		Class<?>[] interfaces = clazz.getInterfaces();
		if (interfaces!=null) {
			for (Class<?> intrfc : interfaces) {
				t = selectionForType(intrfc);
				if (t!=null) {					
					return t;
				}
			}
		}
		Class<?> superType = clazz.getSuperclass();
		if (superType!=null) {
			t = selectionForType(superType);			
		}
		return t;
	}
	
	/**
	 * Gets the selection for the specified object.
	 *
	 * @param object the object
	 * @return Returns the selection that matches to the class
	 *         of the specified object.
	 * @see #selectionForType(Class)
	 */
	public T select(Object object) {
		if (object==null) {
			return null;
		}
		Class<?> clazz = object.getClass();
		T t = selectionForType(clazz);
		if (t!=null) {
			registerSelection(clazz, t);
		}
		return t;
	}
	
	
	
	
	


}
