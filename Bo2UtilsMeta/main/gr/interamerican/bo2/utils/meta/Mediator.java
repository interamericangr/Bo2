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
package gr.interamerican.bo2.utils.meta;

import gr.interamerican.bo2.utils.JavaBeanUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.exceptions.MultipleValidationsException;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link Mediator} is the intermediate that gets or sets the values
 * of properties described by {@link BoPropertyDescriptor} objects.
 */
public class Mediator {
	
	/**
	 * logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Mediator.class);
		
	/**
	 * Message key for wrong descriptor.
	 */
	private static final String INVALID_DESCRIPTOR = 
		Mediator.class.getName()+".invalidDescriptor"; //$NON-NLS-1$
	
	/**
	 * Singleton instance.
	 */
	private static Mediator instance = new Mediator();
	
	/**
	 * Stores names of classes who's properties have bean loaded on the
	 * <code>propertyDescriptors</code> map.
	 */
	HashMap<String, Class<?>> loadedClasses = new HashMap<String, Class<?>>();
	
	/**
	 * Maps fullyQualifiedNames of properties to PropertyDescriptor
	 * objects.
	 */
	HashMap<String, PropertyDescriptor> propertyDescriptors = 
		new HashMap<String, PropertyDescriptor>();
	
	/**
	 * Gets the instance.
	 *
	 * @return Returns the instance
	 */
	public static Mediator getInstance() {
		return instance;
	}

	/**
	 * Private constructor. 
	 */
	private Mediator() {		
		super();
	}
	
	/**
	 * Gets the PropertyDescriptor that describes the specified property.
	 * 
	 * @param boPD {@link BoPropertyDescriptor}.
	 * @return Returns the PropertyDescriptor.
	 */
	private synchronized PropertyDescriptor getPropertyDescriptor(BoPropertyDescriptor<?> boPD) {
		String name = boPD.getFullyQualifiedName();
		
		PropertyDescriptor pd = propertyDescriptors.get(name);
		if (pd==null) {
			String className = boPD.getFullyQualifiedClassName();
			if (loadedClasses.get(className)!=null) {
				invalid(boPD);
			}
			loadPropertiesOfClass(className);
		}
		pd = propertyDescriptors.get(name);
		if (pd==null) {
			invalid(boPD);
		}		
		return pd;
	}
	
	
	/**
	 * Validates a value against the {@link BoPropertyDescriptor}.
	 * 
	 * Unchecked delegate to <code>BoPropertyDescriptor.validate(T value)</code>. 
	 * 
	 * @param <T> Type of value.
	 * @param boPD {@link BoPropertyDescriptor} that describes the property.
	 * @param value Value to set.
	 * 
	 * @throws ValidationException 
	 *         If the value is not valid according to the {@link BoPropertyDescriptor}. 
	 */
	private <T> void validateUnchecked(BoPropertyDescriptor<T> boPD, Object value) 
	throws ValidationException {
		@SuppressWarnings("unchecked")
		T val = (T) value;
		boPD.validate(val);
	}
	
	/**
	 * Throws a runtime exception indicating that the specified
	 * {@link BoPropertyDescriptor} is invalid. 
	 * 
	 * This happens when the class specified by the {@link BoPropertyDescriptor}
	 * does not have a property with the specified name. 
	 * 
	 * @param boPD Invalid BoProeprtyDescriptor
	 */
	void invalid(BoPropertyDescriptor<?> boPD) {
		String message = StringUtils.concat(
				Meta.getMessages().getString(INVALID_DESCRIPTOR),
				StringConstants.SPACE,
				boPD.getFullyQualifiedName());
		throw new RuntimeException(message);
	}
	
