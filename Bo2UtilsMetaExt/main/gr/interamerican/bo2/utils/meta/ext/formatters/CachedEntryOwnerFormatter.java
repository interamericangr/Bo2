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
package gr.interamerican.bo2.utils.meta.ext.formatters;

import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;

/**
 * Formatter for cached entry owners.
 *  
 * @param <T>
 *        Type of entry. 
 * @param <C>
 *        Type of entry code. 
 */
public class CachedEntryOwnerFormatter
<T extends TranslatableEntryOwner<C,?,?>,C extends Comparable<? super C>>
implements Formatter<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * C formatter.
	 */
	Formatter<C> codeFormatter;
	
	/**
	 * Creates a new CachedEntryFormatter object. 
	 * @param codeFormatter 
	 */
	public CachedEntryOwnerFormatter(Formatter<C> codeFormatter) {
		this.codeFormatter = codeFormatter;
	}

	public String format(T t) {
		if(t==null) {
			return StringConstants.NULL;
		}
		return codeFormatter.format(t.getEntry().getCode());
	}

}
