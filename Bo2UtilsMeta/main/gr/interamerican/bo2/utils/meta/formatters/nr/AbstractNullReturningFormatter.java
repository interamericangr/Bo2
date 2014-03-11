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
package gr.interamerican.bo2.utils.meta.formatters.nr;

import gr.interamerican.bo2.utils.meta.formatters.Formatter;

/**
 * Generic {@link Formatter} that returns null if the input object is
 * null. <br/>
 * 
 * @param <T> 
 *        Type of objects being formatted by this formatter.
 *
 */
public abstract  class AbstractNullReturningFormatter<T> 
implements Formatter<T> {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
		
	public String format(T t) {
		if (t==null) {
			return null;
		}
		return mainFormat(t);
	}
	
	/**
	 * Null safe format.
	 * 
	 * @param t
	 *        Object to format. Can never be null.
	 *         
	 * @return Returns the string representation of t.
	 */
	protected abstract String mainFormat(T t);

}
