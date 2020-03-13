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
 * The Class SamplePropertyDefinition.
 */
public  class SamplePropertyDefinition implements PropertyDefinition {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5569862994685822330L;

	/** name. */
	String name;
	
	/** readOnly. */
	Boolean readOnly;
	
	/** hasDefault. */
	Boolean hasDefault;
	
	/** defaultValue. */
	String defaultValue;
	
	/** zeroAllowed. */
	Boolean zeroAllowed;
	
	/** negativeAllowed. */
	Boolean negativeAllowed;
	
	/** nullAllowed. */
	Boolean nullAllowed;
	
	/** minLength. */
	Integer minLength;
	
	/** maxLength. */
	Integer maxLength;
	
	/** integerLength. */
	Integer integerLength;
	
	/** decimalLength. */
	Integer decimalLength;
	
	/** listCd. */
	Long listCd;
	
	/** subListCd. */
	Long subListCd;
	
	/** cacheName. */
	String cacheName;
	
	/**
	 * Expression.
	 */
	String expression;
	
	/**
	 * affected.
	 */
	String affected;
	
	/**
	 * description
	 */
	String description;
	

	@Override
	public String getName() {
		return name;
	}


	@Override
	public void setName(String name) {
		this.name = name;
		
	}

	@Override
	public Boolean getReadOnly() {
		return readOnly;
	}


	@Override
	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
		
	}

	@Override
	public Boolean getHasDefault() {
		return hasDefault;
	}

	@Override
	public void setHasDefault(Boolean hasDefault) {
		this.hasDefault = hasDefault;
	}

	@Override
	public String getDefaultValue() {
		return defaultValue;
	}


	@Override
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;	
	}

	@Override
	public Boolean getZeroAllowed() {
		return zeroAllowed;
	}


	@Override
	public void setZeroAllowed(Boolean zeroAllowed) {
		this.zeroAllowed = zeroAllowed;
		
	}


	@Override
	public Boolean getNegativeAllowed() {
		return negativeAllowed;
	}


	@Override
	public void setNegativeAllowed(Boolean negativeAllowed) {
		this.negativeAllowed = negativeAllowed;
		
	}


	@Override
	public Boolean getNullAllowed() {
		return nullAllowed;
	}


	@Override
	public void setNullAllowed(Boolean nullAllowed) {
		this.nullAllowed = nullAllowed;
		
	}


	@Override
	public Integer getMinLength() {
		return minLength;
	}


	@Override
	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
		
	}


	@Override
	public Integer getMaxLength() {
		return maxLength;
	}

	@Override
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
		
	}


	@Override
	public Integer getIntegerLength() {
		return integerLength;
	}


	@Override
	public void setIntegerLength(Integer integerLength) {
		this.integerLength = integerLength;
		
	}


	@Override
	public Integer getDecimalLength() {
		return decimalLength;
	}

	@Override
	public void setDecimalLength(Integer decimalLength) {
		this.decimalLength = decimalLength;
		
	}


	@Override
	public Long getListCd() {
		return listCd;
	}


	@Override
	public void setListCd(Long listCd) {
		this.listCd = listCd;
		
	}


	@Override
	public Long getSubListCd() {
		return subListCd;
	}


	@Override
	public void setSubListCd(Long subListCd) {
		this.subListCd = subListCd;
	}


	@Override
	public String getCacheName() {
		return cacheName;
	}


	@Override
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
		
	}

	/**
	 * ������� �� definition.
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
	 * ������� �� definition.
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


	@Override
	public void setExpression(String expression) {
		this.expression = expression;
	}


	@Override
	public String getExpression() {
		return expression;
	}
	
	@Override
	public String getAffected() {
		return affected;
	}

	@Override
	public void setAffected(String affected) {
		this.affected = affected;
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description= description;
	}
	
}
