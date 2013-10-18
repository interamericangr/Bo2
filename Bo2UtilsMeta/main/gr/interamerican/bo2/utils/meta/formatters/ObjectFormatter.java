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

import gr.interamerican.bo2.utils.StringUtils;


/**
 * Generic {@link Formatter} that delegates the <code>format(object)</code> 
 * method to the object's <code>toString(object)</code> method.
 */
public class ObjectFormatter 
implements Formatter<Object> {
	
	/**
	 * Singleton.
	 */
	public static final ObjectFormatter INSTANCE = new ObjectFormatter();
	
	/**
	 * Reusable thread safe instance.
	 * 
	 * @param <T>
	 *        Object type.
	 *        
	 * @return Singleton instance of {@link ObjectFormatter}.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Formatter<T> getInstance() {
		return (Formatter<T>) INSTANCE;
	}
		
	public String format(Object t) {
		return StringUtils.toString(t);
	}

}
