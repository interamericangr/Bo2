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

import gr.interamerican.bo2.utils.attributes.Named;

import java.io.Serializable;

/**
 * A {@link PropertyDefinition} object has all the necessary 
 * information for the creation of a {@link BoPropertyDescriptor}.
 * <br/>
 * name: Property name.
 * <br/>
 * readOnly: The value of the property is read-only.
 * <br/>
 * hasDefault: The value of the property has a default value.
 * <br/>
 * defaultValue: The default value of the property (if applicable).
 * <br/>
 * zeroAllowed: The property value may be zero (applicable for {@link Number} sub-types).
 * <br/>
 * negativeAllowed: The property value may be negative (applicable for most 
 *                  {@link Number} sub-types).
 *                  
 * nullAllowed:     The property value may be null.
 * <br/>
 * minLength:       Minimum length of property value string representation.
 * <br/>
 * maxLength:       Maximum length of property value string representation.
 * <br/>
 * integerLength:   Maximum length of property value integer digits (applicable for 
 * 	                {@link Number} sub-types). The corresponding minimum value is 
 *                  implicitly considered to be 0.
 * <br/>
 * decimalLength:   Maximum length of property value decimal digits (applicable for 
 *                  some {@link Number} sub-types). The corresponding minimum value 
 *                  is implicitly considered to be 0.
 * <br/>
 * listCd:          Code of system list that the value for this property is cached 
 *                  into (if applicable).
 * <br/>
 * subListCd:       Code of system sub-list that the value for this property is cached 
 *                  into (if applicable).
 * <br/>
 * cacheName:       Name of the cache that the value of this property is cached to
 *                  (if applicable).
 * <br/>
 * expression:      A regular expression that can validate the value of the property.
 * <br/>
 */
public interface PropertyDefinition 
extends Named, Serializable{
	
	/**
	 * Gets the readOnly.
	 *
	 * @return Returns the readOnly
	 */
	Boolean getReadOnly();

	/**
	 * Assigns a new value to the readOnly.
	 *
	 * @param readOnly the readOnly to set
	 */
	void setReadOnly(Boolean readOnly);

	/**
	 * Gets the hasDefault.
	 *
	 * @return Returns the hasDefault
	 */
	Boolean getHasDefault();

	/**
	 * Assigns a new value to the hasDefault.
	 *
	 * @param hasDefault the hasDefault to set
	 */
	void setHasDefault(Boolean hasDefault);

	/**
	 * Gets the defaultValue.
	 *
	 * @return Returns the defaultValue
	 */
	String getDefaultValue();

	/**
	 * Assigns a new value to the defaultValue.
	 *
	 * @param defaultValue the defaultValue to set
	 */
	void setDefaultValue(String defaultValue);

	/**
	 * Gets the zeroAllowed.
	 *
	 * @return Returns the zeroAllowed
	 */
	Boolean getZeroAllowed();

	/**
	 * Assigns a new value to the zeroAllowed.
	 *
	 * @param zeroAllowed the zeroAllowed to set
	 */
	void setZeroAllowed(Boolean zeroAllowed);

	/**
	 * Gets the negativeAllowed.
	 *
	 * @return Returns the negativeAllowed
	 */
	Boolean getNegativeAllowed();

	/**
	 * Assigns a new value to the negativeAllowed.
	 *
	 * @param negativeAllowed the negativeAllowed to set
	 */
	void setNegativeAllowed(Boolean negativeAllowed);

	/**
	 * Gets the nullAllowed.
	 *
	 * @return Returns the nullAllowed
	 */
	Boolean getNullAllowed();

	/**
	 * Assigns a new value to the nullAllowed.
	 *
	 * @param nullAllowed the nullAllowed to set
	 */
	void setNullAllowed(Boolean nullAllowed);

	/**
	 * Gets the minLength.
	 *
	 * @return Returns the minLength
	 */
	Integer getMinLength();

	/**
	 * Assigns a new value to the minLength.
	 *
	 * @param minLength the minLength to set
	 */
	void setMinLength(Integer minLength);

	/**
	 * Gets the maxLength.
	 *
	 * @return Returns the maxLength
	 */
	Integer getMaxLength();

	/**
	 * Assigns a new value to the maxLength.
	 *
	 * @param maxLength the maxLength to set
	 */
	void setMaxLength(Integer maxLength);

	/**
	 * Gets the integerLength.
	 *
	 * @return Returns the integerLength
	 */
	Integer getIntegerLength();

	/**
	 * Assigns a new value to the integerLength.
	 *
	 * @param integerLength the integerLength to set
	 */
	void setIntegerLength(Integer integerLength);

	/**
	 * Gets the decimalLength.
	 *
	 * @return Returns the decimalLength
	 */
	Integer getDecimalLength();

	/**
	 * Assigns a new value to the decimalLength.
	 *
	 * @param decimalLength the decimalLength to set
	 */
	void setDecimalLength(Integer decimalLength);

	/**
	 * Gets the listCd.
	 *
	 * @return Returns the listCd
	 */
	Long getListCd();

	/**
	 * Assigns a new value to the listCd.
	 *
	 * @param listCd the listCd to set
	 */
	void setListCd(Long listCd);

	/**
	 * Gets the subListCd.
	 *
	 * @return Returns the subListCd
	 */
	Long getSubListCd();

	/**
	 * Assigns a new value to the subListCd.
	 *
	 * @param subListCd the subListCd to set
	 */
	void setSubListCd(Long subListCd);

	/**
	 * Gets the cacheName.
	 *
	 * @return Returns the cacheName
	 */
	String getCacheName();

	/**
	 * Assigns a new value to the cacheName.
	 *
	 * @param cacheName the cacheName to set
	 */
	void setCacheName(String cacheName);
	
	/**
	 * @param expression
	 *        Sets an expression to validate the property value with.
	 */
	void setExpression(String expression);
	
	/**
	 * @return Returns the expression.
	 */
	String getExpression();

}
