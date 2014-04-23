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
package gr.interamerican.bo2.utils.meta.descriptors;

import gr.interamerican.bo2.utils.attributes.Labeled;
import gr.interamerican.bo2.utils.attributes.Named;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;
import gr.interamerican.bo2.utils.meta.parsers.Parser;
import gr.interamerican.bo2.utils.meta.validators.Validator;

import java.io.Serializable;

/**
 * Generic interface for the specification of a business property.
 * 
 * Business objects properties are associated with the javabean properties
 * of business objects but they don't describe exactly the same thing.
 * A java bean property descriptor describes the property from a java
 * development perspective. The BoPropertyDescriptor describes the property 
 * from a user perspective. This is a different perspective than the code,
 * for instance a javabean property could be invisible to the user, 
 * another property could be modifiable by the code, but read-only for the 
 * user. <br/>
 * The name BoPropertyDescriptor stands for Business Object Property
 * Descriptor.
 * 
 * @param <T> Type of property.
 */
public interface BoPropertyDescriptor<T> 
extends Named, Labeled, Serializable {
	
	/**
	 * Gets the index of the property.
	 * 
	 * @return Returns the index of the property.
	 */
	Integer getIndex();
	
	/**
	 * Sets the index.
	 * 
	 * @param index
	 *        The index to set.
	 */
	void setIndex(Integer index);
	
	
	/**
	 * Gets the name of the owner class' package.
	 * 
	 * @return Gets the name of the owner class' package.
	 */
	String getPackageName();
	
	/**
	 * Sets the name of the owner class' package.
	 * 
	 * @param packageName New name.
	 */
	void setPackageName(String packageName);
	
	/**
	 * Gets the name of the owner class.
	 * 
	 * @return Gets the name of the owner class.
	 */
	String getClassName();
	
	/**
	 * Sets the name of the owner class.
	 * 
	 * @param ownerName New name.
	 */
	void setClassName(String ownerName);
	
	/**
	 * Gets the full qualified name the owner class and the property.
	 * 
	 * @return Gets the name of the owner class.
	 */
	String getFullyQualifiedName();
	
	/**
	 * Gets the full qualified name the owner class.
	 * 
	 * @return Gets the fully qualified name of the owner class.
	 */
	String getFullyQualifiedClassName();
	
	/**
	 * Indicates that the property is unmodifiable.
	 * 
	 * @return Returns true if the property is read only.
	 */
	boolean isReadOnly();
	
	/**
	 * Sets the this property to be unmodifiable or not.
	 *  
	 * @param readOnly New value for read only property.
	 */
	void setReadOnly(boolean readOnly);
	
	/**
	 * Indicates if null is allowed for this property.
	 * 
	 * @return Returns true if null is allowed for this property.
	 */
	boolean isNullAllowed();
	
	/**
	 * Sets if null is allowed for this property.
	 * 
	 * @param nullAllowed New value for nullAllowed.
	 */
	void setNullAllowed(boolean nullAllowed);
	
	/**
	 * Sets the flag that null values will be replaced by a default value.
	 * 
	 * @return Returns true if nulls are replaced by a default value.
	 */
	boolean isHasDefault();
	
	/**
	 * Sets the flag if null values of this property are replaced by a
	 * default value.
	 * 
	 * @param hasDefault The new flag for hasDefault.
	 */
	void setHasDefault(boolean hasDefault);
	
	/**
	 * Parses a value without applying any validation.
	 * 
	 * @param value
	 * 
	 * @return Returns the value.
	 * 
	 * @throws ParseException
	 */
	T parse(String value) throws ParseException;
	
	/**
	 * Converts the specified number to the appropriate value that matches to this
	 * {@link BoPropertyDescriptor}.
	 * 
	 * If a numeric translation can't be performed for the specific data type, then
	 * returns null.
	 * 
	 * @param value
	 * 
	 * @return Returns the value.
	 */
	T valueOf(Number value);
	
	/**
	 * Gets the string representation of the value.
	 * 
	 * @param value Value to print.
	 * 
	 * @return Returns the String representation of the value.
	 */
	String format(T value);

	
	/**
	 * Parses and validates a value.
	 * 
	 * @param value Value to parse and validation.
	 * 
	 * @return Returns the value.
	 * 
	 * @throws ParseException 
	 * @throws ValidationException
	 */
	T parseAndValidate(String value) throws ParseException, ValidationException;
	
	/**
	 * Validates a value.
	 * 
	 * @param value
	 * @throws ValidationException 
	 */
	void validate(T value) throws ValidationException;
	
	/**
	 * Default value, if this property has a default.
	 * 
	 * @return Returns the default value.
	 */
	T getDefaultValue();
	
	/**
	 * Sets the default value for this property.
	 * 
	 * @param defaultValue New default value.
	 */
	void setDefaultValue(T defaultValue);
	
	/**
	 * Gets the max length of the formatted field.
	 * 
	 * @return Returns the max length of the formatted field.
	 */
	int getMaxLength();
	
	/**
	 * Sets the max length of the formatted field.
	 * 
	 * @param maxLength
	 *        The max length to set.
	 */
	void setMaxLength(int maxLength);
	
	/**
	 * Set the name of a {@link BoPropertyDescriptor} that this one affects.
	 * This is evaluated within the context of a specific {@link BusinessObjectDescriptor}.
	 *
	 * @param affected the affected to set
	 */
	void setAffected(String affected);
	
	/**
	 * @return Returns the name of the affected {@link BoPropertyDescriptor}.
	 */
	String getAffected();
	
	/**
	 * @return Returns the parser of this BoPropertyDescriptor
	 */
	Parser<T> getParser();
	
	/**
	 * @return Returns the validator of this BoPropertyDescriptor
	 */
	Validator<T> getValidator();

}
