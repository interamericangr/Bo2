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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import gr.interamerican.bo2.utils.conditions.functional.PropertyIsNotNull;

/**
 * Checks that all listed properties of an object are not null.
 * 
 * @param <T>
 *            Type of object being checked.
 */
public class PropertiesAreNotNull<T> implements Condition<T> {

	/**
	 * Condition to delegate the check.
	 */
	Condition<T> condition;

	/**
	 * Creates a new PropertiesAreEitherFullOrEmpty object.
	 * 
	 * @param clazz
	 *            Class of object being checked.
	 * @param properties
	 *            Names of properties that must be not null.
	 * @deprecated Use {@link #PropertiesAreNotNull(Function...)}
	 */
	@Deprecated
	public PropertiesAreNotNull(Class<T> clazz, String... properties) {
		super();
		List<Condition<T>> isNotNull = new ArrayList<>();
		for (int i = 0; i < properties.length; i++) {
			isNotNull.add(new gr.interamerican.bo2.utils.conditions.PropertyIsNotNull<>(properties[i], clazz));
		}
		condition = new And<T>(isNotNull);
	}

	/**
	 * Public Constructor with method references.
	 * 
	 * @param getters
	 *            Getters of fields to check
	 */
	@SafeVarargs
	public PropertiesAreNotNull(Function<T, ?>... getters) {
		super();
		List<Condition<T>> isNotNull = new ArrayList<>();
		for (Function<T, ?> getter : getters) {
			isNotNull.add(new PropertyIsNotNull<>(getter));
		}
		condition = new And<T>(isNotNull);
	}

	@Override
	public boolean check(T t) {
		return condition.check(t);
	}
}