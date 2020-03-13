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
package gr.interamerican.bo2.utils.conditions.functional;

import static gr.interamerican.bo2.utils.FunctionalUtils.nullSafeSynthesize;

import java.util.function.Function;

import gr.interamerican.bo2.utils.FunctionalUtils;
import gr.interamerican.bo2.utils.conditions.Condition;
import gr.interamerican.bo2.utils.conditions.ConditionOnTransformation;

/**
 * Checks a condition on a nested property of the specified object.<br>
 * This simply uses the
 * {@link FunctionalUtils#nullSafeSynthesize(Function, Function)}
 * for easy constructing with up to a 3 level nested property.
 * 
 * @param <T>
 *            Type of object being checked by the condition.
 * @param <P>
 *            Type of transformation result.
 */
public class ConditionOnNestedProperty<T, P> extends ConditionOnTransformation<T, P> {

	/**
	 * Public Constructor with nesting on 1 level.
	 * 
	 * @param <A>
	 *            First Type
	 * @param firstGetter
	 *            First Getter
	 * @param secondGetter
	 *            Second Getter
	 * @param condition
	 *            Condition to check on the property. This condition must be
	 *            applicable to the type of the specified property.
	 */
	public <A> ConditionOnNestedProperty(Function<T, A> firstGetter, Function<A, P> secondGetter,
			Condition<P> condition) {
		super(nullSafeSynthesize(firstGetter, secondGetter), condition);
	}

	/**
	 * Public Constructor with nesting on 2 levels.
	 * 
	 * @param <A>
	 *            First Type
	 * @param <B>
	 *            Second Type
	 * @param firstGetter
	 *            First Getter
	 * @param secondGetter
	 *            Second Getter
	 * @param thirdGetter
	 *            Third Getter
	 * @param condition
	 *            Condition to check on the property. This condition must be
	 *            applicable to the type of the specified property.
	 */
	public <A, B> ConditionOnNestedProperty(Function<T, A> firstGetter, Function<A, B> secondGetter,
			Function<B, P> thirdGetter, Condition<P> condition) {
		super(nullSafeSynthesize(nullSafeSynthesize(firstGetter, secondGetter), thirdGetter), condition);
	}

	/**
	 * Public Constructor with nesting on 3 levels.
	 * 
	 * @param <A>
	 *            First Type
	 * @param <B>
	 *            Second Type
	 * @param <C>
	 *            Third Type
	 * 
	 * @param firstGetter
	 *            First Getter
	 * @param secondGetter
	 *            Second Getter
	 * @param thirdGetter
	 *            Third Getter
	 * @param fourthGetter
	 *            Fourth Getter
	 * @param condition
	 *            Condition to check on the property. This condition must be
	 *            applicable to the type of the specified property.
	 */
	public <A, B, C> ConditionOnNestedProperty(Function<T, A> firstGetter, Function<A, B> secondGetter,
			Function<B, C> thirdGetter, Function<C, P> fourthGetter, Condition<P> condition) {
		super(nullSafeSynthesize(nullSafeSynthesize(nullSafeSynthesize(firstGetter, secondGetter), thirdGetter),
				fourthGetter), condition);
	}
}