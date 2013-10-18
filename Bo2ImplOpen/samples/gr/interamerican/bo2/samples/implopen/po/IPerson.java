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

import java.util.Set;

/**
 * person PO interface
 */
public interface IPerson extends PersistentObject<PersonKey> {

	/**
	 * @param name
	 *            sets the person's name
	 */
	public void setName(String name);

	/**
	 * @return the person's name
	 */
	public String getName();

	/**
	 * @param addresses
	 */
	public void setAddresses(Set<IAddress> addresses);

	/**
	 * @return the person's addresses
	 */
	public Set<IAddress> getAddresses();

	/**
	 * @param address
	 *            utility method to set the two-way association
	 */
	public void addAddress(IAddress address);

}
