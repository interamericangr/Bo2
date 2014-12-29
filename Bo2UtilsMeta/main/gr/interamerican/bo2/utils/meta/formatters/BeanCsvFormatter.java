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

import gr.interamerican.bo2.utils.AdapterUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.adapters.Modification;
import gr.interamerican.bo2.utils.adapters.trans.GetProperties;
import gr.interamerican.bo2.utils.adapters.trans.string.StringReplace;
import gr.interamerican.bo2.utils.meta.transformations.FormatArray;

import java.util.Arrays;
import java.util.Map;

/**
 * Formats a bean as CSV.
 * 
 * @param <B> 
 *        Type of bean.
 */
public class BeanCsvFormatter<B> implements Formatter<B> {
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Extracts the values of the properties.
	 */
	GetProperties get;
	/**
	 * Formats the values of the properties.
	 */
	FormatArray format;
	/**
	 * The delimiter we used. Default is {@link StringConstants#SEMICOLON}.
	 */
	String delimiter = StringConstants.SEMICOLON;

	/**
	 * Option whenever to remove the delimiter from columns and replace it with SPACE
	 */
	boolean removeDelimiterFromColumns = true;
	
	/**
	 * Creates a new BeanFormatter object. Default property value formatting.
	 * @param properties
	 *        ORDERED property names to include as CSV
	 * @param nullTolerant
	 *        Indicates if the nested property fetching should be null tolerant. @see GetProperties
	 */
	public BeanCsvFormatter(String[] properties, boolean nullTolerant) {
		this(properties, defaultFormatters(properties.length, null), nullTolerant);
	}
	
	/**
	 * Creates a new BeanFormatter object. 
	 * @param properties
	 *        ORDERED property names to include as CSV 
	 * @param formatters
	 *        ORDERED formatters to format property values with 
	 * @param nullTolerant
	 *        Indicates if the nested property fetching should be null tolerant. @see GetProperties
	 */
	public BeanCsvFormatter(String[] properties, Formatter<?>[] formatters, boolean nullTolerant) {
		this.get = new GetProperties(properties, nullTolerant);
		this.format = new FormatArray(formatters);
	}
	
	/**
	 * Creates a new BeanFormatter object. 
	 * @param properties
	 *        ORDERED property names to include as CSV 
	 * @param specialFormatters
	 *        A map containing any special (non-default) formatters required along with
	 *        the index of the property they correspond to.
	 * @param nullTolerant
	 *        Indicates if the nested property fetching should be null tolerant. @see GetProperties
	 *        
	 */
	public BeanCsvFormatter(String[] properties, Map<Integer, Formatter<?>> specialFormatters, boolean nullTolerant) {
		this(properties, defaultFormatters(properties.length, specialFormatters), nullTolerant);
	}

	@Override
	public String format(B t) {
		Object[] values = get.execute(t);
		String[] columns = format.execute(values);
		if (removeDelimiterFromColumns) {
			columns = removeDelimiterFromColumns(columns);
		}
		return StringUtils.array2String(columns, delimiter);
	}

	/**
	 * Replaces the {@link #delimiter} from an array of String with
	 * {@link StringConstants#SPACE}.
	 * 
	 * @param columns
	 *            Values to replace from
	 * @return Array of String with replaced values
	 */
	String[] removeDelimiterFromColumns(String[] columns) {
		Modification<String> replace = new StringReplace(delimiter, StringConstants.SPACE);
		return AdapterUtils.apply(Arrays.asList(columns), replace).toArray(new String[0]);
	}

	/**
	 * Creates a Formatters table with the specified length.
	 * 
	 * @param length
	 * @param specialFormatters 
	 * 
	 * @return Returns the table.
	 */
	private static Formatter<?>[] defaultFormatters(int length, Map<Integer, Formatter<?>> specialFormatters) {
		Formatter<?>[] formatters = new Formatter<?>[length];
		for (int i = 0; i < formatters.length; i++) {
			formatters[i] = ObjectFormatter.INSTANCE;
		}
		if(specialFormatters==null) {
			return formatters;
		}
		for(Map.Entry<Integer, Formatter<?>> entry : specialFormatters.entrySet()) {
			formatters[entry.getKey()] = entry.getValue();
		}
		return formatters;
	}

	/**
	 * Sets the delimiter we are supposed to use.<br>
	 * Default is {@link StringConstants#SEMICOLON}
	 * 
	 * @param delimiter
	 *            Delimiter to use
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	/**
	 * Sets whenever we want to remove the delimiter character from the columns
	 * themselves. (in order to avoid accidental extra columns on the csv)
	 * 
	 * @param removeDelimiterFromColumns
	 *            Whenever we want to remove the delimiter character from the
	 *            columns themselves
	 */
	public void setRemoveDelimiterFromColumns(boolean removeDelimiterFromColumns) {
		this.removeDelimiterFromColumns = removeDelimiterFromColumns;
	}
}