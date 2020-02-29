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


/**
 * an address implementation.
 */
public class Address  implements IAddress {
	
	/** default serial version uid. */
	private static final long serialVersionUID = 1L;

	/** primary key of the addresses table. */	
	private Integer id;
	
	/** the address. */
	private String address;
	
	/** the person living here. */
	private IPerson person;
	
	/**
	 * Gets the address.
	 *
	 * @return address
	 */
	@Override
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	@Override
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Sets the person.
	 *
	 * @param person the new person
	 */
	@Override
	public void setPerson(IPerson person) {
		this.person = person;
	}

	/**
	 * Gets the person.
	 *
	 * @return person
	 */
	@Override
	public IPerson getPerson() {
		return person;
	}
	
	/**
	 * Gets the id.
	 * 
	 * @return Returns the id.
	 */
	@Override
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id new id.
	 */
	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public AddressKey getKey() {
		return new AddressKey(id);		
	}

	@Override
	public void setKey(AddressKey key) {
		setId(key.getId());
	}
	
	@Override
	public void tidy() {
		/* empty */
	}

}
