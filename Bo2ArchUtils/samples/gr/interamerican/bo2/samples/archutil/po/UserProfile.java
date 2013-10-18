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

import gr.interamerican.bo2.arch.PersistentObject;


/**
 * User.
 * 
 * Simple PersistentObject for the tests.
 * 
 */
public class UserProfile
implements PersistentObject<UserProfileKey> {

	/**
	 * serialVersionUID
	 */
	static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	Integer profileId;

	/**
	 * id
	 */
	Integer userId;

	/**
	 * name
	 */
	String name;

	/**
	 * Creates a new User object.
	 * @param userId
	 * @param profileId
	 * @param name
	 *
	 */
	public UserProfile(Integer userId, Integer profileId, String name) {
		super();
		this.userId = userId;
		this.profileId = profileId;
		this.name = name;
	}

	/**
	 * Creates a new User object.
	 *
	 */
	public UserProfile() {
		super();
	}

	/**
	 * @return profile id.
	 */
	public Integer getProfileId() {
		return profileId;
	}

	/**
	 * sets the profile id
	 * 
	 * @param profileId
	 */
	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

	/**
	 * @return the user id.
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * sets the user id.
	 * 
	 * @param userId
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Creates and initializes a new user profile.
	 * @param userId
	 * @param profileId
	 * 
	 * @return returns a user profile.
	 */
	public static UserProfile getDummy(int userId, int profileId) {
		UserProfile userProfile = new UserProfile();
		userProfile.setUserId(userId);
		userProfile.setProfileId(profileId);
		userProfile.setName("name"); //$NON-NLS-1$
		return userProfile;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("userId="); //$NON-NLS-1$
		sb.append(userId);
		sb.append(", profileId="); //$NON-NLS-1$
		sb.append(profileId);
		sb.append(", name="); //$NON-NLS-1$
		sb.append(name);
		return sb.toString();
	}

	@Override
	public UserProfileKey getKey() {
		return new UserProfileKey(userId, profileId);
	}

	@Override
	public void setKey(UserProfileKey key) {
		userId = key.getId();
		profileId = key.getProfileId();
	}

	@Override
	public void tidy() {
		/* empty */
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserProfile) {
			UserProfile that = (UserProfile) obj;
			return getKey().equals(that.getKey());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getKey().hashCode();
	}

}
