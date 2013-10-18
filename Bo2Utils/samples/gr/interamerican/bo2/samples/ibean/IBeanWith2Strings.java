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
package gr.interamerican.bo2.samples.ibean;

/**
 * Bean interface with 2 strings.
 */
public interface IBeanWith2Strings {
	/**
	 * Gets the String 1.
	 * 
	 * @return String 1.
	 */
	String getString1();
	
	/**
	 * Sets the string 1.
	 * 
	 * @param string1
	 */
	void setString1(String string1);
	
	/**
	 * Gets the String 2.
	 * 
	 * @return String 2.
	 */
	String getString2();

	/**
	 * Sets the string 2.
	 * 
	 * @param string2
	 */
	void setString2(String string2);

}
