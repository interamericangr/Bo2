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
package gr.interamerican.bo2.utils.beans;

import gr.interamerican.bo2.utils.JavaBeanUtils;
import gr.interamerican.bo2.utils.Utils;

import java.util.List;

/**
 * {@link ExpressionBasedSelection} makes a selection by evaluating a 
 * series of expressions. <br/>
 * 
 * @param <V> 
 *        Type of the return type of the expressions being evaluated.
 * @param <S> 
 *        Type of selection.
 * 
 */
public class ExpressionBasedSelection<V,S> {
	/**
	 * Pairs of property expression and selection.
	 */
	List<Pair<String, S>> pairs;
	
	/**
	 * Creates a new ExpressionBasedSelection object. 
	 *
	 * @param pairs
	 *        List that contains pairs of a property expression and the
	 *        selection that will be returned if the property expression
	 *        evaluated on a bean, returns a result that is equal with the
	 *        specified expectedValue.
	 *         
	 * @param expectedValue
	 *        Value expected to get from the expressions evaluations.
	 */
	public ExpressionBasedSelection(List<Pair<String, S>> pairs, V expectedValue) {
		super();
		this.pairs = pairs;
		this.expectedValue = expectedValue;
	}


	/**
	 * Value being compared against the results of the expressions
	 * evaluations.
	 */
	V expectedValue;
	
	
	/**
	 * Evaluates the property expressions against the specified bean 
	 * until the result of an expression is equal to the specified expected 
	 * result. <br/>
	 * 
	 * When the result of the expression is equal with the expected result,
	 * then the method returns the right element of the pair that contained
	 * the expression.
	 * 
	 * @param bean
	 * 
	 * @return Returns the right element of the first pair, who's left element
	 *         property expression, evaluated on the specified bean will return
	 *         a result that is equal with the <code>expecedValue</code> of this
	 *         {@link ExpressionBasedSelection}. If none of the expressions
	 *         returns the expected value, then the method returns null.
	 */
	public S select(Object bean) {
		for (Pair<String, S> pair : pairs) {
			String expression = pair.getLeft();
			@SuppressWarnings("unchecked")
			V value = (V) JavaBeanUtils.getNestedProperty(bean, expression);
			if (Utils.equals(value, expectedValue)) {
				return pair.getRight();
			}			
		}
		return null;
	}
	
	

}
