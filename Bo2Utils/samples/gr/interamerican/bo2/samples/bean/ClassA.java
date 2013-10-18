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

/**
 * sample class
 */
public class ClassA {
	
	/**
	 * 
	 */
	private String stringA;
	
	/**
	 * 
	 */
	private Integer integerA;
	
	/**
	 * Creates a new TestEqualPropertyMatchingRule.ClassA object. 
	 * @param s 
	 * @param i 
	 *
	 */
	public ClassA(String s, Integer i) {
		this.stringA = s;
		this.integerA = i;
	}

	/**
	 * Assigns a new value to the stringA.
	 *
	 * @param stringA the stringA to set
	 */
	public void setStringA(String stringA) {
		this.stringA = stringA;
	}

	/**
	 * Gets the stringA.
	 *
	 * @return Returns the stringA
	 */
	public String getStringA() {
		return stringA;
	}

	/**
	 * Assigns a new value to the integerA.
	 *
	 * @param integerA the integerA to set
	 */
	public void setIntegerA(Integer integerA) {
		this.integerA = integerA;
	}

	/**
	 * Gets the integerA.
	 *
	 * @return Returns the integerA
	 */
	public Integer getIntegerA() {
		return integerA;
	}
}
