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

import gr.interamerican.bo2.impl.open.po.AbstractKey;

/**
 * Key for User.
 */
public class AddressKey 
extends AbstractKey {
	
	/**
	 * Creates a new UserKey object. 

	 */
	public AddressKey() {
		super();		
	}
	
	
	/**
	 * Creates a new UserKey object. 
	 *
	 * @param id
	 */
	public AddressKey(Integer id) {
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
	
	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.impl.open.po.AbstractKeyImpl#getFields()
	 */
	@Override
	protected Object[] getElements() {
		Object[] o = {id};
		return o;
	}

}
