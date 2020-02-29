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
package gr.interamerican.bo2.samples.implopen.beans;

import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.impl.open.annotations.TypedSelectableProperties;

/**
 * The Class TypedSelectableImpl.
 */
@TypedSelectableProperties(code="codebar", name = "descr", subtype = "null", type = "type")
//@TypedSelectableProperties(code="codebar", name = "descr", subtype = "null", type = "X")
public abstract class TypedSelectableImpl implements TypedSelectable<Long>, TypedSelectable2{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The codebar. */
	private Long codebar;
	
	/** The type. */
	private Long type;
	
	/** The descr. */
	private String descr;

	/**
	 * Gets the codebar.
	 *
	 * @return Returns the codebar
	 */
	@Override
	public Long getCodebar() {
		return codebar;
	}

	/**
	 * Assigns a new value to the codebar.
	 *
	 * @param codebar the codebar to set
	 */
	@Override
	public void setCodebar(Long codebar) {
		this.codebar = codebar;
	}

	/**
	 * Gets the type.
	 *
	 * @return Returns the type
	 */
	@Override
	public Long getType() {
		return type;
	}

	/**
	 * Assigns a new value to the type.
	 *
	 * @param type the type to set
	 */
	@Override
	public void setType(Long type) {
		this.type = type;
	}

	/**
	 * Assigns a new value to the descr.
	 *
	 * @param descr the descr to set
	 */
	@Override
	public void setDescr(String descr) {
		this.descr = descr;
	}

	/**
	 * Gets the descr.
	 *
	 * @return Returns the descr
	 */
	@Override
	public String getDescr() {
		return descr;
	}


}
