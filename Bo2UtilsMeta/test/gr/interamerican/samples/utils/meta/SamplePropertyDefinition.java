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
package gr.interamerican.samples.utils.meta;

import gr.interamerican.bo2.utils.meta.descriptors.PropertyDefinition;


/**
 * 
 */
public  class SamplePropertyDefinition implements PropertyDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5569862994685822330L;

	/**
	 * name
	 */
	String name;
	
	/**
	 * readOnly
	 */
	Boolean readOnly;
	
	/**
	 * hasDefault
	 */
	Boolean hasDefault;
	
	/**
	 * defaultValue
	 */
	String defaultValue;
	
	/**
	 * zeroAllowed
	 */
	Boolean zeroAllowed;
	
	/**
	 * negativeAllowed
	 */
	Boolean negativeAllowed;
	
	/**
	 * nullAllowed
	 */
	Boolean nullAllowed;
	
	/**
	 * minLength
	 */
	Integer minLength;
	
	/**
	 * maxLength
	 */
	Integer maxLength;
	
	/**
	 * integerLength
	 */
	Integer integerLength;
	
	/**
	 * decimalLength
	 */
	Integer decimalLength;
	
	/**
	 * listCd
	 */
	Long listCd;
	
	/**
	 * subListCd
	 */
	Long subListCd;
	
	/**
	 * cacheName
	 */
	String cacheName;
	
	/**
	 * Expression.
	 */
	String expression;
	
	/**
	 * affected.
	 */
	String affected;
	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
		
	}

	public Boolean getReadOnly() {
		return readOnly;
	}


	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
		
	}

	public Boolean getHasDefault() {
		return hasDefault;
	}

	public void setHasDefault(Boolean hasDefault) {
		this.hasDefault = hasDefault;
	}

	public String getDefaultValue() {
		return defaultValue;
	}


	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;	
	}

	public Boolean getZeroAllowed() {
		return zeroAllowed;
	}


	public void setZeroAllowed(Boolean zeroAllowed) {
		this.zeroAllowed = zeroAllowed;
		
	}


	public Boolean getNegativeAllowed() {
		return negativeAllowed;
	}


	public void setNegativeAllowed(Boolean negativeAllowed) {
		this.negativeAllowed = negativeAllowed;
		
	}


	public Boolean getNullAllowed() {
		return nullAllowed;
	}


	public void setNullAllowed(Boolean nullAllowed) {
		this.nullAllowed = nullAllowed;
		
	}


	public Integer getMinLength() {
		return minLength;
	}


	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
		
	}


	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
		
	}


	public Integer getIntegerLength() {
		return integerLength;
	}


	public void setIntegerLength(Integer integerLength) {
		this.integerLength = integerLength;
		
	}


	public Integer getDecimalLength() {
		return decimalLength;
	}

	public void setDecimalLength(Integer decimalLength) {
		this.decimalLength = decimalLength;
		
	}


	public Long getListCd() {
		return listCd;
	}


	public void setListCd(Long listCd) {
		this.listCd = listCd;
		
	}


	public Long getSubListCd() {
		return subListCd;
	}


	public void setSubListCd(Long subListCd) {
		this.subListCd = subListCd;
	}


	public String getCacheName() {
		return cacheName;
	}


	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
		
	}

	/**
	 * ������� �� definition
	 */
	@SuppressWarnings("nls")
	public void fillSamplePropertyDefinition(){
		
		this.setName("pdName");
		this.setReadOnly(false);
		this.setNullAllowed(true);
		this.setDecimalLength(2);
		this.setIntegerLength(10);
		this.setZeroAllowed(true);
		this.setNegativeAllowed(false);
		this.setHasDefault(true);
	}
	/**
	 * ������� �� definition
	 */
	@SuppressWarnings("nls")
	public void fillSamplePropertyDefinitionForMaxLength(){
		
		this.setName("pdName");
		this.setReadOnly(false);
		this.setNullAllowed(true);
		this.setDecimalLength(0);
		this.setIntegerLength(10);
		this.setZeroAllowed(true);
		this.setNegativeAllowed(false);
		this.setHasDefault(false);
	}


	public void setExpression(String expression) {
		this.expression = expression;
	}


	public String getExpression() {
		return expression;
	}
	
	public String getAffected() {
		return affected;
	}

	public void setAffected(String affected) {
		this.affected = affected;
	}
	
}
