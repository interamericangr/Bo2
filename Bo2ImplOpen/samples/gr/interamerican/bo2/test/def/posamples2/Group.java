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

import java.util.Date;
import java.util.List;

/**
 * The Interface Group.
 */
public interface Group 
extends PersistentObject<GroupKey> {
	
	/**
	 * Gets the group id.
	 *
	 * @return the group id
	 */
	public Integer getGroupId();
	
	/**
	 * Sets the group id.
	 *
	 * @param groupId the new group id
	 */
	public void setGroupId(Integer groupId);
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name);
	
	/**
	 * Gets the establishment.
	 *
	 * @return the establishment
	 */
	public Date getEstablishment();
	
	/**
	 * Gets the establishment.
	 *
	 * @param establishment the establishment
	 */
	public void getEstablishment(Date establishment);
	
	/**
	 * Gets the members.
	 *
	 * @return the members
	 */
	public List<Person> getMembers();
	
	/**
	 * Sets the members.
	 *
	 * @param members the new members
	 */
	public void setMembers(List<Person> members);
	
	/**
	 * Gets the president.
	 *
	 * @return the president
	 */
	public Person getPresident();
	
	/**
	 * Sets the president.
	 *
	 * @param president the new president
	 */
	public void setPresident(Person president);
}
