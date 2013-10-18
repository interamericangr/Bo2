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
 * a person implementation
 */
public class Person implements IPerson {

	/**
	 * default serial uid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the person name
	 */
	private String name;

	/**
	 * the po key
	 */	
	private Integer id;

	/**
	 * the person's associated addresses
	 */
	private Set<IAddress> addresses = new HashSet<IAddress>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<IAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<IAddress> addresses) {
		this.addresses = addresses;
	}

	public void addAddress(IAddress address) {
		address.setPerson(this);
		addresses.add(address);
	}
	
	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.PersistentObject#getKey()
	 */	
	public PersonKey getKey() {
		return new PersonKey(id);		
	}
	
	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.PersistentObject#setKey(gr.interamerican.bo2.arch.Key)
	 */	
	public void setKey(PersonKey key) {
		setId(key.getId());
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.PersistentObject#tidy()
	 */
	public void tidy() {
		/* empty */
	}

}
