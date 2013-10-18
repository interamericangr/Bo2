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

/**
 * Bean with code property.
 */
public class SampleBean1 {
	
	/**
	 * Long property.
	 */
	private Integer numberProperty;
	
	/**
	 * String property
	 */
	private String stringProperty;
	
	/**
	 * Name.
	 */
	private String name;
	
	/**
	 * Gets the numberProperty.
	 *
	 * @return Returns the numberProperty
	 */
	public Integer getNumberProperty() {
		return numberProperty;
	}

	/**
	 * Assigns a new value to the numberProperty.
	 *
	 * @param numberProperty the numberProperty to set
	 */
	public void setNumberProperty(Integer numberProperty) {
		this.numberProperty = numberProperty;
	}

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
	 * Gets the stringProperty.
	 *
	 * @return Returns the stringProperty
	 */
	public String getStringProperty() {
		return stringProperty;
	}

	/**
	 * Assigns a new value to the stringProperty.
	 *
	 * @param stringProperty the stringProperty to set
	 */
	public void setStringProperty(String stringProperty) {
		this.stringProperty = stringProperty;
	}

}
