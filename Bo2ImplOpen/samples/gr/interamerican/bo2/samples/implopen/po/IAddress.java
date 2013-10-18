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
package gr.interamerican.bo2.samples.implopen.po;

import gr.interamerican.bo2.arch.PersistentObject;

/**
 * address interface
 */
public interface IAddress extends PersistentObject<AddressKey> {

	/**
	 * @param address
	 *            sets the address
	 */
	public void setAddress(String address);

	/**
	 * @return the address
	 */
	public String getAddress();

	/**
	 * @param person
	 *            sets the person
	 */
	public void setPerson(IPerson person);

	/**
	 * @return the person
	 */
	public IPerson getPerson();
	
	/**
	 * @return the id
	 */
	public Integer getId();
	
	/**
	 * @param id
	 */
	public void setId(Integer id);

}
