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
package gr.interamerican.bo2.utils.meta.ext.formatters.nr;

import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;
import gr.interamerican.bo2.utils.meta.ext.formatters.CachedEntryOwnerFormatter;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.nr.NullReturningFormatter;

/**
 * Null returning {@link CachedEntryOwnerFormatter}.
 * 
 * @param <T>
 *        Type of entry. 
 * @param <C> 
 *        Type of code.
 */
public class NrCachedEntryOwnerFormatter
<T extends TranslatableEntryOwner<C,?,?>,C extends Comparable<? super C>>
extends NullReturningFormatter<T>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * C formatter.
	 */
	Formatter<C> codeFormatter;

	/**
	 * Creates a new NrCachedEntryFormatter object. 
	 * @param codeFormatter 
	 */
	public NrCachedEntryOwnerFormatter(Formatter<C> codeFormatter) {
		super(new CachedEntryOwnerFormatter<T, C>(codeFormatter));
		this.codeFormatter = codeFormatter;
	}
	
	@Override
	protected String mainFormat(T t) {
		return codeFormatter.format(t.getEntry().getCode());
	}

}
