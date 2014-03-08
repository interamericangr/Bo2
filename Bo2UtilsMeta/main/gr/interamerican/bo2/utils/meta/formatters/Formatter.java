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
package gr.interamerican.bo2.utils.meta.formatters;

import java.io.Serializable;

/**
 * A {@link Formatter} creates string representation of objects.
 * 
 * A Writer could get the string representation of an object by the object's 
 * <code>toString()</code> method. However a writer is more useful in cases
 * when a string representation of an object is necessary that is different 
 * than its toString() method.
 * 
 * @param <T> Type of objects represented as strings by the writer. 
 * 
 */
public interface Formatter<T> extends Serializable {
	
	/**
	 * Creates a string representation of an object.
	 * 
	 * @param t Object that will be represented as string.
	 * @return Returns the string representation of the specified object.
	 * 	 */
	String format(T t);

}
