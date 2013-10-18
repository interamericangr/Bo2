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
package gr.interamerican.bo2.impl.open.po;

import gr.interamerican.bo2.arch.Key;
import gr.interamerican.bo2.impl.open.utils.Exceptions;
import gr.interamerican.bo2.utils.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * Implementation of {@link Key} that is based on an array of {@link Method}
 * objects that are invoked on an object.
 * 
 * The values returned by the array gives the implementation of the
 * <code>getElements()</code> method of the superclass {@link AbstractKey}. 
 * The methods used to get the element values must not take any argument.
 */
public class MethodsBasedKey 
extends AbstractKey {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Invalid method message key.
	 */
	private static final String INVALID_METHOD = 
		"FieldsBasedKey.INVALID_METHOD"; //$NON-NLS-1$
	
	/**
	 * Target invocation.
	 */
	private Object target;
	
	/**
	 * Methods.
	 */
	private Method[] methods;
	
	/**
	 * Creates a new FieldsBasedKey object.
	 *  
	 * @param child type of the concrete Key sub-type
	 * @param properties Properties used to get the key elements. 
	 * @param target Target object for the invocation of the methods.
	 */
	public MethodsBasedKey(Class<?> child, String[] properties, Object target) {
		super();
		this.target = target;
		methods = ReflectionUtils.getPropertyGetters(properties, child);
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getGenericParameterTypes().length!=0 ) {
				throw Exceptions.runtime(INVALID_METHOD,methods[i]);
			}
		}
	}
	
	/**
	 * Creates a new MethodsBasedKey object.
	 * 
	 * The target object for the invocation of the methods is
	 * going to be this object. 
	 * 
	 * @param child type of the concrete Key sub-type
	 * @param properties the contents of the @Keyproperties annotation on Key interface
	 *
	 */
	public MethodsBasedKey(Class<?> child, String[] properties) {
		this(child, properties, null);
		this.target = this;
	}
	
	
	@Override
	protected Object[] getElements() {		
		Object[] elements = new Object[methods.length];
		for (int i = 0; i < methods.length; i++) {			
			if (methods[i]==null) {
				elements[i]=null;
			} else {
				Object[] args = null;
				elements[i] = ReflectionUtils.invoke(methods[i], this.target, args); 
			}			
		}
		
		return elements;
	}

}
