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
package gr.interamerican.bo2.utils;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 */

public class Defaults {
	
	/**
	 * Singleton map with standard defaults.
	 */
	static final Map<Class<?>, Object> STANDARD_DEFAULTS = 
		new HashMap<Class<?>, Object>();	
	static {
		STANDARD_DEFAULTS.put(String.class, StringConstants.EMPTY);
		STANDARD_DEFAULTS.put(Double.class, 0.0d);
		STANDARD_DEFAULTS.put(Float.class, 0.0f);
		STANDARD_DEFAULTS.put(Integer.class, 0);
		STANDARD_DEFAULTS.put(Long.class, 0L);
		STANDARD_DEFAULTS.put(Short.class, (short) 0);
		STANDARD_DEFAULTS.put(Boolean.class, false);
		STANDARD_DEFAULTS.put(Character.class, ' ');
		STANDARD_DEFAULTS.put(Byte.class, (byte) 0);
		STANDARD_DEFAULTS.put(BigDecimal.class, BigDecimal.ZERO);
		STANDARD_DEFAULTS.put(double.class, 0.0d);
		STANDARD_DEFAULTS.put(float.class, 0.0f);
		STANDARD_DEFAULTS.put(int.class, 0);
		STANDARD_DEFAULTS.put(long.class, 0L);
		STANDARD_DEFAULTS.put(short.class, (short) 0);
		STANDARD_DEFAULTS.put(boolean.class, false);
		STANDARD_DEFAULTS.put(char.class, ' ');
		STANDARD_DEFAULTS.put(byte.class, (byte) 0);
	}
	
	
	/**
	 * Hidden constructor.
	 */
	private Defaults() {/* empty */}
	
	/**
	 * Gets the default value for the specified type.
	 * 
	 * @param <T>
	 * @param type
	 * 
	 * @return Returns the default value.
	 */
	@SuppressWarnings("unchecked")
	public static  <T> T getDefaultValue(Class<T> type) {
		Object value = STANDARD_DEFAULTS.get(type);
		return (T) value;
	}
	
	/**
	 * Registers the default value for a type.
	 *       
	 * @param type
	 *        Type for which the default value is registered.
	 * @param value
	 *        Default value for the specified type.
	 * @param <T> 
	 *        Type of value.
	 */	
	public static  <T> void registerDefaultValue(Class<T> type, T value) {
		STANDARD_DEFAULTS.put(type, value);
	}
	
	/**
	 * Sets default values to the properties of a bean. <br/>
	 * 
	 * The default values are defined in a map, that maps java type of
	 * property to a default value.
	 * 
	 * @param bean
	 *        Bean to set.
	 * @param clazz
	 *        Type of bean.
	 * @param defaults
	 *        Map with default values.
	 * 
	 * @param <T>
	 */
	public static <T> void setDefaults
	(T bean, Class<T> clazz, Map<Class<?>, Object> defaults) {
		PropertyDescriptor[] properties = JavaBeanUtils.getBeansProperties(clazz);
		for (PropertyDescriptor pd : properties) {
			Class<?> propertyType = pd.getPropertyType();
			Object defaultValue = defaults.get(propertyType);
			JavaBeanUtils.setProperty(pd, defaultValue, bean);
		}		
	}
	
	/**
	 * Sets the standard default values to the properties of a bean. <br/>
	 * 
	 * @param bean
	 *        Bean to set.
	 * @param clazz
	 *        Type of bean.
	 * 
	 * @param <T>
	 *        Type of object being initialized 
	 */
	public static <T> void setStandardDefaults (T bean, Class<T> clazz) {
		setDefaults(bean, clazz, STANDARD_DEFAULTS);
	}
	
	
}
