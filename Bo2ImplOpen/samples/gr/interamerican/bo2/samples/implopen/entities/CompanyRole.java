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

import gr.interamerican.bo2.arch.ext.Codified;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.utils.Utils;

/**
 * The Class CompanyRole.
 */
public class CompanyRole implements TypedSelectable<Long> {
	
	/** id. */
	private static final long serialVersionUID = 1L;

	/** code. */
	private Long code;
	
	/** name. */
	private String name;
	
	/** type. */
	private Long typeId;
	
	/** subtype. */
	private Long subTypeId;
	
	/**
	 * Creates a new CompanyRole object. 
	 *
	 * @param typeId the type id
	 * @param subTypeId the sub type id
	 * @param code the code
	 * @param name the name
	 */
	public CompanyRole(Long typeId, Long subTypeId, Long code, String name) {
		this.typeId = typeId;
		this.subTypeId = subTypeId;
		this.code = code;
		this.name = name;
	}

	@Override
	public Long getCode() {
		return code;
	}

	@Override
	public void setCode(Long code) {
		this.code = code;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	@Override
	public Long getTypeId() {
		return typeId;
	}

	@Override
	public void setSubTypeId(Long subTypeId) {
		this.subTypeId = subTypeId;
	}

	@Override
	public Long getSubTypeId() {
		return subTypeId;
	}

	@Override
	public int compareTo(Codified<Long> o) {
		if(o==null) { return 1; }
		return Utils.nullSafeCompare(o.getCode(), this.getCode());
	}

}
