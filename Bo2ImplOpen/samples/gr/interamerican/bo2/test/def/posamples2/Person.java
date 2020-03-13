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
package gr.interamerican.bo2.test.def.posamples2;

import gr.interamerican.bo2.arch.PersistentObject;

/**
 * The Interface Person.
 */
public interface Person extends PersistentObject<PersonKey> {

	/**
	 * Gets the person id.
	 *
	 * @return the person id
	 */
	public Integer getPersonId();
	
	/**
	 * Sets the person id.
	 *
	 * @param personId the new person id
	 */
	public void setPersonId(String personId);
	
	/**
	 * Gets the amka.
	 *
	 * @return the amka
	 */
	public String getAmka();
	
	/**
	 * Sets the amka.
	 *
	 * @param amka the new amka
	 */
	public void setAmka(String amka);
	
	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName();
	
	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName);
	
	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName();
	
	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName);
	
	/**
	 * Gets the birth date.
	 *
	 * @return the birth date
	 */
	public String getBirthDate();
	
	/**
	 * Sets the birth date.
	 *
	 * @param lastName the new birth date
	 */
	public void setBirthDate(String lastName);

}
