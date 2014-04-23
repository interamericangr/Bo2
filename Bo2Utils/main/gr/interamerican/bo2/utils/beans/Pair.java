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

import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;

import java.io.Serializable;

/**
 * A pair of two objects, the left and right objects.
 * 
 * @param <L> Type of the left object. 
 * @param <R> Type of the right object.
 */
public class Pair<L, R> implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * creates a new pair from the first two elements of an array.
	 * 
	 * @param array Array.
	 * @param <T> Type of elements.
	 * 
	 * @return Returns a pair.
	 */
	public static <T> Pair<T, T> pair(T[] array) {
		return new Pair<T,T>(array[0],array[1]);
	}
	
	/**
	 * left object.
	 */
	private L left;
	
	/**
	 * right object.
	 */
	private R right;
	
	/**
	 * Creates a new Pair object. 
	 *
	 */
	public Pair() {
		super();		
	}

	/**
	 * Creates a new Pair object. 
	 *
	 * @param left
	 * @param right
	 */
	public Pair(L left, R right) {
		this();
		this.left = left;
		this.right = right;
	}

	/**
	 * Gets the left.
	 *
	 * @return Returns the left
	 */
	public L getLeft() {
		return left;
	}

	/**
	 * Assigns a new value to the left.
	 *
	 * @param left the left to set
	 */
	public void setLeft(L left) {
		this.left = left;
	}

	/**
	 * Gets the right.
	 *
	 * @return Returns the right
	 */
	public R getRight() {
		return right;
	}

	/**
	 * Assigns a new value to the right.
	 *
	 * @param right the right to set
	 */
	public void setRight(R right) {
		this.right = right;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (obj instanceof Pair) {
			@SuppressWarnings("rawtypes") 
			Pair that = (Pair) obj;
			return Utils.equals(this.getLeft(), that.getLeft())
			    && Utils.equals(this.getRight(), that.getRight());
		}
		return false;
	}

	@Override
	public int hashCode() {		
		Object[] fields = {left,right};
		return Utils.generateHashCode(fields);
	}
		
	@Override @SuppressWarnings("nls")
    public String toString() {    
		String l = StringUtils.toString(left);
		String r = StringUtils.toString(right);
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(l);
		sb.append(" , ");
		sb.append(r);
		sb.append("]");
    	return  sb.toString(); 
    }

}
