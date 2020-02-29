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

/**
 * Something that contains a Matching Criteria on a bean, aka the getter of a
 * property and the value we expect for that property.<br>
 * Do note that while the lower bound of P to {@link Comparable} is not
 * required, it is defined in order to avoid cases of miss-matching types
 * between getter and value (because generic resolved to object).
 * 
 * @param <T>
 *            Type of Bean
 * @param <P>
 *            Type of Value
 */
public class Match<T, P extends Comparable<? super P>> {

	/**
	 * The Getter
	 */
	private final Function<T, P> getter;
	/**
	 * The Value
	 */
	private final P value;

	/**
	 * Public Constructor.
	 * 
	 * @param getter
	 *            The Getter
	 * @param value
	 *            The Value
	 */
	public Match(Function<T, P> getter, P value) {
		this.getter = getter;
		this.value = value;
	}

	/**
	 * Returns the Getter
	 * 
	 * @return The Getter
	 */
	public Function<T, P> getGetter() {
		return getter;
	}

	/**
	 * Returns the Value
	 * 
	 * @return The Value
	 */
	public P getValue() {
		return value;
	}
}