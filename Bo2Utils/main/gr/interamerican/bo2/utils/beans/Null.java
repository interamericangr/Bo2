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
 * Null safe representation of null.
 * 
 * {@link Null} is comparable with any other object.
 * It is equal only with other Null objects and with null.
 * It is less than any other object.
 * 
 */
public final class Null implements Comparable<Object>{
	
	/**
	 * Singleton instance;
	 */
	public static final Null NULL = new Null();	
	
	/**
	 * Creates a new NullObject object.
	 */
	private Null() {
		super();		
	}
	
	@Override
	public boolean equals(Object obj) {		
		return obj == null || obj instanceof Null;
	}
	
	@Override
	public int hashCode() {		
		return 0;
	}
	
	public int compareTo(Object o) {
		if (this.equals(o)) {
			return 0;
		}
		return -1;
	}	
	
	@Override
	public String toString() {		
		return "null"; //$NON-NLS-1$
	}
	
	

}
