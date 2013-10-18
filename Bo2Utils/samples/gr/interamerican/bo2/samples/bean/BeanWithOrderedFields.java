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
package gr.interamerican.bo2.samples.bean;

import gr.interamerican.bo2.utils.Utils;

import java.io.Serializable;

/**
 * Bean with first, second, third field.
 */
public class BeanWithOrderedFields implements Serializable {
	
	/**
	 * Creates a new BeanWithOrderedFields object. 
	 *
	 * @param first
	 * @param second
	 * @param third
	 * @param fourth 
	 * @param fifth 
	 */
	public BeanWithOrderedFields
	(String first, String second, Integer third, Long fourth, Double fifth) {
		super();
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
		this.fifth = fifth;
	}
	
	/**
	 * Creates a new BeanWithOrderedFields object. 
	 */
	public BeanWithOrderedFields() {
		super();
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof BeanWithOrderedFields)) {
			return false;
		}
		
		BeanWithOrderedFields that = (BeanWithOrderedFields) obj;
		
		/*
		 * Assume that the first two properties
		 * are a database key.
		 */
		boolean isEqual = 
		Utils.equals(this.getFirst(), that.getFirst()) &&
		Utils.equals(this.getSecond(), that.getSecond());
		
		return isEqual;
	}

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * first.
	 */
	public String first;
	/**
	 * second.
	 */
	public String second;
	/**
	 * thirrd.
	 */
	public Integer third;
	/**
	 * thirrd.
	 */
	public Long fourth;
	/**
	 * thirrd.
	 */
	public Double fifth;

	/**
	 * Gets the first.
	 *
	 * @return Returns the first
	 */
	public String getFirst() {
		return first;
	}

	/**
	 * Assigns a new value to the first.
	 *
	 * @param first the first to set
	 */
	public void setFirst(String first) {
		this.first = first;
	}

	/**
	 * Gets the second.
	 *
	 * @return Returns the second
	 */
	public String getSecond() {
		return second;
	}

	/**
	 * Assigns a new value to the second.
	 *
	 * @param second the second to set
	 */
	public void setSecond(String second) {
		this.second = second;
	}

	/**
	 * Gets the third.
	 *
	 * @return Returns the third
	 */
	public Integer getThird() {
		return third;
	}

	/**
	 * Assigns a new value to the third.
	 *
	 * @param third the third to set
	 */
	public void setThird(Integer third) {
		this.third = third;
	}

	/**
	 * Gets the fourth.
	 *
	 * @return Returns the fourth
	 */
	public Long getFourth() {
		return fourth;
	}

	/**
	 * Assigns a new value to the fourth.
	 *
	 * @param fourth the fourth to set
	 */
	public void setFourth(Long fourth) {
		this.fourth = fourth;
	}

	/**
	 * Gets the fifth.
	 *
	 * @return Returns the fifth
	 */
	public Double getFifth() {
		return fifth;
	}

	/**
	 * Assigns a new value to the fifth.
	 *
	 * @param fifth the fifth to set
	 */
	public void setFifth(Double fifth) {
		this.fifth = fifth;
	}

}
