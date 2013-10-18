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
package gr.interamerican.bo2.utils.reflect.beans;

import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.enums.AccessType;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * Definition of a bean property.
 * 
 * @param <T> 
 *        Type of property.
 */
public class BeanPropertyDefinition<T> extends VariableDefinition<T> {
	
	/**
	 * Class on which the property is defined.
	 */
	Class<?> beanClass;
	
	/**
	 * Property getter.
	 */
	Method getter;
	
	/**
	 * Property setter.
	 */
	Method setter;
	
	/**
	 * Access type.
	 */
	AccessType accessType;
	
	/**
	 * Generic propert type.
	 */
	Type genericType;
	
	/**
	 * Creates a new BeanPropertyDefinition object.
	 * 
	 * @param name
	 *            Property name.
	 * @param type
	 *            Property type.
	 * @param genericType
	 * @param beanClass
	 *            Bean class on which the property belongs.
	 * @param getter
	 *            Getter method.
	 * @param setter
	 *            Setter method.
	 */
	public BeanPropertyDefinition(String name, Class<T> type, Type genericType, Class<?> beanClass, Method getter, Method setter) {
		super(name, type);
		this.beanClass = beanClass;
		this.genericType = genericType;
		this.getter = getter;
		this.setter = setter;
	}
	
	
	/**
	 * Assigns a new value to the accessType.
	 *
	 * @param accessType the accessType to set
	 */
	public void setAccessType(AccessType accessType) {
		this.accessType = accessType;
	}


	/**
	 * Gets the access type.
	 * 
	 * If the {@link BeanPropertyDefinition} has either a getter of a settter,
	 * then the access type depends on the accessors. If there is no accessor
	 * method set for this {@link BeanPropertyDefinition}, then the access type
	 * that was set with <code>setAccessType(accessType)</code> is returned.
	 *
	 * @return Returns the access type
	 */
	public AccessType getAccessType() {
		AccessType access = AccessType.accessType(getter!=null, setter!=null);
		return Utils.notNull(access, accessType);
	}


	/**
	 * Gets the getter.
	 *
	 * @return Returns the getter
	 */
	public Method getGetter() {
		return getter;
	}


	/**
	 * Assigns a new value to the getter.
	 *
	 * @param getter the getter to set
	 */
	public void setGetter(Method getter) {
		this.getter = getter;
	}


	/**
	 * Gets the setter.
	 *
	 * @return Returns the setter
	 */
	public Method getSetter() {
		return setter;
	}


	/**
	 * Assigns a new value to the setter.
	 *
	 * @param setter the setter to set
	 */
	public void setSetter(Method setter) {
		this.setter = setter;
	}


	/**
	 * Gets the beanClass.
	 *
	 * @return Returns the beanClass
	 */
	public Class<?> getBeanClass() {
		return beanClass;
	}
	
	/**
	 * Checks if this property can be supported by a field of the
	 * specified type.
	 * 
	 * @param otherType
	 *        Type being checked.
	 *        
	 * @return Returns true if a field of the specified type can
	 *         be used to implement this property.
	 */
	public boolean isCanBeSupportedBy(Class<?> otherType) {
		if (this.getGenericType() instanceof TypeVariable) {
			return this.getType().isAssignableFrom(otherType);
		}
		return otherType.isAssignableFrom(this.getType());
	}
	
	
	/**
	 * Answers if this property has an abstract getter.
	 * 
	 * @return Returns true if the specified property has a getter
	 *         and it is abstract.
	 */
	public boolean isAbstractGetter() {		
		return isAbstract(getter);		
	}
	
	/**
	 * Answers if this property has an abstract setter.
	 * 
	 * @return Returns true if the specified property has a setter
	 *         and it is abstract.
	 */
	public boolean isAbstractSetter() {		
		return isAbstract(setter);		
	}
	
	/**
	 * Gets the genericType.
	 *
	 * @return Returns the genericType
	 */
	public Type getGenericType() {
		return genericType;
	}
	
	/**
	 * Answers if the specified method is abstract.
	 * 
	 * @param accessor
	 * 
	 * @return Returns true if the specified method is abstract.
	 */
	private boolean isAbstract(Method accessor) {
		if (accessor==null) {
			return false;
		}
		return Modifier.isAbstract(accessor.getModifiers());
	}
	
	/**
	 * Indicates if this property has been declared as a generic property.
	 * 
	 * @return Returns true if the property has been declared as a generic 
	 *         property.
	 */
	public boolean isGeneric() {
		return (genericType instanceof TypeVariable);
	}

}
