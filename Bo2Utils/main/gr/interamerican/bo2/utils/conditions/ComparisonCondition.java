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

import gr.interamerican.bo2.utils.comparators.ComparablesComparator;

import java.util.Comparator;

/**
 * Abstract Condition based on a comparison done by a comparator.
 * 
 * @param <T> 
 *        Type of object being checked.
 * 
 */
public abstract class ComparisonCondition<T> 
implements Condition<T> {
	
	/**
	 * Comparator used for the comparison.
	 */
	protected Comparator<T> comparator;
	
	/**
	 * Value being compared with the object specified on <code>check(t)</code>
	 */
	protected T comparedValue;
	
	
	/**
	 * Creates a new LessThan object. 
	 *
	 * @param comparator
	 *        Comparator doing the comparison.
	 * @param comparedValue
	 *        Value being compared against objects being checked by the
	 *        condition.
	 */
	public ComparisonCondition(Comparator<T> comparator, T comparedValue) {
		super();
		this.comparator = comparator;
		this.comparedValue = comparedValue;
	}
	
	/**
	 * Creates a new LessThan object. 
	 *
	 * This constructor will use the default comparator for comparable objects
	 * ({@link ComparablesComparator}). This comparator can be used only if
	 * comparedValue is {@link Comparable}.
	 * 
	 * @param comparedValue
	 *        Value being compared against objects being checked by the
	 *        condition.
	 *        
	 */
	@SuppressWarnings("unchecked")
	public ComparisonCondition(T comparedValue) {
		if (!(comparedValue instanceof Comparable)) {
			String msg = "comparedValue is not Comparable"; //$NON-NLS-1$
			throw new RuntimeException(msg);
		}
		this.comparator = (Comparator<T>) new ComparablesComparator();		
		this.comparedValue = comparedValue;
	}
	
	

}
