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

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.Utils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Family.
 */
public class Family implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Father.
	 */
	Person father;
	/**
	 * Mother.
	 */
	Person mother;
	/**
	 * Children.
	 */
	List<Person> children;
	/**
	 * Date of mariage.
	 */
	Date mariageDate;
	/**
	 * Home address.
	 */
	Address address;
	
	/**
	 * Gets the father.
	 *
	 * @return Returns the father
	 */
	public Person getFather() {
		return father;
	}
	/**
	 * Assigns a new value to the father.
	 *
	 * @param father the father to set
	 */
	public void setFather(Person father) {
		this.father = father;
	}
	/**
	 * Gets the mother.
	 *
	 * @return Returns the mother
	 */
	public Person getMother() {
		return mother;
	}
	/**
	 * Assigns a new value to the mother.
	 *
	 * @param mother the mother to set
	 */
	public void setMother(Person mother) {
		this.mother = mother;
	}
	/**
	 * Gets the children.
	 *
	 * @return Returns the children
	 */
	public List<Person> getChildren() {
		return children;
	}
	/**
	 * Assigns a new value to the children.
	 *
	 * @param children the children to set
	 */
	public void setChildren(List<Person> children) {
		this.children = children;
	}
	/**
	 * Gets the mariageDate.
	 *
	 * @return Returns the mariageDate
	 */
	public Date getMariageDate() {
		return mariageDate;
	}
	/**
	 * Assigns a new value to the mariageDate.
	 *
	 * @param mariageDate the mariageDate to set
	 */
	public void setMariageDate(Date mariageDate) {
		this.mariageDate = mariageDate;
	}
	/**
	 * Gets the address.
	 *
	 * @return Returns the address
	 */
	public Address getAddress() {
		return address;
	}
	/**
	 * Assigns a new value to the address.
	 *
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	@SuppressWarnings("nls")
	@Override
	public String toString() {		
		if (father!=null) {
			return "Family " + Utils.notNull(father.getLastName(), StringConstants.EMPTY);
		}
		if (mother!=null) {
			return "Family " + Utils.notNull(mother.getLastName(), StringConstants.EMPTY);
		}
		return "Family without parents";
	}
	

}
