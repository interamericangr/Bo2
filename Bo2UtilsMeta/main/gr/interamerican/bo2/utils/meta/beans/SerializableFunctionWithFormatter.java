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
package gr.interamerican.bo2.utils.meta.beans;

import java.io.Serializable;

import gr.interamerican.bo2.utils.functions.SerializableFunction;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;

/**
 * A {@link SerializableFunction} with a relevant {@link Formatter}.
 * 
 * @param <T>
 *            Type of SerializableFunction From
 * @param <V>
 *            Type of Result of the SerializableFunction and the Formatter
 */
public class SerializableFunctionWithFormatter<T, V> implements Serializable {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The function
	 */
	final SerializableFunction<T, V> function;

	/**
	 * The formatter
	 */
	final Formatter<V> formatter;

	/**
	 * The header
	 */
	final String header;

	/**
	 * Constructor
	 *
	 * @param function
	 *            The function
	 * @param formatter
	 *            The formatter
	 * @param header
	 *            The header
	 */
	public SerializableFunctionWithFormatter(SerializableFunction<T, V> function, Formatter<V> formatter, String header) {
		this.function = function;
		this.formatter = formatter;
		this.header = header;
	}

	/**
	 * Gets the function.
	 *
	 * @return Returns the function
	 */
	public SerializableFunction<T, V> getFunction() {
		return function;
	}

	/**
	 * Gets the formatter.
	 *
	 * @return Returns the formatter
	 */
	public Formatter<V> getFormatter() {
		return formatter;
	}

	/**
	 * Gets the header.
	 *
	 * @return Returns the header
	 */
	public String getHeader() {
		return header;
	}
}