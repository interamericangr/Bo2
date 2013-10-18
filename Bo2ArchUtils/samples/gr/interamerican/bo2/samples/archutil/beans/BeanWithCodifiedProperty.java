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
package gr.interamerican.bo2.samples.archutil.beans;

import gr.interamerican.bo2.arch.ext.Codified;

/**
 * Bean with a {@link Codified} property.
 */
public class BeanWithCodifiedProperty {
	
	/**
	 * Codified Long property.
	 */
	private Codified<Long> numberProperty;
	
	/**
	 * Codified String property.
	 */
	private Codified<String> stringProperty;
	
	/**
	 * Simple property.
	 */
	private String name;

	/**
	 * Gets the name.
	 *
	 * @return Returns the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Assigns a new value to the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the numberProperty.
	 *
	 * @return Returns the numberProperty
	 */
	public Codified<Long> getNumberProperty() {
		return numberProperty;
	}

	/**
	 * Assigns a new value to the numberProperty.
	 *
	 * @param numberProperty the numberProperty to set
	 */
	public void setNumberProperty(Codified<Long> numberProperty) {
		this.numberProperty = numberProperty;
	}

	/**
	 * Gets the stringProperty.
	 *
	 * @return Returns the stringProperty
	 */
	public Codified<String> getStringProperty() {
		return stringProperty;
	}

	/**
	 * Assigns a new value to the stringProperty.
	 *
	 * @param stringProperty the stringProperty to set
	 */
	public void setStringProperty(Codified<String> stringProperty) {
		this.stringProperty = stringProperty;
	}

}
