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
package gr.interamerican.bo2.utils.meta.descriptors;

import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;

import java.util.Set;

/**
 * Property description for a property that is selected by a list of values.
 * 
 * @param <T>
 *        Type of property. 
 */
public class SelectionBoPropertyDescriptor<T> 
extends AbstractBoPropertyDescriptor<T> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new SelectionBoPropertyDescriptor object. 
	 */
	public SelectionBoPropertyDescriptor() {
		super(null);
	}

	/**
	 * set of values.
	 */
	private Set<T> values;

	/**
	 * Gets the set of values.
	 * 
	 * @return Returns the set of values.
	 */
	public Set<T> getValues() {
		return values;
	}

	/**
	 * Sets the set of values.
	 * 
	 * @param values New set of values.
	 */
	public void setValues(Set<T> values) {
		this.values = values;
	}	

	@Override
	public T parse(String value) throws ParseException {
		for (T entry : values) {
			if (entry.toString().equals(value)) {
				return entry;
			}
		}
		return null;
	}

	@Override
	protected Formatter<T> getFormatter() {
		return ObjectFormatter.<T>getInstance();
	}

}
