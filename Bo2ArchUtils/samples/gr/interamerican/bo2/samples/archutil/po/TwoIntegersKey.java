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
package gr.interamerican.bo2.samples.archutil.po;

import gr.interamerican.bo2.arch.Key;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.comparators.ArrayComparator;

/**
 * Key that consists of two integers.
 */
public class TwoIntegersKey
implements Key {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * first integer.
	 */
	private Integer first;
	/**
	 * second integer.
	 */
	private Integer second;
	/**
	 * Creates a new TwoIntegersKey object. 
	 *
	 */
	public TwoIntegersKey() {
		super();
	}
	/**
	 * Creates a new TwoIntegersKey object. 
	 *
	 * @param first
	 * @param second
	 */
	public TwoIntegersKey(Integer first, Integer second) {
		super();
		this.first = first;
		this.second = second;
	}
	/**
	 * Gets the first.
	 *
	 * @return Returns the first
	 */
	public Integer getFirst() {
		return first;
	}
	/**
	 * Assigns a new value to the first.
	 *
	 * @param first the first to set
	 */
	public void setFirst(Integer first) {
		this.first = first;
	}
	/**
	 * Gets the second.
	 *
	 * @return Returns the second
	 */
	public Integer getSecond() {
		return second;
	}
	/**
	 * Assigns a new value to the second.
	 *
	 * @param second the second to set
	 */
	public void setSecond(Integer second) {
		this.second = second;
	}
	
	@Override
	public int compareTo(Key o) {
		if (o==this) {
			return 0;
		}
		if (o==null) {
			return 1;
		}
		if (o instanceof TwoIntegersKey) {
			TwoIntegersKey that = (TwoIntegersKey) o;
			Integer[] thisFields = {first,second};
			Integer[] thatFields = {that.first,that.second};
			ArrayComparator comp = new ArrayComparator();
			return comp.compare(thisFields, thatFields);
		}
		return -1;
	}	
	@Override
	public int hashCode() {
		Integer[] thisFields = {first,second};
		return Utils.generateHashCode(thisFields);
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Key) {
			return this.compareTo((Key)obj)==0;
		}
		return false;
	}


}
