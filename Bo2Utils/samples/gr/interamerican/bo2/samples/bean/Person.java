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

import gr.interamerican.bo2.samples.enums.Sex;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;

import java.io.Serializable;
import java.util.Date;

/**
 * Person bean.
 */
public class Person implements Serializable  {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * First name.
	 */
	String firstName;
	/**
	 * Last name.
	 */
	String lastName;
	/**
	 * Birthday
	 */
	Date birthDay;
	/**
	 * Social security No
	 */
	String socialSecurityNo;
	/**
	 * Sex.
	 */
	Sex sex;
	
	/**
	 * 
	 */
	 String phones [];
	
	/**
	 * Gets the firstName.
	 *
	 * @return Returns the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * Assigns a new value to the firstName.
	 *
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * Gets the lastName.
	 *
	 * @return Returns the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * Assigns a new value to the lastName.
	 *
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * Gets the birthDay.
	 *
	 * @return Returns the birthDay
	 */
	public Date getBirthDay() {
		return birthDay;
	}
	/**
	 * Assigns a new value to the birthDay.
	 *
	 * @param birthDay the birthDay to set
	 */
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	/**
	 * Gets the socialSecurityNo.
	 *
	 * @return Returns the socialSecurityNo
	 */
	public String getSocialSecurityNo() {
		return socialSecurityNo;
	}
	/**
	 * Assigns a new value to the socialSecurityNo.
	 *
	 * @param socialSecurityNo the socialSecurityNo to set
	 */
	public void setSocialSecurityNo(String socialSecurityNo) {
		this.socialSecurityNo = socialSecurityNo;
	}
	/**
	 * Gets the sex.
	 *
	 * @return Returns the sex
	 */
	public Sex getSex() {
		return sex;
	}
	/**
	 * Assigns a new value to the sex.
	 *
	 * @param sex the sex to set
	 */
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
	/**
	 * Gets phones
	 *
	 * @return Phones
	 */
	public String [] getPhones() {
		return phones;
	}
	/**
	 * Assigns phones
	 * @param phones
	 */
	public void setPhones(String [] phones) {
		this.phones = phones;
	}
	
	@Override
	public String toString() {
		return StringUtils.concat(
			Utils.notNull(firstName, StringConstants.EMPTY),
			StringConstants.SPACE,
			Utils.notNull(lastName, StringConstants.EMPTY));
	}
	
	

}
