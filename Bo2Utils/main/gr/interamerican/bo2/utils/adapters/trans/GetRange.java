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
package gr.interamerican.bo2.utils.adapters.trans;

import java.util.function.Function;

import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.beans.Range;

/**
 * Creates a range defined by two {@link Function}s.
 * 
 * @param <T>
 *            Type of object being transformed to {@link Range}.
 * @param <P>
 *            Type of Range limits.
 */
public class GetRange<T, P extends Comparable<? super P>>
implements Transformation<T, Range<P>> {

	/**
	 * Function for left limit.
	 */
	Function<T, P> getLeft;
	/**
	 * Function for right limit.
	 */
	Function<T, P> getRight;

	/**
	 * Public Constructor
	 *
	 * @param getLeft
	 *            Function that defines the left limit of the range.
	 * @param getRight
	 *            Function that defines the right limit of the range.
	 */
	public GetRange(Function<T, P> getLeft, Function<T, P> getRight) {
		this.getLeft = getLeft;
		this.getRight = getRight;
	}

	@Override
	public Range<P> execute(T a) {
		P left = getLeft.apply(a);
		P right = getRight.apply(a);
		return new Range<P>(left, right);
	}
}