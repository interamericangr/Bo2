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
package gr.interamerican.bo2.utils.conditions;


/**
 * Is equal {@link Condition}.
 *  
 */
public class EqualsIgnoreCaseCondition implements Condition<String> {
	/**
	 * Creates a new EqualityCondition object. 
	 * 
	 * @param value 
	 *        Value checked for equality.
	 */
	public EqualsIgnoreCaseCondition(String value) {
		super();
		this.value = value;
	}


	/**
	 * Value checked for equality.
	 */
	String value;

	
	public boolean check(String t) {
		if (t==null) {
			return (value==null);
		}
		return t.equalsIgnoreCase(value);
	}

}
