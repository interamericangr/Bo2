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
package gr.interamerican.bo2.impl.open.beans;

import gr.interamerican.bo2.arch.ext.Typed;

import java.io.Serializable;

/**
 * Constant type is an object that has an unmodifiable typeId.
 * 
 * The typeID is defined in the constructor. Once defined it can't change.
 * However this class provides a <code>setTypeId(Long typeId)</code> that
 * has no effect.
 */
public class ConstantType implements Serializable {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Type Id.
	 */
	private Long typeId;

	/**
	 * Gets the typeId.
	 * 
	 * @return Returns the type Id.
	 */
	public Long getTypeId() {
		return typeId;
	}

	/**
	 * This method has no effect.
	 * 
	 * It exists so that this class implements the {@link Typed} 
	 * interface, but it will not change the typeId.
	 * 
	 * @param typeId
	 */
	public void setTypeId(@SuppressWarnings("unused") Long typeId) {
		/* do nothing */
	}

	/**
	 * Creates a new ConstantType object. 
	 *
	 * @param typeId The typeId of this object.
	 */
	public ConstantType(Long typeId) {
		super();
		this.typeId = typeId;
	}
}
