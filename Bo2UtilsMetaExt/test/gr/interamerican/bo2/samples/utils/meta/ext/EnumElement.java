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
package gr.interamerican.bo2.samples.utils.meta.ext;

import gr.interamerican.bo2.arch.ext.Codified;
import gr.interamerican.bo2.arch.ext.OwnedEntry;
import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;
import gr.interamerican.bo2.utils.Utils;

import java.io.Serializable;

/**
 * ����������� �� interface {@link OwnedEntry} ��� �� ����� ���
 * �� �������� ��� enums.
 */
public class EnumElement 
implements Serializable, OwnedEntry<Long, Long , Long> {
		
	/**
	 * serialize.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Enum ��� ���� ��� �������.
	 */
	private Enum<?> owner;
	/**
	 * ������� ������ ���� ����� ������ � �������.
	 */
	private Long typeId;

	/**
	 * ������� ������ ��� ����� ����� � �������.
	 */
	private Long subTypeId;
	
	/**
	 * ������� ��������.
	 */
	private Long code;
	
	/**
	 * ����� ��� ��������.
	 */
	private String name;
	
	

	/**
	 * Creates a new EnumElement object. 
	 *
	 * @param typeId
	 * @param element
	 */
	public EnumElement(Long typeId, Enum<?> element) {
		super();
		this.setTypeId(typeId);
		this.setSubTypeId(null);
		this.setCode(new Long(element.ordinal()+1));
		this.setName(element.toString());
		this.owner = element;
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

	public Long getTranslationResourceId() {		
		return null;
	}
	
	public String getTranslation(Long languageId) {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public TranslatableEntryOwner<Long, Long, Long> getOwner() {		
		return (TranslatableEntryOwner<Long, Long, Long>) owner;
	}

	public int compareTo(Codified<Long> o) {
		if(o==null) { return 1; }
		return Utils.nullSafeCompare(o.getCode(), this.getCode());
	}

}