	/**
	 * Loads the properties of the class with the specified name
	 * to the <code>propertyDescriptors</code> map.
	 *  
	 * @param className
	 */
	synchronized void loadPropertiesOfClass(String className) {		
		Class<?> beanClass;
		try {
			beanClass = Class.forName(className);
			loadedClasses.put(className, beanClass);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		PropertyDescriptor[] beanProperties = 
			JavaBeanUtils.getBeansProperties(beanClass);
		for (PropertyDescriptor propertyDescriptor : beanProperties) {
			String propertyName = className+StringConstants.DOT + propertyDescriptor.getName();
			propertyDescriptors.put(propertyName, propertyDescriptor);
		}
	}
	
	/**
	 * Gets the value of a property defined by a BoPropertyDescriptor
	 * on an object.
	 * 
	 * @param <T> Type of object.
	 * @param boPD {@link BoPropertyDescriptor}.
	 * @param bean Bean.
	 * @return Returns the value of the specified property on the bean.
	 */
	@SuppressWarnings({"unchecked","nls"})
	public <T> T getValue(BoPropertyDescriptor<T> boPD, Object bean) {		
		if (logger.isTraceEnabled()) {
			String msg = "Getting value of property " 
				       + boPD.getFullyQualifiedName()
				       + " from " + bean.toString();
			logger.trace(msg);
		}		
		PropertyDescriptor pd = getPropertyDescriptor(boPD);
		Object value = JavaBeanUtils.getProperty(pd, bean); 
		if (logger.isTraceEnabled()) {			
			String msg2 = "Value of property " 
				       + boPD.getFullyQualifiedName()
				       + " is: " + value;
			logger.trace(msg2);
		}
		return (T) value;
	}
	
	/**
	 * Sets the value to the property defined by the {@link BoPropertyDescriptor}.
	 * 
	 * @param <T> Type of value.
	 * @param boPD {@link BoPropertyDescriptor} that describes the property.
	 * @param value Value to set.
	 * @param bean Bean on which the new value will be set.
	 * 
	 * @throws ValidationException 
	 *         If the value is not valid according to the {@link BoPropertyDescriptor}. 
	 */
	@SuppressWarnings("nls")
	public <T> void setValue(BoPropertyDescriptor<T> boPD, T value, Object bean) 
	throws ValidationException {
		if (logger.isTraceEnabled()) {
			String msg = "About to change value of property " 
				       + boPD.getFullyQualifiedName()
				       + " on object " + bean.toString()
				       + " to : " + value;
			logger.trace(msg);
		}	
		
		
		PropertyDescriptor pd = getPropertyDescriptor(boPD);
		boPD.validate(value);
		JavaBeanUtils.setProperty(pd, value, bean);
		
		if (logger.isTraceEnabled()) {
			logger.trace("Value successfully set");
		}
	}

	
	
	
	/**
	 * Gets the values of a beans properties as they are described
	 * by a {@link BusinessObjectDescriptor} that describes the bean.
	 * 
	 * @param descriptor Descriptor of bean.
	 * @param bean Bean
	 * @param <T> Type of bean.
	 * 
	 * @return Returns a Map that maps the the bean's BoPropertyDescriptors
	 *         with their corresponding values.
	 */
	public <T> Map<BoPropertyDescriptor<?>, Object> 
	getValues(BusinessObjectDescriptor<T> descriptor, T bean) {
		Map<BoPropertyDescriptor<?>, Object> propertyValues = 
			new HashMap<BoPropertyDescriptor<?>, Object>();
		
		for (BoPropertyDescriptor<?> boPD : descriptor.getPropertyDescriptors()) {
			Object value = getValue(boPD, bean);
			propertyValues.put(boPD, value);
		}		
		return propertyValues;
	}
	
	/**
	 * Gets the values of a beans properties as they are described
	 * by a {@link BusinessObjectDescriptor} that describes the bean.
	 * 
	 * This method first validates all values and the set them to the bean.
	 * If any of the values is invalid, then the bean will remain unmodified,
	 * while a MultipleValidationsException will be thrown.
	 * 
	 * @param propertyValues
	 *        Map with the values of the bean's properties to set.
	 *         
	 * @param bean
	 *        Bean on which the values are to be set.
	 *         
	 * @throws MultipleValidationsException
	 *         That contains a message for each property that has an invalid value.
	 */
	public void setValues(Map<BoPropertyDescriptor<?>, Object> propertyValues, Object bean) 
	throws MultipleValidationsException {
		validate(propertyValues);
		for (Map.Entry<BoPropertyDescriptor<?>, Object> entry : propertyValues.entrySet()) {
			BoPropertyDescriptor<?> boPD = entry.getKey();			 
			Object value = entry.getValue();
			PropertyDescriptor pd = getPropertyDescriptor(boPD);
			JavaBeanUtils.setProperty(pd, value, bean);
		}
	}
	
	
	/**
	 * Validates a set of values each against a BoPropertyDescriptor.
	 * 
	 * @param propertyValues
	 *        Map with the values of the bean's properties to validate.
	 *         
	 * @throws MultipleValidationsException
	 *         That contains a message for each property that has an invalid value.
	 */
	public void validate(Map<BoPropertyDescriptor<?>, Object> propertyValues) 
	throws MultipleValidationsException {
		Map<Object, String> messages = new HashMap<Object, String>();
		for (Map.Entry<BoPropertyDescriptor<?>, Object> entry : propertyValues.entrySet()) {
			BoPropertyDescriptor<?> boPD = entry.getKey();			 
			Object value = entry.getValue();
			try {
				validateUnchecked(boPD, value);
			} catch (ValidationException e) {
				messages.put(boPD, e.getMessage());
			}
		}
		if (!messages.isEmpty()) {
			throw new MultipleValidationsException(messages);
		}
	}
	

	/**
	 * Validates the values of a bean against a {@link BusinessObjectDescriptor}
	 * that describes it.
	 * 
	 * @param <T> Type of bean.
	 * @param descriptor Business object descriptor that describes the bean.
	 * @param bean Bean to validate.
	 * 
	 * @throws MultipleValidationsException
	 *         That contains a message for each property that has an invalid value.
	 */
	public <T> void validate(BusinessObjectDescriptor<T> descriptor, T bean) 
	throws MultipleValidationsException {
		Map<BoPropertyDescriptor<?>, Object> values = getValues(descriptor,bean);
		validate(values);
	}

}
