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

import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.JavaBeanUtils;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Properties initialized bean is a javabean that is initialized
 * by a Properties object.
 */
public class PropertiesInitializedBean {
	
	/**
	 * Proeprties provider.
	 */
	private Properties properties;
	
	/**
	 * Creates a new PropertiesInitializedBean object. 
	 *
	 * @param properties
	 */
	public PropertiesInitializedBean(Properties properties) {
		super();
		this.properties = properties;
		
		Set<String> allreadySet = new HashSet<String>();
		
		PropertyDescriptor[] beanProperties = 
			JavaBeanUtils.getBeansProperties(this.getClass());
		for (int i = 0; i < beanProperties.length; i++) {
			PropertyDescriptor pd = beanProperties[i];
			Class<?> type = pd.getPropertyType();
			Object value = getValue(pd.getName(),type);
			if (value!=null) {
				JavaBeanUtils.setProperty(pd, value, this);
				allreadySet.add(pd.getName());
			}
		}
		
		List<Field> fields = ReflectionUtils.allFields
			(this.getClass(), PropertiesInitializedBean.class);
		for (Field field : fields) {
			String name = field.getName();
			if (!allreadySet.contains(name)) {
				Class<?> type = field.getType();
				Object value = getValue(name,type);
				if (value!=null) {
					allreadySet.add(name);
					field.setAccessible(true);
					ReflectionUtils.set(field, value, this);
				}
			}
		}
	}
	
	
	/**
	 * Gets the value specified in the properties object for
	 * a property.
	 * 
	 * @param propertyName Name of property.
	 * @param type Type of property.
	 * 
	 * @return Returns the value of the specified property.
	 */
	@SuppressWarnings("unchecked")
	private Object getValue(String propertyName, Class<?> type) {
		String value = properties.getProperty(propertyName);
		if (value==null) {
			return null;
		}
		
		if (String.class.isAssignableFrom(type)) {
			return value;		
		} else if (Double.class.isAssignableFrom(type)) {
			return NumberUtils.string2Double(value);
		} else if (Float.class.isAssignableFrom(type)) {			
			return new Double(NumberUtils.string2Double(value)).floatValue();
		} else if (Integer.class.isAssignableFrom(type)) {
			return new Double(NumberUtils.string2Double(value)).intValue();
		} else if (Long.class.isAssignableFrom(type)) {
			return new Double(NumberUtils.string2Double(value)).longValue();
		} else if (Short.class.isAssignableFrom(type)) {
			return new Double(NumberUtils.string2Double(value)).shortValue();
		} else if (Byte.class.isAssignableFrom(type)) {
			return new Double(NumberUtils.string2Double(value)).byteValue();
		} else if (Character.class.isAssignableFrom(type)) {			
			if (value.length()==0) {
				return null;
			}
			return value.charAt(0);			
		} else if (Date.class.isAssignableFrom(type)) {
			return getDate(value);
		} else if (Boolean.class.isAssignableFrom(type)) {
			return getBoolean(value);
		} else if (Enum.class.isAssignableFrom(type)) {
			return getEnum(value, (Class<? extends Enum<?>>) type);
		}
		return null;
		
	}
	
	/**
	 * Gets a date from value.
	 * 
	 * @param value
	 * @return Returns the date represented by the value.
	 */
	private Date getDate(String value) {
		try {
			return DateUtils.getDate(value);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Gets a boolean from value.
	 * 
	 * @param value
	 * @return Returns true if the value is true, yes or 1
	 *         otherwise returns false.
	 */
	private boolean getBoolean(String value) {
		String s = value.trim();
        return s.equalsIgnoreCase("TRUE")  //$NON-NLS-1$
           ||  s.equalsIgnoreCase("YES")   //$NON-NLS-1$                       
           ||  s.equals("1");              //$NON-NLS-1$
	}
	
	/**
	 * Gets an enum from a value. The value should be equal to the
	 * {@link Enum#toString()} ignoring case.
	 * 
	 * @param value
	 * @param enumType
	 * @return The enum value.
	 */
	private Enum<?> getEnum(String value, Class<? extends Enum<?>> enumType) {
		String s = value.trim();
		for(Enum<?> enumeration : enumType.getEnumConstants()) {
			if(enumeration.toString().equalsIgnoreCase(s)) {
				return enumeration;
			}
		}
		return null;
	}

}
