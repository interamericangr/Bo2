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

import java.util.Collection;
import java.util.function.Function;

import gr.interamerican.bo2.utils.conditions.ConditionOnTransformation;
import gr.interamerican.bo2.utils.conditions.ExistsIn;

/**
 * Condition that checks if a property of an object exists in a collection.
 * 
 * @param <T>
 *            Type of objects being checked by the condition.
 * @param <P>
 *            Type of transformation result.
 */
public class PropertyExistsIn<T, P> extends ConditionOnTransformation<T, P> {

	/**
	 * Public Constructor.
	 * 
	 * @param getter
	 *            Getter
	 * @param collection
	 *            Values.
	 */
	public PropertyExistsIn(Function<T, P> getter, Collection<P> collection) {
		super(getter::apply, new ExistsIn<P>(collection));
	}

	/**
	 * Public Constructor.
	 * 
	 * @param getter
	 *            Getter
	 * @param collection
	 *            Values.
	 */
	@SafeVarargs
	public PropertyExistsIn(Function<T, P> getter, P... collection) {
		super(getter::apply, new ExistsIn<P>(collection));
	}
}