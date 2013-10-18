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

import java.io.Serializable;

/**
 * Address.
 */
public class Address implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	/**
	 * Country.
	 */
	String country;
	/**
	 * City.
	 */
	String city;
	/**
	 * zip code.
	 */
	String zipCode;
	/**
	 * street.
	 */
	String street;
	/**
	 * number.
	 */
	String number;
	
	/**
	 * Gets the country.
	 *
	 * @return Returns the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * Assigns a new value to the country.
	 *
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * Gets the city.
	 *
	 * @return Returns the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * Assigns a new value to the city.
	 *
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * Gets the zipCode.
	 *
	 * @return Returns the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
	/**
	 * Assigns a new value to the zipCode.
	 *
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	/**
	 * Gets the street.
	 *
	 * @return Returns the street
	 */
	public String getStreet() {
		return street;
	}
	/**
	 * Assigns a new value to the street.
	 *
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	/**
	 * Gets the number.
	 *
	 * @return Returns the number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * Assigns a new value to the number.
	 *
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	

}
