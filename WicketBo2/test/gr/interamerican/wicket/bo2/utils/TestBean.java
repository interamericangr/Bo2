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
package gr.interamerican.wicket.bo2.utils;

import java.io.Serializable;

/**
 * 
 */
public class TestBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Long firstAttribute;
	
	/**
	 * 
	 */
	private Long secondAttribute;
	
	/**
	 * 
	 */
	private Long thirdAttribute;

	/**
	 * Creates a new TestBean object. 
	 *
	 */
	public TestBean() {
		super();
	}

	/**
	 * Gets the firstAttribute.
	 *
	 * @return Returns the firstAttribute
	 */
	public Long getFirstAttribute() {
		return firstAttribute;
	}

	/**
	 * Assigns a new value to the firstAttribute.
	 *
	 * @param firstAttribute the firstAttribute to set
	 */
	public void setFirstAttribute(Long firstAttribute) {
		this.firstAttribute = firstAttribute;
	}

	/**
	 * Gets the secondAttribute.
	 *
	 * @return Returns the secondAttribute
	 */
	public Long getSecondAttribute() {
		return secondAttribute;
	}

	/**
	 * Assigns a new value to the secondAttribute.
	 *
	 * @param secondAttribute the secondAttribute to set
	 */
	public void setSecondAttribute(Long secondAttribute) {
		this.secondAttribute = secondAttribute;
	}

	/**
	 * Gets the thirdAttribute.
	 *
	 * @return Returns the thirdAttribute
	 */
	public Long getThirdAttribute() {
		return thirdAttribute;
	}

	/**
	 * Assigns a new value to the thirdAttribute.
	 *
	 * @param thirdAttribute the thirdAttribute to set
	 */
	public void setThirdAttribute(Long thirdAttribute) {
		this.thirdAttribute = thirdAttribute;
	}

}
