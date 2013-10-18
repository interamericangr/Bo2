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
package gr.interamerican.bo2.samples.archutil.po;

import gr.interamerican.bo2.arch.Key;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.comparators.ArrayComparator;

/**
 * Key for User.
 */
public class UserProfileKey
extends UserKey {
	
	/**
	 * Creates a new UserKey object. 

	 */
	public UserProfileKey() {
		super();		
	}
	
	/**
	 * Creates a new UserKey object. 
	 * @param usrId 
	 *
	 * @param profileId
	 */
	public UserProfileKey(Integer usrId, Integer profileId) {
		super(usrId);
		this.profileId = profileId;
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * id
	 */
	private Integer profileId;

	/**
	 * Gets the id.
	 * 
	 * @return Returns the id.
	 */
	public Integer getProfileId() {
		return profileId;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id new id.
	 */
	public void setProfileId(Integer id) {
		this.profileId = id;
	}
	
	@Override
	public int compareTo(Key o) {
		if (o == null) {
			return 1;
		}
		if (UserProfileKey.class.isInstance(o)) {
			UserProfileKey that = (UserProfileKey) o;			
			return new ArrayComparator().compare(this.getElements(), that.getElements());
		}
		return -1;
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserProfileKey) {
			return compareTo((UserProfileKey)obj)==0;
		}
		return false;
	}
	
	@Override
	public int hashCode() {	
		return Utils.generateHashCode(getElements());
	}
	
	/**
	 * @return Returns an array with the key properties.
	 */
	Object[] getElements() {
		return new Object[]{getId(), getProfileId()};
	}

}
