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
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;

/**
 * NullFilteringFormatter for Numbers.
 */
public class NrObjectFormatter extends NullReturningFormatter<Object> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Singleton.
	 */
	public static final NrObjectFormatter INSTANCE = new NrObjectFormatter();
	
	/**
	 * Access Singleton instance.
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
	
	/**
	 * Hidden, access this only from the Singleton instance.
	 */
	private NrObjectFormatter() {
		super(ObjectFormatter.<Object>getInstance());
	}
	
}
