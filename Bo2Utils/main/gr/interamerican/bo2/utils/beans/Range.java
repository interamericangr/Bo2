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

import gr.interamerican.bo2.utils.Utils;


/**
 * Range represents a range between two comparable values.
 * 
 * @param <T> Type of elements in the range.
 */
public class Range<T extends Comparable<? super T>> 
extends Pair<T, T>
implements Comparable<Range<T>> {

	/**
	 * Creates a new Range object.
	 * 
	 */
	public Range() {
		super();
	} 

	/**
	 * Creates a new Range object.
	 * 
	 * @param left
	 * @param right
	 */
	public Range(T left, T right) {
		super();		
		if (left==null) {
			super.setLeft(null);
			super.setRight(right);			
		} else if (right==null) {
			super.setLeft(null);
			super.setRight(left);
		} else if (left.compareTo(right)>0) {
			super.setLeft(right);
			super.setRight(left);			
		} else {
			super.setLeft(left);
			super.setRight(right);
		}
	}
	
	@Override
	public void setLeft(T left) {
		T oldRight = getRight();
		if (Utils.nullSafeCompare(left, oldRight)>0) {
			if (getLeft()==null) {
				super.setLeft(left);
				super.setRight(left);
			} else {
				super.setLeft(getRight());
				super.setRight(left);
			}
		} else {
			super.setLeft(left);
		}
	}
	
	@Override
	public void setRight(T right) {
		if (Utils.nullSafeCompare(right, getLeft())<0) {
			super.setRight(getLeft());
			super.setLeft(right);
		} else {
			super.setRight(right);
		}
	}
	
	/**
	 * Checks if this object has a common intersection with the range specified.
	 * 
	 * @param range
	 *        Range object being checked for intersection with this range.
	 * @return Returns true if this range has a common intersection with
	 *         the other range.
	 */
	public boolean overlapsWith(Range<T> range) {
		return contains(range.getLeft()) 
		|| contains(range.getRight())
		|| range.contains(this.getLeft())
		|| range.contains(this.getRight());
	}

	/**
	 * Checks if a value is contained within this range.
	 * 
	 * @param value
	 *        Value that is searched if is contained within this range.
	 * @return Returns true if this range contains the specified value.
	 */
	public boolean contains(T value) {
		return Utils.nullSafeCompare(value, getLeft()) >= 0
		    && Utils.nullSafeCompare(value, getRight()) <= 0;
	}

	public int compareTo(Range<T> other) {
		if (other == null) {
			return 1;
		}
		int compareLeft = Utils.nullSafeCompare(getLeft(), other.getLeft());
		if (compareLeft == 0) {
			return Utils.nullSafeCompare(getRight(), other.getRight());
		}
		return compareLeft;
	}
	
	/**
	 * Unsafe rawtype delegate to range.contains(t).
	 * 
	 * @param range Range
	 * @param object
	 * @return Returns true if object is contained in the range.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean contains(Range range, Object object) {
		Comparable val = (Comparable) object;
		return range.contains(val);
	}
    
}
