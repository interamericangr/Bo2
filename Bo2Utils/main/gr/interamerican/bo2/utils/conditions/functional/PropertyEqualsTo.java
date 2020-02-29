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

import java.util.function.Function;

import gr.interamerican.bo2.utils.conditions.Condition;
import gr.interamerican.bo2.utils.conditions.ConditionOnTransformation;
import gr.interamerican.bo2.utils.conditions.EqualityCondition;

/**
 * A {@link Condition} that checks the equality over a specific property
 * (defined by it's getter) on an object.<br>
 * Do note that while the lower bound of P to {@link Comparable} is not
 * required, it is defined in order to avoid cases of miss-matching types
 * between getter and value (because generic resolved to object).
 *
 * @param <T>
 *            the generic type
 * @param <P>
 *            Type of transformation result.
 */
public class PropertyEqualsTo<T, P extends Comparable<? super P>> extends ConditionOnTransformation<T, P> {

	/**
	 * Public Constructor.
	 * 
	 * @param getter
	 *            Getter
	 * @param value
	 *            Value to Check
	 */
	public PropertyEqualsTo(Function<T, P> getter, P value) {
		super(getter::apply, new EqualityCondition<P>(value));
	}
}