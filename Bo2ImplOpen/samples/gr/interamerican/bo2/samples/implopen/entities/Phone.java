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
package gr.interamerican.bo2.samples.implopen.entities;

import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.impl.open.po.ValuesBasedKey;


/**
 * The Class Phone.
 */
public class Phone implements PersistentObject<ValuesBasedKey> {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** the phone number. */
	private String phoneNumber;

	/** encapsulates the userid, which is the foreign key to the users table. */
	private ValuesBasedKey key = new ValuesBasedKey(0);

	@Override
	public ValuesBasedKey getKey() {
		return this.key;
	}

	/**
	 * Sets the key.
	 *
	 * @param key the new key
	 */
	@Override
	public void setKey(ValuesBasedKey key) {
		this.key = key;
	}

	/**
	 * Gets the user id.
	 *
	 * @return returns the user id
	 */
	public int getUserId() {
		return (Integer) key.getElement(1);
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId            sets the user id
	 */
	public void setUserId(int userId) {
		this.key = new ValuesBasedKey(userId);
	}

	/**
	 * Gets the phone number.
	 *
	 * @return returns the phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets the phone number.
	 *
	 * @param phoneNumber            sets the phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the dummy.
	 *
	 * @param userId the user id
	 * @return a mock user object for testing
	 */
	public static Phone getDummy(int userId) {
		Phone phone = new Phone();
		phone.setUserId(userId);
		phone.setPhoneNumber("+3069xxxxxxxx"); //$NON-NLS-1$
		return phone;
	}
	
	@Override
	public void tidy() {
		/* empty */
	}

}
