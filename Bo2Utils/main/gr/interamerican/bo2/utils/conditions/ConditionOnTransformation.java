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

import gr.interamerican.bo2.utils.adapters.AnyOperation;

/**
 * Applies a condition on a transformation of the specified object.
 * 
 * @param <A> 
 *        Type of object to apply the condition.
 * @param <P> 
 *        Type of transformation result.
 */
public class ConditionOnTransformation<A,P> 
implements Condition<A> {
	
	/**
	 * Transformation operation.
	 */
	AnyOperation<A, P> transformation;
	/**
	 * Condition to check on the result of the transformation.
	 */
	Condition<P> condition;
	
	public boolean check(A t) {
		P p = transformation.execute(t);
		return condition.check(p);
	}

	/**
	 * Creates a new ConditionOnTransformation object. 
	 *
	 * @param transformation
	 * @param condition
	 */
	public ConditionOnTransformation
	(AnyOperation<A, P> transformation, Condition<P> condition) {
		super();
		this.transformation = transformation;
		this.condition = condition;
	}

}
