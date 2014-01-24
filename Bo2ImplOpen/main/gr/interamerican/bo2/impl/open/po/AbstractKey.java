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
package gr.interamerican.bo2.impl.open.po;

import gr.interamerican.bo2.arch.Key;
import gr.interamerican.bo2.utils.ArrayUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.comparators.ArrayComparator;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Abstract basic implementation of {@link Key}.
 * 
 * This class uses an array of values for the implementation of the compareTo
 * and equals methods. Sub-types of this class must assign to this array the 
 * proper values. The class is using this array in order to be compared with 
 * other instances of {@link AbstractKey} using an {@link ArrayComparator}.
 * The <code>compareTo(Key key)</code> method will return -1 if the input to
 * the comparison is not an instance of AbstractKeyImpl. Namely, AbstractKeyImpl
 * is less than any other instance of Key. <br/>
 * Even though this class has no abstract methods, it is abstract. The attribute
 * <code>fields</code> is protected, hence their is  no way to assign any value 
 * to it, but only from within a sub-class.
 * 
 */
public abstract class AbstractKey 
implements Key {
	
	/**
	 * Serialization version UID
	 */
	private static final long serialVersionUID = 2L;
	
	/**
	 * generic comparator used for object comparison.
	 */
	private static final Comparator<Object[]> COMPARATOR =
		new ArrayComparator();
	

	/**
	 * Gets the fields.
	 *
	 * This is a protected getter. The <code>elements</code> array must not
	 * be exposed outside of the class. In such case, the array would be
	 * exposed to direct changes of its elements, that would bypass any
	 * internal logic of the class. For public exposure use the method
	 * <code>getElementsCopy()</code> that will return a copy of the fields
	 * array.
	 *   
	 * @return Returns the fields.
	 * 
	 * @see #getElementsCopy()
	 */
	protected abstract Object[] getElements();
	
	/**
	 * Gets a copy of the object's fields.
	 * 
	 * @return Returns a copy of the fields array that holds the
	 *         elements of the key.
	 *        
	 * @see #getElements()        
	 */
	public Object[] getElementsCopy() {
		Object[] flds = getElements();
		if (flds==null) {
			return new Object[0];
		}
		return ArrayUtils.copyOf(flds, flds.length);
	}
	
	/**
	 * Gets a copy of the object's fields.
	 * 
	 * @return Returns a copy of the fields array that holds the
	 *         elements of the key.
	 *        
	 * @see #getElements()        
	 */
	public int getElementsCount() {
		return getElements().length;
	}
	
	public int compareTo(Key key) {
		if (key == null) {
			return 1;
		}
		if (AbstractKey.class.isInstance(key)) {
			AbstractKey that = (AbstractKey) key;			
			return COMPARATOR.compare(this.getElements(), that.getElements());
		}
		return -1;
	}
		
	@Override
	public boolean equals(Object obj) {		
		if (obj==null) {
			return false;
		}
		Class<?> thisClass = this.getClass();
		Class<?> thatClass = obj.getClass();
		boolean compatibleTypes = thisClass==thatClass || thisClass.isInstance(obj) || thatClass.isInstance(this);
		if(!compatibleTypes) {
			return false;
		}
		if (AbstractKey.class.isInstance(obj))  {
			AbstractKey that = (AbstractKey) obj;
			return this.compareTo(that)==0;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Utils.generateHashCode(getElements());
	}
	
	@Override
	public String toString() {
		Object[] elements = getElements();
		return Arrays.toString(elements);
	}
	
}
