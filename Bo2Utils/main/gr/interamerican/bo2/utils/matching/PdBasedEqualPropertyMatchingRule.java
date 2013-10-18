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
package gr.interamerican.bo2.utils.matching;

import gr.interamerican.bo2.utils.JavaBeanUtils;
import gr.interamerican.bo2.utils.Utils;

import java.beans.PropertyDescriptor;

/**
 * Matching rule that checks the value of two properties. 
 * 
 * The rule matches two objects of two classes if the return values
 * of the properties 
 * 
 * @param <F> Type of first object.
 * @param <S> Type of second object.
 */
public class PdBasedEqualPropertyMatchingRule<F,S> implements MatchingRule<F, S> {
	
	/**
	 * Property descriptor of first class.
	 */
	private PropertyDescriptor firstPD;
	/**
	 * Property descriptor of second class.
	 */
	private PropertyDescriptor secondPD;

	/**
	 * Creates a new PropertyMatchingRule object. 
	 * 
	 * @param firstPD
	 *        First property descriptor. 
	 * @param secondPD 
	 *        Second property descriptor.
	 */
	public PdBasedEqualPropertyMatchingRule(PropertyDescriptor firstPD, PropertyDescriptor secondPD) {
		super();
		this.firstPD = firstPD;
		this.secondPD = secondPD;
	}

	public boolean isMatch(Object first, Object second) {
		Object firstValue = JavaBeanUtils.getProperty(firstPD, first);
		Object secondValue = JavaBeanUtils.getProperty(secondPD, second);
		return Utils.equals(firstValue, secondValue);
	}

}
