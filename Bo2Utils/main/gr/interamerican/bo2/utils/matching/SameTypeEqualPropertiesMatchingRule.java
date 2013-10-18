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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Matching rule that checks some properties of a class for equality.
 * 
 * The properties being checked are specified in the constructor.
 * 
 * @param <F> Type of first object.
 */
public class SameTypeEqualPropertiesMatchingRule<F> implements MatchingRule<F, F> {
	
	/**
	 * Rule to match all properties.
	 */
	MatchingRule<F, F> match;

	/**
	 * Creates a new EqualPropertiesMatchingRule object. 
	 *
	 * @param clazz
	 *	  
	 * @param propertiesToCompare
	 */
	public SameTypeEqualPropertiesMatchingRule
	(Class<F> clazz, String[] propertiesToCompare) {
		super();
		Set<String> important = new HashSet<String>(Arrays.asList(propertiesToCompare));		
		List<MatchingRule<F, F>> rules = new ArrayList<MatchingRule<F,F>>();
		PropertyDescriptor[] properties = JavaBeanUtils.getBeansProperties(clazz);
		for (PropertyDescriptor pd : properties) {
			if (important.contains(pd.getName())) {
				MatchingRule<F, F> propertyRule = 
					new PdBasedEqualPropertyMatchingRule<F, F>(pd, pd);
				rules.add(propertyRule);
			}
		}
		this.match = new AndMatchingRule<F, F>(rules);		
	}

	public boolean isMatch(F first, F second) {		
		return match.isMatch(first, second);
	}

}
