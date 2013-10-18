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
package gr.interamerican.bo2.samples.jasper;

/**
 * Simple person java bean.
 */
public class SimplePerson {
	/**
	 * Name.
	 */
	String name;
	/**
	 * Surname
	 */
	String surName;
	/**
	 * Gender;
	 */
	String gender;
	
	/**
	 * Creates a new SimplePerson object. 
	 */
	public SimplePerson() {
		super();
	}
	
	/**
	 * Creates a new SimplePerson object. 
	 *
	 * @param name
	 * @param surName
	 * @param gender
	 */
	public SimplePerson(String name, String surName, String gender) {
		this();
		this.name = name;
		this.surName = surName;
		this.gender = gender;
	}

	/**
	 * Gets the name.
	 *
	 * @return Returns the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Assigns a new value to the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the surName.
	 *
	 * @return Returns the surName
	 */
	public String getSurName() {
		return surName;
	}

	/**
	 * Assigns a new value to the surName.
	 *
	 * @param surName the surName to set
	 */
	public void setSurName(String surName) {
		this.surName = surName;
	}

	/**
	 * Gets the gender.
	 *
	 * @return Returns the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Assigns a new value to the gender.
	 *
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

}
