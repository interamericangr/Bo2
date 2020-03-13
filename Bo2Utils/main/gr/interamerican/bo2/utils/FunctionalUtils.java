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
package gr.interamerican.bo2.utils;

import java.util.function.Function;

/**
 * Utilities regarding Functional Interfaces.
 */
public class FunctionalUtils {

	/**
	 * Hidden Constructor
	 */
	public FunctionalUtils() {
		// empty
	}

	/**
	 * Synthesizes a {@link Function} from 2 other {@link Function}s in a null
	 * safe way.
	 * 
	 * @param <A>
	 *            First Type
	 * @param <B>
	 *            Second Type
	 * @param <C>
	 *            Third Type
	 * 
	 * @param first
	 *            First {@link Function}
	 * @param second
	 *            Second {@link Function}
	 * @return Resulting {@link Function}
	 */
	public static <A, B, C> Function<A, C> nullSafeSynthesize(Function<A, B> first, Function<B, C> second) {
		return a -> nullSafeSynthesize(first, second, a);
	}

	/**
	 * Invokes 2 {@link Function}s with a specific input and returns the final
	 * input, but in a null safe way.
	 * 
	 * @param <A>
	 *            First Type
	 * @param <B>
	 *            Second Type
	 * @param <C>
	 *            Third Type
	 * @param first
	 *            First {@link Function}
	 * @param second
	 *            Second {@link Function}
	 * @param input
	 *            The Input
	 * @return Resulting {@link Function}
	 */
	public static <B, A, C> C nullSafeSynthesize(Function<A, B> first, Function<B, C> second, A input) {
		if (input == null) {
			return null;
		}
		B b = first.apply(input);
		if (b == null) {
			return null;
		}
		return second.apply(b);
	}
}