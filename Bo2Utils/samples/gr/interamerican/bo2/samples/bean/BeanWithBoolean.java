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
package gr.interamerican.bo2.samples.bean;

import java.io.Serializable;

/**
 * Bean with a boolean property.
 */
public class BeanWithBoolean implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private boolean bool;

	/**
	 * 
	 */
	private Boolean wrappedBool;

	/**
	 * 
	 */
	boolean hacky;

	/**
	 * 
	 */
	boolean isLala;

	/**
	 * Creates a new BeanWithBoolean object.
	 */
	public BeanWithBoolean() {
		super();
	}

	/**
	 * Creates a new BeanWithBoolean object.
	 * @param bool
	 */
	public BeanWithBoolean(boolean bool) {
		super();
		this.bool = bool;
	}

	/**
	 * Gets the bool.
	 *
	 * @return Returns the bool
	 */
	public boolean isBool() {
		return bool;
	}

	/**
	 * Assigns a new value to the bool.
	 *
	 * @param bool the bool to set
	 */
	public void setBool(boolean bool) {
		this.bool = bool;
	}

	/**
	 * Gets the wrappedBool.
	 *
	 * @return Returns the wrappedBool
	 */
	public Boolean getWrappedBool() {
		return wrappedBool;
	}

	/**
	 * Assigns a new value to the wrappedBool.
	 *
	 * @param wrappedBool the wrappedBool to set
	 */
	public void setWrappedBool(Boolean wrappedBool) {
		this.wrappedBool = wrappedBool;
	}

	/**
	 * Gets the hacky.
	 *
	 * @return Returns the hacky
	 */
	public boolean getHacky() {
		return hacky;
	}

	/**
	 * Assigns a new value to the hacky.
	 *
	 * @param hacky the hacky to set
	 */
	public void setHacky(boolean hacky) {
		this.hacky = hacky;
	}

	/**
	 * Gets the isLala.
	 *
	 * @return Returns the isLala
	 */
	public boolean getIsLala() {
		return isLala;
	}

	/**
	 * Assigns a new value to the isLala.
	 *
	 * @param isLala the isLala to set
	 */
	public void setIsLala(boolean isLala) {
		this.isLala = isLala;
	}




}
