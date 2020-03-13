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

import java.util.ArrayList;
import java.util.List;

import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.conditions.And;
import gr.interamerican.bo2.utils.conditions.Condition;

/**
 * Checks a condition based on multiple properties of an object.
 * 
 * @param <T>
 *            Type of object being checked by the condition.
 */
public class PropertiesEqual<T> implements Condition<T> {

	/**
	 * Checks equality of all specified properties.
	 */
	final And<T> and;

	/**
	 * Public Constructor.
	 *
	 * @param matches
	 *            {@link Match}es between getters and values
	 */
	@SafeVarargs
	public PropertiesEqual(Match<T, ?>... matches) {
		List<Condition<T>> conditions = new ArrayList<>();
		for (Match<T, ?> match : matches) {
			conditions.add(getCondition(match));
		}
		and = new And<>(conditions);
	}

	/**
	 * Creates a {@link Condition} for a {@link Match}
	 * 
	 * @param match
	 *            a Single {@link Match}
	 * @return {@link Condition} based on that {@link Match}
	 */
	<P extends Comparable<? super P>> Condition<T> getCondition(Match<T, ?> match) {
		Match<T, P> casted = Utils.cast(match);
		return new PropertyEqualsTo<>(casted.getGetter(), casted.getValue());
	}

	@Override
	public boolean check(T t) {
		return and.check(t);
	}
}