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
package gr.interamerican.bo2.test.def.posamples;

import gr.interamerican.bo2.arch.ModificationRecord;
import gr.interamerican.bo2.arch.PersistentObject;

/**
 * A {@link Customer} owns many {@link CustomerAddress}es.
 * This is a one-to-many relationship.
 */
@SuppressWarnings("all")
public interface CustomerAddress 
extends CustomerAddressKP, PersistentObject<CustomerAddressKey>, ModificationRecord {
	
	/**
	 * Gets the street.
	 *
	 * @return the street
	 */
	public String getStreet();
	
	/**
	 * Sets the street.
	 *
	 * @param street the new street
	 */
	public void setStreet(String street);
	
	/**
	 * Gets the street no.
	 *
	 * @return the street no
	 */
	public String getStreetNo();
	
	/**
	 * Sets the street no.
	 *
	 * @param streetNo the new street no
	 */
	public void setStreetNo(String streetNo);

}
