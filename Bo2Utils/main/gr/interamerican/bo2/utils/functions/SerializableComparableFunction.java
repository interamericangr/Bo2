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
package gr.interamerican.bo2.utils.functions;

import java.io.Serializable;
import java.util.function.Function;

import gr.interamerican.bo2.utils.FunctionalUtils;

/**
 * Something that is {@link Serializable} and a {@link Function} over something
 * that is {@link Comparable}.
 * 
 * @param <T>
 *            the type of the input to the function
 * @param <R>
 *            the type of the result of the function
 */
@FunctionalInterface
public interface SerializableComparableFunction<T, R extends Comparable<? super R>> extends SerializableFunction<T, R> {

	/**
	 * Synthesizes a new {@link SerializableComparableFunction} from the 2 input
	 * {@link Function}s with the use of the
	 * {@link FunctionalUtils#nullSafeSynthesize(Function, Function)}
	 * 
	 * @param first
	 *            First {@link Function}
	 * @param second
	 *            Second {@link Function}
	 * @return Resulting {@link SerializableComparableFunction}
	 */
	static <A, B, C extends Comparable<C>> SerializableComparableFunction<A, C> nullSafeSynthesize(SerializableFunction<A, B> first,
			SerializableComparableFunction<B, C> second) {
		return (a) -> FunctionalUtils.nullSafeSynthesize(first, second, a);
	}
}