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

import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;
import gr.interamerican.bo2.utils.beans.TypeBasedSelection;
import gr.interamerican.bo2.utils.meta.formatters.nr.NrBooleanFormatter;
import gr.interamerican.bo2.utils.meta.formatters.nr.NrDateFormatter;
import gr.interamerican.bo2.utils.meta.formatters.nr.NrObjectFormatter;

import java.util.Date;

/**
 * Returns a {@link Formatter}, based on the type of the
 * object that is to be formatter by the Formatter.
 * 
 * TODO: Register more appropriate formatters for number types.
 */
public class FormatterResolver  {
	
	/**
	 * Type based selection for default formatters that return "NULL" for null input.
	 */
	private static TypeBasedSelection<Formatter<?>> formatters;
	static {
		formatters = new TypeBasedSelection<Formatter<?>>();
		formatters.registerSelection(Boolean.class, new BooleanFormatter("1", "0")); //$NON-NLS-1$ //$NON-NLS-2$
		formatters.registerSelection(Integer.class, ObjectFormatter.<Integer>getInstance());
		formatters.registerSelection(Long.class, ObjectFormatter.<Long>getInstance());
		formatters.registerSelection(Short.class, ObjectFormatter.<Short>getInstance());
		formatters.registerSelection(String.class, ObjectFormatter.<String>getInstance());
		formatters.registerSelection(Date.class, new DateFormatter(Bo2UtilsEnvironment.getShortDateFormatPattern()));
	}
	
	/**
	 * Type based selection for formatters that return null for null input.
	 */
	private static TypeBasedSelection<Formatter<?>> nrFormatters;
	static {
		nrFormatters = new TypeBasedSelection<Formatter<?>>();
		nrFormatters.registerSelection(Boolean.class, new NrBooleanFormatter("1", "0")); //$NON-NLS-1$ //$NON-NLS-2$
		nrFormatters.registerSelection(Integer.class, ObjectFormatter.<Integer>getInstance());
		nrFormatters.registerSelection(Long.class, NrObjectFormatter.<Long>getInstance());
		nrFormatters.registerSelection(Short.class, NrObjectFormatter.<Short>getInstance());
		nrFormatters.registerSelection(String.class, NrObjectFormatter.<String>getInstance());
		nrFormatters.registerSelection(Date.class, new NrDateFormatter(Bo2UtilsEnvironment.getShortDateFormatPattern()));
	}

	/**
	 * Gets the formatter that can format an object to a String
	 * based on the specified object type.
	 * 
	 * @param type
	 *        Class of C
	 *        
	 * @param <C>
	 *        Type of object to be formatted
	 *        
	 * @return Returns the appropriate Formatter implementation.
	 */
	@SuppressWarnings("unchecked")
	public static <C> Formatter<C> getFormatter(Class<C> type) {
		return (Formatter<C>) formatters.selectionForType(type);
	}
	
	/**
	 * Gets the null returning formatter that can format an object to a String
	 * based on the specified object type.
	 * 
	 * @param type
	 *        Class of C
	 *        
	 * @param <C>
	 *        Type of object to be formatted
	 *        
	 * @return Returns the appropriate Formatter implementation.
	 */
	@SuppressWarnings("unchecked")
	public static <C> Formatter<C> getNrFormatter(Class<C> type) {
		return (Formatter<C>) nrFormatters.selectionForType(type);
	}
	
	/**
	 * Hidden constructor of utility class.
	 */
	private FormatterResolver() { /* hidden */	}

}
