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
 * Something that is {@link Serializable} and a {@link Function}.
 * 
 * @param <T>
 *            the type of the input to the function
 * @param <R>
 *            the type of the result of the function
 */
@FunctionalInterface
public interface SerializableFunction<T, R> extends Serializable, Function<T, R> {

	/**
	 * Synthesizes a new {@link SerializableFunction} from the 2 input
	 * {@link Function}s with the use of the
	 * {@link FunctionalUtils#nullSafeSynthesize(Function, Function)}
	 * 
	 * @param first
	 *            First {@link Function}
	 * @param second
	 *            Second {@link Function}
	 * @return Resulting {@link SerializableFunction}
	 */
	static <A, B, C> SerializableFunction<A, C> nullSafeSynthesize(SerializableFunction<A, B> first,
			SerializableFunction<B, C> second) {
		return (a) -> FunctionalUtils.nullSafeSynthesize(first, second, a);
	}

	/**
	 * Synthesizes a new {@link SerializableFunction} from this and an input
	 * {@link SerializableFunction}.<br>
	 * Just calls
	 * {@link #nullSafeSynthesize(SerializableFunction, SerializableFunction)}
	 * that will invoke
	 * {@link FunctionalUtils#nullSafeSynthesize(Function, Function)}.
	 * 
	 * @param after
	 *            {@link SerializableFunction}
	 * @return Resulting {@link SerializableFunction}
	 */
	default <V> SerializableFunction<T, V> andThen(SerializableFunction<R, V> after) {
		return nullSafeSynthesize(this, after);
	}
}