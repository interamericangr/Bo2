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
 * Creates a {@link Pair} whose left value is {@link Comparable}.
 * 
 * @param <L>
 *        Type of left key
 * @param <R>
 *        Type of right key
 */
public class PairWithComparableLeft
<L extends Comparable<? super L>, R>
extends Pair<L, R> implements Comparable<PairWithComparableLeft<L, R>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new SortablePair object.
	 * 
	 * @param left
	 * @param right
	 */
	public PairWithComparableLeft(L left, R right) {
		super(left, right);
	}

	@Override
	public L getLeft() {
		return super.getLeft();
	}

	public int compareTo(PairWithComparableLeft<L, R> o) {
		if(o==null) {
			return 1;
		}
		if(o.getLeft()==null) {
			if(getLeft()==null) {
				return 0;
			}
			return 1;
		}
		return getLeft().compareTo(o.getLeft());
	}

}
