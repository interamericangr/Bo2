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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class ObjectTypeBean.
 */
public class ObjectTypeBean implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The object type list. */
	private List<ObjectType> objectTypeList =
		new ArrayList<ObjectType>();
	
	/** The object type. */
	private ObjectType objectType = 
		ObjectType.OBJECT1;
	/**
	 * Creates a new ObjectTypeBean object. 
	 *
	 */
	public ObjectTypeBean() {
		super();
	}

	/**
	 * Assigns a new value to the objectTypeList.
	 *
	 * @param objectTypeList the objectTypeList to set
	 */
	public void setObjectTypeList(List<ObjectType> objectTypeList) {
		this.objectTypeList = objectTypeList;
	}

	/**
	 * Gets the objectTypeList.
	 *
	 * @return Returns the objectTypeList
	 */
	public List<ObjectType> getObjectTypeList() {
		return objectTypeList;
	}

	/**
	 * Assigns a new value to the objectType.
	 *
	 * @param objectType the objectType to set
	 */
	public void setObjectType(ObjectType objectType) {
		this.objectType = objectType;
	}

	/**
	 * Gets the objectType.
	 *
	 * @return Returns the objectType
	 */
	public ObjectType getObjectType() {
		return objectType;
	}

	
}
