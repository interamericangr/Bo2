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

/**
 * Key for User.
 */
public class UserKey
implements Key {
	
	/**
	 * Creates a new UserKey object. 

	 */
	public UserKey() {
		super();		
	}
	
	
	/**
	 * Creates a new UserKey object. 
	 *
	 * @param id
	 */
	public UserKey(Integer id) {
		super();
		this.id = id;
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * id
	 */
	private Integer id;

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
	
	@Override
	public int compareTo(Key o) {
		if (o==null) {
			return 1;
		}
		if (o instanceof UserKey) {
			UserKey that = (UserKey) o;
			if (id==null) {
				if (that.id==null) {
					return 0;
				} else {
					return -1;
				}
			} else {
				if (that.id==null) {
					return 1;
				} else {
					return id.compareTo(that.id);
				}
			}			
		} else {
			return -1;
		}
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Key) {
			return compareTo((Key)obj)==0;
		}
		return false;
	}
	
	@Override
	public int hashCode() {	
		if (id==null) {
			return 0;
		}
		return id.hashCode();
	}

}
