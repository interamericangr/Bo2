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
 * 
 */
public class CompanyRole implements TypedSelectable<Long> {
	
	/**
	 * id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * code
	 */
	private Long code;
	
	/**
	 * name
	 */
	private String name;
	
	/**
	 * type
	 */
	private Long typeId;
	
	/**
	 * subtype
	 */
	private Long subTypeId;
	
	/**
	 * Creates a new CompanyRole object. 
	 * 
	 * @param typeId 
	 * @param subTypeId 
	 * @param code 
	 * @param name 
	 *
	 */
	public CompanyRole(Long typeId, Long subTypeId, Long code, String name) {
		this.typeId = typeId;
		this.subTypeId = subTypeId;
		this.code = code;
		this.name = name;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setSubTypeId(Long subTypeId) {
		this.subTypeId = subTypeId;
	}

	public Long getSubTypeId() {
		return subTypeId;
	}

	public int compareTo(Codified<Long> o) {
		if(o==null) { return 1; }
		return Utils.nullSafeCompare(o.getCode(), this.getCode());
	}

}
