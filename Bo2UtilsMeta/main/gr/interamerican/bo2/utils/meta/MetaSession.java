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
package gr.interamerican.bo2.utils.meta;

import java.util.Locale;

/**
 * ThreadLocal info for Bo2UtilsMeta.
 */
public class MetaSession {
	
	/**
	 * Threadlocal locale.
	 */
	static ThreadLocal<Locale> tlLocale = new ThreadLocal<Locale>();
	
	/**
	 * @return Returns the locale for the current thread.
	 */
	public static Locale getLocale() {
		return tlLocale.get();
	}
	
	/**
	 * @param locale
	 *        Sets the locale for the current thread.
	 */
	public static void setLocale(Locale locale) {
		tlLocale.set(locale);
	}

}
