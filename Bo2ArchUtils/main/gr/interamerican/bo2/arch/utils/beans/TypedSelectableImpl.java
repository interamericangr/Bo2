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
package gr.interamerican.bo2.arch.utils.beans;

import gr.interamerican.bo2.arch.ext.Codified;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.utils.Utils;

/**
 * Javabean implementation of {@link TypedSelectable}.
 * @param <C> 
 */
public class TypedSelectableImpl<C extends Comparable<? super C>> 
implements TypedSelectable<C> {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new TypedSelectableMock object. 
	 *
	 */
	public TypedSelectableImpl() {
		/* empty */
	}	
	
	/**
	 * Creates a new TypedSelectableMock object. 
	 *
	 * @param code
	 * @param typeId
	 * @param subTypeId
	 * @param name
	 */
	public TypedSelectableImpl(Long typeId, Long subTypeId, C code, String name) {
		super();
		this.code = code;
		this.typeId = typeId;
		this.subTypeId = subTypeId;
		this.name = name;
	}

	/**
	 * code
	 */
	private C code;
	/**
	 * typeId
	 */
	private Long typeId;
	/**
	 * subtypeid
	 */
	private Long subTypeId;
	/**
	 * name
	 */
	private String name;
	
	public C getCode() {
		return code;
	}

	public void setCode(C code) {
		this.code = code;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getSubTypeId() {
		return subTypeId;
	}

	public void setSubTypeId(Long subTypeId) {
		this.subTypeId = subTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TypedSelectableImpl) {
			@SuppressWarnings("unchecked")
			TypedSelectableImpl<C> that = (TypedSelectableImpl<C>) obj;
			return Utils.equals(this.typeId, that.typeId)
			    && Utils.equals(this.subTypeId, that.subTypeId)   
			    && Utils.equals(this.code, that.code);
		}
		return false;
	}	

	@Override
	public int hashCode() {
		Object[] fields = {typeId, subTypeId, code};
		return Utils.generateHashCode(fields);
	}

	public int compareTo(Codified<C> o) {
		if(o==null) { return 1; }
		return Utils.nullSafeCompare(o.getCode(), this.getCode());
	}

}
