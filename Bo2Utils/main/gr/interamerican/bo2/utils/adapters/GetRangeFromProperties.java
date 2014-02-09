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
package gr.interamerican.bo2.utils.adapters;

import gr.interamerican.bo2.utils.beans.Range;

/**
 * Creates a range defined by two properties of an object.
 * 
 * @param <T> 
 *        Type of object being transformed to {@link Range}.
 * @param <P> 
 *        Type of Range limits.
 * 
 */
public class GetRangeFromProperties<T, P extends Comparable<? super P>>
implements Transformation<T, Range<P>> {
	
	/**
	 * Get left property.
	 */
	GetProperty<T, P> getLeft;
	/**
	 * Get right property.
	 */
	GetProperty<T, P> getRight;
	
	
	/**
	 * Creates a new GetRangeFromProperties object. 
	 * 
	 * @param leftProperty
	 *        Expression of property that defines the left limit of the range. 
	 * @param rightProperty
	 *        Expression of property that defines the right limit of the range. 
	 * @param clazz 
	 *
	 */
	public GetRangeFromProperties(String leftProperty, String rightProperty, Class<T> clazz) {
		super();
		getLeft = new GetProperty<T, P>(leftProperty, clazz);
		getRight = new GetProperty<T, P>(rightProperty, clazz);
	}


	public Range<P> execute(T a) {
		P left = getLeft.execute(a);
		P right = getRight.execute(a);
		return new Range<P>(left, right);
	}

}
