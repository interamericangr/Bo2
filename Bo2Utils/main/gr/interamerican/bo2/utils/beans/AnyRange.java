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
package gr.interamerican.bo2.utils.beans;



/**
 * {@link AnyRange} is a {@link Range} that contains all values.
 * 
 * This implementation of Range treats any value, even null as
 * if it is contained within it.
 * 
 * @param <T> Type of element.
 */
public final class AnyRange<T extends Comparable<? super T>>
extends Range<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new Range object.
	 * 
	 */
	public AnyRange() {
		super(null, null);
	}

	@Override
	public boolean overlapsWith(Range<T> range) {
		return true;
	}

	@Override
	public boolean contains(T value) {
		return true;
	}

	@Override
	public int compareTo(Range<T> other) {
		if (other instanceof AnyRange) {
			return 0;
		}
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof AnyRange);
	}

	@Override
	public String toString() {
		return "..."; //$NON-NLS-1$
	}

	@Override
	public int hashCode() {
		return 891584;
	}

	@Override
	public void setLeft(T left) {/* empty */}

	@Override
	public void setRight(T right) {/* empty */}



}
