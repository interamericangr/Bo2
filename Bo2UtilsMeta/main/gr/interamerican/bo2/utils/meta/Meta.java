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

import gr.interamerican.bo2.utils.beans.MessageGetter;
import gr.interamerican.bo2.utils.beans.Range;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Utility class.
 */
public class Meta {
	
	/**
	 * Hidden constructor. 
	 *
	 */
	private Meta() {
		/* empty */
	}
	
	/**
	 * Local cache of {@link MessageGetter} with Locale.
	 */
	static Map<Locale, MessageGetter> messagesMap = new HashMap<Locale, MessageGetter>();
	
	/**
	 * Resource bundle base path.
	 */
	static String resourceBundlePath = "gr.interamerican.bo2.utils.meta.resources.Messages"; //$NON-NLS-1$


	/**
	 * Gets the Meta {@code}link MessageGetter} with default {@link Locale}.
	 * 
	 * @return Returns MessageGetter.
	 */
	public static MessageGetter getMessages() {
		if(MetaSession.getLocale()!=null) {
			return getMessages(MetaSession.getLocale());
		}
		return getMessages(Locale.getDefault());
	}
	
	/**
	 * Gets the Meta {@code}link MessageGetter} with specified {@link Locale}.
	 * @param locale 
	 * 
	 * @return Returns MessageGetter.
	 */
	private static MessageGetter getMessages(Locale locale) {
		Locale current = locale;
		if(locale==null) {
			current = Locale.getDefault();
		}
		MessageGetter result = messagesMap.get(current); 
		if(result!=null) {
			return result;
		}
		result = new MessageGetter(resourceBundlePath, current);
		messagesMap.put(current, result);
		return result;
	}
	
	/**
	 * Creates a range from two string values.
	 * @param <T> 
	 * @param left
	 * @param right
	 * @param descriptor
	 * @return Returns a range.
	 * @throws ParseException 
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public static <T> Range
	parse(String left, String right, BoPropertyDescriptor<?> descriptor) 
	throws ParseException {
		BoPropertyDescriptor<T> des = (BoPropertyDescriptor<T>) descriptor;
		
		T leftValue = des.parse(left);
		T rightValue = des.parse(right);
		Comparable<? super T> l = (Comparable<? super T>) leftValue; 
		Comparable<? super T> r = (Comparable<? super T>) rightValue;
		return new Range(l, r);
	}
	
	/**
	 * Formats a value.
	 * 
	 * @param <T>
	 * @param value
	 * @param descriptor
	 * @return Returns a range.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <T> String
	format(Object value, BoPropertyDescriptor<?> descriptor) {
		BoPropertyDescriptor<T> des = (BoPropertyDescriptor<T>) descriptor;
		T val = (T) value;
		return des.format(val);
	}
	
	/**
	 * Creates a {@link Formatter} that delegates the format method
	 * to a {@link BoPropertyDescriptor}.
	 *      
	 * @param boPD
	 *        BoPropertyDescriptor.
	 * @param <T>
	 *        Type of object formatted.
	 * 
	 * @return Returns a formatter.
	 * 
	 */	
	public static <T> Formatter<T> getFormatter(final BoPropertyDescriptor<T> boPD) {
		Formatter<T> formatter = new Formatter<T>() {
			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = 1L;
			public String format(T t) {				
				return boPD.format(t);
			}
		};
		return formatter;
	}
	/**
	 * Creates a default formatter length
	 */
	public static int getDefaultFormatterLength = 10;
}
