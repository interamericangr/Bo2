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

import gr.interamerican.bo2.arch.ModificationRecord;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.ext.Codified;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.beans.ModificationRecordImpl;
import gr.interamerican.bo2.utils.SelectionUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.annotations.Child;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * User.
 * 
 * Simple PersistentObject for the tests.
 * 
 */
public class User
implements TypedSelectable<Long>, PersistentObject<UserKey>, ModificationRecord {

	/**
	 * serialVersionUID
	 */
	static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	Integer id;

	/**
	 * name
	 */
	String name;

	/**
	 * role id
	 */
	int roleId;

	/**
	 * user id
	 */
	String usrid;

	/**
	 * user profiles.
	 */
	@Child Set<UserProfile> profiles = new HashSet<UserProfile>();

	/**
	 * {@link ModificationRecord}.
	 */
	ModificationRecord mdf = new ModificationRecordImpl();

	/**
	 * Creates a new User object.
	 *
	 * @param id
	 * @param name
	 * @param roleId
	 * @param usrid
	 */
	public User(Integer id, String name, int roleId, String usrid) {
		super();
		this.id = id;
		this.name = name;
		this.roleId = roleId;
		this.usrid = usrid;
	}

	/**
	 * Creates a new User object.
	 *
	 */
	public User() {
		super();
	}



	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the roleId
	 */
	public int getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the user id
	 */
	public String getUsrid() {
		return usrid;
	}

	/**
	 * @param usrid
	 *            the user id to set
	 */
	public void setUsrid(String usrid) {
		this.usrid = usrid;
	}


	/**
	 * Gets the profiles.
	 *
	 * @return Returns the profiles
	 */
	public Set<UserProfile> getProfiles() {
		return profiles;
	}

	/**
	 * Assigns a new value to the profiles.
	 *
	 * @param profiles the profiles to set
	 */
	public void setProfiles(Set<UserProfile> profiles) {
		this.profiles = profiles;
	}

	/**
	 * Creates and initializes a new user.
	 * 
	 * @param id
	 *            id of the new user.
	 * 
	 * @return returns a user.
	 */
	public static User getDummy(int id) {
		User user = new User();
		user.setId(id);
		user.setName("name"); //$NON-NLS-1$
		user.setUsrid("US00"); //$NON-NLS-1$
		user.setRoleId(0);
		return user;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id="); //$NON-NLS-1$
		sb.append(getId());
		sb.append(", usrid="); //$NON-NLS-1$
		sb.append(usrid);
		sb.append(", name="); //$NON-NLS-1$
		sb.append(name);
		sb.append(", roleId="); //$NON-NLS-1$
		sb.append(roleId);
		return sb.toString();
	}

	@Override
	public UserKey getKey() {
		return new UserKey(id);
	}

	@Override
	public void setKey(UserKey key) {
		setId(key.getId());
	}

	@Override
	public Long getTypeId() {
		return 1L;
	}

	@Override
	public void setTypeId(Long typeId) {
		/* empty */
	}

	@Override
	public Long getSubTypeId() {
		return new Long(roleId);
	}

	@Override
	public void setSubTypeId(Long subTypeId) {
		setRoleId(subTypeId.intValue());
	}

	@Override
	public void setCode(Long code) {
		if (code!=null) {
			id = code.intValue();
		}
	}

	@Override
	public Long getCode() {
		if (id==null) {
			return null;
		}
		return new Long(id);
	}

	@Override
	public void tidy() {
		/* empty */
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User that = (User) obj;
			return getKey().equals(that.getKey());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getKey().hashCode();
	}

	@Override
	public int compareTo(Codified<Long> o) {
		if(o==null) { return 1; }
		return Utils.nullSafeCompare(o.getCode(), getCode());
	}

	@Override
	public Date getLastModified() {
		return mdf.getLastModified();
	}

	@Override
	public void setLastModified(Date lastModified) {
		mdf.setLastModified(lastModified);
	}

	@Override
	public String getLastModifiedBy() {
		return mdf.getLastModifiedBy();
	}

	@Override
	public void setLastModifiedBy(String lastModifiedBy) {
		mdf.setLastModifiedBy(lastModifiedBy);
	}

	/**
	 * Gets the UserProfile associated with this that has the specified profileNo.
	 * 
	 * @param profileId
	 * @return UserProfile.
	 */
	public UserProfile getProfileById(Integer profileId) {
		if (profiles==null) {
			return null;
		}
		return SelectionUtils.selectFirstByProperty("profileId", profileId, profiles, UserProfile.class);		 //$NON-NLS-1$
	}


}
