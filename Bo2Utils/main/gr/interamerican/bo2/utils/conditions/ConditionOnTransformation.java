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

import java.util.function.Function;

import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * Applies a condition on a {@link Function} of the specified object.<br>
 * The {@link Transformation}, as the name of this class implies to, is also a
 * {@link Function} itself.
 * 
 * @param <A>
 *            Type of object to apply the condition.
 * @param <P>
 *            Type of transformation result.
 */
public class ConditionOnTransformation<A, P> implements Condition<A> {

	/**
	 * Transformation operation.
	 */
	Function<A, P> transformation;

	/**
	 * Condition to check on the result of the transformation.
	 */
	Condition<P> condition;

	@Override
	public boolean check(A t) {
		P p = transformation.apply(t);
		return condition.check(p);
	}

	/**
	 * Creates a new ConditionOnTransformation object.
	 *
	 * @param transformation
	 *            the transformation
	 * @param condition
	 *            the condition
	 */
	public ConditionOnTransformation(Function<A, P> transformation, Condition<P> condition) {
		this.transformation = transformation;
		this.condition = condition;
	}

	/**
	 * Gets the condition.
	 *
	 * @return Returns the condition
	 */
	public Condition<P> getCondition() {
		return condition;
	}
}