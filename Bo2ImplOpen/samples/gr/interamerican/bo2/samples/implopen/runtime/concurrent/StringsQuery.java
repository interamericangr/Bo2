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
package gr.interamerican.bo2.samples.implopen.runtime.concurrent;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.workers.IteratorQuery;

import java.util.Arrays;
import java.util.Iterator;

/**
 * 
 */
public class StringsQuery 
extends IteratorQuery<String> {
	/**
	 * Count of strings.
	 */
	int countOfString;

	/**
	 * Creates a new StringsQuery object. 
	 * 
	 * @param countOfString 
	 *
	 */
	public StringsQuery(int countOfString) {
		super();
		this.countOfString = countOfString;
	}
	
	@Override
	protected Iterator<String> createIterator() throws DataException {
		String[] strings = new String[countOfString];
		for (int i = 0; i < strings.length; i++) {
			strings[i] = Integer.toString(i);
		}
		return Arrays.asList(strings).iterator(); 
	}

}
