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



import java.util.HashSet;
import java.util.Set;

/**
 * a person implementation.
 */
public class Person implements IPerson {

	/** default serial uid. */
	private static final long serialVersionUID = 1L;

	/** the person name. */
	private String name;

	/** the po key. */	
	private Integer id;

	/** the person's associated addresses. */
	private Set<IAddress> addresses = new HashSet<IAddress>();

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Set<IAddress> getAddresses() {
		return addresses;
	}

	@Override
	public void setAddresses(Set<IAddress> addresses) {
		this.addresses = addresses;
	}

	@Override
	public void addAddress(IAddress address) {
		address.setPerson(this);
		addresses.add(address);
	}

	@Override
	public PersonKey getKey() {
		return new PersonKey(id);		
	}

	@Override
	public void setKey(PersonKey key) {
		setId(key.getId());
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public void tidy() {
		/* empty */
	}

}
