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
import gr.interamerican.bo2.utils.meta.formatters.nf.AbstractNullFilteringFormatter;


/**
 * Sub-class of {@link AbstractNullFilteringFormatter} that delegates the 
 * <code>mainFormat(t)</code> method to another formatter.
 * 
 * @param <T> 
 *        Type of objects being formatted by this formatter.
 *
 */
public class NullReturningFormatter<T> 
extends AbstractNullReturningFormatter<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Formatter.
	 */
	Formatter<T> formatter;
	
	/**
	 * Creates a new NsFormatter object. 
	 *
	 * @param formatter
	 */
	public NullReturningFormatter(Formatter<T> formatter) {
		super();
		this.formatter = formatter;
	}

	@Override
	protected String mainFormat(T t) {
		return formatter.format(t);
	}

}
