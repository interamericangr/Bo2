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
 * an address implementation
 */
public class Address  implements IAddress {
	
	/**
	 * default serial version uid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * primary key of the addresses table
	 */	
	private Integer id;
	
	/**
	 * the address
	 */
	private String address;
	
	/**
	 * the person living here
	 */
	private IPerson person;
	
	/**
	 * 
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 
	 * @param person
	 */
	public void setPerson(IPerson person) {
		this.person = person;
	}

	/**
	 * 
	 * @return person
	 */
	public IPerson getPerson() {
		return person;
	}
	
	/**
	 * Gets the id.
	 * 
	 * @return Returns the id.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id new id.
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.PersistentObject#getKey()
	 */	
	public AddressKey getKey() {
		return new AddressKey(id);		
	}
	
	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.PersistentObject#setKey(gr.interamerican.bo2.arch.Key)
	 */	
	public void setKey(AddressKey key) {
		setId(key.getId());
	}
	
	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.PersistentObject#tidy()
	 */
	public void tidy() {
		/* empty */
	}

}
