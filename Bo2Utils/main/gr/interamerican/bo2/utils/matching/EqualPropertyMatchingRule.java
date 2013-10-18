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
public class EqualPropertyMatchingRule<F,S> implements MatchingRule<F, S> {
	
	/**
	 * Matching rule.
	 */
	MatchingRule<F, S> match;

	/**
	 * Creates a new PropertyMatchingRule object.
	 *
	 * @param firstClass
	 *        
	 * @param secondClass
	 * @param firstProperty
	 * @param secondProperty
	 */
	public EqualPropertyMatchingRule(Class<F> firstClass, Class<S> secondClass,
			String firstProperty, String secondProperty) {
		super();
		PropertyDescriptor firstPD = 
			JavaBeanUtils.mandatoryPropertyOfClass(firstProperty, firstClass);		
		PropertyDescriptor secondPD;
		if (firstClass.equals(secondClass) && firstProperty.equals(secondProperty)) {
			secondPD = firstPD;
		} else {
			secondPD = JavaBeanUtils.mandatoryPropertyOfClass(secondProperty, secondClass);		
		}		
		match = new PdBasedEqualPropertyMatchingRule<F, S>(firstPD, secondPD);
		
	}
	
	/**
	 * Throws a runtime exception if a class does not have 
	 * a property with a specified name.
	 *  
	 * @param clazz
	 * @param property
	 */
	@SuppressWarnings("nls")
	void invalidProperty(Class<?> clazz, String property) {
		String msg = "Property with name " + property + " not found in class " + clazz.getName();
		throw new RuntimeException(msg);
	}

	public boolean isMatch(F first, S second) {
		return match.isMatch(first, second);
	}

}
